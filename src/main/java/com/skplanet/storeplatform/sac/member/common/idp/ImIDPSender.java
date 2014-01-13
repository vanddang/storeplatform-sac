///*
// * COPYRIGHT(c) SK telecom 2009
// * This software is the proprietary information of SK telecom.
// *
// * Revision History
// * Author       Date            Description
// * --------     ----------      ------------------
// * ?            ?               ?
// * nefer        2009.12.08      move to omp_common / override func - makeSpAuthKey - support 'domain' parameters
// *
// */
//package com.skplanet.storeplatform.sac.member.common.idp;
//
//import java.util.Enumeration;
//import java.util.Hashtable;
//
//import javax.crypto.Mac;
//import javax.crypto.spec.SecretKeySpec;
//
//import org.apache.log4j.Logger;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.stereotype.Component;
//
//import com.skplanet.storeplatform.sac.member.common.idp.vo.ImIDPSenderM;
//
//@Component
//public class ImIDPSender {
//
//	/** IDP로 부터 발급된 Service ID */
//	@Value("#{propertiesForSac['idp.sp_id']}")
//	public String IDP_REQ_OMP_SERVICE_ID;
//	/** IDP 요청 도메인 (HTTP) */
//	@Value("#{propertiesForSac['idp.im.request.url']}")
//	public String IDP_REQUEST_URL;
//	/** IDP에 등록한 OMP Association Key */
//	@Value("#{propertiesForSac['idp.sp_key']}")
//	public String IDP_REQ_OMP_ASSOC_KEY;
//	/** OMP 서비스 도메인 */
//	@Value("#{propertiesForSac['idp.service_domain']}")
//	public String OMP_SERVICE_DOMAIN;
//
//	private static Logger logger = Logger.getLogger(ImIDPSender.class);
//
//	/**
//	 * idp에 전달할 파라미터를 설정한다.
//	 * 
//	 * @param sendData
//	 * @return
//	 * @throws Exception
//	 */
//	@SuppressWarnings({ "unchecked", "rawtypes" })
//	protected Hashtable<String, String> makeSendParam(ImIDPSenderM sendData) throws Exception {
//
//		Hashtable<String, String> param = new Hashtable<String, String>();
//
//		String cmd = sendData.getCmd();
//		String spId = this.IDP_REQ_OMP_SERVICE_ID;
//		String operation_mode = "";
//		if (ImIDPConstants.IDP_CHECK_SERVER_DEV.equals(spId) && this.IDP_REQUEST_URL.indexOf("innoace.com:8002") > -1) {
//			operation_mode = ImIDPConstants.IDP_PARAM_OPERATION_MODE_DEV;
//		} else if (ImIDPConstants.IDP_CHECK_SERVER_DEV.equals(spId)
//				&& this.IDP_REQUEST_URL.indexOf("innoace.com:8003") > -1) {
//			operation_mode = ImIDPConstants.IDP_PARAM_OPERATION_MODE_STAG;
//		} else if (ImIDPConstants.IDP_CHECK_SERVER_STAG.equals(spId) && this.IDP_REQUEST_URL.indexOf("nate") > -1) {
//			operation_mode = ImIDPConstants.IDP_PARAM_OPERATION_MODE_TEST;
//		} else if (ImIDPConstants.IDP_CHECK_SERVER_REAL.equals(spId)) {
//			operation_mode = ImIDPConstants.IDP_PARAM_OPERATION_MODE_REAL;
//		}
//		// 개발용
//		if (ImIDPConstants.IDP_CHECK_SERVER_DEV.equals(spId) && this.IDP_REQUEST_URL.indexOf("211.63.6.59") > -1) {
//			operation_mode = ImIDPConstants.IDP_PARAM_OPERATION_MODE_TEST;
//		} else if (ImIDPConstants.IDP_CHECK_SERVER_STAG.equals(spId)
//				&& this.IDP_REQUEST_URL.indexOf("211.63.6.59") > -1) {
//			operation_mode = ImIDPConstants.IDP_PARAM_OPERATION_MODE_TEST;
//		} else if (ImIDPConstants.IDP_CHECK_SERVER_REAL.equals(spId)) {
//			operation_mode = ImIDPConstants.IDP_PARAM_OPERATION_MODE_REAL;
//		}
//
//		String resp_type = sendData.getResp_type();
//		String resp_flow = sendData.getResp_flow();
//		String resp_url = sendData.getResp_url();
//		String key_type = sendData.getKey_type();
//		String key = sendData.getKey();
//		String user_auth_key = sendData.getUser_auth_key();
//		String user_id = sendData.getUser_id();
//		String old_id = sendData.getOld_id();
//		String user_passwd = sendData.getUser_passwd();
//		String user_passwd_type = sendData.getUser_passwd_type();
//		String user_passwd_modify_date = sendData.getUser_passwd_modify_date();
//		String auth_type = sendData.getAuth_type();
//		String user_tn = sendData.getUser_tn();
//		String is_user_tn_own = sendData.getIs_user_tn_own();
//		String is_user_tn_auth = sendData.getIs_user_tn_auth();
//		String user_tn_nation_cd = sendData.getUser_tn_nation_cd();
//		String user_tn_type = sendData.getUser_tn_type();
//		String user_email = sendData.getUser_email();
//		String is_email_auth = sendData.getIs_email_auth();
//		String user_type = sendData.getUser_type();
//		String user_name = sendData.getUser_name();
//		String is_rname_auth = sendData.getIs_rname_auth();
//		String user_mdn = sendData.getUser_mdn();
//		String user_mdn_auth_key = sendData.getUser_mdn_auth_key();
//		String user_sex = sendData.getUser_sex();
//		String user_birthday = sendData.getUser_birthday();
//		String user_calendar = sendData.getUser_calendar();
//		String user_zipcode = sendData.getUser_zipcode();
//		String user_address = sendData.getUser_address();
//		String user_address2 = sendData.getUser_address2();
//		String user_nation_code = sendData.getUser_nation_code();
//		String user_nation_name = sendData.getUser_nation_name();
//		String lang_code = sendData.getLang_code();
//		String is_im_changed = sendData.getIs_im_changed();
//		String trans_sst_list = sendData.getTans_sst_list();
//		String user_status_code = sendData.getUser_status_code();
//		String parent_type = sendData.getParent_type();
//		String parent_rname_auth_type = sendData.getParent_rname_auth_type();
//		String parent_rname_auth_key = sendData.getParent_rname_auth_key();
//		String parent_name = sendData.getParent_name();
//		String parent_mdn = sendData.getParent_mdn();
//		String parent_email = sendData.getParent_email();
//		String parent_approve_date = sendData.getParent_approve_date();
//		String is_parent_approve = sendData.getIs_parent_approve();
//		String parent_approve_sst_code = sendData.getParent_approve_sst_code();
//		String consent_tac = sendData.getConsent_tac();
//		String join_path_code = sendData.getJoin_path_code();
//		String join_date = sendData.getJoin_date();
//		String join_time = sendData.getJoin_time();
//		String modify_req_date = sendData.getModify_req_date();
//		String modify_req_time = sendData.getModify_req_time();
//		String term_reason_cd = sendData.getTerm_reason_cd();
//		String req_date = sendData.getReq_date();
//		String req_time = sendData.getReq_time();
//		String ipin_ci = sendData.getIpin_ci();
//		String login_status_code = sendData.getLogin_status_code();
//		String login_limit_sst_code = sendData.getLogin_limit_sst_code();
//		String service_name = sendData.getService_name();
//		String im_int_svc_no = sendData.getIm_int_svc_no();
//		String svc_mng_num = sendData.getSvc_mng_num();
//		String model_id = sendData.getModel_id();
//		String user_social_number = sendData.getUser_social_number();
//		String user_mdn_type = sendData.getUser_mdn_type();
//		String user_code = sendData.getUser_code();
//		String mobile_sign = sendData.getMobile_sign();
//		String sign_data = sendData.getSign_data();
//		String spAuthKey = this.makeSpAuthKey();
//		String snAuthKey = sendData.getSn_auth_key();
//		String rname_auth_mns_code = sendData.getRname_auth_mns_code();
//		String rname_auth_sst_code = sendData.getRname_auth_sst_code();
//		String is_biz_auth = sendData.getIs_biz_auth();
//		String user_ci = sendData.getUser_ci();
//		String user_di = sendData.getUser_di();
//		String rname_auth_date = sendData.getRname_auth_date();
//		String udt_type_cd = sendData.getUdt_type_cd();
//		String parent_birthday = sendData.getParent_birthday();
//		String emailYn = sendData.getEmailYn();
//		String marketingYn = sendData.getMarketingYn();
//		String rname_auth_type_cd = sendData.getRname_auth_type_cd();
//		String mdn = sendData.getMdn();
//
//		logger.debug("IDP SEND DATA -----------------------------------");
//		logger.debug("cmd                     =" + cmd);
//		logger.debug("spId                    =" + spId);
//		logger.debug("sp_auth_key             =" + spAuthKey);
//		logger.debug("resp_type               =" + resp_type);
//		logger.debug("resp_flow               =" + resp_flow);
//		logger.debug("resp_url                =" + resp_url);
//		logger.debug("key_type                =" + key_type);
//		logger.debug("key                     =" + key);
//		logger.debug("user_auth_key           =" + user_auth_key);
//		logger.debug("user_id                 =" + user_id);
//		logger.debug("old_id                  =" + old_id);
//		logger.debug("user_passwd             =" + user_passwd);
//		logger.debug("user_passwd_type        =" + user_passwd_type);
//		logger.debug("auth_type               =" + auth_type);
//		logger.debug("user_tn                 =" + user_tn);
//		logger.debug("is_user_tn_own          =" + is_user_tn_own);
//		logger.debug("is_user_tn_auth         =" + is_user_tn_auth);
//		logger.debug("user_tn_nation_cd       =" + user_tn_nation_cd);
//		logger.debug("user_tn_type            =" + user_tn_type);
//		logger.debug("user_email              =" + user_email);
//		logger.debug("is_email_auth           =" + is_email_auth);
//		logger.debug("user_type               =" + user_type);
//		logger.debug("user_name               =" + user_name);
//		logger.debug("is_rname_auth           =" + is_rname_auth);
//		logger.debug("user_mdn                =" + user_mdn);
//		logger.debug("user_mdn_auth_key       =" + user_mdn_auth_key);
//		logger.debug("user_sex                =" + user_sex);
//		logger.debug("user_birthday           =" + user_birthday);
//		logger.debug("user_calendar           =" + user_calendar);
//		logger.debug("user_zipcode            =" + user_zipcode);
//		logger.debug("user_address            =" + user_address);
//		logger.debug("user_address2           =" + user_address2);
//		logger.debug("user_nation_code        =" + user_nation_code);
//		logger.debug("user_nation_name        =" + user_nation_name);
//		logger.debug("lang_code               =" + lang_code);
//		logger.debug("is_im_changed           =" + is_im_changed);
//		logger.debug("trans_sst_list          =" + trans_sst_list);
//		logger.debug("user_status_code        =" + user_status_code);
//		logger.debug("parent_type             =" + parent_type);
//		logger.debug("parent_rname_auth_type  =" + parent_rname_auth_type);
//		logger.debug("parent_rname_auth_key   =" + parent_rname_auth_key);
//		logger.debug("parent_name             =" + parent_name);
//		logger.debug("parent_birthday             =" + parent_birthday);
//		logger.debug("parent_mdn              =" + parent_mdn);
//		logger.debug("parent_email            =" + parent_email);
//		logger.debug("parent_approve_date     =" + parent_approve_date);
//		logger.debug("is_parent_approve       =" + is_parent_approve);
//		logger.debug("parent_approve_sst_code =" + parent_approve_sst_code);
//		logger.debug("consent_tac             =" + consent_tac);
//		logger.debug("join_path_code          =" + join_path_code);
//		logger.debug("join_date               =" + join_date);
//		logger.debug("join_time               =" + join_time);
//		logger.debug("modify_req_date         =" + modify_req_date);
//		logger.debug("modify_req_time         =" + modify_req_time);
//		logger.debug("term_reason_cd          =" + term_reason_cd);
//		logger.debug("req_date                =" + req_date);
//		logger.debug("req_time                =" + req_time);
//		logger.debug("ipin_ci                 =" + ipin_ci);
//		logger.debug("login_status_code       =" + login_status_code);
//		logger.debug("login_limit_sst_code    =" + login_limit_sst_code);
//		logger.debug("service_name            =" + service_name);
//		logger.debug("im_int_svc_no           =" + im_int_svc_no);
//		logger.debug("svc_mng_num             =" + svc_mng_num);
//		logger.debug("model_id                =" + model_id);
//		logger.debug("user_social_number      =" + user_social_number);
//		logger.debug("user_mdn_type           =" + user_mdn_type);
//		logger.debug("user_code               =" + user_code);
//		logger.debug("mobile_sign             =" + mobile_sign);
//		logger.debug("sign_data               =" + sign_data);
//		logger.debug("snAuthKey 			  =" + snAuthKey);
//		logger.debug("is_biz_auth 			  =" + is_biz_auth);
//		logger.debug("rname_auth_mns_code 	  =" + rname_auth_mns_code);
//		logger.debug("rname_auth_sst_code 	  =" + rname_auth_sst_code);
//		logger.debug("user_ci 	  =" + user_ci);
//		logger.debug("user_di 	  =" + user_di);
//		logger.debug("rname_auth_date 	  =" + rname_auth_date);
//		logger.debug("udt_type_cd 	  =" + udt_type_cd);
//		logger.debug("emailYn 	  =" + emailYn);
//		logger.debug("marketingYn 	  =" + marketingYn);
//		logger.debug("rname_auth_type_cd 	  =" + rname_auth_type_cd);
//		logger.debug("mdn 	  =" + mdn);
//		logger.debug("--------------------------------------------------");
//
//		if (cmd != null && !"".equals(cmd))
//			param.put("cmd", cmd);
//		param.put("sp_id", spId);
//		param.put("sp_auth_key", spAuthKey);
//		if (operation_mode != null && !"".equals(operation_mode))
//			param.put("operation_mode", operation_mode);
//		if (resp_type != null && !"".equals(resp_type))
//			param.put("resp_type", resp_type);
//		else
//			param.put("resp_type", ImIDPManager.IDP_PARAM_RESP_TYPE_XML);
//		if (resp_flow != null && !"".equals(resp_flow))
//			param.put("resp_flow", resp_flow);
//		if (resp_url != null && !"".equals(resp_url))
//			param.put("resp_url", resp_url);
//		if (key_type != null && !"".equals(key_type))
//			param.put("key_type", key_type);
//		if (key != null && !"".equals(key))
//			param.put("key", key);
//		if (user_auth_key != null && !"".equals(user_auth_key))
//			param.put("user_auth_key", user_auth_key);
//		if (user_id != null && !"".equals(user_id))
//			param.put("user_id", user_id);
//		if (old_id != null && !"".equals(old_id))
//			param.put("old_id", old_id);
//		if (user_passwd != null && !"".equals(user_passwd))
//			param.put("user_passwd", user_passwd);
//		if (user_passwd_type != null && !"".equals(user_passwd_type))
//			param.put("user_passwd_type", user_passwd_type);
//		if (user_passwd_modify_date != null && !"".equals(user_passwd_modify_date))
//			param.put("user_passwd_modify_date", user_passwd_modify_date);
//		if (auth_type != null && !"".equals(auth_type))
//			param.put("auth_type", auth_type);
//		if (user_tn != null && !"".equals(user_tn))
//			param.put("user_tn", user_tn);
//		if (is_user_tn_own != null && !"".equals(is_user_tn_own))
//			param.put("is_user_tn_own", is_user_tn_own);
//		if (is_user_tn_auth != null && !"".equals(is_user_tn_auth))
//			param.put("is_user_tn_auth", is_user_tn_auth);
//		if (user_tn_nation_cd != null && !"".equals(user_tn_nation_cd))
//			param.put("user_tn_nation_cd", user_tn_nation_cd);
//		if (user_tn_type != null && !"".equals(user_tn_type))
//			param.put("user_tn_type", user_tn_type);
//		if (user_email != null && !"".equals(user_email))
//			param.put("user_email", user_email);
//		if (is_email_auth != null && !"".equals(is_email_auth))
//			param.put("is_email_auth", is_email_auth);
//		if (user_type != null && !"".equals(user_type))
//			param.put("user_type", user_type);
//		if (user_name != null && !"".equals(user_name))
//			param.put("user_name", user_name);
//		if (is_rname_auth != null && !"".equals(is_rname_auth))
//			param.put("is_rname_auth", is_rname_auth);
//		if (user_mdn != null && !"".equals(user_mdn))
//			param.put("user_mdn", user_mdn);
//		if (user_mdn_auth_key != null && !"".equals(user_mdn_auth_key))
//			param.put("user_mdn_auth_key", user_mdn_auth_key);
//		if (user_sex != null && !"".equals(user_sex))
//			param.put("user_sex", user_sex);
//		if (user_birthday != null && !"".equals(user_birthday))
//			param.put("user_birthday", user_birthday);
//		if (user_calendar != null && !"".equals(user_calendar))
//			param.put("user_calendar", user_calendar);
//		if (user_zipcode != null && !"".equals(user_zipcode))
//			param.put("user_zipcode", user_zipcode);
//		if (user_address != null && !"".equals(user_address))
//			param.put("user_address", user_address);
//		if (user_address2 != null && !"".equals(user_address2))
//			param.put("user_address2", user_address2);
//		if (user_nation_code != null && !"".equals(user_nation_code))
//			param.put("user_nation_code", user_nation_code);
//		if (user_nation_name != null && !"".equals(user_nation_name))
//			param.put("user_nation_name", user_nation_name);
//		if (lang_code != null && !"".equals(lang_code))
//			param.put("lang_code", lang_code);
//		if (is_im_changed != null && !"".equals(is_im_changed))
//			param.put("is_im_changed", is_im_changed);
//		if (trans_sst_list != null && !"".equals(trans_sst_list))
//			param.put("trans_sst_list", trans_sst_list);
//		if (user_status_code != null && !"".equals(user_status_code))
//			param.put("user_status_code", user_status_code);
//		if (parent_type != null && !"".equals(parent_type))
//			param.put("parent_type", parent_type);
//		if (parent_rname_auth_type != null && !"".equals(parent_rname_auth_type))
//			param.put("parent_rname_auth_type", parent_rname_auth_type);
//		if (parent_rname_auth_key != null && !"".equals(parent_rname_auth_key))
//			param.put("parent_rname_auth_key", parent_rname_auth_key);
//		if (parent_name != null && !"".equals(parent_name))
//			param.put("parent_name", parent_name);
//		if (parent_mdn != null && !"".equals(parent_mdn))
//			param.put("parent_mdn", parent_mdn);
//		if (parent_email != null && !"".equals(parent_email))
//			param.put("parent_email", parent_email);
//		if (parent_approve_date != null && !"".equals(parent_approve_date))
//			param.put("parent_approve_date", parent_approve_date);
//		if (is_parent_approve != null && !"".equals(is_parent_approve))
//			param.put("is_parent_approve", is_parent_approve);
//		if (parent_approve_sst_code != null && !"".equals(parent_approve_sst_code))
//			param.put("parent_approve_sst_code", parent_approve_sst_code);
//		if (consent_tac != null && !"".equals(consent_tac))
//			param.put("consent_tac", consent_tac);
//		if (join_path_code != null && !"".equals(join_path_code))
//			param.put("join_path_code", join_path_code);
//		if (join_date != null && !"".equals(join_date))
//			param.put("join_date", join_date);
//		if (join_time != null && !"".equals(join_time))
//			param.put("join_time", join_time);
//		if (modify_req_date != null && !"".equals(modify_req_date))
//			param.put("modify_req_date", modify_req_date);
//		if (modify_req_time != null && !"".equals(modify_req_time))
//			param.put("modify_req_time", modify_req_time);
//		if (term_reason_cd != null && !"".equals(term_reason_cd))
//			param.put("term_reason_cd", term_reason_cd);
//		if (req_date != null && !"".equals(req_date))
//			param.put("req_date", req_date);
//		if (req_time != null && !"".equals(req_time))
//			param.put("req_time", req_time);
//		if (ipin_ci != null && !"".equals(ipin_ci))
//			param.put("ipin_ci", ipin_ci);
//		if (login_status_code != null && !"".equals(login_status_code))
//			param.put("login_status_code", login_status_code);
//		if (login_limit_sst_code != null && !"".equals(login_limit_sst_code))
//			param.put("login_limit_sst_code", login_limit_sst_code);
//		if (service_name != null && !"".equals(service_name))
//			param.put("service_name", service_name);
//		if (im_int_svc_no != null && !"".equals(im_int_svc_no))
//			param.put("im_int_svc_no", im_int_svc_no);
//		if (svc_mng_num != null && !"".equals(svc_mng_num))
//			param.put("svc_mng_num", svc_mng_num);
//		if (model_id != null && !"".equals(model_id))
//			param.put("model_id", model_id);
//		if (user_social_number != null && !"".equals(user_social_number))
//			param.put("user_social_number", user_social_number);
//		if (user_mdn_type != null && !"".equals(user_mdn_type))
//			param.put("user_mdn_type", user_mdn_type);
//		if (user_code != null && !"".equals(user_code))
//			param.put("user_code", user_code);
//		if (mobile_sign != null && !"".equals(mobile_sign))
//			param.put("mobile_sign", mobile_sign);
//		if (sign_data != null && !"".equals(sign_data))
//			param.put("sign_data", sign_data);
//		if (snAuthKey != null && !"".equals(snAuthKey))
//			param.put("sn_auth_key", snAuthKey);
//		if (rname_auth_mns_code != null && !"".equals(rname_auth_mns_code))
//			param.put("rname_auth_mns_code", rname_auth_mns_code);
//		if (rname_auth_sst_code != null && !"".equals(rname_auth_sst_code))
//			param.put("rname_auth_sst_code", rname_auth_sst_code);
//		if (is_biz_auth != null && !"".equals(is_biz_auth))
//			param.put("is_biz_auth", is_biz_auth);
//		if (user_ci != null && !"".equals(user_ci))
//			param.put("user_ci", user_ci);
//		if (user_di != null && !"".equals(user_di))
//			param.put("user_di", user_di);
//		if (udt_type_cd != null && !"".equals(udt_type_cd))
//			param.put("udt_type_cd", udt_type_cd);
//		if (rname_auth_date != null && !"".equals(rname_auth_date))
//			param.put("rname_auth_date", rname_auth_date);
//		if (parent_birthday != null && !"".equals(parent_birthday))
//			param.put("parent_birthday", parent_birthday);
//		if (rname_auth_type_cd != null && !"".equals(rname_auth_type_cd))
//			param.put("rname_auth_type_cd", rname_auth_type_cd);
//		if (mdn != null && !"".equals(mdn))
//			param.put("mdn", mdn);
//
//		Enumeration keys = param.keys();
//		String paramKey = null;
//		logger.info("IDP SEND DATA -----------------------------------");
//		logger.info("url=" + sendData.getUrl());
//		while ((keys != null) && keys.hasMoreElements()) {
//			paramKey = (String) keys.nextElement();
//			if ("user_social_number".equals(paramKey))
//				logger.info(paramKey + "= " + param.get(paramKey).substring(0, 6) + "*******");
//			else
//				logger.info(paramKey + "= " + param.get(paramKey));
//		}
//		logger.info("--------------------------------------------------");
//		return param;
//
//	}
//
//	private String toHex(byte hash[]) {
//		StringBuffer buf = new StringBuffer(hash.length * 2);
//
//		for (int i = 0; i < hash.length; i++) {
//			int intVal = hash[i] & 0xff;
//			if (intVal < 0x10) {
//				// append a zero before a one digit hex
//				// number to make it two digits.
//				buf.append("0");
//			}
//			buf.append(Integer.toHexString(intVal));
//		}
//		return buf.toString();
//	}
//
//	/**
//	 * SP_AUTH_KEY 작성시 호출해야할 Mac 생성 API
//	 * 
//	 */
//	private String generateMacSignature(String key, String message) throws Exception {
//		logger.info("key : " + key);
//		logger.info("message : " + message);
//		SecretKeySpec skeySpec = new SecretKeySpec(key.getBytes(), "HmacSHA1");
//
//		Mac mac = Mac.getInstance("HmacSHA1");
//		mac.init(skeySpec);
//
//		byte[] result = mac.doFinal(message.getBytes("euc-kr"));
//
//		return this.toHex(result);
//	}
//
//	/**
//	 * 휴대폰 인증결과 암호화
//	 * 
//	 * @param phoneMeta
//	 * @return
//	 * @throws Exception
//	 */
//	public String makePhoneAuthKey(String phoneMeta) throws Exception {
//		String time = Long.toString(System.currentTimeMillis());
//
//		StringBuffer sb = new StringBuffer();
//		sb.append("user_phone=");
//		sb.append(phoneMeta);
//		sb.append("|time=");
//		sb.append(time);
//
//		return this.generateMacSignature(this.IDP_REQ_OMP_ASSOC_KEY, sb.toString()) + "|" + time;
//	}
//
//	/**
//	 * 실명인증 인증결과 암호화
//	 * 
//	 * @param mbrNm
//	 * @param ssn
//	 * @return
//	 * @throws Exception
//	 */
//	public String makeSnAuthKey(String mbrNm, String ssn) throws Exception {
//		String time = Long.toString(System.currentTimeMillis());
//
//		StringBuffer sb = new StringBuffer();
//		sb.append("sn=");
//		sb.append(ssn);
//		sb.append("|name=");
//		sb.append(mbrNm);
//		sb.append("|time=");
//		sb.append(time);
//		logger.info("=============>[makeSnAuthKey str]" + sb.toString());
//		logger.info("=============>[sn_auth_key]"
//				+ this.generateMacSignature(this.IDP_REQ_OMP_ASSOC_KEY, sb.toString()) + "|" + time);
//		return this.generateMacSignature(this.IDP_REQ_OMP_ASSOC_KEY, sb.toString()) + "|" + time;
//	}
//
//	/**
//	 * make mac signature key <br />
//	 * override func - support domain parameters
//	 * 
//	 * @return
//	 * @throws Exception
//	 * @history 1. ? <br />
//	 *          2. nefer 2009-12-08 override func - override func - support domain parameters
//	 */
//	public String makeSpAuthKey() throws Exception {
//		logger.debug("ImIDPManager.OMP_SERVICE_DOMAIN : " + this.OMP_SERVICE_DOMAIN);
//		logger.debug("makeSpAuthKey(ImIDPManager.OMP_SERVICE_DOMAIN) : " + this.makeSpAuthKey(this.OMP_SERVICE_DOMAIN));
//		return this.makeSpAuthKey(this.OMP_SERVICE_DOMAIN);
//	}
//
//	/**
//	 * make mac signature key
//	 * 
//	 * @return
//	 * @throws Exception
//	 * @history 1. ? <br />
//	 *          2. nefer 2009-12-08 override func - override func - support domain parameters
//	 */
//	public String makeSpAuthKey(String domain) throws Exception {
//		String time = Long.toString(System.currentTimeMillis());
//
//		StringBuffer sb = new StringBuffer();
//		sb.append("sp_id=");
//		sb.append(this.IDP_REQ_OMP_SERVICE_ID);
//		sb.append("|");
//		sb.append("time=");
//		sb.append(time);
//		sb.append("|");
//		sb.append("domain=");
//		sb.append(domain);
//
//		return this.generateMacSignature(this.IDP_REQ_OMP_ASSOC_KEY, sb.toString()) + "|" + time;
//
//	}
//	// 필요 없는 것으로 판단되어 주석 처리 - 임재호 2014.1.8
//	// public String idpReqUrl(String url) throws Exception {
//	// return ImIDPManager.IDP_REQUEST_URL + url;
//	// }
//	//
//	// public String idpReqUrlHttps(String url) throws Exception {
//	// return ImIDPManager.IDP_REQUEST_URL_HTTPS + url;
//	// }
//
// }
