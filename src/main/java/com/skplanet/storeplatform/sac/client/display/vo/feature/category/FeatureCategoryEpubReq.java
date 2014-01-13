package com.skplanet.storeplatform.sac.client.display.vo.feature.category;

/**
 * 추천 상품 (앱,멀티미디어) 조회 Request Value Object.
 * 
 * Updated on : 2013. 12. 26. Updated by : 서영배, GTSOFT.
 */
public class FeatureCategoryEpubReq {

	private static final long serialVersionUID = 1L;

	private String listId; // 리스트ID
	private String prodCharge; // 유무료 구분
	private String prodGradeCd; // 상품 등급
	private String topMenuId; // 메뉴ID
	private int offset; // offset
	private int count; // count
	private String filteredBy; // 필터 조건
	public String getListId() {
		return listId;
	}
	public void setListId(String listId) {
		this.listId = listId;
	}
	public String getProdCharge() {
		return prodCharge;
	}
	public void setProdCharge(String prodCharge) {
		this.prodCharge = prodCharge;
	}
	public String getProdGradeCd() {
		return prodGradeCd;
	}
	public void setProdGradeCd(String prodGradeCd) {
		this.prodGradeCd = prodGradeCd;
	}
	public String getTopMenuId() {
		return topMenuId;
	}
	public void setTopMenuId(String topMenuId) {
		this.topMenuId = topMenuId;
	}
	public int getOffset() {
		return offset;
	}
	public void setOffset(int offset) {
		this.offset = offset;
	}
	public int getCount() {
		return count;
	}
	public void setCount(int count) {
		this.count = count;
	}
	public String getFilteredBy() {
		return filteredBy;
	}
	public void setFilteredBy(String filteredBy) {
		this.filteredBy = filteredBy;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	
}
