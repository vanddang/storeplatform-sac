/*
 * Copyright (c) 2013 SK planet.
 * All right reserved.
 *
 * This software is the confidential and proprietary information of SK planet.
 * You shall not disclose such Confidential Information and
 * shall use it only in accordance with the terms of the license agreement
 * you entered into with SK planet.
 */
package com.skplanet.storeplatform.sac.purchase.cancel.service;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.skplanet.storeplatform.external.client.shopping.sci.ShoppingSCI;
import com.skplanet.storeplatform.external.client.shopping.vo.CouponPublishCancelEcReq;
import com.skplanet.storeplatform.external.client.uaps.vo.UserEcRes;
import com.skplanet.storeplatform.framework.core.exception.StorePlatformException;
import com.skplanet.storeplatform.framework.core.exception.vo.ErrorInfo;
import com.skplanet.storeplatform.framework.core.helper.MultiMessageSourceAccessor;
import com.skplanet.storeplatform.framework.core.util.StringUtils;
import com.skplanet.storeplatform.purchase.client.history.vo.AutoPaymentCancelScReq;
import com.skplanet.storeplatform.purchase.client.history.vo.AutoPaymentCancelScRes;
import com.skplanet.storeplatform.sac.api.util.DateUtil;
import com.skplanet.storeplatform.sac.client.internal.display.localsci.sci.PaymentInfoSCI;
import com.skplanet.storeplatform.sac.client.internal.display.localsci.vo.PaymentInfo;
import com.skplanet.storeplatform.sac.client.internal.display.localsci.vo.PaymentInfoSacReq;
import com.skplanet.storeplatform.sac.client.internal.display.localsci.vo.PaymentInfoSacRes;
import com.skplanet.storeplatform.sac.client.internal.purchase.history.sci.HistoryInternalSCI;
import com.skplanet.storeplatform.sac.client.internal.purchase.history.vo.HistoryCountSacInReq;
import com.skplanet.storeplatform.sac.client.internal.purchase.history.vo.HistoryCountSacInRes;
import com.skplanet.storeplatform.sac.client.internal.purchase.shopping.sci.ShoppingInternalSCI;
import com.skplanet.storeplatform.sac.client.internal.purchase.shopping.vo.CouponUseStatusDetailSacInRes;
import com.skplanet.storeplatform.sac.client.internal.purchase.shopping.vo.CouponUseStatusSacInReq;
import com.skplanet.storeplatform.sac.client.internal.purchase.shopping.vo.CouponUseStatusSacInRes;
import com.skplanet.storeplatform.sac.purchase.cancel.repository.PurchaseCancelRepository;
import com.skplanet.storeplatform.sac.purchase.cancel.vo.PaymentSacParam;
import com.skplanet.storeplatform.sac.purchase.cancel.vo.PrchsDtlSacParam;
import com.skplanet.storeplatform.sac.purchase.cancel.vo.PrchsSacParam;
import com.skplanet.storeplatform.sac.purchase.cancel.vo.PurchaseCancelDetailSacParam;
import com.skplanet.storeplatform.sac.purchase.cancel.vo.PurchaseCancelDetailSacResult;
import com.skplanet.storeplatform.sac.purchase.cancel.vo.PurchaseCancelSacParam;
import com.skplanet.storeplatform.sac.purchase.cancel.vo.PurchaseCancelSacResult;
import com.skplanet.storeplatform.sac.purchase.common.service.PayPlanetShopService;
import com.skplanet.storeplatform.sac.purchase.common.vo.PayPlanetShop;
import com.skplanet.storeplatform.sac.purchase.constant.PurchaseConstants;
import com.skplanet.storeplatform.sac.purchase.history.service.AutoPaymentCancelSacService;
import com.skplanet.storeplatform.sac.purchase.order.repository.PurchaseUapsRespository;

/**
 * 구매 취소 Service Implements.
 * 
 * Updated on : 2014. 1. 16. Updated by : nTels_cswoo81, nTels.
 */
@Service
public class PurchaseCancelServiceImpl implements PurchaseCancelService {

	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private PurchaseCancelRepository purchaseCancelRepository;

	@Autowired
	private MultiMessageSourceAccessor multiMessageSourceAccessor;

	@Autowired
	private HistoryInternalSCI historyInternalSCI;

	@Autowired
	private ShoppingInternalSCI shoppingInternalSCI;

	@Autowired
	private ShoppingSCI shoppingSCI;

	@Autowired
	private AutoPaymentCancelSacService autoPaymentCancelSacService;

	@Autowired
	private PaymentInfoSCI paymentInfoSCI;

	@Autowired
	private PurchaseUapsRespository purchaseUapsRespository;

	@Autowired
	private PayPlanetShopService payPlanetShopService;

	@Override
	public PurchaseCancelSacResult cancelPurchaseList(PurchaseCancelSacParam purchaseCancelSacParam) {

		PurchaseCancelSacResult purchaseCancelSacResult = new PurchaseCancelSacResult();
		List<PurchaseCancelDetailSacResult> prchsCancelList = new ArrayList<PurchaseCancelDetailSacResult>();
		int totCnt = 0;
		int successCnt = 0;
		int failCnt = 0;

		for (PurchaseCancelDetailSacParam purchaseCancelDetailSacParam : purchaseCancelSacParam.getPrchsCancelList()) {

			totCnt++;
			PurchaseCancelDetailSacResult purchaseCancelDetailSacResult = new PurchaseCancelDetailSacResult();
			try {

				if (purchaseCancelSacParam.getPrchsCancelServiceType() == PurchaseConstants.PRCHS_CANCEL_SERVICE_TCASH) {
					this.executePurchaseCancelForTCash(purchaseCancelSacParam, purchaseCancelDetailSacParam);
				} else {
					purchaseCancelDetailSacResult = this.executePurchaseCancel(purchaseCancelSacParam,
							purchaseCancelDetailSacParam);
				}

				/** RO 삭제 처리. */
				for (PrchsDtlSacParam prchsDtlSacParam : purchaseCancelDetailSacParam.getPrchsDtlSacParamList()) {
					if (!StringUtils.startsWith(prchsDtlSacParam.getTenantProdGrpCd(),
							PurchaseConstants.TENANT_PRODUCT_GROUP_APP)) {
						// APP 상품이 아니면 통과.
						continue;
					}
					try {
						this.removeRO(purchaseCancelSacParam, purchaseCancelDetailSacParam, prchsDtlSacParam);
					} catch (Exception e) {
						this.logger.info("RO 삭제 실패! ========= {}, {}", prchsDtlSacParam.getProdId(), e);
					}
				}

			} catch (StorePlatformException e) {

				purchaseCancelDetailSacResult = new PurchaseCancelDetailSacResult();
				purchaseCancelDetailSacResult.setPrchsId(purchaseCancelDetailSacParam.getPrchsId());

				ErrorInfo errorInfo = e.getErrorInfo();

				purchaseCancelDetailSacResult.setResultCd(errorInfo.getCode());
				if (StringUtils.isBlank(errorInfo.getMessage())) {
					purchaseCancelDetailSacResult.setResultMsg(this.multiMessageSourceAccessor.getMessage(
							errorInfo.getCode(), errorInfo.getArgs()));
				} else {
					purchaseCancelDetailSacResult.setResultMsg(errorInfo.getMessage());
				}

			} catch (Exception e) {

				this.logger.debug("SAC_PUR_9999 : {}", e);

				purchaseCancelDetailSacResult = new PurchaseCancelDetailSacResult();
				purchaseCancelDetailSacResult.setPrchsId(purchaseCancelDetailSacParam.getPrchsId());
				purchaseCancelDetailSacResult.setResultCd("SAC_PUR_9999");

				purchaseCancelDetailSacResult.setResultMsg(this.multiMessageSourceAccessor.getMessage("SAC_PUR_9999")
						+ "cause : " + e.getMessage());

			}

			if (StringUtils.equals("SAC_PUR_0000", purchaseCancelDetailSacResult.getResultCd())) {
				successCnt++;
			} else {
				failCnt++;
			}

			prchsCancelList.add(purchaseCancelDetailSacResult);

		}

		purchaseCancelSacResult.setTotCnt(totCnt);
		purchaseCancelSacResult.setSuccessCnt(successCnt);
		purchaseCancelSacResult.setFailCnt(failCnt);
		purchaseCancelSacResult.setPrchsCancelList(prchsCancelList);

		return purchaseCancelSacResult;

	}

	// TODO : TEST MDN 확인. 요건 어케 할지 생각 좀 해봐야 함..
	// TODO : 쿠폰 처리 어떻게 함? T Store 결제일 때..
	// TODO : 테넌트 정책 반영 어떻게 함?

	/*
	 * 오퍼링 상품은 어케 처리? --
	 * 
	 * T Store Cash 취소 일 경우 취소할 캐쉬량이 남이 있는지 확인 필요(예약)
	 * 
	 * 부분유료화 상품 취소 가능?
	 */
	@Override
	public PurchaseCancelDetailSacResult executePurchaseCancel(PurchaseCancelSacParam purchaseCancelSacParam,
			PurchaseCancelDetailSacParam purchaseCancelDetailSacParam) {

		PurchaseCancelDetailSacResult purchaseCancelDetailSacResult = new PurchaseCancelDetailSacResult();

		/** 구매 정보 조회. */
		this.purchaseCancelRepository.setPurchaseDetailInfo(purchaseCancelSacParam, purchaseCancelDetailSacParam);
		if (purchaseCancelDetailSacParam.getPrchsDtlSacParamList() == null
				|| purchaseCancelDetailSacParam.getPrchsDtlSacParamList().size() < 1) {
			// 구매 상세 정보가 없으면 구매 취소 불가.
			throw new StorePlatformException("SAC_PUR_8100");
		}

		/** 구매 정보 체크. */
		PrchsSacParam prchsSacParam = purchaseCancelDetailSacParam.getPrchsSacParam();
		if (!StringUtils.equals(PurchaseConstants.PRCHS_STATUS_COMPT, prchsSacParam.getStatusCd())) {
			// 구매 완료 상태가 아니면 취소 불가.
			throw new StorePlatformException("SAC_PUR_8101");
		}

		if (PurchaseConstants.PRCHS_CANCEL_BY_USER == purchaseCancelSacParam.getPrchsCancelByType()) {
			// 사용자가 취소하는 경우 권한 체크.
			if (!StringUtils.equals(purchaseCancelSacParam.getUserKey(), prchsSacParam.getInsdUsermbrNo())) {
				throw new StorePlatformException("SAC_PUR_8102");
			}
		}

		/** deviceId 조회 및 셋팅. */
		prchsSacParam.setDeviceId(this.purchaseCancelRepository.getDeviceId(prchsSacParam.getInsdUsermbrNo(),
				prchsSacParam.getInsdDeviceId()));

		/** 상품 정보 셋팅. 선물인 경우 동일 Tenant 인 경우만 선물 된다. */
		PaymentInfoSacReq paymentInfoSacReq = new PaymentInfoSacReq();
		paymentInfoSacReq.setTenantId(prchsSacParam.getTenantId());
		List<String> prodIdList = new ArrayList<String>();
		for (PrchsDtlSacParam prchsDtlSacParam : purchaseCancelDetailSacParam.getPrchsDtlSacParamList()) {
			prodIdList.add(prchsDtlSacParam.getProdId());
		}
		paymentInfoSacReq.setProdIdList(prodIdList);
		paymentInfoSacReq.setLangCd(purchaseCancelSacParam.getLangCd());
		PaymentInfoSacRes paymentInfoSacRes = this.paymentInfoSCI.searchPaymentInfo(paymentInfoSacReq);
		if (paymentInfoSacRes == null || paymentInfoSacRes.getPaymentInfoList() == null
				|| paymentInfoSacRes.getPaymentInfoList().size() < 1) {
			throw new StorePlatformException("SAC_PUR_5101");
		}

		/** 구매 상품 별 체크. */
		boolean shoppingYn = false; // 쇼핑상품 구분.
		for (PrchsDtlSacParam prchsDtlSacParam : purchaseCancelDetailSacParam.getPrchsDtlSacParamList()) {
			for (PaymentInfo paymentInfo : paymentInfoSacRes.getPaymentInfoList()) {
				if (!StringUtils.equals(prchsDtlSacParam.getProdId(), paymentInfo.getProdId())) {
					continue;
				}

				prchsDtlSacParam.setAppId(paymentInfo.getAid());
				prchsDtlSacParam.setSpecialSaleCouponId(paymentInfo.getSpecialSaleCouponId());
			}

			if (StringUtils.startsWith(prchsDtlSacParam.getTenantProdGrpCd(),
					PurchaseConstants.TENANT_PRODUCT_GROUP_SHOPPING)) {
				// 쇼핑상품이면 true 셋팅.
				shoppingYn = true;
			}

			if (StringUtils.equals(PurchaseConstants.PRCHS_PROD_TYPE_AUTH, prchsDtlSacParam.getPrchsProdType())) {
				// 정액권 상품 처리.
				this.updateProdTypeFix(purchaseCancelSacParam, prchsDtlSacParam);
			}
		}

		/** 쇼핑 상품 처리. */
		if (shoppingYn) {
			this.executeCancelShoppingCoupon(purchaseCancelSacParam, purchaseCancelDetailSacParam);
		}

		/** 결제가 PayPlanet결제 인지 TStore 결제인지 구분하고 PayPlanet결제이면 authKey, mid 셋팅. */
		this.setPaymentShopInfo(purchaseCancelSacParam, purchaseCancelDetailSacParam);

		/** 결제 취소 처리. */
		// 결제 건이 있을 경우 결제 취소.
		if (purchaseCancelDetailSacParam.getPaymentSacParamList() != null
				&& purchaseCancelDetailSacParam.getPaymentSacParamList().size() > 0) {
			if (StringUtils.isBlank(purchaseCancelDetailSacParam.getPaymentSacParamList().get(0).getMid())) {
				// T Store 결제.
				purchaseCancelDetailSacParam.settStorePayCancelResultList(this.purchaseCancelRepository
						.cancelPaymentToTStore(purchaseCancelSacParam, purchaseCancelDetailSacParam));
			} else {
				// PayPlanet 결제.
				purchaseCancelDetailSacParam.setPayPlanetCancelEcRes(this.purchaseCancelRepository
						.cancelPaymentToPayPlanet(purchaseCancelSacParam, purchaseCancelDetailSacParam));
			}
		}

		/** 구매 DB 취소 처리. */
		this.purchaseCancelRepository.updatePurchaseCancel(purchaseCancelSacParam, purchaseCancelDetailSacParam);

		purchaseCancelDetailSacResult.setPrchsId(purchaseCancelDetailSacParam.getPrchsId());
		purchaseCancelDetailSacResult.setResultCd("SAC_PUR_0000");
		purchaseCancelDetailSacResult.setResultMsg(this.multiMessageSourceAccessor.getMessage("SAC_PUR_0000"));

		this.logger.debug("구매 취소 성공!");

		return purchaseCancelDetailSacResult;

	}

	@Override
	public PurchaseCancelDetailSacResult executePurchaseCancelForTCash(PurchaseCancelSacParam purchaseCancelSacParam,
			PurchaseCancelDetailSacParam purchaseCancelDetailSacParam) {

		PurchaseCancelDetailSacResult purchaseCancelDetailSacResult = new PurchaseCancelDetailSacResult();

		/** 구매 정보 조회. */
		this.purchaseCancelRepository.setPurchaseDetailInfo(purchaseCancelSacParam, purchaseCancelDetailSacParam);
		if (purchaseCancelDetailSacParam.getPaymentSacParamList() == null
				|| purchaseCancelDetailSacParam.getPaymentSacParamList().size() < 1) {
			// 구매 상세 정보가 없으면 구매 취소 불가.
			// throw new StorePlatformException("SAC_PUR_8100");
			// TCASH는 구매 상세 정보가 없어도 구매 취소 가능.
		}

		/** 구매 정보 체크. */
		PrchsSacParam prchsSacParam = purchaseCancelDetailSacParam.getPrchsSacParam();
		if (!StringUtils.equals(PurchaseConstants.PRCHS_STATUS_COMPT, prchsSacParam.getStatusCd())) {
			// 구매 완료 상태가 아니면 취소 불가.
			throw new StorePlatformException("SAC_PUR_8101");
		}

		// T CASH는 운영자용임.. 아래꺼 타면 안됨.
		if (PurchaseConstants.PRCHS_CANCEL_BY_USER == purchaseCancelSacParam.getPrchsCancelByType()) {
			// 사용자가 취소하는 경우 권한 체크.
			if (!StringUtils.equals(purchaseCancelSacParam.getUserKey(), prchsSacParam.getInsdUsermbrNo())) {
				throw new StorePlatformException("SAC_PUR_8102");
			}
		}

		/** deviceId 조회 및 셋팅. */
		prchsSacParam.setDeviceId(this.purchaseCancelRepository.getDeviceId(prchsSacParam.getInsdUsermbrNo(),
				prchsSacParam.getInsdDeviceId()));

		/** 결제가 PayPlanet결제 인지 TStore 결제인지 구분하고 PayPlanet결제이면 authKey, mid 셋팅. */
		this.setPaymentShopInfo(purchaseCancelSacParam, purchaseCancelDetailSacParam);

		/** 구매 상품 별 체크. T CASH는 구매 상품이 TCASH 이므로 상세가 없다. */
		/*
		 * boolean shoppingYn = false; // 쇼핑상품 구분. for (PrchsDtlSacParam prchsDtlSacParam :
		 * purchaseCancelDetailSacParam.getPrchsDtlSacParamList()) {
		 * 
		 * if (StringUtils.startsWith(prchsDtlSacParam.getTenantProdGrpCd(),
		 * PurchaseConstants.TENANT_PRODUCT_GROUP_SHOPPING)) { // 쇼핑상품이면 true 셋팅. shoppingYn = true; }
		 * 
		 * if (StringUtils.equals(PurchaseConstants.PRCHS_PROD_TYPE_AUTH, prchsDtlSacParam.getPrchsProdType())) { // 정액권
		 * 상품 처리. this.updateProdTypeFix(purchaseCancelSacParam, prchsDtlSacParam); }
		 * 
		 * }
		 */

		/** 쇼핑 상품 처리. */
		/*
		 * if (shoppingYn) { this.executeCancelShoppingCoupon(purchaseCancelSacParam, purchaseCancelDetailSacParam); }
		 */

		/** 충전 취소 처리. */
		this.purchaseCancelRepository.cancelTCash(purchaseCancelSacParam, purchaseCancelDetailSacParam);

		/** 결제 취소 처리. */
		// 결제 건이 있을 경우 결제 취소.
		if (purchaseCancelDetailSacParam.getPaymentSacParamList() != null
				&& purchaseCancelDetailSacParam.getPaymentSacParamList().size() > 0) {
			if (StringUtils.isBlank(purchaseCancelDetailSacParam.getPaymentSacParamList().get(0).getMid())) {
				// T Store 결제.
				purchaseCancelDetailSacParam.settStorePayCancelResultList(this.purchaseCancelRepository
						.cancelPaymentToTStore(purchaseCancelSacParam, purchaseCancelDetailSacParam));
			} else {
				// PayPlanet 결제.
				purchaseCancelDetailSacParam.setPayPlanetCancelEcRes(this.purchaseCancelRepository
						.cancelPaymentToPayPlanet(purchaseCancelSacParam, purchaseCancelDetailSacParam));
			}
		}

		/** 구매 DB 취소 처리. */
		this.purchaseCancelRepository.updatePurchaseCancel(purchaseCancelSacParam, purchaseCancelDetailSacParam);

		/**
		 * 전시 상품 구매건수 -1. try { this.purchaseCancelRepository.updatePurchaseCount(purchaseCancelSacParam,
		 * purchaseCancelDetailSacParam); } catch (Exception e) { this.logger.debug("구매 상품 개수 업데이트 실패! ========= {}",
		 * e); }
		 */

		/** RO 삭제 처리. */
		/*
		 * for (PrchsDtlSacParam prchsDtlSacParam : purchaseCancelDetailSacParam.getPrchsDtlSacParamList()) { if
		 * (!StringUtils.startsWith(prchsDtlSacParam.getTenantProdGrpCd(), PurchaseConstants.TENANT_PRODUCT_GROUP_APP))
		 * { // APP 상품이 아니면 통과. continue; } try { this.removeRO(purchaseCancelSacParam, purchaseCancelDetailSacParam,
		 * prchsDtlSacParam); } catch (Exception e) { this.logger.debug("RO 삭제 실패! ========= {}, {}",
		 * prchsDtlSacParam.getProdId(), e); } }
		 */

		purchaseCancelDetailSacResult.setPrchsId(purchaseCancelDetailSacParam.getPrchsId());
		purchaseCancelDetailSacResult.setResultCd("SAC_PUR_0000");
		purchaseCancelDetailSacResult.setResultMsg(this.multiMessageSourceAccessor.getMessage("SAC_PUR_0000"));

		this.logger.debug("구매 취소 성공!");

		return purchaseCancelDetailSacResult;

	}

	private void updateProdTypeFix(PurchaseCancelSacParam purchaseCancelSacParam, PrchsDtlSacParam prchsDtlSacParam) {

		/** 정액권으로 산 상품이 존재하는지 체크. */
		HistoryCountSacInReq historyCountSacInReq = new HistoryCountSacInReq();
		// 구매인지 선물인지 구분하여 조회.
		if (PurchaseConstants.PRCHS_CASE_GIFT_CD.equals(prchsDtlSacParam.getPrchsCaseCd())) {
			// 정액권 선물일 경우 취소 불가!! 최상훈차장님 결정!! 2014.02.13
			throw new StorePlatformException("SAC_PUR_8113");
		}

		// 정액제 상품으로 산 구매내역 조회.
		historyCountSacInReq.setTenantId(prchsDtlSacParam.getUseTenantId());
		historyCountSacInReq.setUserKey(prchsDtlSacParam.getUseInsdUsermbrNo());
		historyCountSacInReq.setStartDt(prchsDtlSacParam.getUseStartDt());
		historyCountSacInReq.setEndDt(prchsDtlSacParam.getUseExprDt());
		historyCountSacInReq.setPrchsCaseCd(prchsDtlSacParam.getPrchsCaseCd());
		historyCountSacInReq.setPrchsStatusCd(PurchaseConstants.PRCHS_STATUS_COMPT);
		historyCountSacInReq.setUseFixrateProdId(prchsDtlSacParam.getProdId());

		HistoryCountSacInRes historyCountSacInRes = this.historyInternalSCI.searchHistoryCount(historyCountSacInReq);
		if (historyCountSacInRes.getTotalCnt() > 0) {
			// 정액권 상품으로 이용한 상품이 존재!
			throw new StorePlatformException("SAC_PUR_8111");
		}

		// 정액권 자동구매 해지예약 호출.
		AutoPaymentCancelScReq autoPaymentCancelScReq = new AutoPaymentCancelScReq();
		autoPaymentCancelScReq.setTenantId(prchsDtlSacParam.getUseTenantId());
		autoPaymentCancelScReq.setUserKey(prchsDtlSacParam.getUseInsdUsermbrNo());
		autoPaymentCancelScReq.setDeviceKey(prchsDtlSacParam.getUseInsdDeviceId());
		autoPaymentCancelScReq.setPrchsId(prchsDtlSacParam.getPrchsId());
		autoPaymentCancelScReq.setAutoPaymentStatusCd(PurchaseConstants.AUTO_PRCHS_STATUS_CLOSE_RESERVATION);
		autoPaymentCancelScReq.setClosedReasonCd(PurchaseConstants.AUTO_PRCHS_CLOSE_PATH_PURCHASE_CANCEL);
		autoPaymentCancelScReq.setClosedReqPathCd(purchaseCancelSacParam.getCancelReqPathCd());

		AutoPaymentCancelScRes autoPaymentCancelScRes = this.autoPaymentCancelSacService
				.updateReservation(autoPaymentCancelScReq);

		if (!StringUtils.equals("Y", autoPaymentCancelScRes.getResultYn())) {
			throw new StorePlatformException("SAC_PUR_8112");
		}

	}

	/**
	 * <pre>
	 * 
	 * 쇼핑상품 취소 시 체크.
	 * 
	 * CASE 1 : 구매내역이 없을 경우. - 공통 
	 * CASE 2 : 구매상태가 구매완료가 아닌 경우. - 공통
	 * CASE 3 : 구 쇼핑 상품인 경우 (불가는 아니지만 티스토어 결제취소만 진행). - 공통
	 * CASE 4 : 결제 방식이 다날결제 이면서 결제월과 취소월이 틀릴 경우. - SHOPPING
	 * CASE 5 : 결제 방식이 SK 후불 이면서 한도상품 가입자의 경우. - SHOPPING
	 * CASE 6 : CMS 쿠폰사용조회 오류가 발생할 경우. - SHOPPING
	 * CASE 7 : CMS 쿠폰사용조회 후 사용된 쿠폰이 존재할 경우. - SHOPPING
	 * </pre>
	 * 
	 * @param purchaseCancelSacParam
	 *            purchaseCancelSacParam
	 * @param purchaseCancelDetailSacParam
	 *            purchaseCancelDetailSacParam
	 */
	private void executeCancelShoppingCoupon(PurchaseCancelSacParam purchaseCancelSacParam,
			PurchaseCancelDetailSacParam purchaseCancelDetailSacParam) {

		/** 결제 취소 가능 여부 확인 */
		List<PaymentSacParam> paymentSacParamList = purchaseCancelDetailSacParam.getPaymentSacParamList();
		for (PaymentSacParam paymentSacParam : paymentSacParamList) {
			if (StringUtils.equals(PurchaseConstants.PAYMENT_METHOD_DANAL, paymentSacParam.getPaymentMtdCd())
					&& !StringUtils.equals(DateUtil.getToday("yyyyMM"), paymentSacParam.getPaymentDt().substring(0, 6))) {
				// 다날 결제이면서 결제월과 취소월이 다른 경우 취소 불가.
				throw new StorePlatformException("SAC_PUR_8401");
			}

			if (StringUtils.equals(PurchaseConstants.PAYMENT_METHOD_SKT_CARRIER, paymentSacParam.getPaymentMtdCd())) {
				// SKT 후불 결제.
				UserEcRes userEcRes = this.purchaseUapsRespository
						.searchUapsMappingInfoByMdn(purchaseCancelDetailSacParam.getPrchsSacParam().getDeviceId());
				String[] serviceCdList = userEcRes.getServiceCD();
				if (serviceCdList != null) {
					for (String serviceCd : serviceCdList) {
						for (String uapsSvcLimitService : PurchaseConstants.UAPS_SVC_LIMIT_SERVICE) {
							if (StringUtils.equals(serviceCd, uapsSvcLimitService)
									&& !StringUtils.equals("Y", purchaseCancelSacParam.getSktLimitUserCancelYn())) {
								// 한도 가입자이면서 한도가입자 취소여부가 Y가 아니면 에러.
								throw new StorePlatformException("SAC_PUR_8123");
							}
						}
					}
				}
			}
		}

		/** 강제 취소가 아닐 경우 쿠폰 사용 유무를 조회해온다. */
		if (!StringUtils.equals("Y", purchaseCancelSacParam.getForceCancelYn())) {

			CouponUseStatusSacInReq couponUseStatusSacInReq = new CouponUseStatusSacInReq();
			// 아래 값이 실제 쓰이지 않음. 그냥 넣어줌;;
			couponUseStatusSacInReq.setTenantId(purchaseCancelSacParam.getTenantId());
			couponUseStatusSacInReq.setSystemId(purchaseCancelSacParam.getSystemId());
			couponUseStatusSacInReq.setUserKey(purchaseCancelSacParam.getUserKey());
			couponUseStatusSacInReq.setDeviceKey(purchaseCancelSacParam.getDeviceKey());

			// prchsId 단위로 처리.
			couponUseStatusSacInReq.setPrchsId(purchaseCancelDetailSacParam.getPrchsId());

			CouponUseStatusSacInRes couponUseStatusSacInRes = this.shoppingInternalSCI
					.getCouponUseStatus(couponUseStatusSacInReq);
			for (CouponUseStatusDetailSacInRes couponUseStatusDetailSacInRes : couponUseStatusSacInRes
					.getCpnUseStatusList()) {
				if (!StringUtils.equals("0", couponUseStatusDetailSacInRes.getCpnUseStatusCd())) {
					throw new StorePlatformException("SAC_PUR_8121");
				}
			}
		}

		/** 쇼핑 쿠폰 취소 처리 */
		CouponPublishCancelEcReq couponPublishCancelEcReq = new CouponPublishCancelEcReq();
		couponPublishCancelEcReq.setPrchsId(purchaseCancelDetailSacParam.getPrchsId());
		couponPublishCancelEcReq.setForceFlag(purchaseCancelSacParam.getForceCancelYn());
		try {
			this.shoppingSCI.cancelCouponPublish(couponPublishCancelEcReq);
		} catch (Exception e) {
			throw new StorePlatformException("SAC_PUR_8122", e);
		}

	}

	/**
	 * <pre>
	 * 
	 * RO 삭제 - 코드 DP0002의DP000201면 APP.
	 * 구매 상품이 APP이고 appid가 null이 아닌경우
	 * 
	 * </pre>
	 * 
	 * @param purchaseCommonParam
	 *            purchaseCommonParam
	 * @param purchaseCancelParamDetail
	 *            purchaseCancelParamDetail
	 */
	private void removeRO(PurchaseCancelSacParam purchaseCancelSacParam,
			PurchaseCancelDetailSacParam purchaseCancelDetailSacParam, PrchsDtlSacParam prchsDtlSacParam) {

		/** 사용자 deviceId 조회. */
		String deviceId = this.purchaseCancelRepository.getDeviceId(prchsDtlSacParam.getUseInsdUsermbrNo(),
				prchsDtlSacParam.getUseInsdDeviceId());

		String resultMsg = "";
		/** AOM PUSH */
		try {
			resultMsg = this.purchaseCancelRepository.aomPush(deviceId, prchsDtlSacParam.getAppId());

			this.logger.debug("removeRO.AomPush result ===== {}", resultMsg);
		} catch (Exception e) {
			this.logger.debug("aom push fail! ========= {}", e.toString());
		}

		/** ARM License Remove */
		try {
			resultMsg = this.purchaseCancelRepository.armRemoveLicense(deviceId, prchsDtlSacParam.getAppId());

			this.logger.debug("removeRO.ArmPush result ===== {}", resultMsg);
		} catch (Exception e) {
			this.logger.debug("arm license remove fail! ========= {}", e.toString());
		}

	}

	/**
	 * 
	 * <pre>
	 * 결제가 PayPlanet결제 인지 TStore 결제인지 구분하고 PayPlanet결제이면 authKey, mid 셋팅.
	 * </pre>
	 * 
	 * @param purchaseCancelSacParam
	 *            purchaseCancelSacParam
	 * @param purchaseCancelDetailSacParam
	 *            purchaseCancelDetailSacParam
	 */
	private void setPaymentShopInfo(PurchaseCancelSacParam purchaseCancelSacParam,
			PurchaseCancelDetailSacParam purchaseCancelDetailSacParam) {

		if (purchaseCancelDetailSacParam.getPaymentSacParamList() == null
				|| purchaseCancelDetailSacParam.getPaymentSacParamList().size() < 1) {
			return;
		}

		PayPlanetShop payPlanetShop = this.payPlanetShopService.getPayPlanetShopInfo(purchaseCancelDetailSacParam
				.getPrchsSacParam().getTenantId());

		for (PaymentSacParam paymentSacParam : purchaseCancelDetailSacParam.getPaymentSacParamList()) {
			// PayPlanet 결제이면 authKey, mid 셋팅.
			if (StringUtils.equals(payPlanetShop.getMid(), paymentSacParam.getMoid())) {
				paymentSacParam.setAuthKey(payPlanetShop.getAuthKey());
				paymentSacParam.setMid(payPlanetShop.getMid());
			}
		}

		return;

	}

}
