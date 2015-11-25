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

// TODO: Auto-generated Javadoc
/**
 * 보안질문 Value Object.
 * 
 * Updated on : 2013. 12. 10. Updated by : wisestone_mikepark
 */
public class PWReminder extends CommonInfo implements Serializable {
	/** The Constant serial Version UID. */
	private static final long serialVersionUID = 1L;

	/** 판매자 키. */
	private String sellerKey;

	/** 판매자회원 ID. */
	private String sellerID;

	/** 보안질문 명. */
	private String questionName;

	/**
	 * 보안질문 설명 값를 리턴한다.
	 * 
	 * @return questionName - 보안질문 명 값
	 */
	public String getQuestionName() {
		return this.questionName;
	}

	/**
	 * 보안질문 설명 값를 설정한다.
	 * 
	 * @param questionName
	 *            보안질문 명 값
	 */
	public void setQuestionName(String questionName) {
		this.questionName = questionName;
	}

	/** 보안질문 답변. */
	private String answerString;

	/** 보안질문 ID. */
	private String questionID;

	/** 보안질문 직접입력 값. */
	private String questionMessage;

	/** 수정일시. */
	private String updateDate; // UPD_DT

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
	 * 판매자 키를 리턴한다.
	 * 
	 * @return sellerKey - 판매자 키
	 */
	public String getSellerKey() {
		return this.sellerKey;
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

	/**
	 * 판매자회원 ID를 리턴한다.
	 * 
	 * @return sellerID - 판매자회원 ID
	 */
	public String getSellerID() {
		return this.sellerID;
	}

	/**
	 * 판매자회원 ID를 설정한다.
	 * 
	 * @param sellerID
	 *            판매자회원 ID
	 */
	public void setSellerID(String sellerID) {
		this.sellerID = sellerID;
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
	 * 보안질문 답변을 리턴한다.
	 * 
	 * @return answerString - 보안질문 답변
	 */
	public String getAnswerString() {
		return this.answerString;
	}

	/**
	 * 보안질문 답변을 설정한다.
	 * 
	 * @param answerString
	 *            보안질문 답변
	 */
	public void setAnswerString(String answerString) {
		this.answerString = answerString;
	}

	/**
	 * 보안질문 직접입력 값를 리턴한다.
	 * 
	 * @return questionMessage - 보안질문 직접입력 값
	 */
	public String getQuestionMessage() {
		return this.questionMessage;
	}

	/**
	 * 보안질문 직접입력 값를 설정한다.
	 * 
	 * @param questionMessage
	 *            보안질문 직접입력 값
	 */
	public void setQuestionMessage(String questionMessage) {
		this.questionMessage = questionMessage;
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
