package com.skplanet.storeplatform.sac.client.member.vo.user;

import java.io.Serializable;

import org.hibernate.validator.constraints.NotEmpty;

import com.skplanet.storeplatform.framework.core.common.vo.CommonInfo;

/**
 * [REQUEST] 회원의 부가 정보(소셜 계정 아이디) 중복 체크
 * 
 * Updated on : 2015. 4. 9. Updated by : 반범진.
 */
public class CheckAdditionalInformationSacReq extends CommonInfo implements Serializable {

	private static final long serialVersionUID = 1L;

	/**
	 * 부가정보코드.
	 */
	@NotEmpty
	private String extraProfile;

	/**
	 * 부가정보값.
	 */
	@NotEmpty
	private String extraProfileValue;

	/**
	 * @return extraProfile
	 */
	public String getExtraProfile() {
		return this.extraProfile;
	}

	/**
	 * @param extraProfile
	 *            String
	 */
	public void setExtraProfile(String extraProfile) {
		this.extraProfile = extraProfile;
	}

	/**
	 * @return extraProfileValue
	 */
	public String getExtraProfileValue() {
		return this.extraProfileValue;
	}

	/**
	 * @param extraProfileValue
	 *            String
	 */
	public void setExtraProfileValue(String extraProfileValue) {
		this.extraProfileValue = extraProfileValue;
	}

}
