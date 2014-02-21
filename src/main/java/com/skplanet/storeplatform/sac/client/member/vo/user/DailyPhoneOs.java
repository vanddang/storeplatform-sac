package com.skplanet.storeplatform.sac.client.member.vo.user;

import org.codehaus.jackson.map.annotate.JsonSerialize;
import org.codehaus.jackson.map.annotate.JsonSerialize.Inclusion;

import com.skplanet.storeplatform.framework.core.common.vo.CommonInfo;

/**
 * [RESPONSE] 각 단말별 OS 누적 가입자 수
 * 
 * Updated on : 2014. 2. 21. Updated by : 강신완, 부르칸.
 */
@JsonSerialize(include = Inclusion.NON_NULL)
public class DailyPhoneOs extends CommonInfo {

	private static final long serialVersionUID = 1L;
	private String modelName;
	private String enctryCount;

	private String osVersion;

	public String getEnctryCount() {
		return this.enctryCount;
	}

	public void setEnctryCount(String enctryCount) {
		this.enctryCount = enctryCount;
	}

	public String getOsVersion() {
		return this.osVersion;
	}

	public void setOsVersion(String osVersion) {
		this.osVersion = osVersion;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public String getModelName() {
		return this.modelName;
	}

	public void setModelName(String modelName) {
		this.modelName = modelName;
	}

}
