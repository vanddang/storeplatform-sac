package com.skplanet.storeplatform.sac.client.member.vo.user;

import java.util.List;

import org.hibernate.validator.constraints.NotEmpty;

import com.skplanet.storeplatform.framework.core.common.vo.CommonInfo;
import com.skplanet.storeplatform.sac.client.member.vo.common.AgreementInfo;

/**
 * [REQUEST] ID 회원 약관 동의 가입 (One ID 회원)
 * 
 * Updated on : 2014. 1. 3. Updated by : 심대진, 다모아 솔루션.
 */
public class CreateByAgreementReq extends CommonInfo {

	private static final long serialVersionUID = 1L;

	/**
	 * 기기 ID (msisdn, uuid).
	 */
	@NotEmpty(message = "필수 파라미터 입니다.")
	private String deviceId;

	/**
	 * 사용자 아이디.
	 */
	@NotEmpty(message = "필수 파라미터 입니다.")
	private String userId;

	/**
	 * 이동 통신사.
	 */
	@NotEmpty(message = "필수 파라미터 입니다.")
	private String deviceTelecom;

	/**
	 * 가입 채널 코드.
	 */
	@NotEmpty(message = "필수 파라미터 입니다.")
	private String joinId;

	/**
	 * 약관 동의 정보.
	 */
	@NotEmpty(groups = AgreementInfo.class, message = "필수 파라미터 입니다.")
	private List<AgreementInfo> agreementList;

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
	 * @return String : joinId
	 */
	public String getJoinId() {
		return this.joinId;
	}

	/**
	 * @param joinId
	 *            String : the joinId to set
	 */
	public void setJoinId(String joinId) {
		this.joinId = joinId;
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
