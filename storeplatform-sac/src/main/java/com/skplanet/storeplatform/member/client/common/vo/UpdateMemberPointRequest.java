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
 * OCB 등록/수정 요청 Value Object
 * 
 * Updated on : 2014. 01. 09. Updated by : wisestone_mikepark
 */
public class UpdateMemberPointRequest extends CommonInfo implements Serializable {
	/** The Constant serial Version UID. */
	private static final long serialVersionUID = 1L;

	/** 공통 요청 Value Object. */
	private CommonRequest commonRequest;

	/**
	 * <PRE>
	 * MemberPoint Value Object. 
	 * 
	 * 신규 등록 경우, Mandatory Fields
	 * userKey, authMethodCode, isUsed, cardNumber, startDate
	 * 
	 * 수정 경우, 필수 검색필드
	 * userKey, cardNumber
	 * 
	 * 수정 가능한 필드
	 * authMethodCode, isUsed, startDate, endDate
	 * </PRE>
	 */
	private MemberPoint memberPoint;

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
	 * MemberPoint Value Object을 리턴한다.
	 * 
	 * @return memberPoint - MemberPoint Value Object
	 */
	public MemberPoint getMemberPoint() {
		return this.memberPoint;
	}

	/**
	 * MemberPoint Value Object을 설정한다.
	 * 
	 * @param memberPoint
	 *            MemberPoint Value Object
	 */
	public void setMemberPoint(MemberPoint memberPoint) {
		this.memberPoint = memberPoint;
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
