package com.skplanet.storeplatform.sac.client.internal.member.user.vo;

import org.codehaus.jackson.map.annotate.JsonSerialize;
import org.codehaus.jackson.map.annotate.JsonSerialize.Inclusion;

import com.skplanet.storeplatform.framework.core.common.vo.CommonInfo;

/**
 * deviceId 와 구매일시로 최근 userKey, deviceKey 조회 [RESPONSE]
 * 
 * Updated on : 2014. 6. 25. Updated by : Rejoice, Burkhan
 */
@JsonSerialize(include = Inclusion.NON_NULL)
public class SearchOrderUserByDeviceIdSacRes extends CommonInfo {

	private static final long serialVersionUID = 1L;

	/** 사용자키. */
	private String userKey;
	/** 디바이스키. */
	private String deviceKey;
	/** 인증일시. */
	private String authenticationDate;
	/** 인증여부. */
	private String authYn;
	/** 테이블명. */
	private String tableName;

	/**
	 * @return the userKey
	 */
	public String getUserKey() {
		return this.userKey;
	}

	/**
	 * @param userKey
	 *            the userKey to set
	 */
	public void setUserKey(String userKey) {
		this.userKey = userKey;
	}

	/**
	 * @return the deviceKey
	 */
	public String getDeviceKey() {
		return this.deviceKey;
	}

	/**
	 * @param deviceKey
	 *            the deviceKey to set
	 */
	public void setDeviceKey(String deviceKey) {
		this.deviceKey = deviceKey;
	}

	/**
	 * @return the authenticationDate
	 */
	public String getAuthenticationDate() {
		return this.authenticationDate;
	}

	/**
	 * @param authenticationDate
	 *            the authenticationDate to set
	 */
	public void setAuthenticationDate(String authenticationDate) {
		this.authenticationDate = authenticationDate;
	}

	/**
	 * @return the authYn
	 */
	public String getAuthYn() {
		return this.authYn;
	}

	/**
	 * @param authYn
	 *            the authYn to set
	 */
	public void setAuthYn(String authYn) {
		this.authYn = authYn;
	}

	/**
	 * @return the tableName
	 */
	public String getTableName() {
		return this.tableName;
	}

	/**
	 * @param tableName
	 *            the tableName to set
	 */
	public void setTableName(String tableName) {
		this.tableName = tableName;
	}
}
