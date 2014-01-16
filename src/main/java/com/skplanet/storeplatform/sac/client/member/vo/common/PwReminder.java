package com.skplanet.storeplatform.sac.client.member.vo.common;

import javax.validation.constraints.NotNull;

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
	@NotNull
	private String answerString;

	/** 보안질문 ID. */
	@NotBlank
	@NotNull
	private String questionID;

	/** 보안질문 직접입력 값. */
	@NotBlank
	@NotNull
	private String questionMessage;

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
}
