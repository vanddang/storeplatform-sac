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

/**
 * 사용자키 변환추적 Value Object
 * 
 * Updated on : 2014. 01. 27. Updated by : wisestone_mikepark
 */
public class UserkeyTrack extends CommonInfo implements Serializable {
	/** The Constant serial Version UID. */
	private static final long serialVersionUID = 1L;

	/** 변환추적 테이블 키. */
	private String trackNo; // SEQUENCE

	/** 이전 사용자 Key. */
	private String preUserKey; // PRE_INSD_USERMBR_NO

	/** 이후 사용자 Key. */
	private String afterUserKey; // AFTER_INSD_USERMBR_NO

	/** 등록 ID. */
	private String regID; // REG_ID

	/** 등록일시. */
	private String regDate; // REG_DT

	/**
	 * 변환추적 테이블 키를 리턴한다.
	 * 
	 * @return trackNo - 변환추적 테이블 키
	 */
	public String getTrackNo() {
		return this.trackNo;
	}

	/**
	 * 변환추적 테이블 키를 설정한다.
	 * 
	 * @param trackNo
	 *            변환추적 테이블 키
	 */
	public void setTrackNo(String trackNo) {
		this.trackNo = trackNo;
	}

	/**
	 * 이전 사용자 Key를 리턴한다.
	 * 
	 * @return preUserKey - 이전 사용자 Key
	 */
	public String getPreUserKey() {
		return this.preUserKey;
	}

	/**
	 * 이전 사용자 Key를 설정한다.
	 * 
	 * @param preUserKey
	 *            이전 사용자 Key
	 */
	public void setPreUserKey(String preUserKey) {
		this.preUserKey = preUserKey;
	}

	/**
	 * 이후 사용자 Key를 리턴한다.
	 * 
	 * @return afterUserKey - 이후 사용자 Key
	 */
	public String getAfterUserKey() {
		return this.afterUserKey;
	}

	/**
	 * 이후 사용자 Key를 설정한다.
	 * 
	 * @param afterUserKey
	 *            이후 사용자 Key
	 */
	public void setAfterUserKey(String afterUserKey) {
		this.afterUserKey = afterUserKey;
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
