/**
 * 
 */
package com.skplanet.storeplatform.sac.client.internal.member.user.vo;

import java.util.Map;

import org.codehaus.jackson.map.annotate.JsonSerialize;
import org.codehaus.jackson.map.annotate.JsonSerialize.Inclusion;

import com.skplanet.storeplatform.framework.core.common.vo.CommonInfo;

/**
 * [RESPONSE] DeviceKey를 이용한 회원정보 & 디바이스정보 조회.
 * 
 * Updated on : 2014. 2. 12. Updated by : 강신완, 부르칸.
 */
@JsonSerialize(include = Inclusion.NON_NULL)
public class SearchUserDeviceSacRes extends CommonInfo {

	private static final long serialVersionUID = 1L;

	/**
	 * String userKey : 회원키. UserInfo회원 정보.
	 */
	private Map<String, UserDeviceInfoSac> userDeviceInfo;

	public Map<String, UserDeviceInfoSac> getUserDeviceInfo() {
		return this.userDeviceInfo;
	}

	public void setUserDeviceInfo(Map<String, UserDeviceInfoSac> userDeviceInfo) {
		this.userDeviceInfo = userDeviceInfo;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

}
