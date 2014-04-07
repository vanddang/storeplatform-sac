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
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.common.Date;

/**
 * Interface Message Layout Value Object.
 * 
 * Updated on : 2014. 04. 07. Updated by : 김형식, SK 플래닛.
 */
@JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
public class Cash extends CommonInfo implements Serializable {
	private static final long serialVersionUID = 1L;
	/**
	 * 캐쉬 종류
	 */
	private String name;
	/**
	 * 캐쉬 가격.
	 */
	private Integer text;
	/**
	 * 캐쉬 할인률.
	 */
	private Double cashRate;

	/**
	 * 캐쉬 날짜 정보.
	 */
	private Date date;

	/**
	 * @return the name
	 */
	public String getName() {
		return this.name;
	}

	/**
	 * @param name
	 *            the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @return the text
	 */
	public Integer getText() {
		return this.text;
	}

	/**
	 * @param text
	 *            the text to set
	 */
	public void setText(Integer text) {
		this.text = text;
	}

	/**
	 * @return the cashRate
	 */
	public Double getCashRate() {
		return this.cashRate;
	}

	/**
	 * @param cashRate
	 *            the cashRate to set
	 */
	public void setCashRate(Double cashRate) {
		this.cashRate = cashRate;
	}

	/**
	 * @return the date
	 */
	public Date getDate() {
		return this.date;
	}

	/**
	 * @param date
	 *            the date to set
	 */
	public void setDate(Date date) {
		this.date = date;
	}

}
