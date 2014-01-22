package com.skplanet.storeplatform.sac.purchase.common;

/**
 * 구매 관련 상수
 * 
 * Updated on : 2014. 1. 9. Updated by : 양주원, 엔텔스.
 */
public final class PurchaseConstants {
	// ================================================================================================
	// TODO:: 임시 코드 영역

	// 구매유형코드: 구매 / 선물 - OR0017S / OR0017G 사용 결정 필요
	public static final String PRCHS_CASE_PURCHASE_CD = "OR0017S"; // 구매
	public static final String PRCHS_CASE_GIFT_CD = "OR0017G"; // 선물

	// ================================================================================================

	/** 사용 여부 Y N. **/
	public static final String USE_Y = "Y"; // Y
	public static final String USE_N = "N"; // N

	/** 구매상태코드. */
	public static final String PRCHS_STATUS_COMPT = "OR000301"; // 구매완료
	public static final String PRCHS_STATUS_CANCEL = "OR000302"; // 구매취소
	public static final String PRCHS_STATUS_FAIL = "OR000303"; // 구매실패
	public static final String PRCHS_STATUS_TEST_CANCEL = "OR000304"; // 시험폰구매취소
	public static final String PRCHS_STATUS_DRBK = "OR000305"; // 환불

	/** 구매요청경로코드. */
	public static final String PRCHS_REQ_PATH_WEB = "OR000401"; // WEB
	public static final String PRCHS_REQ_PATH_WAP = "OR000402"; // WAP
	public static final String PRCHS_REQ_PATH_PC = "OR000403"; // PC Suite
	public static final String PRCHS_REQ_PATH_MOBILE_WEB = "OR000404"; // Mobile WEB
	public static final String PRCHS_REQ_PATH_MOBILE_CLIENT = "OR000405"; // Mobile Client

	/** 수신확인경로코드. */
	public static final String RECV_CONF_PATH_WEB_POP = "OR003901"; // 팝업확인(Web)
	public static final String RECV_CONF_PATH_WEB_MY = "OR003902"; // 보관함확인(Web)
	public static final String RECV_CONF_PATH_MOBILE_POP = "OR003903"; // 팝업확인(Mobile)
	public static final String RECV_CONF_PATH_MOBILE_MY = "OR003904"; // 보관함확인(Mobile)

	/** 구매유형코드. */
	public static final String PRCHS_CASE_SEND = "OR003801"; // 선물발신
	public static final String PRCHS_CASE_RECV = "OR003802"; // 선물수신

	/** 네트워크타입코드. */
	public static final String NETWORK_TYPE_ALL = "DP004401"; // 모두지원
	public static final String NETWORK_TYPE_WIFI = "DP004402"; // WI-FI
	public static final String NETWORK_TYPE_3G = "DP004403"; // 3G

	/** 결제상태코드. */
	public static final String PAYMENT_STATUS_PLAM = "OR000701"; // 결제예정
	public static final String PAYMENT_STATUS_CANCEL = "OR000702"; // 결제취소
	public static final String PAYMENT_STATUS_COMPT = "OR000703"; // 결제완료

	/** 구매상품타입_보유상품. */
	public static final String PRCHS_PROD_TYPE_OWN = "OR020201"; // 보유상품
	/** 구매상품타입_미보유상품. */
	public static final String PRCHS_PROD_TYPE_SEND = "OR020202"; // 미보유상품
	/** 구매상품타입_정액제상품. */
	public static final String PRCHS_PROD_TYPE_FIX = "OR020203"; // 정액제상품

}
