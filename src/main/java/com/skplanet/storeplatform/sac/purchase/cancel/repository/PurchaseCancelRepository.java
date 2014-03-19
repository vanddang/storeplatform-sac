/*
 * Copyright (c) 2013 SK planet.
 * All right reserved.
 *
 * This software is the confidential and proprietary information of SK planet.
 * You shall not disclose such Confidential Information and
 * shall use it only in accordance with the terms of the license agreement
 * you entered into with SK planet.
 */
package com.skplanet.storeplatform.sac.purchase.cancel.repository;

import java.util.List;

import com.skplanet.storeplatform.external.client.payplanet.vo.CancelEcRes;
import com.skplanet.storeplatform.external.client.tstore.vo.PayCancelResult;
import com.skplanet.storeplatform.sac.purchase.cancel.vo.PurchaseCancelDetailSacParam;
import com.skplanet.storeplatform.sac.purchase.cancel.vo.PurchaseCancelSacParam;

/**
 * 구매 취소 repository Interface.
 * 
 * Updated on : 2014. 1. 16. Updated by : nTels_cswoo81, nTels.
 */
public interface PurchaseCancelRepository {

	/**
	 * <pre>
	 * 구매 취소 할 구매 상세 정보 조회.
	 * </pre>
	 * 
	 * @param purchaseCancelSacParam
	 *            purchaseCancelSacParam
	 * @param purchaseCancelDetailSacParam
	 *            purchaseCancelDetailSacParam
	 * @return PurchaseCancelDetailSacParam
	 */
	PurchaseCancelDetailSacParam setPurchaseDetailInfo(PurchaseCancelSacParam purchaseCancelSacParam,
			PurchaseCancelDetailSacParam purchaseCancelDetailSacParam);

	/**
	 * <pre>
	 * deviceId를 조회해온다.
	 * </pre>
	 * 
	 * @param userKey
	 *            userKey
	 * @param deviceKey
	 *            deviceKey
	 * @return deviceId
	 */
	String getDeviceId(String userKey, String deviceKey);

	/**
	 * 
	 * <pre>
	 * method 설명.
	 * </pre>
	 * 
	 * @param purchaseCancelSacParam
	 *            purchaseCancelSacParam
	 * @param purchaseCancelDetailSacParam
	 *            purchaseCancelDetailSacParam
	 */
	CancelEcRes cancelPaymentToPayPlanet(PurchaseCancelSacParam purchaseCancelSacParam,
			PurchaseCancelDetailSacParam purchaseCancelDetailSacParam);

	/**
	 * 
	 * <pre>
	 * method 설명.
	 * </pre>
	 * 
	 * @param purchaseCancelSacParam
	 *            purchaseCancelSacParam
	 * @param purchaseCancelDetailSacParam
	 *            purchaseCancelDetailSacParam
	 */
	List<PayCancelResult> cancelPaymentToTStore(PurchaseCancelSacParam purchaseCancelSacParam,
			PurchaseCancelDetailSacParam purchaseCancelDetailSacParam);

	/**
	 * <pre>
	 * 구매 취소 처리 DB 업데이트.
	 * </pre>
	 * 
	 * @param purchaseCancelSacParam
	 *            purchaseCancelSacParam
	 * @param purchaseCancelDetailSacParam
	 *            purchaseCancelDetailSacParam
	 * @return PurchaseCancelDetailSacParam
	 */
	PurchaseCancelDetailSacParam updatePurchaseCancel(PurchaseCancelSacParam purchaseCancelSacParam,
			PurchaseCancelDetailSacParam purchaseCancelDetailSacParam);

	/**
	 * <pre>
	 * 구매 취소 시 상품 구매 개수 차감 처리 해 준다.
	 * </pre>
	 * 
	 * @param purchaseCancelSacParam
	 *            purchaseCancelSacParam
	 * @param purchaseCancelDetailSacParam
	 *            purchaseCancelDetailSacParam
	 */
	void updatePurchaseCount(PurchaseCancelSacParam purchaseCancelSacParam,
			PurchaseCancelDetailSacParam purchaseCancelDetailSacParam);

	/**
	 * 
	 * <pre>
	 * 구매 취소 시 Aom Message Push.
	 * </pre>
	 * 
	 * @param prchsProdDtl
	 *            prchsProdDtl
	 * @return String
	 */
	String aomPush(String deviceId, String appId);

	/**
	 * <pre>
	 * 구매 취소 시 ARM 서버로 라이센스 삭제 요청.
	 * </pre>
	 * 
	 * @param deviceId
	 *            deviceId
	 * @param appId
	 *            appId
	 * @return String
	 */
	String armRemoveLicense(String deviceId, String appId);

}
