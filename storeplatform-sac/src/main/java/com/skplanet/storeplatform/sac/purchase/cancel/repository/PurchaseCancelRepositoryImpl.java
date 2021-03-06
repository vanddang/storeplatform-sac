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

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.skplanet.storeplatform.external.client.arm.sci.ArmSCI;
import com.skplanet.storeplatform.external.client.arm.vo.RemoveLicenseEcReq;
import com.skplanet.storeplatform.external.client.arm.vo.RemoveLicenseEcRes;
import com.skplanet.storeplatform.external.client.message.sci.MessageSCI;
import com.skplanet.storeplatform.external.client.message.vo.AomSendEcReq;
import com.skplanet.storeplatform.external.client.message.vo.AomSendEcRes;
import com.skplanet.storeplatform.external.client.message.vo.SmsSendEcReq;
import com.skplanet.storeplatform.external.client.message.vo.SmsSendEcRes;
import com.skplanet.storeplatform.external.client.payplanet.sci.CancelSCI;
import com.skplanet.storeplatform.external.client.payplanet.vo.CancelEcReq;
import com.skplanet.storeplatform.external.client.payplanet.vo.CancelEcRes;
import com.skplanet.storeplatform.external.client.payplanet.vo.CancelEcResPaymethod;
import com.skplanet.storeplatform.external.client.shopping.sci.ShoppingSCI;
import com.skplanet.storeplatform.external.client.tstore.sci.TStoreCashSCI;
import com.skplanet.storeplatform.external.client.tstore.sci.TStorePaymentSCI;
import com.skplanet.storeplatform.external.client.tstore.vo.Pay;
import com.skplanet.storeplatform.external.client.tstore.vo.PayCancelResult;
import com.skplanet.storeplatform.external.client.tstore.vo.PaymentCancel;
import com.skplanet.storeplatform.external.client.tstore.vo.PaymentCancelEcReq;
import com.skplanet.storeplatform.external.client.tstore.vo.PaymentCancelEcRes;
import com.skplanet.storeplatform.external.client.tstore.vo.PaymentCancelResult;
import com.skplanet.storeplatform.external.client.tstore.vo.TStoreCashChargeCancelDetailEcReq;
import com.skplanet.storeplatform.external.client.tstore.vo.TStoreCashChargeCancelEcReq;
import com.skplanet.storeplatform.external.client.tstore.vo.TStoreCashChargeCancelEcRes;
import com.skplanet.storeplatform.external.client.tstore.vo.TStoreCashRefundEcReq;
import com.skplanet.storeplatform.external.client.tstore.vo.TStoreCashRefundEcRes;
import com.skplanet.storeplatform.external.client.tstore.vo.TStoreCashSaveCancelEcReq;
import com.skplanet.storeplatform.external.client.tstore.vo.TStoreCashSaveCancelEcRes;
import com.skplanet.storeplatform.external.client.tstore.vo.TStoreCashSpecificRefundEcReq;
import com.skplanet.storeplatform.external.client.tstore.vo.TStoreCashSpecificRefundEcRes;
import com.skplanet.storeplatform.framework.core.exception.StorePlatformException;
import com.skplanet.storeplatform.framework.core.util.StringUtils;
import com.skplanet.storeplatform.purchase.client.cancel.sci.PurchaseCancelSCI;
import com.skplanet.storeplatform.purchase.client.cancel.vo.AutoPaymentScReq;
import com.skplanet.storeplatform.purchase.client.cancel.vo.AutoPaymentScRes;
import com.skplanet.storeplatform.purchase.client.cancel.vo.PurchaseCancelPaymentDetailScReq;
import com.skplanet.storeplatform.purchase.client.cancel.vo.PurchaseCancelScReq;
import com.skplanet.storeplatform.purchase.client.cancel.vo.PurchaseCancelScRes;
import com.skplanet.storeplatform.purchase.client.cancel.vo.PurchaseScReq;
import com.skplanet.storeplatform.purchase.client.cancel.vo.PurchaseScRes;
import com.skplanet.storeplatform.purchase.client.common.vo.MembershipReserve;
import com.skplanet.storeplatform.purchase.client.common.vo.Payment;
import com.skplanet.storeplatform.purchase.client.common.vo.Prchs;
import com.skplanet.storeplatform.purchase.client.common.vo.PrchsDtl;
import com.skplanet.storeplatform.purchase.client.common.vo.PrchsProdCnt;
import com.skplanet.storeplatform.purchase.client.membership.sci.MembershipReserveSCI;
import com.skplanet.storeplatform.purchase.client.product.count.sci.PurchaseCountSCI;
import com.skplanet.storeplatform.purchase.client.product.count.vo.InsertPurchaseProductCountScReq;
import com.skplanet.storeplatform.sac.client.internal.member.user.sci.DeviceSCI;
import com.skplanet.storeplatform.sac.client.internal.member.user.sci.SearchUserSCI;
import com.skplanet.storeplatform.sac.client.internal.member.user.vo.SearchDeviceIdSacReq;
import com.skplanet.storeplatform.sac.client.internal.member.user.vo.SearchDeviceIdSacRes;
import com.skplanet.storeplatform.sac.client.internal.member.user.vo.SearchOrderDeviceIdSacReq;
import com.skplanet.storeplatform.sac.client.internal.member.user.vo.SearchOrderDeviceIdSacRes;
import com.skplanet.storeplatform.sac.client.internal.member.user.vo.SearchOrderUserByDeviceIdSacReq;
import com.skplanet.storeplatform.sac.client.internal.member.user.vo.SearchOrderUserByDeviceIdSacRes;
import com.skplanet.storeplatform.sac.client.internal.member.user.vo.SearchUserDeviceSac;
import com.skplanet.storeplatform.sac.client.internal.member.user.vo.SearchUserDeviceSacReq;
import com.skplanet.storeplatform.sac.client.internal.member.user.vo.SearchUserDeviceSacRes;
import com.skplanet.storeplatform.sac.purchase.cancel.vo.PaymentSacParam;
import com.skplanet.storeplatform.sac.purchase.cancel.vo.PrchsDtlSacParam;
import com.skplanet.storeplatform.sac.purchase.cancel.vo.PrchsSacParam;
import com.skplanet.storeplatform.sac.purchase.cancel.vo.PurchaseCancelDetailSacParam;
import com.skplanet.storeplatform.sac.purchase.cancel.vo.PurchaseCancelSacParam;
import com.skplanet.storeplatform.sac.purchase.common.util.PayPlanetUtils;
import com.skplanet.storeplatform.sac.purchase.common.vo.PurchaseErrorInfo;
import com.skplanet.storeplatform.sac.purchase.constant.PurchaseConstants;
import com.skplanet.storeplatform.sac.purchase.order.PaymethodUtil;

/**
 * 구매 취소 repository implements.
 * 
 * Updated on : 2014. 1. 16. Updated by : nTels_cswoo81, nTels.
 */
@Component
public class PurchaseCancelRepositoryImpl implements PurchaseCancelRepository {

	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private PurchaseCancelSCI purchaseCancelSCI;

	@Autowired
	private MessageSCI messageSCI;

	@Autowired
	private ArmSCI armSCI;

	@Autowired
	private CancelSCI cancelSCI;

	@Autowired
	private TStorePaymentSCI tStorePaymentSCI;

	@Autowired
	private DeviceSCI deviceSCI;

	@Autowired
	private TStoreCashSCI tStoreCashSCI;

	@Autowired
	private PurchaseCountSCI purchaseCountSCI;

	@Autowired
	private MembershipReserveSCI membershipReserveSCI;

	@Autowired
	private SearchUserSCI searchUserSCI;

	@Autowired
	private ShoppingSCI shoppingSCI;

	@Override
	public PurchaseCancelDetailSacParam setPurchaseDetailInfo(PurchaseCancelSacParam purchaseCancelSacParam,
			PurchaseCancelDetailSacParam purchaseCancelDetailSacParam) {

		PurchaseScReq purchaseScReq = new PurchaseScReq();

		// 인입 된 사람의 정보 넣어준다.
		purchaseScReq.setTenantId(purchaseCancelSacParam.getTenantId());
		purchaseScReq.setSystemId(purchaseCancelSacParam.getSystemId());

		// 2015.05.08 userKey 추가
		// purchaseScReq.setUserKey(purchaseCancelSacParam.getUserKey());

		// 취소 할 구매ID를 넣어준다.
		purchaseScReq.setPrchsId(purchaseCancelDetailSacParam.getPrchsId());

		PurchaseScRes purchaseScRes = this.purchaseCancelSCI.getPurchase(purchaseScReq);
		if (purchaseScRes == null) {
			// 구매 상세가 기준이기 때문에 구매 테이블은 체크하지 말아라. 2014-04-25 최상훈c.
			throw new StorePlatformException("SAC_PUR_8100");
		}

		// 구매 정보 가져와서 셋팅.
		purchaseCancelDetailSacParam.setPrchsSacParam(this.convertResForPrchsSacParam(purchaseScRes.getPrchs()));

		// 구매 상세 정보. (AS-IS T Cash의 경우 구매 상세 정보가 없을 수 있다.)
		List<PrchsDtlSacParam> prchsDtlSacParamList = new ArrayList<PrchsDtlSacParam>();
		if (purchaseScRes.getPrchsDtlList() != null) {
			for (PrchsDtl prchsDtl : purchaseScRes.getPrchsDtlList()) {
				PrchsDtlSacParam prchsDtlSacParam = this.converResForPrchsDtlSacParam(prchsDtl);
				prchsDtlSacParamList.add(prchsDtlSacParam);
			}
		}
		purchaseCancelDetailSacParam.setPrchsDtlSacParamList(prchsDtlSacParamList);

		// 결제 상세 정보.
		List<PaymentSacParam> paymentSacParamList = new ArrayList<PaymentSacParam>();
		if (purchaseScRes.getPaymentList() != null) {
			for (Payment payment : purchaseScRes.getPaymentList()) {
				PaymentSacParam paymentSacParam = this.convertResForPaymentSacParam(payment);
				paymentSacParamList.add(paymentSacParam);
			}
		}
		purchaseCancelDetailSacParam.setPaymentSacParamList(paymentSacParamList);

		return purchaseCancelDetailSacParam;

	}

	@Override
	public String getDeviceId(String userKey, String deviceKey) {

		SearchDeviceIdSacReq searchDeviceIdSacReq = new SearchDeviceIdSacReq();
		searchDeviceIdSacReq.setUserKey(userKey);
		searchDeviceIdSacReq.setDeviceKey(deviceKey);
		SearchDeviceIdSacRes searchDeviceIdSacRes = this.deviceSCI.searchDeviceId(searchDeviceIdSacReq);
		if (searchDeviceIdSacRes == null || StringUtils.isEmpty(searchDeviceIdSacRes.getDeviceId())) {
			throw new StorePlatformException("SAC_PUR_4101");
		}

		return searchDeviceIdSacRes.getDeviceId();

	}

	@Override
	public String searchOrderDeviceId(String tenantId, String userKey, String deviceKey) {

		SearchOrderDeviceIdSacReq searchOrderDeviceIdSacReq = new SearchOrderDeviceIdSacReq();
		searchOrderDeviceIdSacReq.setTenantId(tenantId);
		searchOrderDeviceIdSacReq.setUserKey(userKey);
		searchOrderDeviceIdSacReq.setDeviceKey(deviceKey);
		SearchOrderDeviceIdSacRes searchOrderDeviceIdSacRes = this.deviceSCI
				.searchOrderDeviceId(searchOrderDeviceIdSacReq);
		if (searchOrderDeviceIdSacRes == null || StringUtils.isEmpty(searchOrderDeviceIdSacRes.getDeviceId())) {
			throw new StorePlatformException("SAC_PUR_4101");
		}

		return searchOrderDeviceIdSacRes.getDeviceId();

	}

	@Override
	public SearchOrderUserByDeviceIdSacRes searchOrderUserByDeviceId(String deviceId, String orderDt) {

		SearchOrderUserByDeviceIdSacReq searchOrderUserByDeviceIdSacReq = new SearchOrderUserByDeviceIdSacReq();
		searchOrderUserByDeviceIdSacReq.setDeviceId(deviceId);
		searchOrderUserByDeviceIdSacReq.setOrderDt(orderDt);

		return this.searchUserSCI.searchOrderUserByDeviceId(searchOrderUserByDeviceIdSacReq);

	}

	@Override
	public SearchUserDeviceSacRes searchUserByDeviceKey(String tenantId, String userKey, String deviceKey) {

		SearchUserDeviceSac searchUserDeviceSac = new SearchUserDeviceSac();
		searchUserDeviceSac.setTenantId(tenantId);
		searchUserDeviceSac.setUserKey(userKey);
		searchUserDeviceSac.setDeviceKey(deviceKey);

		List<SearchUserDeviceSac> deviceList = new ArrayList<SearchUserDeviceSac>();
		deviceList.add(searchUserDeviceSac);

		SearchUserDeviceSacReq searchUserDeviceSacReq = new SearchUserDeviceSacReq();
		searchUserDeviceSacReq.setSearchUserDeviceReqList(deviceList);

		return this.searchUserSCI.searchUserByDeviceKey(searchUserDeviceSacReq);

	}

	@Override
	public CancelEcRes cancelPaymentToPayPlanet(PurchaseCancelSacParam purchaseCancelSacParam,
			PurchaseCancelDetailSacParam purchaseCancelDetailSacParam) {

		PaymentSacParam paymentSacParam = purchaseCancelDetailSacParam.getPaymentSacParamList().get(0);

		CancelEcReq cancelEcReq = new CancelEcReq();
		cancelEcReq.setToken(PayPlanetUtils.makeToken(paymentSacParam.getAuthKey(), paymentSacParam.getPrchsId(),
				String.valueOf(paymentSacParam.getTotAmt().intValue()), paymentSacParam.getMid()));
		cancelEcReq.setTid(paymentSacParam.getTid()); // StringUtils.substringBefore(paymentSacParam.getTid(), ":")
		cancelEcReq.setCdCancelReason(PurchaseConstants.PAYPLANET_PAYMENT_CANCEL_REASON_VOC);
		cancelEcReq.setUserKey(paymentSacParam.getInsdUsermbrNo()); // 2014.09.01 규격 추가

		return this.cancelSCI.cancelPayment(cancelEcReq);

	}

	@Override
	public List<PayCancelResult> cancelPaymentToTStore(PurchaseCancelSacParam purchaseCancelSacParam,
			PurchaseCancelDetailSacParam purchaseCancelDetailSacParam) {

		PrchsSacParam prchsSacParam = purchaseCancelDetailSacParam.getPrchsSacParam();

		String prodIdList = "";
		String prchsProdType = "";
		String tenantProdGrpCd = "";

		StringBuilder sb = new StringBuilder();
		if (purchaseCancelDetailSacParam.getPrchsDtlSacParamList() != null
				&& purchaseCancelDetailSacParam.getPrchsDtlSacParamList().size() > 0) {

			for (int i = 0; i < purchaseCancelDetailSacParam.getPrchsDtlSacParamList().size(); i++) {
				PrchsDtlSacParam prchsDtlSacParam = purchaseCancelDetailSacParam.getPrchsDtlSacParamList().get(i);
				if (i == 0) {
					sb.append(prchsDtlSacParam.getProdId());
					// 구매 상품 타입 조회. 단위, 권한 상품.
					if (StringUtils.equals(PurchaseConstants.PRCHS_PROD_TYPE_AUTH, prchsDtlSacParam.getPrchsProdType())) {
						// 권한 상품이면 02
						prchsProdType = "02";
					} else {
						// 그 외에(현재는 단위상품뿐이 없음.
						prchsProdType = "01";
					}

					// tenantProdGrpCd 셋팅.
					tenantProdGrpCd = prchsDtlSacParam.getTenantProdGrpCd();

				} else {
					sb.append(",").append(prchsDtlSacParam.getProdId());
				}
			}

		}

		prodIdList = sb.toString();

		PaymentCancelEcReq paymentCancelEcReq = new PaymentCancelEcReq();
		List<PaymentCancel> cancelList = new ArrayList<PaymentCancel>();
		PaymentCancel paymentCancel = new PaymentCancel();
		paymentCancel.setPrchsId(prchsSacParam.getPrchsId());
		paymentCancel.setChannel(PurchaseConstants.TSTORE_CASH_SVC_CHANNEL_SAC); // SAC 고정값.
		paymentCancel.setUserKey(prchsSacParam.getInsdUsermbrNo());
		paymentCancel.setDeviceKey(prchsSacParam.getInsdDeviceId());
		paymentCancel.setMdn(prchsSacParam.getDeviceId());
		paymentCancel.setPrchsProdType(prchsProdType);
		paymentCancel.setTenantProdGrpCd(tenantProdGrpCd);

		List<Pay> payList = new ArrayList<Pay>();
		for (PaymentSacParam paymentSacParam : purchaseCancelDetailSacParam.getPaymentSacParamList()) {
			// 참고 : TStore에서 정의되지 않은 결제일 경우 무조껀 성공값을 받는다. 단, 메시지에 정의되지 않은 결제라고 옴.
			Pay pay = new Pay();
			pay.setPayCls(paymentSacParam.getPaymentMtdCd());
			pay.setTradeId(paymentSacParam.getTid());
			pay.setOrderNo(paymentSacParam.getMoid());
			// T Store는 Integer형만 받는다. 최상훈c 결정 2014.04.14
			pay.setAmt(String.valueOf(paymentSacParam.getPaymentAmt().intValue()));

			pay.setApplyNum(paymentSacParam.getApprNo());
			pay.setApplyDate(StringUtils.substring(paymentSacParam.getPaymentDt(), 0, 8));
			pay.setApplyTime(StringUtils.substring(paymentSacParam.getPaymentDt(), 8));
			pay.setProdId(prodIdList);

			if (StringUtils.equals(PurchaseConstants.PAYMENT_METHOD_CREDIT_CARD, paymentSacParam.getPaymentMtdCd())) {
				// 신용카드 결제이면
				pay.setOrderNo(paymentSacParam.getMoid() == null ? paymentSacParam.getTid() : paymentSacParam.getMoid());
			} else if (StringUtils.equals(PurchaseConstants.PAYMENT_METHOD_DANAL, paymentSacParam.getPaymentMtdCd())) {
				// DANAL 결제이면

			} else if (StringUtils.equals(PurchaseConstants.PAYMENT_METHOD_OCB, paymentSacParam.getPaymentMtdCd())) {
				// OCB 결제이면
				pay.setPaymentTypeCd(paymentSacParam.getCpnType());
			} else if (StringUtils.equals(PurchaseConstants.PAYMENT_METHOD_DOTORI, paymentSacParam.getPaymentMtdCd())) {
				// 도토리 결제이면

			} else if (StringUtils.equals(PurchaseConstants.PAYMENT_METHOD_SKT_CARRIER,
					paymentSacParam.getPaymentMtdCd())) {
				// SKT 후불결제이면
				// 상위 레벨로 이동. //SKT 후불결제 일 때 주던 상품타입을 위에서 처리. pay.setApplyNum(prchsProdType);
			} else if (StringUtils.equals(PurchaseConstants.PAYMENT_METHOD_COUPON, paymentSacParam.getPaymentMtdCd())) {
				// 쿠폰이면
				// T Store 결제 취소 지원 안하는 결제 수단은 결제취소 요청을 안보낸다.
				continue;
			} else if (StringUtils.equals(PurchaseConstants.PAYMENT_METHOD_TSTORE_CASH,
					paymentSacParam.getPaymentMtdCd())
					|| StringUtils.equals(PurchaseConstants.PAYMENT_METHOD_TSTORE_POINT,
							paymentSacParam.getPaymentMtdCd())) {
				// T CASH 결제이면 충전/사용 취소 구분 - 01 : 충전 취소, 02 : 사용 취소. 결제 테이블에는 사용 취소만 있다.
				pay.setApplyNum("02");
			} else if (StringUtils.equals(PurchaseConstants.PAYMENT_METHOD_GIFT_RECEIVED,
					paymentSacParam.getPaymentMtdCd())) {
				// 선물수신결제
				// T Store 결제 취소 지원 안하는 결제 수단은 결제취소 요청을 안보낸다.
				continue;
			} else if (StringUtils.equals(PurchaseConstants.PAYMENT_METHOD_CULTURE, paymentSacParam.getPaymentMtdCd())) {
				// 문화상품권 결제이면
				if (StringUtils.equals(PurchaseConstants.PRCHS_REQ_PATH_WEB, prchsSacParam.getPrchsReqPathCd())) {
					pay.setApplyNum("01");
				} else if (StringUtils.equals(PurchaseConstants.PRCHS_REQ_PATH_MOBILE_CLIENT,
						prchsSacParam.getPrchsReqPathCd())) {
					pay.setApplyNum("02");
				} else {
					pay.setApplyNum("00");
				}
			} else if (StringUtils.equals(PurchaseConstants.PAYMENT_METHOD_TSTORE_GIFTCARD,
					paymentSacParam.getPaymentMtdCd())) {
				// T GiftCard 결제이면
				// T Store 결제 취소 지원 안하는 결제 수단은 결제취소 요청을 안보낸다.
				continue;
			} else if (StringUtils.equals(PurchaseConstants.PAYMENT_METHOD_FIXRATE, paymentSacParam.getPaymentMtdCd())) {
				// 정액권
				// T Store 결제 취소 지원 안하는 결제 수단은 결제취소 요청을 안보낸다.
				continue;
			} else if (StringUtils.equals(PurchaseConstants.PAYMENT_METHOD_SERIESPASS,
					paymentSacParam.getPaymentMtdCd())) {
				// 시리즈패스권
				// T Store 결제 취소 지원 안하는 결제 수단은 결제취소 요청을 안보낸다.
				continue;
			} else if (StringUtils.equals(PurchaseConstants.PAYMENT_METHOD_MOBILE_TMONEY,
					paymentSacParam.getPaymentMtdCd())) {
				// T Money
				// T Store 결제 취소 지원 안하는 결제 수단은 결제취소 요청을 안보낸다.
				continue;
			} else if (StringUtils.equals(PurchaseConstants.PAYMENT_METHOD_TMEMBERSHIP,
					paymentSacParam.getPaymentMtdCd())) {
				// T Membership 결제이면 하드코딩 - 최상훈 2014.03.26
				if (paymentSacParam.getPaymentAmt() / paymentSacParam.getTotAmt() * 100 == 50) {
					// 50퍼 할인율 고정.
					pay.setPaymentTypeCd("1002");
				} else {
					// 20퍼 할인율 고정.
					pay.setPaymentTypeCd("1001");
				}
			} else if (StringUtils.equals(PurchaseConstants.PAYMENT_METHOD_EBOOKCOMIC_OWN,
					paymentSacParam.getPaymentMtdCd())) {
				// 이북/코믹 전권 소장
				// T Store 결제 취소 지원 안하는 결제 수단은 결제취소 요청을 안보낸다.
				continue;
			} else if (StringUtils.equals(PurchaseConstants.PAYMENT_METHOD_EBOOKCOMIC_LOAN,
					paymentSacParam.getPaymentMtdCd())) {
				// 이북/코믹 전권 대여
				// T Store 결제 취소 지원 안하는 결제 수단은 결제취소 요청을 안보낸다.
				continue;
			} else if (StringUtils.equals(PurchaseConstants.PAYMENT_METHOD_B2B, paymentSacParam.getPaymentMtdCd())) {
				// 타사이트결제(B2B)
				// T Store 결제 취소 지원 안하는 결제 수단은 결제취소 요청을 안보낸다.
				continue;
			} else if (StringUtils.equals(PurchaseConstants.PAYMENT_METHOD_PAYPIN, paymentSacParam.getPaymentMtdCd())) {
				// Paypin 결제이면
				pay.setOrderNo(paymentSacParam.getMoid() == null ? paymentSacParam.getTid() : paymentSacParam.getMoid());
			} else if (StringUtils
					.equals(PurchaseConstants.PAYMENT_METHOD_SYRUP_PAY, paymentSacParam.getPaymentMtdCd())) {
				// Syrup Pay 결제이면
				pay.setOrderNo(paymentSacParam.getMoid() == null ? paymentSacParam.getTid() : paymentSacParam.getMoid());
			} else if (StringUtils.equals(PurchaseConstants.PAYMENT_METHOD_GAMECASH, paymentSacParam.getPaymentMtdCd())
					|| StringUtils.equals(PurchaseConstants.PAYMENT_METHOD_GAMECASH_POINT,
							paymentSacParam.getPaymentMtdCd())
					|| StringUtils
							.equals(PurchaseConstants.PAYMENT_METHOD_TGAMEPASS, paymentSacParam.getPaymentMtdCd())) {
				// 게임캐쉬 결제이면
				// 게임캐쉬 보너스 포인트 결제이면
				// T game pass 결제이면
				// orderNo는 규격서와 다르게 기존 값 그대로 보내기로 함.
				pay.setApplyNum("02");
			} else if (StringUtils.equals(PurchaseConstants.PAYMENT_METHOD_SHOPPING_SPECIAL_COUPON,
					paymentSacParam.getPaymentMtdCd())
					|| StringUtils.equals(PurchaseConstants.PAYMENT_METHOD_FIXRATE_REFUND,
							paymentSacParam.getPaymentMtdCd())
					|| StringUtils.equals(PurchaseConstants.PAYMENT_METHOD_SKT_TEST_DEVICE,
							paymentSacParam.getPaymentMtdCd())
					|| StringUtils.equals(PurchaseConstants.PAYMENT_METHOD_IAP_COMMERCIAL_CONVERTED,
							paymentSacParam.getPaymentMtdCd())
					|| StringUtils.equals(PurchaseConstants.PAYMENT_METHOD_EVENT_ONE_PLUS_ONE,
							paymentSacParam.getPaymentMtdCd())
					|| StringUtils.equals(PurchaseConstants.PAYMENT_METHOD_EVENT, paymentSacParam.getPaymentMtdCd())
					|| StringUtils.equals(PurchaseConstants.PAYMENT_METHOD_STORE_TEST_DEVICE,
							paymentSacParam.getPaymentMtdCd())
					|| StringUtils.equals(PurchaseConstants.PAYMENT_METHOD_FREE_PRODUCT,
							paymentSacParam.getPaymentMtdCd())) {
				// 쇼핑 특가상품 쿠폰
				// 정액권 환불
				// SKT 시험폰 결제
				// 부분유료화 정식판 전환
				// 1+1 이벤트
				// 이벤트 - 유료컨텐츠 비과금 결제
				// 테스트폰 결제
				// 무료상품
				// T Store 결제 취소 지원 안하는 결제 수단은 결제취소 요청을 안보낸다.
				continue;
			} else {
				// 정의되지 않은 결제수단은 에러다.
				throw new StorePlatformException("SAC_PUR_8132");
			}

			payList.add(pay);

		}

		if (payList.size() <= 0) {
			this.logger.info("TStore에서 지원하는 결제수단이 없음!");
			return null;
		}

		paymentCancel.setPayList(payList);

		cancelList.add(paymentCancel);
		paymentCancelEcReq.setCancelList(cancelList);

		PaymentCancelEcRes paymentCancelEcRes = this.tStorePaymentSCI.cancelPayment(paymentCancelEcReq);

		PaymentCancelResult paymentCancelResult = paymentCancelEcRes.getCancelList().get(0);

		/** 결제 취소가 하나라도 성공이 있으면 구매 취소 진행한다. 없으면 에러. */
		boolean rstFlag = false;
		StringBuilder errorSb = new StringBuilder();
		for (PayCancelResult payCancelResult : paymentCancelResult.getPayCancelList()) {
			if (StringUtils.equals(PurchaseConstants.TSTORE_PAYMENT_CANCEL_SUCCESS,
					payCancelResult.getPayCancelResultCd())) {
				rstFlag = true;
				break;
			} else {
				errorSb.append("결제수단 : ").append(payCancelResult.getPayCls()).append(", 결제취소결과 : ")
						.append(payCancelResult.getPayCancelResultCd()).append(", 결제취소결과메시지 : ")
						.append(payCancelResult.getPayCancelResultMsg());
			}
		}
		if (!rstFlag) {
			this.logger.info("SAC_PUR_8131 data : {}", paymentCancelResult);
			PurchaseErrorInfo purchaseErrorInfo = new PurchaseErrorInfo();
			purchaseErrorInfo.setErrorMsg(errorSb.toString());
			throw new StorePlatformException("SAC_PUR_8131", purchaseErrorInfo);
		}

		return paymentCancelResult.getPayCancelList();

	}

	@Override
	public PurchaseCancelDetailSacParam updatePurchaseCancel(PurchaseCancelSacParam purchaseCancelSacParam,
			PurchaseCancelDetailSacParam purchaseCancelDetailSacParam) {

		PurchaseCancelScReq purchaseCancelScReq = new PurchaseCancelScReq();

		// 결제 취소 정보를 넣어준다.
		// 구매 상품 건수 업데이트 시 특가상품 여부 확인.
		boolean specialSaleYn = false;
		boolean isSktTestMdn = false;
		List<PurchaseCancelPaymentDetailScReq> purchaseCancelPaymentDetailScReqList = new ArrayList<PurchaseCancelPaymentDetailScReq>();

		if (purchaseCancelDetailSacParam.getPaymentSacParamList() != null
				&& purchaseCancelDetailSacParam.getPaymentSacParamList().size() > 0) {

			for (PaymentSacParam paymentSacParam : purchaseCancelDetailSacParam.getPaymentSacParamList()) {

				// SKT 시험폰 여부
				if (StringUtils.equals(PurchaseConstants.PAYMENT_METHOD_SKT_TEST_DEVICE,
						paymentSacParam.getPaymentMtdCd())) {
					isSktTestMdn = true;
				}

				// 구매 상품 건수 업데이트 시 특가상품 여부 확인.
				if (StringUtils.equals(PurchaseConstants.PAYMENT_METHOD_COUPON, paymentSacParam.getPaymentMtdCd())
						&& (StringUtils.equals(PurchaseConstants.COUPON_TYPE_SHOPPING_SPECIAL_COUPON,
								paymentSacParam.getCpnType()) || StringUtils.equals(
								PurchaseConstants.COUPON_TYPE_SPECIAL_PRICE_PRODUCT, paymentSacParam.getCpnType()))) {
					specialSaleYn = true;
				}

				// 2015-01-27 결제무시라도 DB에 취소로 업데이트 처리한다.
				// 결제 취소 무시가 아니면 DB업데이트 진행.
				// if (!purchaseCancelSacParam.getIgnorePayment()) {

				this.logger.info("## purchaseCancelSacParam.IsPaymentUpdate {}",
						purchaseCancelSacParam.getIsPaymentUpdate());

				// 환불이 경우 해당 값이 false 이며 환불인 경우에는 결제테이블 업데이트 진행하지 않는다.
				if (purchaseCancelSacParam.getIsPaymentUpdate()) {

					PurchaseCancelPaymentDetailScReq purchaseCancelPaymentDetailScReq = new PurchaseCancelPaymentDetailScReq();
					purchaseCancelPaymentDetailScReq.setTenantId(purchaseCancelSacParam.getTenantId());
					purchaseCancelPaymentDetailScReq.setSystemId(purchaseCancelSacParam.getSystemId());
					purchaseCancelPaymentDetailScReq.setPrchsId(purchaseCancelDetailSacParam.getPrchsId());
					purchaseCancelPaymentDetailScReq.setPaymentDtlId(paymentSacParam.getPaymentDtlId());
					purchaseCancelPaymentDetailScReq.setPaymentStatusCd(PurchaseConstants.PRCHS_STATUS_CANCEL);

					// T Store 결제일 경우 처리
					if (purchaseCancelDetailSacParam.gettStorePayCancelResultList() != null
							&& purchaseCancelDetailSacParam.gettStorePayCancelResultList().size() > 0) {
						for (PayCancelResult payCancelResult : purchaseCancelDetailSacParam
								.gettStorePayCancelResultList()) {
							if (StringUtils.equals(payCancelResult.getPayCls(), paymentSacParam.getPaymentMtdCd())) {
								if (!StringUtils.equals(PurchaseConstants.TSTORE_PAYMENT_CANCEL_SUCCESS,
										payCancelResult.getPayCancelResultCd())) {
									// T Store 결제 취소 실패이면
									purchaseCancelPaymentDetailScReq
											.setPaymentStatusCd(PurchaseConstants.PRCHS_STATUS_PAYMENT_FAIL);
								}
								purchaseCancelPaymentDetailScReq.settStorePaymentStatusCd(payCancelResult
										.getPayCancelResultCd());
							}
						}
					}

					// Pay Planet 결제일 경우 처리 2014.09.01
					if (purchaseCancelDetailSacParam.getPayPlanetCancelEcRes() != null
							&& purchaseCancelDetailSacParam.getPayPlanetCancelEcRes().getResPaymethod().size() > 0) {
						for (CancelEcResPaymethod cancelEcResPaymethod : purchaseCancelDetailSacParam
								.getPayPlanetCancelEcRes().getResPaymethod()) {

							this.logger.info("#####  PP결제 수단 = {}{}", PaymethodUtil
									.convert2PayPlanetCodeWithoutPointCode(paymentSacParam.getPaymentMtdCd()),
									cancelEcResPaymethod.getCdPaymethod());

							if (StringUtils.equals(PaymethodUtil.convert2PayPlanetCodeWithoutPointCode(paymentSacParam
									.getPaymentMtdCd()), cancelEcResPaymethod.getCdPaymethod())) {
								if (!StringUtils.equals(PurchaseConstants.TSTORE_PAYPLANET_CANCEL_SUCCESS,
										cancelEcResPaymethod.getResultCode())) {
									// PayPlanet 결제 취소 실패이면
									purchaseCancelPaymentDetailScReq
											.setPaymentStatusCd(PurchaseConstants.PRCHS_STATUS_PAYMENT_FAIL);
								}
								purchaseCancelPaymentDetailScReq.settStorePaymentStatusCd(cancelEcResPaymethod
										.getResultCode());
							}
						}
					}

					purchaseCancelPaymentDetailScReqList.add(purchaseCancelPaymentDetailScReq);
				}

				// }

			}
		}

		// 상품 구매수 업데이트 위한 정보 셋팅.
		InsertPurchaseProductCountScReq insertPurchaseProductCountScReq = new InsertPurchaseProductCountScReq();
		List<PrchsProdCnt> prchsProdCntList = new ArrayList<PrchsProdCnt>();

		List<String> procKeyList = new ArrayList<String>();
		String tenantProdGrpCd = null;
		String procKey = null;

		for (PrchsDtlSacParam prchsDtlSacParam : purchaseCancelDetailSacParam.getPrchsDtlSacParamList()) {

			PrchsProdCnt prchsProdCnt = new PrchsProdCnt();

			// 시험폰일 경우 상품 구매수 업데이트 제외.
			if (isSktTestMdn) {
				break;
			}

			// 전권대여/소장 에피소드 상품은 상품 구매수 업데이트 제외
			if (StringUtils.startsWith(prchsDtlSacParam.getTenantProdGrpCd(),
					PurchaseConstants.TENANT_PRODUCT_GROUP_EBOOKCOMIC)
					&& !StringUtils.equals(PurchaseConstants.PRCHS_PROD_TYPE_AUTH, prchsDtlSacParam.getPrchsProdType())
					&& !StringUtils.isEmpty(prchsDtlSacParam.getUseFixrateProdId())) {
				continue;
			}

			// 1:N 선물 지원 - 중복 구매 가능한 쇼핑상품 / 부분유료화 상품 처리
			tenantProdGrpCd = prchsDtlSacParam.getTenantProdGrpCd();
			if (StringUtils.startsWith(tenantProdGrpCd, PurchaseConstants.TENANT_PRODUCT_GROUP_SHOPPING)
					|| (StringUtils.startsWith(tenantProdGrpCd, PurchaseConstants.TENANT_PRODUCT_GROUP_IAP) && StringUtils
							.equals(prchsDtlSacParam.getContentsType(), "PK0002"))) {

				if (prchsDtlSacParam.getProdQty() > 1) { // 1개 상품을 복수구매 경우
					procKey = prchsDtlSacParam.getProdId() + prchsDtlSacParam.getUseInsdDeviceId();

				} else { // 1개 상품을 1개씩 복수선물 경우 포함
					procKey = prchsDtlSacParam.getProdId() + prchsDtlSacParam.getUseInsdDeviceId()
							+ prchsDtlSacParam.getPrchsDtlId();
				}

				prchsProdCnt.setProdGrpCd(prchsDtlSacParam.getTenantProdGrpCd() + prchsDtlSacParam.getPrchsId()
						+ prchsDtlSacParam.getPrchsDtlId());

			} else {
				// 1:N 선물 지원 & 중복 구매 불가한 상품에 대한 중복 체크
				procKey = prchsDtlSacParam.getProdId() + prchsDtlSacParam.getUseInsdDeviceId();

				prchsProdCnt.setProdGrpCd(prchsDtlSacParam.getTenantProdGrpCd());
			}

			if (procKeyList.contains(procKey)) {
				continue;
			}
			procKeyList.add(procKey);

			prchsProdCnt.setTenantId(prchsDtlSacParam.getTenantId());
			prchsProdCnt.setUseUserKey(prchsDtlSacParam.getUseInsdUsermbrNo());
			prchsProdCnt.setUseDeviceKey(prchsDtlSacParam.getUseInsdDeviceId());
			prchsProdCnt.setPrchsId(prchsDtlSacParam.getPrchsId());
			prchsProdCnt.setPrchsClas(prchsDtlSacParam.getPrchsReqPathCd());
			prchsProdCnt.setProdId(prchsDtlSacParam.getProdId());
			prchsProdCnt.setProdAmt(prchsDtlSacParam.getProdAmt());
			prchsProdCnt.setProdQty(prchsDtlSacParam.getProdQty());
			prchsProdCnt.setStatusCd(PurchaseConstants.PRCHS_STATUS_CANCEL);
			prchsProdCnt.setPrchsDt(StringUtils.substring(prchsDtlSacParam.getPrchsDt(), 0, 8));
			prchsProdCnt.setSprcProdYn(specialSaleYn ? PurchaseConstants.USE_Y : PurchaseConstants.USE_N);

			prchsProdCnt.setRegId(purchaseCancelSacParam.getSystemId());
			prchsProdCnt.setUpdId(purchaseCancelSacParam.getSystemId());

			prchsProdCnt.setUseFixrateProdId(prchsDtlSacParam.getUseFixrateProdId());

			prchsProdCntList.add(prchsProdCnt);
		}
		insertPurchaseProductCountScReq.setPrchsProdCntList(prchsProdCntList);

		// 인입 된 사람의 정보를 넣어준다.
		purchaseCancelScReq.setTenantId(purchaseCancelSacParam.getTenantId());
		purchaseCancelScReq.setSystemId(purchaseCancelSacParam.getSystemId());

		// 2015.05.08 userKey추가
		// purchaseCancelScReq.setUserKey(purchaseCancelSacParam.getUserKey());

		// 구매 취소 정보를 넣어준다.
		purchaseCancelScReq.setReqUserId(purchaseCancelSacParam.getSystemId());
		purchaseCancelScReq.setPrchsId(purchaseCancelDetailSacParam.getPrchsId());
		purchaseCancelScReq.setCancelReqPathCd(purchaseCancelSacParam.getCancelReqPathCd());

		// 2014.10.06 망상취소일 경우 구매상태는 실패로 업데이트
		if (StringUtils.equals(PurchaseConstants.PRCHS_REQ_PATH_PAYMENT_ERROR_CANCEL,
				purchaseCancelSacParam.getCancelReqPathCd())) {
			purchaseCancelScReq.setPrchsStatusCd(PurchaseConstants.PRCHS_STATUS_FAIL);
		} else {
			purchaseCancelScReq.setPrchsStatusCd(PurchaseConstants.PRCHS_STATUS_CANCEL);
		}

		purchaseCancelScReq.setCurrPrchsStatusCd(PurchaseConstants.PRCHS_STATUS_COMPT);
		purchaseCancelScReq.setPurchaseCancelPaymentDetailScReqList(purchaseCancelPaymentDetailScReqList);
		purchaseCancelScReq.setInsertPurchaseProductCountScReq(insertPurchaseProductCountScReq);

		// 취소일자 셋팅을 위해 DB Sysdate 조회
		String cancelDt = this.purchaseCancelSCI.getNowDate();
		purchaseCancelScReq.setCancelDt(cancelDt);

		PurchaseCancelScRes purchaseCancelScRes = this.purchaseCancelSCI.updatePurchaseCancel(purchaseCancelScReq);

		purchaseCancelDetailSacParam.setPaymentCancelCnt(purchaseCancelScRes.getPaymentCancelCnt());
		purchaseCancelDetailSacParam.setPrchsDtlCancelCnt(purchaseCancelScRes.getPrchsDtlCancelCnt());
		purchaseCancelDetailSacParam.setPrchsCancelCnt(purchaseCancelScRes.getPrchsCancelCnt());
		purchaseCancelDetailSacParam.setPrchsProdCntCnt(purchaseCancelScRes.getPrchsProdCntCnt());
		purchaseCancelDetailSacParam.setAutoPrchsCancelCnt(purchaseCancelScRes.getAutoPrchsCancelCnt());
		purchaseCancelDetailSacParam.setCancelDt(cancelDt);

		return purchaseCancelDetailSacParam;

	}

	@Override
	public PurchaseCancelDetailSacParam updatePaymentError(PurchaseCancelSacParam purchaseCancelSacParam,
			PurchaseCancelDetailSacParam purchaseCancelDetailSacParam, String errorCode) {

		PurchaseCancelScReq purchaseCancelScReq = new PurchaseCancelScReq();

		// 인입 된 사람의 정보를 넣어준다.
		purchaseCancelScReq.setTenantId(purchaseCancelSacParam.getTenantId());
		purchaseCancelScReq.setSystemId(purchaseCancelSacParam.getSystemId());
		purchaseCancelScReq.setPrchsId(purchaseCancelDetailSacParam.getPrchsId());
		purchaseCancelScReq.setPaymentErrorCode(errorCode);

		this.purchaseCancelSCI.updatePaymentError(purchaseCancelScReq);

		return purchaseCancelDetailSacParam;
	}

	@Override
	public PurchaseCancelDetailSacParam updateSaveCancel(PurchaseCancelSacParam purchaseCancelSacParam,
			PurchaseCancelDetailSacParam purchaseCancelDetailSacParam, MembershipReserve membershipReserveRes) {

		try {

			// 적립 정보가 있을경우 처리
			if (membershipReserveRes != null) {

				this.logger.info("### MembershipReserve ProcStatusCd = " + membershipReserveRes.getProcStatusCd());

				// 처리상태가 적립예정인 경우
				if (StringUtils.equals(PurchaseConstants.MEMBERSHIP_PROC_STATUS_RESERVE,
						membershipReserveRes.getProcStatusCd())) {

					membershipReserveRes.setProcStatusCd(PurchaseConstants.MEMBERSHIP_PROC_STATUS_RESERVE_CANCEL); // 적립예정취소
					membershipReserveRes.setUpdId(purchaseCancelSacParam.getSystemId());
					this.membershipReserveSCI.updateProcStatus(membershipReserveRes);

				}
				// 처리상태가 적립완료인 경우
				else if (StringUtils.equals(PurchaseConstants.MEMBERSHIP_PROC_STATUS_COMPLETE,
						membershipReserveRes.getProcStatusCd())) {

					// 적립취소요청
					TStoreCashSaveCancelEcReq tStoreCashSaveCancelEcReq = new TStoreCashSaveCancelEcReq();
					tStoreCashSaveCancelEcReq.setUserKey(membershipReserveRes.getInsdUsermbrNo());
					tStoreCashSaveCancelEcReq.setIdentifier(membershipReserveRes.getPointId());
					tStoreCashSaveCancelEcReq.setOrderNo(membershipReserveRes.getPrchsId());

					TStoreCashSaveCancelEcRes tStoreCashSaveCancelEcRes = this.tStoreCashSCI
							.cancelSave(tStoreCashSaveCancelEcReq);

					// 적립취소 성공
					if (StringUtils.equals(PurchaseConstants.TSTORE_CASH_RESULT_CD_SUCCESS,
							tStoreCashSaveCancelEcRes.getResultCd())) {

						membershipReserveRes.setProcStatusCd(PurchaseConstants.MEMBERSHIP_PROC_STATUS_CANCEL); // 적립취소
						membershipReserveRes.setUpdId(purchaseCancelSacParam.getSystemId());
						this.membershipReserveSCI.updateProcStatus(membershipReserveRes);

					} else {

						this.logger.error("### PurchaseCancel MembershopReserve Fail : {}" + tStoreCashSaveCancelEcRes);

						// 적립취소실패
						membershipReserveRes.setProcStatusCd(PurchaseConstants.MEMBERSHIP_PROC_STATUS_FAIL); // 처리실패
						membershipReserveRes.setUpdId(purchaseCancelSacParam.getSystemId());
						this.membershipReserveSCI.updateProcStatus(membershipReserveRes);
					}
				} else {
					this.logger.error("### PurchaseCancel MembershopReserve 적립상태가 적립예정 또는 적립 완료가 아닙니다.");
				}
			}
		} catch (Exception e) {
			this.logger.info("### PurchaseCancel MembershopReserve Fail : {}" + e.toString());

			// 적립취소실패
			membershipReserveRes.setProcStatusCd(PurchaseConstants.MEMBERSHIP_PROC_STATUS_FAIL); // 처리실패
			membershipReserveRes.setUpdId(purchaseCancelSacParam.getSystemId());
			this.membershipReserveSCI.updateProcStatus(membershipReserveRes);
		}

		return purchaseCancelDetailSacParam;
	}

	@Override
	public PurchaseCancelDetailSacParam updatePurchaseCancelForPaymentError(
			PurchaseCancelSacParam purchaseCancelSacParam, PurchaseCancelDetailSacParam purchaseCancelDetailSacParam) {

		PurchaseCancelScReq purchaseCancelScReq = new PurchaseCancelScReq();

		// 결제 취소 데이터 없음. 객체만 생성해준다.
		List<PurchaseCancelPaymentDetailScReq> purchaseCancelPaymentDetailScReqList = new ArrayList<PurchaseCancelPaymentDetailScReq>();

		// 구매 상품 건수 업데이트 없음. 객체만 생성해준다.
		// 상품 구매수 업데이트 위한 정보 셋팅.
		InsertPurchaseProductCountScReq insertPurchaseProductCountScReq = new InsertPurchaseProductCountScReq();
		List<PrchsProdCnt> prchsProdCntList = new ArrayList<PrchsProdCnt>();
		insertPurchaseProductCountScReq.setPrchsProdCntList(prchsProdCntList);

		// 인입 된 사람의 정보를 넣어준다.
		purchaseCancelScReq.setTenantId(purchaseCancelSacParam.getTenantId());
		purchaseCancelScReq.setSystemId(purchaseCancelSacParam.getSystemId());
		// 구매 취소 정보를 넣어준다.
		purchaseCancelScReq.setPrchsId(purchaseCancelDetailSacParam.getPrchsId());
		purchaseCancelScReq.setCancelReqPathCd(purchaseCancelSacParam.getCancelReqPathCd());
		purchaseCancelScReq.setPrchsStatusCd(PurchaseConstants.PRCHS_STATUS_FAIL);
		purchaseCancelScReq.setCurrPrchsStatusCd(PurchaseConstants.PRCHS_STATUS_RESERVATION);
		purchaseCancelScReq.setPurchaseCancelPaymentDetailScReqList(purchaseCancelPaymentDetailScReqList);
		purchaseCancelScReq.setInsertPurchaseProductCountScReq(insertPurchaseProductCountScReq);

		// 취소일자 셋팅을 위해 DB Sysdate 조회
		String cancelDt = this.purchaseCancelSCI.getNowDate();
		purchaseCancelScReq.setCancelDt(cancelDt);

		PurchaseCancelScRes purchaseCancelScRes = this.purchaseCancelSCI.updatePurchaseCancel(purchaseCancelScReq);

		purchaseCancelDetailSacParam.setPaymentCancelCnt(purchaseCancelScRes.getPaymentCancelCnt());
		purchaseCancelDetailSacParam.setPrchsDtlCancelCnt(purchaseCancelScRes.getPrchsDtlCancelCnt());
		purchaseCancelDetailSacParam.setPrchsCancelCnt(purchaseCancelScRes.getPrchsCancelCnt());
		purchaseCancelDetailSacParam.setPrchsProdCntCnt(purchaseCancelScRes.getPrchsProdCntCnt());

		if (purchaseCancelDetailSacParam.getPrchsDtlCancelCnt() < 1) {
			throw new StorePlatformException("SAC_PUR_8103");
		}

		return purchaseCancelDetailSacParam;

	}

	@Override
	public String aomPush(String deviceId, String appId) {

		AomSendEcReq aomSendEcReq = new AomSendEcReq();

		aomSendEcReq.setDeviceId(deviceId);
		aomSendEcReq.setClientType("AM000102");
		aomSendEcReq.setSvcType("AM000205");
		aomSendEcReq.setProdType("AM000301");
		aomSendEcReq.setMsg("KR_ARM_TS/A4/" + deviceId + "/" + appId);
		aomSendEcReq.setAomStat("AM000401");

		AomSendEcRes aomSendEcRes = this.messageSCI.aomSend(aomSendEcReq);

		return aomSendEcRes.getResultStatus();

	}

	@Override
	public String armRemoveLicense(String deviceId, String appId) {

		RemoveLicenseEcReq removeLicenseEcReq = new RemoveLicenseEcReq();

		removeLicenseEcReq.setAppId(appId);
		removeLicenseEcReq.setMdn(deviceId);
		RemoveLicenseEcRes removeLicenseEcRes = this.armSCI.removeLicense(removeLicenseEcReq);

		return removeLicenseEcRes.getResultCd();

	}

	/**
	 * <pre>
	 * SC Prchs VO에서 SAC PrchsSacParam으로 변환.
	 * </pre>
	 * 
	 * @param prchs
	 *            prchs
	 * @return PrchsSacParam
	 */
	private PrchsSacParam convertResForPrchsSacParam(Prchs prchs) {

		PrchsSacParam prchsSacParam = new PrchsSacParam();

		if (prchs == null) {
			return prchsSacParam;
		}

		prchsSacParam.setTenantId(prchs.getTenantId());
		prchsSacParam.setPrchsId(prchs.getPrchsId());
		prchsSacParam.setInsdUsermbrNo(prchs.getInsdUsermbrNo());
		prchsSacParam.setInsdDeviceId(prchs.getInsdDeviceId());
		prchsSacParam.setPrchsDt(prchs.getPrchsDt());
		prchsSacParam.setStatusCd(prchs.getStatusCd());
		prchsSacParam.setTotAmt(prchs.getTotAmt());
		prchsSacParam.setPrchsReqPathCd(prchs.getPrchsReqPathCd());
		prchsSacParam.setCancelReqPathCd(prchs.getCancelReqPathCd());
		prchsSacParam.setCancelDt(prchs.getCancelDt());

		return prchsSacParam;

	}

	/**
	 * <pre>
	 * SC PrchsDtl VO에서 SAC PrchsDtlSacParam으로 변환.
	 * </pre>
	 * 
	 * @param prchsDtl
	 *            prchsDtl
	 * @return PrchsDtlSacParam
	 */
	private PrchsDtlSacParam converResForPrchsDtlSacParam(PrchsDtl prchsDtl) {

		PrchsDtlSacParam prchsDtlSacParam = new PrchsDtlSacParam();

		if (prchsDtl == null) {
			return prchsDtlSacParam;
		}

		prchsDtlSacParam.setTenantId(prchsDtl.getTenantId());
		prchsDtlSacParam.setPrchsId(prchsDtl.getPrchsId());
		prchsDtlSacParam.setPrchsDtlId(prchsDtl.getPrchsDtlId());
		prchsDtlSacParam.setUseTenantId(prchsDtl.getUseTenantId());
		prchsDtlSacParam.setUseInsdUsermbrNo(prchsDtl.getUseInsdUsermbrNo());
		prchsDtlSacParam.setUseInsdDeviceId(prchsDtl.getUseInsdDeviceId());
		prchsDtlSacParam.setPrchsDt(prchsDtl.getPrchsDt());
		prchsDtlSacParam.setTotAmt(prchsDtl.getTotAmt());
		prchsDtlSacParam.setSendInsdUsermbrNo(prchsDtl.getSendInsdUsermbrNo());
		prchsDtlSacParam.setSendInsdDeviceId(prchsDtl.getSendInsdDeviceId());
		prchsDtlSacParam.setRecvDt(prchsDtl.getRecvDt());
		prchsDtlSacParam.setRecvConfPathCd(prchsDtl.getRecvConfPathCd());
		prchsDtlSacParam.setProdId(prchsDtl.getProdId());
		prchsDtlSacParam.setProdAmt(prchsDtl.getProdAmt());
		prchsDtlSacParam.setProdQty(prchsDtl.getProdQty());
		prchsDtlSacParam.setTenantProdGrpCd(prchsDtl.getTenantProdGrpCd());
		prchsDtlSacParam.setStatusCd(prchsDtl.getStatusCd());
		prchsDtlSacParam.setUseStartDt(prchsDtl.getUseStartDt());
		prchsDtlSacParam.setUseExprDt(prchsDtl.getUseExprDt());
		prchsDtlSacParam.setCancelReqPathCd(prchsDtl.getCancelReqPathCd());
		prchsDtlSacParam.setCancelDt(prchsDtl.getCancelDt());
		prchsDtlSacParam.setCpnPublishCd(prchsDtl.getCpnPublishCd());
		prchsDtlSacParam.setCpnDlvUrl(prchsDtl.getCpnDlvUrl());
		prchsDtlSacParam.setCpnAddInfo(prchsDtl.getCpnAddInfo());
		prchsDtlSacParam.setCpnBizOrderNo(prchsDtl.getCpnBizOrderNo());
		prchsDtlSacParam.setCpnBizProdSeq(prchsDtl.getCpnBizProdSeq());
		prchsDtlSacParam.setPrchsCaseCd(prchsDtl.getPrchsCaseCd());
		prchsDtlSacParam.setPrchsProdType(prchsDtl.getPrchsProdType());
		prchsDtlSacParam.setUseFixrateProdId(prchsDtl.getUseFixrateProdId());
		prchsDtlSacParam.setPrchsResvDesc(prchsDtl.getPrchsResvDesc());
		prchsDtlSacParam.setPrchsReqPathCd(prchsDtl.getPrchsReqPathCd());
		prchsDtlSacParam.setResvCol03(prchsDtl.getResvCol03());
		prchsDtlSacParam.setCouponCmsPrchsId(prchsDtl.getCouponCmsPrchsId());
		prchsDtlSacParam.setContentsType(prchsDtl.getContentsType()); // IAP 상품타입

		return prchsDtlSacParam;

	}

	/**
	 * <pre>
	 * SC Payment VO에서 SAC PaymentSacParam으로 변환.
	 * </pre>
	 * 
	 * @param payment
	 *            payment
	 * @return PaymentSacParam
	 */
	private PaymentSacParam convertResForPaymentSacParam(Payment payment) {

		PaymentSacParam paymentSacParam = new PaymentSacParam();

		paymentSacParam.setTenantId(payment.getTenantId());
		paymentSacParam.setPrchsId(payment.getPrchsId());
		paymentSacParam.setPaymentDtlId(payment.getPaymentDtlId());
		paymentSacParam.setInsdUsermbrNo(payment.getInsdUsermbrNo());
		paymentSacParam.setInsdDeviceId(payment.getInsdDeviceId());
		paymentSacParam.setPrchsDt(payment.getPrchsDt());
		paymentSacParam.setTotAmt(payment.getTotAmt());
		paymentSacParam.setPaymentMtdCd(payment.getPaymentMtdCd());
		paymentSacParam.setStatusCd(payment.getStatusCd());
		paymentSacParam.setPaymentDt(payment.getPaymentDt());
		paymentSacParam.setPaymentAmt(payment.getPaymentAmt());
		paymentSacParam.setPaymentCancelDt(payment.getPaymentCancelDt());
		// TID의 경우 OGG동기화 이슈로 인해 붙인 :SYS_GUID를 잘라서 사용한다.
		paymentSacParam.setTid(StringUtils.substringBefore(payment.getTid(), ":"));
		paymentSacParam.setResvCol01(payment.getResvCol01());
		paymentSacParam.setResvCol02(payment.getResvCol02());
		paymentSacParam.setResvCol03(payment.getResvCol03());
		paymentSacParam.setResvCol04(payment.getResvCol04());
		paymentSacParam.setResvCol05(payment.getResvCol05());
		paymentSacParam.setApprNo(payment.getApprNo());
		paymentSacParam.setBillKey(payment.getBillKey());
		paymentSacParam.setCpnId(payment.getCpnId());
		paymentSacParam.setCpnType(payment.getCpnType());
		paymentSacParam.setMoid(payment.getMoid());

		return paymentSacParam;

	}

	@Override
	public void cancelTCash(PurchaseCancelSacParam purchaseCancelSacParam,
			PurchaseCancelDetailSacParam purchaseCancelDetailSacParam) {

		if (purchaseCancelDetailSacParam.getPaymentSacParamList() == null
				|| purchaseCancelDetailSacParam.getPaymentSacParamList().size() < 1) {
			return;
		}
		PaymentSacParam paymentSacParam = purchaseCancelDetailSacParam.getPaymentSacParamList().get(0);

		TStoreCashChargeCancelEcReq tStoreCashChargeCancelEcReq = new TStoreCashChargeCancelEcReq();
		List<TStoreCashChargeCancelDetailEcReq> cashList = new ArrayList<TStoreCashChargeCancelDetailEcReq>();
		TStoreCashChargeCancelDetailEcReq tStoreCashChargeCancelDetailEcReq = new TStoreCashChargeCancelDetailEcReq();

		// tStoreCashChargeCancelDetailEcReq.setIdentifier(identifier);
		tStoreCashChargeCancelDetailEcReq.setCashCls(PurchaseConstants.TSTORE_CASH_CLASS_CASH);
		tStoreCashChargeCancelDetailEcReq.setOrderNo(paymentSacParam.getPrchsId());
		tStoreCashChargeCancelDetailEcReq.setProductGroup(PurchaseConstants.TSTORE_CASH_PRODUCT_GROUP_ALL);
		cashList.add(tStoreCashChargeCancelDetailEcReq);

		tStoreCashChargeCancelEcReq.setUserKey(paymentSacParam.getInsdUsermbrNo());
		tStoreCashChargeCancelEcReq.setCashList(cashList);

		this.tStoreCashSCI.cancelCharge(tStoreCashChargeCancelEcReq);

	}

	@Override
	public TStoreCashChargeCancelEcRes cancelTCashCharge(TStoreCashChargeCancelEcReq tStoreCashChargeCancelEcReq) {
		return this.tStoreCashSCI.cancelCharge(tStoreCashChargeCancelEcReq);
	}

	@Override
	public TStoreCashRefundEcRes refundTCash(TStoreCashRefundEcReq tStoreCashRefundEcReq) {
		return this.tStoreCashSCI.refund(tStoreCashRefundEcReq);
	}

	@Override
	public String getAutoPrchsStatus(PurchaseCancelSacParam purchaseCancelSacParam, PrchsDtlSacParam prchsDtlSacParam) {

		AutoPaymentScReq autoPaymentScReq = new AutoPaymentScReq();
		autoPaymentScReq.setTenantId(prchsDtlSacParam.getUseTenantId());
		autoPaymentScReq.setSystemId(purchaseCancelSacParam.getSystemId());
		autoPaymentScReq.setUserKey(prchsDtlSacParam.getUseInsdUsermbrNo());
		autoPaymentScReq.setPrchsId(prchsDtlSacParam.getPrchsId());
		autoPaymentScReq.setPrchsDtlId(prchsDtlSacParam.getPrchsDtlId());

		AutoPaymentScRes autoPaymentScRes = this.purchaseCancelSCI.getAutoPaymentInfo(autoPaymentScReq);
		if (autoPaymentScRes == null || autoPaymentScRes.getAutoPrchs() == null) {
			return "";
		}
		return autoPaymentScRes.getAutoPrchs().getAutoPaymentStatusCd();
	}

	@Override
	public SmsSendEcRes sendSms(String recvMdn, String msg) {

		SmsSendEcReq smsSendEcReq = new SmsSendEcReq();

		smsSendEcReq.setSendMdn(PurchaseConstants.TSTORE_CALL_CENTER_MDN);
		smsSendEcReq.setRecvMdn(recvMdn);
		smsSendEcReq.setMsg(msg);

		smsSendEcReq.setTeleSvcId("0"); // 텔레서비스ID(0:단문SMS)
		smsSendEcReq.setCarrier("");
		smsSendEcReq.setSrcId("US004530"); // 구매결제취소

		return this.messageSCI.smsSend(smsSendEcReq);
	}

	/**
	 * <pre>
	 * 마일리지 적립 정보 조회
	 * </pre>
	 * 
	 * @param prchsSacParam
	 *            prchsSacParam
	 * @return PurchaseCancelDetailSacParam
	 */
	@Override
	public MembershipReserve getMembershipReserve(PrchsSacParam prchsSacParam) {
		MembershipReserve membershipReserveReq = new MembershipReserve();
		membershipReserveReq.setTenantId(prchsSacParam.getTenantId());
		membershipReserveReq.setPrchsId(prchsSacParam.getPrchsId());
		membershipReserveReq.setStatusCd(PurchaseConstants.PRCHS_STATUS_COMPT);

		return this.membershipReserveSCI.getSaveInfo(membershipReserveReq);
	}

	/**
	 * <pre>
	 * 특가쿠폰 사용 여부.
	 * </pre>
	 * 
	 * @param tenantId
	 *            테넌트 ID
	 * @param prchsId
	 *            구매 ID
	 * @return 특가쿠폰 사용 여부
	 */
	@Override
	public boolean useSpecialCoupon(String tenantId, String prchsId) {
		PurchaseScReq req = new PurchaseScReq();
		req.setTenantId(tenantId);
		req.setPrchsId(prchsId);

		return this.purchaseCancelSCI.useSpecialCoupon(req);
	}

	/**
	 * 
	 * <pre>
	 * T Cash 특정 충전내역 환불을 호출한다.
	 * </pre>
	 * 
	 * @param tStoreCashChargeCancelEcReq
	 *            tStoreCashChargeCancelEcReq
	 * @return TStoreCashChargeCancelEcRes
	 */
	@Override
	public TStoreCashSpecificRefundEcRes specificRefundTCash(TStoreCashSpecificRefundEcReq tStoreCashSpecificRefundEcReq) {
		return this.tStoreCashSCI.specificRefund(tStoreCashSpecificRefundEcReq);
	}

}
