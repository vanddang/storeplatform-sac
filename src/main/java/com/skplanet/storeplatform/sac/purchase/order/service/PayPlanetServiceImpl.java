/*
 * Copyright (c) 2013 SK planet.
 * All right reserved.
 *
 * This software is the confidential and proprietary information of SK planet.
 * You shall not disclose such Confidential Information and
 * shall use it only in accordance with the terms of the license agreement
 * you entered into with SK planet.
 */
package com.skplanet.storeplatform.sac.purchase.order.service;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.context.support.MessageSourceAccessor;
import org.springframework.stereotype.Service;

import com.skplanet.storeplatform.framework.core.exception.StorePlatformException;
import com.skplanet.storeplatform.framework.core.exception.vo.ErrorInfo;
import com.skplanet.storeplatform.sac.client.purchase.vo.order.NotifyPaymentSacReq;
import com.skplanet.storeplatform.sac.client.purchase.vo.order.PPNotifyPaymentSacReq;
import com.skplanet.storeplatform.sac.client.purchase.vo.order.PaymentInfo;
import com.skplanet.storeplatform.sac.purchase.order.constant.PayPlanetConstant;

/**
 * 
 * 
 * Pay Planet -> SAC : 구매인증 요청, 결제처리 결과 알림 처리 서비스 구현
 * 
 * Updated on : 2014. 4. 9. Updated by : 황민규, SK플래닛.
 */
@Service
public class PayPlanetServiceImpl implements PayPlanetService {

	@Autowired
	private MessageSourceAccessor messageSourceAccessor;

	/**
	 *
	 * <pre>
	 * 결제 처리 결과 Noti.
	 * </pre>
	 *
	 * @param tenantId
	 *            테넌트 ID
	 * @param ppNotifyPaymentSacReq
	 *            결제 결과 정보
	 * @return 결제 결과 처리 응답
	 */
	@Override
	public NotifyPaymentSacReq notifyPayment(String tenantId, PPNotifyPaymentSacReq ppNotifyPaymentSacReq) {

		// P/P에서 포인트 구분을 하지 않음에 따른 캐쉬/포인트 구분 작업
		if (StringUtils.isNotBlank(ppNotifyPaymentSacReq.getInfoAmtPurchaseSub())) {

			StringBuffer sbPaymentInfo = new StringBuffer();

			String[] arPayment = null;

			for (String payInfo : ppNotifyPaymentSacReq.getInfoAmtPurchaseSub().split(";")) {
				arPayment = payInfo.split(":", 3);

				if (sbPaymentInfo.length() > 0) {
					sbPaymentInfo.append(";");
				}

				if (StringUtils.equals(arPayment[1], "00")) {
					sbPaymentInfo.append(arPayment[0]).append(":").append(arPayment[2]);

				} else {
					if (StringUtils.equals(arPayment[1], PayPlanetConstant.TSTORE_CASH_CLASS_POINT)) {
						if (StringUtils.equals(arPayment[0], PayPlanetConstant.PAYPLANET_PAYMENT_METHOD_TSTORE_CASH)) {
							sbPaymentInfo.append(PayPlanetConstant.PAYPLANET_PAYMENT_METHOD_TSTORE_POINT).append(":")
									.append(arPayment[2]);
						} else if (StringUtils
								.equals(arPayment[0], PayPlanetConstant.PAYPLANET_PAYMENT_METHOD_GAMECASH)) {
							sbPaymentInfo.append(PayPlanetConstant.PAYPLANET_PAYMENT_METHOD_GAMECASH_POINT).append(":")
									.append(arPayment[2]);
						} else {
							sbPaymentInfo.append(arPayment[0]).append(":").append(arPayment[2]);
						}

					} else if (StringUtils.equals(arPayment[1], PayPlanetConstant.TSTORE_CASH_CLASS_CASH)) {
						sbPaymentInfo.append(arPayment[0]).append(":").append(arPayment[2]);

					} else {
						ErrorInfo errorInfo = new ErrorInfo();
						errorInfo.setCode("EC_PUR_5301");
						errorInfo.setMessage(this.messageSourceAccessor.getMessage("EC_PUR_5301",
								new String[] { arPayment[1] }, LocaleContextHolder.getLocale()));
						throw new StorePlatformException(errorInfo);
					}
				}
			}

			ppNotifyPaymentSacReq.setInfoAmtPurchase(sbPaymentInfo.toString());
		}

		// SAC 요청 개체 세팅
		List<PaymentInfo> paymentInfoList = new ArrayList<PaymentInfo>();

		if (StringUtils.isNotBlank(ppNotifyPaymentSacReq.getInfoAmtPurchase())) {

			PaymentInfo paymentInfo = null;
			String[] arPayment = null;
			String[] arCoupon = null;

			for (String payInfo : ppNotifyPaymentSacReq.getInfoAmtPurchase().split(";")) {
				arPayment = payInfo.split(":", 2);

				paymentInfo = new PaymentInfo();
				paymentInfo.setTid(ppNotifyPaymentSacReq.getTid());
				paymentInfo.setPaymentMtdCd(arPayment[0]);
				paymentInfo.setPaymentAmt(Double.parseDouble(arPayment[1]));

				if (StringUtils.equals(arPayment[0], PayPlanetConstant.PAYPLANET_PAYMENT_METHOD_SKT_CARRIER)) {
					paymentInfo.setSktTestDeviceYn(ppNotifyPaymentSacReq.getFlgSktTestDevice());
					paymentInfo.setMoid(ppNotifyPaymentSacReq.getSvcMangNo()); // SKT 서비스 관리 번호
					paymentInfo.setMnoCd(ppNotifyPaymentSacReq.getPaymentTelecomCd()); // DCB 결제 통신사 코드
					paymentInfo.setLimitMemberYn(ppNotifyPaymentSacReq.getFlgLimitUser()); // 한도가입자 여부
				}

				paymentInfo.setBillKey(ppNotifyPaymentSacReq.getBillingInfo());

				// 쿠폰번호:쿠폰명:쿠폰금액:쿠폰생성주체:쿠폰타입
				if (StringUtils.equals(arPayment[0], PayPlanetConstant.PAYPLANET_PAYMENT_METHOD_COUPON)) {
					arCoupon = ppNotifyPaymentSacReq.getCouponInfo().split(":", 5);
					paymentInfo.setCpnId(arCoupon[0]);
					paymentInfo.setCpnType(arCoupon[4]);
					paymentInfo.setCpnMakeHost(arCoupon[3]);
				}

				paymentInfoList.add(paymentInfo);
			}
		}

		NotifyPaymentSacReq notifyPaymentSacReq = new NotifyPaymentSacReq();
		notifyPaymentSacReq.setResultCd(ppNotifyPaymentSacReq.getCode());
		notifyPaymentSacReq.setResultMsg(ppNotifyPaymentSacReq.getMsg());
		notifyPaymentSacReq.setPrchsId(StringUtils.defaultIfBlank(ppNotifyPaymentSacReq.getOrderId(),
				ppNotifyPaymentSacReq.getPrchsId()));
		notifyPaymentSacReq.setUserKey(ppNotifyPaymentSacReq.getUserKey());
		notifyPaymentSacReq.setProdId(ppNotifyPaymentSacReq.getProdId());
		notifyPaymentSacReq.setTotAmt(Double.parseDouble(ppNotifyPaymentSacReq.getAmtPurchase()));
		notifyPaymentSacReq.setMctSpareParam(ppNotifyPaymentSacReq.getMctSpareParam());
		notifyPaymentSacReq.setOfferingYn(ppNotifyPaymentSacReq.getOfferingYn());
		notifyPaymentSacReq.setTargetPaymentAmt(ppNotifyPaymentSacReq.getTargetPaymentAmt());
		notifyPaymentSacReq.setSaveExpectAmt(ppNotifyPaymentSacReq.getSaveExpectAmt());
		notifyPaymentSacReq.setSaveResultAmt(ppNotifyPaymentSacReq.getSaveResultAmt());
		notifyPaymentSacReq.setProcSubStatusCd(ppNotifyPaymentSacReq.getProcSubStatusCd());
		notifyPaymentSacReq.setPaymentInfoList(paymentInfoList);

		return notifyPaymentSacReq;
	}
}
