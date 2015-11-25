package com.skplanet.storeplatform.sac.client.member.vo.seller;

import com.skplanet.storeplatform.framework.core.common.vo.CommonInfo;

/**
 * 판매자 회원 인증키 생성/연장 Req
 * 
 * Updated on : 2014. 1. 21. Updated by : 김경복, 부르칸.
 */
public class CreateAuthKeyReq extends CommonInfo {

	private static final long serialVersionUID = -4017679478547899647L;

	/** 판매자 키. */
	private String sellerKey;
	/** 만료일시. */
	private String expireDate;
	/** IP 주송. */
	private String ipAddress;

	/**
	 * @return the sellerKey
	 */
	public String getSellerKey() {
		return this.sellerKey;
	}

	/**
	 * @param sellerKey
	 *            the sellerKey to set
	 */
	public void setSellerKey(String sellerKey) {
		this.sellerKey = sellerKey;
	}

	/**
	 * @return the expireDate
	 */
	public String getExpireDate() {
		return this.expireDate;
	}

	/**
	 * @param expireDate
	 *            the expireDate to set
	 */
	public void setExpireDate(String expireDate) {
		this.expireDate = expireDate;
	}

	/**
	 * @return the ipAddress
	 */
	public String getIpAddress() {
		return this.ipAddress;
	}

	/**
	 * @param ipAddress
	 *            the ipAddress to set
	 */
	public void setIpAddress(String ipAddress) {
		this.ipAddress = ipAddress;
	}
}
