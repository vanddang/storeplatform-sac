/*
 * Copyright (c) 2013 SK planet.
 * All right reserved.
 *
 * This software is the confidential and proprietary information of SK planet.
 * You shall not disclose such Confidential Information and
 * shall use it only in accordance with the terms of the license agreement
 * you entered into with SK planet.
 */
package com.skplanet.storeplatform.sac.display.appzine.vo;

/**
 * Appzine 회차별 목록 조회 Default Value Object.
 * 
 * Updated on : 2014. 02. 10. Updated by : 유시혁.
 */
public class AppzineVolList {

	private Integer totalCount;
	private String tenantId;
	private String appznNo;
	private String appznVol;
	private String issuday;
	private String title;
	private String bgImgPath;
	private String themeHtml;
	private String expoYn;
	private String popularTitlImg480;
	private String popularTitlImg800;
	private String newTitlImg480;
	private String newTitlImg800;
	private String eventNm;
	private String eventUrl;
	private String eventImg480;
	private String eventImg800;
	private String titleImg;
	private String themeUpImg;
	private String regDt;

	/**
	 * 
	 * <pre>
	 * 총 건수.
	 * </pre>
	 * 
	 * @return Integer
	 */
	public int getTotalCount() {
		return this.totalCount;
	}

	/**
	 * 
	 * <pre>
	 * 총 건수.
	 * </pre>
	 * 
	 * @param totalCount
	 *            Integer
	 */
	public void setTotalCount(Integer totalCount) {
		this.totalCount = totalCount;
	}

	/**
	 * 
	 * <pre>
	 * 테넌트_ID.
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
	 * 테넌트_ID.
	 * </pre>
	 * 
	 * @param tenantId
	 *            String
	 */
	public void setTenantId(String tenantId) {
		this.tenantId = tenantId;
	}

	/**
	 * 
	 * <pre>
	 * 앱진_번호.
	 * </pre>
	 * 
	 * @return String
	 */
	public String getAppznNo() {
		return this.appznNo;
	}

	/**
	 * 
	 * <pre>
	 * 앱진_번호.
	 * </pre>
	 * 
	 * @param appznNo
	 *            String
	 */
	public void setAppznNo(String appznNo) {
		this.appznNo = appznNo;
	}

	/**
	 * 
	 * <pre>
	 * 앱진_호수.
	 * </pre>
	 * 
	 * @return String
	 */
	public String getAppznVol() {
		return this.appznVol;
	}

	/**
	 * 
	 * <pre>
	 * 앱진_호수.
	 * </pre>
	 * 
	 * @param appznVol
	 *            String
	 */
	public void setAppznVol(String appznVol) {
		this.appznVol = appznVol;
	}

	/**
	 * 
	 * <pre>
	 * 발행일.
	 * </pre>
	 * 
	 * @return String
	 */
	public String getIssuday() {
		return this.issuday;
	}

	/**
	 * 
	 * <pre>
	 * 발행일.
	 * </pre>
	 * 
	 * @param issuday
	 *            String
	 */
	public void setIssuday(String issuday) {
		this.issuday = issuday;
	}

	/**
	 * 
	 * <pre>
	 * 제목.
	 * </pre>
	 * 
	 * @return String
	 */
	public String getTitle() {
		return this.title;
	}

	/**
	 * 
	 * <pre>
	 * 제목.
	 * </pre>
	 * 
	 * @param title
	 *            String
	 */
	public void setTitle(String title) {
		this.title = title;
	}

	/**
	 * 
	 * <pre>
	 * backgroundImagePath.
	 * </pre>
	 * 
	 * @return String
	 */
	public String getBgImgPath() {
		return this.bgImgPath;
	}

	/**
	 * 
	 * <pre>
	 * backgroundImagePath.
	 * </pre>
	 * 
	 * @param bgImgPath
	 *            String
	 */
	public void setBgImgPath(String bgImgPath) {
		this.bgImgPath = bgImgPath;
	}

	/**
	 * 
	 * <pre>
	 * 테마_HTML.
	 * </pre>
	 * 
	 * @return String
	 */
	public String getThemeHtml() {
		return this.themeHtml;
	}

	/**
	 * 
	 * <pre>
	 * 테마_HTML.
	 * </pre>
	 * 
	 * @param themeHtml
	 *            String
	 */
	public void setThemeHtml(String themeHtml) {
		this.themeHtml = themeHtml;
	}

	/**
	 * 
	 * <pre>
	 * 노출_여부.
	 * </pre>
	 * 
	 * @return String
	 */
	public String getExpoYn() {
		return this.expoYn;
	}

	/**
	 * 
	 * <pre>
	 * 노출_여부.
	 * </pre>
	 * 
	 * @param expoYn
	 *            String
	 */
	public void setExpoYn(String expoYn) {
		this.expoYn = expoYn;
	}

	/**
	 * 
	 * <pre>
	 * 인기_타이틀_이미지_480(인기 TOP 10).
	 * </pre>
	 * 
	 * @return String
	 */
	public String getPopularTitlImg480() {
		return this.popularTitlImg480;
	}

	/**
	 * 
	 * <pre>
	 * 인기_타이틀_이미지_480(인기 TOP 10).
	 * </pre>
	 * 
	 * @param popularTitlImg480
	 *            String
	 */
	public void setPopularTitlImg480(String popularTitlImg480) {
		this.popularTitlImg480 = popularTitlImg480;
	}

	/**
	 * 
	 * <pre>
	 * 인기_타이틀_이미지_800(인기 TOP 10).
	 * </pre>
	 * 
	 * @return String
	 */
	public String getPopularTitlImg800() {
		return this.popularTitlImg800;
	}

	/**
	 * 
	 * <pre>
	 * 인기_타이틀_이미지_800(인기 TOP 10).
	 * </pre>
	 * 
	 * @param popularTitlImg800
	 *            String
	 */
	public void setPopularTitlImg800(String popularTitlImg800) {
		this.popularTitlImg800 = popularTitlImg800;
	}

	/**
	 * 
	 * <pre>
	 * NEW_TITL_IMG_480.
	 * </pre>
	 * 
	 * @return String
	 */
	public String getNewTitlImg480() {
		return this.newTitlImg480;
	}

	/**
	 * 
	 * <pre>
	 * NEW_TITL_IMG_480.
	 * </pre>
	 * 
	 * @param newTitlImg480
	 *            String
	 */
	public void setNewTitlImg480(String newTitlImg480) {
		this.newTitlImg480 = newTitlImg480;
	}

	/**
	 * 
	 * <pre>
	 * NEW_TITL_IMG_800.
	 * </pre>
	 * 
	 * @return String
	 */
	public String getNewTitlImg800() {
		return this.newTitlImg800;
	}

	/**
	 * 
	 * <pre>
	 * NEW_TITL_IMG_800.
	 * </pre>
	 * 
	 * @param newTitlImg800
	 *            String
	 */
	public void setNewTitlImg800(String newTitlImg800) {
		this.newTitlImg800 = newTitlImg800;
	}

	/**
	 * 
	 * <pre>
	 * 이벤트_명.
	 * </pre>
	 * 
	 * @return String
	 */
	public String getEventNm() {
		return this.eventNm;
	}

	/**
	 * 
	 * <pre>
	 * 이벤트_명.
	 * </pre>
	 * 
	 * @param eventNm
	 *            String
	 */
	public void setEventNm(String eventNm) {
		this.eventNm = eventNm;
	}

	/**
	 * 
	 * <pre>
	 * 이벤트_URL.
	 * </pre>
	 * 
	 * @return String
	 */
	public String getEventUrl() {
		return this.eventUrl;
	}

	/**
	 * 
	 * <pre>
	 * 이벤트_URL.
	 * </pre>
	 * 
	 * @param eventUrl
	 *            String
	 */
	public void setEventUrl(String eventUrl) {
		this.eventUrl = eventUrl;
	}

	/**
	 * 
	 * <pre>
	 * 이벤트_이미지_480.
	 * </pre>
	 * 
	 * @return String
	 */
	public String getEventImg480() {
		return this.eventImg480;
	}

	/**
	 * 
	 * <pre>
	 * 이벤트_이미지_480.
	 * </pre>
	 * 
	 * @param eventImg480
	 *            String
	 */
	public void setEventImg480(String eventImg480) {
		this.eventImg480 = eventImg480;
	}

	/**
	 * 
	 * <pre>
	 * 이벤트_이미지_800.
	 * </pre>
	 * 
	 * @return String
	 */
	public String getEventImg800() {
		return this.eventImg800;
	}

	/**
	 * 
	 * <pre>
	 * 이벤트_이미지_800.
	 * </pre>
	 * 
	 * @param eventImg800
	 *            String
	 */
	public void setEventImg800(String eventImg800) {
		this.eventImg800 = eventImg800;
	}

	/**
	 * 
	 * <pre>
	 * 제목_이미지.
	 * </pre>
	 * 
	 * @return String
	 */
	public String getTitleImg() {
		return this.titleImg;
	}

	/**
	 * 
	 * <pre>
	 * 제목_이미지.
	 * </pre>
	 * 
	 * @param titleImg
	 *            String
	 */
	public void setTitleImg(String titleImg) {
		this.titleImg = titleImg;
	}

	/**
	 * 
	 * <pre>
	 * 테마_상단_이미지.
	 * </pre>
	 * 
	 * @return String
	 */
	public String getThemeUpImg() {
		return this.themeUpImg;
	}

	/**
	 * 
	 * <pre>
	 * 테마_상단_이미지.
	 * </pre>
	 * 
	 * @param themeUpImg
	 *            String
	 */
	public void setThemeUpImg(String themeUpImg) {
		this.themeUpImg = themeUpImg;
	}

	/**
	 * 
	 * <pre>
	 * 등록일.
	 * </pre>
	 * 
	 * @return String
	 */
	public String getRegDt() {
		return this.regDt;
	}

	/**
	 * 
	 * <pre>
	 * 등록일.
	 * </pre>
	 * 
	 * @param regDt
	 *            String
	 */
	public void setRegDt(String regDt) {
		this.regDt = regDt;
	}

}
