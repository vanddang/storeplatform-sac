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

import com.skplanet.storeplatform.member.client.common.vo.CommonRequest;

/**
 * 사용자회원_ONEID 삭제 요청 Value Object
 * 
 * Updated on : 2013. 12. 10. Updated by : wisestone_mikepark
 */
public class RemoveMbrOneIDRequest {

	/** 공통 요청 Value Object. */
	private CommonRequest commonRequest;

	/** 통합 서비스 관리번호. */
	private String imSvcNo; // INTG_SVC_NO : 통합서비스 관리번호

	/**
	 * 휴면계정유무.
	 */
	private String isDormant;

	/**
	 * 통합서비스 관리번호를 리턴한다.
	 * 
	 * @return imSvcNo - 통합서비스 관리번호
	 */
	public String getImSvcNo() {
		return this.imSvcNo;
	}

	/**
	 * 통합서비스 관리번호를 설정한다.
	 * 
	 * @param imSvcNo
	 *            통합서비스 관리번호
	 */
	public void setImSvcNo(String imSvcNo) {
		this.imSvcNo = imSvcNo;
	}

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

}
