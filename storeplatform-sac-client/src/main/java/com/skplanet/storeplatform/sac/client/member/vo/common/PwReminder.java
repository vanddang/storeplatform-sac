package com.skplanet.storeplatform.sac.client.member.vo.common;

import org.codehaus.jackson.map.annotate.JsonSerialize;
import org.codehaus.jackson.map.annotate.JsonSerialize.Inclusion;
import org.hibernate.validator.constraints.NotBlank;

import com.skplanet.storeplatform.framework.core.common.vo.CommonInfo;

/**
 * Password 보안 질문 리스트
 * 
 * Updated on : 2014. 1. 7. Updated by : 한서구, 부르칸.
 */
@JsonSerialize(include = Inclusion.NON_NULL)
public class PwReminder extends CommonInfo {

	private static final long serialVersionUID = 1L;

	/** 보안질문 답변. */
	@NotBlank
	private String answerString;

	/** 보안질문 ID. */
	@NotBlank
	private String questionId;

	/** 보안질문 ID에 해당하는 문구 (직접입력일 경우, 회원이 직접입력한 값). */
	private String questionMessage;

	/** 판매자 키. */
	private String sellerKey;

	/** 판매자회원 ID. */
	private String sellerId;

	/**
	 * @return the answerString
	 */
	public String getAnswerString() {
		return this.answerString;
	}

	/**
	 * @param answerString
	 *            the answerString to set
	 */
	public void setAnswerString(String answerString) {
		this.answerString = answerString;
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
	 * @return the sellerKey
	 */
	public String getSellerKey() {
		return this.sellerKey;
	}

	/**
	 * @param sellerKey
	 *            the sellerKey to set
	 */
	public void setSellerKey(String sellerKey) {
		this.sellerKey = sellerKey;
	}

	/**
	 * @return the sellerId
	 */
	public String getSellerId() {
		return this.sellerId;
	}

	/**
	 * @param sellerId
	 *            the sellerId to set
	 */
	public void setSellerId(String sellerId) {
		this.sellerId = sellerId;
	}

}
