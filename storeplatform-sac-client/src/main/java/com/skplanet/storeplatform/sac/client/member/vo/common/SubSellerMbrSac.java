package com.skplanet.storeplatform.sac.client.member.vo.common;

import org.codehaus.jackson.map.annotate.JsonSerialize;
import org.codehaus.jackson.map.annotate.JsonSerialize.Inclusion;

import com.skplanet.storeplatform.framework.core.common.vo.CommonInfo;

/**
 * 서브 계정 정보.
 * 
 * Updated on : 2014. 3. 25. Updated by : Rejoice, Burkhan
 */
@JsonSerialize(include = Inclusion.NON_NULL)
public class SubSellerMbrSac extends CommonInfo {
	private static final long serialVersionUID = 1L;

	/** 서브계정 Key . */
	private String subSellerKey;
	/** 서브계정 ID . */
	private String subSellerId;
	/** 서브계정 이메일 . */
	private String subSellerEmail;
	/** 서브계정 연락처 국가코드 . */
	private String subSellerPhoneCountry;
	/** 서브계정 연락처 . */
	private String subSellerPhone;
	/** 서브계정 권한리스트 . */
	private String subSellerCateList;
	/** 로그인 시간 . */
	private String subSellerLoginDttm;
	/** 서브계정 메모. */
	private String subSellerMemo;
	/** 서브계정 등록일시. */
	private String subRegDate;

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
	 * @return the subSellerId
	 */
	public String getSubSellerId() {
		return this.subSellerId;
	}

	/**
	 * @param subSellerId
	 *            the subSellerId to set
	 */
	public void setSubSellerId(String subSellerId) {
		this.subSellerId = subSellerId;
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
	 * @return the subSellerLoginDttm
	 */
	public String getSubSellerLoginDttm() {
		return this.subSellerLoginDttm;
	}

	/**
	 * @param subSellerLoginDttm
	 *            the subSellerLoginDttm to set
	 */
	public void setSubSellerLoginDttm(String subSellerLoginDttm) {
		this.subSellerLoginDttm = subSellerLoginDttm;
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
	 * @return the subRegDate
	 */
	public String getSubRegDate() {
		return this.subRegDate;
	}

	/**
	 * @param subRegDate
	 *            the subRegDate to set
	 */
	public void setSubRegDate(String subRegDate) {
		this.subRegDate = subRegDate;
	}

}
