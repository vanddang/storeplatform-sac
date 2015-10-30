/*
 * Copyright (c) 2013 SK planet.
 * All right reserved.
 *
 * This software is the confidential and proprietary information of SK planet.
 * You shall not disclose such Confidential Information and
 * shall use it only in accordance with the terms of the license agreement
 * you entered into with SK planet.
 */
package com.skplanet.storeplatform.purchase.client.membership.vo;

import com.skplanet.storeplatform.framework.core.common.vo.CommonInfo;

/**
 * 
 * 마일리지 적립 예상 총 금액 조회 요청 VO
 * 
 * Updated on : 2014. 8. 8. Updated by : 이승택, nTels.
 */
public class SearchSaveExpectTotalAmtScReq extends CommonInfo {
	private static final long serialVersionUID = 1L;

	private String tenantId;
	private String insdUsermbrNo;
	private String targetDt;
	private String saveDt;
	private Integer promId;

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
	 * @return the insdUsermbrNo
	 */
	public String getInsdUsermbrNo() {
		return this.insdUsermbrNo;
	}

	/**
	 * @param insdUsermbrNo
	 *            the insdUsermbrNo to set
	 */
	public void setInsdUsermbrNo(String insdUsermbrNo) {
		this.insdUsermbrNo = insdUsermbrNo;
	}

	/**
	 * @return the targetDt
	 */
	public String getTargetDt() {
		return this.targetDt;
	}

	/**
	 * @param targetDt
	 *            the targetDt to set
	 */
	public void setTargetDt(String targetDt) {
		this.targetDt = targetDt;
	}

	/**
	 * @return the saveDt
	 */
	public String getSaveDt() {
		return this.saveDt;
	}

	/**
	 * @param saveDt
	 *            the saveDt to set
	 */
	public void setSaveDt(String saveDt) {
		this.saveDt = saveDt;
	}

	/**
	 * Gets prom id.
	 *
	 * @return the prom id
	 */
	public Integer getPromId() {
		return promId;
	}

	/**
	 * Sets prom id.
	 *
	 * @param promId
	 *            the prom id
	 */
	public void setPromId(Integer promId) {
		this.promId = promId;
	}
}
