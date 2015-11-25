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

import com.skplanet.storeplatform.framework.core.common.vo.CommonInfo;
import com.skplanet.storeplatform.member.client.common.util.Utils;
import com.skplanet.storeplatform.member.client.common.vo.CommonRequest;

/**
 * 비밀번호 힌트 목록 조회 요청 Value Object
 * 
 * Updated on : 2014. 01. 14. Updated by : wisestone_mikepark
 */
public class SearchPwdHintListRequest extends CommonInfo implements Serializable {
	/** The Constant serial Version UID. */
	private static final long serialVersionUID = 1L;

	/** 공통 요청 Value Object. */
	private CommonRequest commonRequest;

	/** 판매자 키. [Mandatory] */
	private String sellerKey;

	/**
	 * 판매자 키를 리턴한다.
	 * 
	 * @return sellerKey - 판매자 키
	 */
	public String getSellerKey() {
		return this.sellerKey;
	}

	/** 언어 코드. */
	private String languageCode;

	/**
	 * 언어코드를 리턴한다.
	 * 
	 * @return languageCode - 언어코드
	 */
	public String getLanguageCode() {
		return this.languageCode;
	}

	/**
	 * 언어코드를 설정한다.
	 * 
	 * @param languageCode
	 *            언어코드
	 */
	public void setLanguageCode(String languageCode) {
		this.languageCode = languageCode;
	}

	/**
	 * 판매자 키를 설정한다.
	 * 
	 * @param sellerKey
	 *            판매자 키
	 */
	public void setSellerKey(String sellerKey) {
		this.sellerKey = sellerKey;
	}

	/** 보안질문 ID. [Optional] */
	private String questionID;

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
	 * 보안질문 ID를 리턴한다.
	 * 
	 * @return questionID - 보안질문 ID
	 */
	public String getQuestionID() {
		return this.questionID;
	}

	/**
	 * 보안질문 ID를 설정한다.
	 * 
	 * @param questionID
	 *            보안질문 ID
	 */
	public void setQuestionID(String questionID) {
		this.questionID = questionID;
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
