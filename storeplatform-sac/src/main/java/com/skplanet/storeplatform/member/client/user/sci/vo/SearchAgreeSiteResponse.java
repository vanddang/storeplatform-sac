/*
 * Copyright (c) 2013 SK planet.
 * All right reserved.
 * 
 * This software is the confidential and proprietary information of SK planet.
 * You shall not disclose such Confidential Information and
 * shall use it only in accordance with the terms of the license agreement
 * you entered into with SK planet.
 */
package com.skplanet.storeplatform.member.client.user.sci.vo;

import java.io.Serializable;

import com.skplanet.storeplatform.framework.core.common.vo.CommonInfo;
import com.skplanet.storeplatform.member.client.common.vo.CommonResponse;
import com.skplanet.storeplatform.member.client.common.vo.MbrOneID;

/**
 * 미동의 사이트 조회 응답 Value Object
 * 
 * Updated on : 2014. 1. 24 Updated by : wisestone_dinga
 */
public class SearchAgreeSiteResponse extends CommonInfo implements Serializable {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/** 공통 응답 Value Object. */
	private CommonResponse commonResponse;

	/** 미동의 사용자 관리 Value Object. */
	private MbrOneID mbrOneID;

	/**
	 * 미동의 사용자 관리 Value Object를 리턴한다.
	 * 
	 * @return mbrOneID - 미동의 사용자 관리 Value Object
	 */
	public MbrOneID getMbrOneID() {
		return this.mbrOneID;
	}

	/**
	 * 미동의 사용자 관리 Value Object를 설정한다.
	 * 
	 * @param mbrOneID
	 *            미동의 사용자 관리 Value Object
	 */
	public void setMbrOneID(MbrOneID mbrOneID) {
		this.mbrOneID = mbrOneID;
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

}
