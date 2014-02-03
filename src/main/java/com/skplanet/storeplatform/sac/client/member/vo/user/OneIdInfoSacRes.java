package com.skplanet.storeplatform.sac.client.member.vo.user;

import org.codehaus.jackson.map.annotate.JsonSerialize;
import org.codehaus.jackson.map.annotate.JsonSerialize.Inclusion;

import com.skplanet.storeplatform.framework.core.common.vo.CommonInfo;

/**
 * [RESPONSE] OneId 정보 조회
 * 
 * Updated on : 2014. 2. 3. Updated by : 강신완, 부르칸.
 */
@JsonSerialize(include = Inclusion.NON_NULL)
public class OneIdInfoSacRes extends CommonInfo {

	private static final long serialVersionUID = 1L;

	/* 실명인증 여부 */
	private String idctYn;

	/* CI */
	private String ci;

	private String ocbYn;

	public String getIdctYn() {
		return this.idctYn;
	}

	public void setIdctYn(String idctYn) {
		this.idctYn = idctYn;
	}

	public String getCi() {
		return this.ci;
	}

	public void setCi(String ci) {
		this.ci = ci;
	}

	public String getOcbYn() {
		return this.ocbYn;
	}

	public void setOcbYn(String ocbYn) {
		this.ocbYn = ocbYn;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

}
