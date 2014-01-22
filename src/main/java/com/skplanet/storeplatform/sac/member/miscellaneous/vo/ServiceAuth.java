package com.skplanet.storeplatform.sac.member.miscellaneous.vo;

import com.skplanet.storeplatform.framework.core.common.vo.CommonInfo;

/**
 * TB_CM_SVC_AUTH 테이블 조회 Value Object.
 * 
 * Updated on : 2014. 1. 17. Updated by : 김다슬, 인크로스.
 */
public class ServiceAuth extends CommonInfo {

	private static final long serialVersionUID = 1L;

	/**
	 * 시퀀스.
	 */
	private String authSeq;

	/**
	 * 회원 번호.
	 */
	private String mbrNo;

	/**
	 * 테넌트 아이디.
	 */
	private String tenantId;

	/**
	 * 인증 타입 코드 SMS(CM010901), Email(CM010902).
	 */
	private String authTypeCd;

	/**
	 * 인증 코드 Signature.
	 */
	private String authSign;

	/**
	 * 인증 코드.
	 */
	private String authValue;

	/**
	 * 인증 코드 생성 일자.
	 */
	private String authValueCreateDt;

	/**
	 * 인증 이메일 주소.
	 */
	private String authEmail;

	/**
	 * 인증 여부.
	 */
	private String authComptYn;

	/**
	 * 등록자.
	 */
	private String regId;

	/**
	 * 등록 일자.
	 */
	private String regDt;

	/**
	 * 업데이트자.
	 */
	private String updId;

	/**
	 * 업데이트 일자.
	 */
	private String updDt;

	/**
	 * 현재 시간.
	 */
	private String currDt;

	/**
	 * @return the authSeq
	 */
	public String getAuthSeq() {
		return this.authSeq;
	}

	/**
	 * @param authSeq
	 *            the authSeq to set
	 */
	public void setAuthSeq(String authSeq) {
		this.authSeq = authSeq;
	}

	/**
	 * @return the mbrNo
	 */
	public String getMbrNo() {
		return this.mbrNo;
	}

	/**
	 * @param mbrNo
	 *            the mbrNo to set
	 */
	public void setMbrNo(String mbrNo) {
		this.mbrNo = mbrNo;
	}

	/**
	 * @return the tenantId
	 */
	public String getTenantId() {
		return this.tenantId;
	}

	/**
	 * @param tenantId
	 *            the tenantId to set
	 */
	public void setTenantId(String tenantId) {
		this.tenantId = tenantId;
	}

	/**
	 * @return the authTypeCd
	 */
	public String getAuthTypeCd() {
		return this.authTypeCd;
	}

	/**
	 * @param authTypeCd
	 *            the authTypeCd to set
	 */
	public void setAuthTypeCd(String authTypeCd) {
		this.authTypeCd = authTypeCd;
	}

	/**
	 * @return the authSign
	 */
	public String getAuthSign() {
		return this.authSign;
	}

	/**
	 * @param authSign
	 *            the authSign to set
	 */
	public void setAuthSign(String authSign) {
		this.authSign = authSign;
	}

	/**
	 * @return the authValue
	 */
	public String getAuthValue() {
		return this.authValue;
	}

	/**
	 * @param authValue
	 *            the authValue to set
	 */
	public void setAuthValue(String authValue) {
		this.authValue = authValue;
	}

	/**
	 * @return the authValueCreateDt
	 */
	public String getAuthValueCreateDt() {
		return this.authValueCreateDt;
	}

	/**
	 * @param authValueCreateDt
	 *            the authValueCreateDt to set
	 */
	public void setAuthValueCreateDt(String authValueCreateDt) {
		this.authValueCreateDt = authValueCreateDt;
	}

	/**
	 * @return the authEmail
	 */
	public String getAuthEmail() {
		return this.authEmail;
	}

	/**
	 * @param authEmail
	 *            the authEmail to set
	 */
	public void setAuthEmail(String authEmail) {
		this.authEmail = authEmail;
	}

	/**
	 * @return the authComptYn
	 */
	public String getAuthComptYn() {
		return this.authComptYn;
	}

	/**
	 * @param authComptYn
	 *            the authComptYn to set
	 */
	public void setAuthComptYn(String authComptYn) {
		this.authComptYn = authComptYn;
	}

	/**
	 * @return the regId
	 */
	public String getRegId() {
		return this.regId;
	}

	/**
	 * @param regId
	 *            the regId to set
	 */
	public void setRegId(String regId) {
		this.regId = regId;
	}

	/**
	 * @return the regDt
	 */
	public String getRegDt() {
		return this.regDt;
	}

	/**
	 * @param regDt
	 *            the regDt to set
	 */
	public void setRegDt(String regDt) {
		this.regDt = regDt;
	}

	/**
	 * @return the updId
	 */
	public String getUpdId() {
		return this.updId;
	}

	/**
	 * @param updId
	 *            the updId to set
	 */
	public void setUpdId(String updId) {
		this.updId = updId;
	}

	/**
	 * @return the updDt
	 */
	public String getUpdDt() {
		return this.updDt;
	}

	/**
	 * @param updDt
	 *            the updDt to set
	 */
	public void setUpdDt(String updDt) {
		this.updDt = updDt;
	}

	/**
	 * @return the currDt
	 */
	public String getCurrDt() {
		return this.currDt;
	}

	/**
	 * @param currDt
	 *            the currDt to set
	 */
	public void setCurrDt(String currDt) {
		this.currDt = currDt;
	}

}
