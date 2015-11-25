package com.skplanet.storeplatform.sac.client.member.vo.user;

import org.hibernate.validator.constraints.NotEmpty;

import com.skplanet.storeplatform.framework.core.common.vo.CommonInfo;

/**
 * [REQUEST]회원 상품권 충전 정보 조회.
 * 
 * Updated on : 2015. 11. 11. Updated by : 반범진.
 */
public class SearchGiftChargeInfoSacReq extends CommonInfo {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * 사용자 Key.
	 */
	@NotEmpty
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
