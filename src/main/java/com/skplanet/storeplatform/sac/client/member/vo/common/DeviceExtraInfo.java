package com.skplanet.storeplatform.sac.client.member.vo.common;

import org.codehaus.jackson.map.annotate.JsonSerialize;
import org.codehaus.jackson.map.annotate.JsonSerialize.Inclusion;

import com.skplanet.storeplatform.framework.core.common.vo.CommonInfo;

/**
 * 휴대기기 부가정보.
 * 
 * Updated on : 2014. 1. 6. Updated by : 반범진, 지티소프트.
 */
@JsonSerialize(include = Inclusion.NON_NULL)
public class DeviceExtraInfo extends CommonInfo {

	private static final long serialVersionUID = 1L;

	/**
	 * 부가정보 코드.
	 */
	private String extraProfile;

	/**
	 * 부가정보 값.
	 */
	private String extraProfileValue;

	/**
	 * 테넌트 아이디.
	 */
	private String tenentId;

	/**
	 * 사용자 키.
	 */
	private String userKey;

	/**
	 * 휴대기기 키.
	 */
	private String deviceKey;

	/** 현재 일시. */
	private String nowDate;

	/** 등록일시 + 7일. */
	private String regDatePlus7;

	/** 수정일시 + 7 일. */
	private String updateDatePlue7;

	/**
	 * @return extraProfile
	 */
	public String getExtraProfile() {
		return this.extraProfile;
	}

	/**
	 * @param extraProfile
	 *            String
	 */
	public void setExtraProfile(String extraProfile) {
		this.extraProfile = extraProfile;
	}

	/**
	 * @return extraProfileValue
	 */
	public String getExtraProfileValue() {
		return this.extraProfileValue;
	}

	/**
	 * @param extraProfileValue
	 *            String
	 */
	public void setExtraProfileValue(String extraProfileValue) {
		this.extraProfileValue = extraProfileValue;
	}

	/**
	 * @return tenentId
	 */
	public String getTenentId() {
		return this.tenentId;
	}

	/**
	 * @param tenentId
	 *            String
	 */
	public void setTenentId(String tenentId) {
		this.tenentId = tenentId;
	}

	/**
	 * @return userKey
	 */
	public String getUserKey() {
		return this.userKey;
	}

	/**
	 * @param userKey
	 *            String
	 */
	public void setUserKey(String userKey) {
		this.userKey = userKey;
	}

	/**
	 * @return deviceKey
	 */
	public String getDeviceKey() {
		return this.deviceKey;
	}

	/**
	 * @param deviceKey
	 *            String
	 */
	public void setDeviceKey(String deviceKey) {
		this.deviceKey = deviceKey;
	}

	/**
	 * @return the nowDate
	 */
	public String getNowDate() {
		return this.nowDate;
	}

	/**
	 * @param nowDate
	 *            the nowDate to set
	 */
	public void setNowDate(String nowDate) {
		this.nowDate = nowDate;
	}

	/**
	 * @return the regDatePlus7
	 */
	public String getRegDatePlus7() {
		return this.regDatePlus7;
	}

	/**
	 * @param regDatePlus7
	 *            the regDatePlus7 to set
	 */
	public void setRegDatePlus7(String regDatePlus7) {
		this.regDatePlus7 = regDatePlus7;
	}

	/**
	 * @return the updateDatePlue7
	 */
	public String getUpdateDatePlue7() {
		return this.updateDatePlue7;
	}

	/**
	 * @param updateDatePlue7
	 *            the updateDatePlue7 to set
	 */
	public void setUpdateDatePlue7(String updateDatePlue7) {
		this.updateDatePlue7 = updateDatePlue7;
	}

}
