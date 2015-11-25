package com.skplanet.storeplatform.sac.client.member.vo.user;

import org.codehaus.jackson.map.annotate.JsonSerialize;
import org.codehaus.jackson.map.annotate.JsonSerialize.Inclusion;

import com.skplanet.storeplatform.framework.core.common.vo.CommonInfo;

/**
 * [REQUEST] 약관 목록 조회
 * 
 * Updated on : 2014. 1. 27. Updated by : 강신완, 부르칸.
 */
@JsonSerialize(include = Inclusion.NON_NULL)
public class SearchAgreementReq extends CommonInfo {

	private static final long serialVersionUID = 1L;

	/* 사용자 키 */
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
