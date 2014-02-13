package com.skplanet.storeplatform.sac.client.member.vo.user;

import org.codehaus.jackson.map.annotate.JsonSerialize;
import org.codehaus.jackson.map.annotate.JsonSerialize.Inclusion;
import org.hibernate.validator.constraints.NotEmpty;

import com.skplanet.storeplatform.framework.core.common.vo.CommonInfo;

/**
 * [REQUEST] 게임센터 연동 정보.
 * 
 * Updated on : 2014. 2. 10. Updated by : 반범진, 지티소프트.
 */
@JsonSerialize(include = Inclusion.NON_NULL)
public class GameCenterSacReq extends CommonInfo {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * systemId.
	 */
	@NotEmpty(message = "파라미터가 존재하지 않습니다.")
	private String systemId;

	/**
	 * tenantId.
	 */
	@NotEmpty(message = "파라미터가 존재하지 않습니다.")
	private String tenantId;

	/**
	 * 내부_사용자회원_번호.
	 */
	@NotEmpty(message = "파라미터가 존재하지 않습니다.")
	private String userKey;

	/**
	 * 이전_내부_사용자회원_번호.
	 */
	private String preUserKey;

	/**
	 * 기기_ID.
	 */
	private String deviceId;

	/**
	 * 이전 기기_ID.
	 */
	private String preDeviceId;

	/**
	 * 작업_코드.
	 */
	@NotEmpty(message = "파라미터가 존재하지 않습니다.")
	private String workCd;

	/**
	 * @return systemId
	 */
	public String getSystemId() {
		return this.systemId;
	}

	/**
	 * @param systemId
	 *            String
	 */
	public void setSystemId(String systemId) {
		this.systemId = systemId;
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

	/**
	 * @return userKey
	 */
	public String getUserKey() {
		return this.userKey;
	}

	/**
	 * @param userKey
	 *            String
	 */
	public void setUserKey(String userKey) {
		this.userKey = userKey;
	}

	/**
	 * @return preUserKey
	 */
	public String getPreUserKey() {
		return this.preUserKey;
	}

	/**
	 * @param preUserKey
	 *            String
	 */
	public void setPreUserKey(String preUserKey) {
		this.preUserKey = preUserKey;
	}

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

	/**
	 * @return preDeviceId
	 */
	public String getPreDeviceId() {
		return this.preDeviceId;
	}

	/**
	 * @param preDeviceId
	 *            String
	 */
	public void setPreDeviceId(String preDeviceId) {
		this.preDeviceId = preDeviceId;
	}

	/**
	 * @return workCd
	 */
	public String getWorkCd() {
		return this.workCd;
	}

	/**
	 * @param workCd
	 *            String
	 */
	public void setWorkCd(String workCd) {
		this.workCd = workCd;
	}

}
