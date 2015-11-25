package com.skplanet.storeplatform.sac.client.member.vo.user;

import org.hibernate.validator.constraints.NotEmpty;

import com.skplanet.storeplatform.framework.core.common.vo.CommonInfo;

/**
 * [REQUEST] 회원 계정 잠금
 * 
 * Updated on : 2014. 1. 3. Updated by : 심대진, 다모아 솔루션.
 */
public class LockAccountSacReq extends CommonInfo {

	private static final long serialVersionUID = 1L;

	@NotEmpty
	private String userId = "";

	/**
	 * @return String : userId
	 */
	public String getUserId() {
		return this.userId;
	}

	/**
	 * @param userId
	 *            String : the userId to set
	 */
	public void setUserId(String userId) {
		this.userId = userId;
	}

}
