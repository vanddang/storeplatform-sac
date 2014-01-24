package com.skplanet.storeplatform.sac.client.member.vo.miscellaneous;

import org.codehaus.jackson.map.annotate.JsonSerialize;
import org.codehaus.jackson.map.annotate.JsonSerialize.Inclusion;

/**
 * 
 * 5.3.10. 사용자별 정책 삭제 [RESPONSE]
 * 
 * Updated on : 2014. 1. 6. Updated by : 김다슬, 인크로스.
 */
@JsonSerialize(include = Inclusion.NON_NULL)
public class RemoveIndividualPolicyRes extends IndividualPolicyInfo {
	private static final long serialVersionUID = 1L;
}
