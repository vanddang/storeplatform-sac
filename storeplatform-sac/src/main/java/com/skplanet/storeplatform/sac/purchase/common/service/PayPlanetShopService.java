/*
 * Copyright (c) 2013 SK planet.
 * All right reserved.
 *
 * This software is the confidential and proprietary information of SK planet.
 * You shall not disclose such Confidential Information and
 * shall use it only in accordance with the terms of the license agreement
 * you entered into with SK planet.
 */
package com.skplanet.storeplatform.sac.purchase.common.service;

import com.skplanet.storeplatform.purchase.client.common.vo.PpProperty;

/**
 * 
 * Pay Planet 가맹점 정보 서비스
 * 
 * Updated on : 2014. 3. 25. Updated by : 이승택, nTels.
 */
public interface PayPlanetShopService {

	/**
	 * 
	 * <pre>
	 * SKT 결제/결제취소 용 SYSTEM DIVISION 조회.
	 * </pre>
	 * 
	 * @param tenantId
	 *            조회할 가맹점의 테넌트 ID
	 * @param prodId
	 *            상품 ID
	 * @param parentProdId
	 *            인앱 모상품 APP ID
	 * @param tenantProdGrpCd
	 *            테넌트 상품 분류 코드
	 * @return 결제/결제취소 용 SYSTEM DIVISION 정보
	 */
	public PpProperty getDcbSystemDivision(String tenantId, String prodId, String parentProdId, String tenantProdGrpCd);

	/**
	 * 
	 * <pre>
	 * Pay Planet 가맹점 정보 조회.
	 * </pre>
	 * 
	 * @param tenantId
	 *            조회할 가맹점의 테넌트 ID
	 * @param apiTypeCd
	 *            P/P 연동 API 타입 코드
	 * @param prchsReqPathCd
	 *            구매 요청 경로 코드
	 * @return Pay Planet 가맹점 정보
	 */
	public PpProperty getPayPlanetShopInfo(String tenantId, String apiTypeCd, String prchsReqPathCd);

	/**
	 * 
	 * <pre>
	 * Pay Planet 가맹점 ID 여부 확인.
	 * </pre>
	 * 
	 * @param mid
	 * @return
	 */
	public boolean startsWithPayPlanetMID(String mid);
}
