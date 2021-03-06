package com.skplanet.storeplatform.sac.member.miscellaneous.vo;

import com.skplanet.storeplatform.framework.core.common.vo.CommonInfo;

/**
 * TB_CM_OSVC_AUTH 테이블 조회 Value Object.
 * 
 * Updated on : 2014. 1. 17. Updated by : 김다슬, 인크로스.
 * Updated on : 2016. 2. 15. Updated by : 임근대, SKP. - Onestore 테이블 변경 TB_CM_SVC_AUTH -> TB_CM_OSVC_AUTH
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
	 * MNO Code.
	 */
	private String authMnoCd;

	/**
	 * 인증 대상 MDN ( 휴대폰 인증 SMS ).
	 */
	private String authMdn;

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
	 * 인증 가능여부 판단하는 파라미터 = 인증코드 생성시각 - (현재 시각 - 인증가능시간).
	 */
	private String currDt;

	/**
	 * 인증 가능 시간.
	 */
	private String timeToLive;

	/**
	 * 인증 실패 횟수
	 */
	private int authCnt;

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

	public String getAuthMnoCd() {
		return authMnoCd;
	}

	public void setAuthMnoCd(String authMnoCd) {
		this.authMnoCd = authMnoCd;
	}

	/**
	 * @return the authMdn
	 */
	public String getAuthMdn() {
		return this.authMdn;
	}

	/**
	 * @param authMdn
	 *            the authMdn to set
	 */
	public void setAuthMdn(String authMdn) {
		this.authMdn = authMdn;
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

	/**
	 * @return the timeToLive
	 */
	public String getTimeToLive() {
		return this.timeToLive;
	}

	/**
	 * @param timeToLive
	 *            the timeToLive to set
	 */
	public void setTimeToLive(String timeToLive) {
		this.timeToLive = timeToLive;
	}

	/**
	 * @return the authCnt
	 */
	public int getAuthCnt() {
		return this.authCnt;
	}

	/**
	 * @param authCnt
	 *            the authCnt to set
	 */
	public void setAuthCnt(int authCnt) {
		this.authCnt = authCnt;
	}

}
