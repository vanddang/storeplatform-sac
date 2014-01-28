package com.skplanet.storeplatform.sac.client.member.vo.miscellaneous;

import org.codehaus.jackson.map.annotate.JsonSerialize;
import org.codehaus.jackson.map.annotate.JsonSerialize.Inclusion;

import com.skplanet.storeplatform.framework.core.common.vo.CommonInfo;

/**
 * 
 * [REQUEST] 부가서비스 가입 & 가입 조회 공통 VO
 * 
 * Updated on : 2014. 1. 6. Updated by : 김다슬, 인크로스.
 */
@JsonSerialize(include = Inclusion.NON_NULL)
public class AdditionalServicInfo extends CommonInfo {

	private static final long serialVersionUID = 1L;
	/**
	 * 기기 ID (mdn)
	 */
	private String deviceId;

	/**
	 * 부가서비스 코드
	 */
	private String svcCode;

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

	/**
	 * @return the svcCode
	 */
	public String getSvcCode() {
		return this.svcCode;
	}

	/**
	 * @param svcCode
	 *            the svcCode to set
	 */
	public void setSvcCode(String svcCode) {
		this.svcCode = svcCode;
	}

}
