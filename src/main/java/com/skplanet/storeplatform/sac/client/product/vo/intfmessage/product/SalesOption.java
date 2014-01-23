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
 * Interface Message SalesOption Value Object.
 * 
 * Updated on : 2013. 12. 17. Updated by : 이태희, SK 플래닛.
 */
@JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
public class SalesOption extends CommonInfo implements Serializable {
	private static final long serialVersionUID = 1L;

	/*
	 * 상품권 종류 (giftcard : 상품권, exchange : 교환권, delivery : 배송상품)
	 */
	private String type;
	private String satus; // 상품 상태 (soldout : 매진상품)
	private String btob; // B2B 상품 여부(Y/N)
	private String maxMonthlySale; // 월간 최대 판매가능 수량
	private String maxDailySale; // 일간 최대 판매가능 수량
	private String maxMonthlyBuy; // 1인 월간 최대 구매가능 수량
	private String maxDailyBuy; // 1인 일간 최대 구매가능 수량
	private String maxOnceBuy; // 1회 최대 구매가능 수량
	private String placeUsage; // 사용장소
	private String restrictUsage; // 사용제한
	private String principleUsage; // 유의사항
	private String refundUsage; // 구매취소/환불

	/**
	 * @return String
	 */
	public String getType() {
		return this.type;
	}

	/**
	 * @param type
	 *            type
	 */
	public void setType(String type) {
		this.type = type;
	}

	/**
	 * @return String
	 */
	public String getSatus() {
		return this.satus;
	}

	/**
	 * @param satus
	 *            satus
	 */
	public void setSatus(String satus) {
		this.satus = satus;
	}

	/**
	 * @return String
	 */
	public String getBtob() {
		return this.btob;
	}

	/**
	 * @param btob
	 *            btob
	 */
	public void setBtob(String btob) {
		this.btob = btob;
	}

	/**
	 * @return String
	 */
	public String getMaxMonthlySale() {
		return this.maxMonthlySale;
	}

	/**
	 * @param maxMonthlySale
	 *            maxMonthlySale
	 */
	public void setMaxMonthlySale(String maxMonthlySale) {
		this.maxMonthlySale = maxMonthlySale;
	}

	/**
	 * @return String
	 */
	public String getMaxDailySale() {
		return this.maxDailySale;
	}

	/**
	 * @param maxDailySale
	 *            maxDailySale
	 */
	public void setMaxDailySale(String maxDailySale) {
		this.maxDailySale = maxDailySale;
	}

	/**
	 * @return String
	 */
	public String getMaxMonthlyBuy() {
		return this.maxMonthlyBuy;
	}

	/**
	 * @param maxMonthlyBuy
	 *            maxMonthlyBuy
	 */
	public void setMaxMonthlyBuy(String maxMonthlyBuy) {
		this.maxMonthlyBuy = maxMonthlyBuy;
	}

	/**
	 * @return String
	 */
	public String getMaxDailyBuy() {
		return this.maxDailyBuy;
	}

	/**
	 * @param maxDailyBuy
	 *            maxDailyBuy
	 */
	public void setMaxDailyBuy(String maxDailyBuy) {
		this.maxDailyBuy = maxDailyBuy;
	}

	/**
	 * @return String
	 */
	public String getMaxOnceBuy() {
		return this.maxOnceBuy;
	}

	/**
	 * @param maxOnceBuy
	 *            maxOnceBuy
	 */
	public void setMaxOnceBuy(String maxOnceBuy) {
		this.maxOnceBuy = maxOnceBuy;
	}

	/**
	 * @return String
	 */
	public String getPlaceUsage() {
		return this.placeUsage;
	}

	/**
	 * @param placeUsage
	 *            placeUsage
	 */
	public void setPlaceUsage(String placeUsage) {
		this.placeUsage = placeUsage;
	}

	/**
	 * @return String
	 */
	public String getRestrictUsage() {
		return this.restrictUsage;
	}

	/**
	 * @param restrictUsage
	 *            restrictUsage
	 */
	public void setRestrictUsage(String restrictUsage) {
		this.restrictUsage = restrictUsage;
	}

	/**
	 * @return String
	 */
	public String getPrincipleUsage() {
		return this.principleUsage;
	}

	/**
	 * @param principleUsage
	 *            principleUsage
	 */
	public void setPrincipleUsage(String principleUsage) {
		this.principleUsage = principleUsage;
	}

	/**
	 * @return String
	 */
	public String getRefundUsage() {
		return this.refundUsage;
	}

	/**
	 * @param refundUsage
	 *            refundUsage
	 */
	public void setRefundUsage(String refundUsage) {
		this.refundUsage = refundUsage;
	}

}
