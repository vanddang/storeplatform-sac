/*
 * Copyright (c) 2013 SK planet.
 * All right reserved.
 *
 * This software is the confidential and proprietary information of SK planet.
 * You shall not disclose such Confidential Information and
 * shall use it only in accordance with the terms of the license agreement
 * you entered into with SK planet.
 */

package com.skplanet.storeplatform.sac.purchase.order.constant;

public class PayPlanetConstant {
	/* SAC I/F ID */
	public static final String SAC_INTERFACE_ID_VERIFY_ORDER = "I02000003";
	public static final String SAC_INTERFACE_ID_NOTIFY_PAYMENT = "I02000014";
	public static final String SAC_INTERFACE_ID_CREATE_PURCHASE = "I02000001";
	public static final String SAC_INTERFACE_ID_CREATE_COMPLETE_PURCHASE = "I02000031";
	public static final String SAC_INTERFACE_ID_CANCEL_PURCHASE = "I02000030";
	public static final String SAC_INTERFACE_ID_CANCEL_PURCHASE_INAPP = "I02000032";
	public static final String SAC_INTERFACE_ID_HISTORY_PURCHASE = "I02000004";
	public static final String SAC_INTERFACE_ID_EXISTENCE_PURCHASE = "I02000006";
	public static final String SAC_INTERFACE_ID_MILEAGE_PURCHASE = "I02000035";
	public static final String SAC_INTERFACE_ID_AUTOPAYMENT_CANCEL = "I02000008";
	public static final String SAC_INTERFACE_ID_SAP_PURCHASE_TRANSFER = "I02000040";

	/* 구매요청 경로 */
	public static final String PRCHS_REQ_PATH_IAP = "OR000407"; // 부분유료화
	public static final String PRCHS_REQ_PATH_IAP_COMMERCIAL_CONVERTED = "OR000408"; // 부분유료화 정식판전환
	public static final String PRCHS_REQ_PATH_IAP_AUTO_PAYMENT_OLD = "OR000447";
	public static final String PRCHS_REQ_PATH_IAP_AUTO_PAYMENT = "OR000452";

	/* 구매유형 */
	public static final String PRCHS_CASE_PURCHASE_CD = "OR020301"; // 구매

	/* 판매금액 처리 타입 */
	public static final String SALE_AMT_PROC_TYPE_SERVER = "OR020502"; // 서버 기준 처리 (요청 금액 무시)

	/* Pay Planet TID prefix */
	public static final String PAYPLANET_TID_PREFIX = "SKTstore"; // Pay Planet TID prefix

	/* Pay Planet 결제 수단 코드 */
	public static final String PAYPLANET_PAYMENT_METHOD_SKT_CARRIER = "11"; // SKT 후불
	public static final String PAYPLANET_PAYMENT_METHOD_DANAL = "12"; // 다날
	public static final String PAYPLANET_PAYMENT_METHOD_CREDIT_CARD = "13"; // 신용카드
	public static final String PAYPLANET_PAYMENT_METHOD_PAYPIN = "14"; // PayPin
	public static final String PAYPLANET_PAYMENT_METHOD_SYRUPPAY = "18"; // SyrupPay
	public static final String PAYPLANET_PAYMENT_METHOD_OCB = "20"; // OK CASHBAG
	public static final String PAYPLANET_PAYMENT_METHOD_TMEMBERSHIP = "21"; // T Membership (YTmembership)
	public static final String PAYPLANET_PAYMENT_METHOD_MOBILE_TMONEY = "22"; // T Money
	public static final String PAYPLANET_PAYMENT_METHOD_DOTORI = "23"; // 도토리
	public static final String PAYPLANET_PAYMENT_METHOD_CULTURE = "24"; // 문화상품권
	public static final String PAYPLANET_PAYMENT_METHOD_TSTORE_CASH = "25"; // T store 캐쉬
	public static final String PAYPLANET_PAYMENT_METHOD_COUPON = "26"; // 쿠폰
	public static final String PAYPLANET_PAYMENT_METHOD_GAMECASH = "27"; // 게임캐쉬
	public static final String PAYPLANET_PAYMENT_METHOD_GAMECASH_POINT = "28"; // 게임캐쉬 보너스 포인트
	public static final String PAYPLANET_PAYMENT_METHOD_TSTORE_POINT = "29"; // T store 포인트
	public static final String PAYPLANET_PAYMENT_METHOD_TGAMEPASS_POINT = "30"; // T game pass 포인트
	/* Store 결제 수단 코드 */
	public static final String STORE_PAYMENT_METHOD_SKT_CARRIER = "OR000605"; // SKT 후불

	/* T Store Cash 서비스 타입 */
	public static final String TSTORE_CASH_CLASS_POINT = "01";
	public static final String TSTORE_CASH_CLASS_CASH = "02";
}
