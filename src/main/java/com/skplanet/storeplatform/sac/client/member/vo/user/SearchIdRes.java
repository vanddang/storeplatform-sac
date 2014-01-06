package com.skplanet.storeplatform.sac.client.member.vo.user;

import com.skplanet.storeplatform.framework.core.common.vo.CommonInfo;

/**
 * [RESPONSE] ID 찾기
 * 
 * Updated on : 2014. 1. 6. Updated by : 강신완, 부르칸.
 */
public class SearchIdRes extends CommonInfo {

	private static final long serialVersionUID = 1L;
	/* 사용자 ID, 모바일 회원이거나 Tstore 회원이 아닐경우 null */
	private String userId;

	public String getUserId() {
		return this.userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

}
