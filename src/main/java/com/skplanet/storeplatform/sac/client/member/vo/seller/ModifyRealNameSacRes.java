package com.skplanet.storeplatform.sac.client.member.vo.seller;

import org.codehaus.jackson.map.annotate.JsonSerialize;
import org.codehaus.jackson.map.annotate.JsonSerialize.Inclusion;

import com.skplanet.storeplatform.framework.core.common.vo.CommonInfo;

/**
 * 2.2.17. 판매자회원 실명 인증 정보 수정 [RESPONSE]
 * 
 * Updated on : 2014. 2. 6. Updated by : 김경복, 부르칸
 */
@JsonSerialize(include = Inclusion.NON_NULL)
public class ModifyRealNameSacRes extends CommonInfo {

	private static final long serialVersionUID = 1L;

	/** 판매자 회원 Key. */
	private String sellerKey;

	public String getSellerKey() {
		return this.sellerKey;
	}

	public void setSellerKey(String sellerKey) {
		this.sellerKey = sellerKey;
	}

}
