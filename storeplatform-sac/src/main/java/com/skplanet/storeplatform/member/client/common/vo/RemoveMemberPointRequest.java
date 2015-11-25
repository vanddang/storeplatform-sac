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
 * MemberPoint 삭제 요청 Value Object
 * 
 * Updated on : 2014. 01. 08. Updated by : wisestone_mikepark
 */
public class RemoveMemberPointRequest extends CommonInfo implements Serializable {
	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/** 공통 요청 Value Object. */
	private CommonRequest commonRequest;

	/**
	 * <PRE>
	 * MemberPoint Value Object 목록.
	 * 
	 * 삭제 조건 Mandatory Fields
	 * userKey, authMethodCode, isUsed, cardNumber, startDate
	 * 
	 * </PRE>
	 */
	private List<MemberPoint> memberPointList;

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
	 * MemberPoint 목록을 리턴한다.
	 * 
	 * @return memberPointList - MemberPoint 목록
	 */
	public List<MemberPoint> getMemberPointList() {
		return this.memberPointList;
	}

	/**
	 * MemberPoint 목록을 설정한다.
	 * 
	 * @param memberPointList
	 *            MemberPoint 목록
	 */
	public void setMemberPointList(List<MemberPoint> memberPointList) {
		this.memberPointList = memberPointList;
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
