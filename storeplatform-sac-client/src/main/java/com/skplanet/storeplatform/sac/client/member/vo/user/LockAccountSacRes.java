package com.skplanet.storeplatform.sac.client.member.vo.user;

import org.codehaus.jackson.map.annotate.JsonSerialize;
import org.codehaus.jackson.map.annotate.JsonSerialize.Inclusion;

import com.skplanet.storeplatform.framework.core.common.vo.CommonInfo;

/**
 * [RESPONSE] 회원 계정 잠금
 * 
 * Updated on : 2014. 1. 3. Updated by : 심대진, 다모아 솔루션.
 */
@JsonSerialize(include = Inclusion.NON_NULL)
public class LockAccountSacRes extends CommonInfo {

	private static final long serialVersionUID = 1L;

	/**
	 * 사용자 아이디.
	 */
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
