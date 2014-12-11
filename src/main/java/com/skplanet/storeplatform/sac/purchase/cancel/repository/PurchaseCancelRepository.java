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

import java.util.List;

import com.skplanet.storeplatform.external.client.message.vo.SmsSendEcRes;
import com.skplanet.storeplatform.external.client.payplanet.vo.CancelEcRes;
import com.skplanet.storeplatform.external.client.tstore.vo.PayCancelResult;
import com.skplanet.storeplatform.external.client.tstore.vo.TStoreCashChargeCancelEcReq;
import com.skplanet.storeplatform.external.client.tstore.vo.TStoreCashChargeCancelEcRes;
import com.skplanet.storeplatform.external.client.tstore.vo.TStoreCashRefundEcReq;
import com.skplanet.storeplatform.external.client.tstore.vo.TStoreCashRefundEcRes;
import com.skplanet.storeplatform.purchase.client.common.vo.MembershipReserve;
import com.skplanet.storeplatform.sac.client.internal.member.user.vo.SearchOrderUserByDeviceIdSacRes;
import com.skplanet.storeplatform.sac.purchase.cancel.vo.PrchsDtlSacParam;
import com.skplanet.storeplatform.sac.purchase.cancel.vo.PrchsSacParam;
import com.skplanet.storeplatform.sac.purchase.cancel.vo.PurchaseCancelDetailSacParam;
import com.skplanet.storeplatform.sac.purchase.cancel.vo.PurchaseCancelSacParam;

/**
 * 구매 취소 repository Interface.
 * 
 * Updated on : 2014. 1. 16. Updated by : nTels_cswoo81, nTels.
 */
public interface PurchaseCancelRepository {

	/**
	 * <pre>
	 * 구매 취소 할 구매 상세 정보 조회.
	 * </pre>
	 * 
	 * @param purchaseCancelSacParam
	 *            purchaseCancelSacParam
	 * @param purchaseCancelDetailSacParam
	 *            purchaseCancelDetailSacParam
	 * @return PurchaseCancelDetailSacParam
	 */
	PurchaseCancelDetailSacParam setPurchaseDetailInfo(PurchaseCancelSacParam purchaseCancelSacParam,
			PurchaseCancelDetailSacParam purchaseCancelDetailSacParam);

	/**
	 * <pre>
	 * deviceId를 조회해온다.
	 * </pre>
	 * 
	 * @param userKey
	 *            userKey
	 * @param deviceKey
	 *            deviceKey
	 * @return deviceId
	 */
	String getDeviceId(String userKey, String deviceKey);

	/**
	 * <pre>
	 * deviceId를 조회해온다.(탈퇴회원포함)
	 * </pre>
	 * 
	 * @param userKey
	 *            userKey
	 * @param deviceKey
	 *            deviceKey
	 * @return deviceId
	 */
	String searchOrderDeviceId(String tenantId, String userKey, String deviceKey);

	/**
	 * <pre>
	 * deviceId로 useKey, deviceKey를 조회해온다.
	 * </pre>
	 * 
	 * @param deviceId
	 *            deviceId
	 * @param orderDt
	 *            orderDt
	 * @return SearchOrderUserByDeviceIdSacRes
	 */
	SearchOrderUserByDeviceIdSacRes searchOrderUserByDeviceId(String deviceId, String orderDt);

	/**
	 * 
	 * <pre>
	 * PayPlanet으로 결제 취소를 요청한다.
	 * </pre>
	 * 
	 * @param purchaseCancelSacParam
	 *            purchaseCancelSacParam
	 * @param purchaseCancelDetailSacParam
	 *            purchaseCancelSacParam
	 * @return CancelEcRes
	 */
	CancelEcRes cancelPaymentToPayPlanet(PurchaseCancelSacParam purchaseCancelSacParam,
			PurchaseCancelDetailSacParam purchaseCancelDetailSacParam);

	/**
	 * 
	 * <pre>
	 * T Store로 결제 취소를 요청한다.
	 * </pre>
	 * 
	 * @param purchaseCancelSacParam
	 *            purchaseCancelSacParam
	 * @param purchaseCancelDetailSacParam
	 *            purchaseCancelDetailSacParam
	 * @return List<PayCancelResult>
	 */
	List<PayCancelResult> cancelPaymentToTStore(PurchaseCancelSacParam purchaseCancelSacParam,
			PurchaseCancelDetailSacParam purchaseCancelDetailSacParam);

	/**
	 * <pre>
	 * 구매 취소 처리 DB 업데이트.
	 * </pre>
	 * 
	 * @param purchaseCancelSacParam
	 *            purchaseCancelSacParam
	 * @param purchaseCancelDetailSacParam
	 *            purchaseCancelDetailSacParam
	 * @return PurchaseCancelDetailSacParam
	 */
	PurchaseCancelDetailSacParam updatePurchaseCancel(PurchaseCancelSacParam purchaseCancelSacParam,
			PurchaseCancelDetailSacParam purchaseCancelDetailSacParam);

	/**
	 * <pre>
	 * T마일리지 적립 취소 처리 DB 업데이트.
	 * </pre>
	 * 
	 * @param purchaseCancelSacParam
	 *            purchaseCancelSacParam
	 * @param purchaseCancelDetailSacParam
	 *            purchaseCancelDetailSacParam
	 * @return PurchaseCancelDetailSacParam
	 */
	PurchaseCancelDetailSacParam updateSaveCancel(PurchaseCancelSacParam purchaseCancelSacParam,
			PurchaseCancelDetailSacParam purchaseCancelDetailSacParam, MembershipReserve membershipReserveResult);

	/**
	 * 
	 * <pre>
	 * 망에러 구매취소 일 때 구매 DB 업데이트.
	 * </pre>
	 * 
	 * @param purchaseCancelSacParam
	 *            purchaseCancelSacParam
	 * @param purchaseCancelDetailSacParam
	 *            purchaseCancelDetailSacParam
	 * @return PurchaseCancelDetailSacParam
	 */
	PurchaseCancelDetailSacParam updatePurchaseCancelForPaymentError(PurchaseCancelSacParam purchaseCancelSacParam,
			PurchaseCancelDetailSacParam purchaseCancelDetailSacParam);

	/**
	 * 
	 * <pre>
	 * 구매 취소 시 Aom Message Push.
	 * </pre>
	 * 
	 * @param prchsProdDtl
	 *            prchsProdDtl
	 * @return String
	 */
	String aomPush(String deviceId, String appId);

	/**
	 * <pre>
	 * 구매 취소 시 ARM 서버로 라이센스 삭제 요청.
	 * </pre>
	 * 
	 * @param deviceId
	 *            deviceId
	 * @param appId
	 *            appId
	 * @return String
	 */
	String armRemoveLicense(String deviceId, String appId);

	/**
	 * 
	 * <pre>
	 * T Cash 취소를 호출한다.
	 * </pre>
	 * 
	 * @param purchaseCancelSacParam
	 *            purchaseCancelSacParam
	 * @param purchaseCancelDetailSacParam
	 *            purchaseCancelDetailSacParam
	 */
	void cancelTCash(PurchaseCancelSacParam purchaseCancelSacParam,
			PurchaseCancelDetailSacParam purchaseCancelDetailSacParam);

	/**
	 * 
	 * <pre>
	 * T Cash 충전 취소를 호출한다.
	 * </pre>
	 * 
	 * @param tStoreCashChargeCancelEcReq
	 *            tStoreCashChargeCancelEcReq
	 * @return TStoreCashChargeCancelEcRes
	 */
	TStoreCashChargeCancelEcRes cancelTCashCharge(TStoreCashChargeCancelEcReq tStoreCashChargeCancelEcReq);

	/**
	 * 
	 * <pre>
	 * T Cash 환불을 호출한다.
	 * </pre>
	 * 
	 * @param tStoreCashRefundEcReq
	 *            tStoreCashRefundEcReq
	 * @return TStoreCashRefundEcRes
	 */
	TStoreCashRefundEcRes refundTCash(TStoreCashRefundEcReq tStoreCashRefundEcReq);

	/**
	 * 
	 * <pre>
	 * method 설명.
	 * </pre>
	 * 
	 * @param purchaseCancelSacParam
	 *            purchaseCancelSacParam
	 * @param prchsDtlSacParam
	 *            prchsDtlSacParam
	 * @return String
	 */
	String getAutoPrchsStatus(PurchaseCancelSacParam purchaseCancelSacParam, PrchsDtlSacParam prchsDtlSacParam);

	/**
	 * 
	 * <pre>
	 * SMS발송.
	 * </pre>
	 * 
	 * @param sendMdn
	 *            보낸사람
	 * @param recvMdn
	 *            받는사람
	 * @param msg
	 *            메세지
	 * @return SmsSendEcRes
	 */
	SmsSendEcRes sendSms(String recvMdn, String msg);

	/**
	 * <pre>
	 * 마일리지 적립 정보 조회
	 * </pre>
	 * 
	 * @param prchsSacParam
	 *            prchsSacParam
	 * @return PurchaseCancelDetailSacParam
	 */
	MembershipReserve getMembershipReserve(PrchsSacParam prchsSacParam);

}
