package com.skplanet.storeplatform.sac.purchase.constant;

/**
 * 구매 관련 상수
 * 
 * Updated on : 2014. 1. 9. Updated by : 양주원, 엔텔스.
 */
public final class PurchaseConstants extends com.skplanet.storeplatform.purchase.constant.PurchaseConstants {

	/** 회원Part 회원정보 조회 결과 에러 코드 */
	public static final String SACINNER_MEMBER_RESULT_USER_NOTFOUND = "SC_MEM_9982";

	/** SKP 법인번호 */
	public static final String SKP_CORPORATION_NO = "1101114699794";

	/** 구매 이력 생성 타입 */
	public static final int CREATE_PURCHASE_TYPE_COMPLETED = 1;
	public static final int CREATE_PURCHASE_TYPE_RESERVED = 2;

	/** 구매 취소 요청자. */
	public static final Integer PRCHS_CANCEL_BY_ADMIN = 1; // ADMIN
	public static final Integer PRCHS_CANCEL_BY_USER = 2; // USER

	/** 쿠폰 발급 형태 */
	public static final String SHOPPING_COUPON_PUBLISH_SYNC = "0"; // 즉시 발급
	public static final String SHOPPING_COUPON_PUBLISH_ASYNC = "1"; // 비동기 발급

	/** 구매인증용 회원 정상/비정상 */
	public static final String VERIFYORDER_USER_STATUS_ABNORMAL = "0"; // 비정상
	public static final String VERIFYORDER_USER_STATUS_NORMAL = "1"; // 정상

	/** 구매인증용 상품 정상/비정상 */
	public static final String VERIFYORDER_PRODUCT_STATUS_ABNORMAL = "0"; // 비정상
	public static final String VERIFYORDER_PRODUCT_STATUS_NORMAL = "1"; // 정상

	/** SKT 결제 타입 */
	public static final String SKT_PAYMENT_TYPE_TESTDEVICE = "T01"; // 시험폰용: 시험폰NUD
	public static final String SKT_PAYMENT_TYPE_ETCSERVICE = "T02"; // 서비스검증용: SKT 불가
	public static final String SKT_PAYMENT_TYPE_NORMAL = "T03"; // 일반

	/** 결제Page Template */
	public static final String PAYMENT_PAGE_TEMPLATE_NORMAL = "TC01"; // 일반
	public static final String PAYMENT_PAGE_TEMPLATE_GAMECASH_FIXRATE = "TC02"; // 정액제 (게임캐쉬)
	public static final String PAYMENT_PAGE_TEMPLATE_LOAN_OWN = "TC03"; // 대여/소장
	public static final String PAYMENT_PAGE_TEMPLATE_AUTOPAY = "TC04"; // 자동결제
	public static final String PAYMENT_PAGE_TEMPLATE_SHOPPING = "TC05"; // 쇼핑
	public static final String PAYMENT_PAGE_TEMPLATE_GIFT = "TC06"; // 선물

	/** T Store Cash */
	/* 서비스 타입 */
	public static final String TSTORE_CASH_SVC_TYPE_INQUIRY = "01"; // 조회
	public static final String TSTORE_CASH_SVC_TYPE_CHARGE = "02"; // 충전
	public static final String TSTORE_CASH_SVC_TYPE_USE = "03"; // 사용
	/* 서비스 상세타입 */
	public static final String TSTORE_CASH_SVC_DETAIL_TYPE_INQUIRY = "01"; // 조회
	public static final String TSTORE_CASH_SVC_DETAIL_TYPE_RESERVATION = "02"; // 예약
	public static final String TSTORE_CASH_SVC_DETAIL_TYPE_CONFIRM = "03"; // 확정
	public static final String TSTORE_CASH_SVC_DETAIL_TYPE_CANCEL = "04"; // 취소
	/* 서비스 채널 */
	public static final String TSTORE_CASH_SVC_CHANNEL_TENANT = "01"; // TENANT
	public static final String TSTORE_CASH_SVC_CHANNEL_SAC = "02"; // SAC
	public static final String TSTORE_CASH_SVC_CHANNEL_PAYPLANET = "03"; // PAY PLANET
	/* 상품군 */
	public static final String TSTORE_CASH_PRODUCT_GROUP_ALL = "00"; // 전체
	public static final String TSTORE_CASH_PRODUCT_GROUP_APP = "01"; // Application
	public static final String TSTORE_CASH_PRODUCT_GROUP_MULTIMEDIA = "02"; // Multimedia
	public static final String TSTORE_CASH_PRODUCT_GROUP_SHOPPING = "03"; // Shopping
	/* 응답코드 */
	public static final String TSTORE_CASH_RESULT_CD_SUCCESS = "0000"; // SUCCESS

	/** T Store Coupon */
	/* 응답코드 */
	public static final String TSTORE_COUPON_RESULT_CD_SUCCESS = "0000"; // SUCCESS

	/** UAPS */
	public static final String UAPS_SVC_TP_SKTTEST = "12"; // 회선 타입: 시험폰

}
