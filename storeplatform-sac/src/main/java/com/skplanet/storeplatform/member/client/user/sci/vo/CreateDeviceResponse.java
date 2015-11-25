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
import java.util.List;

import com.skplanet.storeplatform.framework.core.common.vo.CommonInfo;
import com.skplanet.storeplatform.member.client.common.util.Utils;
import com.skplanet.storeplatform.member.client.common.vo.CommonResponse;
import com.skplanet.storeplatform.member.client.common.vo.MbrMangItemPtcr;

/**
 * 사용자 휴대기기기 등록/수정 응답 Value Object
 * 
 * Updated on : 2014. 1. 3 Updated by : wisestone_brian, wisestone
 */
public class CreateDeviceResponse extends CommonInfo implements Serializable {

	/** The Constant serial Version UID. */
	private static final long serialVersionUID = 1L;

	/** 공통 응답 Value Object. */
	private CommonResponse commonResponse;

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

	/** 사용자 Key. */
	private String userKey;

	/** 기기 Key. */
	private String deviceKey;

	/** 기존 기기 Key. */
	private String previousDeviceKey;

	/** 기존 사용자 Key. */
	private String previousUserKey;

	/** 이전 사용자회원번호. */
	private String preMbrNo; // PRE_USERMBR_NO

	/** 이전 디바이스 key. */
	private String preDeviceKey;
	/** 이전 사용자 key. */
	private String preUserKey;

	private String previousUserID;

	/** 이전 사용자 휴면계정유무. */
	private String previousIsDormant;

	/**
	 * 휴대기기 등록한 사용자 휴면계정유무.
	 */
	private String isDormant;

	/**
	 * 사용자 부가정보 및 관리항목 Value Object List.
	 */
	private List<MbrMangItemPtcr> mbrMangItemPtcrList;

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
	 * 사용자 휴대기기 Key를 리턴한다.
	 * 
	 * @return deviceKey - 사용자 휴대기기 Key
	 */
	public String getDeviceKey() {
		return this.deviceKey;
	}

	/**
	 * 사용자 휴대기기 Key를 설정한다.
	 * 
	 * @param deviceKey
	 *            사용자 휴대기기 Key
	 */
	public void setDeviceKey(String deviceKey) {
		this.deviceKey = deviceKey;
	}

	/**
	 * 기존 휴대기기 Key를 리턴한다.
	 * 
	 * @return previousDeviceKey - 기존 휴대기기 Key
	 */
	public String getPreviousDeviceKey() {
		return this.previousDeviceKey;
	}

	/**
	 * 기존 휴대기기 Key를 설정한다.
	 * 
	 * @param previousDeviceKey
	 *            기존 휴대기기 Key
	 */
	public void setPreviousDeviceKey(String previousDeviceKey) {
		this.previousDeviceKey = previousDeviceKey;
	}

	/**
	 * 기존 사용자 Key를 리턴한다.
	 * 
	 * @return previousUserKey - 기존 사용자 Key
	 */
	public String getPreviousUserKey() {
		return this.previousUserKey;
	}

	/**
	 * 기존 사용자 Key를 설정한다.
	 * 
	 * @param previousUserKey
	 *            기존 사용자 Key
	 */
	public void setPreviousUserKey(String previousUserKey) {
		this.previousUserKey = previousUserKey;
	}

	/**
	 * 이전 사용자회원번호를 리턴한다.
	 * 
	 * @return preMbrNo - 이전 사용자회원번호
	 */
	public String getPreMbrNo() {
		return this.preMbrNo;
	}

	/**
	 * 이전 사용자회원번호를 설정한다.
	 * 
	 * @param preMbrNo
	 *            이전 사용자회원번호
	 */
	public void setPreMbrNo(String preMbrNo) {
		this.preMbrNo = preMbrNo;
	}

	/**
	 * 이전 기기 key를 리턴한다.
	 * 
	 * @return the preDeviceKey
	 */
	public String getPreDeviceKey() {
		return this.preDeviceKey;
	}

	/**
	 * 이전 기기 key를 설정한다.
	 * 
	 * @param preDeviceKey
	 *            the preDeviceKey to set
	 */
	public void setPreDeviceKey(String preDeviceKey) {
		this.preDeviceKey = preDeviceKey;
	}

	/**
	 * 이전 사용자 key를 리턴한다.
	 * 
	 * @return the preUserKey
	 */
	public String getPreUserKey() {
		return this.preUserKey;
	}

	/**
	 * 이전 사용자 key를 설정한다.
	 * 
	 * @param preUserKey
	 *            the preUserKey to set
	 */
	public void setPreUserKey(String preUserKey) {
		this.preUserKey = preUserKey;
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
	 * @return the previousUserID
	 */
	public String getPreviousUserID() {
		return this.previousUserID;
	}

	/**
	 * @param previousUserID
	 *            the previousUserID to set
	 */
	public void setPreviousUserID(String previousUserID) {
		this.previousUserID = previousUserID;
	}

	/**
	 * @return previousIsDormant
	 */
	public String getPreviousIsDormant() {
		return this.previousIsDormant;
	}

	/**
	 * @param previousIsDormant
	 *            String
	 */
	public void setPreviousIsDormant(String previousIsDormant) {
		this.previousIsDormant = previousIsDormant;
	}

	/**
	 * @return the mbrMangItemPtcrList
	 */
	public List<MbrMangItemPtcr> getMbrMangItemPtcrList() {
		return this.mbrMangItemPtcrList;
	}

	/**
	 * @param mbrMangItemPtcrList
	 *            the mbrMangItemPtcrList to set
	 */
	public void setMbrMangItemPtcrList(List<MbrMangItemPtcr> mbrMangItemPtcrList) {
		this.mbrMangItemPtcrList = mbrMangItemPtcrList;
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
