/*
 * COPYRIGHT(c) SK telecom 2009
 * This software is the proprietary information of SK telecom.
 *
 * Revision History
 * Author       Date            Description
 * --------     ----------      ------------------
 * ?            ?               ?
 * nefer        2009.12.08      attach
 *
 */
package com.skplanet.storeplatform.sac.external.idp.constant;

import java.util.Observable;
import java.util.Observer;

public final class Constants implements Observer {

	/**
	 * config.properties에서 정보 가져옴
	 * 
	 */
	// static public Configuration config = null;

	/** 유료개발자 유형코드 */
	public static final String MEM_DEV_TYPE_PRIVATE_PERSON = "US000401"; // 개인셀러
	public static final String MEM_DEV_TYPE_PRIVATE_BUSINESS = "US000402"; // 개인사업자셀러
	public static final String MEM_DEV_TYPE_LEGAL_BUSINESS = "US000403"; // 법인사업자셀러
	public static final String MEM_DEV_TYPE_FOREIGN = "US000404"; // 해외개발자

	/** 유료개발자 회원상태코드 */
	public static final String MEM_DEV_STATUS_REG_MOTION = "US000801"; // 가입신청
	public static final String MEM_DEV_STATUS_REG_FINISH = "US000802"; // 가입완료
	public static final String MEM_DEV_STATUS_REG_REJECT = "US000803"; // 가입거절
	public static final String MEM_DEV_STATUS_TURN_MOTION = "US000804"; // 전환신청
	public static final String MEM_DEV_STATUS_TURN_FINISH = "US000805"; // 전환완료
	public static final String MEM_DEV_STATUS_TURN_REJECT = "US000806"; // 전환거절
	public static final String MEM_DEV_STATUS_LEAVE_MOTION = "US000807"; // 탈퇴신청
	public static final String MEM_DEV_STATUS_LEAVE_FINISH = "US000808"; // 탈퇴완료
	public static final String MEM_DEV_STATUS_REG_MOTION_AGAIN = "US000809"; // 가입재신청
	public static final String MEM_DEV_STATUS_TURN_REG_MOTION = "US000810"; // 준회원 전환신청

	/** 회원탈퇴신청유형코드 */
	public static final String END_REQ_TYPE_USER_POC = "US002001"; // 사용자POC 탈퇴
	public static final String END_REQ_TYPE_DEV_POC = "US002002"; // 개발자POC 탈퇴
	public static final String END_REQ_TYPE_BOTH_POC = "US002003"; // 사용자/개발자POC 동시탈퇴

	/** 회원분류코드 */
	public static final String MEM_TYPE_MOBILE = "US000201"; // 모바일 전용 회원
	public static final String MEM_TYPE_NORMAL = "US000202"; // 일반사용자 회원
	public static final String MEM_TYPE_NORMAL_DEV_NOPAY = "US000203"; // 일반무료개발
	public static final String MEM_TYPE_NORMAL_DEV_PAY = "US000204"; // 일반유료개발
	public static final String MEM_TYPE_DEV_NOPAY = "US000205"; // 무료개발
	public static final String MEM_TYPE_DEV_PAY = "US000206"; // 유료개발
	public static final String MEM_TYPE_BP = "US000207"; // BP

	/** 채널코드 */
	public static final String CHANEL_CD_PCSUITE = "US001001";
	public static final String CHANEL_CD_WEB = "US001002";
	public static final String CHANEL_CD_WAP = "US001003";
	public static final String CHANEL_CD_MOBILE = "US001004";

	//
	// static {
	// config = new PropertiesConfiguration();
	// }
	//
	// public void update(Observable o, Object obj) {
	// config = new PropertiesConfiguration();
	//
	// }

	/**
	 * ========================================= 업무명 정의 ========================================= 사용자 POC 업무
	 * ----------------------------------------- User Product Browsing Order Settlement Community Personalization
	 * Interface
	 * 
	 * 
	 * 개발자 POC 업무 ----------------------------------------- TODO 추가 해야함
	 * 
	 * 
	 * 운영자 POC 업무 ----------------------------------------- TODO 추가 해야함
	 */
	public static final String USERPOC_APPLICATION = "application";
	public static final String USERPOC_BROWSING = "browsing";
	public static final String USERPOC_COMMONS = "commons";
	public static final String USERPOC_COMMUNITY = "commuity";
	public static final String USERPOC_DEVICE = "device";
	public static final String USERPOC_DOWNLOAD_PCSUITE = "download_pcsuite";
	public static final String USERPOC_GAME = "game";
	public static final String USERPOC_INTF = "intf";
	public static final String USERPOC_MAINPAGE_PCSUITE = "mainpage_pcsuite";
	public static final String USERPOC_MULTI = "multi";
	public static final String USERPOC_MYPAGE = "mypage";
	public static final String USERPOC_MYPAGE_PCSUITE = "mypage_pcsuite";
	public static final String USERPOC_ORDER = "order";
	public static final String USERPOC_PURCHASE = "purchase";
	public static final String USERPOC_USER = "user";
	public static final String USERPOC_RX_HIS = "RxProvisioningHis";

	public static final String DEVPOC_PRODUCT = "product";

	// 서버IP
	public static final String SERVER_IP_LOCAL = "127.0.0.1";
	public static final String SERVER_IP_TEST1 = "172.18.200.65"; // 수내 OMP_WAS1 내부IP
	public static final String SERVER_IP_REAL1 = "172.18.69.125"; // 운영 OMP_WAS1 내부IP
	public static final String SERVER_IP_REAL2 = "172.18.69.126"; // 운영 OMP_WAS1 내부IP
	public static final String SERVER_IP_REAL3 = "172.18.69.113"; // 운영 ompadwas1, 2 내부IP -BP/ADMIN
	public static final String SERVER_IP_REAL4 = "172.18.69.114"; // 운영 ompadwas1, 2 내부IP -BP/ADMIN

	/**
	 * 예제---- 파일 업로드 경로 정의
	 * 
	 * 아래와 같은 방법으로 config.properties 파일에 해당 key,value 를 등록하고 여기에 static 필드로 정의해서 static 필드로 사용한다.
	 * 
	 * 업무모듈에서 Configuration 객체를 생성하지않는다.
	 */

	// public static String USER_POC_IP = config.getValue("omp.userpoc.ip");
	// public static String USER_EMAIL_DB_PORT = config.getValue("omp.userpoc.email.db.port");
	// public static String USER_EMAIL_DB_URL = config.getValue("omp.userpoc.email.db.url");
	// public static String USER_EMAIL_DB_NAME = config.getValue("omp.userpoc.email.db.name");
	// public static String USER_EMAIL_DB_ID = config.getValue("omp.userpoc.email.db.id");
	// public static String USER_EMAIL_DB_PASS = config.getValue("omp.userpoc.email.db.pass");
	//
	// public static String USER_CONTENT_DL_INTF_URL = config.getValue("omp.userpoc.contentDL.intf.url");
	// public static String USER_COOKIE_DOMAIN = config.getValue("omp.userpoc.cookie.domain");
	//
	// public static String OMP_DEVPOC_DOMAIN = config.getValue("omp.devpoc.domain");
	// public static String OMP_DEVPOC_MAIN_PAGE_URL = config.getValue("omp.devpoc.main");
	//
	// public static String OMP_IDP_HOSTNAME = config.getValue("omp.userpoc.idp.hostname");
	// public static String OMP_IDP_SP_ID = config.getValue("omp.userpoc.idp.sp_id");
	// public static String OMP_IDP_SP_KEY = config.getValue("omp.userpoc.idp.sp_key");
	// public static String OMP_IDP_SERVICE_DOMAIN = config.getValue("omp.userpoc.idp.service_domain");
	// public static String OMP_IDP_PROTOCOOL = config.getValue("omp.userpoc.idp.protocool");
	//
	// public static String HOPPIN_IMAGE_SERVER_URL = config.getValue("hoppin.image.server.url");
	/*
	 * public static String SSO_SST_CD_NATE_WEB = config.getValue("omp.userpoc.sst.nate.web"); //"10100"; public static
	 * String SSO_SST_CD_CYWORLD_WEB = config.getValue("omp.userpoc.sst.cyworld.web"); //"10200"; public static String
	 * SSO_SST_CD_11ST_WEB = config.getValue("omp.userpoc.sst.11st.web");//"20100"; public static String
	 * SSO_SST_CD_MELON_WEB = config.getValue("omp.userpoc.sst.melon.web");//"30100"; public static String
	 * SSO_SST_CD_TSTORE_WEB = config.getValue("omp.userpoc.sst.tstore.web");//"41100"; public static String
	 * SSO_SST_CD_TCLOUD_WEB = config.getValue("omp.userpoc.sst.tcloud.web");//"41200"; public static String
	 * SSO_SST_CD_TMAP_WEB = config.getValue("omp.userpoc.sst.tmap.web");//"41300";
	 */

	public static String SSO_SST_CD_NATE_WEB = "10100";
	public static String SSO_SST_CD_CYWORLD_WEB = "10200";
	public static String SSO_SST_CD_11ST_WEB = "20100";
	public static String SSO_SST_CD_MELON_WEB = "30100";
	public static String SSO_SST_CD_TSTORE_WEB = "41100";
	public static String SSO_SST_CD_TCLOUD_WEB = "41200";
	public static String SSO_SST_CD_TMAP_WEB = "41300";
	public static String SSO_SST_CD_IM_DEV_CENTER = "40100";
	public static String SSO_SST_CD_CONTEXT_PORTAL = "40200";
	public static String SSO_SST_CD_NOP = "40300";
	public static String SSO_SST_CD_OCB_WEB = "42100";
	public static String OCB_SST_CD = "40300";

	public static String SSO_SST_NATE = "Nate";
	public static String SSO_SST_CYWORLD = "Cyworld";
	public static String SSO_SST_11ST = "11번가";
	public static String SSO_SST_MELON = "멜론";
	public static String SSO_SST_TSTORE = "Tstore";
	public static String SSO_SST_TCLOUD = "Tcloud";
	public static String SSO_SST_TMAP = "Tmap";
	public static String sSO_SST_DEVCENTER = "통합개발자 센터";
	public static String SSO_SST_C_PORTAL = "Context Portal";
	public static String SSO_SST_NOP = "NOP";

	// public static String NATE_REGIST_URL = config.getValue("omp.userpoc.change.nate.url");
	// public static String TCLOUD_AGREE_URL = config.getValue("omp.userpoc.agree.tcloud.url");
	// public static String NATE_AGREE_URL = config.getValue("omp.userpoc.agree.nate.url");
	// public static String SITE_REDIRECT_URL = config.getValue("omp.userpoc.agree.redirect.url");
	//
	// public static String DCD_BILL_IF_URL = config.getValue("dcd.bill.if.url");
	// public static String DCD_CONN_YN = config.getValue("dcd.conn.yn");
	// public static String DCD_USERPOC_IDP_URL = config.getValue("dcd.userpoc.idp.url");
	// public static String OMP_ITOPPING_MIGRATION_INTF_URL = config.getValue("omp.itopping.migration.intf");
	//
	// public static String OMP_DONWLOAD_URL = config.getValue("omp.dl.url");
	// /* NAM J W */
	// public static String USER_SOCIALFILE_PATH = config.getValue("omp.userpoc.social.path");
	//
	// public static String OMP_SERVER_NAME = config.getValue("omp.system.server.name");

	// public static String TCAST_IMG_EFFECT[] =
	// {"/data/img/img_filter/thumbnail/40/round_mask.png","/data/img/img_filter/thumbnail/40/filter_merge.png","/data/img/img_filter/thumbnail/40/drop_shadow.png"};
	// public static int TCAST_IMG_EXPAND[] = {1,1,3,4};//"1,1,3,4";
	public static String TCAST_IMG_EFFECT[] = { "/data/img/img_filter/icon/104/round_mask.png",
			"/data/img/img_filter/icon/104/filter_merge.png", "/data/img/img_filter/icon/104/drop_shadow.png" };
	public static int TCAST_IMG_EXPAND[] = { 4, 4, 18, 18 };
	public static String TCAST_IMG_TYPE = "jpge";
	public static int TCAST_IMG_WIDTH = 80;
	public static int TCAST_IMG_HEIGHT = 80;
	public static String TCAST_DIR_PATH = "thumbnail";

	/* Batch */
	// public static String BATCH_FTP_DIR = config.getValue("omp.bp.batch.ftp.dir");
	// public static String BATCH_LOOP_TIME = config.getValue("omp.bp.batch.loop.time");
	// public static String BATCH_RGH_LT = config.getValue("omp.bp.batch.rgh.lt");
	// public static String BATCH_FILE_REPOSIT = config.getValue("omp.bp.batch.file.reposit");

	/* Ring Bell */
	// public static String OMP_RINGBELL_DOMAIN = config.getValue("ringbell.omp.user.domain");
	// public static String RN_RINGBELL_DOMAIN = config.getValue("ringbell.rn.user.domain");

	// public static final String OMP_DL_SERVER_IP = config.getValue("omp.dl.server.ip");
	// public static final String OMP_DL_SERVER_PORT = config.getValue("omp.dl.server.port");
	// public static final String OMP_DL_WIFI_SERVER_IP = config.getValue("omp.dl.wifi.server.ip");
	// public static final String OMP_DL_WIFI_SERVER_PORT = config.getValue("omp.dl.wifi.server.port");

	/* SK M&C */
	// public static String SK_MC = config.getValue("omp.userpoc.purchase.skmc");

	/**
	 * nefer 2009-12-08 추가
	 */

	/** HTTP 연동 방식 */
	public static final String HTTP_METHOD_GET = "GET";
	public static final String HTTP_METHOD_POST = "POST";

	public static final String IDP_PROPERTIES_FILE = "idp.properties";
	public static final String DEFAULT_PROPERTIES_FILE = "config.properties";

	public static final String YES = "Y";
	public static final String NO = "N";
	public static final String BLANK = "";

	/** 통신사 */
	public static final String TELECOM_SKT = "US001201"; // SKT
	public static final String TELECOM_KFT = "US001202"; // KTF
	public static final String TELECOM_LGT = "US001203"; // LGT

	/** Feed Box 상품 ID **/
	// public static final String FEEDBOX_CID = config.getValue("omp.userpoc.prod.feedbox");

	/** Feed Box 가입 API **/
	// public static final String FEEDBOX_SUBIP = config.getValue("omp.userpoc.feedbox.subip");

	/** 라이브세션(liveSession) **/
	// public static final String LiveSession = config.getValue("omp.userpoc.liveSession");

	// public static final String FEEDBOX_SUBSPID = config.getValue("omp.userpoc.feedbox.subspid");
	// public static final String FEEDBOX_SUBAUTHKEY = config.getValue("omp.userpoc.feedbox.subautykey");
	// public static final String FEEDBOX_SUBCHANNELID = config.getValue("omp.userpoc.feedbox.subchannelid");
	// public static final String BLOGWIDGET_SUBCHANNELID = config.getValue("omp.userpoc.blogwidget.subchannelid");

	/** Blog Widge 이미지 썸네일 코드 */
	public static final String CONTENT_IMAGE_ICON1 = "DP000101";
	public static final String CONTENT_IMAGE_ICON2 = "DP000102";
	public static final String CONTENT_IMAGE_DESC = "DP000107";
	public static final String IMG_214_214 = "DP000108";
	public static final String IMG_104_104 = "DP000109";
	public static final String IMG_80_81 = "DP000110";
	public static final String IMG_76_76 = "DP000111";
	public static final String IMG_72_73 = "DP000112";
	public static final String IMG_64_64 = "DP000121";
	public static final String IMG_52_52 = "DP000113";
	public static final String IMG_44_45 = "DP000114";
	public static final String IMG_42_42 = "DP000115";
	public static final String IMG_32_32 = "DP000122";
	public static final String IMG_24_24 = "DP000120";
	public static final String IMG_120_111_ROUND = "DP000123";
	public static final String IMG_120_111_CIRCLE = "DP000124";
	public static final String IMG_72_72 = "DP000125";
	public static final String IMG_24_24_BMP = "DP000126";
	public static final String IMG_154_154 = "DP000159";

	/** 상품 판매 상태 */
	public static final String CONTENT_SALE_STAT_NA = "PD000401";
	public static final String CONTENT_SALE_STAT_WAIT = "PD000402";
	public static final String CONTENT_SALE_STAT_ING = "PD000403";
	public static final String CONTENT_SALE_STAT_STOP = "PD000404";
	public static final String CONTENT_SALE_STAT_RESTRIC = "PD000405";
	public static final String CONTENT_SALE_STAT_UNREGIST = "PD000406";
	public static final String CONTENT_SALE_STAT_TEMPSTOP = "PD000407";
	public static final String CONTENT_SALE_STAT_DELETE = "PD000408";

	/* OK Cashbag */
	// public static String OCB_IP = config.getValue("omp.userpoc.ocb.ip");
	// public static String OCB_PORT = config.getValue("omp.userpoc.ocb.port");
	// public static String OCB_OPER_CD = config.getValue("omp.userpoc.ocb.opercd");
	// public static String OCB_CONTR_NO = config.getValue("omp.userpoc.ocb.contrno");
	// public static String OCB_RETRY_COUNT = config.getValue("omp.userpoc.ocb.retrycnt");
	//
	// public static String CINE21_MBRNO = config.getValue("omp.userpoc.cine21.mbrno");
	//
	// public static String INTERPARK_URL = config.getValue("omp.userpoc.interpark.url");

	// ADMIN_POC 인증관련
	public static final String ADMIN_AUTH_SESSION_KEY2 = "ADMIN_SESSION"; // 어드민로그인 정보 세션키

	// public static final String RN_SETTING_IP = config.getValue("omp.userpoc.rnsetting.ip");

	/* Social */
	// public static String SOCIAL_IP = config.getValue("omp.userpoc.social.ip");
	// public static String SOCIAL_PORT = config.getValue("omp.userpoc.social.port");

	/* Email */
	// public static String EMAIL_USER_POC_URL = config.getValue("omp.userpoc.email.url");

	// 부분유료화 월정액 탈퇴
	// public static String IN_APP_BILL_URL = config.getValue("omp.userpoc.inappbill.url");

	// ==========================================================================================================================================================================
	/* 도토리 */

	// public static final String CP_IP = config.getValue("omp.userpoc.dotori.cp.ip"); // CP IP

	// public static final String LINKINFO_CP_ID = config.getValue("omp.userpoc.dotori.linkinfo.cp.id"); // 유무선 연동정보 조회
	// CP
	// 계정 ID
	// public static final String LINKINFO_CP_PWD = config.getValue("omp.userpoc.dotori.linkinfo.cp.pwd"); // 유무선 연동정보
	// 조회
	// CP 계정 PWD
	// public static final String BMS_CP_ID = config.getValue("omp.userpoc.dotori.bms.cp.id"); // BMS CP 계정 ID
	// public static final String BMS_CP_PWD = config.getValue("omp.userpoc.dotori.bms.cp.pwd"); // BMS CP 계정 PWD

	// public static final String DOTORI_COMMON_SOCKET_TIMEOUT = config.getValue("omp.userpoc.dotori.common.timeout");
	// // Connect/Read
	// Timeout
	// 세팅
	// public static final String DOTORI_COMMON_SOCKET_TRYCNT = config.getValue("omp.userpoc.dotori.common.trycnt"); //
	// Timeout
	// 시
	// 최대
	// 연결
	// 시도
	// 횟수

	// 도토리 - 유무선연동정보 조회
	// public static final String LINKINFO_SERVER_IP = config.getValue("omp.userpoc.dotori.linkinfo.server.ip"); // [유무선
	// // 연동정보]
	// // 서버 IP :
	// public static final String LINKINFO_SERVER_PORT = config.getValue("omp.userpoc.dotori.linkinfo.server.port"); //
	// [유무선
	// // 연동정보]
	// // 서버
	// // PORT
	// public static final String LINKINFO_SERVER_URL = "http://" + LINKINFO_SERVER_IP + ":" + LINKINFO_SERVER_PORT
	// + "/mobile/getUserDeviceInfo.jsp"; // [유무선 연동정보] 서버 URL
	//
	// // 도토리 - Nate/Cyworld BMS
	// public static final String[] CP_IDPWD_ARRAY = new String[] { BMS_CP_ID, BMS_CP_PWD }; // CP 계정(아이디, 패스워드)
	// public static final String[] CP_IDPWD_IP_ARRAY = new String[] { BMS_CP_ID, BMS_CP_PWD, CP_IP }; // CP 계정(아이디,
	// 패스워드,
	// // CP IP)
	//
	// public static final String NATE_BMS_SERVER_IP = config.getValue("omp.userpoc.dotori.nate.server.ip"); // [네이트] 서버
	// IP
	// public static final String NATE_BMS_SERVER_PORT = config.getValue("omp.userpoc.dotori.nate.server.port"); //
	// [네이트]
	// // 서버 PORT
	// public static final String CYWORLD_BMS_SERVER_IP = config.getValue("omp.userpoc.dotori.cyworld.server.ip"); //
	// [싸이월드]
	// // 서버 IP
	// public static final String CYWORLD_BMS_SERVER_PORT = config.getValue("omp.userpoc.dotori.cyworld.server.port");//
	// [싸이월드]
	// // 서버
	// // PORT
	// public static final String SKPID_NATE_BMS_SERVER_IP = config.getValue("omp.userpoc.dotori.skpid.nate.server.ip");
	// // <SKPID
	// // 통합>
	// // [네이트]
	// // 서버
	// // IP
	// public static final String SKPID_NATE_BMS_SERVER_PORT = config
	// .getValue("omp.userpoc.dotori.skpid.nate.server.port"); // <SKPID 통합> [네이트] 서버 PORT
	// public static final String SKPID_CYWORLD_BMS_SERVER_IP = config
	// .getValue("omp.userpoc.dotori.skpid.cyworld.server.ip"); // <SKPID 통합> [싸이월드] 서버 IP
	// public static final String SKPID_CYWORLD_BMS_SERVER_PORT = config
	// .getValue("omp.userpoc.dotori.skpid.cyworld.server.port"); // <SKPID 통합> [싸이월드] 서버 PORT
	//
	// public static final String NATE_SERVICE_ID = config.getValue("omp.userpoc.dotori.nate.service.id"); // 네이트 서비스ID
	// public static final String CYWORLD_SERVICE_ID = config.getValue("omp.userpoc.dotori.cyworld.service.id"); // 싸이월드
	// // 서비스ID
	// public static final String SKPID_NATE_SERVICE_ID = config.getValue("omp.userpoc.dotori.skpid.nate.service.id");
	// // <SKPID
	// // 통합>
	// // 네이트
	// // 서비스ID
	// public static final String SKPID_CYWORLD_SERVICE_ID = config
	// .getValue("omp.userpoc.dotori.skpid.cyworld.service.id"); // <SKPID 통합> 싸이월드 서비스ID
	//
	// public static final String BMS_PROTOCOL = config.getValue("omp.userpoc.dotori.bms.protocol"); // 유무선 연동 회원
	// 네이트/싸이월드
	// // 통신 프로토콜 : http
	// public static final String BMS_SKPID_PROTOCOL = config.getValue("omp.userpoc.dotori.bms.skpid.protocol"); // 통합ID
	// 회원
	// // 네이트/싸이월드
	// // 통신 프로토콜
	// // : https
	//
	// public static final String NATE_PRODNM_MAXLEN = config.getValue("omp.userpoc.dotori.nate.prodnm.maxlen"); // 네이트
	// 상품명
	// // 최대길이
	// public static final String CYWORLD_PRODNM_MAXLEN = config.getValue("omp.userpoc.dotori.cyworld.prodnm.maxlen");
	// // 싸이월드
	// 상품명
	// 최대길이

	public static final String CYWORLD_SERVICE_TP = "01"; // 서비스구분 (01:미니홈피, 02:클럽, 03:페이퍼, 04:팀플)
	public static final String CYWORLD_SERVICE_TP_ID = null; // 서비스 구분 아이디(SERVICE_TP=01일 때, null)

	public static final int CALL_POOL_INQUIRY_INIT_SIZE = 4; // (조회) Call 스택 초기 사이즈
	public static final int CALL_POOL_PAY_INIT_SIZE = 4; // (예약/과금) Call 스택 초기 사이즈

	// OPERATION (value for tableswitch: 1 to 20)
	public static final int OPERAION_NATE_INQUIRY = 1; // [네이트] 도토리 잔액 조회 (v2)
	public static final int OPERAION_NATE_RESERVE = 2; // [네이트] 예약
	public static final int OPERAION_NATE_RESERVECANCEL = 3; // [네이트] 예약취소
	public static final int OPERAION_NATE_PAY = 4; // [네이트] 과금
	public static final int OPERAION_NATE_PAYCANCEL = 5; // [네이트] 과금취소
	public static final int OPERAION_CYWORLD_INQUIRY = 6; // [싸이월드] 도토리 잔액 조회 (v2)
	public static final int OPERAION_CYWORLD_RESERVE = 7; // [싸이월드] 예약
	public static final int OPERAION_CYWORLD_RESERVECANCEL = 8; // [싸이월드] 예약취소
	public static final int OPERAION_CYWORLD_PAY = 9; // [싸이월드] 과금
	public static final int OPERAION_CYWORLD_PAYCANCEL = 10; // [싸이월드] 과금취소
	public static final int OPERAION_SKPID_NATE_INQUIRY = 11; // <SKPID 통합> [네이트] 도토리 잔액 조회 (v2)
	public static final int OPERAION_SKPID_NATE_RESERVE = 12; // <SKPID 통합> [네이트] 예약
	public static final int OPERAION_SKPID_NATE_RESERVECANCEL = 13; // <SKPID 통합> [네이트] 예약취소
	public static final int OPERAION_SKPID_NATE_PAY = 14; // <SKPID 통합> [네이트] 과금
	public static final int OPERAION_SKPID_NATE_PAYCANCEL = 15; // <SKPID 통합> [네이트] 과금취소
	public static final int OPERAION_SKPID_CYWORLD_INQUIRY = 16; // <SKPID 통합> [싸이월드] 도토리 잔액 조회 (v2)
	public static final int OPERAION_SKPID_CYWORLD_RESERVE = 17; // <SKPID 통합> [싸이월드] 예약
	public static final int OPERAION_SKPID_CYWORLD_RESERVECANCEL = 18; // <SKPID 통합> [싸이월드] 예약취소
	public static final int OPERAION_SKPID_CYWORLD_PAY = 19; // <SKPID 통합> [싸이월드] 과금
	public static final int OPERAION_SKPID_CYWORLD_PAYCANCEL = 20; // <SKPID 통합> [싸이월드] 과금취소

	// ==========================================================================================================================================================================

	// public static final String FB_DOMAIN = config.getValue("omp.fb.like.domain");
	//
	//
	// public static final String ISF_SERVER_URL = config.getValue("omp.isf.server.url"); // ISF 서버 URL
	// public static final String FB_LIKE_URL = config.getValue("omp.fb.like.url"); // 페이스북 좋아요 버튼 URL
	//
	// public static final String FB_APP_ID = config.getValue("omp.fb.appid"); // 페이스북 앱 ID/API 키
	// public static final String FB_APP_SECRET_CODE = config.getValue("omp.fb.appsecretcode"); // 페이스북 앱 시크릿 코드
	// public static final String FB_APP_TINYURL_PREFIX = config.getValue("omp.fb.tstore.prefix"); // GB_tinyUrlPrefix
	//
	// /* 컬쳐랜드 */
	// public static String CULTURE_IP = config.getValue("omp.userpoc.culture.ip"); // 컬쳐랜드 연동 IP
	// public static String CULTURE_PORT = config.getValue("omp.userpoc.culture.port"); // 컬쳐랜드 연동 PORT
	// public static String CULTURE_EX_MEMBER_CODE = config.getValue("omp.userpoc.culture.exmembercode"); // 컬쳐랜드 업체코드
	// public static String CULTURE_SUB_MEMBER_CODE_WEB = config.getValue("omp.userpoc.culture.submembercode.web"); //
	// 컬쳐랜드 하위 업체코드(WEB)
	// public static String CULTURE_SUB_MEMBER_CODE_SC = config.getValue("omp.userpoc.culture.submembercode.sc"); //
	// 컬쳐랜드 하위 업체코드(S/C)
	//
	// //M&Service 기프트카드
	// public static String GIFTCARD_MNSERVICE_AUTH = config.getValue("omp.userpoc.giftcard.mnservice.auth"); // 기프트카드
	// 인증 URL
	// public static String GIFTCARD_MNSERVICE_ROLLBACK = config.getValue("omp.userpoc.giftcard.mnservice.rollback"); //
	// 기프트카드 롤백 URL
	// public static String GIFTCARD_MNSERVICE_REFUND = config.getValue("omp.userpoc.giftcard.mnservice.refund"); //
	// 기프트카드 환불 URL
	//
	// /* T 멤버십 */
	// public static String TMEMBERSHIP_IP = config.getValue("omp.userpoc.tmembership.ip"); // T 멤버십 연동 IP
	// public static String TMEMBERSHIP_PORT = config.getValue("omp.userpoc.tmembership.port"); // T 멤버십 연동 PORT
	//
	// /* SKP 법인번호 */
	// public static String SKP_BLOCK_VALUE = config.getValue("omp.userpoc.skp.block.value"); // SKP 법인번호
	//
	// /* PURCHASE POC IP */
	// public static String PURCHASE_POC_IP = config.getValue("omp.userpoc.purchasepoc.ip"); // PURCHASE POC IP
	//
	// /* PURCHASE POC 호출시간 */
	// public static String PURCHASE_CALL_TIME = config.getValue("omp.userpoc.purchasepoc.call.time"); // PURCHASE POC
	// 호출시간
	//
	// /* OCBPG */
	// public static final String OCBPG_URL = config.getValue("omp.userpoc.ocbpg.url"); // OCBPG URL
	// public static final String OCBPG_TIME = config.getValue("omp.userpoc.ocbpg.time"); // OCBPG 호출시간
	// public static final String OCB_CI_URL = config.getValue("omp.userpoc.ocbci.url"); // CI인증 URL
	// public static final String OCB_CHARGE_URL = config.getValue("omp.userpoc.ocbcharge.url"); // 충전하기 URL
	// public static final String OCBPG_SEED_KEY = config.getValue("omp.userpoc.ocbpg.seedkey"); // OCBPG SEED KEY
	// public static final String OCBPG_MXPASS_KEY = config.getValue("omp.userpoc.ocbpg.mxpasskey"); // OCBPG MXPASS KEY

	/* 부분유료화 사용 내역 조회 URL */
	// public static String IAP_REQ_URL = config.getValue("omp.dev.iap.requrl");
	/*
	 * 통계로그 구축-POC 구분코드(OLAP공통)
	 */
	public static final String STATIST_POCTYPE_WEPOC = "MRT00401";// webpoc

	// public static final String FDS_LOG_VERSION = config.getValue("omp.userpoc.fds.log.version"); // fds log_version

	public static final String BILL_FUP = "FUPDATE";

	/**
	 * 게임센터 관련 상수
	 * 
	 * @author bumworld
	 * 
	 */
	public static class GameCenter {

		/**
		 * 회원 데이타 연동 파라미터
		 */
		public static final String USER_DATA_SYNC_PARAM_WORK_CD = "WORK_CD";
		public static final String USER_DATA_SYNC_PARAM_MBR_NO = "MBR_NO";
		public static final String USER_DATA_SYNC_PARAM_OLD_MBR_NO = "OLD_MBR_NO";
		public static final String USER_DATA_SYNC_PARAM_MDN = "MDN";
		public static final String USER_DATA_SYNC_PARAM_OLD_MDN = "OLD_MDN";
		public static final String USER_DATA_SYNC_PARAM_REQ_TYPE = "REQ_TYPE";

		/**
		 * US003201:번호변경
		 */
		public static final String USER_DATA_SYNC_WORK_CD_01 = "US003201";
		/**
		 * US003202:명의변경
		 */
		public static final String USER_DATA_SYNC_WORK_CD_02 = "US003202";
		/**
		 * US003203:회원탈퇴
		 */
		public static final String USER_DATA_SYNC_WORK_CD_03 = "US003203";
		/**
		 * US003204:MDN등록
		 */
		public static final String USER_DATA_SYNC_WORK_CD_04 = "US003204";
		/**
		 * US003205:MDN삭제
		 */
		public static final String USER_DATA_SYNC_WORK_CD_05 = "US003205";
		/**
		 * US003206:회원전환
		 */
		public static final String USER_DATA_SYNC_WORK_CD_06 = "US003206";

		/**
		 * US003207:통합회원전환
		 */
		public static final String USER_DATA_SYNC_WORK_CD_07 = "US003207";

		/**
		 * USER_POC :0001 DEV_POC :0002 IF :0003 ADMIN_POC:0004 BATCH :0005
		 */
		public static final String USER_DATA_SYNC_REQ_TYPE_USER = "0001";
		public static final String USER_DATA_SYNC_REQ_TYPE_DEV = "0002";
		public static final String USER_DATA_SYNC_REQ_TYPE_IF = "0003";
		public static final String USER_DATA_SYNC_REQ_TYPE_ADMIN = "0004";
		public static final String USER_DATA_SYNC_REQ_TYPE_BATCH = "0005";

	}

	/*
	 * 인증상수 1:휴대폰, 2:공인인증서, 3:IPIN, 4:신용카드, 5:신평사, 6: 이메일 (외국인 법정대리인 인증)
	 */
	public static class AUTH {
		public static final String AUTH_TYPE_KMC = "1"; // 한국모바일 인증
		public static final String AUTH_TYPE_IPIN = "3"; // 아이핀 인증

		public static final String AUTH_CHNL_WEB = "101"; // 웹
		public static final String AUTH_CHNL_MOBILE_2X = "102"; // MobileClient2.X
		public static final String AUTH_CHNL_MOBILE_3X = "103"; // MobileClient3.X\
		public static final String AUTH_CHNL_MOBILE_WEB = "104";// 모바일웹
		public static final String AUTH_CHNL_MOBILE_OPENAPI = "105";// OPENAPI
	}

	/*
	 * 원아이디 관련 상수
	 */
	public static class OneID {
		public static final String REG_TYPE_01 = "01"; // 신규
		public static final String REG_TYPE_02 = "02"; // 전환
		public static final String REG_TYPE_03 = "03"; // 변경
		public static final String REG_TYPE_04 = "04"; // 이용동의
		public static final String REG_TYPE_05 = "05"; // 프로비져닝 일괄전환
		public static final String REG_TYPE_06 = "06"; // 아이디 변경
	}

	/**
	 * 전시카테고리 관련 상수
	 */
	/** 1-Depth 전시카테고리 : 게임 */
	public static final String CAT_GAME = "DP01";
	/** 1-Depth 전시카테고리 : FUN */
	public static final String CAT_FUN = "DP03";
	/** 1-Depth 전시카테고리 : 생활/위치 */
	public static final String CAT_LIFE = "DP04";
	/** 1-Depth 전시카테고리 : 어학/교육 */
	public static final String CAT_LGE = "DP08";
	/** 1-Depth 전시카테고리 : 음악 */
	public static final String CAT_MUSC = "DP16";
	/** 1-Depth 전시카테고리 : 영화 */
	public static final String CAT_MOVI = "DP17";
	/** 1-Depth 전시카테고리 : 방송 */
	public static final String CAT_AIR = "DP18";
	/** 1-Depth 전시카테고리 : 만화 */
	public static final String CAT_CTN = "DP14";

	/** 1-Depth 전시카테고리 : eBook */
	public static final String CAT_BOOK = "DP13";
	/** 1-Depth 전시카테고리 : 마이페이지 */
	public static final String CAT_MYPG = "DP23";
	/** 1-Depth 전시카테고리 : 상황별추천 */
	public static final String CAT_REC = "DP19";
	/** 1-Depth 전시카테고리 : 랭킹 */
	public static final String CAT_RENK = "DP20";
	/** 1-Depth 전시카테고리 : 이벤트 */
	public static final String CAT_EVNT = "DP21";
	/** 1-Depth 전시카테고리 : 고객센터 */
	public static final String CAT_CUMR = "DP22";
	/** 1-Depth 전시카테고리 : 쇼핑 */
	public static final String CAT_SHOP = "DP28";

	/** 전시카테고리 레벨 : 1 */
	public static final int CAT_DEPTH_LEVEL_1 = 1;
	/** 전시카테고리 레벨 : 2 */
	public static final int CAT_DEPTH_LEVEL_2 = 2;
	/** 전시카테고리 레벨 : 3 */
	public static final int CAT_DEPTH_LEVEL_3 = 3;

	/** 쇼핑 Q&A - 문의채널 */
	public static final String SHOPPING_QNA_GRPCD_CHANNEL = "CM0071";

	/**
	 * 쇼핑 Q&A - 등록주체 01 - 구매자 02 - 개발자 03 - 운영자
	 * */
	public static final String SHOPPING_QNA_GRPCD_REGIST = "CM0072";
	public static final String SHOPPING_QNA_DTLCD_REGIST_USER = "CM007201";
	public static final String SHOPPING_QNA_DTLCD_REGIST_DEV = "CM007202";
	public static final String SHOPPING_QNA_DTLCD_REGIST_ADMIN = "CM007203";

	/**
	 * 쇼핑 Q&A - 이관구분 01 - 판매자 02 - 운영자
	 * */
	public static final String SHOPPING_QNA_GRPCD_TRNAS = "CM0070";
	public static final String SHOPPING_QNA_DTLCD_TRNAS_DEV = "CM007001";
	public static final String SHOPPING_QNA_DTLCD_TRNAS_ADMIN = "CM007002";

	/**
	 * 쇼핑 Q&A - 처리상태 01 - 대기 02 - 접수 03 - 답변완료 04 - 삭제
	 * */
	public static final String SHOPPING_QNA_GRPCD_STATCD = "CM0073";
	public static final String SHOPPING_QNA_DTLCD_STATCD_WAIT = "CM007301";
	public static final String SHOPPING_QNA_DTLCD_STATCD_RECIVE = "CM007302";
	public static final String SHOPPING_QNA_DTLCD_STATCD_FINISH = "CM007303";

	/** 쇼핑 Q&A - 문의종류 */
	public static final String SHOPPING_QNA_GRPCD_TYPE = "CM0074";

	/** 개발자 메세지 관리 - 발송구분 */
	public static final String DEVMESSAGE_TARGETGB_GRPCD = "DV0001";
	public static final String DEVMESSAGE_TARGETGB_DTLCD_01 = "DV000101";
	/** 대상선택 */
	public static final String DEVMESSAGE_TARGETGB_DTLCD_02 = "DV000102";
	/** 모두발송 */

	/** 개발자 메세지 관리 - 발송시기 */
	public static final String DEVMESSAGE_TARGETTYPE_GRPCD = "DV0002";
	public static final String DEVMESSAGE_TARGETTYPE_DTLCD_01 = "DV000201";
	/** 직접입력 */
	public static final String DEVMESSAGE_TARGETTYPE_DTLCD_02 = "DV000202";
	/** 엑셀업로드 */

	/** 개발자 메세지 관리 - 발송대상 */
	public static final String DEVMESSAGE_SENDTIME_GRPCD = "DV0003";
	public static final String DEVMESSAGE_SENDTIME_DTLCD_01 = "DV000301";
	/** 즉시발송 */
	public static final String DEVMESSAGE_SENDTIME_DTLCD_02 = "DV000302";
	/** 예약발송 */

	/** 개발자 메세지 관리 - 발송상태구분 */
	public static final String DEVMESSAGE_SENDSTATUS_GRPCD = "DV0004";
	public static final String DEVMESSAGE_SENDSTATUS_DTLCD_01 = "DV000401";
	/** 발송완료 */
	public static final String DEVMESSAGE_SENDSTATUS_DTLCD_02 = "DV000402";
	/** 발송취소 */
	public static final String DEVMESSAGE_SENDSTATUS_DTLCD_03 = "DV000403";
	/** 예약취소 */
	public static final String DEVMESSAGE_SENDSTATUS_DTLCD_04 = "DV000404";
	/** 예약발송 */

	/** 레퍼런스 관리 - 레퍼런스 구분 */
	public static final String DEVMAINREF_REFFERENCEGB_GRPCD = "DV0005";
	public static final String DEVMAINREF_REFFERENCEGB_DTLCD_01 = "DV000501";
	/** 개발도구 및 리소스 */
	public static final String DEVMAINREF_REFFERENCEGB_DTLCD_02 = "DV000502";
	/** 추천 Document */

	/** 영역구분 */
	public static final String DEVMAINREF_AREAGB_GRPCD = "DV0006";
	public static final String DEVMAINREF_AREAGB_DTLCD_01 = "DV000601";
	/** 01 */
	public static final String DEVMAINREF_AREAGB_DTLCD_02 = "DV000602";
	/** 02 */
	public static final String DEVMAINREF_AREAGB_DTLCD_03 = "DV000603";
	/** 03 */

	/** 언어구분 */
	public static final String DEVMAINREF_LANGGB_GRPCD = "DV0007";
	public static final String DEVMAINREF_LANGGB_DTLCD_01 = "DV000701";
	/** 국문 */
	public static final String DEVMAINREF_LANGGB_DTLCD_02 = "DV000702";

	/** 영문 */
	@Override
	public void update(Observable o, Object arg) {
		// TODO Auto-generated method stub

	}

}
