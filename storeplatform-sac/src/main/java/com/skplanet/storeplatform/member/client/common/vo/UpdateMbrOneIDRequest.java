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

/**
 * 미동의 사용자 등록 요청 Value Object
 * 
 * Updated on : 2014. 01. 08. Updated by : wisestone_mikepark
 */
public class UpdateMbrOneIDRequest extends CommonInfo implements Serializable {
	/** The Constant serial Version UID. */
	private static final long serialVersionUID = 1L;

	/** 공통 요청 Value Object. */
	private CommonRequest commonRequest;

	/** 미동의 사용자 관리 Value Object. */
	private MbrOneID mbrOneID;

	/**
	 * 휴면계정 유무.
	 */
	private String isDormant;

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
	 * 미동의 사용자 관리 Value Object를 리턴한다.
	 * 
	 * @return mbrOneID - 미동의 사용자 관리 Value Object
	 */
	public MbrOneID getMbrOneID() {
		return this.mbrOneID;
	}

	/**
	 * 미동의 사용자 관리 Value Object를 설정한다.
	 * 
	 * @param mbrOneID
	 *            미동의 사용자 관리 Value Object
	 */
	public void setMbrOneID(MbrOneID mbrOneID) {
		this.mbrOneID = mbrOneID;
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
