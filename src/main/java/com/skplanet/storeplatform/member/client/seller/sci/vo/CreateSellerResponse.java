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
import com.skplanet.storeplatform.member.client.common.vo.CommonResponse;

/**
 * 판매자회원 가입 응답 Value Object.
 * 
 * Updated on : 2013. 12. 16. Updated by : wisestone_mikepark
 */
public class CreateSellerResponse extends CommonInfo implements Serializable {
	/** The Constant serial Version UID. */
	private static final long serialVersionUID = 1L;

	/** 공통 응답 Value Object. */
	private CommonResponse commonResponse;

	/** 판매자 ID. */
	private String sellerID;

	/** 판매자 키. */
	private String sellerKey; // INSD_SELLERMBR_NO

	/** 로그인 상태 코드. */
	private String loginStatusCode; // LOGIN_STATUS_CD

	/** 직권중지 상태 코드. */
	private String stopStatusCode; // OFAUTH_STOP_STATUS_CD

	/**
	 * 로그인 상태 코드를 리턴한다.
	 * 
	 * @return loginStatusCode - 로그인 상태 코드
	 */
	public String getLoginStatusCode() {
		return this.loginStatusCode;
	}

	/**
	 * 로그인 상태 코드를 설정한다.
	 * 
	 * @param loginStatusCode
	 *            로그인 상태 코드
	 */
	public void setLoginStatusCode(String loginStatusCode) {
		this.loginStatusCode = loginStatusCode;
	}

	/**
	 * 직권중지 상태 코드를 리턴한다.
	 * 
	 * @return stopStatusCode - 직권중지 상태 코드
	 */
	public String getStopStatusCode() {
		return this.stopStatusCode;
	}

	/**
	 * 직권중지 상태 코드를 설정한다.
	 * 
	 * @param stopStatusCode
	 *            직권중지 상태 코드
	 */
	public void setStopStatusCode(String stopStatusCode) {
		this.stopStatusCode = stopStatusCode;
	}

	/**
	 * 판매자 메인상태 코드.
	 * 
	 * @see com.skplanet.storeplatform.member.common.code.MainStateCode
	 */
	private String sellerMainStatus;

	/**
	 * 판매자 서브상태 코드.
	 * 
	 * @see com.skplanet.storeplatform.member.common.code.SubStateCode
	 */
	private String sellerSubStatus;

	/**
	 * 공통 응답 Value Object를 리턴한다.
	 * 
	 * @return commonResponse - 공통 응답 Value Object
	 */
	public CommonResponse getCommonResponse() {
		return this.commonResponse;
	}

	/**
	 * 공통 응답 Value Object를 설정한다.
	 * 
	 * @param commonResponse
	 *            공통 응답 Value Object
	 */
	public void setCommonResponse(CommonResponse commonResponse) {
		this.commonResponse = commonResponse;
	}

	/**
	 * 판매자 ID를 리턴한다.
	 * 
	 * @return sellerID - 판매자 ID
	 */
	public String getSellerID() {
		return this.sellerID;
	}

	/**
	 * 판매자 ID를 설정한다.
	 * 
	 * @param sellerID
	 *            판매자 ID
	 */
	public void setSellerID(String sellerID) {
		this.sellerID = sellerID;
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
	 * 판매자 메인상태 코드를 리턴한다.
	 * 
	 * @return sellerMainStatus - 판매자 메인상태 코드
	 */
	public String getSellerMainStatus() {
		return this.sellerMainStatus;
	}

	/**
	 * 판매자 메인상태 코드를 설정한다.
	 * 
	 * @param sellerMainStatus
	 *            판매자 메인상태 코드
	 */
	public void setSellerMainStatus(String sellerMainStatus) {
		this.sellerMainStatus = sellerMainStatus;
	}

	/**
	 * 판매자 서브상태 코드를 리턴한다.
	 * 
	 * @return sellerSubStatus - 판매자 서브상태 코드
	 */
	public String getSellerSubStatus() {
		return this.sellerSubStatus;
	}

	/**
	 * 판매자 서브상태 코드를 설정한다.
	 * 
	 * @param sellerSubStatus
	 *            판매자 서브상태 코드
	 */
	public void setSellerSubStatus(String sellerSubStatus) {
		this.sellerSubStatus = sellerSubStatus;
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
