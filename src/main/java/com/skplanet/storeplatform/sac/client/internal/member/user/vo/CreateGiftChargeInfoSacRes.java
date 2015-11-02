package com.skplanet.storeplatform.sac.client.internal.member.user.vo;

import com.skplanet.storeplatform.framework.core.common.vo.CommonInfo;

/**
 * SAC Internal [RESPONSE] 상품권 충전 정보 등록.
 * 
 * Updated on : 2015. 10. 28. Updated by : 윤보영.
 */
public class CreateGiftChargeInfoSacRes extends CommonInfo {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * 회원 키.
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
