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

import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.common.Date;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.common.Identifier;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.common.Menu;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.common.Price;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.common.Source;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.common.Title;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.common.Url;
import org.codehaus.jackson.map.annotate.JsonSerialize;

import java.util.List;

/**
 * Interface Message Coupon Value Object.
 * 
 * Updated on : 2013. 12. 17. Updated by : 오승민, Incross.
 */
@JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
public class Coupon extends Product {
	private static final long serialVersionUID = 1L;
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
	 * 자동결제 지원 여부 > status.
	 */
	private AutoPay autopay;

	/**
	 * 쿠폰 사용처 > type.
	 */
	private Coverage coverage;

	private List<EpisodeCoupon> episodeCouponList;


	/**
	 * > Code : 판매상태.
	 */
	private String saleStatus;

	/**
	 * > Cash : 캐쉬정보.
	 */
	private List<Cash> cashList;

	/**
	 * 정액권 이용안내 (freepassGuide).
	 */

	private String freepassGuide;

	/**
	 * 이용권 이용안내 (freepassGuide).
	 */

	private String voucherGuide;

	/**
	 * 이용권 속성정보
	 */
	private FreepassAttr freepassAttr;

	/**
	 * 이용권 속성정보
	 */
	private String requestProduct;
	
	/**
	 * 상품리스트 응답정보
	 */
	private Lists lists;

	/**
	 * @return FreepassAttr
	 */
	public FreepassAttr getFreepassAttr() {
		return this.freepassAttr;
	}

	/**
	 * @param freepassAttr
	 *            freepassAttr
	 */
	public void setFreepassAttr(FreepassAttr freepassAttr) {
		this.freepassAttr = freepassAttr;
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

	/**
	 * @return the saleStatus
	 */
	public String getSaleStatus() {
		return this.saleStatus;
	}

	/**
	 * @param saleStatus
	 *            the saleStatus to set
	 */
	public void setSaleStatus(String saleStatus) {
		this.saleStatus = saleStatus;
	}

	/**
	 * @return the cashList
	 */
	public List<Cash> getCashList() {
		return this.cashList;
	}

	/**
	 * @param cashList
	 *            the cashList to set
	 */
	public void setCashList(List<Cash> cashList) {
		this.cashList = cashList;
	}

	/**
	 * @return the freepassGuide
	 */
	public String getFreepassGuide() {
		return this.freepassGuide;
	}

	/**
	 * @param freepassGuide
	 *            the freepassGuide to set
	 */
	public void setFreepassGuide(String freepassGuide) {
		this.freepassGuide = freepassGuide;
	}

	/**
	 * @return the voucherGuide
	 */
	public String getVoucherGuide() {
		return this.voucherGuide;
	}

	/**
	 * @param voucherGuide
	 *            the voucherGuide to set
	 */
	public void setVoucherGuide(String voucherGuide) {
		this.voucherGuide = voucherGuide;
	}

	/**
	 * @return the requestProduct
	 */
	public String getRequestProduct() {
		return this.requestProduct;
	}

	/**
	 * @param requestProduct
	 *            the requestProduct to set
	 */
	public void setRequestProduct(String requestProduct) {
		this.requestProduct = requestProduct;
	}

	/**
	 * @return the lists
	 */
	public Lists getLists() {
		return lists;
	}
	
	/**
	 * @param lists
	 *            the lists to set
	 */
	public void setLists(Lists lists) {
		this.lists = lists;
	}

}
