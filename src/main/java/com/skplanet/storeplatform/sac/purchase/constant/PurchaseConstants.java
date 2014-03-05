package com.skplanet.storeplatform.sac.purchase.constant;

/**
 * 구매 관련 상수
 * 
 * Updated on : 2014. 1. 9. Updated by : 양주원, 엔텔스.
 */
public final class PurchaseConstants {

	// 구매유형코드: 구매 / 선물
	public static final String PRCHS_CASE_PURCHASE_CD = "OR020301"; // 구매
	public static final String PRCHS_CASE_GIFT_CD = "OR020302"; // 선물

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

	/** 테넌트 판매 정책. */
	public static final String POLICY_ID_001 = "policy001"; // 일반고객쇼핑상품SKT후불결제한도금액
	public static final String POLICY_ID_002 = "policy002"; // 우수고객쇼핑상품SKT후불결제한도금액
	public static final String POLICY_ID_003 = "policy003"; // SKT후불결제금액제한
	public static final String POLICY_ID_004 = "policy004"; // 선물수신한도설정
	public static final String POLICY_ID_005 = "policy005"; // SKT/SKP법인폰제한
	public static final String POLICY_ID_006 = "policy006"; // 서비스허용시험폰
	public static final String POLICY_ID_007 = "policy007"; // 비과금단말등록(TestMdn)
	public static final String POLICY_ID_008 = "policy008"; // Device기반 구매내역관리
	public static final String POLICY_ID_009 = "policy009"; // 유료상품유료결제구매취소정책
	public static final String POLICY_ID_010 = "policy010"; // 유료상품무료결제구매취소정책
	public static final String POLICY_ID_011 = "policy011"; // 무료상품구매취소정책

	// 테넌트 정책 처리 패턴 코드
	public static final String POLICY_PATTERN_SKT_PRCHS_LIMIT = "CM011601"; // SKT후불 구매결제 한도제한
	public static final String POLICY_PATTERN_SKT_RECV_LIMIT = "CM011602"; // SKT후불 선물수신 한도제한
	public static final String POLICY_PATTERN_CORP_DEVICE = "CM011603"; // 법인명의 제한
	public static final String POLICY_PATTERN_SKT_TEST_DEVICE = "CM011604"; // 시험폰 결제 허용
	public static final String POLICY_PATTERN_STORE_TEST_DEVICE = "CM011605"; // 비과금 결제 허용
	public static final String POLICY_PATTERN_DEVICE_BASED_PRCHSHST = "CM011606"; // Device기반 구매내역 관리처리
	public static final String POLICY_PATTERN_ADMIN_CANCEL_CHARGED = "CM011607"; // 유료상품 Admin 결제취소 허용
	public static final String POLICY_PATTERN_USER_CANCEL_CHARGED = "CM011608"; // 유료상품 사용자 결제취소 허용
	public static final String POLICY_PATTERN_ADMIN_CANCEL_FREE = "CM011609"; // 무료상품 Admin 결제취소 허용
	public static final String POLICY_PATTERN_USER_CANCEL_FREE = "CM011610"; // 무료상품 사용자 결제취소 허용
	public static final String POLICY_PATTERN_MEMBER_BLOCK_CD = "CM011611"; // 회원Part 구매차단 코드

	/**
	 * 자동 구매 해지 상태 코드
	 */
	public static final String AUTO_PRCHS_CLOSE_RESERVE = "OR020101";
	public static final String AUTO_PRCHS_CLOSE_RESERVE_CANCEL = "OR020102";
	public static final String AUTO_PRCHS_CLOSE = "OR020103";

}
