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
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.common.Count;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.common.Date;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.common.Identifier;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.common.Price;

/**
 * Interface Message Purchase Value Object.
 * 
 * Updated on : 2013. 12. 17. Updated by : 이태희, SK 플래닛.
 */
@JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
public class Purchase extends CommonInfo implements Serializable {
	private static final long serialVersionUID = 1L;

	private List<Identifier> identifierList; // 구매ID
	/*
	 * 구매상태 (1 : 구매 (payment), 2 : 선물 (gift), 3 : 만료 (expired), 4 : 취소 (cancel))
	 */
	private String state;
	private String show; // 구매내역 숨김처리 여부
	private String token; // 암호화된 토큰 (외부 연동을 위해 사용)
	private String purchaser; // 구매자 전화번호
	private Price price; // 구매가격
	private List<Date> dateList;
	private Rights rights; // 이용기간 및 이용권한
	private Gift gift; // 구매이력이 '선물'일 경우 정의
	private Coupon coupon; // 구매한 쿠폰이 있을 경우 정의
	private AutoPay autoPay; // 자동결제 상품인 경우 정의
	private Count count; // 구매 건수

	/**
	 * @return the identifierList
	 */
	public List<Identifier> getIdentifierList() {
		return this.identifierList;
	}

	/**
	 * @param identifierList
	 *            the identifierList to set
	 */
	public void setIdentifierList(List<Identifier> identifierList) {
		this.identifierList = identifierList;
	}

	/**
	 * @return the state
	 */
	public String getState() {
		return this.state;
	}

	/**
	 * @param state
	 *            the state to set
	 */
	public void setState(String state) {
		this.state = state;
	}

	/**
	 * @return the show
	 */
	public String getShow() {
		return this.show;
	}

	/**
	 * @param show
	 *            the show to set
	 */
	public void setShow(String show) {
		this.show = show;
	}

	/**
	 * @return the token
	 */
	public String getToken() {
		return this.token;
	}

	/**
	 * @param token
	 *            the token to set
	 */
	public void setToken(String token) {
		this.token = token;
	}

	/**
	 * @return the purchaser
	 */
	public String getPurchaser() {
		return this.purchaser;
	}

	/**
	 * @param purchaser
	 *            the purchaser to set
	 */
	public void setPurchaser(String purchaser) {
		this.purchaser = purchaser;
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
	 * @return the dateList
	 */
	public List<Date> getDateList() {
		return this.dateList;
	}

	/**
	 * @param dateList
	 *            the dateList to set
	 */
	public void setDateList(List<Date> dateList) {
		this.dateList = dateList;
	}

	/**
	 * @return the rights
	 */
	public Rights getRights() {
		return this.rights;
	}

	/**
	 * @param rights
	 *            the rights to set
	 */
	public void setRights(Rights rights) {
		this.rights = rights;
	}

	/**
	 * @return the gift
	 */
	public Gift getGift() {
		return this.gift;
	}

	/**
	 * @param gift
	 *            the gift to set
	 */
	public void setGift(Gift gift) {
		this.gift = gift;
	}

	/**
	 * @return the coupon
	 */
	public Coupon getCoupon() {
		return this.coupon;
	}

	/**
	 * @param coupon
	 *            the coupon to set
	 */
	public void setCoupon(Coupon coupon) {
		this.coupon = coupon;
	}

	/**
	 * @return the autoPay
	 */
	public AutoPay getAutoPay() {
		return this.autoPay;
	}

	/**
	 * @param autoPay
	 *            the autoPay to set
	 */
	public void setAutoPay(AutoPay autoPay) {
		this.autoPay = autoPay;
	}

	/**
	 * @return the count
	 */
	public Count getCount() {
		return this.count;
	}

	/**
	 * @param count
	 *            the count to set
	 */
	public void setCount(Count count) {
		this.count = count;
	}
}
