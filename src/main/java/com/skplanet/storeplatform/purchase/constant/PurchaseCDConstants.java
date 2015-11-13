package com.skplanet.storeplatform.purchase.constant;

/**
 * 구매 관련 코드 상수
 * 
 * Updated on : 2014. 1. 9. Updated by : 양주원, 엔텔스.
 */
public class PurchaseCDConstants {
	/** 사용 여부 Y N. **/
	public static final String USE_Y = "Y"; // Y
	public static final String USE_N = "N"; // N

	/** String 처리 **/
	public static final String DELIMITER = ":";
	public static final String SEPARATOR = ";";

	/** 무제한 일시 */
	public static final String UNLIMITED_DATE = "99991231235959";

	/** 테넌트 정책 처리 패턴 코드 */
	public static final String POLICY_PATTERN_PHONE_PRCHS_LIMIT = "CM011601"; // SKT후불 구매결제 한도제한
	public static final String POLICY_PATTERN_PHONE_RECV_LIMIT = "CM011602"; // SKT후불 선물수신 한도제한
	public static final String POLICY_PATTERN_CORP_DEVICE = "CM011603"; // 법인명의 제한
	public static final String POLICY_PATTERN_PHONE_TEST_DEVICE = "CM011604"; // 시험폰 결제 허용
	public static final String POLICY_PATTERN_STORE_TEST_DEVICE_CD = "CM011605"; // 비과금 결제 허용
	public static final String POLICY_PATTERN_DEVICE_BASED_PRCHSHST = "CM011606"; // Device기반 구매내역 관리처리
	public static final String POLICY_PATTERN_ADMIN_CANCEL_CHARGED = "CM011607"; // 유료상품 Admin 결제취소 허용
	public static final String POLICY_PATTERN_USER_CANCEL_CHARGED = "CM011608"; // 유료상품 사용자 결제취소 허용
	public static final String POLICY_PATTERN_ADMIN_CANCEL_FREE = "CM011609"; // 무료상품 Admin 결제취소 허용
	public static final String POLICY_PATTERN_USER_CANCEL_FREE = "CM011610"; // 무료상품 사용자 결제취소 허용
	public static final String POLICY_PATTERN_USER_BLOCK_CD = "CM011611"; // 회원Part 구매차단 코드
	public static final String POLICY_PATTERN_MVNO_ALLOW_CD = "CM011612"; // MVNO 허용코드
	public static final String POLICY_PATTERN_PHONE_SHOPPING_PRCHS_USER_LIMIT = "CM011613"; // 개별고객 쇼핑상품 SKT후불 결제한도
	public static final String POLICY_PATTERN_ADJUST_PAYMETHOD = "CM011614"; // SKT 후불 외 결제수단 재정의
	public static final String POLICY_PATTERN_PHONE_SHOPPING_RECV_USER_LIMIT = "CM011615"; // 개별고객 쇼핑상품 선물수신 SKT후불 결제한도
	public static final String POLICY_PATTERN_ALLOW_PURCHASE_DEVICE = "CM011616"; // 시험폰 구매허용코드
	public static final String POLICY_PATTERN_TMILEAGE_SAVE_PAYMENT_METHOD = "CM011617"; // T마일리지 적립 대상 결제수단
	public static final String POLICY_PATTERN_TMILEAGE_SAVE_LIMIT = "CM011618"; // T마일리지 적립 한도
	public static final String POLICY_PATTERN_SAP_TEST_DEVICE = "CM011619"; // 시험폰 조회 (SAP)
	public static final String POLICY_PATTERN_SAP_CHECK_POLICY = "CM011620"; // (SAP) 결제정책 조회
	public static final String POLICY_PATTERN_EXTRA_UNITCD_SUSPENSION = "CM011308"; // 결제 페이지 결제 차단수단 노출 메시지

	/** 테넌트 정책 적용 전 조건 체크 단위 */
	public static final String POLICY_PRECONDITION_PERIOD_UNIT_CD_PREMONTH = "CM011411"; // 전월
	/** 테넌트 정책 적용 전 조건 구분 단위 */
	public static final String POLICY_PRECONDITION_CLSF_UNIT_CD_COUNT = "CM011501"; // 건

	/** 네트워크타입코드. */
	public static final String NETWORK_TYPE_ALL = "DP004401"; // 모두지원
	public static final String NETWORK_TYPE_WIFI = "DP004402"; // WI-FI
	public static final String NETWORK_TYPE_3G = "DP004403"; // 3G
	public static final String NETWORK_TYPE_LTE = "DP004404"; // LTE

	/** 쇼핑 상품 유형 코드 */
	public static final String SHOPPING_TYPE_GIFT = "DP006301"; // 상품권
	public static final String SHOPPING_TYPE_COUPON = "DP006302"; // 교환권
	public static final String SHOPPING_TYPE_DELIVERY = "DP006303"; // 배송상품
	public static final String SHOPPING_TYPE_B2B = "DP006304"; // B2B상품
	public static final String SHOPPING_TYPE_CASH_EXCHANGE = "DP006305"; // 금액 교환권
	public static final String SHOPPING_TYPE_CHARGE_CARD = "DP006306"; // 충전권

	/** 소장/대여 구분 코드 */
	public static final String PRODUCT_POSS_RENTAL_TYPE_POSSESION = "DP010601"; // 소장
	public static final String PRODUCT_POSS_RENTAL_TYPE_RENTAL = "DP010602"; // 대여
	public static final String PRODUCT_POSS_RENTAL_TYPE_ALL = "DP010603"; // 모두 가능

	/** 상품 이용기간 설정 타입 코드 */
	public static final String PRODUCT_USE_PERIOD_SET_DOWNLOAD = "DP013001"; // 다운로드/스트리밍 시점
	public static final String PRODUCT_USE_PERIOD_SET_PURCHASE = "DP013002"; // 구매시점

	/** 구매상태코드. */
	public static final String PRCHS_STATUS_COMPT = "OR000301"; // 구매완료
	public static final String PRCHS_STATUS_CANCEL = "OR000302"; // 구매취소
	public static final String PRCHS_STATUS_FAIL = "OR000303"; // 구매실패
	public static final String PRCHS_STATUS_TEST_CANCEL = "OR000304"; // 시험폰구매취소
	public static final String PRCHS_STATUS_DRBK = "OR000305"; // 환불
	public static final String PRCHS_STATUS_RESERVATION = "OR000306"; // 구매예약
	public static final String PRCHS_STATUS_RESERVATION_CANCEL = "OR000307"; // 구매예약취소
	public static final String PRCHS_STATUS_PAYMENT_FAIL = "OR000308"; // 결제실패

	/** 구매요청경로코드. */
	public static final String PRCHS_REQ_PATH_ADMIN = "OR000400"; // ADMIN
	public static final String PRCHS_REQ_PATH_WEB = "OR000401"; // WEB
	public static final String PRCHS_REQ_PATH_WAP = "OR000402"; // WAP
	public static final String PRCHS_REQ_PATH_PC = "OR000403"; // PC Suite
	public static final String PRCHS_REQ_PATH_MOBILE_WEB = "OR000404"; // Mobile WEB
	public static final String PRCHS_REQ_PATH_MOBILE_CLIENT = "OR000405"; // Mobile Client
	public static final String PRCHS_REQ_PATH_S2S = "OR000455"; // S2S(결제 취소 진행 안함)
	public static final String PRCHS_REQ_PATH_TWORLD = "OR000406"; // T-WORLD
	public static final String PRCHS_REQ_PATH_IAP = "OR000407"; // 부분유료화
	public static final String PRCHS_REQ_PATH_IAP_COMMERCIAL_CONVERTED = "OR000408"; // 부분유료화 정식판전환
	public static final String PRCHS_REQ_PATH_SOCIAL_SHOPPING = "OR000409"; // 소셜쇼핑
	public static final String PRCHS_REQ_PATH_WEB_SALE = "OR000410"; // 웹 판매
	public static final String PRCHS_REQ_PATH_NATE_CA = "OR000411"; // NATE CA
	public static final String PRCHS_REQ_PATH_VOD_EVENT = "OR000412"; // VOD 이벤트
	public static final String PRCHS_REQ_PATH_T_BENEFIT_EVENT = "OR000413"; // T혜택 이벤트
	public static final String PRCHS_REQ_PATH_T_BENEFIT_RINGBELL_EVENT = "OR000414"; // T혜택 링/벨 이벤트
	public static final String PRCHS_REQ_PATH_DISTRIBUTION_RECOMMNED_APP = "OR000415"; // 유통망 추천 앱
	public static final String PRCHS_REQ_PATH_DINNO_EVENT = "OR000416"; // 디노 이벤트
	public static final String PRCHS_REQ_PATH_WEB2PHONE = "OR000417"; // 웹2폰
	public static final String PRCHS_REQ_PATH_COMAS = "OR000418"; // 코마스
	public static final String PRCHS_REQ_PATH_T_FREEMIUM = "OR000420"; // T프리미엄 (DRM)
	public static final String PRCHS_REQ_PATH_B2B_BALANCE = "OR000421"; // B2B Gateway (정산)
	public static final String PRCHS_REQ_PATH_B2B_NON_BALANCE = "OR000422"; // B2B Gateway (비정산)
	public static final String PRCHS_REQ_PATH_MNS = "OR000423"; // MNS 기프트카드
	public static final String PRCHS_REQ_PATH_GAME_CENTER = "OR000424"; // 게임센터
	public static final String PRCHS_REQ_PATH_CPI_DINNO = "OR000425"; // 디노 (CPI)
	public static final String PRCHS_REQ_PATH_CPI_MNS = "OR000426"; // 엠엔에스 (CPI)
	public static final String PRCHS_REQ_PATH_CPI_COMAS = "OR000427"; // 코마스 (CPI)
	public static final String PRCHS_REQ_PATH_CPA_DINNO = "OR000429"; // 디노 (CPA)
	public static final String PRCHS_REQ_PATH_CPA_MNS = "OR000430"; // 엠엔에스 (CPA)
	public static final String PRCHS_REQ_PATH_CPA_COMAS = "OR000431"; // 코마스 (CPA)
	public static final String PRCHS_REQ_PATH_BIZ_COUPON = "OR000432"; // BIZ 쿠폰
	public static final String PRCHS_REQ_PATH_CPI_IGAWORKS = "OR000433"; // 아이지에이웍스 (CPI)
	public static final String PRCHS_REQ_PATH_CPA_IGAWORKS = "OR000434"; // 아이지에이웍스 (CPA)
	public static final String PRCHS_REQ_PATH_DEV_SITE = "OR000435"; // 개발자 사이트
	public static final String PRCHS_REQ_PATH_ADMIN_REFUND = "OR000437"; // ADMIN(환불)
	public static final String PRCHS_REQ_PATH_ADMIN_FORCE_CANCEL = "OR000438"; // ADMIN(강제구매취소)
	public static final String PRCHS_REQ_PATH_ADMIN_PAYMENT_NOT_SYNC = "OR000439"; // ADMIN(결제대사불일치구매취소)
	public static final String PRCHS_REQ_PATH_PAYMENT_ERROR_CANCEL = "OR000440"; // 망상취소
	public static final String PRCHS_REQ_PATH_TENANT_PAYMENT_ERROR_CANCEL = "OR000443"; // Tenant 망상취소
	public static final String PRCHS_REQ_PATH_CLINK = "OR000442"; // CLINK
	public static final String PRCHS_REQ_PATH_EBOOK_STORAGE = "OR000444"; // 이북보관함
	public static final String PRCHS_REQ_PATH_AOM_AGREE = "OR000446"; // AOM 수신동의
	public static final String PRCHS_REQ_PATH_IAP_AUTO_PAYMENT_CANCEL = "OR000452"; // IAP자동결제 해지
	public static final String PRCHS_REQ_PATH_OFFERING_PAYMENT_ERROR_CANCEL = "OR000456"; // 망상취소-오퍼링 실패

	/** 결제 방법 코드 */
	public static final String PAYMENT_METHOD_CREDIT_CARD = "OR000601"; // 신용카드
	public static final String PAYMENT_METHOD_DANAL = "OR000602"; // 다날 휴대폰 결제
	public static final String PAYMENT_METHOD_OCB = "OR000603"; // OK CASHBAG
	public static final String PAYMENT_METHOD_DOTORI = "OR000604"; // 도토리
	public static final String PAYMENT_METHOD_SKT_CARRIER = "OR000605"; // SKT 후불
	public static final String PAYMENT_METHOD_COUPON = "OR000606"; // 쿠폰
	public static final String PAYMENT_METHOD_TSTORE_CASH = "OR000607"; // T store 캐쉬
	public static final String PAYMENT_METHOD_TSTORE_POINT = "OR000608"; // T store 포인트
	public static final String PAYMENT_METHOD_GIFT_RECEIVED = "OR000609"; // 선물수신결제
	public static final String PAYMENT_METHOD_CULTURE = "OR000610"; // 문화상품권
	public static final String PAYMENT_METHOD_TSTORE_GIFTCARD = "OR000611"; // T store 기프트카드
	public static final String PAYMENT_METHOD_FIXRATE = "OR000612"; // 정액권
	public static final String PAYMENT_METHOD_SERIESPASS = "OR000613"; // 시리즈패스권
	public static final String PAYMENT_METHOD_MOBILE_TMONEY = "OR000614"; // T Money
	public static final String PAYMENT_METHOD_TMEMBERSHIP = "OR000616"; // T Membership (YTmembership)
	public static final String PAYMENT_METHOD_EBOOKCOMIC_OWN = "OR000617"; // 이북/코믹 전권 소장
	public static final String PAYMENT_METHOD_EBOOKCOMIC_LOAN = "OR000618"; // 이북/코믹 전권 대여
	public static final String PAYMENT_METHOD_B2B = "OR000621"; // 타사이트 결제 (B2B Gateway)
	public static final String PAYMENT_METHOD_PAYPIN = "OR000622"; // PayPin
	public static final String PAYMENT_METHOD_GAMECASH = "OR000623"; // 게임캐쉬
	public static final String PAYMENT_METHOD_GAMECASH_POINT = "OR000624"; // 게임캐쉬 보너스 포인트
	public static final String PAYMENT_METHOD_TGAMEPASS = "OR000625"; // T game pass
	public static final String PAYMENT_METHOD_SYRUP_PAY = "OR000626"; // SYRUP PAY
	public static final String PAYMENT_METHOD_BOOKS_CASH = "OR000627"; // 북스캐시
	public static final String PAYMENT_METHOD_BOOKS_CASH_POINT = "OR000628"; // 북스캐시 포인트
	public static final String PAYMENT_METHOD_SHOPPING_SPECIAL_COUPON = "OR000606"; // TAKTODO:: 쇼핑 특가상품 쿠폰
	public static final String PAYMENT_METHOD_FIXRATE_REFUND = "OR000692"; // 정액권 환불
	public static final String PAYMENT_METHOD_SKT_TEST_DEVICE = "OR000694"; // SKT 시험폰 결제
	public static final String PAYMENT_METHOD_IAP_COMMERCIAL_CONVERTED = "OR000695"; // 부분유료화 정식판 전환
	public static final String PAYMENT_METHOD_EVENT_ONE_PLUS_ONE = "OR000696"; // 1+1 이벤트
	public static final String PAYMENT_METHOD_EVENT = "OR000697"; // 이벤트 - 유료컨텐츠 비과금 결제
	public static final String PAYMENT_METHOD_STORE_TEST_DEVICE = "OR000698"; // 테스트폰 결제
	public static final String PAYMENT_METHOD_FREE_PRODUCT = "OR000699"; // 무료상품

	/** 결제 설정 코드 **/
	public static final String PAYMENT_METHOD_SALEPOLICY_AUTOPRCHS = "OR004701"; // 자동결제상품

	/** 결제상태코드 */
	public static final String PAYMENT_STATUS_PLAM = "OR000701"; // 결제예정
	public static final String PAYMENT_STATUS_CANCEL = "OR000702"; // 결제취소
	public static final String PAYMENT_STATUS_COMPT = "OR000703"; // 결제완료

	/** 컬러링&벨소리 음질 */
	public static final String RINGBELL_CLASS_RING_BASIC = "OR0018B"; // 컬러링 일반
	public static final String RINGBELL_CLASS_RING_LONG = "OR0018L"; // 컬러링 롱
	public static final String RINGBELL_CLASS_BELL_BASIC = "OR00181"; // 벨소리 일반
	public static final String RINGBELL_CLASS_BELL_HIGH = "OR001863"; // 벨소리 고음질

	/** 수신확인경로코드 */
	public static final String RECV_CONF_PATH_WEB_POP = "OR003901"; // 팝업확인(Web)
	public static final String RECV_CONF_PATH_WEB_MY = "OR003902"; // 보관함확인(Web)
	public static final String RECV_CONF_PATH_MOBILE_POP = "OR003903"; // 팝업확인(Mobile)
	public static final String RECV_CONF_PATH_MOBILE_MY = "OR003904"; // 보관함확인(Mobile)

	/** 정액제 상품 타입 코드 */
	public static final String FIXRATE_PROD_TYPE_FIXRATE = "OR004301"; // 이용권-정액권 (기간제 정액제 포함)
	public static final String FIXRATE_PROD_TYPE_SERIESPASS = "OR004302"; // 이용권-시리즈패스
	public static final String FIXRATE_PROD_TYPE_EBOOKCOMIC_OWN = "OR004303"; // 이북/코믹 전권소장
	public static final String FIXRATE_PROD_TYPE_EBOOKCOMIC_LOAN = "OR004304"; // 이북/코믹 전권대여

	/** 테넌트 상품 분류 코드 */
	// 그룹
	public static final String TENANT_PRODUCT_GROUP_APP = "OR006211"; // 어플리케이션
	public static final String TENANT_PRODUCT_GROUP_VOD = "OR006212"; // VOD
	public static final String TENANT_PRODUCT_GROUP_MUSIC = "OR006213"; // 뮤직 (MP3)
	public static final String TENANT_PRODUCT_GROUP_EBOOKCOMIC = "OR006214"; // 이북/코믹
	public static final String TENANT_PRODUCT_GROUP_SHOPPING = "OR006221"; // 쇼핑쿠폰
	public static final String TENANT_PRODUCT_GROUP_RINGBELL = "OR006231"; // 컬러링/벨소리
	public static final String TENANT_PRODUCT_GROUP_IAP = "OR006321"; // INAPP (부분유료화)
	// 상세 (FULL CODE)
	public static final String TENANT_PRODUCT_GROUP_DTL_GAME = "OR006211DP01OR006311"; // 게임
	public static final String TENANT_PRODUCT_GROUP_DTL_MOVIE_UNIT = "OR006212DP17OR006311"; // 영화
	public static final String TENANT_PRODUCT_GROUP_DTL_TV_UNIT = "OR006212DP18OR006311"; // 방송
	public static final String TENANT_PRODUCT_GROUP_DTL_MOVIE_FIXRATE = "OR006212DP17OR006331"; // 영화 정액권
	public static final String TENANT_PRODUCT_GROUP_DTL_TV_FIXRATE = "OR006212DP18OR006331"; // 방송 정액권
	public static final String TENANT_PRODUCT_GROUP_DTL_EBOOK_UNIT = "OR006214DP13OR006311"; // EBOOK
	public static final String TENANT_PRODUCT_GROUP_DTL_COMIC_UNIT = "OR006214DP14OR006311"; // COMIC
	public static final String TENANT_PRODUCT_GROUP_DTL_EBOOK_FIXRATE = "OR006214DP13OR006331"; // EBOOK 정액권
	public static final String TENANT_PRODUCT_GROUP_DTL_COMIC_FIXRATE = "OR006214DP14OR006331"; // COMIC 정액권
	public static final String TENANT_PRODUCT_GROUP_DTL_GAMECASH_FIXRATE = "OR006211DP01OR006332"; // 게임캐쉬 정액제
	public static final String TENANT_PRODUCT_GROUP_DTL_BOOKSCASH_FIXRATE = "OR006214DP13OR006332"; // 북스캐쉬 정액제
	// SUFFIX
	public static final String TENANT_PRODUCT_GROUP_SUFFIX_UNIT = "OR006311"; // 일반 상품 suffix
	public static final String TENANT_PRODUCT_GROUP_SUFFIX_FIXRATE = "OR006331"; // 정액 상품 suffix
	public static final String TENANT_PRODUCT_GROUP_SUFFIX_CASH = "OR006332"; // 캐시 정액 상품 suffix
	public static final String TENANT_PRODUCT_GROUP_SUFFIX_CHARGE = "OR006312"; // 충전 상품 suffix

	/** 자동 결제 상태 코드 */
	public static final String AUTO_PRCHS_STATUS_AUTO = "OR020101"; // 자동결제 진행 중
	public static final String AUTO_PRCHS_STATUS_CLOSE_RESERVATION = "OR020102"; // 자동결제 해지 예약
	public static final String AUTO_PRCHS_STATUS_CLOSED = "OR020103"; // 자동결제 해지
	public static final String AUTO_PRCHS_STATUS_IAP_CLOSED = "OR020104"; // 부분유료화 자동결제 해지

	public static final String PRCHS_ID_IAP_GXG = "GXG"; // 부분유료화 구매시작 ID

	/** [OLD] 자동 구매 해지 상태 코드 */
	public static final String AUTO_PRCHS_CLOSE_RESERVE = "OR020101";
	public static final String AUTO_PRCHS_CLOSE_RESERVE_CANCEL = "OR020102";
	public static final String AUTO_PRCHS_CLOSE = "OR020103";

	/** 자동 결제 해지 요청 경로 코드 */
	public static final String AUTO_PRCHS_CLOSE_PATH_PURCHASE_CANCEL = "OR004600";

	/** 구매상품타입 */
	public static final String PRCHS_PROD_TYPE_UNIT = "OR020201"; // 단위상품
	public static final String PRCHS_PROD_TYPE_AUTH = "OR020202"; // 권한상품

	/** 구매유형코드: 구매 / 선물 */
	public static final String PRCHS_CASE_PURCHASE_CD = "OR020301"; // 구매
	public static final String PRCHS_CASE_GIFT_CD = "OR020302"; // 선물

	/** 상품 판매 상태 */
	public static final String PRODUCT_STATUS_SALE = "PD000403"; // 판매중
	public static final String PRODUCT_STATUS_FIXRATE_SALE = "PD013202"; // 정액권 상품 판매중 ;;

	/** 이용기간 단위 */
	public static final String PRODUCT_USE_PERIOD_UNIT_UNLIMITED = "PD00310";
	public static final String PRODUCT_USE_PERIOD_UNIT_HOUR = "PD00311";
	public static final String PRODUCT_USE_PERIOD_UNIT_DATE = "PD00312";
	public static final String PRODUCT_USE_PERIOD_UNIT_MONTH = "PD00313";
	public static final String PRODUCT_USE_PERIOD_UNIT_YEAR = "PD00314";
	public static final String PRODUCT_USE_PERIOD_UNIT_CURR_DATE = "PD00315";
	public static final String PRODUCT_USE_PERIOD_UNIT_CURR_MONTH = "PD00316";
	public static final String PRODUCT_USE_PERIOD_UNIT_CURR_YEAR = "PD00317";
	public static final String PRODUCT_USE_PERIOD_UNIT_MINUTE = "PD00318";
	public static final String PRODUCT_USE_PERIOD_UNIT_SELECT = "PD00319";

	/** 상품 연령 등급 */
	public static final String PRODUCT_GRADE_ALL = "PD004401"; // 전체 이용가
	public static final String PRODUCT_GRADE_12 = "PD004402"; // 12세 이용가
	public static final String PRODUCT_GRADE_15 = "PD004403"; // 15세 이용가
	public static final String PRODUCT_GRADE_19 = "PD004404"; // 청소년 이용불가

	/** 통신사 */
	public static final String TELECOM_SKT = "US001201"; // SKT
	public static final String TELECOM_KT = "US001202"; // KT
	public static final String TELECOM_UPLUS = "US001203"; // U+
	public static final String TELECOM_KCT = "US001298"; // KCT

	/** 회원 상태 */
	public static final String USER_STATUS_NORMAL = "US010201"; // 정상
	public static final String USER_STATUS_TEMP_JOIN = "US010203"; // 가가입

	/** 회원 타입 */
	public static final String USER_TYPE_MOBILE = "US011501"; // 모바일전용 회원
	public static final String USER_TYPE_ONEID = "US011503"; // ONE ID 회원

	/** 전송 분류 코드 */
	public static final String TRANS_CLAS_SELLER_MBR_NO = "OR020401"; // 판매자회원번호
	public static final String TRANS_CLAS_MALL_CD = "OR020402"; // 상점코드

	/** 판매금액 처리 타입 */
	public static final String SALE_AMT_PROC_TYPE_NORMAL = "OR020501"; // 일반 처리
	public static final String SALE_AMT_PROC_TYPE_SERVER = "OR020502"; // 서버 기준 처리 (요청 금액 무시)
	public static final String SALE_AMT_PROC_TYPE_REQUEST = "OR020503"; // 요청 기준 처리 (서버 금액 무시)

	/** 멤버쉽 적립타입 코드 */
	public static final String MEMBERSHIP_SAVE_TYPE_ALL = "OR020601"; // 전체적립
	public static final String MEMBERSHIP_SAVE_TYPE_PART = "OR020602"; // 부분적립
	public static final String MEMBERSHIP_SAVE_TYPE_OVER = "OR020603"; // 한도초과
	public static final String MEMBERSHIP_SAVE_TYPE_PROM_FINISH = "OR020604"; // 프로모션 종료

	/** 멤버쉽 타입 코드 */
	public static final String MEMBERSHIP_TYPE_CASH = "OR020701"; // 캐시
	public static final String MEMBERSHIP_TYPE_GAMECASH = "OR020702"; // 게임 캐시

	/** 멤버쉽 처리상태 코드 */
	public static final String MEMBERSHIP_PROC_STATUS_RESERVE = "OR020801"; // 적립예정
	public static final String MEMBERSHIP_PROC_STATUS_WORKING = "OR020802"; // 처리중
	public static final String MEMBERSHIP_PROC_STATUS_COMPLETE = "OR020803"; // 처리완료
	public static final String MEMBERSHIP_PROC_STATUS_FAIL = "OR020804"; // 처리실패
	public static final String MEMBERSHIP_PROC_STATUS_RESERVE_CANCEL = "OR020805"; // 적립예정취소
	public static final String MEMBERSHIP_PROC_STATUS_CANCEL = "OR020806"; // 적립취소

	/** SAP 결제완료 Noti 처리 타입 코드 */
	public static final String SAP_PURCHASE_NOTI_PROC_STATUS_RESERVE = "OR020901"; // 전송예정
	public static final String SAP_PURCHASE_NOTI_PROC_STATUS_SUCCESS = "OR020902"; // 전송성공
	public static final String SAP_PURCHASE_NOTI_PROC_STATUS_FAIL = "OR020903"; // 전송실패
	public static final String SAP_PURCHASE_NOTI_PROC_STATUS_HOLD = "OR020904"; // 전송보류

	/** PayPlanet API 타입 코드 */
	public static final String PAYPLANET_API_TYPE_PURCHASE = "OR021001"; // 구매(결제)요청
	public static final String PAYPLANET_API_TYPE_CANCEL = "OR021002"; // 결제취소
	public static final String PAYPLANET_API_TYPE_DCB_SYSTEM_DIVISION = "OR021003"; // SKT SYSTEM DIVISION

	/** 작업 처리 상태 코드 */
	public static final String PROCESSING_STATUS_STANDBY = "OR021101"; // 처리대기
	public static final String PROCESSING_STATUS_COMPLETE = "OR021102"; // 처리완료
	public static final String PROCESSING_STATUS_WORKING = "OR021103"; // 처리중
	public static final String PROCESSING_STATUS_FAIL = "OR021104"; // 처리실패

	/** 쿠폰 타입 */
	public static final String COUPON_TYPE_SPECIAL_PRICE_PRODUCT = "OR002406"; // 쿠폰목록 조회:특가상품쿠폰
	public static final String COUPON_TYPE_SHOPPING_SPECIAL_COUPON = "PD015104"; // 쿠폰목록 조회:쇼핑특가쿠폰

	/** Interface ID */
	public static final String INTERFACE_ID_TL_SAC_PUR_0006 = "TL_SAC_PUR_0006"; // Biz쿠폰 비동기 응답

	/** 이관결과코드 */
	public static final String TRC_RESULT_PLAN = "OR030101"; // 예정

	/** 판매자 타입 */
	public static final String SELLER_TYPE_COPORATION = "US010103"; // 법인 사업자
	public static final String SELLER_TYPE_INDIVISUAL = "US010101"; // 개인
	public static final String SELLER_TYPE_INDIVISUAL_COPORATION = "US010102"; // 개인 사업자

	/** 전시 상수 코드 **/
	public static final String DISPLAY_BOOK_CLFSCD_SERIAL = "DP004302"; // 연재
	public static final String DISPLAY_BOOK_CLFSCD_SEPARATE = "DP004301"; // 단행
	public static final String DISPLAY_SVC = "DP004301"; // 단행

	public static final String DISPLAY_SVCGRPCD_APP = "DP000201"; // 애플리케이션 (APP)
	public static final String DISPLAY_SVCGRPCD_MULTIMEDIA = "DP000203"; // 멀티미디어
	public static final String DISPLAY_SVCGRPCD_PHONE_DECO = "DP000204"; // 폰 꾸미기
	public static final String DISPLAY_SVCGRPCD_SOCIAL_SHOPPING = "DP000205"; // 소셜 쇼핑
	public static final String DISPLAY_SVCGRPCD_TSTORE_SHOPPING = "DP000206"; // Tstore 쇼핑
	public static final String DISPLAY_SVCGRPCD_TSTORE_FREEPASS = "DP000207"; // 복합상품(정액권)
}
