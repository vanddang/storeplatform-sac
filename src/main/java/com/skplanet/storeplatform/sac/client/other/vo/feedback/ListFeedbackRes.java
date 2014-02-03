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
public class ListFeedbackRes extends CommonInfo {
	/**
	 *
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * 평균평점 퍼센트.
	 */
	private String avgScorePct;
	/**
	 * 사용후기 전체개수.
	 */
	private int notiTot;
	/**
	 * 다운로드 수.
	 */
	private int dwldCnt;
	/**
	 * 평균평점.
	 */
	private int avgScore;
	/**
	 * 참여자수.
	 */
	private int particpersCnt;
	/**
	 * 사용후기 리스트.
	 */
	private List<Feedback> notiList;

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
	 * @return int
	 */
	public int getNotiTot() {
		return this.notiTot;
	}

	/**
	 * @param notiTot
	 *            notiTot
	 */
	public void setNotiTot(int notiTot) {
		this.notiTot = notiTot;
	}

	/**
	 * @return int
	 */
	public int getDwldCnt() {
		return this.dwldCnt;
	}

	/**
	 * @param dwldCnt
	 *            dwldCnt
	 */
	public void setDwldCnt(int dwldCnt) {
		this.dwldCnt = dwldCnt;
	}

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
