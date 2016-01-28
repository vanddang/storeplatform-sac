package com.skplanet.storeplatform.member.client.user.sci.vo;

import java.io.Serializable;

import com.skplanet.storeplatform.framework.core.common.vo.CommonInfo;
import com.skplanet.storeplatform.member.client.common.vo.CommonResponse;

/**
 * Class 설명
 * 
 * Updated on : 2014. 10. 1. Updated by : Rejoice, Burkhan
 * Updated on : 2016. 01. 27. Updated by : 윤보영, 카레즈
 */
public class SearchOrderDeviceResponse extends CommonInfo implements Serializable {
	/** The Constant serial Version UID. */
	private static final long serialVersionUID = 1L;

	/**
	 * Returns the serial version UID.
	 * 
	 * @return serialVersionUID - the serial version UID
	 */
	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	/** 공통 응답 Value Object. */
	private CommonResponse commonResponse;
	/** 디바이스 ID. */
	private String deviceId;
	/** 디바이스 통신사. */
	private String deviceTelecom;
	/** 인증 여부. */
	private String authYn;
	/** 테이블 명. */
	private String tableName;
    /** MDN */
    private String mdn;

	/**
	 * 공통 응답 Value Object를 리턴한다.
	 * 
	 * @return commonResponse - 공통 응답 Value Object
	 */
	public CommonResponse getCommonResponse() {
		return this.commonResponse;
	}

	/**
	 * 공통 응답 Value Object를 설정한다.
	 * 
	 * @param commonResponse
	 *            공통 응답 Value Object
	 */
	public void setCommonResponse(CommonResponse commonResponse) {
		this.commonResponse = commonResponse;
	}

	/**
	 * 디바이스 ID를 리턴한다.
	 * 
	 * @return deviceId - 디바이스 ID
	 */
	public String getDeviceId() {
		return this.deviceId;
	}

	/**
	 * 디바이스 ID를 설정한다.
	 * 
	 * @param deviceId
	 *            디바이스 ID
	 */
	public void setDeviceId(String deviceId) {
		this.deviceId = deviceId;
	}

	/**
	 * 디바이스 통신사를 리턴한다.
	 * 
	 * @return deviceTelecom - 디바이스 통신사
	 */
	public String getDeviceTelecom() {
		return this.deviceTelecom;
	}

	/**
	 * 디바이스 통신사를 설정한다.
	 * 
	 * @param deviceTelecom
	 *            디바이스 통신사
	 */
	public void setDeviceTelecom(String deviceTelecom) {
		this.deviceTelecom = deviceTelecom;
	}

	/**
	 * 인증 여부를 리턴한다.
	 * 
	 * @return authYn - 인증 여부
	 */
	public String getAuthYn() {
		return this.authYn;
	}

	/**
	 * 인증 여부를 설정한다.
	 * 
	 * @param authYn
	 *            인증 여부
	 */
	public void setAuthYn(String authYn) {
		this.authYn = authYn;
	}

	/**
	 * 테이블명을 리턴한다.
	 * 
	 * @return tableName - 테이블명
	 */
	public String getTableName() {
		return this.tableName;
	}

	/**
	 * 테이블명을 설정한다.
	 * 
	 * @param tableName
	 *            테이블명
	 */
	public void setTableName(String tableName) {
		this.tableName = tableName;
	}

    public String getMdn() {
        return mdn;
    }

    public void setMdn(String mdn) {
        this.mdn = mdn;
    }
}
