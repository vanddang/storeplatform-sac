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
 * 서류정보 Value Object.
 * 
 * Updated on : 2013. 12. 16. Updated by : wisestone_mikepark
 */
public class Document extends CommonInfo implements Serializable {
	/** The Constant serial Version UID. */
	private static final long serialVersionUID = 1L;

	/** 전환신청 키. 이 키는 전환신청 테이블에서 가져와서 입력한다. */
	private String accountChangeKey;

	/**
	 * 서류코드.<br>
	 * US001901 사업자등록증사본<br>
	 * US001902 법인명의통장사본<br>
	 * US001903 법인(개인)인감증명서원본<br>
	 * US001904 통신판매업 신고증 사본<br>
	 * US001905 외국인서류1<br>
	 * US001906 외국인서류2<br>
	 */
	private String documentCode; // DOC_CD 서류코드

	/** 판매자 키. */
	private String sellerKey; // INSD_SELLERMBR_NO 내부 사용자코드

	/** 서류경로. */
	private String documentPath; // REG_DOC_FILE_PATH 서류경로

	/** 서류명. */
	private String documentName; // REG_DOC_FILE_NM 서류명

	/** 서류사이즈. */
	private String documentSize; // REG_DOC_SIZE 서류사이즈

	/** 등록 ID. */
	private String regID; // REG_ID

	/** 등록일시. */
	private String regDate; // REG_DT

	/** 수정 ID. */
	private String updateID; // UPD_ID

	/** 수정일시. */
	private String updateDate; // UPD_DT

	/** 서류 사용여부 (Y/N). */
	private String isUsed; // USE_YN

	/**
	 * 전환신청 키를 리턴한다.
	 * 
	 * @return accountChangeKey - 전환신청 키
	 */
	public String getAccountChangeKey() {
		return this.accountChangeKey;
	}

	/**
	 * 전환신청 키를 설정한다.
	 * 
	 * @param accountChangeKey
	 *            전환신청 키
	 */
	public void setAccountChangeKey(String accountChangeKey) {
		this.accountChangeKey = accountChangeKey;
	}

	/**
	 * 서류코드를 리턴한다.
	 * 
	 * @return documentCode - 서류코드
	 */
	public String getDocumentCode() {
		return this.documentCode;
	}

	/**
	 * 서류코드를 설정한다.
	 * 
	 * @param documentCode
	 *            서류코드
	 */
	public void setDocumentCode(String documentCode) {
		this.documentCode = documentCode;
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
	 * 서류경로를 리턴한다.
	 * 
	 * @return documentPath - 서류경로
	 */
	public String getDocumentPath() {
		return this.documentPath;
	}

	/**
	 * 서류경로를 설정한다.
	 * 
	 * @param documentPath
	 *            서류경로
	 */
	public void setDocumentPath(String documentPath) {
		this.documentPath = documentPath;
	}

	/**
	 * 서류명을 리턴한다.
	 * 
	 * @return documentName - 서류명
	 */
	public String getDocumentName() {
		return this.documentName;
	}

	/**
	 * 서류명을 설정한다.
	 * 
	 * @param documentName
	 *            서류명
	 */
	public void setDocumentName(String documentName) {
		this.documentName = documentName;
	}

	/**
	 * 서류사이즈를 리턴한다.
	 * 
	 * @return documentSize - 서류사이즈
	 */
	public String getDocumentSize() {
		return this.documentSize;
	}

	/**
	 * 서류사이즈를 설정한다.
	 * 
	 * @param documentSize
	 *            서류사이즈
	 */
	public void setDocumentSize(String documentSize) {
		this.documentSize = documentSize;
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
	 * 서류 사용여부 (Y/N)를 리턴한다.
	 * 
	 * @return isUsed - 서류 사용여부 (Y/N)
	 */
	public String getIsUsed() {
		return this.isUsed;
	}

	/**
	 * 서류 사용여부 (Y/N)를 설정한다.
	 * 
	 * @param isUsed
	 *            서류 사용여부 (Y/N)
	 */
	public void setIsUsed(String isUsed) {
		this.isUsed = isUsed;
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
