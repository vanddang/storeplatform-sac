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
	private Integer maxMonthlySale; // 월간 최대 판매가능 수량
	private Integer maxDailySale; // 일간 최대 판매가능 수량
	private Integer maxMonthlyBuy; // 1인 월간 최대 구매가능 수량
	private Integer maxDailyBuy; // 1인 일간 최대 구매가능 수량
	private Integer maxOnceBuy; // 1회 최대 구매가능 수량
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
