package com.skplanet.storeplatform.purchase.payplanet.service;

import com.skplanet.storeplatform.purchase.client.common.vo.PpProperty;

public interface PurchasePayPlanetService {

	/**
	 * 
	 * <pre>
	 * Pay Planet 가맹점 정보 조회.
	 * </pre>
	 * 
	 * @param ppProperty
	 *            PayPlanet 프로퍼티 조회 조건
	 * @return Pay Planet 가맹점 정보
	 */
	public PpProperty searchPayPlanetShopInfo(PpProperty ppProperty);

	/**
	 * 
	 * <pre>
	 * Pay Planet 가맹점 ID를 통한 가맹점 정보 조회.
	 * </pre>
	 * 
	 * @param ppProperty
	 *            PayPlanet 프로퍼티 조회 조건
	 * @return Pay Planet 가맹점 정보
	 */
	public PpProperty searchPayPlanetShopInfoByMid(PpProperty ppProperty);

	/**
	 * 
	 * <pre>
	 * SKT 결제/결제취소 용 SYSTEM DIVISION 조회.
	 * </pre>
	 * 
	 * @param ppProperty
	 *            PayPlanet 프로퍼티 조회 조건
	 * @return Pay Planet 가맹점 정보
	 */
	public PpProperty searchDcbSystemDivision(PpProperty ppProperty);
}
