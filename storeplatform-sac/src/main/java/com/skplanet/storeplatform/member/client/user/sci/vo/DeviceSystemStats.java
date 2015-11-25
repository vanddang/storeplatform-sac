/*
 * Copyright (c) 2013 SK planet.
 * All right reserved.
 * 
 * This software is the confidential and proprietary information of SK planet.
 * You shall not disclose such Confidential Information and
 * shall use it only in accordance with the terms of the license agreement
 * you entered into with SK planet.
 */
package com.skplanet.storeplatform.member.client.user.sci.vo;

import java.io.Serializable;

import com.skplanet.storeplatform.framework.core.common.vo.CommonInfo;

/**
 * 누적 가입자수 통계 Value Object
 * 
 * Updated on : 2014. 02. 11. Updated by : wisestone_mikepark
 */
public class DeviceSystemStats extends CommonInfo implements Serializable {

	/** The Constant serial Version UID. */
	private static final long serialVersionUID = 1L;

	/** 테넌트 ID. */
	private String tenantID; // TENANT_ID

	/** 등록일시. */
	private String regDate; // REG_DT

	/** 모델명. */
	private String modelName; // DEVICE_MODEL_NM

	/** OS버전. */
	private String osVersion; // DEVICE_OS_VER

	/** 누적 가입자수. */
	private String entryCount; // ACML_ENTRY_CNT

	/**
	 * 테넌트 ID를 리턴한다.
	 * 
	 * @return tenantID - 테넌트 ID
	 */
	public String getTenantID() {
		return this.tenantID;
	}

	/**
	 * 테넌트 ID를 설정한다.
	 * 
	 * @param tenantID
	 *            테넌트 ID
	 */
	public void setTenantID(String tenantID) {
		this.tenantID = tenantID;
	}

	/**
	 * 등록일시를 리턴한다.
	 * 
	 * @return regDate - 등록일시
	 */
	public String getRegDate() {
		return this.regDate;
	}

	/**
	 * 등록일시를 설정한다.
	 * 
	 * @param regDate
	 *            등록일시
	 */
	public void setRegDate(String regDate) {
		this.regDate = regDate;
	}

	/**
	 * 모델명을 리턴한다.
	 * 
	 * @return modelName - 모델명
	 */
	public String getModelName() {
		return this.modelName;
	}

	/**
	 * 모델명을 설정한다.
	 * 
	 * @param modelName
	 *            모델명
	 */
	public void setModelName(String modelName) {
		this.modelName = modelName;
	}

	/**
	 * OS버전을 리턴한다.
	 * 
	 * @return osVersion - OS버전
	 */
	public String getOsVersion() {
		return this.osVersion;
	}

	/**
	 * OS버전을 설정한다.
	 * 
	 * @param osVersion
	 *            OS버전
	 */
	public void setOsVersion(String osVersion) {
		this.osVersion = osVersion;
	}

	/**
	 * 누적 가입자수를 리턴한다.
	 * 
	 * @return entryCount - 누적 가입자수
	 */
	public String getEntryCount() {
		return this.entryCount;
	}

	/**
	 * 누적 가입자수를 설정한다.
	 * 
	 * @param entryCount
	 *            누적 가입자수
	 */
	public void setEntryCount(String entryCount) {
		this.entryCount = entryCount;
	}
}
