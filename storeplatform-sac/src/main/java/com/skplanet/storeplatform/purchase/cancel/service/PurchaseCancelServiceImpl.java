/*
 * Copyright (c) 2013 SK planet.
 * All right reserved.
 *
 * This software is the confidential and proprietary information of SK planet.
 * You shall not disclose such Confidential Information and
 * shall use it only in accordance with the terms of the license agreement
 * you entered into with SK planet.
 */
package com.skplanet.storeplatform.purchase.cancel.service;

import com.skplanet.storeplatform.sac.purchase.constant.PurchaseConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.skplanet.storeplatform.framework.core.exception.StorePlatformException;
import com.skplanet.storeplatform.framework.core.persistence.dao.CommonDAO;
import com.skplanet.storeplatform.framework.core.util.StringUtils;
import com.skplanet.storeplatform.purchase.client.cancel.vo.AutoPaymentScReq;
import com.skplanet.storeplatform.purchase.client.cancel.vo.AutoPaymentScRes;
import com.skplanet.storeplatform.purchase.client.cancel.vo.PurchaseCancelPaymentDetailScReq;
import com.skplanet.storeplatform.purchase.client.cancel.vo.PurchaseCancelScReq;
import com.skplanet.storeplatform.purchase.client.cancel.vo.PurchaseCancelScRes;
import com.skplanet.storeplatform.purchase.client.cancel.vo.PurchaseScReq;
import com.skplanet.storeplatform.purchase.client.cancel.vo.PurchaseScRes;
import com.skplanet.storeplatform.purchase.client.common.vo.AutoPrchs;
import com.skplanet.storeplatform.purchase.client.common.vo.Payment;
import com.skplanet.storeplatform.purchase.client.common.vo.Prchs;
import com.skplanet.storeplatform.purchase.client.common.vo.PrchsDtl;
import com.skplanet.storeplatform.purchase.product.count.service.PurchaseCountSCService;

/**
 * 구매 취소 관련 Service Implements.
 * 
 * Updated on : 2014. 1. 20. Updated by : nTels_cswoo81, nTels.
 */
@Service
public class PurchaseCancelServiceImpl implements PurchaseCancelService {

	@Autowired
	private PurchaseCountSCService purchaseCountSCService;

	@Autowired
	@Qualifier("scPurchase")
	private CommonDAO scPurchaseDAO;

	@Override
	public PurchaseScRes getPurchase(PurchaseScReq purchaseScReq) {

		PurchaseScRes purchaseScRes = new PurchaseScRes();

		// 구매 정보 조회.
		purchaseScRes.setPrchs(this.scPurchaseDAO.queryForObject("Cancel.getPrchs", purchaseScReq, Prchs.class));

		// 구매 상세 정보 조회.
		purchaseScRes.setPrchsDtlList(this.scPurchaseDAO.queryForList("Cancel.getPrchsDtlList", purchaseScReq,
				PrchsDtl.class));

		// 결제 정보 조회.
		purchaseScRes.setPaymentList(this.scPurchaseDAO.queryForList("Cancel.getPaymentList", purchaseScReq,
				Payment.class));

		return purchaseScRes;

	}

	@Override
	public PurchaseCancelScRes updatePurchaseCancel(PurchaseCancelScReq purchaseCancelScReq) {

		PurchaseCancelScRes purchaseCancelScRes = new PurchaseCancelScRes();

		/**
		 * OGG 오류 예방차원에서 순서 지켜야 한다고 함. (건수 -> 결제 -> 구매 -> 자동구매 -> 구매상세)
		 */
		// 구매 건수 취소 추가.
		purchaseCancelScRes.setPrchsProdCntCnt(this.purchaseCountSCService
				.insertPurchaseProductCount(purchaseCancelScReq.getInsertPurchaseProductCountScReq()));

		// 결제 취소.
		int paymentCancelCnt = 0;
		for (PurchaseCancelPaymentDetailScReq purchaseCancelPaymentDetailScReq : purchaseCancelScReq
				.getPurchaseCancelPaymentDetailScReqList()) {
			if (StringUtils.isEmpty(purchaseCancelPaymentDetailScReq.getCurrPrchsStatusCd())) {
				// 여기서 셋팅이 안되나?
				purchaseCancelPaymentDetailScReq.setCurrPrchsStatusCd(purchaseCancelScReq.getCurrPrchsStatusCd());
			}
            // 해당 결제수단이 결제취소 상태인 경우에만 payment_cancel_dt에 값을 기록한다.
            if(StringUtils.equals(purchaseCancelPaymentDetailScReq.getPaymentStatusCd(), PurchaseConstants.PRCHS_STATUS_CANCEL)){
                purchaseCancelPaymentDetailScReq.setCancelDt(purchaseCancelScReq.getCancelDt());
            }
			purchaseCancelPaymentDetailScReq.setSystemId(purchaseCancelScReq.getReqUserId());
			paymentCancelCnt = paymentCancelCnt
					+ this.scPurchaseDAO.update("Cancel.updatePayment", purchaseCancelPaymentDetailScReq);
		}
		purchaseCancelScRes.setPaymentCancelCnt(paymentCancelCnt);

		// 구매 취소.
		purchaseCancelScRes.setPrchsCancelCnt(this.scPurchaseDAO.update("Cancel.updatePrchs", purchaseCancelScReq));

		// 자동구매 취소.
		purchaseCancelScRes.setAutoPrchsCancelCnt(this.scPurchaseDAO.update("Cancel.updateAutoPrchs",
				purchaseCancelScReq));

		// 구매 상세 취소.
		purchaseCancelScRes.setPrchsDtlCancelCnt(this.scPurchaseDAO
				.update("Cancel.updatePrchsDtl", purchaseCancelScReq));

		if (purchaseCancelScRes.getPrchsDtlCancelCnt() < 1 && purchaseCancelScRes.getPrchsCancelCnt() < 1) {
			throw new StorePlatformException("SC_PUR_7101");
		}

		return purchaseCancelScRes;

	}

	@Override
	public PurchaseCancelScRes updatePaymentError(PurchaseCancelScReq purchaseCancelScReq) {

		PurchaseCancelScRes purchaseCancelScRes = new PurchaseCancelScRes();

		// 결제 취소.
		purchaseCancelScRes.setPaymentCancelCnt(this.scPurchaseDAO.update("Cancel.updatePaymentError",
				purchaseCancelScReq));

		return purchaseCancelScRes;
	}

	@Override
	public AutoPaymentScRes getAutoPaymentInfo(AutoPaymentScReq autoPaymentScReq) {
		AutoPaymentScRes autoPaymentScRes = new AutoPaymentScRes();
		autoPaymentScRes.setAutoPrchs(this.scPurchaseDAO.queryForObject("Cancel.getAutoPrchs", autoPaymentScReq,
				AutoPrchs.class));
		return autoPaymentScRes;

	}

	/**
	 * 
	 * <pre>
	 * 현재일시 조회.
	 * </pre>
	 * 
	 * @return 현재일시
	 */
	@Override
	public String getNowDate() {

		return this.scPurchaseDAO.queryForObject("Cancel.getNowDate", "", String.class);
	}

	/**
	 * <pre>
	 * 특가쿠폰 사용 여부.
	 * </pre>
	 * 
	 * @param req
	 *            특가쿠폰 사용 여부 조회할 결제정보
	 * @return 특가쿠폰 사용 여부
	 */
	@Override
	public boolean useSpecialCoupon(PurchaseScReq req) {
		return this.scPurchaseDAO.queryForObject("Cancel.getSpecialCouponUseCount", req, java.lang.Integer.class) > 0;
	}

}
