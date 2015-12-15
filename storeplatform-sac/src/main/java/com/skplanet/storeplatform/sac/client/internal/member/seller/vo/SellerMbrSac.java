package com.skplanet.storeplatform.sac.client.internal.member.seller.vo;

import org.codehaus.jackson.map.annotate.JsonSerialize;
import org.codehaus.jackson.map.annotate.JsonSerialize.Inclusion;

import com.skplanet.storeplatform.framework.core.common.vo.CommonInfo;

/**
 * 판매자 정보 Value
 * 
 * Updated on : 2015. 12. 10. Updated by : 최진호, 보고지티.
 */
@JsonSerialize(include = Inclusion.NON_NULL)
public class SellerMbrSac extends CommonInfo {
	private static final long serialVersionUID = 1L;

	/** 판매자 Key. */
	private String sellerKey;
	/** 판매자 ID. */
	private String sellerId;
	/** 판매자 구분 코드. */
	private String sellerClass;
	/** 담당자명. */
	private String charger;
	/** 회사명. */
	private String sellerCompany;
	/** 쇼핑 노출명. */
	private String sellerNickName;
	/** 사업자 등록번호. */
	private String sellerBizNumber;
	/** 판매자명. */
	private String sellerName;
	/** 대표 전화번호. */
	private String repPhone;
	/** 판매자 이메일. */
	private String sellerEmail;
	/** 판매자 주소. */
	private String sellerAddress;
	/** 판매자 상세 주소. */
	private String sellerDetailAddress;
	/** 통신판매업 신고번호. */
	private String bizRegNumber;
	/** 상품 카테고리 코드. */
	private String categoryCd;
	/** 판매 제공자 여부. */
	private String providerYn;
	/** 판매 제공자 Key. */
	private String providerKey;
	/** 제공자 ID. */
	private String providerId;
	/** 제공자 구분 코드. */
	private String providerClass;
	/** 제공자 담당자명. */
	private String providerCharger;
	/** 제공자 회사명. */
	private String providerCompany;
	/** 제공자 쇼핑 노출명. */
	private String providerNickName;
	/** 제공자 통신판매업 신고번호. */
	private String providerBizRegNumber;
	/** 제공자명. */
	private String providerName;
	/** 제공자 대표전화번호. */
	private String providerRepPhone;
	/** 제공자 이메일. */
	private String providerEmail;
	/** 제공자 주소. */
	private String providerAddress;
	/** 제공자 상세주소. */
	private String providerDetailAddress;
	/** 제공자 사업자 등록번호. */
	private String providerBizNumber;

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
	 * @return the sellerClass
	 */
	public String getSellerClass() {
		return this.sellerClass;
	}

	/**
	 * @param sellerClass
	 *            the sellerClass to set
	 */
	public void setSellerClass(String sellerClass) {
		this.sellerClass = sellerClass;
	}

	/**
	 * @return the charger
	 */
	public String getCharger() {
		return this.charger;
	}

	/**
	 * @param charger
	 *            the charger to set
	 */
	public void setCharger(String charger) {
		this.charger = charger;
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
	 * @return the sellerNickName
	 */
	public String getSellerNickName() {
		return this.sellerNickName;
	}

	/**
	 * @param sellerNickName
	 *            the sellerNickName to set
	 */
	public void setSellerNickName(String sellerNickName) {
		this.sellerNickName = sellerNickName;
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
	 * @return the sellerName
	 */
	public String getSellerName() {
		return this.sellerName;
	}

	/**
	 * @param sellerName
	 *            the sellerName to set
	 */
	public void setSellerName(String sellerName) {
		this.sellerName = sellerName;
	}

	/**
	 * @return the repPhone
	 */
	public String getRepPhone() {
		return this.repPhone;
	}

	/**
	 * @param repPhone
	 *            the repPhone to set
	 */
	public void setRepPhone(String repPhone) {
		this.repPhone = repPhone;
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
	 * @return the sellerAddress
	 */
	public String getSellerAddress() {
		return this.sellerAddress;
	}

	/**
	 * @param sellerAddress
	 *            the sellerAddress to set
	 */
	public void setSellerAddress(String sellerAddress) {
		this.sellerAddress = sellerAddress;
	}

	/**
	 * @return the sellerDetailAddress
	 */
	public String getSellerDetailAddress() {
		return this.sellerDetailAddress;
	}

	/**
	 * @param sellerDetailAddress
	 *            the sellerDetailAddress to set
	 */
	public void setSellerDetailAddress(String sellerDetailAddress) {
		this.sellerDetailAddress = sellerDetailAddress;
	}

	/**
	 * @return the bizRegNumber
	 */
	public String getBizRegNumber() {
		return this.bizRegNumber;
	}

	/**
	 * @param bizRegNumber
	 *            the bizRegNumber to set
	 */
	public void setBizRegNumber(String bizRegNumber) {
		this.bizRegNumber = bizRegNumber;
	}

	/**
	 * 상품 카테고리 코드 을(를) 리턴한다.
	 * 
	 * @return categoryCd -
	 */
	public String getCategoryCd() {
		return this.categoryCd;
	}

	/**
	 * 상품 카테고리 코드 을(를) 셋팅한다.
	 * 
	 * @param categoryCd
	 *            categoryCd
	 */
	public void setCategoryCd(String categoryCd) {
		this.categoryCd = categoryCd;
	}

	/**
	 * 판매 제공자 여부 을(를) 리턴한다.
	 * 
	 * @return providerYn -
	 */
	public String getProviderYn() {
		return this.providerYn;
	}

	/**
	 * 판매 제공자 여부 을(를) 셋팅한다.
	 * 
	 * @param providerYn
	 *            providerYn
	 */
	public void setProviderYn(String providerYn) {
		this.providerYn = providerYn;
	}

	/**
	 * 판매 제공자 Key 을(를) 리턴한다.
	 * 
	 * @return providerKey -
	 */
	public String getProviderKey() {
		return this.providerKey;
	}

	/**
	 * 판매 제공자 Key 을(를) 셋팅한다.
	 * 
	 * @param providerKey
	 *            providerKey
	 */
	public void setProviderKey(String providerKey) {
		this.providerKey = providerKey;
	}

	/**
	 * 제공자 ID 을(를) 리턴한다.
	 * 
	 * @return providerId -
	 */
	public String getProviderId() {
		return this.providerId;
	}

	/**
	 * 제공자 ID 을(를) 셋팅한다.
	 * 
	 * @param providerId
	 *            providerId
	 */
	public void setProviderId(String providerId) {
		this.providerId = providerId;
	}

	/**
	 * 제공자 구분 코드 을(를) 리턴한다.
	 * 
	 * @return providerClass -
	 */
	public String getProviderClass() {
		return this.providerClass;
	}

	/**
	 * 제공자 구분 코드 을(를) 셋팅한다.
	 * 
	 * @param providerClass
	 *            providerClass
	 */
	public void setProviderClass(String providerClass) {
		this.providerClass = providerClass;
	}

	/**
	 * 제공자 담당자명 을(를) 리턴한다.
	 * 
	 * @return providerCharger -
	 */
	public String getProviderCharger() {
		return this.providerCharger;
	}

	/**
	 * 제공자 담당자명 을(를) 셋팅한다.
	 * 
	 * @param providerCharger
	 *            providerCharger
	 */
	public void setProviderCharger(String providerCharger) {
		this.providerCharger = providerCharger;
	}

	/**
	 * 제공자 회사명 을(를) 리턴한다.
	 * 
	 * @return providerCompany -
	 */
	public String getProviderCompany() {
		return this.providerCompany;
	}

	/**
	 * 제공자 회사명 을(를) 셋팅한다.
	 * 
	 * @param providerCompany
	 *            providerCompany
	 */
	public void setProviderCompany(String providerCompany) {
		this.providerCompany = providerCompany;
	}

	/**
	 * 제공자 쇼핑 노출명 을(를) 리턴한다.
	 * 
	 * @return providerNickName -
	 */
	public String getProviderNickName() {
		return this.providerNickName;
	}

	/**
	 * 제공자 쇼핑 노출명 을(를) 셋팅한다.
	 * 
	 * @param providerNickName
	 *            providerNickName
	 */
	public void setProviderNickName(String providerNickName) {
		this.providerNickName = providerNickName;
	}

	/**
	 * 제공자 통신판매업 신고번호 을(를) 리턴한다.
	 * 
	 * @return providerBizRegNumber -
	 */
	public String getProviderBizRegNumber() {
		return this.providerBizRegNumber;
	}

	/**
	 * 제공자 통신판매업 신고번호 을(를) 셋팅한다.
	 * 
	 * @param providerBizRegNumber
	 *            providerBizRegNumber
	 */
	public void setProviderBizRegNumber(String providerBizRegNumber) {
		this.providerBizRegNumber = providerBizRegNumber;
	}

	/**
	 * 제공자명 을(를) 리턴한다.
	 * 
	 * @return providerName -
	 */
	public String getProviderName() {
		return this.providerName;
	}

	/**
	 * 제공자명 을(를) 셋팅한다.
	 * 
	 * @param providerName
	 *            providerName
	 */
	public void setProviderName(String providerName) {
		this.providerName = providerName;
	}

	/**
	 * 제공자 대표전화번호 을(를) 리턴한다.
	 * 
	 * @return providerRepPhone -
	 */
	public String getProviderRepPhone() {
		return this.providerRepPhone;
	}

	/**
	 * 제공자 대표전화번호 을(를) 셋팅한다.
	 * 
	 * @param providerRepPhone
	 *            providerRepPhone
	 */
	public void setProviderRepPhone(String providerRepPhone) {
		this.providerRepPhone = providerRepPhone;
	}

	/**
	 * 제공자 이메일 을(를) 리턴한다.
	 * 
	 * @return providerEmail -
	 */
	public String getProviderEmail() {
		return this.providerEmail;
	}

	/**
	 * 제공자 이메일 을(를) 셋팅한다.
	 * 
	 * @param providerEmail
	 *            providerEmail
	 */
	public void setProviderEmail(String providerEmail) {
		this.providerEmail = providerEmail;
	}

	/**
	 * 제공자 주소 을(를) 리턴한다.
	 * 
	 * @return providerAddress -
	 */
	public String getProviderAddress() {
		return this.providerAddress;
	}

	/**
	 * 제공자 주소 을(를) 셋팅한다.
	 * 
	 * @param providerAddress
	 *            providerAddress
	 */
	public void setProviderAddress(String providerAddress) {
		this.providerAddress = providerAddress;
	}

	/**
	 * 제공자 상세주소 을(를) 리턴한다.
	 * 
	 * @return providerDetailAddress -
	 */
	public String getProviderDetailAddress() {
		return this.providerDetailAddress;
	}

	/**
	 * 제공자 상세주소 을(를) 셋팅한다.
	 * 
	 * @param providerDetailAddress
	 *            providerDetailAddress
	 */
	public void setProviderDetailAddress(String providerDetailAddress) {
		this.providerDetailAddress = providerDetailAddress;
	}

	/**
	 * 제공자 사업자 등록번호 을(를) 리턴한다.
	 * 
	 * @return providerBizNumber -
	 */
	public String getProviderBizNumber() {
		return this.providerBizNumber;
	}

	/**
	 * 제공자 사업자 등록번호 을(를) 셋팅한다.
	 * 
	 * @param providerBizNumber
	 *            providerBizNumber
	 */
	public void setProviderBizNumber(String providerBizNumber) {
		this.providerBizNumber = providerBizNumber;
	}

}
