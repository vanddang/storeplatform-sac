/**
 * 
 */
package com.skplanet.storeplatform.member.client.user.sci.vo;

import com.skplanet.storeplatform.framework.core.common.vo.CommonInfo;
import com.skplanet.storeplatform.member.client.common.vo.CommonResponse;

/**
 * PIN 확인 응답 Value Object.
 * 
 * Updated on : 2014. 10. 30. Updated by : Rejoice, Burkhan
 */
public class CheckDevicePinResponse extends CommonInfo {
	/** The Constant serial Version UID. */
	private static final long serialVersionUID = 1L;

	/** 공통 응답 Value Object. */
	private CommonResponse commonResponse;

	/** 기기Key. */
	private String deviceKey;
	/** 기기 ID. */
	private String deviceId;
	/** 사용자 Key. */
	private String userKey;
	/** 인증 실패횟수. */
	private String failCnt;
	/** 휴대기기 설정 정보. */
	private UserMbrDeviceSet userMbrDeviceSet;

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
	 * @return the deviceId
	 */
	public String getDeviceId() {
		return this.deviceId;
	}

	/**
	 * @param deviceId
	 *            the deviceId to set
	 */
	public void setDeviceId(String deviceId) {
		this.deviceId = deviceId;
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
	 * @return the userMbrDeviceSet
	 */
	public UserMbrDeviceSet getUserMbrDeviceSet() {
		return this.userMbrDeviceSet;
	}

	/**
	 * @param userMbrDeviceSet
	 *            the userMbrDeviceSet to set
	 */
	public void setUserMbrDeviceSet(UserMbrDeviceSet userMbrDeviceSet) {
		this.userMbrDeviceSet = userMbrDeviceSet;
	}

	/**
	 * @return the failCnt
	 */
	public String getFailCnt() {
		return this.failCnt;
	}

	/**
	 * @param failCnt
	 *            the failCnt to set
	 */
	public void setFailCnt(String failCnt) {
		this.failCnt = failCnt;
	}

}
