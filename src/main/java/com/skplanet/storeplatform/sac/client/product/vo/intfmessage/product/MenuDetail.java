/*
 * Copyright (c) 2013 SK planet.
 * All right reserved.
 *
 * This software is the confidential and proprietary information of SK planet.
 * You shall not disclose such Confidential Information and
 * shall use it only in accordance with the terms of the license agreement
 * you entered into with SK planet.
 */
package com.skplanet.storeplatform.sac.client.product.vo.intfmessage.product;

import java.util.List;

import org.codehaus.jackson.map.annotate.JsonSerialize;

import com.skplanet.storeplatform.framework.core.common.vo.CommonInfo;

/**
 * Interface Message Menu Value Object.
 * 
 * Updated on : 2014. 02. 12. Updated by : 유시혁.
 */
@JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
public class MenuDetail extends CommonInfo {

	private static final long serialVersionUID = 11123123123L;

	private Integer menuProductCount;
	private String tenantId;
	private String menuId;
	private String systemId;
	private String menuName;
	private String menuEnglishName;
	private String menuDescription;
	private Integer menuDepth;
	private String infrMenuYn;
	private String upMenuId;
	private Integer exposureOrder;
	private String targetUrl;
	private String searchFilePath;
	private String searchFileName;
	private String bodyFilePath;
	private String bodyFileName;
	private Integer bodyFileSize;
	private String mainOnFilePath;
	private String mainOnFileName;
	private String mainOffFilePath;
	private String mainOffFileName;
	private String rankFilePath;
	private String rankFileName;
	private String useYn;
	private String menuIdType;
	private List<MenuDetail> subMenuDetailList;

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
	public String getMenuName() {
		return this.menuName;
	}

	/**
	 * 
	 * <pre>
	 * 메뉴_명.
	 * </pre>
	 * 
	 * @param menuName
	 *            String
	 */
	public void setMenuName(String menuName) {
		this.menuName = menuName;
	}

	/**
	 * 
	 * <pre>
	 * 메뉴_깊이.
	 * </pre>
	 * 
	 * @return String
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
	public String getSearchFileName() {
		return this.searchFileName;
	}

	/**
	 * 
	 * <pre>
	 * 검색_파일_명.
	 * </pre>
	 * 
	 * @param searchFileName
	 *            String
	 */
	public void setSearchFileName(String searchFileName) {
		this.searchFileName = searchFileName;
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
	public String getBodyFileName() {
		return this.bodyFileName;
	}

	/**
	 * 
	 * <pre>
	 * BODY_파일_명.
	 * </pre>
	 * 
	 * @param bodyFileName
	 *            String
	 */
	public void setBodyFileName(String bodyFileName) {
		this.bodyFileName = bodyFileName;
	}

	/**
	 * 
	 * <pre>
	 * BODY_파일_크기.
	 * </pre>
	 * 
	 * @return String
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
	public String getMainOnFileName() {
		return this.mainOnFileName;
	}

	/**
	 * 
	 * <pre>
	 * MAIN_ON_파일_명.
	 * </pre>
	 * 
	 * @param mainOnFileName
	 *            String
	 */
	public void setMainOnFileName(String mainOnFileName) {
		this.mainOnFileName = mainOnFileName;
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
	public String getMainOffFileName() {
		return this.mainOffFileName;
	}

	/**
	 * 
	 * <pre>
	 * MAIN_OFF_파일_명.
	 * </pre>
	 * 
	 * @param mainOffFileName
	 *            String
	 */
	public void setMainOffFileName(String mainOffFileName) {
		this.mainOffFileName = mainOffFileName;
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
	public String getRankFileName() {
		return this.rankFileName;
	}

	/**
	 * 
	 * <pre>
	 * 랭킹_파일_명.
	 * </pre>
	 * 
	 * @param rankFileName
	 *            String
	 */
	public void setRankFileName(String rankFileName) {
		this.rankFileName = rankFileName;
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

	/**
	 * 
	 * <pre>
	 * 메뉴 별 상품 건수.
	 * </pre>
	 * 
	 * @return String
	 */
	public Integer getMenuProductCount() {
		return this.menuProductCount;
	}

	/**
	 * 
	 * <pre>
	 * 메뉴 별 상품 건수.
	 * </pre>
	 * 
	 * @param menuProductCount
	 *            Integer
	 */
	public void setMenuProductCount(Integer menuProductCount) {
		this.menuProductCount = menuProductCount;
	}

	/**
	 * 
	 * <pre>
	 * 메뉴_영문_명.
	 * </pre>
	 * 
	 * @return String
	 */
	public String getMenuEnglishName() {
		return this.menuEnglishName;
	}

	/**
	 * 
	 * <pre>
	 * 메뉴_영문_명.
	 * </pre>
	 * 
	 * @param menuEnglishName
	 *            String
	 */
	public void setMenuEnglishName(String menuEnglishName) {
		this.menuEnglishName = menuEnglishName;
	}

	/**
	 * 
	 * <pre>
	 * 메뉴_설명.
	 * </pre>
	 * 
	 * @return String
	 */
	public String getMenuDescription() {
		return this.menuDescription;
	}

	/**
	 * 
	 * <pre>
	 * 메뉴_설명.
	 * </pre>
	 * 
	 * @param menuDescription
	 *            String
	 */
	public void setMenuDescription(String menuDescription) {
		this.menuDescription = menuDescription;
	}

	/**
	 * 
	 * <pre>
	 * 노출_순서.
	 * </pre>
	 * 
	 * @return String
	 */
	public Integer getExposureOrder() {
		return this.exposureOrder;
	}

	/**
	 * 
	 * <pre>
	 * 노출_순서.
	 * </pre>
	 * 
	 * @param exposureOrder
	 *            Integer
	 */
	public void setExposureOrder(Integer exposureOrder) {
		this.exposureOrder = exposureOrder;
	}

	/**
	 * 
	 * <pre>
	 * 하위 메뉴 상세 리스트.
	 * </pre>
	 * 
	 * @return String
	 */
	public List<MenuDetail> getSubMenuDetailList() {
		return this.subMenuDetailList;
	}

	/**
	 * 
	 * <pre>
	 * 하위 메뉴 상세 리스트.
	 * </pre>
	 * 
	 * @param subMenuDetailList
	 *            String
	 */
	public void setSubMenuDetailList(List<MenuDetail> subMenuDetailList) {
		this.subMenuDetailList = subMenuDetailList;
	}

}
