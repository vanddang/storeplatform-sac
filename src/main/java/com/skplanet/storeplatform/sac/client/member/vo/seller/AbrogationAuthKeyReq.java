package com.skplanet.storeplatform.sac.client.member.vo.seller;

import org.hibernate.validator.constraints.NotBlank;

import com.skplanet.storeplatform.framework.core.common.vo.CommonInfo;

/**
 * 판매자 회원 인증키 폐기 Req
 * 
 * Updated on : 2014. 1. 21. Updated by : 김경복, 부르칸.
 */
public class AbrogationAuthKeyReq extends CommonInfo {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4577513919241061044L;

	/** 판매자 회원키. */
	@NotBlank
	private String sellerKey;

	public String getSellerKey() {
		return this.sellerKey;
	}

	public void setSellerKey(String sellerKey) {
		this.sellerKey = sellerKey;
	}

}
