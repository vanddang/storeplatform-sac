package com.skplanet.storeplatform.sac.member.common.idp.repository;

import java.util.Enumeration;
import java.util.Hashtable;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

import com.skplanet.storeplatform.external.client.idp.sci.IdpSCI;
import com.skplanet.storeplatform.external.client.idp.vo.IDPReceiverM;
import com.skplanet.storeplatform.external.client.idp.vo.ImIDPReceiverM;
import com.skplanet.storeplatform.external.client.idp.vo.SendReq;
import com.skplanet.storeplatform.external.client.idp.vo.SendRes;
import com.skplanet.storeplatform.framework.core.exception.StorePlatformException;
import com.skplanet.storeplatform.sac.member.common.idp.constants.IDPConstants;
import com.skplanet.storeplatform.sac.member.common.idp.constants.ImIDPConstants;
import com.skplanet.storeplatform.sac.member.common.idp.vo.IDPSenderM;
import com.skplanet.storeplatform.sac.member.common.idp.vo.ImIDPSenderM;

/**
 * SAC => E/C Outbound
 * 
 * Updated on : 2014. 1. 16. Updated by : 김경복, 부르칸.
 */
@Repository
public class IDPRepositoryImpl implements IDPRepository {

	private static final Logger LOGGER = LoggerFactory.getLogger(IDPRepositoryImpl.class);

	@Value("#{propertiesForSac['idp.service_domain']}")
	public String OMP_SERVICE_DOMAIN;
	/** IDP에 등록한 OMP Association Key */
	@Value("#{propertiesForSac['idp.sp_key']}")
	public String IDP_REQ_OMP_ASSOC_KEY;
	/** IDP로 부터 발급된 Service ID */
	@Value("#{propertiesForSac['idp.sp_id']}")
	public String IDP_REQ_OMP_SERVICE_ID;
	/** ImIDP 요청 도메인 (HTTP) */
	@Value("#{propertiesForSac['idp.im.request.operation']}")
	public String IDP_OPERATION_MODE;

	@Autowired
	private IdpSCI idpSCI;

	/**
	 * <pre>
	 * IDP _HTTP
	 * </pre>
	 * 
	 * @param sendData
	 * @return @
	 */
	@Override
	public IDPReceiverM sendIDP(IDPSenderM sendData) {

		SendReq sendReq = new SendReq();
		sendReq.setProtocol(SendReq.HTTP_PROTOCOL.HTTPS);
		// TODO : IDP 연동시 POST로 전달하면 에러 발생하여 무조건 GET으로 가도록 셋팅 함 - 임재호 2014.1.8
		// TODO : IDP 연동시 POST로 전달할지 GET으로 전달할지 로직이나 메서드에서 판단 하여 넘겨 주어야 함, 임시로 하드코딩함 - 임재호 2014.1.8
		sendReq.setMethod(SendReq.HTTP_METHOD.GET);
		sendReq.setIm(false);
		sendReq.setUrl(sendData.getUrl());
		sendReq.setReqParam(this.makeIDPSendParam(sendData));

		SendRes sendRes = this.idpSCI.send(sendReq);

		IDPReceiverM receiveData = sendRes.getIdpReceiverM();

		return receiveData;
	}

	/**
	 * <pre>
	 * method 설명.
	 * </pre>
	 * 
	 * @param sendData
	 * @return @
	 */
	@Override
	public ImIDPReceiverM sendImIDP(ImIDPSenderM sendData) {
		SendReq sendReq = new SendReq();
		sendReq.setProtocol(SendReq.HTTP_PROTOCOL.HTTPS);
		// TODO : IDP 연동시 POST로 전달하면 에러 발생하여 무조건 GET으로 가도록 셋팅 함 - 임재호 2014.1.8
		// TODO : IDP 연동시 POST로 전달할지 GET으로 전달할지 로직이나 메서드에서 판단 하여 넘겨 주어야 함, 임시로 하드코딩함 - 임재호 2014.1.8
		sendReq.setMethod(SendReq.HTTP_METHOD.GET);
		sendReq.setIm(true);
		sendReq.setUrl(sendData.getUrl());
		sendReq.setReqParam(this.makeImIDPSendParam(sendData));
		SendRes sendRes = this.idpSCI.send(sendReq);

		ImIDPReceiverM receiveData = sendRes.getImIDPReceiverM();

		return receiveData;
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
	private String generateMacSignature(String key, String message) {
		LOGGER.info("key : " + key);
		LOGGER.info("message : " + message);
		SecretKeySpec skeySpec = new SecretKeySpec(key.getBytes(), "HmacSHA1");

		Mac mac = null;
		byte[] result = null;
		try {
			mac = Mac.getInstance("HmacSHA1");
			mac.init(skeySpec);
			result = mac.doFinal(message.getBytes("euc-kr"));
		} catch (Exception e) {
			throw new StorePlatformException("SAC_MEM_", e);
		}

		return this.toHex(result);
	}

	/**
	 * 휴대폰 인증결과 암호화
	 * 
	 * @param phoneMeta
	 * @return @
	 */
	@Override
	public String makePhoneAuthKey(String phoneMeta) {
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
	 * @return @
	 */
	@Override
	public String makeSnAuthKey(String mbrNm, String userId) {
		String time = Long.toString(System.currentTimeMillis());

		StringBuffer sb = new StringBuffer();
		sb.append("user_id=");
		sb.append(userId);
		sb.append("|name=");
		sb.append(mbrNm);
		sb.append("|time=");
		sb.append(time);
		LOGGER.info("=============>[makeSnAuthKey str]" + sb.toString());
		LOGGER.info("=============>[sn_auth_key]"
				+ this.generateMacSignature(this.IDP_REQ_OMP_ASSOC_KEY, sb.toString()) + "|" + time);
		return this.generateMacSignature(this.IDP_REQ_OMP_ASSOC_KEY, sb.toString()) + "|" + time;
	}

	/**
	 * make mac signature key <br />
	 * override func - support domain parameters
	 * 
	 * @return @
	 * @history 1. ? <br />
	 *          2. nefer 2009-12-08 override func - override func - support domain parameters
	 */
	public String makeSpAuthKey() {
		LOGGER.debug("IDPManager.OMP_SERVICE_DOMAIN : " + this.OMP_SERVICE_DOMAIN);
		LOGGER.debug("makeSpAuthKey(IDPManager.OMP_SERVICE_DOMAIN) : " + this.makeSpAuthKey(this.OMP_SERVICE_DOMAIN));
		return this.makeSpAuthKey(this.OMP_SERVICE_DOMAIN);
	}

	/**
	 * make mac signature key
	 * 
	 * @return @
	 * @history 1. ? <br />
	 *          2. nefer 2009-12-08 override func - override func - support domain parameters
	 */
	public String makeSpAuthKey(String domain) {
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

	/**
	 * idp에 전달할 파라미터를 설정한다.
	 * 
	 * @param sendData
	 * @return @
	 */
	@SuppressWarnings("rawtypes")
	public Hashtable<String, String> makeIDPSendParam(IDPSenderM sendData) {
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

		// LOGGER.debug("IDP SEND DATA -----------------------------------");
		// LOGGER.debug("cmd                =" + cmd);
		// LOGGER.debug("spId               =" + spId);
		// LOGGER.debug("respType           =" + respType);
		// LOGGER.debug("respFlow           =" + respFlow);
		// LOGGER.debug("respUrl            =" + respUrl);
		// LOGGER.debug("keyType            =" + keyType);
		// LOGGER.debug("key                =" + key);
		// LOGGER.debug("pre_key            =" + pre_key);
		// LOGGER.debug("userId             =" + userId);
		// LOGGER.debug("type               =" + type);
		// LOGGER.debug("userName           =" + userName);
		// LOGGER.debug("userSocialNumber   =" + userSocialNumber);
		// LOGGER.debug("userPasswd         =" + userPasswd);
		// LOGGER.debug("userMdn            =" + userMdn);
		// LOGGER.debug("userMdnType        =" + userMdnType);
		// LOGGER.debug("userCode           =" + userCode);
		// LOGGER.debug("mobileSign         =" + mobileSign);
		// LOGGER.debug("signData           =" + signData);
		// LOGGER.debug("imageUrl           =" + imageUrl);
		// LOGGER.debug("imageSign          =" + imageSign);
		// LOGGER.debug("userEmail          =" + userEmail);
		// LOGGER.debug("userJobCode        =" + userJobCode);
		// LOGGER.debug("userZipcode        =" + userZipcode);
		// LOGGER.debug("userAddress        =" + userAddress);
		// LOGGER.debug("userAddress2       =" + userAddress2);
		// LOGGER.debug("userSex            =" + userSex);
		// LOGGER.debug("userBirthday       =" + userBirthday);
		// LOGGER.debug("userPhone          =" + userPhone);
		// LOGGER.debug("watermarkAuth      =" + watermarkAuth);
		// LOGGER.debug("userType           =" + userType);
		// LOGGER.debug("isParents          =" + isParents);
		// LOGGER.debug("parentSocialNumber =" + parentSocialNumber);
		// LOGGER.debug("parentName         =" + parentName);
		// LOGGER.debug("parentEmail        =" + parentEmail);
		// LOGGER.debug("parentMdn          =" + parentMdn);
		// LOGGER.debug("userJob            =" + userJob);
		// LOGGER.debug("userTel            =" + userTel);
		// LOGGER.debug("userAuthKey        =" + userAuthKey);
		// LOGGER.debug("preUserEmail       =" + preUserEmail);
		// LOGGER.debug("userKey            =" + userKey);
		// LOGGER.debug("user_calendar      =" + userCalendar);
		// LOGGER.debug("is_rname_auth      =" + isRnameAuth);
		// LOGGER.debug("sn_auth_key        =" + snAuthKey);
		// LOGGER.debug("phone_auth_key     =" + phoneAuthKey);
		// LOGGER.debug("sp_auth_key        =" + spAuthKey);
		// LOGGER.debug("mdnCorp            =" + mdnCorp);
		// LOGGER.debug("svcCode            =" + svcCode);
		// LOGGER.debug("userSvcMngNum      =" + userSvcMngNum);
		// LOGGER.debug("approve_sp_id      =" + approve_sp_id);
		// LOGGER.debug("comment      =" + comment);
		// LOGGER.debug("targetService      =" + targetService);
		// LOGGER.debug("sp_token      =" + sp_token);
		// LOGGER.debug("service_token      =" + service_token);
		// LOGGER.debug("service_secret      =" + service_secret);
		// LOGGER.debug("service_id      =" + service_id);
		// LOGGER.debug("user_ci      =" + user_ci);
		// LOGGER.debug("--------------------------------------------------");
		param.put("sp_id", spId);
		param.put("sp_auth_key", spAuthKey);

		if (respType != null && !"".equals(respType))
			param.put("resp_type", respType);
		else
			param.put("resp_type", IDPConstants.IDP_PARAM_RESP_TYPE_XML);
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
		LOGGER.info("IDP SEND DATA -----------------------------------");
		LOGGER.info("url=" + sendData.getUrl());
		while ((keys != null) && keys.hasMoreElements()) {
			paramKey = (String) keys.nextElement();
			if ("user_social_number".equals(paramKey) || "parent_social_number".equals(paramKey)
					|| ("key".equals(paramKey) && param.get(paramKey).length() == 13))
				LOGGER.info(paramKey + "= " + param.get(paramKey).substring(0, 6) + "*******");
			else
				LOGGER.info(paramKey + "= " + param.get(paramKey));
		}
		LOGGER.info("--------------------------------------------------");
		return param;

	}

	/**
	 * idp에 전달할 파라미터를 설정한다.
	 * 
	 * @param sendData
	 * @return @
	 */
	@SuppressWarnings("rawtypes")
	public Hashtable<String, String> makeImIDPSendParam(ImIDPSenderM sendData) {

		Hashtable<String, String> param = new Hashtable<String, String>();

		String cmd = sendData.getCmd();
		String spId = this.IDP_REQ_OMP_SERVICE_ID;
		String operation_mode = this.IDP_OPERATION_MODE;
		// if (ImIDPConstants.IDP_CHECK_SERVER_DEV.equals(spId) && this.IDP_REQUEST_URL.indexOf("innoace.com:8002") >
		// -1) {
		// operation_mode = ImIDPConstants.IDP_PARAM_OPERATION_MODE_DEV;
		// } else if (ImIDPConstants.IDP_CHECK_SERVER_DEV.equals(spId)
		// && this.IDP_REQUEST_URL.indexOf("innoace.com:8003") > -1) {
		// operation_mode = ImIDPConstants.IDP_PARAM_OPERATION_MODE_STAG;
		// } else if (ImIDPConstants.IDP_CHECK_SERVER_STAG.equals(spId) && this.IDP_REQUEST_URL.indexOf("nate") > -1) {
		// operation_mode = ImIDPConstants.IDP_PARAM_OPERATION_MODE_TEST;
		// } else if (ImIDPConstants.IDP_CHECK_SERVER_REAL.equals(spId)) {
		// operation_mode = ImIDPConstants.IDP_PARAM_OPERATION_MODE_REAL;
		// }
		// // 개발용
		// if (ImIDPConstants.IDP_CHECK_SERVER_DEV.equals(spId) && this.IDP_REQUEST_URL.indexOf("211.63.6.59") > -1) {
		// operation_mode = ImIDPConstants.IDP_PARAM_OPERATION_MODE_TEST;
		// } else if (ImIDPConstants.IDP_CHECK_SERVER_STAG.equals(spId)
		// && this.IDP_REQUEST_URL.indexOf("211.63.6.59") > -1) {
		// operation_mode = ImIDPConstants.IDP_PARAM_OPERATION_MODE_TEST;
		// } else if (ImIDPConstants.IDP_CHECK_SERVER_REAL.equals(spId)) {
		// operation_mode = ImIDPConstants.IDP_PARAM_OPERATION_MODE_REAL;
		// }

		String resp_type = sendData.getResp_type();
		String resp_flow = sendData.getResp_flow();
		String resp_url = sendData.getResp_url();
		String key_type = sendData.getKey_type();
		String key = sendData.getKey();
		String user_auth_key = sendData.getUser_auth_key();
		String user_id = sendData.getUser_id();
		String old_id = sendData.getOld_id();
		String user_passwd = sendData.getUser_passwd();
		String user_passwd_type = sendData.getUser_passwd_type();
		String user_passwd_modify_date = sendData.getUser_passwd_modify_date();
		String auth_type = sendData.getAuth_type();
		String user_tn = sendData.getUser_tn();
		String is_user_tn_own = sendData.getIs_user_tn_own();
		String is_user_tn_auth = sendData.getIs_user_tn_auth();
		String user_tn_nation_cd = sendData.getUser_tn_nation_cd();
		String user_tn_type = sendData.getUser_tn_type();
		String user_email = sendData.getUser_email();
		String is_email_auth = sendData.getIs_email_auth();
		String user_type = sendData.getUser_type();
		String user_name = sendData.getUser_name();
		String is_rname_auth = sendData.getIs_rname_auth();
		String user_mdn = sendData.getUser_mdn();
		String user_mdn_auth_key = sendData.getUser_mdn_auth_key();
		String user_sex = sendData.getUser_sex();
		String user_birthday = sendData.getUser_birthday();
		String user_calendar = sendData.getUser_calendar();
		String user_zipcode = sendData.getUser_zipcode();
		String user_address = sendData.getUser_address();
		String user_address2 = sendData.getUser_address2();
		String user_nation_code = sendData.getUser_nation_code();
		String user_nation_name = sendData.getUser_nation_name();
		String lang_code = sendData.getLang_code();
		String is_im_changed = sendData.getIs_im_changed();
		String trans_sst_list = sendData.getTans_sst_list();
		String user_status_code = sendData.getUser_status_code();
		String parent_type = sendData.getParent_type();
		String parent_rname_auth_type = sendData.getParent_rname_auth_type();
		String parent_rname_auth_key = sendData.getParent_rname_auth_key();
		String parent_name = sendData.getParent_name();
		String parent_mdn = sendData.getParent_mdn();
		String parent_email = sendData.getParent_email();
		String parent_approve_date = sendData.getParent_approve_date();
		String is_parent_approve = sendData.getIs_parent_approve();
		String parent_approve_sst_code = sendData.getParent_approve_sst_code();
		String consent_tac = sendData.getConsent_tac();
		String join_path_code = sendData.getJoin_path_code();
		String join_date = sendData.getJoin_date();
		String join_time = sendData.getJoin_time();
		String modify_req_date = sendData.getModify_req_date();
		String modify_req_time = sendData.getModify_req_time();
		String term_reason_cd = sendData.getTerm_reason_cd();
		String req_date = sendData.getReq_date();
		String req_time = sendData.getReq_time();
		String ipin_ci = sendData.getIpin_ci();
		String login_status_code = sendData.getLogin_status_code();
		String login_limit_sst_code = sendData.getLogin_limit_sst_code();
		String service_name = sendData.getService_name();
		String im_int_svc_no = sendData.getIm_int_svc_no();
		String svc_mng_num = sendData.getSvc_mng_num();
		String model_id = sendData.getModel_id();
		String user_social_number = sendData.getUser_social_number();
		String user_mdn_type = sendData.getUser_mdn_type();
		String user_code = sendData.getUser_code();
		String mobile_sign = sendData.getMobile_sign();
		String sign_data = sendData.getSign_data();
		String spAuthKey = this.makeSpAuthKey();
		String snAuthKey = sendData.getSn_auth_key();
		String rname_auth_mns_code = sendData.getRname_auth_mns_code();
		String rname_auth_sst_code = sendData.getRname_auth_sst_code();
		String is_biz_auth = sendData.getIs_biz_auth();
		String user_ci = sendData.getUser_ci();
		String user_di = sendData.getUser_di();
		String rname_auth_date = sendData.getRname_auth_date();
		String udt_type_cd = sendData.getUdt_type_cd();
		String parent_birthday = sendData.getParent_birthday();
		String emailYn = sendData.getEmailYn();
		String marketingYn = sendData.getMarketingYn();
		String rname_auth_type_cd = sendData.getRname_auth_type_cd();
		String mdn = sendData.getMdn();
		String join_sst_list = sendData.getJoin_sst_list();

		// LOGGER.debug("IDP SEND DATA -----------------------------------");
		// LOGGER.debug("cmd                     =" + cmd);
		// LOGGER.debug("spId                    =" + spId);
		// LOGGER.debug("sp_auth_key             =" + spAuthKey);
		// LOGGER.debug("resp_type               =" + resp_type);
		// LOGGER.debug("resp_flow               =" + resp_flow);
		// LOGGER.debug("resp_url                =" + resp_url);
		// LOGGER.debug("key_type                =" + key_type);
		// LOGGER.debug("key                     =" + key);
		// LOGGER.debug("user_auth_key           =" + user_auth_key);
		// LOGGER.debug("user_id                 =" + user_id);
		// LOGGER.debug("old_id                  =" + old_id);
		// LOGGER.debug("user_passwd             =" + user_passwd);
		// LOGGER.debug("user_passwd_type        =" + user_passwd_type);
		// LOGGER.debug("auth_type               =" + auth_type);
		// LOGGER.debug("user_tn                 =" + user_tn);
		// LOGGER.debug("is_user_tn_own          =" + is_user_tn_own);
		// LOGGER.debug("is_user_tn_auth         =" + is_user_tn_auth);
		// LOGGER.debug("user_tn_nation_cd       =" + user_tn_nation_cd);
		// LOGGER.debug("user_tn_type            =" + user_tn_type);
		// LOGGER.debug("user_email              =" + user_email);
		// LOGGER.debug("is_email_auth           =" + is_email_auth);
		// LOGGER.debug("user_type               =" + user_type);
		// LOGGER.debug("user_name               =" + user_name);
		// LOGGER.debug("is_rname_auth           =" + is_rname_auth);
		// LOGGER.debug("user_mdn                =" + user_mdn);
		// LOGGER.debug("user_mdn_auth_key       =" + user_mdn_auth_key);
		// LOGGER.debug("user_sex                =" + user_sex);
		// LOGGER.debug("user_birthday           =" + user_birthday);
		// LOGGER.debug("user_calendar           =" + user_calendar);
		// LOGGER.debug("user_zipcode            =" + user_zipcode);
		// LOGGER.debug("user_address            =" + user_address);
		// LOGGER.debug("user_address2           =" + user_address2);
		// LOGGER.debug("user_nation_code        =" + user_nation_code);
		// LOGGER.debug("user_nation_name        =" + user_nation_name);
		// LOGGER.debug("lang_code               =" + lang_code);
		// LOGGER.debug("is_im_changed           =" + is_im_changed);
		// LOGGER.debug("trans_sst_list          =" + trans_sst_list);
		// LOGGER.debug("user_status_code        =" + user_status_code);
		// LOGGER.debug("parent_type             =" + parent_type);
		// LOGGER.debug("parent_rname_auth_type  =" + parent_rname_auth_type);
		// LOGGER.debug("parent_rname_auth_key   =" + parent_rname_auth_key);
		// LOGGER.debug("parent_name             =" + parent_name);
		// LOGGER.debug("parent_birthday             =" + parent_birthday);
		// LOGGER.debug("parent_mdn              =" + parent_mdn);
		// LOGGER.debug("parent_email            =" + parent_email);
		// LOGGER.debug("parent_approve_date     =" + parent_approve_date);
		// LOGGER.debug("is_parent_approve       =" + is_parent_approve);
		// LOGGER.debug("parent_approve_sst_code =" + parent_approve_sst_code);
		// LOGGER.debug("consent_tac             =" + consent_tac);
		// LOGGER.debug("join_path_code          =" + join_path_code);
		// LOGGER.debug("join_date               =" + join_date);
		// LOGGER.debug("join_time               =" + join_time);
		// LOGGER.debug("modify_req_date         =" + modify_req_date);
		// LOGGER.debug("modify_req_time         =" + modify_req_time);
		// LOGGER.debug("term_reason_cd          =" + term_reason_cd);
		// LOGGER.debug("req_date                =" + req_date);
		// LOGGER.debug("req_time                =" + req_time);
		// LOGGER.debug("ipin_ci                 =" + ipin_ci);
		// LOGGER.debug("login_status_code       =" + login_status_code);
		// LOGGER.debug("login_limit_sst_code    =" + login_limit_sst_code);
		// LOGGER.debug("service_name            =" + service_name);
		// LOGGER.debug("im_int_svc_no           =" + im_int_svc_no);
		// LOGGER.debug("svc_mng_num             =" + svc_mng_num);
		// LOGGER.debug("model_id                =" + model_id);
		// LOGGER.debug("user_social_number      =" + user_social_number);
		// LOGGER.debug("user_mdn_type           =" + user_mdn_type);
		// LOGGER.debug("user_code               =" + user_code);
		// LOGGER.debug("mobile_sign             =" + mobile_sign);
		// LOGGER.debug("sign_data               =" + sign_data);
		// LOGGER.debug("snAuthKey 			  =" + snAuthKey);
		// LOGGER.debug("is_biz_auth 			  =" + is_biz_auth);
		// LOGGER.debug("rname_auth_mns_code 	  =" + rname_auth_mns_code);
		// LOGGER.debug("rname_auth_sst_code 	  =" + rname_auth_sst_code);
		// LOGGER.debug("user_ci 	  =" + user_ci);
		// LOGGER.debug("user_di 	  =" + user_di);
		// LOGGER.debug("rname_auth_date 	  =" + rname_auth_date);
		// LOGGER.debug("udt_type_cd 	  =" + udt_type_cd);
		// LOGGER.debug("emailYn 	  =" + emailYn);
		// LOGGER.debug("marketingYn 	  =" + marketingYn);
		// LOGGER.debug("rname_auth_type_cd 	  =" + rname_auth_type_cd);
		// LOGGER.debug("mdn 	  =" + mdn);
		// LOGGER.debug("--------------------------------------------------");

		if (cmd != null && !"".equals(cmd))
			param.put("cmd", cmd);
		param.put("sp_id", spId);
		param.put("sp_auth_key", spAuthKey);
		if (operation_mode != null && !"".equals(operation_mode))
			param.put("operation_mode", operation_mode);
		if (resp_type != null && !"".equals(resp_type))
			param.put("resp_type", resp_type);
		else
			param.put("resp_type", ImIDPConstants.IDP_PARAM_RESP_TYPE_XML);
		if (resp_flow != null && !"".equals(resp_flow))
			param.put("resp_flow", resp_flow);
		if (resp_url != null && !"".equals(resp_url))
			param.put("resp_url", resp_url);
		if (key_type != null && !"".equals(key_type))
			param.put("key_type", key_type);
		if (key != null && !"".equals(key))
			param.put("key", key);
		if (user_auth_key != null && !"".equals(user_auth_key))
			param.put("user_auth_key", user_auth_key);
		if (user_id != null && !"".equals(user_id))
			param.put("user_id", user_id);
		if (old_id != null && !"".equals(old_id))
			param.put("old_id", old_id);
		if (user_passwd != null && !"".equals(user_passwd))
			param.put("user_passwd", user_passwd);
		if (user_passwd_type != null && !"".equals(user_passwd_type))
			param.put("user_passwd_type", user_passwd_type);
		if (user_passwd_modify_date != null && !"".equals(user_passwd_modify_date))
			param.put("user_passwd_modify_date", user_passwd_modify_date);
		if (auth_type != null && !"".equals(auth_type))
			param.put("auth_type", auth_type);
		if (user_tn != null && !"".equals(user_tn))
			param.put("user_tn", user_tn);
		if (is_user_tn_own != null && !"".equals(is_user_tn_own))
			param.put("is_user_tn_own", is_user_tn_own);
		if (is_user_tn_auth != null && !"".equals(is_user_tn_auth))
			param.put("is_user_tn_auth", is_user_tn_auth);
		if (user_tn_nation_cd != null && !"".equals(user_tn_nation_cd))
			param.put("user_tn_nation_cd", user_tn_nation_cd);
		if (user_tn_type != null && !"".equals(user_tn_type))
			param.put("user_tn_type", user_tn_type);
		if (user_email != null && !"".equals(user_email))
			param.put("user_email", user_email);
		if (is_email_auth != null && !"".equals(is_email_auth))
			param.put("is_email_auth", is_email_auth);
		if (user_type != null && !"".equals(user_type))
			param.put("user_type", user_type);
		if (user_name != null && !"".equals(user_name))
			param.put("user_name", user_name);
		if (is_rname_auth != null && !"".equals(is_rname_auth))
			param.put("is_rname_auth", is_rname_auth);
		if (user_mdn != null && !"".equals(user_mdn))
			param.put("user_mdn", user_mdn);
		if (user_mdn_auth_key != null && !"".equals(user_mdn_auth_key))
			param.put("user_mdn_auth_key", user_mdn_auth_key);
		if (user_sex != null && !"".equals(user_sex))
			param.put("user_sex", user_sex);
		if (user_birthday != null && !"".equals(user_birthday))
			param.put("user_birthday", user_birthday);
		if (user_calendar != null && !"".equals(user_calendar))
			param.put("user_calendar", user_calendar);
		if (user_zipcode != null && !"".equals(user_zipcode))
			param.put("user_zipcode", user_zipcode);
		if (user_address != null && !"".equals(user_address))
			param.put("user_address", user_address);
		if (user_address2 != null && !"".equals(user_address2))
			param.put("user_address2", user_address2);
		if (user_nation_code != null && !"".equals(user_nation_code))
			param.put("user_nation_code", user_nation_code);
		if (user_nation_name != null && !"".equals(user_nation_name))
			param.put("user_nation_name", user_nation_name);
		if (lang_code != null && !"".equals(lang_code))
			param.put("lang_code", lang_code);
		if (is_im_changed != null && !"".equals(is_im_changed))
			param.put("is_im_changed", is_im_changed);
		if (trans_sst_list != null && !"".equals(trans_sst_list))
			param.put("trans_sst_list", trans_sst_list);
		if (user_status_code != null && !"".equals(user_status_code))
			param.put("user_status_code", user_status_code);
		if (parent_type != null && !"".equals(parent_type))
			param.put("parent_type", parent_type);
		if (parent_rname_auth_type != null && !"".equals(parent_rname_auth_type))
			param.put("parent_rname_auth_type", parent_rname_auth_type);
		if (parent_rname_auth_key != null && !"".equals(parent_rname_auth_key))
			param.put("parent_rname_auth_key", parent_rname_auth_key);
		if (parent_name != null && !"".equals(parent_name))
			param.put("parent_name", parent_name);
		if (parent_mdn != null && !"".equals(parent_mdn))
			param.put("parent_mdn", parent_mdn);
		if (parent_email != null && !"".equals(parent_email))
			param.put("parent_email", parent_email);
		if (parent_approve_date != null && !"".equals(parent_approve_date))
			param.put("parent_approve_date", parent_approve_date);
		if (is_parent_approve != null && !"".equals(is_parent_approve))
			param.put("is_parent_approve", is_parent_approve);
		if (parent_approve_sst_code != null && !"".equals(parent_approve_sst_code))
			param.put("parent_approve_sst_code", parent_approve_sst_code);
		if (consent_tac != null && !"".equals(consent_tac))
			param.put("consent_tac", consent_tac);
		if (join_path_code != null && !"".equals(join_path_code))
			param.put("join_path_code", join_path_code);
		if (join_date != null && !"".equals(join_date))
			param.put("join_date", join_date);
		if (join_time != null && !"".equals(join_time))
			param.put("join_time", join_time);
		if (modify_req_date != null && !"".equals(modify_req_date))
			param.put("modify_req_date", modify_req_date);
		if (modify_req_time != null && !"".equals(modify_req_time))
			param.put("modify_req_time", modify_req_time);
		if (term_reason_cd != null && !"".equals(term_reason_cd))
			param.put("term_reason_cd", term_reason_cd);
		if (req_date != null && !"".equals(req_date))
			param.put("req_date", req_date);
		if (req_time != null && !"".equals(req_time))
			param.put("req_time", req_time);
		if (ipin_ci != null && !"".equals(ipin_ci))
			param.put("ipin_ci", ipin_ci);
		if (login_status_code != null && !"".equals(login_status_code))
			param.put("login_status_code", login_status_code);
		if (login_limit_sst_code != null && !"".equals(login_limit_sst_code))
			param.put("login_limit_sst_code", login_limit_sst_code);
		if (service_name != null && !"".equals(service_name))
			param.put("service_name", service_name);
		if (im_int_svc_no != null && !"".equals(im_int_svc_no))
			param.put("im_int_svc_no", im_int_svc_no);
		if (svc_mng_num != null && !"".equals(svc_mng_num))
			param.put("svc_mng_num", svc_mng_num);
		if (model_id != null && !"".equals(model_id))
			param.put("model_id", model_id);
		if (user_social_number != null && !"".equals(user_social_number))
			param.put("user_social_number", user_social_number);
		if (user_mdn_type != null && !"".equals(user_mdn_type))
			param.put("user_mdn_type", user_mdn_type);
		if (user_code != null && !"".equals(user_code))
			param.put("user_code", user_code);
		if (mobile_sign != null && !"".equals(mobile_sign))
			param.put("mobile_sign", mobile_sign);
		if (sign_data != null && !"".equals(sign_data))
			param.put("sign_data", sign_data);
		if (snAuthKey != null && !"".equals(snAuthKey))
			param.put("sn_auth_key", snAuthKey);
		if (rname_auth_mns_code != null && !"".equals(rname_auth_mns_code))
			param.put("rname_auth_mns_code", rname_auth_mns_code);
		if (rname_auth_sst_code != null && !"".equals(rname_auth_sst_code))
			param.put("rname_auth_sst_code", rname_auth_sst_code);
		if (is_biz_auth != null && !"".equals(is_biz_auth))
			param.put("is_biz_auth", is_biz_auth);
		if (user_ci != null && !"".equals(user_ci))
			param.put("user_ci", user_ci);
		if (user_di != null && !"".equals(user_di))
			param.put("user_di", user_di);
		if (udt_type_cd != null && !"".equals(udt_type_cd))
			param.put("udt_type_cd", udt_type_cd);
		if (rname_auth_date != null && !"".equals(rname_auth_date))
			param.put("rname_auth_date", rname_auth_date);
		if (parent_birthday != null && !"".equals(parent_birthday))
			param.put("parent_birthday", parent_birthday);
		if (rname_auth_type_cd != null && !"".equals(rname_auth_type_cd))
			param.put("rname_auth_type_cd", rname_auth_type_cd);
		if (mdn != null && !"".equals(mdn))
			param.put("mdn", mdn);
		if (join_sst_list != null && !"".equals(join_sst_list))
			param.put("join_sst_list", join_sst_list);

		Enumeration keys = param.keys();
		String paramKey = null;
		LOGGER.info("IDP SEND DATA -----------------------------------");
		LOGGER.info("url=" + sendData.getUrl());
		while ((keys != null) && keys.hasMoreElements()) {
			paramKey = (String) keys.nextElement();
			if ("user_social_number".equals(paramKey))
				LOGGER.info(paramKey + "= " + param.get(paramKey).substring(0, 6) + "*******");
			else
				LOGGER.info(paramKey + "= " + param.get(paramKey));
		}
		LOGGER.info("--------------------------------------------------");
		return param;

	}

	/**
	 * <pre>
	 * 이메일 중복 체크.
	 * </pre>
	 * 
	 * @param email
	 * @return @
	 */
	// @Override
	// public IDPReceiverM alredyJoinCheckByEmail(String email) {
	// return this.idpSCI.alredyJoinCheckByEmail(email, 1).getIdpReceiverM();
	// }
}
