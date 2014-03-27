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

import java.util.List;

import com.skplanet.storeplatform.purchase.client.order.vo.CreatePurchaseSc;

/**
 * 
 * 구매 상품 건수 처리 서비스 인터페이스
 * 
 * Updated on : 2014. 3. 25. Updated by : 이승택, nTels.
 */
public interface PurchaseCountService {
	/**
	 * 
	 * <pre>
	 * 상품 건수 저장.
	 * </pre>
	 * 
	 * @param createPurchaseScList
	 *            구매 정보 목록
	 * @param prchsStatusCd
	 *            구매상태
	 * @return 추가한 상품 갯수
	 */
	public int insertPurchaseProductCount(List<CreatePurchaseSc> createPurchaseScList, String prchsStatusCd);

	/**
	 * 
	 * <pre>
	 * [DUMMY] 비트랜잭션 테스트 진행.. 상품 건수 저장.
	 * </pre>
	 * 
	 * @param createPurchaseScList
	 *            구매 정보 목록
	 * @param prchsStatusCd
	 *            구매상태
	 * @return 추가한 상품 갯수
	 */
	public int dummyPurchaseProductCount(List<CreatePurchaseSc> createPurchaseScList, String prchsStatusCd);
}
