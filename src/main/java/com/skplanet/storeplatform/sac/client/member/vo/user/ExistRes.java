package com.skplanet.storeplatform.sac.client.member.vo.user;

import org.codehaus.jackson.map.annotate.JsonSerialize;
import org.codehaus.jackson.map.annotate.JsonSerialize.Inclusion;

import com.skplanet.storeplatform.framework.core.common.vo.CommonInfo;

/**
 * [RESPONSE] 회원가입 여부 조회 (ID/MDN 기반)
 * 
 * Updated on : 2014. 1. 6. Updated by : 강신완, 부르칸.
 */
@JsonSerialize(include = Inclusion.NON_NULL)
public class ExistRes extends CommonInfo {

	private static final long serialVersionUID = 1L;

	/* 사용자 Key, Value : IW102158844420091030165015 */
	private String userKey;
	/* 회원여부, Value : Y/N */
	private String tstoreYn;
	/* 사용자 구분 코드, Value : US011501(기기사용자) US011502(IDP사용자) US011503(OneId사용자) null(Tstore 회원 아님) */
	private String userType;
	/* 회원 아이디, Value : 모바일 회원이거나 Tstore, 회원이 아닐경우 null */
	private String userId;
	/* 실명인증 여부 Value : Y/N, Tstore회원이 아닐 경우 null */
	private String isRealName;
	/* 14세 미만 여부 Value : Y/N, Tstore회원이 아닐 경우 null */
	private String under14;
	/* 14세 미만 법정대리인 동의 여부 Value : Y/N, Tstore회원이 아닐 경우 null */
	private String agencyYn;
	/* 상품 19금 여부 Value : Y/N, Tstore회원이 아닐 경우 null */
	private String prodAdultYn;
	/* 19세 미만 여부 Value : Y/N, Tstore회원이 아닐 경우 null */
	private String under19;
	/* 회원 이메일 정보 Value : 없을 경우 null */
	private String userEmail;

	public String getUserKey() {
		return this.userKey;
	}

	public void setUserKey(String userKey) {
		this.userKey = userKey;
	}

	public String getTstoreYn() {
		return this.tstoreYn;
	}

	public void setTstoreYn(String tstoreYn) {
		this.tstoreYn = tstoreYn;
	}

	public String getUserType() {
		return this.userType;
	}

	public void setUserType(String userType) {
		this.userType = userType;
	}

	public String getUserId() {
		return this.userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getIsRealName() {
		return this.isRealName;
	}

	public void setIsRealName(String isRealName) {
		this.isRealName = isRealName;
	}

	public String getUnder14() {
		return this.under14;
	}

	public void setUnder14(String under14) {
		this.under14 = under14;
	}

	public String getAgencyYn() {
		return this.agencyYn;
	}

	public void setAgencyYn(String agencyYn) {
		this.agencyYn = agencyYn;
	}

	public String getProdAdultYn() {
		return this.prodAdultYn;
	}

	public void setProdAdultYn(String prodAdultYn) {
		this.prodAdultYn = prodAdultYn;
	}

	public String getUnder19() {
		return this.under19;
	}

	public void setUnder19(String under19) {
		this.under19 = under19;
	}

	public String getUserEmail() {
		return this.userEmail;
	}

	public void setUserEmail(String userEmail) {
		this.userEmail = userEmail;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

}
