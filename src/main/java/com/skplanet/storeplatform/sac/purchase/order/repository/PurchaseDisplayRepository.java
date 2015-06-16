/*
 * Copyright (c) 2013 SK planet.
 * All right reserved.
 *
 * This software is the confidential and proprietary information of SK planet.
 * You shall not disclose such Confidential Information and
 * shall use it only in accordance with the terms of the license agreement
 * you entered into with SK planet.
 */
package com.skplanet.storeplatform.sac.purchase.order.repository;

import java.util.List;
import java.util.Map;

import com.skplanet.storeplatform.sac.client.internal.display.localsci.vo.CmpxProductInfo;
import com.skplanet.storeplatform.sac.client.internal.display.localsci.vo.CmpxProductInfoList;
import com.skplanet.storeplatform.sac.client.internal.display.localsci.vo.CmpxProductSacReq;
import com.skplanet.storeplatform.sac.client.internal.display.localsci.vo.FreePassInfo;
import com.skplanet.storeplatform.sac.client.internal.display.localsci.vo.IapProductInfoRes;
import com.skplanet.storeplatform.sac.purchase.order.vo.PurchaseProduct;

/**
 * 
 * 구매 시 필요한 전시Part SAC internal I/F repository
 * 
 * Updated on : 2014. 2. 27. Updated by : 이승택, nTels.
 */
public interface PurchaseDisplayRepository {

	/**
	 * 
	 * <pre>
	 * 상품 정보 조회.
	 * </pre>
	 * 
	 * @param tenantId
	 *            테넌트ID
	 * @param langCd
	 *            언어코드
	 * @param deviceModelCd
	 *            단말모델코드
	 * @param prodIdList
	 *            조회할 상품ID 목록
	 * @param bFlat
	 *            정액상품 여부
	 * @return 상품ID에 매핑되는 상품정보를 담은 Map
	 */
	public Map<String, PurchaseProduct> searchPurchaseProductList(String tenantId, String langCd, String deviceModelCd,
			List<String> prodIdList, boolean bFlat);

	/**
	 * 
	 * <pre>
	 * IAP 상품정보 조회.
	 * </pre>
	 * 
	 * @param partProdId
	 *            IAP 상품ID
	 * @param tenantId
	 *            테넌트ID
	 * @return IAP 상품정보 VO
	 */
	public IapProductInfoRes searchIapProductInfo(String partProdId, String tenantId);

	/**
	 * 
	 * <pre>
	 * 정액제 상품 DRM 정보 조회.
	 * </pre>
	 * 
	 * @param tenantId
	 *            테넌트ID
	 * @param langCd
	 *            언어코드
	 * @param fixrateProdId
	 *            정액제 상품 ID
	 * @param episodeProdId
	 *            해당 정액제 상품을 이용하여 구매할 상품 ID
	 * @return 정액제 상품 DRM 정보
	 */
	public FreePassInfo searchFreePassDrmInfo(String tenantId, String langCd, String fixrateProdId, String episodeProdId);

	/**
	 * 
	 * <pre>
	 * 이용권에 등록된 상품 목록 조회.
	 * </pre>
	 * 
	 * @param cmpxProductSacReq
	 *            이용권에 등록된 상품 목록 조회 요청VO
	 * @return 이용권에 등록된 상품 목록
	 */
	public List<CmpxProductInfoList> searchCmpxProductList(CmpxProductSacReq cmpxProductSacReq);

	/**
	 * 
	 * <pre>
	 * 이용권 및 에피소드 상품 정보 조회.
	 * </pre>
	 * 
	 * @param tenantId
	 *            테넌트ID
	 * @param langCd
	 *            언어코드
	 * @param fixrateProdId
	 *            정액제 상품 ID
	 * @param episodeProdId
	 *            해당 정액제 상품을 이용하여 구매할 상품 ID
	 * @param chapter
	 *            회차 정보 (이용권에 매핑된 상품 중 요청한 회차만 조회)
	 * @return 이용권 및 에피소드 상품 정보
	 */
	public CmpxProductInfo searchCmpxProductInfo(String tenantId, String langCd, String fixrateProdId,
			String episodeProdId, String chapter);

	/**
	 * 
	 * <pre>
	 * 쇼핑 특가 상품에 대해 품절 등록.
	 * </pre>
	 * 
	 * @param tenantId
	 *            테넌트ID
	 * @param prodId
	 *            품절상태로 등록할 쇼핑특가상품ID
	 */
	public void updateSpecialPriceSoldOut(String tenantId, String prodId);

	/**
	 * 실시간으로 구매 및 구매 취소시에 쇼핑 특가 상품에 대한 구매수를 업데이트 한다.
	 * 
	 * @param tenentId
	 *            테넌트ID
	 * @param purchaseId
	 *            구매ID
	 * @param productId
	 *            상품ID
	 * @param purchaseStatusCd
	 *            구매상태 코드
	 * @param purchaseCount
	 *            업데이트할 구매건수
	 * @param purchaseDate
	 *            구매 일시
	 * @param purchaseCancelDate
	 *            구매취소 일시
	 */
	public void updateSpecialPurchaseCount(String tenentId, String purchaseId, String productId,
			String purchaseStatusCd, Integer purchaseCount, String purchaseDate, String purchaseCancelDate);
}
