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
 * Updated on : 2014. 02. 12. Updated by : 유시혁.
 */
public class Menu {

	private Integer totalCount;
	private Integer menuProdCnt;
	private String tenantId;
	private String menuId;
	private String systemId;
	private String menuNm;
	private String menuEngNm;
	private String menuDesc;
	private Integer menuDepth;
	private String infrMenuYn;
	private String upMenuId;
	private Integer expoOrd;
	private String targetUrl;
	private String searchFilePath;
	private String searchFileNm;
	private String bodyFilePath;
	private String bodyFileNm;
	private Integer bodyFileSize;
	private String mainOnFilePath;
	private String mainOnFileNm;
	private String mainOffFilePath;
	private String mainOffFileNm;
	private String rankFilePath;
	private String rankFileNm;
	private String useYn;
	private String menuIdType;

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
	 * 메뉴 별 상품 건수.
	 * </pre>
	 * 
	 * @return Integer
	 */
	public Integer getMenuProdCnt() {
		return this.menuProdCnt;
	}

	/**
	 * 
	 * <pre>
	 * 메뉴 별 상품 건수.
	 * </pre>
	 * 
	 * @param menuProdCnt
	 *            Integer
	 */
	public void setMenuProdCnt(Integer menuProdCnt) {
		this.menuProdCnt = menuProdCnt;
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
	 *            String
	 */
	public void setTenantId(String tenantId) {
		this.tenantId = tenantId;
	}

	/**
	 * 
	 * <pre>
	 * 메뉴_ID.
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
	 * 메뉴_ID.
	 * </pre>
	 * 
	 * @param menuId
	 *            String
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
	 * @param systemId
	 *            String
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
	 * @param menuNm
	 *            String
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
	 * @param menuEngNm
	 *            String
	 */
	public void setMenuEngNm(String menuEngNm) {
		this.menuEngNm = menuEngNm;
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
	 * @param menuDesc
	 *            String
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
	 * @return Integer
	 */
	public Integer getMenuDepth() {
		return this.menuDepth;
	}

	/**
	 * 
	 * <pre>
	 * 메뉴_깊이.
	 * </pre>
	 * 
	 * @param menuDepth
	 *            Integer
	 */
	public void setMenuDepth(Integer menuDepth) {
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
	 * @param infrMenuYn
	 *            String
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
	 * @param upMenuId
	 *            String
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
	 * @return Integer
	 */
	public Integer getExpoOrd() {
		return this.expoOrd;
	}

	/**
	 * 
	 * <pre>
	 * 노출_순서.
	 * </pre>
	 * 
	 * @param expoOrd
	 *            Integer
	 */
	public void setExpoOrd(Integer expoOrd) {
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
	 * @param targetUrl
	 *            String
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
	 * @param searchFilePath
	 *            String
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
	 * @param searchFileNm
	 *            String
	 */
	public void setSearchFileNm(String searchFileNm) {
		this.searchFileNm = searchFileNm;
	}

	/**
	 * 
	 * <pre>
	 * 검색_파일_경로.
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
	 * 검색_파일_경로.
	 * </pre>
	 * 
	 * @param bodyFilePath
	 *            String
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
	 * @param bodyFileNm
	 *            String
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
	 * @param bodyFileSize
	 *            Integer
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
	 * @param mainOnFilePath
	 *            String
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
	 * @param mainOnFileNm
	 *            String
	 */
	public void setMainOnFileNm(String mainOnFileNm) {
		this.mainOnFileNm = mainOnFileNm;
	}

	/**
	 * 
	 * <pre>
	 * MAIN_OFF_파일_경로.
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
	 * MAIN_OFF_파일_경로.
	 * </pre>
	 * 
	 * @param mainOffFilePath
	 *            String
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
	 * @param mainOffFileNm
	 *            String
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
	 * @param rankFilePath
	 *            String
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
	 * @param rankFileNm
	 *            String
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
	 * @param useYn
	 *            String
	 */
	public void setUseYn(String useYn) {
		this.useYn = useYn;
	}

	/**
	 * 
	 * <pre>
	 * 메뉴_ID_타입(어플:A,멀티미디어:M).
	 * </pre>
	 * 
	 * @return String
	 */
	public String getMenuIdType() {
		return this.menuIdType;
	}

	/**
	 * 
	 * <pre>
	 * 메뉴_ID_타입(어플:A,멀티미디어:M).
	 * </pre>
	 * 
	 * @param menuIdType
	 *            String
	 */
	public void setMenuIdType(String menuIdType) {
		this.menuIdType = menuIdType;
	}

}
