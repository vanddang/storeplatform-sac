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

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.skplanet.storeplatform.external.client.shopping.sci.ShoppingSCI;
import com.skplanet.storeplatform.external.client.shopping.vo.BizCouponPublishDetailEcReq;
import com.skplanet.storeplatform.external.client.shopping.vo.BizCouponPublishEcReq;
import com.skplanet.storeplatform.external.client.shopping.vo.BizCouponPublishEcRes;
import com.skplanet.storeplatform.external.client.shopping.vo.CouponPublishCancelEcReq;
import com.skplanet.storeplatform.external.client.shopping.vo.CouponPublishCancelEcRes;
import com.skplanet.storeplatform.external.client.shopping.vo.CouponPublishEcReq;
import com.skplanet.storeplatform.external.client.shopping.vo.CouponPublishEcRes;
import com.skplanet.storeplatform.sac.purchase.order.vo.PurchaseUserDevice;

/**
 * 
 * Biz 쿠폰발급요청 repositoryImpl
 * 
 * Updated on : 2014. 2. 27. Updated by : ntels_yjw, nTels.
 */
@Component
public class PurchaseShoppingOrderRepositoryImpl implements PurchaseShoppingOrderRepository {
	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private ShoppingSCI shoppingSCI;

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
	@Override
	public CouponPublishEcRes createCouponPublish(String prchsId, String useDeviceId, String buyDeviceId,
			String couponCode, String itemCode, int qty) {
		CouponPublishEcReq couponPublishEcReq = new CouponPublishEcReq();
		couponPublishEcReq.setPrchsId(prchsId);
		couponPublishEcReq.setUseMdn(useDeviceId);
		couponPublishEcReq.setBuyMdn(buyDeviceId);
		couponPublishEcReq.setCouponCode(couponCode);
		couponPublishEcReq.setItemCode(itemCode);
		couponPublishEcReq.setItemCount(qty);

		this.logger.info("PRCHS,ORDER,SAC,SHOPPING_CMS,PUBLISH,REQ,{}", couponPublishEcReq);
		CouponPublishEcRes couponPublishEcRes = this.shoppingSCI.createCouponPublish(couponPublishEcReq);
		this.logger.info("PRCHS,ORDER,SAC,SHOPPING_CMS,PUBLISH,RES,{}", couponPublishEcRes);

		return couponPublishEcRes;
	}

	/**
	 * 
	 * <pre>
	 * 쿠폰 발급 취소 요청.
	 * </pre>
	 * 
	 * @param prchsId
	 *            취소할 구매ID
	 */
	@Override
	public void cancelCouponPublish(String prchsId) {
		CouponPublishCancelEcReq couponPublishCancelEcReq = new CouponPublishCancelEcReq();
		couponPublishCancelEcReq.setPrchsId(prchsId);

		this.logger.info("PRCHS,ORDER,SAC,SHOPPING_CMS,CANCEL,REQ,{}", couponPublishCancelEcReq);
		CouponPublishCancelEcRes couponPublishCancelEcRes = this.shoppingSCI
				.cancelCouponPublish(couponPublishCancelEcReq);
		this.logger.info("PRCHS,ORDER,SAC,SHOPPING_CMS,CANCEL,RES,{}", couponPublishCancelEcRes);
	}

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
	@Override
	public void createBizCouponPublish(String prchsId, String adminId, String deviceId, String couponCode,
			List<PurchaseUserDevice> receiverList) {
		List<BizCouponPublishDetailEcReq> bizCouponPublishDetailEcList = new ArrayList<BizCouponPublishDetailEcReq>();

		BizCouponPublishDetailEcReq bizCouponPublishDetailEcReq = null;
		for (PurchaseUserDevice receiver : receiverList) {
			bizCouponPublishDetailEcReq = new BizCouponPublishDetailEcReq();
			bizCouponPublishDetailEcReq.setPrchsId(prchsId);
			bizCouponPublishDetailEcReq.setMdn(receiver.getDeviceId());
			bizCouponPublishDetailEcList.add(bizCouponPublishDetailEcReq);
		}

		BizCouponPublishEcReq bizCouponPublishEcReq = new BizCouponPublishEcReq();
		bizCouponPublishEcReq.setAdminId(adminId);
		bizCouponPublishEcReq.setMdn(deviceId);
		bizCouponPublishEcReq.setCouponCode(couponCode);
		bizCouponPublishEcReq.setBizCouponPublishDetailList(bizCouponPublishDetailEcList);

		this.logger.info("PRCHS,ORDER,SAC,SHOPPING_CMS,BIZ,PUBLISH,REQ,{}", bizCouponPublishEcReq);
		BizCouponPublishEcRes bizCouponPublishEcRes = this.shoppingSCI.createBizCouponPublish(bizCouponPublishEcReq);
		this.logger.info("PRCHS,ORDER,SAC,SHOPPING_CMS,BIZ,PUBLISH,RES,{}", bizCouponPublishEcRes);
	}
}
