package com.skplanet.storeplatform.sac.client.member.vo.user;

import java.util.List;

import org.codehaus.jackson.map.annotate.JsonSerialize;
import org.codehaus.jackson.map.annotate.JsonSerialize.Inclusion;

import com.skplanet.storeplatform.framework.core.common.vo.CommonInfo;
import com.skplanet.storeplatform.sac.client.member.vo.miscellaneous.IndividualPolicyInfo;

/**
 * [RESPONSE] DeviceId를 이용하여 회원 정보 조회
 * 
 * Updated on : 2014. 2. 3. Updated by : 심대진, 다모아 솔루션.
 */
@JsonSerialize(include = Inclusion.NON_NULL)
public class DetailByDeviceIdSacRes extends CommonInfo {

	private static final long serialVersionUID = 1L;

	/**
	 * OMPD 여부 (Y or N).
	 */
	private String isOpmd = "";

	/**
	 * OMPD 모번호.
	 */
	private String msisdn = "";

	/**
	 * SKT 이용정지회원 여부 (Y or N).
	 */
	private String isSktPause = "";

	/**
	 * 정책 리스트.
	 */
	private List<IndividualPolicyInfo> policyCodeList = null;

	/**
	 * 사용자 Key.
	 */
	private String userKey = "";

	/**
	 * 사용자 구분 코드.
	 */
	private String userType = "";

	/**
	 * 사용자명.
	 */
	private String userName = "";

	/**
	 * 사용자 아이디.
	 */
	private String userId = "";

	/**
	 * 실명인증 여부.
	 */
	private String isRealNameYn = "";

	/**
	 * 사용자 생년월일.
	 */
	private String userBirthDay = "";

	/**
	 * 기기 Key.
	 */
	private String deviceKey = "";

	/**
	 * 기기 아이디.
	 */
	private String deviceId = "";

	/**
	 * 이동 통신사.
	 */
	private String deviceTelecom = "";

	/**
	 * 모델명.
	 */
	private String model = "";

	/**
	 * 선물수신가능 단말 여부.
	 */
	private String giftYn = "";

	/**
	 * @return String : isOpmd
	 */
	public String getIsOpmd() {
		return this.isOpmd;
	}

	/**
	 * @param isOpmd
	 *            String : the isOpmd to set
	 */
	public void setIsOpmd(String isOpmd) {
		this.isOpmd = isOpmd;
	}

	/**
	 * @return String : msisdn
	 */
	public String getMsisdn() {
		return this.msisdn;
	}

	/**
	 * @param msisdn
	 *            String : the msisdn to set
	 */
	public void setMsisdn(String msisdn) {
		this.msisdn = msisdn;
	}

	/**
	 * @return String : isSktPause
	 */
	public String getIsSktPause() {
		return this.isSktPause;
	}

	/**
	 * @param isSktPause
	 *            String : the isSktPause to set
	 */
	public void setIsSktPause(String isSktPause) {
		this.isSktPause = isSktPause;
	}

	/**
	 * @return List<IndividualPolicyInfo> : policyCodeList
	 */
	public List<IndividualPolicyInfo> getPolicyCodeList() {
		return this.policyCodeList;
	}

	/**
	 * @param policyCodeList
	 *            List<IndividualPolicyInfo> : the policyCodeList to set
	 */
	public void setPolicyCodeList(List<IndividualPolicyInfo> policyCodeList) {
		this.policyCodeList = policyCodeList;
	}

	/**
	 * @return String : userKey
	 */
	public String getUserKey() {
		return this.userKey;
	}

	/**
	 * @param userKey
	 *            String : the userKey to set
	 */
	public void setUserKey(String userKey) {
		this.userKey = userKey;
	}

	/**
	 * @return String : userType
	 */
	public String getUserType() {
		return this.userType;
	}

	/**
	 * @param userType
	 *            String : the userType to set
	 */
	public void setUserType(String userType) {
		this.userType = userType;
	}

	/**
	 * @return String : userName
	 */
	public String getUserName() {
		return this.userName;
	}

	/**
	 * @param userName
	 *            String : the userName to set
	 */
	public void setUserName(String userName) {
		this.userName = userName;
	}

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
	 * @return String : isRealNameYn
	 */
	public String getIsRealNameYn() {
		return this.isRealNameYn;
	}

	/**
	 * @param isRealNameYn
	 *            String : the isRealNameYn to set
	 */
	public void setIsRealNameYn(String isRealNameYn) {
		this.isRealNameYn = isRealNameYn;
	}

	/**
	 * @return String : userBirthDay
	 */
	public String getUserBirthDay() {
		return this.userBirthDay;
	}

	/**
	 * @param userBirthDay
	 *            String : the userBirthDay to set
	 */
	public void setUserBirthDay(String userBirthDay) {
		this.userBirthDay = userBirthDay;
	}

	/**
	 * @return String : deviceKey
	 */
	public String getDeviceKey() {
		return this.deviceKey;
	}

	/**
	 * @param deviceKey
	 *            String : the deviceKey to set
	 */
	public void setDeviceKey(String deviceKey) {
		this.deviceKey = deviceKey;
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
	 * @return String : model
	 */
	public String getModel() {
		return this.model;
	}

	/**
	 * @param model
	 *            String : the model to set
	 */
	public void setModel(String model) {
		this.model = model;
	}

	/**
	 * @return String : giftYn
	 */
	public String getGiftYn() {
		return this.giftYn;
	}

	/**
	 * @param giftYn
	 *            String : the giftYn to set
	 */
	public void setGiftYn(String giftYn) {
		this.giftYn = giftYn;
	}

}
