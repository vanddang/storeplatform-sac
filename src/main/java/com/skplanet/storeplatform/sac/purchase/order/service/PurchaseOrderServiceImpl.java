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

import com.skplanet.storeplatform.purchase.client.common.vo.Payment;
import com.skplanet.storeplatform.purchase.client.common.vo.Prchs;
import com.skplanet.storeplatform.purchase.client.common.vo.PrchsDtl;
import com.skplanet.storeplatform.purchase.client.dummypurchase.sci.DummyPurchaseSCI;
import com.skplanet.storeplatform.purchase.client.order.sci.PurchaseOrderSCI;
import com.skplanet.storeplatform.purchase.client.order.vo.ConfirmPurchaseReqSC;
import com.skplanet.storeplatform.purchase.client.order.vo.CreatePaymentReqSC;
import com.skplanet.storeplatform.purchase.client.order.vo.ReservePurchaseReqSC;
import com.skplanet.storeplatform.purchase.client.order.vo.SearchReservedPurchaseReqSC;
import com.skplanet.storeplatform.purchase.client.order.vo.SearchReservedPurchaseResSC;
import com.skplanet.storeplatform.sac.client.purchase.vo.order.CreatePurchaseReqProduct;
import com.skplanet.storeplatform.sac.client.purchase.vo.order.NotifyPaymentReq;
import com.skplanet.storeplatform.sac.purchase.constant.PurchaseConstants;
import com.skplanet.storeplatform.sac.purchase.order.precheck.CheckerManager;
import com.skplanet.storeplatform.sac.purchase.order.precheck.PurchaseOrderChecker;
import com.skplanet.storeplatform.sac.purchase.order.vo.PaymentPageParam;
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
	 * @param purchaseOrderInfo
	 *            구매요청 정보
	 */
	@Override
	public void checkPurchase(PurchaseOrder purchaseOrderInfo) {
		List<PurchaseOrderChecker> checkerList = this.checkerManager.getCheckerList(null);

		Long startTime = System.currentTimeMillis();

		for (PurchaseOrderChecker checker : checkerList) {
			if (checker.isTarget(purchaseOrderInfo) == false) {
				continue;
			}

			if (checker.checkAndSetInfo(purchaseOrderInfo) == false) {
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
	 * @param purchaseOrderInfo
	 *            구매요청 정보
	 */
	@Override
	public void freePurchase(PurchaseOrder purchaseOrderInfo) {
		String prchsId = this.makePrchsId(purchaseOrderInfo.getPrchsReqPathCd(), purchaseOrderInfo.getProductList()
				.get(0).getProdId());
		purchaseOrderInfo.setPrchsId(prchsId);

		// 구매 이력 저장 데이터 세팅
		Prchs prchs = new Prchs();
		prchs.setTenantId(purchaseOrderInfo.getTenantId());
		prchs.setPrchsId(prchsId);
		prchs.setInsdUsermbrNo(purchaseOrderInfo.getUserKey());
		prchs.setInsdDeviceId(purchaseOrderInfo.getDeviceKey());
		prchs.setPrchsStatusCd("OR000301");
		prchs.setTotAmt(purchaseOrderInfo.getRealTotAmt());
		prchs.setPrchsReqPathCd(purchaseOrderInfo.getPrchsReqPathCd());
		prchs.setCurrencyCd(purchaseOrderInfo.getCurrencyCd());
		prchs.setClientIp(purchaseOrderInfo.getClientIp());
		prchs.setNetworkTypeCd(purchaseOrderInfo.getNetworkTypeCd());
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
		for (CreatePurchaseReqProduct product : purchaseOrderInfo.getCreatePurchaseReq().getProductList()) {
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
	 * @param purchaseOrderInfo
	 *            구매요청 정보
	 */
	@Override
	public void reservePurchase(PurchaseOrder purchaseOrderInfo) {
		String prchsId = this.makePrchsId(purchaseOrderInfo.getPrchsReqPathCd(), purchaseOrderInfo.getProductList()
				.get(0).getProdId());
		purchaseOrderInfo.setPrchsId(prchsId);

		// 구매상세 이력 저장 데이터 세팅
		String tenantId = purchaseOrderInfo.getTenantId();
		String systemId = purchaseOrderInfo.getSystemId();
		String userKey = purchaseOrderInfo.getUserKey();
		String deviceKey = purchaseOrderInfo.getDeviceKey();
		String prchsReqPathCd = purchaseOrderInfo.getPrchsReqPathCd();
		String clientIp = purchaseOrderInfo.getClientIp();
		Double realTotAmt = purchaseOrderInfo.getRealTotAmt();

		List<PrchsDtl> prchsDtlList = new ArrayList<PrchsDtl>();
		PrchsDtl prchsDtl = null;
		int i = 0, prchsDtlCnt = 1;
		for (CreatePurchaseReqProduct product : purchaseOrderInfo.getCreatePurchaseReq().getProductList()) {
			for (i = 0; i < product.getProdQty(); i++) {
				prchsDtl = new PrchsDtl();
				prchsDtl.setTenantId(tenantId);
				prchsDtl.setPrchsId(prchsId);
				prchsDtl.setUseTenantId(tenantId);
				prchsDtl.setUseInsdUsermbrNo(userKey);
				prchsDtl.setUseInsdDeviceId(deviceKey);
				prchsDtl.setStatusCd("OR000301");
				prchsDtl.setTotAmt(realTotAmt);
				prchsDtl.setPrchsReqPathCd(prchsReqPathCd);
				prchsDtl.setClientIp(clientIp);
				prchsDtl.setHidingYn("N");
				prchsDtl.setRegId(systemId);
				prchsDtl.setUpdId(systemId);

				prchsDtl.setCancelReqPathCd(null);
				prchsDtl.setCancelDt(null);
				prchsDtl.setPrchsCaseCd(null);

				prchsDtl.setProdId(product.getProdId());
				prchsDtl.setProdAmt(product.getProdAmt());
				prchsDtl.setProdQty(product.getProdQty());
				prchsDtl.setTenantProdGrpCd(product.getTenantProdGrpCd());
				prchsDtl.setRePrchsPmtYn("N");
				prchsDtl.setUseExprDt("99991231235959");
				prchsDtl.setDwldExprDt("99991231235959");
				prchsDtl.setResvCol01(product.getResvCol01());
				prchsDtl.setResvCol02(product.getResvCol02());
				prchsDtl.setResvCol03(product.getResvCol03());
				prchsDtl.setResvCol04(product.getResvCol04());
				prchsDtl.setResvCol05(product.getResvCol05());

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
	 * 유료구매 - 구매예약 정보 조회.
	 * </pre>
	 * 
	 * @param tenantId
	 *            테넌트 ID
	 * @param prchsId
	 *            구매 ID
	 * @param useUserKey
	 *            내부 회원 NO
	 */
	@Override
	public PrchsDtl searchReservedPurchaseDetail(String tenantId, String prchsId, String useUserKey) {
		SearchReservedPurchaseReqSC reqSearch = new SearchReservedPurchaseReqSC();
		reqSearch.setTenantId(tenantId);
		reqSearch.setPrchsId(prchsId);
		reqSearch.setUseUserKey(useUserKey);

		SearchReservedPurchaseResSC res = this.purchaseOrderSCI.searchReservedPurchaseDetail(reqSearch);
		return res.getPrchsDtl();
	}

	/**
	 * 
	 * <pre>
	 * 유료구매 - 구매확정: 구매상세 내역 상태변경 & 구매 내역 저장 & (선물 경우)발송 상세 내역 저장.
	 * </pre>
	 * 
	 * @param prchs
	 *            구매 정보
	 */
	@Override
	public void confirmPurchase(Prchs prchs) {
		ConfirmPurchaseReqSC reqConfirm = new ConfirmPurchaseReqSC();
		reqConfirm.setTenantId(prchs.getTenantId());
		reqConfirm.setSystemId(null);
		reqConfirm.setUseUserKey(prchs.getInsdUsermbrNo());
		reqConfirm.setPrchsId(prchs.getPrchsId());
		reqConfirm.setCurrencyCd(prchs.getCurrencyCd());
		reqConfirm.setNetworkTypeCd(prchs.getNetworkTypeCd());

		this.purchaseOrderSCI.confirmPurchase(reqConfirm);

	}

	/**
	 * 
	 * <pre>
	 * 결제 내역 생성.
	 * </pre>
	 * 
	 * @param prchs
	 *            구매정보
	 * @param notifyParam
	 *            결제정보
	 */
	@Override
	public void createPayment(Prchs prchs, NotifyPaymentReq notifyParam) {
		String tenantId = prchs.getTenantId();
		String prchsId = prchs.getPrchsId();
		String payUserKey = prchs.getInsdUsermbrNo();
		String payDeviceKey = prchs.getInsdDeviceId();
		String prchsDt = prchs.getPrchsDt();
		Double totAmt = prchs.getTotAmt();

		String tid = notifyParam.getTid();

		List<Payment> paymentList = new ArrayList<Payment>();
		Payment payment = null;

		String[] arPaymethod = notifyParam.getPaymethodInfo().split(";"); // 수단코드:결제금액;...
		String[] arPayInfo = null;

		int dtlIdCnt = 1;
		for (String pay : arPaymethod) {
			arPayInfo = pay.split(":");

			payment = new Payment();
			payment.setTenantId(tenantId);
			payment.setPrchsId(prchsId);
			payment.setPaymentDtlId(dtlIdCnt++);
			payment.setInsdUsermbrNo(payUserKey);
			payment.setInsdDeviceId(payDeviceKey);
			payment.setPrchsDt(prchsDt);
			payment.setTotAmt(totAmt);

			payment.setPaymentMtdCd(arPayInfo[0]);
			payment.setPaymentAmt(Double.valueOf(arPayInfo[1]));
			payment.setPaymentDt(prchsDt);
			payment.setPaymentStatusCd("OR000301");

			payment.setTid(tid);

			payment.setRegId("test");
			payment.setUpdId("test");

			paymentList.add(payment);
		}

		// 결제 내역 생성 요청

		CreatePaymentReqSC reqPayment = new CreatePaymentReqSC();
		reqPayment.setPaymentList(paymentList);

		this.purchaseOrderSCI.createPayment(reqPayment);
	}

	/**
	 * 
	 * <pre>
	 * 유료구매 - 결제Page 준비작업.
	 * </pre>
	 * 
	 * @param purchaseOrderInfo
	 *            구매요청 정보
	 */
	@Override
	public void setPaymentPageInfo(PurchaseOrder purchaseOrderInfo) {
		purchaseOrderInfo.setPaymentPageUrl(PP_PAYMENT_PAGE_URL);
		purchaseOrderInfo.setPaymentPageParam(this.makeEncryptedPaymentParameter(purchaseOrderInfo));
	}

	/**
	 * 
	 * <pre>
	 * 결제Page 요청시 전달할 (암호화된) 파라미터 생성.
	 * </pre>
	 * 
	 * @param purchaseOrderInfo
	 * @return
	 */
	private String makeEncryptedPaymentParameter(PurchaseOrder purchaseOrderInfo) {

		String useUserKey = null;
		if (PurchaseConstants.PRCHS_CASE_GIFT_CD.equals(purchaseOrderInfo.getPrchsCaseCd())) {
			useUserKey = purchaseOrderInfo.getRecvUserKey();
		} else {
			useUserKey = purchaseOrderInfo.getUserKey();
		}

		PaymentPageParam param = new PaymentPageParam();
		param.setMid(purchaseOrderInfo.getMid());
		param.setAuthKey(purchaseOrderInfo.getAuthKey());
		param.setOrderId(purchaseOrderInfo.getPrchsId());
		param.setMctTrDate("");
		param.setAmtPurchase(String.valueOf(purchaseOrderInfo.getRealTotAmt()));
		param.setPid(purchaseOrderInfo.getProductList().get(0).getProdId());
		param.setpName(purchaseOrderInfo.getProductList().get(0).getProdId());
		param.setpDescription(purchaseOrderInfo.getProductList().get(0).getProdId());
		param.setpType(purchaseOrderInfo.getProductList().get(0).getProdId());
		param.setAid("");
		param.setReturnFormat(PaymentPageParam.PP_RETURN_FORMAT_JSON);
		param.setReturnPath(purchaseOrderInfo.getResultUrl());
		param.setResultPath(SAC_PUR_PAYMENT_NOTIFY_URL);
		param.setMdn(purchaseOrderInfo.getPurchaseMember().getDeviceId());
		param.setNmDevice(purchaseOrderInfo.getDeviceModelCd());
		param.setImei("");
		param.setUacd("");
		param.setTypeNetwork(purchaseOrderInfo.getNetworkTypeCd().substring(
				purchaseOrderInfo.getNetworkTypeCd().length() - 1));
		param.setCarrier("1");
		param.setNoSim("");
		param.setFlgSim("");

		// 가맹점 필요 파라미터
		StringBuffer sbSpare = new StringBuffer(1024);
		sbSpare.append("tenantId=").append(purchaseOrderInfo.getTenantId()).append("&systemId=")
				.append(purchaseOrderInfo.getSystemId()).append("&useUserKey=").append(useUserKey)
				.append("&networkTypeCd=").append(purchaseOrderInfo.getNetworkTypeCd()).append("&currencyCd=")
				.append(purchaseOrderInfo.getCurrencyCd());

		param.setMctSpareParam(sbSpare.toString());

		// 암호화
		String eDataStr = param.makeEncDataFormat();

		// Token
		// String tokenStr = param.makeTokenFormat();

		return eDataStr;
	}

	public static final String PP_PAYMENT_PAGE_URL = "http://localhost:8080/payplanet/paymentPage";
	public static final String SAC_PUR_PAYMENT_NOTIFY_URL = "http://localhost:8010/sp_sac/purchase/order/notifyPayment/v1";
}
