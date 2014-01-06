package com.skplanet.storeplatform.sac.client.member.vo.common;

import org.codehaus.jackson.map.annotate.JsonSerialize;
import org.codehaus.jackson.map.annotate.JsonSerialize.Inclusion;

import com.skplanet.storeplatform.framework.core.common.vo.CommonInfo;

/**
 * 사용자 부가정보
 * 
 * Updated on : 2014. 1. 6. Updated by : 반범진, 지티소프트.
 */

@JsonSerialize(include = Inclusion.NON_NULL)
public class UserExtraInfo extends CommonInfo {

	private static final long serialVersionUID = 1L;

	/**
	 * 부가정보 코드
	 */
	private String extraProfile;

	private String extraProfileCode;

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

	public String getExtraProfileCode() {
		return this.extraProfileCode;
	}

	public void setExtraProfileCode(String extraProfileCode) {
		this.extraProfileCode = extraProfileCode;
	}

}
