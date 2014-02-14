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
	private String tenantId; // tenant id
	private String menuId; // menu id
	private String systemId; // system id
	private String menuName; // menu name
	private String menuEnglishName; // menu English name
	private String menuDescription; // menu description
	private Integer menuDepth; // menu depth
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

	public String getTenantId() {
		return this.tenantId;
	}

	public void setTenantId(String tenantId) {
		this.tenantId = tenantId;
	}

	public String getMenuId() {
		return this.menuId;
	}

	public void setMenuId(String menuId) {
		this.menuId = menuId;
	}

	public String getSystemId() {
		return this.systemId;
	}

	public void setSystemId(String systemId) {
		this.systemId = systemId;
	}

	public String getMenuName() {
		return this.menuName;
	}

	public void setMenuName(String menuName) {
		this.menuName = menuName;
	}

	public Integer getMenuDepth() {
		return this.menuDepth;
	}

	public void setMenuDepth(Integer menuDepth) {
		this.menuDepth = menuDepth;
	}

	public String getInfrMenuYn() {
		return this.infrMenuYn;
	}

	public void setInfrMenuYn(String infrMenuYn) {
		this.infrMenuYn = infrMenuYn;
	}

	public String getUpMenuId() {
		return this.upMenuId;
	}

	public void setUpMenuId(String upMenuId) {
		this.upMenuId = upMenuId;
	}

	public String getTargetUrl() {
		return this.targetUrl;
	}

	public void setTargetUrl(String targetUrl) {
		this.targetUrl = targetUrl;
	}

	public String getSearchFilePath() {
		return this.searchFilePath;
	}

	public void setSearchFilePath(String searchFilePath) {
		this.searchFilePath = searchFilePath;
	}

	public String getSearchFileName() {
		return this.searchFileName;
	}

	public void setSearchFileName(String searchFileName) {
		this.searchFileName = searchFileName;
	}

	public String getBodyFilePath() {
		return this.bodyFilePath;
	}

	public void setBodyFilePath(String bodyFilePath) {
		this.bodyFilePath = bodyFilePath;
	}

	public String getBodyFileName() {
		return this.bodyFileName;
	}

	public void setBodyFileName(String bodyFileName) {
		this.bodyFileName = bodyFileName;
	}

	public Integer getBodyFileSize() {
		return this.bodyFileSize;
	}

	public void setBodyFileSize(Integer bodyFileSize) {
		this.bodyFileSize = bodyFileSize;
	}

	public String getMainOnFilePath() {
		return this.mainOnFilePath;
	}

	public void setMainOnFilePath(String mainOnFilePath) {
		this.mainOnFilePath = mainOnFilePath;
	}

	public String getMainOnFileName() {
		return this.mainOnFileName;
	}

	public void setMainOnFileName(String mainOnFileName) {
		this.mainOnFileName = mainOnFileName;
	}

	public String getMainOffFilePath() {
		return this.mainOffFilePath;
	}

	public void setMainOffFilePath(String mainOffFilePath) {
		this.mainOffFilePath = mainOffFilePath;
	}

	public String getMainOffFileName() {
		return this.mainOffFileName;
	}

	public void setMainOffFileName(String mainOffFileName) {
		this.mainOffFileName = mainOffFileName;
	}

	public String getRankFilePath() {
		return this.rankFilePath;
	}

	public void setRankFilePath(String rankFilePath) {
		this.rankFilePath = rankFilePath;
	}

	public String getRankFileName() {
		return this.rankFileName;
	}

	public void setRankFileName(String rankFileName) {
		this.rankFileName = rankFileName;
	}

	public String getUseYn() {
		return this.useYn;
	}

	public void setUseYn(String useYn) {
		this.useYn = useYn;
	}

	public String getMenuIdType() {
		return this.menuIdType;
	}

	public void setMenuIdType(String menuIdType) {
		this.menuIdType = menuIdType;
	}

	public Integer getMenuProductCount() {
		return this.menuProductCount;
	}

	public void setMenuProductCount(Integer menuProductCount) {
		this.menuProductCount = menuProductCount;
	}

	public String getMenuEnglishName() {
		return this.menuEnglishName;
	}

	public void setMenuEnglishName(String menuEnglishName) {
		this.menuEnglishName = menuEnglishName;
	}

	public String getMenuDescription() {
		return this.menuDescription;
	}

	public void setMenuDescription(String menuDescription) {
		this.menuDescription = menuDescription;
	}

	public Integer getExposureOrder() {
		return this.exposureOrder;
	}

	public void setExposureOrder(Integer exposureOrder) {
		this.exposureOrder = exposureOrder;
	}

	public List<MenuDetail> getSubMenuDetailList() {
		return this.subMenuDetailList;
	}

	public void setSubMenuDetailList(List<MenuDetail> subMenuDetailList) {
		this.subMenuDetailList = subMenuDetailList;
	}

}
