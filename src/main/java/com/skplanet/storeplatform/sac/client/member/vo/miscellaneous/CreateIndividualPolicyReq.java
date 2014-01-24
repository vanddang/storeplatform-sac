package com.skplanet.storeplatform.sac.client.member.vo.miscellaneous;

import org.hibernate.validator.constraints.NotBlank;

import com.skplanet.storeplatform.framework.core.common.vo.CommonInfo;

/**
 * 
 * 5.3.9. 사용자별 정책 등록/수정 [REQUEST]
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

	public String getPolicyCode() {
		return this.policyCode;
	}

	public void setPolicyCode(String policyCode) {
		this.policyCode = policyCode;
	}

	public String getKey() {
		return this.key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public String getValue() {
		return this.value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public String getRegId() {
		return this.regId;
	}

	public void setRegId(String regId) {
		this.regId = regId;
	}
}
