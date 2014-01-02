/*
 * Copyright (c) 2013 SK planet.
 * All right reserved.
 *
 * This software is the confidential and proprietary information of SK planet.
 * You shall not disclose such Confidential Information and
 * shall use it only in accordance with the terms of the license agreement
 * you entered into with SK planet.
 */

package com.skplanet.storeplatform.sac.api.vo;

import java.io.Serializable;

public class ERROR_DATA implements Serializable {

	/**
	 *
	 */
	// private static final long serialVersionUID = 2864668372166277348L;

	// VARCHAR(4) 에러 코드
	private String ERROR_CODE;
	// VARCHAR(300)　 에러 메시지

	private String ERROR_MSG;

	public String getERROR_CODE() {
		return this.ERROR_CODE;
	}

	public void setERROR_CODE(String error_code) {
		this.ERROR_CODE = error_code;
	}

	public String getERROR_MSG() {
		return this.ERROR_MSG;
	}

	public void setERROR_MSG(String error_msg) {
		this.ERROR_MSG = error_msg;
	}

	@Override
	public String toString() {
		return "ERROR_DATA [ERROR_CODE=" + this.ERROR_CODE + ", ERROR_MSG=" + this.ERROR_MSG + "]";
	}
}
