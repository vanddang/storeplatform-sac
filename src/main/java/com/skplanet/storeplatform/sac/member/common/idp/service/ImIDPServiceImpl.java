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

@Service
public class ImIDPServiceImpl implements ImIDPService {

	private static final Logger logger = LoggerFactory.getLogger(ImIDPServiceImpl.class);

	@Autowired
	private IDPRepository repository;

	/**
	 * <pre>
	 * 2.2.5 통합 ID회원로그인
	 * </pre>
	 * 
	 * @param key
	 * @param pwd
	 * @return
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
	 * @return
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

		return this.repository.sendImIDPPHttps(sendData);
	}

	/**
	 * <pre>
	 * 2.1.2 서비스이용동의요청
	 * </pre>
	 * 
	 * @param param
	 * @return
	 * @throws Exception
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
		// String parent_mdn = (String) param.get("parent_mdn");
		String parent_email = (String) param.get("parent_email");
		String parent_approve_date = (String) param.get("parent_approve_date");
		String is_parent_approve = (String) param.get("is_parent_approve");
		// String parent_approve_sst_code = (String) param.get("parent_approve_sst_code");

		ImIDPSenderM sendData = new ImIDPSenderM();
		sendData.setUrl(ImIDPConstants.IDP_REQ_URL_JOIN);
		sendData.setCmd(ImIDPConstants.IDP_REQ_CMD_AGREE_USER);
		sendData.setResp_type(ImIDPConstants.IDP_PARAM_RESP_TYPE_XML);
		sendData.setResp_flow(ImIDPConstants.IDP_PARAM_RESP_FLOW_RESPONSE);
		sendData.setKey(key);
		if (null == key_type || "".equals(key_type))
			key_type = ImIDPConstants.IDP_PARAM_KEY_TYPE_IM_SERVICE_NO;
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
		// if (parent_mdn != null)
		// sendData.setParent_mdn(parent_mdn);
		if (parent_email != null)
			sendData.setParent_email(parent_email);
		if (parent_approve_date != null)
			sendData.setParent_approve_date(parent_approve_date);
		if (is_parent_approve != null)
			sendData.setIs_parent_approve(is_parent_approve);
		// if (parent_approve_sst_code != null)
		// sendData.setParent_approve_sst_code(parent_approve_sst_code);
		sendData.setModify_req_date(DateUtil.getToday("yyyyMMdd"));
		sendData.setModify_req_time(DateUtil.getToday("hhmmss"));

		return this.repository.sendImIDPPHttps(sendData); // sendIDP(sendData);
	}

	/**
	 * <pre>
	 * 2.1.16 공통프로파일조회요청 (For Server)
	 * </pre>
	 * 
	 * @param imServiceNo
	 * @return
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

		return this.repository.sendImIDP(sendData); // sendIDP(sendData);
	}
}
