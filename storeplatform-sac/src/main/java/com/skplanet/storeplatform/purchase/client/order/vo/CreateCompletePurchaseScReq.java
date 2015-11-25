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

import java.util.List;

import com.skplanet.storeplatform.framework.core.common.vo.CommonInfo;
import com.skplanet.storeplatform.purchase.client.common.vo.MembershipReserve;
import com.skplanet.storeplatform.purchase.client.common.vo.Payment;
import com.skplanet.storeplatform.purchase.client.common.vo.PrchsProdCnt;
import com.skplanet.storeplatform.purchase.client.common.vo.UniqueTid;

/**
 * 
 * 구매/결제 통합 구매이력 생성 요청 VO
 * 
 * Updated on : 2014. 6. 25. Updated by : 이승택, nTels.
 */
public class CreateCompletePurchaseScReq extends CommonInfo {
	private static final long serialVersionUID = 1L;

	private List<PrchsProdCnt> prchsProdCntList; // 구매건수
	private List<PrchsDtlMore> prchsDtlMoreList; // 구매상세, 구매, 추가정보
	private List<Payment> paymentList; // 결제
	private UniqueTid uniqueTid; // Unique TID

	private List<MembershipReserve> membershipReserveList; // 멤버쉽 적립예약 목록

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
	 * @return the prchsDtlMoreList
	 */
	public List<PrchsDtlMore> getPrchsDtlMoreList() {
		return this.prchsDtlMoreList;
	}

	/**
	 * @param prchsDtlMoreList
	 *            the prchsDtlMoreList to set
	 */
	public void setPrchsDtlMoreList(List<PrchsDtlMore> prchsDtlMoreList) {
		this.prchsDtlMoreList = prchsDtlMoreList;
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
	 * @return the uniqueTid
	 */
	public UniqueTid getUniqueTid() {
		return this.uniqueTid;
	}

	/**
	 * @param uniqueTid
	 *            the uniqueTid to set
	 */
	public void setUniqueTid(UniqueTid uniqueTid) {
		this.uniqueTid = uniqueTid;
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

}
