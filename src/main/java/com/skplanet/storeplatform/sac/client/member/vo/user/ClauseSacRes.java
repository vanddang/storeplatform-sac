package com.skplanet.storeplatform.sac.client.member.vo.user;

import org.codehaus.jackson.map.annotate.JsonSerialize;
import org.codehaus.jackson.map.annotate.JsonSerialize.Inclusion;

import com.skplanet.storeplatform.framework.core.common.vo.CommonInfo;

/**
 * [RESPONSE] 약관 목록 조회
 * 
 * Updated on : 2014. 2. 3. Updated by : 강신완, 부르칸.
 */
@JsonSerialize(include = Inclusion.NON_NULL)
public class ClauseSacRes extends CommonInfo {

	private static final long serialVersionUID = 1L;

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

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

}
