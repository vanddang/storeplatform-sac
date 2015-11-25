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

/**
 * 사용자회원 기본정보 수정 요청 Value Object
 * 
 * Updated on : 2013. 12. 13. Updated by : wisestone_dinga
 */
public class UpdateUserRequest extends CommonInfo implements Serializable {

	/** The Constant serial Version UID. */
	private static final long serialVersionUID = 1L;

	/** 공통 요청 Value Object. */
	private CommonRequest commonRequest;

	/** 사용자 기본정보 Value Object. */
	private UserMbr userMbr;

	/** 사용자 법정대리인 정보 Value Object. */
	private MbrLglAgent mbrLglAgent;

	/** 사용자 실명인증정보 Value Object. */
	private MbrAuth mbrAuth;

	/** 사용자 부가정보 및 관리항목 Value Object List. */
	private List<MbrMangItemPtcr> mbrMangItemPtcrList;

	/** 사용자 약관동의 정보 Value Object List. */
	private List<MbrClauseAgree> mbrClauseAgree;

	/**
	 * 휴면계정유무.
	 */
	private String isDormant;

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
	 * 사용자 기본정보 Value Object를 리턴한다.
	 * 
	 * @return userMbr - 사용자 기본정보 Value Object
	 */
	public UserMbr getUserMbr() {
		return this.userMbr;
	}

	/**
	 * 사용자 기본정보 Value Object를 설정한다.
	 * 
	 * @param userMbr
	 *            사용자 기본정보 Value Object
	 */
	public void setUserMbr(UserMbr userMbr) {
		this.userMbr = userMbr;
	}

	/**
	 * 사용자 실명인증 정보 Value Object를 리턴한다.
	 * 
	 * @return userMbrAuth - 사용자 실명인증 정보 Value Object
	 */
	public MbrAuth getMbrAuth() {
		return this.mbrAuth;
	}

	/**
	 * 사용자 실명인증 정보 Value Object를 설정한다.
	 * 
	 * @param mbrAuth
	 *            사용자 실명인증 정보 Value Object
	 */
	public void setMbrAuth(MbrAuth mbrAuth) {
		this.mbrAuth = mbrAuth;
	}

	/**
	 * 사용자 부가정보 및 관리항목 정보 Value Object List를 리턴한다.
	 * 
	 * @return mbrMangItemPtcrList - 사용자 부가정보 및 관리항목 정보 Value Object List
	 */
	public List<MbrMangItemPtcr> getMbrMangItemPtcrList() {
		return this.mbrMangItemPtcrList;
	}

	/**
	 * 사용자 부가정보 및 관리항목 정보 Value Object List를 설정한다.
	 * 
	 * @param mbrMangItemPtcrList
	 *            사용자 부가정보 및 관리항목 정보 Value Object List
	 */
	public void setMbrMangItemPtcrList(List<MbrMangItemPtcr> mbrMangItemPtcrList) {
		this.mbrMangItemPtcrList = mbrMangItemPtcrList;
	}

	/**
	 * 사용자 약관동의 항목 Value Object List를 리턴한다.
	 * 
	 * @return mbrClauseAgree - 사용자 약관동의 항목 Value Object List
	 */
	public List<MbrClauseAgree> getMbrClauseAgree() {
		return this.mbrClauseAgree;
	}

	/**
	 * 사용자 약관동의 항목 Value Object List를 설정한다.
	 * 
	 * @param mbrClauseAgree
	 *            사용자 약관동의 항목 Value Object List
	 */
	public void setMbrClauseAgree(List<MbrClauseAgree> mbrClauseAgree) {
		this.mbrClauseAgree = mbrClauseAgree;
	}

	/**
	 * 사용자 법정대리인 정보 Value Object를 리턴한다.
	 * 
	 * @return mbrLglAgent - 사용자 법정대리인 정보 Value Object
	 */
	public MbrLglAgent getMbrLglAgent() {
		return this.mbrLglAgent;
	}

	/**
	 * 사용자 법정대리인 정보 Value Object를 리턴한다.
	 * 
	 * @param mbrLglAgent
	 *            사용자 법정대리인 정보 Value Object
	 */
	public void setMbrLglAgent(MbrLglAgent mbrLglAgent) {
		this.mbrLglAgent = mbrLglAgent;
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
