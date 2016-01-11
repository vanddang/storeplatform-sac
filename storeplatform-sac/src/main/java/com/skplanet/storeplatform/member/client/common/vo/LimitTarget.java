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

import com.skplanet.storeplatform.framework.core.common.vo.CommonInfo;
import com.skplanet.storeplatform.member.client.common.util.Utils;

import java.io.Serializable;

/**
 * 제한 정책 Value Object
 * 
 * Updated on : 2013. 12. 20. Updated by : wisestone_mikepark
 */
public class LimitTarget extends CommonInfo implements Serializable {
	/** The Constant serial Version UID. */
	private static final long serialVersionUID = 1L;

	/** 제한 정책 테이블 키. */
	private String limitTargetNo; // SEQUENCE

	/** 제한 정책 키. */
	private String limitPolicyKey; // LIMIT_POLICY_KEY

	/** 제한 정책 코드. */
	private String limitPolicyCode; // LIMIT_POLICY_CD

	/** 정책 적용 값. */
	private String policyApplyValue; // POLICY_APPLY_VALUE

	/** Start date. */
	private String startDate; // USE_START_DT

	/** End date. */
	private String endDate; // USE_END_DT

	/** Ins date. */
	private String insDate; // INS_DT

	/** 등록 ID. */
	private String regID; // REG_ID

	/** 등록일시. */
	private String regDate; // REG_DT

	/** 수정 ID. */
	private String updateID; // UPD_ID

	/** 수정일시. */
	private String updateDate; // UPD_DT

	/** 정책_적용_값. */
	private String limitAmount; // LIMT_AMT

	/** 이전_정책_적용_값. */
	private String preLimitAmount; // PRE_LIMT_AMT

	/** 정책_적용_값. */
	private String permissionType; // PMT_TYPE

	/** 사용여부 (Y/N). */
	private String isUsed; // USE_YN

	/**
	 * 회선_관리_상태.
	 */
	private String lineMangStatus; // LINE_MANG_STATUS

	/**
	 * 정책_적용_값을 리턴한다.
	 * 
	 * @return permissionType - 정책_적용_값
	 */
	public String getPermissionType() {
		return this.permissionType;
	}

	/**
	 * 정책_적용_값을 설정한다.
	 * 
	 * @param permissionType
	 *            정책_적용_값
	 */
	public void setPermissionType(String permissionType) {
		this.permissionType = permissionType;
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
	 * 정책_적용_값을 리턴한다.
	 * 
	 * @return limitAmount - 정책_적용_값
	 */
	public String getLimitAmount() {
		return this.limitAmount;
	}

	/**
	 * 정책_적용_값을 설정한다.
	 * 
	 * @param limitAmount
	 *            정책_적용_값
	 */
	public void setLimitAmount(String limitAmount) {
		this.limitAmount = limitAmount;
	}

	/**
	 * 이전_정책_적용_값을 리턴한다.
	 * 
	 * @return preLimitAmount - 이전_정책_적용_값
	 */
	public String getPreLimitAmount() {
		return this.preLimitAmount;
	}

	/**
	 * 이전_정책_적용_값을 설정한다.
	 * 
	 * @param preLimitAmount
	 *            이전_정책_적용_값
	 */
	public void setPreLimitAmount(String preLimitAmount) {
		this.preLimitAmount = preLimitAmount;
	}

	/**
	 * 제한 정책 테이블 키를 리턴한다.
	 * 
	 * @return limitTargetNo - 제한 정책 테이블 키
	 */
	public String getLimitTargetNo() {
		return this.limitTargetNo;
	}

	/**
	 * 제한 정책 테이블 키를 설정한다.
	 * 
	 * @param limitTargetNo
	 *            제한 정책 테이블 키
	 */
	public void setLimitTargetNo(String limitTargetNo) {
		this.limitTargetNo = limitTargetNo;
	}

	/**
	 * 제한 정책 키를 리턴한다.
	 * 
	 * @return limitPolicyKey - 제한 정책 키
	 */
	public String getLimitPolicyKey() {
		return this.limitPolicyKey;
	}

	/**
	 * 제한 정책 키를 설정한다.
	 * 
	 * @param limitPolicyKey
	 *            제한 정책 키
	 */
	public void setLimitPolicyKey(String limitPolicyKey) {
		this.limitPolicyKey = limitPolicyKey;
	}

	/**
	 * 제한 정책 코드를 리턴한다.
	 * 
	 * @return limitPolicyCode - 제한 정책 코드
	 */
	public String getLimitPolicyCode() {
		return this.limitPolicyCode;
	}

	/**
	 * 제한 정책 코드를 설정한다.
	 * 
	 * @param limitPolicyCode
	 *            제한 정책 코드
	 */
	public void setLimitPolicyCode(String limitPolicyCode) {
		this.limitPolicyCode = limitPolicyCode;
	}

	/**
	 * 정책 적용 값을 리턴한다.
	 * 
	 * @return policyApplyValue - 정책 적용 값
	 */
	public String getPolicyApplyValue() {
		return this.policyApplyValue;
	}

	/**
	 * 정책 적용 값을 설정한다.
	 * 
	 * @param policyApplyValue
	 *            정책 적용 값
	 */
	public void setPolicyApplyValue(String policyApplyValue) {
		this.policyApplyValue = policyApplyValue;
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
	 * 등록 ID를 리턴한다.
	 * 
	 * @return regID - 등록 ID
	 */
	public String getRegID() {
		return this.regID;
	}

	/**
	 * 등록 ID를 설정한다.
	 * 
	 * @param regID
	 *            등록 ID
	 */
	public void setRegID(String regID) {
		this.regID = regID;
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
	 * 수정 ID를 리턴한다.
	 * 
	 * @return updateID - 수정 ID
	 */
	public String getUpdateID() {
		return this.updateID;
	}

	/**
	 * 수정 ID를 설정한다.
	 * 
	 * @param updateID
	 *            수정 ID
	 */
	public void setUpdateID(String updateID) {
		this.updateID = updateID;
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
	 * @return lineMangStatus
	 */
	public String getLineMangStatus() {
		return this.lineMangStatus;
	}

	/**
	 * @param lineMangStatus
	 *            String
	 */
	public void setLineMangStatus(String lineMangStatus) {
		this.lineMangStatus = lineMangStatus;
	}

	/**
	 * @return insDate
	 */
	public String getInsDate() {
		return this.insDate;
	}

	/**
	 * @param insDate
	 *            String
	 */
	public void setInsDate(String insDate) {
		this.insDate = insDate;
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
