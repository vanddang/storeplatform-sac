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
	private String questionID;

	/** 보안질문 직접입력 값. */
	@NotBlank
	private String questionMessage;

	/** 판매자 키. */
	private String sellerKey;

	/** 판매자회원 ID. */
	private String sellerId;

	public String getSellerId() {
		return this.sellerId;
	}

	public void setSellerId(String sellerId) {
		this.sellerId = sellerId;
	}

	public String getAnswerString() {
		return this.answerString;
	}

	public void setAnswerString(String answerString) {
		this.answerString = answerString;
	}

	public String getQuestionID() {
		return this.questionID;
	}

	public void setQuestionID(String questionID) {
		this.questionID = questionID;
	}

	public String getQuestionMessage() {
		return this.questionMessage;
	}

	public void setQuestionMessage(String questionMessage) {
		this.questionMessage = questionMessage;
	}

	public String getSellerKey() {
		return this.sellerKey;
	}

	public void setSellerKey(String sellerKey) {
		this.sellerKey = sellerKey;
	}

}
