package com.skplanet.storeplatform.sac.client.member.vo.user;

import java.util.List;

import org.hibernate.validator.constraints.NotEmpty;

import com.skplanet.storeplatform.framework.core.common.vo.CommonInfo;

/**
 * [REQUEST] DeviceId를 이용하여 회원 정보 조회
 * 
 * Updated on : 2014. 2. 3. Updated by : 심대진, 다모아 솔루션.
 */
public class DetailByDeviceIdSacReq extends CommonInfo {

	private static final long serialVersionUID = 1L;

	/**
	 * 기기 ID
	 */
	@NotEmpty
	private String deviceId = "";

	/**
	 * 기기 ID 타입
	 */
	@NotEmpty
	private String deviceIdType = "";

	/**
	 * 확인할 정책의 Key 값
	 */
	private String key = "";

	/**
	 * 정책 리스트
	 */
	private List<PolicyCodeInfo> policyCodeList = null;

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
	 * @return String : key
	 */
	public String getKey() {
		return this.key;
	}

	/**
	 * @param key
	 *            String : the key to set
	 */
	public void setKey(String key) {
		this.key = key;
	}

	/**
	 * @return List<PolicyCodeInfo> : policyCodeList
	 */
	public List<PolicyCodeInfo> getPolicyCodeList() {
		return this.policyCodeList;
	}

	/**
	 * @param policyCodeList
	 *            List<PolicyCodeInfo> : the policyCodeList to set
	 */
	public void setPolicyCodeList(List<PolicyCodeInfo> policyCodeList) {
		this.policyCodeList = policyCodeList;
	}

	/**
	 * 정책 코드 정보
	 * 
	 * Updated on : 2014. 2. 3. Updated by : 심대진, 다모아 솔루션.
	 */
	public static class PolicyCodeInfo extends CommonInfo {

		private static final long serialVersionUID = 1L;

		private String policyCode;

		/**
		 * @return String : policyCode
		 */
		public String getPolicyCode() {
			return this.policyCode;
		}

		/**
		 * @param policyCode
		 *            String : the policyCode to set
		 */
		public void setPolicyCode(String policyCode) {
			this.policyCode = policyCode;
		}

	}

}
