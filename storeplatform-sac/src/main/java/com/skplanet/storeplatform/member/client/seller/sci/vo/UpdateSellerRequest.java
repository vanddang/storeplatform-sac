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
import com.skplanet.storeplatform.member.client.common.vo.MbrLglAgent;

// TODO: Auto-generated Javadoc
/**
 * 판매자회원 기본정보 수정 요청 Value Object.
 * 
 * Updated on : 2013. 12. 10. Updated by : wisestone_mikepark
 */
public class UpdateSellerRequest extends CommonInfo implements Serializable {

	/** The Constant serial Version UID. */
	private static final long serialVersionUID = 1L;

	/** 공통 요청 Value Object. */
	private CommonRequest commonRequest;

	/** 비밀번호 보안질문 Value Object 목록. */
	private List<PWReminder> pWReminderList;

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
	 * <pre>
	 * 판매자정보 Value Object 필수 항목.  
	 * tenantID : tenantID
	 * sellerKey : 판매자 Key
	 * </pre>
	 */
	private SellerMbr sellerMbr;

	/** 실명인증 Value Object. */
	private MbrAuth mbrAuth;

	/** 법정대리인 Value Object. */
	private MbrLglAgent mbrLglAgent;

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
