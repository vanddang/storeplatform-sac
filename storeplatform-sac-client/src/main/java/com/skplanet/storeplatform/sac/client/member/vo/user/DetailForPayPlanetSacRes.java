package com.skplanet.storeplatform.sac.client.member.vo.user;

import com.skplanet.storeplatform.framework.core.common.vo.CommonInfo;
import com.skplanet.storeplatform.sac.client.member.vo.common.Agreement;
import com.skplanet.storeplatform.sac.client.member.vo.common.DeviceInfo;
import com.skplanet.storeplatform.sac.client.member.vo.common.UserInfo;
import org.codehaus.jackson.map.annotate.JsonSerialize;
import org.codehaus.jackson.map.annotate.JsonSerialize.Inclusion;

import java.util.List;

/**
 * [RESPONSE] PayPlanet 회원 정보 조회.
 * 
 * Updated on : 2016. 2. 12. Updated by : 반범진.
 */
@JsonSerialize(include = Inclusion.NON_NULL)
public class DetailForPayPlanetSacRes extends CommonInfo {

	private static final long serialVersionUID = 1L;

	/**
	 * 기기 ID.
	 */
	private String deviceId = "";
	/**
	 * 기기 mdn.
	 */
	private String mdn = "";
	/**
	 * 통신사.
	 */
	private String deviceTelecom = "";
	/**
	 * 회원 상태코드.
	 */
	private String userStatus = "";

	/**
	 * 사용자 정보.
	 */
	private UserInfo userInfo;

	/**
	 * 약관동의 리스트.
	 */
	private List<Agreement> agreementList;

	/**
	 * 휴대기기 정보.
	 */
	private DeviceInfo deviceInfo;

	/**
	 * @return String : deviceId
	 */
	public String getDeviceId() {
		return deviceId;
	}

	/**
	 * @param deviceId
	 *            String : the deviceId to set
	 */
	public void setDeviceId(String deviceId) {
		this.deviceId = deviceId;
	}

	/**
	 * @return String : mdn
	 */
	public String getMdn() {
		return mdn;
	}

	/**
	 * @param mdn
	 *            String : the mdn to set
	 */
	public void setMdn(String mdn) {
		this.mdn = mdn;
	}

	/**
	 * @return String : deviceTelecom
	 */
	public String getDeviceTelecom() {
		return deviceTelecom;
	}

	/**
	 * @param deviceTelecom
	 *            String : the deviceTelecom to set
	 */
	public void setDeviceTelecom(String deviceTelecom) {
		this.deviceTelecom = deviceTelecom;
	}

	/**
	 * @return String : userStatus
	 */
	public String getUserStatus() {
		return userStatus;
	}

	/**
	 * @param userStatus
	 *            String : the userStatus to set
	 */
	public void setUserStatus(String userStatus) {
		this.userStatus = userStatus;
	}

	/**
	 * @return userInfo
	 */
	public UserInfo getUserInfo() {
		return userInfo;
	}

	/**
	 * @param userInfo
	 *            UserInfo
	 */
	public void setUserInfo(UserInfo userInfo) {
		this.userInfo = userInfo;
	}

	/**
	 * @return agreementList
	 */
	public List<Agreement> getAgreementList() {
		return agreementList;
	}

	/**
	 * @param agreementList
	 *            List<Agreement>
	 */
	public void setAgreementList(List<Agreement> agreementList) {
		this.agreementList = agreementList;
	}

	/**
	 * @return deviceInfo
	 */
	public DeviceInfo getDeviceInfo() {
		return deviceInfo;
	}

	/**
	 * @param deviceInfo
	 *            DeviceInfo
	 */
	public void setDeviceInfo(DeviceInfo deviceInfo) {
		this.deviceInfo = deviceInfo;
	}
}
