package com.skplanet.storeplatform.sac.member.common.idp.service;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.skplanet.storeplatform.external.client.idp.vo.ImIDPReceiverM;
import com.skplanet.storeplatform.sac.api.util.DateUtil;
import com.skplanet.storeplatform.sac.member.common.idp.constants.ImIDPConstants;
import com.skplanet.storeplatform.sac.member.common.idp.repository.IDPRepository;
import com.skplanet.storeplatform.sac.member.common.idp.vo.ImIDPSenderM;

/**
 * ImIDP API
 * 
 * Updated on : 2014. 1. 13. Updated by : 김경복, 부르칸.
 */
@Service
public class ImIDPServiceImpl implements ImIDPService {

	private static final Logger LOGGER = LoggerFactory.getLogger(ImIDPServiceImpl.class);

	@Autowired
	private IDPRepository repository;

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
	public ImIDPReceiverM authForId(String key, String pwd) throws Exception {
		ImIDPSenderM sendData = new ImIDPSenderM();

		sendData.setUrl(ImIDPConstants.IDP_REQ_URL_USER_AUTH);
		sendData.setCmd(ImIDPConstants.IDP_REQ_CMD_AUTH_FOR_ID);
		sendData.setResp_type(ImIDPConstants.IDP_PARAM_RESP_TYPE_XML);
		sendData.setResp_flow(ImIDPConstants.IDP_PARAM_RESP_FLOW_RESPONSE);
		sendData.setKey(key);
		sendData.setKey_type(ImIDPConstants.IDP_PARAM_KEY_TYPE_IM_ID);
		sendData.setUser_passwd(pwd);

		return this.repository.sendImIDP(sendData);
	}

	/**
	 * <pre>
	 * IMDN 정보 조회 (SKT 가입자)
	 * </pre>
	 * 
	 * @param mdn
	 * @return ImIDPReceiverM
	 * @throws Exception
	 */
	@Override
	public ImIDPReceiverM getMdnInfoIDP(String mdn) throws Exception {
		ImIDPSenderM sendData = new ImIDPSenderM();

		sendData.setUrl(ImIDPConstants.IDP_REQ_URL_USER_INFO_SEARCH);
		sendData.setCmd(ImIDPConstants.IDP_REQ_CMD_GET_MDNINFO_IDP);
		sendData.setResp_type(ImIDPConstants.IDP_PARAM_RESP_TYPE_XML);
		sendData.setResp_flow(ImIDPConstants.IDP_PARAM_RESP_FLOW_RESPONSE);
		sendData.setMdn(mdn);

		return this.repository.sendImIDP(sendData);
	}

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
	public ImIDPReceiverM agreeUser(Map<String, Object> param) throws Exception {
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

		ImIDPSenderM sendData = new ImIDPSenderM();
		sendData.setUrl(ImIDPConstants.IDP_REQ_URL_JOIN);
		sendData.setCmd(ImIDPConstants.IDP_REQ_CMD_AGREE_USER);
		sendData.setResp_type(ImIDPConstants.IDP_PARAM_RESP_TYPE_XML);
		sendData.setResp_flow(ImIDPConstants.IDP_PARAM_RESP_FLOW_RESPONSE);

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
		return this.repository.sendImIDP(sendData);
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
	public ImIDPReceiverM userInfoIdpSearchServer(String imServiceNo) throws Exception {
		ImIDPSenderM sendData = new ImIDPSenderM();
		sendData.setUrl(ImIDPConstants.IDP_REQ_URL_USER_INFO_SEARCH);
		sendData.setCmd(ImIDPConstants.IDP_REQ_CMD_FIND_COMMON_IDP_PROFILE_FOR_SERVER);
		sendData.setResp_type(ImIDPConstants.IDP_PARAM_RESP_TYPE_XML);
		sendData.setResp_flow(ImIDPConstants.IDP_PARAM_RESP_FLOW_RESPONSE);
		sendData.setKey(imServiceNo);
		sendData.setKey_type(ImIDPConstants.IDP_PARAM_KEY_TYPE_IM_SERVICE_NO);
		sendData.setReq_date(DateUtil.getToday("yyyyMMdd"));
		sendData.setReq_time(DateUtil.getToday("hhmmss"));

		return this.repository.sendImIDP(sendData);
	}

	/**
	 * <pre>
	 * ID 가입여부 체크.
	 * </pre>
	 * 
	 * @param id
	 * @return ImIDPReceiverM
	 * @throws Exception
	 */
	@Override
	public ImIDPReceiverM checkIdStatusIdpIm(String id) throws Exception {
		ImIDPSenderM sendData = new ImIDPSenderM();

		sendData.setUrl(ImIDPConstants.IDP_REQ_URL_JOIN);
		sendData.setCmd(ImIDPConstants.IDP_REQ_CMD_ID_STATUS_IDP_IM);
		sendData.setResp_type(ImIDPConstants.IDP_PARAM_RESP_TYPE_XML);
		sendData.setResp_flow(ImIDPConstants.IDP_PARAM_RESP_FLOW_RESPONSE);
		sendData.setUser_id(id);

		return this.repository.sendImIDP(sendData);
	}

	/**
	 * <pre>
	 * 부가프로파일정보수정요청.
	 * </pre>
	 * 
	 * @param param
	 * @return ImIDPReceiverM
	 * @throws Exception
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
		sendData.setUrl(ImIDPConstants.IDP_REQ_URL_USER_INFO_MODIFY);
		sendData.setCmd(ImIDPConstants.IDP_REQ_CMD_MODIFY_ADDITIONAL);
		sendData.setResp_type(ImIDPConstants.IDP_PARAM_RESP_TYPE_XML);
		sendData.setResp_flow(ImIDPConstants.IDP_PARAM_RESP_FLOW_RESPONSE);
		sendData.setKey(key);
		sendData.setKey_type(ImIDPConstants.IDP_PARAM_KEY_TYPE_IM_SERVICE_NO);
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

		return this.repository.sendImIDP(sendData);
	}

	/**
	 * <pre>
	 * method 설명.
	 * </pre>
	 * 
	 * @param param
	 * @return
	 * @throws Exception
	 */
	@Override
	public ImIDPReceiverM discardUser(Map<String, Object> param) throws Exception {
		String key = (String) param.get("key");
		String user_auth_key = (String) param.get("user_auth_key");
		String term_reason_cd = (String) param.get("term_reason_cd");

		ImIDPSenderM sendData = new ImIDPSenderM();
		sendData.setUrl(ImIDPConstants.IDP_REQ_URL_SECEDE);
		sendData.setCmd(ImIDPConstants.IDP_REQ_CMD_DISAGREE_USER);
		sendData.setResp_type(ImIDPConstants.IDP_PARAM_RESP_TYPE_XML);
		sendData.setResp_flow(ImIDPConstants.IDP_PARAM_RESP_FLOW_RESPONSE);
		sendData.setKey(key);
		sendData.setKey_type(ImIDPConstants.IDP_PARAM_KEY_TYPE_IM_SERVICE_NO);
		sendData.setUser_auth_key(user_auth_key);
		if (term_reason_cd != null)
			sendData.setTerm_reason_cd(term_reason_cd);
		sendData.setModify_req_date(DateUtil.getToday("yyyyMMdd"));
		sendData.setModify_req_time(DateUtil.getToday("hhmmss"));

		return this.repository.sendImIDP(sendData);
	}

}
