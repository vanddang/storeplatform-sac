package com.skplanet.storeplatform.sac.client.display.vo.search;

import com.skplanet.storeplatform.framework.core.common.vo.CommonInfo;

public class SearchProductReq extends CommonInfo {
	private static final long serialVersionUID = 11123123142L;
	private String prodGradeCd; // 상품등급코드

	private String topMenuId; // 탑메뉴ID

	private String menuId; // 메뉴ID

	private String listId; // 리스트ID

	private String filterdby; // 조회유형

	private String orderedBy; // 상품정렬순서

	private Integer offset; // 시작점 ROW

	private Integer count; // 페이지당 노출될 ROW 개수

	private String deviceModelNo; // 단말 모델 코드

	private String tenantId; // 테넌트ID

	private String stdDt; // 기준일시

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

	public String getMenuId() {
		return this.menuId;
	}

	public void setMenuId(String menuId) {
		this.menuId = menuId;
	}

	public String getListId() {
		return this.listId;
	}

	public void setListId(String listId) {
		this.listId = listId;
	}

	public String getFilterdby() {
		return this.filterdby;
	}

	public void setFilterdby(String filterdby) {
		this.filterdby = filterdby;
	}

	public String getOrderedBy() {
		return this.orderedBy;
	}

	public void setOrderedBy(String orderedBy) {
		this.orderedBy = orderedBy;
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

	public String getDeviceModelNo() {
		return this.deviceModelNo;
	}

	public void setDeviceModelNo(String deviceModelNo) {
		this.deviceModelNo = deviceModelNo;
	}

	public String getTenantId() {
		return this.tenantId;
	}

	public void setTenantId(String tenantId) {
		this.tenantId = tenantId;
	}

	public String getStdDt() {
		return this.stdDt;
	}

	public void setStdDt(String stdDt) {
		this.stdDt = stdDt;
	}

}
