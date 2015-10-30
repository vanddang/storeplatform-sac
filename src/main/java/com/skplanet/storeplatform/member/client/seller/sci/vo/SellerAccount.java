/*
 * Copyright (c) 2013 SK planet.
 * All right reserved.
 * 
 * This software is the confidential and proprietary information of SK planet.
 * You shall not disclose such Confidential Information and
 * shall use it only in accordance with the terms of the license agreement
 * you entered into with SK planet.
 */
package com.skplanet.storeplatform.member.client.seller.sci.vo;

import java.io.Serializable;

import com.skplanet.storeplatform.framework.core.common.vo.CommonInfo;
import com.skplanet.storeplatform.member.client.common.util.Utils;

// TODO: Auto-generated Javadoc
/**
 * 판매자 정산정보 Value Object.
 * 
 * Updated on : 2013. 12. 20. Updated by : wisestone_mikepark
 */
public class SellerAccount extends CommonInfo implements Serializable {
	// TB_US_SELLERMBR_CHRG_SETT_ACCT 정산

	/** The Constant serial Version UID. */
	private static final long serialVersionUID = 1L;

	/** 판매자 키. */
	private String sellerKey; // INSD_SELLERMBR_NO

	/** Start date. */
	private String startDate; // USE_START_DT

	/** End date. */
	private String endDate; // USE_END_DT

	/** 계좌인증일시. ex (YYYYMMDDHH24MISS) ("20130122162853") 2013-01-22 16:28:53. */
	private String accountRealDate; // ACCT_AUTH_DT 계좌인증일시

	/** 계좌번호. */
	private String bankAccount; // ACCT_NO 계좌번호

	/** 예금자명. */
	private String bankAcctName; // DEPSTR_NM 예금자명

	/** 은행코드. */
	private String bankCode; // BANK_CD 은행코드

	/** 은행BSB . */
	private String bankBsb; //

	/** 사유. */
	private String reason; // REASON 사유 -> 알아서 넣어주면된다. (변경 'change', 최초 'new' 등록)으로 등록

	/** 사용여부 (Y/N). */
	private String isUsed; // USE_YN 사용여부 ->알아서 넣어주면된다.

	/** 은행명. */
	private String bankName; // FR_BANK_NM 은행명

	/** 은행지점코드. */
	private String bankBranchCode; // FR_BRCH_CD 은행지점코드

	/** 은행지점명. */
	private String bankBranch; // FR_BRCH_NM 은행지점명

	/** 은행주소. */
	private String bankAddress; // FR_BANK_ADDR 외국은행주소

	/** 은행 위치. */
	private String bankLocation; // FR_BANK_LOC 외국은행 위치

	/** Swift 코드. */
	private String swiftCode; // INTL_SWIFT_CD Swift 코드

	/** IBAN 코드. */
	private String ibanCode; // INTL_IBAN IBAN 코드

	/** ABA 코드. */
	private String abaCode; // INTL_ABA ABA 코드

	/** TPIN 코드. */
	private String tpinCode; // FR_TPIN_NO => FR_TIN_NO 로 바뀌었음

	/** 과세여부. */
	private String isTaxable; // TXN_YN 간이 과세 여부 ##### 전환 쪽에서 사용

	/** 전환 유형코드. */
	private String changedCd; // // CHANGED_CASE_CD 전환 유형코드 (US001507,US001503,US001501) >> api 에 추가한다.

	/**
	 * 전환 유형코드를 리턴한다.
	 * 
	 * @return changedCd - 전환 유형코드
	 */
	public String getChangedCd() {
		return this.changedCd;
	}

	/**
	 * 전환 유형코드를 설정한다.
	 * 
	 * @param changedCd
	 *            전환 유형코드
	 */
	public void setChangedCd(String changedCd) {
		this.changedCd = changedCd;
	}

	/**
	 * 간이 과세여부를 리턴한다.
	 * 
	 * @return isTaxable - 간이 과세여부
	 */
	public String getIsTaxable() {
		return this.isTaxable;
	}

	/**
	 * 간이 과세여부를 설정한다.
	 * 
	 * @param isTaxable
	 *            간이 과세여부
	 */
	public void setIsTaxable(String isTaxable) {
		this.isTaxable = isTaxable;
	}

	/**
	 * 은행BSB 리턴한다.
	 * 
	 * @return bankBsb - 은행명Bsb
	 */
	public String getBankBsb() {
		return this.bankBsb;
	}

	/**
	 * 은행Bsb 설정한다.
	 * 
	 * @param bankBsb
	 *            Bsb
	 */
	public void setBankBsb(String bankBsb) {
		this.bankBsb = bankBsb;
	}

	/**
	 * 판매자 키를 리턴한다.
	 * 
	 * @return sellerKey - 판매자 키
	 */
	public String getSellerKey() {
		return this.sellerKey;
	}

	/**
	 * 판매자 키를 설정한다.
	 * 
	 * @param sellerKey
	 *            판매자 키
	 */
	public void setSellerKey(String sellerKey) {
		this.sellerKey = sellerKey;
	}

	/**
	 * Start date를 리턴한다.
	 * 
	 * @return startDate - Start date
	 */
	public String getStartDate() {
		return this.startDate;
	}

	/**
	 * Start date를 설정한다.
	 * 
	 * @param startDate
	 *            Start date
	 */
	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}

	/**
	 * End date를 리턴한다.
	 * 
	 * @return endDate - End date
	 */
	public String getEndDate() {
		return this.endDate;
	}

	/**
	 * End date를 설정한다.
	 * 
	 * @param endDate
	 *            End date
	 */
	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}

	/**
	 * 계좌인증일시를 리턴한다.
	 * 
	 * @return accountRealDate - 계좌인증일시
	 */
	public String getAccountRealDate() {
		return this.accountRealDate;
	}

	/**
	 * 계좌인증일시를 설정한다.
	 * 
	 * @param accountRealDate
	 *            계좌인증일시
	 */
	public void setAccountRealDate(String accountRealDate) {
		this.accountRealDate = accountRealDate;
	}

	/**
	 * 계좌번호를 리턴한다.
	 * 
	 * @return bankAccount - 계좌번호
	 */
	public String getBankAccount() {
		return this.bankAccount;
	}

	/**
	 * 계좌번호를 설정한다.
	 * 
	 * @param bankAccount
	 *            계좌번호
	 */
	public void setBankAccount(String bankAccount) {
		this.bankAccount = bankAccount;
	}

	/**
	 * 예금자명을 리턴한다.
	 * 
	 * @return bankAcctName - 예금자명
	 */
	public String getBankAcctName() {
		return this.bankAcctName;
	}

	/**
	 * 예금자명을 설정한다.
	 * 
	 * @param bankAcctName
	 *            예금자명
	 */
	public void setBankAcctName(String bankAcctName) {
		this.bankAcctName = bankAcctName;
	}

	/**
	 * 은행코드를 리턴한다.
	 * 
	 * @return bankCode - 은행코드
	 */
	public String getBankCode() {
		return this.bankCode;
	}

	/**
	 * 은행코드를 설정한다.
	 * 
	 * @param bankCode
	 *            은행코드
	 */
	public void setBankCode(String bankCode) {
		this.bankCode = bankCode;
	}

	/**
	 * 사유를 리턴한다.
	 * 
	 * @return reason - 사유
	 */
	public String getReason() {
		return this.reason;
	}

	/**
	 * 사유를 설정한다.
	 * 
	 * @param reason
	 *            사유
	 */
	public void setReason(String reason) {
		this.reason = reason;
	}

	/**
	 * 사용여부 (Y/N)를 리턴한다.
	 * 
	 * @return isUsed - 사용여부 (Y/N)
	 */
	public String getIsUsed() {
		return this.isUsed;
	}

	/**
	 * 사용여부 (Y/N)를 설정한다.
	 * 
	 * @param isUsed
	 *            사용여부 (Y/N)
	 */
	public void setIsUsed(String isUsed) {
		this.isUsed = isUsed;
	}

	/**
	 * 은행명을 리턴한다.
	 * 
	 * @return bankName - 은행명
	 */
	public String getBankName() {
		return this.bankName;
	}

	/**
	 * 은행명을 설정한다.
	 * 
	 * @param bankName
	 *            은행명
	 */
	public void setBankName(String bankName) {
		this.bankName = bankName;
	}

	/**
	 * 은행지점코드를 리턴한다.
	 * 
	 * @return bankBranchCode - 은행지점코드
	 */
	public String getBankBranchCode() {
		return this.bankBranchCode;
	}

	/**
	 * 은행지점코드를 설정한다.
	 * 
	 * @param bankBranchCode
	 *            은행지점코드
	 */
	public void setBankBranchCode(String bankBranchCode) {
		this.bankBranchCode = bankBranchCode;
	}

	/**
	 * 은행지점명을 리턴한다.
	 * 
	 * @return bankBranch - 은행지점명
	 */
	public String getBankBranch() {
		return this.bankBranch;
	}

	/**
	 * 은행지점명을 설정한다.
	 * 
	 * @param bankBranch
	 *            은행지점명
	 */
	public void setBankBranch(String bankBranch) {
		this.bankBranch = bankBranch;
	}

	/**
	 * 은행주소를 리턴한다.
	 * 
	 * @return bankAddress - 은행주소
	 */
	public String getBankAddress() {
		return this.bankAddress;
	}

	/**
	 * 은행주소를 설정한다.
	 * 
	 * @param bankAddress
	 *            은행주소
	 */
	public void setBankAddress(String bankAddress) {
		this.bankAddress = bankAddress;
	}

	/**
	 * 은행 위치를 리턴한다.
	 * 
	 * @return bankLocation - 은행 위치
	 */
	public String getBankLocation() {
		return this.bankLocation;
	}

	/**
	 * 은행 위치를 설정한다.
	 * 
	 * @param bankLocation
	 *            은행 위치
	 */
	public void setBankLocation(String bankLocation) {
		this.bankLocation = bankLocation;
	}

	/**
	 * Swift 코드를 리턴한다.
	 * 
	 * @return swiftCode - Swift 코드
	 */
	public String getSwiftCode() {
		return this.swiftCode;
	}

	/**
	 * Swift 코드를 설정한다.
	 * 
	 * @param swiftCode
	 *            Swift 코드
	 */
	public void setSwiftCode(String swiftCode) {
		this.swiftCode = swiftCode;
	}

	/**
	 * IBAN 코드를 리턴한다.
	 * 
	 * @return ibanCode - IBAN 코드
	 */
	public String getIbanCode() {
		return this.ibanCode;
	}

	/**
	 * IBAN 코드를 설정한다.
	 * 
	 * @param ibanCode
	 *            IBAN 코드
	 */
	public void setIbanCode(String ibanCode) {
		this.ibanCode = ibanCode;
	}

	/**
	 * ABA 코드를 리턴한다.
	 * 
	 * @return abaCode - ABA 코드
	 */
	public String getAbaCode() {
		return this.abaCode;
	}

	/**
	 * ABA 코드를 설정한다.
	 * 
	 * @param abaCode
	 *            ABA 코드
	 */
	public void setAbaCode(String abaCode) {
		this.abaCode = abaCode;
	}

	/**
	 * TPIN 코드를 리턴한다.
	 * 
	 * @return tpinCode - TPIN 코드
	 */
	public String getTpinCode() {
		return this.tpinCode;
	}

	/**
	 * TPIN 코드를 설정한다.
	 * 
	 * @param tpinCode
	 *            TPIN 코드
	 */
	public void setTpinCode(String tpinCode) {
		this.tpinCode = tpinCode;
	}

	/**
	 * Returns the serial version UID.
	 * 
	 * @return serialVersionUID - the serial version UID
	 */
	public static long getSerialversionuid() {
		return serialVersionUID;
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
