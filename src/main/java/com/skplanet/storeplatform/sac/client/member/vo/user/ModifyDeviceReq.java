package com.skplanet.storeplatform.sac.client.member.vo.user;

import org.hibernate.validator.constraints.NotEmpty;

import com.skplanet.storeplatform.framework.core.common.vo.CommonInfo;
import com.skplanet.storeplatform.sac.client.member.vo.common.DeviceInfo;

/**
 * [REQUEST] 휴대기기 수정
 * 
 * Updated on : 2014. 1. 21. Updated by : 반범진. 지티소프트.
 */
public class ModifyDeviceReq extends CommonInfo {

	private static final long serialVersionUID = 1L;

	/**
	 * 사용자 권한 Key
	 */
	private String userAuthKey;

	/**
	 * 사용자 Key
	 */
	@NotEmpty(message = "필수 파라미터 입니다.")
	private String userKey;

	/**
	 * 사용자 단말 정보
	 */
	private DeviceInfo deviceInfo;

	public String getUserAuthKey() {
		return this.userAuthKey;
	}

	public void setUserAuthKey(String userAuthKey) {
		this.userAuthKey = userAuthKey;
	}

	public String getUserKey() {
		return this.userKey;
	}

	public void setUserKey(String userKey) {
		this.userKey = userKey;
	}

	public DeviceInfo getDeviceInfo() {
		return this.deviceInfo;
	}

	public void setDeviceInfo(DeviceInfo deviceInfo) {
		this.deviceInfo = deviceInfo;
	}

}
