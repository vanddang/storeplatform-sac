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
import com.skplanet.storeplatform.purchase.client.common.vo.Payment;
import com.skplanet.storeplatform.purchase.client.common.vo.PrchsProdCnt;

/**
 * 
 * 무료구매 요청 VO
 * 
 * Updated on : 2014. 4. 2. Updated by : 이승택, nTels.
 */
public class MakeFreePurchaseScReq extends CommonInfo {
	private static final long serialVersionUID = 1L;

	private List<PrchsProdCnt> prchsProdCntList; // 구매건수
	private List<PrchsDtlMore> prchsDtlMoreList; // 구매상세, 구매, 추가정보
	private List<Payment> paymentList; // 결제
	private List<PrchsDtlMore> packageEpisodeList; // 정액제 상품 하위 에피소드 상품 목록

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

}
