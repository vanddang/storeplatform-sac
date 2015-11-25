package com.skplanet.storeplatform.sac.client.member.vo.user;

import java.io.Serializable;

import org.codehaus.jackson.map.annotate.JsonSerialize;
import org.codehaus.jackson.map.annotate.JsonSerialize.Inclusion;

import com.skplanet.storeplatform.framework.core.common.vo.CommonInfo;

/**
 * [RESPONSE] Save&Sync 인증.
 * 
 * Updated on : 2014. 3. 5. Updated by : 반범진. 지티소프트.
 */
@JsonSerialize(include = Inclusion.NON_NULL)
public class AuthorizeSaveAndSyncByMacRes extends CommonInfo implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * 사용자 고유 Key.
	 */
	private String userKey;

	/**
	 * 기기 Key.
	 */
	private String deviceKey;

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
	 * @return deviceKey
	 */
	public String getDeviceKey() {
		return this.deviceKey;
	}

	/**
	 * @param deviceKey
	 *            String
	 */
	public void setDeviceKey(String deviceKey) {
		this.deviceKey = deviceKey;
	}

}
