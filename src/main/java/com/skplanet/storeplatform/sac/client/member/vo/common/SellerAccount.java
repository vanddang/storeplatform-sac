package com.skplanet.storeplatform.sac.client.member.vo.common;

import org.codehaus.jackson.map.annotate.JsonSerialize;
import org.codehaus.jackson.map.annotate.JsonSerialize.Inclusion;

import com.skplanet.storeplatform.framework.core.common.vo.CommonInfo;

/**
 * 판매자회원 정산 정보 조회 판매자 판매자 정산정보
 * 
 * Updated on : 2014. 1. 7. Updated by : 한서구, 부르칸.
 */
@JsonSerialize(include = Inclusion.NON_NULL)
public class SellerAccount extends CommonInfo {

	private static final long serialVersionUID = 1L;

	private String abaCode;
	private String accountRealDate;
	private String bankAccount;
	private String bankAcctName;
	private String bankAddress;
	private String bankBranch;
	private String bankBranchCode;
	private String bankCode;
	private String bankLocation;
	private String bankName;
	private String endDate;
	private String ibanCode;
	private String isUsed;
	private String reason;
	private String sellerKey;
	private String startDate;
	private String swiftCode;
	private String tpinCode;
	private String sellerBizZip;
	private String sellerBizAddress;
	private String sellerBizDetailAddress;

	public String getSellerBizZip() {
		return this.sellerBizZip;
	}

	public void setSellerBizZip(String sellerBizZip) {
		this.sellerBizZip = sellerBizZip;
	}

	public String getSellerBizAddress() {
		return this.sellerBizAddress;
	}

	public void setSellerBizAddress(String sellerBizAddress) {
		this.sellerBizAddress = sellerBizAddress;
	}

	public String getSellerBizDetailAddress() {
		return this.sellerBizDetailAddress;
	}

	public void setSellerBizDetailAddress(String sellerBizDetailAddress) {
		this.sellerBizDetailAddress = sellerBizDetailAddress;
	}

	public String getAbaCode() {
		return this.abaCode;
	}

	public void setAbaCode(String abaCode) {
		this.abaCode = abaCode;
	}

	public String getAccountRealDate() {
		return this.accountRealDate;
	}

	public void setAccountRealDate(String accountRealDate) {
		this.accountRealDate = accountRealDate;
	}

	public String getBankAccount() {
		return this.bankAccount;
	}

	public void setBankAccount(String bankAccount) {
		this.bankAccount = bankAccount;
	}

	public String getBankAcctName() {
		return this.bankAcctName;
	}

	public void setBankAcctName(String bankAcctName) {
		this.bankAcctName = bankAcctName;
	}

	public String getBankAddress() {
		return this.bankAddress;
	}

	public void setBankAddress(String bankAddress) {
		this.bankAddress = bankAddress;
	}

	public String getBankBranch() {
		return this.bankBranch;
	}

	public void setBankBranch(String bankBranch) {
		this.bankBranch = bankBranch;
	}

	public String getBankBranchCode() {
		return this.bankBranchCode;
	}

	public void setBankBranchCode(String bankBranchCode) {
		this.bankBranchCode = bankBranchCode;
	}

	public String getBankCode() {
		return this.bankCode;
	}

	public void setBankCode(String bankCode) {
		this.bankCode = bankCode;
	}

	public String getBankLocation() {
		return this.bankLocation;
	}

	public void setBankLocation(String bankLocation) {
		this.bankLocation = bankLocation;
	}

	public String getBankName() {
		return this.bankName;
	}

	public void setBankName(String bankName) {
		this.bankName = bankName;
	}

	public String getEndDate() {
		return this.endDate;
	}

	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}

	public String getIbanCode() {
		return this.ibanCode;
	}

	public void setIbanCode(String ibanCode) {
		this.ibanCode = ibanCode;
	}

	public String getIsUsed() {
		return this.isUsed;
	}

	public void setIsUsed(String isUsed) {
		this.isUsed = isUsed;
	}

	public String getReason() {
		return this.reason;
	}

	public void setReason(String reason) {
		this.reason = reason;
	}

	public String getSellerKey() {
		return this.sellerKey;
	}

	public void setSellerKey(String sellerKey) {
		this.sellerKey = sellerKey;
	}

	public String getStartDate() {
		return this.startDate;
	}

	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}

	public String getSwiftCode() {
		return this.swiftCode;
	}

	public void setSwiftCode(String swiftCode) {
		this.swiftCode = swiftCode;
	}

	public String getTpinCode() {
		return this.tpinCode;
	}

	public void setTpinCode(String tpinCode) {
		this.tpinCode = tpinCode;
	}

}
