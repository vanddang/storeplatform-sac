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

/**
 * 비밀번호 힌트 Value Object
 * 
 * Updated on : 2014. 01. 14. Updated by : wisestone_mikepark
 */
public class SellerMbrPwdHint extends CommonInfo implements Serializable {
	/** The Constant serial Version UID. */
	private static final long serialVersionUID = 1L;

	/** 언어 코드. */
	private String languageCode;

	/** 보안질문 ID. */
	private String questionID;

	/** 보안질문 명. */
	private String questionName;

	/** 보안질문 설명. */
	private String questionDescription;

	/** 전시 여부. */
	private String isDisplay;

	/** 전시 순서. */
	private int displayOrder;

	/** 등록 ID. */
	private String regID; // REG_ID

	/** 등록일시. */
	private String regDate; // REG_DT

	/** 수정 ID. */
	private String updateID; // UPD_ID

	/** 수정일시. */
	private String updateDate; // UPD_DT

	/**
	 * 언어 코드를 리턴한다.
	 * 
	 * @return languageCode - 언어 코드
	 */
	public String getLanguageCode() {
		return this.languageCode;
	}

	/**
	 * 언어 코드를 설정한다.
	 * 
	 * @param languageCode
	 *            언어 코드
	 */
	public void setLanguageCode(String languageCode) {
		this.languageCode = languageCode;
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
	 * 보안질문 명을 리턴한다.
	 * 
	 * @return questionName - 보안질문 명
	 */
	public String getQuestionName() {
		return this.questionName;
	}

	/**
	 * 보안질문 명을 설정한다.
	 * 
	 * @param questionName
	 *            보안질문 명
	 */
	public void setQuestionName(String questionName) {
		this.questionName = questionName;
	}

	/**
	 * 보안질문 설명을 리턴한다.
	 * 
	 * @return questionDescription - 보안질문 설명
	 */
	public String getQuestionDescription() {
		return this.questionDescription;
	}

	/**
	 * 보안질문 설명을 설정한다.
	 * 
	 * @param questionDescription
	 *            보안질문 설명
	 */
	public void setQuestionDescription(String questionDescription) {
		this.questionDescription = questionDescription;
	}

	/**
	 * 전시 여부를 리턴한다.
	 * 
	 * @return isDisplay - 전시 여부
	 */
	public String getIsDisplay() {
		return this.isDisplay;
	}

	/**
	 * 전시 여부를 설정한다.
	 * 
	 * @param isDisplay
	 *            전시 여부
	 */
	public void setIsDisplay(String isDisplay) {
		this.isDisplay = isDisplay;
	}

	/**
	 * 전시 순서를 리턴한다.
	 * 
	 * @return displayOrder - 전시 순서
	 */
	public int getDisplayOrder() {
		return this.displayOrder;
	}

	/**
	 * 전시 순서를 설정한다.
	 * 
	 * @param displayOrder
	 *            전시 순서
	 */
	public void setDisplayOrder(int displayOrder) {
		this.displayOrder = displayOrder;
	}

	/**
	 * 등록 ID를 리턴한다.
	 * 
	 * @return regID - 등록 ID
	 */
	public String getRegID() {
		return this.regID;
	}

	/**
	 * 등록 ID를 설정한다.
	 * 
	 * @param regID
	 *            등록 ID
	 */
	public void setRegID(String regID) {
		this.regID = regID;
	}

	/**
	 * 등록일시를 리턴한다.
	 * 
	 * @return regDate - 등록일시
	 */
	public String getRegDate() {
		return this.regDate;
	}

	/**
	 * 등록일시를 설정한다.
	 * 
	 * @param regDate
	 *            등록일시
	 */
	public void setRegDate(String regDate) {
		this.regDate = regDate;
	}

	/**
	 * 수정 ID를 리턴한다.
	 * 
	 * @return updateID - 수정 ID
	 */
	public String getUpdateID() {
		return this.updateID;
	}

	/**
	 * 수정 ID를 설정한다.
	 * 
	 * @param updateID
	 *            수정 ID
	 */
	public void setUpdateID(String updateID) {
		this.updateID = updateID;
	}

	/**
	 * 수정일시를 리턴한다.
	 * 
	 * @return updateDate - 수정일시
	 */
	public String getUpdateDate() {
		return this.updateDate;
	}

	/**
	 * 수정일시를 설정한다.
	 * 
	 * @param updateDate
	 *            수정일시
	 */
	public void setUpdateDate(String updateDate) {
		this.updateDate = updateDate;
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
