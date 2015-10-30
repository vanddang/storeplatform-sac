/*
 * Copyright (c) 2013 SK planet.
 * All right reserved.
 * 
 * This software is the confidential and proprietary information of SK planet.
 * You shall not disclose such Confidential Information and
 * shall use it only in accordance with the terms of the license agreement
 * you entered into with SK planet.
 */
package com.skplanet.storeplatform.member.client.seller.sci.vo;

import java.io.Serializable;
import java.util.List;

import com.skplanet.storeplatform.framework.core.common.vo.CommonInfo;
import com.skplanet.storeplatform.member.client.common.util.Utils;
import com.skplanet.storeplatform.member.client.common.vo.CommonResponse;

/**
 * 비밀번호 힌트 목록 조회 응답 Value Object
 * 
 * Updated on : 2014. 01. 14. Updated by : wisestone_mikepark
 */
public class SearchPwdHintListResponse extends CommonInfo implements Serializable {
	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/** 공통 응답 Value Object. */
	private CommonResponse commonResponse;

	/** 비밀번호 보안질문 Value Object 목록. */
	private List<PWReminder> pWReminderList;

	/**
	 * 비밀번호 보안질문 Value Object 목록를 리턴한다.
	 * 
	 * @return pWReminderList - 비밀번호 보안질문 Value Object 목록
	 */
	public List<PWReminder> getPWReminderList() {
		return this.pWReminderList;
	}

	/**
	 * 비밀번호 보안질문 Value Object 목록를 설정한다.
	 * 
	 * @param pWReminderList
	 *            비밀번호 보안질문 Value Object 목록
	 */
	public void setPWReminderList(List<PWReminder> pWReminderList) {
		this.pWReminderList = pWReminderList;
	}

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
