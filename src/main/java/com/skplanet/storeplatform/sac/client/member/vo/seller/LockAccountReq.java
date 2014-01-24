package com.skplanet.storeplatform.sac.client.member.vo.seller;

import org.codehaus.jackson.map.annotate.JsonSerialize;
import org.codehaus.jackson.map.annotate.JsonSerialize.Inclusion;
import org.hibernate.validator.constraints.NotBlank;

import com.skplanet.storeplatform.framework.core.common.vo.CommonInfo;

/**
 * 5.2.16. 판매자 계정 잠금 [REQUEST]
 * 
 * Updated on : 2014. 1. 23. Updated by : 김경복, 부르칸.
 */
@JsonSerialize(include = Inclusion.NON_NULL)
public class LockAccountReq extends CommonInfo {

	private static final long serialVersionUID = 1L;

	/** 판매자 회원 ID. */
	@NotBlank
	private String sellerId;

	public String getSellerId() {
		return this.sellerId;
	}

	public void setSellerId(String sellerId) {
		this.sellerId = sellerId;
	}

}
