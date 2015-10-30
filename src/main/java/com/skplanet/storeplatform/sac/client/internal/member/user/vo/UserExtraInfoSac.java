package com.skplanet.storeplatform.sac.client.internal.member.user.vo;

import com.skplanet.storeplatform.framework.core.common.vo.CommonInfo;

public class UserExtraInfoSac extends CommonInfo {

	private static final long serialVersionUID = 1L;

	private String extraProfile;
	private String extraProfileValue;

	/**
	 * @return the extraProfile
	 */
	public String getExtraProfile() {
		return this.extraProfile;
	}

	/**
	 * @param extraProfile
	 *            the extraProfile to set
	 */
	public void setExtraProfile(String extraProfile) {
		this.extraProfile = extraProfile;
	}

	/**
	 * @return the extraProfileValue
	 */
	public String getExtraProfileValue() {
		return this.extraProfileValue;
	}

	/**
	 * @param extraProfileValue
	 *            the extraProfileValue to set
	 */
	public void setExtraProfileValue(String extraProfileValue) {
		this.extraProfileValue = extraProfileValue;
	}

}
