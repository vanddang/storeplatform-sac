/**
 * 
 */
package com.skplanet.storeplatform.sac.client.member.vo.user;

import java.util.Map;

import org.codehaus.jackson.map.annotate.JsonSerialize;
import org.codehaus.jackson.map.annotate.JsonSerialize.Inclusion;

import com.skplanet.storeplatform.framework.core.common.vo.CommonInfo;

/**
 * [RESPONSE]deviceKey 목록을 이용하여 회원정보 & 디바이스 목록조회.
 * 
 * Updated on : 2014. 3. 07. Updated by : 강신완, 부르칸.
 */
@JsonSerialize(include = Inclusion.NON_NULL)
public class SearchUserDeviceRes extends CommonInfo {
	private static final long serialVersionUID = 1L;

	/**
	 * String deviceKey : 디바이스 키
	 */
	private Map<String, UserInfoByDeviceKey> userDeviceInfo;

	public Map<String, UserInfoByDeviceKey> getUserDeviceInfo() {
		return this.userDeviceInfo;
	}

	public void setUserDeviceInfo(Map<String, UserInfoByDeviceKey> userDeviceInfo) {
		this.userDeviceInfo = userDeviceInfo;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

}
