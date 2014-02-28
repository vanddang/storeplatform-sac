package com.skplanet.storeplatform.sac.client.member.vo.user;

import java.io.Serializable;

import org.hibernate.validator.constraints.NotEmpty;

import com.skplanet.storeplatform.framework.core.common.vo.CommonInfo;

/**
 * [REQUEST] 자동업데이트 회원 인증.
 * 
 * Updated on : 2014. 2. 28. Updated by : 반범진. 지티소프트.
 */
public class AuthorizeForAutoUpdateReq extends CommonInfo implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * 휴대기기 번호.
	 */
	@NotEmpty(message = "파라미터가 존재하지 않습니다.")
	private String deviceId;

	/**
	 * @return deviceId
	 */
	public String getDeviceId() {
		return this.deviceId;
	}

	/**
	 * @param deviceId
	 *            String
	 */
	public void setDeviceId(String deviceId) {
		this.deviceId = deviceId;
	}

}
