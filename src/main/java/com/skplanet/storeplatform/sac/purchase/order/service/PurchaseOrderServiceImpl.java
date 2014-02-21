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
import com.skplanet.storeplatform.purchase.client.order.sci.PurchaseOrderSCI;
import com.skplanet.storeplatform.purchase.client.order.sci.PurchaseOrderSearchSCI;
import com.skplanet.storeplatform.purchase.client.order.vo.ConfirmPurchaseScReq;
import com.skplanet.storeplatform.purchase.client.order.vo.ConfirmPurchaseScRes;
import com.skplanet.storeplatform.purchase.client.order.vo.CreateAutoPurchaseScReq;
import com.skplanet.storeplatform.purchase.client.order.vo.CreatePaymentScReq;
import com.skplanet.storeplatform.purchase.client.order.vo.CreatePaymentScRes;
import com.skplanet.storeplatform.purchase.client.order.vo.CreatePurchaseSc;
import com.skplanet.storeplatform.purchase.client.order.vo.CreatePurchaseScReq;
import com.skplanet.storeplatform.purchase.client.order.vo.CreatePurchaseScRes;
import com.skplanet.storeplatform.purchase.client.order.vo.SearchReservedPurchaseScReq;
import com.skplanet.storeplatform.purchase.client.order.vo.SearchReservedPurchaseScRes;
import com.skplanet.storeplatform.sac.client.purchase.vo.order.NotifyPaymentSacReq;
import com.skplanet.storeplatform.sac.other.common.CryptUtils;
import com.skplanet.storeplatform.sac.purchase.constant.PurchaseConstants;
import com.skplanet.storeplatform.sac.purchase.order.dummy.vo.DummyProduct;
import com.skplanet.storeplatform.sac.purchase.order.vo.PaymentPageParam;
import com.skplanet.storeplatform.sac.purchase.order.vo.PurchaseOrderInfo;

/**
 * 
 * 구매 서비스 구현
 * 
 * Updated on : 2014. 1. 3. Updated by : 이승택, nTels.
 */
@Service
public class PurchaseOrderServiceImpl implements PurchaseOrderService {
	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	private static final String CREATE_PURCHASE_TYPE_COMPLETED = "Completed";
	private static final String CREATE_PURCHASE_TYPE_RESERVED = "Reserved";

	public static final String DUMMY_PP_PAYMENT_PAGE_URL = "http://121.165.99.39:8080/payplanet/paymentPage";
	public static final String DUMMY_SAC_PUR_PAYMENT_NOTIFY_URL = "http://121.165.99.39:8010/sp_sac/purchase/order/notifyPayment/v1";

	@Autowired
	private PurchaseOrderSCI purchaseOrderSCI;
	@Autowired
	private PurchaseOrderSearchSCI purchaseOrderSearchSCI;

	/*
	 * <pre> 새로운 구매ID 생성. </pre>
	 * 
	 * @param tempPrchsReqPathCd (임시파라미터) 구매요청경로코드
	 * 
	 * @param tempProdId (임시파라미터) 상품ID
	 * 
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
	public void createFreePurchase(PurchaseOrderInfo purchaseOrderInfo) {
		this.createPurchaseByType(purchaseOrderInfo, CREATE_PURCHASE_TYPE_COMPLETED);
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
	public void createReservedPurchase(PurchaseOrderInfo purchaseOrderInfo) {
		this.createPurchaseByType(purchaseOrderInfo, CREATE_PURCHASE_TYPE_RESERVED);
	}

	/*
	 * <pre> 구매이력 생성. </pre>
	 * 
	 * @param purchaseOrderInfo 구매요청 정보 VO
	 * 
	 * @param type 구매이력 생성 타입: Completed-구매완료, Reserved-구매예약
	 */
	private void createPurchaseByType(PurchaseOrderInfo purchaseOrderInfo, String type) {
		this.logger.debug("PRCHS,ORDER,CREATE,START,{},{}", type, purchaseOrderInfo);

		// 구매ID 생성
		if (StringUtils.isBlank(purchaseOrderInfo.getPrchsId())) {
			String prchsId = this.makePrchsId(purchaseOrderInfo.getPrchsReqPathCd(), purchaseOrderInfo.getProductList()
					.get(0).getProdId());
			purchaseOrderInfo.setPrchsId(prchsId);
		}

		// 구매시간 세팅
		purchaseOrderInfo
				.setPrchsDt(DateFormatUtils.format(Calendar.getInstance().getTimeInMillis(), "yyyyMMddHHmmss"));

		// 구매생성 요청 데이터 생성
		List<CreatePurchaseSc> createPurchaseList = this.makeCreatePurchaseScListForRequest(purchaseOrderInfo);

		// 구매생성 요청
		CreatePurchaseScRes res = null;
		if (StringUtils.equals(type, CREATE_PURCHASE_TYPE_COMPLETED)) { // 구매완료
			res = this.purchaseOrderSCI.createCompletedPurchase(new CreatePurchaseScReq(createPurchaseList));
		} else if (StringUtils.equals(type, CREATE_PURCHASE_TYPE_RESERVED)) { // 구매예약
			res = this.purchaseOrderSCI.createReservedPurchase(new CreatePurchaseScReq(createPurchaseList));
		} // else 는 진입 가능성 zero

		this.logger.debug("PRCHS,ORDER,CREATE,END,{},{}", type, res.getCount());
		if (res.getCount() < 1 || res.getCount() != createPurchaseList.size()) {
			throw new StorePlatformException("SAC_PUR_0001", "구매요청 처리 중 에러 발생");
		}
	}

	/*
	 * <pre> 구매생성을 위한 데이터 목록 생성. </pre>
	 * 
	 * @param purchaseOrderInfo 구매요청 VO
	 * 
	 * @return 구매생성을 위한 데이터 목록
	 */
	private List<CreatePurchaseSc> makeCreatePurchaseScListForRequest(PurchaseOrderInfo purchaseOrderInfo) {

		// 구매생성 요청 데이터 세팅
		List<CreatePurchaseSc> createPurchaseList = new ArrayList<CreatePurchaseSc>();
		CreatePurchaseSc createPurchase = null;

		int prchsDtlCnt = 1, i = 0;
		for (DummyProduct product : purchaseOrderInfo.getProductList()) {
			for (i = 0; i < product.getProdQty(); i++) {
				createPurchase = new CreatePurchaseSc();

				createPurchase.setPrchsDtlId(prchsDtlCnt++);

				createPurchase.setTenantId(purchaseOrderInfo.getTenantId());
				createPurchase.setPrchsId(purchaseOrderInfo.getPrchsId());
				createPurchase.setPrchsDt(purchaseOrderInfo.getPrchsDt());
				if (StringUtils.equals(purchaseOrderInfo.getPrchsCaseCd(), PurchaseConstants.PRCHS_CASE_GIFT_CD)) {
					createPurchase.setUseTenantId(purchaseOrderInfo.getRecvTenantId());
					createPurchase.setUseInsdUsermbrNo(purchaseOrderInfo.getRecvUserKey());
					createPurchase.setUseInsdDeviceId(purchaseOrderInfo.getRecvDeviceKey());
					createPurchase.setSendInsdUsermbrNo(purchaseOrderInfo.getUserKey());
					createPurchase.setSendInsdDeviceId(purchaseOrderInfo.getDeviceKey());
				} else {
					createPurchase.setUseTenantId(purchaseOrderInfo.getTenantId());
					createPurchase.setUseInsdUsermbrNo(purchaseOrderInfo.getUserKey());
					createPurchase.setUseInsdDeviceId(purchaseOrderInfo.getDeviceKey());
				}
				createPurchase.setTotAmt(purchaseOrderInfo.getRealTotAmt());
				createPurchase.setPrchsReqPathCd(purchaseOrderInfo.getPrchsReqPathCd());
				createPurchase.setClientIp(purchaseOrderInfo.getClientIp());
				createPurchase.setHidingYn(PurchaseConstants.USE_N);
				createPurchase.setRegId(purchaseOrderInfo.getSystemId());
				createPurchase.setUpdId(purchaseOrderInfo.getSystemId());
				createPurchase.setPrchsCaseCd(purchaseOrderInfo.getPrchsCaseCd());
				createPurchase.setTenantProdGrpCd(purchaseOrderInfo.getTenantProdGrpCd());
				createPurchase.setCurrencyCd(purchaseOrderInfo.getCurrencyCd()); // PRCHS
				createPurchase.setNetworkTypeCd(purchaseOrderInfo.getNetworkTypeCd()); // PRCHS

				if (product.isbFlat()) { // TAKTODO:: 권한상품 판단
					createPurchase.setPrchsProdType(PurchaseConstants.PRCHS_PROD_TYPE_FIX);
				} else {
					createPurchase.setPrchsProdType(PurchaseConstants.PRCHS_PROD_TYPE_OWN);
				}
				createPurchase.setProdId(product.getProdId());
				createPurchase.setProdAmt(product.getProdAmt());
				createPurchase.setProdQty(product.getProdQty());
				createPurchase.setRePrchsPmtYn(product.getbDupleProd() ? "Y" : "N");
				createPurchase.setResvCol01(product.getResvCol01());
				createPurchase.setResvCol02(product.getResvCol02());
				createPurchase.setResvCol03(product.getResvCol03());
				createPurchase.setResvCol04(product.getResvCol04());
				createPurchase.setResvCol05(product.getResvCol05());
				createPurchase.setUsePeriodUnitCd(product.getUsePeriodUnitCd());
				createPurchase.setUsePeriod(product.getUsePeriod());

				createPurchaseList.add(createPurchase);
			}
		}

		return createPurchaseList;
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
	public CreatePurchaseSc searchReservedPurchaseDetail(String tenantId, String prchsId, String useUserKey) {
		// TAKTODO:: 구매예약 정보 조회 처리
		SearchReservedPurchaseScReq reqSearch = new SearchReservedPurchaseScReq();
		reqSearch.setTenantId(tenantId);
		reqSearch.setPrchsId(prchsId);
		reqSearch.setUseUserKey(useUserKey);

		SearchReservedPurchaseScRes res = this.purchaseOrderSearchSCI.searchReservedPurchaseDetail(reqSearch);
		return res.getCreatePurchaseSc();
	}

	/**
	 * 
	 * <pre>
	 * 유료구매 - 구매확정: 구매상세 내역 상태변경 & 구매 내역 저장 & (선물 경우)발송 상세 내역 저장, 결제내역 저장.
	 * </pre>
	 * 
	 * @param createPurchaseSc
	 *            구매상세 정보
	 * @param notifyPaymentReq
	 *            결제결과 정보
	 */
	@Override
	public void updateConfirmPurchase(CreatePurchaseSc createPurchaseSc, NotifyPaymentSacReq notifyPaymentReq) {
		this.logger.debug("PRCHS,ORDER,CONFIRM,START,{}", createPurchaseSc);

		// 구매확정
		ConfirmPurchaseScReq reqConfirm = new ConfirmPurchaseScReq();
		reqConfirm.setTenantId(createPurchaseSc.getTenantId());
		reqConfirm.setSystemId(createPurchaseSc.getSystemId());
		reqConfirm.setUseUserKey(createPurchaseSc.getUseInsdUsermbrNo());
		reqConfirm.setPrchsId(createPurchaseSc.getPrchsId());
		reqConfirm.setCurrencyCd(createPurchaseSc.getCurrencyCd());
		reqConfirm.setNetworkTypeCd(createPurchaseSc.getNetworkTypeCd());

		ConfirmPurchaseScRes resConfirm = this.purchaseOrderSCI.confirmPurchase(reqConfirm);
		this.logger.debug("PRCHS,ORDER,CONFIRM,END,{}", resConfirm.getCount());
		if (resConfirm.getCount() < 1) {
			throw new StorePlatformException("SAC_PUR_0001", "구매확정 처리 중 에러 발생");
		}

		createPurchaseSc.setStatusCd(PurchaseConstants.PRCHS_STATUS_COMPT); // 구매확정

		// 결제 내역 저장
		if (StringUtils.equals(createPurchaseSc.getPrchsCaseCd(), PurchaseConstants.PRCHS_CASE_GIFT_CD)) { // 선물경우,
																										   // TB_구매 는
																										   // 발신자
			createPurchaseSc.setInsdUsermbrNo(createPurchaseSc.getSendInsdUsermbrNo());
			createPurchaseSc.setInsdDeviceId(createPurchaseSc.getSendInsdDeviceId());
		} else {
			createPurchaseSc.setInsdUsermbrNo(createPurchaseSc.getUseInsdUsermbrNo());
			createPurchaseSc.setInsdDeviceId(createPurchaseSc.getUseInsdDeviceId());
		}
		this.createPayment(createPurchaseSc, notifyPaymentReq);

		// 자동결제 처리
		if (StringUtils.isNotBlank(notifyPaymentReq.getGwBillkey())) {
			List<AutoPrchs> autoPrchsList = new ArrayList<AutoPrchs>();

			AutoPrchs autoPrchs = new AutoPrchs();
			autoPrchs.setTenantId(createPurchaseSc.getTenantId());
			autoPrchs.setFstPrchsId(createPurchaseSc.getPrchsId());
			autoPrchs.setFstPrchsDtlId(1); // TAKTODO
			autoPrchs.setInsdUsermbrNo(createPurchaseSc.getInsdUsermbrNo());
			autoPrchs.setInsdDeviceId(createPurchaseSc.getInsdDeviceId());
			autoPrchs.setProdId(createPurchaseSc.getProdId());
			autoPrchs.setPaymentStartDt(createPurchaseSc.getPrchsDt());
			autoPrchs.setPaymentEndDt("99991231235959"); // TAKTODO
			autoPrchs.setAfterPaymentDt(createPurchaseSc.getUseExprDt().substring(0, 8) + "000000"); // TAKTODO
			autoPrchs.setReqPathCd(createPurchaseSc.getPrchsReqPathCd());
			autoPrchs.setClientIp(createPurchaseSc.getClientIp());
			autoPrchs.setPrchsTme(0);
			autoPrchs.setLastPrchsId(createPurchaseSc.getPrchsId());
			autoPrchs.setLastPrchsDtlId(1);
			autoPrchs.setRegId(createPurchaseSc.getResvCol05());
			autoPrchs.setUpdId(createPurchaseSc.getResvCol05());
			autoPrchs.setResvCol01(createPurchaseSc.getResvCol01());
			autoPrchs.setResvCol02(createPurchaseSc.getResvCol02());
			autoPrchs.setResvCol03(createPurchaseSc.getResvCol03());
			autoPrchs.setResvCol04(createPurchaseSc.getResvCol04());
			autoPrchs.setResvCol05(createPurchaseSc.getResvCol05());
			autoPrchsList.add(autoPrchs);

			CreateAutoPurchaseScReq autoReq = new CreateAutoPurchaseScReq();
			autoReq.setAutoPrchsList(autoPrchsList);

			this.purchaseOrderSCI.createNewAutoPurchase(autoReq);
		}
	}

	/*
	 * 
	 * <pre> 결제 내역 생성. </pre>
	 * 
	 * @param createPurchaseSc 구매정보
	 * 
	 * @param notifyParam 결제정보
	 */
	private void createPayment(CreatePurchaseSc createPurchaseSc, NotifyPaymentSacReq notifyParam) {
		this.logger.debug("PRCHS,ORDER,NOTIPAY,START,{}", notifyParam);

		String tenantId = createPurchaseSc.getTenantId();
		String systemId = createPurchaseSc.getSystemId();
		String prchsId = createPurchaseSc.getPrchsId();
		String payUserKey = createPurchaseSc.getInsdUsermbrNo();
		String payDeviceKey = createPurchaseSc.getInsdDeviceId();
		String prchsDt = createPurchaseSc.getPrchsDt();
		Double totAmt = createPurchaseSc.getTotAmt();

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

			payment.setPaymentMtdCd("OR0006" + arPayInfo[0]);
			payment.setPaymentAmt(Double.valueOf(arPayInfo[1]));
			payment.setPaymentDt(prchsDt);
			payment.setPaymentStatusCd(createPurchaseSc.getStatusCd());

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
	public void setPaymentPageInfo(PurchaseOrderInfo purchaseOrderInfo) {
		this.logger.debug("PRCHS,ORDER,SET_PAYPAGE,START,{}", purchaseOrderInfo);

		purchaseOrderInfo.setPaymentPageUrl(DUMMY_PP_PAYMENT_PAGE_URL);
		purchaseOrderInfo.setPaymentPageParam(this.makeEncryptedPaymentParameter(purchaseOrderInfo));

		this.logger.debug("PRCHS,ORDER,SET_PAYPAGE,END,{}", purchaseOrderInfo);
	}

	/*
	 * <pre> 결제Page 요청시 전달할 (암호화된) 파라미터 생성. </pre>
	 * 
	 * @param purchaseOrderInfo 구매요청 정보 VO
	 * 
	 * @return 결제Page로 전달할 파라미터의 암호화된 문자열
	 */
	private String makeEncryptedPaymentParameter(PurchaseOrderInfo purchaseOrderInfo) {

		DummyProduct product = purchaseOrderInfo.getProductList().get(0);

		PaymentPageParam param = new PaymentPageParam();
		param.setMid(purchaseOrderInfo.getMid());
		param.setAuthKey(purchaseOrderInfo.getAuthKey());
		param.setOrderId(purchaseOrderInfo.getPrchsId());
		param.setMctTrDate(purchaseOrderInfo.getPrchsDt());
		param.setAmtPurchase(String.valueOf(purchaseOrderInfo.getRealTotAmt()));
		param.setPid(product.getProdId());
		param.setpName(product.getProdNm());
		param.setpDescription(product.getProdId()); // TAKTODO
		param.setpType(product.getProdId()); // TAKTODO
		param.setAid(product.getAid());
		param.setReturnFormat(PaymentPageParam.PP_RETURN_FORMAT_JSON);
		param.setReturnPath(purchaseOrderInfo.getReturnUrl());
		param.setResultPath(DUMMY_SAC_PUR_PAYMENT_NOTIFY_URL);
		param.setMdn(purchaseOrderInfo.getPurchaseMember().getDeviceId());
		param.setNmDevice(purchaseOrderInfo.getDeviceModelCd());
		param.setImei(purchaseOrderInfo.getImei());
		param.setUacd(purchaseOrderInfo.getUacd());
		param.setTypeNetwork(purchaseOrderInfo.getNetworkTypeCd().substring(
				purchaseOrderInfo.getNetworkTypeCd().length() - 1));
		param.setCarrier(purchaseOrderInfo.getPurchaseMember().getTelecom());
		param.setNoSim(purchaseOrderInfo.getSimNo());
		param.setFlgSim(purchaseOrderInfo.getSimYn());

		// 가맹점 필요 파라미터
		String useUserKey = null;
		if (StringUtils.equals(purchaseOrderInfo.getPrchsCaseCd(), PurchaseConstants.PRCHS_CASE_GIFT_CD)) {
			useUserKey = purchaseOrderInfo.getRecvUserKey();
		} else {
			useUserKey = purchaseOrderInfo.getUserKey();
		}
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
			throw new StorePlatformException("SAC_PUR_0001", "구매요청 처리 중 에러 발생");
		}
		param.setToken(token);

		this.logger.debug("TAKTEST,{},{}", eData, token);

		return eData;
	}

}
