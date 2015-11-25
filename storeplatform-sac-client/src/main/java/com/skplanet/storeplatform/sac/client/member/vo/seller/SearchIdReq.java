package com.skplanet.storeplatform.sac.client.member.vo.seller;

import org.codehaus.jackson.map.annotate.JsonSerialize;
import org.codehaus.jackson.map.annotate.JsonSerialize.Inclusion;

import com.skplanet.storeplatform.framework.core.common.vo.CommonInfo;

/**
 * 판매자회원 ID 찾기
 * 
 * Updated on : 2014. 1. 7. Updated by : 한서구, 부르칸.
 */
@JsonSerialize(include = Inclusion.NON_NULL)
public class SearchIdReq extends CommonInfo {

	private static final long serialVersionUID = 1L;

	/** 판매자 이메일 . */
	private String sellerEmail;
	/** 회사명 . */
	private String sellerCompany;
	/** 사업자 등록번호. */
	private String sellerBizNumber;
	/** 전화번호 . */
	private String sellerPhone;

	/**
	 * @return the sellerEmail
	 */
	public String getSellerEmail() {
		return this.sellerEmail;
	}

	/**
	 * @param sellerEmail
	 *            the sellerEmail to set
	 */
	public void setSellerEmail(String sellerEmail) {
		this.sellerEmail = sellerEmail;
	}

	/**
	 * @return the sellerCompany
	 */
	public String getSellerCompany() {
		return this.sellerCompany;
	}

	/**
	 * @param sellerCompany
	 *            the sellerCompany to set
	 */
	public void setSellerCompany(String sellerCompany) {
		this.sellerCompany = sellerCompany;
	}

	/**
	 * @return the sellerBizNumber
	 */
	public String getSellerBizNumber() {
		return this.sellerBizNumber;
	}

	/**
	 * @param sellerBizNumber
	 *            the sellerBizNumber to set
	 */
	public void setSellerBizNumber(String sellerBizNumber) {
		this.sellerBizNumber = sellerBizNumber;
	}

	/**
	 * @return the sellerPhone
	 */
	public String getSellerPhone() {
		return this.sellerPhone;
	}

	/**
	 * @param sellerPhone
	 *            the sellerPhone to set
	 */
	public void setSellerPhone(String sellerPhone) {
		this.sellerPhone = sellerPhone;
	}

}
