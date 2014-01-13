/*
 * Copyright (c) 2013 SK planet.
 * All right reserved.
 *
 * This software is the confidential and proprietary information of SK planet.
 * You shall not disclose such Confidential Information and
 * shall use it only in accordance with the terms of the license agreement
 * you entered into with SK planet.
 */
package com.skplanet.storeplatform.sac.runtime.extend.vo;

import com.skplanet.storeplatform.framework.core.common.vo.CommonInfo;

/**
 * x-sac-network-info 헤더를 컨트롤러에서 제공하기 위한 Value Object
 *
 * Updated on : 2014. 1. 13.
 * Updated by : 서대영, SK 플래닛.
 */
public class Network extends CommonInfo {

	private static final long serialVersionUID = 1L;

	private String type;
	private String operator;
	private String simOperator;

	public Network() {
		super();
	}

	public String getType() {
		return this.type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getOperator() {
		return this.operator;
	}
	public void setOperator(String operator) {
		this.operator = operator;
	}
	public String getSimOperator() {
		return this.simOperator;
	}
	public void setSimOperator(String simOperator) {
		this.simOperator = simOperator;
	}

}
