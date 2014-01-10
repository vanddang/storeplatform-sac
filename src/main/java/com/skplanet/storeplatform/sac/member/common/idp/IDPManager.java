/*
 * COPYRIGHT(c) SK telecom 2009
 * This software is the proprietary information of SK telecom.
 *
 * Revision History
 * or       Date            Description
 * --------     ----------      ------------------
 * ?            ?               ?
 * nefer        2009.12.08      move to omp_common (remove unusable variable, change expose properties method)
 *
 */
package com.skplanet.storeplatform.sac.member.common.idp;

import java.util.Hashtable;
import java.util.Map;

import org.apache.commons.lang.time.StopWatch;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.skplanet.storeplatform.external.client.idp.sci.IdpSCI;
import com.skplanet.storeplatform.external.client.idp.vo.IDPReceiverM;
import com.skplanet.storeplatform.external.client.idp.vo.SendReq;
import com.skplanet.storeplatform.external.client.idp.vo.SendRes;
import com.skplanet.storeplatform.sac.member.common.idp.vo.IDPSenderM;

@Component
public class IDPManager implements IDPConstants {

	@Autowired
	private IdpSCI idpSCI;

	private static Logger logger = Logger.getLogger(IDPManager.class);
	// 필요 없는 것으로 판단되어 주석 처리 - 임재호 2014.1.8
	// private static IDPManager instance = null;

	// 필요 없는 것으로 판단되어 주석 처리 - 임재호 2014.1.8
	// private final IDPReceiver idpReceiver = null;
	@Autowired
	private IDPSender idpSender;
	// private final IDPReceiverM receivData = null;

	// 일단 테스트를 위해 주석 처리 - 임재호 2014.1.8
	// private SMSApi smsapi = null;

	// 필요 없는 것으로 판단되어 주석 처리 - 임재호 2014.1.8
	// private final DevProperties prop = null;
	//
	// /** OMP 서비스 도메인 */
	// IDPSender로 이동하고 주석 처리 - 임재호 2014.1.8
	// @Value("#{propertiesForSac['idp.service_domain']}")
	// public static String OMP_SERVICE_DOMAIN;
	// 필요 없는 것으로 판단되어 주석 처리 - 임재호 2014.1.8
	// /** OMP 서비스 URL */
	// public static String OMP_SERVICE_URL = "";
	// /** OMP 서비스 URL (HTTPS) */
	// public static String OMP_SERVICE_URL_HTTPS = "";
	// /** OMP 서비스 URL IP */
	// public static String OMP_SERVICE_URL_IP = "";
	//
	// /** IDP 요청 도메인 (HTTP) */
	// public static String IDP_REQUEST_URL = "";
	// /** IDP 요청 도메인 (HTTPS) */
	// public static String IDP_REQUEST_URL_HTTPS = ""; //
	//
	// public static String IDP_REQUEST_SERVER_URL = "";
	// public static String IDP_REQUEST_SERVER_URL_HTTPS = "";
	//
	// IDPSender로 이동하고 주석 처리 - 임재호 2014.1.8
	// /** IDP에 등록한 OMP Association Key */
	// @Value("#{propertiesForSac['idp.sp_key']}")
	// public static String IDP_REQ_OMP_ASSOC_KEY;
	// /** IDP로 부터 발급된 Service ID */
	@Value("#{propertiesForSac['idp.sp_id']}")
	public String IDP_REQ_OMP_SERVICE_ID;

	// /** IDP 인증 후 서비스 리턴 URL */
	// // 로그인, SSO 인증
	// public static String IDP_REDT_URL_USER_AUTH = "";
	// /** IDP SSO 로그아웃 처리 후 리턴 URL */
	// // 로그 아웃
	// public static String IDP_REDT_URL_USER_LOGOUT = "";
	// // 회원가입
	// public static String IDP_REDT_URL_USER_REGIST_AUTH = "";
	// // 회원정보 변경
	// public static String IDP_REDT_URL_USER_MODIFY_PROFILE_AUTH = "";
	// // 이메일 변경
	// public static String IDP_REDT_URL_USER_MODIFY_EMAIL_AUTH = "";
	// // 패스워드 변경
	// public static String IDP_REDT_URL_USER_MODIFY_PWD_AUTH = "";
	//
	// /*
	// * IDP 통합 회원 고도화 추가
	// */
	// // 타채널 아이디 인증
	// public static String IDP_REDT_URL_OTHER_CHANNEL_ID_AUTH = "";
	// // 타채널 가입 리스트 조회
	// public static String IDP_REDT_URL_OTHER_CHANNEL_LIST = "";

	// static {
	// IDPManager.getInstance();
	// }

	public IDPManager() {

		// 필요 없는 것으로 판단되어 주석 처리 - 임재호 2014.1.8
		// this.idpReceiver = new IDPReceiver();
		// this.idpSender = new IDPSender();
		// 일단 테스트를 위해 주석 처리 - 임재호 2014.1.8
		// this.smsapi = new SMSApi();

		// 필요 없는 것으로 판단되어 주석 처리 - 임재호 2014.1.8
		// this.setPropString();
	}

	// 필요 없는 것으로 판단되어 주석 처리 - 임재호 2014.1.8
	// private void setPropString() {
	// try {
	//
	// this.prop = new DevProperties();
	//
	// OMP_SERVICE_DOMAIN = this.prop.getString("omp.service.domain");
	// OMP_SERVICE_URL = this.prop.getString("omp.dev.service.url");
	// OMP_SERVICE_URL_HTTPS = this.prop.getString("omp.dev.service.url.https");
	// OMP_SERVICE_URL_IP = this.prop.getString("omp.dev.service.url.ip");
	//
	// IDP_REQUEST_URL = this.prop.getString("omp.idp.request.url");
	// IDP_REQUEST_URL_HTTPS = this.prop.getString("omp.idp.request.url.https");
	//
	// IDP_REQUEST_SERVER_URL = this.prop.getString("omp.idp.request.server.url");
	// IDP_REQUEST_SERVER_URL_HTTPS = this.prop.getString("omp.idp.request.server.url.https");
	//
	// IDP_REQ_OMP_ASSOC_KEY = this.prop.getString("omp.idp.request.assockey");
	// IDP_REQ_OMP_SERVICE_ID = this.prop.getString("omp.idp.request.serviceid");
	//
	// IDP_REDT_URL_USER_AUTH = this.prop.getString("omp.dev.idp.redturl.user.auth");
	// IDP_REDT_URL_USER_LOGOUT = this.prop.getString("omp.dev.idp.redturl.user.logout");
	// IDP_REDT_URL_USER_REGIST_AUTH = this.prop.getString("omp.dev.idp.redturl.user.regist.auth");
	// IDP_REDT_URL_USER_MODIFY_PROFILE_AUTH =
	// this.prop.getString("omp.dev.idp.redturl.user.modifyProfile.auth");
	// IDP_REDT_URL_USER_MODIFY_EMAIL_AUTH = this.prop.getString("omp.dev.idp.redturl.user.modifyEmail.auth");
	// IDP_REDT_URL_USER_MODIFY_PWD_AUTH = this.prop.getString("omp.dev.idp.redturl.user.modifyPwd.auth");
	//
	// logger.info("<setPropString> IDP_REQ_OMP_SERVICE_ID : " + IDP_REQ_OMP_SERVICE_ID);
	// } catch (Exception e) {
	// // TODO Auto-generated catch block
	// e.printStackTrace();
	// }
	//
	// }

	// 필요 없는 것으로 판단되어 주석 처리 - 임재호 2014.1.8
	// /**
	// * IDPManager Singleton 객체를 반환한다.
	// *
	// * @return
	// */
	// public synchronized static IDPManager getInstance() {
	// if (instance == null) {
	// instance = new IDPManager();
	// logger.info("IDPManager Instance Create");
	// }
	// logger.info("IDPManager Instance is not null");
	// return instance;
	// }

	// -------------------------------------------------
	// 기본 API
	// -------------------------------------------------

	/**
	 * 서비스 가입 여부를 체크한다. (이메일 기준)
	 * 
	 * @param email
	 *            이메일
	 * @return
	 * @throws Exception
	 */
	public IDPReceiverM alredyJoinCheckByEmail(String email) throws Exception {
		return this.alredyJoinCheck(email, IDP_PARAM_KEY_TYPE_EMAIL);
	}

	/**
	 * 서비스 가입 여부를 체크한다. (주민등록번호 기준)
	 * 
	 * @param sn
	 *            주민등록번호
	 * @return
	 * @throws Exception
	 * @deprecated : IDP 실명 기반 API 삭제
	 */
	@Deprecated
	public IDPReceiverM alredyJoinCheckBySn(String ssn) throws Exception {
		return this.alredyJoinCheck(ssn, IDP_PARAM_KEY_TYPE_SN);
	}

	/**
	 * 서비스 가입 여부를 체크한다.
	 * 
	 * @param checkKey
	 *            가입체크 대상 정보
	 * @param checkKeyType
	 *            가입체크 대상 구분
	 * @return
	 * @throws Exception
	 */
	public IDPReceiverM alredyJoinCheck(String checkKey, String checkKeyType) throws Exception {
		IDPSenderM sendData = new IDPSenderM();

		sendData.setUrl(IDP_REQ_URL_JOIN);
		sendData.setCmd(IDP_REQ_CMD_ALEADY_JOIN_CHECK);
		sendData.setResp_type(IDP_PARAM_RESP_TYPE_XML);
		sendData.setResp_flow(IDP_PARAM_RESP_FLOW_RESPONSE);
		sendData.setKey(checkKey);
		sendData.setKey_type(checkKeyType);

		return this.sendIDP(sendData);
	}

	/**
	 * ID 중복 체크
	 * 
	 * @param id
	 *            아이디
	 * @return
	 * @throws Exception
	 */
	public IDPReceiverM checkDupID(String id) throws Exception {
		IDPSenderM sendData = new IDPSenderM();

		sendData.setUrl(IDP_REQ_URL_JOIN);
		sendData.setCmd(IDP_REQ_CMD_DUPLICATE_ID_CHECK);
		sendData.setResp_type(IDP_PARAM_RESP_TYPE_XML);
		sendData.setResp_flow(IDP_PARAM_RESP_FLOW_RESPONSE);
		sendData.setUser_id(id);

		return this.sendIDP(sendData);
	}

	/**
	 * 실명인증 (내국인)
	 * 
	 * @param name
	 *            이름
	 * @param ssn
	 *            주민번호
	 * @return
	 * @throws Exception
	 * @deprecated : IDP API 주민번호 필드 삭제
	 */
	@Deprecated
	public IDPReceiverM realNameAuthForNative(String name, String ssn) throws Exception {
		return this.realNameAuth(IDP_PARAM_KEY_AUTH_TYPE_NATIVE, name, ssn);
	}

	/**
	 * 실명인증 (외국인)
	 * 
	 * @param name
	 *            이름
	 * @param ssn
	 *            주민번호
	 * @return
	 * @throws Exception
	 * @deprecated : IDP API 주민번호 필드 삭제
	 */
	@Deprecated
	public IDPReceiverM realNameAuthForForeign(String name, String ssn) throws Exception {
		return this.realNameAuth(IDP_PARAM_KEY_AUTH_TYPE_FOREIGN, name, ssn);
	}

	/**
	 * 실명인증
	 * 
	 * @param personType
	 *            실명인증 대상 구분
	 * @param name
	 *            이름
	 * @param ssn
	 *            주민번호
	 * @return
	 * @throws Exception
	 * @deprecated : IDP API 주민번호 필드 삭제
	 */
	@Deprecated
	public IDPReceiverM realNameAuth(String personType, String name, String ssn) throws Exception {
		IDPSenderM sendData = new IDPSenderM();

		// 외국인 실명인증 메소드가 따로 존재하나 실명인증 소스가 사방에 퍼져있어 본 메소드만 수정. 20100108 soohee
		if (ssn != null && ssn.length() > 7) {
			String firstStr = ssn.substring(6, 7);
			if ("5".equals(firstStr) || "6".equals(firstStr)) {
				personType = IDP_PARAM_KEY_AUTH_TYPE_FOREIGN;
			} else {
				personType = IDP_PARAM_KEY_AUTH_TYPE_NATIVE;
			}
		}

		sendData.setUrl(IDP_REQ_URL_REALNAME_AUTH);
		sendData.setCmd(IDP_REQ_CMD_REALNAME_AUTH);
		sendData.setResp_type(IDP_PARAM_RESP_TYPE_XML);
		sendData.setResp_flow(IDP_PARAM_RESP_FLOW_RESPONSE);
		sendData.setType(personType);
		sendData.setUser_name(name);
		sendData.setUser_social_number(ssn);

		return this.sendIDPHttps(sendData); // TODO 스테이징 반영 시 https
	}

	/**
	 * 인증번호 발송을 요청한다.
	 * 
	 * @param mdn
	 * @return
	 * @throws Exception
	 */
	public IDPReceiverM sendMobileAuthCode(String mdn) throws Exception {
		return this.sendMobileAuthCode(mdn, null, null);
	}

	/**
	 * 인증번호 발송을 요청한다.
	 * 
	 * @param mdn
	 *            휴대폰번호
	 * @param telType
	 *            이통사구분
	 * @return
	 * @throws Exception
	 */
	public IDPReceiverM sendMobileAuthCode(String mdn, String telType) throws Exception {
		return this.sendMobileAuthCode(mdn, telType, null);
	}

	/**
	 * 인증번호 발송을 요청한다.
	 * 
	 * @param mdn
	 *            휴대폰번호
	 * @param telType
	 *            이통사구분
	 * @param ssn
	 *            주민번호 앞 7자리
	 * @return
	 * @throws Exception
	 */
	public IDPReceiverM sendMobileAuthCode(String mdn, String telType, String ssn) throws Exception {
		return this.sendMobileAuthCode(mdn, telType, ssn, null);

	}

	/**
	 * 인증번호 발송을 요청한다.
	 * 
	 * @param mdn
	 *            휴대폰번호
	 * @param telType
	 *            이통사구분
	 * @param ssn
	 *            주민번호 앞 7자리
	 * @return
	 * @throws Exception
	 */
	public IDPReceiverM sendMobileAuthCode(String mdn, String telType, String ssn, String scrId) throws Exception {
		IDPSenderM sendData = new IDPSenderM();

		sendData.setUrl(IDP_REQ_URL_MOBILE_AUTH);
		sendData.setCmd(IDP_REQ_CMD_MOBILE_AUTHCODE_SEND);
		sendData.setResp_type(IDP_PARAM_RESP_TYPE_XML);
		sendData.setResp_flow(IDP_PARAM_RESP_FLOW_RESPONSE);
		sendData.setUser_mdn(mdn);
		sendData.setSend_order("1"); // 인증번호 요청은 1로 세팅
		sendData.setScr_id(scrId);

		if (telType != null)
			sendData.setUser_mdn_type(telType);
		if (ssn != null)
			sendData.setUser_social_number(ssn);
		// 일단 테스트를 위해 주석 처리 - 임재호 2014.1.8
		// return this.smsapi.sendSMSAuthcode(sendData);
		return null;

	}

	/**
	 * 휴대폰 인증
	 * 
	 * @param mdn
	 *            휴대폰번호
	 * @param authCode
	 *            사용자 입력 인증코드
	 * @param mobileSign
	 * @param signData
	 * @return
	 * @throws Exception
	 */
	public IDPReceiverM mobileAuth(String mdn, String authCode, String mobileSign, String signData) throws Exception {
		return this.mobileAuth(mdn, authCode, mobileSign, signData, null); // TODO https
	}

	/**
	 * 휴대폰 인증
	 * 
	 * @param mdn
	 *            휴대폰번호
	 * @param authCode
	 *            사용자 입력 인증코드
	 * @param mobileSign
	 * @param signData
	 * @return
	 * @throws Exception
	 */
	public IDPReceiverM mobileAuth(String mdn, String authCode, String mobileSign, String signData, String scrId)
			throws Exception {
		IDPSenderM sendData = new IDPSenderM();

		sendData.setUrl(IDP_REQ_URL_MOBILE_AUTH);
		sendData.setCmd(IDP_REQ_CMD_MOBILE_AUTH);
		sendData.setResp_type(IDP_PARAM_RESP_TYPE_XML);
		sendData.setResp_flow(IDP_PARAM_RESP_FLOW_RESPONSE);
		sendData.setUser_mdn(mdn);
		sendData.setUser_code(authCode);
		sendData.setMobile_sign(mobileSign);
		sendData.setSign_data(signData);
		sendData.setScr_id(scrId);

		// 일단 테스트를 위해 주석 처리 - 임재호 2014.1.8
		// return this.smsapi.confirmSMSAuthcode(sendData); // TODO https
		return null;
	}

	/**
	 * 워터마크 이미지 URL을 가져온다.
	 * 
	 * @return
	 * @throws Exception
	 */
	public IDPReceiverM warterMarkImageUrl() throws Exception {
		IDPSenderM sendData = new IDPSenderM();

		sendData.setUrl(IDP_REQ_URL_WATERMARK_AUTH);
		sendData.setCmd(IDP_REQ_CMD_WATERMARK_AUTH_IMAGE);
		sendData.setResp_type(IDP_PARAM_RESP_TYPE_XML);
		sendData.setResp_flow(IDP_PARAM_RESP_FLOW_RESPONSE);

		return this.sendIDP(sendData);
	}

	/**
	 * 워터마크 인증
	 * 
	 * @param authCode
	 *            사용자가 입력한 인증코드
	 * @param imageSign
	 * @param signData
	 * @return
	 * @throws Exception
	 */
	public IDPReceiverM warterMarkAuth(String authCode, String imageSign, String signData) throws Exception {
		IDPSenderM sendData = new IDPSenderM();

		sendData.setUrl(IDP_REQ_URL_WATERMARK_AUTH);
		sendData.setCmd(IDP_REQ_CMD_WATERMARK_AUTH);
		sendData.setResp_type(IDP_PARAM_RESP_TYPE_XML);
		sendData.setResp_flow(IDP_PARAM_RESP_FLOW_RESPONSE);
		sendData.setUser_code(authCode);
		sendData.setImage_sign(imageSign);
		sendData.setSign_data(signData);

		return this.sendIDPHttps(sendData); // TODO https
	}

	/**
	 * Nate ID 인증
	 * 
	 * @param nateId
	 * @param natePwd
	 * @return
	 * @throws Exception
	 */
	public IDPReceiverM authNateId(String nateId, String natePwd) throws Exception {
		IDPSenderM sendData = new IDPSenderM();

		sendData.setUrl(IDP_REQ_URL_NATEID_AUTH);
		sendData.setCmd(IDP_REQ_CMD_NATEID_AUTH);
		sendData.setResp_type(IDP_PARAM_RESP_TYPE_XML);
		sendData.setResp_flow(IDP_PARAM_RESP_FLOW_RESPONSE);
		sendData.setUser_id(nateId);
		sendData.setUser_passwd(natePwd);

		return this.sendIDPHttps(sendData); // TODO https
	}

	/**
	 * 유선회원의 Password 인증
	 * 
	 * @param id
	 * @param pwd
	 * @return
	 * @throws Exception
	 */
	public IDPReceiverM authPwd(String id, String pwd) throws Exception {
		IDPSenderM sendData = new IDPSenderM();

		sendData.setUrl(IDP_REQ_URL_USER_AUTH);
		sendData.setCmd(IDP_REQ_CMD_AUTH_FOR_PWD);
		sendData.setResp_type(IDP_PARAM_RESP_TYPE_XML);
		sendData.setResp_flow(IDP_PARAM_RESP_FLOW_RESPONSE);
		sendData.setUser_id(id);
		sendData.setUser_passwd(pwd);

		return this.sendIDPHttps(sendData);
	}

	/**
	 * 유선회원의 MDN 인증
	 * 
	 * @param user_mdn
	 * @param user_code
	 * @return
	 * @throws Exception
	 */
	public IDPReceiverM authMdn(String user_mdn, String user_code, String mobile_sign, String sign_data)
			throws Exception {
		IDPSenderM sendData = new IDPSenderM();

		sendData.setUrl(IDP_REQ_URL_USER_AUTH);
		sendData.setCmd(IDP_REQ_CMD_AUTH_FOR_MDN);
		sendData.setResp_type(IDP_PARAM_RESP_TYPE_XML);
		sendData.setResp_flow(IDP_PARAM_RESP_FLOW_RESPONSE);
		sendData.setUser_mdn(user_mdn);
		sendData.setUser_code(user_code);
		sendData.setMobile_sign(mobile_sign);
		sendData.setSign_data(sign_data);

		return this.sendIDPHttps(sendData);
	}

	/**
	 * Email 인증 Link 재발급 (가가입 상태인 회원용)
	 * 
	 * @param id
	 * @return
	 * @throws Exception
	 */
	public IDPReceiverM getEmailAuthLink(String id) throws Exception {
		IDPSenderM sendData = new IDPSenderM();

		sendData.setUrl(IDP_REQ_URL_JOIN);
		sendData.setCmd(IDP_REQ_CMD_GET_EMAIL_AUTH);
		sendData.setResp_type(IDP_PARAM_RESP_TYPE_XML);
		sendData.setResp_flow(IDP_PARAM_RESP_FLOW_RESPONSE);
		sendData.setUser_id(id);

		return this.sendIDP(sendData);
	}

	public IDPReceiverM getEmailAuthLink(String user_auth_key, String id) throws Exception {
		IDPSenderM sendData = new IDPSenderM();
		sendData.setSp_id(this.IDP_REQ_OMP_SERVICE_ID);
		sendData.setSp_auth_key(user_auth_key);
		sendData.setUrl(IDP_REQ_URL_JOIN);
		sendData.setCmd(IDP_REQ_CMD_GET_EMAIL_AUTH);
		sendData.setResp_type(IDP_PARAM_RESP_TYPE_XML);
		sendData.setResp_flow(IDP_PARAM_RESP_FLOW_RESPONSE);
		sendData.setUser_id(id);
		return this.sendIDPHttps(sendData);
	}

	/**
	 * 가가입 회원의 이메일 변경
	 * 
	 * @param preEmail
	 * @param email
	 * @param pwd
	 * @param userKey
	 * @return
	 * @throws Exception
	 */
	public IDPReceiverM modifyEmail4JoinWait(String preEmail, String email, String pwd, String userKey)
			throws Exception {
		IDPSenderM sendData = new IDPSenderM();

		sendData.setUrl(IDP_REQ_URL_USER_INFO_MODIFY);
		sendData.setCmd(IDP_REQ_CMD_MODIFY_EMAIL);
		sendData.setResp_type(IDP_PARAM_RESP_TYPE_XML);
		sendData.setResp_flow(IDP_PARAM_RESP_FLOW_RESPONSE);
		sendData.setPre_user_email(preEmail);
		sendData.setUser_email(email);
		sendData.setUser_passwd(pwd);
		sendData.setUser_key(userKey);

		return this.sendIDPHttps(sendData); // TODO https 로 바꾸기
	}

	/**
	 * 
	 * @param user_auth_key
	 * @param key_type
	 * @param key
	 * @return
	 * @throws Exception
	 */
	public IDPReceiverM modifyAuthInfo(String user_auth_key, String key_type, String key) throws Exception {
		IDPSenderM sendData = new IDPSenderM();
		sendData.setUrl(IDP_REQ_URL_USER_INFO_MODIFY);
		sendData.setCmd(IDP_REQ_CMD_MODIFY_AUTH_INFO);
		sendData.setResp_type(IDP_PARAM_RESP_TYPE_XML);
		sendData.setResp_flow(IDP_PARAM_RESP_FLOW_RESPONSE);

		sendData.setUser_auth_key(user_auth_key);
		sendData.setKey_type(key_type);
		sendData.setKey(key);

		return this.sendIDPHttps(sendData); // TODO https 로 바꾸기
	}

	public IDPReceiverM modifyEmailAuthInfo(String sp_auth_key, String user_auth_key, String user_key, String pre_key,
			String key) throws Exception {
		IDPSenderM sendData = new IDPSenderM();
		sendData.setSp_id(this.IDP_REQ_OMP_SERVICE_ID);
		sendData.setUrl(IDP_REQ_URL_USER_INFO_MODIFY);
		sendData.setCmd(IDP_REQ_CMD_MODIFY_AUTH_INFO);
		sendData.setResp_type(IDP_PARAM_RESP_TYPE_XML);
		sendData.setResp_flow(IDP_PARAM_RESP_FLOW_RESPONSE);
		sendData.setSp_auth_key(sp_auth_key);
		sendData.setUser_key(user_key);
		sendData.setUser_auth_key(user_auth_key);
		sendData.setKey_type("1"); // email
		sendData.setKey(key);
		sendData.setPre_key(pre_key);

		return this.sendIDPHttps(sendData); // TODO https 로 바꾸기
	}

	public IDPReceiverM modifyPasswordAuthInfo(String user_auth_key, String user_key, String pre_key, String key)
			throws Exception {
		IDPSenderM sendData = new IDPSenderM();
		sendData.setSp_id(this.IDP_REQ_OMP_SERVICE_ID);
		sendData.setUrl(IDP_REQ_URL_USER_INFO_MODIFY);
		sendData.setCmd(IDP_REQ_CMD_MODIFY_AUTH_INFO);
		sendData.setResp_type(IDP_PARAM_RESP_TYPE_XML);
		sendData.setResp_flow(IDP_PARAM_RESP_FLOW_RESPONSE);
		sendData.setUser_key(user_key);
		sendData.setUser_auth_key(user_auth_key);
		sendData.setKey_type("2"); // password
		sendData.setKey(key);
		sendData.setPre_key(pre_key);

		return this.sendIDPHttps(sendData); // TODO https 로 바꾸기
	}

	/**
	 * 휴대폰인증 정보의 보안적용
	 * 
	 * @param phoneMeta
	 * @return
	 * @throws Exception
	 */
	public String makePhoneAuthKey(String phoneMeta) throws Exception {
		return this.idpSender.makePhoneAuthKey(phoneMeta);
	}

	/**
	 * 실명인증 정보의 보안적용
	 * 
	 * @param phoneMeta
	 * @return
	 * @throws Exception
	 */
	public String makeSnAuthKey(String mbrNm, String userId) throws Exception {
		return this.idpSender.makeSnAuthKey(mbrNm, userId);
	}

	// -------------------------------------------------
	// 회원 가입 API (IDP_REQ_CMD_JOIN_FOR_EMAIL)
	// -------------------------------------------------
	public IDPReceiverM joinMember(Map<String, Object> param) throws Exception {
		String userId = (String) param.get("user_id");
		String userPasswd = (String) param.get("user_passwd");
		String userEmail = (String) param.get("user_email");
		String userSocialNumber = (String) param.get("user_social_number");
		String userName = (String) param.get("user_name");
		String snAuthKey = (String) param.get("sn_auth_key");
		String userSex = (String) param.get("user_sex");
		String userBirthday = (String) param.get("user_birthday");
		String userCalendar = (String) param.get("user_calendar");
		String userZipcode = (String) param.get("user_zipcode");
		String userAddress = (String) param.get("user_address");
		String userAddress2 = (String) param.get("user_address2");
		String userTel = (String) param.get("user_tel");
		String isForeign = (String) param.get("is_foreign");
		String isRnameAuth = (String) param.get("is_rname_auth");
		String userPhone = (String) param.get("user_phone");
		String phoneAuthKey = (String) param.get("phone_auth_key");
		String watermarkAuth = (String) param.get("watermark_auth");
		String userCode = (String) param.get("user_code");
		String imageSign = (String) param.get("image_sign");
		String signData = (String) param.get("sign_data");
		String userCi = (String) param.get("user_ci");

		IDPSenderM sendData = new IDPSenderM();
		sendData.setUrl(IDP_REQ_URL_JOIN);
		sendData.setCmd(IDP_REQ_CMD_JOIN_FOR_EMAIL);
		sendData.setResp_type(IDP_PARAM_RESP_TYPE_XML);
		sendData.setResp_flow(IDP_PARAM_RESP_FLOW_RESPONSE);
		sendData.setUser_id(userId);
		sendData.setUser_passwd(userPasswd);
		sendData.setUser_email(userEmail);
		if (null != userSocialNumber)
			sendData.setUser_social_number(userSocialNumber);
		if (null != userName)
			sendData.setUser_name(userName);
		if (null != snAuthKey)
			sendData.setSn_auth_key(snAuthKey);
		// if(null != snAuthKey && (null != isRnameAuth &&
		// "Y".equals(isRnameAuth)))sendData.setSn_auth_key(makeSnAuthKey(userName, userId));
		if (null != userSex)
			sendData.setUser_sex(userSex);
		if (null != userBirthday)
			sendData.setUser_birthday(userBirthday);
		if (null != userCalendar)
			sendData.setUser_calendar(userCalendar);
		if (null != userZipcode)
			sendData.setUser_zipcode(userZipcode);
		if (null != userAddress)
			sendData.setUser_address(userAddress);
		if (null != userAddress2)
			sendData.setUser_address2(userAddress2);
		if (null != userTel)
			sendData.setUser_tel(userTel);
		if (null != isForeign)
			sendData.setIs_foreign(isForeign);
		sendData.setIs_rname_auth(isRnameAuth);
		if (null != userPhone)
			sendData.setUser_phone(userPhone);
		if (null != phoneAuthKey)
			sendData.setPhone_auth_key(phoneAuthKey);
		sendData.setWatermark_auth(watermarkAuth);
		if (null != userCode)
			sendData.setUser_code(userCode);
		if (null != imageSign)
			sendData.setImage_sign(imageSign);
		if (null != signData)
			sendData.setSign_data(signData);
		if (null != userCi)
			sendData.setUser_ci(userCi);

		return this.sendIDPHttps(sendData);
	}

	// -------------------------------------------------
	// 비 실명 회원 인증 API (redirect)
	// -------------------------------------------------
	/**
	 * 유선회원의 ID 인증
	 * 
	 * @param userId
	 *            사용자 ID
	 * @param userPwd
	 *            사용자 PWD
	 * @return
	 * @throws Exception
	 */
	public IDPReceiverM userAuthForId(String userId, String userPwd) throws Exception {
		IDPSenderM sendData = new IDPSenderM();

		sendData.setUrl(IDP_REQ_URL_USER_AUTH);
		sendData.setCmd(IDP_REQ_CMD_AUTH_FOR_ID);
		sendData.setResp_type(IDP_PARAM_RESP_TYPE_XML);
		sendData.setResp_flow(IDP_PARAM_RESP_FLOW_RESPONSE);
		sendData.setUser_id(userId);
		sendData.setUser_passwd(userPwd);

		return this.sendIDP(sendData);
	}

	/**
	 * 유선회원의 SSO로그아웃
	 */
	public IDPReceiverM userLogoutForSSO() throws Exception {
		IDPSenderM sendData = new IDPSenderM();

		sendData.setUrl(IDP_REQ_URL_USER_AUTH);
		sendData.setCmd(IDP_REQ_CMD_LOGOUT_FOR_SSO);
		sendData.setResp_type(IDP_PARAM_RESP_TYPE_XML);
		sendData.setResp_flow(IDP_PARAM_RESP_FLOW_RESPONSE);

		return this.sendIDPHttps(sendData);
	}

	// -------------------------------------------------
	// 회원 정보 조회 API
	// -------------------------------------------------

	/**
	 * 회원 기본 정보 조회 (User Key)
	 * 
	 * @param userAuthKey
	 *            사용자 인증시 IDP 로 부터 발급 받은 인증키
	 * @param userKey
	 *            회원가입시 IDP 로 부터 부여받는 사용자 키
	 * @return
	 * @throws Exception
	 */
	public IDPReceiverM searchUserCommonInfoByUserKey(String userAuthKey, String userKey) throws Exception {

		return this.searchUserCommonInfo(userAuthKey, IDP_PARAM_KEY_QUERY_KEY_TYPE_USER_KEY, userKey);
	}

	/**
	 * 회원 기본 정보 조회 (ID)
	 * 
	 * @param userAuthKey
	 *            사용자 인증시 IDP 로 부터 발급 받은 인증키
	 * @param userID
	 * @return
	 * @throws Exception
	 */
	public IDPReceiverM searchUserCommonInfoByID(String userAuthKey, String userID) throws Exception {

		return this.searchUserCommonInfo(userAuthKey, IDP_PARAM_KEY_QUERY_KEY_TYPE_ID, userID);
	}

	/**
	 * 회원 기본 정보 조회 (EMAIL)
	 * 
	 * @param userAuthKey
	 *            사용자 인증시 IDP 로 부터 발급 받은 인증키
	 * @param email
	 * @return
	 * @throws Exception
	 */
	public IDPReceiverM searchUserCommonInfoByEmail(String userAuthKey, String email) throws Exception {

		return this.searchUserCommonInfo(userAuthKey, IDP_PARAM_KEY_QUERY_KEY_TYPE_EMAIL, email);
	}

	/**
	 * 회원 기본 정보 조회
	 * 
	 * @param userAuthKey
	 *            사용자 인증시 IDP 로 부터 발급 받은 인증키
	 * @param queryKeyType
	 *            쿼리 조건 구분
	 * @param queryKeyValue
	 *            쿼리 값
	 * @return
	 * @throws Exception
	 */
	public IDPReceiverM searchUserCommonInfo(String userAuthKey, String queryKeyType, String queryKeyValue)
			throws Exception {
		IDPSenderM sendData = new IDPSenderM();

		sendData.setUrl(IDP_REQ_URL_USER_INFO_SEARCH);
		sendData.setCmd(IDP_REQ_CMD_FIND_COMMON_PROFILE_FOR_SERVER);
		sendData.setResp_type(IDP_PARAM_RESP_TYPE_XML);
		sendData.setResp_flow(IDP_PARAM_RESP_FLOW_RESPONSE);
		sendData.setUser_auth_key(userAuthKey);
		sendData.setKey_type(queryKeyType);
		sendData.setKey(queryKeyValue);

		return this.sendIDPHttps(sendData); // TODO https
	}

	/**
	 * 회원기본정보조회 (SP 서버용)
	 * 
	 * @param keyType
	 * @param key
	 * @return
	 * @throws Exception
	 */
	public IDPReceiverM searchUserCommonInfo4SPServer(String keyType, String key) throws Exception {
		IDPSenderM sendData = new IDPSenderM();

		sendData.setUrl(IDP_REQ_URL_USER_INFO_SEARCH);
		sendData.setCmd(IDP_REQ_CMD_FIND_COMMON_PROFILE_FOR_SERVER);
		sendData.setResp_type(IDP_PARAM_RESP_TYPE_XML);
		sendData.setResp_flow(IDP_PARAM_RESP_FLOW_RESPONSE);
		sendData.setKey_type(keyType);
		sendData.setKey(key);

		return this.sendIDP(sendData); // TODO 스테이징 반영 시 https
	}

	/**
	 * ID 조회 (EMAIL)
	 * 
	 * @param email
	 * @param userCode
	 *            사용자 입력 코드
	 * @param imageSign
	 *            워터마크 인증을 위해 IDP로부터 받은 signature
	 * @param signData
	 *            signature을 구성한 source data
	 * @return
	 * @throws Exception
	 */
	public IDPReceiverM searchUserIDByEmail(String email, String userCode, String imageSign, String signData)
			throws Exception {

		return this.searchUserID(IDP_PARAM_KEY_QUERY_ID_KEY_TYPE_EMAIL, email, userCode, imageSign, signData);
	}

	/**
	 * ID 조회 (SSN)
	 * 
	 * @param ssn
	 * @return
	 * @throws Exception
	 * @deprecated : IDP API 주민번호 필드 삭제
	 */
	@Deprecated
	public IDPReceiverM searchUserIDBySN(String ssn) throws Exception {

		return this.searchUserID(IDP_PARAM_KEY_QUERY_ID_KEY_TYPE_SN, ssn, null, null, null);
	}

	/**
	 * ID 조회 (User Key)
	 * 
	 * @param userKey
	 *            회원가입시 IDP 로 부터 부여받는 사용자 키
	 * @param userCode
	 *            사용자 입력 코드
	 * @param imageSign
	 *            워터마크 인증을 위해 IDP로부터 받은 signature
	 * @param signData
	 *            signature을 구성한 source data
	 * @return
	 * @throws Exception
	 */
	public IDPReceiverM searchUserIDByUserKey(String userKey, String userCode, String imageSign, String signData)
			throws Exception {

		return this.searchUserID(IDP_PARAM_KEY_QUERY_ID_KEY_TYPE_USERKEY, userKey, userCode, imageSign, signData);
	}

	/**
	 * ID 조회
	 * 
	 * @param queryKeyType
	 *            쿼리 조건 구분
	 * @param queryKeyValue
	 *            쿼리 값
	 * @param userCode
	 *            사용자 입력 코드
	 * @param imageSign
	 *            워터마크 인증을 위해 IDP로부터 받은 signature
	 * @param signData
	 *            signature을 구성한 source data
	 * @return
	 * @throws Exception
	 */
	public IDPReceiverM searchUserID(String queryKeyType, String queryKeyValue, String userCode, String imageSign,
			String signData) throws Exception {
		IDPSenderM sendData = new IDPSenderM();

		sendData.setUrl(IDP_REQ_URL_USER_INFO_SEARCH);
		sendData.setCmd(IDP_REQ_CMD_FIND_ID);
		sendData.setResp_type(IDP_PARAM_RESP_TYPE_XML);
		sendData.setResp_flow(IDP_PARAM_RESP_FLOW_RESPONSE);
		sendData.setKey_type(queryKeyType);
		sendData.setKey(queryKeyValue);

		if (IDP_PARAM_KEY_QUERY_ID_KEY_TYPE_SN.equals(queryKeyType)) {
			sendData.setWatermark_auth(IDP_PARAM_KEY_WATERMARK_AUTH_NON_INCLISION);
		} else {
			sendData.setWatermark_auth(IDP_PARAM_KEY_WATERMARK_AUTH_INCLISION);
			sendData.setUser_code(userCode);
			sendData.setImage_sign(imageSign);
			sendData.setSign_data(signData);
		}

		return this.sendIDP(sendData);
	}

	/*
	 * 통합아이디 기존사용자 아이디 찾기
	 */
	public IDPReceiverM searchUserID(String queryKeyType, String queryKeyValue) throws Exception {
		IDPSenderM sendData = new IDPSenderM();

		sendData.setUrl(IDP_REQ_URL_USER_INFO_SEARCH);
		sendData.setCmd(IDP_REQ_CMD_FIND_ID);
		sendData.setResp_type(IDP_PARAM_RESP_TYPE_XML);
		sendData.setResp_flow(IDP_PARAM_RESP_FLOW_RESPONSE);
		sendData.setKey_type(queryKeyType);
		sendData.setKey(queryKeyValue);
		sendData.setWatermark_auth(IDP_PARAM_KEY_WATERMARK_AUTH_NON_INCLISION);

		return this.sendIDP(sendData);
	}

	/**
	 * 임시 비밀 번호 발급 (EMAIL)
	 * 
	 * @param email
	 * @return
	 * @throws Exception
	 */
	public IDPReceiverM findPwdByEmail(String email) throws Exception {
		return this.findPwd(IDP_PARAM_KEY_QUERY_PWD_KEY_TYPE_EMAIL, email);
	}

	/**
	 * 임시 비밀 번호 발급 (ID)
	 * 
	 * @param userID
	 * @return
	 * @throws Exception
	 */
	public IDPReceiverM findPwdByID(String userID) throws Exception {
		return this.findPwd(IDP_PARAM_KEY_QUERY_PWD_KEY_TYPE_ID, userID);
	}

	/**
	 * 임시 비밀 번호 발급 (ID) 워터마크 포함
	 * 
	 * @param userID
	 * @return
	 * @throws Exception
	 */
	public IDPReceiverM findPwdByID(String userID, String userCode, String imageSign, String signData) throws Exception {
		return this.findPwd(IDP_PARAM_KEY_QUERY_PWD_KEY_TYPE_ID, userID, userCode, imageSign, signData);
	}

	/**
	 * 임시 비밀 번호 발급 (User Key)
	 * 
	 * @param userKey
	 *            회원가입시 IDP 로 부터 부여받는 사용자 키
	 * @return
	 * @throws Exception
	 */
	public IDPReceiverM findPwdByUserKey(String userKey) throws Exception {
		return this.findPwd(IDP_PARAM_KEY_QUERY_PWD_KEY_TYPE_USERKEY, userKey);
	}

	/**
	 * 임시 비밀 번호 발급
	 * 
	 * @param queryKeyType
	 *            쿼리 조건 구분
	 * @param queryKeyValue
	 *            쿼리 값
	 * @return
	 * @throws Exception
	 */
	public IDPReceiverM findPwd(String queryKeyType, String queryKeyValue) throws Exception {
		return this.findPwd(queryKeyType, queryKeyValue, null, null, null);
	}

	/**
	 * 임시 비밀 번호 발급 (워터마크 포함)
	 * 
	 * @param queryKeyType
	 *            쿼리 조건 구분
	 * @param queryKeyValue
	 *            쿼리 값
	 * @return
	 * @throws Exception
	 */
	public IDPReceiverM findPwd(String queryKeyType, String queryKeyValue, String userCode, String imageSign,
			String signData) throws Exception {
		IDPSenderM sendData = new IDPSenderM();

		sendData.setUrl(IDP_REQ_URL_USER_INFO_SEARCH);
		sendData.setCmd(IDP_REQ_CMD_FIND_PWD);
		sendData.setResp_type(IDP_PARAM_RESP_TYPE_XML);
		sendData.setResp_flow(IDP_PARAM_RESP_FLOW_RESPONSE);
		sendData.setWatermark_auth(IDP_PARAM_KEY_WATERMARK_AUTH_NON_INCLISION);
		sendData.setKey_type(queryKeyType);
		sendData.setKey(queryKeyValue);
		if (userCode != null && imageSign != null && signData != null) {
			sendData.setWatermark_auth(IDP_PARAM_KEY_WATERMARK_AUTH_INCLISION);
			sendData.setUser_code(userCode);
			sendData.setImage_sign(imageSign);
			sendData.setSign_data(signData);
		}
		return this.sendIDPHttps(sendData); // TODO https
	}

	// -------------------------------------------------
	// 회원 정보 변경 API (redirect)
	// -------------------------------------------------

	// -------------------------------------------------
	// 회원 해지 API
	// -------------------------------------------------

	/**
	 * 사용자 해지 (ID)
	 * 
	 * @param userAuthKey
	 *            사용자 인증시 IDP 로 부터 발급 받은 인증키
	 * @param userID
	 * @return
	 * @throws Exception
	 */
	public IDPReceiverM secedeUserByID(String userAuthKey, String userID) throws Exception {
		return this.secedeUser(userAuthKey, IDP_PARAM_KEY_SECEDE_KEY_TYPE_ID, userID);
	}

	/**
	 * 사용자 해지 (User Key)
	 * 
	 * @param userAuthKey
	 *            사용자 인증시 IDP 로 부터 발급 받은 인증키
	 * @param userKey
	 *            회원가입시 IDP 로 부터 부여받는 사용자 키
	 * @return
	 * @throws Exception
	 */
	public IDPReceiverM secedeUserByUserKey(String userAuthKey, String userKey) throws Exception {
		return this.secedeUser(userAuthKey, IDP_PARAM_KEY_SECEDE_KEY_TYPE_USERKEY, userKey);
	}

	/**
	 * 사용자 해지
	 * 
	 * @param userAuthKey
	 *            사용자 인증시 IDP 로 부터 발급 받은 인증키
	 * @param secedeKeyType
	 *            해지 조건 구분
	 * @param secedeKeyValue
	 *            해지 사용자 uniq key
	 * @return
	 * @throws Exception
	 */
	public IDPReceiverM secedeUser(String userAuthKey, String secedeKeyType, String secedeKeyValue) throws Exception {
		IDPSenderM sendData = new IDPSenderM();

		sendData.setUrl(IDP_REQ_URL_SECEDE);
		sendData.setCmd(IDP_REQ_CMD_SECEDE_USER);
		sendData.setResp_type(IDP_PARAM_RESP_TYPE_XML);
		sendData.setResp_flow(IDP_PARAM_RESP_FLOW_RESPONSE);
		// sendData.setWatermark_auth(IDP_PARAM_KEY_WATERMARK_AUTH_NON_INCLISION);
		sendData.setUser_auth_key(userAuthKey);
		sendData.setKey_type(secedeKeyType);
		sendData.setKey(secedeKeyValue);

		return this.sendIDPHttps(sendData); // TODO https
	}

	// -------------------------------------------------
	// 무선 API
	// -------------------------------------------------

	/**
	 * 가입여부 체크
	 * 
	 * @param mdn
	 *            휴대폰번호
	 * @return
	 * @throws Exception
	 */
	public IDPReceiverM aleadyJoinCheck4Mdn(String mdn) throws Exception {
		IDPSenderM sendData = new IDPSenderM();

		sendData.setUrl(IDP_REQ_URL_JOIN);
		sendData.setCmd(IDP_REQ_CMD_ALEADY_JOIN_MDN);
		sendData.setResp_type(IDP_PARAM_RESP_TYPE_XML);
		sendData.setResp_flow(IDP_PARAM_RESP_FLOW_RESPONSE);
		sendData.setUser_mdn(mdn);

		return this.sendIDP(sendData);
	}

	/**
	 * 모바일 회원 인증
	 * 
	 * @param mdn
	 *            휴대폰번호
	 * @return
	 * @throws Exception
	 */
	public IDPReceiverM authForWap(String mdn) throws Exception {
		IDPSenderM sendData = new IDPSenderM();

		sendData.setUrl(IDP_REQ_URL_USER_AUTH);
		sendData.setCmd(IDP_REQ_CMD_AUTH_FOR_WAP);
		sendData.setResp_type(IDP_PARAM_RESP_TYPE_XML);
		sendData.setResp_flow(IDP_PARAM_RESP_FLOW_RESPONSE);
		sendData.setUser_mdn(mdn);

		return this.sendIDP(sendData);
	}

	/**
	 * 모바일 회원가입
	 * 
	 * @param mdn
	 *            휴대폰번호
	 * @return
	 * @throws Exception
	 */
	public IDPReceiverM join4Wap(String mdn) throws Exception {
		IDPSenderM sendData = new IDPSenderM();

		sendData.setUrl(IDP_REQ_URL_JOIN);
		sendData.setCmd(IDP_REQ_CMD_JOIN_FOR_WAP);
		sendData.setResp_type(IDP_PARAM_RESP_TYPE_XML);
		sendData.setResp_flow(IDP_PARAM_RESP_FLOW_RESPONSE);
		sendData.setMdn_corp(IDP_PARAM_KEY_USER_MDN_TYPE_SKT);
		sendData.setUser_mdn(mdn);

		return this.sendIDP(sendData);
	}

	/**
	 * 사용자 해지 (모바일 회원용)
	 * 
	 * @param mdn
	 *            모바일 회원의 휴대폰번호
	 * @return
	 * @throws Exception
	 */
	public IDPReceiverM secedeUser4Wap(String mdn) throws Exception {
		IDPSenderM sendData = new IDPSenderM();

		sendData.setUrl(IDP_REQ_URL_SECEDE);
		sendData.setCmd(IDP_REQ_CMD_SECEDE_FOR_WAP);
		sendData.setResp_type(IDP_PARAM_RESP_TYPE_XML);
		sendData.setResp_flow(IDP_PARAM_RESP_FLOW_RESPONSE);
		sendData.setUser_mdn(mdn);

		return this.sendIDP(sendData);
	}

	// -------------------------------------------------
	// 부가서비스 API
	// -------------------------------------------------

	/**
	 * 부가서비스 가입
	 * 
	 * @param mdn
	 *            휴대폰 번호
	 * @param svcCd
	 *            부가서비스 코드
	 * @return
	 * @throws Exception
	 */
	public IDPReceiverM joinSupService(String mdn, String svcCode) throws Exception {
		return this.joinSupService(mdn, svcCode, null);
	}

	/**
	 * 부가서비스 가입
	 * 
	 * @param mdn
	 *            휴대폰 번호
	 * @param svcCd
	 *            부가서비스 코드
	 * @param svcMngNum
	 *            휴대폰 번호에 대한 서비스 관리번호
	 * @return
	 * @throws Exception
	 */
	public IDPReceiverM joinSupService(String mdn, String svcCd, String svcMngNum) throws Exception {
		IDPSenderM sendData = new IDPSenderM();

		sendData.setUrl(IDP_REQ_URL_JOIN);
		sendData.setCmd(IDP_REQ_CMD_JOIN_SUP_SERVICE);
		sendData.setResp_type(IDP_PARAM_RESP_TYPE_XML);
		sendData.setResp_flow(IDP_PARAM_RESP_FLOW_RESPONSE);
		sendData.setUser_mdn(mdn);
		sendData.setSvc_code(svcCd);

		if (svcMngNum != null && svcMngNum.length() > 0)
			sendData.setUser_svc_mng_num(svcMngNum);

		return this.sendIDP(sendData);
	}

	/**
	 * 부가서비스 해지
	 * 
	 * @param mdn
	 *            휴대폰 번호
	 * @param svcCd
	 *            부가서비스 코드
	 * @return
	 * @throws Exception
	 */
	public IDPReceiverM secedeSupService(String mdn, String svcCode) throws Exception {
		return this.secedeSupService(mdn, svcCode, null);
	}

	/**
	 * 부가서비스 해지
	 * 
	 * @param mdn
	 *            휴대폰 번호
	 * @param svcCd
	 *            부가서비스 코드
	 * @param svcMngNum
	 *            휴대폰 번호에 대한 서비스 관리번호
	 * @return
	 * @throws Exception
	 */
	public IDPReceiverM secedeSupService(String mdn, String svcCd, String svcMngNum) throws Exception {
		IDPSenderM sendData = new IDPSenderM();

		sendData.setUrl(IDP_REQ_URL_SECEDE);
		sendData.setCmd(IDP_REQ_CMD_SECEDE_SUP_SERVICE);
		sendData.setResp_type(IDP_PARAM_RESP_TYPE_XML);
		sendData.setResp_flow(IDP_PARAM_RESP_FLOW_RESPONSE);
		sendData.setUser_mdn(mdn);
		sendData.setSvc_code(svcCd);

		if (svcMngNum != null && svcMngNum.length() > 0)
			sendData.setUser_svc_mng_num(svcMngNum);

		return this.sendIDP(sendData);
	}

	/**
	 * T-MAP 가입 가능 단말 여부 조회
	 * 
	 * @param mdn
	 *            휴대폰 번호
	 * @return
	 * @throws Exception
	 */
	public IDPReceiverM tmapServiceAvalibleCheck(String mdn) throws Exception {
		IDPSenderM sendData = new IDPSenderM();

		sendData.setUrl(IDP_REQ_URL_USER_INFO_SEARCH);
		sendData.setCmd(IDP_REQ_CMD_TMAP_SERVICE_AVALIBLE_CHECK);
		sendData.setResp_type(IDP_PARAM_RESP_TYPE_XML);
		sendData.setResp_flow(IDP_PARAM_RESP_FLOW_RESPONSE);
		sendData.setUser_mdn(mdn);

		return this.sendIDP(sendData);
	}

	/**
	 * 부가서비스 가입 여부 조회
	 * 
	 * @param mdn
	 *            휴대폰 번호
	 * @param svcCd
	 *            부가서비스 코드
	 * @return
	 * @throws Exception
	 */
	public IDPReceiverM tmapServiceCheck(String mdn, String svcCd) throws Exception {
		IDPSenderM sendData = new IDPSenderM();

		sendData.setUrl(IDP_REQ_URL_USER_INFO_SEARCH);
		sendData.setCmd(IDP_REQ_CMD_TMAP_SERVICE_CHECK);
		sendData.setResp_type(IDP_PARAM_RESP_TYPE_XML);
		sendData.setResp_flow(IDP_PARAM_RESP_FLOW_RESPONSE);
		sendData.setUser_mdn(mdn);
		sendData.setSvc_code(svcCd);

		return this.sendIDP(sendData);
	}

	// ---------------------------------------------------------------------------------------------------------

	/**
	 * IDP 연동 결과를 XML로 전달받아 객체를 생성 반환한다.
	 * 
	 * @param sendData
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("rawtypes")
	public IDPReceiverM sendIDP(IDPSenderM sendData) throws Exception {

		StopWatch stopWatch = new StopWatch();

		stopWatch.start();

		Hashtable param = this.idpSender.makeSendParam(sendData);
		SendReq sendReq = new SendReq();
		sendReq.setProtocol(SendReq.HTTP_PROTOCOL.HTTP);
		// TODO : IDP 연동시 POST로 전달하면 에러 발생하여 무조건 GET으로 가도록 셋팅 함 - 임재호 2014.1.8
		// TODO : IDP 연동시 POST로 전달할지 GET으로 전달할지 로직이나 메서드에서 판단 하여 넘겨 주어야 함, 임시로 하드코딩함 - 임재호 2014.1.8
		sendReq.setMethod(SendReq.HTTP_METHOD.GET);
		sendReq.setIm(false);
		sendReq.setUrl(sendData.getUrl());
		sendReq.setReqParam(param);

		SendRes sendRes = this.idpSCI.send(sendReq);

		IDPReceiverM receiveData = sendRes.getIdpReceiverM();

		stopWatch.stop();
		// OMC로그 주석 처리 함
		// IDPOMCLog.setLog(stopWatch, receiveData);

		return receiveData;
	}

	/**
	 * HTTPS 프로토콜로 IDP 연동 결과를 XML로 전달받아 객체를 생성 반환한다.
	 * 
	 * @param sendData
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("rawtypes")
	public IDPReceiverM sendIDPHttps(IDPSenderM sendData) throws Exception {

		StopWatch stopWatch = new StopWatch();

		stopWatch.start();

		Hashtable param = this.idpSender.makeSendParam(sendData);

		SendReq sendReq = new SendReq();
		sendReq.setProtocol(SendReq.HTTP_PROTOCOL.HTTPS);
		// TODO : IDP 연동시 POST로 전달할지 GET으로 전달할지 로직이나 메서드에서 판단 하여 넘겨 주어야 함, 임시로 하드코딩함 - 임재호 2014.1.8
		sendReq.setMethod(SendReq.HTTP_METHOD.POST);
		sendReq.setIm(false);
		sendReq.setUrl(sendData.getUrl());
		sendReq.setReqParam(param);

		SendRes sendRes = this.idpSCI.send(sendReq);

		IDPReceiverM receiveData = sendRes.getIdpReceiverM();

		stopWatch.stop();
		// OMC로그 주석 처리 함
		// IDPOMCLog.setLog(stopWatch, receiveData);

		return receiveData;
	}

	@SuppressWarnings("unused")
	public IDPReceiverM modifyProfile(Map<String, Object> param) throws Exception {
		IDPSenderM sendData = new IDPSenderM();

		sendData.setUrl(IDP_REQ_URL_USER_INFO_MODIFY);
		sendData.setCmd(IDP_REQ_CMD_MODIFY_PROFILE);
		sendData.setResp_type(IDP_PARAM_RESP_TYPE_XML);
		sendData.setResp_flow(IDP_PARAM_RESP_FLOW_RESPONSE);

		String key_type = "";
		String key = "";
		String user_auth_key = "";
		String user_job_code = "";
		String user_zipcode = "";
		String user_address = "";
		String user_address2 = "";
		String user_sex = "";
		String user_birthday = "";
		String user_social_number = "";
		String user_name = "";
		String user_phone = "";
		String service_profile = "";
		String user_calendar = "";
		String user_tel = "";
		String user_type = "";
		String is_foreign = "";
		String user_nation = "";
		String is_rname_auth = "";
		String sn_auth_key = "";
		String phone_auth_key = "";
		String sp_auth_key = "";
		String user_id = "";
		String user_ci = "";

		sp_auth_key = (String) param.get("sp_auth_key");
		key_type = (String) param.get("key_type");
		key = (String) param.get("key");
		user_auth_key = (String) param.get("user_auth_key");
		user_job_code = (String) param.get("user_job_code");
		user_zipcode = (String) param.get("user_zipcode");
		user_address = (String) param.get("user_address");
		user_address2 = (String) param.get("user_address2");
		user_sex = (String) param.get("user_sex");
		user_birthday = (String) param.get("user_birthday");
		user_social_number = (String) param.get("user_social_number");
		user_name = (String) param.get("user_name");
		user_phone = (String) param.get("user_phone");
		service_profile = (String) param.get("service_profile");
		user_calendar = (String) param.get("user_calendar");
		user_tel = (String) param.get("user_tel");
		user_type = (String) param.get("user_type");
		is_foreign = (String) param.get("is_foreign");
		user_nation = (String) param.get("user_nation");
		is_rname_auth = (String) param.get("is_rname_auth");
		sn_auth_key = (String) param.get("sn_auth_key");
		phone_auth_key = (String) param.get("phone_auth_key");
		user_id = (String) param.get("user_id");
		user_ci = (String) param.get("user_ci");

		if (user_phone != null && !"".equals(user_phone)) {
			try {
				if (phone_auth_key == null || "".equals(phone_auth_key)) {
					phone_auth_key = this.makePhoneAuthKey(user_phone);
				}
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		/*
		 * if ("Y".equals(is_rname_auth)) { if (!"".equals(user_name) && !"".equals(user_id)) { try { sn_auth_key =
		 * makeSnAuthKey(user_name, user_id); logger.info("sn_auth_key : "+sn_auth_key); } catch (Exception e) { // TODO
		 * Auto-generated catch block e.printStackTrace(); } } else { is_rname_auth = "N"; } }
		 */

		// sendData.setSp_id(Constants.OMP_IDP_SP_ID);
		// sendData.setSp_auth_key(sn_auth_key);
		sendData.setUser_auth_key(user_auth_key);
		sendData.setKey_type(key_type);
		sendData.setKey(key);
		sendData.setSp_auth_key(sp_auth_key);

		if (user_job_code != null) {
			sendData.setUser_job_code(user_job_code);
		}
		if (user_zipcode != null) {
			sendData.setUser_zipcode(user_zipcode);
		}
		if (user_address != null) {
			sendData.setUser_address(user_address);
		}
		if (user_address2 != null) {
			sendData.setUser_address2(user_address2);
		}
		if (user_sex != null) {
			sendData.setUser_sex(user_sex);
		}
		if (user_birthday != null) {
			sendData.setUser_birthday(user_birthday);
		}
		if (user_social_number != null) {
			sendData.setUser_social_number(user_social_number);
		}
		if (user_name != null) {
			sendData.setUser_name(user_name);
		}
		if (user_phone != null) {
			sendData.setUser_phone(user_phone);
		}
		if (service_profile != null) {
			// nefer 2009-12-10 어디에 쓰는 필드인지 판명 불가
			// requestParam += "&service_profile="+service_profile;
		}
		if (user_calendar != null) {
			sendData.setUser_calendar(user_calendar);
		}
		if (user_tel != null) {
			sendData.setUser_tel(user_tel);
		}
		if (user_type != null) {
			sendData.setUser_type(user_type);
		}
		if (is_foreign != null) {
			// nefer 2009-12-10 어디에 쓰는 필드인지 판명 불가
			// requestParam += "&is_foreign="+is_foreign;
		}
		if (user_nation != null) {
			// nefer 2009-12-10 어디에 쓰는 필드인지 판명 불가
			// requestParam += "&user_nation="+user_nation;
		}
		if (is_rname_auth != null) {
			sendData.setIs_rname_auth(is_rname_auth);
		}
		if (phone_auth_key != null) {
			sendData.setPhone_auth_key(phone_auth_key);
		}
		if (sn_auth_key != null) {
			sendData.setSn_auth_key(sn_auth_key);
		}
		if (user_ci != null) {
			sendData.setUser_ci(user_ci);
		}

		return this.sendIDP(sendData); // TODO https
	}

	// IDP 통합 고도화 -------------------------------------------------------------
	/**
	 * 타채널 ID 인증
	 * 
	 * @param id
	 *            /pwd
	 * @return
	 * @throws Exception
	 */
	public IDPReceiverM otherChannelIdAuth(String user_id, String user_passwd) throws Exception {
		IDPSenderM sendData = new IDPSenderM();

		sendData.setUrl(IDP_REQ_URL_USER_AUTH);
		sendData.setCmd(IDP_REQ_CMD_OTHER_CHANNEL_ID_AUTH);
		sendData.setResp_type(IDP_PARAM_RESP_TYPE_XML);
		sendData.setResp_flow(IDP_PARAM_RESP_FLOW_RESPONSE);
		sendData.setUser_id(user_id);
		sendData.setUser_passwd(user_passwd);

		return this.sendIDPHttps(sendData);
	}

	/**
	 * 가입된 타채널 조회
	 * 
	 * @param key_type
	 *            /key
	 * @return
	 * @throws Exception
	 *             key type 1:user_id, 2:email
	 */
	public IDPReceiverM otherChannelList(String key_type, String key) throws Exception {
		IDPSenderM sendData = new IDPSenderM();

		sendData.setUrl(IDP_REQ_URL_USER_INFO_SEARCH);
		sendData.setCmd(IDP_REQ_CMD_OTHER_CHANNEL_LIST);
		sendData.setResp_type(IDP_PARAM_RESP_TYPE_XML);
		sendData.setResp_flow(IDP_PARAM_RESP_FLOW_RESPONSE);
		sendData.setKey_type(key_type);
		sendData.setKey(key);

		return this.sendIDP(sendData);
	}

	/**
	 * 가입동의에 의한 회원가입
	 * 
	 * @param user_id
	 *            , passwd
	 * @return
	 * @throws Exception
	 */
	public IDPReceiverM otherChannelRegist(String user_id, String user_passwd) throws Exception {
		IDPSenderM sendData = new IDPSenderM();

		sendData.setUrl(IDP_REQ_URL_JOIN);
		sendData.setCmd(IDP_REQ_CMD_OTHER_CHANNEL_REGIST);
		sendData.setResp_type(IDP_PARAM_RESP_TYPE_XML);
		sendData.setResp_flow(IDP_PARAM_RESP_FLOW_RESPONSE);
		sendData.setUser_id(user_id);
		sendData.setUser_passwd(user_passwd);
		// sendData.setApprove_sp_id(IDPManager.IDP_REQ_OMP_SERVICE_ID);
		return this.sendIDP(sendData);
	}

	// skt폰 여부
	public IDPReceiverM findModelId(String mdn) throws Exception {
		IDPSenderM sendData = new IDPSenderM();

		sendData.setUrl(IDP_REQ_URL_USER_INFO_SEARCH);
		sendData.setCmd(IDP_REQ_CMD_FIND_MODEL_ID);
		sendData.setResp_type(IDP_PARAM_RESP_TYPE_XML);
		sendData.setResp_flow(IDP_PARAM_RESP_FLOW_RESPONSE);
		sendData.setUser_mdn(mdn);

		return this.sendIDP(sendData);
	}

	// SMS 발송
	public IDPReceiverM mobileSend(String user_mdn, String comment) throws Exception {
		return this.mobileSend(user_mdn, comment, null);
	}

	// SMS 발송
	public IDPReceiverM mobileSend(String user_mdn, String comment, String scrId) throws Exception {
		IDPSenderM sendData = new IDPSenderM();
		if (scrId == null || scrId.equals(""))
			scrId = "US004528";

		sendData.setUrl(IDP_REQ_URL_MOBILE_SEND);
		sendData.setCmd(IDP_REQ_CMD_MOBILE_SEND);
		sendData.setResp_type(IDP_PARAM_RESP_TYPE_XML);
		sendData.setResp_flow(IDP_PARAM_RESP_FLOW_RESPONSE);
		sendData.setUser_mdn(user_mdn);
		sendData.setComment(comment);
		sendData.setScr_id(scrId);

		if (scrId.equals("US004520"))
			sendData.setSend_order("1"); // 웹2폰
		else if (scrId.equals("US004521"))
			sendData.setSend_order("1"); // QR코드 발송
		else if (scrId.equals("US004522"))
			sendData.setSend_order("1"); // 추천하기 발송
		else if (scrId.equals("US004528"))
			sendData.setSend_order("2"); // 기타공통

		// 일단 테스트를 위해 주석 처리 - 임재호 2014.1.8
		// return this.smsapi.sendSimpleSMS(sendData); // real Https
		return null;
	}

	/**
	 * IDP sms 전송 api - 보내는 이 추가
	 * 
	 * @param receiverMdn
	 * @param comment
	 * @param senderMdn
	 * @return
	 * @throws Exception
	 * @author J.I.PARK
	 * @date 2011. 10. 13.
	 */
	public IDPReceiverM idpSmsSend(String receiverMdn, String msg, String senderMdn) throws Exception {

		// TODO.. 현재는 보내는 사람 전화번호가 없음 .
		// 보내는 이 전화번호(senderMdn)가 들어오게 되면 아래 로직에 보내는 이 정보 추가 필요. 2011.10.13 jiaprk mod
		return this.mobileSend(receiverMdn, msg);
	}

	/**
	 * IDP sms 전송 api - 보내는 이 추가
	 * 
	 * @param receiverMdn
	 * @param comment
	 * @param senderMdn
	 * @return
	 * @throws Exception
	 * @author J.I.PARK
	 * @date 2011. 10. 13.
	 */
	public IDPReceiverM idpSmsSend(String receiverMdn, String msg, String senderMdn, String scrId) throws Exception {

		// TODO.. 현재는 보내는 사람 전화번호가 없음 .
		// 보내는 이 전화번호(senderMdn)가 들어오게 되면 아래 로직에 보내는 이 정보 추가 필요. 2011.10.13 jiaprk mod
		return this.mobileSend(receiverMdn, msg, scrId);
	}

	/**
	 * 부가서비스 가입 처리를 요청하는 api
	 * 
	 * @param mdn
	 * @param svcCode
	 *            연동 코드 : NA00003492 (임시)티스토어정액제 / NA00003493 (실제)티스토어정액제
	 * @return
	 * @throws Exception
	 * @author bw yun
	 * @date 2012. 3. 30.
	 */
	public IDPReceiverM joinSupServiceRequest(String mdn, String svcCode) throws Exception {
		IDPSenderM sendData = new IDPSenderM();
		// 연동 코드 : NA00003492 (임시)티스토어정액제 / NA00003493 (실제)티스토어정액제

		sendData.setUrl(IDP_REQ_URL_JOIN);
		sendData.setCmd(IDP_REQ_CMD_JOIN_SUP_SERVICE_REQUEST);
		sendData.setResp_type(IDP_PARAM_RESP_TYPE_XML);
		sendData.setResp_flow(IDP_PARAM_RESP_FLOW_RESPONSE);
		sendData.setUser_mdn(mdn);
		sendData.setSvc_code(svcCode);

		return this.sendIDP(sendData);
	}

	/**
	 * 부가 서비스 해지 처리를 요청하는 API
	 * 
	 * @param mdn
	 * @param svcCode
	 *            연동 코드 : NA00003492 (임시)티스토어정액제 / NA00003493 (실제)티스토어정액제
	 * @return
	 * @throws Exception
	 * @author bw yun
	 * @date 2012. 3. 30.
	 */
	public IDPReceiverM secedeSupServiceRequest(String mdn, String svcCode) throws Exception {
		IDPSenderM sendData = new IDPSenderM();
		// 연동 코드 : NA00003492 (임시)티스토어정액제 / NA00003493 (실제)티스토어정액제

		sendData.setUrl(IDP_REQ_URL_SECEDE);
		sendData.setCmd(IDP_REQ_CMD_SECEDE_SUP_SERVICE_REQUEST);
		sendData.setResp_type(IDP_PARAM_RESP_TYPE_XML);
		sendData.setResp_flow(IDP_PARAM_RESP_FLOW_RESPONSE);
		sendData.setUser_mdn(mdn);
		sendData.setSvc_code(svcCode);

		return this.sendIDP(sendData);
	}

	/**
	 * skt가입자 요금제 조회
	 * 
	 * @param mdn
	 * @return
	 * @throws Exception
	 * @author bw yun
	 * @date 2012. 4. 3.
	 */
	public IDPReceiverM findBill(String mdn) throws Exception {
		IDPSenderM sendData = new IDPSenderM();
		// 연동 코드 : NA00003492 (임시)티스토어정액제 / NA00003493 (실제)티스토어정액제

		sendData.setUrl(IDP_REQ_URL_USER_INFO_SEARCH);
		sendData.setCmd(IDP_REQ_CMD_FIND_BILL);
		sendData.setResp_type(IDP_PARAM_RESP_TYPE_XML);
		sendData.setResp_flow(IDP_PARAM_RESP_FLOW_RESPONSE);
		sendData.setUser_mdn(mdn);

		return this.sendIDP(sendData);
	}

	/**
	 * 토큰 생성
	 * 
	 * @param mdn
	 * @return
	 * @throws Exception
	 * @author yk kim
	 * @date 2012. 12. 10.
	 */
	public IDPReceiverM createToken(String user_id) throws Exception {
		IDPSenderM sendData = new IDPSenderM();
		sendData.setUrl(IDP_REQ_URL_CREATE_TOKEN);
		sendData.setCmd(IDP_REQ_CMD_CREATE_TOKEN);
		sendData.setUser_id(user_id);
		sendData.setTarget_service("tstore");

		return this.sendIDP(sendData);
	}

	/**
	 * 토큰 맵핑
	 * 
	 * @param mdn
	 * @return
	 * @throws Exception
	 * @author yk kim
	 * @date 2012. 12. 10.
	 */
	public IDPReceiverM mappingToken(String user_id) throws Exception {
		IDPSenderM sendData = new IDPSenderM();
		sendData.setUrl(IDP_REQ_URL_MAPPING_TOKEN);
		sendData.setCmd(IDP_REQ_CMD_MAPPING_TOKEN);
		sendData.setUser_id(user_id);
		sendData.setService_token("tstore");
		sendData.setService_secret("tstore");
		sendData.setService_id(user_id);
		sendData.setTarget_service("tstore");

		return this.sendIDP(sendData);
	}

	/**
	 * 토큰 조회
	 * 
	 * @param mdn
	 * @return
	 * @throws Exception
	 * @author yk kim
	 * @date 2012. 12. 10.
	 */
	public IDPReceiverM searchToken(String user_id) throws Exception {
		IDPSenderM sendData = new IDPSenderM();
		sendData.setUrl(IDP_REQ_URL_SEARCH_TOKEN);
		sendData.setCmd(IDP_REQ_CMD_SEARCH_TOKEN);
		sendData.setUser_id(user_id);
		sendData.setTarget_service("tstore");
		sendData.setSp_token("tstore");
		return this.sendIDP(sendData);
	}

	/**
	 * 토큰 삭제
	 * 
	 * @param mdn
	 * @return
	 * @throws Exception
	 * @author yk kim
	 * @date 2012. 12. 10.
	 */
	public IDPReceiverM deleteToken(String user_id) throws Exception {
		IDPSenderM sendData = new IDPSenderM();
		sendData.setUrl(IDP_REQ_URL_DELETE_TOKEN);
		sendData.setCmd(IDP_REQ_CMD_DELETE_TOKEN);
		sendData.setUser_id(user_id);
		sendData.setTarget_service("tstore");
		sendData.setType("1");
		return this.sendIDP(sendData);
	}
}
