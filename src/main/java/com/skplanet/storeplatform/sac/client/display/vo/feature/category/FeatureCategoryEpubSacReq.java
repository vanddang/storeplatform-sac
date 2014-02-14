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

	/**
	 * 
	 * <pre>
	 * dummy.
	 * </pre>
	 * 
	 * @return String
	 */
	public String getDummy() {
		return this.dummy;
	}

	/**
	 * 
	 * <pre>
	 * dummy.
	 * </pre>
	 * 
	 * @param dummy
	 *            dummy
	 */
	public void setDummy(String dummy) {
		this.dummy = dummy;
	}

	/**
	 * 
	 * <pre>
	 * 리스트 ID.
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
	 * 리스트 ID.
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
	 * 유무료 구분.
	 * </pre>
	 * 
	 * @return String
	 */
	public String getProdCharge() {
		return this.prodCharge;
	}

	/**
	 * 
	 * <pre>
	 * 유무료 구분.
	 * </pre>
	 * 
	 * @param prodCharge
	 *            prodCharge
	 */
	public void setProdCharge(String prodCharge) {
		this.prodCharge = prodCharge;
	}

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
	 * offset.
	 * </pre>
	 * 
	 * @return Integer
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
	 * @return Integer
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
	 * serialVersionUID.
	 * </pre>
	 * 
	 * @return serialVersionUID
	 */
	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	/**
	 * 
	 * <pre>
	 * 테넌트 ID.
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
	 * 테넌트 ID.
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
	 * 단말 코드.
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
	 * 단말 코드.
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
	 * 상품등급코드 배열.
	 * </pre>
	 * 
	 * @return String[]
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

}
