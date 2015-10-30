/*
 * Copyright (c) 2013 SK planet.
 * All right reserved.
 *
 * This software is the confidential and proprietary information of SK planet.
 * You shall not disclose such Confidential Information and
 * shall use it only in accordance with the terms of the license agreement
 * you entered into with SK planet.
 */
package com.skplanet.storeplatform.purchase.client.order.vo;

import com.skplanet.storeplatform.framework.core.common.vo.CommonInfo;
import com.skplanet.storeplatform.purchase.client.common.vo.MembershipReserve;
import com.skplanet.storeplatform.purchase.client.common.vo.Payment;
import com.skplanet.storeplatform.purchase.client.common.vo.PrchsProdCnt;

import java.util.List;

/**
 * 
 * 구매확정 요청 VO
 * 
 * Updated on : 2014. 1. 20. Updated by : 이승택, nTels.
 */
public class ConfirmPurchaseScReq extends CommonInfo {
	private static final long serialVersionUID = 1L;

	private String tenantId;
	private String systemId;
	private String prchsId;
	// 구매DB 파티션
	private String useTenantId;
	private String useInsdUsermbrNo;

	private String networkTypeCd;

 	// 오퍼링 ID
	private String offeringId;
	// CPS CPID
	private String mediaId;
	// 구매일자
	private String prchsDt;

	private List<PrchsProdCnt> prchsProdCntList; // 구매건수
	private List<Payment> paymentList; // 결제
	private List<AutoPrchsMore> autoPrchsMoreList; // 자동구매
	private List<ShoppingCouponPublishInfo> shoppingCouponList; // 쇼핑 쿠폰
	private List<PrchsDtlMore> packageEpisodeList; // 정액제 상품 하위 에피소드 상품 목록
	private List<MembershipReserve> membershipReserveList; // 멤버쉽 적립예약 목록

	// 캐시 정보 (RESV03에 입력)
	private String cashInfo;

	/**
	 * @return the tenantId
	 */
	public String getTenantId() {
		return this.tenantId;
	}

	/**
	 * @param tenantId
	 *            the tenantId to set
	 */
	public void setTenantId(String tenantId) {
		this.tenantId = tenantId;
	}

	/**
	 * @return the systemId
	 */
	public String getSystemId() {
		return this.systemId;
	}

	/**
	 * @param systemId
	 *            the systemId to set
	 */
	public void setSystemId(String systemId) {
		this.systemId = systemId;
	}

	/**
	 * @return the prchsId
	 */
	public String getPrchsId() {
		return this.prchsId;
	}

	/**
	 * @param prchsId
	 *            the prchsId to set
	 */
	public void setPrchsId(String prchsId) {
		this.prchsId = prchsId;
	}

	/**
	 * @return the useTenantId
	 */
	public String getUseTenantId() {
		return this.useTenantId;
	}

	/**
	 * @param useTenantId
	 *            the useTenantId to set
	 */
	public void setUseTenantId(String useTenantId) {
		this.useTenantId = useTenantId;
	}

	/**
	 * @return the useInsdUsermbrNo
	 */
	public String getUseInsdUsermbrNo() {
		return this.useInsdUsermbrNo;
	}

	/**
	 * @param useInsdUsermbrNo
	 *            the useInsdUsermbrNo to set
	 */
	public void setUseInsdUsermbrNo(String useInsdUsermbrNo) {
		this.useInsdUsermbrNo = useInsdUsermbrNo;
	}

	/**
	 * @return the networkTypeCd
	 */
	public String getNetworkTypeCd() {
		return this.networkTypeCd;
	}

	/**
	 * @param networkTypeCd
	 *            the networkTypeCd to set
	 */
	public void setNetworkTypeCd(String networkTypeCd) {
		this.networkTypeCd = networkTypeCd;
	}

	/**
	 * @return the offeringId
	 */
	public String getOfferingId() {
		return this.offeringId;
	}

	/**
	 * @param offeringId
	 *            the offeringId to set
	 */
	public void setOfferingId(String offeringId) {
		this.offeringId = offeringId;
	}

	/**
	 * @return the mediaId
	 */
	public String getMediaId() {
		return this.mediaId;
	}

	/**
	 * @param mediaId
	 *            the mediaId to set
	 */
	public void setMediaId(String mediaId) {
		this.mediaId = mediaId;
	}

	/**
	 * @return the prchsProdCntList
	 */
	public List<PrchsProdCnt> getPrchsProdCntList() {
		return this.prchsProdCntList;
	}

	/**
	 * @param prchsProdCntList
	 *            the prchsProdCntList to set
	 */
	public void setPrchsProdCntList(List<PrchsProdCnt> prchsProdCntList) {
		this.prchsProdCntList = prchsProdCntList;
	}

	/**
	 * @return the paymentList
	 */
	public List<Payment> getPaymentList() {
		return this.paymentList;
	}

	/**
	 * @param paymentList
	 *            the paymentList to set
	 */
	public void setPaymentList(List<Payment> paymentList) {
		this.paymentList = paymentList;
	}

	/**
	 * @return the autoPrchsMoreList
	 */
	public List<AutoPrchsMore> getAutoPrchsMoreList() {
		return this.autoPrchsMoreList;
	}

	/**
	 * @param autoPrchsMoreList
	 *            the autoPrchsMoreList to set
	 */
	public void setAutoPrchsMoreList(List<AutoPrchsMore> autoPrchsMoreList) {
		this.autoPrchsMoreList = autoPrchsMoreList;
	}

	/**
	 * @return the shoppingCouponList
	 */
	public List<ShoppingCouponPublishInfo> getShoppingCouponList() {
		return this.shoppingCouponList;
	}

	/**
	 * @param shoppingCouponList
	 *            the shoppingCouponList to set
	 */
	public void setShoppingCouponList(List<ShoppingCouponPublishInfo> shoppingCouponList) {
		this.shoppingCouponList = shoppingCouponList;
	}

	/**
	 * @return the packageEpisodeList
	 */
	public List<PrchsDtlMore> getPackageEpisodeList() {
		return this.packageEpisodeList;
	}

	/**
	 * @param packageEpisodeList
	 *            the packageEpisodeList to set
	 */
	public void setPackageEpisodeList(List<PrchsDtlMore> packageEpisodeList) {
		this.packageEpisodeList = packageEpisodeList;
	}

	/**
	 * @return the membershipReserveList
	 */
	public List<MembershipReserve> getMembershipReserveList() {
		return this.membershipReserveList;
	}

	/**
	 * @param membershipReserveList
	 *            the membershipReserveList to set
	 */
	public void setMembershipReserveList(List<MembershipReserve> membershipReserveList) {
		this.membershipReserveList = membershipReserveList;
	}

	/**
	 * Gets prchs dt.
	 * 
	 * @return the prchs dt
	 */
	public String getPrchsDt() {
		return this.prchsDt;
	}

	/**
	 * Sets prchs dt.
	 * 
	 * @param prchsDt
	 *            the prchs dt
	 */
	public void setPrchsDt(String prchsDt) {
		this.prchsDt = prchsDt;
	}

	/**
	 * Gets cash info.
	 *
	 * @return the cash info
	 */
	public String getCashInfo() {
		return cashInfo;
	}

	/**
	 * Sets cash info.
	 *
	 * @param cashInfo
	 *            the cash info
	 */
	public void setCashInfo(String cashInfo) {
		this.cashInfo = cashInfo;
	}
}
