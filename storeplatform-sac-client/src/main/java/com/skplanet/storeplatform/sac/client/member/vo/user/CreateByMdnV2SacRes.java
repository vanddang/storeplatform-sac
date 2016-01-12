package com.skplanet.storeplatform.sac.client.member.vo.user;

import com.skplanet.storeplatform.framework.core.common.vo.CommonInfo;
import org.codehaus.jackson.map.annotate.JsonSerialize;
import org.codehaus.jackson.map.annotate.JsonSerialize.Inclusion;

/**
 * [RESPONSE] 모바일 전용 회원 가입 V2
 * 
 * Updated on : 2016. 1. 11. Updated by : 반범진.
 */
@JsonSerialize(include = Inclusion.NON_NULL)
public class CreateByMdnV2SacRes extends CommonInfo {

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
