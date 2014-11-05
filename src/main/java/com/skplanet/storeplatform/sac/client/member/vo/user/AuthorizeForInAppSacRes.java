package com.skplanet.storeplatform.sac.client.member.vo.user;

import java.util.ArrayList;

import org.codehaus.jackson.map.annotate.JsonSerialize;
import org.codehaus.jackson.map.annotate.JsonSerialize.Inclusion;

import com.skplanet.storeplatform.framework.core.common.vo.CommonInfo;
import com.skplanet.storeplatform.sac.client.member.vo.common.Agreement;
import com.skplanet.storeplatform.sac.client.member.vo.common.DeviceInfo;
import com.skplanet.storeplatform.sac.client.member.vo.common.MbrAuth;
import com.skplanet.storeplatform.sac.client.member.vo.common.TstoreEtcInfo;
import com.skplanet.storeplatform.sac.client.member.vo.common.UserInfo;

/**
 * [Response] PayPlanet에 InApp용으로 제공되는 T Store 회원인증.
 * 
 * Updated on : 2014. 11. 3. Updated by : 반범진, SK 플래닛.
 */
@JsonSerialize(include = Inclusion.NON_NULL)
public class AuthorizeForInAppSacRes extends CommonInfo {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * 트랜잭션 번호.
	 */
	private String trxNo = "";
	/**
	 * 테넌트 ID.
	 */
	private String tenantId = "";
	/**
	 * 기기 ID (msisdn).
	 */
	private String deviceId = "";
	/**
	 * 통신사.
	 */
	private String deviceTelecom = "";
	/**
	 * 회원 상태코드.
	 */
	private String userStatus = "";
	/**
	 * 각 마켓 회원가입 URL.
	 */
	private String userJoinUrl = "";
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
	private ArrayList<Agreement> agreementList;

	/**
	 * 휴대기기 정보.
	 */
	private DeviceInfo deviceInfo;

	/**
	 * 실명인증 정보.
	 */
	private MbrAuth mbrAuth;

	/**
	 * Tstore 기타 정보.
	 */
	private TstoreEtcInfo tstoreEtcInfo;

	/**
	 * @return trxNo
	 */
	public String getTrxNo() {
		return this.trxNo;
	}

	/**
	 * @param trxNo
	 *            String
	 */
	public void setTrxNo(String trxNo) {
		this.trxNo = trxNo;
	}

	/**
	 * @return tenantId
	 */
	public String getTenantId() {
		return this.tenantId;
	}

	/**
	 * @param tenantId
	 *            String
	 */
	public void setTenantId(String tenantId) {
		this.tenantId = tenantId;
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
	 * @return userStatus
	 */
	public String getUserStatus() {
		return this.userStatus;
	}

	/**
	 * @param userStatus
	 *            String
	 */
	public void setUserStatus(String userStatus) {
		this.userStatus = userStatus;
	}

	/**
	 * @return userJoinUrl
	 */
	public String getUserJoinUrl() {
		return this.userJoinUrl;
	}

	/**
	 * @param userJoinUrl
	 *            String
	 */
	public void setUserJoinUrl(String userJoinUrl) {
		this.userJoinUrl = userJoinUrl;
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
	public ArrayList<Agreement> getAgreementList() {
		return this.agreementList;
	}

	/**
	 * @param agreementList
	 *            ArrayList<Agreement>
	 */
	public void setAgreementList(ArrayList<Agreement> agreementList) {
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

}
