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

import com.skplanet.storeplatform.framework.core.common.vo.CommonInfo;
import com.skplanet.storeplatform.member.client.common.vo.CommonRequest;
import com.skplanet.storeplatform.member.client.common.vo.MbrAuth;
import com.skplanet.storeplatform.member.client.common.vo.MbrLglAgent;

/**
 * 사용자 실명인증 정보 수정 요청 Value Object
 * 
 * Updated on : 2013. 12. 11. Updated by : wisestone_dinga
 */
public class UpdateRealNameRequest extends CommonInfo implements Serializable {

	/** The Constant serial Version UID. */
	private static final long serialVersionUID = 1L;

	/** 공통 요청 Value Object. */
	private CommonRequest commonRequest;

	/** 사용자 Key. */
	private String userKey;

	/**
	 * 실명인증 여부 Y/N.
	 */
	private String isRealName;

	/**
	 * <pre>
	 * 실명인증 대상. 
	 * 본인 : OWN
	 * 법정대리인 : PARENT
	 * </pre>
	 */
	private String isOwn;

	/**
	 * 휴면계정유무.
	 */
	private String isDormant;

	/**
	 * 실명인증 대상이 법정대리인인 경우 사용하는 Value Object.
	 */
	private MbrLglAgent mbrLglAgent;

	/**
	 * 실명인증 대상이 본인인 경우 사용하는 Value Object.
	 */
	private MbrAuth mbrAuth;

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
	 * 실명인증 여부(Y/N)을 리턴한다.
	 * 
	 * @return isRealName - 실명인증 여부(Y/N)
	 */
	public String getIsRealName() {
		return this.isRealName;
	}

	/**
	 * 실명인증 여부(Y/N)을 설정한다.
	 * 
	 * @param isRealName
	 *            실명인증 여부(Y/N)
	 */
	public void setIsRealName(String isRealName) {
		this.isRealName = isRealName;
	}

	/**
	 * 실명인증 대상 여부(본인/법정대리인)를 리턴한다.
	 * 
	 * @return isOwn - 실명인증 대상 여부(본인/법정대리인)
	 */
	public String getIsOwn() {
		return this.isOwn;
	}

	/**
	 * 실명인증 대상 여부(본인/법정대리인)를 설정한다.
	 * 
	 * @param isOwn
	 *            실명인증 대상 여부(본인/법정대리인)
	 */
	public void setIsOwn(String isOwn) {
		this.isOwn = isOwn;
	}

	/**
	 * 법정대리인인 정보 Value Object를 리턴한다.
	 * 
	 * @return userMbrLglAgent - 법정대리인인 정보 Value Object
	 */
	public MbrLglAgent getMbrLglAgent() {
		return this.mbrLglAgent;
	}

	/**
	 * 법정대리인인 정보 Value Object를 설정한다.
	 * 
	 * @param mbrLglAgent
	 *            법정대리인인 정보 Value Object
	 */
	public void setMbrLglAgent(MbrLglAgent mbrLglAgent) {
		this.mbrLglAgent = mbrLglAgent;
	}

	/**
	 * 실명인증 정보 Value Object를 리턴한다.
	 * 
	 * @return mbrAuth - 실명인증 정보 Value Object
	 */
	public MbrAuth getMbrAuth() {
		return this.mbrAuth;
	}

	/**
	 * 실명인증 정보 Value Object를 설정한다.
	 * 
	 * @param mbrAuth
	 *            실명인증 정보 Value Object
	 */
	public void setUserMbrAuth(MbrAuth mbrAuth) {
		this.mbrAuth = mbrAuth;
	}

	/**
	 * @return isDormant
	 */
	public String getIsDormant() {
		return this.isDormant;
	}

	/**
	 * @param isDormant
	 *            String
	 */
	public void setIsDormant(String isDormant) {
		this.isDormant = isDormant;
	}

}
