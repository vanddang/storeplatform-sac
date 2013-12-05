/*
 * Copyright (c) 2013 SK planet.
 * All right reserved.
 *
 * This software is the confidential and proprietary information of SK planet.
 * You shall not disclose such Confidential Information and
 * shall use it only in accordance with the terms of the license agreement
 * you entered into with SK planet.
 */
package com.skplanet.storeplatform.sac.client.product.vo;

import com.skplanet.storeplatform.framework.core.annotation.ProtobufMapping;

/**
 * 일반/특정 상품 카테고리 리스트 조회 Default Value Object.
 * 
 * Updated on : 2013. 11. 28. Updated by : 이태희, SK 플래닛.
 */
@ProtobufMapping(ProductCategoryProto.ProductCategory.class)
public class ProductCategoryVO {
	private String prodId; // 상품ID

	private String prodNm; // 상품명

	private String topCategoryNo; // 탑카테고리번호

	private String topCategoryNm; // 탑카테고리명

	private String categoryNo; // 카테고리번호

	private String categoryNm; // 카테고리명

	/**
	 * <pre>
	 * method 설명.
	 * </pre>
	 * 
	 * @return String
	 */
	public String getProdId() {
		return this.prodId;
	}

	/**
	 * <pre>
	 * method 설명.
	 * </pre>
	 * 
	 * @param prodId
	 *            prodId
	 */
	public void setProdId(String prodId) {
		this.prodId = prodId;
	}

	/**
	 * <pre>
	 * method 설명.
	 * </pre>
	 * 
	 * @return String
	 */
	public String getProdNm() {
		return this.prodNm;
	}

	/**
	 * <pre>
	 * method 설명.
	 * </pre>
	 * 
	 * @param prodNm
	 *            prodNm
	 */
	public void setProdNm(String prodNm) {
		this.prodNm = prodNm;
	}

	/**
	 * <pre>
	 * method 설명.
	 * </pre>
	 * 
	 * @return String
	 */
	public String getTopCategoryNo() {
		return this.topCategoryNo;
	}

	/**
	 * <pre>
	 * method 설명.
	 * </pre>
	 * 
	 * @param topCategoryNo
	 *            topCategoryNo
	 */
	public void setTopCategoryNo(String topCategoryNo) {
		this.topCategoryNo = topCategoryNo;
	}

	/**
	 * <pre>
	 * method 설명.
	 * </pre>
	 * 
	 * @return String
	 */
	public String getTopCategoryNm() {
		return this.topCategoryNm;
	}

	/**
	 * <pre>
	 * method 설명.
	 * </pre>
	 * 
	 * @param topCategoryNm
	 *            topCategoryNm
	 */
	public void setTopCategoryNm(String topCategoryNm) {
		this.topCategoryNm = topCategoryNm;
	}

	/**
	 * <pre>
	 * method 설명.
	 * </pre>
	 * 
	 * @return String
	 */
	public String getCategoryNo() {
		return this.categoryNo;
	}

	/**
	 * <pre>
	 * method 설명.
	 * </pre>
	 * 
	 * @param categoryNo
	 *            categoryNo
	 */
	public void setCategoryNo(String categoryNo) {
		this.categoryNo = categoryNo;
	}

	/**
	 * <pre>
	 * method 설명.
	 * </pre>
	 * 
	 * @return String
	 */
	public String getCategoryNm() {
		return this.categoryNm;
	}

	/**
	 * <pre>
	 * method 설명.
	 * </pre>
	 * 
	 * @param categoryNm
	 *            categoryNm
	 */
	public void setCategoryNm(String categoryNm) {
		this.categoryNm = categoryNm;
	}
}
