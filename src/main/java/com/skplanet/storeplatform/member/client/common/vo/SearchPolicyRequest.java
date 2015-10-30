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
import java.util.List;

import com.skplanet.storeplatform.framework.core.common.vo.CommonInfo;
import com.skplanet.storeplatform.member.client.common.util.Utils;

/**
 * 제한 정책 목록 조회 요청 Value Object
 * 
 * Updated on : 2014. 01. 08. Updated by : wisestone_mikepark
 */
public class SearchPolicyRequest extends CommonInfo implements Serializable {
	/** The Constant serial Version UID. */
	private static final long serialVersionUID = 1L;

	/** 공통 요청 Value Object. */
	private CommonRequest commonRequest;

	/** 제한 정책 키. */
	private String limitPolicyKey; // LIMIT_POLICY_KEY

	/** 제한 정책 테이블 코드 목록. */
	private List<String> limitPolicyCodeList;

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
	 * 제한 정책 키를 리턴한다.
	 * 
	 * @return limitPolicyKey - 제한 정책 키
	 */
	public String getLimitPolicyKey() {
		return this.limitPolicyKey;
	}

	/**
	 * 제한 정책 키를 설정한다.
	 * 
	 * @param limitPolicyKey
	 *            제한 정책 키
	 */
	public void setLimitPolicyKey(String limitPolicyKey) {
		this.limitPolicyKey = limitPolicyKey;
	}

	/**
	 * 제한 정책 테이블 코드 목록을 리턴한다.
	 * 
	 * @return limitPolicyCodeList - 제한 정책 테이블 코드 목록
	 */
	public List<String> getLimitPolicyCodeList() {
		return this.limitPolicyCodeList;
	}

	/**
	 * 제한 정책 테이블 코드 목록을 설정한다.
	 * 
	 * @param limitPolicyCodeList
	 *            제한 정책 테이블 코드 목록
	 */
	public void setLimitPolicyCodeList(List<String> limitPolicyCodeList) {
		this.limitPolicyCodeList = limitPolicyCodeList;
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
