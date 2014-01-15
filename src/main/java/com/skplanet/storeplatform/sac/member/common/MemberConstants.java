package com.skplanet.storeplatform.sac.member.common;

/**
 * 회원 관련 상수
 * 
 * Updated on : 2014. 1. 9. Updated by : 김경복, 부르칸.
 */
public final class MemberConstants {

	/** 사용 여부 Y N. **/
	public static final String USE_Y = "Y"; // Y
	public static final String USE_N = "N"; // N

	/** SC회원 결과 코드. */
	public static final String RESULT_SUCCES = "0000"; // 성공
	public static final String RESULT_FAIL = "9999"; // 실패
	public static final String RESULT_NOT_FOUND_INPUT = "9998"; // 입력 파라미터가 없음
	public static final String RESULT_NOT_FOUND_COMMON = "9997"; // 공통 파라미터 없음
	public static final String RESULT_NOT_FOUND_TANENT_ID = "9996"; // 테넌트 아이디 없음
	public static final String RESULT_NOT_FOUND_USER_KEY = "9995"; // 사용자키 없음
	public static final String RESULT_NOT_FOUND_EDIT_INPUT_ITEM = "9994"; // 수정 또는 추가할 항목이 없음
	public static final String RESULT_NOT_FOUND_MANDATORY = "9993"; // 필수 파라미터 없음
	public static final String RESULT_WRONG_KEY_TYPE = "9992"; // 잘못된 키값
	public static final String RESULT_UNKNOWN_ERR = "9991"; // 알수 없는 에러
	public static final String RESULT_UNKNOWN_USER_ID = "9990"; // 존재하지 않는 사용자ID

	/** 사용자구분코드. */
	public static final String USER_TYPE_MOBILE = "US011501"; // 기기사용자
	public static final String USER_TYPE_IDPID = "US011502"; // IDP사용자
	public static final String USER_TYPE_ONEID = "US011503"; // OneID사용자

	/** 메인상태코드. */
	public static final String MAIN_STATUS_NORMAL = "US010201"; // 정상
	public static final String MAIN_STATUS_SECEDE = "US010202"; // 탈퇴(자의탈퇴/직권탈퇴)
	public static final String MAIN_STATUS_WATING = "US010203"; // 가가입
	public static final String MAIN_STATUS_PAUSE = "US010204"; // 일시정지(로그인제한/직권중지/7일이용정지/30일이용정지/영구이용정지)
	public static final String MAIN_STATUS_CONVERSION = "US010205"; // 전환

	/** 서브상태 코드. */
	public static final String SUB_STATUS_NORMAL = "US010301"; // 정상
	public static final String SUB_STATUS_SECEDE_MOTION = "US010302"; // 탈퇴신청
	public static final String SUB_STATUS_SECEDE_FINISH = "US010303"; // 탈퇴완료
	public static final String SUB_STATUS_JOIN_APPLY_ETC = "US010304"; // 가입승인 만료
	public static final String SUB_STATUS_JOIN_APPLY_WATING = "US010305"; // 가입승인 대기
	public static final String SUB_STATUS_EMAIL_CERT_WATING = "US010306"; // 이메일변경 승인 대기
	public static final String SUB_STATUS_LOGIN_PAUSE = "US010307"; // 로그인 제한
	public static final String SUB_STATUS_AUTHORITY_PAUSE = "US010308"; // 직권 중지
	public static final String SUB_STATUS_SEVEN_PAUSE = "US010309"; // 7일 이용정지
	public static final String SUB_STATUS_THIRTY_PAUSE = "US010310"; // 30일 이용정지
	public static final String SUB_STATUS_ETERNAL_PAUSE = "US010311"; // 영구 이용정지
	public static final String SUB_STATUS_TURN_MOTION = "US010312"; // 전환 신청
	public static final String SUB_STATUS_SEVEN_MOTION_AGAIN = "US010313"; // 전환 재신청
	public static final String SUB_STATUS_TURN_REJECT = "US010314"; // 전환 거절

	/** 검색 구분자. */
	public static final String KEY_TYPE_INSD_USERMBR_NO = "INSD_USERMBR_NO"; // 내부 사용자 키
	public static final String KEY_TYPE_MBR_ID = "MBR_ID"; // 사용자 ID
	public static final String KEY_TYPE_INSD_SELLERMBR_NO = "INSD_SELLERMBR_NO"; // 내부 판매자 키
	public static final String KEY_TYPE_SELLERMBR_ID = "SELLERMBR_ID"; // 판매자 ID
	public static final String KEY_TYPE_USERMBR_NO = "USERMBR_NO"; // 통합서비스 키
	public static final String KEY_TYPE_INSD_DEVICE_ID = "INSD_DEVICE_ID"; // 내부 기기 키
	public static final String KEY_TYPE_DEVICE_ID = "DEVICE_ID"; // 기기 ID
	public static final String KEY_TYPE_EMAIL_ADDR = "EMAIL_ADDR"; // 사용자 이메일
	public static final String KEY_TYPE_EMAIL = "EMAIL"; // 판매자 이메일
	public static final String KEY_TYPE_TEL_NO = "TEL_NO"; // 사용자 연락처
	public static final String KEY_TYPE_WILS_TEL_NO = "WILS_TEL_NO"; // 판매자 연락처

	/** 통신사 코드. */
	public static final String DEVICE_TELECOM_SKT = "US012101"; // SKT
	public static final String DEVICE_TELECOM_KT = "US012102"; // KT
	public static final String DEVICE_TELECOM_LGT = "US012103"; // U+
	public static final String DEVICE_TELECOM_OMD = "US012104"; // 자급제 단말
	public static final String DEVICE_TELECOM_NSH = "US012105"; // NSH
	public static final String DEVICE_TELECOM_NON = "US012106"; // NON
	public static final String DEVICE_TELECOM_IOS = "US012107"; // iOS

	/** 휴대기기 부가속성. */
	public static final String DEVICE_EXTRA_OMPDOWNLOADER_YN = "US011401"; // OMP DOWNLOADER 설치 여부
	public static final String DEVICE_EXTRA_STANDBYSCREEN_YN = "US011402"; // 대기화면 설정 여부
	public static final String DEVICE_EXTRA_IMMNGNUM = "US011403"; // 통합서비스관리번호
	public static final String DEVICE_EXTRA_UACD = "US011404"; // UA 코드
	public static final String DEVICE_EXTRA_OMPSUPPORT_YN = "US011405"; // OMP 지원 단말 여부
	public static final String DEVICE_EXTRA_OSVERSION = "US011406"; // OS 버전
	public static final String DEVICE_EXTRA_SCVERSION = "US011407"; // 샵클 버전
	public static final String DEVICE_EXTRA_APPSTATISTICS_YN = "US011408"; // 앱 사용통계 사용여부
	public static final String DEVICE_EXTRA_DODORYAUTH_DATE = "US011409"; // 도토리 인증일
	public static final String DEVICE_EXTRA_DODORYAUTH_YN = "US011410"; // 도토리 인증여부
	public static final String DEVICE_EXTRA_EMBEDDED_YN = "US011411"; // 임베디드 여부
	public static final String DEVICE_EXTRA_OMPUACE = "US011412"; // OMD UA코드
	public static final String DEVICE_EXTRA_ROOTING_YN = "US011413"; // 루팅 여부

	/** 미지원 단말. */
	public static final String NOT_SUPPORT_HP_CORP = "NSH";
	public static final String NOT_SUPPORT_HP_UACODE = "9999";
	public static final String NOT_SUPPORT_HP_MODEL_CD = "android_standard";
	public static final String NOT_SUPPORT_HP_MODEL_NM = "미지원표준단말";

	/** DeviceIdType 정의. */
	public static final String DEVICE_ID_TYPE_MSISDN = "msisdn";
	public static final String DEVICE_ID_TYPE_UUID = "uuid";
	public static final String DEVICE_ID_TYPE_MACADDRESS = "macaddress";

	/** 통합회원. */
	public static final String SSO_SST_CD_TSTORE = "41100";

	/**
	 * 판매자 회원 상수들
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
	}
}
