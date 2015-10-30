package com.skplanet.storeplatform.member.client.user.sci.vo;

import com.skplanet.storeplatform.framework.core.common.vo.CommonInfo;
import com.skplanet.storeplatform.member.client.common.vo.CommonRequest;

/**
 * 휴대기기 설정정보 이관 기능 요청 Value Object
 * 
 * Updated on : 2014. 11. 18. Updated by : Rejoice, Burkhan
 */
public class TransferDeviceSetInfoRequest extends CommonInfo {

	/** The Constant serial Version UID. */
	private static final long serialVersionUID = 1L;

	/** 공통 요청 Value Object. */
	private CommonRequest commonRequest;

	/** 사용자 key. */
	private String userKey;
	/** 기기 key. */
	private String deviceKey;
	/** 이전 사용자 key. */
	private String preUserKey;
	/** 이전 기기 key. */
	private String preDeviceKey;
	/** 사용자 휴면계정유무. */
	private String isDormant;
	/** 이전 사용자 휴면계정유무. */
	private String preIsDormant;

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
	 * @return the preUserKey
	 */
	public String getPreUserKey() {
		return this.preUserKey;
	}

	/**
	 * @param preUserKey
	 *            the preUserKey to set
	 */
	public void setPreUserKey(String preUserKey) {
		this.preUserKey = preUserKey;
	}

	/**
	 * @return the preDeviceKey
	 */
	public String getPreDeviceKey() {
		return this.preDeviceKey;
	}

	/**
	 * @param preDeviceKey
	 *            the preDeviceKey to set
	 */
	public void setPreDeviceKey(String preDeviceKey) {
		this.preDeviceKey = preDeviceKey;
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

	/**
	 * @return preIsDormant
	 */
	public String getPreIsDormant() {
		return this.preIsDormant;
	}

	/**
	 * @param preIsDormant
	 *            String
	 */
	public void setPreIsDormant(String preIsDormant) {
		this.preIsDormant = preIsDormant;
	}

}
