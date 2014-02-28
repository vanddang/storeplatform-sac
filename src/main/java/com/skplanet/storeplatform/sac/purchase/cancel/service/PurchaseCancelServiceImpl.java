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
import org.springframework.context.support.MessageSourceAccessor;
import org.springframework.stereotype.Service;

import com.skplanet.storeplatform.external.client.arm.sci.ArmSCI;
import com.skplanet.storeplatform.external.client.arm.vo.RemoveLicenseEcReq;
import com.skplanet.storeplatform.external.client.arm.vo.RemoveLicenseEcRes;
import com.skplanet.storeplatform.external.client.shopping.sci.ShoppingSCI;
import com.skplanet.storeplatform.external.client.shopping.vo.CouponPublishCancelEcReq;
import com.skplanet.storeplatform.framework.core.exception.StorePlatformException;
import com.skplanet.storeplatform.framework.core.util.StringUtils;
import com.skplanet.storeplatform.purchase.client.cancel.sci.PurchaseCancelSCI;
import com.skplanet.storeplatform.purchase.client.cancel.vo.PurchaseCancelScReq;
import com.skplanet.storeplatform.purchase.client.cancel.vo.PurchaseScReq;
import com.skplanet.storeplatform.purchase.client.cancel.vo.PurchaseScRes;
import com.skplanet.storeplatform.purchase.client.common.vo.PrchsDtl;
import com.skplanet.storeplatform.purchase.client.history.vo.AutoPaymentCancelScReq;
import com.skplanet.storeplatform.purchase.client.history.vo.AutoPaymentCancelScRes;
import com.skplanet.storeplatform.purchase.constant.PurchaseConstants;
import com.skplanet.storeplatform.sac.client.internal.display.localsci.sci.PaymentInfoSCI;
import com.skplanet.storeplatform.sac.client.internal.display.localsci.sci.UpdatePurchaseCountSCI;
import com.skplanet.storeplatform.sac.client.internal.display.localsci.vo.PaymentInfoSacReq;
import com.skplanet.storeplatform.sac.client.internal.display.localsci.vo.PaymentInfoSacRes;
import com.skplanet.storeplatform.sac.client.internal.display.localsci.vo.UpdatePurchaseCountSacReq;
import com.skplanet.storeplatform.sac.client.internal.member.user.sci.DeviceSCI;
import com.skplanet.storeplatform.sac.client.internal.member.user.vo.SearchDeviceIdSacReq;
import com.skplanet.storeplatform.sac.client.internal.member.user.vo.SearchDeviceIdSacRes;
import com.skplanet.storeplatform.sac.client.internal.purchase.history.sci.HistoryInternalSCI;
import com.skplanet.storeplatform.sac.client.internal.purchase.history.vo.HistoryCountSacInReq;
import com.skplanet.storeplatform.sac.client.internal.purchase.history.vo.HistoryCountSacInRes;
import com.skplanet.storeplatform.sac.client.internal.purchase.shopping.sci.ShoppingInternalSCI;
import com.skplanet.storeplatform.sac.client.internal.purchase.shopping.vo.CouponUseStatusDetailSacInRes;
import com.skplanet.storeplatform.sac.client.internal.purchase.shopping.vo.CouponUseStatusSacInReq;
import com.skplanet.storeplatform.sac.client.internal.purchase.shopping.vo.CouponUseStatusSacInRes;
import com.skplanet.storeplatform.sac.purchase.cancel.repository.PurchaseCancelRepository;
import com.skplanet.storeplatform.sac.purchase.cancel.vo.PrchsProdDtl;
import com.skplanet.storeplatform.sac.purchase.cancel.vo.PurchaseCancelDetailSacParam;
import com.skplanet.storeplatform.sac.purchase.cancel.vo.PurchaseCancelDetailSacResult;
import com.skplanet.storeplatform.sac.purchase.cancel.vo.PurchaseCancelSacParam;
import com.skplanet.storeplatform.sac.purchase.cancel.vo.PurchaseCancelSacResult;
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
	private MessageSourceAccessor messageSourceAccessor;

	@Autowired
	private PurchaseCancelSCI purchaseCancelSCI;

	@Autowired
	private HistoryInternalSCI historyInternalSCI;

	@Autowired
	private ShoppingInternalSCI shoppingInternalSCI;

	@Autowired
	private DeviceSCI deviceSCI;

	@Autowired
	private AutoPaymentCancelSacService autoPaymentCancelSacService;

	@Autowired
	private ShoppingSCI shoppingSCI;

	@Autowired
	private PurchaseCancelRepository purchaseCancelRepository;

	@Autowired
	private ArmSCI armSCI;

	@Autowired
	private UpdatePurchaseCountSCI updatePurchaseCountSCI;

	@Autowired
	private PaymentInfoSCI paymentInfoSCI;

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
				if (StringUtils.isBlank(e.getErrorInfo().getMessage())) {
					purchaseCanDetailSacResult.setResultMsg(this.messageSourceAccessor.getMessage(e.getErrorInfo()
							.getCode()));
				} else {
					purchaseCanDetailSacResult.setResultMsg(e.getErrorInfo().getMessage());
				}

			} catch (Exception e) {

				this.logger.info("SAC_PUR_9999 : {}", e);

				purchaseCanDetailSacResult = new PurchaseCancelDetailSacResult();
				purchaseCanDetailSacResult.setPrchsId(purchaseCancelDetailSacParam.getPrchsId());
				purchaseCanDetailSacResult.setResultCd("SAC_PUR_9999");
				purchaseCanDetailSacResult.setResultMsg(this.messageSourceAccessor.getMessage("SAC_PUR_9999"));

			}

			if (StringUtils.equals("SAC_PUR_0000", purchaseCanDetailSacResult.getResultCd())) {
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
	public PurchaseCancelDetailSacResult updatePurchaseCancel(PurchaseCancelSacParam purchaseCancelSacParam,
			PurchaseCancelDetailSacParam purchaseCancelDetailSacParam) {

		PurchaseCancelDetailSacResult purchaseCancelDetailSacResult = new PurchaseCancelDetailSacResult();

		// 구매 정보 조회.
		PurchaseScReq purchaseScReq = new PurchaseScReq();

		// 인입 된 사람의 정보 넣어준다.
		purchaseScReq.setTenantId(purchaseCancelSacParam.getTenantId());
		purchaseScReq.setSystemId(purchaseCancelSacParam.getSystemId());
		purchaseScReq.setPrchsId(purchaseCancelDetailSacParam.getPrchsId());

		PurchaseScRes purchaseScRes = this.purchaseCancelSCI.getPurchase(purchaseScReq);

		// 구매 정보 가져와서 셋팅.
		purchaseCancelDetailSacParam.setPrchs(purchaseScRes.getPrchs());
		purchaseCancelDetailSacParam.setPrchsDtlList(purchaseScRes.getPrchsDtlList());
		purchaseCancelDetailSacParam.setPaymentList(purchaseScRes.getPaymentList());

		List<PrchsProdDtl> prchsProdDtlList = new ArrayList<PrchsProdDtl>();
		purchaseCancelDetailSacParam.setPrchsProdDtlList(prchsProdDtlList);

		// 각 상품 별 체크.
		for (PrchsDtl prchsDtl : purchaseCancelDetailSacParam.getPrchsDtlList()) {

			if (!StringUtils.equals(PurchaseConstants.PRCHS_STATUS_COMPT, prchsDtl.getStatusCd())) {
				throw new StorePlatformException("SAC_PUR_8101");
			}

			if (purchaseCancelSacParam.getPrchsCancelByType() == PurchaseConstants.PRCHS_CANCEL_BY_USER) {
				// 사용자가 취소하는 경우 권한 체크.
				if (StringUtils.equals(PurchaseConstants.PRCHS_CASE_GIFT_CD, prchsDtl.getPrchsCaseCd())) {
					// 선물이면 보낸사람 정보 확인.
					if (!StringUtils.equals(purchaseCancelSacParam.getUserKey(), prchsDtl.getSendInsdUsermbrNo())) {
						throw new StorePlatformException("SAC_PUR_8102");
					}
				} else {
					// 구매이면 이용자 정보 확인.
					if (!StringUtils.equals(purchaseCancelSacParam.getUserKey(), prchsDtl.getUseInsdUsermbrNo())) {
						throw new StorePlatformException("SAC_PUR_8102");
					}
				}
			}

			// 상품 정보 및 상품 이용자 정보 셋팅.
			PrchsProdDtl prchsProdDtl = new PrchsProdDtl();

			prchsProdDtl.setProdId(prchsDtl.getProdId());

			SearchDeviceIdSacReq searchDeviceIdSacReq = new SearchDeviceIdSacReq();
			searchDeviceIdSacReq.setUserKey(prchsDtl.getUseInsdUsermbrNo());
			searchDeviceIdSacReq.setDeviceKey(prchsDtl.getUseInsdDeviceId());
			SearchDeviceIdSacRes searchDeviceIdSacRes = this.deviceSCI.searchDeviceId(searchDeviceIdSacReq);

			prchsProdDtl.setDeviceId(searchDeviceIdSacRes.getDeviceId());

			// 전시 쪽 정보 가져와서 appId 셋팅.
			// 현재는 상품 정보가 어떻게 더 쓰일지 몰라 앞에 있지만 나중에 RO삭제 시 조회하도록 뒤로 옮겨야 할 듯..
			if (StringUtils.contains(PurchaseConstants.APP_TENANT_PROD_GRP_CD, prchsDtl.getTenantProdGrpCd())) {
				// 전시쪽 정보 가져와서 appId 셋팅.
				PaymentInfoSacReq paymentInfoSacReq = new PaymentInfoSacReq();
				paymentInfoSacReq.setTenantId(prchsDtl.getUseTenantId());
				List<String> prodIdList = new ArrayList<String>();
				prodIdList.add(prchsDtl.getProdId());
				paymentInfoSacReq.setProdIdList(prodIdList);
				paymentInfoSacReq.setLangCd(purchaseCancelSacParam.getLangCd());
				PaymentInfoSacRes paymentInfoSacRes = this.paymentInfoSCI.searchPaymentInfo(paymentInfoSacReq);
				if (paymentInfoSacRes != null && paymentInfoSacRes.getPaymentInfoList() != null
						&& paymentInfoSacRes.getPaymentInfoList().size() == 1) {
					prchsProdDtl.setAppId(paymentInfoSacRes.getPaymentInfoList().get(0).getAid());
				}

			}

			prchsProdDtlList.add(prchsProdDtl);

			if (StringUtils.equals(PurchaseConstants.PRCHS_PROD_TYPE_FIX, prchsDtl.getPrchsProdType())) {
				// 정액권 상품 처리.
				this.updateProdTypeFix(purchaseCancelSacParam, prchsDtl);

			}

		}

		// 쇼핑쿠폰 상품일 경우 쇼핑쿠폰 상품 취소.
		// 쇼핑쿠폰 사용유무 조회.
		for (PrchsDtl prchsDtl : purchaseCancelDetailSacParam.getPrchsDtlList()) {
			if (StringUtils.contains(PurchaseConstants.SHOPPING_COUPON_TENANT_PROD_GRP_CD,
					prchsDtl.getTenantProdGrpCd())) {
				this.updateCancelShoppingCoupon(purchaseCancelSacParam, purchaseCancelDetailSacParam);
				break;
			}
		}

		// 결제 취소.
		// TODO : PayPlanet 연동.

		// 구매 취소.
		PurchaseCancelScReq purchaseCancelScReq = new PurchaseCancelScReq();

		// 인입 된 사람의 정보를 넣어준다.
		purchaseCancelScReq.setTenantId(purchaseCancelSacParam.getTenantId());
		purchaseCancelScReq.setSystemId(purchaseCancelSacParam.getSystemId());
		// 구매 취소 정보를 넣어준다.
		purchaseCancelScReq.setPrchsId(purchaseCancelDetailSacParam.getPrchsId());
		purchaseCancelScReq.setPrchsStatusCd(PurchaseConstants.PRCHS_STATUS_CANCEL);
		purchaseCancelScReq.setCancelReqPathCd(purchaseCancelSacParam.getCancelReqPathCd());

		this.purchaseCancelSCI.updatePurchaseCancel(purchaseCancelScReq);

		// 전시 상품 구매건수 -1
		try {
			this.updatePurchaseCount(purchaseCancelSacParam, purchaseCancelDetailSacParam);
		} catch (Exception e) {
			this.logger.info("구매 상품 개수 업데이트 실패! ========= {}", e);
		}

		// RO 삭제! 삭제 실패해도 취소처리는 성공!
		try {
			this.removeRO(purchaseCancelSacParam, purchaseCancelDetailSacParam);
		} catch (Exception e) {
			this.logger.info("RO 삭제 실패! ========= {}", e);
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
		purchaseCancelDetailSacResult.setResultCd("SAC_PUR_0000");
		purchaseCancelDetailSacResult.setResultMsg(this.messageSourceAccessor.getMessage("SAC_PUR_0000"));

		this.logger.debug("구매 취소 성공!");

		return purchaseCancelDetailSacResult;

	}

	private void updateProdTypeFix(PurchaseCancelSacParam purchaseCancelSacParam, PrchsDtl prchsDtl) {
		// 정액제 상품으로 산 구매내역 조회.
		HistoryCountSacInReq historyCountSacInReq = new HistoryCountSacInReq();
		// 구매인지 선물인지 구분하여 조회.
		if (PurchaseConstants.PRCHS_CASE_GIFT_CD.equals(prchsDtl.getPrchsCaseCd())) {
			// 정액권 선물일 경우 취소 불가!! 최상훈차장님 결정!! 2014.02.13
			throw new StorePlatformException("SAC_PUR_8113");
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
			throw new StorePlatformException("SAC_PUR_8111");
		}

		// 정액권 자동구매 해지예약 호출.
		AutoPaymentCancelScReq autoPaymentCancelScReq = new AutoPaymentCancelScReq();
		autoPaymentCancelScReq.setTenantId(prchsDtl.getUseTenantId());
		autoPaymentCancelScReq.setUserKey(prchsDtl.getUseInsdUsermbrNo());
		autoPaymentCancelScReq.setDeviceKey(prchsDtl.getUseInsdDeviceId());
		autoPaymentCancelScReq.setPrchsId(prchsDtl.getPrchsId());
		autoPaymentCancelScReq.setClosedCd(PurchaseConstants.AUTO_PRCHS_CLOSE_RESERVE);
		// TODO : 추후 구매 취소 코드 정의 되면 받아야 함. 일단 고객요청으로 셋팅.
		autoPaymentCancelScReq.setClosedReasonCd("OR004601");
		autoPaymentCancelScReq.setClosedReqPathCd(purchaseCancelSacParam.getCancelReqPathCd());

		AutoPaymentCancelScRes autoPaymentCancelScRes = this.autoPaymentCancelSacService
				.updateReservation(autoPaymentCancelScReq);

		if (!StringUtils.equals("Y", autoPaymentCancelScRes.getResultYn())) {
			throw new StorePlatformException("SAC_PUR_8112");
		}

	}

	private void updateCancelShoppingCoupon(PurchaseCancelSacParam purchaseCancelSacParam,
			PurchaseCancelDetailSacParam purchaseCancelDetailSacParam) {

		CouponUseStatusSacInReq couponUseStatusSacInReq = new CouponUseStatusSacInReq();

		// 사용자의 tenantId, systemId를 알 수 없어서 구매자 또는 운영자의 값을 넣는다.
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

		// 쇼핑쿠폰 취소 요청.
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
	private void removeRO(PurchaseCancelSacParam purchaseCancelSacParam,
			PurchaseCancelDetailSacParam purchaseCancelDetailSacParam) {

		for (PrchsProdDtl prchsProdDtl : purchaseCancelDetailSacParam.getPrchsProdDtlList()) {
			if (StringUtils.isBlank(prchsProdDtl.getAppId())) {
				// appId가 있는 경우만 ro삭제 진행.
				continue;
			}
			// 각 상품 별 RO 삭제.
			// AOM Message Push.
			String resultMsg = "";
			try {
				resultMsg = this.purchaseCancelRepository.aomPush(prchsProdDtl);

				this.logger.info("removeRO.AomPush result ===== {}", resultMsg);
			} catch (Exception e) {
				this.logger.info("aom push fail! ========= {}", e.toString());
			}

			// ARM License 삭제 요청.
			RemoveLicenseEcReq removeLicenseEcReq = new RemoveLicenseEcReq();
			removeLicenseEcReq.setAppId(prchsProdDtl.getAppId());
			removeLicenseEcReq.setMdn(prchsProdDtl.getDeviceId());
			RemoveLicenseEcRes removeLicenseEcRes;
			try {
				removeLicenseEcRes = this.armSCI.removeLicense(removeLicenseEcReq);

				this.logger.info("removeRO.ArmPush result ===== {} ====== {}", removeLicenseEcRes.getResultCd(),
						removeLicenseEcRes.getResultMsg());
			} catch (Exception e) {
				this.logger.info("arm license remove fail! ========= {}", e.toString());
			}

		}

	}

	private void updatePurchaseCount(PurchaseCancelSacParam purchaseCancelSacParam,
			PurchaseCancelDetailSacParam purchaseCancelDetailSacParam) {

		List<UpdatePurchaseCountSacReq> updatePurchaseCountSacReqList = new ArrayList<UpdatePurchaseCountSacReq>();

		for (PrchsDtl prchsDtl : purchaseCancelDetailSacParam.getPrchsDtlList()) {
			UpdatePurchaseCountSacReq updatePurchaseCountSacReq = new UpdatePurchaseCountSacReq();
			updatePurchaseCountSacReq.setTenantId(prchsDtl.getUseTenantId());
			updatePurchaseCountSacReq.setProductId(prchsDtl.getProdId());
			updatePurchaseCountSacReq.setPurchaseCount(-prchsDtl.getProdQty());

			updatePurchaseCountSacReqList.add(updatePurchaseCountSacReq);
		}

		this.updatePurchaseCountSCI.updatePurchaseCount(updatePurchaseCountSacReqList);
	}

}
