package com.skplanet.storeplatform.sac.client.member.vo.seller;

import com.skplanet.storeplatform.framework.core.common.vo.CommonInfo;

public class LockAccountReq extends CommonInfo {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/** 판매자 회원 ID */
	private String sellerId;

	public String getSellerId() {
		return this.sellerId;
	}

	public void setSellerId(String sellerId) {
		this.sellerId = sellerId;
	}

}
