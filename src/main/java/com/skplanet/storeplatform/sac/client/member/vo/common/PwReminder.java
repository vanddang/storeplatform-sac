package com.skplanet.storeplatform.sac.client.member.vo.common;

import org.codehaus.jackson.map.annotate.JsonSerialize;
import org.codehaus.jackson.map.annotate.JsonSerialize.Inclusion;

import com.skplanet.storeplatform.framework.core.common.vo.CommonInfo;

/**
 * Password 보안 질문 리스트
 * 
 * Updated on : 2014. 1. 7. Updated by : 한서구, 부르칸.
 */
@JsonSerialize(include = Inclusion.NON_NULL)
public class PwReminder extends CommonInfo {

	private static final long serialVersionUID = 1L;

	private String pwReminderCode;
	private String pwReminderQ;

	public String getPwReminderCode() {
		return this.pwReminderCode;
	}

	public void setPwReminderCode(String pwReminderCode) {
		this.pwReminderCode = pwReminderCode;
	}

	public String getPwReminderQ() {
		return this.pwReminderQ;
	}

	public void setPwReminderQ(String pwReminderQ) {
		this.pwReminderQ = pwReminderQ;
	}

}
