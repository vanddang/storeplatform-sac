package com.skplanet.storeplatform.sac.client.member.vo.miscellaneous;

import java.util.List;

import org.hibernate.validator.constraints.NotBlank;

import com.skplanet.storeplatform.framework.core.common.vo.CommonInfo;

/**
 * 
 * 2.3.8. 사용자별 정책 조회 [REQUEST]
 * 
 * Updated on : 2014. 1. 6. Updated by : 김다슬, 인크로스.
 */

public class GetIndividualPolicyReq extends CommonInfo {
	private static final long serialVersionUID = 1L;

	/**
	 * 확인할 정책의 key값.
	 */
	@NotBlank
	private String key;

	/**
	 * 정책 코드리스트.
	 */
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

	/**
	 * 정책 코드.
	 * 
	 * Updated on : 2014. 1. 22. Updated by : 김경복, 부르칸.
	 */
	public static class PolicyCode extends CommonInfo {

		private static final long serialVersionUID = 1L;

		/** 정책 코드. */
		@NotBlank
		public String policyCode;

		public String getPolicyCode() {
			return this.policyCode;
		}

		public void setPolicyCode(String policyCode) {
			this.policyCode = policyCode;
		}
	}
}
