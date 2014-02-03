/*
 * Copyright (c) 2013 SK planet.
 * All right reserved.
 *
 * This software is the confidential and proprietary information of SK planet.
 * You shall not disclose such Confidential Information and
 * shall use it only in accordance with the terms of the license agreement
 * you entered into with SK planet.
 */
package com.skplanet.storeplatform.sac.client.purchase.vo.history;

import java.io.Serializable;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotEmpty;

import com.skplanet.storeplatform.framework.core.common.vo.CommonInfo;

/**
 * 
 * 자동결제해치예약 요청.
 * 
 * Updated on : 2014. 01. 15. Updated by : 조용진, 엔텔스.
 */

public class AutoPaymentCancelSacReq extends CommonInfo implements Serializable {
	private static final long serialVersionUID = 1L;

	private String tenantId; // Tenant ID
	private String systemId; // 시스템ID
	@NotNull
	@NotEmpty
	private String insdUsermbrNo; // 회원번호
	@NotNull
	@NotEmpty
	private String insdDeviceId; // 디바이스 번호
	@NotNull
	@NotEmpty
	private String prchsId; // 구매ID
	@NotNull
	@NotEmpty
	private String closedCd; // 해지예약코드
	@NotNull
	@NotEmpty
	private String closedReasonCd; // 해지사유 코드
	@NotNull
	@NotEmpty
	private String closedReqPathCd; // 해지요청경로 코드

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
	 * @return the insdDeviceId
	 */
	public String getInsdDeviceId() {
		return this.insdDeviceId;
	}

	/**
	 * @param insdDeviceId
	 *            the insdDeviceId to set
	 */
	public void setInsdDeviceId(String insdDeviceId) {
		this.insdDeviceId = insdDeviceId;
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
	 * @return the closedCd
	 */
	public String getClosedCd() {
		return this.closedCd;
	}

	/**
	 * @param closedCd
	 *            the closedCd to set
	 */
	public void setClosedCd(String closedCd) {
		this.closedCd = closedCd;
	}

	/**
	 * @return the closedReasonCd
	 */
	public String getClosedReasonCd() {
		return this.closedReasonCd;
	}

	/**
	 * @param closedReasonCd
	 *            the closedReasonCd to set
	 */
	public void setClosedReasonCd(String closedReasonCd) {
		this.closedReasonCd = closedReasonCd;
	}

	/**
	 * @return the closedReqPathCd
	 */
	public String getClosedReqPathCd() {
		return this.closedReqPathCd;
	}

	/**
	 * @param closedReqPathCd
	 *            the closedReqPathCd to set
	 */
	public void setClosedReqPathCd(String closedReqPathCd) {
		this.closedReqPathCd = closedReqPathCd;
	}

}
