/*
 * Copyright (c) 2013 SK planet.
 * All right reserved.
 *
 * This software is the confidential and proprietary information of SK planet.
 * You shall not disclose such Confidential Information and
 * shall use it only in accordance with the terms of the license agreement
 * you entered into with SK planet.
 */
package com.skplanet.storeplatform.sac.client.display.vo.openapi;

import com.skplanet.storeplatform.framework.core.common.vo.CommonInfo;

/**
 * OpenApi Download Best 상품 조회 List Value Object.
 * 
 * Updated on : 2014. 02. 10. Updated by : 이석희, SK 플래닛.
 */
public class NewAppRecommandSacReq extends CommonInfo {

	private static final long serialVersionUID = 1L;
	private String tenantId; // tenantId
	private String langCd; // 언어코드
	private String imageCd; // 원본 이미지 코드
	private String previewImagecd; // 미리보기 이미지 코드

	private String releaseType; // 출시구분
	private String sellerKey; // 판매자 회원 key
	private Integer offset; // 시작점 ROW
	private Integer count; // 페이지당 노출될 ROW 개수

	private String[] arrayProdudctId; // 상품 ID Array

	/**
	 * @return the tenantId
	 */
	public String getTenantId() {
		return this.tenantId;
	}

	/**
	 * @param tenantId
	 *            the tenantId to set
	 */
	public void setTenantId(String tenantId) {
		this.tenantId = tenantId;
	}

	/**
	 * @return the langCd
	 */
	public String getLangCd() {
		return this.langCd;
	}

	/**
	 * @param langCd
	 *            the langCd to set
	 */
	public void setLangCd(String langCd) {
		this.langCd = langCd;
	}

	/**
	 * @return the imageCd
	 */
	public String getImageCd() {
		return this.imageCd;
	}

	/**
	 * @return the previewImagecd
	 */
	public String getPreviewImagecd() {
		return this.previewImagecd;
	}

	/**
	 * @param previewImagecd
	 *            the previewImagecd to set
	 */
	public void setPreviewImagecd(String previewImagecd) {
		this.previewImagecd = previewImagecd;
	}

	/**
	 * @param imageCd
	 *            the imageCd to set
	 */
	public void setImageCd(String imageCd) {
		this.imageCd = imageCd;
	}

	/**
	 * @return the releaseType
	 */
	public String getReleaseType() {
		return this.releaseType;
	}

	/**
	 * @param releaseType
	 *            the releaseType to set
	 */
	public void setReleaseType(String releaseType) {
		this.releaseType = releaseType;
	}

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
	 * @return the offset
	 */
	public Integer getOffset() {
		return this.offset;
	}

	/**
	 * @param offset
	 *            the offset to set
	 */
	public void setOffset(Integer offset) {
		this.offset = offset;
	}

	/**
	 * @return the count
	 */
	public Integer getCount() {
		return this.count;
	}

	/**
	 * @param count
	 *            the count to set
	 */
	public void setCount(Integer count) {
		this.count = count;
	}

	/**
	 * @return the arrayProdudctId
	 */
	public String[] getArrayProdudctId() {
		return this.arrayProdudctId;
	}

	/**
	 * @param arrayProdudctId
	 *            the arrayProdudctId to set
	 */
	public void setArrayProdudctId(String[] arrayProdudctId) {
		this.arrayProdudctId = arrayProdudctId;
	}

}
