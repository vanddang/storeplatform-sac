package com.skplanet.storeplatform.sac.client.member.vo.miscellaneous;

import org.hibernate.validator.constraints.NotBlank;

import com.skplanet.storeplatform.framework.core.common.vo.CommonInfo;

/**
 * 
 * 2.3.9. 사용자별 정책 등록/수정 [REQUEST]
 * 
 * Updated on : 2014. 1. 6. Updated by : 김다슬, 인크로스.
 */
public class CreateIndividualPolicyReq extends CommonInfo {

	private static final long serialVersionUID = 1L;

	/**
	 * 정책 코드.
	 */
	@NotBlank
	private String policyCode;
	/**
	 * 확인할 정책의 key값.
	 */
	@NotBlank
	private String key;
	/**
	 * key값에 대한 value.
	 */
	@NotBlank
	private String value;

	/**
	 * 등록자.
	 */
	@NotBlank
	private String regId;

	/**
	 * @return the policyCode
	 */
	public String getPolicyCode() {
		return this.policyCode;
	}

	/**
	 * @param policyCode
	 *            the policyCode to set
	 */
	public void setPolicyCode(String policyCode) {
		this.policyCode = policyCode;
	}

	/**
	 * @return the key
	 */
	public String getKey() {
		return this.key;
	}

	/**
	 * @param key
	 *            the key to set
	 */
	public void setKey(String key) {
		this.key = key;
	}

	/**
	 * @return the value
	 */
	public String getValue() {
		return this.value;
	}

	/**
	 * @param value
	 *            the value to set
	 */
	public void setValue(String value) {
		this.value = value;
	}

	/**
	 * @return the regId
	 */
	public String getRegId() {
		return this.regId;
	}

	/**
	 * @param regId
	 *            the regId to set
	 */
	public void setRegId(String regId) {
		this.regId = regId;
	}

}
