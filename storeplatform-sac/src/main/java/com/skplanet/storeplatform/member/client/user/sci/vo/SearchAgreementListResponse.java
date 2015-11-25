/*
 * Copyright (c) 2014 SK planet.
 * All right reserved.
 * 
 * This software is the confidential and proprietary information of SK planet.
 * You shall not disclose such Confidential Information and
 * shall use it only in accordance with the terms of the license agreement
 * you entered into with SK planet.
 */
package com.skplanet.storeplatform.member.client.user.sci.vo;

import java.io.Serializable;
import java.util.List;

import com.skplanet.storeplatform.framework.core.common.vo.CommonInfo;
import com.skplanet.storeplatform.member.client.common.util.Utils;
import com.skplanet.storeplatform.member.client.common.vo.CommonResponse;
import com.skplanet.storeplatform.member.client.common.vo.MbrClauseAgree;

/**
 * 사용자 약관동의 목록 응답 Value Object
 * 
 * Updated on : 2014. 1. 6 Updated by : wisestone_brian, wisestone
 */
public class SearchAgreementListResponse extends CommonInfo implements Serializable {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/** 공통 응답 Value Object. */
	private CommonResponse commonResponse;

	/** 사용자 약관동의 Value Object List. */
	private List<MbrClauseAgree> mbrClauseAgreeList;

	/** 사용자 Key. */
	private String userKey;

	/**
	 * 사용자 Key를 리턴한다.
	 * 
	 * @return userKey - 사용자 Key
	 */
	public String getUserKey() {
		return this.userKey;
	}

	/**
	 * 사용자 Key를 설정한다.
	 * 
	 * @param userKey
	 *            사용자 Key
	 */
	public void setUserKey(String userKey) {
		this.userKey = userKey;
	}

	/**
	 * 공통 응답 Value Object를 리턴한다.
	 * 
	 * @return commonResponse - 공통 응답 Value Object
	 */
	public CommonResponse getCommonResponse() {
		return this.commonResponse;
	}

	/**
	 * 공통 응답 Value Object를 설정한다.
	 * 
	 * @param commonResponse
	 *            공통 응답 Value Object
	 */
	public void setCommonResponse(CommonResponse commonResponse) {
		this.commonResponse = commonResponse;
	}

	/**
	 * 사용자 약관동의 Value Object List를 리턴한다.
	 * 
	 * @return mbrClauseAgreeList - 사용자 약관동의 Value Object List
	 */
	public List<MbrClauseAgree> getMbrClauseAgreeList() {
		return this.mbrClauseAgreeList;
	}

	/**
	 * 사용자 약관동의 Value Object List를 설정한다.
	 * 
	 * @param mbrClauseAgreeList
	 *            사용자 약관동의 Value Object List
	 */
	public void setMbrClauseAgreeList(List<MbrClauseAgree> mbrClauseAgreeList) {
		this.mbrClauseAgreeList = mbrClauseAgreeList;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return Utils.printKeyValues(this);
	}
}
