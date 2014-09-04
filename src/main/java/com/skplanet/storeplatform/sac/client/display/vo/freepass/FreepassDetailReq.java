package com.skplanet.storeplatform.sac.client.display.vo.freepass;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotBlank;

/**
 * 
 * FreepassDetailReq Value Object
 * 
 * 정액제 req VO
 * 
 * Updated on : 2014. 2. 13. Updated by : 서영배, GTSOFT
 */
public class FreepassDetailReq {

	private static final long serialVersionUID = 1L;

	private String topMenuId; // 최상위메뉴ID
	private String kind; // 자유이용권 종류
	@NotNull
	@NotBlank
	private String productId; // 상품ID
	private String menuId; // 메뉴ID
	private String channelId; // 채널상품ID
	private int offset; // offset
	private int count; // count
	private String bannerImageCd; // 배너이미지
	private String thumbnailImageCd; // 썸네일이미지
	private String ebookThumbnailImageCd; // (이북,코믹)썸네일이미지
	private String prodStatusCd; // 상품상태
	private String standardModelCd; // 상품상태

	// common req 전까지 임시
	private String tenantId; // 테넌트ID
	private String langCd; // 언어
	private String deviceModelCd; // 단말모델

	// 구매내역 체크
	private String userKey; // 사용자키
	private String deviceKey; // 단말키

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
	 * @return the kind
	 */
	public String getKind() {
		return this.kind;
	}

	/**
	 * @param kind
	 *            the kind to set
	 */
	public void setKind(String kind) {
		this.kind = kind;
	}

	/**
	 * @return the productId
	 */
	public String getProductId() {
		return this.productId;
	}

	/**
	 * @param productId
	 *            the productId to set
	 */
	public void setProductId(String productId) {
		this.productId = productId;
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
	 * @return the channelId
	 */
	public String getChannelId() {
		return this.channelId;
	}

	/**
	 * @param channelId
	 *            the channelId to set
	 */
	public void setChannelId(String channelId) {
		this.channelId = channelId;
	}

	/**
	 * @return the offset
	 */
	public int getOffset() {
		return this.offset;
	}

	/**
	 * @param offset
	 *            the offset to set
	 */
	public void setOffset(int offset) {
		this.offset = offset;
	}

	/**
	 * @return the count
	 */
	public int getCount() {
		return this.count;
	}

	/**
	 * @param count
	 *            the count to set
	 */
	public void setCount(int count) {
		this.count = count;
	}

	/**
	 * @return the bannerImageCd
	 */
	public String getBannerImageCd() {
		return this.bannerImageCd;
	}

	/**
	 * @param bannerImageCd
	 *            the bannerImageCd to set
	 */
	public void setBannerImageCd(String bannerImageCd) {
		this.bannerImageCd = bannerImageCd;
	}

	/**
	 * @return the ebookThumbnailImageCd
	 */
	public String getEbookThumbnailImageCd() {
		return this.ebookThumbnailImageCd;
	}

	/**
	 * @param ebookThumbnailImageCd
	 *            the ebookThumbnailImageCd to set
	 */
	public void setEbookThumbnailImageCd(String ebookThumbnailImageCd) {
		this.ebookThumbnailImageCd = ebookThumbnailImageCd;
	}

	/**
	 * @return the thumbnailImageCd
	 */
	public String getThumbnailImageCd() {
		return this.thumbnailImageCd;
	}

	/**
	 * @param thumbnailImageCd
	 *            the thumbnailImageCd to set
	 */
	public void setThumbnailImageCd(String thumbnailImageCd) {
		this.thumbnailImageCd = thumbnailImageCd;
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
	 * @return the standardModelCd
	 */
	public String getStandardModelCd() {
		return this.standardModelCd;
	}

	/**
	 * @param standardModelCd
	 *            the standardModelCd to set
	 */
	public void setStandardModelCd(String standardModelCd) {
		this.standardModelCd = standardModelCd;
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
	 * @return the langCd
	 */
	public String getLangCd() {
		return this.langCd;
	}

	/**
	 * @param langCd
	 *            the langCd to set
	 */
	public void setLangCd(String langCd) {
		this.langCd = langCd;
	}

	/**
	 * @return the deviceModelCd
	 */
	public String getDeviceModelCd() {
		return this.deviceModelCd;
	}

	/**
	 * @param deviceModelCd
	 *            the deviceModelCd to set
	 */
	public void setDeviceModelCd(String deviceModelCd) {
		this.deviceModelCd = deviceModelCd;
	}

    /**
	 * @return the serialversionuid
	 */
	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	/**
	 * @return the userKey
	 */
	public String getUserKey() {
		return this.userKey;
	}

	/**
	 * @param userKey
	 *            the userKey to set
	 */
	public void setUserKey(String userKey) {
		this.userKey = userKey;
	}

	/**
	 * @return the deviceKey
	 */
	public String getDeviceKey() {
		return this.deviceKey;
	}

	/**
	 * @param deviceKey
	 *            the deviceKey to set
	 */
	public void setDeviceKey(String deviceKey) {
		this.deviceKey = deviceKey;
	}

}
