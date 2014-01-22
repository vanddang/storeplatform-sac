package com.skplanet.storeplatform.sac.client.member.vo.common;

import java.util.List;

import org.codehaus.jackson.map.annotate.JsonSerialize;
import org.codehaus.jackson.map.annotate.JsonSerialize.Inclusion;

import com.skplanet.storeplatform.framework.core.common.vo.CommonInfo;

/**
 * 휴대기기 정보
 * 
 * Updated on : 2014. 1. 6. Updated by : 반범진, 지티소프트.
 */
@JsonSerialize(include = Inclusion.NON_NULL)
public class DeviceInfo extends CommonInfo {

	private static final long serialVersionUID = 1L;

	/**
	 * 사용자 Id
	 */
	private String userId;

	/**
	 * 사용자 key
	 */
	private String userKey;

	/**
	 * 기기 Key
	 */
	private String deviceKey;

	/**
	 * 기기 ID
	 */
	private String deviceId;

	/**
	 * 기기 ID 타입 (msisdn, uuid, macaddress)
	 */
	private String deviceIdType;

	/**
	 * 기기 타입 코드
	 */
	private String deviceType;

	/**
	 * 기기 모델 번호
	 */
	private String deviceModelNo;

	/**
	 * 통신사 코드
	 */
	private String deviceTelecom;

	/**
	 * 기기별명
	 */
	private String deviceNickName;

	/**
	 * 대표기기 여부
	 */
	private String isPrimary;

	/**
	 * 인증여부
	 */
	private String isAuthenticated;

	/**
	 * 인증일시
	 */
	private String authenticationDate;

	/**
	 * SMS 수신 여부
	 */
	private String isRecvSms;

	/**
	 * 휴대기기 사용 여부
	 */
	private String isUsed;

	/**
	 * 기기고유 ID
	 */
	private String nativeId;

	/**
	 * 기기계정
	 */
	private String deviceAccount;

	/**
	 * 가입 채널 코드
	 */
	private String joinId;

	/**
	 * 제조사명
	 */
	private String makeComp;

	/**
	 * 수정 일시
	 */
	private String updateDate;

	/**
	 * 시작 일시
	 */
	private String startDate;

	/**
	 * 종료 일시
	 */
	private String endDate;

	/**
	 * TenantId
	 */
	private String TenantId;

	/**
	 * 사용자 단말 부가 정보 리스트 - 회원정보조회
	 */
	private List<DeviceExtraInfo> userDeviceExtraInfo;

	public String getDeviceKey() {
		return this.deviceKey;
	}

	public void setDeviceKey(String deviceKey) {
		this.deviceKey = deviceKey;
	}

	public String getDeviceId() {
		return this.deviceId;
	}

	public void setDeviceId(String deviceId) {
		this.deviceId = deviceId;
	}

	public String getDeviceType() {
		return this.deviceType;
	}

	public void setDeviceType(String deviceType) {
		this.deviceType = deviceType;
	}

	public String getDeviceModelNo() {
		return this.deviceModelNo;
	}

	public void setDeviceModelNo(String deviceModelNo) {
		this.deviceModelNo = deviceModelNo;
	}

	public String getDeviceTelecom() {
		return this.deviceTelecom;
	}

	public void setDeviceTelecom(String deviceTelecom) {
		this.deviceTelecom = deviceTelecom;
	}

	public String getDeviceNickName() {
		return this.deviceNickName;
	}

	public void setDeviceNickName(String deviceNickName) {
		this.deviceNickName = deviceNickName;
	}

	public String getIsPrimary() {
		return this.isPrimary;
	}

	public void setIsPrimary(String isPrimary) {
		this.isPrimary = isPrimary;
	}

	public String getIsAuthenticated() {
		return this.isAuthenticated;
	}

	public void setIsAuthenticated(String isAuthenticated) {
		this.isAuthenticated = isAuthenticated;
	}

	public String getAuthenticationDate() {
		return this.authenticationDate;
	}

	public void setAuthenticationDate(String authenticationDate) {
		this.authenticationDate = authenticationDate;
	}

	public String getIsRecvSms() {
		return this.isRecvSms;
	}

	public void setIsRecvSms(String isRecvSms) {
		this.isRecvSms = isRecvSms;
	}

	public String getDeviceAccount() {
		return this.deviceAccount;
	}

	public void setDeviceAccount(String deviceAccount) {
		this.deviceAccount = deviceAccount;
	}

	public String getJoinId() {
		return this.joinId;
	}

	public void setJoinId(String joinId) {
		this.joinId = joinId;
	}

	public String getMakeComp() {
		return this.makeComp;
	}

	public void setMakeComp(String makeComp) {
		this.makeComp = makeComp;
	}

	public List<DeviceExtraInfo> getUserDeviceExtraInfo() {
		return this.userDeviceExtraInfo;
	}

	public void setUserDeviceExtraInfo(List<DeviceExtraInfo> userDeviceExtraInfo) {
		this.userDeviceExtraInfo = userDeviceExtraInfo;
	}

	public String getIsUsed() {
		return this.isUsed;
	}

	public void setIsUsed(String isUsed) {
		this.isUsed = isUsed;
	}

	public String getUpdateDate() {
		return this.updateDate;
	}

	public void setUpdateDate(String updateDate) {
		this.updateDate = updateDate;
	}

	public String getStartDate() {
		return this.startDate;
	}

	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}

	public String getEndDate() {
		return this.endDate;
	}

	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}

	public String getUserId() {
		return this.userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getNativeId() {
		return this.nativeId;
	}

	public void setNativeId(String nativeId) {
		this.nativeId = nativeId;
	}

	public String getTenantId() {
		return this.TenantId;
	}

	public void setTenantId(String tenantId) {
		this.TenantId = tenantId;
	}

	public String getDeviceIdType() {
		return this.deviceIdType;
	}

	public void setDeviceIdType(String deviceIdType) {
		this.deviceIdType = deviceIdType;
	}

	public String getUserKey() {
		return this.userKey;
	}

	public void setUserKey(String userKey) {
		this.userKey = userKey;
	}

}
