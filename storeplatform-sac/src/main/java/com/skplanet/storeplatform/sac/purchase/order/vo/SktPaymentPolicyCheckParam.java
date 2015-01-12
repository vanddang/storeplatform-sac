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
 * 구매 제한 정책 체크 요청 VO
 * 
 * Updated on : 2014. 1. 24. Updated by : 이승택, nTels.
 */
public class SktPaymentPolicyCheckParam extends CommonInfo {
	private static final long serialVersionUID = 201401221L;

	private String tenantId;
	private String userKey;
	private String deviceKey;
	private String deviceId;
	private String recvTenantId;
	private String recvUserKey;
	private String recvDeviceKey;
	private String recvDeviceId;
	private String tenantProdGrpCd;
	private double paymentTotAmt;

	private String svcMangNo; // SKT 서비스 관리번호

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
	 * @return the svcMangNo
	 */
	public String getSvcMangNo() {
		return this.svcMangNo;
	}

	/**
	 * @param svcMangNo
	 *            the svcMangNo to set
	 */
	public void setSvcMangNo(String svcMangNo) {
		this.svcMangNo = svcMangNo;
	}

}
