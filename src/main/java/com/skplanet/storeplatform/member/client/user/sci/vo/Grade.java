package com.skplanet.storeplatform.member.client.user.sci.vo;

import java.io.Serializable;

import com.skplanet.storeplatform.framework.core.common.vo.CommonInfo;
import com.skplanet.storeplatform.member.client.common.util.Utils;

/**
 * 회원 등급.
 * 
 * Updated on : 2014. 7. 10. Updated by : Rejoice, Burkhan
 */
public class Grade extends CommonInfo implements Serializable {

	private static final long serialVersionUID = 1L;

	/** 회원 등급. */
	private String userGradeCd;
	/** 기준 연월. */
	private String baseYm;
	/** 마일리지일련번호. */
	private String mlgSeq;
	/** 회원번호. */
	private String mbrNo;
	/** 회원등급코드. */
	private String mbrLvlCd;
	/** 윈백여부. */
	private String winbackYn;
	/** 고객결제금액. */
	private String custPayAmt;
	/** 사은포인트금액. */
	private String repayPntAmt;
	/** 작업명. */
	private String operNm;
	/** 작업일시. */
	private String operDtm;

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

	/**
	 * @return the baseYm
	 */
	public String getBaseYm() {
		return this.baseYm;
	}

	/**
	 * @param baseYm
	 *            the baseYm to set
	 */
	public void setBaseYm(String baseYm) {
		this.baseYm = baseYm;
	}

	/**
	 * @return the mlgSeq
	 */
	public String getMlgSeq() {
		return this.mlgSeq;
	}

	/**
	 * @param mlgSeq
	 *            the mlgSeq to set
	 */
	public void setMlgSeq(String mlgSeq) {
		this.mlgSeq = mlgSeq;
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
	 * @return the mbrLvlCd
	 */
	public String getMbrLvlCd() {
		return this.mbrLvlCd;
	}

	/**
	 * @param mbrLvlCd
	 *            the mbrLvlCd to set
	 */
	public void setMbrLvlCd(String mbrLvlCd) {
		this.mbrLvlCd = mbrLvlCd;
	}

	/**
	 * @return the winbackYn
	 */
	public String getWinbackYn() {
		return this.winbackYn;
	}

	/**
	 * @param winbackYn
	 *            the winbackYn to set
	 */
	public void setWinbackYn(String winbackYn) {
		this.winbackYn = winbackYn;
	}

	/**
	 * @return the custPayAmt
	 */
	public String getCustPayAmt() {
		return this.custPayAmt;
	}

	/**
	 * @param custPayAmt
	 *            the custPayAmt to set
	 */
	public void setCustPayAmt(String custPayAmt) {
		this.custPayAmt = custPayAmt;
	}

	/**
	 * @return the repayPntAmt
	 */
	public String getRepayPntAmt() {
		return this.repayPntAmt;
	}

	/**
	 * @param repayPntAmt
	 *            the repayPntAmt to set
	 */
	public void setRepayPntAmt(String repayPntAmt) {
		this.repayPntAmt = repayPntAmt;
	}

	/**
	 * @return the operNm
	 */
	public String getOperNm() {
		return this.operNm;
	}

	/**
	 * @param operNm
	 *            the operNm to set
	 */
	public void setOperNm(String operNm) {
		this.operNm = operNm;
	}

	/**
	 * @return the operDtm
	 */
	public String getOperDtm() {
		return this.operDtm;
	}

	/**
	 * @param operDtm
	 *            the operDtm to set
	 */
	public void setOperDtm(String operDtm) {
		this.operDtm = operDtm;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return Utils.printKeyValues(this);
	}
}
