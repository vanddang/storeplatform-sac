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
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.common.Date;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.common.Identifier;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.common.Price;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.common.Source;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.common.Title;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.common.Url;

/**
 * Interface Message Coupon Value Object.
 * 
 * Updated on : 2013. 12. 17. Updated by : 오승민, Incross.
 */
@JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
public class Coupon extends CommonInfo implements Serializable {
	private static final long serialVersionUID = 1L;
	/**
	 * 쿠폰 ID.
	 */
	private Identifier identifier;
	/**
	 * 상태 > use: 사용 > notUse: 미사용 > cancel: 취소/환불 > expired: 기간만료.
	 */
	private String status;
	/**
	 * show > yes|no.
	 */
	private String show;
	/**
	 * kind > couponAll: 쿠폰전체대상 > couponLimited: 쿠폰한정판 > couponBuyer: 쿠폰구매자전체 > couponId: ID > couponMisisdn: 휴대폰번호 >.
	 * broadcastFreepass: 방송자동결제 > movieFreepass: 영화자동결제 > seriesFreepass: 시리즈 1회결제 > giftCoupon: 상품권 > directCoupon:.
	 * 교환권 > shippingCoupon: 배송상품 > rentalFreepass/ebook: 이북/코믹 전권 대여 > seriesFreepass/ebook: 이북/코믹 전권 소장.
	 */
	private String kind;
	/**
	 * 쿠폰설명.
	 */
	private String couponExplain;

	/**
	 * 배송지 입력 URL.
	 */
	private Url url;
	/**
	 * 가격.
	 */
	private Price price;
	/**
	 * 유효기간(시작/만료일자).
	 */
	private Date date;
	/**
	 * 쿠폰명/정액권명.
	 */
	private Title title;

	/**
	 * 자동결제 지원 여부 > status.
	 */
	private AutoPay autopay;

	/**
	 * 썸네일 또는 배너 이미지.
	 */
	private List<Source> sourceList;

	/**
	 * selectOption > T스토어 쇼핑상품(type:shoppingStore)인 경우 사용.
	 */
	private SelectOption selectOption;
	/**
	 * 쿠폰 사용처 > type.
	 */
	private Coverage coverage;

	private List<EpisodeCoupon> episodeCouponList;
	
	/**
	 * Identifier 배열.
	 */
	private List<Identifier> identifierList;

	/**
	 * @return Identifier
	 */
	public Identifier getIdentifier() {
		return this.identifier;
	}

	/**
	 * @param identifier
	 *            identifier
	 */
	public void setIdentifier(Identifier identifier) {
		this.identifier = identifier;
	}

	/**
	 * @return String
	 */
	public String getStatus() {
		return this.status;
	}

	/**
	 * @param status
	 *            status
	 */
	public void setStatus(String status) {
		this.status = status;
	}

	/**
	 * @return String
	 */
	public String getShow() {
		return this.show;
	}

	/**
	 * @param show
	 *            show
	 */
	public void setShow(String show) {
		this.show = show;
	}

	/**
	 * @return String
	 */
	public String getKind() {
		return this.kind;
	}

	/**
	 * @param kind
	 *            kind
	 */
	public void setKind(String kind) {
		this.kind = kind;
	}

	/**
	 * @return String
	 */
	public String getCouponExplain() {
		return this.couponExplain;
	}

	/**
	 * @param couponExplain
	 *            couponExplain
	 */
	public void setCouponExplain(String couponExplain) {
		this.couponExplain = couponExplain;
	}

	/**
	 * @return Url
	 */
	public Url getUrl() {
		return this.url;
	}

	/**
	 * @param url
	 *            url
	 */
	public void setUrl(Url url) {
		this.url = url;
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
	 * @return Date
	 */
	public Date getDate() {
		return this.date;
	}

	/**
	 * @param date
	 *            date
	 */
	public void setDate(Date date) {
		this.date = date;
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
	 * @return AutoPay
	 */
	public AutoPay getAutopay() {
		return this.autopay;
	}

	/**
	 * @param autopay
	 *            autopay
	 */
	public void setAutopay(AutoPay autopay) {
		this.autopay = autopay;
	}

	/**
	 * @return List<Source>
	 */
	public List<Source> getSourceList() {
		return this.sourceList;
	}

	/**
	 * @param sourceList
	 *            sourceList
	 */
	public void setSourceList(List<Source> sourceList) {
		this.sourceList = sourceList;
	}

	/**
	 * @return SelectOption
	 */
	public SelectOption getSelectOption() {
		return this.selectOption;
	}

	/**
	 * @param selectOption
	 *            selectOption
	 */
	public void setSelectOption(SelectOption selectOption) {
		this.selectOption = selectOption;
	}

	/**
	 * @return Coverage
	 */
	public Coverage getCoverage() {
		return this.coverage;
	}

	/**
	 * @param coverage
	 *            coverage
	 */
	public void setCoverage(Coverage coverage) {
		this.coverage = coverage;
	}

	/**
	 * @return List<EpisodeCoupon>
	 */
	public List<EpisodeCoupon> getEpisodeCouponList() {
		return this.episodeCouponList;
	}

	/**
	 * @param episodeCouponList
	 *            episodeCouponList
	 */
	public void setEpisodeCouponList(List<EpisodeCoupon> episodeCouponList) {
		this.episodeCouponList = episodeCouponList;
	}

	public List<Identifier> getIdentifierList() {
		return this.identifierList;
	}

	public void setIdentifierList(List<Identifier> identifierList) {
		this.identifierList = identifierList;
	}

}
