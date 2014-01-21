package com.skplanet.storeplatform.sac.client.member.vo.miscellaneous;

import java.util.List;

import org.codehaus.jackson.map.annotate.JsonSerialize;
import org.codehaus.jackson.map.annotate.JsonSerialize.Inclusion;

import com.skplanet.storeplatform.framework.core.common.vo.CommonInfo;

/**
 * 
 * [RESPONSE] 사용자별 정책 조회
 * 
 * Updated on : 2014. 1. 6. Updated by : 김다슬, 인크로스.
 */
@JsonSerialize(include = Inclusion.NON_NULL)
public class GetIndividualPolicyRes extends CommonInfo {

	private static final long serialVersionUID = 1L;

	/** 정책 리스트. */
	private List<IndividualPolicyInfo> policyList;

	public List<IndividualPolicyInfo> getPolicyList() {
		return this.policyList;
	}

	public void setPolicyList(List<IndividualPolicyInfo> policyList) {
		this.policyList = policyList;
	}

}
