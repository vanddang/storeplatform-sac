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
import com.skplanet.storeplatform.member.client.common.vo.CommonRequest;

/**
 * 분리보관예정 사용자 조회 요청 Value Object.
 */
public class SearchDeActivateUserRequest extends CommonInfo implements Serializable {
	/** The Constant serial Version UID. */
	private static final long serialVersionUID = 1L;

	/** 공통 요청 Value Object. */
	private CommonRequest commonRequest;

	/**
	 * 사용자키.
	 */
	private String userKey;

	/**
	 * 분리보관예정 몇주전.
	 */
	private String weekAgo;

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

	/**
	 * @return weekAgo
	 */
	public String getWeekAgo() {
		return this.weekAgo;
	}

	/**
	 * @param weekAgo
	 *            String
	 */
	public void setWeekAgo(String weekAgo) {
		this.weekAgo = weekAgo;
	}

	/**
	 * @return commonRequest
	 */
	public CommonRequest getCommonRequest() {
		return this.commonRequest;
	}

	/**
	 * @param commonRequest
	 *            CommonRequest
	 */
	public void setCommonRequest(CommonRequest commonRequest) {
		this.commonRequest = commonRequest;
	}

}
