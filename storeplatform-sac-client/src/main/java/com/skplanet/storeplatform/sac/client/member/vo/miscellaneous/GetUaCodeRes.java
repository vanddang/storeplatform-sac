package com.skplanet.storeplatform.sac.client.member.vo.miscellaneous;

import org.codehaus.jackson.map.annotate.JsonSerialize;
import org.codehaus.jackson.map.annotate.JsonSerialize.Inclusion;

import com.skplanet.storeplatform.framework.core.common.vo.CommonInfo;

/**
 * 
 * [RESPONSE] UA 코드 정보 조회
 * 
 * Updated on : 2014. 1. 6. Updated by : 김다슬, 인크로스.
 */
@JsonSerialize(include = Inclusion.NON_NULL)
public class GetUaCodeRes extends CommonInfo {
	private static final long serialVersionUID = 1L;

	/**
	 * UA 코드.
	 */
	private String uaCd;

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
