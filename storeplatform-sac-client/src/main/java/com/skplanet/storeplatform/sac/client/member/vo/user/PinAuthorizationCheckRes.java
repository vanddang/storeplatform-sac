package com.skplanet.storeplatform.sac.client.member.vo.user;

import org.codehaus.jackson.map.annotate.JsonSerialize;
import org.codehaus.jackson.map.annotate.JsonSerialize.Inclusion;

import com.skplanet.storeplatform.framework.core.common.vo.CommonInfo;

/**
 * 2.1.60. 휴대기기 PIN 번호 인증 여부 확인 [RESPONSE].
 * 
 * Updated on : 2015. 6. 26. Updated by : incross_bottangs, 카레즈
 */
@JsonSerialize(include = Inclusion.NON_NULL)
public class PinAuthorizationCheckRes extends CommonInfo {

	private static final long serialVersionUID = 1L;

	/** PIN 인증 여부 확인 휴대폰 번호 */
	private String deviceId;

	/** PIN 인증 확인 결과 */
	private String comptYn;

	/**
	 * @return the deviceId
	 */
	public String getDeviceId() {
		return this.deviceId;
	}

	/**
	 * @return the comptYn
	 */
	public String getComptYn() {
		return this.comptYn;
	}

	/**
	 * @param deviceId
	 *            the deviceId to set
	 */
	public void setDeviceId(String deviceId) {
		this.deviceId = deviceId;
	}

	/**
	 * @param comptYn
	 *            the comptYn to set
	 */
	public void setComptYn(String comptYn) {
		this.comptYn = comptYn;
	}
}
