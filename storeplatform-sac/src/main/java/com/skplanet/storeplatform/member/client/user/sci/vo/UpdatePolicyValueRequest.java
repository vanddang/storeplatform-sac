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
import com.skplanet.storeplatform.member.client.common.vo.CommonRequest;

/**
 * 제한 정책 Value 수정 요청 Value Object
 * 
 * Updated on : 2014. 02. 25. Updated by : wisestone_mikepark
 */
public class UpdatePolicyValueRequest extends CommonInfo implements Serializable {
	/** The Constant serial Version UID. */
	private static final long serialVersionUID = 1L;

	/** 공통 요청 Value Object. */
	private CommonRequest commonRequest;

	/** NEW 제한 정책 값. */
	private String newApplyValue; // POLICY_APPLY_VALUE

	/** OLD 제한 정책 값. */
	private String oldApplyValue; // POLICY_APPLY_VALUE

	/**
	 * 휴면계정유무.
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
	 * new 제한 정책 값를 리턴한다.
	 * 
	 * @return newApplyValue - new 제한 정책 값
	 */
	public String getNewApplyValue() {
		return this.newApplyValue;
	}

	/**
	 * new 제한 정책 값를 설정한다.
	 * 
	 * @param newApplyValue
	 *            제한 정책 값
	 */
	public void setNewApplyValue(String newApplyValue) {
		this.newApplyValue = newApplyValue;
	}

	/**
	 * old 제한 정책 값를 리턴한다.
	 * 
	 * @return oldApplyValue - old 제한 정책 값
	 */
	public String getOldApplyValue() {
		return this.oldApplyValue;
	}

	/**
	 * old 제한 정책 값를 설정한다.
	 * 
	 * @param oldApplyValue
	 *            old 제한 정책 값
	 */
	public void setOldApplyValue(String oldApplyValue) {
		this.oldApplyValue = oldApplyValue;
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
