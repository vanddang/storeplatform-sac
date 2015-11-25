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
 * Interface Message Chapter Value Object.
 * 
 * Updated on : 2013. 12. 17. Updated by : 이태희, SK 플래닛.
 */
@JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
public class Chapter extends CommonInfo implements Serializable {
	private static final long serialVersionUID = 1L;

	private String unit; // 회차
	private Integer text; // 회차의 수

	/**
	 * Chapter.
	 */
	public Chapter() {
		super();
	}

	/**
	 * @param unit
	 *            String
	 * @param text
	 *            Integer
	 */
	public Chapter(String unit, Integer text) {
		super();
		this.unit = unit;
		this.text = text;
	}

	/**
	 * @return Integer
	 */
	public String getUnit() {
		return this.unit;
	}

	/**
	 * @param unit
	 *            unit
	 */
	public void setUnit(String unit) {
		this.unit = unit;
	}

	/**
	 * @return Integer
	 */
	public Integer getText() {
		return this.text;
	}

	/**
	 * @param text
	 *            text
	 */
	public void setText(Integer text) {
		this.text = text;
	}

}
