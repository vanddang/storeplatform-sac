package com.skplanet.storeplatform.member.client.user.sci.vo;

import java.io.Serializable;

import com.skplanet.storeplatform.framework.core.common.vo.CommonInfo;
import com.skplanet.storeplatform.member.client.common.vo.CommonRequest;

/**
 * 사용자 가입 요청 Value Object Updated on : 2015. 05. 27. Updated by : incross_jackwylde
 */
public class MoveUserInfoRequest extends CommonInfo implements Serializable {

	/** The Constant serial Version UID. */
	private static final long serialVersionUID = 1L;

	/** 공통 요청 Value Object. */
	private CommonRequest commonRequest;

	/**
	 * @return the commonRequest
	 */
	public CommonRequest getCommonRequest() {
		return this.commonRequest;
	}

	/**
	 * @param commonRequest
	 *            the commonRequest to set
	 */
	public void setCommonRequest(CommonRequest commonRequest) {
		this.commonRequest = commonRequest;
	}

	/** 회원 키 Value Object. */
	private String userKey;

	/** 회원 이동 타입 Value Object. */
	private String moveType;

	/** IDP 연동 결과. */
	private String idpResultYn;

	/** IDP 에러 코드. */
	private String idpErrCd;

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
	 * @return the moveType
	 */
	public String getMoveType() {
		return this.moveType;
	}

	/**
	 * @param moveType
	 *            the moveType to set
	 */
	public void setMoveType(String moveType) {
		this.moveType = moveType;
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

}
