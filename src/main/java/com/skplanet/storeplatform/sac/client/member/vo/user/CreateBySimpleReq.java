package com.skplanet.storeplatform.sac.client.member.vo.user;

import java.util.List;

import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotEmpty;

import com.skplanet.storeplatform.framework.core.common.vo.CommonInfo;
import com.skplanet.storeplatform.sac.client.member.vo.common.DeviceExtraInfo;

/**
 * [REQUEST] ID 회원 간편 가입 (IDP 회원)
 * 
 * Updated on : 2014. 1. 6. Updated by : 심대진, 다모아 솔루션.
 */
public class CreateBySimpleReq extends CommonInfo {

	private static final long serialVersionUID = 1L;

	/**
	 * 사용자 아이디.
	 */
	@NotEmpty(message = "필수 파라미터 입니다.")
	private String userId;

	/**
	 * 사용자 비밀번호.
	 */
	@NotEmpty(message = "필수 파라미터 입니다.")
	private String userPw;

	/**
	 * 사용자 이메일.
	 */
	@Email(message = "유효한 Email 주소가 아닙니다.")
	@NotEmpty(message = "필수 파라미터 입니다.")
	private String userEmail;

	/**
	 * 기기 ID
	 */
	private String deviceId;

	/**
	 * 기기 ID 타입.
	 */
	private String deviceIdType;

	/**
	 * 이동 통신사.
	 */
	private String deviceTelecom;

	/**
	 * 기기고유 ID (imei).
	 */
	private String nativeId;

	/**
	 * 기기 계정 (Gmail).
	 */
	private String deviceAccount;

	/**
	 * 가입 채널 코드.
	 */
	private String joinId;

	/**
	 * Sms 수신 동의 여부.
	 */
	private String isRecvSms;

	/**
	 * 사용자 단말 부가 정보 리스트.
	 */
	private List<DeviceExtraInfo> deviceExtraInfoList;

	/**
	 * @return String : userId
	 */
	public String getUserId() {
		return this.userId;
	}

	/**
	 * @param userId
	 *            String : the userId to set
	 */
	public void setUserId(String userId) {
		this.userId = userId;
	}

	/**
	 * @return String : userPw
	 */
	public String getUserPw() {
		return this.userPw;
	}

	/**
	 * @param userPw
	 *            String : the userPw to set
	 */
	public void setUserPw(String userPw) {
		this.userPw = userPw;
	}

	/**
	 * @return String : userEmail
	 */
	public String getUserEmail() {
		return this.userEmail;
	}

	/**
	 * @param userEmail
	 *            String : the userEmail to set
	 */
	public void setUserEmail(String userEmail) {
		this.userEmail = userEmail;
	}

	/**
	 * @return String : deviceId
	 */
	public String getDeviceId() {
		return this.deviceId;
	}

	/**
	 * @param deviceId
	 *            String : the deviceId to set
	 */
	public void setDeviceId(String deviceId) {
		this.deviceId = deviceId;
	}

	/**
	 * @return String : deviceIdType
	 */
	public String getDeviceIdType() {
		return this.deviceIdType;
	}

	/**
	 * @param deviceIdType
	 *            String : the deviceIdType to set
	 */
	public void setDeviceIdType(String deviceIdType) {
		this.deviceIdType = deviceIdType;
	}

	/**
	 * @return String : deviceTelecom
	 */
	public String getDeviceTelecom() {
		return this.deviceTelecom;
	}

	/**
	 * @param deviceTelecom
	 *            String : the deviceTelecom to set
	 */
	public void setDeviceTelecom(String deviceTelecom) {
		this.deviceTelecom = deviceTelecom;
	}

	/**
	 * @return String : nativeId
	 */
	public String getNativeId() {
		return this.nativeId;
	}

	/**
	 * @param nativeId
	 *            String : the nativeId to set
	 */
	public void setNativeId(String nativeId) {
		this.nativeId = nativeId;
	}

	/**
	 * @return String : deviceAccount
	 */
	public String getDeviceAccount() {
		return this.deviceAccount;
	}

	/**
	 * @param deviceAccount
	 *            String : the deviceAccount to set
	 */
	public void setDeviceAccount(String deviceAccount) {
		this.deviceAccount = deviceAccount;
	}

	/**
	 * @return String : joinId
	 */
	public String getJoinId() {
		return this.joinId;
	}

	/**
	 * @param joinId
	 *            String : the joinId to set
	 */
	public void setJoinId(String joinId) {
		this.joinId = joinId;
	}

	/**
	 * @return String : isRecvSms
	 */
	public String getIsRecvSms() {
		return this.isRecvSms;
	}

	/**
	 * @param isRecvSms
	 *            String : the isRecvSms to set
	 */
	public void setIsRecvSms(String isRecvSms) {
		this.isRecvSms = isRecvSms;
	}

	/**
	 * @return List<DeviceExtraInfo> : deviceExtraInfoList
	 */
	public List<DeviceExtraInfo> getDeviceExtraInfoList() {
		return this.deviceExtraInfoList;
	}

	/**
	 * @param deviceExtraInfoList
	 *            List<DeviceExtraInfo> : the deviceExtraInfoList to set
	 */
	public void setDeviceExtraInfoList(List<DeviceExtraInfo> deviceExtraInfoList) {
		this.deviceExtraInfoList = deviceExtraInfoList;
	}

}
