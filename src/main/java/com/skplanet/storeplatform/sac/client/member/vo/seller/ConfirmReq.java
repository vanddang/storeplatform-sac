package com.skplanet.storeplatform.sac.client.member.vo.seller;

import org.hibernate.validator.constraints.NotBlank;

import com.skplanet.storeplatform.framework.core.common.vo.CommonInfo;

/**
 * 5.2.14. 판매자 회원 계정 승인 [REQUEST]
 * 
 * Updated on : 2014. 1. 24. Updated by : 김경복, 부르칸
 */
public class ConfirmReq extends CommonInfo {

	private static final long serialVersionUID = 1L;

	/** 판매자 Key. */
	@NotBlank
	private String sellerKey;

	public String getSellerKey() {
		return this.sellerKey;
	}

	public void setSellerKey(String sellerKey) {
		this.sellerKey = sellerKey;
	}
}
