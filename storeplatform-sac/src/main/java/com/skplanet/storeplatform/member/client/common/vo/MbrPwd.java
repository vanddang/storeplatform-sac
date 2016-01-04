/*
 * Copyright (c) 2013 SK planet.
 * All right reserved.
 * 
 * This software is the confidential and proprietary information of SK planet.
 * You shall not disclose such Confidential Information and
 * shall use it only in accordance with the terms of the license agreement
 * you entered into with SK planet.
 */
package com.skplanet.storeplatform.member.client.common.vo;

import com.skplanet.storeplatform.framework.core.common.vo.CommonInfo;
import com.skplanet.storeplatform.member.client.common.util.Utils;

import java.io.Serializable;

// TODO: Auto-generated Javadoc
/**
 * 비밀번호 value object
 * 
 * Updated on : 2013. 12. 10. Updated by : wisestone_mikepark
 */
public class MbrPwd extends CommonInfo implements Serializable {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/** 내부 회원 키. */
	private String memberKey; // INSD_SELLERMBR_NO 내부 사용자코드

	/** 내부 회원 ID. */
	private String memberID; // ID 정보 (sellerID) or user(userID)

	/** 비밀번호encType . */
	private String encType;

	/**
	 * 비밀번호를 반환한다.
	 * 
	 * @return the pwd - 비밀번호
	 */
	public String getEncType() {
		return this.encType;
	}

	/**
	 * 비밀번호encType를 설정한다.
	 * 
	 * @param encType
	 *            비밀번호encType
	 */
	public void setEncType(String encType) {
		this.encType = encType;
	}

	/** 이전 비밀번호. */
	private String oldPW; // OldPW

	/** 비밀번호. */
	private String memberPW; // PWD

	/** 비밀번호 변경일시. */
	private String pwRegDate; // UPD_DT PW변경일시

	/** 로그인 실패 카운트. */
	private int loginFailCount; // FAIL_CNT

	/** Tenant id. */
	private String tenantID;

	/**
	 * 내부 회원 키를 리턴한다.
	 * 
	 * @return memberKey - 내부 회원 키
	 */
	public String getMemberKey() {
		return this.memberKey;
	}

	/**
	 * 내부 회원 키를 설정한다.
	 * 
	 * @param memberKey
	 *            내부 회원 키
	 */
	public void setMemberKey(String memberKey) {
		this.memberKey = memberKey;
	}

	/**
	 * 이전 비밀번호를 리턴한다.
	 * 
	 * @return oldPW - 이전 비밀번호
	 */
	public String getOldPW() {
		return this.oldPW;
	}

	/**
	 * 이전 비밀번호를 설정한다.
	 * 
	 * @param oldPW
	 *            이전 비밀번호
	 */
	public void setOldPW(String oldPW) {
		this.oldPW = oldPW;
	}

	/**
	 * 내부 회원 ID를 리턴한다.
	 * 
	 * @return memberID - 내부 회원 ID
	 */
	public String getMemberID() {
		return this.memberID;
	}

	/**
	 * 내부 회원 ID를 설정한다.
	 * 
	 * @param memberID
	 *            내부 회원 ID
	 */
	public void setMemberID(String memberID) {
		this.memberID = memberID;
	}

	/**
	 * 비밀번호를 리턴한다.
	 * 
	 * @return memberPW - 비밀번호
	 */
	public String getMemberPW() {
		return this.memberPW;
	}

	/**
	 * 비밀번호를 설정한다.
	 * 
	 * @param memberPW
	 *            비밀번호
	 */
	public void setMemberPW(String memberPW) {
		this.memberPW = memberPW;
	}

	/**
	 * Tenant ID를 리턴한다.
	 * 
	 * @return tenantID - Tenant ID
	 */
	public String getTenantID() {
		return this.tenantID;
	}

	/**
	 * Tenant ID를 설정한다.
	 * 
	 * @param tenantID
	 *            Tenant ID
	 */
	public void setTenantID(String tenantID) {
		this.tenantID = tenantID;
	}

	/**
	 * 비밀번호 변경일시를 리턴한다.
	 * 
	 * @return pwRegDate - 비밀번호 변경일시
	 */
	public String getPwRegDate() {
		return this.pwRegDate;
	}

	/**
	 * 비밀번호 변경일시를 설정한다.
	 * 
	 * @param pwRegDate
	 *            비밀번호 변경일시
	 */
	public void setPwRegDate(String pwRegDate) {
		this.pwRegDate = pwRegDate;
	}

	/**
	 * 로그인 실패 카운트를 리턴한다.
	 * 
	 * @return loginFailCount - 로그인 실패 카운트
	 */
	public int getLoginFailCount() {
		return this.loginFailCount;
	}

	/**
	 * 로그인 실패 카운트를 설정한다.
	 * 
	 * @param loginFailCount
	 *            로그인 실패 카운트
	 */
	public void setLoginFailCount(int loginFailCount) {
		this.loginFailCount = loginFailCount;
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
