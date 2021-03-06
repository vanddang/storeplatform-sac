package com.skplanet.storeplatform.sac.client.member.vo.miscellaneous;

import java.util.List;

import org.codehaus.jackson.map.annotate.JsonSerialize;
import org.codehaus.jackson.map.annotate.JsonSerialize.Inclusion;

import com.skplanet.storeplatform.framework.core.common.vo.CommonInfo;

/**
 * 
 * 2.3.8. 사용자별 정책 조회 [RESPONSE]
 * 
 * Updated on : 2014. 1. 6. Updated by : 김다슬, 인크로스.
 */
@JsonSerialize(include = Inclusion.NON_NULL)
public class GetIndividualPolicyRes extends CommonInfo {

	private static final long serialVersionUID = 1L;

	/** 정책 리스트. */
	private List<IndividualPolicyInfo> policyList;

	/**
	 * @return the policyList
	 */
	public List<IndividualPolicyInfo> getPolicyList() {
		return this.policyList;
	}

	/**
	 * @param policyList
	 *            the policyList to set
	 */
	public void setPolicyList(List<IndividualPolicyInfo> policyList) {
		this.policyList = policyList;
	}

}
