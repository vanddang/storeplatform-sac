package com.skplanet.storeplatform.sac.display.meta.vo;

import com.skplanet.storeplatform.framework.core.common.vo.CommonInfo;

/**
 * 상품 기본 정보 조회 Info
 * 
 * Updated on : 2014. 1. 27. Updated by : 오승민, 인크로스
 */
public class ProductBasicInfo extends CommonInfo {
	private static final long serialVersionUID = 1L;

	private Integer totalCount;
	private String tenantId;
	private String menuId;
	private String prodId;
	private String partProdId;
	private String svcGrpCd;
	private String svcTypeCd;
	private String contentsTypeCd;
	private String metaClsfCd;
	private String topMenuId;
	private String expoOrd;
	private String firstProdId;
	private String partParentClsfCd;

	// 쇼핑 관련
	private String catalogId;
	private String brandId;

	// 웹툰 관련
	private String seriallyWkdy;

	// eBook
	private String etcCd;

	// 정액권 관련
	private String prodStatusCd;

	// 앱코디 관련
	private String recmReason;

	/**
	 * @return the totalCount
	 */
	public Integer getTotalCount() {
		return this.totalCount;
	}

	/**
	 * @param totalCount
	 *            the totalCount to set
	 */
	public void setTotalCount(Integer totalCount) {
		this.totalCount = totalCount;
	}

	/**
	 * @return the tenantId
	 */
	public String getTenantId() {
		return this.tenantId;
	}

	/**
	 * @param tenantId
	 *            the tenantId to set
	 */
	public void setTenantId(String tenantId) {
		this.tenantId = tenantId;
	}

	/**
	 * @return the menuId
	 */
	public String getMenuId() {
		return this.menuId;
	}

	/**
	 * @param menuId
	 *            the menuId to set
	 */
	public void setMenuId(String menuId) {
		this.menuId = menuId;
	}

	/**
	 * @return the prodId
	 */
	public String getProdId() {
		return this.prodId;
	}

	/**
	 * @param prodId
	 *            the prodId to set
	 */
	public void setProdId(String prodId) {
		this.prodId = prodId;
	}

	/**
	 * @return the partProdId
	 */
	public String getPartProdId() {
		return this.partProdId;
	}

	/**
	 * @param partProdId
	 *            the partProdId to set
	 */
	public void setPartProdId(String partProdId) {
		this.partProdId = partProdId;
	}

	/**
	 * @return the svcGrpCd
	 */
	public String getSvcGrpCd() {
		return this.svcGrpCd;
	}

	/**
	 * @param svcGrpCd
	 *            the svcGrpCd to set
	 */
	public void setSvcGrpCd(String svcGrpCd) {
		this.svcGrpCd = svcGrpCd;
	}

	/**
	 * @return the svcTypeCd
	 */
	public String getSvcTypeCd() {
		return this.svcTypeCd;
	}

	/**
	 * @param svcTypeCd
	 *            the svcTypeCd to set
	 */
	public void setSvcTypeCd(String svcTypeCd) {
		this.svcTypeCd = svcTypeCd;
	}

	/**
	 * @return the contentsTypeCd
	 */
	public String getContentsTypeCd() {
		return this.contentsTypeCd;
	}

	/**
	 * @param contentsTypeCd
	 *            the contentsTypeCd to set
	 */
	public void setContentsTypeCd(String contentsTypeCd) {
		this.contentsTypeCd = contentsTypeCd;
	}

	/**
	 * @return the metaClsfCd
	 */
	public String getMetaClsfCd() {
		return this.metaClsfCd;
	}

	/**
	 * @param metaClsfCd
	 *            the metaClsfCd to set
	 */
	public void setMetaClsfCd(String metaClsfCd) {
		this.metaClsfCd = metaClsfCd;
	}

	/**
	 * @return the topMenuId
	 */
	public String getTopMenuId() {
		return this.topMenuId;
	}

	/**
	 * @param topMenuId
	 *            the topMenuId to set
	 */
	public void setTopMenuId(String topMenuId) {
		this.topMenuId = topMenuId;
	}

	/**
	 * @return the expoOrd
	 */
	public String getExpoOrd() {
		return this.expoOrd;
	}

	/**
	 * @param expoOrd
	 *            the expoOrd to set
	 */
	public void setExpoOrd(String expoOrd) {
		this.expoOrd = expoOrd;
	}

	public String getCatalogId() {
		return this.catalogId;
	}

	public void setCatalogId(String catalogId) {
		this.catalogId = catalogId;
	}

	public String getBrandId() {
		return this.brandId;
	}

	public void setBrandId(String brandId) {
		this.brandId = brandId;
	}

	/**
	 * @return the firstProdId
	 */
	public String getFirstProdId() {
		return this.firstProdId;
	}

	/**
	 * @param firstProdId
	 *            the firstProdId to set
	 */
	public void setFirstProdId(String firstProdId) {
		this.firstProdId = firstProdId;
	}

	/**
	 * @return the seriallyWkdy
	 */
	public String getSeriallyWkdy() {
		return this.seriallyWkdy;
	}

	/**
	 * @param seriallyWkdy
	 *            the seriallyWkdy to set
	 */
	public void setSeriallyWkdy(String seriallyWkdy) {
		this.seriallyWkdy = seriallyWkdy;
	}

	/**
	 * @return the etcCd
	 */
	public String getEtcCd() {
		return this.etcCd;
	}

	/**
	 * @param etcCd
	 *            the etcCd to set
	 */
	public void setEtcCd(String etcCd) {
		this.etcCd = etcCd;
	}

	public String getPartParentClsfCd() {
		return this.partParentClsfCd;
	}

	public void setPartParentClsfCd(String partParentClsfCd) {
		this.partParentClsfCd = partParentClsfCd;
	}

	/**
	 * @return the prodStatusCd
	 */
	public String getProdStatusCd() {
		return this.prodStatusCd;
	}

	/**
	 * @param prodStatusCd
	 *            the prodStatusCd to set
	 */
	public void setProdStatusCd(String prodStatusCd) {
		this.prodStatusCd = prodStatusCd;
	}

	/**
	 * @return the recmReason
	 */
	public String getRecmReason() {
		return this.recmReason;
	}

	/**
	 * @param recmReason
	 *            the recmReason to set
	 */
	public void setRecmReason(String recmReason) {
		this.recmReason = recmReason;
	}
}
