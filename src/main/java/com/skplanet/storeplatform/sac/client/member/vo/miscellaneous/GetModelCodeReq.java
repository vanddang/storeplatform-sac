/**
 * 
 */
package com.skplanet.storeplatform.sac.client.member.vo.miscellaneous;

import javax.validation.constraints.Pattern;

import org.codehaus.jackson.map.annotate.JsonSerialize;
import org.codehaus.jackson.map.annotate.JsonSerialize.Inclusion;
import org.hibernate.validator.constraints.NotBlank;

import com.skplanet.storeplatform.framework.core.common.vo.CommonInfo;

/**
 * [REQUEST]단말 모델 코드 조회
 * 
 * Updated on : 2014. 2. 4. Updated by : 김다슬, 인크로스.
 */
@JsonSerialize(include = Inclusion.NON_NULL)
public class GetModelCodeReq extends CommonInfo {

	private static final long serialVersionUID = 1L;

	/**
	 * MDN
	 */
	@NotBlank
	@Pattern(regexp = "[0-9]{10,11}", message = "유효하지 않은 휴대폰 번호입니다.")
	private String msisdn;

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

}
