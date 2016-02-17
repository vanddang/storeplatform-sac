package com.skplanet.storeplatform.sac.client.member.vo.user;

import org.codehaus.jackson.map.annotate.JsonSerialize;
import org.codehaus.jackson.map.annotate.JsonSerialize.Inclusion;
import org.hibernate.validator.constraints.NotBlank;

import com.skplanet.storeplatform.framework.core.common.vo.CommonInfo;

/**
 * 2.1.49. 휴대기기 설정 정보 등록/수정. [REQUEST]
 * 
 * Updated on : 2015. 10. 29. Updated by : 최진호, 보고지티.
 */
@JsonSerialize(include = Inclusion.NON_NULL)
public class ModifyDeviceSetInfoSacReq extends CommonInfo {

	private static final long serialVersionUID = 1L;

	/** 기기Key. */
	@NotBlank
	private String deviceKey;
	/** 회원 Key. */
	@NotBlank
	private String userKey;
	/** 자동 업데이트 유무. */
	private String isAutoUpdate;
	/** 자동 업데이트 설정 구분. */
	private String autoUpdateSet;
	/** Wi-Fi 자동 업데이트 유무. */
	private String isAutoUpdateWifi;
	/** 로그인 잠금 유무. */
	private String isLoginLock;
	/** 결제 PIN 재입력 여부. */
	private String isPinRetry;
	/** 성인 콘테츠 잠금 유무. */
	private String isAdult;
	/** 성인 컨텐츠 잠금 여부. */
	private String isAdultLock;
	/** Wi-Fi에서만 다운로드 여부. */
	private String isDownloadWifiOnly;

    /** 실명인증 일자. */
	private String realNameDate;
	/** 실명인증 MDN. */
	private String realNameMdn;
    /** 실명인증 통신사 */
    private String realNameDeviceTelecom;

    /**
     * 업데이트 표시 여부
     */
    private String isShowUpdate;

    /**
     * 임시 저장 위치
     */
    private String tempStorageCd;

    /**
     * 콘텐츠 저장 위치
     */
    private String contentsStorageCd;

    /**
     * 바로가기 아이콘 만들기 여부
     */
    private String isMakeShortcut;

	/**
	 * @return the deviceKey
	 */
	public String getDeviceKey() {
		return this.deviceKey;
	}

	/**
	 * @param deviceKey
	 *            the deviceKey to set
	 */
	public void setDeviceKey(String deviceKey) {
		this.deviceKey = deviceKey;
	}

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
	 * @return the isAutoUpdate
	 */
	public String getIsAutoUpdate() {
		return this.isAutoUpdate;
	}

	/**
	 * @param isAutoUpdate
	 *            the isAutoUpdate to set
	 */
	public void setIsAutoUpdate(String isAutoUpdate) {
		this.isAutoUpdate = isAutoUpdate;
	}

	/**
	 * @return the autoUpdateSet
	 */
	public String getAutoUpdateSet() {
		return this.autoUpdateSet;
	}

	/**
	 * @param autoUpdateSet
	 *            the autoUpdateSet to set
	 */
	public void setAutoUpdateSet(String autoUpdateSet) {
		this.autoUpdateSet = autoUpdateSet;
	}

	/**
	 * @return the isAutoUpdateWifi
	 */
	public String getIsAutoUpdateWifi() {
		return this.isAutoUpdateWifi;
	}

	/**
	 * @param isAutoUpdateWifi
	 *            the isAutoUpdateWifi to set
	 */
	public void setIsAutoUpdateWifi(String isAutoUpdateWifi) {
		this.isAutoUpdateWifi = isAutoUpdateWifi;
	}

	/**
	 * @return the isLoginLock
	 */
	public String getIsLoginLock() {
		return this.isLoginLock;
	}

	/**
	 * @param isLoginLock
	 *            the isLoginLock to set
	 */
	public void setIsLoginLock(String isLoginLock) {
		this.isLoginLock = isLoginLock;
	}

	/**
	 * @return the isPinRetry
	 */
	public String getIsPinRetry() {
		return this.isPinRetry;
	}

	/**
	 * @param isPinRetry
	 *            the isPinRetry to set
	 */
	public void setIsPinRetry(String isPinRetry) {
		this.isPinRetry = isPinRetry;
	}

	/**
	 * @return the isAdult
	 */
	public String getIsAdult() {
		return this.isAdult;
	}

	/**
	 * @param isAdult
	 *            the isAdult to set
	 */
	public void setIsAdult(String isAdult) {
		this.isAdult = isAdult;
	}

	/**
	 * @return the isAdultLock
	 */
	public String getIsAdultLock() {
		return this.isAdultLock;
	}

	/**
	 * @param isAdultLock
	 *            the isAdultLock to set
	 */
	public void setIsAdultLock(String isAdultLock) {
		this.isAdultLock = isAdultLock;
	}

	/**
	 * @return the isDownloadWifiOnly
	 */
	public String getIsDownloadWifiOnly() {
		return this.isDownloadWifiOnly;
	}

	/**
	 * @param isDownloadWifiOnly
	 *            the isDownloadWifiOnly to set
	 */
	public void setIsDownloadWifiOnly(String isDownloadWifiOnly) {
		this.isDownloadWifiOnly = isDownloadWifiOnly;
	}

    /**
	 * 실명인증 일자(을)를 리턴한다.
	 * 
	 * @return realNameDate - realNameDate
	 */
	public String getRealNameDate() {
		return this.realNameDate;
	}

	/**
	 * 실명인증 일자(을)를 셋팅한다.
	 * 
	 * @param realNameDate
	 *            realNameDate
	 */
	public void setRealNameDate(String realNameDate) {
		this.realNameDate = realNameDate;
	}

	/**
	 * 실명인증 MDN(을)를 리턴한다.
	 * 
	 * @return realNameMdn - realNameMdn
	 */
	public String getRealNameMdn() {
		return this.realNameMdn;
	}

	/**
	 * 실명인증 MDN(을)를 셋팅한다.
	 * 
	 * @param realNameMdn
	 *            realNameMdn
	 */
	public void setRealNameMdn(String realNameMdn) {
		this.realNameMdn = realNameMdn;
	}

    public String getIsShowUpdate() {
        return isShowUpdate;
    }

    public void setIsShowUpdate(String isShowUpdate) {
        this.isShowUpdate = isShowUpdate;
    }

    public String getTempStorageCd() {
        return tempStorageCd;
    }

    public void setTempStorageCd(String tempStorageCd) {
        this.tempStorageCd = tempStorageCd;
    }

    public String getContentsStorageCd() {
        return contentsStorageCd;
    }

    public void setContentsStorageCd(String contentsStorageCd) {
        this.contentsStorageCd = contentsStorageCd;
    }

    public String getIsMakeShortcut() {
        return isMakeShortcut;
    }

    public void setIsMakeShortcut(String isMakeShortcut) {
        this.isMakeShortcut = isMakeShortcut;
    }

    public String getRealNameDeviceTelecom() {
        return realNameDeviceTelecom;
    }

    public void setRealNameDeviceTelecom(String realNameDeviceTelecom) {
        this.realNameDeviceTelecom = realNameDeviceTelecom;
    }
}
