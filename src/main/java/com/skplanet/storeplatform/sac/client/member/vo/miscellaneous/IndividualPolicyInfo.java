package com.skplanet.storeplatform.sac.client.member.vo.miscellaneous;

import com.skplanet.storeplatform.framework.core.common.vo.CommonInfo;

/**
 * 
 * 사용자별 정책 공통 VO
 * 
 * Updated on : 2014. 1. 6. Updated by : 김다슬, 인크로스.
 */
public class IndividualPolicyInfo extends CommonInfo {

	private static final long serialVersionUID = 1L;
	/**
	 * 정책 코드.
	 */
	private String policyCode;
	/**
	 * 확인할 정책의 key값.
	 */
	private String key;
	/**
	 * key값에 대한 value.
	 */
	private String value;

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

}
