package com.skplanet.storeplatform.member.client.user.sci.vo;

import com.skplanet.storeplatform.framework.core.common.vo.CommonInfo;
import com.skplanet.storeplatform.member.client.common.vo.CommonResponse;

/**
 * 단말 부가정보 등록/수정 Value Object.
 * 
 * Updated on : 2015. 5. 12. Updated by : Rejoice, Burkhan
 */
public class UpdateDeviceManagementResponse extends CommonInfo {

	/** The Constant serial Version UID. */
	private static final long serialVersionUID = 1L;
	/** 공통 응답 Value Object. */
	private CommonResponse commonResponse;
	/** 사용자 Key. */
	private String userKey;
	/** 휴대기기 Key. */
	private String deviceKey;

	/**
	 * @return the commonResponse
	 */
	public CommonResponse getCommonResponse() {
		return this.commonResponse;
	}

	/**
	 * @param commonResponse
	 *            the commonResponse to set
	 */
	public void setCommonResponse(CommonResponse commonResponse) {
		this.commonResponse = commonResponse;
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

}
