/*
 * Copyright (c) 2013 SK planet.
 * All right reserved.
 *
 * This software is the confidential and proprietary information of SK planet.
 * You shall not disclose such Confidential Information and
 * shall use it only in accordance with the terms of the license agreement
 * you entered into with SK planet.
 */
package com.skplanet.storeplatform.sac.purchase.order.vo;

import java.util.ArrayList;
import java.util.List;

import com.skplanet.storeplatform.framework.core.common.vo.CommonInfo;
import com.skplanet.storeplatform.sac.client.purchase.vo.order.CreatePurchaseSacReq;

/**
 * 
 * 구매요청 정보
 * 
 * Updated on : 2014. 1. 16. Updated by : 이승택, nTels.
 */
public class PurchaseOrderInfo extends CommonInfo {
	private static final long serialVersionUID = 201401101L;

	// ------------------------------------------------------------------------
	// 구매요청 파라미터 정보
	private final CreatePurchaseSacReq createPurchaseReq;

	private String tenantId; // 테넌트 ID
	private String systemId; // 시스템 ID
	private String langCd; // 언어 코드
	private String userKey; // 내부 회원 번호
	private String deviceKey; // 내부 디바이스 ID
	private String prchsReqPathCd; // 구매 요청 경로 코드
	private String currencyCd; // 통화 코드
	private String ignoreReqAmtYn; // Y : 요청한 금액을 무시하고 서버 상품 금액 기준으로 구매 처리
	private double totAmt; // 총 결제 금액
	private String clientIp; // 클라이언트 IP
	private String networkTypeCd; // 네트워크 타입 코드
	private String prchsCaseCd; // 구매 유형 코드
	private String recvTenantId; // 수신자 테넌트 ID
	private String recvUserKey; // 수신자 내부 회원 번호
	private String recvDeviceKey; // 수신자 내부 디바이스 ID
	private String tenantProdGrpCd; // 테넌트 상품 분류 코드
	private String deviceModelCd; // 디바이스 모델 코드
	private String telecomCd; // 통신사: US001201-SKT, US001202-KT, US001203-LG U+
	private String imei; // 단말 식별 번호
	private String uacd; // 단말 모델 식별 번호
	private String simNo; // SIM Serial Number
	private String simYn; // SIM 조회 가능 여부

	// ------------------------------------------------------------------------
	// 추가 구매 진행 정보
	private String prchsId; // 구매 ID
	private String prchsDt; // 구매 ID
	private double realTotAmt; // 최종 결제 총 금액
	private boolean freeChargeReq; // 비과금 요청 여부
	private boolean testMdn; // Store 관리 Test MDN 여부
	private boolean blockPayment; // 구매차단 여부
	private String freePaymentMtdCd; // 무료구매처리 시 결제수단 코드 : 결제이력 생성하는 경우에만 세팅
	private String specialCouponId; // 쇼핑 특가 상품 쿠폰ID
	private double specialCouponAmt; // 쇼핑 특가 상품 쿠폰 금액

	// ------------------------------------------------------------------------
	// 회원 정보
	private PurchaseUserDevice purchaseUser; // 구매(선물발신) 회원정보
	private PurchaseUserDevice receiveUser; // 선물수신 회원정보
	private List<PurchaseUserDevice> receiveUserList; // 선물 수신 회원 목록

	// ------------------------------------------------------------------------
	// 상품 정보
	private List<PurchaseProduct> purchaseProductList = new ArrayList<PurchaseProduct>(); // 구매할 상품 정보 리스트

	// ------------------------------------------------------------------------
	// Pay Planet 결제Page 정보
	private String resultType; // 결과 타입: payment-결제Page 요청진행, free-무료구매 완료
	private String mid; // 가맹점 ID
	private String authKey; // 가맹점 인증키
	private String encKey; // Pay Planet 암호화
	private String paymentPageUrl; // 결제Page_URL
	private PaymentPageParam paymentPageParam; // 결제Page_요청_파라미터

	// ================================================================================================

	/**
	 * @param createPurchaseReq
	 *            createPurchaseReq
	 */
	public PurchaseOrderInfo(CreatePurchaseSacReq createPurchaseReq) {
		this.createPurchaseReq = createPurchaseReq;
	}

	// ================================================================================================

	/**
	 * @return the tenantId
	 */
	public String getTenantId() {
		return this.tenantId;
	}

	/**
	 * @param tenantId
	 *            the tenantId to set
	 */
	public void setTenantId(String tenantId) {
		this.tenantId = tenantId;
	}

	/**
	 * @return the systemId
	 */
	public String getSystemId() {
		return this.systemId;
	}

	/**
	 * @param systemId
	 *            the systemId to set
	 */
	public void setSystemId(String systemId) {
		this.systemId = systemId;
	}

	/**
	 * @return the langCd
	 */
	public String getLangCd() {
		return this.langCd;
	}

	/**
	 * @param langCd
	 *            the langCd to set
	 */
	public void setLangCd(String langCd) {
		this.langCd = langCd;
	}

	/**
	 * @return the userKey
	 */
	public String getUserKey() {
		return this.userKey;
	}

	/**
	 * @param userKey
	 *            the userKey to set
	 */
	public void setUserKey(String userKey) {
		this.userKey = userKey;
	}

	/**
	 * @return the deviceKey
	 */
	public String getDeviceKey() {
		return this.deviceKey;
	}

	/**
	 * @param deviceKey
	 *            the deviceKey to set
	 */
	public void setDeviceKey(String deviceKey) {
		this.deviceKey = deviceKey;
	}

	/**
	 * @return the prchsReqPathCd
	 */
	public String getPrchsReqPathCd() {
		return this.prchsReqPathCd;
	}

	/**
	 * @param prchsReqPathCd
	 *            the prchsReqPathCd to set
	 */
	public void setPrchsReqPathCd(String prchsReqPathCd) {
		this.prchsReqPathCd = prchsReqPathCd;
	}

	/**
	 * @return the mid
	 */
	public String getMid() {
		return this.mid;
	}

	/**
	 * @param mid
	 *            the mid to set
	 */
	public void setMid(String mid) {
		this.mid = mid;
	}

	/**
	 * @return the authKey
	 */
	public String getAuthKey() {
		return this.authKey;
	}

	/**
	 * @param authKey
	 *            the authKey to set
	 */
	public void setAuthKey(String authKey) {
		this.authKey = authKey;
	}

	/**
	 * @return the currencyCd
	 */
	public String getCurrencyCd() {
		return this.currencyCd;
	}

	/**
	 * @param currencyCd
	 *            the currencyCd to set
	 */
	public void setCurrencyCd(String currencyCd) {
		this.currencyCd = currencyCd;
	}

	/**
	 * @return the ignoreReqAmtYn
	 */
	public String getIgnoreReqAmtYn() {
		return this.ignoreReqAmtYn;
	}

	/**
	 * @param ignoreReqAmtYn
	 *            the ignoreReqAmtYn to set
	 */
	public void setIgnoreReqAmtYn(String ignoreReqAmtYn) {
		this.ignoreReqAmtYn = ignoreReqAmtYn;
	}

	/**
	 * @return the totAmt
	 */
	public double getTotAmt() {
		return this.totAmt;
	}

	/**
	 * @param totAmt
	 *            the totAmt to set
	 */
	public void setTotAmt(double totAmt) {
		this.totAmt = totAmt;
	}

	/**
	 * @return the clientIp
	 */
	public String getClientIp() {
		return this.clientIp;
	}

	/**
	 * @param clientIp
	 *            the clientIp to set
	 */
	public void setClientIp(String clientIp) {
		this.clientIp = clientIp;
	}

	/**
	 * @return the networkTypeCd
	 */
	public String getNetworkTypeCd() {
		return this.networkTypeCd;
	}

	/**
	 * @param networkTypeCd
	 *            the networkTypeCd to set
	 */
	public void setNetworkTypeCd(String networkTypeCd) {
		this.networkTypeCd = networkTypeCd;
	}

	/**
	 * @return the prchsCaseCd
	 */
	public String getPrchsCaseCd() {
		return this.prchsCaseCd;
	}

	/**
	 * @param prchsCaseCd
	 *            the prchsCaseCd to set
	 */
	public void setPrchsCaseCd(String prchsCaseCd) {
		this.prchsCaseCd = prchsCaseCd;
	}

	/**
	 * @return the recvTenantId
	 */
	public String getRecvTenantId() {
		return this.recvTenantId;
	}

	/**
	 * @param recvTenantId
	 *            the recvTenantId to set
	 */
	public void setRecvTenantId(String recvTenantId) {
		this.recvTenantId = recvTenantId;
	}

	/**
	 * @return the recvUserKey
	 */
	public String getRecvUserKey() {
		return this.recvUserKey;
	}

	/**
	 * @param recvUserKey
	 *            the recvUserKey to set
	 */
	public void setRecvUserKey(String recvUserKey) {
		this.recvUserKey = recvUserKey;
	}

	/**
	 * @return the recvDeviceKey
	 */
	public String getRecvDeviceKey() {
		return this.recvDeviceKey;
	}

	/**
	 * @param recvDeviceKey
	 *            the recvDeviceKey to set
	 */
	public void setRecvDeviceKey(String recvDeviceKey) {
		this.recvDeviceKey = recvDeviceKey;
	}

	/**
	 * @return the deviceModelCd
	 */
	public String getDeviceModelCd() {
		return this.deviceModelCd;
	}

	/**
	 * @param deviceModelCd
	 *            the deviceModelCd to set
	 */
	public void setDeviceModelCd(String deviceModelCd) {
		this.deviceModelCd = deviceModelCd;
	}

	/**
	 * @return the prchsId
	 */
	public String getPrchsId() {
		return this.prchsId;
	}

	/**
	 * @param prchsId
	 *            the prchsId to set
	 */
	public void setPrchsId(String prchsId) {
		this.prchsId = prchsId;
	}

	/**
	 * @return the prchsDt
	 */
	public String getPrchsDt() {
		return this.prchsDt;
	}

	/**
	 * @param prchsDt
	 *            the prchsDt to set
	 */
	public void setPrchsDt(String prchsDt) {
		this.prchsDt = prchsDt;
	}

	/**
	 * @return the realTotAmt
	 */
	public double getRealTotAmt() {
		return this.realTotAmt;
	}

	/**
	 * @param realTotAmt
	 *            the realTotAmt to set
	 */
	public void setRealTotAmt(double realTotAmt) {
		this.realTotAmt = realTotAmt;
	}

	/**
	 * @return the tenantProdGrpCd
	 */
	public String getTenantProdGrpCd() {
		return this.tenantProdGrpCd;
	}

	/**
	 * @param tenantProdGrpCd
	 *            the tenantProdGrpCd to set
	 */
	public void setTenantProdGrpCd(String tenantProdGrpCd) {
		this.tenantProdGrpCd = tenantProdGrpCd;
	}

	/**
	 * @return the telecomCd
	 */
	public String getTelecomCd() {
		return this.telecomCd;
	}

	/**
	 * @param telecomCd
	 *            the telecomCd to set
	 */
	public void setTelecomCd(String telecomCd) {
		this.telecomCd = telecomCd;
	}

	/**
	 * @return the imei
	 */
	public String getImei() {
		return this.imei;
	}

	/**
	 * @param imei
	 *            the imei to set
	 */
	public void setImei(String imei) {
		this.imei = imei;
	}

	/**
	 * @return the uacd
	 */
	public String getUacd() {
		return this.uacd;
	}

	/**
	 * @param uacd
	 *            the uacd to set
	 */
	public void setUacd(String uacd) {
		this.uacd = uacd;
	}

	/**
	 * @return the simNo
	 */
	public String getSimNo() {
		return this.simNo;
	}

	/**
	 * @param simNo
	 *            the simNo to set
	 */
	public void setSimNo(String simNo) {
		this.simNo = simNo;
	}

	/**
	 * @return the simYn
	 */
	public String getSimYn() {
		return this.simYn;
	}

	/**
	 * @param simYn
	 *            the simYn to set
	 */
	public void setSimYn(String simYn) {
		this.simYn = simYn;
	}

	/**
	 * @return the resultType
	 */
	public String getResultType() {
		return this.resultType;
	}

	/**
	 * @param resultType
	 *            the resultType to set
	 */
	public void setResultType(String resultType) {
		this.resultType = resultType;
	}

	/**
	 * @return the encKey
	 */
	public String getEncKey() {
		return this.encKey;
	}

	/**
	 * @param encKey
	 *            the encKey to set
	 */
	public void setEncKey(String encKey) {
		this.encKey = encKey;
	}

	/**
	 * @return the paymentPageUrl
	 */
	public String getPaymentPageUrl() {
		return this.paymentPageUrl;
	}

	/**
	 * @param paymentPageUrl
	 *            the paymentPageUrl to set
	 */
	public void setPaymentPageUrl(String paymentPageUrl) {
		this.paymentPageUrl = paymentPageUrl;
	}

	/**
	 * @return the paymentPageParam
	 */
	public PaymentPageParam getPaymentPageParam() {
		return this.paymentPageParam;
	}

	/**
	 * @param paymentPageParam
	 *            the paymentPageParam to set
	 */
	public void setPaymentPageParam(PaymentPageParam paymentPageParam) {
		this.paymentPageParam = paymentPageParam;
	}

	/**
	 * @return the createPurchaseReq
	 */
	public CreatePurchaseSacReq getCreatePurchaseReq() {
		return this.createPurchaseReq;
	}

	/**
	 * @return the freeChargeReq
	 */
	public boolean isFreeChargeReq() {
		return this.freeChargeReq;
	}

	/**
	 * @param freeChargeReq
	 *            the freeChargeReq to set
	 */
	public void setFreeChargeReq(boolean freeChargeReq) {
		this.freeChargeReq = freeChargeReq;
	}

	/**
	 * @return the purchaseUser
	 */
	public PurchaseUserDevice getPurchaseUser() {
		return this.purchaseUser;
	}

	/**
	 * @param purchaseUser
	 *            the purchaseUser to set
	 */
	public void setPurchaseUser(PurchaseUserDevice purchaseUser) {
		this.purchaseUser = purchaseUser;
	}

	/**
	 * @return the receiveUser
	 */
	public PurchaseUserDevice getReceiveUser() {
		return this.receiveUser;
	}

	/**
	 * @param receiveUser
	 *            the receiveUser to set
	 */
	public void setReceiveUser(PurchaseUserDevice receiveUser) {
		this.receiveUser = receiveUser;
	}

	/**
	 * @return the receiveUserList
	 */
	public List<PurchaseUserDevice> getReceiveUserList() {
		return this.receiveUserList;
	}

	/**
	 * @param receiveUserList
	 *            the receiveUserList to set
	 */
	public void setReceiveUserList(List<PurchaseUserDevice> receiveUserList) {
		this.receiveUserList = receiveUserList;
	}

	/**
	 * @return the purchaseProductList
	 */
	public List<PurchaseProduct> getPurchaseProductList() {
		return this.purchaseProductList;
	}

	/**
	 * @param purchaseProductList
	 *            the purchaseProductList to set
	 */
	public void setPurchaseProductList(List<PurchaseProduct> purchaseProductList) {
		this.purchaseProductList = purchaseProductList;
	}

	/**
	 * @return the testMdn
	 */
	public boolean isTestMdn() {
		return this.testMdn;
	}

	/**
	 * @param testMdn
	 *            the testMdn to set
	 */
	public void setTestMdn(boolean testMdn) {
		this.testMdn = testMdn;
	}

	/**
	 * @return the blockPayment
	 */
	public boolean isBlockPayment() {
		return this.blockPayment;
	}

	/**
	 * @param blockPayment
	 *            the blockPayment to set
	 */
	public void setBlockPayment(boolean blockPayment) {
		this.blockPayment = blockPayment;
	}

	/**
	 * @return the freePaymentMtdCd
	 */
	public String getFreePaymentMtdCd() {
		return this.freePaymentMtdCd;
	}

	/**
	 * @param freePaymentMtdCd
	 *            the freePaymentMtdCd to set
	 */
	public void setFreePaymentMtdCd(String freePaymentMtdCd) {
		this.freePaymentMtdCd = freePaymentMtdCd;
	}

	/**
	 * @return the specialCouponId
	 */
	public String getSpecialCouponId() {
		return this.specialCouponId;
	}

	/**
	 * @param specialCouponId
	 *            the specialCouponId to set
	 */
	public void setSpecialCouponId(String specialCouponId) {
		this.specialCouponId = specialCouponId;
	}

	/**
	 * @return the specialCouponAmt
	 */
	public double getSpecialCouponAmt() {
		return this.specialCouponAmt;
	}

	/**
	 * @param specialCouponAmt
	 *            the specialCouponAmt to set
	 */
	public void setSpecialCouponAmt(double specialCouponAmt) {
		this.specialCouponAmt = specialCouponAmt;
	}

}
