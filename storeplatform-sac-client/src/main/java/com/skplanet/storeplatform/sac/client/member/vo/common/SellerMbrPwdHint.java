package com.skplanet.storeplatform.sac.client.member.vo.common;

import org.codehaus.jackson.map.annotate.JsonSerialize;
import org.codehaus.jackson.map.annotate.JsonSerialize.Inclusion;

import com.skplanet.storeplatform.framework.core.common.vo.CommonInfo;

/**
 * 비밀번호 힌트 Value Object
 * 
 * Updated on : 2014. 1. 7. Updated by : 한서구, 부르칸.
 */
@JsonSerialize(include = Inclusion.NON_NULL)
public class SellerMbrPwdHint extends CommonInfo {

	private static final long serialVersionUID = 1L;

	/** 언어 코드. */
	private String languageCode;

	/** 보안질문 ID. */
	private String questionId;

	/** 보안질문 직접입력 값. */
	private String questionMessage;

	/** 보안질문 명. */
	private String questionName;

	/** 보안질문 설명. */
	private String questionDescription;

	/** 전시 여부. */
	private String isDisplay;

	/** 전시 순서. */
	private String displayOrder;

	/**
	 * @return the languageCode
	 */
	public String getLanguageCode() {
		return this.languageCode;
	}

	/**
	 * @param languageCode
	 *            the languageCode to set
	 */
	public void setLanguageCode(String languageCode) {
		this.languageCode = languageCode;
	}

	/**
	 * @return the questionId
	 */
	public String getQuestionId() {
		return this.questionId;
	}

	/**
	 * @param questionId
	 *            the questionId to set
	 */
	public void setQuestionId(String questionId) {
		this.questionId = questionId;
	}

	/**
	 * @return the questionMessage
	 */
	public String getQuestionMessage() {
		return this.questionMessage;
	}

	/**
	 * @param questionMessage
	 *            the questionMessage to set
	 */
	public void setQuestionMessage(String questionMessage) {
		this.questionMessage = questionMessage;
	}

	/**
	 * @return the questionName
	 */
	public String getQuestionName() {
		return this.questionName;
	}

	/**
	 * @param questionName
	 *            the questionName to set
	 */
	public void setQuestionName(String questionName) {
		this.questionName = questionName;
	}

	/**
	 * @return the questionDescription
	 */
	public String getQuestionDescription() {
		return this.questionDescription;
	}

	/**
	 * @param questionDescription
	 *            the questionDescription to set
	 */
	public void setQuestionDescription(String questionDescription) {
		this.questionDescription = questionDescription;
	}

	/**
	 * @return the isDisplay
	 */
	public String getIsDisplay() {
		return this.isDisplay;
	}

	/**
	 * @param isDisplay
	 *            the isDisplay to set
	 */
	public void setIsDisplay(String isDisplay) {
		this.isDisplay = isDisplay;
	}

	/**
	 * @return the displayOrder
	 */
	public String getDisplayOrder() {
		return this.displayOrder;
	}

	/**
	 * @param displayOrder
	 *            the displayOrder to set
	 */
	public void setDisplayOrder(String displayOrder) {
		this.displayOrder = displayOrder;
	}

}
