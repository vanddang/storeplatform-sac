/*
 * Copyright (c) 2013 SK planet.
 * All right reserved.
 *
 * This software is the confidential and proprietary information of SK planet.
 * You shall not disclose such Confidential Information and
 * shall use it only in accordance with the terms of the license agreement
 * you entered into with SK planet.
 */
package com.skplanet.storeplatform.sac.client.product.vo.intfmessage.common;

import java.io.Serializable;

import com.skplanet.storeplatform.framework.core.annotation.ProtobufMapping;
import com.skplanet.storeplatform.framework.core.common.vo.CommonVO;
import com.skplanet.storeplatform.sac.client.intfmessage.common.vo.TimeProto;

/**
 * Interface Message Time Value Object.
 * 
 * Updated on : 2013. 12. 17. Updated by : 이태희, SK 플래닛.
 */
@ProtobufMapping(TimeProto.Time.class)
public class Time extends CommonVO implements Serializable {
	private static final long serialVersionUID = 1L;

	/*
	 * 시간에 대한 단위 ( sec:초단위(Default), min: 분, hour: 시간, day: 일단위, month: 월, year: 년)
	 */
	private String unit;
	private String type;// 날짜타입 (relative : (Default : 상대시간))
	/*
	 * time에 대한 정의 ( duration : 정기구독기간, additional : 추가 기간)
	 */
	private String name;
	private String text; // 24시간은 86400으로 표기 가능

	public String getUnit() {
		return this.unit;
	}

	public void setUnit(String unit) {
		this.unit = unit;
	}

	public String getType() {
		return this.type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getText() {
		return this.text;
	}

	public void setText(String text) {
		this.text = text;
	}
}
