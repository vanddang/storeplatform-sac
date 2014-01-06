package com.skplanet.storeplatform.sac.client.member.vo.user;

import com.skplanet.storeplatform.framework.core.common.vo.CommonInfo;

/**
 * [RESPONSE] 회원 정보 수정
 * 
 * Updated on : 2014. 1. 6. Updated by : 심대진, 다모아 솔루션.
 */
public class ModifyRes extends CommonInfo {

	private static final long serialVersionUID = 1L;

	private String userKey;

	public String getUserKey() {
		return this.userKey;
	}

	public void setUserKey(String userKey) {
		this.userKey = userKey;
	}

}
