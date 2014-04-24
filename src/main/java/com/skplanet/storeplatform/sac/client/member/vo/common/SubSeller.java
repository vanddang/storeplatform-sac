package com.skplanet.storeplatform.sac.client.member.vo.common;

import java.util.List;

import org.codehaus.jackson.map.annotate.JsonSerialize;
import org.codehaus.jackson.map.annotate.JsonSerialize.Inclusion;

import com.skplanet.storeplatform.framework.core.common.vo.CommonInfo;

/**
 * 서브계정 목록 조회 서브계정 정보 리스트
 * 
 * Updated on : 2014. 1. 7. Updated by : 한서구, 부르칸.
 */
@JsonSerialize(include = Inclusion.NON_NULL)
public class SubSeller extends CommonInfo {

	private static final long serialVersionUID = 1L;

	private String sellerKey;
	private String sellerId;
	private String sellerPw;
	private String sellerEmail;
	private String sellerPhoneCountry;
	private String sellerPhone;
	private String sellerMemo;
	private String lastLoginDttm;
	private List<SellerRight> sellerRightList;

	/**
	 * @return the sellerKey
	 */
	public String getSellerKey() {
		return this.sellerKey;
	}

	/**
	 * @param sellerKey
	 *            the sellerKey to set
	 */
	public void setSellerKey(String sellerKey) {
		this.sellerKey = sellerKey;
	}

	/**
	 * @return the sellerId
	 */
	public String getSellerId() {
		return this.sellerId;
	}

	/**
	 * @param sellerId
	 *            the sellerId to set
	 */
	public void setSellerId(String sellerId) {
		this.sellerId = sellerId;
	}

	/**
	 * @return the sellerPw
	 */
	public String getSellerPw() {
		return this.sellerPw;
	}

	/**
	 * @param sellerPw
	 *            the sellerPw to set
	 */
	public void setSellerPw(String sellerPw) {
		this.sellerPw = sellerPw;
	}

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
	 * @return the sellerPhoneCountry
	 */
	public String getSellerPhoneCountry() {
		return this.sellerPhoneCountry;
	}

	/**
	 * @param sellerPhoneCountry
	 *            the sellerPhoneCountry to set
	 */
	public void setSellerPhoneCountry(String sellerPhoneCountry) {
		this.sellerPhoneCountry = sellerPhoneCountry;
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

	/**
	 * @return the sellerMemo
	 */
	public String getSellerMemo() {
		return this.sellerMemo;
	}

	/**
	 * @param sellerMemo
	 *            the sellerMemo to set
	 */
	public void setSellerMemo(String sellerMemo) {
		this.sellerMemo = sellerMemo;
	}

	/**
	 * @return the lastLoginDttm
	 */
	public String getLastLoginDttm() {
		return this.lastLoginDttm;
	}

	/**
	 * @param lastLoginDttm
	 *            the lastLoginDttm to set
	 */
	public void setLastLoginDttm(String lastLoginDttm) {
		this.lastLoginDttm = lastLoginDttm;
	}

	/**
	 * @return the sellerRightList
	 */
	public List<SellerRight> getSellerRightList() {
		return this.sellerRightList;
	}

	/**
	 * @param sellerRightList
	 *            the sellerRightList to set
	 */
	public void setSellerRightList(List<SellerRight> sellerRightList) {
		this.sellerRightList = sellerRightList;
	}

}
