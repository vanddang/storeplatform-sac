/*
 * Copyright (c) 2013 SK planet.
 * All right reserved.
 *
 * This software is the confidential and proprietary information of SK planet.
 * You shall not disclose such Confidential Information and
 * shall use it only in accordance with the terms of the license agreement
 * you entered into with SK planet.
 */
package com.skplanet.storeplatform.sac.display.menu.vo;

/**
 * 메뉴 조회 Default Value Object.
 * 
 * Updated on : 2013. 12. 19. Updated by : 윤주영, SK 플래닛.
 */
public class MenuDetailDTO {

	private int totalCount;

	private String tenantId = ""; // tenant id
	private String menuId = ""; // menu id
	private String systemId = ""; // system id
	private String menuNm = ""; // menu Nm
	private String menuEngNm = ""; // menu English Nm
	private String menuDesc = ""; // menu description
	private String menuDepth = ""; // menu depth
	private String infrMenuYn = "";
	private String upMenuId = "";
	private String expoOrd = "";
	private String targetUrl = "";
	private String searchFilePath = "";
	private String searchFileNm = "";
	private String bodyFilePath = "";
	private String bodyFileNm = "";
	private Integer bodyFileSize;
	private String mainOnFilePath = "";
	private String mainOnFileNm = "";
	private String mainOffFilePath = "";
	private String mainOffFileNm = "";
	private String rankFilePath = "";
	private String rankFileNm = "";
	private String useYn = "";

	/**
	 * 
	 * <pre>
	 * 전체건수.
	 * </pre>
	 * 
	 * @return int
	 */
	public int getTotalCount() {
		return this.totalCount;
	}

	/**
	 * 
	 * <pre>
	 * 전체건수.
	 * </pre>
	 * 
	 * @param int totalCount
	 */
	public void setTotalCount(int totalCount) {
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
	 * @param String
	 *            tenantId
	 */
	public void setTenantId(String tenantId) {
		this.tenantId = tenantId;
	}

	/**
	 * 
	 * <pre>
	 * 테넌트_메뉴.
	 * </pre>
	 * 
	 * @return String
	 */
	public String getMenuId() {
		return this.menuId;
	}

	/**
	 * 
	 * <pre>
	 * 테넌트_메뉴.
	 * </pre>
	 * 
	 * @param String
	 *            menuId
	 */
	public void setMenuId(String menuId) {
		this.menuId = menuId;
	}

	/**
	 * 
	 * <pre>
	 * 시스템_ID.
	 * </pre>
	 * 
	 * @return String
	 */
	public String getSystemId() {
		return this.systemId;
	}

	/**
	 * 
	 * <pre>
	 * 시스템_ID.
	 * </pre>
	 * 
	 * @param String
	 *            systemId
	 */
	public void setSystemId(String systemId) {
		this.systemId = systemId;
	}

	/**
	 * 
	 * <pre>
	 * 메뉴_명.
	 * </pre>
	 * 
	 * @return String
	 */
	public String getMenuNm() {
		return this.menuNm;
	}

	/**
	 * 
	 * <pre>
	 * 메뉴_명.
	 * </pre>
	 * 
	 * @param String
	 *            menuNm
	 */
	public void setMenuNm(String menuNm) {
		this.menuNm = menuNm;
	}

	/**
	 * 
	 * <pre>
	 * 메뉴_영문_명.
	 * </pre>
	 * 
	 * @return String
	 */
	public String getMenuEngNm() {
		return this.menuEngNm;
	}

	/**
	 * 
	 * <pre>
	 * 메뉴_영문_명.
	 * </pre>
	 * 
	 * @param String
	 *            menuEngNm
	 */
	public void setMenuEngNm(String menuEngNm) {
		this.menuEngNm = this.menuEngNm;
	}

	/**
	 * 
	 * <pre>
	 * 메뉴_설명.
	 * </pre>
	 * 
	 * @return String
	 */
	public String getMenuDesc() {
		return this.menuDesc;
	}

	/**
	 * 
	 * <pre>
	 * 메뉴_설명.
	 * </pre>
	 * 
	 * @param String
	 *            menuDesc
	 */
	public void setMenuDesc(String menuDesc) {
		this.menuDesc = menuDesc;
	}

	/**
	 * 
	 * <pre>
	 * 메뉴_깊이.
	 * </pre>
	 * 
	 * @return String
	 */
	public String getMenuDepth() {
		return this.menuDepth;
	}

	/**
	 * 
	 * <pre>
	 * 메뉴_깊이.
	 * </pre>
	 * 
	 * @param String
	 *            menuDepth
	 */
	public void setMenuDepth(String menuDepth) {
		this.menuDepth = menuDepth;
	}

	/**
	 * 
	 * <pre>
	 * 하위_메뉴_여부.
	 * </pre>
	 * 
	 * @return String
	 */
	public String getInfrMenuYn() {
		return this.infrMenuYn;
	}

	/**
	 * 
	 * <pre>
	 * 하위_메뉴_여부.
	 * </pre>
	 * 
	 * @param String
	 *            infrMenuYn
	 */
	public void setInfrMenuYn(String infrMenuYn) {
		this.infrMenuYn = infrMenuYn;
	}

	/**
	 * 
	 * <pre>
	 * 상위_메뉴_ID.
	 * </pre>
	 * 
	 * @return String
	 */
	public String getUpMenuId() {
		return this.upMenuId;
	}

	/**
	 * 
	 * <pre>
	 * 상위_메뉴_ID.
	 * </pre>
	 * 
	 * @param String
	 *            upMenuId
	 */
	public void setUpMenuId(String upMenuId) {
		this.upMenuId = upMenuId;
	}

	/**
	 * 
	 * <pre>
	 * 노출_순서.
	 * </pre>
	 * 
	 * @return String
	 */
	public String getExpoOrd() {
		return this.expoOrd;
	}

	/**
	 * 
	 * <pre>
	 * 노출_순서.
	 * </pre>
	 * 
	 * @param String
	 *            expoOrd
	 */
	public void setExpoOrd(String expoOrd) {
		this.expoOrd = expoOrd;
	}

	/**
	 * 
	 * <pre>
	 * 대상_URL.
	 * </pre>
	 * 
	 * @return String
	 */
	public String getTargetUrl() {
		return this.targetUrl;
	}

	/**
	 * 
	 * <pre>
	 * 대상_URL.
	 * </pre>
	 * 
	 * @param String
	 *            targetUrl
	 */
	public void setTargetUrl(String targetUrl) {
		this.targetUrl = targetUrl;
	}

	/**
	 * 
	 * <pre>
	 * 검색_파일_경로.
	 * </pre>
	 * 
	 * @return String
	 */
	public String getSearchFilePath() {
		return this.searchFilePath;
	}

	/**
	 * 
	 * <pre>
	 * 검색_파일_경로.
	 * </pre>
	 * 
	 * @param String
	 *            searchFilePath
	 */
	public void setSearchFilePath(String searchFilePath) {
		this.searchFilePath = searchFilePath;
	}

	/**
	 * 
	 * <pre>
	 * 검색_파일_명.
	 * </pre>
	 * 
	 * @return String
	 */
	public String getSearchFileNm() {
		return this.searchFileNm;
	}

	/**
	 * 
	 * <pre>
	 * 검색_파일_명.
	 * </pre>
	 * 
	 * @param String
	 *            searchFileNm
	 */
	public void setSearchFileNm(String searchFileNm) {
		this.searchFileNm = searchFileNm;
	}

	/**
	 * 
	 * <pre>
	 * BODY_파일_경로.
	 * </pre>
	 * 
	 * @return String
	 */
	public String getBodyFilePath() {
		return this.bodyFilePath;
	}

	/**
	 * 
	 * <pre>
	 * BODY_파일_경로.
	 * </pre>
	 * 
	 * @param String
	 *            bodyFilePath
	 */
	public void setBodyFilePath(String bodyFilePath) {
		this.bodyFilePath = bodyFilePath;
	}

	/**
	 * 
	 * <pre>
	 * BODY_파일_명.
	 * </pre>
	 * 
	 * @return String
	 */
	public String getBodyFileNm() {
		return this.bodyFileNm;
	}

	/**
	 * 
	 * <pre>
	 * BODY_파일_명.
	 * </pre>
	 * 
	 * @param String
	 *            bodyFileNm
	 */
	public void setBodyFileNm(String bodyFileNm) {
		this.bodyFileNm = bodyFileNm;
	}

	/**
	 * 
	 * <pre>
	 * BODY_파일_크기.
	 * </pre>
	 * 
	 * @return Integer
	 */
	public Integer getBodyFileSize() {
		return this.bodyFileSize;
	}

	/**
	 * 
	 * <pre>
	 * BODY_파일_크기.
	 * </pre>
	 * 
	 * @param Integer
	 *            bodyFileSize
	 */
	public void setBodyFileSize(Integer bodyFileSize) {
		this.bodyFileSize = bodyFileSize;
	}

	/**
	 * 
	 * <pre>
	 * MAIN_ON_파일_경로.
	 * </pre>
	 * 
	 * @return String
	 */
	public String getMainOnFilePath() {
		return this.mainOnFilePath;
	}

	/**
	 * 
	 * <pre>
	 * MAIN_ON_파일_경로.
	 * </pre>
	 * 
	 * @param String
	 *            mainOnFilePath
	 */
	public void setMainOnFilePath(String mainOnFilePath) {
		this.mainOnFilePath = mainOnFilePath;
	}

	/**
	 * 
	 * <pre>
	 * MAIN_ON_파일_명.
	 * </pre>
	 * 
	 * @return String
	 */
	public String getMainOnFileNm() {
		return this.mainOnFileNm;
	}

	/**
	 * 
	 * <pre>
	 * MAIN_ON_파일_명.
	 * </pre>
	 * 
	 * @param String
	 *            mainOnFileNm
	 */
	public void setMainOnFileNm(String mainOnFileNm) {
		this.mainOnFileNm = mainOnFileNm;
	}

	/**
	 * 
	 * <pre>
	 * MAIN_ON_파일_경로.
	 * </pre>
	 * 
	 * @return String
	 */
	public String getMainOffFilePath() {
		return this.mainOffFilePath;
	}

	/**
	 * 
	 * <pre>
	 * MAIN_ON_파일_경로.
	 * </pre>
	 * 
	 * @param String
	 *            mainOffFilePath
	 */
	public void setMainOffFilePath(String mainOffFilePath) {
		this.mainOffFilePath = mainOffFilePath;
	}

	/**
	 * 
	 * <pre>
	 * MAIN_OFF_파일_명.
	 * </pre>
	 * 
	 * @return String
	 */
	public String getMainOffFileNm() {
		return this.mainOffFileNm;
	}

	/**
	 * 
	 * <pre>
	 * MAIN_OFF_파일_명.
	 * </pre>
	 * 
	 * @param String
	 *            mainOffFileNm
	 */
	public void setMainOffFileNm(String mainOffFileNm) {
		this.mainOffFileNm = mainOffFileNm;
	}

	/**
	 * 
	 * <pre>
	 * 랭킹_파일_경로.
	 * </pre>
	 * 
	 * @return String
	 */
	public String getRankFilePath() {
		return this.rankFilePath;
	}

	/**
	 * 
	 * <pre>
	 * 랭킹_파일_경로.
	 * </pre>
	 * 
	 * @param String
	 *            rankFilePath
	 */
	public void setRankFilePath(String rankFilePath) {
		this.rankFilePath = rankFilePath;
	}

	/**
	 * 
	 * <pre>
	 * 랭킹_파일_명.
	 * </pre>
	 * 
	 * @return String
	 */
	public String getRankFileNm() {
		return this.rankFileNm;
	}

	/**
	 * 
	 * <pre>
	 * 랭킹_파일_명.
	 * </pre>
	 * 
	 * @param String
	 *            rankFileNm
	 */
	public void setRankFileNm(String rankFileNm) {
		this.rankFileNm = rankFileNm;
	}

	/**
	 * 
	 * <pre>
	 * 사용_여부.
	 * </pre>
	 * 
	 * @return String
	 */
	public String getUseYn() {
		return this.useYn;
	}

	/**
	 * 
	 * <pre>
	 * 사용_여부.
	 * </pre>
	 * 
	 * @param String
	 *            useYn
	 */
	public void setUseYn(String useYn) {
		this.useYn = useYn;
	}
}
