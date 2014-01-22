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
	private Integer voterCount; // 참여자수
	private Integer downloadCount; // 다운로드 수
	private Double score; // 평점
	private String lastRank; // 지난순위
	private Integer feedbackTotal; // 사용 후기 전체 개수
	private String changeRank; // 변동 순위, 하락은 음수로 표현한다.
	private String explain; // 평점상세정보 "너무 좋아요", "만족해요", "좋아요", "그저 그래요", "별로에요" 순서로 정의

	/**
	 * 
	 * <pre>
	 * 다운로드수.
	 * </pre>
	 * 
	 * @return Integer
	 */
	public Integer getDownloadCount() {
		return this.downloadCount;
	}

	/**
	 * 
	 * <pre>
	 * 다운로드수.
	 * </pre>
	 * 
	 * @param downloadCount
	 *            downloadCount
	 */
	public void setDownloadCount(Integer downloadCount) {
		this.downloadCount = downloadCount;
	}

	/**
	 * 
	 * <pre>
	 * 평점.
	 * </pre>
	 * 
	 * @return double
	 */
	public Double getScore() {
		return this.score;
	}

	/**
	 * 
	 * <pre>
	 * 평점.
	 * </pre>
	 * 
	 * @param score
	 *            score
	 */
	public void setScore(Double score) {
		this.score = score;
	}

	/**
	 * 
	 * <pre>
	 * 지난순위.
	 * </pre>
	 * 
	 * @return String
	 */
	public String getLastRank() {
		return this.lastRank;
	}

	/**
	 * 
	 * <pre>
	 * 지난순위.
	 * </pre>
	 * 
	 * @param lastRank
	 *            lastRank
	 */
	public void setLastRank(String lastRank) {
		this.lastRank = lastRank;
	}

	/**
	 * 
	 * <pre>
	 * 참여자수.
	 * </pre>
	 * 
	 * @return Integer
	 */
	public Integer getVoterCount() {
		return this.voterCount;
	}

	/**
	 * 
	 * <pre>
	 * 참여자수.
	 * </pre>
	 * 
	 * @param voterCount
	 *            voterCount
	 */
	public void setVoterCount(Integer voterCount) {
		this.voterCount = voterCount;
	}

	/**
	 * 
	 * <pre>
	 * 사용 후기 전체 개수.
	 * </pre>
	 * 
	 * @return Integer
	 */
	public Integer getFeedbackTotal() {
		return this.feedbackTotal;
	}

	/**
	 * 
	 * <pre>
	 * 사용 후기 전체 개수.
	 * </pre>
	 * 
	 * @param feedbackTotal
	 *            feedbackTotal
	 */
	public void setFeedbackTotal(Integer feedbackTotal) {
		this.feedbackTotal = feedbackTotal;
	}

	/**
	 * 
	 * <pre>
	 * 변동 순위, 하락은 음수로 표현한다.
	 * </pre>
	 * 
	 * @return String
	 */
	public String getChangeRank() {
		return this.changeRank;
	}

	/**
	 * 
	 * <pre>
	 * 변동 순위, 하락은 음수로 표현한다.
	 * </pre>
	 * 
	 * @param changeRank
	 *            changeRank
	 */
	public void setChangeRank(String changeRank) {
		this.changeRank = changeRank;
	}

	/**
	 * 
	 * <pre>
	 * 평점상세정보 "너무 좋아요", "만족해요", "좋아요", "그저 그래요", "별로에요" 순서로 정의.
	 * </pre>
	 * 
	 * @return String
	 */
	public String getExplain() {
		return this.explain;
	}

	/**
	 * 
	 * <pre>
	 * 평점상세정보 "너무 좋아요", "만족해요", "좋아요", "그저 그래요", "별로에요" 순서로 정의.
	 * </pre>
	 * 
	 * @param explain
	 *            explain
	 */
	public void setExplain(String explain) {
		this.explain = explain;
	}
}
