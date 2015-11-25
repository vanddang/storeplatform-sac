package com.skplanet.storeplatform.sac.client.internal.member.user.vo;

import java.io.Serializable;

import com.skplanet.storeplatform.framework.core.common.vo.CommonInfo;

/**
 * 회원 등급 조회 [REQUEST].
 * 
 * Updated on : 2014. 7. 11. Updated by : Rejoice, Burkhan
 */
public class SearchUserGradeSacReq extends CommonInfo implements Serializable {

	private static final long serialVersionUID = 1L;

	/** 회원 키. */
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
