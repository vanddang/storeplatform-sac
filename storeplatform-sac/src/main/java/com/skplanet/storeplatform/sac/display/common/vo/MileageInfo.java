/*
 * Copyright (c) 2013 SK planet.
 * All right reserved.
 *
 * This software is the confidential and proprietary information of SK planet.
 * You shall not disclose such Confidential Information and
 * shall use it only in accordance with the terms of the license agreement
 * you entered into with SK planet.
 */
package com.skplanet.storeplatform.sac.display.common.vo;

/**
 * <p>
 * MileageInfo
 * </p>
 * Updated on : 2014. 07. 25 Updated by : 정희원, SK 플래닛.
 */
public class MileageInfo {

	/**
	 * 정책 대상 코드 ( 카테고리, 상품 )
	 */
	private String policyTargetCd;
	
    /**
     * 플래티넘 등급 적립률
     */
    private Integer rateLv1 = 0;

    /**
     * 골드 등급 적립률
     */
    private Integer rateLv2 = 0;

    /**
     * 실버 등급 적립률
     */
    private Integer rateLv3 = 0;

	public String getPolicyTargetCd() {
		return policyTargetCd;
	}

	public void setPolicyTargetCd(String policyTargetCd) {
		this.policyTargetCd = policyTargetCd;
	}

	public Integer getRateLv1() {
        return rateLv1;
    }

    public void setRateLv1(Integer rateLv1) {
        this.rateLv1 = rateLv1;
    }

    public Integer getRateLv2() {
        return rateLv2;
    }

    public void setRateLv2(Integer rateLv2) {
        this.rateLv2 = rateLv2;
    }

    public Integer getRateLv3() {
        return rateLv3;
    }

    public void setRateLv3(Integer rateLv3) {
        this.rateLv3 = rateLv3;
    }
}
