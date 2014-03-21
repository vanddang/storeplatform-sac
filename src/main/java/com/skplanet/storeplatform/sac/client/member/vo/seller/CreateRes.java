package com.skplanet.storeplatform.sac.client.member.vo.seller;

import org.codehaus.jackson.map.annotate.JsonSerialize;
import org.codehaus.jackson.map.annotate.JsonSerialize.Inclusion;

import com.skplanet.storeplatform.framework.core.common.vo.CommonInfo;
import com.skplanet.storeplatform.sac.client.member.vo.common.SellerMbrSac;

/**
 * 2.2.1. 판매자 회원 가입 [RESPONSE]
 * 
 * Updated on : 2014. 1. 7. Updated by : 한서구, 부르칸.
 */
@JsonSerialize(include = Inclusion.NON_NULL)
public class CreateRes extends CommonInfo {

	private static final long serialVersionUID = 1L;

	/** 판매자 회원 정보. */
	private SellerMbrSac sellerMbr;

	/**
	 * @return the sellerMbr
	 */
	public SellerMbrSac getSellerMbr() {
		return this.sellerMbr;
	}

	/**
	 * @param sellerMbr
	 *            the sellerMbr to set
	 */
	public void setSellerMbr(SellerMbrSac sellerMbr) {
		this.sellerMbr = sellerMbr;
	}

}
