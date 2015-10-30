package com.skplanet.storeplatform.sac.client.internal.member.user.vo;

import java.util.List;

import com.skplanet.storeplatform.framework.core.common.vo.CommonInfo;

/**
 * 소셜계정 회원 정보.
 * 
 * Updated on : 2015. 6. 11. Updated by : Rejoice, Burkhan
 */
public class SocialUserInfoSac extends CommonInfo {

	private static final long serialVersionUID = 1L;

	/** tenant Id */
	private String tenantId;

	/** 회원키. */
	private String userKey;

	/** 사용자 구분 코드. */
	private String userType;

	/** 사용자 ID. */
	private String userId;

	/** 디바이스 키 리스트. */
	private List<String> deviceKeyList;

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
	 * @return the userType
	 */
	public String getUserType() {
		return this.userType;
	}

	/**
	 * @param userType
	 *            the userType to set
	 */
	public void setUserType(String userType) {
		this.userType = userType;
	}

	/**
	 * @return the userId
	 */
	public String getUserId() {
		return this.userId;
	}

	/**
	 * @param userId
	 *            the userId to set
	 */
	public void setUserId(String userId) {
		this.userId = userId;
	}

	/**
	 * @return the deviceKeyList
	 */
	public List<String> getDeviceKeyList() {
		return this.deviceKeyList;
	}

	/**
	 * @param deviceKeyList
	 *            the deviceKeyList to set
	 */
	public void setDeviceKeyList(List<String> deviceKeyList) {
		this.deviceKeyList = deviceKeyList;
	}

}
