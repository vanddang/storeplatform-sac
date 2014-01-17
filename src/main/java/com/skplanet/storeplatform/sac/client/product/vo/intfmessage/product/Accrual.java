/*
 * Copyright (c) 2013 SK planet.
 * All right reserved.
 *
 * This software is the confidential and proprietary information of SK planet.
 * You shall not disclose such Confidential Information and
 * shall use it only in accordance with the terms of the license agreement
 * you entered into with SK planet.
 */
package com.skplanet.storeplatform.sac.client.product.vo.intfmessage.product;

import java.io.Serializable;

import org.codehaus.jackson.map.annotate.JsonSerialize;

import com.skplanet.storeplatform.framework.core.common.vo.CommonInfo;

/**
 * Interface Message Accrual Value Object.
 * 
 * Updated on : 2013. 12. 17. Updated by : 오승민, Incross.
 */
@JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
public class Accrual extends CommonInfo implements Serializable {
	private static final long serialVersionUID = 1L;
	/**
	 * 참여자수
	 */
	private Integer voterCount;
	/**
	 * 다운로드수
	 */
	private Integer downloadCount;
	/**
	 * 평점
	 */
	private Double score;
	/**
	 * 지난순위
	 */
	private String lastRank;
	/**
	 * 사용 후기 전체 개수
	 */
	private Integer feedbackTotal;
	/**
	 * 변동 순위, 하락은 음수로 표현한다.
	 */
	private String changeRank;

	/**
	 * 평점상세정보 "너무 좋아요", "만족해요", "좋아요", "그저 그래요", "별로에요" 순서로 정의
	 */
	private String explain;

	public Integer getDownloadCount() {
		return this.downloadCount;
	}

	public void setDownloadCount(Integer downloadCount) {
		this.downloadCount = downloadCount;
	}

	public double getScore() {
		return this.score;
	}

	public void setScore(double score) {
		this.score = score;
	}

	public String getLastRank() {
		return this.lastRank;
	}

	public void setLastRank(String lastRank) {
		this.lastRank = lastRank;
	}

	public Integer getVoterCount() {
		return this.voterCount;
	}

	public void setVoterCount(Integer voterCount) {
		this.voterCount = voterCount;
	}

	public Integer getFeedbackTotal() {
		return this.feedbackTotal;
	}

	public void setFeedbackTotal(Integer feedbackTotal) {
		this.feedbackTotal = feedbackTotal;
	}

	public String getChangeRank() {
		return this.changeRank;
	}

	public void setChangeRank(String changeRank) {
		this.changeRank = changeRank;
	}

	public void setScore(Double score) {
		this.score = score;
	}

	public String getExplain() {
		return this.explain;
	}

	public void setExplain(String explain) {
		this.explain = explain;
	}

}
