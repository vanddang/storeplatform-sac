package com.skplanet.storeplatform.sac.client.member.vo.common;

import org.codehaus.jackson.map.annotate.JsonSerialize;
import org.codehaus.jackson.map.annotate.JsonSerialize.Inclusion;

import com.skplanet.storeplatform.framework.core.common.vo.CommonInfo;

/**
 * 사용자 부가정보
 * 
 * Updated on : 2014. 1. 7. Updated by : 한서구, 부르칸.
 */
@JsonSerialize(include = Inclusion.NON_NULL)
public class SellerRight extends CommonInfo {

	private static final long serialVersionUID = 1L;

	private String rightProfile;
	private String rightProfileValue;

	public String getRightProfile() {
		return this.rightProfile;
	}

	public void setRightProfile(String rightProfile) {
		this.rightProfile = rightProfile;
	}

	public String getRightProfileValue() {
		return this.rightProfileValue;
	}

	public void setRightProfileValue(String rightProfileValue) {
		this.rightProfileValue = rightProfileValue;
	}

}
