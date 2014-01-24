package com.skplanet.storeplatform.sac.client.member.vo.miscellaneous;

import org.codehaus.jackson.map.annotate.JsonSerialize;
import org.codehaus.jackson.map.annotate.JsonSerialize.Inclusion;

/**
 * 
 * 5.3.9. 사용자별 정책 등록/수정 [RESPONSE]
 * 
 * Updated on : 2014. 1. 6. Updated by : 김다슬, 인크로스.
 */
@JsonSerialize(include = Inclusion.NON_NULL)
public class CreateIndividualPolicyRes extends IndividualPolicyInfo {
	private static final long serialVersionUID = 1L;

}
