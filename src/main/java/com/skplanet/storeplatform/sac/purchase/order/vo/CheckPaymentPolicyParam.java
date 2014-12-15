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

import com.skplanet.storeplatform.framework.core.common.vo.CommonInfo;

/**
 * 
 * 결제 정책 체크 요청 VO
 * 
 * Updated on : 2014. 11. 24. Updated by : 이승택, nTels.
 */
public class CheckPaymentPolicyParam extends CommonInfo {
	private static final long serialVersionUID = 1L;

	private String tenantId;
	private String systemId;
	private String userKey;
	private String deviceKey;
	private String deviceId;
	private String recvTenantId;
	private String recvUserKey;
	private String recvDeviceKey;
	private String recvDeviceId;
	private String tenantProdGrpCd;
	private double paymentTotAmt;

	private String sktSvcMangNo; // SKT 서비스 관리번호
	private String telecom; // 통신사 코드

	private String prodId;
	private String prodCaseCd;
	private String cmpxProdClsfCd;
	private String parentProdId; // 인앱 경우에만 사용 : AID

	private boolean autoPrchs; // 자동결제 상품여부
	private boolean s2sAutoPrchs; // IAP S2S 자동결제 상품여부
	private boolean s2s; // IAP S2S 상품여부

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
	 * @return the deviceId
	 */
	public String getDeviceId() {
		return this.deviceId;
	}

	/**
	 * @param deviceId
	 *            the deviceId to set
	 */
	public void setDeviceId(String deviceId) {
		this.deviceId = deviceId;
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
	 * @return the recvDeviceId
	 */
	public String getRecvDeviceId() {
		return this.recvDeviceId;
	}

	/**
	 * @param recvDeviceId
	 *            the recvDeviceId to set
	 */
	public void setRecvDeviceId(String recvDeviceId) {
		this.recvDeviceId = recvDeviceId;
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
	 * @return the paymentTotAmt
	 */
	public double getPaymentTotAmt() {
		return this.paymentTotAmt;
	}

	/**
	 * @param paymentTotAmt
	 *            the paymentTotAmt to set
	 */
	public void setPaymentTotAmt(double paymentTotAmt) {
		this.paymentTotAmt = paymentTotAmt;
	}

	/**
	 * @return the sktSvcMangNo
	 */
	public String getSktSvcMangNo() {
		return this.sktSvcMangNo;
	}

	/**
	 * @param sktSvcMangNo
	 *            the sktSvcMangNo to set
	 */
	public void setSktSvcMangNo(String sktSvcMangNo) {
		this.sktSvcMangNo = sktSvcMangNo;
	}

	/**
	 * @return the telecom
	 */
	public String getTelecom() {
		return this.telecom;
	}

	/**
	 * @param telecom
	 *            the telecom to set
	 */
	public void setTelecom(String telecom) {
		this.telecom = telecom;
	}

	/**
	 * @return the prodId
	 */
	public String getProdId() {
		return this.prodId;
	}

	/**
	 * @param prodId
	 *            the prodId to set
	 */
	public void setProdId(String prodId) {
		this.prodId = prodId;
	}

	/**
	 * @return the prodCaseCd
	 */
	public String getProdCaseCd() {
		return this.prodCaseCd;
	}

	/**
	 * @param prodCaseCd
	 *            the prodCaseCd to set
	 */
	public void setProdCaseCd(String prodCaseCd) {
		this.prodCaseCd = prodCaseCd;
	}

	/**
	 * @return the cmpxProdClsfCd
	 */
	public String getCmpxProdClsfCd() {
		return this.cmpxProdClsfCd;
	}

	/**
	 * @param cmpxProdClsfCd
	 *            the cmpxProdClsfCd to set
	 */
	public void setCmpxProdClsfCd(String cmpxProdClsfCd) {
		this.cmpxProdClsfCd = cmpxProdClsfCd;
	}

	/**
	 * @return the parentProdId
	 */
	public String getParentProdId() {
		return this.parentProdId;
	}

	/**
	 * @param parentProdId
	 *            the parentProdId to set
	 */
	public void setParentProdId(String parentProdId) {
		this.parentProdId = parentProdId;
	}

	/**
	 * @return the autoPrchs
	 */
	public boolean isAutoPrchs() {
		return this.autoPrchs;
	}

	/**
	 * @param autoPrchs
	 *            the autoPrchs to set
	 */
	public void setAutoPrchs(boolean autoPrchs) {
		this.autoPrchs = autoPrchs;
	}

	/**
	 * @return the s2sAutoPrchs
	 */
	public boolean isS2sAutoPrchs() {
		return this.s2sAutoPrchs;
	}

	/**
	 * @param s2sAutoPrchs
	 *            the s2sAutoPrchs to set
	 */
	public void setS2sAutoPrchs(boolean s2sAutoPrchs) {
		this.s2sAutoPrchs = s2sAutoPrchs;
	}

	/**
	 * @return the s2s
	 */
	public boolean isS2s() {
		return this.s2s;
	}

	/**
	 * @param s2s
	 *            the s2s to set
	 */
	public void setS2s(boolean s2s) {
		this.s2s = s2s;
	}

}
