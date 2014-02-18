/*
 * Copyright (c) 2013 SK planet.
 * All right reserved.
 *
 * This software is the confidential and proprietary information of SK planet.
 * You shall not disclose such Confidential Information and
 * shall use it only in accordance with the terms of the license agreement
 * you entered into with SK planet.
 */
package com.skplanet.storeplatform.sac.purchase.product.vo;

import com.skplanet.storeplatform.framework.core.common.vo.CommonInfo;

/**
 * Calss 설명
 * 
 * Updated on : 2014. 2. 13. Updated by : 조용진, NTELS.
 */
public class GetProductCountSacRes extends CommonInfo {
	private static final long serialVersionUID = 1L;

	private String prodId;
	private Integer prchsCnt;
	private Integer prchsAmt;
	private String prchsMm;
	private Integer prchsMmCnt;
	private Integer prchsMmAmt;
	private String prchsDay;
	private Integer prchsDayCnt;
	private Integer prchsDayAmt;

	/**
	 * @return the prodId
	 */
	public String getProdId() {
		return this.prodId;
	}

	/**
	 * @param prodId
	 *            the prodId to set
	 */
	public void setProdId(String prodId) {
		this.prodId = prodId;
	}

	/**
	 * @return the prchsCnt
	 */
	public Integer getPrchsCnt() {
		return this.prchsCnt;
	}

	/**
	 * @param prchsCnt
	 *            the prchsCnt to set
	 */
	public void setPrchsCnt(Integer prchsCnt) {
		this.prchsCnt = prchsCnt;
	}

	/**
	 * @return the prchsAmt
	 */
	public Integer getPrchsAmt() {
		return this.prchsAmt;
	}

	/**
	 * @param prchsAmt
	 *            the prchsAmt to set
	 */
	public void setPrchsAmt(Integer prchsAmt) {
		this.prchsAmt = prchsAmt;
	}

	/**
	 * @return the prchsMm
	 */
	public String getPrchsMm() {
		return this.prchsMm;
	}

	/**
	 * @param prchsMm
	 *            the prchsMm to set
	 */
	public void setPrchsMm(String prchsMm) {
		this.prchsMm = prchsMm;
	}

	/**
	 * @return the prchsMmCnt
	 */
	public Integer getPrchsMmCnt() {
		return this.prchsMmCnt;
	}

	/**
	 * @param prchsMmCnt
	 *            the prchsMmCnt to set
	 */
	public void setPrchsMmCnt(Integer prchsMmCnt) {
		this.prchsMmCnt = prchsMmCnt;
	}

	/**
	 * @return the prchsMmAmt
	 */
	public Integer getPrchsMmAmt() {
		return this.prchsMmAmt;
	}

	/**
	 * @param prchsMmAmt
	 *            the prchsMmAmt to set
	 */
	public void setPrchsMmAmt(Integer prchsMmAmt) {
		this.prchsMmAmt = prchsMmAmt;
	}

	/**
	 * @return the prchsDay
	 */
	public String getPrchsDay() {
		return this.prchsDay;
	}

	/**
	 * @param prchsDay
	 *            the prchsDay to set
	 */
	public void setPrchsDay(String prchsDay) {
		this.prchsDay = prchsDay;
	}

	/**
	 * @return the prchsDayCnt
	 */
	public Integer getPrchsDayCnt() {
		return this.prchsDayCnt;
	}

	/**
	 * @param prchsDayCnt
	 *            the prchsDayCnt to set
	 */
	public void setPrchsDayCnt(Integer prchsDayCnt) {
		this.prchsDayCnt = prchsDayCnt;
	}

	/**
	 * @return the prchsDayAmt
	 */
	public Integer getPrchsDayAmt() {
		return this.prchsDayAmt;
	}

	/**
	 * @param prchsDayAmt
	 *            the prchsDayAmt to set
	 */
	public void setPrchsDayAmt(Integer prchsDayAmt) {
		this.prchsDayAmt = prchsDayAmt;
	}

}
