package com.skplanet.storeplatform.member.client.user.sci.vo;

import com.skplanet.storeplatform.framework.core.common.vo.CommonInfo;
import com.skplanet.storeplatform.member.client.common.vo.CommonRequest;

/**
 * 소셜계정 이력 등록 요청 Value Object.
 * 
 * Updated on : 2015. 6. 8. Updated by : Rejoice, Burkhan
 */
public class CreateSocialAccountRequest extends CommonInfo {

	/** The Constant serial Version UID. */
	private static final long serialVersionUID = 1L;

	/** 공통 요청 Value Object. */
	private CommonRequest commonRequest;

	/** 사용자 Key. */
	private String userKey;
	/** 소셜계정타입. */
	private String socialAcctType;
	/** 소셜계정 내부 아이디. */
	private String socialAcctIntId;

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
	 * @return the socialAcctType
	 */
	public String getSocialAcctType() {
		return this.socialAcctType;
	}

	/**
	 * @param socialAcctType
	 *            the socialAcctType to set
	 */
	public void setSocialAcctType(String socialAcctType) {
		this.socialAcctType = socialAcctType;
	}

	/**
	 * @return the socialAcctIntId
	 */
	public String getSocialAcctIntId() {
		return this.socialAcctIntId;
	}

	/**
	 * @param socialAcctIntId
	 *            the socialAcctIntId to set
	 */
	public void setSocialAcctIntId(String socialAcctIntId) {
		this.socialAcctIntId = socialAcctIntId;
	}

}
