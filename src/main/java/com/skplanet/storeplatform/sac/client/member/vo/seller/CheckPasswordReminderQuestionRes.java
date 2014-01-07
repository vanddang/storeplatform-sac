package com.skplanet.storeplatform.sac.client.member.vo.seller;

import org.codehaus.jackson.map.annotate.JsonSerialize;
import org.codehaus.jackson.map.annotate.JsonSerialize.Inclusion;

import com.skplanet.storeplatform.framework.core.common.vo.CommonInfo;

@JsonSerialize(include = Inclusion.NON_NULL)
public class CheckPasswordReminderQuestionRes extends CommonInfo {

	private static final long serialVersionUID = 1L;

	private String isCorrect;

	public String getIsCorrect() {
		return this.isCorrect;
	}

	public void setIsCorrect(String isCorrect) {
		this.isCorrect = isCorrect;
	}

}
