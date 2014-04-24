package com.skplanet.storeplatform.sac.client.member.vo.seller;

import com.skplanet.storeplatform.framework.core.common.vo.CommonInfo;
import com.skplanet.storeplatform.sac.client.member.vo.common.SellerMbrSac;

/**
 * 2.2.35. 판매자회원 전환가입 [RESPONSE]
 * 
 * Updated on : 2014. 4. 23. Updated by : Rejoice, Burkhan
 */
public class CreateChangeSacRes extends CommonInfo {

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
