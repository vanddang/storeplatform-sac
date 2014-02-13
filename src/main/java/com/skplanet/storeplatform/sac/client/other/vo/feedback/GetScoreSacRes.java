/*
 * Copyright (c) 2013 SK planet.
 * All right reserved.
 *
 * This software is the confidential and proprietary information of SK planet.
 * You shall not disclose such Confidential Information and
 * shall use it only in accordance with the terms of the license agreement
 * you entered into with SK planet.
 */
package com.skplanet.storeplatform.sac.client.other.vo.feedback;

import com.skplanet.storeplatform.framework.core.common.vo.CommonInfo;

/**
 * 
 * GetScoreRes Value Object
 * 
 * Updated on : 2014. 1. 20. Updated by : 김현일, 인크로스.
 */
public class GetScoreSacRes extends CommonInfo {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * 상품 ID.
	 */
	private String prodId;
	/**
	 * 총 평균 평점.
	 */
	private String totEvluScore;

	/**
	 * 평균 평점.
	 */
	private String avgEvluScore;
	/**
	 * 평균 평점 퍼센트.
	 */
	private String avgEvluScorePct;
	/**
	 * 참여자 수.
	 */
	private String paticpersCnt;

	/**
	 * @return String
	 */
	public String getProdId() {
		return this.prodId;
	}

	/**
	 * @param prodId
	 *            prodId
	 */
	public void setProdId(String prodId) {
		this.prodId = prodId;
	}

	/**
	 * @return String
	 */
	public String getTotEvluScore() {
		return this.totEvluScore;
	}

	/**
	 * @param totEvluScore
	 *            totEvluScore
	 */
	public void setTotEvluScore(String totEvluScore) {
		this.totEvluScore = totEvluScore;
	}

	/**
	 * @return String
	 */
	public String getAvgEvluScore() {
		return this.avgEvluScore;
	}

	/**
	 * @param avgEvluScore
	 *            avgEvluScore
	 */
	public void setAvgEvluScore(String avgEvluScore) {
		this.avgEvluScore = avgEvluScore;
	}

	/**
	 * @return String
	 */
	public String getAvgEvluScorePct() {
		return this.avgEvluScorePct;
	}

	/**
	 * @param avgEvluScorePct
	 *            avgEvluScorePct
	 */
	public void setAvgEvluScorePct(String avgEvluScorePct) {
		this.avgEvluScorePct = avgEvluScorePct;
	}

	/**
	 * @return String
	 */
	public String getPaticpersCnt() {
		return this.paticpersCnt;
	}

	/**
	 * @param paticpersCnt
	 *            paticpersCnt
	 */
	public void setPaticpersCnt(String paticpersCnt) {
		this.paticpersCnt = paticpersCnt;
	}

}
