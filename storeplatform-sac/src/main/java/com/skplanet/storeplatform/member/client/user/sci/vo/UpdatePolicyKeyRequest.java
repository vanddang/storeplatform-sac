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
 * 제한 정책 Key 수정 요청 Value Object
 * 
 * Updated on : 2014. 02. 25. Updated by : wisestone_mikepark
 */
public class UpdatePolicyKeyRequest extends CommonInfo implements Serializable {
	/** The Constant serial Version UID. */
	private static final long serialVersionUID = 1L;

	/** 공통 요청 Value Object. */
	private CommonRequest commonRequest;

	/** NEW 제한 정책 키. */
	private String newLimitPolicyKey; // LIMIT_POLICY_KEY

	/** OLD 제한 정책 키. */
	private String oldLimitPolicyKey; // LIMIT_POLICY_KEY

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
	 * new 제한 정책 키를 리턴한다.
	 * 
	 * @return newLimitPolicyKey - new 제한 정책 키
	 */
	public String getNewLimitPolicyKey() {
		return this.newLimitPolicyKey;
	}

	/**
	 * new 제한 정책 키를 설정한다.
	 * 
	 * @param newLimitPolicyKey
	 *            제한 정책 키
	 */
	public void setNewLimitPolicyKey(String newLimitPolicyKey) {
		this.newLimitPolicyKey = newLimitPolicyKey;
	}

	/**
	 * old 제한 정책 키를 리턴한다.
	 * 
	 * @return oldLimitPolicyKey - old 제한 정책 키
	 */
	public String getOldLimitPolicyKey() {
		return this.oldLimitPolicyKey;
	}

	/**
	 * old 제한 정책 키를 설정한다.
	 * 
	 * @param oldLimitPolicyKey
	 *            old 제한 정책 키
	 */
	public void setOldLimitPolicyKey(String oldLimitPolicyKey) {
		this.oldLimitPolicyKey = oldLimitPolicyKey;
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
