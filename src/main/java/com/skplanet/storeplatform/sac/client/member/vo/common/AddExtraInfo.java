package com.skplanet.storeplatform.sac.client.member.vo.common;

import com.skplanet.storeplatform.framework.core.common.vo.CommonInfo;

/**
 * 휴대기기 부가정보
 * 
 * Updated on : 2014. 1. 6. Updated by : 반범진, 지티소프트.
 */
public class AddExtraInfo extends CommonInfo {

	private static final long serialVersionUID = 1L;

	/**
	 * 부가정보 코드
	 */
	private String extraProfile;

	/**
	 * 부가정보 값
	 */
	private String extraProfileValue;

	public String getExtraProfile() {
		return this.extraProfile;
	}

	public void setExtraProfile(String extraProfile) {
		this.extraProfile = extraProfile;
	}

	public String getExtraProfileValue() {
		return this.extraProfileValue;
	}

	public void setExtraProfileValue(String extraProfileValue) {
		this.extraProfileValue = extraProfileValue;
	}

}
