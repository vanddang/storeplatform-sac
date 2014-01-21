package com.skplanet.storeplatform.sac.client.member.vo.seller;

import com.skplanet.storeplatform.framework.core.common.vo.CommonInfo;

/**
 * 판매자 회원 인증키 폐기 Res
 * 
 * Updated on : 2014. 1. 21. Updated by : 김경복, 부르칸.
 */
public class AbrogationAuthKeyRes extends CommonInfo {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4626339064209250099L;

	/** 판매자 회원키. */
	private String sellerKey;

	public String getSellerKey() {
		return this.sellerKey;
	}

	public void setSellerKey(String sellerKey) {
		this.sellerKey = sellerKey;
	}

}
