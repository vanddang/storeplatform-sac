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
import com.skplanet.storeplatform.sac.api.util.StringUtil;
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
	private PurchaseCancelRepository purchaseCancelRepository;

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

			} catch (Exception e) {

				purchaseCanDetailSacResult = new PurchaseCancelDetailSacResult();
				purchaseCanDetailSacResult.setPrchsId(purchaseCancelDetailSacParam.getPrchsId());
				purchaseCanDetailSacResult.setResultCd("1111");
				purchaseCanDetailSacResult.setResultMsg("실패!");

			}
			// TODO : 성공 코드값 및 메시지 정의 필요
			if ("0000".equals(StringUtil.setTrim(purchaseCanDetailSacResult.getResultCd()))) {
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

		for (PrchsDtl prchsDtl : purchaseCancelDetailSacParam.getPrchsDtl()) {

			if (!StringUtils.equals(PurchaseConstants.PRCHS_STATUS_COMPT, prchsDtl.getStatusCd())) {
				// 구매 완료가 아니면 구매 취소 처리 불가!
				// TODO : EXCEPTION
				throw new StorePlatformException("구매 취소 처리 불가!");
			}

			if (purchaseCancelDetailSacParam.getPrchsCancelByType() == PurchaseConstants.PRCHS_CANCEL_BY_USER) {
				// 사용자가 취소하는 경우.
				if (StringUtils.equals(PurchaseConstants.PRCHS_CASE_GIFT_CD, prchsDtl.getPrchsCaseCd())) {
					// 선물이면
					if (!StringUtils.equals(purchaseCommonSacParam.getUserKey(), prchsDtl.getSendInsdUsermbrNo())) {
						// TODO : EXCEPTION
						throw new StorePlatformException("구매 취소 처리 불가! : 다른 사람꺼 취소 못 함!");
					}
				} else {
					// 구매이면
					if (!StringUtils.equals(purchaseCommonSacParam.getUserKey(), prchsDtl.getUseInsdUsermbrNo())) {
						// TODO : EXCEPTION
						throw new StorePlatformException("구매 취소 처리 불가! : 다른 사람꺼 취소 못 함!");
					}
				}
			}

			// 정액권 상품 처리.
			if (StringUtils.equals(PurchaseConstants.PRCHS_PROD_TYPE_FIX, prchsDtl.getPrchsProdType())) {
				// 정액제 상품으로 산 구매내역 조회.
				HistoryCountSacInReq historyCountSacInReq = new HistoryCountSacInReq();
				// 구매인지 선물인지 구분하여 조회.
				if (PurchaseConstants.PRCHS_CASE_GIFT_CD.equals(prchsDtl.getPrchsCaseCd())) {
					// 정액권 선물일 경우 취소 불가!! 최상훈차장님 결정!! 2014.02.13
					// TODO : EXCEPTION
					throw new StorePlatformException("구매 취소 실패 : 정액권 상품 선물한 경우 취소 불가!");
				}

				historyCountSacInReq.setTenantId(prchsDtl.getUseTenantId());
				historyCountSacInReq.setUserKey(prchsDtl.getUseInsdUsermbrNo());
				historyCountSacInReq.setStartDt(prchsDtl.getUseStartDt());
				historyCountSacInReq.setEndDt(prchsDtl.getUseExprDt());
				historyCountSacInReq.setPrchsCaseCd(prchsDtl.getPrchsCaseCd());
				historyCountSacInReq.setPrchsStatusCd(PurchaseConstants.PRCHS_STATUS_COMPT);
				historyCountSacInReq.setUseFixrateProdId(prchsDtl.getProdId());

				HistoryCountSacInRes historyCountSacInRes = this.historyInternalSCI
						.searchHistoryCount(historyCountSacInReq);

				if (historyCountSacInRes.getTotalCnt() > 0) {
					// 정액권 상품으로 이용한 상품이 존재!
					// TODO : EXCEPTION
					throw new StorePlatformException("구매 취소 처리 불가! : 정액권 상품으로 이용한 상품이 존재");
				}

				// TODO : 정액권 자동구매 해지예약 호출.
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
					// TODO : EXCEPTION
					throw new StorePlatformException("정액권 자동구매 해지예약 해지 실패!");
				}

			}

			// 쇼핑쿠폰 상품일 경우 쇼핑쿠폰 상품 취소.
			// 쇼핑쿠폰 사용유무 조회.
			CouponUseStatusSacInReq couponUseStatusSacInReq = new CouponUseStatusSacInReq();
			couponUseStatusSacInReq.setTenantId(prchsDtl.getUseTenantId());
			// TODO : SystemId의 경우 확인 필요! 사용자의 systemId를 알 수 없어서 구매자 또는 운영자의 systemId를 넣는다.
			couponUseStatusSacInReq.setSystemId(purchaseCommonSacParam.getSystemId());
			couponUseStatusSacInReq.setUserKey(prchsDtl.getUseInsdUsermbrNo());
			couponUseStatusSacInReq.setDeviceKey(prchsDtl.getUseInsdDeviceId());
			couponUseStatusSacInReq.setPrchsId(prchsDtl.getPrchsId());
			couponUseStatusSacInReq.setCpnPublishCd(prchsDtl.getCpnPublishCd());

			CouponUseStatusSacInRes couponUseStatusSacInRes = this.shoppingInternalSCI
					.getCouponUseStatus(couponUseStatusSacInReq);

			List<CouponUseStatusDetailSacInRes> couponUseStatusList = couponUseStatusSacInRes.getCpnUseStatusList();
			if (couponUseStatusList == null || couponUseStatusList.size() != 1
					|| StringUtils.equals("0", couponUseStatusList.get(0).getCpnUseStatusCd())) {
				// 쇼핑쿠폰이 미상용이 아닐 경우 취소 불가!
				// TODO : EXCEPTION
				throw new StorePlatformException("구매 취소 처리 불가! : 쇼핑쿠폰이 미사용이 아닐 경우 취소 불가!");
			}

			// 쇼핑쿠폰 취소 요청.
			CouponPublishCancelEcReq couponPublishCancelEcReq = new CouponPublishCancelEcReq();
			couponPublishCancelEcReq.setPrchsId(prchsDtl.getPrchsId());
			couponPublishCancelEcReq.setForceFlag("N");
			try {
				this.shoppingSCI.cancelCouponPublish(couponPublishCancelEcReq);
			} catch (Exception e) {
				// TODO : EXCEPTION
				throw new StorePlatformException("쇼핑쿠폰 취소 실패!", e);
			}

		}

		// 결제 취소.

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

		// RO 삭제!
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
		 * 구매 상태가 정상인지
		 * 
		 * 이용내역? 이용이력? 이런게 있는지? 확인?
		 * 
		 * 선물 한 건지 받은건지 ?
		 * 
		 * 이미 이용중인지 확인? 이미 만료 된 건이 있는지 확인? use_expr_dt
		 * 
		 * 오퍼링 상품은 어케 처리?
		 * 
		 * 정액권 구매건인지
		 * 
		 * 정액권 구매건일 경우 상품 산게 있는지 체크
		 * 
		 * 정액권으로 산 상품이랑 일반 결제랑 함께 있을 수 있는지?
		 * 
		 * T Store Cash 취소 일 경우 취소할 캐쉬량이 남이 있는지 확인 필요(예약)
		 * 
		 * 쇼핑쿠폰 인지 확인 - 쇼셜쇼핑 TSTORE 쇼핑?
		 * 
		 * 쿠폰처리 확인?
		 * 
		 * 부분유료화 상품 취소 가능?
		 * 
		 * 
		 * 
		 * ro 삭제해야하는 건 있는지 -> RO삭제 실패해도 구매 취소 성공?
		 * 
		 * 이용내역 취소는 머지?
		 */

		purchaseCancelDetailSacResult.setPrchsId(purchaseCancelDetailSacParam.getPrchsId());
		purchaseCancelDetailSacResult.setResultCd("0000");
		purchaseCancelDetailSacResult.setResultMsg("성공");

		this.logger.debug("구매 취소 성공!");

		return purchaseCancelDetailSacResult; // purchaseCancelResultDetail;

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
