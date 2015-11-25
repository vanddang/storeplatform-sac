package com.skplanet.storeplatform.sac.client.member.vo.miscellaneous;

import org.codehaus.jackson.map.annotate.JsonSerialize;
import org.codehaus.jackson.map.annotate.JsonSerialize.Inclusion;

/**
 * 
 * [REQUEST] 부가서비스 가입
 * 
 * Updated on : 2014. 1. 6. Updated by : 김다슬, 인크로스.
 */
@JsonSerialize(include = Inclusion.NON_NULL)
public class CreateAdditionalServiceReq extends AdditionalServicInfo {

	private static final long serialVersionUID = 1L;

	/**
	 * MDN에 대한 서비스 관리번호.
	 */
	private String svcMngNum;

	/**
	 * @return the svcMngNum
	 */
	public String getSvcMngNum() {
		return this.svcMngNum;
	}

	/**
	 * @param svcMngNum
	 *            the svcMngNum to set
	 */
	public void setSvcMngNum(String svcMngNum) {
		this.svcMngNum = svcMngNum;
	}

}
