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
 * Interface Message SelectOption.SubSelectOption Value Object.
 * 
 * Updated on : 2013. 12. 17. Updated by : 이태희, SK 플래닛.
 */
@JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
public class SubSelectOption extends CommonInfo implements Serializable {
	private static final long serialVersionUID = 1L;
	private String id; // 에피소드 ID
	private String subId; // 두번째 콤보에 들어갈 옵션 ID
	private Title title; // 두번째 콤보에 들어갈 제목
	private Price price; // 두번째 콤보에 들어갈 가격
	private String itemCode; // 아이템 코드
	private String salesStatus; // 판매상태

	/**
	 * @return the id
	 */
	public String getId() {
		return this.id;
	}

	/**
	 * @param id
	 *            the id to set
	 */
	public void setId(String id) {
		this.id = id;
	}

	/**
	 * @return String
	 */
	public String getSubId() {
		return this.subId;
	}

	/**
	 * @param subId
	 *            subId
	 */
	public void setSubId(String subId) {
		this.subId = subId;
	}

	/**
	 * @return the title
	 */
	public Title getTitle() {
		return this.title;
	}

	/**
	 * @param title
	 *            the title to set
	 */
	public void setTitle(Title title) {
		this.title = title;
	}

	/**
	 * @return the price
	 */
	public Price getPrice() {
		return this.price;
	}

	/**
	 * @param price
	 *            the price to set
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

}
