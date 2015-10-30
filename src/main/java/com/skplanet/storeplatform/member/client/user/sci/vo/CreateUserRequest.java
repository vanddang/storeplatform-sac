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
import java.util.List;

import com.skplanet.storeplatform.framework.core.common.vo.CommonInfo;
import com.skplanet.storeplatform.member.client.common.util.Utils;
import com.skplanet.storeplatform.member.client.common.vo.CommonRequest;
import com.skplanet.storeplatform.member.client.common.vo.MbrAuth;
import com.skplanet.storeplatform.member.client.common.vo.MbrClauseAgree;
import com.skplanet.storeplatform.member.client.common.vo.MbrLglAgent;
import com.skplanet.storeplatform.member.client.common.vo.MbrMangItemPtcr;
import com.skplanet.storeplatform.member.client.common.vo.MbrPwd;

/**
 * 사용자 가입 요청 Value Object
 * 
 * Updated on : 2013. 12. 10. Updated by : wisestone_mikepark
 */
public class CreateUserRequest extends CommonInfo implements Serializable {

	/** The Constant serial Version UID. */
	private static final long serialVersionUID = 1L;

	/** 공통 요청 Value Object. */
	private CommonRequest commonRequest;

	/** 사용자 정보 Value Object. */
	private UserMbr userMbr;

	// 시작
	/** 법정대리인 Value Object. */
	private MbrLglAgent mbrLglAgent;

	/** 실명인증 Value Object. */
	private MbrAuth mbrAuth;

	/** 사용자 관리항목/부가속성 리스트 Value Object. */
	private List<MbrMangItemPtcr> mbrMangItemPtcrList;

	/** 이용약관 Value Object. */
	private List<MbrClauseAgree> mbrClauseAgreeList;

	/** 사용자 비밀번호 정보 Value Object. */
	private MbrPwd mbrPwd;

	/**
	 * 공통 요청 Value Object를 리턴한다.
	 * 
	 * @return commonRequest - 공통 요청 Value Object
	 */
	public CommonRequest getCommonRequest() {
		return this.commonRequest;
	}

	/**
	 * 사용자 정보 Value Object를 리턴한다.
	 * 
	 * @return userMbr - 사용자 정보 Value Object
	 */
	public UserMbr getUserMbr() {
		return this.userMbr;
	}

	/**
	 * 사용자 정보 Value Object를 설정한다.
	 * 
	 * @param userMbr
	 *            사용자 정보 Value Object
	 */
	public void setUserMbr(UserMbr userMbr) {
		this.userMbr = userMbr;
	}

	/**
	 * 사용자 인증정보 Value Object를 리턴한다.
	 * 
	 * @return mbrAuth - 사용자 인증정보 Value Object
	 */
	public MbrAuth getMbrAuth() {
		return this.mbrAuth;
	}

	/**
	 * 사용자 인증정보 Value Object를 설정한다.
	 * 
	 * @param mbrAuth
	 *            사용자 인증정보 Value Object
	 */
	public void setMbrAuth(MbrAuth mbrAuth) {
		this.mbrAuth = mbrAuth;
	}

	/**
	 * 사용자 관리항목 및 부가속성을 정의한 Value Object를 리턴한다.
	 * 
	 * @return MbrMangItemPtcrList - 사용자 관리항목/부가속성 Value Object
	 */
	public List<MbrMangItemPtcr> getMbrMangItemPtcrList() {
		return this.mbrMangItemPtcrList;
	}

	/**
	 * 사용자 관리항목 및 부가속성 Value Object를 설정한다.
	 * 
	 * @param mbrMangItemPtcrList
	 *            사용자 관리항목/부가속성 Value Object
	 */
	public void setMbrMangItemPtcrList(List<MbrMangItemPtcr> mbrMangItemPtcrList) {
		this.mbrMangItemPtcrList = mbrMangItemPtcrList;
	}

	/**
	 * 사용자 약관정보 Value Object를 리턴한다.
	 * 
	 * @return mbrClauseAgreeList - 약관정보 Value Object
	 */
	public List<MbrClauseAgree> getMbrClauseAgreeList() {
		return this.mbrClauseAgreeList;
	}

	/**
	 * 사용자 약관정보 Value Object를 설정한다.
	 * 
	 * @param mbrClauseAgreeList
	 *            약관정보 Value Object
	 */
	public void setMbrClauseAgreeList(List<MbrClauseAgree> mbrClauseAgreeList) {
		this.mbrClauseAgreeList = mbrClauseAgreeList;
	}

	/**
	 * Returns the serial version UID.
	 * 
	 * @return serialVersionUID - the serial version UID
	 */
	public static long getSerialversionuid() {
		return serialVersionUID;
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
	 * 법정대리인 정보 Value Object를 리턴한다.
	 * 
	 * @return mbrLglAgent - 법정대리인 정보 Value Object
	 */
	public MbrLglAgent getMbrLglAgent() {
		return this.mbrLglAgent;
	}

	/**
	 * 법정대리인 정보 Value Object를 설정한다.
	 * 
	 * @param mbrLglAgent
	 *            법정대리인 정보 Value Object
	 */
	public void setMbrLglAgent(MbrLglAgent mbrLglAgent) {
		this.mbrLglAgent = mbrLglAgent;
	}

	/**
	 * 사용자 비밀번호 정보 Value Object를 리턴한다.
	 * 
	 * @return mbrPwd - 사용자 비밀번호 정보 Value Object
	 */
	public MbrPwd getMbrPwd() {
		return this.mbrPwd;
	}

	/**
	 * 사용자 비밀번호 정보 Value Object를 설정한다.
	 * 
	 * @param mbrPwd
	 *            사용자 비밀번호 정보 Value Object
	 */
	public void setMbrPwd(MbrPwd mbrPwd) {
		this.mbrPwd = mbrPwd;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.skplanet.storeplatform.framework.core.common.vo.CommonInfo#toString()
	 */
	@Override
	public String toString() {
		return Utils.printKeyValues(this);
	}
}
