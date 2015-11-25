/*
 * Copyright (c) 2013 SK planet.
 * All right reserved.
 *
 * This software is the confidential and proprietary information of SK planet.
 * You shall not disclose such Confidential Information and
 * shall use it only in accordance with the terms of the license agreement
 * you entered into with SK planet.
 */
package com.skplanet.storeplatform.sac.client.display.vo.feature.intimateMessage;

import org.hibernate.validator.constraints.NotBlank;

import com.skplanet.storeplatform.framework.core.common.vo.CommonInfo;

/**
 * Intimate Message 조회 Request Value Object.
 * 
 * Updated on : 2014. 02. 06. Updated by : 이태희.
 */
public class IntimateMessageSacReq extends CommonInfo {
	private static final long serialVersionUID = 1L;

	@NotBlank
	private String msgType; // 메세지 타입

	private String userKey; // 사용자고유키

	private String deviceKey; // 디바이스키

	private Integer offset; // 시작점 ROW

	private Integer count; // 페이지당 노출될 ROW 개수

	private String tenantId; // 테넌트ID

	private String deviceChangeYn; // 기기변경 여부

	private String memberGrade; // 회원등급

	/**
	 * @return the msgType
	 */
	public String getMsgType() {
		return this.msgType;
	}

	/**
	 * @param msgType
	 *            the msgType to set
	 */
	public void setMsgType(String msgType) {
		this.msgType = msgType;
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
	 * @return the offset
	 */
	public Integer getOffset() {
		return this.offset;
	}

	/**
	 * @param offset
	 *            the offset to set
	 */
	public void setOffset(Integer offset) {
		this.offset = offset;
	}

	/**
	 * @return the count
	 */
	public Integer getCount() {
		return this.count;
	}

	/**
	 * @param count
	 *            the count to set
	 */
	public void setCount(Integer count) {
		this.count = count;
	}

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
	 * @return the deviceChangeYn
	 */
	public String getDeviceChangeYn() {
		return this.deviceChangeYn;
	}

	/**
	 * @param deviceChangeYn
	 *            the deviceChangeYn to set
	 */
	public void setDeviceChangeYn(String deviceChangeYn) {
		this.deviceChangeYn = deviceChangeYn;
	}

	/**
	 * @return the memberGrade
	 */
	public String getMemberGrade() {
		return this.memberGrade;
	}

	/**
	 * @param memberGrade
	 *            the memberGrade to set
	 */
	public void setMemberGrade(String memberGrade) {
		this.memberGrade = memberGrade;
	}
}
