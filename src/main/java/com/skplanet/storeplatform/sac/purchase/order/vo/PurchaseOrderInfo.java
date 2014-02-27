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
import com.skplanet.storeplatform.sac.purchase.order.dummy.vo.DummyMember;
import com.skplanet.storeplatform.sac.purchase.order.dummy.vo.DummyProduct;

/**
 * 
 * Dummy 구매요청 정보
 * 
 * Updated on : 2014. 1. 16. Updated by : 이승택, nTels.
 */
public class PurchaseOrderInfo extends CommonInfo {
	private static final long serialVersionUID = 201401101L;

	private final CreatePurchaseSacReq createPurchaseReq;

	private String tenantId; // 테넌트 ID
	private String systemId; // 시스템 ID
	private String userKey; // 내부 회원 번호
	private String deviceKey; // 내부 디바이스 ID
	private String prchsReqPathCd; // 구매 요청 경로 코드
	private String mid; // 가맹점 ID
	private String authKey; // 가맹점 인증키
	private String returnUrl; // 테넌트 결과처리 URL
	private String currencyCd; // 통화 코드
	private double totAmt; // 총 결제 금액
	private String clientIp; // 클라이언트 IP
	private String networkTypeCd; // 네트워크 타입 코드
	private String prchsCaseCd; // 구매 유형 코드
	private String recvTenantId; // 수신자 테넌트 ID
	private String recvUserKey; // 수신자 내부 회원 번호
	private String recvDeviceKey; // 수신자 내부 디바이스 ID
	private String tenantProdGrpCd; // 테넌트 상품 분류 코드
	private String imei; // 단말 식별 번호
	private String uacd; // 단말 모델 식별 번호
	private String simNo; // SIM Serial Number
	private String simYn; // SIM 조회 가능 여부

	private String deviceModelCd; // 요청 단말 모델 코드

	private String prchsId; // 구매 ID
	private String prchsDt; // 구매 ID
	private double realTotAmt; // 최종 결제 총 금액
	private boolean freeChargeReq; // 비과금 요청 여부

	private String resultType; // 결과 타입: payment-결제Page 요청진행, free-무료구매 완료
	private String paymentPageUrl; // 결제Page_URL
	private String paymentPageParam; // 결제Page_요청_파라미터

	private DummyMember purchaseMember; // 구매(선물발신) 회원정보
	private DummyMember recvMember; // 선물수신 회원정보

	private List<DummyProduct> productList = new ArrayList<DummyProduct>(); // 구매할 상품 정보 리스트

	private PurchaseOrderPolicyCheckResult policyInfo = new PurchaseOrderPolicyCheckResult(); // 제한정책 정보

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
	 * @return the returnUrl
	 */
	public String getReturnUrl() {
		return this.returnUrl;
	}

	/**
	 * @param returnUrl
	 *            the returnUrl to set
	 */
	public void setReturnUrl(String returnUrl) {
		this.returnUrl = returnUrl;
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
	public String getPaymentPageParam() {
		return this.paymentPageParam;
	}

	/**
	 * @param paymentPageParam
	 *            the paymentPageParam to set
	 */
	public void setPaymentPageParam(String paymentPageParam) {
		this.paymentPageParam = paymentPageParam;
	}

	/**
	 * @return the purchaseMember
	 */
	public DummyMember getPurchaseMember() {
		return this.purchaseMember;
	}

	/**
	 * @param purchaseMember
	 *            the purchaseMember to set
	 */
	public void setPurchaseMember(DummyMember purchaseMember) {
		this.purchaseMember = purchaseMember;
	}

	/**
	 * @return the recvMember
	 */
	public DummyMember getRecvMember() {
		return this.recvMember;
	}

	/**
	 * @param recvMember
	 *            the recvMember to set
	 */
	public void setRecvMember(DummyMember recvMember) {
		this.recvMember = recvMember;
	}

	/**
	 * @return the productList
	 */
	public List<DummyProduct> getProductList() {
		return this.productList;
	}

	/**
	 * @param productList
	 *            the productList to set
	 */
	public void setProductList(List<DummyProduct> productList) {
		this.productList = productList;
	}

	/**
	 * @return the policyInfo
	 */
	public PurchaseOrderPolicyCheckResult getPolicyInfo() {
		return this.policyInfo;
	}

	/**
	 * @param policyInfo
	 *            the policyInfo to set
	 */
	public void setPolicyInfo(PurchaseOrderPolicyCheckResult policyInfo) {
		this.policyInfo = policyInfo;
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

}
