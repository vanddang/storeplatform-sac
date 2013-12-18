/*
 * Copyright (c) 2013 SK planet.
 * All right reserved.
 *
 * This software is the confidential and proprietary information of SK planet.
 * You shall not disclose such Confidential Information and
 * shall use it only in accordance with the terms of the license agreement
 * you entered into with SK planet.
 */
package com.skplanet.storeplatform.sac.runtime.flow.vo;

/**
 * 
 * Calss 설명
 * 
 * Updated on : 2013. 11. 26. Updated by : 김현일, 인크로스.
 */
public class HeaderVO {
	private String authKey;
	private String systemId;
	private String ist;
	private String path;

	/**
	 * 
	 * @return String
	 */
	public String getAuthKey() {
		return this.authKey;
	}

	/**
	 * 
	 * @param authKey
	 *            authKey
	 */
	public void setAuthKey(String authKey) {
		this.authKey = authKey;
	}

	/**
	 * 
	 * @return String
	 */
	public String getSystemId() {
		return this.systemId;
	}

	/**
	 * 
	 * @param systemId
	 *            systemId
	 */
	public void setSystemId(String systemId) {
		this.systemId = systemId;
	}

	/**
	 * 
	 * @return String
	 */
	public String getIst() {
		return this.ist;
	}

	/**
	 * 
	 * @param ist
	 *            ist
	 */
	public void setIst(String ist) {
		this.ist = ist;
	}

	/**
	 * 
	 * @return String
	 */
	public String getPath() {
		return this.path;
	}

	/**
	 * 
	 * @param path
	 *            path
	 */
	public void setPath(String path) {
		this.path = path;
	}

}
