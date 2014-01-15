package com.skplanet.storeplatform.sac.client.member.vo.seller;

import org.codehaus.jackson.map.annotate.JsonSerialize;
import org.codehaus.jackson.map.annotate.JsonSerialize.Inclusion;

import com.skplanet.storeplatform.framework.core.common.vo.CommonInfo;
import com.skplanet.storeplatform.sac.client.member.vo.common.SellerMbr;

/**
 * 판매자회원 인증
 * 
 * Updated on : 2014. 1. 7. Updated by : 한서구, 부르칸.
 */
@JsonSerialize(include = Inclusion.NON_NULL)
public class AuthorizeRes extends CommonInfo {

	private static final long serialVersionUID = 1L;
	/** 판매자 회원 정보. */
	private SellerMbr sellerMbr;
	/** 로그인 실패 횟수 . */
	private String loginFailCount;

	public SellerMbr getSellerMbr() {
		return this.sellerMbr;
	}

	public void setSellerMbr(SellerMbr sellerMbr) {
		this.sellerMbr = sellerMbr;
	}

	/**
	 * @return the loginFailCount
	 */
	public String getLoginFailCount() {
		return this.loginFailCount;
	}

	/**
	 * @param loginFailCount
	 *            the loginFailCount to set
	 */
	public void setLoginFailCount(String loginFailCount) {
		this.loginFailCount = loginFailCount;
	}
}
