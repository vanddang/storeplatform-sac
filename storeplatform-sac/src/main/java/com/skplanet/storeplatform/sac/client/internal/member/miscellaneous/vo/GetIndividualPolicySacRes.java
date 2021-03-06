package com.skplanet.storeplatform.sac.client.internal.member.miscellaneous.vo;

import java.util.List;

import org.codehaus.jackson.map.annotate.JsonSerialize;
import org.codehaus.jackson.map.annotate.JsonSerialize.Inclusion;

import com.skplanet.storeplatform.framework.core.common.vo.CommonInfo;

/**
 * 
 * 2.3.8. 사용자별 정책 조회 [RESPONSE]
 * 
 * Updated on : 2014. 3. 11. Updated by : 강신완, 부르칸.
 * Updated on : 2016. 2. 12. Updated by : 윤보영, 카레즈.
 */
@JsonSerialize(include = Inclusion.NON_NULL)
public class GetIndividualPolicySacRes extends CommonInfo {

	private static final long serialVersionUID = 1L;

	/** 정책 리스트. */
    @Deprecated
	private List<IndividualPolicyInfoSac> policyList;

	/**
	 * @return the policyList
	 */
	public List<IndividualPolicyInfoSac> getPolicyList() {
		return this.policyList;
	}

	/**
	 * @param policyList
	 *            the policyList to set
	 */
	public void setPolicyList(List<IndividualPolicyInfoSac> policyList) {
		this.policyList = policyList;
	}

    /** 정책 리스트. */
    private List<IndividualPolicyInfoSac> policyCodeList;


    public List<IndividualPolicyInfoSac> getPolicyCodeList() {
        return policyCodeList;
    }

    public void setPolicyCodeList(List<IndividualPolicyInfoSac> policyCodeList) {
        this.policyCodeList = policyCodeList;
    }

}
