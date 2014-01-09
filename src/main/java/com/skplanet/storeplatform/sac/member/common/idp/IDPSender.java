/*
 * COPYRIGHT(c) SK telecom 2009
 * This software is the proprietary information of SK telecom.
 *
 * Revision History
 * Author       Date            Description
 * --------     ----------      ------------------
 * ?            ?               ?
 * nefer        2009.12.08      move to omp_common / override func - makeSpAuthKey - support 'domain' parameters
 *
 */
package com.skplanet.storeplatform.sac.member.common.idp;

import java.util.Enumeration;
import java.util.Hashtable;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.skplanet.storeplatform.sac.member.common.idp.vo.IDPSenderM;

@Component
public class IDPSender {

	private static Logger logger = Logger.getLogger(IDPSender.class);

	@Value("#{propertiesForSac['idp.service_domain']}")
	public String OMP_SERVICE_DOMAIN;
	/** IDP에 등록한 OMP Association Key */
	@Value("#{propertiesForSac['idp.sp_key']}")
	public String IDP_REQ_OMP_ASSOC_KEY;
	/** IDP로 부터 발급된 Service ID */
	@Value("#{propertiesForSac['idp.sp_id']}")
	public String IDP_REQ_OMP_SERVICE_ID;

	/* IDP 요청 URL */
	public static final String IDP_REQ_URL_JOIN = "/web/Join.api"; // omp.dev.idp.requrl.join
	public static final String IDP_REQ_URL_REALNAME_AUTH = "/web/RealNameAuth.api"; // omp.dev.idp.requrl.realname.auth
	public static final String IDP_REQ_URL_NATEID_AUTH = "/web/AuthNateID.api"; // omp.dev.idp.requrl.nate.auth
	public static final String IDP_REQ_URL_MOBILE_AUTH = "/web/MobileAuth.api"; // omp.dev.idp.requrl.mobile.auth
	public static final String IDP_REQ_URL_WATERMARK_AUTH = "/web/WatermarkAuth.api"; // omp.dev.idp.requrl.watermark.auth
	public static final String IDP_REQ_URL_USER_AUTH = "/web/Auth.api"; // omp.dev.idp.requrl.user.auth
	public static final String IDP_REQ_URL_USER_INFO_SEARCH = "/web/Search.api"; // omp.dev.idp.requrl.userinfo.search
	public static final String IDP_REQ_URL_USER_INFO_MODIFY = "/web/Modify.api"; // omp.dev.idp.requrl.userinfo.modify
	public static final String IDP_REQ_URL_SECEDE = "/web/Secede.api"; // omp.dev.idp.requrl.secede
	public static final String IDP_REQ_URL_MOBILE_SEND = "/web/MobileAuth.api";
	public static final String IDP_REQ_URL_CREATE_TOKEN = "/web/SpToken.api";
	public static final String IDP_REQ_URL_MAPPING_TOKEN = "/web/TokenMapping.api";
	public static final String IDP_REQ_URL_SEARCH_TOKEN = "/web/SpTokenCheck.api";
	public static final String IDP_REQ_URL_DELETE_TOKEN = "/web/MappingDel.api";

	/* IDP 요청 COMMAND */
	public static final String IDP_REQ_CMD_ALEADY_JOIN_CHECK = "aleadyJoinCheck"; // omp.dev.idp.reqcmd.aleady.joincheck
	public static final String IDP_REQ_CMD_DUPLICATE_ID_CHECK = "duplicateIDCheck"; // omp.dev.idp.reqcmd.duplicate.idcheck
	public static final String IDP_REQ_CMD_REALNAME_AUTH = "realNameAuth"; // omp.dev.idp.reqcmd.realname.auth
	public static final String IDP_REQ_CMD_NATEID_AUTH = "authNateID"; // omp.dev.idp.reqcmd.nateid.auth
	public static final String IDP_REQ_CMD_MOBILE_AUTHCODE_SEND = "mobileAuthcodeSend"; // omp.dev.idp.reqcmd.mobile.authcode.send
	public static final String IDP_REQ_CMD_MOBILE_AUTH = "mobileAuth"; // omp.dev.idp.reqcmd.mobile.auth
	public static final String IDP_REQ_CMD_WATERMARK_AUTH_IMAGE = "watermarkAuthImage"; // omp.dev.idp.reqcmd.watermark.auth.image
	public static final String IDP_REQ_CMD_WATERMARK_AUTH = "watermarkAuth"; // omp.dev.idp.reqcmd.watermark.auth
	public static final String IDP_REQ_CMD_JOIN_FOR_EMAIL = "joinApplyForEmail"; // omp.dev.idp.reqcmd.join.email
	public static final String IDP_REQ_CMD_JOIN_FOR_SN = "joinApplyForSN"; // omp.dev.idp.reqcmd.join.sn
	public static final String IDP_REQ_CMD_JOIN_COMPLETE = "joinComplete"; // omp.dev.idp.reqcmd.join.complete
	public static final String IDP_REQ_CMD_JOIN_REQEST = "join"; // omp.dev.idp.reqcmd.reqest
	public static final String IDP_REQ_CMD_AUTH_FOR_ID = "authForId"; // omp.dev.idp.reqcmd.auth.id
	public static final String IDP_REQ_CMD_AUTH_FOR_PWD = "authForPasswd"; // omp.dev.idp.reqcmd.auth.pwd
	public static final String IDP_REQ_CMD_AUTH_FOR_MDN = "authForMDN"; // omp.dev.idp.reqcmd.auth.mdn
	public static final String IDP_REQ_CMD_FIND_COMMON_PROFILE_FOR_BROWSER = "findCommonProfile"; // omp.dev.idp.reqcmd.find.commonprofile
	public static final String IDP_REQ_CMD_FIND_COMMON_PROFILE_FOR_SERVER = "findCommonProfileForServer"; // omp.dev.idp.reqcmd.find.commonprofile.server
	public static final String IDP_REQ_CMD_FIND_SPECIAL_PROFILE = "findSpecialProfile"; // omp.dev.idp.reqcmd.find.specialprofile
	public static final String IDP_REQ_CMD_FIND_ID = "findID"; // omp.dev.idp.reqcmd.find.id
	public static final String IDP_REQ_CMD_FIND_PWD = "findPasswd"; // omp.dev.idp.reqcmd.get.temppwd
	public static final String IDP_REQ_CMD_MODIFY_AUTH_INFO = "modifyAuthInfo"; // omp.dev.idp.reqcmd.modify.authinfo
	public static final String IDP_REQ_CMD_MODIFY_PROFILE = "modifyProfile"; // omp.dev.idp.reqcmd.modify.profile
	public static final String IDP_REQ_CMD_SECEDE_USER = "secedeUser"; // omp.dev.idp.reqcmd.secede.user
	public static final String IDP_REQ_CMD_AUTH_FOR_WAP = "authForWap";
	public static final String IDP_REQ_CMD_SECEDE_FOR_WAP = "secedeForWap"; // omp.dev.idp.reqcmd.secede.wap
	public static final String IDP_REQ_CMD_JOIN_FOR_WAP = "joinForWap";
	public static final String IDP_REQ_CMD_ALEADY_JOIN_MDN = "aleadyJoinCheckForMdn";
	public static final String IDP_REQ_CMD_AUTH_FOR_SSO = "authForSSO"; // omp.dev.idp.reqcmd.auth.sso
	public static final String IDP_REQ_CMD_LOGOUT_FOR_SSO = "logoutForSSO"; // omp.dev.idp.reqcmd.logout.sso
	public static final String IDP_REQ_CMD_GET_EMAIL_AUTH = "getEmailAuthLink";
	public static final String IDP_REQ_CMD_MODIFY_EMAIL = "modifyUserEmail";
	public static final String IDP_REQ_CMD_JOIN_SUP_SERVICE = "joinSupServiceRequest";
	public static final String IDP_REQ_CMD_SECEDE_SUP_SERVICE = "secedeSupServiceRequest";
	public static final String IDP_REQ_CMD_TMAP_SERVICE_AVALIBLE_CHECK = "tmapServiceAvalibleCheck";
	public static final String IDP_REQ_CMD_TMAP_SERVICE_CHECK = "tmapServiceCheck";
	public static final String IDP_REQ_CMD_FIND_MODEL_ID = "findModelId";
	public static final String IDP_REQ_CMD_MOBILE_SEND = "mobileSend";
	public static final String IDP_REQ_CMD_JOIN_SUP_SERVICE_REQUEST = "joinSupServiceRequest";// 부가서비스 가입
	public static final String IDP_REQ_CMD_SECEDE_SUP_SERVICE_REQUEST = "secedeSupServiceRequest";// 부가서비스 해지
	public static final String IDP_REQ_CMD_FIND_BILL = "findBill";// skt가입자 요금제 조회
	public static final String IDP_REQ_CMD_CREATE_TOKEN = "spToken";
	public static final String IDP_REQ_CMD_MAPPING_TOKEN = "tokenMapping";
	public static final String IDP_REQ_CMD_SEARCH_TOKEN = "spTokenCheck";
	public static final String IDP_REQ_CMD_DELETE_TOKEN = "mappingDel";

	/*
	 * IDP 통합 회원 고도화 추가
	 */
	public static final String IDP_REQ_CMD_OTHER_CHANNEL_ID_AUTH = "authIntegratedPasswd";
	public static final String IDP_REQ_CMD_OTHER_CHANNEL_LIST = "findJoinServiceList";
	public static final String IDP_REQ_CMD_OTHER_CHANNEL_REGIST = "joinApproveRequest";

	/**
	 * idp에 전달할 파라미터를 설정한다.
	 * 
	 * @param sendData
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	protected Hashtable<String, String> makeSendParam(IDPSenderM sendData) throws Exception {
		Hashtable<String, String> param = new Hashtable<String, String>();

		String cmd = sendData.getCmd();
		String spId = this.IDP_REQ_OMP_SERVICE_ID;
		String respType = sendData.getResp_type();
		String respFlow = sendData.getResp_flow();
		String respUrl = sendData.getResp_url();
		String keyType = sendData.getKey_type();
		String key = sendData.getKey();
		String pre_key = sendData.getPre_key();
		String userId = sendData.getUser_id();
		String type = sendData.getType();
		String userName = sendData.getUser_name();
		String userSocialNumber = sendData.getUser_social_number();
		String userPasswd = sendData.getUser_passwd();
		String userMdn = sendData.getUser_mdn();
		String userMdnType = sendData.getUser_mdn_type();
		String userCode = sendData.getUser_code();
		String mobileSign = sendData.getMobile_sign();
		String signData = sendData.getSign_data();
		String imageUrl = sendData.getImage_url();
		String imageSign = sendData.getImage_sign();
		String userEmail = sendData.getUser_email();
		String userJobCode = sendData.getUser_job_code();
		String userZipcode = sendData.getUser_zipcode();
		String userAddress = sendData.getUser_address();
		String userAddress2 = sendData.getUser_address2();
		String userSex = sendData.getUser_sex();
		String userBirthday = sendData.getUser_birthday();
		String userPhone = sendData.getUser_phone();
		String watermarkAuth = sendData.getWatermark_auth();
		String userType = sendData.getUser_type();
		String isParents = sendData.getIs_parents();
		String parentSocialNumber = sendData.getParent_social_number();
		String parentName = sendData.getParent_name();
		String parentEmail = sendData.getParent_email();
		String parentMdn = sendData.getParent_mdn();
		String userJob = sendData.getUser_job();
		String userTel = sendData.getUser_tel();
		String userAuthKey = sendData.getUser_auth_key();
		String preUserEmail = sendData.getPre_user_email();
		String userKey = sendData.getUser_key();
		String userCalendar = sendData.getUser_calendar();
		String isRnameAuth = sendData.getIs_rname_auth();
		String snAuthKey = sendData.getSn_auth_key();
		String phoneAuthKey = sendData.getPhone_auth_key();
		String spAuthKey = this.makeSpAuthKey();
		/* 모바일 회원가입 */
		String mdnCorp = sendData.getMdn_corp();
		/* 부가서비스 가입, 해지 */
		String svcCode = sendData.getSvc_code();
		String userSvcMngNum = sendData.getUser_svc_mng_num();
		String approve_sp_id = sendData.getApprove_sp_id();
		String comment = sendData.getComment();
		String targetService = sendData.getTarget_service();
		String sp_token = sendData.getSp_token();
		String service_token = sendData.getService_token();
		String service_secret = sendData.getService_secret();
		String service_id = sendData.getService_id();
		String user_ci = sendData.getUser_ci();

		logger.debug("IDP SEND DATA -----------------------------------");
		logger.debug("cmd                =" + cmd);
		logger.debug("spId               =" + spId);
		logger.debug("respType           =" + respType);
		logger.debug("respFlow           =" + respFlow);
		logger.debug("respUrl            =" + respUrl);
		logger.debug("keyType            =" + keyType);
		logger.debug("key                =" + key);
		logger.debug("pre_key            =" + pre_key);
		logger.debug("userId             =" + userId);
		logger.debug("type               =" + type);
		logger.debug("userName           =" + userName);
		logger.debug("userSocialNumber   =" + userSocialNumber);
		logger.debug("userPasswd         =" + userPasswd);
		logger.debug("userMdn            =" + userMdn);
		logger.debug("userMdnType        =" + userMdnType);
		logger.debug("userCode           =" + userCode);
		logger.debug("mobileSign         =" + mobileSign);
		logger.debug("signData           =" + signData);
		logger.debug("imageUrl           =" + imageUrl);
		logger.debug("imageSign          =" + imageSign);
		logger.debug("userEmail          =" + userEmail);
		logger.debug("userJobCode        =" + userJobCode);
		logger.debug("userZipcode        =" + userZipcode);
		logger.debug("userAddress        =" + userAddress);
		logger.debug("userAddress2       =" + userAddress2);
		logger.debug("userSex            =" + userSex);
		logger.debug("userBirthday       =" + userBirthday);
		logger.debug("userPhone          =" + userPhone);
		logger.debug("watermarkAuth      =" + watermarkAuth);
		logger.debug("userType           =" + userType);
		logger.debug("isParents          =" + isParents);
		logger.debug("parentSocialNumber =" + parentSocialNumber);
		logger.debug("parentName         =" + parentName);
		logger.debug("parentEmail        =" + parentEmail);
		logger.debug("parentMdn          =" + parentMdn);
		logger.debug("userJob            =" + userJob);
		logger.debug("userTel            =" + userTel);
		logger.debug("userAuthKey        =" + userAuthKey);
		logger.debug("preUserEmail       =" + preUserEmail);
		logger.debug("userKey            =" + userKey);
		logger.debug("user_calendar      =" + userCalendar);
		logger.debug("is_rname_auth      =" + isRnameAuth);
		logger.debug("sn_auth_key        =" + snAuthKey);
		logger.debug("phone_auth_key     =" + phoneAuthKey);
		logger.debug("sp_auth_key        =" + spAuthKey);
		logger.debug("mdnCorp            =" + mdnCorp);
		logger.debug("svcCode            =" + svcCode);
		logger.debug("userSvcMngNum      =" + userSvcMngNum);
		logger.debug("approve_sp_id      =" + approve_sp_id);
		logger.debug("comment      =" + comment);
		logger.debug("targetService      =" + targetService);
		logger.debug("sp_token      =" + sp_token);
		logger.debug("service_token      =" + service_token);
		logger.debug("service_secret      =" + service_secret);
		logger.debug("service_id      =" + service_id);
		logger.debug("user_ci      =" + user_ci);
		logger.debug("--------------------------------------------------");
		param.put("sp_id", spId);
		param.put("sp_auth_key", spAuthKey);

		if (respType != null && !"".equals(respType))
			param.put("resp_type", respType);
		else
			param.put("resp_type", IDPManager.IDP_PARAM_RESP_TYPE_XML);
		if (respFlow != null && !"".equals(respFlow))
			param.put("resp_flow", respFlow);
		if (respUrl != null && !"".equals(respUrl))
			param.put("resp_url", respUrl);
		if (keyType != null && !"".equals(keyType))
			param.put("key_type", keyType);
		if (key != null && !"".equals(key))
			param.put("key", key);
		if (pre_key != null && !"".equals(pre_key))
			param.put("pre_key", pre_key);
		if (userId != null && !"".equals(userId))
			param.put("user_id", userId);
		if (type != null && !"".equals(type))
			param.put("type", type);
		if (userName != null && !"".equals(userName))
			param.put("user_name", userName);
		if (userSocialNumber != null && !"".equals(userSocialNumber))
			param.put("user_social_number", userSocialNumber);
		if (userPasswd != null && !"".equals(userPasswd))
			param.put("user_passwd", userPasswd);
		if (userMdn != null && !"".equals(userMdn))
			param.put("user_mdn", userMdn);
		if (userMdnType != null && !"".equals(userMdnType))
			param.put("user_mdn_type", userMdnType);
		if (userCode != null && !"".equals(userCode))
			param.put("user_code", userCode);
		if (mobileSign != null && !"".equals(mobileSign))
			param.put("mobile_sign", mobileSign);
		if (signData != null && !"".equals(signData))
			param.put("sign_data", signData);
		if (imageUrl != null && !"".equals(imageUrl))
			param.put("image_url", imageUrl);
		if (imageSign != null && !"".equals(imageSign))
			param.put("image_sign", imageSign);
		if (userEmail != null && !"".equals(userEmail))
			param.put("user_email", userEmail);
		if (userJobCode != null && !"".equals(userJobCode))
			param.put("user_job_code", userJobCode);
		if (userZipcode != null && !"".equals(userZipcode))
			param.put("user_zipcode", userZipcode);
		if (userAddress != null && !"".equals(userAddress))
			param.put("user_address", userAddress);
		if (userAddress2 != null && !"".equals(userAddress2))
			param.put("user_address2", userAddress2);
		if (userSex != null && !"".equals(userSex))
			param.put("user_sex", userSex);
		if (userBirthday != null && !"".equals(userBirthday))
			param.put("user_birthday", userBirthday);
		if (userPhone != null)
			param.put("user_phone", userPhone);
		if (watermarkAuth != null && !"".equals(watermarkAuth))
			param.put("watermark_auth", watermarkAuth);
		if (userType != null && !"".equals(userType))
			param.put("user_type", userType);
		if (isParents != null && !"".equals(isParents))
			param.put("is_parents", isParents);
		if (parentSocialNumber != null && !"".equals(parentSocialNumber))
			param.put("parent_social_number", parentSocialNumber);
		if (parentName != null && !"".equals(parentName))
			param.put("parent_name", parentName);
		if (parentEmail != null && !"".equals(parentEmail))
			param.put("parent_email", parentEmail);
		if (parentMdn != null && !"".equals(parentMdn))
			param.put("parent_mdn", parentMdn);
		if (userJob != null && !"".equals(userJob))
			param.put("user_job", userJob);
		if (userTel != null && !"".equals(userTel))
			param.put("user_tel", userTel);
		if (userAuthKey != null && !"".equals(userAuthKey))
			param.put("user_auth_key", userAuthKey);
		if (cmd != null && !"".equals(cmd))
			param.put("cmd", cmd);
		if (preUserEmail != null && !"".equals(preUserEmail))
			param.put("pre_user_email", preUserEmail);
		if (userKey != null && !"".equals(userKey))
			param.put("user_key", userKey);
		if (userCalendar != null && !"".equals(userCalendar))
			param.put("user_calendar", userCalendar);
		if (isRnameAuth != null && !"".equals(isRnameAuth))
			param.put("is_rname_auth", isRnameAuth);
		if (snAuthKey != null && !"".equals(snAuthKey))
			param.put("sn_auth_key", snAuthKey);
		if (phoneAuthKey != null)
			param.put("phone_auth_key", phoneAuthKey);
		if (mdnCorp != null && !"".equals(mdnCorp))
			param.put("mdn_corp", mdnCorp);
		if (svcCode != null && !"".equals(svcCode))
			param.put("svc_code", svcCode);
		if (userSvcMngNum != null && !"".equals(userSvcMngNum))
			param.put("user_svc_mng_num", userSvcMngNum);
		if (approve_sp_id != null && !"".equals(approve_sp_id))
			param.put("approve_sp_id", approve_sp_id);
		if (comment != null && !"".equals(comment))
			param.put("comment", comment);
		if (targetService != null && !"".equals(targetService))
			param.put("target_service", targetService);
		if (sp_token != null && !"".equals(sp_token))
			param.put("sp_token", sp_token);
		if (service_token != null && !"".equals(service_token))
			param.put("service_token", service_token);
		if (service_secret != null && !"".equals(service_secret))
			param.put("service_secret", service_secret);
		if (service_id != null && !"".equals(service_id))
			param.put("service_id", service_id);
		if (user_ci != null && !"".equals(user_ci))
			param.put("user_ci", user_ci);
		Enumeration keys = param.keys();
		String paramKey = null;
		logger.info("IDP SEND DATA -----------------------------------");
		logger.info("url=" + sendData.getUrl());
		while ((keys != null) && keys.hasMoreElements()) {
			paramKey = (String) keys.nextElement();
			if ("user_social_number".equals(paramKey) || "parent_social_number".equals(paramKey)
					|| ("key".equals(paramKey) && param.get(paramKey).length() == 13))
				logger.info(paramKey + "= " + param.get(paramKey).substring(0, 6) + "*******");
			else
				logger.info(paramKey + "= " + param.get(paramKey));
		}
		logger.info("--------------------------------------------------");
		return param;

	}

	private String toHex(byte hash[]) {
		StringBuffer buf = new StringBuffer(hash.length * 2);

		for (int i = 0; i < hash.length; i++) {
			int intVal = hash[i] & 0xff;
			if (intVal < 0x10) {
				// append a zero before a one digit hex
				// number to make it two digits.
				buf.append("0");
			}
			buf.append(Integer.toHexString(intVal));
		}
		return buf.toString();
	}

	/**
	 * SP_AUTH_KEY 작성시 호출해야할 Mac 생성 API
	 * 
	 */
	private String generateMacSignature(String key, String message) throws Exception {
		logger.info("key : " + key);
		logger.info("message : " + message);
		SecretKeySpec skeySpec = new SecretKeySpec(key.getBytes(), "HmacSHA1");

		Mac mac = Mac.getInstance("HmacSHA1");
		mac.init(skeySpec);

		byte[] result = mac.doFinal(message.getBytes("euc-kr"));

		return this.toHex(result);
	}

	/**
	 * 휴대폰 인증결과 암호화
	 * 
	 * @param phoneMeta
	 * @return
	 * @throws Exception
	 */
	public String makePhoneAuthKey(String phoneMeta) throws Exception {
		String time = Long.toString(System.currentTimeMillis());

		StringBuffer sb = new StringBuffer();
		sb.append("user_phone=");
		sb.append(phoneMeta);
		sb.append("|time=");
		sb.append(time);

		return this.generateMacSignature(this.IDP_REQ_OMP_ASSOC_KEY, sb.toString()) + "|" + time;
	}

	/**
	 * 실명인증 인증결과 암호화
	 * 
	 * @param mbrNm
	 * @param ssn
	 * @return
	 * @throws Exception
	 */
	public String makeSnAuthKey(String mbrNm, String userId) throws Exception {
		String time = Long.toString(System.currentTimeMillis());

		StringBuffer sb = new StringBuffer();
		sb.append("user_id=");
		sb.append(userId);
		sb.append("|name=");
		sb.append(mbrNm);
		sb.append("|time=");
		sb.append(time);
		logger.info("=============>[makeSnAuthKey str]" + sb.toString());
		logger.info("=============>[sn_auth_key]"
				+ this.generateMacSignature(this.IDP_REQ_OMP_ASSOC_KEY, sb.toString()) + "|" + time);
		return this.generateMacSignature(this.IDP_REQ_OMP_ASSOC_KEY, sb.toString()) + "|" + time;
	}

	/**
	 * make mac signature key <br />
	 * override func - support domain parameters
	 * 
	 * @return
	 * @throws Exception
	 * @history 1. ? <br />
	 *          2. nefer 2009-12-08 override func - override func - support domain parameters
	 */
	public String makeSpAuthKey() throws Exception {
		logger.debug("IDPManager.OMP_SERVICE_DOMAIN : " + this.OMP_SERVICE_DOMAIN);
		logger.debug("makeSpAuthKey(IDPManager.OMP_SERVICE_DOMAIN) : " + this.makeSpAuthKey(this.OMP_SERVICE_DOMAIN));
		return this.makeSpAuthKey(this.OMP_SERVICE_DOMAIN);
	}

	/**
	 * make mac signature key
	 * 
	 * @return
	 * @throws Exception
	 * @history 1. ? <br />
	 *          2. nefer 2009-12-08 override func - override func - support domain parameters
	 */
	public String makeSpAuthKey(String domain) throws Exception {
		String time = Long.toString(System.currentTimeMillis());

		StringBuffer sb = new StringBuffer();
		sb.append("sp_id=");
		sb.append(this.IDP_REQ_OMP_SERVICE_ID);
		sb.append("|");
		sb.append("time=");
		sb.append(time);
		sb.append("|");
		sb.append("domain=");
		sb.append(domain);

		return this.generateMacSignature(this.IDP_REQ_OMP_ASSOC_KEY, sb.toString()) + "|" + time;

	}

	// 필요 없는 것으로 판단되어 주석 처리 - 임재호 2014.1.8
	// public String idpReqUrl(String url) throws Exception {
	// return IDPManager.IDP_REQUEST_URL + url;
	// }
	//
	// public String idpReqUrlHttps(String url) throws Exception {
	// return IDPManager.IDP_REQUEST_URL_HTTPS + url;
	// }

}
