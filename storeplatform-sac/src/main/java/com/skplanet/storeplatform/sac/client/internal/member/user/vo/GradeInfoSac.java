package com.skplanet.storeplatform.sac.client.internal.member.user.vo;

import java.io.Serializable;

import org.codehaus.jackson.map.annotate.JsonSerialize;
import org.codehaus.jackson.map.annotate.JsonSerialize.Inclusion;

import com.skplanet.storeplatform.framework.core.common.vo.CommonInfo;

/**
 * 회원 등급 정보 Value Object.
 * 
 * Updated on : 2014. 7. 11. Updated by : Rejoice, Burkhan
 */
@JsonSerialize(include = Inclusion.NON_NULL)
public class GradeInfoSac extends CommonInfo implements Serializable {

	private static final long serialVersionUID = 1L;

	/** 회원 등급 - platinum : 플래티넘, gold : 골드, silver : 실버. */
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
