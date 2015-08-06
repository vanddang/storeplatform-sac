/*
 * Copyright (c) 2013 SK planet.
 * All right reserved.
 *
 * This software is the confidential and proprietary information of SK planet.
 * You shall not disclose such Confidential Information and
 * shall use it only in accordance with the terms of the license agreement
 * you entered into with SK planet.
 */

package com.skplanet.storeplatform.sac.display.personal.vo;

/**
 * Updated on : 2015-08-06. Updated by : 양해엽, SK Planet.
 */
public class UpdateContextParam {
	private String tenantId;
	private String langCd;
	private String deviceModelCd;
	private String deviceId;
	private String networkType;
	private Integer updLimitCnt;
	private MemberInfo memberInfo;
	private String memberType;
	private String operator;

	public UpdateContextParam(String tenantId, String langCd, String deviceModelCd, String networkType, String memberType, String operator) {
		this.tenantId = tenantId;
		this.langCd = langCd;
		this.deviceModelCd = deviceModelCd;
		this.networkType = networkType;
		this.memberType = memberType;
		this.operator = operator;
	}

	public UpdateContextParam(String tenantId, String langCd, String deviceModelCd, String deviceId, String networkType, Integer updLimitCnt) {
		this.tenantId = tenantId;
		this.langCd = langCd;
		this.deviceModelCd = deviceModelCd;
		this.deviceId = deviceId;
		this.networkType = networkType;
		this.updLimitCnt = updLimitCnt;
	}

	public String getTenantId() {
		return tenantId;
	}

	public void setTenantId(String tenantId) {
		this.tenantId = tenantId;
	}

	public String getLangCd() {
		return langCd;
	}

	public void setLangCd(String langCd) {
		this.langCd = langCd;
	}

	public String getDeviceModelCd() {
		return deviceModelCd;
	}

	public void setDeviceModelCd(String deviceModelCd) {
		this.deviceModelCd = deviceModelCd;
	}

	public String getDeviceId() {
		return deviceId;
	}

	public void setDeviceId(String deviceId) {
		this.deviceId = deviceId;
	}

	public String getNetworkType() {
		return networkType;
	}

	public void setNetworkType(String networkType) {
		this.networkType = networkType;
	}

	public Integer getUpdLimitCnt() {
		return updLimitCnt;
	}

	public void setUpdLimitCnt(Integer updLimitCnt) {
		this.updLimitCnt = updLimitCnt;
	}

	public MemberInfo getMemberInfo() {
		return memberInfo;
	}

	public void setMemberInfo(MemberInfo memberInfo) {
		this.memberInfo = memberInfo;
	}

	public String getMemberType() {
		return memberType;
	}

	public void setMemberType(String memberType) {
		this.memberType = memberType;
	}

	public String getOperator() {
		return operator;
	}

	public void setOperator(String operator) {
		this.operator = operator;
	}

}
