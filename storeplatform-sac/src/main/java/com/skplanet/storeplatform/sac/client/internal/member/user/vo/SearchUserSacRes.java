/**
 * 
 */
package com.skplanet.storeplatform.sac.client.internal.member.user.vo;

import java.util.Map;

import org.codehaus.jackson.map.annotate.JsonSerialize;
import org.codehaus.jackson.map.annotate.JsonSerialize.Inclusion;

import com.skplanet.storeplatform.framework.core.common.vo.CommonInfo;

/**
 * [RESPONSE] UserKey를 이용한 회원정보 조회.
 * 
 * Updated on : 2014. 2. 12. Updated by : 김다슬, 인크로스.
 */
@JsonSerialize(include = Inclusion.NON_NULL)
public class SearchUserSacRes extends CommonInfo {

	private static final long serialVersionUID = 1L;

	/**
	 * String userKey : 회원키. UserInfo회원 정보.
	 */
	private Map<String, UserInfoSac> userInfo;

	/**
	 * @return the userInfo
	 */
	public Map<String, UserInfoSac> getUserInfo() {
		return this.userInfo;
	}

	/**
	 * @param userInfo
	 *            the userInfo to set
	 */
	public void setUserInfo(Map<String, UserInfoSac> userInfo) {
		this.userInfo = userInfo;
	}

}
