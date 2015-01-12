/*
 * Copyright (c) 2013 SK planet.
 * All right reserved.
 *
 * This software is the confidential and proprietary information of SK planet.
 * You shall not disclose such Confidential Information and
 * shall use it only in accordance with the terms of the license agreement
 * you entered into with SK planet.
 */
package com.skplanet.storeplatform.sac.display.feature.isf.invoker.vo;

import com.skplanet.storeplatform.external.client.isf.vo.ConditionValuesType;

/**
 * ISF EC Request Value Object.
 * 
 * Updated on : 2014. 02. 21. Updated by : 윤주영, SK 플래닛.
 */
public class IsfEcReq {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String id; // 서비스 구분 아이디

	private String chCode; // 접속채널 구분 코드

	private String mbn; // 회원 번호

	private String mdn; // 회원의 MDN_NO

	private ConditionValuesType conditionValues;

	private String type; // 타입

	public String getId() {
		return this.id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getChCode() {
		return this.chCode;
	}

	public void setChCode(String chCode) {
		this.chCode = chCode;
	}

	public String getMbn() {
		return this.mbn;
	}

	public void setMbn(String mbn) {
		this.mbn = mbn;
	}

	public String getMdn() {
		return this.mdn;
	}

	public void setMdn(String mdn) {
		this.mdn = mdn;
	}

	public ConditionValuesType getConditionValues() {
		return this.conditionValues;
	}

	public void setConditionValues(ConditionValuesType conditionValues) {
		this.conditionValues = conditionValues;
	}

	public String getType() {
		return this.type;
	}

	public void setType(String type) {
		this.type = type;
	}

}
