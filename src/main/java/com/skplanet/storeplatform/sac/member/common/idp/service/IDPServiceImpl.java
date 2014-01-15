package com.skplanet.storeplatform.sac.member.common.idp.service;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.skplanet.storeplatform.external.client.idp.vo.IDPReceiverM;
import com.skplanet.storeplatform.sac.member.common.idp.constants.IDPConstants;
import com.skplanet.storeplatform.sac.member.common.idp.repository.IDPRepository;
import com.skplanet.storeplatform.sac.member.common.idp.vo.IDPSenderM;

/**
 * IDP - API 항목
 * 
 * Updated on : 2014. 1. 13. Updated by : 김경복, 부르칸.
 */
@Service
public class IDPServiceImpl implements IDPService {

	@Autowired
	private IDPRepository repository;

	/**
	 * <pre>
	 * 아이디 중복 체크
	 * </pre>
	 * 
	 * @param id
	 * @return
	 * @throws Exception
	 */
	@Override
	public IDPReceiverM checkDupID(String id) throws Exception {
		IDPSenderM sendData = new IDPSenderM();

		sendData.setUrl(IDPConstants.IDP_REQ_URL_JOIN);
		sendData.setCmd(IDPConstants.IDP_REQ_CMD_DUPLICATE_ID_CHECK);
		sendData.setResp_type(IDPConstants.IDP_PARAM_RESP_TYPE_XML);
		sendData.setResp_flow(IDPConstants.IDP_PARAM_RESP_FLOW_RESPONSE);
		sendData.setUser_id(id);

		return this.repository.sendIDP(sendData);
	}

	/**
	 * <pre>
	 * Email 중복 가입여부 조회.
	 * </pre>
	 * 
	 * @param email
	 * @return
	 * @throws Exception
	 */
	@Override
	public IDPReceiverM alredyJoinCheckByEmail(String email) throws Exception {
		IDPSenderM sendData = new IDPSenderM();
		sendData.setUrl(IDPConstants.IDP_REQ_URL_JOIN);
		sendData.setCmd(IDPConstants.IDP_REQ_CMD_ALEADY_JOIN_CHECK);
		sendData.setResp_type(IDPConstants.IDP_PARAM_RESP_TYPE_XML);
		sendData.setResp_flow(IDPConstants.IDP_PARAM_RESP_FLOW_RESPONSE);
		sendData.setKey(email);
		sendData.setKey_type(IDPConstants.IDP_PARAM_KEY_TYPE_EMAIL);
		return this.repository.sendIDP(sendData);
	}

	/**
	 * <pre>
	 * 워터마크 발급.
	 * </pre>
	 * 
	 * @return
	 * @throws Exception
	 */
	@Override
	public IDPReceiverM warterMarkImageUrl() throws Exception {
		IDPSenderM sendData = new IDPSenderM();

		sendData.setUrl(IDPConstants.IDP_REQ_URL_WATERMARK_AUTH);
		sendData.setCmd(IDPConstants.IDP_REQ_CMD_WATERMARK_AUTH_IMAGE);
		sendData.setResp_type(IDPConstants.IDP_PARAM_RESP_TYPE_XML);
		sendData.setResp_flow(IDPConstants.IDP_PARAM_RESP_FLOW_RESPONSE);

		return this.repository.sendIDP(sendData);
	}

	/**
	 * <pre>
	 * 워터마크 인증.
	 * </pre>
	 * 
	 * @param authCode
	 * @param imageSign
	 * @param signData
	 * @return
	 * @throws Exception
	 */
	@Override
	public IDPReceiverM warterMarkAuth(String authCode, String imageSign, String signData) throws Exception {
		IDPSenderM sendData = new IDPSenderM();

		sendData.setUrl(IDPConstants.IDP_REQ_URL_WATERMARK_AUTH);
		sendData.setCmd(IDPConstants.IDP_REQ_CMD_WATERMARK_AUTH);
		sendData.setResp_type(IDPConstants.IDP_PARAM_RESP_TYPE_XML);
		sendData.setResp_flow(IDPConstants.IDP_PARAM_RESP_FLOW_RESPONSE);
		sendData.setUser_code(authCode);
		sendData.setImage_sign(imageSign);
		sendData.setSign_data(signData);
		return this.repository.sendIDP(sendData);
	}

	/**
	 * <pre>
	 * 회원탈퇴.
	 * </pre>
	 * 
	 * @param userAuthKey
	 * @param secedeKeyType
	 * @param secedeKeyValue
	 * @return IDPReceiverM
	 * @throws Exception
	 */
	@Override
	public IDPReceiverM secedeUser(String userAuthKey, String secedeKeyType, String secedeKeyValue) throws Exception {
		IDPSenderM sendData = new IDPSenderM();

		sendData.setUrl(IDPConstants.IDP_REQ_URL_SECEDE);
		sendData.setCmd(IDPConstants.IDP_REQ_CMD_SECEDE_USER);
		sendData.setResp_type(IDPConstants.IDP_PARAM_RESP_TYPE_XML);
		sendData.setResp_flow(IDPConstants.IDP_PARAM_RESP_FLOW_RESPONSE);
		// sendData.setWatermark_auth(IDP_PARAM_KEY_WATERMARK_AUTH_NON_INCLISION);
		sendData.setUser_auth_key(userAuthKey);
		sendData.setKey_type(secedeKeyType);
		sendData.setKey(secedeKeyValue);
		return this.repository.sendIDP(sendData);
	}

	/**
	 * <pre>
	 * 모바일 회원 인증
	 * </pre>
	 * 
	 * @param mdn
	 * @return
	 * @throws Exception
	 */
	@Override
	public IDPReceiverM authForWap(String mdn) throws Exception {
		IDPSenderM sendData = new IDPSenderM();

		sendData.setUrl(IDPConstants.IDP_REQ_URL_USER_AUTH);
		sendData.setCmd(IDPConstants.IDP_REQ_CMD_AUTH_FOR_WAP);
		sendData.setResp_type(IDPConstants.IDP_PARAM_RESP_TYPE_XML);
		sendData.setResp_flow(IDPConstants.IDP_PARAM_RESP_FLOW_RESPONSE);
		sendData.setUser_mdn(mdn);

		return this.repository.sendIDP(sendData);
	}

	/**
	 * <pre>
	 * 모바일 회원가입
	 * </pre>
	 * 
	 * @param mdn
	 * @return
	 * @throws Exception
	 */
	@Override
	public IDPReceiverM join4Wap(String mdn) throws Exception {
		IDPSenderM sendData = new IDPSenderM();

		sendData.setUrl(IDPConstants.IDP_REQ_URL_JOIN);
		sendData.setCmd(IDPConstants.IDP_REQ_CMD_JOIN_FOR_WAP);
		sendData.setResp_type(IDPConstants.IDP_PARAM_RESP_TYPE_XML);
		sendData.setResp_flow(IDPConstants.IDP_PARAM_RESP_FLOW_RESPONSE);
		sendData.setMdn_corp(IDPConstants.IDP_PARAM_KEY_USER_MDN_TYPE_SKT);
		sendData.setUser_mdn(mdn);

		return this.repository.sendIDP(sendData);
	}

	/**
	 * <pre>
	 * 모바일 회원 탈퇴
	 * </pre>
	 * 
	 * @param mdn
	 * @return
	 * @throws Exception
	 */
	@Override
	public IDPReceiverM secedeUser4Wap(String mdn) throws Exception {
		IDPSenderM sendData = new IDPSenderM();

		sendData.setUrl(IDPConstants.IDP_REQ_URL_SECEDE);
		sendData.setCmd(IDPConstants.IDP_REQ_CMD_SECEDE_FOR_WAP);
		sendData.setResp_type(IDPConstants.IDP_PARAM_RESP_TYPE_XML);
		sendData.setResp_flow(IDPConstants.IDP_PARAM_RESP_FLOW_RESPONSE);
		sendData.setUser_mdn(mdn);
		return this.repository.sendIDP(sendData);
	}

	/**
	 * <pre>
	 * 로그인
	 * </pre>
	 * 
	 * @param userId
	 * @param userPwd
	 * @return
	 * @throws Exception
	 */
	@Override
	public IDPReceiverM userAuthForId(String userId, String userPwd) throws Exception {
		IDPSenderM sendData = new IDPSenderM();

		sendData.setUrl(IDPConstants.IDP_REQ_URL_USER_AUTH);
		sendData.setCmd(IDPConstants.IDP_REQ_CMD_AUTH_FOR_ID);
		sendData.setResp_type(IDPConstants.IDP_PARAM_RESP_TYPE_XML);
		sendData.setResp_flow(IDPConstants.IDP_PARAM_RESP_FLOW_RESPONSE);
		sendData.setUser_id(userId);
		sendData.setUser_passwd(userPwd);

		return this.repository.sendIDP(sendData);
	}

	/**
	 * <pre>
	 * 프로파일 수정
	 * </pre>
	 * 
	 * @param param
	 * @return
	 * @throws Exception
	 */
	@Override
	public IDPReceiverM modifyProfile(Map<String, Object> param) throws Exception {
		IDPSenderM sendData = new IDPSenderM();

		sendData.setUrl(IDPConstants.IDP_REQ_URL_USER_INFO_MODIFY);
		sendData.setCmd(IDPConstants.IDP_REQ_CMD_MODIFY_PROFILE);
		sendData.setResp_type(IDPConstants.IDP_PARAM_RESP_TYPE_XML);
		sendData.setResp_flow(IDPConstants.IDP_PARAM_RESP_FLOW_RESPONSE);

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
			if (phone_auth_key == null || "".equals(phone_auth_key)) {
				phone_auth_key = this.repository.makePhoneAuthKey(user_phone);
			}
		}

		if (is_rname_auth != null && "Y".equals(is_rname_auth)) {
			if (user_name != null && !"".equals(is_rname_auth)) {
				sn_auth_key = this.repository.makeSnAuthKey(user_name, user_id);
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

		// TODO Https 변경
		return this.repository.sendIDP(sendData);
	}

	/**
	 * <pre>
	 * 3.4.1	무선 회원 Profile 조회 (For SP Server)
	 * </pre>
	 * 
	 * @param mdn
	 * @return
	 * @throws Exception
	 */
	@Override
	public IDPReceiverM findProfileForWap(String mdn) throws Exception {
		IDPSenderM sendData = new IDPSenderM();

		sendData.setUrl(IDPConstants.IDP_REQ_URL_USER_INFO_SEARCH);
		sendData.setCmd(IDPConstants.IDP_REQ_CMD_FIND_PROFILE_FOR_WAP);
		sendData.setResp_type(IDPConstants.IDP_PARAM_RESP_TYPE_XML);
		sendData.setResp_flow(IDPConstants.IDP_PARAM_RESP_FLOW_RESPONSE);
		sendData.setKey(mdn);
		sendData.setKey_type("1");

		return this.repository.sendIDP(sendData);
	}

	@Override
	public IDPReceiverM deviceCompare(String mdn) throws Exception {
		IDPSenderM sendData = new IDPSenderM();

		sendData.setUrl(IDPConstants.IDP_REQ_URL_USER_INFO_SEARCH);
		sendData.setCmd(IDPConstants.IDP_REQ_CMD_DEVICE_COMPARE);
		sendData.setResp_type(IDPConstants.IDP_PARAM_RESP_TYPE_XML);
		sendData.setResp_flow(IDPConstants.IDP_PARAM_RESP_FLOW_RESPONSE);
		sendData.setKey(mdn);
		sendData.setKey_type("1");

		return this.repository.sendIDP(sendData);
	}
}
