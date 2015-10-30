/*
 * Copyright (c) 2013 SK planet.
 * All right reserved.
 * 
 * This software is the confidential and proprietary information of SK planet.
 * You shall not disclose such Confidential Information and
 * shall use it only in accordance with the terms of the license agreement
 * you entered into with SK planet.
 */
package com.skplanet.storeplatform.member.client.seller.sci.vo;

import java.io.Serializable;
import java.util.List;

import com.skplanet.storeplatform.framework.core.common.vo.CommonInfo;
import com.skplanet.storeplatform.member.client.common.util.Utils;
import com.skplanet.storeplatform.member.client.common.vo.CommonResponse;
import com.skplanet.storeplatform.member.client.common.vo.MbrAuth;
import com.skplanet.storeplatform.member.client.common.vo.MbrLglAgent;
import com.skplanet.storeplatform.member.client.common.vo.MbrPwd;

// TODO: Auto-generated Javadoc
/**
 * 판매자회원 기본정보 조회 응답 Value Object.
 * 
 * Updated on : 2013. 12. 10. Updated by : wisestone_mikepark
 */
public class SearchSellerResponse extends CommonInfo implements Serializable {

	/** The Constant serial Version UID. */
	private static final long serialVersionUID = 1L;

	/** 공통 응답 Value Object. */
	private CommonResponse commonResponse;

	/** 판매자 키. */
	private String sellerKey; // INSD_SELLERMBR_NO

	/** 판매자 정보 Value Object. */
	private SellerMbr sellerMbr;

	/** 비밀번호 Value Object. */
	private MbrPwd mbrPwd; // PW변경일시 ( TB_US_SELLERMBR_PWD 테이블에서 가져오기)

	/** 실명인증 Value Object. */
	private MbrAuth mbrAuth;

	/** 법정대리인 Value Object. */
	private MbrLglAgent mbrLglAgent;

	/** 탭권한 Value Object 목록. */
	private List<TabAuth> tabAuthList;
	//
	// /** 약관동의 Value Object 목록. */
	// private List<MbrClauseAgree> mbrClauseAgreeList; // 이용약관 TB_US_SELLERMBR_CLAUSE_AGREE

	/** 판매자 멀티미디어정보 Value Object 목록. */
	private List<ExtraRight> extraRightList; // US_판매자회원_멀티미디어_권한 TB_US_SELLERMBR_MULTIMDA_AUTH (bp 사인경우만)

	/** 판매자 비밀번호 보안질문 확인 Value Object 목록. */
	private List<PWReminder> pWReminderList;

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
	 * 탭권한 정보를 리턴한다.
	 * 
	 * @return tabAuthList - 탭권한 정보
	 */
	public List<TabAuth> getTabAuthList() {
		return this.tabAuthList;
	}

	/**
	 * 탭권한 정보를 설정한다.
	 * 
	 * @param tabAuthList
	 *            탭권한 정보
	 */
	public void setTabAuthList(List<TabAuth> tabAuthList) {
		this.tabAuthList = tabAuthList;
	}

	/**
	 * 판매자 키를 리턴한다.
	 * 
	 * @return sellerKey - 판매자 키
	 */
	public String getSellerKey() {
		return this.sellerKey;
	}

	/**
	 * 판매자 키를 설정한다.
	 * 
	 * @param sellerKey
	 *            판매자 키
	 */
	public void setSellerKey(String sellerKey) {
		this.sellerKey = sellerKey;
	}

	/**
	 * 판매자정보 Value Object를 리턴한다.
	 * 
	 * @return sellerMbr - 판매자정보 Value Object
	 */
	public SellerMbr getSellerMbr() {
		return this.sellerMbr;
	}

	/**
	 * 판매자정보 Value Object를 설정한다.
	 * 
	 * @param sellerMbr
	 *            판매자정보 Value Object
	 */
	public void setSellerMbr(SellerMbr sellerMbr) {
		this.sellerMbr = sellerMbr;
	}

	/**
	 * 법정대리인 Value Object를 리턴한다.
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
	 * 실명인증 Value Object를 리턴한다.
	 * 
	 * @return mbrAuth - 실명인증 Value Object
	 */
	public MbrAuth getMbrAuth() {
		return this.mbrAuth;
	}

	/**
	 * 실명인증 Value Object를 설정한다.
	 * 
	 * @param mbrAuth
	 *            실명인증 Value Object
	 */
	public void setMbrAuth(MbrAuth mbrAuth) {
		this.mbrAuth = mbrAuth;
	}

	/**
	 * 비밀번호 Value Object를 리턴한다.
	 * 
	 * @return mbrPwd - 비밀번호 Value Object
	 */
	public MbrPwd getMbrPwd() {
		return this.mbrPwd;
	}

	/**
	 * 비밀번호 Value Object를 설정한다.
	 * 
	 * @param mbrPwd
	 *            비밀번호 Value Object
	 */
	public void setMbrPwd(MbrPwd mbrPwd) {
		this.mbrPwd = mbrPwd;
	}

	/**
	 * 판매자 멀티미디어정보 Value Object 목록을 리턴한다.
	 * 
	 * @return extraRightList - 판매자 멀티미디어정보 Value Object 목록
	 */
	public List<ExtraRight> getExtraRightList() {
		return this.extraRightList;
	}

	/**
	 * 판매자 멀티미디어정보 Value Object 목록을 설정한다.
	 * 
	 * @param extraRightList
	 *            판매자 멀티미디어정보 Value Object 목록
	 */
	public void setExtraRightList(List<ExtraRight> extraRightList) {
		this.extraRightList = extraRightList;
	}

	// /**
	// * 약관동의 Value Object 목록를 리턴한다.
	// *
	// * @return mbrClauseAgree - 약관동의 Value Object 목록
	// */
	// public List<MbrClauseAgree> getMbrClauseAgreeList() {
	// return this.mbrClauseAgreeList;
	// }

	// /**
	// * 약관동의 Value Object 목록를 설정한다.
	// *
	// * @param mbrClauseAgreeList
	// * 약관동의 Value Object 목록
	// */
	// public void setMbrClauseAgreeList(List<MbrClauseAgree> mbrClauseAgreeList) {
	// this.mbrClauseAgreeList = mbrClauseAgreeList;
	// }

	/**
	 * 비밀번호 보안질문 확인 Value Object 목록를 리턴한다.
	 * 
	 * @return pWReminderList - 비밀번호 보안질문 확인 Value Object 목록
	 */
	public List<PWReminder> getPWReminderList() {
		return this.pWReminderList;
	}

	/**
	 * 비밀번호 보안질문 확인 Value Object 목록를 설정한다.
	 * 
	 * @param pWReminderList
	 *            비밀번호 보안질문 확인 Value Object 목록
	 */
	public void setPWReminderList(List<PWReminder> pWReminderList) {
		this.pWReminderList = pWReminderList;
	}

	/**
	 * Returns the serial version UID.
	 * 
	 * @return serialVersionUID - the serial version UID
	 */
	public static long getSerialversionuid() {
		return serialVersionUID;
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
