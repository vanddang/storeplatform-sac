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
import com.skplanet.storeplatform.sac.client.intfmessage.user.vo.PurchaseProto;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.common.CountVO;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.common.DateVO;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.common.IdentifierVO;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.common.PriceVO;

/**
 * Interface Message Purchase Value Object.
 * 
 * Updated on : 2013. 12. 17. Updated by : 이태희, SK 플래닛.
 */
@ProtobufMapping(PurchaseProto.Purchase.class)
public class PurchaseVO extends CommonVO implements Serializable {
	private static final long serialVersionUID = 1L;

	private IdentifierVO identifier; // 구매ID
	/*
	 * 구매상태 (1 : 구매 (payment), 2 : 선물 (gift), 3 : 만료 (expired), 4 : 취소 (cancel))
	 */
	private String state;
	private String show; // 구매내역 숨김처리 여부
	private String token; // 암호화된 토큰 (외부 연동을 위해 사용)
	private String purchaser; // 구매자 전화번호
	private PriceVO price; // 구매가격
	private DateVO date; // 구매일자
	private RightsVO rights; // 이용기간 및 이용권한
	private GiftVO gift; // 구매이력이 '선물'일 경우 정의
	private CouponVO coupon; // 구매한 쿠폰이 있을 경우 정의
	private AutoPayVO autoPay; // 자동결제 상품인 경우 정의
	private CountVO count; // 구매 건수
	private IdentifierVO channelIdentifier; // 채널 ID

	public IdentifierVO getIdentifier() {
		return this.identifier;
	}

	public void setIdentifier(IdentifierVO identifier) {
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

	public PriceVO getPrice() {
		return this.price;
	}

	public void setPrice(PriceVO price) {
		this.price = price;
	}

	public DateVO getDate() {
		return this.date;
	}

	public void setDate(DateVO date) {
		this.date = date;
	}

	public RightsVO getRights() {
		return this.rights;
	}

	public void setRights(RightsVO rights) {
		this.rights = rights;
	}

	public GiftVO getGift() {
		return this.gift;
	}

	public void setGift(GiftVO gift) {
		this.gift = gift;
	}

	public CouponVO getCoupon() {
		return this.coupon;
	}

	public void setCoupon(CouponVO coupon) {
		this.coupon = coupon;
	}

	public AutoPayVO getAutoPay() {
		return this.autoPay;
	}

	public void setAutoPay(AutoPayVO autoPay) {
		this.autoPay = autoPay;
	}

	public CountVO getCount() {
		return this.count;
	}

	public void setCount(CountVO count) {
		this.count = count;
	}

	public IdentifierVO getChannelIdentifier() {
		return this.channelIdentifier;
	}

	public void setChannelIdentifier(IdentifierVO channelIdentifier) {
		this.channelIdentifier = channelIdentifier;
	}
}
