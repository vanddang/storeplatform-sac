package com.skplanet.storeplatform.sac.member.common.constant;

/**
 * 회원 관련 상수
 * 
 * Updated on : 2014. 1. 9. Updated by : 김경복, 부르칸.
 */
/**
 * Calss 설명
 * 
 * Updated on : 2014. 6. 26. Updated by : 김다슬, 인크로스.
 */
public final class MemberConstants {

	/** 사용 여부 Y N. **/
	public static final String USE_Y = "Y"; // Y
	public static final String USE_N = "N"; // N

	/** SC회원 결과 코드. */
	public static final String RESULT_SUCCES = "0000"; // 성공
	public static final String RESULT_FAIL = "9999"; // 값없음
	public static final String RESULT_NOT_FOUND_INPUT = "9998"; // 입력 파라미터가 없음
	public static final String RESULT_NOT_FOUND_COMMON = "9997"; // 공통 파라미터 없음
	public static final String RESULT_NOT_FOUND_TANENT_ID = "9996"; // 테넌트 아이디 없음
	public static final String RESULT_NOT_FOUND_USER_KEY = "9995"; // 사용자키 없음
	public static final String RESULT_NOT_FOUND_EDIT_INPUT_ITEM = "9994"; // 수정 또는 추가할 항목이 없음
	public static final String RESULT_NOT_FOUND_MANDATORY = "9993"; // 필수 파라미터 없음
	public static final String RESULT_WRONG_KEY_TYPE = "9992"; // 잘못된 키값
	public static final String RESULT_UNKNOWN_ERR = "9991"; // 알수 없는 에러
	public static final String RESULT_UNKNOWN_USER_ID = "9990"; // 존재하지 않는 사용자ID
	public static final String SC_ERROR_DARK = "SC_MEM_9991"; // SC 알수없는에러(보통 DB에 2건이상 존재할때)
	public static final String SC_ERROR_NO_DATA = "SC_MEM_9982"; // SC 검색결과 없음
	public static final String SC_ERROR_NO_USERKEY = "SC_MEM_9995"; // 회원키 없음
	public static final String SC_ERROR_DUPLICATED_DEVICE_ID = "SC_MEM_9985"; // 디바이스크 중복
	public static final String SC_ERROR_EDIT_INPUT_ITEM_NOT_FOUND = "SC_MEM_9994"; // 수정 또는 추가할 항목이 없음
	public static final String SC_ERROR_INSERT_OR_UPDATE = "SC_MEM_9984"; // insert or update 오류

	/** SAC회원 결과 코드. */
	public static final String SAC_ERROR_NO_ONEID = "SAC_MEM_1302"; // OneID 회원이 아님
    public static final String SAC_ERROR_NO_USERKEY = "SAC_MEM_0003"; // 회원키 없음

	/** EC IDP 에러 코드 문자열. */
	public static final String EC_IDP_ERROR_CODE_TYPE = "EC_IDP_";

	/** EC IDP 알수 없는 에러. */
	public static final String EC_IDP_ERROR_UNDEF = "EC_UNDEF_ERROR";

	/** 사용자구분코드. */
	public static final String USER_TYPE_MOBILE = "US011501"; // 기기사용자
	public static final String USER_TYPE_TSTORE = "US011502"; // Tstore ID 사용자
	@Deprecated
	public static final String USER_TYPE_IDPID = "US011502"; // IDP사용자
	@Deprecated
	public static final String USER_TYPE_ONEID = "US011503"; // OneID사용자
	public static final String USER_TYPE_NAVER = "US011504"; // 네이버ID사용자
	public static final String USER_TYPE_GOOGLE = "US011505"; // 구글ID사용자
	public static final String USER_TYPE_FACEBOOK = "US011506"; // 페이스북ID사용자

	/** 메인상태코드. */
	public static final String MAIN_STATUS_NORMAL = "US010201"; // 정상
	public static final String MAIN_STATUS_SECEDE = "US010202"; // 탈퇴
	public static final String MAIN_STATUS_WATING = "US010203"; // 가가입
	public static final String MAIN_STATUS_PAUSE = "US010204"; // 일시정지

	/** 서브상태 코드. */
	public static final String SUB_STATUS_NORMAL = "US010301"; // 정상
	public static final String SUB_STATUS_SECEDE_MOTION = "US010302"; // 탈퇴신청
	public static final String SUB_STATUS_SECEDE_FINISH = "US010303"; // 탈퇴완료
	public static final String SUB_STATUS_ACCT_APPLY_WATING = "US010305"; // 정산정보승인대기
	public static final String SUB_STATUS_JOIN_APPLY_WATING = "US010306"; // 이메일승인대기
	public static final String SUB_STATUS_SEVEN_PAUSE = "US010308"; // 7일 이용정지
	public static final String SUB_STATUS_THIRTY_PAUSE = "US010309"; // 30일 이용정지
	public static final String SUB_STATUS_ETERNAL_PAUSE = "US010310"; // 영구 이용정지
	public static final String SUB_STATUS_TURN_MOTION = "US010311"; // 개발자유형전환 신청
	public static final String SUB_STATUS_SEVEN_MOTION_AGAIN = "US010312"; // 개발자유형전환 재신청
	public static final String SUB_STATUS_TURN_REJECT = "US010313"; // 개발자유형전환 거절
	public static final String SUB_STATUS_CHANGE_USER = "US010314"; // 변동처리대상
	public static final String SUB_STATUS_ACCT_JOIN_REJECT = "US010315"; // 정산정보승인거절
	public static final String SUB_STATUS_SECEDE_REJECT = "US010316"; // 탈퇴거절
	public static final String SUB_STATUS_ID_REUSE = "US010317"; // ID재사용
	public static final String SUB_STATUS_ASSOCIATE_MEMBER = "US010319"; // 준회원

	/** 검색 구분자. */
	public static final String KEY_TYPE_INSD_USERMBR_NO = "INSD_USERMBR_NO"; // 내부 사용자 키
	public static final String KEY_TYPE_MBR_ID = "MBR_ID"; // 사용자 ID
	public static final String KEY_TYPE_INSD_SELLERMBR_NO = "INSD_SELLERMBR_NO"; // 내부 판매자 키
	public static final String KEY_TYPE_SELLERMBR_ID = "SELLERMBR_ID"; // 판매자 ID
	public static final String KEY_TYPE_USERMBR_NO = "USERMBR_NO"; // 통합서비스 키
	public static final String KEY_TYPE_INSD_DEVICE_ID = "INSD_DEVICE_ID"; // 내부 기기 키
	public static final String KEY_TYPE_DEVICE_ID = "DEVICE_ID"; // 기기 ID
	public static final String KEY_TYPE_SVC_MANG_NO = "SVC_MANG_NO"; // 서비스 관리번호
	public static final String KEY_TYPE_INTG_SVC_NO = "INTG_SVC_NO"; // OneID 회원키
	public static final String KEY_TYPE_EMAIL_ADDR = "EMAIL_ADDR"; // 사용자 이메일
	public static final String KEY_TYPE_EMAIL = "EMAIL"; // 판매자 이메일
	public static final String KEY_TYPE_TEL_NO = "TEL_NO"; // 사용자 연락처
	public static final String KEY_TYPE_WILS_TEL_NO = "WILS_TEL_NO"; // 판매자 연락처
	public static final String KEY_TYPE_SELLERMBR_BIZ_NO = "BIZ_REG_NO"; // 사업자 등록번호
	public static final String KEY_TYPE_MDN = "MDN"; // 전화번호
	public static final String KEY_TYPE_AUTHORIZE_DEVICE_ID = "AUTHORIZE_DEVICE_ID"; // 기기 ID (모바일 인증 시)
    public static final String KEY_TYPE_AUTHORIZE_SVC_MANG_NO = "AUTHORIZE_SVC_MANG_NO"; // 서비스 관리번호 (모바일 인증 시)
	public static final String KEY_TYPE_AUTHORIZE_MDN = "AUTHORIZE_MDN"; // MDN (모바일 인증 시)

	/** 통신사 코드. */
	public static final String DEVICE_TELECOM_SKT = "US001201"; // SKT
	public static final String DEVICE_TELECOM_KT = "US001202"; // KT
	public static final String DEVICE_TELECOM_LGT = "US001203"; // U+
	public static final String DEVICE_TELECOM_OMD = "US001204"; // 자급제 단말
	public static final String DEVICE_TELECOM_NSH = "US001205"; // NSH
	public static final String DEVICE_TELECOM_NON = "US001206"; // NON
	public static final String DEVICE_TELECOM_IOS = "US001207"; // iOS
	public static final String DEVICE_TELECOM_SKM = "US001208"; // SKM
	public static final String DEVICE_TELECOM_KTM = "US001209"; // KTM
	public static final String DEVICE_TELECOM_LGM = "US001210"; // LGM

	/** 통신사 코드명. */
	public static final String NM_DEVICE_TELECOM_SKT = "SKT"; // SKT
	public static final String NM_DEVICE_TELECOM_KT = "KT"; // KT
	public static final String NM_DEVICE_TELECOM_LGT = "U+"; // U+
	public static final String NM_DEVICE_TELECOM_OMD = "OMD"; // 자급제 단말
	public static final String NM_DEVICE_TELECOM_NSH = "NSH"; // NSH
	public static final String NM_DEVICE_TELECOM_NON = "NON"; // NON
	public static final String NM_DEVICE_TELECOM_IOS = "ISB"; // iOS
	public static final String NM_DEVICE_TELECOM_SKM = "SKM"; // SKM
	public static final String NM_DEVICE_TELECOM_KTM = "KTM"; // KTM
	public static final String NM_DEVICE_TELECOM_LGM = "LGM"; // LGM

	/** 사용자 부가속성. */
	public static final String USER_EXTRA_GROP_CD = "US0109"; // 부가속성 그룹 코드
	public static final String USER_EXTRA_CERTIFICATION = "US010901"; // 공인인증여부
	public static final String USER_EXTRA_SKTBILLSEPARATION = "US010902"; // SKT 청구서 분리 여부
	public static final String USER_EXTRA_FACEBOOKACCESSTOKEN = "US010903"; // Facebook Access Token 정보
	public static final String USER_EXTRA_FACEBOOKPURCHASE = "US010904"; // Facebook 구매 연동 여부
	public static final String USER_EXTRA_FACEBOOKRATING = "US010905"; // Facebook 평점 연동 여부
	public static final String USER_EXTRA_FACEBOOKREVIEW = "US010906"; // Facebook 후기 연동 여부
	public static final String USER_EXTRA_MEMBERPOINTJOIN = "US010907"; // 통합포인트 가입 여부
	public static final String USER_EXTRA_FACEBOOK_ID = "US010911"; // Facebook ID
	public static final String USER_EXTRA_PROFILEIMGPATH = "US010912"; // 프로필 이미지 경로

	// 2015-07-28 추가 적용.
	public static final String USER_EXTRA_SOCIAL_ACCT_TYPE = "US010913";
	public static final String USER_EXTRA_SOCIAL_ACCT_ID = "US010914";
	public static final String USER_EXTRA_SOCIAL_ACCT_INT_ID = "US010915";
	public static final String USER_EXTRA_SOCIAL_EMAIL = "US010916";
	public static final String USER_EXTRA_SOCIAL_ACCT_TOKEN = "US010917";
	public static final String USER_EXTRA_SOCIAL_REF_TOKEN = "US010918";
	public static final String USER_EXTRA_SOCIAL_EXPIRED_TIME = "US010919";

	public static final String USER_EXTRA_SYRUP_SSO_CREDENTIAL = "US010920"; // syrup Pay에서 발행하는 자격증명 값
	public static final String USER_EXTRA_SOCIL_MEMBER_NO = "US010922"; // social 아이디 회원번호
    // 2016-02-02 추가
	public static final String USER_EXTRA_SOCIAL_NAVER_ID = "US010921"; // NaverId

	public static final String USER_EXTRA_FACEBOOK = "facebook";
	public static final String USER_EXTRA_KAKAO = "kakao";
	public static final String USER_EXTRA_GOOGLE = "google";

	/** 휴대기기 부가속성. */
	public static final String DEVICE_EXTRA_OMPDOWNLOADER_YN = "US011401"; // OMP DOWNLOADER 설치 여부
	public static final String DEVICE_EXTRA_STANDBYSCREEN_YN = "US011402"; // 대기화면 설정 여부
	public static final String DEVICE_EXTRA_UACD = "US011403"; // UA 코드
	public static final String DEVICE_EXTRA_OMPSUPPORT_YN = "US011404"; // OMP 지원 단말 여부
	public static final String DEVICE_EXTRA_OSVERSION = "US011405"; // OS 버전
	public static final String DEVICE_EXTRA_SCVERSION = "US011406"; // 샵클 버전
	@Deprecated
	public static final String DEVICE_EXTRA_APPSTATISTICS_YN = "US011407"; // 앱 사용통계 사용여부
	@Deprecated
	public static final String DEVICE_EXTRA_DODORYAUTH_DATE = "US011408"; // 도토리 인증일
	@Deprecated
	public static final String DEVICE_EXTRA_DODORYAUTH_YN = "US011409"; // 도토리 인증여부
	public static final String DEVICE_EXTRA_EMBEDDED_YN = "US011410"; // 임베디드 여부
	public static final String DEVICE_EXTRA_OMDUACD = "US011411"; // OMD UA코드
	public static final String DEVICE_EXTRA_ROOTING_YN = "US011412"; // 루팅 여부
	@Deprecated
	public static final String DEVICE_EXTRA_TCLOUD_SUPPORT_YN = "US011413"; // Tcloud 약관동의 여부
	public static final String DEVICE_EXTRA_PUSH_YN = "US011414"; // PUSH 알림 수신 동의 여부
	public static final String DEVICE_EXTRA_LIMIT_CHARGE_YN = "US011415"; // 한도요금제 여부

	/** 기기변경 유형 코드. */
	public static final String DEVICE_CHANGE_TYPE_USER_SELECT = "US012001"; // 사용자선택
	public static final String DEVICE_CHANGE_TYPE_MODEL_CHANGE = "US012002"; // 기기변경
	public static final String DEVICE_CHANGE_TYPE_NUMBER_CHANGE = "US012003"; // 번호변경
	public static final String DEVICE_CHANGE_TYPE_NAME_CHANGE = "US012004"; // 명의변경
	public static final String DEVICE_CHANGE_TYPE_NUMBER_SECEDE = "US012005"; // 단말해지
	public static final String DEVICE_CHANGE_TYPE_JOIN_COMPLETE = "US012006"; // 가입승인
	public static final String DEVICE_CHANGE_TYPE_EMAIL_COMPLETE = "US012007"; // 이메일변경승인
	public static final String DEVICE_CHANGE_TYPE_EMAIL_JOIN_COMPLETE = "US012008"; // 가입승인만료
	public static final String DEVICE_CHANGE_TYPE_MODIFY_PROFILE = "US012009"; // 회원정보수정
	public static final String DEVICE_CHANGE_TYPE_JOIN_ECG = "US012010"; // 부가서비스가입
	public static final String DEVICE_CHANGE_TYPE_SECEDE_ECG = "US012011"; // 부가서비스해지
	public static final String DEVICE_CHANGE_TYPE_NUMBER_MOVE = "US012012"; // 번호이동
	public static final String DEVICE_CHANGE_TYPE_IMEI_CHANGE = "US012013"; // imei변경
	public static final String DEVICE_CHANGE_TYPE_GMAIL_CHANGE = "US012014"; // gmail변경
	public static final String DEVICE_CHANGE_TYPE_SMS_CHANGE = "US012015"; // SMS 수신동의 변경
	public static final String DEVICE_CHANGE_TYPE_DEVICEID_CHANGE = "US012016"; // deviceId변경(oneStore 재설치) // TODO. 공통코드 추가 필요!!

	/** 미지원 단말. */
	public static final String NOT_SUPPORT_HP_CORP = "NSH";
	public static final String NOT_SUPPORT_HP_UACODE = "9999";
	public static final String NOT_SUPPORT_HP_MODEL_CD = "android_standard";
	public static final String NOT_SUPPORT_HP_MODEL_NM = "미지원표준단말";

	/**
	 * 가상 프로비저닝 모델명 (앱).
	 */
	public static final String DP_ANY_PHONE_4APP = "ANY-PHONE-4APP";
	public static final String DP_ANY_PHONE_4APP_NM = "애니폰APP";

	/**
	 * 가상 프로비저닝 모델명 (멀티미디어).
	 */
	public static final String DP_ANY_PHONE_4MM = "ANY-PHONE-4MM";

	/** DeviceIdType 정의. */
	public static final String DEVICE_ID_TYPE_MSISDN = "msisdn";
	public static final String DEVICE_ID_TYPE_UUID = "uuid";
	public static final String DEVICE_ID_TYPE_MACADDRESS = "macaddress";
	public static final String DEVICE_ID_TYPE_IMEI = "imei";

	/** 실명인증 수단 코드 판단 값. */
	public static final String REAL_NAME_AUTH_TYPE_MOBILE = "1"; // 휴대폰
	public static final String REAL_NAME_AUTH_TYPE_IPIN = "2"; // IPIN
	public static final String REAL_NAME_AUTH_TYPE_ETC = "9"; // 기타

	/** 실명인증 수단 코드. */
	public static final String REAL_NAME_AUTH_MOBILE = "US011101"; // 휴대폰인증
	public static final String REAL_NAME_AUTH_IPIN = "US011102"; // IPIN인증
	public static final String REAL_NAME_AUTH_EMAIL = "US011103"; // 이메일인증

	/** 통합회원. */
	public static final String SSO_SST_CD_TSTORE = "41100";
	public static final String SSO_SST_CD_OCB_WEB = "42100"; // OCB 서비스 사이트코드

	/** 실명인증 사이트 코드 (realNameSite). */
	public static final String AUTH_CHNL_WEB = "US011201"; // 웹
	public static final String AUTH_CHNL_MOBILE_2X = "US011202"; // MobileClient2.X
	public static final String AUTH_CHNL_MOBILE_3X = "US011203"; // MobileClient3.X\
	public static final String AUTH_CHNL_MOBILE_WEB = "US011204"; // 모바일웹
	public static final String AUTH_CHNL_MOBILE_OPENAPI = "US011205"; // OPENAPI

	/** 실명인증 대상. */
	public static final String AUTH_TYPE_OWN = "OWN"; // 본인
	public static final String AUTH_TYPE_PARENT = "PARENT"; // 법정대리인
	public static final String AUTH_TYPE_CORP = "CORP"; // 법인

	/** 통합회원 로그인 상태코드. */
	public static final String USER_LOGIN_STATUS_NOMAL = "10"; // 로그인 가능
	public static final String USER_LOGIN_STATUS_PAUSE = "20"; // 로그인 제한

	/** 통합회원 직권중지 상태코드. */
	public static final String USER_STOP_STATUS_NOMAL = "80"; // 직권중지 해제
	public static final String USER_STOP_STATUS_PAUSE = "90"; // 직권중지 설정

	/** 법정대리인 관계 코드. */
	public static final String PARENT_TYPE_FATHER = "US011801"; // 아버지
	public static final String PARENT_TYPE_MOTHER = "US011802"; // 어머니
	public static final String PARENT_TYPE_ECT = "US011803"; // 기타

	/** 탈퇴유형 코드. */
	public static final String USER_WITHDRAW_CLASS_CHANGE_ID = "US010701"; // ID 전환
	public static final String USER_WITHDRAW_CLASS_USER_SELECTED = "US010702"; // 사용자선택
	public static final String USER_WITHDRAW_CLASS_PROVISIONING = "US010703"; // 프로비저닝
	public static final String USER_WITHDRAW_CLASS_USER_DEVICE = "US010704"; // 단말이동
	public static final String USER_WITHDRAW_CLASS_JOIN_AGREE_EXPIRED = "US010705"; // 가입승인만료

	/** 약관 항목 코드. */
	public static final String POLICY_AGREEMENT_CLAUSE_ONEID = "US010601"; // ONEID이용약관
	public static final String POLICY_AGREEMENT_CLAUSE_INDIVIDUAL_INFO_HANDLE_ONEID = "US010602"; // ONEID개인정보취급방침
	public static final String POLICY_AGREEMENT_CLAUSE_TSTORE = "US010603"; // TSTORE이용약관(TAC001)
	public static final String POLICY_AGREEMENT_CLAUSE_CASH = "US010604"; // TSTORE캐쉬이용약관(TAC003)
	public static final String POLICY_AGREEMENT_CLAUSE_INDIVIDUAL_INFO_HANDLE_TSTORE = "US010605"; // TSTORE개인정보취급방침
	public static final String POLICY_AGREEMENT_CLAUSE_PROTECT_YOUTH = "US010606"; // TSTORE청소년보호정책
	public static final String POLICY_AGREEMENT_CLAUSE_APP_STATS_INFO_USE = "US010607"; // TSTORE앱이용통계정보활용
	public static final String POLICY_AGREEMENT_CLAUSE_MARKETING = "US010608"; // TSTORE정보광고활용(TAC006)
	public static final String POLICY_AGREEMENT_CLAUSE_COMMUNICATION_CHARGE = "US010609"; // TSTORE통신과금서비스(TAC002)
	public static final String POLICY_AGREEMENT_CLAUSE_INDIVIDUAL_INFO_HANDLE_OTHERS = "US010611"; // 개인정보의3자제공(TAC005)
	public static final String POLICY_AGREEMENT_CLAUSE_INDIVIDUAL_SAVE = "US010612"; // 개인정보 수집 및 이용안내(TAC004)

	/** OneId 약관 코드 */
	public static final String ONEID_AGREEMENT_CLAUSE_TAC001 = "TAC001"; // TSTORE이용약관
	public static final String ONEID_AGREEMENT_CLAUSE_TAC002 = "TAC002"; // TSTORE통신과금서비스
	public static final String ONEID_AGREEMENT_CLAUSE_TAC003 = "TAC003"; // TSTORE캐쉬이용약관
	public static final String ONEID_AGREEMENT_CLAUSE_TAC004 = "TAC004"; // 개인정보 수집 및 이용안내
	public static final String ONEID_AGREEMENT_CLAUSE_TAC005 = "TAC005"; // 개인정보의3자제공
	public static final String ONEID_AGREEMENT_CLAUSE_TAC006 = "TAC006"; // TSTORE정보광고활용

	/**
	 * 사용자 제한 정책 코드.
	 */
	public static final String USER_LIMIT_POLICY_TESTER = "US011702"; // 단말 검증자
	public static final String USER_LIMIT_POLICY_SERVICE_STOP_TSTORE = "OR003101"; // 서비스 단품 제한
	public static final String USER_LIMIT_POLICY_SERVICE_STOP_NATECA = "OR003103"; // 서비스 부분 제한

	/**
	 * 게임센터 관련 상수.
	 */
	public static final String GAMECENTER_WORK_CD_MOBILENUMBER_CHANGE = "US003201"; // 번호변경
	public static final String GAMECENTER_WORK_CD_NAME_CHANGE = "US003202"; // 명의변경
	public static final String GAMECENTER_WORK_CD_USER_SECEDE = "US003203"; // 회원탈퇴
	public static final String GAMECENTER_WORK_CD_MOBILENUMBER_INSERT = "US003204"; // MDN등록
	public static final String GAMECENTER_WORK_CD_MOBILENUMBER_DELETE = "US003205"; // MDN삭제
	public static final String GAMECENTER_WORK_CD_USER_CHANGE = "US003206"; // 회원전환
	public static final String GAMECENTER_WORK_CD_IMUSER_CHANGE = "US003207"; // 통합회원전환

	/**
	 * 가입자 상태 코드.
	 */
	public static final String JOIN_STATUS_CODE_NORMAL = "10"; // 정상
	public static final String JOIN_STATUS_CODE_HALF_AUTH = "11"; // 가인증

	/**
	 * 로그인시 휴대기기 정보 비교 필드.
	 */
	public static final String LOGIN_DEVICE_EQUALS_DEVICE_TELECOM = "deviceTelecom";
	public static final String LOGIN_DEVICE_EQUALS_DEVICE_ACCOUNT = "deviceAccount";
	public static final String LOGIN_DEVICE_EQUALS_NATIVE_ID = "nativeId";

	/*
	 * MDN이 소속된 통신사 정보 0: SKT, 1: non-SKT, 2: KTF, 3: LGT
	 */
	public static final String MDN_CARRIER_DEVICE_TELECOM_SKT = "SKT"; // SKT
	public static final String MDN_CARRIER_DEVICE_TELECOM_KTF = "KTF"; // KTF
	public static final String MDN_CARRIER_DEVICE_TELECOM_LGT = "LGT"; // LGT

	/** Non-specific TenantID */
	public static final String TENANT_ID_NON_SPECIFIC = "S00";
	/** T store TenantID */
	public static final String TENANT_ID_TSTORE = "S01";
	public static final String TENANT_ID_OLLEH_MARKET = "S02";
	public static final String TENANT_ID_UPLUS_STORE = "S03";

	/** 개발자센터 SystemID */
	public static final String SYSTEM_ID_DEV_POC = "S00-02001";
	/** 모바일웹 SystemID */
	public static final String SYSTEM_ID_MOBILE_WEB = "S01-02001";
	/** 티스토어 포털웹 SystemID */
	public static final String SYSTEM_ID_TSTORE_PORTAL_WEB = "S01-03001";

	/** IN APP 2.0 SystemID */
	public static final String SYSTEM_ID_INAPP_2 = "S01-11002";
	/** Pay Planet T store SystemID */
	public static final String SYSTEM_ID_PAYPLANET_S01 = "S01-14001";
	/** Pay Planet K store SystemID */
	public static final String SYSTEM_ID_PAYPLANET_S02 = "S02-14001";
	/** Pay Planet U store SystemID */
	public static final String SYSTEM_ID_PAYPLANET_S03 = "S03-14001";

	/** SMS 인증 타입 코드 */
	public static final String AUTH_TYPE_CD_SMS = "CM010901";
	/** EMAIL 인증 타입 코드 */
	public static final String AUTH_TYPE_CD_EMAIL = "CM010902";
	/** 변경 인증 EMAIL 인증 타입 코드 */
	public static final String AUTH_TYPE_CD_EMAIL_AUTH_URL = "CM010903";
	/** PIN 인증 타입 코드 */
	public static final String AUTH_TYPE_CD_PIN = "CM010904";

	/** DCD 가입 코드 */
	@Deprecated
	public static final String DCD_REG_CD = "4097";
	/** DCD 가입 상품 아이디 */
	@Deprecated
	public static final String DCD_REG_PROD_ID = "A000Z00001";

	/** InApp 회원상태코드. */
	public static final String INAPP_USER_STATUS_NORMAL = "US014601"; // 성공(정상회원)
	public static final String INAPP_USER_STATUS_PAUSE = "US014602"; // 이용제한 회원
	public static final String INAPP_USER_STATUS_NO_MEMBER = "US014603"; // 비회원
	public static final String INAPP_USER_STATUS_IMEI_MISMATCH = "US014604"; // IMEI 불일치
	public static final String INAPP_USER_STATUS_USIM_MISMATCH = "US014605"; // USIM 불일치
	public static final String INAPP_USER_STATUS_PARAM_ERROR = "US014606"; // 파라메터 오류
	public static final String INAPP_USER_STATUS_SYSTEM_ERROR = "US014607"; // 시스템 연동 오류
	public static final String INAPP_USER_STATUS_NOT_PURCHASE = "US014608"; // 구매불가회원

	/** InApp 회원상태코드 메세지. */
	public static final String INAPP_USER_STATUS_NORMAL_TEXT = "성공(정상회원)";
	public static final String INAPP_USER_STATUS_PAUSE_TEXT = "이용제한 회원";
	public static final String INAPP_USER_STATUS_NO_MEMBER_TEXT = "비회원";
	public static final String INAPP_USER_STATUS_IMEI_MISMATCH_TEXT = "IMEI 불일치";
	public static final String INAPP_USER_STATUS_USIM_MISMATCH_TEXT = "USIM 불일치";
	public static final String INAPP_USER_STATUS_PARAM_ERROR_TEXT = "파라메터 오류";
	public static final String INAPP_USER_STATUS_SYSTEM_ERROR_TEXT = "시스템 연동 오류";

	/** SAP 회원의 연령대 정보 */
	public static final String PROD_EXPO_LEVL_19_MORE = "US014705"; // 19세 이상
	public static final String PROD_EXPO_LEVL_18_MORE = "US014704"; // 18세 이상
	public static final String PROD_EXPO_LEVL_15_MORE = "US014703"; // 15세 이상
	public static final String PROD_EXPO_LEVL_12_MORE = "US014702"; // 12세 이상
	public static final String PROD_EXPO_LEVL_12_UNDER = "US014701"; // 12세 미만

	/** SMS발송 테넌트 구분 */
	public static final String SMS_SENDER_GRP_CD = "US0122"; // SMS발송 테넌트 구분 그룹 코드
	public static final String SMS_SENDER_TENANT_ID_TSTORE = "US012201"; // 티스토어
	public static final String SMS_SENDER_TENANT_ID_UPLUS_STORE = "US012202"; // U+ 스토어
	public static final String SMS_SENDER_TENANT_ID_OLLEH_MARKET = "US012203"; // 올레마켓

	/** 회원 상태 정상 처리 코드. */
	public static final String USER_MOVE_TYPE_ACTIVATE = "US012301";
	/** 회원 상태 휴면 처리 코드. */
	public static final String USER_MOVE_TYPE_DORMANT = "US012302";

	/** 등록. */
	public static final String TYPE_CREATE = "CREATE";
	/** 수정. */
	public static final String TYPE_MODIFY = "UPDATE";

	/** SMS SendOrder 1. */
	public static final String SMS_SEND_ORDER_FIRST = "1";

    /** 회원 사용자 성별 코드 */
    public static final String SEX_TYPE_MALE = "M"; // 남자
    public static final String SEX_TYPE_FEMALE = "F"; // 여자

	/**
	 * 판매자 회원 상수들.
	 * 
	 * Updated on : 2014. 1. 9. Updated by : 김경복, 부르칸.
	 */
	public static class SellerConstants {
		/** 판매자 구분. */
		public static final String SELLER_TYPE_PRIVATE_PERSON = "US010101"; // 개인
		public static final String SELLER_TYPE_PRIVATE_BUSINESS = "US010102"; // 개인 사업자
		public static final String SELLER_TYPE_LEGAL_BUSINESS = "US010103"; // 법인 사업자

		/** 판매자 분류. */
		public static final String SELLER_TYPE_NOPAY = "US011301"; // 무료
		public static final String SELLER_TYPE_PAY = "US011302"; // 유료
		public static final String SELLER_TYPE_BP = "US011303"; // BP

		public static final String SELLER_REASON_CHANGE = "change"; // 변경
		public static final String SELLER_REASON_NEW = "new"; // 신규

		public static final String SELLER_APP_DISPLAY_TOP = "Top";
		public static final String SELLER_APP_DISPLAY_LOWER = "Lower";
	}
}
