package com.skplanet.storeplatform.sac.external.idp;

import org.springframework.stereotype.Service;

import com.skplanet.storeplatform.sac.external.idp.constant.ImIDPManagerConstants;

@Service
public class ImIDPManager implements ImIDPManagerConstants {

	/** OMP 서비스 도메인 */
	public static String OMP_SERVICE_DOMAIN = "www.omp.com";
	/** OMP 서비스 URL */
	public static String OMP_SERVICE_URL = "omp.dev.service.url";
	/** OMP 서비스 URL (HTTPS) */
	public static String OMP_SERVICE_URL_HTTPS = "omp.dev.service.url.https";
	/** OMP 서비스 URL IP */
	public static String OMP_SERVICE_URL_IP = "omp.dev.service.url.ip";

	/** IDP 요청 도메인 (HTTP) */
	public static String IDP_REQUEST_URL = "http://idp.innoace.com";
	/** IDP 요청 도메인 (HTTPS) */
	public static String IDP_REQUEST_URL_HTTPS = "https://idp.innoace.com"; //

	/** IDP에 등록한 OMP Association Key */
	public static String IDP_REQ_OMP_ASSOC_KEY = "6b0cc48e477f066b7ef353a4f9e8b756";
	/** IDP로 부터 발급된 Service ID */
	public static String IDP_REQ_OMP_SERVICE_ID = "OMP10000";

	/* One ID 2.0 Portal Inbound Url Information */
	public static String ONEID_PORTAL_DOMAIN = "https://www.skplanetoneid.com"; // domain
	public static String ONEID_PORTAL_JOIN = "/extif/sst/join.do"; // 가입
	public static String ONEID_PORTAL_TRANS_JOIN = "/extif/sst/transJoin.do"; // 바꾸기 (기존회원을 one id portal로 전환
	public static String ONEID_PORTAL_TRANS_JOIN_POINT = "/extif/sst/transJoinPoint.do";// 포인트 가입 처리 바꾸기
	public static String ONEID_PORTAL_AGREE = "/extif/sst/agree.do"; // 서비스 이용동의
	public static String ONEID_PORTAL_AGREE_POINT = "/extif/sst/agree.do"; // 서비스 이용동의
	public static String ONEID_PORTAL_DELETE = "/extif/sst/delete.do"; // One ID 탈퇴
	public static String ONEID_PORTAL_UNLOCKUSER = "/extif/sst/unlockUser.do"; // 아이디 잠금 해지
	public static String ONEID_PORTAL_SEARCHID = "/login/searchIdForm.do"; // 아이디 찾기
	public static String ONEID_PORTAL_SEARCHPASSWORD = "/login/searchPasswordForm.do"; // 비밀번호 재설정
	public static String ONEID_PORTAL_GUIDE = "/guide/integrateGuide.do"; // 기존아이디 통합안내
}
