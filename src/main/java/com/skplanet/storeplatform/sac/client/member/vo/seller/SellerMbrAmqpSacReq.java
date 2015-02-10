package com.skplanet.storeplatform.sac.client.member.vo.seller;

import java.util.List;

import com.skplanet.storeplatform.framework.core.common.vo.CommonInfo;

public class SellerMbrAmqpSacReq extends CommonInfo {
	private static final long serialVersionUID = 1L;

	private String workCd;

	private String workDttm;

	private String insdSellermbrNo;

	private String prsnCorpClsfCd;

	private String mbrClasCd;

	private String devTpCd;

	private String devMbrStatCd;

	private String mbrStatCd;

	private String entryDay;

	private String bolterDay;

	private String apprDay;

	private String sellermbrId;

	private String mbrNm;

	private String ctzRegNo;

	private String birth;

	private String sex;

	private String zip;

	private String addr;

	private String dtlAddr;

	private String email;

	private String emailRecvYn;

	private String realnmAuthYn;

	private String chrgpersNm;

	private String custCorsEmail;

	private String custCorsTelNo;

	private String chrgpersWilsTelNo;

	private String acctNo;

	private String bankCd;

	private String depstrNm;

	private String acctAuthYn;

	private String acctAuthDt;

	private String compNm;

	private String bizKindCd;

	private String bizRegNo;

	private String condNm;

	private String indtNm;

	private String corpRegNo;

	private String repTelNo;

	private String ceoNm;

	private String msalbizDeclYn;

	private String msalbizDeclNo;

	private String msalbizUndeclReasonCd;

	private String enprplZip;

	private String enprplAddr;

	private String enprplAddrDtl;

	private String easyTxnYn;

	private String taxBillPubYn;

	private String vendorCd;

	private String ictrySellermbrYn;

	private String nationCd;

	private String vatYn;

	private String frBankNm;

	private String frBrchNm;

	private String intlSwiftCd;

	private String frBrchCd;

	private String intlAba;

	private String frBankAddr;

	private String intlIban;

	private String regId;

	private String regDt;

	private String updId;

	private String updDt;

	private String pmtYn;

	private String pmtCnt;

	private List<Multimda> multimdaList;

	public class Multimda {
		private String multimdaCd;
		private String tenantSettRate;
		private String sellermbrSettRate;

		/**
		 * @return the multimdaCd
		 */
		public String getMultimdaCd() {
			return this.multimdaCd;
		}

		/**
		 * @param multimdaCd
		 *            the multimdaCd to set
		 */
		public void setMultimdaCd(String multimdaCd) {
			this.multimdaCd = multimdaCd;
		}

		/**
		 * @return the tenantSettRate
		 */
		public String getTenantSettRate() {
			return this.tenantSettRate;
		}

		/**
		 * @param tenantSettRate
		 *            the tenantSettRate to set
		 */
		public void setTenantSettRate(String tenantSettRate) {
			this.tenantSettRate = tenantSettRate;
		}

		/**
		 * @return the sellermbrSettRate
		 */
		public String getSellermbrSettRate() {
			return this.sellermbrSettRate;
		}

		/**
		 * @param sellermbrSettRate
		 *            the sellermbrSettRate to set
		 */
		public void setSellermbrSettRate(String sellermbrSettRate) {
			this.sellermbrSettRate = sellermbrSettRate;
		}
	}

	/**
	 * @return the workCd
	 */
	public String getWorkCd() {
		return this.workCd;
	}

	/**
	 * @param workCd
	 *            the workCd to set
	 */
	public void setWorkCd(String workCd) {
		this.workCd = workCd;
	}

	/**
	 * @return the workDttm
	 */
	public String getWorkDttm() {
		return this.workDttm;
	}

	/**
	 * @param workDttm
	 *            the workDttm to set
	 */
	public void setWorkDttm(String workDttm) {
		this.workDttm = workDttm;
	}

	/**
	 * @return the insdSellermbrNo
	 */
	public String getInsdSellermbrNo() {
		return this.insdSellermbrNo;
	}

	/**
	 * @param insdSellermbrNo
	 *            the insdSellermbrNo to set
	 */
	public void setInsdSellermbrNo(String insdSellermbrNo) {
		this.insdSellermbrNo = insdSellermbrNo;
	}

	/**
	 * @return the prsnCorpClsfCd
	 */
	public String getPrsnCorpClsfCd() {
		return this.prsnCorpClsfCd;
	}

	/**
	 * @param prsnCorpClsfCd
	 *            the prsnCorpClsfCd to set
	 */
	public void setPrsnCorpClsfCd(String prsnCorpClsfCd) {
		this.prsnCorpClsfCd = prsnCorpClsfCd;
	}

	/**
	 * @return the mbrClasCd
	 */
	public String getMbrClasCd() {
		return this.mbrClasCd;
	}

	/**
	 * @param mbrClasCd
	 *            the mbrClasCd to set
	 */
	public void setMbrClasCd(String mbrClasCd) {
		this.mbrClasCd = mbrClasCd;
	}

	/**
	 * @return the devTpCd
	 */
	public String getDevTpCd() {
		return this.devTpCd;
	}

	/**
	 * @param devTpCd
	 *            the devTpCd to set
	 */
	public void setDevTpCd(String devTpCd) {
		this.devTpCd = devTpCd;
	}

	/**
	 * @return the devMbrStatCd
	 */
	public String getDevMbrStatCd() {
		return this.devMbrStatCd;
	}

	/**
	 * @param devMbrStatCd
	 *            the devMbrStatCd to set
	 */
	public void setDevMbrStatCd(String devMbrStatCd) {
		this.devMbrStatCd = devMbrStatCd;
	}

	/**
	 * @return the mbrStatCd
	 */
	public String getMbrStatCd() {
		return this.mbrStatCd;
	}

	/**
	 * @param mbrStatCd
	 *            the mbrStatCd to set
	 */
	public void setMbrStatCd(String mbrStatCd) {
		this.mbrStatCd = mbrStatCd;
	}

	/**
	 * @return the entryDay
	 */
	public String getEntryDay() {
		return this.entryDay;
	}

	/**
	 * @param entryDay
	 *            the entryDay to set
	 */
	public void setEntryDay(String entryDay) {
		this.entryDay = entryDay;
	}

	/**
	 * @return the bolterDay
	 */
	public String getBolterDay() {
		return this.bolterDay;
	}

	/**
	 * @param bolterDay
	 *            the bolterDay to set
	 */
	public void setBolterDay(String bolterDay) {
		this.bolterDay = bolterDay;
	}

	/**
	 * @return the apprDay
	 */
	public String getApprDay() {
		return this.apprDay;
	}

	/**
	 * @param apprDay
	 *            the apprDay to set
	 */
	public void setApprDay(String apprDay) {
		this.apprDay = apprDay;
	}

	/**
	 * @return the sellermbrId
	 */
	public String getSellermbrId() {
		return this.sellermbrId;
	}

	/**
	 * @param sellermbrId
	 *            the sellermbrId to set
	 */
	public void setSellermbrId(String sellermbrId) {
		this.sellermbrId = sellermbrId;
	}

	/**
	 * @return the mbrNm
	 */
	public String getMbrNm() {
		return this.mbrNm;
	}

	/**
	 * @param mbrNm
	 *            the mbrNm to set
	 */
	public void setMbrNm(String mbrNm) {
		this.mbrNm = mbrNm;
	}

	/**
	 * @return the ctzRegNo
	 */
	public String getCtzRegNo() {
		return this.ctzRegNo;
	}

	/**
	 * @param ctzRegNo
	 *            the ctzRegNo to set
	 */
	public void setCtzRegNo(String ctzRegNo) {
		this.ctzRegNo = ctzRegNo;
	}

	/**
	 * @return the birth
	 */
	public String getBirth() {
		return this.birth;
	}

	/**
	 * @param birth
	 *            the birth to set
	 */
	public void setBirth(String birth) {
		this.birth = birth;
	}

	/**
	 * @return the sex
	 */
	public String getSex() {
		return this.sex;
	}

	/**
	 * @param sex
	 *            the sex to set
	 */
	public void setSex(String sex) {
		this.sex = sex;
	}

	/**
	 * @return the zip
	 */
	public String getZip() {
		return this.zip;
	}

	/**
	 * @param zip
	 *            the zip to set
	 */
	public void setZip(String zip) {
		this.zip = zip;
	}

	/**
	 * @return the addr
	 */
	public String getAddr() {
		return this.addr;
	}

	/**
	 * @param addr
	 *            the addr to set
	 */
	public void setAddr(String addr) {
		this.addr = addr;
	}

	/**
	 * @return the dtlAddr
	 */
	public String getDtlAddr() {
		return this.dtlAddr;
	}

	/**
	 * @param dtlAddr
	 *            the dtlAddr to set
	 */
	public void setDtlAddr(String dtlAddr) {
		this.dtlAddr = dtlAddr;
	}

	/**
	 * @return the email
	 */
	public String getEmail() {
		return this.email;
	}

	/**
	 * @param email
	 *            the email to set
	 */
	public void setEmail(String email) {
		this.email = email;
	}

	/**
	 * @return the emailRecvYn
	 */
	public String getEmailRecvYn() {
		return this.emailRecvYn;
	}

	/**
	 * @param emailRecvYn
	 *            the emailRecvYn to set
	 */
	public void setEmailRecvYn(String emailRecvYn) {
		this.emailRecvYn = emailRecvYn;
	}

	/**
	 * @return the realnmAuthYn
	 */
	public String getRealnmAuthYn() {
		return this.realnmAuthYn;
	}

	/**
	 * @param realnmAuthYn
	 *            the realnmAuthYn to set
	 */
	public void setRealnmAuthYn(String realnmAuthYn) {
		this.realnmAuthYn = realnmAuthYn;
	}

	/**
	 * @return the chrgpersNm
	 */
	public String getChrgpersNm() {
		return this.chrgpersNm;
	}

	/**
	 * @param chrgpersNm
	 *            the chrgpersNm to set
	 */
	public void setChrgpersNm(String chrgpersNm) {
		this.chrgpersNm = chrgpersNm;
	}

	/**
	 * @return the custCorsEmail
	 */
	public String getCustCorsEmail() {
		return this.custCorsEmail;
	}

	/**
	 * @param custCorsEmail
	 *            the custCorsEmail to set
	 */
	public void setCustCorsEmail(String custCorsEmail) {
		this.custCorsEmail = custCorsEmail;
	}

	/**
	 * @return the custCorsTelNo
	 */
	public String getCustCorsTelNo() {
		return this.custCorsTelNo;
	}

	/**
	 * @param custCorsTelNo
	 *            the custCorsTelNo to set
	 */
	public void setCustCorsTelNo(String custCorsTelNo) {
		this.custCorsTelNo = custCorsTelNo;
	}

	/**
	 * @return the chrgpersWilsTelNo
	 */
	public String getChrgpersWilsTelNo() {
		return this.chrgpersWilsTelNo;
	}

	/**
	 * @param chrgpersWilsTelNo
	 *            the chrgpersWilsTelNo to set
	 */
	public void setChrgpersWilsTelNo(String chrgpersWilsTelNo) {
		this.chrgpersWilsTelNo = chrgpersWilsTelNo;
	}

	/**
	 * @return the acctNo
	 */
	public String getAcctNo() {
		return this.acctNo;
	}

	/**
	 * @param acctNo
	 *            the acctNo to set
	 */
	public void setAcctNo(String acctNo) {
		this.acctNo = acctNo;
	}

	/**
	 * @return the bankCd
	 */
	public String getBankCd() {
		return this.bankCd;
	}

	/**
	 * @param bankCd
	 *            the bankCd to set
	 */
	public void setBankCd(String bankCd) {
		this.bankCd = bankCd;
	}

	/**
	 * @return the depstrNm
	 */
	public String getDepstrNm() {
		return this.depstrNm;
	}

	/**
	 * @param depstrNm
	 *            the depstrNm to set
	 */
	public void setDepstrNm(String depstrNm) {
		this.depstrNm = depstrNm;
	}

	/**
	 * @return the acctAuthYn
	 */
	public String getAcctAuthYn() {
		return this.acctAuthYn;
	}

	/**
	 * @param acctAuthYn
	 *            the acctAuthYn to set
	 */
	public void setAcctAuthYn(String acctAuthYn) {
		this.acctAuthYn = acctAuthYn;
	}

	/**
	 * @return the acctAuthDt
	 */
	public String getAcctAuthDt() {
		return this.acctAuthDt;
	}

	/**
	 * @param acctAuthDt
	 *            the acctAuthDt to set
	 */
	public void setAcctAuthDt(String acctAuthDt) {
		this.acctAuthDt = acctAuthDt;
	}

	/**
	 * @return the compNm
	 */
	public String getCompNm() {
		return this.compNm;
	}

	/**
	 * @param compNm
	 *            the compNm to set
	 */
	public void setCompNm(String compNm) {
		this.compNm = compNm;
	}

	/**
	 * @return the bizKindCd
	 */
	public String getBizKindCd() {
		return this.bizKindCd;
	}

	/**
	 * @param bizKindCd
	 *            the bizKindCd to set
	 */
	public void setBizKindCd(String bizKindCd) {
		this.bizKindCd = bizKindCd;
	}

	/**
	 * @return the bizRegNo
	 */
	public String getBizRegNo() {
		return this.bizRegNo;
	}

	/**
	 * @param bizRegNo
	 *            the bizRegNo to set
	 */
	public void setBizRegNo(String bizRegNo) {
		this.bizRegNo = bizRegNo;
	}

	/**
	 * @return the condNm
	 */
	public String getCondNm() {
		return this.condNm;
	}

	/**
	 * @param condNm
	 *            the condNm to set
	 */
	public void setCondNm(String condNm) {
		this.condNm = condNm;
	}

	/**
	 * @return the indtNm
	 */
	public String getIndtNm() {
		return this.indtNm;
	}

	/**
	 * @param indtNm
	 *            the indtNm to set
	 */
	public void setIndtNm(String indtNm) {
		this.indtNm = indtNm;
	}

	/**
	 * @return the corpRegNo
	 */
	public String getCorpRegNo() {
		return this.corpRegNo;
	}

	/**
	 * @param corpRegNo
	 *            the corpRegNo to set
	 */
	public void setCorpRegNo(String corpRegNo) {
		this.corpRegNo = corpRegNo;
	}

	/**
	 * @return the repTelNo
	 */
	public String getRepTelNo() {
		return this.repTelNo;
	}

	/**
	 * @param repTelNo
	 *            the repTelNo to set
	 */
	public void setRepTelNo(String repTelNo) {
		this.repTelNo = repTelNo;
	}

	/**
	 * @return the ceoNm
	 */
	public String getCeoNm() {
		return this.ceoNm;
	}

	/**
	 * @param ceoNm
	 *            the ceoNm to set
	 */
	public void setCeoNm(String ceoNm) {
		this.ceoNm = ceoNm;
	}

	/**
	 * @return the msalbizDeclYn
	 */
	public String getMsalbizDeclYn() {
		return this.msalbizDeclYn;
	}

	/**
	 * @param msalbizDeclYn
	 *            the msalbizDeclYn to set
	 */
	public void setMsalbizDeclYn(String msalbizDeclYn) {
		this.msalbizDeclYn = msalbizDeclYn;
	}

	/**
	 * @return the msalbizDeclNo
	 */
	public String getMsalbizDeclNo() {
		return this.msalbizDeclNo;
	}

	/**
	 * @param msalbizDeclNo
	 *            the msalbizDeclNo to set
	 */
	public void setMsalbizDeclNo(String msalbizDeclNo) {
		this.msalbizDeclNo = msalbizDeclNo;
	}

	/**
	 * @return the msalbizUndeclReasonCd
	 */
	public String getMsalbizUndeclReasonCd() {
		return this.msalbizUndeclReasonCd;
	}

	/**
	 * @param msalbizUndeclReasonCd
	 *            the msalbizUndeclReasonCd to set
	 */
	public void setMsalbizUndeclReasonCd(String msalbizUndeclReasonCd) {
		this.msalbizUndeclReasonCd = msalbizUndeclReasonCd;
	}

	/**
	 * @return the enprplZip
	 */
	public String getEnprplZip() {
		return this.enprplZip;
	}

	/**
	 * @param enprplZip
	 *            the enprplZip to set
	 */
	public void setEnprplZip(String enprplZip) {
		this.enprplZip = enprplZip;
	}

	/**
	 * @return the enprplAddr
	 */
	public String getEnprplAddr() {
		return this.enprplAddr;
	}

	/**
	 * @param enprplAddr
	 *            the enprplAddr to set
	 */
	public void setEnprplAddr(String enprplAddr) {
		this.enprplAddr = enprplAddr;
	}

	/**
	 * @return the enprplAddrDtl
	 */
	public String getEnprplAddrDtl() {
		return this.enprplAddrDtl;
	}

	/**
	 * @param enprplAddrDtl
	 *            the enprplAddrDtl to set
	 */
	public void setEnprplAddrDtl(String enprplAddrDtl) {
		this.enprplAddrDtl = enprplAddrDtl;
	}

	/**
	 * @return the easyTxnYn
	 */
	public String getEasyTxnYn() {
		return this.easyTxnYn;
	}

	/**
	 * @param easyTxnYn
	 *            the easyTxnYn to set
	 */
	public void setEasyTxnYn(String easyTxnYn) {
		this.easyTxnYn = easyTxnYn;
	}

	/**
	 * @return the taxBillPubYn
	 */
	public String getTaxBillPubYn() {
		return this.taxBillPubYn;
	}

	/**
	 * @param taxBillPubYn
	 *            the taxBillPubYn to set
	 */
	public void setTaxBillPubYn(String taxBillPubYn) {
		this.taxBillPubYn = taxBillPubYn;
	}

	/**
	 * @return the vendorCd
	 */
	public String getVendorCd() {
		return this.vendorCd;
	}

	/**
	 * @param vendorCd
	 *            the vendorCd to set
	 */
	public void setVendorCd(String vendorCd) {
		this.vendorCd = vendorCd;
	}

	/**
	 * @return the ictrySellermbrYn
	 */
	public String getIctrySellermbrYn() {
		return this.ictrySellermbrYn;
	}

	/**
	 * @param ictrySellermbrYn
	 *            the ictrySellermbrYn to set
	 */
	public void setIctrySellermbrYn(String ictrySellermbrYn) {
		this.ictrySellermbrYn = ictrySellermbrYn;
	}

	/**
	 * @return the nationCd
	 */
	public String getNationCd() {
		return this.nationCd;
	}

	/**
	 * @param nationCd
	 *            the nationCd to set
	 */
	public void setNationCd(String nationCd) {
		this.nationCd = nationCd;
	}

	/**
	 * @return the vatYn
	 */
	public String getVatYn() {
		return this.vatYn;
	}

	/**
	 * @param vatYn
	 *            the vatYn to set
	 */
	public void setVatYn(String vatYn) {
		this.vatYn = vatYn;
	}

	/**
	 * @return the frBankNm
	 */
	public String getFrBankNm() {
		return this.frBankNm;
	}

	/**
	 * @param frBankNm
	 *            the frBankNm to set
	 */
	public void setFrBankNm(String frBankNm) {
		this.frBankNm = frBankNm;
	}

	/**
	 * @return the frBrchNm
	 */
	public String getFrBrchNm() {
		return this.frBrchNm;
	}

	/**
	 * @param frBrchNm
	 *            the frBrchNm to set
	 */
	public void setFrBrchNm(String frBrchNm) {
		this.frBrchNm = frBrchNm;
	}

	/**
	 * @return the intlSwiftCd
	 */
	public String getIntlSwiftCd() {
		return this.intlSwiftCd;
	}

	/**
	 * @param intlSwiftCd
	 *            the intlSwiftCd to set
	 */
	public void setIntlSwiftCd(String intlSwiftCd) {
		this.intlSwiftCd = intlSwiftCd;
	}

	/**
	 * @return the frBrchCd
	 */
	public String getFrBrchCd() {
		return this.frBrchCd;
	}

	/**
	 * @param frBrchCd
	 *            the frBrchCd to set
	 */
	public void setFrBrchCd(String frBrchCd) {
		this.frBrchCd = frBrchCd;
	}

	/**
	 * @return the intlAba
	 */
	public String getIntlAba() {
		return this.intlAba;
	}

	/**
	 * @param intlAba
	 *            the intlAba to set
	 */
	public void setIntlAba(String intlAba) {
		this.intlAba = intlAba;
	}

	/**
	 * @return the frBankAddr
	 */
	public String getFrBankAddr() {
		return this.frBankAddr;
	}

	/**
	 * @param frBankAddr
	 *            the frBankAddr to set
	 */
	public void setFrBankAddr(String frBankAddr) {
		this.frBankAddr = frBankAddr;
	}

	/**
	 * @return the intlIban
	 */
	public String getIntlIban() {
		return this.intlIban;
	}

	/**
	 * @param intlIban
	 *            the intlIban to set
	 */
	public void setIntlIban(String intlIban) {
		this.intlIban = intlIban;
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
	 * @return the pmtYn
	 */
	public String getPmtYn() {
		return this.pmtYn;
	}

	/**
	 * @param pmtYn
	 *            the pmtYn to set
	 */
	public void setPmtYn(String pmtYn) {
		this.pmtYn = pmtYn;
	}

	/**
	 * @return the pmtCnt
	 */
	public String getPmtCnt() {
		return this.pmtCnt;
	}

	/**
	 * @param pmtCnt
	 *            the pmtCnt to set
	 */
	public void setPmtCnt(String pmtCnt) {
		this.pmtCnt = pmtCnt;
	}

	/**
	 * @return the multimdaList
	 */
	public List<Multimda> getMultimdaList() {
		return this.multimdaList;
	}

	/**
	 * @param multimdaList
	 *            the multimdaList to set
	 */
	public void setMultimdaList(List<Multimda> multimdaList) {
		this.multimdaList = multimdaList;
	}

}
