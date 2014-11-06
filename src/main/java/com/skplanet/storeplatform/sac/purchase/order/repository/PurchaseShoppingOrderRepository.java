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

import com.skplanet.storeplatform.external.client.shopping.vo.CouponPublishEcRes;
import com.skplanet.storeplatform.external.client.shopping.vo.CouponPublishV2EcRes;
import com.skplanet.storeplatform.sac.purchase.order.vo.PurchaseUserDevice;

/**
 * 
 * Biz 쿠폰발급요청 repository
 * 
 * Updated on : 2014. 2. 27. Updated by : ntels_yjw, nTels.
 */
public interface PurchaseShoppingOrderRepository {

	/**
	 * 
	 * <pre>
	 * 쿠폰 발급 V2 요청.
	 * </pre>
	 * 
	 * @param prchsId
	 *            구매 ID
	 * @param couponCode
	 *            CMS 쿠폰코드
	 * @param itemCode
	 *            CMS 단품코드
	 * @param buyDeviceId
	 *            구매자 MDN
	 * @param useDeviceIdList
	 *            이용자 MDN 목록
	 * @return 발급 요청 결과 개체
	 */
	public CouponPublishV2EcRes createCouponPublishV2(String prchsId, String couponCode, String itemCode,
			String buyDeviceId, List<String> useDeviceIdList);

	/**
	 * 
	 * <pre>
	 * 쿠폰 발급 요청.
	 * </pre>
	 * 
	 * @param prchsId
	 *            구매 ID
	 * @param useDeviceId
	 *            이용자 MDN
	 * @param buyDeviceId
	 *            구매자 MDN
	 * @param couponCode
	 *            CMS 쿠폰코드
	 * @param itemCode
	 *            CMS 단품코드
	 * @param qty
	 *            수량
	 * @return 발급 요청 결과 개체
	 */
	public CouponPublishEcRes createCouponPublish(String prchsId, String useDeviceId, String buyDeviceId,
			String couponCode, String itemCode, int qty);

	/**
	 * 
	 * <pre>
	 * 쿠폰 발급 취소 요청.
	 * </pre>
	 * 
	 * @param prchsId
	 *            취소할 구매ID
	 */
	public void cancelCouponPublish(String prchsId);

	/**
	 * 
	 * <pre>
	 * Biz쿠폰 발급 요청.
	 * </pre>
	 * 
	 * @param prchsId
	 *            구매ID
	 * @param adminId
	 *            회원(어드민) ID
	 * @param deviceId
	 *            MDN
	 * @param couponCode
	 *            CMS 쿠폰코드
	 * @param receiverList
	 *            수신자목록
	 */
	public void createBizCouponPublish(String prchsId, String adminId, String deviceId, String couponCode,
			List<PurchaseUserDevice> receiverList);
}
