package com.skplanet.storeplatform.sac.client.display.vo.feature.category;

import com.skplanet.storeplatform.framework.core.common.vo.CommonInfo;

/**
 * 추천 상품 (앱,멀티미디어) 조회 Request Value Object.
 * 
 * Updated on : 2013. 12. 26. Updated by : 서영배, GTSOFT.
 */
public class FeatureCategoryEpubSacReq extends CommonInfo {

	private static final long serialVersionUID = 1L;

	private String listId; // 리스트ID
	private String prodCharge; // 유무료 구분
	private String prodGradeCd; // 상품 등급
	private String topMenuId; // 메뉴ID
	private Integer offset; // offset
	private Integer count; // count
	private String filteredBy; // 필터 조건

	// common req 전까지 임시
	private String tenantId; // 메뉴ID
	private String langCd; // 메뉴ID
	private String deviceModelCd; // 메뉴ID
	private String stdDt; // 배치일자

	private String[] prodGradeCdArr; // 상품등급코드 배열

	// Dummy Data용
	private String dummy; // 메뉴ID

	public String getDummy() {
		return this.dummy;
	}

	public void setDummy(String dummy) {
		this.dummy = dummy;
	}

	public String getListId() {
		return this.listId;
	}

	public void setListId(String listId) {
		this.listId = listId;
	}

	public String getProdCharge() {
		return this.prodCharge;
	}

	public void setProdCharge(String prodCharge) {
		this.prodCharge = prodCharge;
	}

	public String getProdGradeCd() {
		return this.prodGradeCd;
	}

	public void setProdGradeCd(String prodGradeCd) {
		this.prodGradeCd = prodGradeCd;
	}

	public String getTopMenuId() {
		return this.topMenuId;
	}

	public void setTopMenuId(String topMenuId) {
		this.topMenuId = topMenuId;
	}

	public Integer getOffset() {
		return this.offset;
	}

	public void setOffset(Integer offset) {
		this.offset = offset;
	}

	public Integer getCount() {
		return this.count;
	}

	public void setCount(Integer count) {
		this.count = count;
	}

	public String getFilteredBy() {
		return this.filteredBy;
	}

	public void setFilteredBy(String filteredBy) {
		this.filteredBy = filteredBy;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public String getTenantId() {
		return this.tenantId;
	}

	public void setTenantId(String tenantId) {
		this.tenantId = tenantId;
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

	public String getStdDt() {
		return this.stdDt;
	}

	public void setStdDt(String stdDt) {
		this.stdDt = stdDt;
	}

	public String[] getProdGradeCdArr() {
		return this.prodGradeCdArr == null ? null : this.prodGradeCdArr.clone();
	}

	public void setProdGradeCdArr(String[] prodGradeCdArr) {
		this.prodGradeCdArr = prodGradeCdArr == null ? null : prodGradeCdArr.clone();
	}

}
