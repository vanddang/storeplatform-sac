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
 * 탭 권한 Value Object.
 * 
 * Updated on : 2013. 12. 17. Updated by : wisestone_mikepark
 */
public class TabAuth extends CommonInfo implements Serializable {
	// TB_US_SELLERMBR_MULTIMDA_AUTH 멀티미디어권한 정보 테이블
	/** The Constant serial Version UID. */
	private static final long serialVersionUID = 1L;

	/** Tenant ID. */
	private String tenantID;

	/** 판매자 키. */
	private String sellerKey;

	/** 탭 코드. */
	private String tabCode; // TAB_CD

	/** 탭권한 유무(Y/N). */
	private String isPmt; // PMT_YN

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
	 * 탭권한 유무 리턴한다.
	 * 
	 * @return isPmt - 탭권한 유무
	 */
	public String getIsPmt() {
		return this.isPmt;
	}

	/**
	 * 탭코드 리턴한다.
	 * 
	 * @return tabCode - 탭코드
	 */
	public String getTabCode() {
		return this.tabCode;
	}

	/**
	 * 탭코드 설정한다.
	 * 
	 * @param tabCode
	 *            탭코드
	 */
	public void setTabCode(String tabCode) {
		this.tabCode = tabCode;
	}

	/**
	 * 탭권한 유무 설정한다.
	 * 
	 * @param isPmt
	 *            탭권한 유무
	 */
	public void setIsPmt(String isPmt) {
		this.isPmt = isPmt;
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
