package com.skplanet.storeplatform.sac.client.display.vo.webtoon;

import org.hibernate.validator.constraints.NotBlank;

/**
 * 웹툰 상품 상세 조회 Request Value Object.
 * 
 * Updated on : 2014. 2. 19. Updated by : 조준일, nTels.
 */
public class WebtoonDetailSacReq {

	@NotBlank
	private String prodId; // 상품ID
	private String deviceModelCd; // 단말 모델 코드
	private String tenantId; // 테넌트ID
	private String imageCd; // 이미지 코드
	private String langCd; // 언어

	private String orderedBy; // 이전회, 다음회 정렬 순서
	private String chapter; // 상세 현재 챕터

	/**
	 * 
	 * <pre>
	 * 상품ID.
	 * </pre>
	 * 
	 * @return String
	 */
	public String getProdId() {
		return this.prodId;
	}

	/**
	 * 
	 * <pre>
	 * 상품ID.
	 * </pre>
	 * 
	 * @param prodId
	 *            prodId
	 */
	public void setProdId(String prodId) {
		this.prodId = prodId;
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
	 * 테넌트ID.
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
	 * 테넌트ID.
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
	 * 이미지코드.
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
	 * 이미지코드.
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
	 * 이전회, 다음회 정렬 순서.
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
	 * 이전회, 다음회 정렬 순서.
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
	 * 현재 회차.
	 * </pre>
	 * 
	 * @return String
	 */
	public String getChapter() {
		return this.chapter;
	}

	/**
	 * 
	 * <pre>
	 * 현재 회차.
	 * </pre>
	 * 
	 * @param chapter
	 *            chapter
	 */
	public void setChapter(String chapter) {
		this.chapter = chapter;
	}

}
