package com.skplanet.storeplatform.sac.client.internal.member.user.vo;

import java.util.Map;

import com.skplanet.storeplatform.framework.core.common.vo.CommonInfo;

/**
 * 2.1.14. 소셜계정 등록 회원 정보 조회 [RESPONSE].
 * 
 * Updated on : 2015. 6. 11. Updated by : Rejoice, Burkhan
 */
public class SearchSocialAccountSacRes extends CommonInfo {

	private static final long serialVersionUID = 1L;

	/** 소셜 계정 회원 정보 객체. */
	private Map<String, SocialUserInfoSac> socialUserInfo;

	/**
	 * @return the socialUserInfo
	 */
	public Map<String, SocialUserInfoSac> getSocialUserInfo() {
		return this.socialUserInfo;
	}

	/**
	 * @param socialUserInfo
	 *            the socialUserInfo to set
	 */
	public void setSocialUserInfo(Map<String, SocialUserInfoSac> socialUserInfo) {
		this.socialUserInfo = socialUserInfo;
	}

}
