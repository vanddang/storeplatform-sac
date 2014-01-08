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
import com.skplanet.storeplatform.sac.external.idp.IDPReceiver;
import com.skplanet.storeplatform.sac.external.idp.IDPSender;
import com.skplanet.storeplatform.sac.external.idp.vo.IDPReceiverM;
import com.skplanet.storeplatform.sac.external.idp.vo.IDPSenderM;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;

/**
 * 
 * IdpService IDP 연동 서비스 인터페이스(CoreStoreBusiness) 구현체
 * 
 * Updated on : 2014. 1. 2. Updated by : Jeon.ByungYoul, SK planet.
 */
@Service("IdpSacServiceImpl")
@Transactional
public class IdpSacServiceImpl implements IdpSacService {

	private static final Logger logger = LoggerFactory
			.getLogger(IdpSacServiceImpl.class);

	@Autowired
	private IDPSCI idpSCI;

	private static IDPSender idpSender = null;

	private static IDPReceiver idpReceiver = null;
	private static IDPReceiverM receivData = null;

	// -------------------------------------------------
	// 기본 API
	// -------------------------------------------------

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.skplanet.storeplatform.sac.external.idp.service.IdpSacServiceSample1Impl
	 * #alredyJoinCheckByEmail(java.lang .String)
	 */
	@Override
	public IDPReceiverM alredyJoinCheckByEmail(String email) throws Exception {

		logger.info("============ storeplatform-sac start ============");

		return this.alredyJoinCheck(email, IDP_PARAM_KEY_TYPE_EMAIL);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.skplanet.storeplatform.sac.external.idp.service.IdpSacServiceSample1Impl
	 * #alredyJoinCheckBySn(java.lang.String )
	 */
	@Override
	public IDPReceiverM alredyJoinCheckBySn(String ssn) throws Exception {

		return this.alredyJoinCheck(ssn, IDP_PARAM_KEY_TYPE_SN);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.skplanet.storeplatform.sac.external.idp.service.IdpSacServiceSample1Impl
	 * #alredyJoinCheck(java.lang.String, java.lang.String)
	 */
	@Override
	public IDPReceiverM alredyJoinCheck(String checkKey, String checkKeyType)
			throws Exception {

		IDPSenderM sendData = new IDPSenderM();

		sendData.setUrl(IDPSender.IDP_REQ_URL_JOIN);
		sendData.setCmd(IDPSender.IDP_REQ_CMD_ALEADY_JOIN_CHECK);
		sendData.setResp_type(IDP_PARAM_RESP_TYPE_XML);
		sendData.setResp_flow(IDP_PARAM_RESP_FLOW_RESPONSE);
		sendData.setKey(checkKey);
		sendData.setKey_type(checkKeyType);

		return this.sendIDP(sendData);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.skplanet.storeplatform.sac.external.idp.service.IdpSacServiceSample1Impl
	 * #checkDupID(java.lang.String)
	 */
	@Override
	public IDPReceiverM checkDupID(String id) throws Exception {

		IDPSenderM sendData = new IDPSenderM();

		sendData.setUrl(IDPSender.IDP_REQ_URL_JOIN);
		sendData.setCmd(IDPSender.IDP_REQ_CMD_DUPLICATE_ID_CHECK);
		sendData.setResp_type(IDP_PARAM_RESP_TYPE_XML);
		sendData.setResp_flow(IDP_PARAM_RESP_FLOW_RESPONSE);
		sendData.setUser_id(id);

		return this.sendIDP(sendData);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.skplanet.storeplatform.sac.external.idp.service.IdpSacServiceSample1Impl
	 * #realNameAuthForNative(java.lang. String, java.lang.String)
	 */
	@Override
	public IDPReceiverM realNameAuthForNative(String name, String ssn)
			throws Exception {
		return this.realNameAuth(IDP_PARAM_KEY_AUTH_TYPE_NATIVE, name, ssn);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.skplanet.storeplatform.sac.external.idp.service.IdpSacServiceSample1Impl
	 * #realNameAuthForForeign(java.lang .String, java.lang.String)
	 */
	@Override
	public IDPReceiverM realNameAuthForForeign(String name, String ssn)
			throws Exception {
		return this.realNameAuth(IDP_PARAM_KEY_AUTH_TYPE_FOREIGN, name, ssn);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.skplanet.storeplatform.sac.external.idp.service.IdpSacServiceSample1Impl
	 * #realNameAuth(java.lang.String, java.lang.String, java.lang.String)
	 */
	@Override
	public IDPReceiverM realNameAuth(String personType, String name, String ssn)
			throws Exception {

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

		sendData.setUrl(IDPSender.IDP_REQ_URL_REALNAME_AUTH);
		sendData.setCmd(IDPSender.IDP_REQ_CMD_REALNAME_AUTH);
		sendData.setResp_type(IDP_PARAM_RESP_TYPE_XML);
		sendData.setResp_flow(IDP_PARAM_RESP_FLOW_RESPONSE);
		sendData.setType(personType);
		sendData.setUser_name(name);
		sendData.setUser_social_number(ssn);

		return this.sendIDPHttps(sendData); // TODO 스테이징 반영 시 https
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.skplanet.storeplatform.sac.external.idp.service.IdpSacServiceSample1Impl
	 * #sendMobileAuthCode(java.lang.String)
	 */
	@Override
	public IDPReceiverM sendMobileAuthCode(String mdn) throws Exception {
		return this.sendMobileAuthCode(mdn, null, null);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.skplanet.storeplatform.sac.external.idp.service.IdpSacServiceSample1Impl
	 * #sendMobileAuthCode(java.lang.String, java.lang.String)
	 */
	@Override
	public IDPReceiverM sendMobileAuthCode(String mdn, String telType)
			throws Exception {
		return this.sendMobileAuthCode(mdn, telType, null);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.skplanet.storeplatform.sac.external.idp.service.IdpSacServiceSample1Impl
	 * #sendMobileAuthCode(java.lang.String, java.lang.String, java.lang.String)
	 */
	@Override
	public IDPReceiverM sendMobileAuthCode(String mdn, String telType,
			String ssn) throws Exception {
		IDPSenderM sendData = new IDPSenderM();

		sendData.setUrl(IDPSender.IDP_REQ_URL_MOBILE_AUTH);
		sendData.setCmd(IDPSender.IDP_REQ_CMD_MOBILE_AUTHCODE_SEND);
		sendData.setResp_type(IDP_PARAM_RESP_TYPE_XML);
		sendData.setResp_flow(IDP_PARAM_RESP_FLOW_RESPONSE);
		sendData.setUser_mdn(mdn);

		if (telType != null)
			sendData.setUser_mdn_type(telType);

		if (ssn != null)
			sendData.setUser_social_number(ssn);

		return this.sendIDP(sendData);

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.skplanet.storeplatform.sac.external.idp.service.IdpSacServiceSample1Impl
	 * #mobileAuth(java.lang.String, java.lang.String, java.lang.String,
	 * java.lang.String)
	 */
	@Override
	public IDPReceiverM mobileAuth(String mdn, String authCode,
			String mobileSign, String signData) throws Exception {
		IDPSenderM sendData = new IDPSenderM();

		sendData.setUrl(IDPSender.IDP_REQ_URL_MOBILE_AUTH);
		sendData.setCmd(IDPSender.IDP_REQ_CMD_MOBILE_AUTH);
		sendData.setResp_type(IDP_PARAM_RESP_TYPE_XML);
		sendData.setResp_flow(IDP_PARAM_RESP_FLOW_RESPONSE);
		sendData.setUser_mdn(mdn);
		sendData.setUser_code(authCode);
		sendData.setMobile_sign(mobileSign);
		sendData.setSign_data(signData);

		return this.sendIDPHttps(sendData); // TODO https
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.skplanet.storeplatform.sac.external.idp.service.IdpSacServiceSample1Impl
	 * #warterMarkImageUrl()
	 */
	@Override
	public IDPReceiverM warterMarkImageUrl() throws Exception {
		IDPSenderM sendData = new IDPSenderM();

		sendData.setUrl(IDPSender.IDP_REQ_URL_WATERMARK_AUTH);
		sendData.setCmd(IDPSender.IDP_REQ_CMD_WATERMARK_AUTH_IMAGE);
		sendData.setResp_type(IDP_PARAM_RESP_TYPE_XML);
		sendData.setResp_flow(IDP_PARAM_RESP_FLOW_RESPONSE);

		return this.sendIDP(sendData);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.skplanet.storeplatform.sac.external.idp.service.IdpSacServiceSample1Impl
	 * #warterMarkAuth(java.lang.String, java.lang.String, java.lang.String)
	 */
	@Override
	public IDPReceiverM warterMarkAuth(String authCode, String imageSign,
			String signData) throws Exception {
		IDPSenderM sendData = new IDPSenderM();

		sendData.setUrl(IDPSender.IDP_REQ_URL_WATERMARK_AUTH);
		sendData.setCmd(IDPSender.IDP_REQ_CMD_WATERMARK_AUTH);
		sendData.setResp_type(IDP_PARAM_RESP_TYPE_XML);
		sendData.setResp_flow(IDP_PARAM_RESP_FLOW_RESPONSE);
		sendData.setUser_code(authCode);
		sendData.setImage_sign(imageSign);
		sendData.setSign_data(signData);

		return this.sendIDPHttps(sendData); // TODO https
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.skplanet.storeplatform.sac.external.idp.service.IdpSacServiceSample1Impl
	 * #authNateId(java.lang.String, java.lang.String)
	 */
	@Override
	public IDPReceiverM authNateId(String nateId, String natePwd)
			throws Exception {
		IDPSenderM sendData = new IDPSenderM();

		sendData.setUrl(IDPSender.IDP_REQ_URL_NATEID_AUTH);
		sendData.setCmd(IDPSender.IDP_REQ_CMD_NATEID_AUTH);
		sendData.setResp_type(IDP_PARAM_RESP_TYPE_XML);
		sendData.setResp_flow(IDP_PARAM_RESP_FLOW_RESPONSE);
		sendData.setUser_id(nateId);
		sendData.setUser_passwd(natePwd);

		return this.sendIDPHttps(sendData); // TODO https
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.skplanet.storeplatform.sac.external.idp.service.IdpSacServiceSample1Impl
	 * #authPwd(java.lang.String, java.lang.String)
	 */
	@Override
	public IDPReceiverM authPwd(String id, String pwd) throws Exception {
		IDPSenderM sendData = new IDPSenderM();

		sendData.setUrl(IDPSender.IDP_REQ_URL_USER_AUTH);
		sendData.setCmd(IDPSender.IDP_REQ_CMD_AUTH_FOR_PWD);
		sendData.setResp_type(IDP_PARAM_RESP_TYPE_XML);
		sendData.setResp_flow(IDP_PARAM_RESP_FLOW_RESPONSE);
		sendData.setUser_id(id);
		sendData.setUser_passwd(pwd);

		return this.sendIDPHttps(sendData);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.skplanet.storeplatform.sac.external.idp.service.IdpSacServiceSample1Impl
	 * #authMdn(java.lang.String, java.lang.String, java.lang.String,
	 * java.lang.String)
	 */
	@Override
	public IDPReceiverM authMdn(String user_mdn, String user_code,
			String mobile_sign, String sign_data) throws Exception {
		IDPSenderM sendData = new IDPSenderM();

		sendData.setUrl(IDPSender.IDP_REQ_URL_USER_AUTH);
		sendData.setCmd(IDPSender.IDP_REQ_CMD_AUTH_FOR_MDN);
		sendData.setResp_type(IDP_PARAM_RESP_TYPE_XML);
		sendData.setResp_flow(IDP_PARAM_RESP_FLOW_RESPONSE);
		sendData.setUser_mdn(user_mdn);
		sendData.setUser_code(user_code);
		sendData.setMobile_sign(mobile_sign);
		sendData.setSign_data(sign_data);

		return this.sendIDPHttps(sendData);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.skplanet.storeplatform.sac.external.idp.service.IdpSacServiceSample1Impl
	 * #getEmailAuthLink(java.lang.String)
	 */
	@Override
	public IDPReceiverM getEmailAuthLink(String id) throws Exception {
		IDPSenderM sendData = new IDPSenderM();

		sendData.setUrl(IDPSender.IDP_REQ_URL_JOIN);
		sendData.setCmd(IDPSender.IDP_REQ_CMD_GET_EMAIL_AUTH);
		sendData.setResp_type(IDP_PARAM_RESP_TYPE_XML);
		sendData.setResp_flow(IDP_PARAM_RESP_FLOW_RESPONSE);
		sendData.setUser_id(id);

		return this.sendIDP(sendData);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.skplanet.storeplatform.sac.external.idp.service.IdpSacServiceSample1Impl
	 * #getEmailAuthLink(java.lang.String, java.lang.String)
	 */
	@Override
	public IDPReceiverM getEmailAuthLink(String user_auth_key, String id)
			throws Exception {

		IDPSenderM sendData = new IDPSenderM();
		// sendData.setSp_id(Constants.OMP_IDP_SP_ID); //
		sendData.setSp_auth_key(user_auth_key);
		sendData.setUrl(IDPSender.IDP_REQ_URL_JOIN);
		sendData.setCmd(IDPSender.IDP_REQ_CMD_GET_EMAIL_AUTH);
		sendData.setResp_type(IDP_PARAM_RESP_TYPE_XML);
		sendData.setResp_flow(IDP_PARAM_RESP_FLOW_RESPONSE);
		sendData.setUser_id(id);
		return this.sendIDPHttps(sendData);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.skplanet.storeplatform.sac.external.idp.service.IdpSacServiceSample1Impl
	 * #modifyEmail4JoinWait(java.lang.String , java.lang.String,
	 * java.lang.String, java.lang.String)
	 */
	@Override
	public IDPReceiverM modifyEmail4JoinWait(String preEmail, String email,
			String pwd, String userKey) throws Exception {

		IDPSenderM sendData = new IDPSenderM();

		sendData.setUrl(IDPSender.IDP_REQ_URL_USER_INFO_MODIFY);
		sendData.setCmd(IDPSender.IDP_REQ_CMD_MODIFY_EMAIL);
		sendData.setResp_type(IDP_PARAM_RESP_TYPE_XML);
		sendData.setResp_flow(IDP_PARAM_RESP_FLOW_RESPONSE);
		sendData.setPre_user_email(preEmail);
		sendData.setUser_email(email);
		sendData.setUser_passwd(pwd);
		sendData.setUser_key(userKey);

		return this.sendIDPHttps(sendData); // TODO https 로 바꾸기
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.skplanet.storeplatform.sac.external.idp.service.IdpSacServiceSample1Impl
	 * #modifyAuthInfo(java.lang.String, java.lang.String, java.lang.String)
	 */
	@Override
	public IDPReceiverM modifyAuthInfo(String user_auth_key, String key_type,
			String key) throws Exception {
		IDPSenderM sendData = new IDPSenderM();
		sendData.setUrl(IDPSender.IDP_REQ_URL_USER_INFO_MODIFY);
		sendData.setCmd(IDPSender.IDP_REQ_CMD_MODIFY_AUTH_INFO);
		sendData.setResp_type(IDP_PARAM_RESP_TYPE_XML);
		sendData.setResp_flow(IDP_PARAM_RESP_FLOW_RESPONSE);

		sendData.setUser_auth_key(user_auth_key);
		sendData.setKey_type(key_type);
		sendData.setKey(key);

		return this.sendIDPHttps(sendData); // TODO https 로 바꾸기
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.skplanet.storeplatform.sac.external.idp.service.IdpSacServiceSample1Impl
	 * #modifyEmailAuthInfo(java.lang.String , java.lang.String,
	 * java.lang.String, java.lang.String, java.lang.String)
	 */
	@Override
	public IDPReceiverM modifyEmailAuthInfo(String sp_auth_key,
			String user_auth_key, String user_key, String pre_key, String key)
			throws Exception {

		IDPSenderM sendData = new IDPSenderM();
		// sendData.setSp_id(Constants.OMP_IDP_SP_ID); //prop
		sendData.setUrl(IDPSender.IDP_REQ_URL_USER_INFO_MODIFY);
		sendData.setCmd(IDPSender.IDP_REQ_CMD_MODIFY_AUTH_INFO);
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

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.skplanet.storeplatform.sac.external.idp.service.IdpSacServiceSample1Impl
	 * #modifyPasswordAuthInfo(java.lang .String, java.lang.String,
	 * java.lang.String, java.lang.String)
	 */
	@Override
	public IDPReceiverM modifyPasswordAuthInfo(String user_auth_key,
			String user_key, String pre_key, String key) throws Exception {

		IDPSenderM sendData = new IDPSenderM();
		// sendData.setSp_id(Constants.OMP_IDP_SP_ID);
		sendData.setUrl(IDPSender.IDP_REQ_URL_USER_INFO_MODIFY);
		sendData.setCmd(IDPSender.IDP_REQ_CMD_MODIFY_AUTH_INFO);
		sendData.setResp_type(IDP_PARAM_RESP_TYPE_XML);
		sendData.setResp_flow(IDP_PARAM_RESP_FLOW_RESPONSE);
		sendData.setUser_key(user_key);
		sendData.setUser_auth_key(user_auth_key);
		sendData.setKey_type("2"); // password
		sendData.setKey(key);
		sendData.setPre_key(pre_key);

		return this.sendIDPHttps(sendData); // TODO https 로 바꾸기
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.skplanet.storeplatform.sac.external.idp.service.IdpSacServiceSample1Impl
	 * #makePhoneAuthKey(java.lang.String)
	 */
	@Override
	public String makePhoneAuthKey(String phoneMeta) throws Exception {
		return this.idpSender.makePhoneAuthKey(phoneMeta);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.skplanet.storeplatform.sac.external.idp.service.IdpSacServiceSample1Impl
	 * #makeSnAuthKey(java.lang.String, java.lang.String)
	 */
	@Override
	public String makeSnAuthKey(String mbrNm, String userId) throws Exception {
		return this.idpSender.makeSnAuthKey(mbrNm, userId);
	}

	// -------------------------------------------------
	// 회원 가입 API (IDP_REQ_CMD_JOIN_FOR_EMAIL)
	// -------------------------------------------------
	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.skplanet.storeplatform.sac.external.idp.service.IdpSacServiceSample1Impl
	 * #joinMember(java.util.Map)
	 */
	@Override
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
		sendData.setUrl(IDPSender.IDP_REQ_URL_JOIN);
		sendData.setCmd(IDPSender.IDP_REQ_CMD_JOIN_FOR_EMAIL);
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
		// "Y".equals(isRnameAuth)))sendData.setSn_auth_key(makeSnAuthKey(userName,
		// userId));
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
	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.skplanet.storeplatform.sac.external.idp.service.IdpSacServiceSample1Impl
	 * #userAuthForId(java.lang.String, java.lang.String)
	 */
	@Override
	public IDPReceiverM userAuthForWap(String userMdn) throws Exception {

		IDPSenderM sendData = new IDPSenderM();

		sendData.setUrl(IDPSender.IDP_REQ_URL_USER_AUTH);
		sendData.setCmd(IDPSender.IDP_REQ_CMD_AUTH_FOR_WAP);
		sendData.setResp_type(IDP_PARAM_RESP_TYPE_XML);
		sendData.setResp_flow(IDP_PARAM_RESP_FLOW_RESPONSE);
		sendData.setUser_mdn(userMdn);

		return this.sendIDP(sendData);
	}

	// -------------------------------------------------
	// 비 실명 회원 인증 API (redirect)
	// -------------------------------------------------
	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.skplanet.storeplatform.sac.external.idp.service.IdpSacServiceSample1Impl
	 * #userAuthForId(java.lang.String, java.lang.String)
	 */
	@Override
	public IDPReceiverM userAuthForId(String userId, String userPwd)
			throws Exception {

		IDPSenderM sendData = new IDPSenderM();

		sendData.setUrl(IDPSender.IDP_REQ_URL_USER_AUTH);
		sendData.setCmd(IDPSender.IDP_REQ_CMD_AUTH_FOR_ID);
		sendData.setResp_type(IDP_PARAM_RESP_TYPE_XML);
		sendData.setResp_flow(IDP_PARAM_RESP_FLOW_RESPONSE);
		sendData.setUser_id(userId);
		sendData.setUser_passwd(userPwd);

		return this.sendIDP(sendData);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.skplanet.storeplatform.sac.external.idp.service.IdpSacServiceSample1Impl
	 * #userLogoutForSSO()
	 */
	@Override
	public IDPReceiverM userLogoutForSSO() throws Exception {

		IDPSenderM sendData = new IDPSenderM();

		sendData.setUrl(IDPSender.IDP_REQ_URL_USER_AUTH);
		sendData.setCmd(IDPSender.IDP_REQ_CMD_LOGOUT_FOR_SSO);
		sendData.setResp_type(IDP_PARAM_RESP_TYPE_XML);
		sendData.setResp_flow(IDP_PARAM_RESP_FLOW_RESPONSE);

		return this.sendIDPHttps(sendData);
	}

	// -------------------------------------------------
	// 회원 정보 조회 API
	// -------------------------------------------------

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.skplanet.storeplatform.sac.external.idp.service.IdpSacServiceSample1Impl
	 * #searchUserCommonInfoByUserKey(java .lang.String, java.lang.String)
	 */
	@Override
	public IDPReceiverM searchUserCommonInfoByUserKey(String userAuthKey,
			String userKey) throws Exception {

		return this.searchUserCommonInfo(userAuthKey,
				IDP_PARAM_KEY_QUERY_KEY_TYPE_USER_KEY, userKey);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.skplanet.storeplatform.sac.external.idp.service.IdpSacServiceSample1Impl
	 * #searchUserCommonInfoByID(java.lang .String, java.lang.String)
	 */
	@Override
	public IDPReceiverM searchUserCommonInfoByID(String userAuthKey,
			String userID) throws Exception {

		return this.searchUserCommonInfo(userAuthKey,
				IDP_PARAM_KEY_QUERY_KEY_TYPE_ID, userID);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.skplanet.storeplatform.sac.external.idp.service.IdpSacServiceSample1Impl
	 * #searchUserCommonInfoByEmail(java .lang.String, java.lang.String)
	 */
	@Override
	public IDPReceiverM searchUserCommonInfoByEmail(String userAuthKey,
			String email) throws Exception {

		return this.searchUserCommonInfo(userAuthKey,
				IDP_PARAM_KEY_QUERY_KEY_TYPE_EMAIL, email);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.skplanet.storeplatform.sac.external.idp.service.IdpSacServiceSample1Impl
	 * #searchUserCommonInfo(java.lang.String , java.lang.String,
	 * java.lang.String)
	 */
	@Override
	public IDPReceiverM searchUserCommonInfo(String userAuthKey,
			String queryKeyType, String queryKeyValue) throws Exception {

		IDPSenderM sendData = new IDPSenderM();

		sendData.setUrl(IDPSender.IDP_REQ_URL_USER_INFO_SEARCH);
		sendData.setCmd(IDPSender.IDP_REQ_CMD_FIND_COMMON_PROFILE_FOR_SERVER);
		sendData.setResp_type(IDP_PARAM_RESP_TYPE_XML);
		sendData.setResp_flow(IDP_PARAM_RESP_FLOW_RESPONSE);
		sendData.setUser_auth_key(userAuthKey);
		sendData.setKey_type(queryKeyType);
		sendData.setKey(queryKeyValue);

		return this.sendIDPHttps(sendData); // TODO https
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.skplanet.storeplatform.sac.external.idp.service.IdpSacServiceSample1Impl
	 * #searchUserCommonInfo4SPServer(java .lang.String, java.lang.String)
	 */
	@Override
	public IDPReceiverM searchUserCommonInfo4SPServer(String keyType, String key)
			throws Exception {
		IDPSenderM sendData = new IDPSenderM();

		sendData.setUrl(IDPSender.IDP_REQ_URL_USER_INFO_SEARCH);
		sendData.setCmd(IDPSender.IDP_REQ_CMD_FIND_COMMON_PROFILE_FOR_SERVER);
		sendData.setResp_type(IDP_PARAM_RESP_TYPE_XML);
		sendData.setResp_flow(IDP_PARAM_RESP_FLOW_RESPONSE);
		sendData.setKey_type(keyType);
		sendData.setKey(key);

		return this.sendIDP(sendData); // TODO 스테이징 반영 시 https
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.skplanet.storeplatform.sac.external.idp.service.IdpSacServiceSample1Impl
	 * #searchUserIDByEmail(java.lang.String , java.lang.String,
	 * java.lang.String, java.lang.String)
	 */
	@Override
	public IDPReceiverM searchUserIDByEmail(String email, String userCode,
			String imageSign, String signData) throws Exception {

		return this.searchUserID(IDP_PARAM_KEY_QUERY_ID_KEY_TYPE_EMAIL, email,
				userCode, imageSign, signData);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.skplanet.storeplatform.sac.external.idp.service.IdpSacServiceSample1Impl
	 * #searchUserIDBySN(java.lang.String)
	 */
	@Override
	public IDPReceiverM searchUserIDBySN(String ssn) throws Exception {

		return this.searchUserID(IDP_PARAM_KEY_QUERY_ID_KEY_TYPE_SN, ssn, null,
				null, null);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.skplanet.storeplatform.sac.external.idp.service.IdpSacServiceSample1Impl
	 * #searchUserIDByUserKey(java.lang. String, java.lang.String,
	 * java.lang.String, java.lang.String)
	 */
	@Override
	public IDPReceiverM searchUserIDByUserKey(String userKey, String userCode,
			String imageSign, String signData) throws Exception {

		return this.searchUserID(IDP_PARAM_KEY_QUERY_ID_KEY_TYPE_USERKEY,
				userKey, userCode, imageSign, signData);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.skplanet.storeplatform.sac.external.idp.service.IdpSacServiceSample1Impl
	 * #searchUserID(java.lang.String, java.lang.String, java.lang.String,
	 * java.lang.String, java.lang.String)
	 */
	@Override
	public IDPReceiverM searchUserID(String queryKeyType, String queryKeyValue,
			String userCode, String imageSign, String signData)
			throws Exception {

		IDPSenderM sendData = new IDPSenderM();

		sendData.setUrl(IDPSender.IDP_REQ_URL_USER_INFO_SEARCH);
		sendData.setCmd(IDPSender.IDP_REQ_CMD_FIND_ID);
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

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.skplanet.storeplatform.sac.external.idp.service.IdpSacServiceSample1Impl
	 * #searchUserID(java.lang.String, java.lang.String)
	 */
	@Override
	public IDPReceiverM searchUserID(String queryKeyType, String queryKeyValue)
			throws Exception {

		IDPSenderM sendData = new IDPSenderM();

		sendData.setUrl(IDPSender.IDP_REQ_URL_USER_INFO_SEARCH);
		sendData.setCmd(IDPSender.IDP_REQ_CMD_FIND_ID);
		sendData.setResp_type(IDP_PARAM_RESP_TYPE_XML);
		sendData.setResp_flow(IDP_PARAM_RESP_FLOW_RESPONSE);
		sendData.setKey_type(queryKeyType);
		sendData.setKey(queryKeyValue);
		sendData.setWatermark_auth(IDP_PARAM_KEY_WATERMARK_AUTH_NON_INCLISION);

		return this.sendIDP(sendData);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.skplanet.storeplatform.sac.external.idp.service.IdpSacServiceSample1Impl
	 * #findPwdByEmail(java.lang.String)
	 */
	@Override
	public IDPReceiverM findPwdByEmail(String email) throws Exception {
		return this.findPwd(IDP_PARAM_KEY_QUERY_PWD_KEY_TYPE_EMAIL, email);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.skplanet.storeplatform.sac.external.idp.service.IdpSacServiceSample1Impl
	 * #findPwdByID(java.lang.String)
	 */
	@Override
	public IDPReceiverM findPwdByID(String userID) throws Exception {
		return this.findPwd(IDP_PARAM_KEY_QUERY_PWD_KEY_TYPE_ID, userID);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.skplanet.storeplatform.sac.external.idp.service.IdpSacServiceSample1Impl
	 * #findPwdByID(java.lang.String, java.lang.String, java.lang.String,
	 * java.lang.String)
	 */
	@Override
	public IDPReceiverM findPwdByID(String userID, String userCode,
			String imageSign, String signData) throws Exception {
		return this.findPwd(IDP_PARAM_KEY_QUERY_PWD_KEY_TYPE_ID, userID,
				userCode, imageSign, signData);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.skplanet.storeplatform.sac.external.idp.service.IdpSacServiceSample1Impl
	 * #findPwdByUserKey(java.lang.String)
	 */
	@Override
	public IDPReceiverM findPwdByUserKey(String userKey) throws Exception {
		return this.findPwd(IDP_PARAM_KEY_QUERY_PWD_KEY_TYPE_USERKEY, userKey);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.skplanet.storeplatform.sac.external.idp.service.IdpSacServiceSample1Impl
	 * #findPwd(java.lang.String, java.lang.String)
	 */
	@Override
	public IDPReceiverM findPwd(String queryKeyType, String queryKeyValue)
			throws Exception {
		return this.findPwd(queryKeyType, queryKeyValue, null, null, null);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.skplanet.storeplatform.sac.external.idp.service.IdpSacServiceSample1Impl
	 * #findPwd(java.lang.String, java.lang.String, java.lang.String,
	 * java.lang.String, java.lang.String)
	 */
	@Override
	public IDPReceiverM findPwd(String queryKeyType, String queryKeyValue,
			String userCode, String imageSign, String signData)
			throws Exception {

		IDPSenderM sendData = new IDPSenderM();

		sendData.setUrl(IDPSender.IDP_REQ_URL_USER_INFO_SEARCH);
		sendData.setCmd(IDPSender.IDP_REQ_CMD_FIND_PWD);
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

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.skplanet.storeplatform.sac.external.idp.service.IdpSacServiceSample1Impl
	 * #secedeUserByID(java.lang.String, java.lang.String)
	 */
	@Override
	public IDPReceiverM secedeUserByID(String userAuthKey, String userID)
			throws Exception {
		return this.secedeUser(userAuthKey, IDP_PARAM_KEY_SECEDE_KEY_TYPE_ID,
				userID);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.skplanet.storeplatform.sac.external.idp.service.IdpSacServiceSample1Impl
	 * #secedeUserByUserKey(java.lang.String , java.lang.String)
	 */
	@Override
	public IDPReceiverM secedeUserByUserKey(String userAuthKey, String userKey)
			throws Exception {
		return this.secedeUser(userAuthKey,
				IDP_PARAM_KEY_SECEDE_KEY_TYPE_USERKEY, userKey);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.skplanet.storeplatform.sac.external.idp.service.IdpSacServiceSample1Impl
	 * #secedeUser(java.lang.String, java.lang.String, java.lang.String)
	 */
	@Override
	public IDPReceiverM secedeUser(String userAuthKey, String secedeKeyType,
			String secedeKeyValue) throws Exception {

		IDPSenderM sendData = new IDPSenderM();

		sendData.setUrl(IDPSender.IDP_REQ_URL_SECEDE);
		sendData.setCmd(IDPSender.IDP_REQ_CMD_SECEDE_USER);
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

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.skplanet.storeplatform.sac.external.idp.service.IdpSacServiceSample1Impl
	 * #aleadyJoinCheck4Mdn(java.lang.String )
	 */
	@Override
	public IDPReceiverM aleadyJoinCheck4Mdn(String mdn) throws Exception {

		IDPSenderM sendData = new IDPSenderM();

		sendData.setUrl(IDPSender.IDP_REQ_URL_JOIN);
		sendData.setCmd(IDPSender.IDP_REQ_CMD_ALEADY_JOIN_MDN);
		sendData.setResp_type(IDP_PARAM_RESP_TYPE_XML);
		sendData.setResp_flow(IDP_PARAM_RESP_FLOW_RESPONSE);
		sendData.setUser_mdn(mdn);

		return this.sendIDP(sendData);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.skplanet.storeplatform.sac.external.idp.service.IdpSacServiceSample1Impl
	 * #authForWap(java.lang.String)
	 */
	@Override
	public IDPReceiverM authForWap(String mdn) throws Exception {

		IDPSenderM sendData = new IDPSenderM();

		sendData.setUrl(IDPSender.IDP_REQ_URL_USER_AUTH);
		sendData.setCmd(IDPSender.IDP_REQ_CMD_AUTH_FOR_WAP);
		sendData.setResp_type(IDP_PARAM_RESP_TYPE_XML);
		sendData.setResp_flow(IDP_PARAM_RESP_FLOW_RESPONSE);
		sendData.setUser_mdn(mdn);

		return this.sendIDP(sendData);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.skplanet.storeplatform.sac.external.idp.service.IdpSacServiceSample1Impl
	 * #join4Wap(java.lang.String)
	 */
	@Override
	public IDPReceiverM join4Wap(String mdn) throws Exception {

		IDPSenderM sendData = new IDPSenderM();

		sendData.setUrl(IDPSender.IDP_REQ_URL_JOIN);
		sendData.setCmd(IDPSender.IDP_REQ_CMD_JOIN_FOR_WAP);
		sendData.setResp_type(IDP_PARAM_RESP_TYPE_XML);
		sendData.setResp_flow(IDP_PARAM_RESP_FLOW_RESPONSE);
		sendData.setMdn_corp(IDP_PARAM_KEY_USER_MDN_TYPE_SKT);
		sendData.setUser_mdn(mdn);

		return this.sendIDP(sendData);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.skplanet.storeplatform.sac.external.idp.service.IdpSacServiceSample1Impl
	 * #secedeUser4Wap(java.lang.String)
	 */
	@Override
	public IDPReceiverM secedeUser4Wap(String mdn) throws Exception {

		IDPSenderM sendData = new IDPSenderM();

		sendData.setUrl(IDPSender.IDP_REQ_URL_SECEDE);
		sendData.setCmd(IDPSender.IDP_REQ_CMD_SECEDE_FOR_WAP);
		sendData.setResp_type(IDP_PARAM_RESP_TYPE_XML);
		sendData.setResp_flow(IDP_PARAM_RESP_FLOW_RESPONSE);
		sendData.setUser_mdn(mdn);

		return this.sendIDP(sendData);
	}

	// -------------------------------------------------
	// 부가서비스 API
	// -------------------------------------------------

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.skplanet.storeplatform.sac.external.idp.service.IdpSacServiceSample1Impl
	 * #joinSupService(java.lang.String, java.lang.String)
	 */
	@Override
	public IDPReceiverM joinSupService(String mdn, String svcCode)
			throws Exception {
		return this.joinSupService(mdn, svcCode, null);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.skplanet.storeplatform.sac.external.idp.service.IdpSacServiceSample1Impl
	 * #joinSupService(java.lang.String, java.lang.String, java.lang.String)
	 */
	@Override
	public IDPReceiverM joinSupService(String mdn, String svcCd,
			String svcMngNum) throws Exception {

		IDPSenderM sendData = new IDPSenderM();

		sendData.setUrl(IDPSender.IDP_REQ_URL_JOIN);
		sendData.setCmd(IDPSender.IDP_REQ_CMD_JOIN_SUP_SERVICE);
		sendData.setResp_type(IDP_PARAM_RESP_TYPE_XML);
		sendData.setResp_flow(IDP_PARAM_RESP_FLOW_RESPONSE);
		sendData.setUser_mdn(mdn);
		sendData.setSvc_code(svcCd);

		if (svcMngNum != null && svcMngNum.length() > 0)
			sendData.setUser_svc_mng_num(svcMngNum);

		return this.sendIDP(sendData);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.skplanet.storeplatform.sac.external.idp.service.IdpSacServiceSample1Impl
	 * #secedeSupService(java.lang.String, java.lang.String)
	 */
	@Override
	public IDPReceiverM secedeSupService(String mdn, String svcCode)
			throws Exception {
		return this.secedeSupService(mdn, svcCode, null);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.skplanet.storeplatform.sac.external.idp.service.IdpSacServiceSample1Impl
	 * #secedeSupService(java.lang.String, java.lang.String, java.lang.String)
	 */
	@Override
	public IDPReceiverM secedeSupService(String mdn, String svcCd,
			String svcMngNum) throws Exception {

		IDPSenderM sendData = new IDPSenderM();

		sendData.setUrl(IDPSender.IDP_REQ_URL_SECEDE);
		sendData.setCmd(IDPSender.IDP_REQ_CMD_SECEDE_SUP_SERVICE);
		sendData.setResp_type(IDP_PARAM_RESP_TYPE_XML);
		sendData.setResp_flow(IDP_PARAM_RESP_FLOW_RESPONSE);
		sendData.setUser_mdn(mdn);
		sendData.setSvc_code(svcCd);

		if (svcMngNum != null && svcMngNum.length() > 0)
			sendData.setUser_svc_mng_num(svcMngNum);

		return this.sendIDP(sendData);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.skplanet.storeplatform.sac.external.idp.service.IdpSacServiceSample1Impl
	 * #tmapServiceAvalibleCheck(java.lang .String)
	 */
	@Override
	public IDPReceiverM tmapServiceAvalibleCheck(String mdn) throws Exception {

		IDPSenderM sendData = new IDPSenderM();

		sendData.setUrl(IDPSender.IDP_REQ_URL_USER_INFO_SEARCH);
		sendData.setCmd(IDPSender.IDP_REQ_CMD_TMAP_SERVICE_AVALIBLE_CHECK);
		sendData.setResp_type(IDP_PARAM_RESP_TYPE_XML);
		sendData.setResp_flow(IDP_PARAM_RESP_FLOW_RESPONSE);
		sendData.setUser_mdn(mdn);

		return this.sendIDP(sendData);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.skplanet.storeplatform.sac.external.idp.service.IdpSacServiceSample1Impl
	 * #tmapServiceCheck(java.lang.String, java.lang.String)
	 */
	@Override
	public IDPReceiverM tmapServiceCheck(String mdn, String svcCd)
			throws Exception {

		IDPSenderM sendData = new IDPSenderM();

		sendData.setUrl(IDPSender.IDP_REQ_URL_USER_INFO_SEARCH);
		sendData.setCmd(IDPSender.IDP_REQ_CMD_TMAP_SERVICE_CHECK);
		sendData.setResp_type(IDP_PARAM_RESP_TYPE_XML);
		sendData.setResp_flow(IDP_PARAM_RESP_FLOW_RESPONSE);
		sendData.setUser_mdn(mdn);
		sendData.setSvc_code(svcCd);

		return this.sendIDP(sendData);
	}

	// ---------------------------------------------------------------------------------------------------------

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.skplanet.storeplatform.sac.external.idp.service.IdpSacServiceSample1Impl
	 * #sendIDP(com.skplanet.storeplatform
	 * .sac.external.idptemp.model.IDPSenderM)
	 */
	public IDPReceiverM sendIDP(IDPSenderM sendData) throws Exception {

		// AS-IS 로직 삭제
		// StopWatch stopWatch = new StopWatch();

		// stopWatch.start();

		Hashtable param = this.idpSender.makeSendParam(sendData);

		// TO-BE 기능을 위한 로직 추가
		IDPTxReq req = new IDPTxReq();
		req.setHttpMethod("POST");
		req.setHttpProtocal("HTTPS");
		req.setReqUrl(sendData.getUrl());
		req.setReqParamHtable(param);

		IDPTxRes res = this.idpSCI.send(req);
		String responseMsg = res.getResXml();
		// AS-IS 로직 삭제
		// String responseMsg =
		// sendHttp(this.idpSender.idpReqUrl(sendData.getUrl()), param);

		logger.info("HTTP IDP 연동 결과 [XML] ::::   " + responseMsg);

		IDPReceiverM receiveData = this.resultIDPFromXml(responseMsg);

		// stopWatch.stop();

		// IDPOMCLog.setLog(stopWatch, receiveData);

		return receiveData;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.skplanet.storeplatform.sac.external.idp.service.IdpSacServiceSample1Impl
	 * #sendTempIDP(com.skplanet.storeplatform
	 * .sac.external.idptemp.model.IDPSenderM)
	 */
	public IDPReceiverM sendTempIDP(IDPSenderM sendData) throws Exception {

		// AS-IS 로직 삭제
		// StopWatch stopWatch = new StopWatch();

		// stopWatch.start();

		Hashtable param = this.idpSender.makeSendParam(sendData);

		// TO-BE 기능을 위한 로직 추가
		IDPTxReq req = new IDPTxReq();
		req.setHttpMethod("POST");
		req.setHttpProtocal("HTTPS");
		req.setReqUrl(sendData.getUrl());
		req.setReqParamHtable(param);

		IDPTxRes res = this.idpSCI.send(req);
		String responseMsg = res.getResXml();
		// AS-IS 로직 삭제
		// String responseMsg = this.sendHttp("http://idp.innoace.com" +
		// sendData.getUrl(), param);

		logger.info("HTTP IDP 연동 결과 [XML] ::::   " + responseMsg);

		IDPReceiverM receiveData = this.resultIDPFromXml(responseMsg);

		// stopWatch.stop();

		return receiveData;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.skplanet.storeplatform.sac.external.idp.service.IdpSacServiceSample1Impl
	 * #sendIDPHttps(com.skplanet.storeplatform
	 * .sac.external.idptemp.model.IDPSenderM)
	 */
	@Override
	public IDPReceiverM sendIDPHttps(IDPSenderM sendData) throws Exception {

		// AS-IS 로직 삭제
		// StopWatch stopWatch = new StopWatch();
		//
		// stopWatch.start();

		Hashtable param = this.idpSender.makeSendParam(sendData);

		// TO-BE 기능을 위한 로직 추가
		IDPTxReq req = new IDPTxReq();
		req.setHttpMethod("POST");
		req.setHttpProtocal("HTTPS");
		req.setReqUrl(sendData.getUrl());
		req.setReqParamHtable(param);

		IDPTxRes res = this.idpSCI.send(req);
		String responseMsg = res.getResXml();
		// AS-IS 로직 삭제
		// String responseMsg =
		// this.sendHttps(this.idpSender.idpReqUrlHttps(sendData.getUrl()),
		// param);

		logger.info("HTTPS IDP 연동 결과 [XML] ::::   " + responseMsg);

		IDPReceiverM receiveData = this.resultIDPFromXml(responseMsg);

		// stopWatch.stop();

		return receiveData;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.skplanet.storeplatform.sac.external.idp.service.IdpSacServiceSample1Impl
	 * #sendHttp(java.lang.String, java.util.Hashtable)
	 */
	public String sendHttp(String url, Hashtable param) throws Exception {

		if (url == null || url.trim().length() == 0)
			throw new Exception("URL is null.");

		logger.info("idp request url : " + url);

		// AS-IS 로직 삭제
		// HTTPUtil hc = new HTTPUtil(url, Constants.HTTP_METHOD_POST, param);
		// hc.setEncoding("UTF-8");
		return null;
		// return hc.getContent(30000); //ext 커널 VO return : TODO
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.skplanet.storeplatform.sac.external.idp.service.IdpSacServiceSample1Impl
	 * #sendHttps(java.lang.String, java.util.Hashtable)
	 */
	public String sendHttps(String url, Hashtable param) throws Exception {

		if (url == null || url.trim().length() == 0)
			throw new Exception("URL is null.");

		logger.info("idp request url : " + url);
		// AS-IS 로직 삭제
		// HTTPUtil hc = new HTTPUtil(url, Constants.HTTP_METHOD_POST, param);
		// hc.setEncoding("UTF-8");
		return null;
		// return hc.getContent(30000); //ext 커널 VO return : TODO
	}

	/**
	 * IDP 연동 결과를 Parsing 하여 recevie 객체에 담는다.
	 * 
	 * @param data
	 * @return
	 * @throws Exception
	 */
	private IDPReceiverM resultIDPFromXml(String data) throws Exception {

		XStream xs = new XStream(new DomDriver());
		this.receivData = new IDPReceiverM();

		xs.alias("idpResponse", IDPReceiverM.class);

		xs.fromXML(data, this.receivData);

		this.receivData = this.idpReceiver.nullValue(this.receivData);

		if (null != this.receivData.getResponseBody()) {
			String idList = this.receivData.getResponseBody().getId_list();

			if (!"".equals(idList) && idList != null) {
				this.receivData.getResponseBody().setIdList(
						this.idpReceiver.tokenize(idList, "|"));
			}
		}

		return this.receivData;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.skplanet.storeplatform.sac.external.idp.service.IdpSacServiceSample1Impl
	 * #modifyProfile(java.util.Map)
	 */
	@Override
	public IDPReceiverM modifyProfile(Map<String, Object> param)
			throws Exception {

		IDPSenderM sendData = new IDPSenderM();

		sendData.setUrl(IDPSender.IDP_REQ_URL_USER_INFO_MODIFY);
		sendData.setCmd(IDPSender.IDP_REQ_CMD_MODIFY_PROFILE);
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
		 * if ("Y".equals(is_rname_auth)) { if (!"".equals(user_name) &&
		 * !"".equals(user_id)) { try { sn_auth_key = makeSnAuthKey(user_name,
		 * user_id); logger.info("sn_auth_key : "+sn_auth_key); } catch
		 * (Exception e) { // TODO Auto-generated catch block
		 * e.printStackTrace(); } } else { is_rname_auth = "N"; } }
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

	// ---------------------------------------------------------------------------------------------------------

	// IDP 통합 고도화 -------------------------------------------------------------
	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.skplanet.storeplatform.sac.external.idp.service.IdpSacServiceSample1Impl
	 * #otherChannelIdAuth(java.lang.String, java.lang.String)
	 */
	@Override
	public IDPReceiverM otherChannelIdAuth(String user_id, String user_passwd)
			throws Exception {

		IDPSenderM sendData = new IDPSenderM();

		sendData.setUrl(IDPSender.IDP_REQ_URL_USER_AUTH);
		sendData.setCmd(IDPSender.IDP_REQ_CMD_OTHER_CHANNEL_ID_AUTH);
		sendData.setResp_type(IDP_PARAM_RESP_TYPE_XML);
		sendData.setResp_flow(IDP_PARAM_RESP_FLOW_RESPONSE);
		sendData.setUser_id(user_id);
		sendData.setUser_passwd(user_passwd);

		return this.sendIDPHttps(sendData);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.skplanet.storeplatform.sac.external.idp.service.IdpSacServiceSample1Impl
	 * #otherChannelList(java.lang.String, java.lang.String)
	 */
	@Override
	public IDPReceiverM otherChannelList(String key_type, String key)
			throws Exception {

		IDPSenderM sendData = new IDPSenderM();

		sendData.setUrl(IDPSender.IDP_REQ_URL_USER_INFO_SEARCH);
		sendData.setCmd(IDPSender.IDP_REQ_CMD_OTHER_CHANNEL_LIST);
		sendData.setResp_type(IDP_PARAM_RESP_TYPE_XML);
		sendData.setResp_flow(IDP_PARAM_RESP_FLOW_RESPONSE);
		sendData.setKey_type(key_type);
		sendData.setKey(key);

		return this.sendIDP(sendData);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.skplanet.storeplatform.sac.external.idp.service.IdpSacServiceSample1Impl
	 * #otherChannelRegist(java.lang.String, java.lang.String)
	 */
	@Override
	public IDPReceiverM otherChannelRegist(String user_id, String user_passwd)
			throws Exception {

		IDPSenderM sendData = new IDPSenderM();

		sendData.setUrl(IDPSender.IDP_REQ_URL_JOIN);
		sendData.setCmd(IDPSender.IDP_REQ_CMD_OTHER_CHANNEL_REGIST);
		sendData.setResp_type(IDP_PARAM_RESP_TYPE_XML);
		sendData.setResp_flow(IDP_PARAM_RESP_FLOW_RESPONSE);
		sendData.setUser_id(user_id);
		sendData.setUser_passwd(user_passwd);
		// sendData.setApprove_sp_id(IDPManager.IDP_REQ_OMP_SERVICE_ID);
		return this.sendIDP(sendData);
	}

	// IDP 통합 고도화-skt폰 여부
	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.skplanet.storeplatform.sac.external.idp.service.IdpSacServiceSample1Impl
	 * #findModelId(java.lang.String)
	 */
	@Override
	public IDPReceiverM findModelId(String mdn) throws Exception {

		IDPSenderM sendData = new IDPSenderM();

		sendData.setUrl(IDPSender.IDP_REQ_URL_USER_INFO_SEARCH);
		sendData.setCmd(IDPSender.IDP_REQ_CMD_FIND_MODEL_ID);
		sendData.setResp_type(IDP_PARAM_RESP_TYPE_XML);
		sendData.setResp_flow(IDP_PARAM_RESP_FLOW_RESPONSE);
		sendData.setUser_mdn(mdn);

		return this.sendIDP(sendData);
	}

	// IDP 통합 고도화-SMS 발송
	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.skplanet.storeplatform.sac.external.idp.service.IdpSacServiceSample1Impl
	 * #mobileSend(java.lang.String, java.lang.String)
	 */
	@Override
	public IDPReceiverM mobileSend(String user_mdn, String comment)
			throws Exception {

		IDPSenderM sendData = new IDPSenderM();

		sendData.setUrl(IDPSender.IDP_REQ_URL_MOBILE_SEND);
		sendData.setCmd(IDPSender.IDP_REQ_CMD_MOBILE_SEND);
		sendData.setResp_type(IDP_PARAM_RESP_TYPE_XML);
		sendData.setResp_flow(IDP_PARAM_RESP_FLOW_RESPONSE);
		sendData.setUser_mdn(user_mdn);
		sendData.setComment(comment);

		return this.sendIDPHttps(sendData); // real Https
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.skplanet.storeplatform.sac.external.idp.service.IdpSacServiceSample1Impl
	 * #idpSmsSend(java.lang.String, java.lang.String, java.lang.String)
	 */
	@Override
	public IDPReceiverM idpSmsSend(String receiverMdn, String msg,
			String senderMdn) throws Exception {

		// TODO.. 현재는 보내는 사람 전화번호가 없음 .
		// 보내는 이 전화번호(senderMdn)가 들어오게 되면 아래 로직에 보내는 이 정보 추가 필요. 2011.10.13
		// jiaprk mod
		return this.mobileSend(receiverMdn, msg);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.skplanet.storeplatform.sac.external.idp.service.IdpSacServiceSample1Impl
	 * #joinSupServiceRequest(java.lang. String, java.lang.String)
	 */
	@Override
	public IDPReceiverM joinSupServiceRequest(String mdn, String svcCode)
			throws Exception {

		IDPSenderM sendData = new IDPSenderM();
		// 연동 코드 : NA00003492 (임시)티스토어정액제 / NA00003493 (실제)티스토어정액제

		sendData.setUrl(IDPSender.IDP_REQ_URL_JOIN);
		sendData.setCmd(IDPSender.IDP_REQ_CMD_JOIN_SUP_SERVICE_REQUEST);
		sendData.setResp_type(IDP_PARAM_RESP_TYPE_XML);
		sendData.setResp_flow(IDP_PARAM_RESP_FLOW_RESPONSE);
		sendData.setUser_mdn(mdn);
		sendData.setSvc_code(svcCode);

		return this.sendIDP(sendData);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.skplanet.storeplatform.sac.external.idp.service.IdpSacServiceSample1Impl
	 * #secedeSupServiceRequest(java.lang .String, java.lang.String)
	 */
	@Override
	public IDPReceiverM secedeSupServiceRequest(String mdn, String svcCode)
			throws Exception {

		IDPSenderM sendData = new IDPSenderM();
		// 연동 코드 : NA00003492 (임시)티스토어정액제 / NA00003493 (실제)티스토어정액제

		sendData.setUrl(IDPSender.IDP_REQ_URL_SECEDE);
		sendData.setCmd(IDPSender.IDP_REQ_CMD_SECEDE_SUP_SERVICE_REQUEST);
		sendData.setResp_type(IDP_PARAM_RESP_TYPE_XML);
		sendData.setResp_flow(IDP_PARAM_RESP_FLOW_RESPONSE);
		sendData.setUser_mdn(mdn);
		sendData.setSvc_code(svcCode);

		return this.sendIDP(sendData);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.skplanet.storeplatform.sac.external.idp.service.IdpSacServiceSample1Impl
	 * #findBill(java.lang.String)
	 */
	@Override
	public IDPReceiverM findBill(String mdn) throws Exception {

		IDPSenderM sendData = new IDPSenderM();
		// 연동 코드 : NA00003492 (임시)티스토어정액제 / NA00003493 (실제)티스토어정액제

		sendData.setUrl(IDPSender.IDP_REQ_URL_USER_INFO_SEARCH);
		sendData.setCmd(IDPSender.IDP_REQ_CMD_FIND_BILL);
		sendData.setResp_type(IDP_PARAM_RESP_TYPE_XML);
		sendData.setResp_flow(IDP_PARAM_RESP_FLOW_RESPONSE);
		sendData.setUser_mdn(mdn);

		return this.sendIDP(sendData);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.skplanet.storeplatform.sac.external.idp.service.IdpSacServiceSample1Impl
	 * #createToken(java.lang.String)
	 */
	@Override
	public IDPReceiverM createToken(String user_id) throws Exception {

		IDPSenderM sendData = new IDPSenderM();
		sendData.setUrl(IDPSender.IDP_REQ_URL_CREATE_TOKEN);
		sendData.setCmd(IDPSender.IDP_REQ_CMD_CREATE_TOKEN);
		sendData.setUser_id(user_id);
		sendData.setTarget_service("tstore");

		return this.sendIDP(sendData);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.skplanet.storeplatform.sac.external.idp.service.IdpSacServiceSample1Impl
	 * #mappingToken(java.lang.String)
	 */
	@Override
	public IDPReceiverM mappingToken(String user_id) throws Exception {

		IDPSenderM sendData = new IDPSenderM();
		sendData.setUrl(IDPSender.IDP_REQ_URL_MAPPING_TOKEN);
		sendData.setCmd(IDPSender.IDP_REQ_CMD_MAPPING_TOKEN);
		sendData.setUser_id(user_id);
		sendData.setService_token("tstore");
		sendData.setService_secret("tstore");
		sendData.setService_id(user_id);
		sendData.setTarget_service("tstore");

		return this.sendIDP(sendData);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.skplanet.storeplatform.sac.external.idp.service.IdpSacServiceSample1Impl
	 * #searchToken(java.lang.String)
	 */
	@Override
	public IDPReceiverM searchToken(String user_id) throws Exception {

		IDPSenderM sendData = new IDPSenderM();
		sendData.setUrl(IDPSender.IDP_REQ_URL_SEARCH_TOKEN);
		sendData.setCmd(IDPSender.IDP_REQ_CMD_SEARCH_TOKEN);
		sendData.setUser_id(user_id);
		sendData.setTarget_service("tstore");
		sendData.setSp_token("tstore");
		return this.sendIDP(sendData);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.skplanet.storeplatform.sac.external.idp.service.IdpSacServiceSample1Impl
	 * #deleteToken(java.lang.String)
	 */
	@Override
	public IDPReceiverM deleteToken(String user_id) throws Exception {

		IDPSenderM sendData = new IDPSenderM();
		sendData.setUrl(IDPSender.IDP_REQ_URL_DELETE_TOKEN);
		sendData.setCmd(IDPSender.IDP_REQ_CMD_DELETE_TOKEN);
		sendData.setUser_id(user_id);
		sendData.setTarget_service("tstore");
		sendData.setType("1");

		return this.sendIDP(sendData);
	}

}
