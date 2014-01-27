package com.skplanet.storeplatform.sac.client.member.vo.seller;

import org.codehaus.jackson.map.annotate.JsonSerialize;
import org.codehaus.jackson.map.annotate.JsonSerialize.Inclusion;

import com.skplanet.storeplatform.framework.core.common.vo.CommonInfo;

/**
 * 2.2.16. 판매자 계정 잠금 [RESPONSE]
 * 
 * Updated on : 2014. 1. 7. Updated by : 한서구, 부르칸.
 */
@JsonSerialize(include = Inclusion.NON_NULL)
public class LockAccountRes extends CommonInfo {

	private static final long serialVersionUID = 1L;

	public LockAccountRes() {
		super();
	}

	public LockAccountRes(String sellerId) {
		super();
		this.sellerId = sellerId;
	}

	/** 판매자 회원 ID. */
	private String sellerId;

	public String getSellerId() {
		return this.sellerId;
	}

	public void setSellerId(String sellerId) {
		this.sellerId = sellerId;
	}

}
