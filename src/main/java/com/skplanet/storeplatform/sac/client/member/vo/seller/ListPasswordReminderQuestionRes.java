package com.skplanet.storeplatform.sac.client.member.vo.seller;

import java.util.List;

import org.codehaus.jackson.map.annotate.JsonSerialize;
import org.codehaus.jackson.map.annotate.JsonSerialize.Inclusion;

import com.skplanet.storeplatform.framework.core.common.vo.CommonInfo;
import com.skplanet.storeplatform.sac.client.member.vo.common.PwReminder;

@JsonSerialize(include = Inclusion.NON_NULL)
public class ListPasswordReminderQuestionRes extends CommonInfo {

	private static final long serialVersionUID = 1L;

	List<PwReminder> pwReminderList;

	public List<PwReminder> getPwReminderList() {
		return this.pwReminderList;
	}

	public void setPwReminderList(List<PwReminder> pwReminderList) {
		this.pwReminderList = pwReminderList;
	}

}
