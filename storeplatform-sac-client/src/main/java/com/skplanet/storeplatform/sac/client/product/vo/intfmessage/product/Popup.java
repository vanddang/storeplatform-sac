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
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.common.Price;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.common.Title;

/**
 * Interface Message Popup Value Object.
 * 
 * Updated on : 2013. 12. 17. Updated by : 이태희, SK 플래닛.
 */
@JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
public class Popup extends CommonInfo implements Serializable {
	private static final long serialVersionUID = 1L;

	private String id; // 이벤트 ID
	private String layout; // 레이아웃 (dimmed : 반투명, fillscreen : 전체화면)
	private Title title; // 선택옵션 제목
	private Price price; // 선택옵션 가격
	private SelectOption selectOption; // child 옵션

	/**
	 * @return String
	 */
	public String getId() {
		return this.id;
	}

	/**
	 * @param id
	 *            id
	 */
	public void setId(String id) {
		this.id = id;
	}

	/**
	 * @return String
	 */
	public String getLayout() {
		return this.layout;
	}

	/**
	 * @param layout
	 *            layout
	 */
	public void setLayout(String layout) {
		this.layout = layout;
	}

	/**
	 * @return Title
	 */
	public Title getTitle() {
		return this.title;
	}

	/**
	 * @param title
	 *            title
	 */
	public void setTitle(Title title) {
		this.title = title;
	}

	/**
	 * @return Price
	 */
	public Price getPrice() {
		return this.price;
	}

	/**
	 * @param price
	 *            price
	 */
	public void setPrice(Price price) {
		this.price = price;
	}

	/**
	 * @return SelectOption
	 */
	public SelectOption getSelectOption() {
		return this.selectOption;
	}

	/**
	 * @param selectOption
	 *            selectOption
	 */
	public void setSelectOption(SelectOption selectOption) {
		this.selectOption = selectOption;
	}

}
