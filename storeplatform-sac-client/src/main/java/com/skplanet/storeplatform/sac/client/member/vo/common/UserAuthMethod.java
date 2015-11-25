package com.skplanet.storeplatform.sac.client.member.vo.common;

import com.skplanet.storeplatform.framework.core.common.vo.CommonInfo;

/**
 * 변동성 회원의 추가 인증 수단
 * 
 * Updated on : 2014. 3. 11. Updated by : 반범진, 지티소프트.
 */
public class UserAuthMethod extends CommonInfo {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * 인증한 userId.
	 */
	private String userId;

	/**
	 * 실명인증여부(Y/N).
	 */
	private String isRealName;

	/**
	 * @return userId
	 */
	public String getUserId() {
		return this.userId;
	}

	/**
	 * @param userId
	 *            String
	 */
	public void setUserId(String userId) {
		this.userId = userId;
	}

	/**
	 * @return isRealName
	 */
	public String getIsRealName() {
		return this.isRealName;
	}

	/**
	 * @param isRealName
	 *            String
	 */
	public void setIsRealName(String isRealName) {
		this.isRealName = isRealName;
	}

}
