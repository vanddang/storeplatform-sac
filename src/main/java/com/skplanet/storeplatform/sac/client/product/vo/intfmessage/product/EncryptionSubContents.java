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
 * EncryptionSubContents Value Object.
 * 
 * Updated on : 2014. 02. 11. Updated by : 이태희, SK 플래닛.
 */
@JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
public class EncryptionSubContents extends CommonInfo implements Serializable {
	private static final long serialVersionUID = 1L;

	private String scid; // 서브컨텐츠ID
	private String path; // 물리파일경로
	private String type;
	private String deltaPath;

	/**
	 * @return the scid
	 */
	public String getScid() {
		return this.scid;
	}

	/**
	 * @param scid
	 *            the scid to set
	 */
	public void setScid(String scid) {
		this.scid = scid;
	}

	/**
	 * @return the path
	 */
	public String getPath() {
		return this.path;
	}

	/**
	 * @param path
	 *            the path to set
	 */
	public void setPath(String path) {
		this.path = path;
	}

	/**
	 * @return the type
	 */
	public String getType() {
		return this.type;
	}

	/**
	 * @param type
	 *            the type to set
	 */
	public void setType(String type) {
		this.type = type;
	}

	/**
	 * @return the deltaPath
	 */
	public String getDeltaPath() {
		return this.deltaPath;
	}

	/**
	 * @param deltaPath
	 *            the deltaPath to set
	 */
	public void setDeltaPath(String deltaPath) {
		this.deltaPath = deltaPath;
	}
}
