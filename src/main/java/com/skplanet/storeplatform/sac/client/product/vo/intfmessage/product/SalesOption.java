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

import com.skplanet.storeplatform.framework.core.annotation.ProtobufMapping;
import com.skplanet.storeplatform.framework.core.common.vo.CommonVO;
import com.skplanet.storeplatform.sac.client.intfmessage.product.vo.SalesOptionProto;

/**
 * Interface Message SalesOption Value Object.
 * 
 * Updated on : 2013. 12. 17. Updated by : 이태희, SK 플래닛.
 */
@ProtobufMapping(SalesOptionProto.SalesOption.class)
public class SalesOption extends CommonVO implements Serializable {
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

	public String getType() {
		return this.type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getSatus() {
		return this.satus;
	}

	public void setSatus(String satus) {
		this.satus = satus;
	}

	public String getBtob() {
		return this.btob;
	}

	public void setBtob(String btob) {
		this.btob = btob;
	}

	public String getMaxMonthlySale() {
		return this.maxMonthlySale;
	}

	public void setMaxMonthlySale(String maxMonthlySale) {
		this.maxMonthlySale = maxMonthlySale;
	}

	public String getMaxDailySale() {
		return this.maxDailySale;
	}

	public void setMaxDailySale(String maxDailySale) {
		this.maxDailySale = maxDailySale;
	}

	public String getMaxMonthlyBuy() {
		return this.maxMonthlyBuy;
	}

	public void setMaxMonthlyBuy(String maxMonthlyBuy) {
		this.maxMonthlyBuy = maxMonthlyBuy;
	}

	public String getMaxDailyBuy() {
		return this.maxDailyBuy;
	}

	public void setMaxDailyBuy(String maxDailyBuy) {
		this.maxDailyBuy = maxDailyBuy;
	}

	public String getMaxOnceBuy() {
		return this.maxOnceBuy;
	}

	public void setMaxOnceBuy(String maxOnceBuy) {
		this.maxOnceBuy = maxOnceBuy;
	}

	public String getPlaceUsage() {
		return this.placeUsage;
	}

	public void setPlaceUsage(String placeUsage) {
		this.placeUsage = placeUsage;
	}

	public String getRestrictUsage() {
		return this.restrictUsage;
	}

	public void setRestrictUsage(String restrictUsage) {
		this.restrictUsage = restrictUsage;
	}

	public String getPrincipleUsage() {
		return this.principleUsage;
	}

	public void setPrincipleUsage(String principleUsage) {
		this.principleUsage = principleUsage;
	}

	public String getRefundUsage() {
		return this.refundUsage;
	}

	public void setRefundUsage(String refundUsage) {
		this.refundUsage = refundUsage;
	}
}
