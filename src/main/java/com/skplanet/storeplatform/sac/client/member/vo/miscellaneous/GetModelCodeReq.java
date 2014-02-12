/**
 * 
 */
package com.skplanet.storeplatform.sac.client.member.vo.miscellaneous;

import org.codehaus.jackson.map.annotate.JsonSerialize;
import org.codehaus.jackson.map.annotate.JsonSerialize.Inclusion;

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
	private String msisdn;

	/**
	 * UA 코드
	 */
	private String uaCd;

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
	 * @return the uaCd
	 */
	public String getUaCd() {
		return this.uaCd;
	}

	/**
	 * @param uaCd
	 *            the uaCd to set
	 */
	public void setUaCd(String uaCd) {
		this.uaCd = uaCd;
	}

}
