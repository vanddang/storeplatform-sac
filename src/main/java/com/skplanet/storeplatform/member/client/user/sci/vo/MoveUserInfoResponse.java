package com.skplanet.storeplatform.member.client.user.sci.vo;

import java.io.Serializable;

import com.skplanet.storeplatform.framework.core.common.vo.CommonInfo;
import com.skplanet.storeplatform.member.client.common.vo.CommonResponse;

/**
 * 사용자 가입 요청 Value Object Updated on : 2015. 05. 27. Updated by : incross_jackwylde
 */
public class MoveUserInfoResponse extends CommonInfo implements Serializable {

	/** The Constant serial Version UID. */
	private static final long serialVersionUID = 1L;

	/** 공통 응답 Value Object. */
	private CommonResponse commonResponse;

	/** tenantID. */
	private String tenantID;

	/** 회원 키. */
	private String userKey;

	/** 전환 코드. */
	private String transCd;

	/** 통합 서비스 번호. */
	private String intgSvcNo;

	/** 사용자 회원 번호. */
	private String userMbrNo;

	/** IDP 연동 결과. */
	private String idpResultYn;

	/** IDP 에러 코드. */
	private String idpErrCd;

	/** 회원 ID. */
	private String mbrId;

	/** 회원 분류 코드. */
	private String mbrClasCd;

	/** 이메일. */
	private String emailAddr;

	/** User Device. */
	private UserMbrDevice userMbrDevice;

	/**
	 * @return the commonResponse
	 */
	public CommonResponse getCommonResponse() {
		return this.commonResponse;
	}

	/**
	 * @return the tenantID
	 */
	public String getTenantID() {
		return this.tenantID;
	}

	/**
	 * @param tenantID
	 *            the tenantID to set
	 */
	public void setTenantID(String tenantID) {
		this.tenantID = tenantID;
	}

	/**
	 * @param commonResponse
	 *            the commonResponse to set
	 */
	public void setCommonResponse(CommonResponse commonResponse) {
		this.commonResponse = commonResponse;
	}

	/**
	 * @return the userKey
	 */
	public String getUserKey() {
		return this.userKey;
	}

	/**
	 * @param userKey
	 *            the userKey to set
	 */
	public void setUserKey(String userKey) {
		this.userKey = userKey;
	}

	/**
	 * @return the transCd
	 */
	public String getTransCd() {
		return this.transCd;
	}

	/**
	 * @param transCd
	 *            the transCd to set
	 */
	public void setTransCd(String transCd) {
		this.transCd = transCd;
	}

	/**
	 * @return the intgSvcNo
	 */
	public String getIntgSvcNo() {
		return this.intgSvcNo;
	}

	/**
	 * @param intgSvcNo
	 *            the intgSvcNo to set
	 */
	public void setIntgSvcNo(String intgSvcNo) {
		this.intgSvcNo = intgSvcNo;
	}

	/**
	 * @return the userMbrNo
	 */
	public String getUserMbrNo() {
		return this.userMbrNo;
	}

	/**
	 * @param userMbrNo
	 *            the userMbrNo to set
	 */
	public void setUserMbrNo(String userMbrNo) {
		this.userMbrNo = userMbrNo;
	}

	/**
	 * @return the idpResultYn
	 */
	public String getIdpResultYn() {
		return this.idpResultYn;
	}

	/**
	 * @param idpResultYn
	 *            the idpResultYn to set
	 */
	public void setIdpResultYn(String idpResultYn) {
		this.idpResultYn = idpResultYn;
	}

	/**
	 * @return the idpErrCd
	 */
	public String getIdpErrCd() {
		return this.idpErrCd;
	}

	/**
	 * @param idpErrCd
	 *            the idpErrCd to set
	 */
	public void setIdpErrCd(String idpErrCd) {
		this.idpErrCd = idpErrCd;
	}

	/**
	 * @return the mbrId
	 */
	public String getMbrId() {
		return this.mbrId;
	}

	/**
	 * @param mbrId
	 *            the mbrId to set
	 */
	public void setMbrId(String mbrId) {
		this.mbrId = mbrId;
	}

	/**
	 * @return the mbrClasCd
	 */
	public String getMbrClasCd() {
		return this.mbrClasCd;
	}

	/**
	 * @return the emailAddr
	 */
	public String getEmailAddr() {
		return this.emailAddr;
	}

	/**
	 * @param emailAddr
	 *            the emailAddr to set
	 */
	public void setEmailAddr(String emailAddr) {
		this.emailAddr = emailAddr;
	}

	/**
	 * @param mbrClasCd
	 *            the mbrClasCd to set
	 */
	public void setMbrClasCd(String mbrClasCd) {
		this.mbrClasCd = mbrClasCd;
	}

	/**
	 * @return the userMbrDevice
	 */
	public UserMbrDevice getUserMbrDevice() {
		return this.userMbrDevice;
	}

	/**
	 * @param userMbrDevice
	 *            the userMbrDevice to set
	 */
	public void setUserMbrDevice(UserMbrDevice userMbrDevice) {
		this.userMbrDevice = userMbrDevice;
	}

}
