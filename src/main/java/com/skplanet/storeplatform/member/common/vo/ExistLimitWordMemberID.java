/*
 * Copyright (c) 2013 SK planet.
 * All right reserved.
 * 
 * This software is the confidential and proprietary information of SK planet.
 * You shall not disclose such Confidential Information and
 * shall use it only in accordance with the terms of the license agreement
 * you entered into with SK planet.
 */
package com.skplanet.storeplatform.member.common.vo;

import java.io.Serializable;

import com.skplanet.storeplatform.framework.core.common.vo.CommonInfo;

/**
 * 중복/제한단어 아이디 조회 Value Object
 * 
 * Updated on : 2014. 01. 15. Updated by : wisestone_dinga
 */
public class ExistLimitWordMemberID extends CommonInfo implements Serializable {

	/** The Constant serial Version UID. */
	private static final long serialVersionUID = 1L;

	/** 이미 존재하는 아이디. */
	private String existWordID;

	/** 제한단어 아이디. */
	private String limitWordID;

	/**
	 * 이미 존재하는 아이디를 반환.
	 * 
	 * @return existWordID - 이미 존재하는 아이디
	 */
	public String getExistWordID() {
		return this.existWordID;
	}

	/**
	 * 이미 존재하는 아이디를 설정.
	 * 
	 * @param existWordID
	 *            이미 존재하는 아이디.
	 */
	public void setExistWordID(String existWordID) {
		this.existWordID = existWordID;
	}

	/**
	 * 제한단어 아이디를 반환.
	 * 
	 * @return limitWordID - 제한단어 아이디
	 */
	public String getLimitWordID() {
		return this.limitWordID;
	}

	/**
	 * Sets the limit word id.
	 * 
	 * @param limitWordID
	 *            제한단어 아이디
	 */
	public void setLimitWordID(String limitWordID) {
		this.limitWordID = limitWordID;
	}

}
