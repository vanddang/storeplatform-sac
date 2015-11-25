package com.skplanet.storeplatform.sac.client.member.vo.common;

import java.io.Serializable;

import com.skplanet.storeplatform.framework.core.common.vo.CommonInfo;

/**
 * 회원등급 정보.
 * 
 * Updated on : 2014. 7. 10. Updated by : Rejoice, Burkhan
 */
public class GradeInfo extends CommonInfo implements Serializable {

	private static final long serialVersionUID = 1L;

	/** 회원 등급. */
	private String userGradeCd;

	/**
	 * @return the userGradeCd
	 */
	public String getUserGradeCd() {
		return this.userGradeCd;
	}

	/**
	 * @param userGradeCd
	 *            the userGradeCd to set
	 */
	public void setUserGradeCd(String userGradeCd) {
		this.userGradeCd = userGradeCd;
	}

}
