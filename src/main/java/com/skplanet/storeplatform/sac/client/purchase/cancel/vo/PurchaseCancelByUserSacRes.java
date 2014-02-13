/*
 * Copyright (c) 2013 SK planet.
 * All right reserved.
 *
 * This software is the confidential and proprietary information of SK planet.
 * You shall not disclose such Confidential Information and
 * shall use it only in accordance with the terms of the license agreement
 * you entered into with SK planet.
 */
package com.skplanet.storeplatform.sac.client.purchase.cancel.vo;

import java.util.List;

import com.skplanet.storeplatform.sac.client.purchase.common.vo.PurchaseCommonSacRes;

/**
 * 구매 취소(사용자) 응답 VO.
 * 
 * Updated on : 2014. 2. 12. Updated by : nTels_cswoo81, nTels.
 */
public class PurchaseCancelByUserSacRes extends PurchaseCommonSacRes {

	private static final long serialVersionUID = 1L;

	private Integer totCnt;
	private Integer successCnt;
	private Integer failCnt;
	private List<PurchaseCancelByUserDetailSacRes> prchsCancelList;

	/**
	 * @return the totCnt
	 */
	public Integer getTotCnt() {
		return this.totCnt;
	}

	/**
	 * @param totCnt
	 *            the totCnt to set
	 */
	public void setTotCnt(Integer totCnt) {
		this.totCnt = totCnt;
	}

	/**
	 * @return the successCnt
	 */
	public Integer getSuccessCnt() {
		return this.successCnt;
	}

	/**
	 * @param successCnt
	 *            the successCnt to set
	 */
	public void setSuccessCnt(Integer successCnt) {
		this.successCnt = successCnt;
	}

	/**
	 * @return the failCnt
	 */
	public Integer getFailCnt() {
		return this.failCnt;
	}

	/**
	 * @param failCnt
	 *            the failCnt to set
	 */
	public void setFailCnt(Integer failCnt) {
		this.failCnt = failCnt;
	}

	/**
	 * @return the prchsCancelList
	 */
	public List<PurchaseCancelByUserDetailSacRes> getPrchsCancelList() {
		return this.prchsCancelList;
	}

	/**
	 * @param prchsCancelList
	 *            the prchsCancelList to set
	 */
	public void setPrchsCancelList(List<PurchaseCancelByUserDetailSacRes> prchsCancelList) {
		this.prchsCancelList = prchsCancelList;
	}

}
