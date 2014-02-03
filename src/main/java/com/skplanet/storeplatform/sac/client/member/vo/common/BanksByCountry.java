package com.skplanet.storeplatform.sac.client.member.vo.common;

import org.codehaus.jackson.map.annotate.JsonSerialize;
import org.codehaus.jackson.map.annotate.JsonSerialize.Inclusion;

import com.skplanet.storeplatform.framework.core.common.vo.CommonInfo;

/**
 * 판매자회원 나라별 은행 정보
 * 
 * Updated on : 2014. 2. 3. Updated by : 한서구, 부르칸.
 */
@JsonSerialize(include = Inclusion.NON_NULL)
public class BanksByCountry extends CommonInfo {

	private static final long serialVersionUID = 1L;

	private String nationCode;
	private String nateionName;
	private String alphaCode;
	private String namericCode;
	private String bankNo;
	private String nationTelNo;
	private String taxtrtCnclsYn;
	private String bizIncmTxnYn;
	private String rmtPosbYn;
	private String ibanUseYn;
	private String swiftUseYn;
	private String abaUseYn;
	private String sortUseYn;
	private String bsbUseYn;
	private String UseYn;
	private String nationDesc;
	private String regId;
	private String regDt;
	private String updId;
	private String updDt;

	public String getNationCode() {
		return this.nationCode;
	}

	public void setNationCode(String nationCode) {
		this.nationCode = nationCode;
	}

	public String getNateionName() {
		return this.nateionName;
	}

	public void setNateionName(String nateionName) {
		this.nateionName = nateionName;
	}

	public String getAlphaCode() {
		return this.alphaCode;
	}

	public void setAlphaCode(String alphaCode) {
		this.alphaCode = alphaCode;
	}

	public String getNamericCode() {
		return this.namericCode;
	}

	public void setNamericCode(String namericCode) {
		this.namericCode = namericCode;
	}

	public String getBankNo() {
		return this.bankNo;
	}

	public void setBankNo(String bankNo) {
		this.bankNo = bankNo;
	}

	public String getNationTelNo() {
		return this.nationTelNo;
	}

	public void setNationTelNo(String nationTelNo) {
		this.nationTelNo = nationTelNo;
	}

	public String getTaxtrtCnclsYn() {
		return this.taxtrtCnclsYn;
	}

	public void setTaxtrtCnclsYn(String taxtrtCnclsYn) {
		this.taxtrtCnclsYn = taxtrtCnclsYn;
	}

	public String getBizIncmTxnYn() {
		return this.bizIncmTxnYn;
	}

	public void setBizIncmTxnYn(String bizIncmTxnYn) {
		this.bizIncmTxnYn = bizIncmTxnYn;
	}

	public String getRmtPosbYn() {
		return this.rmtPosbYn;
	}

	public void setRmtPosbYn(String rmtPosbYn) {
		this.rmtPosbYn = rmtPosbYn;
	}

	public String getIbanUseYn() {
		return this.ibanUseYn;
	}

	public void setIbanUseYn(String ibanUseYn) {
		this.ibanUseYn = ibanUseYn;
	}

	public String getSwiftUseYn() {
		return this.swiftUseYn;
	}

	public void setSwiftUseYn(String swiftUseYn) {
		this.swiftUseYn = swiftUseYn;
	}

	public String getAbaUseYn() {
		return this.abaUseYn;
	}

	public void setAbaUseYn(String abaUseYn) {
		this.abaUseYn = abaUseYn;
	}

	public String getSortUseYn() {
		return this.sortUseYn;
	}

	public void setSortUseYn(String sortUseYn) {
		this.sortUseYn = sortUseYn;
	}

	public String getBsbUseYn() {
		return this.bsbUseYn;
	}

	public void setBsbUseYn(String bsbUseYn) {
		this.bsbUseYn = bsbUseYn;
	}

	public String getUseYn() {
		return this.UseYn;
	}

	public void setUseYn(String useYn) {
		this.UseYn = useYn;
	}

	public String getNationDesc() {
		return this.nationDesc;
	}

	public void setNationDesc(String nationDesc) {
		this.nationDesc = nationDesc;
	}

	public String getRegId() {
		return this.regId;
	}

	public void setRegId(String regId) {
		this.regId = regId;
	}

	public String getRegDt() {
		return this.regDt;
	}

	public void setRegDt(String regDt) {
		this.regDt = regDt;
	}

	public String getUpdId() {
		return this.updId;
	}

	public void setUpdId(String updId) {
		this.updId = updId;
	}

	public String getUpdDt() {
		return this.updDt;
	}

	public void setUpdDt(String updDt) {
		this.updDt = updDt;
	}

}
