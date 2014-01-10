/**
 * 
 */
package com.skplanet.storeplatform.sac.client.member.vo.seller;

import com.skplanet.storeplatform.framework.core.common.vo.CommonInfo;

/**
 * Calss 설명
 * 
 * Updated on : 2014. 1. 9. Updated by : 김경복, 부르칸.
 */
public class AuthorizeReq extends CommonInfo {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/** 판매자 회원 ID */
	private String sellerId;
	/** 판매자 회원 PW */
	private String sellerPW;
	/** 계정 잠금 해제 요청 ('Y'/'N') */
	private String releaseLock;

	public String getSellerId() {
		return this.sellerId;
	}

	public void setSellerId(String sellerId) {
		this.sellerId = sellerId;
	}

	public String getSellerPW() {
		return this.sellerPW;
	}

	public void setSellerPW(String sellerPW) {
		this.sellerPW = sellerPW;
	}

	public String getReleaseLock() {
		return this.releaseLock;
	}

	public void setReleaseLock(String releaseLock) {
		this.releaseLock = releaseLock;
	}
}
