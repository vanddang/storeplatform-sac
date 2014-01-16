/*
 * Copyright (c) 2013 SK planet.
 * All right reserved.
 *
 * This software is the confidential and proprietary information of SK planet.
 * You shall not disclose such Confidential Information and
 * shall use it only in accordance with the terms of the license agreement
 * you entered into with SK planet.
 */
package com.skplanet.storeplatform.sac.purchase.order.dummy.service;

/**
 * 
 * Dummy 어드민 서비스 구현체
 * 
 * Updated on : 2014. 1. 16. Updated by : 이승택, nTels.
 */
public class DummyAdminServiceImpl {
	/**
	 * <pre>
	 * Test MDN 조회.
	 * </pre>
	 * 
	 * @param tenantId
	 *            테넌트ID
	 * @param mdn
	 *            MDN
	 * @return Test MDN 여부
	 */
	public boolean isTestMdn(String tenantId, String mdn) {
		return false;
	}

	/**
	 * <pre>
	 * 시험폰 조회.
	 * </pre>
	 * 
	 * @param tenantId
	 *            테넌트ID
	 * @param mdn
	 *            MDN
	 * @return 시험폰 여부
	 */
	public boolean isSktTest(String tenantId, String mdn) {
		return false;
	}

	/**
	 * <pre>
	 * SKT 법인폰 조회.
	 * </pre>
	 * 
	 * @param tenantId
	 *            테넌트ID
	 * @param mdn
	 *            MDN
	 * @return SKT 법인폰 여부
	 */
	public boolean isSktCorporate(String tenantId, String mdn) {
		return false;
	}

	/**
	 * <pre>
	 * SKP 법인폰 조회.
	 * </pre>
	 * 
	 * @param tenantId
	 *            테넌트ID
	 * @param mdn
	 *            MDN
	 * @return SKP 법인폰 여부
	 */
	public boolean isSkpCorporate(String tenantId, String mdn) {
		return false;
	}

	/**
	 * <pre>
	 * SKT 후불 구매한도 조회.
	 * </pre>
	 */
	public void getSktLimit() {
	}

	/**
	 * <pre>
	 * 쇼핑 상품 구매한도 조회.
	 * </pre>
	 */
	public void getShoppingLimit() {
	}

	/**
	 * <pre>
	 * 선물 발신한도 조회.
	 * </pre>
	 */
	public void getGiftSendLimit() {
	}

	/**
	 * <pre>
	 * 선물 수신한도 조회.
	 * </pre>
	 */
	public void getGiftRecvLimit() {
	}
}
