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
public class AppzineAppList {

	private Integer totalCount;
	private String tenantId;
	private String seq;
	private String appznNo;
	private String appType;
	private String appOrd;
	private String contentsTitl;
	private String appPid;
	private String appUrl;
	private String img480;
	private String img800;
	private String titleImg;
	private String themeUpImg;

	/**
	 * 
	 * <pre>
	 * 총 건수.
	 * </pre>
	 * 
	 * @return Integer
	 */
	public Integer getTotalCount() {
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
	 * 일련번호(SEQ_APPZINE_NEW_APP).
	 * </pre>
	 * 
	 * @return String
	 */
	public String getSeq() {
		return this.seq;
	}

	/**
	 * 
	 * <pre>
	 * 일련번호(SEQ_APPZINE_NEW_APP).
	 * </pre>
	 * 
	 * @param seq
	 *            String
	 */
	public void setSeq(String seq) {
		this.seq = seq;
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
	 * 앱 TYPE(인기:001, 신규:002, 테마:003).
	 * </pre>
	 * 
	 * @return String
	 */
	public String getAppType() {
		return this.appType;
	}

	/**
	 * 
	 * <pre>
	 * 앱 TYPE(인기:001, 신규:002, 테마:003).
	 * </pre>
	 * 
	 * @param appType
	 *            String
	 */
	public void setAppType(String appType) {
		this.appType = appType;
	}

	/**
	 * 
	 * <pre>
	 * 앱_순서.
	 * </pre>
	 * 
	 * @return String
	 */
	public String getAppOrd() {
		return this.appOrd;
	}

	/**
	 * 
	 * <pre>
	 * 앱_순서.
	 * </pre>
	 * 
	 * @param appOrd
	 *            String
	 */
	public void setAppOrd(String appOrd) {
		this.appOrd = appOrd;
	}

	/**
	 * 
	 * <pre>
	 * 컨텐츠_타이틀.
	 * </pre>
	 * 
	 * @return String
	 */
	public String getContentsTitl() {
		return this.contentsTitl;
	}

	/**
	 * 
	 * <pre>
	 * 컨텐츠_타이틀.
	 * </pre>
	 * 
	 * @param contentsTitl
	 *            String
	 */
	public void setContentsTitl(String contentsTitl) {
		this.contentsTitl = contentsTitl;
	}

	/**
	 * 
	 * <pre>
	 * 앱_PID.
	 * </pre>
	 * 
	 * @return String
	 */
	public String getAppPid() {
		return this.appPid;
	}

	/**
	 * 
	 * <pre>
	 * 앱_PID.
	 * </pre>
	 * 
	 * @param appPid
	 *            String
	 */
	public void setAppPid(String appPid) {
		this.appPid = appPid;
	}

	/**
	 * 
	 * <pre>
	 * 앱_URL.
	 * </pre>
	 * 
	 * @return String
	 */
	public String getAppUrl() {
		return this.appUrl;
	}

	/**
	 * 
	 * <pre>
	 * 앱_URL.
	 * </pre>
	 * 
	 * @param appUrl
	 *            String
	 */
	public void setAppUrl(String appUrl) {
		this.appUrl = appUrl;
	}

	/**
	 * 
	 * <pre>
	 * 이미지_480.
	 * </pre>
	 * 
	 * @return String
	 */
	public String getImg480() {
		return this.img480;
	}

	/**
	 * 
	 * <pre>
	 * 이미지_480.
	 * </pre>
	 * 
	 * @param img480
	 *            String
	 */
	public void setImg480(String img480) {
		this.img480 = img480;
	}

	/**
	 * 
	 * <pre>
	 * 이미지_800.
	 * </pre>
	 * 
	 * @return String
	 */
	public String getImg800() {
		return this.img800;
	}

	/**
	 * 
	 * <pre>
	 * 이미지_800.
	 * </pre>
	 * 
	 * @param img800
	 *            String
	 */
	public void setImg800(String img800) {
		this.img800 = img800;
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

}
