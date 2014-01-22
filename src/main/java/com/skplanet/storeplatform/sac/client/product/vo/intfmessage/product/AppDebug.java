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

/**
 * Interface Message AppDebug Value Object.
 * 
 * Updated on : 2013. 12. 17. Updated by : 오승민, Incross.
 */
@JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
public class AppDebug extends CommonInfo implements Serializable {
	private static final long serialVersionUID = 1L;
	private String packageName; // 패키지명
	private String versionCode; // 패키지 버전 코드

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

}
