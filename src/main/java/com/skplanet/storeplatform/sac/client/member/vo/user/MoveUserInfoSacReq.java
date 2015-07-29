package com.skplanet.storeplatform.sac.client.member.vo.user;

import org.codehaus.jackson.map.annotate.JsonSerialize;
import org.codehaus.jackson.map.annotate.JsonSerialize.Inclusion;
import org.hibernate.validator.constraints.NotBlank;

import com.skplanet.storeplatform.framework.core.common.vo.CommonInfo;

@JsonSerialize(include = Inclusion.NON_NULL)
public class MoveUserInfoSacReq extends CommonInfo {

	private static final long serialVersionUID = 1L;

	/** 회원 Key. */
	@NotBlank
	private String userKey;

	/** 회원이동 Type. */
	@NotBlank
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
