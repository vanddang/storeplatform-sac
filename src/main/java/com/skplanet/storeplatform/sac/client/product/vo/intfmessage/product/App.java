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
	private String aid; // Applicaton ID
	private String supportedOs; // 지원 OS
	private String packageName; // 패키지명
	private String versionCode; // 패키지 버전 코드
	private String version; // App 상품 버전
	private Integer size; // 용량 (byte단위)
	private Date date; // 상품 등록일자
	private History history; // update history
	private Install install; // Install 정보
	private String filePath; // 파일경로

	/**
	 * 
	 * <pre>
	 * Applicaton ID.
	 * </pre>
	 * 
	 * @return String
	 */
	public String getAid() {
		return this.aid;
	}

	/**
	 * 
	 * <pre>
	 * Applicaton ID.
	 * </pre>
	 * 
	 * @param aid
	 *            aid
	 */
	public void setAid(String aid) {
		this.aid = aid;
	}

	/**
	 * 
	 * <pre>
	 * 지원 OS.
	 * </pre>
	 * 
	 * @return String
	 */
	public String getSupportedOs() {
		return this.supportedOs;
	}

	/**
	 * 
	 * <pre>
	 * 지원 OS.
	 * </pre>
	 * 
	 * @param supportedOs
	 *            supportedOs
	 */
	public void setSupportedOs(String supportedOs) {
		this.supportedOs = supportedOs;
	}

	/**
	 * 
	 * <pre>
	 * 패키지명.
	 * </pre>
	 * 
	 * @return String
	 */
	public String getPackageName() {
		return this.packageName;
	}

	/**
	 * 
	 * <pre>
	 * 패키지명.
	 * </pre>
	 * 
	 * @param packageName
	 *            packageName
	 */
	public void setPackageName(String packageName) {
		this.packageName = packageName;
	}

	/**
	 * 
	 * <pre>
	 * 패키지 버전 코드.
	 * </pre>
	 * 
	 * @return String
	 */
	public String getVersionCode() {
		return this.versionCode;
	}

	/**
	 * 
	 * <pre>
	 * 패키지 버전 코드.
	 * </pre>
	 * 
	 * @param versionCode
	 *            versionCode
	 */
	public void setVersionCode(String versionCode) {
		this.versionCode = versionCode;
	}

	/**
	 * 
	 * <pre>
	 * App 상품 버전.
	 * </pre>
	 * 
	 * @return String
	 */
	public String getVersion() {
		return this.version;
	}

	/**
	 * 
	 * <pre>
	 * App 상품 버전.
	 * </pre>
	 * 
	 * @param version
	 *            version
	 */
	public void setVersion(String version) {
		this.version = version;
	}

	/**
	 * 
	 * <pre>
	 * 용량 (byte단위).
	 * </pre>
	 * 
	 * @return Integer
	 */
	public Integer getSize() {
		return this.size;
	}

	/**
	 * 
	 * <pre>
	 * 용량 (byte단위).
	 * </pre>
	 * 
	 * @param size
	 *            size
	 */
	public void setSize(Integer size) {
		this.size = size;
	}

	/**
	 * 
	 * <pre>
	 * 상품 등록일자.
	 * </pre>
	 * 
	 * @return Date
	 */
	public Date getDate() {
		return this.date;
	}

	/**
	 * 
	 * <pre>
	 * 상품 등록일자.
	 * </pre>
	 * 
	 * @param date
	 *            date
	 */
	public void setDate(Date date) {
		this.date = date;
	}

	/**
	 * 
	 * <pre>
	 * update history.
	 * </pre>
	 * 
	 * @return History
	 */
	public History getHistory() {
		return this.history;
	}

	/**
	 * 
	 * <pre>
	 * update history.
	 * </pre>
	 * 
	 * @param history
	 *            history
	 */
	public void setHistory(History history) {
		this.history = history;
	}

	/**
	 * 
	 * <pre>
	 * Install 정보.
	 * </pre>
	 * 
	 * @return Install
	 */
	public Install getInstall() {
		return this.install;
	}

	/**
	 * 
	 * <pre>
	 * Debug 정보.
	 * </pre>
	 * 
	 * @param install
	 *            install
	 */
	public void setInstall(Install install) {
		this.install = install;
	}

	/**
	 * 
	 * <pre>
	 * 파일 경로.
	 * </pre>
	 * 
	 * @return String
	 */
	public String getFilePath() {
		return this.filePath;
	}

	/**
	 * 
	 * <pre>
	 * 파일 경로.
	 * </pre>
	 * 
	 * @param filePath
	 *            filePath
	 */
	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}

}
