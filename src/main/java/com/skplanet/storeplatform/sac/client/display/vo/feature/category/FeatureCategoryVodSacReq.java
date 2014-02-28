/*
 * Copyright (c) 2013 SK planet.
 * All right reserved.
 *
 * This software is the confidential and proprietary information of SK planet.
 * You shall not disclose such Confidential Information and
 * shall use it only in accordance with the terms of the license agreement
 * you entered into with SK planet.
 */
package com.skplanet.storeplatform.sac.client.display.vo.feature.category;

import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.NotBlank;

import com.skplanet.storeplatform.framework.core.common.vo.CommonInfo;

/**
 * Feature VOD 카테고리 상품 조회 Request Value Object.
 * 
 * Updated on : 2014. 01. 06. Updated by : 이태희, SK 플래닛.
 */
public class FeatureCategoryVodSacReq extends CommonInfo {
	private static final long serialVersionUID = 1L;

	private String prodGradeCd; // 상품등급코드

	@NotBlank
	@Pattern(regexp = "^DP17|^DP18")
	private String topMenuId; // 탑메뉴ID

	private String menuId; // 메뉴ID

	@NotBlank
	@Pattern(regexp = "^ADM000000008|^TGR000000002")
	private String listId; // 리스트ID

	private String filteredBy; // 조회유형

	private String orderedBy; // 상품정렬순서

	private Integer offset; // 시작점 ROW

	private Integer count; // 페이지당 노출될 ROW 개수

	private String stdDt; // 기준일시

	private String subStdDt; // 기준일시(최신Up에서 사용)

	private String deviceModelCd; // 단말 모델 코드

	private String tenantId; // 테넌트ID

	private String imageCd; // 이미지 코드

	private String langCd; // 언어

	private String[] prodGradeCdArr; // 상품등급코드 배열

	/**
	 * 
	 * <pre>
	 * 상품등급코드.
	 * </pre>
	 * 
	 * @return String
	 */
	public String getProdGradeCd() {
		return this.prodGradeCd;
	}

	/**
	 * 
	 * <pre>
	 * 상품등급코드.
	 * </pre>
	 * 
	 * @param prodGradeCd
	 *            prodGradeCd
	 */
	public void setProdGradeCd(String prodGradeCd) {
		this.prodGradeCd = prodGradeCd;
	}

	/**
	 * 
	 * <pre>
	 * 상위 메뉴ID.
	 * </pre>
	 * 
	 * @return String
	 */
	public String getTopMenuId() {
		return this.topMenuId;
	}

	/**
	 * 
	 * <pre>
	 * 상위 메뉴ID.
	 * </pre>
	 * 
	 * @param topMenuId
	 *            topMenuId
	 */
	public void setTopMenuId(String topMenuId) {
		this.topMenuId = topMenuId;
	}

	/**
	 * 
	 * <pre>
	 * 메뉴ID.
	 * </pre>
	 * 
	 * @return String
	 */
	public String getMenuId() {
		return this.menuId;
	}

	/**
	 * 
	 * <pre>
	 * 메뉴ID.
	 * </pre>
	 * 
	 * @param menuId
	 *            menuId
	 */
	public void setMenuId(String menuId) {
		this.menuId = menuId;
	}

	/**
	 * 
	 * <pre>
	 * 리스트ID.
	 * </pre>
	 * 
	 * @return String
	 */
	public String getListId() {
		return this.listId;
	}

	/**
	 * 
	 * <pre>
	 * 리스트ID.
	 * </pre>
	 * 
	 * @param listId
	 *            listId
	 */
	public void setListId(String listId) {
		this.listId = listId;
	}

	/**
	 * 
	 * <pre>
	 * 조회유형.
	 * </pre>
	 * 
	 * @return String
	 */
	public String getFilteredBy() {
		return this.filteredBy;
	}

	/**
	 * 
	 * <pre>
	 * 조회유형.
	 * </pre>
	 * 
	 * @param filteredBy
	 *            filteredBy
	 */
	public void setFilteredBy(String filteredBy) {
		this.filteredBy = filteredBy;
	}

	/**
	 * 
	 * <pre>
	 * 정렬순서.
	 * </pre>
	 * 
	 * @return String
	 */
	public String getOrderedBy() {
		return this.orderedBy;
	}

	/**
	 * 
	 * <pre>
	 * 정렬순서.
	 * </pre>
	 * 
	 * @param orderedBy
	 *            orderedBy
	 */
	public void setOrderedBy(String orderedBy) {
		this.orderedBy = orderedBy;
	}

	/**
	 * 
	 * <pre>
	 * offset.
	 * </pre>
	 * 
	 * @return int
	 */
	public Integer getOffset() {
		return this.offset;
	}

	/**
	 * 
	 * <pre>
	 * offset.
	 * </pre>
	 * 
	 * @param offset
	 *            offset
	 */
	public void setOffset(Integer offset) {
		this.offset = offset;
	}

	/**
	 * 
	 * <pre>
	 * count.
	 * </pre>
	 * 
	 * @return int
	 */
	public Integer getCount() {
		return this.count;
	}

	/**
	 * 
	 * <pre>
	 * count.
	 * </pre>
	 * 
	 * @param count
	 *            count
	 */
	public void setCount(Integer count) {
		this.count = count;
	}

	/**
	 * 
	 * <pre>
	 * 배치완료일자.
	 * </pre>
	 * 
	 * @return String
	 */
	public String getStdDt() {
		return this.stdDt;
	}

	/**
	 * 
	 * <pre>
	 * 배치완료일자.
	 * </pre>
	 * 
	 * @param stdDt
	 *            stdDt
	 */
	public void setStdDt(String stdDt) {
		this.stdDt = stdDt;
	}

	/**
	 * 
	 * <pre>
	 * 단말모델명.
	 * </pre>
	 * 
	 * @return String
	 */
	public String getDeviceModelCd() {
		return this.deviceModelCd;
	}

	/**
	 * 
	 * <pre>
	 * 단말모델명.
	 * </pre>
	 * 
	 * @param deviceModelCd
	 *            deviceModelCd
	 */
	public void setDeviceModelCd(String deviceModelCd) {
		this.deviceModelCd = deviceModelCd;
	}

	/**
	 * 
	 * <pre>
	 * 테넌트ID.
	 * </pre>
	 * 
	 * @return String
	 */
	public String getTenantId() {
		return this.tenantId;
	}

	/**
	 * 
	 * <pre>
	 * 테넌트ID.
	 * </pre>
	 * 
	 * @param tenantId
	 *            tenantId
	 */
	public void setTenantId(String tenantId) {
		this.tenantId = tenantId;
	}

	/**
	 * 
	 * <pre>
	 * 이미지코드.
	 * </pre>
	 * 
	 * @return String
	 */
	public String getImageCd() {
		return this.imageCd;
	}

	/**
	 * 
	 * <pre>
	 * 이미지코드.
	 * </pre>
	 * 
	 * @param imageCd
	 *            imageCd
	 */
	public void setImageCd(String imageCd) {
		this.imageCd = imageCd;
	}

	/**
	 * 
	 * <pre>
	 * 언어코드.
	 * </pre>
	 * 
	 * @return String
	 */
	public String getLangCd() {
		return this.langCd;
	}

	/**
	 * 
	 * <pre>
	 * 언어코드.
	 * </pre>
	 * 
	 * @param langCd
	 *            langCd
	 */
	public void setLangCd(String langCd) {
		this.langCd = langCd;
	}

	/**
	 * 
	 * <pre>
	 * 상품등급코드 배열.
	 * </pre>
	 * 
	 * @return String
	 */
	public String[] getProdGradeCdArr() {
		return this.prodGradeCdArr == null ? null : this.prodGradeCdArr.clone();
	}

	/**
	 * 
	 * <pre>
	 * 상품등급코드 배열.
	 * </pre>
	 * 
	 * @param prodGradeCdArr
	 *            prodGradeCdArr
	 */
	public void setProdGradeCdArr(String[] prodGradeCdArr) {
		this.prodGradeCdArr = prodGradeCdArr == null ? null : prodGradeCdArr.clone();
	}

	/**
	 * 
	 * <pre>
	 * 서브 배치완료일자.
	 * </pre>
	 * 
	 * @return String
	 */
	public String getSubStdDt() {
		return this.subStdDt;
	}

	/**
	 * 
	 * <pre>
	 * 서브 배치완료일자.
	 * </pre>
	 * 
	 * @param subStdDt
	 *            subStdDt
	 */
	public void setSubStdDt(String subStdDt) {
		this.subStdDt = subStdDt;
	}

}
