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
 * AvgScore Value Object
 * 
 * Updated on : 2014. 1. 23. Updated by : 김현일, 인크로스.
 */
public class AvgScore extends CommonInfo {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * 평점.
	 */
	private int avgScore;

	/**
	 * 평점 퍼센트.
	 */
	private int avgScorePct;
	/**
	 * 참여수.
	 */
	private int particpersCnt;

	/**
	 * @return int
	 */
	public int getAvgScore() {
		return this.avgScore;
	}

	/**
	 * @param avgScore
	 *            avgScore
	 */
	public void setAvgScore(int avgScore) {
		this.avgScore = avgScore;
	}

	/**
	 * @return int
	 */
	public int getAvgScorePct() {
		return this.avgScorePct;
	}

	/**
	 * @param avgScorePct
	 *            avgScorePct
	 */
	public void setAvgScorePct(int avgScorePct) {
		this.avgScorePct = avgScorePct;
	}

	/**
	 * @return int
	 */
	public int getParticpersCnt() {
		return this.particpersCnt;
	}

	/**
	 * @param particpersCnt
	 *            particpersCnt
	 */
	public void setParticpersCnt(int particpersCnt) {
		this.particpersCnt = particpersCnt;
	}

}
