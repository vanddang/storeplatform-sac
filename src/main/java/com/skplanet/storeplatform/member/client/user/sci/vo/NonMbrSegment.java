/*
 * Copyright (c) 2014 SK planet.
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
 * 유통망 추천앱 비회원 정보 Value Object
 * 
 * Updated on : 2014. 02. 13. Updated by : 황정택, 와이즈스톤.
 */
public class NonMbrSegment extends CommonInfo implements Serializable {
	/** The Constant serial Version UID. */
	private static final long serialVersionUID = 1L;

	/** 사용자 Key. */
	private String userKey; // INSD_USERMBR_NO

	/** 휴대기기 ID(MDN/UUID/MAC). */
	private String deviceID; // DEVICE_ID

	/** 휴대기기 통합 관리 번호. */
	private String svcMangNum; // SVC_MANG_NO

	/** 비회원 체크횟수. */
	int checkCount; // NONMBR_CHECK_CNT

	/** 처리 여부. */
	private String isProcessed; // PROC_YN

	/** 등록일시. */
	private String regDate; // REG_DT

	/** 처리 일시. */
	private String processDate; // PROC_DT

	/**
	 * 사용자 Key를 리턴한다.
	 * 
	 * @return userKey - 사용자 Key
	 */
	public String getUserKey() {
		return this.userKey;
	}

	/**
	 * 사용자 Key를 리턴한다.
	 * 
	 * @param userKey
	 *            사용자 Key
	 */
	public void setUserKey(String userKey) {
		this.userKey = userKey;
	}

	/**
	 * 휴대기기 ID(MDN/UUID/MAC) 정보를 리턴한다.
	 * 
	 * @return deviceID - 휴대기기 ID(MDN/UUID/MAC) 정보
	 */
	public String getDeviceID() {
		return this.deviceID;
	}

	/**
	 * 휴대기기 ID(MDN/UUID/MAC) 정보를 설정한다.
	 * 
	 * @param deviceID
	 *            휴대기기 ID(MDN/UUID/MAC) 정보
	 */
	public void setDeviceID(String deviceID) {
		this.deviceID = deviceID;
	}

	/**
	 * 휴대기기 통합관리번호를 리턴한다.
	 * 
	 * @return svcMangNum - 통합관리번호
	 */
	public String getSvcMangNum() {
		return this.svcMangNum;
	}

	/**
	 * 휴대기기 통합관리번호를 설정한다.
	 * 
	 * @param svcMangNum
	 *            통합관리번호
	 */
	public void setSvcMangNum(String svcMangNum) {
		this.svcMangNum = svcMangNum;
	}

	/**
	 * 비회원 체크횟수를 리턴한다.
	 * 
	 * @return checkCount - 비회원 체크횟수
	 */
	public int getcheckCount() {
		return this.checkCount;
	}

	/**
	 * 비회원 체크횟수를 설정한다.
	 * 
	 * @param checkCount
	 *            비회원 체크횟수
	 */
	public void setCheckCount(int checkCount) {
		this.checkCount = checkCount;
	}

	/**
	 * 처리여부을 리턴한다.
	 * 
	 * @return isProcessed - 처리여부
	 */
	public String getIsProcessed() {
		return this.isProcessed;
	}

	/**
	 * 처리여부을 설정한다.
	 * 
	 * @param isProcessed
	 *            처리여부
	 */
	public void setIsProcessed(String isProcessed) {
		this.isProcessed = isProcessed;
	}

	/**
	 * 정보 등록일자를 리턴한다.
	 * 
	 * @return regDate - 등록일자
	 */
	public String getRegDate() {
		return this.regDate;
	}

	/**
	 * 정보 등록일자를 설정한다.
	 * 
	 * @param regDate
	 *            등록일자
	 */
	public void setRegDate(String regDate) {
		this.regDate = regDate;
	}

	/**
	 * 처리일을 리턴한다.
	 * 
	 * @return processDate - 처리일
	 */
	public String getProcessDate() {
		return this.processDate;
	}

	/**
	 * 처리일을 설정한다.
	 * 
	 * @param processDate
	 *            처리일
	 */
	public void setProcessDate(String processDate) {
		this.processDate = processDate;
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
