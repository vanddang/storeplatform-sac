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
import com.skplanet.storeplatform.member.client.common.vo.CommonRequest;
import com.skplanet.storeplatform.member.client.common.vo.MbrMangItemPtcr;

/**
 * 사용자 부가정보 및 관리항목 정보수정 요청 Value Object
 * 
 * Updated on : 2014. 1. 6 Updated by : wisestone_brian, wisestone
 */
public class UpdateManagementRequest extends CommonInfo implements Serializable {

	/** The Constant serial Version UID. */
	private static final long serialVersionUID = 1L;

	/** 공통 요청 Value Object. */
	private CommonRequest commonRequest;

	/** 사용자 Key. */
	private String userKey;

	/** 사용자 부가정보 및 관리항목 Value Object List. */
	private List<MbrMangItemPtcr> mbrMangItemPtcr;

	/**
	 * 사용자 부가정보 및 관리항목 Value Object List를 리턴한다.
	 * 
	 * @return mbrMangItemPtcr - 사용자 부가정보 및 관리항목 Value Object List
	 */
	public List<MbrMangItemPtcr> getMbrMangItemPtcr() {
		return this.mbrMangItemPtcr;
	}

	/**
	 * 사용자 부가정보 및 관리항목 Value Object List를 설정한다.
	 * 
	 * @param mbrMangItemPtcr
	 *            사용자 부가정보 및 관리항목 Value Object List
	 */
	public void setMbrMangItemPtcr(List<MbrMangItemPtcr> mbrMangItemPtcr) {
		this.mbrMangItemPtcr = mbrMangItemPtcr;
	}

	/**
	 * 공통 요청 Value Object를 리턴한다.
	 * 
	 * @return commonRequest - 공통 요청 Value Object
	 */
	public CommonRequest getCommonRequest() {
		return this.commonRequest;
	}

	/**
	 * 공통 요청 Value Object를 설정한다.
	 * 
	 * @param commonRequest
	 *            공통 요청 Value Object
	 */
	public void setCommonRequest(CommonRequest commonRequest) {
		this.commonRequest = commonRequest;
	}

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

}
