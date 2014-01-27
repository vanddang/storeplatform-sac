package com.skplanet.storeplatform.sac.client.member.vo.miscellaneous;

import org.codehaus.jackson.map.annotate.JsonSerialize;
import org.codehaus.jackson.map.annotate.JsonSerialize.Inclusion;
import org.hibernate.validator.constraints.NotBlank;

import com.skplanet.storeplatform.framework.core.common.vo.CommonInfo;

/**
 * 
 * 2.3.10. 사용자별 정책 삭제 [REQUEST]
 * 
 * Updated on : 2014. 1. 6. Updated by : 김다슬, 인크로스.
 */
@JsonSerialize(include = Inclusion.NON_NULL)
public class RemoveIndividualPolicyReq extends CommonInfo {
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
