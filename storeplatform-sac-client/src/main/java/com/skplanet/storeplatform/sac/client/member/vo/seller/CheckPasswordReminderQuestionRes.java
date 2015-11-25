package com.skplanet.storeplatform.sac.client.member.vo.seller;

import org.codehaus.jackson.map.annotate.JsonSerialize;
import org.codehaus.jackson.map.annotate.JsonSerialize.Inclusion;

import com.skplanet.storeplatform.framework.core.common.vo.CommonInfo;

/**
 * Password 보안 질문 확인
 * 
 * Updated on : 2014. 1. 7. Updated by : 한서구, 부르칸.
 */
@JsonSerialize(include = Inclusion.NON_NULL)
public class CheckPasswordReminderQuestionRes extends CommonInfo {

	private static final long serialVersionUID = 1L;

	/**
	 * 보안질문 일치 여부 (Y/N).
	 */
	private String isCorrect;

	/**
	 * @return the isCorrect
	 */
	public String getIsCorrect() {
		return this.isCorrect;
	}

	/**
	 * @param isCorrect
	 *            the isCorrect to set
	 */
	public void setIsCorrect(String isCorrect) {
		this.isCorrect = isCorrect;
	}

}
