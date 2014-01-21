package com.skplanet.storeplatform.sac.client.member.vo.user;

import java.util.List;

import com.skplanet.storeplatform.framework.core.common.vo.CommonInfo;
import com.skplanet.storeplatform.sac.client.member.vo.common.UserExtraInfo;

/**
 * [REQUEST] 회원 부가정보 등록/수정
 * 
 * Updated on : 2014. 1. 6. Updated by : 강신완, 부르칸.
 */
public class UserExtraInfoReq extends CommonInfo {

	private static final long serialVersionUID = 1L;

	/* 사용자 키 */
	private String userKey;

	/* 사용자 부가정보 관리 항목 */
	private List<UserExtraInfo> addInfoList;

	public String getUserKey() {
		return this.userKey;
	}

	public void setUserKey(String userKey) {
		this.userKey = userKey;
	}

	public List<UserExtraInfo> getAddInfoList() {
		return this.addInfoList;
	}

	public void setAddInfoList(List<UserExtraInfo> addInfoList) {
		this.addInfoList = addInfoList;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

}
