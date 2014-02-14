/*
 * Copyright (c) 2013 SK planet.
 * All right reserved.
 *
 * This software is the confidential and proprietary information of SK planet.
 * You shall not disclose such Confidential Information and
 * shall use it only in accordance with the terms of the license agreement
 * you entered into with SK planet.
 */
package com.skplanet.storeplatform.sac.client.display.vo.music;

import com.skplanet.storeplatform.framework.core.common.vo.CommonInfo;

/**
 * 음원컨텐츠 조회 Request Value Object.
 * 
 * Updated on : 2013. 12. 19. Updated by : 윤주영, SK 플래닛.
 */
public class MusicContentsSacReq extends CommonInfo {

	private static final long serialVersionUID = 11123123145L;

	private String filteredBy; // 차트 구분 코드

	private String imageCd; // 이미지 사이즈 코드

	private String dpi;

	private String purchase;

	private String orderedBy;

	private String menuId;

	private String langCd;

	private String deviceModelCd;

	private String tenantId;

	private int offset = 1; // 시작점 ROW

	private int count = 20; // 페이지당 노출 ROW 수

	private String chartClsfCd;

	private String batchId;

	private String stdDt; // 배치일자

	/**
	 * 
	 * <pre>
	 * 차트 구분 코드.
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
	 * 차트 구분 코드.
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
	 * 언어코드.
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
	 * 언어코드.
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
	 * purchase.
	 * </pre>
	 * 
	 * @return String
	 */
	public String getPurchase() {
		return this.purchase;
	}

	/**
	 * 
	 * <pre>
	 * purchase.
	 * </pre>
	 * 
	 * @param purchase
	 *            purchase
	 */
	public void setPurchase(String purchase) {
		this.purchase = purchase;
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
	 * 메뉴 아이디.
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
	 * 메뉴 아이디.
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
	 * offset.
	 * </pre>
	 * 
	 * @return int
	 */
	public int getOffset() {
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
	public void setOffset(int offset) {
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
	public int getCount() {
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
	public void setCount(int count) {
		this.count = count;
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
	 * 테넌트 아이디.
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
	 * 테넌트 아이디.
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
	 * dpi.
	 * </pre>
	 * 
	 * @return String
	 */
	public String getDpi() {
		return this.dpi;
	}

	/**
	 * 
	 * <pre>
	 * dpi.
	 * </pre>
	 * 
	 * @param dpi
	 *            dpi
	 */
	public void setDpi(String dpi) {
		this.dpi = dpi;
	}

	/**
	 * 
	 * <pre>
	 * 챠트구분코드.
	 * </pre>
	 * 
	 * @return String
	 */
	public String getChartClsfCd() {
		return this.chartClsfCd;
	}

	/**
	 * 
	 * <pre>
	 * 챠트구분코드.
	 * </pre>
	 * 
	 * @param chartClsfCd
	 *            chartClsfCd
	 */
	public void setChartClsfCd(String chartClsfCd) {
		this.chartClsfCd = chartClsfCd;
	}

	/**
	 * 
	 * <pre>
	 * 배치 아이디.
	 * </pre>
	 * 
	 * @return String
	 */
	public String getBatchId() {
		return this.batchId;
	}

	/**
	 * 
	 * <pre>
	 * 배치 아이디.
	 * </pre>
	 * 
	 * @param batchId
	 *            batchId
	 */
	public void setBatchId(String batchId) {
		this.batchId = batchId;
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

}
