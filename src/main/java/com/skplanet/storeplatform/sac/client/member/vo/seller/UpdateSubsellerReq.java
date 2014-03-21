package com.skplanet.storeplatform.sac.client.member.vo.seller;

import org.hibernate.validator.constraints.NotBlank;

import com.skplanet.storeplatform.framework.core.common.vo.CommonInfo;

/**
 * [REQUEST]서브계정 수정.
 * 
 * Updated on : 2014. 3. 20. Updated by : 김다슬, 인크로스.
 */
public class UpdateSubsellerReq extends CommonInfo {

	private static final long serialVersionUID = 1L;

	/** 판매자 키. */
	@NotBlank
	private String sellerKey;

	/** 서브계정 키. */
	@NotBlank
	private String subSellerKey;

	/** 서브계정 비밀번호. */
	private String subSellerPW;

	/** 서브계정 메모. */
	private String subSellerMemo;

	/** 서브계정 권한리스트. */
	private String subSellerCateList;

	/** 서브계정 Email. */
	private String subSellerEmail;

	/**
	 * 서브계정 무선 국가 번호 (WILS_NATION_NO).
	 */
	private String subSellerPhoneCountry;

	/**
	 * 서브계정 무선 전화번호 (WILS_TEL_NO).
	 */
	private String subSellerPhone;

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

	/**
	 * @return the subSellerPW
	 */
	public String getSubSellerPW() {
		return this.subSellerPW;
	}

	/**
	 * @param subSellerPW
	 *            the subSellerPW to set
	 */
	public void setSubSellerPW(String subSellerPW) {
		this.subSellerPW = subSellerPW;
	}

	/**
	 * @return the subSellerMemo
	 */
	public String getSubSellerMemo() {
		return this.subSellerMemo;
	}

	/**
	 * @param subSellerMemo
	 *            the subSellerMemo to set
	 */
	public void setSubSellerMemo(String subSellerMemo) {
		this.subSellerMemo = subSellerMemo;
	}

	/**
	 * @return the subSellerCateList
	 */
	public String getSubSellerCateList() {
		return this.subSellerCateList;
	}

	/**
	 * @param subSellerCateList
	 *            the subSellerCateList to set
	 */
	public void setSubSellerCateList(String subSellerCateList) {
		this.subSellerCateList = subSellerCateList;
	}

	/**
	 * @return the subSellerEmail
	 */
	public String getSubSellerEmail() {
		return this.subSellerEmail;
	}

	/**
	 * @param subSellerEmail
	 *            the subSellerEmail to set
	 */
	public void setSubSellerEmail(String subSellerEmail) {
		this.subSellerEmail = subSellerEmail;
	}

	/**
	 * @return the subSellerPhoneCountry
	 */
	public String getSubSellerPhoneCountry() {
		return this.subSellerPhoneCountry;
	}

	/**
	 * @param subSellerPhoneCountry
	 *            the subSellerPhoneCountry to set
	 */
	public void setSubSellerPhoneCountry(String subSellerPhoneCountry) {
		this.subSellerPhoneCountry = subSellerPhoneCountry;
	}

	/**
	 * @return the subSellerPhone
	 */
	public String getSubSellerPhone() {
		return this.subSellerPhone;
	}

	/**
	 * @param subSellerPhone
	 *            the subSellerPhone to set
	 */
	public void setSubSellerPhone(String subSellerPhone) {
		this.subSellerPhone = subSellerPhone;
	}

}
