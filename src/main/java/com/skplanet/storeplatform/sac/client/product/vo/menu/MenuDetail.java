/*
 * Copyright (c) 2013 SK planet.
 * All right reserved.
 *
 * This software is the confidential and proprietary information of SK planet.
 * You shall not disclose such Confidential Information and
 * shall use it only in accordance with the terms of the license agreement
 * you entered into with SK planet.
 */
package com.skplanet.storeplatform.sac.client.product.vo.menu;

import java.io.Serializable;

import com.skplanet.storeplatform.framework.core.annotation.ProtobufMapping;
import com.skplanet.storeplatform.framework.core.common.vo.CommonVO;
import com.skplanet.storeplatform.sac.client.intfmessage.common.vo.MenuDetailProto;

/**
 * Interface Message Menu Value Object.
 * 
 * Updated on : 2013. 12. 19 Updated by : 윤주영, SK 플래닛.
 */
@ProtobufMapping(MenuDetailProto.MenuDetail.class)
public class MenuDetail extends CommonVO implements Serializable {

	private static final long serialVersionUID = 11123123123L;

	private String tenantId = ""; // tenant id
	private String menuId = ""; // menu id
	private String systemId = ""; // system id
	private String menuName = ""; // menu name
	private String menuEngName = ""; // menu English name
	private String menuDesc = ""; // menu description
	private String menuDepth = ""; // menu depth
	private String infrMenuYn = "";
	private String upMenuId = "";
	private String expoOrd = "";
	private String targetUrl = "";
	private String searchFilePath = "";
	private String searchFileName = "";
	private String lnbFilePath = "";
	private String lnbFileName = "";
	private String lnbFileSize = "";
	private String bodyFilePath = "";
	private String bodyFileName = "";
	private String bodyFileSize = "";
	private String mainOnFilePath = "";
	private String mainOnFileName = "";
	private String mainOffFilePath = "";
	private String mainOffFileName = "";
	private String rankFilePath = "";
	private String rankFileName = "";
	private String useYn = "";

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

	public String getMenuEngName() {
		return this.menuEngName;
	}

	public void setMenuEngName(String menuEngName) {
		this.menuEngName = menuEngName;
	}

	public String getMenuDesc() {
		return this.menuDesc;
	}

	public void setMenuDesc(String menuDesc) {
		this.menuDesc = menuDesc;
	}

	public String getMenuDepth() {
		return this.menuDepth;
	}

	public void setMenuDepth(String menuDepth) {
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

	public String getExpoOrd() {
		return this.expoOrd;
	}

	public void setExpoOrd(String expoOrd) {
		this.expoOrd = expoOrd;
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

	public String getLnbFilePath() {
		return this.lnbFilePath;
	}

	public void setLnbFilePath(String lnbFilePath) {
		this.lnbFilePath = lnbFilePath;
	}

	public String getLnbFileName() {
		return this.lnbFileName;
	}

	public void setLnbFileName(String lnbFileName) {
		this.lnbFileName = lnbFileName;
	}

	public String getLnbFileSize() {
		return this.lnbFileSize;
	}

	public void setLnbFileSize(String lnbFileSize) {
		this.lnbFileSize = lnbFileSize;
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

	public String getBodyFileSize() {
		return this.bodyFileSize;
	}

	public void setBodyFileSize(String bodyFileSize) {
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

	public static long getSerialversionuid() {
		return serialVersionUID;
	}
}
