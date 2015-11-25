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

import org.codehaus.jackson.map.annotate.JsonSerialize;
import org.codehaus.jackson.map.annotate.JsonSerialize.Inclusion;

import com.skplanet.storeplatform.framework.core.common.vo.CommonInfo;

/**
 * 
 * ListFeedbackRes Value Object
 * 
 * Updated on : 2014. 10. 28. Updated by : 백승현, SP Tek.
 */
@JsonSerialize(include = Inclusion.NON_NULL)
public class ListFeedbackSacRes extends CommonInfo {
	/**
	 *
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * 평균평점 퍼센트.
	 */
	private String avgEvluScorePcts;
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
	private String paticpersCnt;
	/**
	 * 사용후기 리스트.
	 */
	private List<Feedback> notiList;

	/**
	 * 회원별 프로파일 이미지
	 */
	private String profileImgUrl;

	/**
	 * @return String
	 */
	public String getAvgEvluScorePcts() {
		return this.avgEvluScorePcts;
	}

	/**
	 * @param avgEvluScorePcts
	 *            avgEvluScorePcts
	 */
	public void setAvgEvluScorePcts(String avgEvluScorePcts) {
		this.avgEvluScorePcts = avgEvluScorePcts;
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

	/**
	 * @return the profileImgUrl
	 */
	public String getProfileImgUrl() {
		return this.profileImgUrl;
	}

	/**
	 * @param profileImgUrl
	 *            the profileImgUrl to set
	 */
	public void setProfileImgUrl(String profileImgUrl) {
		this.profileImgUrl = profileImgUrl;
	}

}
