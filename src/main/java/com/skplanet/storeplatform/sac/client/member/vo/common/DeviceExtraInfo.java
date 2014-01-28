package com.skplanet.storeplatform.sac.client.member.vo.common;

import org.codehaus.jackson.map.annotate.JsonSerialize;
import org.codehaus.jackson.map.annotate.JsonSerialize.Inclusion;

import com.skplanet.storeplatform.framework.core.common.vo.CommonInfo;

/**
 * 휴대기기 부가정보
 * 
 * Updated on : 2014. 1. 6. Updated by : 반범진, 지티소프트.
 */
@JsonSerialize(include = Inclusion.NON_DEFAULT)
public class DeviceExtraInfo extends CommonInfo {

	private static final long serialVersionUID = 1L;

	/**
	 * 부가정보 코드
	 */
	private String extraProfile = "";

	/**
	 * 부가정보 값
	 */
	private String extraProfileValue = "";

	/**
	 * 테넌트 아이디
	 */
	private String tenentId;

	/**
	 * 사용자 키
	 */
	private String userKey;

	/**
	 * 휴대기기 키
	 */
	private String deviceKey;

	public String getExtraProfile() {
		return this.extraProfile;
	}

	public void setExtraProfile(String extraProfile) {
		this.extraProfile = extraProfile;
	}

	public String getExtraProfileValue() {
		return this.extraProfileValue;
	}

	public void setExtraProfileValue(String extraProfileValue) {
		this.extraProfileValue = extraProfileValue;
	}

	public String getTenentId() {
		return this.tenentId;
	}

	public void setTenentId(String tenentId) {
		this.tenentId = tenentId;
	}

	public String getUserKey() {
		return this.userKey;
	}

	public void setUserKey(String userKey) {
		this.userKey = userKey;
	}

	public String getDeviceKey() {
		return this.deviceKey;
	}

	public void setDeviceKey(String deviceKey) {
		this.deviceKey = deviceKey;
	}

}
