/*
 * Copyright (c) 2013 SK planet.
 * All right reserved.
 *
 * This software is the confidential and proprietary information of SK planet.
 * You shall not disclose such Confidential Information and
 * shall use it only in accordance with the terms of the license agreement
 * you entered into with SK planet.
 */
package com.skplanet.storeplatform.purchase.payplanet.sci;

import org.springframework.beans.factory.annotation.Autowired;

import com.skplanet.storeplatform.framework.integration.bean.LocalSCI;
import com.skplanet.storeplatform.purchase.client.common.vo.PpProperty;
import com.skplanet.storeplatform.purchase.client.payplanet.sci.PurchasePayPlanetSCI;
import com.skplanet.storeplatform.purchase.payplanet.service.PurchasePayPlanetService;

@LocalSCI
public class PurchasePayPlanetSCIController implements PurchasePayPlanetSCI {
	@Autowired
	private PurchasePayPlanetService purchasePayPlanetService;

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
	@Override
	public PpProperty searchPayPlanetShopInfo(PpProperty ppProperty) {
		return this.purchasePayPlanetService.searchPayPlanetShopInfo(ppProperty);
	}

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
	@Override
	public PpProperty searchPayPlanetShopInfoByMid(PpProperty ppProperty) {
		return this.purchasePayPlanetService.searchPayPlanetShopInfoByMid(ppProperty);
	}

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
	@Override
	public PpProperty searchDcbSystemDivision(PpProperty ppProperty) {
		return this.purchasePayPlanetService.searchDcbSystemDivision(ppProperty);
	}

}
