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

import org.codehaus.jackson.map.annotate.JsonSerialize;
import org.codehaus.jackson.map.annotate.JsonSerialize.Inclusion;

import com.skplanet.storeplatform.framework.core.common.vo.CommonInfo;

/**
 * 
 * AvgScore Value Object
 * 
 * Updated on : 2014. 1. 23. Updated by : 김현일, 인크로스.
 */
@JsonSerialize(include = Inclusion.NON_NULL)
public class AvgScore extends CommonInfo {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * 평점.
	 */
	private String avgScore;

	/**
	 * 평점 퍼센트.
	 */
	private String avgScorePct;
	/**
	 * 참여수.
	 */
	private String paticpersCnt;

	/**
	 * @return String
	 */
	public String getAvgScore() {
		return this.avgScore;
	}

	/**
	 * @param avgScore
	 *            avgScore
	 */
	public void setAvgScore(String avgScore) {
		this.avgScore = avgScore;
	}

	/**
	 * @return String
	 */
	public String getAvgScorePct() {
		return this.avgScorePct;
	}

	/**
	 * @param avgScorePct
	 *            avgScorePct
	 */
	public void setAvgScorePct(String avgScorePct) {
		this.avgScorePct = avgScorePct;
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
