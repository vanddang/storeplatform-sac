package com.skplanet.storeplatform.sac.client.internal.member.user.vo;

import java.util.Map;

import com.skplanet.storeplatform.framework.core.common.vo.CommonInfo;

public class SearchUserExtraInfoSacRes extends CommonInfo {

	private static final long serialVersionUID = 1L;

	private Map searchUserExtraInfoSacRes;

	/**
	 * @return the searchUserExtraInfoSacRes
	 */
	public Map getSearchUserExtraInfoSacRes() {
		return this.searchUserExtraInfoSacRes;
	}

	/**
	 * @param searchUserExtraInfoSacRes
	 *            the searchUserExtraInfoSacRes to set
	 */
	public void setSearchUserExtraInfoSacRes(Map searchUserExtraInfoSacRes) {
		this.searchUserExtraInfoSacRes = searchUserExtraInfoSacRes;
	}

}
