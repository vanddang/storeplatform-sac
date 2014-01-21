package com.skplanet.storeplatform.sac.client.member.vo.miscellaneous;

import org.hibernate.validator.constraints.NotEmpty;

import com.skplanet.storeplatform.framework.core.common.vo.CommonInfo;

/**
 * 
 * [REQUEST] 사용자별 정책 조회
 * 
 * Updated on : 2014. 1. 6. Updated by : 김다슬, 인크로스.
 */

public class GetIndividualPolicyReq extends CommonInfo {
	private static final long serialVersionUID = 1L;

	/**
	 * 정책 코드.
	 */
	@NotEmpty(message = "policyCode should not be empty")
	private String policyCode;
	/**
	 * 확인할 정책의 key값.
	 */
	@NotEmpty(message = "key should not be empty")
	private String key;

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
}
