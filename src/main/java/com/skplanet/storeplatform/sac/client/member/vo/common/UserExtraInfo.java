package com.skplanet.storeplatform.sac.client.member.vo.common;

import org.codehaus.jackson.map.annotate.JsonSerialize;
import org.codehaus.jackson.map.annotate.JsonSerialize.Inclusion;

import com.skplanet.storeplatform.framework.core.common.vo.CommonInfo;

/**
 * 사용자 부가정보
 * 
 * Updated on : 2014. 1. 6. Updated by : 반범진, 지티소프트.
 */

@JsonSerialize(include = Inclusion.NON_NULL)
public class UserExtraInfo extends CommonInfo {

	private static final long serialVersionUID = 1L;

	/**
	 * 관리항목 코드
	 */
	private String extraProfileCode;

	/*
	 * 관리항목 값
	 */
	private String extraProfileValue;

	/*
	 * Tenant ID
	 */
	private String tenantId;

	/*
	 * 내부 회원 키
	 */
	private String userKey;

	public String getExtraProfileCode() {
		return this.extraProfileCode;
	}

	public void setExtraProfileCode(String extraProfileCode) {
		this.extraProfileCode = extraProfileCode;
	}

	public String getExtraProfileValue() {
		return this.extraProfileValue;
	}

	public void setExtraProfileValue(String extraProfileValue) {
		this.extraProfileValue = extraProfileValue;
	}

	public String getTenantId() {
		return this.tenantId;
	}

	public void setTenantId(String tenantId) {
		this.tenantId = tenantId;
	}

	public String getUserKey() {
		return this.userKey;
	}

	public void setUserKey(String userKey) {
		this.userKey = userKey;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

}
