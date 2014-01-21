package com.skplanet.storeplatform.sac.client.member.vo.seller;

import com.skplanet.storeplatform.framework.core.common.vo.CommonInfo;

/**
 * 판매자 회원 인증키 생성/연장 Req
 * 
 * Updated on : 2014. 1. 21. Updated by : 김경복, 부르칸.
 */
public class CreateAuthKeyReq extends CommonInfo {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4017679478547899647L;

	/** 판매자 키. */
	private String sellerKey;
	/** 만료일시. */
	private String expireDate;
	/** IP 주송. */
	private String ipAddress;

	public String getSellerKey() {
		return this.sellerKey;
	}

	public void setSellerKey(String sellerKey) {
		this.sellerKey = sellerKey;
	}

	public String getExpireDate() {
		return this.expireDate;
	}

	public void setExpireDate(String expireDate) {
		this.expireDate = expireDate;
	}

	public String getIpAddress() {
		return this.ipAddress;
	}

	public void setIpAddress(String ipAddress) {
		this.ipAddress = ipAddress;
	}

}
