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

import org.codehaus.jackson.map.annotate.JsonSerialize;

import com.skplanet.storeplatform.framework.core.common.vo.CommonInfo;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.common.Price;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.common.Title;

/**
 * Interface Message SelectOption Value Object.
 * 
 * Updated on : 2013. 12. 17. Updated by : 이태희, SK 플래닛.
 */
@JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
public class SelectOption extends CommonInfo implements Serializable {
	private static final long serialVersionUID = 1L;

	private String id; // 선택 옵션 ID
	private String index; // 선택옵션 번호
	private Title title; // 선택옵션 제목
	private Price price; // 선택옵션 가격
	private String itemCode; // 아이템 코드
	private String salesStatus; // 판매 상태
	private List<SubSelectOption> subSelectOptionList; // 두번째 옵션 리스트

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
	public String getIndex() {
		return this.index;
	}

	/**
	 * @param index
	 *            index
	 */
	public void setIndex(String index) {
		this.index = index;
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
	 * @return the itemCode
	 */
	public String getItemCode() {
		return this.itemCode;
	}

	/**
	 * @param itemCode
	 *            the itemCode to set
	 */
	public void setItemCode(String itemCode) {
		this.itemCode = itemCode;
	}

	/**
	 * @return the salesStatus
	 */
	public String getSalesStatus() {
		return this.salesStatus;
	}

	/**
	 * @param salesStatus
	 *            the salesStatus to set
	 */
	public void setSalesStatus(String salesStatus) {
		this.salesStatus = salesStatus;
	}

	/**
	 * @return List<SubSelectOption>
	 */
	public List<SubSelectOption> getSubSelectOptionList() {
		return this.subSelectOptionList;
	}

	/**
	 * @param subSelectOptionList
	 *            subSelectOptionList
	 */
	public void setSubSelectOptionList(List<SubSelectOption> subSelectOptionList) {
		this.subSelectOptionList = subSelectOptionList;
	}

}
