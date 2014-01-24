/*
 * Copyright (c) 2013 SK planet.
 * All right reserved.
 *
 * This software is the confidential and proprietary information of SK planet.
 * You shall not disclose such Confidential Information and
 * shall use it only in accordance with the terms of the license agreement
 * you entered into with SK planet.
 */
package com.skplanet.storeplatform.sac.purchase.order.service;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import org.apache.commons.lang3.time.DateFormatUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.skplanet.storeplatform.purchase.client.common.vo.Prchs;
import com.skplanet.storeplatform.purchase.client.common.vo.PrchsDtl;
import com.skplanet.storeplatform.purchase.client.dummypurchase.sci.DummyPurchaseSCI;
import com.skplanet.storeplatform.purchase.client.order.sci.PurchaseOrderSCI;
import com.skplanet.storeplatform.purchase.client.order.vo.ReservePurchaseReqSC;
import com.skplanet.storeplatform.sac.client.purchase.vo.order.CreatePurchaseReqProduct;
import com.skplanet.storeplatform.sac.purchase.order.precheck.CheckerManager;
import com.skplanet.storeplatform.sac.purchase.order.precheck.PurchaseOrderChecker;
import com.skplanet.storeplatform.sac.purchase.order.vo.PurchaseOrder;

/**
 * 
 * 구매 서비스 구현
 * 
 * Updated on : 2014. 1. 3. Updated by : 이승택, nTels.
 */
@Service
@Transactional
public class PurchaseOrderServiceImpl implements PurchaseOrderService {
	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private DummyPurchaseSCI dummyPurchaseSCI;

	@Autowired
	private PurchaseOrderSCI purchaseOrderSCI;

	private final CheckerManager checkerManager = new CheckerManager();

	/**
	 * 
	 * <pre>
	 * 구매 전처리.
	 * </pre>
	 * 
	 * @param purchaseInfo
	 *            구매요청 정보
	 */
	@Override
	public void checkPurchase(PurchaseOrder purchaseInfo) {
		List<PurchaseOrderChecker> checkerList = this.checkerManager.getCheckerList(null);

		Long startTime = System.currentTimeMillis();

		for (PurchaseOrderChecker checker : checkerList) {
			if (checker.isTarget(purchaseInfo) == false) {
				continue;
			}

			if (checker.checkAndSetInfo(purchaseInfo) == false) {
				break;
			}
		}

		this.logger.debug("PRCHS,DUMMY,CHECK," + (System.currentTimeMillis() - startTime) + "ms");
	}

	/**
	 * 
	 * <pre>
	 * 새로운 구매ID 생성.
	 * </pre>
	 * 
	 * @param tempPrchsReqPathCd
	 *            (임시파라미터) 구매요청경로코드
	 * @param tempProdId
	 *            (임시파라미터) 상품ID
	 * @return 새로 생성된 구매ID
	 */
	private String makePrchsId(String tempPrchsReqPathCd, String tempProdId) {
		// 구매ID
		String formattedNow = DateFormatUtils.format(Calendar.getInstance().getTimeInMillis(), "ddmmss");
		return "TAK" + formattedNow + tempPrchsReqPathCd.substring(8) + (int) (Math.random() * 1000)
				+ tempProdId.substring(7) + (int) (Math.random() * 1000);
	}

	/**
	 * 
	 * <pre>
	 * 무료구매 처리.
	 * </pre>
	 * 
	 * @param purchaseInfo
	 *            구매요청 정보
	 */
	@Override
	public void freePurchase(PurchaseOrder purchaseInfo) {
		String prchsId = this.makePrchsId(purchaseInfo.getPrchsReqPathCd(), purchaseInfo.getProductList().get(0)
				.getProdId());
		purchaseInfo.setPrchsId(prchsId);

		// 구매 이력 저장 데이터 세팅
		Prchs prchs = new Prchs();
		prchs.setTenantId(purchaseInfo.getTenantId());
		prchs.setPrchsId(prchsId);
		prchs.setInsdUsermbrNo(purchaseInfo.getUserKey());
		prchs.setInsdDeviceId(purchaseInfo.getDeviceKey());
		prchs.setPrchsStatusCd("OR000301");
		prchs.setTotAmt(purchaseInfo.getRealTotAmt());
		prchs.setPrchsReqPathCd(purchaseInfo.getPrchsReqPathCd());
		prchs.setCurrencyCd(purchaseInfo.getCurrencyCd());
		prchs.setClientIp(purchaseInfo.getClientIp());
		prchs.setNetworkTypeCd(purchaseInfo.getNetworkTypeCd());
		prchs.setRegId("testpurchase");
		prchs.setUpdId("testpurchase");

		prchs.setCancelReqPathCd(null);
		prchs.setCancelDt(null);
		prchs.setPrchsCaseCd(null);
		prchs.setResvCol01(null);
		prchs.setResvCol02(null);
		prchs.setResvCol03(null);
		prchs.setResvCol04(null);
		prchs.setResvCol05(null);

		// 구매상세 이력 저장 데이터 세팅
		PrchsDtl prchsDtl = new PrchsDtl();
		prchsDtl.setTenantId(prchs.getTenantId());
		prchsDtl.setPrchsId(prchs.getPrchsId());
		prchsDtl.setUseTenantId(prchs.getTenantId());
		prchsDtl.setUseInsdUsermbrNo(prchs.getInsdUsermbrNo());
		prchsDtl.setUseInsdDeviceId(prchs.getInsdDeviceId());
		prchsDtl.setTotAmt(prchs.getTotAmt());
		prchsDtl.setPrchsReqPathCd(prchs.getPrchsReqPathCd());
		prchsDtl.setClientIp(prchs.getClientIp());
		prchsDtl.setStatusCd(prchs.getPrchsStatusCd());
		prchsDtl.setHidingYn("N");
		prchsDtl.setRegId(prchs.getRegId());
		prchsDtl.setUpdId(prchs.getUpdId());

		// 구매상세 이력 저장
		int i = 0;
		int prchsDtlCnt = 1;
		for (CreatePurchaseReqProduct product : purchaseInfo.getCreatePurchaseReq().getProductList()) {
			prchsDtl.setProdId(product.getProdId());
			prchsDtl.setProdAmt(product.getProdAmt());
			prchsDtl.setProdQty(product.getProdQty());
			prchsDtl.setTenantProdGrpCd("GRP-1");
			prchsDtl.setRePrchsPmtYn("N");
			prchsDtl.setUseExprDt("99991231235959");
			prchsDtl.setDwldExprDt("99991231235959");

			for (i = 0; i < product.getProdQty(); i++) {
				prchsDtl.setPrchsDtlId(prchsDtlCnt++);

				this.dummyPurchaseSCI.createPrchsDtl(prchsDtl);
			}
		}

		// 구매 이력 저장
		this.dummyPurchaseSCI.createPrchs(prchs);

		// Payment payment = new Payment();
		// this.dummyPurchaseSCI.createPayment(payment);
	}

	/**
	 * 
	 * <pre>
	 * 유료구매 - 구매예약.
	 * </pre>
	 * 
	 * @param purchaseInfo
	 *            구매요청 정보
	 */
	@Override
	public void reservePurchase(PurchaseOrder purchaseInfo) {
		String prchsId = this.makePrchsId(purchaseInfo.getPrchsReqPathCd(), purchaseInfo.getProductList().get(0)
				.getProdId());

		purchaseInfo.setPrchsId(prchsId);

		// 구매상세 이력 저장 데이터 세팅
		List<PrchsDtl> prchsDtlList = new ArrayList<PrchsDtl>();
		PrchsDtl prchsDtl = null;
		int i = 0, prchsDtlCnt = 1;
		for (CreatePurchaseReqProduct product : purchaseInfo.getCreatePurchaseReq().getProductList()) {
			for (i = 0; i < product.getProdQty(); i++) {
				prchsDtl = new PrchsDtl();
				prchsDtl.setTenantId(purchaseInfo.getTenantId());
				prchsDtl.setPrchsId(prchsId);
				prchsDtl.setUseTenantId(purchaseInfo.getTenantId());
				prchsDtl.setUseInsdUsermbrNo(purchaseInfo.getUserKey());
				prchsDtl.setUseInsdDeviceId(purchaseInfo.getDeviceKey());
				prchsDtl.setStatusCd("OR000301");
				prchsDtl.setTotAmt(purchaseInfo.getRealTotAmt());
				prchsDtl.setPrchsReqPathCd(purchaseInfo.getPrchsReqPathCd());
				prchsDtl.setClientIp(purchaseInfo.getClientIp());
				prchsDtl.setHidingYn("N");
				prchsDtl.setRegId("testpurchase");
				prchsDtl.setUpdId("testpurchase");

				prchsDtl.setCancelReqPathCd(null);
				prchsDtl.setCancelDt(null);
				prchsDtl.setPrchsCaseCd(null);
				prchsDtl.setResvCol01(null);
				prchsDtl.setResvCol02(null);
				prchsDtl.setResvCol03(null);
				prchsDtl.setResvCol04(null);
				prchsDtl.setResvCol05(null);

				prchsDtl.setProdId(product.getProdId());
				prchsDtl.setProdAmt(product.getProdAmt());
				prchsDtl.setProdQty(product.getProdQty());
				prchsDtl.setTenantProdGrpCd("GRP-1");
				prchsDtl.setRePrchsPmtYn("N");
				prchsDtl.setUseExprDt("99991231235959");
				prchsDtl.setDwldExprDt("99991231235959");

				prchsDtl.setPrchsDtlId(prchsDtlCnt++);

				//
				prchsDtlList.add(prchsDtl);
			}
		}

		// 구매예약
		ReservePurchaseReqSC reservePurchaseReqSC = new ReservePurchaseReqSC();
		reservePurchaseReqSC.setPrchsDtlList(prchsDtlList);
		this.purchaseOrderSCI.reservePurchase(reservePurchaseReqSC);
	}

	/**
	 * 
	 * <pre>
	 * 유료구매 - 결제Page 준비작업.
	 * </pre>
	 * 
	 * @param purchaseInfo
	 *            구매요청 정보
	 */
	@Override
	public void setPaymentPageInfo(PurchaseOrder purchaseInfo) {
		purchaseInfo.setPaymentPageUrl(PP_PAYMENT_PAGE_URL);
		purchaseInfo.setPaymentPageParam("");

	}

	public static final String PP_PAYMENT_PAGE_URL = "http://localhost:8080/paymentPage";
	public static final String SAC_PUR_PAYMENT_NOTIFY_URL = "http://localhost:8010/sp_sac/purchase/order/notifyPayment/v1";
}
