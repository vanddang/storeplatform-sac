package com.skplanet.storeplatform.sac.member.common.vo;

import com.skplanet.storeplatform.framework.core.common.vo.CommonInfo;

/**
 * TB_CM_CLAUSE TB_CM_TENANT_CLAUSE
 * 
 * aid로 sellerKey 가져오기
 * 
 * Updated on : 2014. 1. 8. Updated by : 한서구, 부르칸.
 */
public class SellerDTO extends CommonInfo {

	private static final long serialVersionUID = 1L;

	private String sellerKey;

	public String getSellerKey() {
		return this.sellerKey;
	}

	public void setSellerKey(String sellerKey) {
		this.sellerKey = sellerKey;
	}

}
