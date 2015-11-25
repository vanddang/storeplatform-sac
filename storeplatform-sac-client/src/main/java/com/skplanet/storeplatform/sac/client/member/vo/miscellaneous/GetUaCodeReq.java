package com.skplanet.storeplatform.sac.client.member.vo.miscellaneous;

import org.codehaus.jackson.map.annotate.JsonSerialize;
import org.codehaus.jackson.map.annotate.JsonSerialize.Inclusion;

import com.skplanet.storeplatform.framework.core.common.vo.CommonInfo;

/**
 * 
 * [REQUEST] UA 코드 정보 조회
 * 
 * Updated on : 2014. 1. 6. Updated by : 김다슬, 인크로스.
 */
@JsonSerialize(include = Inclusion.NON_NULL)
public class GetUaCodeReq extends CommonInfo {
	private static final long serialVersionUID = 1L;

	/**
	 * 휴대폰 번호.
	 */
	private String msisdn;

	/**
	 * 단말 모델명.
	 */
	private String deviceModelNo;

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
	 * @return the deviceModelNo
	 */
	public String getDeviceModelNo() {
		return this.deviceModelNo;
	}

	/**
	 * @param deviceModelNo
	 *            the deviceModelNo to set
	 */
	public void setDeviceModelNo(String deviceModelNo) {
		this.deviceModelNo = deviceModelNo;
	}

}
