package com.skplanet.storeplatform.sac.client.internal.member.miscellaneous.vo;

import java.util.List;

import org.hibernate.validator.constraints.NotBlank;

import com.skplanet.storeplatform.framework.core.common.vo.CommonInfo;

/**
 * 
 * 2.3.8. 사용자별 정책 조회 [REQUEST]
 * 
 * Updated on : 2014. 1. 6. Updated by : 강신완, 부르칸.
 */

public class GetIndividualPolicySacReq extends CommonInfo {
	private static final long serialVersionUID = 1L;

	/**
	 * 확인할 정책의 key값.
	 */
	@NotBlank
	private String key;

	/** TENANT_ID. */
	private String tenantId;

	/**
	 * 정책 코드리스트.
	 */
	private List<PolicyCode> policyCodeList;

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
	 * @return the tenantId
	 */
	public String getTenantId() {
		return this.tenantId;
	}

	/**
	 * @param tenantId
	 *            the tenantId to set
	 */
	public void setTenantId(String tenantId) {
		this.tenantId = tenantId;
	}

	/**
	 * @return the policyCodeList
	 */
	public List<PolicyCode> getPolicyCodeList() {
		return this.policyCodeList;
	}

	/**
	 * @param policyCodeList
	 *            the policyCodeList to set
	 */
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

	}
}
