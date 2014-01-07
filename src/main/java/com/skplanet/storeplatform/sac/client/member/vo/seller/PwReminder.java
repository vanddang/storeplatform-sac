package com.skplanet.storeplatform.sac.client.member.vo.seller;

import org.codehaus.jackson.map.annotate.JsonSerialize;
import org.codehaus.jackson.map.annotate.JsonSerialize.Inclusion;

import com.skplanet.storeplatform.framework.core.common.vo.CommonInfo;

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
