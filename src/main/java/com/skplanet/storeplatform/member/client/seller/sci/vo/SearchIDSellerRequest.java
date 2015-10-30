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

// TODO: Auto-generated Javadoc
/**
 * 판매자회원 ID 찾기 요청 Value Object
 * 
 * Updated on : 2013. 12. 10. Updated by : wisestone_mikepark
 */
public class SearchIDSellerRequest extends CommonInfo implements Serializable {

	/** The Constant serial Version UID. */
	private static final long serialVersionUID = 1L;

	/** 공통 요청 Value Object. */
	private CommonRequest commonRequest;

	/** 판매자 Email. */
	private String sellerEmail;

	/** 회사명. */
	private String sellerCompany;

	/** 사업자 등록번호. */
	private String sellerBizNumber;

	/** 전화번호. */
	private String sellerPhone;

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
	 * 판매자 Email를 리턴한다.
	 * 
	 * @return sellerEmail - 판매자 Email
	 */
	public String getSellerEmail() {
		return this.sellerEmail;
	}

	/**
	 * 판매자 Email를 설정한다.
	 * 
	 * @param sellerEmail
	 *            판매자 Email
	 */
	public void setSellerEmail(String sellerEmail) {
		this.sellerEmail = sellerEmail;
	}

	/**
	 * 회사명를 리턴한다.
	 * 
	 * @return sellerCompany - 회사명
	 */
	public String getSellerCompany() {
		return this.sellerCompany;
	}

	/**
	 * 회사명를 설정한다.
	 * 
	 * @param sellerCompany
	 *            회사명
	 */
	public void setSellerCompany(String sellerCompany) {
		this.sellerCompany = sellerCompany;
	}

	/**
	 * 사업자 등록번호를 리턴한다.
	 * 
	 * @return sellerBizNumber - 사업자 등록번호
	 */
	public String getSellerBizNumber() {
		return this.sellerBizNumber;
	}

	/**
	 * 사업자 등록번호를 설정한다.
	 * 
	 * @param sellerBizNumber
	 *            사업자 등록번호
	 */
	public void setSellerBizNumber(String sellerBizNumber) {
		this.sellerBizNumber = sellerBizNumber;
	}

	/**
	 * 전화번호를 리턴한다.
	 * 
	 * @return sellerPhone - 전화번호
	 */
	public String getSellerPhone() {
		return this.sellerPhone;
	}

	/**
	 * 전화번호를 설정한다.
	 * 
	 * @param sellerPhone
	 *            전화번호
	 */
	public void setSellerPhone(String sellerPhone) {
		this.sellerPhone = sellerPhone;
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
