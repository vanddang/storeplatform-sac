package com.skplanet.storeplatform.member.client.user.sci.vo;

import com.skplanet.storeplatform.framework.core.common.vo.CommonInfo;
import com.skplanet.storeplatform.member.client.common.vo.CommonRequest;

/**
 * market pin 이관 요청 Value Object
 * 
 * Updated on : 2016. 01. 27. Updated by : 반범진.
 */
public class TransferMarketPinRequest extends CommonInfo {

	/** The Constant serial Version UID. */
	private static final long serialVersionUID = 1L;

	/** 공통 요청 Value Object. */
	private CommonRequest commonRequest;

	/** 사용자 key. */
	private String userKey;
	/** 이전 사용자 key. */
	private String preUserKey;

	/**
	 * @return the commonRequest
	 */
	public CommonRequest getCommonRequest() {
		return this.commonRequest;
	}

	/**
	 * @return the userKey
	 */
	public String getUserKey() {
		return this.userKey;
	}

	/**
	 * @return the preUserKey
	 */
	public String getPreUserKey() {
		return this.preUserKey;
	}

	/**
	 * @param commonRequest
	 *            the commonRequest to set
	 */
	public void setCommonRequest(CommonRequest commonRequest) {
		this.commonRequest = commonRequest;
	}

	/**
	 * @param userKey
	 *            the userKey to set
	 */
	public void setUserKey(String userKey) {
		this.userKey = userKey;
	}

	/**
	 * @param preUserKey
	 *            the preUserKey to set
	 */
	public void setPreUserKey(String preUserKey) {
		this.preUserKey = preUserKey;
	}
}
