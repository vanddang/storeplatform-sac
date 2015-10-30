/*
 * Copyright (c) 2013 SK planet.
 * All right reserved.
 *
 * This software is the confidential and proprietary information of SK planet.
 * You shall not disclose such Confidential Information and
 * shall use it only in accordance with the terms of the license agreement
 * you entered into with SK planet.
 */
package com.skplanet.storeplatform.purchase.client.history.vo;

import java.io.Serializable;
import java.util.List;

import com.skplanet.storeplatform.framework.core.common.vo.CommonInfo;

/**
 * 
 * 구매내역숨김처리 컴포넌트 요청.
 * 
 * Updated on : 2014. 12. 20. Updated by : 조용진, 엔텔스.
 */
public class HidingScReq extends CommonInfo implements Serializable {
	private static final long serialVersionUID = 1L;

	private String tenantId; // Tenant ID
	private String systemId; // 시스템ID
	private String userKey; // 내부사용자번호
	private String deviceKey; // 내부디바이스ID
	private String sendYn; // 미보유상품 숨김처리 Y, 보유상품 숨김처리 N
	private String deviceYn; // 미보유상품 숨김처리 Y, 보유상품 숨김처리 N
	private List<HidingListSc> hidingList; // 구매내역 숨김 리스트

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
	 * @return the deviceYn
	 */
	public String getDeviceYn() {
		return this.deviceYn;
	}

	/**
	 * @param deviceYn
	 *            the deviceYn to set
	 */
	public void setDeviceYn(String deviceYn) {
		this.deviceYn = deviceYn;
	}

	/**
	 * @return the hidingList
	 */
	public List<HidingListSc> getHidingList() {
		return this.hidingList;
	}

	/**
	 * @param hidingList
	 *            the hidingList to set
	 */
	public void setHidingList(List<HidingListSc> hidingList) {
		this.hidingList = hidingList;
	}

}
