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

	private Identifier identifier; // 구매ID
	/*
	 * 구매상태 (1 : 구매 (payment), 2 : 선물 (gift), 3 : 만료 (expired), 4 : 취소 (cancel))
	 */
	private String state;
	private String show; // 구매내역 숨김처리 여부
	private String token; // 암호화된 토큰 (외부 연동을 위해 사용)
	private String purchaser; // 구매자 전화번호
	private Price price; // 구매가격
	private Date date; // 구매일자
	private Rights rights; // 이용기간 및 이용권한
	private Gift gift; // 구매이력이 '선물'일 경우 정의
	private Coupon coupon; // 구매한 쿠폰이 있을 경우 정의
	private AutoPay autoPay; // 자동결제 상품인 경우 정의
	private Count count; // 구매 건수
	private Identifier channelIdentifier; // 채널 ID

	public Identifier getIdentifier() {
		return this.identifier;
	}

	public void setIdentifier(Identifier identifier) {
		this.identifier = identifier;
	}

	public String getState() {
		return this.state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getShow() {
		return this.show;
	}

	public void setShow(String show) {
		this.show = show;
	}

	public String getToken() {
		return this.token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public String getPurchaser() {
		return this.purchaser;
	}

	public void setPurchaser(String purchaser) {
		this.purchaser = purchaser;
	}

	public Price getPrice() {
		return this.price;
	}

	public void setPrice(Price price) {
		this.price = price;
	}

	public Date getDate() {
		return this.date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public Rights getRights() {
		return this.rights;
	}

	public void setRights(Rights rights) {
		this.rights = rights;
	}

	public Gift getGift() {
		return this.gift;
	}

	public void setGift(Gift gift) {
		this.gift = gift;
	}

	public Coupon getCoupon() {
		return this.coupon;
	}

	public void setCoupon(Coupon coupon) {
		this.coupon = coupon;
	}

	public AutoPay getAutoPay() {
		return this.autoPay;
	}

	public void setAutoPay(AutoPay autoPay) {
		this.autoPay = autoPay;
	}

	public Count getCount() {
		return this.count;
	}

	public void setCount(Count count) {
		this.count = count;
	}

	public Identifier getChannelIdentifier() {
		return this.channelIdentifier;
	}

	public void setChannelIdentifier(Identifier channelIdentifier) {
		this.channelIdentifier = channelIdentifier;
	}
}
