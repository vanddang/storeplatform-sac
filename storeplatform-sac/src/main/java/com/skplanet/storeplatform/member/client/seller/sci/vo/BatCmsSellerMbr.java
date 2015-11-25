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
 * 판매자 기본정보 Value Object
 * 
 * Updated on : 2013. 12. 10. Updated by : wisestone_mikepark
 */
public class BatCmsSellerMbr extends CommonInfo implements Serializable {

	/** The Constant serial Version UID. */
	private static final long serialVersionUID = 1L;

	/** 판매자 키. */
	private String sellerKey; // INSD_SELLERMBR_NO

	/** 상태_타입. */
	private String statusType;

	/** 시도_횟수. */
	private String attemptCnt;

	/** 결과_코드. */
	private String resultCode;

	/** 등록일시. */
	private String regDate; // REG_DT

	/** 수정일시. */
	private String updateDate; // UPD_DT

	/** Sequence number. */
	private String sequence; // AUTH_SEQ

	/**
	 * 상태_타입 리턴한다.
	 * 
	 * @return statusType - 상태_타입
	 */

	public String getStatusType() {
		return this.statusType;
	}

	/**
	 * 상태_타입 설정한다.
	 * 
	 * @param statusType
	 *            상태_타입
	 */
	public void setStatusType(String statusType) {
		this.statusType = statusType;
	}

	/**
	 * 시도_횟수 리턴한다.
	 * 
	 * @return attemptCnt - 시도_횟수
	 */

	public String getAttemptCnt() {
		return this.attemptCnt;
	}

	/**
	 * 시도_횟수 설정한다.
	 * 
	 * @param attemptCnt
	 *            시도_횟수
	 */
	public void setAttemptCnt(String attemptCnt) {
		this.attemptCnt = attemptCnt;
	}

	/**
	 * 결과_코드리턴한다.
	 * 
	 * @return resultCode - 결과_코드
	 */

	public String getResultCode() {
		return this.resultCode;
	}

	/**
	 * 결과_코드 설정한다.
	 * 
	 * @param resultCode
	 *            결과_코드
	 */
	public void setResultCode(String resultCode) {
		this.resultCode = resultCode;
	}

	/**
	 * Sequence number를 리턴한다.
	 * 
	 * @return sequence - Sequence number
	 */
	public String getSequence() {
		return this.sequence;
	}

	/**
	 * Sequence number를 설정한다.
	 * 
	 * @param sequence
	 *            Sequence number
	 */
	public void setSequence(String sequence) {
		this.sequence = sequence;
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
