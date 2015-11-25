package com.skplanet.storeplatform.member.client.user.sci.vo;

import java.io.Serializable;

import com.skplanet.storeplatform.framework.core.common.vo.CommonInfo;
import com.skplanet.storeplatform.member.client.common.vo.CommonRequest;

/**
 * Class 설명
 * 
 * Updated on : 2014. 10. 2. Updated by : Rejoice, Burkhan
 */
public class SearchUserSegmentRequest extends CommonInfo implements Serializable {

	/** The Constant serial Version UID. */
	private static final long serialVersionUID = 1L;

	/**
	 * Returns the serial version UID.
	 * 
	 * @return serialVersionUID - the serial version UID
	 */
	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	/** 공통 요청 Value Object. */
	private CommonRequest commonRequest;

	/**
	 * 사용자 key값.
	 */
	private String userKey;

	/** 휴대기기 Key. */
	private String deviceKey;

	/**
	 * 휴면계정유무.
	 */
	private String isDormant;

	/**
	 * 사용자 Key를 리턴한다.
	 * 
	 * @return userKey - 사용자 Key
	 */
	public String getUserKey() {
		return this.userKey;
	}

	/**
	 * 사용자 Key를 설정한다.
	 * 
	 * @param userKey
	 *            사용자 Key
	 */
	public void setUserKey(String userKey) {
		this.userKey = userKey;
	}

	/**
	 * 사용자 휴대기기 Key를 리턴한다.
	 * 
	 * @return deviceKey - 사용자 휴대기기 Key
	 */
	public String getDeviceKey() {
		return this.deviceKey;
	}

	/**
	 * 사용자 휴대기기 Key를 설정한다.
	 * 
	 * @param deviceKey
	 *            사용자 휴대기기 Key
	 */
	public void setDeviceKey(String deviceKey) {
		this.deviceKey = deviceKey;
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
	 * 공통 요청 Value Object를 리턴한다.
	 * 
	 * @return commonRequest - 공통 요청 Value Object
	 */
	public CommonRequest getCommonRequest() {
		return this.commonRequest;
	}

	/**
	 * 공통 요청 Value Object를 설정한다.
	 * 
	 * @param commonRequest
	 *            공통 요청 Value Object
	 */
	public void setCommonRequest(CommonRequest commonRequest) {
		this.commonRequest = commonRequest;
	}
}
