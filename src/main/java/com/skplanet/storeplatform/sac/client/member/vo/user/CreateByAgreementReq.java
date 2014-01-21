package com.skplanet.storeplatform.sac.client.member.vo.user;

import java.util.List;

import org.hibernate.validator.constraints.NotEmpty;

import com.skplanet.storeplatform.framework.core.common.vo.CommonInfo;
import com.skplanet.storeplatform.sac.client.member.vo.common.AgreementInfo;
import com.skplanet.storeplatform.sac.client.member.vo.common.DeviceExtraInfo;

/**
 * [REQUEST] ID 회원 약관 동의 가입 (One ID 회원)
 * 
 * Updated on : 2014. 1. 3. Updated by : 심대진, 다모아 솔루션.
 */
public class CreateByAgreementReq extends CommonInfo {

	private static final long serialVersionUID = 1L;

	/**
	 * 사용자 아이디.
	 */
	@NotEmpty(message = "필수 파라미터 입니다.")
	private String userId;

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
	 * 약관 동의 정보.
	 */
	private List<AgreementInfo> agreementList;

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

	/**
	 * @return List<AgreementInfo> : agreementList
	 */
	public List<AgreementInfo> getAgreementList() {
		return this.agreementList;
	}

	/**
	 * @param agreementList
	 *            List<AgreementInfo> : the agreementList to set
	 */
	public void setAgreementList(List<AgreementInfo> agreementList) {
		this.agreementList = agreementList;
	}

}
