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

	public String getFilteredBy() {
		return this.filteredBy;
	}

	public void setFilteredBy(String filteredBy) {
		this.filteredBy = filteredBy;
	}

	public String getImageCd() {
		return this.imageCd;
	}

	public void setImageCd(String imageCd) {
		this.imageCd = imageCd;
	}

	public String getPurchase() {
		return this.purchase;
	}

	public void setPurchase(String purchase) {
		this.purchase = purchase;
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

	public int getOffset() {
		return this.offset;
	}

	public void setOffset(int offset) {
		this.offset = offset;
	}

	public int getCount() {
		return this.count;
	}

	public void setCount(int count) {
		this.count = count;
	}

	public String getLangCd() {
		return this.langCd;
	}

	public void setLangCd(String langCd) {
		this.langCd = langCd;
	}

	public String getDeviceModelCd() {
		return this.deviceModelCd;
	}

	public void setDeviceModelCd(String deviceModelCd) {
		this.deviceModelCd = deviceModelCd;
	}

	public String getTenantId() {
		return this.tenantId;
	}

	public void setTenantId(String tenantId) {
		this.tenantId = tenantId;
	}

	public String getDpi() {
		return this.dpi;
	}

	public void setDpi(String dpi) {
		this.dpi = dpi;
	}

	public String getChartClsfCd() {
		return this.chartClsfCd;
	}

	public void setChartClsfCd(String chartClsfCd) {
		this.chartClsfCd = chartClsfCd;
	}

	public String getBatchId() {
		return this.batchId;
	}

	public void setBatchId(String batchId) {
		this.batchId = batchId;
	}

	public String getStdDt() {
		return this.stdDt;
	}

	public void setStdDt(String stdDt) {
		this.stdDt = stdDt;
	}

}
