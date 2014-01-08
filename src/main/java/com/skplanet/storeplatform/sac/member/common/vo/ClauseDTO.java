package com.skplanet.storeplatform.sac.member.common.vo;

import com.skplanet.storeplatform.framework.core.common.vo.CommonInfo;

/**
 * TB_CM_CLAUSE TB_CM_TENANT_CLAUSE
 * 
 * 필수 약관 동의 목록
 * 
 * Updated on : 2014. 1. 8. Updated by : 심대진, 다모아 솔루션.
 */
public class ClauseDTO extends CommonInfo {

	private static final long serialVersionUID = 1L;

	/**************************
	 * TABLE - TB_CM_TENANT_CLAUSE
	 *************************/

	/**
	 * 테넌트 아이디
	 */
	private String tenantId;

	/**
	 * 필수 동의 여부
	 */
	private String mandAgreeYn;

	/**
	 * 사용여부
	 */
	private String useYn;

	/*******************************
	 * TABLE - TB_CM_CLAUSE
	 *******************************/

	/**
	 * 약관 아이디
	 */
	private String clauseId;

	private String clauseItemCd;

	private String startDay;

	private String endDay;

	private String filePath;

	private String fileNm;

	private String clauseVer;

	private String upClauseId;

	private String dpYn;

	private String regId;

	private String regDt;

	private String updId;

	private String updDt;

	public String getTenantId() {
		return this.tenantId;
	}

	public void setTenantId(String tenantId) {
		this.tenantId = tenantId;
	}

	public String getMandAgreeYn() {
		return this.mandAgreeYn;
	}

	public void setMandAgreeYn(String mandAgreeYn) {
		this.mandAgreeYn = mandAgreeYn;
	}

	public String getUseYn() {
		return this.useYn;
	}

	public void setUseYn(String useYn) {
		this.useYn = useYn;
	}

	public String getClauseId() {
		return this.clauseId;
	}

	public void setClauseId(String clauseId) {
		this.clauseId = clauseId;
	}

	public String getClauseItemCd() {
		return this.clauseItemCd;
	}

	public void setClauseItemCd(String clauseItemCd) {
		this.clauseItemCd = clauseItemCd;
	}

	public String getStartDay() {
		return this.startDay;
	}

	public void setStartDay(String startDay) {
		this.startDay = startDay;
	}

	public String getEndDay() {
		return this.endDay;
	}

	public void setEndDay(String endDay) {
		this.endDay = endDay;
	}

	public String getFilePath() {
		return this.filePath;
	}

	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}

	public String getFileNm() {
		return this.fileNm;
	}

	public void setFileNm(String fileNm) {
		this.fileNm = fileNm;
	}

	public String getClauseVer() {
		return this.clauseVer;
	}

	public void setClauseVer(String clauseVer) {
		this.clauseVer = clauseVer;
	}

	public String getUpClauseId() {
		return this.upClauseId;
	}

	public void setUpClauseId(String upClauseId) {
		this.upClauseId = upClauseId;
	}

	public String getDpYn() {
		return this.dpYn;
	}

	public void setDpYn(String dpYn) {
		this.dpYn = dpYn;
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
