package com.skplanet.storeplatform.sac.client.member.vo.miscellaneous;

import org.codehaus.jackson.map.annotate.JsonSerialize;
import org.codehaus.jackson.map.annotate.JsonSerialize.Inclusion;
import org.hibernate.validator.constraints.NotEmpty;

import com.skplanet.storeplatform.framework.core.common.vo.CommonInfo;

/**
 * 
 * [REQUEST] 부가서비스 가입 & 가입 조회 공통 VO
 * 
 * Updated on : 2014. 1. 6. Updated by : 김다슬, 인크로스.
 */
@JsonSerialize(include = Inclusion.NON_NULL)
public class AdditionalServicInfo extends CommonInfo {

	private static final long serialVersionUID = 1L;
	/**
	 * 휴대폰 번호
	 */
	@NotEmpty(message = "필수 파라미터 입니다.")
	private String msisdn;

	/**
	 * 부가서비스 코드
	 */
	@NotEmpty(message = "필수 파라미터 입니다.")
	private String svcCode;

	/**
	 * @return the msisdn
	 */
	public String getMsisdn() {
		return this.msisdn;
	}

	/**
	 * @param msisdn
	 *            the msisdn to set
	 */
	public void setMsisdn(String msisdn) {
		this.msisdn = msisdn;
	}

	/**
	 * @return the svcCode
	 */
	public String getSvcCode() {
		return this.svcCode;
	}

	/**
	 * @param svcCode
	 *            the svcCode to set
	 */
	public void setSvcCode(String svcCode) {
		this.svcCode = svcCode;
	}

}
