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
import com.skplanet.storeplatform.member.client.common.vo.CommonRequest;

/**
 * 판매자 상태변경 요청 Value Object.
 * 
 * Updated on : 2013. 12. 10. Updated by : wisestone_mikepark
 */
public class UpdateStatusSellerRequest extends CommonInfo implements Serializable {
	/** The Constant serial Version UID. */
	private static final long serialVersionUID = 1L;

	/** 공통 요청 Value Object. */
	private CommonRequest commonRequest;

	/** 판매자 ID. */
	private String sellerID;

	/** 가입여부. */
	private String isNewEntry;

	/**
	 * 가입여부를 리턴한다.
	 * 
	 * @return isNewEntry - 가입여부
	 */
	public String getIsNewEntry() {
		return this.isNewEntry;
	}

	/**
	 * 가입여부를 설정한다.
	 * 
	 * @param isNewEntry
	 *            가입여부
	 */
	public void setIsNewEntry(String isNewEntry) {
		this.isNewEntry = isNewEntry;
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
	 * 공통 요청 Value Object를 리턴한다.
	 * 
	 * @return commonRequest - 공통 요청 Value Object
	 */
	public CommonRequest getCommonRequest() {
		return this.commonRequest;
	}

	/**
	 * 공통 요청 Value Object를 설정한다.
	 * 
	 * @param commonRequest
	 *            공통 요청 Value Object
	 */
	public void setCommonRequest(CommonRequest commonRequest) {
		this.commonRequest = commonRequest;
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
