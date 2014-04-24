package com.skplanet.storeplatform.sac.purchase.constant;

/**
 * 구매 관련 상수
 * 
 * Updated on : 2014. 1. 9. Updated by : 양주원, 엔텔스.
 */
public final class PurchaseConstants extends com.skplanet.storeplatform.purchase.constant.PurchaseConstants {
	/** 서비스 서버 단계 환경변수 */
	public static final String ENV_SERVER_LEVEL_REAL = "real"; // System 설정 서버 단계 property: 상용
	public static final String ENV_SERVER_LEVEL_QA = "qa"; // System 설정 서버 단계 property: QA
	public static final String ENV_SERVER_LEVEL_DEV = "dev"; // System 설정 서버 단계 property: 개발기
	public static final String ENV_SERVER_LEVEL_LOCAL = "local"; // System 설정 서버 단계 property: 로컬

	/** 회원Part 조회 결과 에러 코드 */
	public static final String SACINNER_MEMBER_RESULT_NOTFOUND = "SC_MEM_9982";

	/** SKP 법인번호 */
	public static final String SKP_CORPORATION_NO = "1101114699794";

	/** (다날) 컨텐츠 타입 */
	public static final String DANAL_CONTENT_TYPE_DIGITAL = "0"; // 디지털 상품
	public static final String DANAL_CONTENT_TYPE_REAL = "1"; // 실물 상품

	/** SKT후불 결제수단 재정의 원인 타입 */
	public static final String SKT_ADJUST_REASON_NO_LIMIT = "L00"; // 제한없음 / 타통신사
	public static final String SKT_ADJUST_REASON_MVNO = "L01"; // MVNO
	public static final String SKT_ADJUST_REASON_CORP = "L02"; // 법인폰
	public static final String SKT_ADJUST_REASON_SKTTEST_ALLOW = "L03"; // 허용 SKT시험폰
	public static final String SKT_ADJUST_REASON_SKTTEST_NOT_ALLOW = "L04"; // 비허용 SKT시험폰
	public static final String SKT_ADJUST_REASON_SHOPPING_USERPART_LIMIT = "L05"; // SKT후불 쇼핑상품 회원 별 강제적용 한도금액
	public static final String SKT_ADJUST_REASON_SHOPPING_LIMIT = "L06"; // SKT후불 쇼핑상품 한도금액
	public static final String SKT_ADJUST_REASON_SHOPPING_RECV_LIMIT = "L07"; // SKT후불 쇼핑상품 선물수신 한도금액
	public static final String SKT_ADJUST_REASON_LIMIT = "L08"; // SKT후불 한도금액
	public static final String SKT_ADJUST_REASON_RECV_LIMIT = "L09"; // SKT후불 선물수신 한도금액

	/** 구매 이력 생성 타입 */
	public static final int CREATE_PURCHASE_TYPE_COMPLETED = 1;
	public static final int CREATE_PURCHASE_TYPE_RESERVED = 2;

	/** 구매 취소 요청자. */
	public static final Integer PRCHS_CANCEL_BY_ADMIN = 1; // ADMIN
	public static final Integer PRCHS_CANCEL_BY_USER = 2; // USER

	/** 구매 취소 서비스 타입. */
	public static final Integer PRCHS_CANCEL_SERVICE_DEFAULT = 0; // 기본
	public static final Integer PRCHS_CANCEL_SERVICE_TCASH = 1; // TCASH

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
	public static final String TSTORE_CASH_CLASS_POINT = "01";
	public static final String TSTORE_CASH_CLASS_CASH = "02";

	public static final String TSTORE_CASH_SVC_TYPE_INQUIRY = "01"; // 조회
	public static final String TSTORE_CASH_SVC_TYPE_CHARGE = "02"; // 충전
	public static final String TSTORE_CASH_SVC_TYPE_USE = "03"; // 사용

	public static final String TSTORE_CASH_SVC_TYPE_CHARGE_CANCEL = "01"; // 충전취소
	public static final String TSTORE_CASH_SVC_TYPE_USE_CANCEL = "02"; // 사용취소

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

	/** T Store 구매완료 Noti */
	/* Noti 타입 */
	public static final String TSTORE_NOTI_TYPE_NORMALPAY = "01"; // Type: 일반결제
	public static final String TSTORE_NOTI_TYPE_AUTOPAY = "02"; // Type: 자동결제
	public static final String TSTORE_NOTI_TYPE_AUTOPAY_RESERVED = "03"; // Type: 자동결제 해지예약
	public static final String TSTORE_NOTI_TYPE_AUTOPAY_CLOSED = "04"; // Type: 자동결제 해지완료
	/* Noti 쇼핑쿠폰 발행 처리 타입 */
	public static final String TSTORE_NOTI_PUBLISH_TYPE_SYNC = "01"; // Type: 동기 (또는 쇼핑 이외 상품)
	public static final String TSTORE_NOTI_PUBLISH_TYPE_ASYNC = "02"; // Type: 비동기
	public static final String TSTORE_NOTI_PUBLISH_TYPE_ASYNC_COMPLETED = "03"; // Type: 비동기 완료
	/* Noti 결과 */
	public static final String TSTORE_NOTI_RESULT_SUCCESS = "00"; // 00: 성공
	public static final String TSTORE_NOTI_RESULT_FAIL = "01"; // 01: 실패

	/** UAPS */
	public static final String UAPS_SVC_TP_SKTTEST = "12"; // 회선 타입: 시험폰
	public static final String[] UAPS_SVC_LIMIT_SERVICE = { "NA00002125" }; // 한도요금 상품

	/** Pay Planet 결제 방법 코드 */
	public static final String PAYPLANET_PAYMENT_METHOD_SKT_CARRIER = "11"; // SKT 후불
	public static final String PAYPLANET_PAYMENT_METHOD_DANAL = "12"; // 다날
	public static final String PAYPLANET_PAYMENT_METHOD_CREDIT_CARD = "13"; // 신용카드
	public static final String PAYPLANET_PAYMENT_METHOD_PAYPIN = "14"; // PayPin
	public static final String PAYPLANET_PAYMENT_METHOD_OCB = "20"; // OK CASHBAG
	public static final String PAYPLANET_PAYMENT_METHOD_TMEMBERSHIP = "21"; // T Membership (YTmembership)
	public static final String PAYPLANET_PAYMENT_METHOD_MOBILE_TMONEY = "22"; // T Money
	public static final String PAYPLANET_PAYMENT_METHOD_DOTORI = "23"; // 도토리
	public static final String PAYPLANET_PAYMENT_METHOD_CULTURE = "24"; // 문화상품권
	public static final String PAYPLANET_PAYMENT_METHOD_TSTORE_CASH = "25"; // T store 캐쉬
	public static final String PAYPLANET_PAYMENT_METHOD_COUPON = "26"; // 쿠폰

	/** Pay Planet 네트워크 타입 */
	public static final String PAYPLANET_NETWORK_TYPE_3GLTE = "1"; // 3G, LTE
	public static final String PAYPLANET_NETWORK_TYPE_WIFI = "2"; // WIFI
	public static final String PAYPLANET_NETWORK_TYPE_UNKNOWN = "3"; // Unknown

	/** Pay Planet 통신사 */
	public static final String PAYPLANET_TELECOM_SKT = "S"; // SKT
	public static final String PAYPLANET_TELECOM_KT = "K"; // KT
	public static final String PAYPLANET_TELECOM_LGT = "L"; // LG U+
	public static final String PAYPLANET_TELECOM_UNKNOWN = "X"; // Unknown

	/** Pay Planet 결제 취소 사유 */
	public static final String PAYPLANET_PAYMENT_CANCEL_REASON_VOC = "E01"; // VOC 운영 취소.

	/** T Store 결제 성공 코드 */
	public static final String TSTORE_PAYMENT_CANCEL_SUCCESS = "0000";

	/** 구매 상품 건수 업데이트 처리 상태 코드 */
	public static final String PURCHASE_PRODUCT_COUNT_PROC_STATUS_NO = "N";
	public static final String PURCHASE_PRODUCT_COUNT_PROC_STATUS_RESERVE = "R";
	public static final String PURCHASE_PRODUCT_COUNT_PROC_STATUS_UPDATE = "U";
	public static final String PURCHASE_PRODUCT_COUNT_PROC_STATUS_SUCCESS = "S";
	public static final String PURCHASE_PRODUCT_COUNT_PROC_STATUS_FAIL = "F";

	/** Interface ID */
	public static final String INTERFACE_ID_TL00027 = "TL_SAC_PUR_0027"; // Biz쿠폰 비동기 응답

}
