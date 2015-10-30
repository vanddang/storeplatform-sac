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
import com.skplanet.storeplatform.member.client.common.util.Utils;

// TODO: Auto-generated Javadoc
/**
 * 사용자 징계정보 응답 Value Object.
 */
public class UserMbrPnsh extends CommonInfo implements Serializable {

	/** The Constant serial Version UID. */
	private static final long serialVersionUID = 1L;

	/** 사용자 Key. */
	private String userKey;

	/** 테넌트 ID. */
	private String tenantID;

	/** 사용자 징계처리 여부(Y/N). */
	private String isRestricted;

	/** 사용자 징계 시작일. */
	private String restrictStartDate; // 징계 시작일

	/** 사용자 징계 종료일. */
	private String restrictEndDate; // 징계 종료일

	/** 징계구분 코드. */
	private String restrictID; // 징계구분 코드

	/** 신고 게시물 수. */
	private String restrictCount; // 신고 게시물수

	/** 징계 등록일. */
	private String restrictRegisterDate; // 징계 등록일

	/** 징계 등록자. */
	private String restrictOwner; // 징계 등록자

	/**
	 * 휴면계정유무.
	 */
	private String isDormant;

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
	 * 사용자가 속한 테넌트 ID를 리턴한다.
	 * 
	 * @return tenantID - 테넌트 ID
	 */
	public String getTenantID() {
		return this.tenantID;
	}

	/**
	 * 사용자가 속한 테넌트 ID를 설정한다.
	 * 
	 * @param tenantID
	 *            테넌트 ID
	 */
	public void setTenantID(String tenantID) {
		this.tenantID = tenantID;
	}

	/**
	 * 사용자 징계여부(Y/N)를 리턴한다.
	 * 
	 * @return isRestricted - 사용자 징계여부(Y/N)
	 */
	public String getIsRestricted() {
		return this.isRestricted;
	}

	/**
	 * 사용자 징계여부(Y/N)를 설정한다.
	 * 
	 * @param isRestricted
	 *            사용자 징계여부(Y/N)
	 */
	public void setIsRestricted(String isRestricted) {
		this.isRestricted = isRestricted;
	}

	/**
	 * 징계시작일을 리턴한다.
	 * 
	 * @return restrictStartDate - 징계시작일
	 */
	public String getRestrictStartDate() {
		return this.restrictStartDate;
	}

	/**
	 * 징계시작일을 설정한다.
	 * 
	 * @param restrictStartDate
	 *            징계시작일
	 */
	public void setRestrictStartDate(String restrictStartDate) {
		this.restrictStartDate = restrictStartDate;
	}

	/**
	 * 징계종료일을 리턴한다.
	 * 
	 * @return restrictEndDate - 징계종료일
	 */
	public String getRestrictEndDate() {
		return this.restrictEndDate;
	}

	/**
	 * 징계종료일을 설정한다.
	 * 
	 * @param restrictEndDate
	 *            징계종료일
	 */
	public void setRestrictEndDate(String restrictEndDate) {
		this.restrictEndDate = restrictEndDate;
	}

	/**
	 * 징계 코드를 리턴한다.
	 * 
	 * @return restrictID - 징계 코드
	 */
	public String getRestrictID() {
		return this.restrictID;
	}

	/**
	 * 징계 코드를 설정한다.
	 * 
	 * @param restrictID
	 *            징계 코드
	 */
	public void setRestrictID(String restrictID) {
		this.restrictID = restrictID;
	}

	/**
	 * 신고 게시물수를 리턴한다.
	 * 
	 * @return restrictCount - 신고 게시물수
	 */
	public String getRestrictCount() {
		return this.restrictCount;
	}

	/**
	 * 신고 게시물수를 설정한다.
	 * 
	 * @param restrictCount
	 *            신고 게시물수
	 */
	public void setRestrictCount(String restrictCount) {
		this.restrictCount = restrictCount;
	}

	/**
	 * 사용자의 징계가 등록된 날짜를 리턴한다.
	 * 
	 * @return restrictRegisterDate - 징계 등록일
	 */
	public String getRestrictRegisterDate() {
		return this.restrictRegisterDate;
	}

	/**
	 * 사용자의 징계 등록일을 설정한다.
	 * 
	 * @param restrictRegisterDate
	 *            징계 등록일
	 */
	public void setRestrictRegisterDate(String restrictRegisterDate) {
		this.restrictRegisterDate = restrictRegisterDate;
	}

	/**
	 * 징계 등록자를 리턴한다.
	 * 
	 * @return restrictOwner - 징계 등록자
	 */
	public String getRestrictOwner() {
		return this.restrictOwner;
	}

	/**
	 * 징계 등록자를 설정한다.
	 * 
	 * @param restrictOwner
	 *            징계 등록자
	 */
	public void setRestrictOwner(String restrictOwner) {
		this.restrictOwner = restrictOwner;
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
