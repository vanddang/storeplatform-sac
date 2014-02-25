/**
 * 
 */
package com.skplanet.storeplatform.sac.client.member.vo.user;

import java.util.Map;

import org.codehaus.jackson.map.annotate.JsonSerialize;
import org.codehaus.jackson.map.annotate.JsonSerialize.Inclusion;

import com.skplanet.storeplatform.framework.core.common.vo.CommonInfo;

/**
 * [RESPONSE]userKey 목록을 이용하여 회원정보 목록조회.
 * 
 * Updated on : 2014. 2. 24. Updated by : 김다슬, 인크로스.
 */
@JsonSerialize(include = Inclusion.NON_NULL)
public class SearchUserRes extends CommonInfo {
	private static final long serialVersionUID = 1L;

	/**
	 * String userKey : 회원키. UserInfo회원 정보.
	 */
	private Map<String, UserInfoByUserKey> userInfo;

	/**
	 * @return the userInfo
	 */
	public Map<String, UserInfoByUserKey> getUserInfo() {
		return this.userInfo;
	}

	/**
	 * @param userInfo
	 *            the userInfo to set
	 */
	public void setUserInfo(Map<String, UserInfoByUserKey> userInfo) {
		this.userInfo = userInfo;
	}

}
