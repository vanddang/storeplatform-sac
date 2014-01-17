package com.skplanet.storeplatform.sac.client.member.vo.miscellaneous;

import org.codehaus.jackson.map.annotate.JsonSerialize;
import org.codehaus.jackson.map.annotate.JsonSerialize.Inclusion;
import org.hibernate.validator.constraints.NotEmpty;

import com.skplanet.storeplatform.framework.core.common.vo.CommonInfo;

/**
 * 
 * [REQUEST] OPMD 모회선 번호 조회
 * 
 * Updated on : 2014. 1. 6. Updated by : 김다슬, 인크로스.
 */
@JsonSerialize(include = Inclusion.NON_NULL)
public class GetOpmdReq extends CommonInfo {
	private static final long serialVersionUID = 1L;

	/**
	 * 자회선 번호
	 */
	@NotEmpty(message = "필수 파라미터 입니다.")
	private String msisdn;

	/**
	 * @return String : msisdn
	 */
	public String getMsisdn() {
		return this.msisdn;
	}

	/**
	 * @parma msisdn
	 */
	public void setMsisdn(String msisdn) {
		this.msisdn = msisdn;
	}

}
