/*
 * Copyright (c) 2013 SK planet.
 * All right reserved.
 *
 * This software is the confidential and proprietary information of SK planet.
 * You shall not disclose such Confidential Information and
 * shall use it only in accordance with the terms of the license agreement
 * you entered into with SK planet.
 */
package com.skplanet.storeplatform.sac.external.idp.service;

import java.util.HashMap;
import java.util.Hashtable;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.skplanet.storeplatform.external.client.idp.sci.IDPSCI;
import com.skplanet.storeplatform.external.client.idp.vo.IDPTxReq;
import com.skplanet.storeplatform.external.client.idp.vo.IDPTxRes;
import com.skplanet.storeplatform.sac.external.idp.ImIDPManager;
import com.skplanet.storeplatform.sac.external.idp.ImIDPReceiver;
import com.skplanet.storeplatform.sac.external.idp.ImIDPSender;
import com.skplanet.storeplatform.sac.external.idp.util.IdpDateUtil;
import com.skplanet.storeplatform.sac.external.idp.vo.ImIDPReceiverM;
import com.skplanet.storeplatform.sac.external.idp.vo.ImIDPSenderM;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;

@Service("ImIdpSacService")
@Transactional
public class ImIdpSacServiceImpl implements ImIdpSacService {

	private static final Logger logger = LoggerFactory.getLogger(ImIDPManager.class);

	@Autowired
	private IDPSCI idpSCI;

	private static ImIDPReceiver idpReceiver = new ImIDPReceiver();
	private static ImIDPSender imidpSender = new ImIDPSender();
	private static ImIDPReceiverM receivData = new ImIDPReceiverM();

	/** OMP 서비스 도메인 */
	public static String OMP_SERVICE_DOMAIN = "https://www.skplanetoneid.com";
	/** OMP 서비스 URL */
	public static String OMP_SERVICE_URL = "omp.dev.service.url";
	/** OMP 서비스 URL (HTTPS) */
	public static String OMP_SERVICE_URL_HTTPS = "omp.dev.service.url.https";
	/** OMP 서비스 URL IP */
	public static String OMP_SERVICE_URL_IP = "omp.dev.service.url.ip";

	/** IDP 요청 도메인 (HTTP) */
	public static String IDP_REQUEST_URL = "omp.imidp.request.url";
	/** IDP 요청 도메인 (HTTPS) */
	public static String IDP_REQUEST_URL_HTTPS = "omp.imidp.request.url.https";

	/** IDP에 등록한 OMP Association Key */
	public static String IDP_REQ_OMP_ASSOC_KEY = "omp.idp.request.assockey";
	/** IDP로 부터 발급된 Service ID */
	public static String IDP_REQ_OMP_SERVICE_ID = "omp.idp.request.serviceid";

	public static String IDP_OPERATION_MODE = "omp.idp.request.operation";

	/* One ID 2.0 Portal Inbound Url Information */
	public static String ONEID_PORTAL_DOMAIN = "https://www.skplanetoneid.com";// domain
	public static String ONEID_PORTAL_JOIN = "omp.oneid.portal.join";// 가입
	public static String ONEID_PORTAL_TRANS_JOIN = "omp.oneid.portal.transJoin";// 바꾸기 (기존회원을 one id portal로 전환
	public static String ONEID_PORTAL_TRANS_JOIN_POINT = "omp.oneid.portal.transJoinPoint";// 포인트 가입 처리 바꾸기
	public static String ONEID_PORTAL_AGREE = "omp.oneid.portal.agree";// 서비스 이용동의
	public static String ONEID_PORTAL_AGREE_POINT = "omp.oneid.portal.agreePoint";// 서비스 이용동의
	public static String ONEID_PORTAL_DELETE = "omp.oneid.portal.delete"; // One ID 탈퇴
	public static String ONEID_PORTAL_UNLOCKUSER = "omp.oneid.portal.unlockUser";// 아이디 잠금 해지
	public static String ONEID_PORTAL_SEARCHID = "omp.oneid.portal.searchIdForm";//
	public static String ONEID_PORTAL_SEARCHPASSWORD = "omp.oneid.portal.searchPasswordForm";//
	public static String ONEID_PORTAL_GUIDE = "omp.oneid.portal.guide";//

	/* One ID 2.0 */

	/**
	 * 2.1.1 신규가입요청
	 */
	@Override
	public ImIDPReceiverM createUser(Map<String, Object> param) throws Exception {

		String userId = (String) param.get("user_id");
		String authType = (String) param.get("auth_type");
		String oldId = (String) param.get("old_id");
		String userPasswd = (String) param.get("user_passwd");
		String userTn = (String) param.get("user_tn");
		String userTnType = (String) param.get("user_tn_type");
		String isUserTnAuth = (String) param.get("is_user_tn_auth");
		String isUserTnOwn = (String) param.get("is_user_tn_own");
		String userTnNationCd = (String) param.get("user_tn_nation_cd");
		String userEmail = (String) param.get("user_email");
		String isEmailAuth = (String) param.get("is_email_auth");
		String userType = (String) param.get("user_type");
		String userName = (String) param.get("user_name");
		String isRnameAuth = (String) param.get("is_rname_auth");
		String rnameAuthSstCode = (String) param.get("rname_auth_sst_code");
		String userMdn = (String) param.get("user_mdn");
		String userMdnAuthKey = (String) param.get("user_mdn_auth_key");
		String userSex = (String) param.get("user_sex");
		String userBirthday = (String) param.get("user_birthday");
		String userCalendar = (String) param.get("user_calendar");
		String userZipcode = (String) param.get("user_zipcode");
		String userAddress = (String) param.get("user_address");
		String userAddress2 = (String) param.get("user_address2");
		String userNationCode = (String) param.get("user_nation_code");
		String userNationName = (String) param.get("user_nation_name");
		String isImChanged = (String) param.get("is_im_changed");
		String transSstList = (String) param.get("trans_sst_list");
		String userStatusCode = (String) param.get("user_status_code");
		String parentType = (String) param.get("parent_type");
		String parentRnameAuthType = (String) param.get("parent_rname_auth_type");
		String parentRnameAuthKey = (String) param.get("parent_rname_auth_key");
		String parentName = (String) param.get("parent_name");
		String parentMdn = (String) param.get("parent_mdn");
		String parentEmail = (String) param.get("parent_email");
		String parentApproveDate = (String) param.get("parent_approve_date");
		String isParentApprove = (String) param.get("is_parent_approve");
		String parentApproveSstCode = (String) param.get("parent_approve_sst_code");
		String consentTac = (String) param.get("consent_tac");
		String joinPathCode = (String) param.get("join_path_code");
		String userPasswdModifyDate = (String) param.get("user_passwd_modify_date");

		ImIDPSenderM sendData = new ImIDPSenderM();
		sendData.setUrl(imidpSender.IDP_REQ_URL_JOIN);
		sendData.setCmd(imidpSender.IDP_REQ_CMD_CREATE_USER);
		sendData.setResp_type(IDP_PARAM_RESP_TYPE_XML);
		sendData.setResp_flow(IDP_PARAM_RESP_FLOW_RESPONSE);
		sendData.setUser_id(userId);
		sendData.setIs_biz_auth("N");
		sendData.setUser_passwd(userPasswd);
		sendData.setUser_passwd_type(IDP_PARAM_PASSWD_TYPE_NORMAL);
		sendData.setUser_passwd_modify_date(userPasswdModifyDate);
		if (null == authType || "".equals(authType))
			authType = IDP_PARAM_REGIST_AUTH_TYPE_EMAIL;
		sendData.setAuth_type(authType);
		sendData.setUser_tn(userTn);
		if (isUserTnAuth != null)
			sendData.setIs_user_tn_auth(isUserTnAuth);
		if (userTnType != null)
			sendData.setUser_tn_type(userTnType);
		if (userTnNationCd != null)
			sendData.setUser_tn_nation_cd(userTnNationCd);
		if (oldId != null)
			sendData.setOld_id(oldId);
		if (isUserTnOwn != null)
			sendData.setIs_user_tn_own(isUserTnOwn);
		if (userEmail != null)
			sendData.setUser_email(userEmail);
		if (isEmailAuth != null)
			sendData.setIs_email_auth(isEmailAuth);
		if (userType != null)
			sendData.setUser_type(userType);
		if (userName != null)
			sendData.setUser_name(userName);
		if (isRnameAuth != null)
			sendData.setIs_rname_auth(isRnameAuth);
		if (rnameAuthSstCode != null)
			sendData.setRname_auth_sst_code(rnameAuthSstCode);
		if (userMdn != null)
			sendData.setUser_mdn(userMdn);
		if (userMdnAuthKey != null)
			sendData.setUser_mdn_auth_key(userMdnAuthKey);
		if (userSex != null)
			sendData.setUser_sex(userSex);
		if (userBirthday != null)
			sendData.setUser_birthday(userBirthday);
		if (userCalendar != null)
			sendData.setUser_calendar(userCalendar);
		if (userZipcode != null)
			sendData.setUser_zipcode(userZipcode);
		if (userAddress != null)
			sendData.setUser_address(userAddress);
		if (userAddress2 != null)
			sendData.setUser_address2(userAddress2);
		if (userNationCode != null)
			sendData.setUser_nation_code(userNationCode);
		if (userNationName != null)
			sendData.setUser_nation_name(userNationName);
		sendData.setLang_code(IDP_PARAM_LANG_CODE);
		if (isImChanged != null)
			sendData.setIs_im_changed(isImChanged);
		if (transSstList != null)
			sendData.setTans_sst_list(transSstList);
		if (userStatusCode != null)
			sendData.setUser_status_code(userStatusCode);
		if (parentType != null)
			sendData.setParent_type(parentType);
		if (parentRnameAuthType != null)
			sendData.setParent_rname_auth_type(parentRnameAuthType);
		if (parentRnameAuthKey != null)
			sendData.setParent_rname_auth_key(parentRnameAuthKey);
		if (parentName != null)
			sendData.setParent_name(parentName);
		if (parentMdn != null)
			sendData.setParent_mdn(parentMdn);
		if (parentEmail != null)
			sendData.setParent_email(parentEmail);
		if (parentApproveDate != null)
			sendData.setParent_approve_date(parentApproveDate);
		if (isParentApprove != null)
			sendData.setIs_parent_approve(isParentApprove);
		if (parentApproveSstCode != null)
			sendData.setParent_approve_sst_code(parentApproveSstCode);
		if (consentTac != null)
			sendData.setConsent_tac(consentTac);
		if (joinPathCode != null)
			sendData.setJoin_path_code(joinPathCode);
		sendData.setJoin_date(IdpDateUtil.getCurrentDate());
		sendData.setJoin_time(IdpDateUtil.getCurrentTime());
		sendData.setModify_req_date(IdpDateUtil.getCurrentDate());
		sendData.setModify_req_time(IdpDateUtil.getCurrentTime());

		// TODO : business logic
		return this.sendIDPHttps(sendData); // sendIDP(sendData);
	}

	/**
	 * 2.1.2 서비스이용동의요청
	 */
	@Override
	public ImIDPReceiverM agreeUser(Map<String, Object> param) throws Exception {
		String key = (String) param.get("key");
		String key_type = (String) param.get("key_type");
		String user_mdn = (String) param.get("user_mdn");
		String user_mdn_auth_key = (String) param.get("user_mdn_auth_key");
		String parent_type = (String) param.get("parent_type");
		String parent_rname_auth_type = (String) param.get("parent_rname_auth_type");
		String parent_rname_auth_key = (String) param.get("parent_rname_auth_key");
		String parent_name = (String) param.get("parent_name");
		String parent_mdn = (String) param.get("parent_mdn");
		String parent_email = (String) param.get("parent_email");
		String parent_approve_date = (String) param.get("parent_approve_date");
		String is_parent_approve = (String) param.get("is_parent_approve");
		String parent_approve_sst_code = (String) param.get("parent_approve_sst_code");

		ImIDPSenderM sendData = new ImIDPSenderM();
		sendData.setUrl(imidpSender.IDP_REQ_URL_JOIN);
		sendData.setCmd(imidpSender.IDP_REQ_CMD_AGREE_USER);
		sendData.setResp_type(IDP_PARAM_RESP_TYPE_XML);
		sendData.setResp_flow(IDP_PARAM_RESP_FLOW_RESPONSE);
		sendData.setKey(key);
		if (null == key_type || "".equals(key_type))
			key_type = IDP_PARAM_KEY_TYPE_IM_SERVICE_NO;
		sendData.setKey_type(key_type);
		if (user_mdn != null)
			sendData.setUser_mdn(user_mdn);
		if (user_mdn_auth_key != null)
			sendData.setUser_mdn_auth_key(user_mdn_auth_key);
		if (parent_type != null)
			sendData.setParent_type(parent_type);
		if (parent_rname_auth_type != null)
			sendData.setParent_rname_auth_type(parent_rname_auth_type);
		if (parent_rname_auth_key != null)
			sendData.setParent_rname_auth_key(parent_rname_auth_key);
		if (parent_name != null)
			sendData.setParent_name(parent_name);
		if (parent_mdn != null)
			sendData.setParent_mdn(parent_mdn);
		if (parent_email != null)
			sendData.setParent_email(parent_email);
		if (parent_approve_date != null)
			sendData.setParent_approve_date(parent_approve_date);
		if (is_parent_approve != null)
			sendData.setIs_parent_approve(is_parent_approve);
		if (parent_approve_sst_code != null)
			sendData.setParent_approve_sst_code(parent_approve_sst_code);
		sendData.setModify_req_date(IdpDateUtil.getCurrentDate());
		sendData.setModify_req_time(IdpDateUtil.getCurrentTime());

		// TODO : business logic
		return this.sendIDPHttps(sendData); // sendIDP(sendData);
	}

	/**
	 * 2.1.3 회원탈퇴요청
	 */
	@Override
	public ImIDPReceiverM DeleteUser(Map<String, Object> param) throws Exception {
		String key = (String) param.get("key");
		String user_auth_key = (String) param.get("user_auth_key");
		String term_reason_cd = (String) param.get("term_reason_cd");

		ImIDPSenderM sendData = new ImIDPSenderM();
		sendData.setUrl(ImIDPSender.IDP_REQ_URL_SECEDE);
		sendData.setCmd(ImIDPSender.IDP_REQ_CMD_DELETE_USER);
		sendData.setResp_type(IDP_PARAM_RESP_TYPE_XML);
		sendData.setResp_flow(IDP_PARAM_RESP_FLOW_RESPONSE);
		sendData.setKey(key);
		sendData.setKey_type(IDP_PARAM_KEY_TYPE_IM_SERVICE_NO);
		sendData.setUser_auth_key(user_auth_key);
		if (term_reason_cd != null)
			sendData.setTerm_reason_cd(term_reason_cd);
		sendData.setModify_req_date(IdpDateUtil.getCurrentDate());
		sendData.setModify_req_time(IdpDateUtil.getCurrentTime());

		return this.sendIDPHttps(sendData); // sendIDP(sendData);
	}

	/**
	 * 2.1.4 개별약관해지요청
	 */
	@Override
	public ImIDPReceiverM discardUser(Map<String, Object> param) throws Exception {
		String key = (String) param.get("key");
		String user_auth_key = (String) param.get("user_auth_key");
		String term_reason_cd = (String) param.get("term_reason_cd");

		ImIDPSenderM sendData = new ImIDPSenderM();
		sendData.setUrl(ImIDPSender.IDP_REQ_URL_SECEDE);
		sendData.setCmd(ImIDPSender.IDP_REQ_CMD_DISAGREE_USER);
		sendData.setResp_type(IDP_PARAM_RESP_TYPE_XML);
		sendData.setResp_flow(IDP_PARAM_RESP_FLOW_RESPONSE);
		sendData.setKey(key);
		sendData.setKey_type(IDP_PARAM_KEY_TYPE_IM_SERVICE_NO);
		sendData.setUser_auth_key(user_auth_key);
		if (term_reason_cd != null)
			sendData.setTerm_reason_cd(term_reason_cd);
		sendData.setModify_req_date(IdpDateUtil.getCurrentDate());
		sendData.setModify_req_time(IdpDateUtil.getCurrentTime());

		return this.sendIDPHttps(sendData); // sendIDP(sendData);

	}

	/**
	 * 2.1.5 공통프로파일조회요청 (For User Agent)
	 */
	@Override
	public ImIDPReceiverM userInfoSearchBrowser(Map<String, Object> param) throws Exception {
		String key = (String) param.get("key");
		String user_auth_key = (String) param.get("user_auth_key");

		ImIDPSenderM sendData = new ImIDPSenderM();
		sendData.setUrl(ImIDPSender.IDP_REQ_URL_USER_INFO_SEARCH);
		sendData.setCmd(ImIDPSender.IDP_REQ_CMD_FIND_COMMON_PROFILE_FOR_BROWSER);
		sendData.setResp_type(IDP_PARAM_RESP_TYPE_XML);
		sendData.setResp_flow(IDP_PARAM_RESP_FLOW_RESPONSE);
		sendData.setKey(key);
		sendData.setKey_type(IDP_PARAM_KEY_TYPE_IM_SERVICE_NO);
		sendData.setUser_auth_key(user_auth_key);
		sendData.setReq_date(IdpDateUtil.getCurrentDate());
		sendData.setReq_time(IdpDateUtil.getCurrentTime());

		return this.sendIDPHttps(sendData);
	}

	/**
	 * 2.1.6 공통프로파일조회요청 (For Server)
	 */
	@Override
	public ImIDPReceiverM userInfoSearchServer(String imServiceNo) throws Exception {
		ImIDPSenderM sendData = new ImIDPSenderM();
		sendData.setUrl(ImIDPSender.IDP_REQ_URL_USER_INFO_SEARCH);
		sendData.setCmd(ImIDPSender.IDP_REQ_CMD_FIND_COMMON_PROFILE_FOR_SERVER);
		sendData.setResp_type(IDP_PARAM_RESP_TYPE_XML);
		sendData.setResp_flow(IDP_PARAM_RESP_FLOW_RESPONSE);
		sendData.setKey(imServiceNo);
		sendData.setKey_type(IDP_PARAM_KEY_TYPE_IM_SERVICE_NO);
		sendData.setReq_date(IdpDateUtil.getCurrentDate());
		sendData.setReq_time(IdpDateUtil.getCurrentTime());

		return this.sendIDPHttps(sendData); // sendIDP(sendData);
	}

	/**
	 * 2.1.7 공통프로파일정보수정요청
	 */
	@Override
	public ImIDPReceiverM updateUserInfo(Map<String, Object> param) throws Exception {
		String key = (String) param.get("key");
		String user_auth_key = (String) param.get("user_auth_key");
		String user_tn = (String) param.get("user_tn");
		String is_user_tn_auth = (String) param.get("is_user_tn_auth");
		String is_user_tn_own = (String) param.get("is_user_tn_own");
		String user_tn_type = (String) param.get("user_tn_type");
		String user_tn_nation_cd = (String) param.get("user_tn_nation_cd");
		String user_email = (String) param.get("user_email");
		String is_email_auth = (String) param.get("is_email_auth");
		String user_mdn = (String) param.get("user_mdn");
		String user_mdn_auth_key = (String) param.get("user_mdn_auth_key");
		String user_sex = (String) param.get("user_sex");
		String user_calendar = (String) param.get("user_calendar");
		String user_zipcode = (String) param.get("user_zipcode");
		String user_address = (String) param.get("user_address");
		String user_address2 = (String) param.get("user_address2");
		String user_nation_code = (String) param.get("user_nation_code");
		String user_status_code = (String) param.get("user_status_code");
		String user_type = (String) param.get("user_type");
		if (!(null != user_type && !"".equals(user_type))) {
			user_type = "1";
		}

		String udt_type_cd = (String) param.get("udt_type_cd");
		if ("Y".equals(is_user_tn_auth) && "Y".equals(is_email_auth))
			udt_type_cd = "3";
		else if ("Y".equals(is_user_tn_auth))
			udt_type_cd = "1";
		else if ("Y".equals(is_email_auth))
			udt_type_cd = "2";

		String is_biz_auth = (String) param.get("is_biz_auth");
		if (!(null != is_biz_auth && !"".equals(is_biz_auth))) {
			is_biz_auth = "N";
		}

		ImIDPSenderM sendData = new ImIDPSenderM();
		sendData.setUrl(ImIDPSender.IDP_REQ_URL_USER_INFO_MODIFY);
		sendData.setCmd(ImIDPSender.IDP_REQ_CMD_MODIFY_PROFILE);
		sendData.setResp_type(IDP_PARAM_RESP_TYPE_XML);
		sendData.setResp_flow(IDP_PARAM_RESP_FLOW_RESPONSE);
		sendData.setKey(key);
		sendData.setKey_type(IDP_PARAM_KEY_TYPE_IM_SERVICE_NO);
		sendData.setUser_auth_key(user_auth_key);
		if (user_tn != null)
			sendData.setUser_tn(user_tn);
		if (is_user_tn_auth != null)
			sendData.setIs_user_tn_auth(is_user_tn_auth);
		if (is_user_tn_own != null)
			sendData.setIs_user_tn_own(is_user_tn_own);
		if (user_tn_type != null)
			sendData.setUser_tn_type(user_tn_type);
		if (user_tn_nation_cd != null)
			sendData.setUser_tn_nation_cd(user_tn_nation_cd);
		if (user_email != null)
			sendData.setUser_email(user_email);
		if (is_email_auth != null)
			sendData.setIs_email_auth(is_email_auth);
		if (user_mdn != null)
			sendData.setUser_mdn(user_mdn);
		if (user_mdn_auth_key != null)
			sendData.setUser_mdn_auth_key(user_mdn_auth_key);
		if (user_sex != null)
			sendData.setUser_sex(user_sex);
		if (user_calendar != null)
			sendData.setUser_calendar(user_calendar);
		if (user_zipcode != null)
			sendData.setUser_zipcode(user_zipcode);
		if (user_address != null)
			sendData.setUser_address(user_address);
		if (user_address2 != null)
			sendData.setUser_address2(user_address2);
		if (user_nation_code != null)
			sendData.setUser_nation_code(user_nation_code);
		if (user_status_code != null)
			sendData.setUser_status_code(user_status_code);
		if (udt_type_cd != null)
			sendData.setUdt_type_cd(udt_type_cd);
		sendData.setModify_req_date(IdpDateUtil.getCurrentDate());
		sendData.setModify_req_time(IdpDateUtil.getCurrentTime());
		sendData.setUser_type(user_type);
		sendData.setIs_biz_auth(is_biz_auth);

		return this.sendIDPHttps(sendData); // sendIDP(sendData);
	}

	/**
	 * 2.1.8 부가프로파일정보수정요청
	 */
	@Override
	public ImIDPReceiverM updateAdditionalInfo(Map<String, Object> param) throws Exception {
		String key = (String) param.get("key");
		String user_auth_key = (String) param.get("user_auth_key");
		String user_mdn = (String) param.get("user_mdn");
		String user_mdn_auth_key = (String) param.get("user_mdn_auth_key");
		String user_sex = (String) param.get("user_sex");
		String user_calendar = (String) param.get("user_calendar");
		String user_zipcode = (String) param.get("user_zipcode");
		String user_address = (String) param.get("user_address");
		String user_address2 = (String) param.get("user_address2");

		ImIDPSenderM sendData = new ImIDPSenderM();
		sendData.setUrl(ImIDPSender.IDP_REQ_URL_USER_INFO_MODIFY);
		sendData.setCmd(ImIDPSender.IDP_REQ_CMD_MODIFY_ADDITIONAL);
		sendData.setResp_type(IDP_PARAM_RESP_TYPE_XML);
		sendData.setResp_flow(IDP_PARAM_RESP_FLOW_RESPONSE);
		sendData.setKey(key);
		sendData.setKey_type(IDP_PARAM_KEY_TYPE_IM_SERVICE_NO);
		sendData.setUser_auth_key(user_auth_key);
		if (user_mdn != null)
			sendData.setUser_mdn(user_mdn);
		if (user_mdn_auth_key != null)
			sendData.setUser_mdn_auth_key(user_mdn_auth_key);
		if (user_sex != null)
			sendData.setUser_sex(user_sex);
		if (user_calendar != null)
			sendData.setUser_calendar(user_calendar);
		if (user_zipcode != null)
			sendData.setUser_zipcode(user_zipcode);
		if (user_address != null)
			sendData.setUser_address(user_address);
		if (user_address2 != null)
			sendData.setUser_address2(user_address2);
		sendData.setModify_req_date(IdpDateUtil.getCurrentDate());
		sendData.setModify_req_time(IdpDateUtil.getCurrentTime());

		return this.sendIDPHttps(sendData); // sendIDP(sendData);
	}

	/**
	 * 2.1.9 비밀번호변경요청
	 */
	@Override
	public ImIDPReceiverM modifyPwd(Map<String, Object> param) throws Exception {
		String key = (String) param.get("key");
		String user_auth_key = (String) param.get("user_auth_key");
		String user_passwd = (String) param.get("user_passwd");
		String user_passwd_type = (String) param.get("user_passwd_type");
		String user_passwd_modify_date = (String) param.get("user_passwd_modify_date");

		ImIDPSenderM sendData = new ImIDPSenderM();
		sendData.setUrl(ImIDPSender.IDP_REQ_URL_USER_INFO_MODIFY);
		sendData.setCmd(ImIDPSender.IDP_REQ_CMD_MODIFY_PWD);
		sendData.setResp_type(IDP_PARAM_RESP_TYPE_XML);
		sendData.setResp_flow(IDP_PARAM_RESP_FLOW_RESPONSE);
		sendData.setKey(key);
		sendData.setKey_type(IDP_PARAM_KEY_TYPE_IM_SERVICE_NO);
		sendData.setUser_auth_key(user_auth_key);
		if (user_passwd != null)
			sendData.setUser_passwd(user_passwd);
		if (user_passwd_type != null)
			sendData.setUser_passwd_type(user_passwd_type);
		if (user_passwd_modify_date != null)
			sendData.setUser_passwd_modify_date(user_passwd_modify_date);
		sendData.setModify_req_date(IdpDateUtil.getCurrentDate());
		sendData.setModify_req_time(IdpDateUtil.getCurrentTime());

		return this.sendIDPHttps(sendData); // sendIDP(sendData);
	}

	/**
	 * 2.1.10 비밀번호초기화및임시비번발급
	 */
	@Override
	public ImIDPReceiverM resetPwd(Map<String, Object> param) throws Exception {
		String key = (String) param.get("key");
		String user_tn = (String) param.get("user_tn");
		String user_tn_auth = (String) param.get("user_tn_auth");
		if ("".equals(user_tn_auth) || null == user_tn_auth)
			user_tn_auth = "N";
		String user_email = (String) param.get("user_email");
		String is_email_auth = (String) param.get("is_email_auth");
		if (null != user_email && null == is_email_auth)
			is_email_auth = "N";

		ImIDPSenderM sendData = new ImIDPSenderM();
		sendData.setUrl(ImIDPSender.IDP_REQ_URL_USER_INFO_MODIFY);
		sendData.setCmd(ImIDPSender.IDP_REQ_CMD_RESET_PWD);
		sendData.setResp_type(IDP_PARAM_RESP_TYPE_XML);
		sendData.setResp_flow(IDP_PARAM_RESP_FLOW_RESPONSE);
		sendData.setKey(key);
		sendData.setKey_type(IDP_PARAM_KEY_TYPE_IM_SERVICE_NO);
		if (!"".equals(user_tn) && user_tn != null)
			sendData.setUser_tn(user_tn);
		if (user_tn_auth != null) {
			sendData.setIs_user_tn_auth(user_tn_auth);
			if ("Y".equals(user_tn_auth)) {
				sendData.setUser_tn_nation_cd("82");
				sendData.setUser_tn_type("M");
			}

		}
		if (user_email != null)
			sendData.setUser_email(user_email);
		if (is_email_auth != null)
			sendData.setIs_email_auth(is_email_auth);
		sendData.setLang_code(IDP_PARAM_LANG_CODE);
		sendData.setModify_req_date(IdpDateUtil.getCurrentDate());
		sendData.setModify_req_time(IdpDateUtil.getCurrentTime());

		return this.sendIDPHttps(sendData); // sendIDP(sendData);
	}

	/**
	 * 2.1.11 ID 중복체크(가통합 ID Pool)
	 */
	@Override
	public ImIDPReceiverM dupUserIdCheck(String id) throws Exception {

		ImIDPSenderM sendData = new ImIDPSenderM();

		sendData.setUrl(ImIDPSender.IDP_REQ_URL_USER_INFO_SEARCH);
		sendData.setCmd(ImIDPSender.IDP_REQ_CMD_DUP_CHECK_ID);
		sendData.setResp_type(IDP_PARAM_RESP_TYPE_XML);
		sendData.setResp_flow(IDP_PARAM_RESP_FLOW_RESPONSE);
		sendData.setUser_id(id);
		sendData.setReq_date(IdpDateUtil.getCurrentDate());
		sendData.setReq_time(IdpDateUtil.getCurrentTime());

		// TODO : business logic
		return this.sendIDP(sendData);
	}

	/**
	 * 2.1.13 전화번호중복체크
	 */
	@Override
	public ImIDPReceiverM dupTelNoCheck(String user_tn) throws Exception {
		ImIDPSenderM sendData = new ImIDPSenderM();
		sendData.setUrl(ImIDPSender.IDP_REQ_URL_USER_INFO_SEARCH);
		sendData.setCmd(ImIDPSender.IDP_REQ_CMD_DUP_CHECK_TELNO);
		sendData.setResp_type(IDP_PARAM_RESP_TYPE_XML);
		sendData.setResp_flow(IDP_PARAM_RESP_FLOW_RESPONSE);
		sendData.setUser_tn(user_tn);
		sendData.setUser_tn_type(IDP_PARAM_USER_TN_TYPE_MOBILE);
		sendData.setUser_tn_nation_cd(IDP_PARAM_USER_TN_NATION_CD);
		sendData.setReq_date(IdpDateUtil.getCurrentDate());
		sendData.setReq_time(IdpDateUtil.getCurrentTime());

		return this.sendIDP(sendData);
	}

	/**
	 * 2.1.14 인증 E-mail 재발송요청
	 */
	@Override
	public ImIDPReceiverM resendConfirmEmail(String key, String user_email) throws Exception {
		ImIDPSenderM sendData = new ImIDPSenderM();
		sendData.setUrl(ImIDPSender.IDP_REQ_URL_USER_AUTH);
		sendData.setCmd(ImIDPSender.IDP_REQ_CMD_CONFIRM_EMAIL_RESEND);
		sendData.setResp_type(IDP_PARAM_RESP_TYPE_XML);
		sendData.setResp_flow(IDP_PARAM_RESP_FLOW_RESPONSE);
		sendData.setKey(key);
		sendData.setKey_type(IDP_PARAM_KEY_TYPE_IM_ID);
		sendData.setUser_email(user_email);
		sendData.setLang_code(IDP_PARAM_LANG_CODE);
		sendData.setReq_date(IdpDateUtil.getCurrentDate());
		sendData.setReq_time(IdpDateUtil.getCurrentTime());
		return this.sendIDP(sendData);
	}

	/**
	 * 2.1.15 인증 SMS 발송요청
	 */
	@Override
	public ImIDPReceiverM sendAuthSms(String key, String user_tn) throws Exception {
		ImIDPSenderM sendData = new ImIDPSenderM();

		sendData.setUrl(ImIDPSender.IDP_REQ_URL_USER_AUTH);
		sendData.setCmd(ImIDPSender.IDP_REQ_CMD_AUTH_SMS_SEND);
		sendData.setResp_type(IDP_PARAM_RESP_TYPE_XML);
		sendData.setResp_flow(IDP_PARAM_RESP_FLOW_RESPONSE);
		sendData.setKey(key);
		sendData.setKey_type(IDP_PARAM_KEY_TYPE_IM_SERVICE_NO);
		sendData.setUser_tn(user_tn);
		sendData.setLang_code(IDP_PARAM_LANG_CODE);
		sendData.setReq_date(IdpDateUtil.getCurrentDate());
		sendData.setReq_time(IdpDateUtil.getCurrentTime());
		return this.sendIDP(sendData);
	}

	/**
	 * 2.1.16 인증 E-mail 발송요청(인증 코드 발급)
	 */
	@Override
	public ImIDPReceiverM sendAuthEmail(String key, String user_email) throws Exception {
		ImIDPSenderM sendData = new ImIDPSenderM();

		sendData.setUrl(ImIDPSender.IDP_REQ_URL_USER_AUTH);
		sendData.setCmd(ImIDPSender.IDP_REQ_CMD_AUTH_EMAIL_SEND);
		sendData.setResp_type(IDP_PARAM_RESP_TYPE_XML);
		sendData.setResp_flow(IDP_PARAM_RESP_FLOW_RESPONSE);
		sendData.setKey(key);
		sendData.setKey_type(IDP_PARAM_KEY_TYPE_IM_ID);
		sendData.setUser_email(user_email);
		sendData.setLang_code(IDP_PARAM_LANG_CODE);
		sendData.setReq_date(IdpDateUtil.getCurrentDate());
		sendData.setReq_time(IdpDateUtil.getCurrentTime());
		return this.sendIDP(sendData);
	}

	/**
	 * 2.1.19 유효기간설정
	 */
	public ImIDPReceiverM setVaildPeriod(String key) throws Exception {
		ImIDPSenderM sendData = new ImIDPSenderM();
		sendData.setUrl(ImIDPSender.IDP_REQ_URL_ID_VALID);
		sendData.setCmd(ImIDPSender.IDP_REQ_CMD_SET_VAILD_PERIOD);
		sendData.setResp_type(IDP_PARAM_RESP_TYPE_XML);
		sendData.setResp_flow(IDP_PARAM_RESP_FLOW_RESPONSE);
		sendData.setKey(key);
		sendData.setKey_type(IDP_PARAM_KEY_TYPE_IM_SERVICE_NO);
		sendData.setReq_date(IdpDateUtil.getCurrentDate());
		sendData.setReq_time(IdpDateUtil.getCurrentTime());
		return this.sendIDP(sendData);
	}

	/**
	 * 2.1.20 유효기간설정해제
	 */
	@Override
	public ImIDPReceiverM resetVaildPeriod(String key) throws Exception {
		ImIDPSenderM sendData = new ImIDPSenderM();

		sendData.setUrl(ImIDPSender.IDP_REQ_URL_ID_VALID);
		sendData.setCmd(ImIDPSender.IDP_REQ_CMD_RESET_VAILD_PERIOD);
		sendData.setResp_type(IDP_PARAM_RESP_TYPE_XML);
		sendData.setResp_flow(IDP_PARAM_RESP_FLOW_RESPONSE);
		sendData.setKey(key);
		sendData.setKey_type(IDP_PARAM_KEY_TYPE_IM_SERVICE_NO);
		sendData.setReq_date(IdpDateUtil.getCurrentDate());
		sendData.setReq_time(IdpDateUtil.getCurrentTime());

		return this.sendIDP(sendData);
	}

	/**
	 * 2.1.21 탈퇴가능 확인 요청
	 */
	@Override
	public ImIDPReceiverM checkDelUser(String key) throws Exception {
		ImIDPSenderM sendData = new ImIDPSenderM();

		sendData.setUrl(ImIDPSender.IDP_REQ_URL_SECEDE);
		sendData.setCmd(ImIDPSender.IDP_REQ_CMD_CHECK_DEL_USER);
		sendData.setResp_type(IDP_PARAM_RESP_TYPE_XML);
		sendData.setResp_flow(IDP_PARAM_RESP_FLOW_RESPONSE);
		sendData.setKey(key);
		sendData.setKey_type(IDP_PARAM_KEY_TYPE_IM_SERVICE_NO);
		sendData.setReq_date(IdpDateUtil.getCurrentDate());
		sendData.setReq_time(IdpDateUtil.getCurrentTime());

		return this.sendIDP(sendData);
	}

	/**
	 * 2.1.22 ID 일괄전환 가능 확인
	 */
	public ImIDPReceiverM checkTransUserId(String id, String ipin_ci) throws Exception {
		ImIDPSenderM sendData = new ImIDPSenderM();

		sendData.setUrl(ImIDPSender.IDP_REQ_URL_USER_INFO_SEARCH);
		sendData.setCmd(ImIDPSender.IDP_REQ_CMD_CHECK_TRANS_USERID);
		sendData.setResp_type(IDP_PARAM_RESP_TYPE_XML);
		sendData.setResp_flow(IDP_PARAM_RESP_FLOW_RESPONSE);
		sendData.setUser_id(id);
		sendData.setIpin_ci(ipin_ci);
		sendData.setReq_date(IdpDateUtil.getCurrentDate());
		sendData.setReq_time(IdpDateUtil.getCurrentTime());

		return this.sendIDP(sendData);
	}

	/**
	 * 2.1.23 로그인 상태 정보 변경 요청
	 */
	@Override
	public ImIDPReceiverM setLoginStatus(String key, String login_status_code) throws Exception {
		ImIDPSenderM sendData = new ImIDPSenderM();
		sendData.setUrl(ImIDPSender.IDP_REQ_URL_USER_INFO_MODIFY);
		sendData.setCmd(ImIDPSender.IDP_REQ_CMD_SET_LOGIN_STATUS);
		sendData.setResp_type(IDP_PARAM_RESP_TYPE_XML);
		sendData.setResp_flow(IDP_PARAM_RESP_FLOW_RESPONSE);
		// sendData.setLogin_limit_sst_code(Constants.SSO_SST_CD_TSTORE_WEB);
		sendData.setLogin_status_code(login_status_code);
		sendData.setKey(key);
		sendData.setKey_type(IDP_PARAM_KEY_TYPE_IM_ID);
		sendData.setModify_req_date(IdpDateUtil.getCurrentDate());
		sendData.setModify_req_time(IdpDateUtil.getCurrentTime());
		return this.sendIDP(sendData);
	}

	/**
	 * 2.1.24 가입 인증 SMS 발송 요청
	 */
	@Override
	public ImIDPReceiverM authCreateSms(String user_tn, String user_tn_nation_cd) throws Exception {
		ImIDPSenderM sendData = new ImIDPSenderM();
		sendData.setUrl(ImIDPSender.IDP_REQ_URL_USER_AUTH);
		sendData.setCmd(ImIDPSender.IDP_REQ_CMD_AUTH_CREATE_SMS);
		sendData.setResp_type(IDP_PARAM_RESP_TYPE_XML);
		sendData.setResp_flow(IDP_PARAM_RESP_FLOW_RESPONSE);
		sendData.setUser_tn(user_tn);
		sendData.setUser_tn_nation_cd(user_tn_nation_cd);
		sendData.setLang_code(IDP_PARAM_LANG_CODE);
		sendData.setReq_date(IdpDateUtil.getCurrentDate());
		sendData.setReq_time(IdpDateUtil.getCurrentTime());
		return this.sendIDP(sendData);
	}

	/**
	 * 2.1.26 실명 변경 요청
	 */
	@Override
	public ImIDPReceiverM updateUserName(String key, String user_name, String user_birthday, String sn_auth_key,
			String user_auth_key, String rname_auth_mns_code, String ci, String di, HashMap map) throws Exception {

		ImIDPSenderM sendData = new ImIDPSenderM();
		sendData.setUrl(ImIDPSender.IDP_REQ_URL_USER_INFO_MODIFY);
		sendData.setCmd(ImIDPSender.IDP_REQ_CMD_UPDATE_USER_NAME);
		sendData.setResp_type(IDP_PARAM_RESP_TYPE_XML);
		sendData.setResp_flow(IDP_PARAM_RESP_FLOW_RESPONSE);
		sendData.setUser_auth_key(user_auth_key);
		sendData.setUser_name(user_name);
		sendData.setUser_birthday(user_birthday);
		sendData.setUser_calendar("1");
		sendData.setIs_rname_auth("Y");
		// sendData.setRname_auth_sst_code(Constants.SSO_SST_CD_TSTORE_WEB);
		sendData.setSn_auth_key(sn_auth_key);
		sendData.setKey(key);
		sendData.setKey_type(IDP_PARAM_KEY_TYPE_IM_SERVICE_NO);
		sendData.setModify_req_date(IdpDateUtil.getCurrentDate());
		sendData.setModify_req_time(IdpDateUtil.getCurrentTime());
		sendData.setRname_auth_mns_code((rname_auth_mns_code == null || rname_auth_mns_code.equals("") ? "2" : rname_auth_mns_code));
		sendData.setUser_ci(ci);
		sendData.setUser_di(di);

		sendData.setUser_sex("N");
		if (null != map && null != map.get("user_sex") && !"".equals(map.get("user_sex").toString())) {
			sendData.setUser_sex(map.get("user_sex").toString());
		}

		// R=회원 개명 , E=CI 기보유 , C=
		sendData.setRname_auth_type_cd("E");
		if (null != map && null != map.get("rname_auth_type_cd")
				&& !"".equals(map.get("rname_auth_type_cd").toString())) {
			sendData.setRname_auth_type_cd(map.get("rname_auth_type_cd").toString());
		}
		sendData.setRname_auth_date(IdpDateUtil.getCurrentDate() + "" + IdpDateUtil.getCurrentTime());

		return this.sendIDP(sendData);
	}

	/**
	 * 2.1.27 법정대리인 동의정보 변경 요청
	 */
	@Override
	public ImIDPReceiverM updateGuardian(String key, String parent_type, String parent_rname_auth_key,
			String parent_name, String parent_email, String user_auth_key, String parent_birthday) throws Exception {
		ImIDPSenderM sendData = new ImIDPSenderM();
		sendData.setUrl(ImIDPSender.IDP_REQ_URL_USER_INFO_MODIFY);
		sendData.setCmd(ImIDPSender.IDP_REQ_CMD_UPDATE_GUARDIAN);
		sendData.setResp_type(IDP_PARAM_RESP_TYPE_XML);
		sendData.setResp_flow(IDP_PARAM_RESP_FLOW_RESPONSE);
		sendData.setUser_auth_key(user_auth_key);
		sendData.setKey(key);
		sendData.setKey_type(IDP_PARAM_KEY_TYPE_IM_SERVICE_NO);
		sendData.setParent_type(parent_type);
		sendData.setParent_rname_auth_type("5"); // IPIN
		sendData.setParent_rname_auth_key(parent_rname_auth_key);
		sendData.setParent_name(parent_name);
		sendData.setParent_birthday(parent_birthday);// 주민번호 앞자리
		sendData.setParent_email(parent_email);
		sendData.setParent_approve_date(IdpDateUtil.getCurrentDate());
		sendData.setIs_parent_approve("Y");
		// sendData.setParent_approve_sst_code(Constants.SSO_SST_CD_TSTORE_WEB);
		sendData.setModify_req_date(IdpDateUtil.getCurrentDate());
		sendData.setModify_req_time(IdpDateUtil.getCurrentTime());
		return this.sendIDP(sendData);
	}

	/* 2.1.27 개별 프로파일 조회 - One ID 2.0 신규 */
	@Override
	public ImIDPReceiverM getSerivceInfo(Map<String, Object> param) throws Exception {
		String key = (String) param.get("key");
		String emailYn = (String) param.get("emailYn");
		if (null == emailYn || "".equals(emailYn))
			emailYn = "N";
		String marketingYn = (String) param.get("marketingYn");
		if (null == marketingYn || "".equals(marketingYn))
			marketingYn = "N";
		/*
		 * //참조용 코드 Map<String, Object> param String user_tn = (String)param.get("user_tn");
		 * 
		 * String user_tn_auth = (String)param.get("user_tn_auth"); if("".equals(user_tn_auth) || null ==
		 * user_tn_auth)user_tn_auth = "N";
		 */
		ImIDPSenderM sendData = new ImIDPSenderM();
		sendData.setUrl(ImIDPSender.IDP_REQ_URL_USER_INFO_SEARCH);
		sendData.setCmd(ImIDPSender.IDP_REQ_CMD_GET_SERIVCE_INFO);
		sendData.setResp_type(IDP_PARAM_RESP_TYPE_XML);
		sendData.setResp_flow(IDP_PARAM_RESP_FLOW_RESPONSE);
		sendData.setKey(key);
		sendData.setKey_type(IDP_PARAM_KEY_TYPE_IM_SERVICE_NO);
		sendData.setReq_date(IdpDateUtil.getCurrentDate());
		sendData.setReq_time(IdpDateUtil.getCurrentTime());
		sendData.setEmailYn(emailYn);
		sendData.setMarketingYn(marketingYn);

		// resp_url필드는 resp_flow=redt”(redirect 방식)일때만 url지정
		return this.sendIDPHttps(sendData); // sendIDP(sendData);
	}

	/**
	 * 2.2.1 ID 중복체크
	 */
	@Override
	public ImIDPReceiverM checkDupId(String id) throws Exception {
		ImIDPSenderM sendData = new ImIDPSenderM();

		sendData.setUrl(ImIDPSender.IDP_REQ_URL_JOIN);
		sendData.setCmd(ImIDPSender.IDP_REQ_CMD_DUP_ID_CHECK);
		sendData.setResp_type(IDP_PARAM_RESP_TYPE_XML);
		sendData.setResp_flow(IDP_PARAM_RESP_FLOW_RESPONSE);
		sendData.setUser_id(id);

		return this.sendIDP(sendData);
	}

	/**
	 * 2.2.5 통합 ID회원로그인
	 */
	@Override
	public ImIDPReceiverM authForId(String key, String pwd) throws Exception {
		ImIDPSenderM sendData = new ImIDPSenderM();

		sendData.setUrl(ImIDPSender.IDP_REQ_URL_USER_AUTH);
		sendData.setCmd(ImIDPSender.IDP_REQ_CMD_AUTH_FOR_ID);
		sendData.setResp_type(IDP_PARAM_RESP_TYPE_XML);
		sendData.setResp_flow(IDP_PARAM_RESP_FLOW_RESPONSE);
		sendData.setKey(key);
		sendData.setKey_type(IDP_PARAM_KEY_TYPE_IM_ID);
		sendData.setUser_passwd(pwd);

		return this.sendIDP(sendData);
	}

	/**
	 * 2.2.6 통합 ID 서비스가입리스트조회
	 */
	@Override
	public ImIDPReceiverM findJoinServiceList(String user_id) throws Exception {
		ImIDPSenderM sendData = new ImIDPSenderM();

		sendData.setUrl(ImIDPSender.IDP_REQ_URL_USER_INFO_SEARCH);
		sendData.setCmd(ImIDPSender.IDP_REQ_CMD_JOIN_SERVICE_LIST);
		sendData.setResp_type(IDP_PARAM_RESP_TYPE_XML);
		sendData.setResp_flow(IDP_PARAM_RESP_FLOW_RESPONSE);
		sendData.setKey(user_id);
		sendData.setKey_type(IDP_PARAM_KEY_TYPE_IM_ID);
		return this.sendIDP(sendData);
	}

	/**
	 * 2.2.7 휴대폰 인증코드 발송
	 */
	@Override
	public ImIDPReceiverM sendMobileAuthCode(String user_mdn, String user_mdn_type) throws Exception {
		ImIDPSenderM sendData = new ImIDPSenderM();

		sendData.setUrl(ImIDPSender.IDP_REQ_URL_MOBILE_AUTH);
		sendData.setCmd(ImIDPSender.IDP_REQ_CMD_MOBILE_AUTHCODE_SEND);
		sendData.setResp_type(IDP_PARAM_RESP_TYPE_XML);
		sendData.setResp_flow(IDP_PARAM_RESP_FLOW_RESPONSE);
		sendData.setUser_mdn(user_mdn);
		sendData.setUser_mdn_type(user_mdn_type);

		return this.sendIDP(sendData);
	}

	@Override
	public ImIDPReceiverM sendMobileAuthCode(String user_mdn, String user_mdn_type, String user_social_number)
			throws Exception {
		ImIDPSenderM sendData = new ImIDPSenderM();

		sendData.setUrl(ImIDPSender.IDP_REQ_URL_MOBILE_AUTH);
		sendData.setCmd(ImIDPSender.IDP_REQ_CMD_PARENT_MOBILE_AUTHCODE_SEND);
		sendData.setResp_type(IDP_PARAM_RESP_TYPE_XML);
		sendData.setResp_flow(IDP_PARAM_RESP_FLOW_RESPONSE);
		sendData.setUser_mdn(user_mdn);
		sendData.setUser_mdn_type(user_mdn_type);
		sendData.setUser_social_number(user_social_number);

		return this.sendIDP(sendData);
	}

	/**
	 * 2.2.8 휴대폰 인증
	 */
	@Override
	public ImIDPReceiverM mobileAuth(Map<String, Object> param) throws Exception {
		String user_mdn = (String) param.get("user_mdn");
		String user_code = (String) param.get("user_code");
		String mobile_sign = (String) param.get("mobile_sign");
		String sign_data = (String) param.get("sign_data");
		String svc_mng_num = (String) param.get("svc_mng_num");
		String model_id = (String) param.get("model_id");

		ImIDPSenderM sendData = new ImIDPSenderM();
		sendData.setUrl(ImIDPSender.IDP_REQ_URL_MOBILE_AUTH);
		sendData.setCmd(ImIDPSender.IDP_REQ_CMD_MOBILE_AUTH);
		sendData.setResp_type(IDP_PARAM_RESP_TYPE_XML);
		sendData.setResp_flow(IDP_PARAM_RESP_FLOW_RESPONSE);
		sendData.setUser_mdn(user_mdn);
		sendData.setUser_code(user_code);
		sendData.setMobile_sign(mobile_sign);
		sendData.setSign_data(sign_data);
		if (null != svc_mng_num)
			sendData.setSvc_mng_num(svc_mng_num);
		if (null != model_id)
			sendData.setModel_id(model_id);

		return this.sendIDP(sendData);
	}

	/**
	 * 2.2.9 ID 가입여부 체크
	 */
	@Override
	public ImIDPReceiverM checkIdStatusIdpIm(String id) throws Exception {
		ImIDPSenderM sendData = new ImIDPSenderM();

		sendData.setUrl(ImIDPSender.IDP_REQ_URL_JOIN);
		sendData.setCmd(ImIDPSender.IDP_REQ_CMD_ID_STATUS_IDP_IM);
		sendData.setResp_type(IDP_PARAM_RESP_TYPE_XML);
		sendData.setResp_flow(IDP_PARAM_RESP_FLOW_RESPONSE);
		sendData.setUser_id(id);

		return this.sendIDP(sendData);
	}

	/**
	 * 2.2.10 통합 ID 전환시동일회원확인(SP용)
	 */
	@Override
	public ImIDPReceiverM checkIdPwdAuth(String id, String pwd) throws Exception {
		ImIDPSenderM sendData = new ImIDPSenderM();

		sendData.setUrl(ImIDPSender.IDP_REQ_URL_IMID_CHECK);
		sendData.setCmd(ImIDPSender.IDP_REQ_CMD_CHECK_IDPWD_AUTH);
		sendData.setResp_type(IDP_PARAM_RESP_TYPE_XML);
		sendData.setResp_flow(IDP_PARAM_RESP_FLOW_RESPONSE);
		sendData.setUser_id(id);
		sendData.setUser_passwd(pwd);
		return this.sendIDP(sendData);
	}

	/**
	 * 2.2.12 IM통합회원 ID 찾기
	 */
	@Override
	public ImIDPReceiverM findUserIdByMdn(Map<String, Object> param) throws Exception {
		String user_mdn = (String) param.get("user_mdn");
		String user_code = (String) param.get("user_code");
		String mobile_sign = (String) param.get("mobile_sign");
		String sign_data = (String) param.get("sign_data");
		String svc_mng_num = (String) param.get("svc_mng_num");
		String model_id = (String) param.get("model_id");

		ImIDPSenderM sendData = new ImIDPSenderM();
		sendData.setUrl(ImIDPSender.IDP_REQ_URL_USER_INFO_SEARCH);
		sendData.setCmd(ImIDPSender.IDP_REQ_CMD_FIND_USERID_BY_MDN);
		sendData.setResp_type(IDP_PARAM_RESP_TYPE_XML);
		sendData.setResp_flow(IDP_PARAM_RESP_FLOW_RESPONSE);
		sendData.setUser_mdn(user_mdn);
		sendData.setUser_code(user_code);
		sendData.setMobile_sign(mobile_sign);
		sendData.setSign_data(sign_data);
		if (null != svc_mng_num)
			sendData.setSvc_mng_num(svc_mng_num);
		if (null != model_id)
			sendData.setModel_id(model_id);

		return this.sendIDP(sendData);
	}

	/**
	 * 2.2.14 통합SSO ID회원 로그인
	 */
	@Override
	public ImIDPReceiverM authForSvcNo(String im_int_svc_no) throws Exception {
		ImIDPSenderM sendData = new ImIDPSenderM();

		sendData.setUrl(ImIDPSender.IDP_REQ_URL_USER_AUTH);
		sendData.setCmd(ImIDPSender.IDP_REQ_CMD_AUTH_FOR_SSO_ID);
		sendData.setResp_type(IDP_PARAM_RESP_TYPE_XML);
		sendData.setResp_flow(IDP_PARAM_RESP_FLOW_RESPONSE);
		sendData.setIm_int_svc_no(im_int_svc_no);

		return this.sendIDPHttps(sendData); // sendIDP(sendData);
	}

	/**
	 * 2.1.16 공통프로파일조회요청 (For Server)
	 */
	@Override
	public ImIDPReceiverM userInfoIdpSearchServer(String imServiceNo) throws Exception {
		ImIDPSenderM sendData = new ImIDPSenderM();
		sendData.setUrl(ImIDPSender.IDP_REQ_URL_USER_INFO_SEARCH);
		sendData.setCmd(ImIDPSender.IDP_REQ_CMD_FIND_COMMON_IDP_PROFILE_FOR_SERVER);
		sendData.setResp_type(IDP_PARAM_RESP_TYPE_XML);
		sendData.setResp_flow(IDP_PARAM_RESP_FLOW_RESPONSE);
		sendData.setKey(imServiceNo);
		sendData.setKey_type(IDP_PARAM_KEY_TYPE_IM_SERVICE_NO);
		sendData.setReq_date(IdpDateUtil.getCurrentDate());
		sendData.setReq_time(IdpDateUtil.getCurrentTime());

		return this.sendIDPHttps(sendData); // sendIDP(sendData);
	}

	@Override
	public String makeSnAuthKey(String mbrNm, String user_birthday) throws Exception {
		return this.imidpSender.makeSnAuthKey(mbrNm, user_birthday);
	}

	// ---------------------------------------------------------------------------------------------------------

	/**
	 * IDP 연동 결과를 XML로 전달받아 객체를 생성 반환한다.
	 * 
	 * @param sendData
	 * @return
	 * @throws Exception
	 */
	public ImIDPReceiverM sendIDP(ImIDPSenderM sendData) throws Exception {

		Hashtable param = this.imidpSender.makeSendParam(sendData);

		IDPTxReq req = new IDPTxReq();
		req.setHttpMethod("");
		req.setHttpProtocal("HTTP");
		req.setReqUrl(sendData.getCmd());

		/**
		 * Response
		 */
		IDPTxRes res = this.idpSCI.send(req);
		String responseMsg = res.getResXml();
		logger.info("HTTPS IDP 연동 결과 [XML] ::::   " + responseMsg);

		ImIDPReceiverM receiveData = this.resultIDPFromXml(responseMsg);

		return receiveData;
	}

	/**
	 * HTTPS 프로토콜로 IDP 연동 결과를 XML로 전달받아 객체를 생성 반환한다.
	 * 
	 * @param sendData
	 * @return
	 * @throws Exception
	 */
	public ImIDPReceiverM sendIDPHttps(ImIDPSenderM sendData) throws Exception {

		// AS IS 로직 삭제
		// StopWatch stopWatch = new StopWatch();

		// stopWatch.start();

		Hashtable param = this.imidpSender.makeSendParam(sendData);

		IDPTxReq req = new IDPTxReq();
		req.setHttpMethod("");
		req.setHttpProtocal("HTTP");
		req.setReqUrl(sendData.getCmd());

		/**
		 * Response
		 */
		// AS IS 로직 삭제
		// String responseMsg = this.sendHttps(this.idpSender.idpReqUrlHttps(sendData.getUrl()), param);

		IDPTxRes res = this.idpSCI.send(req);
		String responseMsg = res.getResXml();

		logger.info("HTTPS IDP 연동 결과 [XML] ::::   " + responseMsg);

		ImIDPReceiverM receiveData = this.resultIDPFromXml(responseMsg);

		return receiveData;
	}

	/**
	 * HTTP URL에 해당하는 내용을 조회한다.
	 * 
	 * @param url
	 * @param param
	 * @return
	 * @throws Exception
	 */
	public String sendHttp(String url, Hashtable param) throws Exception {
		if (url == null || url.trim().length() == 0)
			throw new Exception("URL is null.");

		logger.info("idp request url : " + url);

		// AS IS 로직 삭제
		// HTTPUtil hc = new HTTPUtil(url, Constants.HTTP_METHOD_POST, param);
		// hc.setEncoding("UTF-8");

		return null; // TODO : hc.getContent(30000);
	}

	/**
	 * HTTPS URL에 해당하는 내용을 조회한다. : TODO
	 * 
	 * @param url
	 * @param param
	 * @return
	 * @throws Exception
	 */
	public String sendHttps(String url, Hashtable param) throws Exception {
		if (url == null || url.trim().length() == 0)
			throw new Exception("URL is null.");

		logger.info("idp request url : " + url);

		// AS IS 로직 삭제
		// HTTPUtil hc = new HTTPUtil(url, Constants.HTTP_METHOD_POST, param);
		// hc.setEncoding("UTF-8");

		return null; // TODO : hc.getContent(30000);
	}

	/**
	 * IDP 연동 결과를 Parsing 하여 recevie 객체에 담는다.
	 * 
	 * @param data
	 * @return
	 * @throws Exception
	 */
	private ImIDPReceiverM resultIDPFromXml(String data) throws Exception {

		XStream xs = new XStream(new DomDriver());
		this.receivData = new ImIDPReceiverM();

		xs.alias("idpResponse", ImIDPReceiverM.class);

		xs.fromXML(data, this.receivData);

		this.receivData = this.idpReceiver.nullValue(this.receivData);

		// String idList = receivData.getResponseBody().getId_list();

		// if (!"".equals(idList) && idList != null) {
		// receivData.getResponseBody().setIdList(idpReceiver.tokenize(idList, "|"));
		// }

		return this.receivData;
	}

}
