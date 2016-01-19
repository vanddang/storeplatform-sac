package com.skplanet.storeplatform.sac.client.member.vo.user;

import com.skplanet.storeplatform.framework.core.common.vo.CommonInfo;
import org.codehaus.jackson.map.annotate.JsonSerialize;
import org.codehaus.jackson.map.annotate.JsonSerialize.Inclusion;

/**
 * [RESPONSE] 회원 Market PIN 확인
 * 
 * Updated on : 2015. 1. 7. Updated by : 임근대, SKP.
 */
@JsonSerialize(include = Inclusion.NON_NULL)
public class CheckUserMarketPinRes extends CommonInfo {

	private static final long serialVersionUID = 1L;

	/** 사용자 고유키 */
	private String userKey;

	/** 실패 건수 */
	private Integer failCnt;

	public CheckUserMarketPinRes() {}

	public CheckUserMarketPinRes(String userKey, Integer failCnt) {
		this.userKey = userKey;
		this.failCnt = failCnt;
	}

	public String getUserKey() {
		return this.userKey;
	}

	public void setUserKey(String userKey) {
		this.userKey = userKey;
	}

	public Integer getFailCnt() {
		return failCnt;
	}

	public void setFailCnt(Integer failCnt) {
		this.failCnt = failCnt;
	}
}
