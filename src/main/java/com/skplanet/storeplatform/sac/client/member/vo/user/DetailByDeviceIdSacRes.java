package com.skplanet.storeplatform.sac.client.member.vo.user;

import java.util.List;

import org.codehaus.jackson.map.annotate.JsonSerialize;
import org.codehaus.jackson.map.annotate.JsonSerialize.Inclusion;

import com.skplanet.storeplatform.framework.core.common.vo.CommonInfo;
import com.skplanet.storeplatform.sac.client.member.vo.common.Agreement;
import com.skplanet.storeplatform.sac.client.member.vo.common.DeviceInfo;
import com.skplanet.storeplatform.sac.client.member.vo.common.MbrAuth;
import com.skplanet.storeplatform.sac.client.member.vo.common.MbrLglAgent;
import com.skplanet.storeplatform.sac.client.member.vo.common.UserInfo;
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
	 * OMPD 여부 (Y or N)
	 */
	private String isOpmd = "";

	/**
	 * OMPD 모번호
	 */
	private String msisdn = "";

	/**
	 * SKT 이용정지회원 여부 (Y or N)
	 */
	private String isSktStop = "";

	/**
	 * 정책 리스트
	 */
	private List<IndividualPolicyInfo> policyCodeList = null;

	/**
	 * 휴대기기정보 리스트
	 */
	private List<DeviceInfo> deviceInfoList = null;

	/**
	 * 사용자 정보
	 */
	private UserInfo userInfo = null;

	/**
	 * 약관동의정보 리스트
	 */
	private List<Agreement> agreementList = null;

	/**
	 * 사용자 인증정보
	 */
	private List<MbrAuth> mbrAuthInfo = null;

	/**
	 * 법정대리인 정보
	 */
	private List<MbrLglAgent> mbrLglAgentInfo = null;

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
	 * @return String : isSktStop
	 */
	public String getIsSktStop() {
		return this.isSktStop;
	}

	/**
	 * @param isSktStop
	 *            String : the isSktStop to set
	 */
	public void setIsSktStop(String isSktStop) {
		this.isSktStop = isSktStop;
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
	 * @return List<DeviceInfo> : deviceInfoList
	 */
	public List<DeviceInfo> getDeviceInfoList() {
		return this.deviceInfoList;
	}

	/**
	 * @param deviceInfoList
	 *            List<DeviceInfo> : the deviceInfoList to set
	 */
	public void setDeviceInfoList(List<DeviceInfo> deviceInfoList) {
		this.deviceInfoList = deviceInfoList;
	}

	/**
	 * @return UserInfo : userInfo
	 */
	public UserInfo getUserInfo() {
		return this.userInfo;
	}

	/**
	 * @param userInfo
	 *            UserInfo : the userInfo to set
	 */
	public void setUserInfo(UserInfo userInfo) {
		this.userInfo = userInfo;
	}

	/**
	 * @return List<Agreement> : agreementList
	 */
	public List<Agreement> getAgreementList() {
		return this.agreementList;
	}

	/**
	 * @param agreementList
	 *            List<Agreement> : the agreementList to set
	 */
	public void setAgreementList(List<Agreement> agreementList) {
		this.agreementList = agreementList;
	}

	/**
	 * @return List<MbrAuth> : mbrAuthInfo
	 */
	public List<MbrAuth> getMbrAuthInfo() {
		return this.mbrAuthInfo;
	}

	/**
	 * @param mbrAuthInfo
	 *            List<MbrAuth> : the mbrAuthInfo to set
	 */
	public void setMbrAuthInfo(List<MbrAuth> mbrAuthInfo) {
		this.mbrAuthInfo = mbrAuthInfo;
	}

	/**
	 * @return List<MbrLglAgent> : mbrLglAgentInfo
	 */
	public List<MbrLglAgent> getMbrLglAgentInfo() {
		return this.mbrLglAgentInfo;
	}

	/**
	 * @param mbrLglAgentInfo
	 *            List<MbrLglAgent> : the mbrLglAgentInfo to set
	 */
	public void setMbrLglAgentInfo(List<MbrLglAgent> mbrLglAgentInfo) {
		this.mbrLglAgentInfo = mbrLglAgentInfo;
	}

}
