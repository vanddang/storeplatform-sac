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
	private String subYn; // 서브 여부
	private String status; // 상품 상태 (soldout : 매진상품)
	private Integer maxCount; // 최대 판매가능 수량
	private Integer maxMonthlySale; // 월간 최대 판매가능 수량
	private Integer maxDailySale; // 일간 최대 판매가능 수량
	private Integer maxMonthlyBuy; // 1인 월간 최대 구매가능 수량
	private Integer maxDailyBuy; // 1인 일간 최대 구매가능 수량
	private Integer maxOnceBuy; // 1회 최대 구매가능 수량
	private String specialCouponId; // 특가상품 쿠폰 ID

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
	 * @return the subYn
	 */
	public String getSubYn() {
		return this.subYn;
	}

	/**
	 * @param subYn
	 *            the subYn to set
	 */
	public void setSubYn(String subYn) {
		this.subYn = subYn;
	}

	/**
	 * @return the status
	 */
	public String getStatus() {
		return this.status;
	}

	/**
	 * @param status
	 *            the status to set
	 */
	public void setStatus(String status) {
		this.status = status;
	}

	/**
	 * @return the maxCount
	 */
	public Integer getMaxCount() {
		return this.maxCount;
	}

	/**
	 * @param maxCount
	 *            the maxCount to set
	 */
	public void setMaxCount(Integer maxCount) {
		this.maxCount = maxCount;
	}

	/**
	 * @return the maxMonthlySale
	 */
	public Integer getMaxMonthlySale() {
		return this.maxMonthlySale;
	}

	/**
	 * @param maxMonthlySale
	 *            the maxMonthlySale to set
	 */
	public void setMaxMonthlySale(Integer maxMonthlySale) {
		this.maxMonthlySale = maxMonthlySale;
	}

	/**
	 * @return the maxDailySale
	 */
	public Integer getMaxDailySale() {
		return this.maxDailySale;
	}

	/**
	 * @param maxDailySale
	 *            the maxDailySale to set
	 */
	public void setMaxDailySale(Integer maxDailySale) {
		this.maxDailySale = maxDailySale;
	}

	/**
	 * @return the maxMonthlyBuy
	 */
	public Integer getMaxMonthlyBuy() {
		return this.maxMonthlyBuy;
	}

	/**
	 * @param maxMonthlyBuy
	 *            the maxMonthlyBuy to set
	 */
	public void setMaxMonthlyBuy(Integer maxMonthlyBuy) {
		this.maxMonthlyBuy = maxMonthlyBuy;
	}

	/**
	 * @return the maxDailyBuy
	 */
	public Integer getMaxDailyBuy() {
		return this.maxDailyBuy;
	}

	/**
	 * @param maxDailyBuy
	 *            the maxDailyBuy to set
	 */
	public void setMaxDailyBuy(Integer maxDailyBuy) {
		this.maxDailyBuy = maxDailyBuy;
	}

	/**
	 * @return the maxOnceBuy
	 */
	public Integer getMaxOnceBuy() {
		return this.maxOnceBuy;
	}

	/**
	 * @param maxOnceBuy
	 *            the maxOnceBuy to set
	 */
	public void setMaxOnceBuy(Integer maxOnceBuy) {
		this.maxOnceBuy = maxOnceBuy;
	}

	/**
	 * @return the specialCouponId
	 */
	public String getSpecialCouponId() {
		return this.specialCouponId;
	}

	/**
	 * @param specialCouponId
	 *            the specialCouponId to set
	 */
	public void setSpecialCouponId(String specialCouponId) {
		this.specialCouponId = specialCouponId;
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
