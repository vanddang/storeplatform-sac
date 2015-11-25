/*
 * Copyright (c) 2013 SK planet.
 * All right reserved.
 *
 * This software is the confidential and proprietary information of SK planet.
 * You shall not disclose such Confidential Information and
 * shall use it only in accordance with the terms of the license agreement
 * you entered into with SK planet.
 */
package com.skplanet.storeplatform.sac.display.cache.vo;

import java.io.Serializable;

/**
 * <p>
 * 상품 통계
 * </p>
 * Updated on : 2015. 02. 25 Updated by : 정희원, SK 플래닛.
 */
public class ProductStats implements Serializable {

    private static final long serialVersionUID = 1L;

    public ProductStats() {
        this.purchaseCount = 0;
        this.participantCount = 0;
        this.averageScore = 0.0;
    }

    /**
     * 구매건수
     */
    private Integer purchaseCount;
    /**
     * 참여자수
     */
    private Integer participantCount;
    /**
     * 평점
     */
    private Double averageScore;

    public Integer getPurchaseCount() {
        return purchaseCount;
    }

    public void setPurchaseCount(Integer purchaseCount) {
        this.purchaseCount = purchaseCount;
    }

    public Integer getParticipantCount() {
        return participantCount;
    }

    public void setParticipantCount(Integer participantCount) {
        this.participantCount = participantCount;
    }

    public Double getAverageScore() {
        return averageScore;
    }

    public void setAverageScore(Double averageScore) {
        this.averageScore = averageScore;
    }
}
