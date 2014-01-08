package com.skplanet.storeplatform.sac.client.member.vo.user;

import org.codehaus.jackson.map.annotate.JsonSerialize;
import org.codehaus.jackson.map.annotate.JsonSerialize.Inclusion;

import com.skplanet.storeplatform.framework.core.common.vo.CommonInfo;

/**
 * [RESPONSE] ID 기반 회원 인증 (One ID, IDP 회원)
 * 
 * Updated on : 2014. 1. 3. Updated by : 반범진. 지티소프트.
 */
@JsonSerialize(include = Inclusion.NON_NULL)
public class AuthorizeByIdRes extends CommonInfo {

	private static final long serialVersionUID = 1L;

	/**
	 * 사용자 고유 Key
	 */
	private String userKey;

	/**
	 * IDP 인증 Key
	 */
	private String userAuthKey;

	/**
	 * 회원상태
	 */
	private String userStatus;

	/**
	 * 가입사이트 코드(mbrStatus Temporary 일때만 유효)
	 */
	private String joinSiteCd;

	/**
	 * 가입사이트 코드 값(mbrStatus Temporary 일때만 유효)
	 */
	private String joinSiteNm;

	public String getUserKey() {
		return this.userKey;
	}

	public void setUserKey(String userKey) {
		this.userKey = userKey;
	}

	public String getUserAuthKey() {
		return this.userAuthKey;
	}

	public void setUserAuthKey(String userAuthKey) {
		this.userAuthKey = userAuthKey;
	}

	public String getUserStatus() {
		return this.userStatus;
	}

	public void setUserStatus(String userStatus) {
		this.userStatus = userStatus;
	}

	public String getJoinSiteCd() {
		return this.joinSiteCd;
	}

	public void setJoinSiteCd(String joinSiteCd) {
		this.joinSiteCd = joinSiteCd;
	}

	public String getJoinSiteNm() {
		return this.joinSiteNm;
	}

	public void setJoinSiteNm(String joinSiteNm) {
		this.joinSiteNm = joinSiteNm;
	}

}
