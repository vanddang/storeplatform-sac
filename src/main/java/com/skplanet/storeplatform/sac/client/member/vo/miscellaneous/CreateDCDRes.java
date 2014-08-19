package com.skplanet.storeplatform.sac.client.member.vo.miscellaneous;

import org.codehaus.jackson.map.annotate.JsonSerialize;
import org.codehaus.jackson.map.annotate.JsonSerialize.Inclusion;

import com.skplanet.storeplatform.framework.core.common.vo.CommonInfo;

/**
 * [RESPONSE] DCD 가입
 * 
 * Updated on : 2014. 8. 6. Updated by : 김영균, 지티소프트.
 */
@JsonSerialize(include = Inclusion.NON_NULL)
public class CreateDCDRes extends CommonInfo {
	private static final long serialVersionUID = 1L;
	/**
	 * 기기 ID.
	 */
	private String deviceId;

	/**
	 * @return the deviceId
	 */
	public String getDeviceId() {
		return this.deviceId;
	}

	/**
	 * @param deviceId
	 *            the deviceId to set
	 */
	public void setDeviceId(String deviceId) {
		this.deviceId = deviceId;
	}

}
