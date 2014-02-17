/*
 * Copyright (c) 2013 SK planet.
 * All right reserved.
 *
 * This software is the confidential and proprietary information of SK planet.
 * You shall not disclose such Confidential Information and
 * shall use it only in accordance with the terms of the license agreement
 * you entered into with SK planet.
 */
package com.skplanet.storeplatform.sac.runtime.acl.vo;

import org.apache.commons.lang3.StringUtils;


/**
*
* T_CN_INTERFACE 디비 맵핑용 Value Object
*
* Updated on : 2014. 2. 17.
* Updated by : 서대영, SK플래닛.
*/
public enum InterfaceStatus {

	AVALIABLE("CM010601"), UNAVAILABLE("CM010602"), DEPRECATED("CM010603");

	private final String code;

	InterfaceStatus(String code) {
		this.code = code;
	}

	public String getCode() {
		return this.code;
	}

	public static InterfaceStatus fromCode(String code) {
		for (InterfaceStatus status : values()) {
			if (StringUtils.equalsIgnoreCase(status.getCode(), code)) {
				return status;
			}
		}
		return null;
	}

}
