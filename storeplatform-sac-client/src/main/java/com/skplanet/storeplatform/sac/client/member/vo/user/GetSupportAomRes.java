package com.skplanet.storeplatform.sac.client.member.vo.user;

import org.codehaus.jackson.map.annotate.JsonSerialize;
import org.codehaus.jackson.map.annotate.JsonSerialize.Inclusion;

import com.skplanet.storeplatform.framework.core.common.vo.CommonInfo;

/**
 * [RESPONSE] 단말 AOM 지원 여부 확인
 * 
 * Updated on : 2014. 1. 6. Updated by : 강신완, 부르칸.
 */
@JsonSerialize(include = Inclusion.NON_NULL)
public class GetSupportAomRes extends CommonInfo {

	private static final long serialVersionUID = 1L;

	/* AOM 지원여부 */
	private String isAomSupport;

	public String getIsAomSupport() {
		return this.isAomSupport;
	}

	public void setIsAomSupport(String isAomSupport) {
		this.isAomSupport = isAomSupport;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

}
