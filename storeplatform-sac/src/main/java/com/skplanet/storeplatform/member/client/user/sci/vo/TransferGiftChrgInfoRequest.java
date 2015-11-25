package com.skplanet.storeplatform.member.client.user.sci.vo;

import com.skplanet.storeplatform.framework.core.common.vo.CommonInfo;
import com.skplanet.storeplatform.member.client.common.vo.CommonRequest;

/**
 * 사용자 상품권 충전소 정보 이관 기능 요청 Value Object
 * 
 * Updated on : 2015. 11. 02. Updated by : 최진호, 보고지티.
 */
public class TransferGiftChrgInfoRequest extends CommonInfo {

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
