package com.skplanet.storeplatform.sac.client.member.vo.user;

import org.codehaus.jackson.map.annotate.JsonSerialize;
import org.codehaus.jackson.map.annotate.JsonSerialize.Inclusion;

import com.skplanet.storeplatform.framework.core.common.vo.CommonInfo;

/**
 * 2.1.57. 실명 인증 정보 초기화 [RESPONSE].
 * 
 * Updated on : 2015. 04. 09. Updated by : 윤보영, 카레즈
 */
@JsonSerialize(include = Inclusion.NON_NULL)
public class InitRealNameRes extends CommonInfo {

	private static final long serialVersionUID = 1L;

	/**
	 * 사용자 Key.
	 */
	private String userKey;

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

}
