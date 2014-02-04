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
	private String isSktPause = "";

	/**
	 * 정책 리스트
	 */
	private List<IndividualPolicyInfo> policyCodeList = null;

	/**
	 * 사용자 Key
	 */
	private String userKey = "";

	/**
	 * 기기 Key
	 */
	private String deviceKey = "";

	/**
	 * 이동 통신사
	 */
	private String deviceTelecom = "";

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

}
