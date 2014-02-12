package com.skplanet.storeplatform.sac.client.member.vo.user;

import java.util.List;

import org.codehaus.jackson.map.annotate.JsonSerialize;
import org.codehaus.jackson.map.annotate.JsonSerialize.Inclusion;

import com.skplanet.storeplatform.framework.core.common.vo.CommonInfo;
import com.skplanet.storeplatform.sac.client.member.vo.common.UserExtraInfo;

/**
 * [RESPONSE] 회원 부가 정보 등록/수정
 * 
 * Updated on : 2014. 1. 6. Updated by : 강신완, 부르칸.
 */
@JsonSerialize(include = Inclusion.NON_NULL)
public class UserExtraInfoRes extends CommonInfo {

	private static final long serialVersionUID = 1L;

	/* 사용자 고유 Key */
	private String userKey;

	/* 부가정보 리스트 */
	private List<UserExtraInfo> userExtraInfoList;

	public String getUserKey() {
		return this.userKey;
	}

	public void setUserKey(String userKey) {
		this.userKey = userKey;
	}

	public List<UserExtraInfo> getUserExtraInfoList() {
		return this.userExtraInfoList;
	}

	public void setUserExtraInfoList(List<UserExtraInfo> userExtraInfoList) {
		this.userExtraInfoList = userExtraInfoList;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

}
