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
import com.skplanet.storeplatform.member.client.common.vo.CommonResponse;
import com.skplanet.storeplatform.member.client.common.vo.MbrAuth;
import com.skplanet.storeplatform.member.client.common.vo.MbrClauseAgree;
import com.skplanet.storeplatform.member.client.common.vo.MbrLglAgent;
import com.skplanet.storeplatform.member.client.common.vo.MbrMangItemPtcr;

/**
 * 사용자 회원 기본정보 조회 응답 Value Object
 * 
 * Updated on : 2013. 12. 13. Updated by : wisestone_dinga
 */
public class SearchUserResponse extends CommonInfo implements Serializable {

	/** The Constant serial Version UID. */
	private static final long serialVersionUID = 1L;

	/** 공통 응답 Value Object. */
	private CommonResponse commonResponse;

	/** 사용자 Key. */
	private String userKey;

	/** 사용자 기본정보 Value Object. */
	private UserMbr userMbr;

	/** 사용자 징계정보 Value Object. */
	private UserMbrPnsh userMbrPnsh;

	/** 사용자 법정대리인 정보 Value Object. */
	private MbrLglAgent mbrLglAgent;

	/** 사용자 인증정보 Value Object. */
	private MbrAuth mbrAuth;

	/**
	 * 사용자 부가정보 및 관리항목 Value Object List.
	 */
	private List<MbrMangItemPtcr> mbrMangItemPtcrList;

	/** 약관동의 정보 Value Object List. */
	private List<MbrClauseAgree> mbrClauseAgreeList;

	/** 비밀번호 변경일자. */
	private String pwRegDate;

	/** 사용자 변동성 여부. */
	private String isChangeSubject;

	/** 사용자 휴대기기 전체 등록 대수. */
	private String totalDeviceCount;

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
	 * 사용자 징계정보 Value Object를 리턴한다.
	 * 
	 * @return userMbrPnsh - 사용자 징계정보 Value Object
	 */
	public UserMbrPnsh getUserMbrPnsh() {
		return this.userMbrPnsh;
	}

	/**
	 * 사용자 징계정보 Value Object를 설정한다.
	 * 
	 * @param userMbrPnsh
	 *            사용자 징계정보 Value Object
	 */
	public void setUserMbrPnsh(UserMbrPnsh userMbrPnsh) {
		this.userMbrPnsh = userMbrPnsh;
	}

	/**
	 * 사용자 인증정보 Value Object를 리턴한다.
	 * 
	 * @return userMbrAuth - 사용자 인증정보 Value Object
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
	 * 사용자 부가정보 및 관리항목 Value Object List를 리턴한다.
	 * 
	 * @return mbrMangItemPtcrList - 사용자 부가정보 및 관리항목 Value Object List
	 */
	public List<MbrMangItemPtcr> getMbrMangItemPtcrList() {
		return this.mbrMangItemPtcrList;
	}

	/**
	 * 사용자 부가정보 및 관리항목 Value Object List를 설정한다.
	 * 
	 * @param mbrMangItemPtcrList
	 *            사용자 부가정보 및 관리항목 Value Object List
	 */
	public void setMbrMangItemPtcrList(List<MbrMangItemPtcr> mbrMangItemPtcrList) {
		this.mbrMangItemPtcrList = mbrMangItemPtcrList;
	}

	/**
	 * 약관동의 항목 Value Object List를 리턴한다.
	 * 
	 * @return mbrClauseAgreeList - 약관동의 항목 Value Object List
	 */
	public List<MbrClauseAgree> getMbrClauseAgreeList() {
		return this.mbrClauseAgreeList;
	}

	/**
	 * 약관동의 항목 Value Object List를 설정한다.
	 * 
	 * @param mbrClauseAgreeList
	 *            약관동의 항목 Value Object List
	 */
	public void setMbrClauseAgreeList(List<MbrClauseAgree> mbrClauseAgreeList) {
		this.mbrClauseAgreeList = mbrClauseAgreeList;
	}

	/**
	 * 법정대리인 Value Object를 관리한다.
	 * 
	 * @return mbrLglAgent - 법정대리인 Value Object
	 */
	public MbrLglAgent getMbrLglAgent() {
		return this.mbrLglAgent;
	}

	/**
	 * 법정대리인 Value Object를 설정한다.
	 * 
	 * @param mbrLglAgent
	 *            법정대리인 Value Object
	 */
	public void setMbrLglAgent(MbrLglAgent mbrLglAgent) {
		this.mbrLglAgent = mbrLglAgent;
	}

	/**
	 * 패스워드 변경일자를 리턴한다.
	 * 
	 * @return pwRegDate - 패스워드 변경일자
	 */
	public String getPwRegDate() {
		return this.pwRegDate;
	}

	/**
	 * 패스워드 변경일자를 설정한다.
	 * 
	 * @param pwRegDate
	 *            패스워드 변경일자
	 */
	public void setPwRegDate(String pwRegDate) {
		this.pwRegDate = pwRegDate;
	}

	/**
	 * 사용자 변동성 여부를 리턴한다.
	 * 
	 * @return isChangeSubject - 사용자 변동성 여부
	 */
	public String getIsChangeSubject() {
		return this.isChangeSubject;
	}

	/**
	 * 사용자 변동성 여부를 설정한다.
	 * 
	 * @param isChangeSubject
	 *            사용자 변동성 여부
	 */
	public void setIsChangeSubject(String isChangeSubject) {
		this.isChangeSubject = isChangeSubject;
	}

	/**
	 * 사용자 휴대기기 전체 등록 대수를 리턴한다.
	 * 
	 * @return totalDeviceCount - 사용자 휴대기기 전체 등록 대수
	 */
	public String getTotalDeviceCount() {
		return this.totalDeviceCount;
	}

	/**
	 * 사용자 휴대기기 전체 등록 대수를 수정한다.
	 * 
	 * @param totalDeviceCount
	 *            사용자 휴대기기 전체 등록 대수
	 */
	public void setTotalDeviceCount(String totalDeviceCount) {
		this.totalDeviceCount = totalDeviceCount;
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
