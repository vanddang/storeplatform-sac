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

	private List<String> modelName;

	private List<DailyPhoneOs> dailyPhoneOsList;

	public List<String> getModelName() {
		return this.modelName;
	}

	public void setModelName(List<String> modelName) {
		this.modelName = modelName;
	}

	public List<DailyPhoneOs> getDailyPhoneOsList() {
		return this.dailyPhoneOsList;
	}

	public void setDailyPhoneOsList(List<DailyPhoneOs> dailyPhoneOsList) {
		this.dailyPhoneOsList = dailyPhoneOsList;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

}
