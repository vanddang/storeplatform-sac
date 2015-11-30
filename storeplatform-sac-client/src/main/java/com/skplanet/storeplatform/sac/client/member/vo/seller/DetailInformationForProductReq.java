package com.skplanet.storeplatform.sac.client.member.vo.seller;

import org.codehaus.jackson.map.annotate.JsonSerialize;
import org.codehaus.jackson.map.annotate.JsonSerialize.Inclusion;

import com.skplanet.storeplatform.framework.core.common.vo.CommonInfo;

/**
 * [REQUEST] 상품상세의 판매자 정보.
 * 
 * Updated on : 2015. 11. 27. Updated by : 최진호, 보고지티
 */
@JsonSerialize(include = Inclusion.NON_NULL)
public class DetailInformationForProductReq extends CommonInfo {

	private static final long serialVersionUID = 1L;

	/** 판매자 key. */
	private String sellerKey;
	/** App ID. */
	private String aid;
	/** 상품 카테고리 */
	private String categoryCd;

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
	 * @return the aid
	 */
	public String getAid() {
		return this.aid;
	}

	/**
	 * @param aid
	 *            the aid to set
	 */
	public void setAid(String aid) {
		this.aid = aid;
	}

	/**
	 * 상품 카테고리 을(를) 리턴한다.
	 * 
	 * @return categoryCd -
	 */
	public String getCategoryCd() {
		return this.categoryCd;
	}

	/**
	 * 상품 카테고리 을(를) 셋팅한다.
	 * 
	 * @param categoryCd
	 *            categoryCd
	 */
	public void setCategoryCd(String categoryCd) {
		this.categoryCd = categoryCd;
	}
}
