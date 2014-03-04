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

import org.hibernate.validator.constraints.NotBlank;

import com.skplanet.storeplatform.framework.core.common.vo.CommonInfo;

/**
 * OpenApi 상품 검색 요청(By 상품명) - No Provisioning List Value Object.
 * 
 * Updated on : 2014. 03. 03. Updated by : 백승현, SK 플래닛.
 */
public class NoProvisionSacReq extends CommonInfo {

	private static final long serialVersionUID = 1L;
	private String tenantId; // tenantId
	private String langCd; // 언어코드
	private String imageCd; // 원본 이미지 코드
	private String previewImagecd; // 미리보기 이미지 코드

	@NotBlank
	private String topMenuIdList; // TOP 메뉴 ID

	private String[] arrayTopMenuId;

	@NotBlank
	private String searchKeyword; // 검색어
	@NotBlank
	private String orderedBy; // 정렬조건(최신순: recent, 평점순: popular)
	private Integer offset; // 시작점 ROW
	private Integer count; // 페이지당 노출 될 ROW 개수

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
	 * @param imageCd
	 *            the imageCd to set
	 */
	public void setImageCd(String imageCd) {
		this.imageCd = imageCd;
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
	 * @return the searchKeyword
	 */
	public String getSearchKeyword() {
		return this.searchKeyword;
	}

	/**
	 * @param searchKeyword
	 *            the searchKeyword to set
	 */
	public void setSearchKeyword(String searchKeyword) {
		this.searchKeyword = searchKeyword;
	}

	/**
	 * @return the orderedBy
	 */
	public String getOrderedBy() {
		return this.orderedBy;
	}

	/**
	 * @param orderedBy
	 *            the orderedBy to set
	 */
	public void setOrderedBy(String orderedBy) {
		this.orderedBy = orderedBy;
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
	 * @return the arrayTopMenuId
	 */
	public String[] getArrayTopMenuId() {
		return this.arrayTopMenuId;
	}

	/**
	 * @param arrayTopMenuId
	 *            the arrayTopMenuId to set
	 */
	public void setArrayTopMenuId(String[] arrayTopMenuId) {
		this.arrayTopMenuId = arrayTopMenuId;
	}

	/**
	 * @return the topMenuIdList
	 */
	public String getTopMenuIdList() {
		return this.topMenuIdList;
	}

	/**
	 * @param topMenuIdList
	 *            the topMenuIdList to set
	 */
	public void setTopMenuIdList(String topMenuIdList) {
		this.topMenuIdList = topMenuIdList;
	}

}
