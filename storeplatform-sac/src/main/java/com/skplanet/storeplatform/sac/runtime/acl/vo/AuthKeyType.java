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
* AuthKey 유형 (테스트/상용)
*
* Updated on : 2014. 2. 18.
* Updated by : 임근대, SK플래닛.
*/
public enum AuthKeyType {
    /** 상용키 */
	PROD("CM010201"),
    /** 테스트키 */
    TEST("CM010202");

	private final String code;

	AuthKeyType(String code) {
		this.code = code;
	}

	public String getCode() {
		return this.code;
	}

	public static AuthKeyType fromCode(String code) {
		for (AuthKeyType status : values()) {
			if (StringUtils.equalsIgnoreCase(status.getCode(), code)) {
				return status;
			}
		}
		return null;
	}

}
