/**
 * 
 */
package com.skplanet.storeplatform.sac.client.internal.member.user.vo;

import org.codehaus.jackson.map.annotate.JsonSerialize;
import org.codehaus.jackson.map.annotate.JsonSerialize.Inclusion;

import com.skplanet.storeplatform.framework.core.common.vo.CommonInfo;

/**
 * [RESPONSE] 기기ID 정보 조회.
 * 
 * Updated on : 2014. 2. 11. Updated by : 김다슬, 인크로스.
 */
@JsonSerialize(include = Inclusion.NON_NULL)
public class SearchDeviceIdSacRes extends CommonInfo {

	private static final long serialVersionUID = 1L;

	/** 기기ID ( msisdn, uuid, macAddress ). */
	private String deviceId;

	/** 통신사 코드. */
	private String deviceTelecom;

	/** 단말 여부 */
	private String authYn;

	/**
	 * 테넌트ID.
	 */
	private String tenantId;

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
	 * @return the deviceTelecom
	 */
	public String getDeviceTelecom() {
		return this.deviceTelecom;
	}

	/**
	 * @param deviceTelecom
	 *            the deviceTelecom to set
	 */
	public void setDeviceTelecom(String deviceTelecom) {
		this.deviceTelecom = deviceTelecom;
	}

	/**
	 * @return String : authYn
	 */
	public String getAuthYn() {
		return this.authYn;
	}

	/**
	 * @param authYn
	 *            String : the authYn to set
	 */
	public void setAuthYn(String authYn) {
		this.authYn = authYn;
	}

	/**
	 * @return tenantId
	 */
	public String getTenantId() {
		return this.tenantId;
	}

	/**
	 * @param tenantId
	 *            String
	 */
	public void setTenantId(String tenantId) {
		this.tenantId = tenantId;
	}
}
