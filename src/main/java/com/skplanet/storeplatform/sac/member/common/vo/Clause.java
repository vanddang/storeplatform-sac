package com.skplanet.storeplatform.sac.member.common.vo;

import com.skplanet.storeplatform.framework.core.common.vo.CommonInfo;

/**
 * TB_CM_CLAUSE TB_CM_TENANT_CLAUSE
 * 
 * 필수 약관 동의 목록
 * 
 * Updated on : 2014. 1. 8. Updated by : 심대진, 다모아 솔루션.
 */
public class Clause extends CommonInfo {

	private static final long serialVersionUID = 1L;

	/**************************
	 * TABLE - TB_CM_TENANT_CLAUSE
	 *************************/

	/**
	 * 테넌트 아이디.
	 */
	private String tenantId;

	/**
	 * 필수 동의 여부.
	 */
	private String mandAgreeYn;

	/**
	 * 사용여부.
	 */
	private String useYn;

	/*******************************
	 * TABLE - TB_CM_CLAUSE
	 *******************************/

	/**
	 * 약관 아이디.
	 */
	private String clauseId;

	private String clauseItemCd;

	private String startDay;

	private String endDay;

	private String filePath;

	private String fileNm;

	private String clauseVer;

	private String upClauseItemCd;

	private String dpYn;

	private String regId;

	private String regDt;

	private String updId;

	private String updDt;

	/**
	 * 모바일웹 파일경로.
	 */
	private String mwFilePath;

	/**
	 * 모바일웹 파일명.
	 */
	private String mwFileNm;

	/**
	 * 재동의 여부.
	 */
	private String reAgreeYn;

	/**
	 * 모바일 약관 여부.
	 */
	private String isMobileYn;

	/**
	 * @return String : tenantId
	 */
	public String getTenantId() {
		return this.tenantId;
	}

	/**
	 * @param tenantId
	 *            String : the tenantId to set
	 */
	public void setTenantId(String tenantId) {
		this.tenantId = tenantId;
	}

	/**
	 * @return String : mandAgreeYn
	 */
	public String getMandAgreeYn() {
		return this.mandAgreeYn;
	}

	/**
	 * @param mandAgreeYn
	 *            String : the mandAgreeYn to set
	 */
	public void setMandAgreeYn(String mandAgreeYn) {
		this.mandAgreeYn = mandAgreeYn;
	}

	/**
	 * @return String : useYn
	 */
	public String getUseYn() {
		return this.useYn;
	}

	/**
	 * @param useYn
	 *            String : the useYn to set
	 */
	public void setUseYn(String useYn) {
		this.useYn = useYn;
	}

	/**
	 * @return String : clauseId
	 */
	public String getClauseId() {
		return this.clauseId;
	}

	/**
	 * @param clauseId
	 *            String : the clauseId to set
	 */
	public void setClauseId(String clauseId) {
		this.clauseId = clauseId;
	}

	/**
	 * @return String : clauseItemCd
	 */
	public String getClauseItemCd() {
		return this.clauseItemCd;
	}

	/**
	 * @param clauseItemCd
	 *            String : the clauseItemCd to set
	 */
	public void setClauseItemCd(String clauseItemCd) {
		this.clauseItemCd = clauseItemCd;
	}

	/**
	 * @return String : startDay
	 */
	public String getStartDay() {
		return this.startDay;
	}

	/**
	 * @param startDay
	 *            String : the startDay to set
	 */
	public void setStartDay(String startDay) {
		this.startDay = startDay;
	}

	/**
	 * @return String : endDay
	 */
	public String getEndDay() {
		return this.endDay;
	}

	/**
	 * @param endDay
	 *            String : the endDay to set
	 */
	public void setEndDay(String endDay) {
		this.endDay = endDay;
	}

	/**
	 * @return String : filePath
	 */
	public String getFilePath() {
		return this.filePath;
	}

	/**
	 * @param filePath
	 *            String : the filePath to set
	 */
	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}

	/**
	 * @return String : fileNm
	 */
	public String getFileNm() {
		return this.fileNm;
	}

	/**
	 * @param fileNm
	 *            String : the fileNm to set
	 */
	public void setFileNm(String fileNm) {
		this.fileNm = fileNm;
	}

	/**
	 * @return String : clauseVer
	 */
	public String getClauseVer() {
		return this.clauseVer;
	}

	/**
	 * @param clauseVer
	 *            String : the clauseVer to set
	 */
	public void setClauseVer(String clauseVer) {
		this.clauseVer = clauseVer;
	}

	/**
	 * @return String : upClauseId
	 */
	public String getUpClauseItemCd() {
		return this.upClauseItemCd;
	}

	/**
	 * @param upClauseId
	 *            String : the upClauseId to set
	 */
	public void setUpClauseItemCd(String upClauseItemCd) {
		this.upClauseItemCd = upClauseItemCd;
	}

	/**
	 * @return String : dpYn
	 */
	public String getDpYn() {
		return this.dpYn;
	}

	/**
	 * @param dpYn
	 *            String : the dpYn to set
	 */
	public void setDpYn(String dpYn) {
		this.dpYn = dpYn;
	}

	/**
	 * @return String : regId
	 */
	public String getRegId() {
		return this.regId;
	}

	/**
	 * @param regId
	 *            String : the regId to set
	 */
	public void setRegId(String regId) {
		this.regId = regId;
	}

	/**
	 * @return String : regDt
	 */
	public String getRegDt() {
		return this.regDt;
	}

	/**
	 * @param regDt
	 *            String : the regDt to set
	 */
	public void setRegDt(String regDt) {
		this.regDt = regDt;
	}

	/**
	 * @return String : updId
	 */
	public String getUpdId() {
		return this.updId;
	}

	/**
	 * @param updId
	 *            String : the updId to set
	 */
	public void setUpdId(String updId) {
		this.updId = updId;
	}

	/**
	 * @return String : updDt
	 */
	public String getUpdDt() {
		return this.updDt;
	}

	/**
	 * @param updDt
	 *            String : the updDt to set
	 */
	public void setUpdDt(String updDt) {
		this.updDt = updDt;
	}

	/**
	 * @return the mwFilePath
	 */
	public String getMwFilePath() {
		return this.mwFilePath;
	}

	/**
	 * @param mwFilePath
	 *            the mwFilePath to set
	 */
	public void setMwFilePath(String mwFilePath) {
		this.mwFilePath = mwFilePath;
	}

	/**
	 * @return the mwFileNm
	 */
	public String getMwFileNm() {
		return this.mwFileNm;
	}

	/**
	 * @param mwFileNm
	 *            the mwFileNm to set
	 */
	public void setMwFileNm(String mwFileNm) {
		this.mwFileNm = mwFileNm;
	}

	/**
	 * @return the reAgreeYn
	 */
	public String getReAgreeYn() {
		return this.reAgreeYn;
	}

	/**
	 * @param reAgreeYn
	 *            the reAgreeYn to set
	 */
	public void setReAgreeYn(String reAgreeYn) {
		this.reAgreeYn = reAgreeYn;
	}

	/**
	 * @return the isMobileYn
	 */
	public String getIsMobileYn() {
		return this.isMobileYn;
	}

	/**
	 * @param isMobileYn
	 *            the isMobileYn to set
	 */
	public void setIsMobileYn(String isMobileYn) {
		this.isMobileYn = isMobileYn;
	}

}
