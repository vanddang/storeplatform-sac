/*
 * Copyright (c) 2013 SK planet.
 * All right reserved.
 *
 * This software is the confidential and proprietary information of SK planet.
 * You shall not disclose such Confidential Information and
 * shall use it only in accordance with the terms of the license agreement
 * you entered into with SK planet.
 */
package com.skplanet.storeplatform.sac.client.display.vo.best;

import javax.validation.Valid;
import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.NotBlank;

import com.skplanet.storeplatform.framework.core.common.vo.CommonInfo;

/**
 * BEST 다운로드 상품 조회 List Value Object.
 * 
 * Updated on : 2013. 12. 23. Updated by : 이석희, SK 플래닛.
 */
public class BestDownloadSacReq extends CommonInfo {
	private static final long serialVersionUID = 1L;
	private String tenantId; // tenantId
	private String systemId; // 시스템Id
	private String deviceModelCd; // 단말모델코드
	private String langCd; // 언어코드
	@Pattern(regexp = "|[0-9]*")
	private String stdDt; // 배치완료 기준일시
	@NotBlank
	@Pattern(regexp = "RNK000000006|RNK000000008|RNK000000007|RNK000000003|RNK000000005|RNK000000004")
	private String listId; // 리스트 Id
	private String topMenuId; // Top 메뉴 Id
	private String[] arrayTopMenuId; // Top 메뉴 Id Array
	private String menuId; // 메뉴 Id
	@Pattern(regexp = "|ebook\\+normal|ebook\\+genre")
	private String filteredBy; // 카테고리 유형
	private String b2bprod; // B2B 상품구분
	private String hdv; // HDV 지원구분
	private String drm; // drm 지원구분
	private String prodGradeCd; // 상품등급코드
	@Pattern(regexp = "|DP010601|DP010602")
	private String possLendClsfCd; // 소장/대여 구분 코드
	private String[] arrayProdGradeCd; // 상품등급코드 Array
	private String prodChrgYn; // 상품 유/무료 구분
	@Valid
	private Integer offset; // 시작점 ROW
	@Valid
	private Integer count; // 페이지당 노출 ROW 수

    private String ebookSprtYn; // eBook 상품 지원여부

	private String comicSprtYn; // Comic 상품 지원여부

	private String videoDrmSprtYn; // VOD 상품 DRM 지원 여부

	private String sdVideoSprtYn; // VOD 상품 SD 지원 여부

	private String searchHisYn; // 이력 테이블 조회 여부

	/**
	 * 
	 * <pre>
	 * tenantId.
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
	 * tenantId.
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
	 * 시스템Id.
	 * </pre>
	 * 
	 * @return String
	 */
	public String getSystemId() {
		return this.systemId;
	}

	/**
	 * 
	 * <pre>
	 * 시스템Id.
	 * </pre>
	 * 
	 * @param systemId
	 *            systemId
	 */
	public void setSystemId(String systemId) {
		this.systemId = systemId;
	}

	/**
	 * 
	 * <pre>
	 * 단말모델코드.
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
	 * 단말모델코드.
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
	 * 리스트 Id.
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
	 * 리스트 Id.
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
	 * 메뉴 Id.
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
	 * 메뉴 Id.
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
	 * 카테고리 유형.
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
	 * 카테고리 유형.
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
	 * B2B 상품구분.
	 * </pre>
	 * 
	 * @return String
	 */
	public String getB2bprod() {
		return this.b2bprod;
	}

	/**
	 * 
	 * <pre>
	 * B2B 상품구분.
	 * </pre>
	 * 
	 * @param b2bprod
	 *            b2bprod
	 */
	public void setB2bprod(String b2bprod) {
		this.b2bprod = b2bprod;
	}

	/**
	 * 
	 * <pre>
	 * HDV 지원구분.
	 * </pre>
	 * 
	 * @return String
	 */
	public String getHdv() {
		return this.hdv;
	}

	/**
	 * 
	 * <pre>
	 * HDV 지원구분.
	 * </pre>
	 * 
	 * @param hdv
	 *            String hdv
	 */
	public void setHdv(String hdv) {
		this.hdv = hdv;
	}

	/**
	 * 
	 * <pre>
	 * drm 지원구분.
	 * </pre>
	 * 
	 * @return String
	 */
	public String getDrm() {
		return this.drm;
	}

	/**
	 * 
	 * <pre>
	 * drm 지원구분.
	 * </pre>
	 * 
	 * @param drm
	 *            drm
	 */
	public void setDrm(String drm) {
		this.drm = drm;
	}

	/**
	 * 
	 * <pre>
	 * 상품 이용등급 코드.
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
	 * 상품 이용등급 코드.
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
	 * 소장/대여 구분 코드.
	 * </pre>
	 * 
	 * @return the possLendClsfCd
	 */
	public String getPossLendClsfCd() {
		return this.possLendClsfCd;
	}

	/**
	 * 
	 * <pre>
	 * 소장/대여 구분 코드.
	 * </pre>
	 * 
	 * @param possLendClsfCd
	 *            the possLendClsfCd to set
	 */
	public void setPossLendClsfCd(String possLendClsfCd) {
		this.possLendClsfCd = possLendClsfCd;
	}

	/**
	 * 
	 * <pre>
	 * 상품 이용등급 코드 Array.
	 * </pre>
	 * 
	 * @return String[]
	 */
	public String[] getArrayProdGradeCd() {
		return this.arrayProdGradeCd;
	}

	/**
	 * 
	 * <pre>
	 * 상품 이용등급 코드 Array.
	 * </pre>
	 * 
	 * @param arrayProdGradeCd
	 *            arrayProdGradeCd
	 */
	public void setArrayProdGradeCd(String[] arrayProdGradeCd) {
		this.arrayProdGradeCd = arrayProdGradeCd;
	}

	/**
	 * 
	 * <pre>
	 * 상품 유/무료 구분.
	 * </pre>
	 * 
	 * @return the prodChrgYn
	 */
	public String getProdChrgYn() {
		return this.prodChrgYn;
	}

	/**
	 * 
	 * <pre>
	 * 상품 유/무료 구분.
	 * </pre>
	 * 
	 * @param prodChrgYn
	 *            the prodChrgYn to set
	 */
	public void setProdChrgYn(String prodChrgYn) {
		this.prodChrgYn = prodChrgYn;
	}

	/**
	 * 
	 * <pre>
	 * 시작점 ROW.
	 * </pre>
	 * 
	 * @return String
	 */
	public Integer getOffset() {
		return this.offset;
	}

	/**
	 * 
	 * <pre>
	 * 시작점 ROW.
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
	 * 페이지당 노출 ROW 수.
	 * </pre>
	 * 
	 * @return String
	 */
	public Integer getCount() {
		return this.count;
	}

	/**
	 * 
	 * <pre>
	 * 페이지당 노출 ROW 수.
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
	 * TOP 메뉴 ID.
	 * </pre>
	 * 
	 * @return the topMenuId
	 */
	public String getTopMenuId() {
		return this.topMenuId;
	}

	/**
	 * 
	 * <pre>
	 * TOP 메뉴 ID.
	 * </pre>
	 * 
	 * @param topMenuId
	 *            the topMenuId to set
	 */
	public void setTopMenuId(String topMenuId) {
		this.topMenuId = topMenuId;
	}

	/**
	 * 
	 * <pre>
	 * TOP 메뉴 ID Array.
	 * </pre>
	 * 
	 * @return the arrayTopMenuId
	 */
	public String[] getArrayTopMenuId() {
		return this.arrayTopMenuId;
	}

	/**
	 * 
	 * <pre>
	 * TOP 메뉴 ID Array.
	 * </pre>
	 * 
	 * @param arrayTopMenuId
	 *            the arrayTopMenuId to set
	 */
	public void setArrayTopMenuId(String[] arrayTopMenuId) {
		this.arrayTopMenuId = arrayTopMenuId;
	}

	/**
	 * 
	 * <pre>
	 * 배치완료 기준일시.
	 * </pre>
	 * 
	 * @return the stdDt
	 */
	public String getStdDt() {
		return this.stdDt;
	}

	/**
	 * 
	 * <pre>
	 * 배치완료 기준일시.
	 * </pre>
	 * 
	 * @param stdDt
	 *            the stdDt to set
	 */
	public void setStdDt(String stdDt) {
		this.stdDt = stdDt;
	}

	/**
	 * @return the ebookSprtYn
	 */
	public String getEbookSprtYn() {
		return this.ebookSprtYn;
	}

	/**
	 * @param ebookSprtYn
	 *            the ebookSprtYn to set
	 */
	public void setEbookSprtYn(String ebookSprtYn) {
		this.ebookSprtYn = ebookSprtYn;
	}

	/**
	 * @return the comicSprtYn
	 */
	public String getComicSprtYn() {
		return this.comicSprtYn;
	}

	/**
	 * @param comicSprtYn
	 *            the comicSprtYn to set
	 */
	public void setComicSprtYn(String comicSprtYn) {
		this.comicSprtYn = comicSprtYn;
	}

	/**
	 * @return the videoDrmSprtYn
	 */
	public String getVideoDrmSprtYn() {
		return this.videoDrmSprtYn;
	}

	/**
	 * @param videoDrmSprtYn
	 *            the videoDrmSprtYn to set
	 */
	public void setVideoDrmSprtYn(String videoDrmSprtYn) {
		this.videoDrmSprtYn = videoDrmSprtYn;
	}

	/**
	 * @return the sdVideoSprtYn
	 */
	public String getSdVideoSprtYn() {
		return this.sdVideoSprtYn;
	}

	/**
	 * @param sdVideoSprtYn
	 *            the sdVideoSprtYn to set
	 */
	public void setSdVideoSprtYn(String sdVideoSprtYn) {
		this.sdVideoSprtYn = sdVideoSprtYn;
	}

	/**
	 * @return the searchHisYn
	 */
	public String getSearchHisYn() {
		return this.searchHisYn;
	}

	/**
	 * @param searchHisYn
	 *            the searchHisYn to set
	 */
	public void setSearchHisYn(String searchHisYn) {
		this.searchHisYn = searchHisYn;
	}

}
