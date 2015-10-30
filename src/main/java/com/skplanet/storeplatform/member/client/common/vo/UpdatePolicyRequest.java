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
 * 제한 정책 등록/수정 요청 Value Object
 * 
 * Updated on : 2014. 01. 08. Updated by : wisestone_mikepark
 */
public class UpdatePolicyRequest extends CommonInfo implements Serializable {
	/** The Constant serial Version UID. */
	private static final long serialVersionUID = 1L;

	/** 공통 요청 Value Object. */
	private CommonRequest commonRequest;

	/**
	 * <PRE>
	 * 제한 정책정보 Value Object 목록.
	 * 
	 * 신규 등록 경우, Mandatory Fields
	 * limitPolicyCode, limitPolicyKey, regID
	 * 
	 * 수정 경우, 필수 검색필드
	 * limitPolicyCode, limitPolicyKey
	 * 
	 * 수정 가능한 필드
	 * policyApplyValue, updateID
	 * </PRE>
	 */
	private List<LimitTarget> limitTargetList;

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
	 * 제한 정책정보 Value Object 목록을 리턴한다.
	 * 
	 * @return limitTargetList - 제한 정책정보 Value Object 목록
	 */
	public List<LimitTarget> getLimitTargetList() {
		return this.limitTargetList;
	}

	/**
	 * 제한 정책정보 Value Object 목록을 설정한다.
	 * 
	 * @param limitTargetList
	 *            제한 정책정보 Value Object 목록
	 */
	public void setLimitTargetList(List<LimitTarget> limitTargetList) {
		this.limitTargetList = limitTargetList;
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
