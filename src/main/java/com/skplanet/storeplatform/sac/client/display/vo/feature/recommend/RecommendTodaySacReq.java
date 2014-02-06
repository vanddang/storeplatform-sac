package com.skplanet.storeplatform.sac.client.display.vo.feature.recommend;

/**
 * TODAY 상품 조회 Request Value Object.
 * 
 * UUpdated on : 2014. 2. 6. Updated by : 조준일, nTels.
 */
public class RecommendTodaySacReq {
	private static final long serialVersionUID = 1L;

	private String listId; // 리스트ID
	private String prodCharge; // 유무료 구분
	private String prodGradeCd; // 상품 등급
	private String topMenuId; // 메뉴ID
	private int offset; // offset
	private int count; // count

	// common req 전까지 임시
	private String tenantId; // 메뉴ID
	private String langCd; // 메뉴ID
	private String deviceModelCd; // 메뉴ID
	private String stdDt; // 배치일자

	private String[] prodGradeCdArr;

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
		return this.prodGradeCdArr;
	}

	public void setProdGradeCdArr(String[] prodGradeCdArr) {
		this.prodGradeCdArr = prodGradeCdArr;
	}
}
