package com.skplanet.storeplatform.sac.client.member.vo.user;

import java.util.List;

import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.NotEmpty;

import com.skplanet.storeplatform.framework.core.common.vo.CommonInfo;
import com.skplanet.storeplatform.sac.client.member.vo.common.AgreementInfo;
import com.skplanet.storeplatform.sac.client.member.vo.common.DeviceExtraInfo;

/**
 * [REQUEST] Save&Sync 가입
 * 
 * Updated on : 2014. 3. 5. Updated by : 심대진, 다모아 솔루션.
 */
public class CreateSaveAndSyncReq extends CommonInfo {

	private static final long serialVersionUID = 1L;

	/**
	 * 기기 ID
	 */
	@NotEmpty
	private String deviceId = "";

	/**
	 * 기기 ID 타입 (MSISDN or MAC)
	 */
	@NotEmpty
	@Pattern(regexp = "^msisdn|macaddress")
	private String deviceIdType = "";

	/**
	 * SMS 수신 여부.
	 */
	private String isRecvSms = "";

	/**
	 * 사용자 단말 부가 정보 리스트.
	 */
	private List<DeviceExtraInfo> deviceExtraInfoList = null;

	/**
	 * 약관동의 정보.
	 */
	@NotEmpty
	private List<AgreementInfo> agreementList = null;

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
