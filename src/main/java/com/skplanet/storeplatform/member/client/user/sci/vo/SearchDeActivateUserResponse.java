package com.skplanet.storeplatform.member.client.user.sci.vo;

/*
 * Copyright (c) 2013 SK planet.
 * All right reserved.
 * 
 * This software is the confidential and proprietary information of SK planet.
 * You shall not disclose such Confidential Information and
 * shall use it only in accordance with the terms of the license agreement
 * you entered into with SK planet.
 */
import java.io.Serializable;

import com.skplanet.storeplatform.framework.core.common.vo.CommonInfo;
import com.skplanet.storeplatform.member.client.common.vo.CommonResponse;

/**
 * 분리보관예정 사용자 조회 응답 Value Object.
 */
public class SearchDeActivateUserResponse extends CommonInfo implements Serializable {
	/** The Constant serial Version UID. */
	private static final long serialVersionUID = 1L;

	/** 공통 응답 Value Object. */
	private CommonResponse commonResponse;

	/** 사용자 정보 Value Object. */
	private UserMbr userMbr;

	/**
	 * 사용자키.
	 */
	private String userKey;

	/**
	 * @return commonResponse
	 */
	public CommonResponse getCommonResponse() {
		return this.commonResponse;
	}

	/**
	 * @param commonResponse
	 *            CommonResponse
	 */
	public void setCommonResponse(CommonResponse commonResponse) {
		this.commonResponse = commonResponse;
	}

	/**
	 * @return userMbr
	 */
	public UserMbr getUserMbr() {
		return this.userMbr;
	}

	/**
	 * @param userMbr
	 *            UserMbr
	 */
	public void setUserMbr(UserMbr userMbr) {
		this.userMbr = userMbr;
	}

	/**
	 * @return userKey
	 */
	public String getUserKey() {
		return this.userKey;
	}

	/**
	 * @param userKey
	 *            String
	 */
	public void setUserKey(String userKey) {
		this.userKey = userKey;
	}

}
