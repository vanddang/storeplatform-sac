package com.skplanet.storeplatform.sac.client.member.vo.user;

import java.util.List;

import org.codehaus.jackson.map.annotate.JsonSerialize;
import org.codehaus.jackson.map.annotate.JsonSerialize.Inclusion;

import com.skplanet.storeplatform.framework.core.common.vo.CommonInfo;

/**
 * [RESPONSE] 각 단말별 OS 누적 가입자 수
 * 
 * Updated on : 2014. 2. 21. Updated by : 강신완, 부르칸.
 */
@JsonSerialize(include = Inclusion.NON_NULL)
public class ListDailyPhoneOsSacRes extends CommonInfo {

	private static final long serialVersionUID = 1L;

	private List<DailyPhoneOs> dailyPhoneList;

	public List<DailyPhoneOs> getDailyPhoneList() {
		return this.dailyPhoneList;
	}

	public void setDailyPhoneList(List<DailyPhoneOs> dailyPhoneList) {
		this.dailyPhoneList = dailyPhoneList;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

}
