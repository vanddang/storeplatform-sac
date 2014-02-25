package com.skplanet.storeplatform.sac.client.member.vo.seller;

import org.codehaus.jackson.map.annotate.JsonSerialize;
import org.codehaus.jackson.map.annotate.JsonSerialize.Inclusion;

import com.skplanet.storeplatform.framework.core.common.vo.CommonInfo;
import com.skplanet.storeplatform.sac.client.member.vo.common.SellerMbr;

/**
 * 2.2.3 판매자회원 인증 [RESPONSE]
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
	/** 로그인 성공 여부. */
	private String isLoginSuccess;
	/** 인증키. */
	private String sessionKey;
	/** 만료일시. */
	private String expireDate;
	/** 서브계정여부. */
	private String isSubSeller;
	/** 로그인 상태. */
	private String loginStatusCode;
	/** Sub Seller Key. */
	private String subSellerKey;

	/**
	 * @return the sellerMbr
	 */
	public SellerMbr getSellerMbr() {
		return this.sellerMbr;
	}

	/**
	 * @param sellerMbr
	 *            the sellerMbr to set
	 */
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

	/**
	 * @return the isLoginSuccess
	 */
	public String getIsLoginSuccess() {
		return this.isLoginSuccess;
	}

	/**
	 * @param isLoginSuccess
	 *            the isLoginSuccess to set
	 */
	public void setIsLoginSuccess(String isLoginSuccess) {
		this.isLoginSuccess = isLoginSuccess;
	}

	/**
	 * @return the sessionKey
	 */
	public String getSessionKey() {
		return this.sessionKey;
	}

	/**
	 * @param sessionKey
	 *            the sessionKey to set
	 */
	public void setSessionKey(String sessionKey) {
		this.sessionKey = sessionKey;
	}

	/**
	 * @return the expireDate
	 */
	public String getExpireDate() {
		return this.expireDate;
	}

	/**
	 * @param expireDate
	 *            the expireDate to set
	 */
	public void setExpireDate(String expireDate) {
		this.expireDate = expireDate;
	}

	/**
	 * @return the isSubSeller
	 */
	public String getIsSubSeller() {
		return this.isSubSeller;
	}

	/**
	 * @param isSubSeller
	 *            the isSubSeller to set
	 */
	public void setIsSubSeller(String isSubSeller) {
		this.isSubSeller = isSubSeller;
	}

	/**
	 * @return the loginStatusCode
	 */
	public String getLoginStatusCode() {
		return this.loginStatusCode;
	}

	/**
	 * @param loginStatusCode
	 *            the loginStatusCode to set
	 */
	public void setLoginStatusCode(String loginStatusCode) {
		this.loginStatusCode = loginStatusCode;
	}

	/**
	 * @return the subSellerKey
	 */
	public String getSubSellerKey() {
		return this.subSellerKey;
	}

	/**
	 * @param subSellerKey
	 *            the subSellerKey to set
	 */
	public void setSubSellerKey(String subSellerKey) {
		this.subSellerKey = subSellerKey;
	}

}
