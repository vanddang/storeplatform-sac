package com.skplanet.storeplatform.sac.client.member.vo.user;

import com.skplanet.storeplatform.framework.core.common.vo.CommonInfo;

/**
 * [REQUEST] Store의 약관에 대한 동의 목록 조회하는 기능을 제공한다.
 * 
 * Updated on : 2014. 1. 6. Updated by : 강신완, 부르칸.
 */
public class ListTermsAgreementSacReq extends CommonInfo {

	private static final long serialVersionUID = 1L;

	private String userKey;

	public String getUserKey() {
		return this.userKey;
	}

	public void setUserKey(String userKey) {
		this.userKey = userKey;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

}
