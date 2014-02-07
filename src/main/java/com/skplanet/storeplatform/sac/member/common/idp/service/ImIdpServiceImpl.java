package com.skplanet.storeplatform.sac.member.common.idp.service;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.skplanet.storeplatform.external.client.idp.vo.ImIdpReceiverM;
import com.skplanet.storeplatform.sac.api.util.DateUtil;
import com.skplanet.storeplatform.sac.member.common.idp.constants.IdpConstants;
import com.skplanet.storeplatform.sac.member.common.idp.constants.ImIdpConstants;
import com.skplanet.storeplatform.sac.member.common.idp.repository.IdpRepository;
import com.skplanet.storeplatform.sac.member.common.idp.vo.ImIdpSenderM;

/**
 * ImIDP API
 * 
 * Updated on : 2014. 1. 13. Updated by : 김경복, 부르칸.
 */
@Service
public class ImIdpServiceImpl implements ImIdpService {

	private static final Logger LOGGER = LoggerFactory.getLogger(ImIdpServiceImpl.class);

	@Autowired
	private IdpRepository repository;

	/**
	 * <pre>
	 * 2.1.2 서비스이용동의요청
	 * </pre>
	 * 
	 * @param param
	 * @return ImIDPReceiverM
	 * @throws Exception
	 */
	@Override
	public ImIdpReceiverM agreeUser(Map<String, Object> param) {
		/** param Key. */
		// operator_id
		// key_type
		// key
		// user_mdn
		// user_ci
		// user_di
		// join_sst_list
		// ocb_join_code
		// os_code
		// browser_code
		// user_mdn_auth_key
		// modify_req_date
		// modify_req_time
		// service_profiles

		ImIdpSenderM sendData = new ImIdpSenderM();
		sendData.setUrl(ImIdpConstants.IDP_REQ_URL_JOIN);
		sendData.setCmd(ImIdpConstants.IDP_REQ_CMD_AGREE_USER);
		sendData.setResp_type(ImIdpConstants.IDP_PARAM_RESP_TYPE_XML);
		sendData.setResp_flow(ImIdpConstants.IDP_PARAM_RESP_FLOW_RESPONSE);

		sendData.setOperator_id((String) param.get("operator_id"));
		sendData.setOcb_join_code((String) param.get("ocb_join_code"));
		sendData.setOs_code((String) param.get("os_code"));
		sendData.setBrowser_code((String) param.get("browser_code"));
		sendData.setService_profiles((String) param.get("key_type"));
		sendData.setKey_type((String) param.get("key_type"));
		sendData.setKey((String) param.get("key"));
		sendData.setUser_mdn((String) param.get("user_mdn"));
		sendData.setUser_ci((String) param.get("user_ci"));
		sendData.setUser_di((String) param.get("user_di"));
		sendData.setJoin_sst_list((String) param.get("join_sst_list"));
		sendData.setUser_mdn_auth_key((String) param.get("user_mdn_auth_key"));
		sendData.setModify_req_date(DateUtil.getToday("yyyyMMdd"));
		sendData.setModify_req_time(DateUtil.getToday("hhmmss"));
		return this.repository.sendImIDP(sendData, IdpConstants.HTTP_METHOD_GET);
	}

	/**
	 * <pre>
	 * 2.1.4 개별약관해지요청..
	 * </pre>
	 * 
	 * @param param
	 * @return ImIDPReceiverM
	 * @throws Exception
	 */
	@Override
	public ImIdpReceiverM discardUser(Map<String, Object> param) {
		String key = (String) param.get("key");
		String user_auth_key = (String) param.get("user_auth_key");
		String term_reason_cd = (String) param.get("term_reason_cd");

		ImIdpSenderM sendData = new ImIdpSenderM();
		sendData.setUrl(ImIdpConstants.IDP_REQ_URL_SECEDE);
		sendData.setCmd(ImIdpConstants.IDP_REQ_CMD_DISAGREE_USER);
		sendData.setResp_type(ImIdpConstants.IDP_PARAM_RESP_TYPE_XML);
		sendData.setResp_flow(ImIdpConstants.IDP_PARAM_RESP_FLOW_RESPONSE);
		sendData.setKey(key);
		sendData.setKey_type(ImIdpConstants.IDP_PARAM_KEY_TYPE_IM_SERVICE_NO);
		sendData.setUser_auth_key(user_auth_key);
		if (term_reason_cd != null)
			sendData.setTerm_reason_cd(term_reason_cd);
		sendData.setModify_req_date(DateUtil.getToday("yyyyMMdd"));
		sendData.setModify_req_time(DateUtil.getToday("hhmmss"));

		return this.repository.sendImIDP(sendData, IdpConstants.HTTP_METHOD_GET);
	}

	/**
	 * <pre>
	 * 2.1.6 공통프로파일조회요청 (For Server).
	 * </pre>
	 * 
	 * @param imServiceNo
	 * @return ImIDPReceiverM
	 * @throws Exception
	 */
	@Override
	public ImIdpReceiverM userInfoSearchServer(String imServiceNo) {
		ImIdpSenderM sendData = new ImIdpSenderM();
		sendData.setUrl(ImIdpConstants.IDP_REQ_URL_USER_INFO_SEARCH);
		sendData.setCmd(ImIdpConstants.IDP_REQ_CMD_FIND_COMMON_PROFILE_FOR_SERVER);
		sendData.setResp_type(ImIdpConstants.IDP_PARAM_RESP_TYPE_XML);
		sendData.setResp_flow(ImIdpConstants.IDP_PARAM_RESP_FLOW_RESPONSE);
		sendData.setKey(imServiceNo);
		sendData.setKey_type(ImIdpConstants.IDP_PARAM_KEY_TYPE_IM_SERVICE_NO);
		sendData.setReq_date(DateUtil.getToday("yyyyMMdd"));
		sendData.setReq_time(DateUtil.getToday("hhmmss"));

		return this.repository.sendImIDP(sendData, IdpConstants.HTTP_METHOD_GET);
	}

	/**
	 * <pre>
	 * 2.1.7 공통프로파일정보수정요청.
	 * </pre>
	 * 
	 * @param param
	 * @return ImIDPReceiverM
	 * @throws Exception
	 */
	@Override
	public ImIdpReceiverM updateUserInfo(Map<String, Object> param) {
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

		ImIdpSenderM sendData = new ImIdpSenderM();
		sendData.setUrl(ImIdpConstants.IDP_REQ_URL_USER_INFO_MODIFY);
		sendData.setCmd(ImIdpConstants.IDP_REQ_CMD_MODIFY_PROFILE);
		sendData.setResp_type(ImIdpConstants.IDP_PARAM_RESP_TYPE_XML);
		sendData.setResp_flow(ImIdpConstants.IDP_PARAM_RESP_FLOW_RESPONSE);
		sendData.setKey(key);
		sendData.setKey_type(ImIdpConstants.IDP_PARAM_KEY_TYPE_IM_SERVICE_NO);
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
		sendData.setModify_req_date(DateUtil.getToday("yyyyMMdd"));
		sendData.setModify_req_time(DateUtil.getToday("hhmmss"));
		sendData.setUser_type(user_type);
		sendData.setIs_biz_auth(is_biz_auth);

		return this.repository.sendImIDP(sendData, IdpConstants.HTTP_METHOD_GET);
	}

	/**
	 * <pre>
	 * 2.1.8 부가프로파일정보수정요청
	 * </pre>
	 * 
	 * @param param
	 * @return ImIDPReceiverM
	 * @throws Exception
	 */
	@Override
	public ImIdpReceiverM updateAdditionalInfo(Map<String, Object> param) {
		String key = (String) param.get("key");
		String user_auth_key = (String) param.get("user_auth_key");
		String user_mdn = (String) param.get("user_mdn");
		String user_mdn_auth_key = (String) param.get("user_mdn_auth_key");
		String user_sex = (String) param.get("user_sex");
		String user_calendar = (String) param.get("user_calendar");
		String user_zipcode = (String) param.get("user_zipcode");
		String user_address = (String) param.get("user_address");
		String user_address2 = (String) param.get("user_address2");

		ImIdpSenderM sendData = new ImIdpSenderM();
		sendData.setUrl(ImIdpConstants.IDP_REQ_URL_USER_INFO_MODIFY);
		sendData.setCmd(ImIdpConstants.IDP_REQ_CMD_MODIFY_ADDITIONAL);
		sendData.setResp_type(ImIdpConstants.IDP_PARAM_RESP_TYPE_XML);
		sendData.setResp_flow(ImIdpConstants.IDP_PARAM_RESP_FLOW_RESPONSE);
		sendData.setKey(key);
		sendData.setKey_type(ImIdpConstants.IDP_PARAM_KEY_TYPE_IM_SERVICE_NO);
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
		sendData.setModify_req_date(DateUtil.getToday("yyyyMMdd"));
		sendData.setModify_req_time(DateUtil.getToday("hhmmss"));

		return this.repository.sendImIDP(sendData, IdpConstants.HTTP_METHOD_GET);
	}

	/**
	 * <pre>
	 * 2.1.9 비밀번호변경요청.
	 * </pre>
	 * 
	 * @param param
	 * @return ImIDPReceiverM
	 * @throws Exception
	 */
	@Override
	public ImIdpReceiverM modifyPwd(Map<String, Object> param) {
		String key = (String) param.get("key");
		String user_auth_key = (String) param.get("user_auth_key");
		String user_passwd = (String) param.get("user_passwd");
		String user_passwd_type = (String) param.get("user_passwd_type");
		String user_passwd_modify_date = (String) param.get("user_passwd_modify_date");

		ImIdpSenderM sendData = new ImIdpSenderM();
		sendData.setUrl(ImIdpConstants.IDP_REQ_URL_USER_INFO_MODIFY);
		sendData.setCmd(ImIdpConstants.IDP_REQ_CMD_MODIFY_PWD);
		sendData.setResp_type(ImIdpConstants.IDP_PARAM_RESP_TYPE_XML);
		sendData.setResp_flow(ImIdpConstants.IDP_PARAM_RESP_FLOW_RESPONSE);
		sendData.setKey(key);
		sendData.setKey_type(ImIdpConstants.IDP_PARAM_KEY_TYPE_IM_SERVICE_NO);
		sendData.setUser_auth_key(user_auth_key);
		if (user_passwd != null)
			sendData.setUser_passwd(user_passwd);
		if (user_passwd_type != null)
			sendData.setUser_passwd_type(user_passwd_type);
		if (user_passwd_modify_date != null)
			sendData.setUser_passwd_modify_date(user_passwd_modify_date);
		sendData.setModify_req_date(DateUtil.getToday("yyyyMMdd"));
		sendData.setModify_req_time(DateUtil.getToday("hhmmss"));

		return this.repository.sendImIDP(sendData, IdpConstants.HTTP_METHOD_GET);
	}

	/**
	 * <pre>
	 * 2.1.20 로그인 상태 정보 변경 요청.
	 * </pre>
	 * 
	 * @param key
	 * @param login_status_code
	 * @return ImIDPReceiverM
	 * @throws Exception
	 */
	@Override
	public ImIdpReceiverM setLoginStatus(String key, String login_status_code) {
		ImIdpSenderM sendData = new ImIdpSenderM();
		sendData.setUrl(ImIdpConstants.IDP_REQ_URL_USER_INFO_MODIFY);
		sendData.setCmd(ImIdpConstants.IDP_REQ_CMD_SET_LOGIN_STATUS);
		sendData.setResp_type(ImIdpConstants.IDP_PARAM_RESP_TYPE_XML);
		sendData.setResp_flow(ImIdpConstants.IDP_PARAM_RESP_FLOW_RESPONSE);
		sendData.setLogin_limit_sst_code(ImIdpConstants.SSO_SST_CD_TSTORE_WEB);
		sendData.setLogin_status_code(login_status_code);
		sendData.setKey(key);
		sendData.setKey_type(ImIdpConstants.IDP_PARAM_KEY_TYPE_IM_ID);
		sendData.setModify_req_date(DateUtil.getToday("yyyyMMdd"));
		sendData.setModify_req_time(DateUtil.getToday("hhmmss"));
		return this.repository.sendImIDP(sendData, IdpConstants.HTTP_METHOD_GET);
	}

	/**
	 * <pre>
	 * 2.1.24 실명변경 요청.
	 * </pre>
	 * 
	 * @param key
	 * @param user_name
	 * @param user_birthday
	 * @param sn_auth_key
	 * @param user_auth_key
	 * @param rname_auth_mns_code
	 * @param ci
	 * @param di
	 * @param map
	 * @return ImIDPReceiverM
	 * @throws Exception
	 */
	@Override
	public ImIdpReceiverM updateUserName(String key, String user_name, String user_birthday, String sn_auth_key,
			String user_auth_key, String rname_auth_mns_code, String ci, String di, HashMap map) {
		ImIdpSenderM sendData = new ImIdpSenderM();
		sendData.setUrl(ImIdpConstants.IDP_REQ_URL_USER_INFO_MODIFY);
		sendData.setCmd(ImIdpConstants.IDP_REQ_CMD_UPDATE_USER_NAME);
		sendData.setResp_type(ImIdpConstants.IDP_PARAM_RESP_TYPE_XML);
		sendData.setResp_flow(ImIdpConstants.IDP_PARAM_RESP_FLOW_RESPONSE);
		sendData.setUser_auth_key(user_auth_key);
		sendData.setUser_name(user_name);
		sendData.setUser_birthday(user_birthday);
		sendData.setUser_calendar("1");
		sendData.setIs_rname_auth("Y");
		sendData.setRname_auth_sst_code(ImIdpConstants.SSO_SST_CD_TSTORE_WEB);
		sendData.setSn_auth_key(sn_auth_key);
		sendData.setKey(key);
		sendData.setKey_type(ImIdpConstants.IDP_PARAM_KEY_TYPE_IM_SERVICE_NO);
		sendData.setModify_req_date(DateUtil.getToday("yyyyMMdd"));
		sendData.setModify_req_time(DateUtil.getToday("hhmmss"));
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
		sendData.setRname_auth_date(DateUtil.getToday("yyyyMMdd") + "" + DateUtil.getToday("hhmmss"));

		return this.repository.sendImIDP(sendData, IdpConstants.HTTP_METHOD_GET);
	}

	/**
	 * <pre>
	 * 2.1.25 법정대리인 동의정보 변경 요청.
	 * </pre>
	 * 
	 * @param key
	 * @param parent_type
	 * @param parent_rname_auth_key
	 * @param parent_name
	 * @param parent_email
	 * @param user_auth_key
	 * @param parent_birthday
	 * @return ImIDPReceiverM
	 * @throws Exception
	 */
	@Override
	public ImIdpReceiverM updateGuardian(String key, String parent_type, String parent_rname_auth_key,
			String parent_name, String parent_email, String user_auth_key, String parent_birthday) {
		ImIdpSenderM sendData = new ImIdpSenderM();
		sendData.setUrl(ImIdpConstants.IDP_REQ_URL_USER_INFO_MODIFY);
		sendData.setCmd(ImIdpConstants.IDP_REQ_CMD_UPDATE_GUARDIAN);
		sendData.setResp_type(ImIdpConstants.IDP_PARAM_RESP_TYPE_XML);
		sendData.setResp_flow(ImIdpConstants.IDP_PARAM_RESP_FLOW_RESPONSE);
		sendData.setUser_auth_key(user_auth_key);
		sendData.setKey(key);
		sendData.setKey_type(ImIdpConstants.IDP_PARAM_KEY_TYPE_IM_SERVICE_NO);
		sendData.setParent_type(parent_type);
		sendData.setParent_rname_auth_type("5"); // IPIN
		sendData.setParent_rname_auth_key(parent_rname_auth_key);
		sendData.setParent_name(parent_name);
		sendData.setParent_birthday(parent_birthday);// 주민번호 앞자리
		sendData.setParent_email(parent_email);
		sendData.setParent_approve_date(DateUtil.getToday("yyyyMMdd"));
		sendData.setIs_parent_approve("Y");
		sendData.setParent_approve_sst_code(ImIdpConstants.SSO_SST_CD_TSTORE_WEB);
		sendData.setModify_req_date(DateUtil.getToday("yyyyMMdd"));
		sendData.setModify_req_time(DateUtil.getToday("hhmmss"));
		return this.repository.sendImIDP(sendData, IdpConstants.HTTP_METHOD_GET);
	}

	/**
	 * <pre>
	 * 2.2.5 통합 ID회원로그인
	 * </pre>
	 * 
	 * @param key
	 * @param pwd
	 * @return ImIDPReceiverM
	 * @throws Exception
	 */
	@Override
	public ImIdpReceiverM authForId(String key, String pwd) {
		ImIdpSenderM sendData = new ImIdpSenderM();

		sendData.setUrl(ImIdpConstants.IDP_REQ_URL_USER_AUTH);
		sendData.setCmd(ImIdpConstants.IDP_REQ_CMD_AUTH_FOR_ID);
		sendData.setResp_type(ImIdpConstants.IDP_PARAM_RESP_TYPE_XML);
		sendData.setResp_flow(ImIdpConstants.IDP_PARAM_RESP_FLOW_RESPONSE);
		sendData.setKey(key);
		sendData.setKey_type(ImIdpConstants.IDP_PARAM_KEY_TYPE_IM_ID);
		sendData.setUser_passwd(pwd);

		return this.repository.sendImIDP(sendData, IdpConstants.HTTP_METHOD_GET);
	}

	/**
	 * <pre>
	 * 2.2.6. 통합 ID 서비스 가입리스트 조회.
	 * </pre>
	 * 
	 * @param param
	 * @return ImIDPReceiverM
	 * @throws Exception
	 */
	@Override
	public ImIdpReceiverM findJoinServiceListIDP(Map<String, Object> param) {
		String key = (String) param.get("key");
		String keyType = (String) param.get("keyType");
		ImIdpSenderM sendData = new ImIdpSenderM();
		sendData.setUrl(ImIdpConstants.IDP_REQ_URL_USER_INFO_SEARCH);
		sendData.setCmd(ImIdpConstants.IDP_REQ_CMD_FIND_JOIN_SERVICE_LIST);
		sendData.setResp_type(ImIdpConstants.IDP_PARAM_RESP_TYPE_XML);
		sendData.setResp_flow(ImIdpConstants.IDP_PARAM_RESP_FLOW_RESPONSE);
		sendData.setKey(key);
		sendData.setKey_type(keyType);
		return this.repository.sendImIDP(sendData, IdpConstants.HTTP_METHOD_GET);
	}

	/**
	 * <pre>
	 * 2.2.9 ID 가입여부 체크.
	 * </pre>
	 * 
	 * @param id
	 * @return ImIDPReceiverM
	 * @throws Exception
	 */
	@Override
	public ImIdpReceiverM checkIdStatusIdpIm(String id) {
		ImIdpSenderM sendData = new ImIdpSenderM();

		sendData.setUrl(ImIdpConstants.IDP_REQ_URL_JOIN);
		sendData.setCmd(ImIdpConstants.IDP_REQ_CMD_ID_STATUS_IDP_IM);
		sendData.setResp_type(ImIdpConstants.IDP_PARAM_RESP_TYPE_XML);
		sendData.setResp_flow(ImIdpConstants.IDP_PARAM_RESP_FLOW_RESPONSE);
		sendData.setUser_id(id);

		return this.repository.sendImIDP(sendData, IdpConstants.HTTP_METHOD_GET);
	}

	/**
	 * <pre>
	 * 2.2.11 MDN 정보 조회 (SKT 가입자)
	 * </pre>
	 * 
	 * @param mdn
	 * @return ImIDPReceiverM
	 * @throws Exception
	 */
	@Override
	public ImIdpReceiverM getMdnInfoIDP(String mdn) {
		ImIdpSenderM sendData = new ImIdpSenderM();

		sendData.setUrl(ImIdpConstants.IDP_REQ_URL_USER_INFO_SEARCH);
		sendData.setCmd(ImIdpConstants.IDP_REQ_CMD_GET_MDN_INFO_IDP);
		sendData.setResp_type(ImIdpConstants.IDP_PARAM_RESP_TYPE_XML);
		sendData.setResp_flow(ImIdpConstants.IDP_PARAM_RESP_FLOW_RESPONSE);
		sendData.setMdn(mdn);

//		return this.repository.sendImIDP(sendData, IdpConstants.HTTP_METHOD_POST);
		return this.repository.sendImIDP(sendData, IdpConstants.HTTP_METHOD_GET);
	}

	@Override
	public ImIdpReceiverM findUserIdByMdn(Map<String, Object> param) {
		String user_mdn = (String) param.get("user_mdn");
		String user_code = (String) param.get("user_code");
		String mobile_sign = (String) param.get("mobile_sign");
		String sign_data = (String) param.get("sign_data");
		String svc_mng_num = (String) param.get("svc_mng_num");
		String model_id = (String) param.get("model_id");

		ImIdpSenderM sendData = new ImIdpSenderM();
		sendData.setUrl(ImIdpConstants.IDP_REQ_URL_USER_INFO_SEARCH);
		sendData.setCmd(ImIdpConstants.IDP_REQ_CMD_FIND_USERID_BY_MDN);
		sendData.setResp_type(ImIdpConstants.IDP_PARAM_RESP_TYPE_XML);
		sendData.setResp_flow(ImIdpConstants.IDP_PARAM_RESP_FLOW_RESPONSE);
		sendData.setUser_mdn(user_mdn);
		sendData.setUser_code(user_code);
		sendData.setMobile_sign(mobile_sign);
		sendData.setSign_data(sign_data);
		if (null != svc_mng_num)
			sendData.setSvc_mng_num(svc_mng_num);
		if (null != model_id)
			sendData.setModel_id(model_id);

		return this.repository.sendImIDP(sendData, IdpConstants.HTTP_METHOD_GET);
	}

	/**
	 * <pre>
	 * 2.1.16 공통프로파일조회요청 (For Server)
	 * </pre>
	 * 
	 * @param imServiceNo
	 * @return ImIDPReceiverM
	 * @throws Exception
	 */
	@Override
	public ImIdpReceiverM userInfoIdpSearchServer(String imServiceNo) {
		ImIdpSenderM sendData = new ImIdpSenderM();
		sendData.setUrl(ImIdpConstants.IDP_REQ_URL_USER_INFO_SEARCH);
		sendData.setCmd(ImIdpConstants.IDP_REQ_CMD_FIND_COMMON_IDP_PROFILE_FOR_SERVER);
		sendData.setResp_type(ImIdpConstants.IDP_PARAM_RESP_TYPE_XML);
		sendData.setResp_flow(ImIdpConstants.IDP_PARAM_RESP_FLOW_RESPONSE);
		sendData.setKey(imServiceNo);
		sendData.setKey_type(ImIdpConstants.IDP_PARAM_KEY_TYPE_IM_SERVICE_NO);
		sendData.setReq_date(DateUtil.getToday("yyyyMMdd"));
		sendData.setReq_time(DateUtil.getToday("hhmmss"));

		return this.repository.sendImIDP(sendData, IdpConstants.HTTP_METHOD_GET);
	}

}
