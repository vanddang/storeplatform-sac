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

import com.skplanet.storeplatform.external.client.shopping.sci.ShoppingSCI;
import com.skplanet.storeplatform.external.client.shopping.vo.*;
import com.skplanet.storeplatform.sac.purchase.order.vo.PurchaseUserDevice;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

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
	 * @param bGift
	 *            선물여부 (true: 선물)
	 * @return 발급 요청 결과 개체
	 */
	@Override
	public CouponPublishV2EcRes createCouponPublishV2(String prchsId, String couponCode, String itemCode,
			String buyDeviceId, List<String> useDeviceIdList, boolean bGift) {
		CouponPublishV2EcReq couponPublishV2EcReq = new CouponPublishV2EcReq();
		couponPublishV2EcReq.setPrchsId(prchsId);
		couponPublishV2EcReq.setCouponCode(couponCode);
		couponPublishV2EcReq.setItemCode(itemCode);
		couponPublishV2EcReq.setBuyMdn(buyDeviceId);
		couponPublishV2EcReq.setGiftFlag(bGift ? "Y" : "N");
		StringBuffer sbMdns = new StringBuffer();
		int cnt = 0;
		for (String mdn : useDeviceIdList) {
			if (cnt > 0) {
				sbMdns.append(",");
			}
			cnt++;
			sbMdns.append(mdn);
		}
		couponPublishV2EcReq.setUseMdns(sbMdns.toString());

		this.logger.info("PRCHS,ORDER,SAC,SHOPPING,PUBLISH,REQ,V2,{}",
				ReflectionToStringBuilder.toString(couponPublishV2EcReq, ToStringStyle.SHORT_PREFIX_STYLE));
		CouponPublishV2EcRes couponPublishV2EcRes = this.shoppingSCI.createCouponPublishV2(couponPublishV2EcReq);
		this.logger.info("PRCHS,ORDER,SAC,SHOPPING,PUBLISH,RES,V2,{}",
				ReflectionToStringBuilder.toString(couponPublishV2EcRes, ToStringStyle.SHORT_PREFIX_STYLE));

		return couponPublishV2EcRes;
	}

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

		this.logger.info("PRCHS,ORDER,SAC,SHOPPING,PUBLISH,REQ,{}",
				ReflectionToStringBuilder.toString(couponPublishEcReq, ToStringStyle.SHORT_PREFIX_STYLE));
		CouponPublishEcRes couponPublishEcRes = this.shoppingSCI.createCouponPublish(couponPublishEcReq);
		this.logger.info("PRCHS,ORDER,SAC,SHOPPING,PUBLISH,RES,{}",
				ReflectionToStringBuilder.toString(couponPublishEcRes, ToStringStyle.SHORT_PREFIX_STYLE));

		return couponPublishEcRes;
	}

	/**
	 * <pre>
	 * 상품권 충전.
	 * </pre>
	 * 
	 * @param couponCode
	 *            the prod id
	 * @param prchsId
	 *            the prchs id
	 * @param deviceId
	 *            the device id
	 * @param memberId
	 *            the member id
	 * 
	 * @return the coupon charge ec res
	 */
	@Override
	public CouponChargeEcRes chargeGoods(String couponCode, String userKey, String prchsId, String deviceId,
			String memberId) {
		CouponChargeEcReq couponChargeEcReq = new CouponChargeEcReq();
		couponChargeEcReq.setCouponCode(couponCode);
		couponChargeEcReq.setUserKey(userKey);
		couponChargeEcReq.setPrchsId(prchsId);
		couponChargeEcReq.setMdn(deviceId);
		couponChargeEcReq.setMemberId(memberId);

		this.logger.info("PRCHS,ORDER,SAC,SHOPPING,CHARGE,REQ,{}",
				ReflectionToStringBuilder.toString(couponChargeEcReq, ToStringStyle.SHORT_PREFIX_STYLE));
		CouponChargeEcRes couponChargeEcRes = this.shoppingSCI.chargeGoods(couponChargeEcReq);
		this.logger.info("PRCHS,ORDER,SAC,SHOPPING,CHARGE,RES,{}",
				ReflectionToStringBuilder.toString(couponChargeEcRes, ToStringStyle.SHORT_PREFIX_STYLE));

		return couponChargeEcRes;
	}

	@Override
	public CouponCancelChargeEcRes cancelGoods(String prchsId, String cancelType) {
		CouponCancelChargeEcReq couponCancelChargeEcReq = new CouponCancelChargeEcReq();
		couponCancelChargeEcReq.setPrchsId(prchsId);
		couponCancelChargeEcReq.setCancelType(cancelType);

		this.logger.info("PRCHS,ORDER,SAC,SHOPPING,CANCELCHARGE,REQ,{}",
				ReflectionToStringBuilder.toString(couponCancelChargeEcReq, ToStringStyle.SHORT_PREFIX_STYLE));
		CouponCancelChargeEcRes couponChargeEcRes = this.shoppingSCI.cancelGoods(couponCancelChargeEcReq);
		this.logger.info("PRCHS,ORDER,SAC,SHOPPING,CANCELCHARGE,RES,{}",
				ReflectionToStringBuilder.toString(couponChargeEcRes, ToStringStyle.SHORT_PREFIX_STYLE));

		return couponChargeEcRes;
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

		this.logger.info("PRCHS,ORDER,SAC,SHOPPING,CANCEL,REQ,{}",
				ReflectionToStringBuilder.toString(couponPublishCancelEcReq, ToStringStyle.SHORT_PREFIX_STYLE));
		CouponPublishCancelEcRes couponPublishCancelEcRes = this.shoppingSCI
				.cancelCouponPublish(couponPublishCancelEcReq);
		this.logger.info("PRCHS,ORDER,SAC,SHOPPING,CANCEL,RES,{}",
				ReflectionToStringBuilder.toString(couponPublishCancelEcRes, ToStringStyle.SHORT_PREFIX_STYLE));
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
		int dtlId = 1;
		for (PurchaseUserDevice receiver : receiverList) {
			bizCouponPublishDetailEcReq = new BizCouponPublishDetailEcReq();
			bizCouponPublishDetailEcReq.setPrchsId(prchsId + StringUtils.leftPad(String.valueOf(dtlId++), 4, "0"));
			bizCouponPublishDetailEcReq.setMdn(receiver.getDeviceId());
			bizCouponPublishDetailEcList.add(bizCouponPublishDetailEcReq);
		}

		BizCouponPublishEcReq bizCouponPublishEcReq = new BizCouponPublishEcReq();
		bizCouponPublishEcReq.setAdminId(adminId);
		bizCouponPublishEcReq.setMdn(deviceId);
		bizCouponPublishEcReq.setCouponCode(couponCode);
		bizCouponPublishEcReq.setBizCouponPublishDetailList(bizCouponPublishDetailEcList);

		this.logger.info("PRCHS,ORDER,SAC,SHOPPING,BIZ,PUBLISH,REQ,{}",
				ReflectionToStringBuilder.toString(bizCouponPublishEcReq, ToStringStyle.SHORT_PREFIX_STYLE));
		BizCouponPublishEcRes bizCouponPublishEcRes = this.shoppingSCI.createBizCouponPublish(bizCouponPublishEcReq);
		this.logger.info("PRCHS,ORDER,SAC,SHOPPING,BIZ,PUBLISH,RES,{}",
				ReflectionToStringBuilder.toString(bizCouponPublishEcRes, ToStringStyle.SHORT_PREFIX_STYLE));
	}
}
