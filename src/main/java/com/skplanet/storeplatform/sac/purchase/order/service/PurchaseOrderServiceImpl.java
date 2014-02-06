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

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.skplanet.storeplatform.framework.core.exception.StorePlatformException;
import com.skplanet.storeplatform.purchase.client.common.vo.AutoPrchs;
import com.skplanet.storeplatform.purchase.client.common.vo.Payment;
import com.skplanet.storeplatform.purchase.client.common.vo.Prchs;
import com.skplanet.storeplatform.purchase.client.common.vo.PrchsDtl;
import com.skplanet.storeplatform.purchase.client.dummypurchase.sci.DummyPurchaseSCI;
import com.skplanet.storeplatform.purchase.client.order.sci.PurchaseOrderSCI;
import com.skplanet.storeplatform.purchase.client.order.vo.ConfirmPurchaseScReq;
import com.skplanet.storeplatform.purchase.client.order.vo.ConfirmPurchaseScRes;
import com.skplanet.storeplatform.purchase.client.order.vo.CreateAutoPurchaseScReq;
import com.skplanet.storeplatform.purchase.client.order.vo.CreateCompletedPurchaseScReq;
import com.skplanet.storeplatform.purchase.client.order.vo.CreateCompletedPurchaseScRes;
import com.skplanet.storeplatform.purchase.client.order.vo.CreatePaymentScReq;
import com.skplanet.storeplatform.purchase.client.order.vo.CreatePaymentScRes;
import com.skplanet.storeplatform.purchase.client.order.vo.ReservePurchaseScReq;
import com.skplanet.storeplatform.purchase.client.order.vo.ReservePurchaseScRes;
import com.skplanet.storeplatform.purchase.client.order.vo.SearchReservedPurchaseScReq;
import com.skplanet.storeplatform.purchase.client.order.vo.SearchReservedPurchaseScRes;
import com.skplanet.storeplatform.sac.client.purchase.vo.order.NotifyPaymentSacReq;
import com.skplanet.storeplatform.sac.other.common.CryptUtils;
import com.skplanet.storeplatform.sac.purchase.constant.PurchaseConstants;
import com.skplanet.storeplatform.sac.purchase.order.dummy.vo.DummyProduct;
import com.skplanet.storeplatform.sac.purchase.order.vo.PaymentPageParam;
import com.skplanet.storeplatform.sac.purchase.order.vo.PurchaseOrder;

/**
 * 
 * 구매 서비스 구현
 * 
 * Updated on : 2014. 1. 3. Updated by : 이승택, nTels.
 */
@Service
public class PurchaseOrderServiceImpl implements PurchaseOrderService {
	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private DummyPurchaseSCI dummyPurchaseSCI;

	@Autowired
	private PurchaseOrderSCI purchaseOrderSCI;

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
		// TAKTODO:: 구매ID 생성
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
	public void createFreePurchase(PurchaseOrder purchaseOrderInfo) {
		this.logger.debug("PRCHS,ORDER,FREE,START,{}", purchaseOrderInfo);

		// 구매ID 생성
		String prchsId = this.makePrchsId(purchaseOrderInfo.getPrchsReqPathCd(), purchaseOrderInfo.getProductList()
				.get(0).getProdId());
		purchaseOrderInfo.setPrchsId(prchsId);

		// 구매상세 이력 저장 데이터 세팅
		List<PrchsDtl> prchsDtlList = new ArrayList<PrchsDtl>();
		PrchsDtl prchsDtl = null;
		int prchsDtlCnt = 1, i = 0;
		for (DummyProduct product : purchaseOrderInfo.getProductList()) {
			for (i = 0; i < product.getProdQty(); i++) {
				prchsDtl = new PrchsDtl();

				prchsDtl.setPrchsDtlId(prchsDtlCnt++);

				prchsDtl.setTenantId(purchaseOrderInfo.getTenantId());
				prchsDtl.setPrchsId(prchsId);
				prchsDtl.setUseTenantId(purchaseOrderInfo.getTenantId());
				prchsDtl.setUseInsdUsermbrNo(purchaseOrderInfo.getUserKey());
				prchsDtl.setUseInsdDeviceId(purchaseOrderInfo.getDeviceKey());
				prchsDtl.setTotAmt(purchaseOrderInfo.getRealTotAmt());
				prchsDtl.setPrchsReqPathCd(purchaseOrderInfo.getPrchsReqPathCd());
				prchsDtl.setClientIp(purchaseOrderInfo.getClientIp());
				prchsDtl.setHidingYn("N");
				prchsDtl.setRegId(purchaseOrderInfo.getSystemId());
				prchsDtl.setUpdId(purchaseOrderInfo.getSystemId());
				prchsDtl.setPrchsCaseCd(purchaseOrderInfo.getPrchsCaseCd());
				prchsDtl.setPrchsProdType(PurchaseConstants.PRCHS_PROD_TYPE_OWN); // TAKTODO

				prchsDtl.setProdId(product.getProdId());
				prchsDtl.setProdAmt(product.getProdAmt());
				prchsDtl.setProdQty(product.getProdQty());
				prchsDtl.setTenantProdGrpCd(product.getTenantProdGrpCd());
				prchsDtl.setRePrchsPmtYn(product.getbDupleProd() ? "Y" : "N");
				prchsDtl.setResvCol01(product.getResvCol01());
				prchsDtl.setResvCol02(product.getResvCol02());
				prchsDtl.setResvCol03(product.getResvCol03());
				prchsDtl.setResvCol04(product.getResvCol04());
				prchsDtl.setResvCol05(product.getResvCol05());
				prchsDtl.setUseExprDt("20991231235959");
				prchsDtl.setDwldExprDt("20991231235959");

				prchsDtlList.add(prchsDtl);
			}
		}

		// 구매완료 생성 요청
		CreateCompletedPurchaseScReq req = new CreateCompletedPurchaseScReq();
		req.setNetworkTypeCd(purchaseOrderInfo.getNetworkTypeCd());
		req.setCurrencyCd(purchaseOrderInfo.getCurrencyCd());
		req.setPrchsDtlList(prchsDtlList);

		CreateCompletedPurchaseScRes res = this.purchaseOrderSCI.createCompletedPurchase(req);
		this.logger.debug("PRCHS,ORDER,FREE,END,{}", res.getCount());
		if (res.getCount() < 1 || res.getCount() != prchsDtlList.size()) {
			throw new StorePlatformException("SAC_PUR_0001", "구매요청 처리 중 에러 발생");
		}
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
	public void createReservedPurchase(PurchaseOrder purchaseOrderInfo) {
		this.logger.debug("PRCHS,ORDER,RESERVE,START,{}", purchaseOrderInfo);

		// 구매ID 생성
		String prchsId = this.makePrchsId(purchaseOrderInfo.getPrchsReqPathCd(), purchaseOrderInfo.getProductList()
				.get(0).getProdId());
		purchaseOrderInfo.setPrchsId(prchsId);

		// 구매상세 이력 저장 데이터 세팅
		List<PrchsDtl> prchsDtlList = new ArrayList<PrchsDtl>();
		PrchsDtl prchsDtl = null;
		int prchsDtlCnt = 1, i = 0;
		for (DummyProduct product : purchaseOrderInfo.getProductList()) {
			for (i = 0; i < product.getProdQty(); i++) {
				prchsDtl = new PrchsDtl();

				prchsDtl.setPrchsDtlId(prchsDtlCnt++);

				prchsDtl.setTenantId(purchaseOrderInfo.getTenantId());
				prchsDtl.setPrchsId(prchsId);
				if (PurchaseConstants.PRCHS_CASE_GIFT_CD.equals(purchaseOrderInfo.getPrchsCaseCd())) {
					prchsDtl.setUseTenantId(purchaseOrderInfo.getRecvTenantId());
					prchsDtl.setUseInsdUsermbrNo(purchaseOrderInfo.getRecvUserKey());
					prchsDtl.setUseInsdDeviceId(purchaseOrderInfo.getRecvDeviceKey());
					prchsDtl.setSendInsdUsermbrNo(purchaseOrderInfo.getUserKey());
					prchsDtl.setSendInsdDeviceId(purchaseOrderInfo.getDeviceKey());
				} else {
					prchsDtl.setUseTenantId(purchaseOrderInfo.getTenantId());
					prchsDtl.setUseInsdUsermbrNo(purchaseOrderInfo.getUserKey());
					prchsDtl.setUseInsdDeviceId(purchaseOrderInfo.getDeviceKey());
				}
				prchsDtl.setTotAmt(purchaseOrderInfo.getRealTotAmt());
				prchsDtl.setPrchsReqPathCd(purchaseOrderInfo.getPrchsReqPathCd());
				prchsDtl.setClientIp(purchaseOrderInfo.getClientIp());
				prchsDtl.setHidingYn("N");
				prchsDtl.setRegId(purchaseOrderInfo.getSystemId());
				prchsDtl.setUpdId(purchaseOrderInfo.getSystemId());
				prchsDtl.setPrchsCaseCd(purchaseOrderInfo.getPrchsCaseCd());
				prchsDtl.setPrchsProdType(PurchaseConstants.PRCHS_PROD_TYPE_OWN); // TAKTODO

				prchsDtl.setProdId(product.getProdId());
				prchsDtl.setProdAmt(product.getProdAmt());
				prchsDtl.setProdQty(product.getProdQty());
				prchsDtl.setTenantProdGrpCd(product.getTenantProdGrpCd());
				prchsDtl.setRePrchsPmtYn(product.getbDupleProd() ? "Y" : "N");
				prchsDtl.setResvCol01(product.getResvCol01());
				prchsDtl.setResvCol02(product.getResvCol02());
				prchsDtl.setResvCol03(product.getResvCol03());
				prchsDtl.setResvCol04(product.getResvCol04());
				prchsDtl.setResvCol05(product.getResvCol05());
				prchsDtl.setUseExprDt("20991231235959");
				prchsDtl.setDwldExprDt("20991231235959");

				prchsDtlList.add(prchsDtl);
			}
		}

		// 구매예약
		ReservePurchaseScReq reservePurchaseReqSC = new ReservePurchaseScReq();
		reservePurchaseReqSC.setPrchsDtlList(prchsDtlList);

		ReservePurchaseScRes res = this.purchaseOrderSCI.reservePurchase(reservePurchaseReqSC);
		this.logger.debug("PRCHS,ORDER,RESERVE,END,{}", res.getCount());
		if (res.getCount() < 1 || res.getCount() != prchsDtlList.size()) {
			throw new StorePlatformException("SAC_PUR_0001", "구매요청 처리 중 에러 발생");
		}

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
		SearchReservedPurchaseScReq reqSearch = new SearchReservedPurchaseScReq();
		reqSearch.setTenantId(tenantId);
		reqSearch.setPrchsId(prchsId);
		reqSearch.setUseUserKey(useUserKey);

		SearchReservedPurchaseScRes res = this.purchaseOrderSCI.searchReservedPurchaseDetail(reqSearch);
		return res.getPrchsDtl();
	}

	/**
	 * 
	 * <pre>
	 * 유료구매 - 구매확정: 구매상세 내역 상태변경 & 구매 내역 저장 & (선물 경우)발송 상세 내역 저장, 결제내역 저장.
	 * </pre>
	 * 
	 * @param prchsDtl
	 *            구매상세 정보
	 * @param notifyPaymentReq
	 *            결제결과 정보
	 * @param currencyCd
	 *            통화코드
	 * @param networkTypeCd
	 *            네트워크 타입 코드
	 */
	@Override
	public void updateConfirmPurchase(PrchsDtl prchsDtl, NotifyPaymentSacReq notifyPaymentReq, String currencyCd,
			String networkTypeCd) {
		this.logger.debug("PRCHS,ORDER,CONFIRM,START,{}", prchsDtl);

		// 구매확정
		ConfirmPurchaseScReq reqConfirm = new ConfirmPurchaseScReq();
		reqConfirm.setTenantId(prchsDtl.getTenantId());
		reqConfirm.setSystemId(prchsDtl.getResvCol05()); // TAKTODO
		reqConfirm.setUseUserKey(prchsDtl.getUseInsdUsermbrNo());
		reqConfirm.setPrchsId(prchsDtl.getPrchsId());
		reqConfirm.setCurrencyCd(currencyCd);
		reqConfirm.setNetworkTypeCd(networkTypeCd);

		ConfirmPurchaseScRes res = this.purchaseOrderSCI.confirmPurchase(reqConfirm);
		this.logger.debug("PRCHS,ORDER,CONFIRM,END,{}", res.getCount());
		if (res.getCount() < 1) {
			throw new StorePlatformException("SAC_PUR_0001", "구매확정 처리 중 에러 발생");
		}

		// 결제 내역 저장
		Prchs prchs = new Prchs();
		prchs.setTenantId(prchsDtl.getTenantId());
		prchs.setResvCol05(prchsDtl.getResvCol05()); // TAKTODO
		prchs.setPrchsId(prchsDtl.getPrchsId());
		prchs.setPrchsDt(prchsDtl.getPrchsDt());
		prchs.setTotAmt(prchsDtl.getTotAmt());

		if (PurchaseConstants.PRCHS_CASE_GIFT_CD.equals(prchsDtl.getPrchsCaseCd())) { // 선물경우, 발신자 기준
			prchs.setInsdUsermbrNo(prchsDtl.getSendInsdUsermbrNo());
			prchs.setInsdDeviceId(prchsDtl.getSendInsdDeviceId());
		} else {
			prchs.setInsdUsermbrNo(prchsDtl.getUseInsdUsermbrNo());
			prchs.setInsdDeviceId(prchsDtl.getUseInsdDeviceId());
		}
		this.createPayment(prchs, notifyPaymentReq);

		// 자동결제 처리
		if (StringUtils.isNotBlank(notifyPaymentReq.getGwBillkey())) {
			List<AutoPrchs> autoPrchsList = new ArrayList<AutoPrchs>();

			AutoPrchs autoPrchs = new AutoPrchs();
			autoPrchs.setTenantId(prchsDtl.getTenantId());
			autoPrchs.setFstPrchsId(prchsDtl.getPrchsId());
			autoPrchs.setFstPrchsDtlId(1); // TAKTODO
			autoPrchs.setInsdUsermbrNo(prchs.getInsdUsermbrNo());
			autoPrchs.setInsdDeviceId(prchs.getInsdDeviceId());
			autoPrchs.setProdId(prchsDtl.getProdId());
			autoPrchs.setPaymentStartDt(prchsDtl.getPrchsDt());
			autoPrchs.setPaymentEndDt("99991231235959");
			autoPrchs.setAfterPaymentDt(prchsDtl.getUseExprDt().substring(0, 8) + "000000");
			autoPrchs.setReqPathCd(prchsDtl.getPrchsReqPathCd());
			autoPrchs.setClientIp(prchsDtl.getClientIp());
			autoPrchs.setPrchsTme(0);
			autoPrchs.setLastPrchsId(prchsDtl.getPrchsId());
			autoPrchs.setLastPrchsDtlId(1);
			autoPrchs.setRegId(prchsDtl.getResvCol05());
			autoPrchs.setUpdId(prchsDtl.getResvCol05());
			autoPrchs.setResvCol01(prchsDtl.getResvCol01());
			autoPrchs.setResvCol02(prchsDtl.getResvCol02());
			autoPrchs.setResvCol03(prchsDtl.getResvCol03());
			autoPrchs.setResvCol04(prchsDtl.getResvCol04());
			autoPrchs.setResvCol05(prchsDtl.getResvCol05());
			autoPrchsList.add(autoPrchs);

			CreateAutoPurchaseScReq autoReq = new CreateAutoPurchaseScReq();
			autoReq.setAutoPrchsList(autoPrchsList);

			this.purchaseOrderSCI.createNewAutoPurchase(autoReq);
		}
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
	public void createPayment(Prchs prchs, NotifyPaymentSacReq notifyParam) {
		this.logger.debug("PRCHS,ORDER,NOTIPAY,START,{}", notifyParam);

		String tenantId = prchs.getTenantId();
		String systemId = prchs.getResvCol05(); // TAKTODO
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

			payment.setRegId(systemId);
			payment.setUpdId(systemId);

			paymentList.add(payment);
		}

		// 결제 내역 생성 요청
		CreatePaymentScReq reqPayment = new CreatePaymentScReq();
		reqPayment.setPaymentList(paymentList);

		CreatePaymentScRes res = this.purchaseOrderSCI.createPayment(reqPayment);
		this.logger.debug("PRCHS,ORDER,NOTIPAY,END,{}", notifyParam);
		if (res.getCount() < 1 || res.getCount() != paymentList.size()) {
			throw new StorePlatformException("SAC_PUR_0001", "결제내역 저장 처리 중 에러 발생");
		}
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
		this.logger.debug("PRCHS,ORDER,SET_PAYPAGE,START,{}", purchaseOrderInfo);

		purchaseOrderInfo.setPaymentPageUrl(DUMMY_PP_PAYMENT_PAGE_URL);
		purchaseOrderInfo.setPaymentPageParam(this.makeEncryptedPaymentParameter(purchaseOrderInfo));

		this.logger.debug("PRCHS,ORDER,SET_PAYPAGE,END,{}", purchaseOrderInfo);
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
		param.setResultPath(DUMMY_SAC_PUR_PAYMENT_NOTIFY_URL);
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
		sbSpare.append("tenantId:").append(purchaseOrderInfo.getTenantId()).append("&systemId:")
				.append(purchaseOrderInfo.getSystemId()).append("&useUserKey:").append(useUserKey)
				.append("&networkTypeCd:").append(purchaseOrderInfo.getNetworkTypeCd()).append("&currencyCd:")
				.append(purchaseOrderInfo.getCurrencyCd());

		param.setMctSpareParam(sbSpare.toString());

		// 암호화
		String eData = param.makeEncDataFormat();
		eData = CryptUtils.encrypt(CryptUtils.AES128_CTR, "5", eData);
		eData = Base64.encodeBase64String(eData.getBytes());
		param.seteData(eData);

		// Token
		String token = param.makeTokenFormat();
		try {
			token = Base64.encodeBase64String(MessageDigest.getInstance("MD5").digest(token.getBytes()));
		} catch (NoSuchAlgorithmException e) {
			// TODO::
			;
		}
		param.setToken(token);

		this.logger.debug("TAKTEST,{},{}", eData, token);

		return eData;
	}

	public static final String DUMMY_PP_PAYMENT_PAGE_URL = "http://121.165.99.39:8080/payplanet/paymentPage";
	public static final String DUMMY_SAC_PUR_PAYMENT_NOTIFY_URL = "http://121.165.99.39:8010/sp_sac/purchase/order/notifyPayment/v1";
}
