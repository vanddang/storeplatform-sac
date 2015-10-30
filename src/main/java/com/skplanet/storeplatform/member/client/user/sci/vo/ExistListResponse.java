package com.skplanet.storeplatform.member.client.user.sci.vo;

import java.util.List;

import com.skplanet.storeplatform.framework.core.common.vo.CommonInfo;
import com.skplanet.storeplatform.member.client.common.vo.CommonResponse;

/**
 * 회원 가입여부 리스트 조회 응답 Value Object
 * 
 * Updated on : 2014. 12. 12. Updated by : Rejoice, Burkhan
 */
public class ExistListResponse extends CommonInfo {

	/** The Constant serial Version UID. */
	private static final long serialVersionUID = 1L;

	/** 공통 응답 Value Object. */
	private CommonResponse commonResponse;

	/** 휴대기기 ID 리스트. */
	private List<UserMbrDevice> deviceIdList;

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
	 * @return the deviceIdList
	 */
	public List<UserMbrDevice> getDeviceIdList() {
		return this.deviceIdList;
	}

	/**
	 * @param deviceIdList
	 *            the deviceIdList to set
	 */
	public void setDeviceIdList(List<UserMbrDevice> deviceIdList) {
		this.deviceIdList = deviceIdList;
	}

}
