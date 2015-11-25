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

/**
 * 로그인 정보 Value Object
 * 
 * Updated on : 2014. 01. 17. Updated by : wisestone_mikepark
 */
public class LoginInfo extends CommonInfo implements Serializable {
	/** The Constant serial Version UID. */
	private static final long serialVersionUID = 1L;

	/** 판매자 키. */
	private String sellerKey; // INSD_SELLERMBR_NO

	/** IP 주소. */
	private String ipAddress; // IP_ADDR

	/** Session Key. */
	private String sessionKey; // SESSION_KEY

	/** 등록일시. */
	private String regDate; // REG_DT

	/** 만료일시. ex ( YYYYMMDDHH24MISS ) */
	private String expireDate; // EXPR_DT

	/** 서브계정 여부 2014-09-17(추가). */
	private String isSubSeller;

	/** 부모 판매자키 2014-09-17(추가). */
	private String parentSellerKey;

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
	 * IP 주소를 리턴한다.
	 * 
	 * @return ipAddress - IP 주소
	 */
	public String getIpAddress() {
		return this.ipAddress;
	}

	/**
	 * IP 주소를 설정한다.
	 * 
	 * @param ipAddress
	 *            IP 주소
	 */
	public void setIpAddress(String ipAddress) {
		this.ipAddress = ipAddress;
	}

	/**
	 * Session Key를 리턴한다.
	 * 
	 * @return sessionKey - Session Key
	 */
	public String getSessionKey() {
		return this.sessionKey;
	}

	/**
	 * Session Key를 설정한다.
	 * 
	 * @param sessionKey
	 *            Session Key
	 */
	public void setSessionKey(String sessionKey) {
		this.sessionKey = sessionKey;
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
	 * 만료일시를 리턴한다.
	 * 
	 * @return expireDate - 만료일시
	 */
	public String getExpireDate() {
		return this.expireDate;
	}

	/**
	 * 만료일시를 설정한다.
	 * 
	 * @param expireDate
	 *            만료일시
	 */
	public void setExpireDate(String expireDate) {
		this.expireDate = expireDate;
	}

	/**
	 * 서브계정여부를 리턴한다.
	 * 
	 * @return the isSubSeller 서브계정여부
	 */
	public String getIsSubSeller() {
		return this.isSubSeller;
	}

	/**
	 * 서브계정여부를 설정한다.
	 * 
	 * @param isSubSeller
	 *            the isSubSeller to set 서브계정여부
	 */
	public void setIsSubSeller(String isSubSeller) {
		this.isSubSeller = isSubSeller;
	}

	/**
	 * 부모판매자키를 리턴한다.
	 * 
	 * @return the parentSellerKey 부모판매자키
	 */
	public String getParentSellerKey() {
		return this.parentSellerKey;
	}

	/**
	 * 부모판매자키를 설정한다.
	 * 
	 * @param parentSellerKey
	 *            the upSellerKey to set 부모판매자키
	 */
	public void setParentSellerKey(String parentSellerKey) {
		this.parentSellerKey = parentSellerKey;
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
