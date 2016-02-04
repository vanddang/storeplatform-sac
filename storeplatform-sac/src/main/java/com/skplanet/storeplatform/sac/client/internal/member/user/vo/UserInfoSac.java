package com.skplanet.storeplatform.sac.client.internal.member.user.vo;

import java.util.List;

import org.codehaus.jackson.map.annotate.JsonSerialize;
import org.codehaus.jackson.map.annotate.JsonSerialize.Inclusion;

import com.skplanet.storeplatform.framework.core.common.vo.CommonInfo;

/**
 * 회원 정보.
 * 
 * Updated on : 2014. 2. 21. Updated by : 김다슬, 인크로스.
 * Updated on : 2016. 1. 26. Updated by : 윤보영, 카레즈.
 */
@JsonSerialize(include = Inclusion.NON_NULL)
public class UserInfoSac extends CommonInfo {

	private static final long serialVersionUID = 1L;

	/** tenant Id (타파트 작업 후 삭제 필요) */
    @Deprecated
	private String tenantId;

	/** 회원키. */
	private String userKey;

	/** 사용자 구분 코드. */
	private String userType;

	/** 사용자 메인 상태 코드. */
	private String userMainStatus;

	/** 사용자 서브 상태 코드. */
	private String userSubStatus;

	/** 사용자 ID. */
	private String userId;

	/** 기기 ID List. */
	private List<DeviceInfoSac> deviceInfoListSac;

	/**
	 * @return the userKey
	 */
	public String getUserKey() {
		return this.userKey;
	}

	/**
	 * @param userKey
	 *            the userKey to set
	 */
	public void setUserKey(String userKey) {
		this.userKey = userKey;
	}

	/**
	 * @return the userType
	 */
	public String getUserType() {
		return this.userType;
	}

	/**
	 * @param userType
	 *            the userType to set
	 */
	public void setUserType(String userType) {
		this.userType = userType;
	}

	/**
	 * @return the userMainStatus
	 */
	public String getUserMainStatus() {
		return this.userMainStatus;
	}

	/**
	 * @param userMainStatus
	 *            the userMainStatus to set
	 */
	public void setUserMainStatus(String userMainStatus) {
		this.userMainStatus = userMainStatus;
	}

	/**
	 * @return the userSubStatus
	 */
	public String getUserSubStatus() {
		return this.userSubStatus;
	}

	/**
	 * @param userSubStatus
	 *            the userSubStatus to set
	 */
	public void setUserSubStatus(String userSubStatus) {
		this.userSubStatus = userSubStatus;
	}

	/**
	 * @return the userId
	 */
	public String getUserId() {
		return this.userId;
	}

	/**
	 * @param userId
	 *            the userId to set
	 */
	public void setUserId(String userId) {
		this.userId = userId;
	}

    /**
	 * @return the tenantId
	 */
	public String getTenantId() {
		return this.tenantId;
	}

	/**
	 * @param tenantId
	 *            the tenantId to set
	 */
	public void setTenantId(String tenantId) {
		this.tenantId = tenantId;
	}

    public List<DeviceInfoSac> getDeviceInfoListSac() {
        return deviceInfoListSac;
    }

    public void setDeviceInfoListSac(List<DeviceInfoSac> deviceInfoListSac) {
        this.deviceInfoListSac = deviceInfoListSac;
    }
}
