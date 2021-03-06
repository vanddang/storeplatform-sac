package com.skplanet.storeplatform.sac.client.member.vo.common;

import java.util.List;

import org.codehaus.jackson.map.annotate.JsonSerialize;
import org.codehaus.jackson.map.annotate.JsonSerialize.Inclusion;

import com.skplanet.storeplatform.framework.core.common.vo.CommonInfo;

/**
 * 휴대기기 정보.
 * 
 * Updated on : 2014. 1. 6. Updated by : 반범진, 지티소프트.
 */
@JsonSerialize(include = Inclusion.NON_NULL)
public class DeviceInfo extends CommonInfo {

	private static final long serialVersionUID = 1L;

	/**
	 * 사용자 Id.
	 */
	private String userId;

	/**
	 * 사용자 key.
	 */
	private String userKey;

	/**
	 * 기기 Key.
	 */
	private String deviceKey;

	/**
	 * 기기 ID.
	 */
	private String deviceId;

	/**
	 * 기기 mdn.
	 */
	private String mdn;

	/**
	 * 기기 mdn 자번호.
	 */
	private String omdn;

	/**
	 * 기기 ID 타입 (msisdn, uuid, macaddress, imei).
	 */
	private String deviceIdType;

	/**
	 * 기기 모델 번호.
	 */
	private String deviceModelNo;

	/**
	 * 통신사 코드.
	 */
	private String deviceTelecom;

	/**
	 * 대표기기 여부.
	 */
	private String isPrimary;

	/**
	 * SMS 수신 여부.
	 */
	private String isRecvSms;

	/**
	 * 기기고유 ID.
	 */
	private String nativeId;

	/**
	 * 기기 고유 번호 비교여부.
	 */
	private String isNativeIdAuth;

	/**
	 * 기기계정.
	 */
	private String deviceAccount;

	/**
	 * 가입 채널 코드.
	 */
	private String joinId;

	/**
	 * 수정 일시.
	 */
	private String updateDate;

	/**
	 * 시작 일시.
	 */
	private String startDate;

	/**
	 * 종료 일시.
	 */
	private String endDate;

	/**
	 * 휴대기기 통합 관리 번호.
	 */
	private String svcMangNum;

	/**
	 * 휴대기기 인증여부.
	 */
	private String isAuthenticated;

	/**
	 * 휴대기기 인증일자.
	 */
	private String authenticationDate;

	/**
	 * 휴면계정유무.
	 */
	private String isDormant;

	/**
	 * usim 번호.
	 */
	private String simSerialNo;

	/**
	 * 사용자 단말 부가 정보 리스트 - 회원정보조회.
	 */
	private List<DeviceExtraInfo> deviceExtraInfoList;

	/**
	 * 타사 회선의 고유 Key.
	 */
	private String marketDeviceKey;

	/**
	 * 타사 deviceKey에 대한 hash값(U+ Store).
	 */
	private String deviceKeyAuth;

	/**
	 * 샵클 업데이트 필요 여부(SAP 간편회원인증 API 필드).
	 */
	private String isSCUpdate;

	/**
     * 휴대기기 마지막 로그인 일자.
     */
    private String lastLoginDt;

    /**
     * 휴대기기 제조사명.
     */
    private String makeComp;

    /**
     * 휴대기기 OS 플랫폼.
     */
    private String vmType;

    /**
     * 휴대기기 모델 설명.
     */
    private String modelNm;

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

	/**
	 * @return deviceId
	 */
	public String getDeviceId() {
		return this.deviceId;
	}

	/**
	 * @param deviceId
	 *            String
	 */
	public void setDeviceId(String deviceId) {
		this.deviceId = deviceId;
	}

	/**
	 * @return deviceModelNo
	 */
	public String getDeviceModelNo() {
		return this.deviceModelNo;
	}

	/**
	 * @param deviceModelNo
	 *            String
	 */
	public void setDeviceModelNo(String deviceModelNo) {
		this.deviceModelNo = deviceModelNo;
	}

	/**
	 * @return deviceTelecom
	 */
	public String getDeviceTelecom() {
		return this.deviceTelecom;
	}

	/**
	 * @param deviceTelecom
	 *            String
	 */
	public void setDeviceTelecom(String deviceTelecom) {
		this.deviceTelecom = deviceTelecom;
	}

	/**
	 * @return isPrimary
	 */
	public String getIsPrimary() {
		return this.isPrimary;
	}

	/**
	 * @param isPrimary
	 *            String
	 */
	public void setIsPrimary(String isPrimary) {
		this.isPrimary = isPrimary;
	}

	/**
	 * @return isRecvSms
	 */
	public String getIsRecvSms() {
		return this.isRecvSms;
	}

	/**
	 * @param isRecvSms
	 *            String
	 */
	public void setIsRecvSms(String isRecvSms) {
		this.isRecvSms = isRecvSms;
	}

	/**
	 * @return deviceAccount
	 */
	public String getDeviceAccount() {
		return this.deviceAccount;
	}

	/**
	 * @param deviceAccount
	 *            String
	 */
	public void setDeviceAccount(String deviceAccount) {
		this.deviceAccount = deviceAccount;
	}

	/**
	 * @return joinId
	 */
	public String getJoinId() {
		return this.joinId;
	}

	/**
	 * @param joinId
	 *            String
	 */
	public void setJoinId(String joinId) {
		this.joinId = joinId;
	}

	/**
	 * @return deviceExtraInfoList
	 */
	public List<DeviceExtraInfo> getDeviceExtraInfoList() {
		return this.deviceExtraInfoList;
	}

	/**
	 * @param deviceExtraInfoList
	 *            List<DeviceExtraInfo>
	 */
	public void setDeviceExtraInfoList(List<DeviceExtraInfo> deviceExtraInfoList) {
		this.deviceExtraInfoList = deviceExtraInfoList;
	}

	/**
	 * @return updateDate
	 */
	public String getUpdateDate() {
		return this.updateDate;
	}

	/**
	 * @param updateDate
	 *            String
	 */
	public void setUpdateDate(String updateDate) {
		this.updateDate = updateDate;
	}

	/**
	 * @return startDate
	 */
	public String getStartDate() {
		return this.startDate;
	}

	/**
	 * @param startDate
	 *            String
	 */
	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}

	/**
	 * @return endDate
	 */
	public String getEndDate() {
		return this.endDate;
	}

	/**
	 * @param endDate
	 *            String
	 */
	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}

	/**
	 * @return userId
	 */
	public String getUserId() {
		return this.userId;
	}

	/**
	 * @param userId
	 *            String
	 */
	public void setUserId(String userId) {
		this.userId = userId;
	}

	/**
	 * @return nativeId
	 */
	public String getNativeId() {
		return this.nativeId;
	}

	/**
	 * @param nativeId
	 *            String
	 */
	public void setNativeId(String nativeId) {
		this.nativeId = nativeId;
	}

	/**
	 * @return deviceIdType
	 */
	public String getDeviceIdType() {
		return this.deviceIdType;
	}

	/**
	 * @param deviceIdType
	 *            String
	 */
	public void setDeviceIdType(String deviceIdType) {
		this.deviceIdType = deviceIdType;
	}

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
	 * @return svcMangNum
	 */
	public String getSvcMangNum() {
		return this.svcMangNum;
	}

	/**
	 * @param svcMangNum
	 *            String
	 */
	public void setSvcMangNum(String svcMangNum) {
		this.svcMangNum = svcMangNum;
	}

	/**
	 * @return isNativeIdAuth
	 */
	public String getIsNativeIdAuth() {
		return this.isNativeIdAuth;
	}

	/**
	 * @param isNativeIdAuth
	 *            String
	 */
	public void setIsNativeIdAuth(String isNativeIdAuth) {
		this.isNativeIdAuth = isNativeIdAuth;
	}

	/**
	 * @return isAuthenticated
	 */
	public String getIsAuthenticated() {
		return this.isAuthenticated;
	}

	/**
	 * @param isAuthenticated
	 *            String
	 */
	public void setIsAuthenticated(String isAuthenticated) {
		this.isAuthenticated = isAuthenticated;
	}

	/**
	 * @return authenticationDate
	 */
	public String getAuthenticationDate() {
		return this.authenticationDate;
	}

	/**
	 * @param authenticationDate
	 *            String
	 */
	public void setAuthenticationDate(String authenticationDate) {
		this.authenticationDate = authenticationDate;
	}

	/**
	 * @return marketDeviceKey
	 */
	public String getMarketDeviceKey() {
		return this.marketDeviceKey;
	}

	/**
	 * @param marketDeviceKey
	 *            String
	 */
	public void setMarketDeviceKey(String marketDeviceKey) {
		this.marketDeviceKey = marketDeviceKey;
	}

	/**
	 * @return deviceKeyAuth
	 */
	public String getDeviceKeyAuth() {
		return this.deviceKeyAuth;
	}

	/**
	 * @param deviceKeyAuth
	 *            String
	 */
	public void setDeviceKeyAuth(String deviceKeyAuth) {
		this.deviceKeyAuth = deviceKeyAuth;
	}

	/**
	 * @return isSCUpdate
	 */
	public String getIsSCUpdate() {
		return this.isSCUpdate;
	}

	/**
	 * @param isSCUpdate
	 *            String
	 */
	public void setIsSCUpdate(String isSCUpdate) {
		this.isSCUpdate = isSCUpdate;
	}

	/**
	 * @return isDormant
	 */
	public String getIsDormant() {
		return this.isDormant;
	}

	/**
	 * @param isDormant
	 *            String
	 */
	public void setIsDormant(String isDormant) {
		this.isDormant = isDormant;
	}

	/**
	 * @return mdn
	 */
	public String getMdn() {
		return this.mdn;
	}

	/**
	 * @param mdn
	 *            String
	 */
	public void setMdn(String mdn) {
		this.mdn = mdn;
	}

	/**
	 * @return omdn
	 */
	public String getOmdn() {
		return this.omdn;
	}

	/**
	 * @param omdn
	 *            String
	 */
	public void setOmdn(String omdn) {
		this.omdn = omdn;
	}

	/**
	 * @return simSerialNo
	 */
	public String getSimSerialNo() {
		return simSerialNo;
	}

	/**
	 * @param simSerialNo
	 *            String
	 */
	public void setSimSerialNo(String simSerialNo) {
		this.simSerialNo = simSerialNo;
	}

	/**
	 * @return lastLoginDt
	 */
	public String getLastLoginDt() {
		return lastLoginDt;
	}

	/**
	 * @param lastLoginDt
	 *            String
	 */
	public void setLastLoginDt(String lastLoginDt) {
		this.lastLoginDt = lastLoginDt;
	}

    public String getMakeComp() {
        return makeComp;
    }

    public void setMakeComp(String makeComp) {
        this.makeComp = makeComp;
    }

    public String getVmType() {
        return vmType;
    }

    public void setVmType(String vmType) {
        this.vmType = vmType;
    }

    public String getModelNm() {
        return modelNm;
    }

    public void setModelNm(String modelNm) {
        this.modelNm = modelNm;
    }


}
