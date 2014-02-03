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
 * 구매 취소 응답 VO.
 * 
 * Updated on : 2014. 1. 16. Updated by : nTels_cswoo81, nTels.
 */
public class PurchaseCancelRes extends PurchaseCommonSacRes {

	private static final long serialVersionUID = 1L;

	private Integer totCnt;
	private Integer successCnt;
	private Integer failCnt;
	private List<PurchaseCancelResDetail> prchsCancelResultList;

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
	 * @return the prchsCancelResultList
	 */
	public List<PurchaseCancelResDetail> getPrchsCancelResultList() {
		return this.prchsCancelResultList;
	}

	/**
	 * @param prchsCancelResultList
	 *            the prchsCancelResultList to set
	 */
	public void setPrchsCancelResultList(List<PurchaseCancelResDetail> prchsCancelResultList) {
		this.prchsCancelResultList = prchsCancelResultList;
	}

}
