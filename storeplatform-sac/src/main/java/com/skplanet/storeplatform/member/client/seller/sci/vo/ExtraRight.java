/*
 * Copyright (c) 2013 SK planet.
 * All right reserved.
 * 
 * This software is the confidential and proprietary information of SK planet.
 * You shall not disclose such Confidential Information and
 * shall use it only in accordance with the terms of the license agreement
 * you entered into with SK planet.
 */
package com.skplanet.storeplatform.member.client.seller.sci.vo;

import java.io.Serializable;

import com.skplanet.storeplatform.framework.core.common.vo.CommonInfo;
import com.skplanet.storeplatform.member.client.common.util.Utils;

// TODO: Auto-generated Javadoc
/**
 * 멀티미디어권한 Value Object.
 * 
 * Updated on : 2013. 12. 17. Updated by : wisestone_mikepark
 */
public class ExtraRight extends CommonInfo implements Serializable {
	// TB_US_SELLERMBR_MULTIMDA_AUTH 멀티미디어권한 정보 테이블
	/** The Constant serial Version UID. */
	private static final long serialVersionUID = 1L;

	/** Tenant ID. */
	private String tenantID;

	/** 판매자 키. */
	private String sellerKey;

	/** 멀티미디어권한 코드. */
	private String rightProfileCode; // MULTIMDA_CD

	/** 판매자 정산율. */
	private String sellerRate; // SELLERMBR_SETT_RATE 판매자 정산율

	/** Tenant 정산율. */
	private String tenantRate; // SELLERMBR_SETT_RATE tenant 정산율

	/** Start date. ex (YYYYMMDDHH24MISS) ("20130122162853") 2013-01-22 16:28:53. */
	private String startDate; // USE_START_DT

	/** End date. ex (YYYYMMDDHH24MISS) ("20130122162853") 2013-01-22 16:28:53. */
	private String endDate; // USE_END_DT

	/** 등록 ID. */
	private String regID; // REG_ID

	/** 등록일시. */
	private String regDate; // REG_DT

	/** 수정 ID. */
	private String updateID; // UPD_ID

	/** 수정일시. */
	private String updateDate; // UPD_DT

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
	 * 판매자 키를 리턴한다.
	 * 
	 * @return sellerKey - 판매자 키
	 */
	public String getSellerKey() {
		return this.sellerKey;
	}

	/**
	 * 판매자 키를 설정한다.
	 * 
	 * @param sellerKey
	 *            판매자 키
	 */
	public void setSellerKey(String sellerKey) {
		this.sellerKey = sellerKey;
	}

	/**
	 * 판매자 정산율을 리턴한다.
	 * 
	 * @return sellerRate - 판매자 정산율
	 */
	public String getSellerRate() {
		return this.sellerRate;
	}

	/**
	 * 판매자 정산율을 설정한다.
	 * 
	 * @param sellerRate
	 *            판매자 정산율
	 */
	public void setSellerRate(String sellerRate) {
		this.sellerRate = sellerRate;
	}

	/**
	 * Tenant 정산율을 리턴한다.
	 * 
	 * @return tenantRate - Tenant 정산율
	 */
	public String getTenantRate() {
		return this.tenantRate;
	}

	/**
	 * Tenant 정산율을 설정한다.
	 * 
	 * @param tenantRate
	 *            Tenant 정산율
	 */
	public void setTenantRate(String tenantRate) {
		this.tenantRate = tenantRate;
	}

	/**
	 * 멀티미디어권한 코드를 리턴한다.
	 * 
	 * @return rightProfileCode - 멀티미디어권한 코드
	 */
	public String getRightProfileCode() {
		return this.rightProfileCode;
	}

	/**
	 * 멀티미디어권한 코드를 설정한다.
	 * 
	 * @param rightProfileCode
	 *            멀티미디어권한 코드
	 */
	public void setRightProfileCode(String rightProfileCode) {
		this.rightProfileCode = rightProfileCode;
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
