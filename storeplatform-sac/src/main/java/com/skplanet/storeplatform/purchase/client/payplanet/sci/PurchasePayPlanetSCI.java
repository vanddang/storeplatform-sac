/*
 * Copyright (c) 2013 SK planet.
 * All right reserved.
 *
 * This software is the confidential and proprietary information of SK planet.
 * You shall not disclose such Confidential Information and
 * shall use it only in accordance with the terms of the license agreement
 * you entered into with SK planet.
 */
package com.skplanet.storeplatform.purchase.client.payplanet.sci;

import com.skplanet.storeplatform.framework.core.proxy.SCI;
import com.skplanet.storeplatform.purchase.client.common.vo.PpProperty;

/**
 * 
 * PayPlanet 정보 인터페이스
 * 
 * Updated on : 2015. 3. 2. Updated by : 이승택, nTels.
 */
@SCI
public interface PurchasePayPlanetSCI {

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
