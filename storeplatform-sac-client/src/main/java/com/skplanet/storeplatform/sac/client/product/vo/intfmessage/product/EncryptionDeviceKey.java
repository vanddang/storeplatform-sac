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
 * EncryptionDeviceKey Value Object.
 * 
 * Updated on : 2014. 02. 11. Updated by : 이태희, SK 플래닛.
 */
@JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
public class EncryptionDeviceKey extends CommonInfo implements Serializable {
	private static final long serialVersionUID = 1L;

	private String key; // 디바이스키
	private String type; // 디바이스유형(msisdn, uuid, mac)
	private String subKey; // 디바이스 유형값

	/**
	 * @return the key
	 */
	public String getKey() {
		return this.key;
	}

	/**
	 * @param key
	 *            the key to set
	 */
	public void setKey(String key) {
		this.key = key;
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
	 * @return the subKey
	 */
	public String getSubKey() {
		return this.subKey;
	}

	/**
	 * @param subKey
	 *            the subKey to set
	 */
	public void setSubKey(String subKey) {
		this.subKey = subKey;
	}
}
