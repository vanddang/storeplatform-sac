package com.skplanet.storeplatform.sac.client.member.vo.seller;

import java.util.List;

import org.codehaus.jackson.map.annotate.JsonSerialize;
import org.codehaus.jackson.map.annotate.JsonSerialize.Inclusion;
import org.hibernate.validator.constraints.NotBlank;

import com.skplanet.storeplatform.framework.core.common.vo.CommonInfo;

/**
 * Password 보안 질문 확인
 * 
 * Updated on : 2014. 1. 20. Updated by : 한서구, 부르칸.
 */
@JsonSerialize(include = Inclusion.NON_NULL)
public class CheckPasswordReminderQuestionReq extends CommonInfo {

	private static final long serialVersionUID = 1L;

	/** 판매자회원 ID. */
	@NotBlank
	private String sellerId; // SELLERMBR_ID 판매자회원 id

	/** 비밀번호 보안질문 Value Object. */
	private List<PwReminder> pwReminderList;

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

	/**
	 * @return the pwReminderList
	 */
	public List<PwReminder> getPwReminderList() {
		return this.pwReminderList;
	}

	/**
	 * @param pwReminderList
	 *            the pwReminderList to set
	 */
	public void setPwReminderList(List<PwReminder> pwReminderList) {
		this.pwReminderList = pwReminderList;
	}

	/**
	 * 보안질문 정보
	 * 
	 * Updated on : 2014. 3. 24. Updated by : Rejoice, Burkhan
	 */
	public static class PwReminder {
		/** 보안질문 답변. */
		@NotBlank
		private String answerString;

		/** 보안질문 ID. */
		@NotBlank
		private String questionId;

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

	}
}
