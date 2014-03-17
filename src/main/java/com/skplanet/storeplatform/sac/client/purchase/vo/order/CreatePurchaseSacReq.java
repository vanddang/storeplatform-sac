/*
 * Copyright (c) 2013 SK planet.
 * All right reserved.
 *
 * This software is the confidential and proprietary information of SK planet.
 * You shall not disclose such Confidential Information and
 * shall use it only in accordance with the terms of the license agreement
 * you entered into with SK planet.
 */
package com.skplanet.storeplatform.sac.client.purchase.vo.order;

import java.util.List;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Null;

import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.NotEmpty;

import com.skplanet.storeplatform.framework.core.common.vo.CommonInfo;

/**
 * 
 * 구매요청 요청 VO
 * 
 * Updated on : 2014. 1. 16. Updated by : 이승택, nTels.
 */
public class CreatePurchaseSacReq extends CommonInfo {
	public interface GroupCreatePurchase {
	}

	public interface GroupCreateFreePurchase {
	}

	private static final long serialVersionUID = 201401031L;

	@NotBlank(groups = { GroupCreatePurchase.class, GroupCreateFreePurchase.class })
	private String userKey; // 내부 회원 번호
	@NotBlank(groups = { GroupCreatePurchase.class, GroupCreateFreePurchase.class })
	private String deviceKey; // 내부 디바이스 ID
	@Null(groups = { GroupCreateFreePurchase.class })
	private String recvUserKey; // (선물 경우 필수) 수신자 내부 회원 번호
	@Null(groups = { GroupCreateFreePurchase.class })
	private String recvDeviceKey; // (선물 경우 필수) 수신자 내부 디바이스 ID
	@NotBlank(groups = { GroupCreatePurchase.class, GroupCreateFreePurchase.class })
	private String prchsReqPathCd; // 구매 요청 경로 코드
	@Null(groups = { GroupCreateFreePurchase.class })
	private String mid; // 가맹점 ID
	@Null(groups = { GroupCreateFreePurchase.class })
	private String authKey; // 가맹점 인증키
	@Null(groups = { GroupCreateFreePurchase.class })
	private String returnUrl; // 결과처리 URL
	@NotBlank(groups = { GroupCreatePurchase.class, GroupCreateFreePurchase.class })
	private String currencyCd; // 통화 코드
	@NotNull(groups = { GroupCreatePurchase.class })
	@Null(groups = { GroupCreateFreePurchase.class })
	private Double totAmt; // 총 결제 금액
	@NotBlank(groups = { GroupCreatePurchase.class, GroupCreateFreePurchase.class })
	private String clientIp; // 클라이언트 IP
	@NotBlank(groups = { GroupCreatePurchase.class, GroupCreateFreePurchase.class })
	private String networkTypeCd; // 네트워크 타입 코드
	@NotBlank(groups = { GroupCreatePurchase.class, GroupCreateFreePurchase.class })
	private String prchsCaseCd; // 구매 유형 코드
	@NotBlank(groups = { GroupCreatePurchase.class, GroupCreateFreePurchase.class })
	private String tenantProdGrpCd; // 테넌트 상품 분류 코드
	@Null(groups = { GroupCreateFreePurchase.class })
	private String imei; // 단말 식별 번호
	@Null(groups = { GroupCreateFreePurchase.class })
	private String uacd; // 단말 모델 식별 번호
	@Null(groups = { GroupCreateFreePurchase.class })
	private String simNo; // SIM Serial Number
	@Null(groups = { GroupCreateFreePurchase.class })
	private String simYn; // SIM 조회 가능 여부

	@NotEmpty(groups = { GroupCreatePurchase.class, GroupCreateFreePurchase.class })
	@Valid
	private List<CreatePurchaseSacReqProduct> productList; // 구매할 상품 리스트

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
	 * @return the productList
	 */
	public List<CreatePurchaseSacReqProduct> getProductList() {
		return this.productList;
	}

	/**
	 * @param productList
	 *            the productList to set
	 */
	public void setProductList(List<CreatePurchaseSacReqProduct> productList) {
		this.productList = productList;
	}

}
