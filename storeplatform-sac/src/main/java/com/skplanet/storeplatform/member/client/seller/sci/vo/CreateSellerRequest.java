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
import com.skplanet.storeplatform.member.client.common.vo.CommonRequest;
import com.skplanet.storeplatform.member.client.common.vo.MbrAuth;
import com.skplanet.storeplatform.member.client.common.vo.MbrPwd;

// TODO: Auto-generated Javadoc
/**
 * 판매자회원 가입 요청 Value Object.
 * 
 * Updated on : 2013. 12. 10. Updated by : wisestone_mikepark
 */
public class CreateSellerRequest extends CommonInfo implements Serializable {

	/** The Constant serial Version UID. */
	private static final long serialVersionUID = 1L;

	/** 공통 요청 Value Object. */
	private CommonRequest commonRequest;

	/** 판매자정보 Value Object. */
	private SellerMbr sellerMbr;

	/** 판매자 비밀번호 정보 memberPW 저장 Value Object. */
	private MbrPwd mbrPwd;

	/**
	 * 판매자 비밀번호 정보 Value Object를 리턴한다.
	 * 
	 * @return mbrPwd - 사용자 비밀번호 정보 Value Object
	 */
	public MbrPwd getMbrPwd() {
		return this.mbrPwd;
	}

	/**
	 * 판매자 비밀번호 정보 Value Object를 설정한다.
	 * 
	 * @param mbrPwd
	 *            사용자 비밀번호 정보 Value Object
	 */
	public void setMbrPwd(MbrPwd mbrPwd) {
		this.mbrPwd = mbrPwd;
	}

	/** 실명인증 Value Object. */
	private MbrAuth mbrAuth;
	//
	// /** 이용약관 Value Object. */
	// private List<MbrClauseAgree> mbrClauseAgreeList;

	/** 비밀번호 보안질문 Value Object 목록. */
	private List<PWReminder> pWReminderList;

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

	// /**
	// * 이용약관 Value Object를 리턴한다.
	// *
	// * @return mbrClauseAgreeList - 이용약관 Value Object
	// */
	// public List<MbrClauseAgree> getMbrClauseAgreeList() {
	// return this.mbrClauseAgreeList;
	// }

	// /**
	// * 이용약관 Value Object를 설정한다.
	// *
	// * @param mbrClauseAgreeList
	// * 이용약관 Value Object
	// */
	// public void setMbrClauseAgree(List<MbrClauseAgree> mbrClauseAgreeList) {
	// this.mbrClauseAgreeList = mbrClauseAgreeList;
	// }

	/**
	 * 비밀번호 보안질문 Value Object 목록를 리턴한다.
	 * 
	 * @return pWReminderList - 비밀번호 보안질문 Value Object 목록
	 */
	public List<PWReminder> getPWReminderList() {
		return this.pWReminderList;
	}

	/**
	 * 비밀번호 보안질문 Value Object 목록를 설정한다.
	 * 
	 * @param pWReminderList
	 *            비밀번호 보안질문 Value Object 목록
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
