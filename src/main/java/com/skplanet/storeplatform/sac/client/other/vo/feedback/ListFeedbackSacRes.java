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

import java.util.List;

import com.skplanet.storeplatform.framework.core.common.vo.CommonInfo;

/**
 * 
 * ListFeedbackRes Value Object
 * 
 * Updated on : 2014. 1. 27. Updated by : 김현일, 인크로스.
 */
public class ListFeedbackSacRes extends CommonInfo {
	/**
	 *
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * 평균평점 퍼센트.
	 */
	private String avgEvluScorePct;
	/**
	 * 사용후기 전체개수.
	 */
	private String notiTot;
	/**
	 * 다운로드 수.
	 */
	private String dwldCnt;
	/**
	 * 평균평점.
	 */
	private String avgEvluScore;
	/**
	 * 참여자수.
	 */
	private String particpersCnt;
	/**
	 * 사용후기 리스트.
	 */
	private List<Feedback> notiList;

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
	public String getNotiTot() {
		return this.notiTot;
	}

	/**
	 * @param notiTot
	 *            notiTot
	 */
	public void setNotiTot(String notiTot) {
		this.notiTot = notiTot;
	}

	/**
	 * @return String
	 */
	public String getDwldCnt() {
		return this.dwldCnt;
	}

	/**
	 * @param dwldCnt
	 *            dwldCnt
	 */
	public void setDwldCnt(String dwldCnt) {
		this.dwldCnt = dwldCnt;
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
	public String getParticpersCnt() {
		return this.particpersCnt;
	}

	/**
	 * @param particpersCnt
	 *            particpersCnt
	 */
	public void setParticpersCnt(String particpersCnt) {
		this.particpersCnt = particpersCnt;
	}

	/**
	 * @return List<Feedback>
	 */
	public List<Feedback> getNotiList() {
		return this.notiList;
	}

	/**
	 * @param notiList
	 *            notiList
	 */
	public void setNotiList(List<Feedback> notiList) {
		this.notiList = notiList;
	}
}
