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

import java.io.Serializable;

import com.skplanet.storeplatform.framework.core.common.vo.CommonInfo;
import com.skplanet.storeplatform.member.client.common.util.Utils;

// TODO: Auto-generated Javadoc
/**
 * 관리항목 내역 Value Object.
 */
public class MbrMangItemPtcr extends CommonInfo implements Serializable {

	/** The Constant serial Version UID. */
	private static final long serialVersionUID = 1L;

	/** Tenant ID. */
	private String tenantID;

	/** 내부 회원 키. */
	private String userKey; // 내부 사용자 id

	/** 관리항목 코드. */
	private String extraProfile; // MANG_ITEM_CD, 관리항목 코드

	/** 관리항목 값. */
	private String extraProfileValue; // REG_RESULT_VALUE, 등록_결과_값

	/** 등록일시. */
	private String regDate; // REG_DT

	/** 등록 ID. */
	private String regID; // REG_ID

	/** 수정일시. */
	private String updateDate; // UPD_DT

	/** 수정 ID. */
	private String updateID; // UPD_ID

	/** 외부(IDP)에서 할당된 사용자 Key. OGG용, 추후 삭제 예정, 2014.03.20. */
	private String imMbrNo; // IDP 통합서비스 키 USERMBR_NO

	/** 사용자 ID. */
	private String userID;

	/**
	 * 수정 ID를 리턴한다.
	 * 
	 * @return updateID - 수정 ID
	 */
	public String getUpdateID() {
		return this.updateID;
	}

	/**
	 * 수정 ID를 설정한다.
	 * 
	 * @param updateID
	 *            수정 ID
	 */
	public void setUpdateID(String updateID) {
		this.updateID = updateID;
	}

	/**
	 * 수정일시를 리턴한다.
	 * 
	 * @return updateDate - 수정일시
	 */
	public String getUpdateDate() {
		return this.updateDate;
	}

	/**
	 * 수정일시를 설정한다.
	 * 
	 * @param updateDate
	 *            수정일시
	 */
	public void setUpdateDate(String updateDate) {
		this.updateDate = updateDate;
	}

	/**
	 * 등록 ID를 리턴한다.
	 * 
	 * @return regID - 등록 ID
	 */
	public String getRegID() {
		return this.regID;
	}

	/**
	 * 등록 ID를 설정한다.
	 * 
	 * @param regID
	 *            등록 ID
	 */
	public void setRegID(String regID) {
		this.regID = regID;
	}

	/**
	 * 등록일시를 리턴한다.
	 * 
	 * @return regDate - 등록일시
	 */
	public String getRegDate() {
		return this.regDate;
	}

	/**
	 * 등록일시를 설정한다.
	 * 
	 * @param regDate
	 *            등록일시
	 */
	public void setRegDate(String regDate) {
		this.regDate = regDate;
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
	 * 내부 회원 키를 리턴한다.
	 * 
	 * @return userKey - 내부 회원 키
	 */
	public String getUserKey() {
		return this.userKey;
	}

	/**
	 * 내부 회원 키를 설정한다.
	 * 
	 * @param userKey
	 *            내부 회원 키
	 */
	public void setUserKey(String userKey) {
		this.userKey = userKey;
	}

	/**
	 * 관리항목 코드를 리턴한다.
	 * 
	 * @return extraProfile - 관리항목 코드
	 */
	public String getExtraProfile() {
		return this.extraProfile;
	}

	/**
	 * 관리항목 코드를 설정한다.
	 * 
	 * @param extraProfile
	 *            관리항목 코드
	 */
	public void setExtraProfile(String extraProfile) {
		this.extraProfile = extraProfile;
	}

	/**
	 * 관리항목 값을 리턴한다.
	 * 
	 * @return extraProfileValue - 관리항목 값
	 */
	public String getExtraProfileValue() {
		return this.extraProfileValue;
	}

	/**
	 * 관리항목 값을 설정한다.
	 * 
	 * @param extraProfileValue
	 *            관리항목 값
	 */
	public void setExtraProfileValue(String extraProfileValue) {
		this.extraProfileValue = extraProfileValue;
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
	 * 외부(IDP/OneID)에서 할당된 회원 Key를 리턴한다.
	 * 
	 * @return imMbrNo - 외부(IDP/OneID)에서 할당된 회원 Key
	 */
	public String getImMbrNo() {
		return this.imMbrNo;
	}

	/**
	 * 외부(IDP/OneID)에서 할당된 회원 Key를 설정한다.
	 * 
	 * @param imMbrNo
	 *            외부(IDP/OneID)에서 할당된 회원 Key
	 */
	public void setImMbrNo(String imMbrNo) {
		this.imMbrNo = imMbrNo;
	}

	/**
	 * 사용자 ID를 리턴한다.
	 * 
	 * @return userID
	 */
	public String getUserID() {
		return this.userID;
	}

	/**
	 * 사용자 ID를 설정한다.
	 * 
	 * @param userID
	 *            사용자 ID
	 */
	public void setUserID(String userID) {
		this.userID = userID;
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
