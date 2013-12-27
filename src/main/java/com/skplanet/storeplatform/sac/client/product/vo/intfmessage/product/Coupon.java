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
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.common.Date;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.common.Price;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.common.Source;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.common.Title;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.common.Url;

/**
 * Interface Message Coupon Value Object.
 * 
 * Updated on : 2013. 12. 17. Updated by : 오승민, Incross.
 */
public class Coupon extends CommonInfo implements Serializable {
	private static final long serialVersionUID = 1L;
	/**
	 * 쿠폰 ID
	 */
	private String identifier;
	/**
	 * 타입 > coupon: 쿠폰 > freepass: 정액권 > shoppingStore: Tstore 쇼핑 발급 쿠폰 > shoppingStore/special: Tstore 쇼핑 발급 쿠폰 특가 상품
	 * 쿠폰
	 */
	private String type;
	/**
	 * 상태 > use: 사용 > notUse: 미사용 > cancel: 취소/환불 > expired: 기간만료
	 */
	private String status;
	/**
	 * show > yes|no
	 */
	private String show;
	/**
	 * kind > couponAll: 쿠폰전체대상 > couponLimited: 쿠폰한정판 > couponBuyer: 쿠폰구매자전체 > couponId: ID > couponMisisdn: 휴대폰번호 >
	 * broadcastFreepass: 방송자동결제 > movieFreepass: 영화자동결제 > seriesFreepass: 시리즈 1회결제 > giftCoupon: 상품권 > directCoupon:
	 * 교환권 > shippingCoupon: 배송상품 > rentalFreepass/ebook: 이북/코믹 전권 대여 > seriesFreepass/ebook: 이북/코믹 전권 소장
	 */
	private String kind;

	/**
	 * 배송지 입력 URL
	 */
	private Url url;
	/**
	 * 가격
	 */
	private Price price;
	/**
	 * 유효기간(시작/만료일자)
	 */
	private Date date;
	/**
	 * 쿠폰명/정액권명
	 */
	private Title title;

	/**
	 * 자동결제 지원 여부 > status
	 */
	private AutoPay autopay;
	/**
	 * 쿠폰 소개 제목(T store 회원만을 위한 방송 패스)
	 */
	private String couponGuide;
	/**
	 * 쿠폰 소개 내용
	 */
	private String couponGuideExplain;

	/**
	 * 썸네일 또는 배너 이미지
	 */
	private List<Source> sourceList;

	/**
	 * selectOption > T스토어 쇼핑상품(type:shoppingStore)인 경우 사용
	 */
	private SelectOption selectOption;
	/**
	 * 쿠폰 사용처 > type
	 */
	private Coverage coverage;
	/**
	 * 
	 */
	List<EpisodeCoupon> episodeCouponList;

	public String getIdentifier() {
		return this.identifier;
	}

	public void setIdentifier(String identifier) {
		this.identifier = identifier;
	}

	public String getType() {
		return this.type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getStatus() {
		return this.status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getShow() {
		return this.show;
	}

	public void setShow(String show) {
		this.show = show;
	}

	public String getKind() {
		return this.kind;
	}

	public void setKind(String kind) {
		this.kind = kind;
	}

	public Url getUrl() {
		return this.url;
	}

	public void setUrl(Url url) {
		this.url = url;
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

	public Title getTitle() {
		return this.title;
	}

	public void setTitle(Title title) {
		this.title = title;
	}

	public List<Source> getSourceList() {
		return this.sourceList;
	}

	public void setSourceList(List<Source> sourceList) {
		this.sourceList = sourceList;
	}

	public SelectOption getSelectOption() {
		return this.selectOption;
	}

	public void setSelectOption(SelectOption selectOption) {
		this.selectOption = selectOption;
	}

	public Coverage getCoverage() {
		return this.coverage;
	}

	public void setCoverage(Coverage coverage) {
		this.coverage = coverage;
	}

	public List<EpisodeCoupon> getEpisodeCouponList() {
		return this.episodeCouponList;
	}

	public void setEpisodeCouponList(List<EpisodeCoupon> episodeCouponList) {
		this.episodeCouponList = episodeCouponList;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public AutoPay getAutopay() {
		return this.autopay;
	}

	public String getCouponGuide() {
		return this.couponGuide;
	}

	public String getCouponGuideExplain() {
		return this.couponGuideExplain;
	}
}
