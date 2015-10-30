/*
 * Copyright (c) 2013 SK planet.
 * All right reserved.
 * 
 * This software is the confidential and proprietary information of SK planet.
 * You shall not disclose such Confidential Information and
 * shall use it only in accordance with the terms of the license agreement
 * you entered into with SK planet.
 */
package com.skplanet.storeplatform.member.client.common.vo;

import java.io.Serializable;

import com.skplanet.storeplatform.framework.core.common.vo.CommonInfo;
import com.skplanet.storeplatform.member.client.common.util.Utils;

// TODO: Auto-generated Javadoc
/**
 * 이용약관 Value Object
 * 
 * Updated on : 2013. 12. 10. Updated by : wisestone_mikepark
 */
public class MbrClauseAgree extends CommonInfo implements Serializable {

	/** The Constant serial Version UID. */
	private static final long serialVersionUID = 1L;

	/** 이용약관 ID. */
	private String extraAgreementID; // CLAUSE_ID

	/** 이용약관 동의 여부. */
	private String isExtraAgreement; // AGREE_YN

	/** 필수동의 여부. */
	private String isMandatory; // MAND_AGREE_YN

	/** 이용약관 버전. */
	private String extraAgreementVersion; // CLAUSE_VER

	/** Tenant id. */
	private String tenantID; // TENANT_ID

	/** 내부 회원 키. */
	private String memberKey; // 내부 사용자

	/** 등록일시. */
	private String regDate; // REG_DT

	/** 수정일시. */
	private String updateDate; // UPD_DT

	/**
	 * 이용약관 ID를 리턴한다.
	 * 
	 * @return extraAgreementID - 이용약관 ID
	 */
	public String getExtraAgreementID() {
		return this.extraAgreementID;
	}

	/**
	 * 이용약관 ID를 설정한다.
	 * 
	 * @param extraAgreementID
	 *            이용약관 ID
	 */
	public void setExtraAgreementID(String extraAgreementID) {
		this.extraAgreementID = extraAgreementID;
	}

	/**
	 * 등록일시를 리턴한다.
	 * 
	 * @return regDate - 등록일시
	 */
	public String getRegDate() {
		return this.regDate;
	}

	/**
	 * 등록일시를 설정한다.
	 * 
	 * @param regDate
	 *            등록일시
	 */
	public void setRegDate(String regDate) {
		this.regDate = regDate;
	}

	/**
	 * 수정일시를 리턴한다.
	 * 
	 * @return updateDate - 수정일시
	 */
	public String getUpdateDate() {
		return this.updateDate;
	}

	/**
	 * 수정일시를 설정한다.
	 * 
	 * @param updateDate
	 *            수정일시
	 */
	public void setUpdateDate(String updateDate) {
		this.updateDate = updateDate;
	}

	/**
	 * 이용약관 동의 여부를 리턴한다.
	 * 
	 * @return isExtraAgreement - 이용약관 동의 여부
	 */
	public String getIsExtraAgreement() {
		return this.isExtraAgreement;
	}

	/**
	 * 이용약관 동의 여부를 설정한다.
	 * 
	 * @param isExtraAgreement
	 *            이용약관 동의 여부
	 */
	public void setIsExtraAgreement(String isExtraAgreement) {
		this.isExtraAgreement = isExtraAgreement;
	}

	/**
	 * 필수동의 여부를 리턴한다.
	 * 
	 * @return isMandatory - 필수동의 여부
	 */
	public String getIsMandatory() {
		return this.isMandatory;
	}

	/**
	 * 필수동의 여부를 설정한다.
	 * 
	 * @param isMandatory
	 *            필수동의 여부
	 */
	public void setIsMandatory(String isMandatory) {
		this.isMandatory = isMandatory;
	}

	/**
	 * 이용약관 버전을 리턴한다.
	 * 
	 * @return extraAgreementVersion - 이용약관 버전
	 */
	public String getExtraAgreementVersion() {
		return this.extraAgreementVersion;
	}

	/**
	 * 이용약관 버전을 설정한다.
	 * 
	 * @param extraAgreementVersion
	 *            이용약관 버전
	 */
	public void setExtraAgreementVersion(String extraAgreementVersion) {
		this.extraAgreementVersion = extraAgreementVersion;
	}

	/**
	 * Tenant ID를 리턴한다.
	 * 
	 * @return tenantID - Tenant ID
	 */
	public String getTenantID() {
		return this.tenantID;
	}

	/**
	 * Tenant ID를 설정한다.
	 * 
	 * @param tenantID
	 *            Tenant ID
	 */
	public void setTenantID(String tenantID) {
		this.tenantID = tenantID;
	}

	/**
	 * 내부 회원 키를 리턴한다.
	 * 
	 * @return memberKey - 내부 회원 키
	 */
	public String getMemberKey() {
		return this.memberKey;
	}

	/**
	 * 내부 회원 키를 설정한다.
	 * 
	 * @param memberKey
	 *            내부 회원 키
	 */
	public void setMemberKey(String memberKey) {
		this.memberKey = memberKey;
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
	 * @see com.skplanet.storeplatform.framework.core.common.vo.CommonInfo#toString()
	 */
	@Override
	public String toString() {
		return Utils.printKeyValues(this);
	}

}
