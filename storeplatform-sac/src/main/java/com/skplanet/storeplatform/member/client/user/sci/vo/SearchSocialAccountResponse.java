package com.skplanet.storeplatform.member.client.user.sci.vo;

import java.util.List;

import com.skplanet.storeplatform.framework.core.common.vo.CommonInfo;
import com.skplanet.storeplatform.member.client.common.vo.CommonResponse;

/**
 * 소셜계정이력 조회 응답 Value Object.
 * 
 * Updated on : 2015. 6. 9. Updated by : Rejoice, Burkhan
 */
public class SearchSocialAccountResponse extends CommonInfo {

	/** The Constant serial Version UID. */
	private static final long serialVersionUID = 1L;

	/** 공통 요청 Value Object. */
	private CommonResponse commonResponse;

	private String userKey;
	private List<SocialAccount> socialAccountList;

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
	 * @return the socialAccountList
	 */
	public List<SocialAccount> getSocialAccountList() {
		return this.socialAccountList;
	}

	/**
	 * @param socialAccountList
	 *            the socialAccountList to set
	 */
	public void setSocialAccountList(List<SocialAccount> socialAccountList) {
		this.socialAccountList = socialAccountList;
	}

}
