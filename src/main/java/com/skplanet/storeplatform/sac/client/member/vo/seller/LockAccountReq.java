package com.skplanet.storeplatform.sac.client.member.vo.seller;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotBlank;

import com.skplanet.storeplatform.framework.core.common.vo.CommonInfo;

public class LockAccountReq extends CommonInfo {

	private static final long serialVersionUID = 1L;

	/** 판매자 회원 ID */
	@NotNull(message = "sellerId should not be null")
	@NotBlank(message = "sellerId should not be empty")
	private String sellerId;

	public String getSellerId() {
		return this.sellerId;
	}

	public void setSellerId(String sellerId) {
		this.sellerId = sellerId;
	}

}
