package com.skplanet.storeplatform.sac.client.member.vo.user;

import org.hibernate.validator.constraints.NotEmpty;

import com.skplanet.storeplatform.framework.core.common.vo.CommonInfo;
import com.skplanet.storeplatform.sac.client.member.vo.common.DeviceInfo;

/**
 * [REQUEST] 휴대기기 등록
 * 
 * Updated on : 2014. 1. 6. Updated by : 반범진. 지티소프트.
 */
public class CreateDeviceReq extends CommonInfo {

	private static final long serialVersionUID = 1L;

	/**
	 * 사용자 권한 Key
	 */
	@NotEmpty(message = "파라미터가 존재하지 않습니다.")
	private String userAuthKey;

	/**
	 * 사용자 Key
	 */
	@NotEmpty(message = "파라미터가 존재하지 않습니다.")
	private String userKey;

	/**
	 * 최대 등록 가능한 휴대기기 수
	 */
	@NotEmpty(message = "파라미터가 존재하지 않습니다.")
	private String regMaxCnt;

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

	public String getRegMaxCnt() {
		return this.regMaxCnt;
	}

	public void setRegMaxCnt(String regMaxCnt) {
		this.regMaxCnt = regMaxCnt;
	}

	public DeviceInfo getDeviceInfo() {
		return this.deviceInfo;
	}

	public void setDeviceInfo(DeviceInfo deviceInfo) {
		this.deviceInfo = deviceInfo;
	}

}
