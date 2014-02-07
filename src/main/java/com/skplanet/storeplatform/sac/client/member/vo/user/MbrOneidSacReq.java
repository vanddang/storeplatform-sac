package com.skplanet.storeplatform.sac.client.member.vo.user;

import java.io.Serializable;

import com.skplanet.storeplatform.framework.core.common.vo.CommonInfo;

/**
 * [REQUEST] OneId 회원정보
 * 
 * Updated on : 2014. 2. 7. Updated by : 강신완. 부르칸.
 */
public class MbrOneidSacReq extends CommonInfo implements Serializable {

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
