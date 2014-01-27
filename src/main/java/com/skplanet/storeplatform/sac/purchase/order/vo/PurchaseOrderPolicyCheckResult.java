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
public class PurchaseOrderPolicyCheckResult extends CommonInfo {
	private static final long serialVersionUID = 201401221L;

	private boolean bBlock; // 결제 차단 여부
	private boolean bTestMdn; // 스토어 테스트폰 여부
	private boolean bSktTest; // SKT 시험폰 여부
	private boolean bSktCorp; // SKT 법인폰 여부
	private boolean bSkpCorp; // SKP 법인폰 여부

	/**
	 * @return the bBlock
	 */
	public boolean isbBlock() {
		return this.bBlock;
	}

	/**
	 * @param bBlock
	 *            the bBlock to set
	 */
	public void setbBlock(boolean bBlock) {
		this.bBlock = bBlock;
	}

	/**
	 * @return the bTestMdn
	 */
	public boolean isbTestMdn() {
		return this.bTestMdn;
	}

	/**
	 * @param bTestMdn
	 *            the bTestMdn to set
	 */
	public void setbTestMdn(boolean bTestMdn) {
		this.bTestMdn = bTestMdn;
	}

	/**
	 * @return the bSktTest
	 */
	public boolean isbSktTest() {
		return this.bSktTest;
	}

	/**
	 * @param bSktTest
	 *            the bSktTest to set
	 */
	public void setbSktTest(boolean bSktTest) {
		this.bSktTest = bSktTest;
	}

	/**
	 * @return the bSktCorp
	 */
	public boolean isbSktCorp() {
		return this.bSktCorp;
	}

	/**
	 * @param bSktCorp
	 *            the bSktCorp to set
	 */
	public void setbSktCorp(boolean bSktCorp) {
		this.bSktCorp = bSktCorp;
	}

	/**
	 * @return the bSkpCorp
	 */
	public boolean isbSkpCorp() {
		return this.bSkpCorp;
	}

	/**
	 * @param bSkpCorp
	 *            the bSkpCorp to set
	 */
	public void setbSkpCorp(boolean bSkpCorp) {
		this.bSkpCorp = bSkpCorp;
	}

}
