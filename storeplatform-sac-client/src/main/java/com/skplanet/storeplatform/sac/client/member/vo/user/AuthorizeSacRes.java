package com.skplanet.storeplatform.sac.client.member.vo.user;

import java.util.List;

import org.codehaus.jackson.map.annotate.JsonSerialize;
import org.codehaus.jackson.map.annotate.JsonSerialize.Inclusion;

import com.skplanet.storeplatform.framework.core.common.vo.CommonInfo;
import com.skplanet.storeplatform.sac.client.member.vo.common.Agreement;
import com.skplanet.storeplatform.sac.client.member.vo.common.DeviceInfo;
import com.skplanet.storeplatform.sac.client.member.vo.common.MarketPinInfo;
import com.skplanet.storeplatform.sac.client.member.vo.common.MbrAuth;
import com.skplanet.storeplatform.sac.client.member.vo.common.TstoreEtcInfo;
import com.skplanet.storeplatform.sac.client.member.vo.common.UserInfo;

/**
 * [Response] PayPlanet에 제공되는 T Store 회원인증.
 * 
 * Updated on : 2014. 11. 7. Updated by : 반범진, SK 플래닛.
 */
@JsonSerialize(include = Inclusion.NON_NULL)
public class AuthorizeSacRes extends CommonInfo {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * 사용자 main 상태코드.
	 */
	private String userMainStatus = "";

	/**
	 * 인증 Key.
	 */
	private String userAuthKey = "";

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
	 * 결제PIN 정보.
	 */
	private MarketPinInfo pinInfo;

	/**
	 * 실명인증 정보.
	 */
	private MbrAuth mbrAuth;

	/**
	 * Tstore 기타 정보.
	 */
	private TstoreEtcInfo tstoreEtcInfo;

	/**
	 * @return userMainStatus
	 */
	public String getUserMainStatus() {
		return this.userMainStatus;
	}

	/**
	 * @param userMainStatus
	 *            String
	 */
	public void setUserMainStatus(String userMainStatus) {
		this.userMainStatus = userMainStatus;
	}

	/**
	 * @return userAuthKey
	 */
	public String getUserAuthKey() {
		return this.userAuthKey;
	}

	/**
	 * @param userAuthKey
	 *            String
	 */
	public void setUserAuthKey(String userAuthKey) {
		this.userAuthKey = userAuthKey;
	}

	/**
	 * @return userInfo
	 */
	public UserInfo getUserInfo() {
		return this.userInfo;
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
		return this.agreementList;
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
		return this.deviceInfo;
	}

	/**
	 * @param deviceInfo
	 *            DeviceInfo
	 */
	public void setDeviceInfo(DeviceInfo deviceInfo) {
		this.deviceInfo = deviceInfo;
	}

	/**
	 * @return mbrAuth
	 */
	public MbrAuth getMbrAuth() {
		return this.mbrAuth;
	}

	/**
	 * @param mbrAuth
	 *            MbrAuth
	 */
	public void setMbrAuth(MbrAuth mbrAuth) {
		this.mbrAuth = mbrAuth;
	}

	/**
	 * @return tstoreEtcInfo
	 */
	public TstoreEtcInfo getTstoreEtcInfo() {
		return this.tstoreEtcInfo;
	}

	/**
	 * @param tstoreEtcInfo
	 *            TstoreEtcInfo
	 */
	public void setTstoreEtcInfo(TstoreEtcInfo tstoreEtcInfo) {
		this.tstoreEtcInfo = tstoreEtcInfo;
	}

	/**
	 * @return pinInfo
	 */
	public MarketPinInfo getPinInfo() {
		return this.pinInfo;
	}

	/**
	 * @param pinInfo
	 *            MarketPinInfo
	 */
	public void setPinInfo(MarketPinInfo pinInfo) {
		this.pinInfo = pinInfo;
	}

}
