package com.skplanet.storeplatform.sac.client.member.vo.common;

import java.util.List;

import org.codehaus.jackson.map.annotate.JsonSerialize;
import org.codehaus.jackson.map.annotate.JsonSerialize.Inclusion;

import com.skplanet.storeplatform.framework.core.common.vo.CommonInfo;

/**
 * 소셜계정 이력 정보.
 * 
 * Updated on : 2015. 6. 10. Updated by : Rejoice, Burkhan
 */
@JsonSerialize(include = Inclusion.NON_NULL)
public class SocialAccountInfo extends CommonInfo {

	private static final long serialVersionUID = 1L;

	/** 테넌트 아이디. */
	private String tenantId;
	/** 사용자 key. */
	private String userKey;
	/** 사용자 Id. */
	private String userId;
	/** 사용자 구분코드. */
	private String userType;
	/** 기기 Key 리스트. */
	private List<DeviceInfo> deviceKeyList;

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
	 * @return the deviceKeyList
	 */
	public List<DeviceInfo> getDeviceKeyList() {
		return this.deviceKeyList;
	}

	/**
	 * @param deviceKeyList
	 *            the deviceKeyList to set
	 */
	public void setDeviceKeyList(List<DeviceInfo> deviceKeyList) {
		this.deviceKeyList = deviceKeyList;
	}

}
