/**
 * 
 */
package com.skplanet.storeplatform.member.client.user.sci.vo;

import com.skplanet.storeplatform.framework.core.common.vo.CommonInfo;
import com.skplanet.storeplatform.member.client.common.vo.CommonResponse;

/**
 * 휴대기기 설정 정보 조회 응답 Value Object.
 * 
 * Updated on : 2014. 10. 30. Updated by : Rejoice, Burkhan
 */
public class SearchDeviceSetInfoResponse extends CommonInfo {
	/** The Constant serial Version UID. */
	private static final long serialVersionUID = 1L;

	/** 공통 응답 Value Object. */
	private CommonResponse commonResponse;

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

}
