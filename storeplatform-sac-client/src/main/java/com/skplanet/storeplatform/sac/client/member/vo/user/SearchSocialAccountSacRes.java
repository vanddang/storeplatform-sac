package com.skplanet.storeplatform.sac.client.member.vo.user;

import java.util.List;

import org.codehaus.jackson.map.annotate.JsonSerialize;
import org.codehaus.jackson.map.annotate.JsonSerialize.Inclusion;

import com.skplanet.storeplatform.framework.core.common.vo.CommonInfo;
import com.skplanet.storeplatform.sac.client.member.vo.common.SocialAccountInfo;

/**
 * 2.1.59. 소셜 계정 등록 회원 리스트 [RESPONSE].
 * 
 * Updated on : 2015. 6. 3. Updated by : Rejoice, Burkhan
 */
@JsonSerialize(include = Inclusion.NON_NULL)
public class SearchSocialAccountSacRes extends CommonInfo {

	private static final long serialVersionUID = 1L;

	/** 사용자 리스트. */
	private List<SocialAccountInfo> userList;

	/**
	 * @return the userList
	 */
	public List<SocialAccountInfo> getUserList() {
		return this.userList;
	}

	/**
	 * @param userList
	 *            the userList to set
	 */
	public void setUserList(List<SocialAccountInfo> userList) {
		this.userList = userList;
	}

}
