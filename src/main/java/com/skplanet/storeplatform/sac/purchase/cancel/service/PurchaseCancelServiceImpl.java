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
import com.skplanet.storeplatform.framework.core.exception.StorePlatformException;
import com.skplanet.storeplatform.framework.core.util.StringUtils;
import com.skplanet.storeplatform.purchase.client.cancel.sci.PurchaseCancelSCI;
import com.skplanet.storeplatform.purchase.client.cancel.vo.PurchaseCancelPaymentDetailScReq;
import com.skplanet.storeplatform.purchase.client.cancel.vo.PurchaseCancelScReq;
import com.skplanet.storeplatform.purchase.client.cancel.vo.PurchaseScReq;
import com.skplanet.storeplatform.purchase.client.cancel.vo.PurchaseScRes;
import com.skplanet.storeplatform.purchase.client.common.vo.Payment;
import com.skplanet.storeplatform.purchase.client.common.vo.PrchsDtl;
import com.skplanet.storeplatform.purchase.client.history.vo.AutoPaymentCancelScReq;
import com.skplanet.storeplatform.purchase.client.history.vo.AutoPaymentCancelScRes;
import com.skplanet.storeplatform.purchase.constant.PurchaseConstants;
import com.skplanet.storeplatform.sac.client.internal.purchase.history.sci.HistoryInternalSCI;
import com.skplanet.storeplatform.sac.client.internal.purchase.history.vo.HistoryCountSacInReq;
import com.skplanet.storeplatform.sac.client.internal.purchase.history.vo.HistoryCountSacInRes;
import com.skplanet.storeplatform.sac.client.internal.purchase.shopping.sci.ShoppingInternalSCI;
import com.skplanet.storeplatform.sac.client.internal.purchase.shopping.vo.CouponUseStatusDetailSacInRes;
import com.skplanet.storeplatform.sac.client.internal.purchase.shopping.vo.CouponUseStatusSacInReq;
import com.skplanet.storeplatform.sac.client.internal.purchase.shopping.vo.CouponUseStatusSacInRes;
import com.skplanet.storeplatform.sac.purchase.cancel.repository.PurchaseCancelRepository;
import com.skplanet.storeplatform.sac.purchase.cancel.vo.PurchaseCancelDetailSacParam;
import com.skplanet.storeplatform.sac.purchase.cancel.vo.PurchaseCancelDetailSacResult;
import com.skplanet.storeplatform.sac.purchase.cancel.vo.PurchaseCancelSacParam;
import com.skplanet.storeplatform.sac.purchase.cancel.vo.PurchaseCancelSacResult;
import com.skplanet.storeplatform.sac.purchase.common.vo.PurchaseCommonSacParam;
import com.skplanet.storeplatform.sac.purchase.history.service.AutoPaymentCancelSacService;

/**
 * 구매 취소 Service Implements.
 * 
 * Updated on : 2014. 1. 16. Updated by : nTels_cswoo81, nTels.
 */
@Service
public class PurchaseCancelServiceImpl implements PurchaseCancelService {

	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private PurchaseCancelSCI purchaseCancelSCI;

	@Autowired
	private HistoryInternalSCI historyInternalSCI;

	@Autowired
	private ShoppingInternalSCI shoppingInternalSCI;

	@Autowired
	private AutoPaymentCancelSacService autoPaymentCancelSacService;

	@Autowired
	private ShoppingSCI shoppingSCI;

	@Autowired
	private PurchaseCancelRepository purchaseCancelRepository;

	@Override
	public PurchaseCancelSacResult cancelPurchaseList(PurchaseCancelSacParam purchaseCancelSacParam) {

		PurchaseCancelSacResult purchaseCancelSacResult = new PurchaseCancelSacResult();

		List<PurchaseCancelDetailSacResult> prchsCancelList = new ArrayList<PurchaseCancelDetailSacResult>();
		int totCnt = 0;
		int successCnt = 0;
		int failCnt = 0;

		for (PurchaseCancelDetailSacParam purchaseCancelDetailSacParam : purchaseCancelSacParam.getPrchsCancelList()) {

			totCnt++;
			PurchaseCancelDetailSacResult purchaseCanDetailSacResult = new PurchaseCancelDetailSacResult();
			try {

				purchaseCanDetailSacResult = this.updatePurchaseCancel(purchaseCancelSacParam,
						purchaseCancelDetailSacParam);

			} catch (StorePlatformException e) {

				purchaseCanDetailSacResult = new PurchaseCancelDetailSacResult();
				purchaseCanDetailSacResult.setPrchsId(purchaseCancelDetailSacParam.getPrchsId());
				purchaseCanDetailSacResult.setResultCd(e.getErrorInfo().getCode());
				purchaseCanDetailSacResult.setResultMsg(e.getErrorInfo().getMessage());

			} catch (Exception e) {

				purchaseCanDetailSacResult = new PurchaseCancelDetailSacResult();
				purchaseCanDetailSacResult.setPrchsId(purchaseCancelDetailSacParam.getPrchsId());
				purchaseCanDetailSacResult.setResultCd("9999");
				purchaseCanDetailSacResult.setResultMsg(e.getMessage());

			}
			// TODO : 성공 코드값 및 메시지 정의 필요
			if (StringUtils.equals("0000", purchaseCanDetailSacResult.getResultCd())) {
				successCnt++;
			} else {
				failCnt++;
			}

			prchsCancelList.add(purchaseCanDetailSacResult);

		}

		purchaseCancelSacResult.setTotCnt(totCnt);
		purchaseCancelSacResult.setSuccessCnt(successCnt);
		purchaseCancelSacResult.setFailCnt(failCnt);
		purchaseCancelSacResult.setPrchsCancelList(prchsCancelList);

		return purchaseCancelSacResult;

	}

	@Override
	public PurchaseCancelDetailSacResult updatePurchaseCancel(PurchaseCommonSacParam purchaseCommonSacParam,
			PurchaseCancelDetailSacParam purchaseCancelDetailSacParam) {

		PurchaseCancelDetailSacResult purchaseCancelDetailSacResult = new PurchaseCancelDetailSacResult();

		// 구매 정보 조회.
		PurchaseScReq purchaseScReq = new PurchaseScReq();

		purchaseScReq.setTenantId(purchaseCommonSacParam.getTenantId());
		purchaseScReq.setSystemId(purchaseCommonSacParam.getSystemId());
		purchaseScReq.setPrchsId(purchaseCancelDetailSacParam.getPrchsId());

		PurchaseScRes purchaseScRes = this.purchaseCancelSCI.getPurchase(purchaseScReq);

		purchaseCancelDetailSacParam.setPrchs(purchaseScRes.getPrchs());
		purchaseCancelDetailSacParam.setPrchsDtl(purchaseScRes.getPrchsDtlList());
		purchaseCancelDetailSacParam.setPaymentList(purchaseScRes.getPaymentList());

		// 각 상품 별 체크.
		for (PrchsDtl prchsDtl : purchaseCancelDetailSacParam.getPrchsDtl()) {

			if (!StringUtils.equals(PurchaseConstants.PRCHS_STATUS_COMPT, prchsDtl.getStatusCd())) {
				throw new StorePlatformException("CSWOO8101_4001");
			}

			if (purchaseCancelDetailSacParam.getPrchsCancelByType() == PurchaseConstants.PRCHS_CANCEL_BY_USER) {
				// 사용자가 취소하는 경우.
				if (StringUtils.equals(PurchaseConstants.PRCHS_CASE_GIFT_CD, prchsDtl.getPrchsCaseCd())) {
					// 선물이면 보낸사람 정보 확인.
					if (!StringUtils.equals(purchaseCommonSacParam.getUserKey(), prchsDtl.getSendInsdUsermbrNo())) {
						throw new StorePlatformException("CSWOO8101_4002");
					}
				} else {
					// 구매이면 이용자 정보 확인.
					if (!StringUtils.equals(purchaseCommonSacParam.getUserKey(), prchsDtl.getUseInsdUsermbrNo())) {
						throw new StorePlatformException("CSWOO8101_4002");
					}
				}
			}

			if (StringUtils.equals(PurchaseConstants.PRCHS_PROD_TYPE_FIX, prchsDtl.getPrchsProdType())) {
				// 정액권 상품 처리.
				this.updateProdTypeFix(purchaseCancelDetailSacParam, prchsDtl);

			}

		}

		// 쇼핑쿠폰 상품일 경우 쇼핑쿠폰 상품 취소.
		// 쇼핑쿠폰 사용유무 조회.
		// TODO : 전시쪽 정보 가져와서 쇼핑쿠폰 일 경우 태운다. prchsDtl.getProdId();
		this.updateCancelShoppingCoupon(purchaseCommonSacParam, purchaseCancelDetailSacParam);

		// 결제 취소.
		// TODO : PayPlanet 연동.
		// 구매 취소.
		PurchaseCancelScReq purchaseCancelScReq = new PurchaseCancelScReq();

		// 인입 된 사람의 정보를 넣어준다.
		purchaseCancelScReq.setTenantId(purchaseCommonSacParam.getTenantId());
		purchaseCancelScReq.setSystemId(purchaseCommonSacParam.getSystemId());
		// 구매 취소 정보를 넣어준다.
		purchaseCancelScReq.setPrchsId(purchaseCancelDetailSacParam.getPrchsId());
		purchaseCancelScReq.setPrchsStatusCd(PurchaseConstants.PRCHS_STATUS_CANCEL);
		purchaseCancelScReq.setCancelReqPathCd(purchaseCancelDetailSacParam.getCancelReqPathCd());

		// 결제 취소 정보를 넣어준다.
		List<PurchaseCancelPaymentDetailScReq> purchaseCancelPaymentList = new ArrayList<PurchaseCancelPaymentDetailScReq>();
		// TODO : PayPlanet 끝나면 PayPlanet정보로 넣어준다!!
		for (Payment payment : purchaseCancelDetailSacParam.getPaymentList()) {
			PurchaseCancelPaymentDetailScReq purchaseCancelPaymentDetailScReq = new PurchaseCancelPaymentDetailScReq();

			purchaseCancelPaymentDetailScReq.setTenantId(purchaseCancelDetailSacParam.getPrchs().getTenantId());
			purchaseCancelPaymentDetailScReq.setSystemId(purchaseCommonSacParam.getSystemId());
			purchaseCancelPaymentDetailScReq.setPrchsId(purchaseCancelDetailSacParam.getPrchsId());
			purchaseCancelPaymentDetailScReq.setPaymentDtlId(payment.getPaymentDtlId());
			// TODO : PayPlanet 끝나면 PayPlanet정보로 넣어준다!!
			purchaseCancelPaymentDetailScReq.setPaymentStatusCd(PurchaseConstants.PRCHS_STATUS_CANCEL);

			purchaseCancelPaymentList.add(purchaseCancelPaymentDetailScReq);
		}
		purchaseCancelScReq.setPurchaseCancelPaymentList(purchaseCancelPaymentList);

		this.purchaseCancelSCI.updatePurchaseCancel(purchaseCancelScReq);

		// RO 삭제! 삭제 실패해도 취소처리는 성공!
		try {
			this.removeRO(purchaseCommonSacParam, purchaseCancelDetailSacParam);
		} catch (Exception e) {
			this.logger.info("RO 삭제 실패!");
		}

		// TODO : TEST MDN 확인. 요건 어케 할지 생각 좀 해봐야 함..

		// TODO : payment 테이블 가져와야 함? 결제 취소 후 머 벨리데이션 해야하나? payPlanet 연동 규격서 확인 후 작업..
		// TODO : payPlanet에 취소 요청

		// TODO : 쇼핑 쿠폰이면 처리 .. 쇼핑 쿠폰 처리 위치가 변경 될 수 있음.. 구 쿠폰의경우 PP 후에 처리 되야 할꺼고..
		// TODO : 신 쿠폰의 경우 PP에서 한도체크나 다날체크를 하면 쇼핑쿠폰처리가 뒤에 오고..
		// TODO : PP에서 한도체크나 다날체크 안하고 SAC에서 해야하면 쇼핑쿠폰처리가 앞에 오고..? 쇼핑쿠폰은 실물쿠폰인데 결제취소만 되고 쿠폰은 취소 안되어도 컴플레인이 없나?
		/*
		 * 티스토어 쇼핑 구매취소불가. CASE 4 : 결제 방식이 다날결제 이면서 결제월과 취소월이 틀릴 경우 티스토어 쇼핑 구매취소불가. CASE 5 : 결제 방식이 SK 후불 이면서 한도상품 가입자의
		 * 경우. 티스토어 쇼핑 구매취소불가. CASE 6 : CMS 쿠폰사용조회 오류가 발생할 경우 티스토어 쇼핑 구매취소불가. CASE 7 : CMS 쿠폰사용조회 후 사용된 쿠폰이 존재할 경우.
		 */

		// TR 시작
		// DB 업데이트
		/*
		 * purchaseCancelParamDetail = this.purchaseCancelRepository.cancelPurchase(purchaseCommonParam,
		 * purchaseCancelParamDetail);
		 * 
		 * if (purchaseCancelParamDetail.getPrchsCancelCnt() != 1 || purchaseCancelParamDetail.getPrchsDtlCancelCnt() <
		 * 1) { throw new StorePlatformException("구매 취소 처리 실패!"); }
		 */

		// TR 끝
		// RO 삭제 - 코드 DP0002의DP000201면 APP
		// // 구매 상품이 APP이고 appid가 null이 아닌경우

		// 상품 구매 건수 차감 호출.

		/*
		 * purchaseCancelResultDetail.setPrchsId(purchaseCancelParamDetail.getPrchsId());
		 * purchaseCancelResultDetail.setPrchsCancelResultCd("성공");
		 * purchaseCancelResultDetail.setPrchsCancelResultMsg("성공");
		 * 
		 * // ro 삭제 진행 삭제 실패해도 구매취소는 성공! try { this.removeRO(purchaseCommonParam, purchaseCancelParamDetail); } catch
		 * (Exception e) { this.logger.debug("ro 삭제 실패! : {} ", e.getMessage()); }
		 */

		/*
		 * 
		 * 선물 한 건지 받은건지 ?
		 * 
		 * 이미 이용중인지 확인? 이미 만료 된 건이 있는지 확인? use_expr_dt
		 * 
		 * 오퍼링 상품은 어케 처리?
		 * 
		 * T Store Cash 취소 일 경우 취소할 캐쉬량이 남이 있는지 확인 필요(예약)
		 * 
		 * 쇼핑쿠폰 인지 확인 - 쇼셜쇼핑 TSTORE 쇼핑?
		 * 
		 * 
		 * 부분유료화 상품 취소 가능?
		 * 
		 * 
		 * 
		 * ro 삭제해야하는 건 있는지 -> RO삭제 실패해도 구매 취소 성공?
		 */

		purchaseCancelDetailSacResult.setPrchsId(purchaseCancelDetailSacParam.getPrchsId());
		purchaseCancelDetailSacResult.setResultCd("0000");
		purchaseCancelDetailSacResult.setResultMsg("성공");

		this.logger.debug("구매 취소 성공!");

		return purchaseCancelDetailSacResult;

	}

	private void updateProdTypeFix(PurchaseCancelDetailSacParam purchaseCancelDetailSacParam, PrchsDtl prchsDtl) {
		// 정액제 상품으로 산 구매내역 조회.
		HistoryCountSacInReq historyCountSacInReq = new HistoryCountSacInReq();
		// 구매인지 선물인지 구분하여 조회.
		if (PurchaseConstants.PRCHS_CASE_GIFT_CD.equals(prchsDtl.getPrchsCaseCd())) {
			// 정액권 선물일 경우 취소 불가!! 최상훈차장님 결정!! 2014.02.13
			throw new StorePlatformException("CSWOO8101_4003");
		}

		historyCountSacInReq.setTenantId(prchsDtl.getUseTenantId());
		historyCountSacInReq.setUserKey(prchsDtl.getUseInsdUsermbrNo());
		historyCountSacInReq.setStartDt(prchsDtl.getUseStartDt());
		historyCountSacInReq.setEndDt(prchsDtl.getUseExprDt());
		historyCountSacInReq.setPrchsCaseCd(prchsDtl.getPrchsCaseCd());
		historyCountSacInReq.setPrchsStatusCd(PurchaseConstants.PRCHS_STATUS_COMPT);
		historyCountSacInReq.setUseFixrateProdId(prchsDtl.getProdId());

		HistoryCountSacInRes historyCountSacInRes = this.historyInternalSCI.searchHistoryCount(historyCountSacInReq);

		if (historyCountSacInRes.getTotalCnt() > 0) {
			// 정액권 상품으로 이용한 상품이 존재!
			throw new StorePlatformException("CSWOO8101_4004");
		}

		// 정액권 자동구매 해지예약 호출.
		AutoPaymentCancelScReq autoPaymentCancelScReq = new AutoPaymentCancelScReq();
		autoPaymentCancelScReq.setTenantId(prchsDtl.getTenantId());
		autoPaymentCancelScReq.setUserKey(prchsDtl.getUseInsdUsermbrNo());
		autoPaymentCancelScReq.setDeviceKey(prchsDtl.getUseInsdDeviceId());
		autoPaymentCancelScReq.setPrchsId(prchsDtl.getPrchsId());
		autoPaymentCancelScReq.setClosedCd(PurchaseConstants.AUTO_PRCHS_CLOSE_RESERVE);
		// TODO : 추후 코드 정의 되면 받아야 함. 일단 고객요청으로 셋팅.
		autoPaymentCancelScReq.setClosedReasonCd("OR004601");
		autoPaymentCancelScReq.setClosedReqPathCd(purchaseCancelDetailSacParam.getCancelReqPathCd());

		AutoPaymentCancelScRes autoPaymentCancelScRes = this.autoPaymentCancelSacService
				.updateReservation(autoPaymentCancelScReq);

		if (!StringUtils.equals("Y", autoPaymentCancelScRes.getResultYn())) {
			throw new StorePlatformException("CSWOO8101_4005");
		}

	}

	private void updateCancelShoppingCoupon(PurchaseCommonSacParam purchaseCommonSacParam,
			PurchaseCancelDetailSacParam purchaseCancelDetailSacParam) {

		CouponUseStatusSacInReq couponUseStatusSacInReq = new CouponUseStatusSacInReq();

		// TODO : SystemId의 경우 확인 필요! 사용자의 tenantId, systemId를 알 수 없어서 구매자 또는 운영자의 systemId를 넣는다.
		couponUseStatusSacInReq.setTenantId(purchaseCommonSacParam.getTenantId());
		couponUseStatusSacInReq.setSystemId(purchaseCommonSacParam.getSystemId());
		couponUseStatusSacInReq.setUserKey(purchaseCommonSacParam.getUserKey());
		couponUseStatusSacInReq.setDeviceKey(purchaseCommonSacParam.getDeviceKey());

		// prchsId 단위로 처리.
		couponUseStatusSacInReq.setPrchsId(purchaseCancelDetailSacParam.getPrchsId());

		CouponUseStatusSacInRes couponUseStatusSacInRes;
		try {
			couponUseStatusSacInRes = this.shoppingInternalSCI.getCouponUseStatus(couponUseStatusSacInReq);
			for (CouponUseStatusDetailSacInRes couponUseStatusDetailSacInRes : couponUseStatusSacInRes
					.getCpnUseStatusList()) {
				if (!StringUtils.equals("0", couponUseStatusDetailSacInRes.getCpnUseStatusCd())) {
					throw new StorePlatformException("CSWOO8101_4006");
				}
			}
		} catch (Exception e1) {
			throw new StorePlatformException("CSWOO8101_4007", e1);
		}

		// 쇼핑쿠폰 취소 요청.
		CouponPublishCancelEcReq couponPublishCancelEcReq = new CouponPublishCancelEcReq();
		couponPublishCancelEcReq.setPrchsId(purchaseCancelDetailSacParam.getPrchsId());
		couponPublishCancelEcReq.setForceFlag("N");
		try {
			this.shoppingSCI.cancelCouponPublish(couponPublishCancelEcReq);
		} catch (Exception e) {
			throw new StorePlatformException("CSWOO8101_4008", e);
		}

	}

	/**
	 * 
	 * <pre>
	 * method 설명.
	 * </pre>
	 * 
	 * @param purchaseCommonParam
	 *            purchaseCommonParam
	 * @param purchaseCancelParamDetail
	 *            purchaseCancelParamDetail
	 */
	private void removeRO(PurchaseCommonSacParam purchaseCommonParam,
			PurchaseCancelDetailSacParam purchaseCancelDetailSacParam) {

		// aom message 발송.

		// arm 라이센스 취소 호출.
		// this.purchaseCancelRepository.removeLicense(purchaseCommonParam, purchaseCancelParamDetail);

	}

}
