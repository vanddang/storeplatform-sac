package com.skplanet.storeplatform.sac.client.member.vo.miscellaneous;

import org.codehaus.jackson.map.annotate.JsonSerialize;
import org.codehaus.jackson.map.annotate.JsonSerialize.Inclusion;

/**
 * 
 * [RESPONSE] 부가서비스 가입 조회
 * 
 * Updated on : 2014. 1. 6. Updated by : 김다슬, 인크로스.
 */
@JsonSerialize(include = Inclusion.NON_NULL)
public class GetAdditionalServiceRes extends AdditionalServicInfo {
	private static final long serialVersionUID = 1L;

	/**
	 * 각 부가서비스별 가입여부 결과
	 */
	private String svcJoinResult;

	public String getSvcJoinResult() {
		return this.svcJoinResult;
	}

	public void setSvcJoinResult(String svcJoinResult) {
		this.svcJoinResult = svcJoinResult;
	}

}
