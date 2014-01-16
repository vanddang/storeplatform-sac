/*
 * Copyright (c) 2013 SK planet.
 * All right reserved.
 *
 * This software is the confidential and proprietary information of SK planet.
 * You shall not disclose such Confidential Information and
 * shall use it only in accordance with the terms of the license agreement
 * you entered into with SK planet.
 */
package com.skplanet.storeplatform.sac.purchase.order.dummy.vo;

import com.skplanet.storeplatform.framework.core.common.vo.CommonInfo;

/**
 * 
 * Dummy 회원정보
 * 
 * Updated on : 2014. 1. 16. Updated by : 이승택, nTels.
 */
public class DummyMember extends CommonInfo {

	private static final long serialVersionUID = 201401101L;

	private String tenantId; // 테넌트 ID
	private String insdUsermbrNo; // 내부 회원 번호
	private String insdDeviceId; // 내부 디바이스 ID
	private String mdn; // MDN
	private Integer age; // 연령
	private Boolean bAvailableMember; // 정상상태 회원 여부
	private Boolean bLogin; // 로그인 여부

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
	 * @return the mdn
	 */
	public String getMdn() {
		return this.mdn;
	}

	/**
	 * @param mdn
	 *            the mdn to set
	 */
	public void setMdn(String mdn) {
		this.mdn = mdn;
	}

	/**
	 * @return the age
	 */
	public Integer getAge() {
		return this.age;
	}

	/**
	 * @param age
	 *            the age to set
	 */
	public void setAge(Integer age) {
		this.age = age;
	}

	/**
	 * @return the bAvailableMember
	 */
	public Boolean getbAvailableMember() {
		return this.bAvailableMember;
	}

	/**
	 * @param bAvailableMember
	 *            the bAvailableMember to set
	 */
	public void setbAvailableMember(Boolean bAvailableMember) {
		this.bAvailableMember = bAvailableMember;
	}

	/**
	 * @return the bLogin
	 */
	public Boolean getbLogin() {
		return this.bLogin;
	}

	/**
	 * @param bLogin
	 *            the bLogin to set
	 */
	public void setbLogin(Boolean bLogin) {
		this.bLogin = bLogin;
	}

}
