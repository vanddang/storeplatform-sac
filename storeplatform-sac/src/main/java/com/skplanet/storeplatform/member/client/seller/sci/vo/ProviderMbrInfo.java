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

/**
 * 판매 제공자 정보 Value Object
 * 
 * Updated on : 2015. 11. 16. Updated by : 최진호, 보고지티.
 */
public class ProviderMbrInfo extends CommonInfo implements Serializable {

	/** */
	private static final long serialVersionUID = 1L;
	/** 상하구분. */
	public String appStat;
	/** 회사명. */
	public String sellerCompany;
	/** 제공자명. */
	public String sellerName;
	/** 제공자 Email . */
	public String sellerEmail;
	/** 통신판매업 신고번호 . */
	public String bizRegNumber;
	/** 주소 . */
	public String sellerAddress;
	/** 연락처 . */
	public String sellerPhone;

	/**
	 * @return the appStat
	 */
	public String getAppStat() {
		return this.appStat;
	}

	/**
	 * @param appStat
	 *            the appStat to set
	 */
	public void setAppStat(String appStat) {
		this.appStat = appStat;
	}

	/**
	 * @return the sellerCompany
	 */
	public String getSellerCompany() {
		return this.sellerCompany;
	}

	/**
	 * @param sellerCompany
	 *            the sellerCompany to set
	 */
	public void setSellerCompany(String sellerCompany) {
		this.sellerCompany = sellerCompany;
	}

	/**
	 * @return the sellerName
	 */
	public String getSellerName() {
		return this.sellerName;
	}

	/**
	 * @param sellerName
	 *            the sellerName to set
	 */
	public void setSellerName(String sellerName) {
		this.sellerName = sellerName;
	}

	/**
	 * @return the sellerEmail
	 */
	public String getSellerEmail() {
		return this.sellerEmail;
	}

	/**
	 * @param sellerEmail
	 *            the sellerEmail to set
	 */
	public void setSellerEmail(String sellerEmail) {
		this.sellerEmail = sellerEmail;
	}

	/**
	 * @return the bizRegNumber
	 */
	public String getBizRegNumber() {
		return this.bizRegNumber;
	}

	/**
	 * @param bizRegNumber
	 *            the bizRegNumber to set
	 */
	public void setBizRegNumber(String bizRegNumber) {
		this.bizRegNumber = bizRegNumber;
	}

	/**
	 * @return the sellerAddress
	 */
	public String getSellerAddress() {
		return this.sellerAddress;
	}

	/**
	 * @param sellerAddress
	 *            the sellerAddress to set
	 */
	public void setSellerAddress(String sellerAddress) {
		this.sellerAddress = sellerAddress;
	}

	/**
	 * @return the sellerPhone
	 */
	public String getSellerPhone() {
		return this.sellerPhone;
	}

	/**
	 * @param sellerPhone
	 *            the sellerPhone to set
	 */
	public void setSellerPhone(String sellerPhone) {
		this.sellerPhone = sellerPhone;
	}

}
