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
import java.util.regex.Pattern;

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.skplanet.storeplatform.external.client.message.vo.SmsSendEcRes;
import com.skplanet.storeplatform.external.client.sap.sci.SapPurchaseSCI;
import com.skplanet.storeplatform.external.client.sap.vo.SendPurchaseNotiEcReq;
import com.skplanet.storeplatform.external.client.shopping.sci.ShoppingSCI;
import com.skplanet.storeplatform.external.client.shopping.vo.CouponPublishCancelEcReq;
import com.skplanet.storeplatform.external.client.tstore.vo.TStoreCashChargeCancelDetailEcReq;
import com.skplanet.storeplatform.external.client.tstore.vo.TStoreCashChargeCancelEcReq;
import com.skplanet.storeplatform.external.client.uaps.vo.UserEcRes;
import com.skplanet.storeplatform.framework.core.exception.StorePlatformException;
import com.skplanet.storeplatform.framework.core.exception.vo.ErrorInfo;
import com.skplanet.storeplatform.framework.core.helper.MultiMessageSourceAccessor;
import com.skplanet.storeplatform.framework.core.util.StringUtils;
import com.skplanet.storeplatform.purchase.client.common.vo.MembershipReserve;
import com.skplanet.storeplatform.purchase.client.common.vo.SapNoti;
import com.skplanet.storeplatform.purchase.client.history.vo.AutoPaymentCancelScReq;
import com.skplanet.storeplatform.purchase.client.history.vo.AutoPaymentCancelScRes;
import com.skplanet.storeplatform.purchase.client.membership.sci.MembershipReserveSCI;
import com.skplanet.storeplatform.purchase.client.order.sci.PurchaseOrderSCI;
import com.skplanet.storeplatform.purchase.client.order.vo.CreateSapNotiScReq;
import com.skplanet.storeplatform.sac.api.util.DateUtil;
import com.skplanet.storeplatform.sac.client.internal.display.localsci.sci.PaymentInfoSCI;
import com.skplanet.storeplatform.sac.client.internal.display.localsci.vo.PaymentInfoSacReq;
import com.skplanet.storeplatform.sac.client.internal.display.localsci.vo.PaymentInfoSacRes;
import com.skplanet.storeplatform.sac.client.internal.member.user.vo.SearchOrderUserByDeviceIdSacRes;
import com.skplanet.storeplatform.sac.client.internal.member.user.vo.SearchUserDeviceSacRes;
import com.skplanet.storeplatform.sac.client.internal.member.user.vo.UserDeviceInfoSac;
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
import com.skplanet.storeplatform.sac.purchase.common.vo.PurchaseErrorInfo;
import com.skplanet.storeplatform.sac.purchase.constant.PurchaseConstants;
import com.skplanet.storeplatform.sac.purchase.history.service.AutoPaymentCancelSacService;
import com.skplanet.storeplatform.sac.purchase.order.repository.PurchaseUapsRepository;

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
	private PurchaseUapsRepository purchaseUapsRespository;

	@Autowired
	private PayPlanetShopService payPlanetShopService;

	@Autowired
	private MembershipReserveSCI membershipReserveSCI;

	@Autowired
	private SapPurchaseSCI sapPurchaseSCI;

	@Autowired
	private PurchaseOrderSCI purchaseOrderSCI;

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

				/** 구매 취소 처리. */
				if (purchaseCancelSacParam.getPrchsCancelServiceType() == PurchaseConstants.PRCHS_CANCEL_SERVICE_TCASH) {
					purchaseCancelDetailSacResult = this.executePurchaseCancelForTCash(purchaseCancelSacParam,
							purchaseCancelDetailSacParam);
				} else {
					purchaseCancelDetailSacResult = this.executePurchaseCancel(purchaseCancelSacParam,
							purchaseCancelDetailSacParam);
				}

			} catch (StorePlatformException e) {

				purchaseCancelDetailSacResult = new PurchaseCancelDetailSacResult();
				purchaseCancelDetailSacResult.setPrchsId(purchaseCancelDetailSacParam.getPrchsId());

				ErrorInfo errorInfo = e.getErrorInfo();
				String errorMsg = "";
				String addErrorMsg = "";

				purchaseCancelDetailSacResult.setResultCd(errorInfo.getCode());
				if (StringUtils.isBlank(errorInfo.getMessage())) {
					errorMsg = this.multiMessageSourceAccessor.getMessage(errorInfo.getCode(), errorInfo.getArgs());
				} else {
					errorMsg = errorInfo.getMessage();
				}

				if (errorInfo.getArgs() != null && errorInfo.getArgs().length > 0) {
					for (Object obj : errorInfo.getArgs()) {
						if (obj instanceof PurchaseErrorInfo) {
							addErrorMsg = ((PurchaseErrorInfo) obj).getErrorMsg();
						}
					}
				}

				purchaseCancelDetailSacResult.setResultMsg(errorMsg
						+ (StringUtils.isBlank(addErrorMsg) ? "" : ", detailMsg : " + addErrorMsg));

				this.logger.info("SAC_PUR_XXXX code : {}", purchaseCancelDetailSacResult.getResultCd() + ", msg : "
						+ purchaseCancelDetailSacResult.getResultMsg());
				this.logger.info("purchaseCancelDetailSacParam data : {}", purchaseCancelDetailSacParam);

			} catch (Exception e) {

				purchaseCancelDetailSacResult = new PurchaseCancelDetailSacResult();
				purchaseCancelDetailSacResult.setPrchsId(purchaseCancelDetailSacParam.getPrchsId());
				purchaseCancelDetailSacResult.setResultCd("SAC_PUR_8999");

				purchaseCancelDetailSacResult.setResultMsg(this.multiMessageSourceAccessor.getMessage("SAC_PUR_8999")
						+ "cause : " + e);

				this.logger.info("SAC_PUR_8999 : code : {}", purchaseCancelDetailSacResult.getResultCd() + ", msg : "
						+ purchaseCancelDetailSacResult.getResultMsg());
				this.logger.info("purchaseCancelDetailSacParam data : {}", purchaseCancelDetailSacParam);

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

		/** 마일리지 적립 정보 조회. */
		MembershipReserve membershipReserveRes = this.purchaseCancelRepository.getMembershipReserve(prchsSacParam);

		// 마일리지 적립 정보가 존재하며 처리상태가 처리중인 경우 취소불가
		if (membershipReserveRes != null
				&& StringUtils.equals(PurchaseConstants.MEMBERSHIP_PROC_STATUS_WORKING,
						membershipReserveRes.getProcStatusCd())) {
			throw new StorePlatformException("SAC_PUR_8501");
		}

		/*
		 * 정액권 AI-IS 데이터로 인해 체크 제외 2014.08.05 최상훈C if (prchsSacParam.getTotAmt() > 0 &&
		 * (purchaseCancelDetailSacParam.getPaymentSacParamList() == null || purchaseCancelDetailSacParam
		 * .getPaymentSacParamList().isEmpty())) { throw new StorePlatformException("SAC_PUR_8104"); }
		 */

		/** deviceId 조회 및 셋팅. */
		if (StringUtils.isBlank(purchaseCancelDetailSacParam.getCancelMdn())) {
			// 2014.07.29 최상훈c 요건 추가(해당값이 존재하면 회원정보 조회 안함)
			if (!purchaseCancelSacParam.getIgnorePayPlanet()) {

				prchsSacParam
						.setDeviceId(this.purchaseCancelRepository.searchOrderDeviceId(prchsSacParam.getTenantId(),
								prchsSacParam.getInsdUsermbrNo(), prchsSacParam.getInsdDeviceId()));
			}
		} else {
			SearchOrderUserByDeviceIdSacRes searchOrderUserByDeviceIdSacRes = this.purchaseCancelRepository
					.searchOrderUserByDeviceId(purchaseCancelDetailSacParam.getCancelMdn(), prchsSacParam.getPrchsDt());

			if (searchOrderUserByDeviceIdSacRes != null) {
				if (!StringUtils.equals(searchOrderUserByDeviceIdSacRes.getUserKey(), prchsSacParam.getInsdUsermbrNo())
						|| !StringUtils.equals(searchOrderUserByDeviceIdSacRes.getDeviceKey(),
								prchsSacParam.getInsdDeviceId())) {
					throw new StorePlatformException("SAC_PUR_4107");
				}
			}

			prchsSacParam.setDeviceId(purchaseCancelDetailSacParam.getCancelMdn());
		}

		/*
		 * 
		 * ..................................구매 취소 시 상품 정보 확인 불필요하여 주석 처리..............................................
		 * ..............................상품 정보 셋팅.선물인 경우 동일 Tenant 인 경우만 선물 된다........................................
		 * PaymentInfoSacReq paymentInfoSacReq = new PaymentInfoSacReq();
		 * paymentInfoSacReq.setTenantId(prchsSacParam.getTenantId()); List<String> prodIdList = new
		 * ArrayList<String>(); for (PrchsDtlSacParam prchsDtlSacParam :
		 * purchaseCancelDetailSacParam.getPrchsDtlSacParamList()) { prodIdList.add(prchsDtlSacParam.getProdId()); }
		 * paymentInfoSacReq.setProdIdList(prodIdList); paymentInfoSacReq.setLangCd(purchaseCancelSacParam.getLangCd());
		 * PaymentInfoSacRes paymentInfoSacRes = this.paymentInfoSCI.searchPaymentInfo(paymentInfoSacReq); if
		 * (paymentInfoSacRes == null || paymentInfoSacRes.getPaymentInfoList() == null ||
		 * paymentInfoSacRes.getPaymentInfoList().size() < 1) { throw new StorePlatformException("SAC_PUR_5101"); }
		 */

		/** 구매 상품 별 체크. */
		boolean shoppingYn = false; // 쇼핑상품 구분.
		for (PrchsDtlSacParam prchsDtlSacParam : purchaseCancelDetailSacParam.getPrchsDtlSacParamList()) {

			if (StringUtils.startsWith(prchsDtlSacParam.getTenantProdGrpCd(),
					PurchaseConstants.TENANT_PRODUCT_GROUP_SHOPPING)) {
				// Biz쿠폰은 구매취소 처리 불가!
				if (StringUtils.equals(PurchaseConstants.PRCHS_REQ_PATH_BIZ_COUPON,
						prchsDtlSacParam.getPrchsReqPathCd())) {
					throw new StorePlatformException("SAC_PUR_8128");
				}

				// 쇼핑상품이면 true 셋팅.
				shoppingYn = true;
			}

			if (StringUtils.startsWith(prchsDtlSacParam.getTenantProdGrpCd(),
					PurchaseConstants.TENANT_PRODUCT_GROUP_DTL_GAMECASH_FIXRATE)) {
				// 게임캐쉬 정액제 처리. - 게임캐쉬 충전 취소 처리.
				String resvCol03 = prchsDtlSacParam.getResvCol03();
				String tCashCash = StringUtils.substringBetween(resvCol03, "CASH=", ";");
				String tCashPoint = StringUtils.substringBetween(resvCol03, "POINT=", ";");

				if (StringUtils.equals(PurchaseConstants.PRCHS_REQ_PATH_ADMIN_REFUND,
						purchaseCancelSacParam.getCancelReqPathCd())) {
					// 환불
				} else {
					// 취소
				}

				// 환불, 취소 구분 없이 무조껀 충전취소 처리 - 2014.04.30 최상훈c.
				TStoreCashChargeCancelEcReq tStoreCashChargeCancelEcReq = new TStoreCashChargeCancelEcReq();
				List<TStoreCashChargeCancelDetailEcReq> cashList = new ArrayList<TStoreCashChargeCancelDetailEcReq>();
				if (StringUtils.isNotBlank(tCashCash)) {
					TStoreCashChargeCancelDetailEcReq tStoreCashChargeCancelDetailEcReq = new TStoreCashChargeCancelDetailEcReq();
					tStoreCashChargeCancelDetailEcReq.setIdentifier(tCashCash);
					tStoreCashChargeCancelDetailEcReq.setCashCls(PurchaseConstants.TSTORE_CASH_CLASS_CASH);
					tStoreCashChargeCancelDetailEcReq.setOrderNo(prchsDtlSacParam.getPrchsId());
					tStoreCashChargeCancelDetailEcReq.setProductGroup(PurchaseConstants.TSTORE_CASH_PRODUCT_GROUP_APP);
					cashList.add(tStoreCashChargeCancelDetailEcReq);
				}
				if (StringUtils.isNotBlank(tCashPoint)) {
					TStoreCashChargeCancelDetailEcReq tStoreCashChargeCancelDetailEcReq = new TStoreCashChargeCancelDetailEcReq();
					tStoreCashChargeCancelDetailEcReq.setIdentifier(tCashPoint);
					tStoreCashChargeCancelDetailEcReq.setCashCls(PurchaseConstants.TSTORE_CASH_CLASS_POINT);
					tStoreCashChargeCancelDetailEcReq.setOrderNo(prchsDtlSacParam.getPrchsId());
					tStoreCashChargeCancelDetailEcReq.setProductGroup(PurchaseConstants.TSTORE_CASH_PRODUCT_GROUP_APP);
					cashList.add(tStoreCashChargeCancelDetailEcReq);
				}

				tStoreCashChargeCancelEcReq.setUserKey(prchsDtlSacParam.getUseInsdUsermbrNo());
				tStoreCashChargeCancelEcReq.setCashList(cashList);
				this.purchaseCancelRepository.cancelTCashCharge(tStoreCashChargeCancelEcReq);

			}

			// 정액권 상품 체크 (이북/코믹 정액권 인 경우 체크 제외한다. 전권상품일 경우 )
			if (StringUtils.equals(PurchaseConstants.PRCHS_PROD_TYPE_AUTH, prchsDtlSacParam.getPrchsProdType())) {

				if (!StringUtils.equals(PurchaseConstants.TENANT_PRODUCT_GROUP_DTL_EBOOK_FIXRATE,
						prchsDtlSacParam.getTenantProdGrpCd())
						&& !StringUtils.equals(PurchaseConstants.TENANT_PRODUCT_GROUP_DTL_COMIC_FIXRATE,
								prchsDtlSacParam.getTenantProdGrpCd())) {
					// 정액권 상품 처리.
					this.updateProdTypeFix(purchaseCancelSacParam, prchsDtlSacParam);
				}
			}
		}

		/** 쇼핑 상품 처리. */
		if (shoppingYn) {
			this.executeCancelShoppingCoupon(purchaseCancelSacParam, purchaseCancelDetailSacParam);
		}

		if (!purchaseCancelSacParam.getIgnorePayment()) {
			// 결제 처리 무시가 아니면 결제 취소 진행.
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

					try {
						// PayPlanet 결제.
						purchaseCancelDetailSacParam.setPayPlanetCancelEcRes(this.purchaseCancelRepository
								.cancelPaymentToPayPlanet(purchaseCancelSacParam, purchaseCancelDetailSacParam));
					} catch (StorePlatformException e) {
						ErrorInfo errorInfo = e.getErrorInfo();
						if (!"EC_PAYPLANET_9984".equals(errorInfo.getCode())) {
							throw e;
						}
					}
				}
			}
		}

		/** 구매 DB 취소 처리. */
		this.purchaseCancelRepository.updatePurchaseCancel(purchaseCancelSacParam, purchaseCancelDetailSacParam);

		/** 마일리지 적립 취소 처리 */
		if (membershipReserveRes != null) {
			this.purchaseCancelRepository.updateSaveCancel(purchaseCancelSacParam, purchaseCancelDetailSacParam,
					membershipReserveRes);
		}

		/** RO 삭제 처리. */
		// 2015.02.02 최상훈C 요청 주석처리
		// if (!purchaseCancelSacParam.getIgnorePayPlanet()) {
		// for (PrchsDtlSacParam prchsDtlSacParam : purchaseCancelDetailSacParam.getPrchsDtlSacParamList()) {
		// if (!StringUtils.startsWith(prchsDtlSacParam.getTenantProdGrpCd(),
		// PurchaseConstants.TENANT_PRODUCT_GROUP_APP)) {
		// // APP 상품이 아니면 통과.
		// continue;
		// }
		// try {
		// this.cancelRO(purchaseCancelSacParam, purchaseCancelDetailSacParam, prchsDtlSacParam);
		// } catch (Exception e) {
		// this.logger.info("RO 삭제 실패! ========= {}, {}", prchsDtlSacParam.getProdId(), e);
		// }
		// }
		// }

		purchaseCancelDetailSacResult.setPrchsId(purchaseCancelDetailSacParam.getPrchsId());
		purchaseCancelDetailSacResult.setResultCd("SAC_PUR_0000");
		purchaseCancelDetailSacResult.setResultMsg(this.multiMessageSourceAccessor.getMessage("SAC_PUR_0000"));

		/** 비회원 선물건이면 SMS를 발송한다. */
		try {
			for (PrchsDtlSacParam prchsDtlSacParam : purchaseCancelDetailSacParam.getPrchsDtlSacParamList()) {
				if (StringUtils.equals(PurchaseConstants.PRCHS_CASE_GIFT_CD, prchsDtlSacParam.getPrchsCaseCd())
						&& StringUtils.equals(PurchaseConstants.NON_MEMBER, prchsDtlSacParam.getUseInsdUsermbrNo())) {

					this.giftSendSms(prchsDtlSacParam, prchsSacParam, purchaseCancelSacParam);
					break;
				}
			}
		} catch (Exception e) {
			this.logger.error("### 구매 취소 SMS 발송 실패  prchsId : " + purchaseCancelDetailSacParam.getPrchsId());
		}

		// SAP 관련 구매취소 Noti (타사이면서 유료상품에 대해서만 noti)
		if (!StringUtils.equals(prchsSacParam.getTenantId(), PurchaseConstants.TENANT_ID_TSTORE)
				&& prchsSacParam.getTotAmt() > 0) {
			this.cancelNoti(prchsSacParam, purchaseCancelDetailSacParam, purchaseCancelSacParam);
		}

		this.logger.info("### 구매 취소 성공! ###");
		this.logger.info("purchaseCancelDetailSacParam data : {}", purchaseCancelDetailSacParam);

		return purchaseCancelDetailSacResult;

	}

	@Override
	public PurchaseCancelDetailSacResult executePurchaseCancelForTCash(PurchaseCancelSacParam purchaseCancelSacParam,
			PurchaseCancelDetailSacParam purchaseCancelDetailSacParam) {

		PurchaseCancelDetailSacResult purchaseCancelDetailSacResult = new PurchaseCancelDetailSacResult();

		/** 구매 정보 조회. */
		this.purchaseCancelRepository.setPurchaseDetailInfo(purchaseCancelSacParam, purchaseCancelDetailSacParam);
		if (purchaseCancelDetailSacParam.getPrchsSacParam() == null
				|| StringUtils.isBlank(purchaseCancelDetailSacParam.getPrchsSacParam().getPrchsId())) {
			throw new StorePlatformException("SAC_PUR_8100");
		}
		if (purchaseCancelDetailSacParam.getPaymentSacParamList() == null
				|| purchaseCancelDetailSacParam.getPaymentSacParamList().size() < 1) {
			// 구매 상세 정보가 없으면 구매 취소 불가.
			// throw new StorePlatformException("SAC_PUR_8100");
			// TCASH는 구매 상세 정보가 없어도 구매 취소 가능.
			// TCASH는 구매 정보가 있어야 함.
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
		prchsSacParam.setDeviceId(this.purchaseCancelRepository.searchOrderDeviceId(prchsSacParam.getTenantId(),
				prchsSacParam.getInsdUsermbrNo(), prchsSacParam.getInsdDeviceId()));

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

		purchaseCancelDetailSacResult.setPrchsId(purchaseCancelDetailSacParam.getPrchsId());
		purchaseCancelDetailSacResult.setResultCd("SAC_PUR_0000");
		purchaseCancelDetailSacResult.setResultMsg(this.multiMessageSourceAccessor.getMessage("SAC_PUR_0000"));

		this.logger.debug("구매 취소 성공!");

		return purchaseCancelDetailSacResult;

	}

	@Override
	public PurchaseCancelDetailSacResult cancelPurchaseForPaymentError(PurchaseCancelSacParam purchaseCancelSacParam,
			PurchaseCancelDetailSacParam purchaseCancelDetailSacParam) {
		PurchaseCancelDetailSacResult purchaseCancelDetailSacResult = new PurchaseCancelDetailSacResult();

		/** 구매 정보 조회. */
		this.purchaseCancelRepository.setPurchaseDetailInfo(purchaseCancelSacParam, purchaseCancelDetailSacParam);
		if (purchaseCancelDetailSacParam.getPrchsDtlSacParamList() == null
				|| purchaseCancelDetailSacParam.getPrchsDtlSacParamList().isEmpty()) {
			// 구매 상세 정보가 없으면 구매 취소 불가.
			throw new StorePlatformException("SAC_PUR_8100");
		}

		/** 구매 정보 체크. */
		PrchsDtlSacParam prchsDtlSacParam = purchaseCancelDetailSacParam.getPrchsDtlSacParamList().get(0);
		if (StringUtils.equals(PurchaseConstants.PRCHS_STATUS_RESERVATION, prchsDtlSacParam.getStatusCd())) {
			// 구매 예약 상태이면 구매 실패로 업데이트.
			try {
				this.purchaseCancelRepository.updatePurchaseCancelForPaymentError(purchaseCancelSacParam,
						purchaseCancelDetailSacParam);

				purchaseCancelDetailSacResult.setPrchsId(purchaseCancelDetailSacParam.getPrchsId());
				purchaseCancelDetailSacResult.setResultCd("SAC_PUR_0000");
				purchaseCancelDetailSacResult.setResultMsg(this.multiMessageSourceAccessor.getMessage("SAC_PUR_0000"));

			} catch (StorePlatformException e) {
				if (StringUtils.equals("SC_PUR_7101", e.getCode()) || StringUtils.equals("SAC_PUR_8103", e.getCode())) {
					// 예약상태인 구매가 없거나, 구매상태가 완료로 변경되었으면 구매 취소 호출.
					purchaseCancelDetailSacResult = this.executePurchaseCancel(purchaseCancelSacParam,
							purchaseCancelDetailSacParam);
				} else {
					throw e;
				}
			}

		} else if (StringUtils.equals(PurchaseConstants.PRCHS_STATUS_COMPT, prchsDtlSacParam.getStatusCd())) {
			// 구매 완료이면 구매 취소 호출.
			purchaseCancelDetailSacResult = this.executePurchaseCancel(purchaseCancelSacParam,
					purchaseCancelDetailSacParam);
		} else {
			throw new StorePlatformException("SAC_PUR_8101");
		}

		return purchaseCancelDetailSacResult;

	}

	@Override
	public PurchaseCancelDetailSacResult cancelPurchaseForInApp(PurchaseCancelSacParam purchaseCancelSacParam,
			PurchaseCancelDetailSacParam purchaseCancelDetailSacParam) {
		PurchaseCancelDetailSacResult purchaseCancelDetailSacResult = new PurchaseCancelDetailSacResult();

		/** 구매 정보 조회. */
		this.purchaseCancelRepository.setPurchaseDetailInfo(purchaseCancelSacParam, purchaseCancelDetailSacParam);
		if (purchaseCancelDetailSacParam.getPrchsDtlSacParamList() == null
				|| purchaseCancelDetailSacParam.getPrchsDtlSacParamList().isEmpty()) {
			// 구매 상세 정보가 없으면 구매 취소 불가.
			throw new StorePlatformException("SAC_PUR_8100");
		}

		/** 구매 정보 체크. */
		PrchsDtlSacParam prchsDtlSacParam = purchaseCancelDetailSacParam.getPrchsDtlSacParamList().get(0);
		if (!StringUtils.equals(PurchaseConstants.PRCHS_STATUS_COMPT, prchsDtlSacParam.getStatusCd())) {
			throw new StorePlatformException("SAC_PUR_8101");
		}

		if (!StringUtils.equals(PurchaseConstants.PRCHS_REQ_PATH_IAP, prchsDtlSacParam.getPrchsReqPathCd())) {
			// (구)InApp이 아니면 에러.
			throw new StorePlatformException("SAC_PUR_8129");
		}

		/** 마일리지 적립 정보 조회. */
		MembershipReserve membershipReserveReq = new MembershipReserve();
		membershipReserveReq.setTenantId(prchsDtlSacParam.getTenantId());
		membershipReserveReq.setTypeCd(PurchaseConstants.MEMBERSHIP_TYPE_TMEMBERSHIP);
		membershipReserveReq.setPrchsId(prchsDtlSacParam.getPrchsId());
		membershipReserveReq.setStatusCd(PurchaseConstants.PRCHS_STATUS_COMPT);
		MembershipReserve membershipReserveRes = this.membershipReserveSCI.getSaveInfo(membershipReserveReq);

		// 마일리지 적립 정보가 존재하며 처리상태가 처리중인 경우 취소불가
		if (membershipReserveRes != null
				&& StringUtils.equals(PurchaseConstants.MEMBERSHIP_PROC_STATUS_WORKING,
						membershipReserveRes.getProcStatusCd())) {
			throw new StorePlatformException("SAC_PUR_8501");
		}

		/** 구매 DB 취소 처리. */
		this.purchaseCancelRepository.updatePurchaseCancel(purchaseCancelSacParam, purchaseCancelDetailSacParam);

		/** 마일리지 적립 취소 처리 */
		this.purchaseCancelRepository.updateSaveCancel(purchaseCancelSacParam, purchaseCancelDetailSacParam,
				membershipReserveRes);

		purchaseCancelDetailSacResult.setPrchsId(purchaseCancelDetailSacParam.getPrchsId());
		purchaseCancelDetailSacResult.setResultCd("SAC_PUR_0000");
		purchaseCancelDetailSacResult.setResultMsg(this.multiMessageSourceAccessor.getMessage("SAC_PUR_0000"));

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
		historyCountSacInReq.setDeviceKey(prchsDtlSacParam.getUseInsdDeviceId());
		historyCountSacInReq.setStartDt(prchsDtlSacParam.getUseStartDt());
		historyCountSacInReq.setEndDt(prchsDtlSacParam.getUseExprDt());
		historyCountSacInReq.setPrchsCaseCd(prchsDtlSacParam.getPrchsCaseCd());
		historyCountSacInReq.setPrchsStatusCd(PurchaseConstants.PRCHS_STATUS_COMPT);
		historyCountSacInReq.setUseFixrateProdId(prchsDtlSacParam.getProdId());
		historyCountSacInReq.setPrchsProdHaveYn("Y");

		// Device 기반 체크를 위해 셋팅해줌
		if (StringUtils.isNotBlank(StringUtils.substring(prchsDtlSacParam.getTenantProdGrpCd(), 0, 8))) {
			historyCountSacInReq.setTenantProdGrpCd(StringUtils.substring(prchsDtlSacParam.getTenantProdGrpCd(), 0, 8));
		} else {
			historyCountSacInReq.setTenantProdGrpCd(prchsDtlSacParam.getTenantProdGrpCd());
		}

		HistoryCountSacInRes historyCountSacInRes = this.historyInternalSCI.searchHistoryCount(historyCountSacInReq);
		if (historyCountSacInRes.getTotalCnt() > 0) {
			// 정액권 상품으로 이용한 상품이 존재!
			throw new StorePlatformException("SAC_PUR_8111");
		}

		// 정액권 자동구매 확인.
		String autoPrchsStatusCd = this.purchaseCancelRepository.getAutoPrchsStatus(purchaseCancelSacParam,
				prchsDtlSacParam);
		if (StringUtils.equals(PurchaseConstants.AUTO_PRCHS_STATUS_AUTO, autoPrchsStatusCd)) {
			// 자동결제 중일 경우 정액권 자동구매 해지예약 호출.
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
				this.logger.info("AutoPaymentCancelScReq data : {}", autoPaymentCancelScReq);
				this.logger.info("AutoPaymentCancelScRes data : {}", autoPaymentCancelScRes);
				throw new StorePlatformException("SAC_PUR_8112");
			}
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

		this.validateCancelPaymentForShoppingCoupon(purchaseCancelSacParam, purchaseCancelDetailSacParam);

		/** 강제 취소가 아닐 경우 쿠폰 사용 유무를 조회해온다. */
		if (!StringUtils.equals("Y", purchaseCancelSacParam.getShoppingForceCancelYn())) {

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
					this.logger.info("SAC_PUR_8121 couponUseStatusDetailSacInRes data : {}",
							couponUseStatusDetailSacInRes);

					if (StringUtils.equals("1", couponUseStatusDetailSacInRes.getCpnUseStatusCd())) {
						throw new StorePlatformException("SAC_PUR_8121");
					} else if (StringUtils.equals("2", couponUseStatusDetailSacInRes.getCpnUseStatusCd())) {
						// 2014.10.24 취소/환불된 쿠폰일 경우 구매취소 정상처리한다. 최상훈C
						// throw new StorePlatformException("SAC_PUR_8124");
					} else if (StringUtils.equals("3", couponUseStatusDetailSacInRes.getCpnUseStatusCd())) {
						throw new StorePlatformException("SAC_PUR_8125");
					} else if (StringUtils.equals("4", couponUseStatusDetailSacInRes.getCpnUseStatusCd())) {
						throw new StorePlatformException("SAC_PUR_8126");
					} else {
						throw new StorePlatformException("SAC_PUR_8127");
					}
				}
			}
		}

		/** 쇼핑 쿠폰 취소 처리 */
		// Biz쿠폰의 경우 취소 불가처리! 나중에 취소 가능하게 하려면 for문 돌면서 prchsId + prchsDtlId로 취소 요청 처리.
		// AS-IS 선물의 경우 선물구매 ID를 넣어준다.
		String couponPrchsId = purchaseCancelDetailSacParam.getPrchsDtlSacParamList().get(0).getCouponCmsPrchsId();

		CouponPublishCancelEcReq couponPublishCancelEcReq = new CouponPublishCancelEcReq();
		couponPublishCancelEcReq.setPrchsId(couponPrchsId);
		couponPublishCancelEcReq.setForceFlag(purchaseCancelSacParam.getShoppingForceCancelYn());
		try {
			this.shoppingSCI.cancelCouponPublish(couponPublishCancelEcReq);
		} catch (StorePlatformException e) {

			if (!purchaseCancelSacParam.getIgnoreCouponCms()) {
				ErrorInfo errorInfo = e.getErrorInfo();
				this.logger.info("Shopping Coupon Exception CODE : {}", errorInfo.getCode());
				if (!StringUtils.equals("EC_SCPNCMS_3215", errorInfo.getCode())) {
					this.logger.info("SAC_PUR_8122 Exception : {}", e.getMessage());
					throw new StorePlatformException("SAC_PUR_8122", e);
				}
			}
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
	// private void cancelRO(PurchaseCancelSacParam purchaseCancelSacParam,
	// PurchaseCancelDetailSacParam purchaseCancelDetailSacParam, PrchsDtlSacParam prchsDtlSacParam) {
	//
	// /** 사용자 deviceId 조회. */
	// String deviceId = this.purchaseCancelRepository.searchOrderDeviceId(prchsDtlSacParam.getTenantId(),
	// prchsDtlSacParam.getUseInsdUsermbrNo(), prchsDtlSacParam.getUseInsdDeviceId());
	//
	// /** appId 조회. */
	// PaymentInfoSacReq paymentInfoSacReq = new PaymentInfoSacReq();
	// paymentInfoSacReq.setTenantId(prchsDtlSacParam.getUseTenantId());
	//
	// List<String> prodIdList = new ArrayList<String>();
	// prodIdList.add(prchsDtlSacParam.getProdId());
	// paymentInfoSacReq.setProdIdList(prodIdList);
	//
	// paymentInfoSacReq.setLangCd(purchaseCancelSacParam.getLangCd());
	// PaymentInfoSacRes paymentInfoSacRes = this.paymentInfoSCI.searchPaymentInfo(paymentInfoSacReq);
	// if (paymentInfoSacRes == null || paymentInfoSacRes.getPaymentInfoList() == null
	// || paymentInfoSacRes.getPaymentInfoList().size() < 1) {
	// throw new StorePlatformException("SAC_PUR_5101");
	// }
	// prchsDtlSacParam.setAppId(paymentInfoSacRes.getPaymentInfoList().get(0).getAid());
	//
	// String resultMsg = "";
	// /** AOM PUSH */
	// try {
	// resultMsg = this.purchaseCancelRepository.aomPush(deviceId, prchsDtlSacParam.getAppId());
	//
	// this.logger.debug("removeRO.AomPush result ===== {}", resultMsg);
	// } catch (Exception e) {
	// this.logger.debug("aom push fail! ========= {}", e.toString());
	// }
	//
	// /** ARM License Remove */
	// try {
	// resultMsg = this.purchaseCancelRepository.armRemoveLicense(deviceId, prchsDtlSacParam.getAppId());
	//
	// this.logger.debug("removeRO.ArmPush result ===== {}", resultMsg);
	// } catch (Exception e) {
	// this.logger.debug("arm license remove fail! ========= {}", e.toString());
	// }
	//
	// }

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
			// PayPlanet 결제이면 authKey, mid 셋팅. MOID에 서비스 관리번호가 들어가면서 아래와 같이 로직 변경
			if (StringUtils.startsWith(paymentSacParam.getTid(), PurchaseConstants.PAYPLANET_TID_PREFIX)
					|| StringUtils.startsWith(paymentSacParam.getTid(), PurchaseConstants.PAYPLANET_TID_PREFIX_SEC)) {
				this.logger.info("[##PurchaseCancel] PayPlanet 결제처리");
				paymentSacParam.setAuthKey(payPlanetShop.getAuthKey());
				paymentSacParam.setMid(StringUtils.substringBefore(paymentSacParam.getTid(), "_"));
			}
		}

		return;

	}

	private void validateCancelPaymentForShoppingCoupon(PurchaseCancelSacParam purchaseCancelSacParam,
			PurchaseCancelDetailSacParam purchaseCancelDetailSacParam) {

		if (purchaseCancelSacParam.getIgnorePayment()) {
			return;
		}

		/** 결제 취소 가능 여부 확인 */
		List<PaymentSacParam> paymentSacParamList = purchaseCancelDetailSacParam.getPaymentSacParamList();
		if (paymentSacParamList != null && !paymentSacParamList.isEmpty()) {
			for (PaymentSacParam paymentSacParam : paymentSacParamList) {
				if (StringUtils.equals(PurchaseConstants.PAYMENT_METHOD_DANAL, paymentSacParam.getPaymentMtdCd())
						&& !StringUtils.isEmpty(paymentSacParam.getPaymentDt())
						&& !StringUtils.equals(DateUtil.getToday("yyyyMM"),
								StringUtils.substring(paymentSacParam.getPaymentDt(), 0, 6))) {
					// 다날 결제이면서 결제월과 취소월이 다른 경우 취소 불가.
					throw new StorePlatformException("SAC_PUR_8401");
				}

				if (!purchaseCancelSacParam.getIgnorePayPlanet()) {
					if (StringUtils.equals(PurchaseConstants.PAYMENT_METHOD_SKT_CARRIER,
							paymentSacParam.getPaymentMtdCd())) {
						// SKT 후불 결제.
						UserEcRes userEcRes = this.purchaseUapsRespository
								.searchUapsMappingInfoByMdn(purchaseCancelDetailSacParam.getPrchsSacParam()
										.getDeviceId());
						String[] serviceCdList = userEcRes.getServiceCD();
						if (serviceCdList != null) {
							for (String serviceCd : serviceCdList) {
								for (String uapsSvcLimitService : PurchaseConstants.UAPS_SVC_LIMIT_SERVICE) {
									if (StringUtils.equals(serviceCd, uapsSvcLimitService)
											&& !StringUtils.equals("Y",
													purchaseCancelSacParam.getSktLimitUserCancelYn())) {
										// 한도 가입자이면서 한도가입자 취소여부가 Y가 아니면 에러.
										throw new StorePlatformException("SAC_PUR_8123");
									}
								}
							}
						}
					}
				}
			}
		}
	}

	private String devicdKeyToMdn(String deviceKey) {

		deviceKey = deviceKey.replaceAll("Ab", "0").replaceAll("cDe", "5").replaceAll("Fg", "2")
				.replaceAll("HiJk", "6").replaceAll("Lm", "4").replaceAll("NoP", "1").replaceAll("qR", "8")
				.replaceAll("sTu", "7").replaceAll("Vw", "3").replaceAll("XyZ", "9");

		return deviceKey;
	}

	private void giftSendSms(PrchsDtlSacParam prchsDtlSacParam, PrchsSacParam prchsSacParam,
			PurchaseCancelSacParam purchaseCancelSacParam) {

		this.logger.info("### NONMEMBER CANCEL SMS TARGET");
		List<String> prodIdList = new ArrayList<String>();

		PaymentInfoSacReq paymentInfoSacReq = new PaymentInfoSacReq();
		paymentInfoSacReq.setTenantId(prchsSacParam.getTenantId());
		prodIdList.add(prchsDtlSacParam.getProdId());
		paymentInfoSacReq.setProdIdList(prodIdList);
		paymentInfoSacReq.setLangCd(purchaseCancelSacParam.getLangCd());
		paymentInfoSacReq.setDeviceModelCd(purchaseCancelSacParam.getModel());

		// 상품정보 조회
		PaymentInfoSacRes paymentInfoSacRes = this.paymentInfoSCI.searchPaymentInfo(paymentInfoSacReq);

		String prodNm = null;

		if (paymentInfoSacRes != null && paymentInfoSacRes.getPaymentInfoList().size() > 0) {
			prodNm = paymentInfoSacRes.getPaymentInfoList().get(0).getProdNm();
		}

		String sendMdn = prchsSacParam.getDeviceId(); // 보낸사람MDN
		if (StringUtils.isBlank(sendMdn)) {
			sendMdn = this.purchaseCancelRepository.searchOrderDeviceId(prchsSacParam.getTenantId(),
					prchsSacParam.getInsdUsermbrNo(), prchsSacParam.getInsdDeviceId());
		}

		sendMdn = this.makePhoneNumber(sendMdn);

		String recvMdn = this.devicdKeyToMdn(prchsDtlSacParam.getUseInsdDeviceId()); // 받는사람 MDN
		String msg = sendMdn + "님이 선물하신 \"<" + prodNm + ">\"을 취소하셨습니다.";

		this.logger.info("### NONMEMBER CANCEL SMS recvMdn =  " + recvMdn);
		this.logger.info("### NONMEMBER CANCEL SMS  msg =  " + msg);

		// SMS 발송 요청
		SmsSendEcRes res = this.purchaseCancelRepository.sendSms(recvMdn, msg);

		if (res != null && StringUtils.equals("success", res.getResultStatus())) {
			this.logger.info("### NONMEMBER CANCEL SMS SUCCESS");
		} else {
			this.logger.error("### NONMEMBER CANCEL SMS FAIL");
		}

	}

	private String makePhoneNumber(String phoneNumber) {

		String regEx = "(\\d{3})(\\d{3,4})(\\d{4})";

		if (!Pattern.matches(regEx, phoneNumber))
			return phoneNumber;

		return phoneNumber.replaceAll(regEx, "$1-$2-$3");

	}

	private void cancelNoti(PrchsSacParam prchsSacParam, PurchaseCancelDetailSacParam purchaseCancelDetailSacParam,
			PurchaseCancelSacParam purchaseCancelSacParam) {

		String userEmail = null;

		// userKey, deviceKey 를 이용한 회원정보조회
		SearchUserDeviceSacRes searchUserDeviceSacRes = this.purchaseCancelRepository.searchUserByDeviceKey(
				prchsSacParam.getTenantId(), prchsSacParam.getInsdUsermbrNo(), prchsSacParam.getInsdDeviceId());
		UserDeviceInfoSac userDeviceInfoSac = searchUserDeviceSacRes.getUserDeviceInfo().get(
				prchsSacParam.getInsdDeviceId());

		SendPurchaseNotiEcReq sendPurchaseNotiEcReq = new SendPurchaseNotiEcReq();
		sendPurchaseNotiEcReq.setTenantId(prchsSacParam.getTenantId());
		sendPurchaseNotiEcReq.setDeviceId(userDeviceInfoSac.getDeviceId());
		sendPurchaseNotiEcReq.setDeviceKey(userDeviceInfoSac.getMarketDeviceKey());
		sendPurchaseNotiEcReq.setUserEmail(userEmail);
		sendPurchaseNotiEcReq.setPrchsId(prchsSacParam.getPrchsId());
		sendPurchaseNotiEcReq.setPrchsDt(prchsSacParam.getPrchsDt());
		sendPurchaseNotiEcReq.setCancelDt(purchaseCancelDetailSacParam.getCancelDt());
		sendPurchaseNotiEcReq.setStatusCd(PurchaseConstants.PRCHS_STATUS_CANCEL);
		sendPurchaseNotiEcReq.setTotAmt(prchsSacParam.getTotAmt());
		sendPurchaseNotiEcReq.setRequestId(purchaseCancelDetailSacParam.getPaymentSacParamList().get(0).getTid());

		// Noti
		this.logger.info("PRCHS,CANCEL,SAC,POST,NOTI,SAP,REQ,ONLY,{}",
				ReflectionToStringBuilder.toString(sendPurchaseNotiEcReq, ToStringStyle.SHORT_PREFIX_STYLE));

		String errDesc = null;
		boolean bSucc = false;

		try {
			// 정상완료응답이 아닌경우 Exception 발생
			this.sapPurchaseSCI.sendPurchaseNoti(sendPurchaseNotiEcReq);
			bSucc = true;

		} catch (Exception e) {
			if (e instanceof StorePlatformException) {
				errDesc = ((StorePlatformException) e).getCode();
			} else {
				errDesc = e.getMessage();
			}
		}

		this.logger.info("PRCHS,CANCEL,SAC,POST,NOTI,SAP,RESULT,{},{}", bSucc, errDesc);

		String procStatusCd = bSucc ? PurchaseConstants.SAP_PURCHASE_NOTI_PROC_STATUS_SUCCESS : PurchaseConstants.SAP_PURCHASE_NOTI_PROC_STATUS_RESERVE;

		this.notiDbInsert(prchsSacParam, purchaseCancelDetailSacParam, purchaseCancelSacParam, sendPurchaseNotiEcReq,
				procStatusCd, errDesc);
	}

	private void notiDbInsert(PrchsSacParam prchsSacParam, PurchaseCancelDetailSacParam purchaseCancelDetailSacParam,
			PurchaseCancelSacParam purchaseCancelSacParam, SendPurchaseNotiEcReq sendPurchaseNotiEcReq,
			String procStatusCd, String errDesc) {
		List<SapNoti> sapNotiList = new ArrayList<SapNoti>();
		SapNoti sapNoti = new SapNoti();

		sapNoti.setTenantId(sendPurchaseNotiEcReq.getTenantId());
		sapNoti.setPrchsId(sendPurchaseNotiEcReq.getPrchsId());
		sapNoti.setPrchsDtlId(1);
		sapNoti.setInsdUsermbrNo(prchsSacParam.getInsdUsermbrNo());
		sapNoti.setInsdDeviceId(prchsSacParam.getInsdDeviceId());
		sapNoti.setMarketDeviceKey(sendPurchaseNotiEcReq.getDeviceKey());
		sapNoti.setDeviceId(sendPurchaseNotiEcReq.getDeviceId());
		sapNoti.setUserEmail(sendPurchaseNotiEcReq.getUserEmail());
		sapNoti.setStatusCd(sendPurchaseNotiEcReq.getStatusCd());
		sapNoti.setPrchsDt(sendPurchaseNotiEcReq.getPrchsDt());
		sapNoti.setCancelDt(sendPurchaseNotiEcReq.getCancelDt());
		sapNoti.setTotAmt(sendPurchaseNotiEcReq.getTotAmt());
		sapNoti.setPpTid(sendPurchaseNotiEcReq.getRequestId());
		sapNoti.setPrchsCaseCd(purchaseCancelDetailSacParam.getPrchsDtlSacParamList().get(0).getPrchsCaseCd()); // 2014.12.19
		sapNoti.setAddParamInfo("");
		sapNoti.setProcStatusCd(procStatusCd);
		sapNoti.setProcDesc(errDesc);
		sapNoti.setRegId(purchaseCancelSacParam.getSystemId());
		sapNoti.setUpdId(purchaseCancelSacParam.getSystemId());

		this.logger.info("PRCHS,CANCEL,SAC,POST,NOTI,SAP,REQ,INSERT,{}",
				ReflectionToStringBuilder.toString(sapNoti, ToStringStyle.SHORT_PREFIX_STYLE));

		sapNotiList.add(sapNoti);

		try {
			this.purchaseOrderSCI.createSapNoti(new CreateSapNotiScReq(sapNotiList));

		} catch (Exception e) {
			if (e instanceof StorePlatformException) {
				errDesc = ((StorePlatformException) e).getCode();
			} else {
				errDesc = e.getMessage();
			}
			this.logger.info("PRCHS,CANCEL,SAC,POST,NOTI,SAP,INS,ERROR,{},{}", errDesc);
		}
	}
}
