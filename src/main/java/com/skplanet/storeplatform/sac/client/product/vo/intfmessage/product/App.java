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

import java.io.Serializable;

import org.codehaus.jackson.map.annotate.JsonSerialize;

import com.skplanet.storeplatform.framework.core.common.vo.CommonInfo;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.common.Date;

/**
 * Interface Message App Value Object.
 * 
 * Updated on : 2013. 12. 17. Updated by : 오승민, Incross.
 */
@JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
public class App extends CommonInfo implements Serializable {
	private static final long serialVersionUID = 1L;
	/**
	 * Applicaton ID
	 */
	private String aid;
	/**
	 * 지원 OS
	 */
	private String supportedOs;
	/**
	 * 패키지명
	 */
	private String packageName;
	/**
	 * 패키지 버전 코드
	 */
	private String versionCode;
	/**
	 * App 상품 버전
	 */
	private String version;
	/**
	 * 용량 (byte단위)
	 */
	private String size;
	/**
	 * 상품 등록일자
	 */
	private Date date;
	/**
	 * Debug 정보
	 */
	private AppDebug appDebug;
	/**
	 * update history
	 */
	private History history;
	/**
	 * Install 정보
	 */
	private Install install;

	public String getAid() {
		return this.aid;
	}

	public void setAid(String aid) {
		this.aid = aid;
	}

	public String getSupportedOs() {
		return this.supportedOs;
	}

	public void setSupportedOs(String supportedOs) {
		this.supportedOs = supportedOs;
	}

	public String getPackageName() {
		return this.packageName;
	}

	public void setPackageName(String packageName) {
		this.packageName = packageName;
	}

	public String getVersionCode() {
		return this.versionCode;
	}

	public void setVersionCode(String versionCode) {
		this.versionCode = versionCode;
	}

	public String getVersion() {
		return this.version;
	}

	public void setVersion(String version) {
		this.version = version;
	}

	public String getSize() {
		return this.size;
	}

	public void setSize(String size) {
		this.size = size;
	}

	public Date getDate() {
		return this.date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public AppDebug getAppDebug() {
		return this.appDebug;
	}

	public void setAppDebug(AppDebug appDebug) {
		this.appDebug = appDebug;
	}

	public History getHistory() {
		return this.history;
	}

	public void setHistory(History history) {
		this.history = history;
	}

	public Install getInstall() {
		return this.install;
	}

	public void setInstall(Install install) {
		this.install = install;
	}

}
