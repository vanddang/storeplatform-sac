/*
 * Copyright (c) 2013 SK planet.
 * All right reserved.
 *
 * This software is the confidential and proprietary information of SK planet.
 * You shall not disclose such Confidential Information and
 * shall use it only in accordance with the terms of the license agreement
 * you entered into with SK planet.
 */
package com.skplanet.storeplatform.purchase.history.vo;

import java.io.Serializable;

import com.skplanet.storeplatform.framework.core.common.vo.CommonInfo;

/**
 * 
 * 구매내역숨김 조회셋팅.
 * 
 * Updated on : 2013. 12. 28. Updated by : 조용진, 엔텔스.
 */
public class Hiding extends CommonInfo implements Serializable {

	/** The Constant serial Version UID. */
	private static final long serialVersionUID = 1L;

	private String tenantId; // Tenant ID
	private String systemId;
	private String userKey; // 내부사용자번호
	private String deviceKey; // 내부디바이스ID
	private String prchsId; // 구매아이디
	private Integer prchsDtlId; // 구매상세아이디
	private String sendYn; // 미보유상품 숨김처리 Y, 보유상품 숨김처리 N
	private String hidingYn; // 숨김여부

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
	 * @return the prchsDtlId
	 */
	public Integer getPrchsDtlId() {
		return this.prchsDtlId;
	}

	/**
	 * @param prchsDtlId
	 *            the prchsDtlId to set
	 */
	public void setPrchsDtlId(Integer prchsDtlId) {
		this.prchsDtlId = prchsDtlId;
	}

	/**
	 * @return the sendYn
	 */
	public String getSendYn() {
		return this.sendYn;
	}

	/**
	 * @param sendYn
	 *            the sendYn to set
	 */
	public void setSendYn(String sendYn) {
		this.sendYn = sendYn;
	}

	/**
	 * @return the hidingYn
	 */
	public String getHidingYn() {
		return this.hidingYn;
	}

	/**
	 * @param hidingYn
	 *            the hidingYn to set
	 */
	public void setHidingYn(String hidingYn) {
		this.hidingYn = hidingYn;
	}

}
