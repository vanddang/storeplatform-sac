package com.skplanet.storeplatform.member.client.user.sci.vo;

import java.util.List;

import com.skplanet.storeplatform.framework.core.common.vo.CommonInfo;
import com.skplanet.storeplatform.member.client.common.vo.CommonRequest;

/**
 * 단말 부가정보 등록/수정 Value Object.
 * 
 * Updated on : 2015. 5. 12. Updated by : Rejoice, Burkhan
 */
public class UpdateDeviceManagementRequest extends CommonInfo {

	/** The Constant serial Version UID. */
	private static final long serialVersionUID = 1L;
	/** 공통 요청 Value Object. */
	private CommonRequest commonRequest;
	/** 사용자 Key. */
	private String userKey;
	/** 휴대기기 Key. */
	private String deviceKey;
	/** 기기 부가속성 및 관리항목(기기상세) 정보 Value Object List. */
	private List<UserMbrDeviceDetail> userMbrDeviceDetail;
	/**
	 * 휴면계정유무.
	 */
	private String isDormant;

	/**
	 * @return the commonRequest
	 */
	public CommonRequest getCommonRequest() {
		return this.commonRequest;
	}

	/**
	 * @param commonRequest
	 *            the commonRequest to set
	 */
	public void setCommonRequest(CommonRequest commonRequest) {
		this.commonRequest = commonRequest;
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

	/**
	 * @return the userMbrDeviceDetail
	 */
	public List<UserMbrDeviceDetail> getUserMbrDeviceDetail() {
		return this.userMbrDeviceDetail;
	}

	/**
	 * @param userMbrDeviceDetail
	 *            the userMbrDeviceDetail to set
	 */
	public void setUserMbrDeviceDetail(List<UserMbrDeviceDetail> userMbrDeviceDetail) {
		this.userMbrDeviceDetail = userMbrDeviceDetail;
	}

	/**
	 * @return isDormant
	 */
	public String getIsDormant() {
		return this.isDormant;
	}

	/**
	 * @param isDormant
	 *            String
	 */
	public void setIsDormant(String isDormant) {
		this.isDormant = isDormant;
	}

}
