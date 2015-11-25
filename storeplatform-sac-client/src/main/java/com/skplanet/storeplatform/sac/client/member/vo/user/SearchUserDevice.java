/**
 * 
 */
package com.skplanet.storeplatform.sac.client.member.vo.user;

import org.codehaus.jackson.map.annotate.JsonSerialize;
import org.codehaus.jackson.map.annotate.JsonSerialize.Inclusion;

import com.skplanet.storeplatform.framework.core.common.vo.CommonInfo;

/**
 * [REQUEST]deviceKey 목록을 이용하여 회원정보 & 디바이스 목록조회.
 * 
 * Updated on : 2014. 3. 07. Updated by : 강신완, 부르칸.
 */
@JsonSerialize(include = Inclusion.NON_NULL)
public class SearchUserDevice extends CommonInfo {
	private static final long serialVersionUID = 1L;

	/**
	 * 디바이스 키.
	 */
	private String deviceKey;

	private String userKey;

	public String getDeviceKey() {
		return this.deviceKey;
	}

	public void setDeviceKey(String deviceKey) {
		this.deviceKey = deviceKey;
	}

	public String getUserKey() {
		return this.userKey;
	}

	public void setUserKey(String userKey) {
		this.userKey = userKey;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

}
