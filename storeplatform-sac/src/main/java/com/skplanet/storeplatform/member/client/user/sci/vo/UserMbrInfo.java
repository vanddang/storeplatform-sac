package com.skplanet.storeplatform.member.client.user.sci.vo;

import com.skplanet.storeplatform.framework.core.common.vo.CommonInfo;

import java.io.Serializable;
import java.util.Date;

/**
 * 사용자 전체 기본정보 Value Object
 * 
 * Updated on : 2015. 05. 29. Updated by : incross_cmlee80
 */
public class UserMbrInfo extends CommonInfo implements Serializable {

	/** The Constant serial Version UID. */
	private static final long serialVersionUID = 1L;

	private String tenantId;
	private String insdUserMbrNo;
	private String userMbrNo;
	private Date startDt;
	private Date endDt;
	private String mbrClasCd;
	private String mbrStatusMainCd;
	private String mbrStatusSubCd;
	private int deviceRegCnt;
	private String entryDay;
	private String bolterDay;
	private String bolterReasonDesc;
	private Date updDt;
	private String mbrId;
	private String mbrNm;
	private String birth;
	private String sex;
	private String telNationNo;
	private String telNo;
	private String emailAddr;
	private String emailRecvYn;
	private String smsRecvYn;
	private String realNmAuthYn;
	private String lglAgentAgreeYn;
	private String nationCd;
	private String langCd;
	private String intgSvcNo;
	private String changedEntryYn;
	private String oneidChangedEntryDay;
	private String systemId;
	private String oneidUseAgreeSvcList;
	private String mnoCd;
	private String bolterCaseCd;
	private String loginStatusCd;
	private String ofauthStopStatusCd;
	private String intgPontYn;
	private String updEmailAddr;
	private Date lastLoginDt;
	private Date lastPrchsDt;

	/**
	 * @return the tenantId
	 */
	public String getTenantId() {
		return this.tenantId;
	}

	/**
	 * @param tenantId
	 *            the tenantId to set
	 */
	public void setTenantId(String tenantId) {
		this.tenantId = tenantId;
	}

	/**
	 * @return the insdUserMbrNo
	 */
	public String getInsdUserMbrNo() {
		return this.insdUserMbrNo;
	}

	/**
	 * @param insdUserMbrNo
	 *            the insdUserMbrNo to set
	 */
	public void setInsdUserMbrNo(String insdUserMbrNo) {
		this.insdUserMbrNo = insdUserMbrNo;
	}

	/**
	 * @return the userMbrNo
	 */
	public String getUserMbrNo() {
		return this.userMbrNo;
	}

	/**
	 * @param userMbrNo
	 *            the userMbrNo to set
	 */
	public void setUserMbrNo(String userMbrNo) {
		this.userMbrNo = userMbrNo;
	}

	/**
	 * @return the startDt
	 */
	public Date getStartDt() {
		return this.startDt;
	}

	/**
	 * @param startDt
	 *            the startDt to set
	 */
	public void setStartDt(Date startDt) {
		this.startDt = startDt;
	}

	/**
	 * @return the endDt
	 */
	public Date getEndDt() {
		return this.endDt;
	}

	/**
	 * @param endDt
	 *            the endDt to set
	 */
	public void setEndDt(Date endDt) {
		this.endDt = endDt;
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
	 * @return the mbrStatusMainCd
	 */
	public String getMbrStatusMainCd() {
		return this.mbrStatusMainCd;
	}

	/**
	 * @param mbrStatusMainCd
	 *            the mbrStatusMainCd to set
	 */
	public void setMbrStatusMainCd(String mbrStatusMainCd) {
		this.mbrStatusMainCd = mbrStatusMainCd;
	}

	/**
	 * @return the mbrStatusSubCd
	 */
	public String getMbrStatusSubCd() {
		return this.mbrStatusSubCd;
	}

	/**
	 * @param mbrStatusSubCd
	 *            the mbrStatusSubCd to set
	 */
	public void setMbrStatusSubCd(String mbrStatusSubCd) {
		this.mbrStatusSubCd = mbrStatusSubCd;
	}

	/**
	 * @return the deviceRegCnt
	 */
	public int getDeviceRegCnt() {
		return this.deviceRegCnt;
	}

	/**
	 * @param deviceRegCnt
	 *            the deviceRegCnt to set
	 */
	public void setDeviceRegCnt(int deviceRegCnt) {
		this.deviceRegCnt = deviceRegCnt;
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
	 * @return the bolterReasonDesc
	 */
	public String getBolterReasonDesc() {
		return this.bolterReasonDesc;
	}

	/**
	 * @param bolterReasonDesc
	 *            the bolterReasonDesc to set
	 */
	public void setBolterReasonDesc(String bolterReasonDesc) {
		this.bolterReasonDesc = bolterReasonDesc;
	}

	/**
	 * @return the updDt
	 */
	public Date getUpdDt() {
		return this.updDt;
	}

	/**
	 * @param updDt
	 *            the updDt to set
	 */
	public void setUpdDt(Date updDt) {
		this.updDt = updDt;
	}

	/**
	 * @return the mbrId
	 */
	public String getMbrId() {
		return this.mbrId;
	}

	/**
	 * @param mbrId
	 *            the mbrId to set
	 */
	public void setMbrId(String mbrId) {
		this.mbrId = mbrId;
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
	 * @return the telNationNo
	 */
	public String getTelNationNo() {
		return this.telNationNo;
	}

	/**
	 * @param telNationNo
	 *            the telNationNo to set
	 */
	public void setTelNationNo(String telNationNo) {
		this.telNationNo = telNationNo;
	}

	/**
	 * @return the telNo
	 */
	public String getTelNo() {
		return this.telNo;
	}

	/**
	 * @param telNo
	 *            the telNo to set
	 */
	public void setTelNo(String telNo) {
		this.telNo = telNo;
	}

	/**
	 * @return the emailAddr
	 */
	public String getEmailAddr() {
		return this.emailAddr;
	}

	/**
	 * @param emailAddr
	 *            the emailAddr to set
	 */
	public void setEmailAddr(String emailAddr) {
		this.emailAddr = emailAddr;
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
	 * @return the smsRecvYn
	 */
	public String getSmsRecvYn() {
		return this.smsRecvYn;
	}

	/**
	 * @param smsRecvYn
	 *            the smsRecvYn to set
	 */
	public void setSmsRecvYn(String smsRecvYn) {
		this.smsRecvYn = smsRecvYn;
	}

	/**
	 * @return the realNmAuthYn
	 */
	public String getRealNmAuthYn() {
		return this.realNmAuthYn;
	}

	/**
	 * @param realNmAuthYn
	 *            the realNmAuthYn to set
	 */
	public void setRealNmAuthYn(String realNmAuthYn) {
		this.realNmAuthYn = realNmAuthYn;
	}

	/**
	 * @return the lglAgentAgreeYn
	 */
	public String getLglAgentAgreeYn() {
		return this.lglAgentAgreeYn;
	}

	/**
	 * @param lglAgentAgreeYn
	 *            the lglAgentAgreeYn to set
	 */
	public void setLglAgentAgreeYn(String lglAgentAgreeYn) {
		this.lglAgentAgreeYn = lglAgentAgreeYn;
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
	 * @return the langCd
	 */
	public String getLangCd() {
		return this.langCd;
	}

	/**
	 * @param langCd
	 *            the langCd to set
	 */
	public void setLangCd(String langCd) {
		this.langCd = langCd;
	}

	/**
	 * @return the intgSvcNo
	 */
	public String getIntgSvcNo() {
		return this.intgSvcNo;
	}

	/**
	 * @param intgSvcNo
	 *            the intgSvcNo to set
	 */
	public void setIntgSvcNo(String intgSvcNo) {
		this.intgSvcNo = intgSvcNo;
	}

	/**
	 * @return the changedEntryYn
	 */
	public String getChangedEntryYn() {
		return this.changedEntryYn;
	}

	/**
	 * @param changedEntryYn
	 *            the changedEntryYn to set
	 */
	public void setChangedEntryYn(String changedEntryYn) {
		this.changedEntryYn = changedEntryYn;
	}

	/**
	 * @return the oneidChangedEntryDay
	 */
	public String getOneidChangedEntryDay() {
		return this.oneidChangedEntryDay;
	}

	/**
	 * @param oneidChangedEntryDay
	 *            the oneidChangedEntryDay to set
	 */
	public void setOneidChangedEntryDay(String oneidChangedEntryDay) {
		this.oneidChangedEntryDay = oneidChangedEntryDay;
	}

	/**
	 * @return the systemId
	 */
	public String getSystemId() {
		return this.systemId;
	}

	/**
	 * @param systemId
	 *            the systemId to set
	 */
	public void setSystemId(String systemId) {
		this.systemId = systemId;
	}

	/**
	 * @return the oneidUseAgreeSvcList
	 */
	public String getOneidUseAgreeSvcList() {
		return this.oneidUseAgreeSvcList;
	}

	/**
	 * @param oneidUseAgreeSvcList
	 *            the oneidUseAgreeSvcList to set
	 */
	public void setOneidUseAgreeSvcList(String oneidUseAgreeSvcList) {
		this.oneidUseAgreeSvcList = oneidUseAgreeSvcList;
	}

	/**
	 * @return the mnoCd
	 */
	public String getMnoCd() {
		return this.mnoCd;
	}

	/**
	 * @param mnoCd
	 *            the mnoCd to set
	 */
	public void setMnoCd(String mnoCd) {
		this.mnoCd = mnoCd;
	}

	/**
	 * @return the bolterCaseCd
	 */
	public String getBolterCaseCd() {
		return this.bolterCaseCd;
	}

	/**
	 * @param bolterCaseCd
	 *            the bolterCaseCd to set
	 */
	public void setBolterCaseCd(String bolterCaseCd) {
		this.bolterCaseCd = bolterCaseCd;
	}

	/**
	 * @return the loginStatusCd
	 */
	public String getLoginStatusCd() {
		return this.loginStatusCd;
	}

	/**
	 * @param loginStatusCd
	 *            the loginStatusCd to set
	 */
	public void setLoginStatusCd(String loginStatusCd) {
		this.loginStatusCd = loginStatusCd;
	}

	/**
	 * @return the ofauthStopStatusCd
	 */
	public String getOfauthStopStatusCd() {
		return this.ofauthStopStatusCd;
	}

	/**
	 * @param ofauthStopStatusCd
	 *            the ofauthStopStatusCd to set
	 */
	public void setOfauthStopStatusCd(String ofauthStopStatusCd) {
		this.ofauthStopStatusCd = ofauthStopStatusCd;
	}

	/**
	 * @return the intgPontYn
	 */
	public String getIntgPontYn() {
		return this.intgPontYn;
	}

	/**
	 * @param intgPontYn
	 *            the intgPontYn to set
	 */
	public void setIntgPontYn(String intgPontYn) {
		this.intgPontYn = intgPontYn;
	}

	/**
	 * @return the updEmailAddr
	 */
	public String getUpdEmailAddr() {
		return this.updEmailAddr;
	}

	/**
	 * @param updEmailAddr
	 *            the updEmailAddr to set
	 */
	public void setUpdEmailAddr(String updEmailAddr) {
		this.updEmailAddr = updEmailAddr;
	}

	/**
	 * @return the lastLoginDt
	 */
	public Date getLastLoginDt() {
		return this.lastLoginDt;
	}

	/**
	 * @param lastLoginDt
	 *            the lastLoginDt to set
	 */
	public void setLastLoginDt(Date lastLoginDt) {
		this.lastLoginDt = lastLoginDt;
	}

	/**
	 * @return the lastPrchsDt
	 */
	public Date getLastPrchsDt() {
		return this.lastPrchsDt;
	}

	/**
	 * @param lastPrchsDt
	 *            the lastPrchsDt to set
	 */
	public void setLastPrchsDt(Date lastPrchsDt) {
		this.lastPrchsDt = lastPrchsDt;
	}

}
