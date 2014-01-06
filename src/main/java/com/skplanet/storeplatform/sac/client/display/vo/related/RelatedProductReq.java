/*
 * Copyright (c) 2013 SK planet.
 * All right reserved.
 *
 * This software is the confidential and proprietary information of SK planet.
 * You shall not disclose such Confidential Information and
 * shall use it only in accordance with the terms of the license agreement
 * you entered into with SK planet.
 */
package com.skplanet.storeplatform.sac.client.display.vo.related;

import java.io.Serializable;

import com.skplanet.storeplatform.framework.core.common.vo.CommonInfo;

/**
 * 일반 상품 카테고리 리스트 조회 Input Value Object.
 * 
 * Updated on : 2013. 12. 19. Updated by : 윤주영, SK 플래닛.
 */
public class RelatedProductReq extends CommonInfo implements Serializable {

	private static final long serialVersionUID = 11123123143L;

	private String sellerNo; // 판매자 회원 번호

	private String artistName; // 아티스트 이름

	private String artistId; // 아티스트 아이디

	private String productId; // 상품 아이디

	private String exceptId; // 제외 상품 아이디

	private String filteredBy; // 차트 구분 코드

	private String imageSizeCd; // 이미지 사이즈 코드

	private String orderedBy;

	private String menuId;

	private String offset; // 시작점 ROW

	private String count; // 페이지당 노출 ROW 수

	public String getSellerNo() {
		return this.sellerNo;
	}

	public void setSellerNo(String sellerNo) {
		this.sellerNo = sellerNo;
	}

	public String getArtistName() {
		return this.artistName;
	}

	public void setArtistName(String artistName) {
		this.artistName = artistName;
	}

	public String getArtistId() {
		return this.artistId;
	}

	public void setArtistId(String artistId) {
		this.artistId = artistId;
	}

	public String getProductId() {
		return this.productId;
	}

	public void setProductId(String productId) {
		this.productId = productId;
	}

	public String getExceptId() {
		return this.exceptId;
	}

	public void setExceptId(String exceptId) {
		this.exceptId = exceptId;
	}

	public String getFilteredBy() {
		return this.filteredBy;
	}

	public void setFilteredBy(String filteredBy) {
		this.filteredBy = filteredBy;
	}

	public String getImageSizeCd() {
		return this.imageSizeCd;
	}

	public void setImageSizeCd(String imageSizeCd) {
		this.imageSizeCd = imageSizeCd;
	}

	public String getOrderedBy() {
		return this.orderedBy;
	}

	public void setOrderedBy(String orderedBy) {
		this.orderedBy = orderedBy;
	}

	public String getMenuId() {
		return this.menuId;
	}

	public void setMenuId(String menuId) {
		this.menuId = menuId;
	}

	public String getOffset() {
		return this.offset;
	}

	public void setOffset(String offset) {
		this.offset = offset;
	}

	public String getCount() {
		return this.count;
	}

	public void setCount(String count) {
		this.count = count;
	}

}
