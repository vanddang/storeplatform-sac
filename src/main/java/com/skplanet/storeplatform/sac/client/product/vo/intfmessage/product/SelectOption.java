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
import java.util.List;

import com.skplanet.storeplatform.framework.core.common.vo.CommonInfo;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.common.Price;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.common.Title;

/**
 * Interface Message SelectOption Value Object.
 * 
 * Updated on : 2013. 12. 17. Updated by : 이태희, SK 플래닛.
 */
public class SelectOption extends CommonInfo implements Serializable {
	private static final long serialVersionUID = 1L;

	private String id; // 선택 옵션 ID
	private String index; // 선택옵션 번호
	private Title title; // 선택옵션 제목
	private Price price; // 선택옵션 가격
	private List<SubSelectOption> subSelectOptionList; // 두번째 옵션 리스트

	public String getId() {
		return this.id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getIndex() {
		return this.index;
	}

	public void setIndex(String index) {
		this.index = index;
	}

	public Title getTitle() {
		return this.title;
	}

	public void setTitle(Title title) {
		this.title = title;
	}

	public Price getPrice() {
		return this.price;
	}

	public void setPrice(Price price) {
		this.price = price;
	}

	public List<SubSelectOption> getSubSelectOptionList() {
		return this.subSelectOptionList;
	}

	public void setSubSelectOptionList(List<SubSelectOption> subSelectOptionList) {
		this.subSelectOptionList = subSelectOptionList;
	}
}
