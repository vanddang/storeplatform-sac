package com.skplanet.storeplatform.sac.purchase.order.dummy.service;

public class DummyAdminServiceImpl {
	/**
	 * Test MDN 조회.
	 */
	public boolean isTestMdn(String tenantId, String mdn) {
		return false;
	}

	/**
	 * 시험폰 조회.
	 */
	public boolean isSktTest(String tenantId, String mdn) {
		return false;
	}

	/**
	 * SKT 법인폰 조회.
	 */
	public boolean isSktCorporate(String tenantId, String mdn) {
		return false;
	}

	/**
	 * SKP 법인폰 조회.
	 */
	public boolean isSkpCorporate(String tenantId, String mdn) {
		return false;
	}

	/**
	 * SKT 후불 구매한도 조회.
	 */
	public void getSktLimit() {
	}

	/**
	 * 쇼핑 상품 구매한도 조회.
	 */
	public void getShoppingLimit() {
	}

	/**
	 * 선물 발신한도 조회.
	 */
	public void getGiftSendLimit() {
	}

	/**
	 * 선물 수신한도 조회.
	 */
	public void getGiftRecvLimit() {
	}
}
