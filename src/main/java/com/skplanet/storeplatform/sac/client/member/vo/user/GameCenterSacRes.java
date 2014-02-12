package com.skplanet.storeplatform.sac.client.member.vo.user;

import com.skplanet.storeplatform.framework.core.common.vo.CommonInfo;

/**
 * [RESPONSE] 게임센터 연동 정보.
 * 
 * Updated on : 2014. 2. 10. Updated by : 반범진, 지티소프트.
 */
public class GameCenterSacRes extends CommonInfo {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * 내부_사용자회원_번호.
	 */
	private String userKey;

	/**
	 * @return userKey
	 */
	public String getUserKey() {
		return this.userKey;
	}

	/**
	 * @param userKey
	 *            String
	 */
	public void setUserKey(String userKey) {
		this.userKey = userKey;
	}

}
