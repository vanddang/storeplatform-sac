package com.skplanet.storeplatform.sac.client.member.vo.user;

import com.skplanet.storeplatform.framework.core.common.vo.CommonInfo;
import org.codehaus.jackson.map.annotate.JsonSerialize;
import org.codehaus.jackson.map.annotate.JsonSerialize.Inclusion;

/**
 * [RESPONSE] ID기반(Social ID) 회원 가입.
 *
 * Updated on : 2016. 1. 7. Updated by : 반범진.
 */
@JsonSerialize(include = Inclusion.NON_NULL)
public class CreateByIdSacRes extends CommonInfo {

	private static final long serialVersionUID = 1L;

	/**
	 * 사용자 고유키.
	 */
	private String userKey = "";

	/**
	 * 단말 고유키.
	 */
	private String deviceKey = "";

	/**
	 * @return String : userKey
	 */
	public String getUserKey() {
		return this.userKey;
	}

	/**
	 * @param userKey
	 *            String : the userKey to set
	 */
	public void setUserKey(String userKey) {
		this.userKey = userKey;
	}

	/**
	 * @return String : deviceKey
	 */
	public String getDeviceKey() {
		return this.deviceKey;
	}

	/**
	 * @param deviceKey
	 *            String : the deviceKey to set
	 */
	public void setDeviceKey(String deviceKey) {
		this.deviceKey = deviceKey;
	}

}
