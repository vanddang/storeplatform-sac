/*
 * Copyright (c) 2013 SK planet.
 * All right reserved.
 *
 * This software is the confidential and proprietary information of SK planet.
 * You shall not disclose such Confidential Information and
 * shall use it only in accordance with the terms of the license agreement
 * you entered into with SK planet.
 */
package com.skplanet.storeplatform.sac.purchase.order.vo;

import com.skplanet.storeplatform.framework.core.common.vo.CommonInfo;

/**
 * 
 * 구매 제한 정책 체크 결과 VO
 * 
 * Updated on : 2014. 1. 24. Updated by : 이승택, nTels.
 */
public class SktPaymentPolicyCheckResult extends CommonInfo {
	private static final long serialVersionUID = 201401221L;

	private String sktLimitType; // 제한에 걸린 유형

	private boolean sktTestMdn; // SKT 시험폰 여부
	private boolean sktTestMdnWhiteList; // SKT 시험폰 White List 등록 여부
	private boolean corporation; // SKT/SKP 법인폰 여부
	private boolean skpCorporation; // SKP 법인폰 여부 (SKP후불 OCB적립 제한으로, 법인 구분 필요)
	private boolean mvno; // MVNO 회선 여부
	private double sktRestAmt; // SKT 후불 잔여 금액

	/**
	 * @return the sktLimitType
	 */
	public String getSktLimitType() {
		return this.sktLimitType;
	}

	/**
	 * @param sktLimitType
	 *            the sktLimitType to set
	 */
	public void setSktLimitType(String sktLimitType) {
		this.sktLimitType = sktLimitType;
	}

	/**
	 * @return the sktTestMdn
	 */
	public boolean isSktTestMdn() {
		return this.sktTestMdn;
	}

	/**
	 * @param sktTestMdn
	 *            the sktTestMdn to set
	 */
	public void setSktTestMdn(boolean sktTestMdn) {
		this.sktTestMdn = sktTestMdn;
	}

	/**
	 * @return the sktTestMdnWhiteList
	 */
	public boolean isSktTestMdnWhiteList() {
		return this.sktTestMdnWhiteList;
	}

	/**
	 * @param sktTestMdnWhiteList
	 *            the sktTestMdnWhiteList to set
	 */
	public void setSktTestMdnWhiteList(boolean sktTestMdnWhiteList) {
		this.sktTestMdnWhiteList = sktTestMdnWhiteList;
	}

	/**
	 * @return the corporation
	 */
	public boolean isCorporation() {
		return this.corporation;
	}

	/**
	 * @param corporation
	 *            the corporation to set
	 */
	public void setCorporation(boolean corporation) {
		this.corporation = corporation;
	}

	/**
	 * @return the skpCorporation
	 */
	public boolean isSkpCorporation() {
		return this.skpCorporation;
	}

	/**
	 * @param skpCorporation
	 *            the skpCorporation to set
	 */
	public void setSkpCorporation(boolean skpCorporation) {
		this.skpCorporation = skpCorporation;
	}

	/**
	 * @return the mvno
	 */
	public boolean isMvno() {
		return this.mvno;
	}

	/**
	 * @param mvno
	 *            the mvno to set
	 */
	public void setMvno(boolean mvno) {
		this.mvno = mvno;
	}

	/**
	 * @return the sktRestAmt
	 */
	public double getSktRestAmt() {
		return this.sktRestAmt;
	}

	/**
	 * @param sktRestAmt
	 *            the sktRestAmt to set
	 */
	public void setSktRestAmt(double sktRestAmt) {
		this.sktRestAmt = sktRestAmt;
	}

}
