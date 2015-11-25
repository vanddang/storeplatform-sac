/*
 * Copyright (c) 2013 SK planet.
 * All right reserved.
 *
 * This software is the confidential and proprietary information of SK planet.
 * You shall not disclose such Confidential Information and
 * shall use it only in accordance with the terms of the license agreement
 * you entered into with SK planet.
 */
package com.skplanet.storeplatform.purchase.client.order.vo;

import com.skplanet.storeplatform.framework.core.common.vo.CommonInfo;

/**
 * 
 * SKT 후불 결제 관련 조회 요청 VO
 * 
 * Updated on : 2014. 2. 12. Updated by : 이승택, nTels.
 */
public class SearchSktPaymentScReq extends CommonInfo {
	private static final long serialVersionUID = 201402121L;

	private String tenantId;
	private String userKey;
	private String deviceKey;
	private String applyUnitCd;
	private String condUnitCd;
	private String condValue;
	private String condClsfUnitCd;
	private String condPeriodUnitCd;
	private String condPeriodValue;
	private String tenantProdGrpCd;
	private String exceptTenantProdGrpCd;

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
	 * @return the applyUnitCd
	 */
	public String getApplyUnitCd() {
		return this.applyUnitCd;
	}

	/**
	 * @param applyUnitCd
	 *            the applyUnitCd to set
	 */
	public void setApplyUnitCd(String applyUnitCd) {
		this.applyUnitCd = applyUnitCd;
	}

	/**
	 * @return the condUnitCd
	 */
	public String getCondUnitCd() {
		return this.condUnitCd;
	}

	/**
	 * @param condUnitCd
	 *            the condUnitCd to set
	 */
	public void setCondUnitCd(String condUnitCd) {
		this.condUnitCd = condUnitCd;
	}

	/**
	 * @return the condValue
	 */
	public String getCondValue() {
		return this.condValue;
	}

	/**
	 * @param condValue
	 *            the condValue to set
	 */
	public void setCondValue(String condValue) {
		this.condValue = condValue;
	}

	/**
	 * @return the condClsfUnitCd
	 */
	public String getCondClsfUnitCd() {
		return this.condClsfUnitCd;
	}

	/**
	 * @param condClsfUnitCd
	 *            the condClsfUnitCd to set
	 */
	public void setCondClsfUnitCd(String condClsfUnitCd) {
		this.condClsfUnitCd = condClsfUnitCd;
	}

	/**
	 * @return the condPeriodUnitCd
	 */
	public String getCondPeriodUnitCd() {
		return this.condPeriodUnitCd;
	}

	/**
	 * @param condPeriodUnitCd
	 *            the condPeriodUnitCd to set
	 */
	public void setCondPeriodUnitCd(String condPeriodUnitCd) {
		this.condPeriodUnitCd = condPeriodUnitCd;
	}

	/**
	 * @return the condPeriodValue
	 */
	public String getCondPeriodValue() {
		return this.condPeriodValue;
	}

	/**
	 * @param condPeriodValue
	 *            the condPeriodValue to set
	 */
	public void setCondPeriodValue(String condPeriodValue) {
		this.condPeriodValue = condPeriodValue;
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
	 * @return the exceptTenantProdGrpCd
	 */
	public String getExceptTenantProdGrpCd() {
		return this.exceptTenantProdGrpCd;
	}

	/**
	 * @param exceptTenantProdGrpCd
	 *            the exceptTenantProdGrpCd to set
	 */
	public void setExceptTenantProdGrpCd(String exceptTenantProdGrpCd) {
		this.exceptTenantProdGrpCd = exceptTenantProdGrpCd;
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
