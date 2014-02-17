package com.skplanet.storeplatform.sac.display.product.vo;

//public class CmsVo extends CommonModel implements Pagenateable {
public class CmsVo {
	
	
	private int txHisNo;
	private String transactionKey;
	private String txType;
	private String prodId;
	private String mbrNo;
	private String serverHostNm;
	private String endYn;
	private String resultCd;
	private String resultMsg;
	private String dtlDesc;
	private String regDttm;
	private String updDttm;
	
	public int getTxHisNo() {
		return txHisNo;
	}
	public void setTxHisNo(int txHisNo) {
		this.txHisNo = txHisNo;
	}
	public String getTransactionKey() {
		return transactionKey;
	}
	public void setTransactionKey(String transactionKey) {
		this.transactionKey = transactionKey;
	}
	public String getTxType() {
		return txType;
	}
	public void setTxType(String txType) {
		this.txType = txType;
	}
	public String getProdId() {
		return prodId;
	}
	public void setProdId(String prodId) {
		this.prodId = prodId;
	}
	public String getMbrNo() {
		return mbrNo;
	}
	public void setMbrNo(String mbrNo) {
		this.mbrNo = mbrNo;
	}
	public String getServerHostNm() {
		return serverHostNm;
	}
	public void setServerHostNm(String serverHostNm) {
		this.serverHostNm = serverHostNm;
	}
	public String getEndYn() {
		return endYn;
	}
	public void setEndYn(String endYn) {
		this.endYn = endYn;
	}
	public String getResultCd() {
		return resultCd;
	}
	public void setResultCd(String resultCd) {
		this.resultCd = resultCd;
	}
	public String getResultMsg() {
		return resultMsg;
	}
	public void setResultMsg(String resultMsg) {
		this.resultMsg = resultMsg;
	}
	public String getDtlDesc() {
		return dtlDesc;
	}
	public void setDtlDesc(String dtlDesc) {
		this.dtlDesc = dtlDesc;
	}
	public String getRegDttm() {
		return regDttm;
	}
	public void setRegDttm(String regDttm) {
		this.regDttm = regDttm;
	}
	public String getUpdDttm() {
		return updDttm;
	}
	public void setUpdDttm(String updDttm) {
		this.updDttm = updDttm;
	}
	
}