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

	private int totalCount;
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

	public int getTotalCount() {
		return this.totalCount;
	}

	public void setTotalCount(int totalCount) {
		this.totalCount = totalCount;
	}

	public String getTenantId() {
		return this.tenantId;
	}

	public void setTenantId(String tenantId) {
		this.tenantId = tenantId;
	}

	public String getSeq() {
		return this.seq;
	}

	public void setSeq(String seq) {
		this.seq = seq;
	}

	public String getAppznNo() {
		return this.appznNo;
	}

	public void setAppznNo(String appznNo) {
		this.appznNo = appznNo;
	}

	public String getAppType() {
		return this.appType;
	}

	public void setAppType(String appType) {
		this.appType = appType;
	}

	public String getAppOrd() {
		return this.appOrd;
	}

	public void setAppOrd(String appOrd) {
		this.appOrd = appOrd;
	}

	public String getContentsTitl() {
		return this.contentsTitl;
	}

	public void setContentsTitl(String contentsTitl) {
		this.contentsTitl = contentsTitl;
	}

	public String getAppPid() {
		return this.appPid;
	}

	public void setAppPid(String appPid) {
		this.appPid = appPid;
	}

	public String getAppUrl() {
		return this.appUrl;
	}

	public void setAppUrl(String appUrl) {
		this.appUrl = appUrl;
	}

	public String getImg480() {
		return this.img480;
	}

	public void setImg480(String img480) {
		this.img480 = img480;
	}

	public String getImg800() {
		return this.img800;
	}

	public void setImg800(String img800) {
		this.img800 = img800;
	}

}
