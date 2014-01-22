package com.skplanet.storeplatform.sac.client.member.vo.miscellaneous;

import java.util.List;

import org.hibernate.validator.constraints.NotBlank;

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
	 * 확인할 정책의 key값.
	 */
	@NotBlank(message = "key should not be empty")
	private String key;

	private List<PolicyCode> policyCodeList;

	public String getKey() {
		return this.key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public List<PolicyCode> getPolicyCodeList() {
		return this.policyCodeList;
	}

	public void setPolicyCodeList(List<PolicyCode> policyCodeList) {
		this.policyCodeList = policyCodeList;
	}

	public static class PolicyCode {
		@NotBlank(message = "key should not be empty")
		public String policyCode;

		public String getPolicyCode() {
			return this.policyCode;
		}

		public void setPolicyCode(String policyCode) {
			this.policyCode = policyCode;
		}
	}
}
