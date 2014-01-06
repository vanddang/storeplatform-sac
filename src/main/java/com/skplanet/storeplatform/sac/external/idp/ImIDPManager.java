package com.skplanet.storeplatform.sac.external.idp;

import org.springframework.stereotype.Service;

import com.skplanet.storeplatform.sac.external.idp.constant.ImIDPManagerConstants;

@Service
public class ImIDPManager implements ImIDPManagerConstants {

	/** OMP 서비스 도메인 */
	public static String OMP_SERVICE_DOMAIN = "";
	/** OMP 서비스 URL */
	public static String OMP_SERVICE_URL = "";
	/** OMP 서비스 URL (HTTPS) */
	public static String OMP_SERVICE_URL_HTTPS = "";
	/** OMP 서비스 URL IP */
	public static String OMP_SERVICE_URL_IP = "";

	/** IDP 요청 도메인 (HTTP) */
	public static String IDP_REQUEST_URL = "";
	/** IDP 요청 도메인 (HTTPS) */
	public static String IDP_REQUEST_URL_HTTPS = ""; //

	/** IDP에 등록한 OMP Association Key */
	public static String IDP_REQ_OMP_ASSOC_KEY = "";
	/** IDP로 부터 발급된 Service ID */
	public static String IDP_REQ_OMP_SERVICE_ID = "";

	public static String IDP_OPERATION_MODE = "";

	/* One ID 2.0 Portal Inbound Url Information */
	// public static String ONEID_PORTAL_DOMAIN = "";// domain
	// public static String ONEID_PORTAL_JOIN = "";// 가입
	// public static String ONEID_PORTAL_TRANS_JOIN = "";// 바꾸기 (기존회원을 one id portal로 전환 )
	// public static String ONEID_PORTAL_TRANS_JOIN_POINT = "";// 포인트 가입 처리 바꾸기
	// public static String ONEID_PORTAL_AGREE = "";// 서비스 이용동의
	// public static String ONEID_PORTAL_AGREE_POINT = "";// 포인트 가입 처리 이용동의
	// public static String ONEID_PORTAL_DELETE = ""; // One ID 탈퇴
	// public static String ONEID_PORTAL_UNLOCKUSER = "";// 아이디 잠금 해지
	// public static String ONEID_PORTAL_SEARCHID = "";// 아이디 찾기
	// public static String ONEID_PORTAL_SEARCHPASSWORD = "";// 비밀번호 재설정
	// public static String ONEID_PORTAL_GUIDE = "";// 기존아이디 통합안내

}
