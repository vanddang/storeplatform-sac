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

import com.skplanet.storeplatform.framework.core.annotation.ProtobufMapping;
import com.skplanet.storeplatform.framework.core.common.vo.CommonVO;
import com.skplanet.storeplatform.sac.client.intfmessage.product.vo.AccrualProto;

/**
 * Interface Message Identifier Value Object.
 * 
 * Updated on : 2013. 12. 17. Updated by : 오승민, Incross.
 */
@SuppressWarnings("serial")
@ProtobufMapping(AccrualProto.Accrual.class)
public class AccrualVO extends CommonVO implements Serializable {
	/**
	 * 참여자수
	 */
	private String voterCount;
	/**
	 * 다운로드수
	 */
	private String downloadCount;
	/**
	 * 평점
	 */
	private double score;
	/**
	 * 지난순위
	 */
	private String lastRank;
	/**
	 * 사용 후기 전체 개수
	 */
	private String feedbackTotal;
	/**
	 * 변동 순위, 하락은 음수로 표현한다.
	 */
	private String changeRank;

	/**
	 * 평점상세정보 "너무 좋아요", "만족해요", "좋아요", "그저 그래요", "별로에요" 순서로 정의
	 */
	private String explain;

	public String getVoterCount() {
		return this.voterCount;
	}

	public void setVoterCount(String voterCount) {
		this.voterCount = voterCount;
	}

	public String getDownloadCount() {
		return this.downloadCount;
	}

	public void setDownloadCount(String downloadCount) {
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

	public String getFeedbackTotal() {
		return this.feedbackTotal;
	}

	public void setFeedbackTotal(String feedbackTotal) {
		this.feedbackTotal = feedbackTotal;
	}

	public String getChangeRank() {
		return this.changeRank;
	}

	public void setChangeRank(String changeRank) {
		this.changeRank = changeRank;
	}

	public String getExplain() {
		return this.explain;
	}

	public void setExplain(String explain) {
		this.explain = explain;
	}

}
