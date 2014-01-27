package com.skplanet.storeplatform.sac.client.member.vo.user;

import org.codehaus.jackson.map.annotate.JsonSerialize;
import org.codehaus.jackson.map.annotate.JsonSerialize.Inclusion;

import com.skplanet.storeplatform.framework.core.common.vo.CommonInfo;

/**
 * [REQUEST] 단말 AOM 지원여부 확인
 * 
 * Updated on : 2014. 1. 27. Updated by : 강신완, 부르칸.
 */
@JsonSerialize(include = Inclusion.NON_NULL)
public class SupportAomRes extends CommonInfo {
	private static final long serialVersionUID = 1L;

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
