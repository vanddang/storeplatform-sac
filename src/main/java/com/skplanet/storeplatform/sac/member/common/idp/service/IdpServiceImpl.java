package com.skplanet.storeplatform.sac.member.common.idp.service;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.skplanet.storeplatform.external.client.idp.vo.IdpReceiverM;
import com.skplanet.storeplatform.sac.member.common.idp.constants.IdpConstants;
import com.skplanet.storeplatform.sac.member.common.idp.repository.IdpRepository;
import com.skplanet.storeplatform.sac.member.common.idp.vo.IdpSenderM;

/**
 * IDP - API 항목
 * 
 * Updated on : 2014. 1. 13. Updated by : 김경복, 부르칸.
 */
@Service
public class IdpServiceImpl implements IdpService {

	@Autowired
	private IdpRepository repository;

	/**
	 * <pre>
	 * 2.1.2. 서비스 중복 가입 체크. (이메일 기준).
	 * </pre>
	 * 
	 * @param email
	 * @return IDPReceiverM
	 */
	@Override
	public IdpReceiverM alredyJoinCheckByEmail(String email) {
		IdpSenderM sendData = new IdpSenderM();
		sendData.setUrl(IdpConstants.IDP_REQ_URL_JOIN);
		sendData.setCmd(IdpConstants.IDP_REQ_CMD_ALEADY_JOIN_CHECK);
		sendData.setResp_type(IdpConstants.IDP_PARAM_RESP_TYPE_XML);
		sendData.setResp_flow(IdpConstants.IDP_PARAM_RESP_FLOW_RESPONSE);
		sendData.setKey(email);
		sendData.setKey_type(IdpConstants.IDP_PARAM_KEY_TYPE_EMAIL);
		return this.repository.sendIDP(sendData, IdpConstants.HTTP_METHOD_GET);
	}

	/**
	 * <pre>
	 * 2.1.13. 아이디 중복 체크.
	 * </pre>
	 * 
	 * @param id
	 * @return IdpReceiverM
	 */
	@Override
	public IdpReceiverM checkDupID(String id) {
		IdpSenderM sendData = new IdpSenderM();

		sendData.setUrl(IdpConstants.IDP_REQ_URL_JOIN);
		sendData.setCmd(IdpConstants.IDP_REQ_CMD_DUPLICATE_ID_CHECK);
		sendData.setResp_type(IdpConstants.IDP_PARAM_RESP_TYPE_XML);
		sendData.setResp_flow(IdpConstants.IDP_PARAM_RESP_FLOW_RESPONSE);
		sendData.setUser_id(id);

		return this.repository.sendIDP(sendData, IdpConstants.HTTP_METHOD_GET);
	}

	/**
	 * <pre>
	 * 2.1.8. 자동 가입 방지 Image 발급.
	 * </pre>
	 * 
	 * @return IdpReceiverM
	 */
	@Override
	public IdpReceiverM warterMarkImageUrl() {
		IdpSenderM sendData = new IdpSenderM();

		sendData.setUrl(IdpConstants.IDP_REQ_URL_WATERMARK_AUTH);
		sendData.setCmd(IdpConstants.IDP_REQ_CMD_WATERMARK_AUTH_IMAGE);
		sendData.setResp_type(IdpConstants.IDP_PARAM_RESP_TYPE_XML);
		sendData.setResp_flow(IdpConstants.IDP_PARAM_RESP_FLOW_RESPONSE);
		return this.repository.sendIDP(sendData, IdpConstants.HTTP_METHOD_GET);
	}

	/**
	 * <pre>
	 * 2.1.9. 자동 가입 방지 인증.
	 * [POST]
	 * </pre>
	 * 
	 * @param authCode
	 * @param imageSign
	 * @param signData
	 * @return IdpReceiverM
	 */
	@Override
	public IdpReceiverM warterMarkAuth(String authCode, String imageSign, String signData) {
		IdpSenderM sendData = new IdpSenderM();

		sendData.setUrl(IdpConstants.IDP_REQ_URL_WATERMARK_AUTH);
		sendData.setCmd(IdpConstants.IDP_REQ_CMD_WATERMARK_AUTH);
		sendData.setResp_type(IdpConstants.IDP_PARAM_RESP_TYPE_XML);
		sendData.setResp_flow(IdpConstants.IDP_PARAM_RESP_FLOW_RESPONSE);
		sendData.setUser_code(authCode);
		sendData.setImage_sign(imageSign);
		sendData.setSign_data(signData);
		return this.repository.sendIDP(sendData, IdpConstants.HTTP_METHOD_POST);
	}

	/**
	 * <pre>
	 * 2.1.10. 휴대폰 단말 기종 조회 및 업데이트.
	 * </pre>
	 * 
	 * @param mdn
	 * @return IdpReceiverM
	 */
	@Override
	public IdpReceiverM deviceCompare(String mdn) {
		IdpSenderM sendData = new IdpSenderM();

		sendData.setUrl(IdpConstants.IDP_REQ_URL_USER_INFO_SEARCH);
		sendData.setCmd(IdpConstants.IDP_REQ_CMD_DEVICE_COMPARE);
		sendData.setResp_type(IdpConstants.IDP_PARAM_RESP_TYPE_XML);
		sendData.setResp_flow(IdpConstants.IDP_PARAM_RESP_FLOW_RESPONSE);
		sendData.setUser_mdn(mdn);

		return this.repository.sendIDP(sendData, IdpConstants.HTTP_METHOD_GET);
	}

	/**
	 * <pre>
	 * 2.2.7 간편 회원 가입
	 * </pre>
	 * 
	 * @param param
	 * @return IdpReceiverM
	 */
	@Override
	public IdpReceiverM simpleJoin(Map<String, Object> param) {
		IdpSenderM sendData = new IdpSenderM();

		sendData.setUrl(IdpConstants.IDP_REQ_URL_JOIN);
		sendData.setCmd(IdpConstants.IDP_REQ_CMD_SIMPLE_JOIN);
		sendData.setResp_type(IdpConstants.IDP_PARAM_RESP_TYPE_XML);
		sendData.setResp_flow(IdpConstants.IDP_PARAM_RESP_FLOW_RESPONSE);
		sendData.setUser_id((String) param.get("user_id"));
		sendData.setUser_passwd((String) param.get("user_passwd"));
		sendData.setUser_email((String) param.get("user_email"));
		sendData.setUser_phone((String) param.get("user_phone"));
		sendData.setPhone_auth_key((String) param.get("phone_auth_key"));

		return this.repository.sendIDP(sendData, IdpConstants.HTTP_METHOD_GET);
	}

	/**
	 * <pre>
	 * 2.3.1. 유선 회원의 로그인.
	 * [POST]
	 * </pre>
	 * 
	 * @param userId
	 * @param userPwd
	 * @return IdpReceiverM
	 */
	@Override
	public IdpReceiverM userAuthForId(String userId, String userPwd) {
		IdpSenderM sendData = new IdpSenderM();

		sendData.setUrl(IdpConstants.IDP_REQ_URL_USER_AUTH);
		sendData.setCmd(IdpConstants.IDP_REQ_CMD_AUTH_FOR_ID);
		sendData.setResp_type(IdpConstants.IDP_PARAM_RESP_TYPE_XML);
		sendData.setResp_flow(IdpConstants.IDP_PARAM_RESP_FLOW_RESPONSE);
		sendData.setUser_id(userId);
		sendData.setUser_passwd(userPwd);

		return this.repository.sendIDP(sendData, IdpConstants.HTTP_METHOD_POST);
	}

	/**
	 * <pre>
	 * 2.3.2. 유선 회원의 비밀번호 확인.
	 * [POST]
	 * </pre>
	 * 
	 * @param id
	 * @param pwd
	 * @return IdpReceiverM
	 */
	@Override
	public IdpReceiverM authPwd(String id, String pwd) {
		IdpSenderM sendData = new IdpSenderM();

		sendData.setUrl(IdpConstants.IDP_REQ_URL_USER_AUTH);
		sendData.setCmd(IdpConstants.IDP_REQ_CMD_AUTH_FOR_PWD);
		sendData.setResp_type(IdpConstants.IDP_PARAM_RESP_TYPE_XML);
		sendData.setResp_flow(IdpConstants.IDP_PARAM_RESP_FLOW_RESPONSE);
		sendData.setUser_id(id);
		sendData.setUser_passwd(pwd);

		return this.repository.sendIDP(sendData, IdpConstants.HTTP_METHOD_POST);
	}

	/**
	 * <pre>
	 * 2.4.2. 기본 Profile 조회 (For SP Server).
	 * </pre>
	 * 
	 * @param userAuthKey
	 * @param queryKeyType
	 *            : 1=id, 2=Email, 3=User Key, default=1
	 * @param queryKeyValue
	 * @return IdpReceiverM
	 */
	@Deprecated
	@Override
	public IdpReceiverM searchUserCommonInfo(String queryKeyType, String queryKeyValue) {
		IdpSenderM sendData = new IdpSenderM();

		sendData.setUrl(IdpConstants.IDP_REQ_URL_USER_INFO_SEARCH);
		sendData.setCmd(IdpConstants.IDP_REQ_CMD_FIND_COMMON_PROFILE_FOR_SERVER);
		sendData.setResp_type(IdpConstants.IDP_PARAM_RESP_TYPE_XML);
		sendData.setResp_flow(IdpConstants.IDP_PARAM_RESP_FLOW_RESPONSE);
		sendData.setKey_type(queryKeyType);
		sendData.setKey(queryKeyValue);

		return this.repository.sendIDP(sendData, IdpConstants.HTTP_METHOD_GET);
	}

	/**
	 * <pre>
	 * 2.4.3. 특정 Profile 조회 (For SO Server).
	 * </pre>
	 * 
	 * @param queryKeyType
	 *            : 1=id, 2=Email, 3=User Key, default=1
	 * @param queryKeyValue
	 * @return IdpReceiverM
	 */
	@Override
	public IdpReceiverM searchSpecialProfile(String queryKeyType, String queryKeyValue) {
		IdpSenderM sendData = new IdpSenderM();

		sendData.setUrl(IdpConstants.IDP_REQ_URL_USER_INFO_SEARCH);
		sendData.setCmd(IdpConstants.IDP_REQ_CMD_FIND_SPECIAL_PROFILE);
		sendData.setResp_type(IdpConstants.IDP_PARAM_RESP_TYPE_XML);
		sendData.setResp_flow(IdpConstants.IDP_PARAM_RESP_FLOW_RESPONSE);
		sendData.setKey_type(queryKeyType);
		sendData.setKey(queryKeyValue);

		return this.repository.sendIDP(sendData, IdpConstants.HTTP_METHOD_GET);
	}

	/**
	 * <pre>
	 * 2.5.1. 인증 정보 변경 API.
	 * [POST]
	 * </pre>
	 * 
	 * @param user_auth_key
	 * @param key_type
	 *            : 1=Email, 2=password, default=1
	 * @param key
	 * @return IdpReceiverM
	 */
	@Override
	public IdpReceiverM modifyAuthInfo(String user_auth_key, String key_type, String key) {
		IdpSenderM sendData = new IdpSenderM();
		sendData.setUrl(IdpConstants.IDP_REQ_URL_USER_INFO_MODIFY);
		sendData.setCmd(IdpConstants.IDP_REQ_CMD_MODIFY_AUTH_INFO);
		sendData.setResp_type(IdpConstants.IDP_PARAM_RESP_TYPE_XML);
		sendData.setResp_flow(IdpConstants.IDP_PARAM_RESP_FLOW_RESPONSE);

		sendData.setUser_auth_key(user_auth_key);
		sendData.setKey_type(key_type);
		sendData.setKey(key);

		return this.repository.sendIDP(sendData, IdpConstants.HTTP_METHOD_POST);
	}

	/**
	 * <pre>
	 * 2.5.2. 회원 정보 변경.
	 * [POST]
	 * </pre>
	 * 
	 * @param param
	 * @return IdpReceiverM
	 */
	@Override
	public IdpReceiverM modifyProfile(Map<String, Object> param) {
		IdpSenderM sendData = new IdpSenderM();

		sendData.setUrl(IdpConstants.IDP_REQ_URL_USER_INFO_MODIFY);
		sendData.setCmd(IdpConstants.IDP_REQ_CMD_MODIFY_PROFILE);
		sendData.setResp_type(IdpConstants.IDP_PARAM_RESP_TYPE_XML);
		sendData.setResp_flow(IdpConstants.IDP_PARAM_RESP_FLOW_RESPONSE);

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
		return this.repository.sendIDP(sendData, IdpConstants.HTTP_METHOD_POST);
	}

	/**
	 * <pre>
	 * 2.6.1. 회원 해지.
	 * [POST]
	 * </pre>
	 * 
	 * @param userAuthKey
	 * @param secedeKeyType
	 *            : 1=User ID, 2=User Key, default=1
	 * @param secedeKeyValue
	 * @return IdpReceiverM
	 */
	@Override
	public IdpReceiverM secedeUser(String userAuthKey, String secedeKeyType, String secedeKeyValue) {
		IdpSenderM sendData = new IdpSenderM();

		sendData.setUrl(IdpConstants.IDP_REQ_URL_SECEDE);
		sendData.setCmd(IdpConstants.IDP_REQ_CMD_SECEDE_USER);
		sendData.setResp_type(IdpConstants.IDP_PARAM_RESP_TYPE_XML);
		sendData.setResp_flow(IdpConstants.IDP_PARAM_RESP_FLOW_RESPONSE);
		sendData.setUser_auth_key(userAuthKey);
		sendData.setKey_type(secedeKeyType);
		sendData.setKey(secedeKeyValue);
		return this.repository.sendIDP(sendData, IdpConstants.HTTP_METHOD_POST);
	}

	/**
	 * <pre>
	 * 3.1.1. MDN/Password 중복 가입 체크.
	 * </pre>
	 * 
	 * @param mdn
	 * @return IdpReceiverM
	 */
	@Override
	public IdpReceiverM aleadyJoinCheckForMdn(String mdn) {
		IdpSenderM sendData = new IdpSenderM();

		sendData.setUrl(IdpConstants.IDP_REQ_URL_JOIN);
		sendData.setCmd(IdpConstants.IDP_REQ_CMD_ALEADY_JOIN_MDN);
		sendData.setResp_type(IdpConstants.IDP_PARAM_RESP_TYPE_XML);
		sendData.setResp_flow(IdpConstants.IDP_PARAM_RESP_FLOW_RESPONSE);
		sendData.setUser_mdn(mdn);
		return this.repository.sendIDP(sendData, IdpConstants.HTTP_METHOD_GET);
	}

	/**
	 * <pre>
	 * 3.2.1. 모바일 회원가입.
	 * [POST]
	 * </pre>
	 * 
	 * @param mdn
	 * @param mdnCorp
	 *            : [SKT, KTF, LGT]
	 * @return IdpReceiverM
	 */
	@Override
	public IdpReceiverM join4Wap(String mdn, String mdnCorp) {
		IdpSenderM sendData = new IdpSenderM();

		sendData.setUrl(IdpConstants.IDP_REQ_URL_JOIN);
		sendData.setCmd(IdpConstants.IDP_REQ_CMD_JOIN_FOR_WAP);
		sendData.setResp_type(IdpConstants.IDP_PARAM_RESP_TYPE_XML);
		sendData.setResp_flow(IdpConstants.IDP_PARAM_RESP_FLOW_RESPONSE);
		sendData.setMdn_corp(mdnCorp);
		sendData.setUser_mdn(mdn);

		return this.repository.sendIDP(sendData, IdpConstants.HTTP_METHOD_POST);
	}

	/**
	 * <pre>
	 * 3.3.1. 무선 회원 인증 (For SP Server).
	 * [POST]
	 * </pre>
	 * 
	 * @param mdn
	 * @return IdpReceiverM
	 */
	@Override
	public IdpReceiverM authForWap(String mdn) {
		IdpSenderM sendData = new IdpSenderM();

		sendData.setUrl(IdpConstants.IDP_REQ_URL_USER_AUTH);
		sendData.setCmd(IdpConstants.IDP_REQ_CMD_AUTH_FOR_WAP);
		sendData.setResp_type(IdpConstants.IDP_PARAM_RESP_TYPE_XML);
		sendData.setResp_flow(IdpConstants.IDP_PARAM_RESP_FLOW_RESPONSE);
		sendData.setUser_mdn(mdn);

		return this.repository.sendIDP(sendData, IdpConstants.HTTP_METHOD_POST);
	}

	/**
	 * <pre>
	 * 3.4.1 무선 회원 Profile 조회 (For SP Server).
	 * </pre>
	 * 
	 * @param mdn
	 * @return IdpReceiverM
	 */
	@Override
	public IdpReceiverM findProfileForWap(String mdn) {
		IdpSenderM sendData = new IdpSenderM();

		sendData.setUrl(IdpConstants.IDP_REQ_URL_USER_INFO_SEARCH);
		sendData.setCmd(IdpConstants.IDP_REQ_CMD_FIND_PROFILE_FOR_WAP);
		sendData.setResp_type(IdpConstants.IDP_PARAM_RESP_TYPE_XML);
		sendData.setResp_flow(IdpConstants.IDP_PARAM_RESP_FLOW_RESPONSE);
		sendData.setKey(mdn);
		sendData.setKey_type("1");

		return this.repository.sendIDP(sendData, IdpConstants.HTTP_METHOD_GET);
	}

	/**
	 * <pre>
	 * 3.6.1. 무선 회원 해지 (For SP Server).
	 * </pre>
	 * 
	 * @param mdn
	 * @return IdpReceiverM
	 */
	@Override
	public IdpReceiverM secedeUser4Wap(String mdn) {
		IdpSenderM sendData = new IdpSenderM();

		sendData.setUrl(IdpConstants.IDP_REQ_URL_SECEDE);
		sendData.setCmd(IdpConstants.IDP_REQ_CMD_SECEDE_FOR_WAP);
		sendData.setResp_type(IdpConstants.IDP_PARAM_RESP_TYPE_XML);
		sendData.setResp_flow(IdpConstants.IDP_PARAM_RESP_FLOW_RESPONSE);
		sendData.setUser_mdn(mdn);
		return this.repository.sendIDP(sendData, IdpConstants.HTTP_METHOD_GET);
	}

	/**
	 * <pre>
	 * 4.1.1. 부가서비스 가입.
	 * </pre>
	 * 
	 * @param mdn
	 * @param svcCd
	 * @param svcMngNum
	 * @return IdpReceiverM
	 */
	@Deprecated
	@Override
	public IdpReceiverM joinSupService(String mdn, String svcCd, String svcMngNum) {
		IdpSenderM sendData = new IdpSenderM();

		sendData.setUrl(IdpConstants.IDP_REQ_URL_JOIN);
		sendData.setCmd(IdpConstants.IDP_REQ_CMD_JOIN_SUP_SERVICE);
		sendData.setResp_type(IdpConstants.IDP_PARAM_RESP_TYPE_XML);
		sendData.setResp_flow(IdpConstants.IDP_PARAM_RESP_FLOW_RESPONSE);
		sendData.setUser_mdn(mdn);
		sendData.setSvc_code(svcCd);

		if (svcMngNum != null && svcMngNum.length() > 0)
			sendData.setUser_svc_mng_num(svcMngNum);

		return this.repository.sendIDP(sendData, IdpConstants.HTTP_METHOD_GET);
	}

	/**
	 * <pre>
	 * 4.2.1. 부가서비스 해지.
	 * </pre>
	 * 
	 * @param mdn
	 * @param svcCd
	 * @param svcMngNum
	 * @return IdpReceiverM
	 */
	@Override
	public IdpReceiverM secedeSupService(String mdn, String svcCd, String svcMngNum) {
		IdpSenderM sendData = new IdpSenderM();

		sendData.setUrl(IdpConstants.IDP_REQ_URL_SECEDE);
		sendData.setCmd(IdpConstants.IDP_REQ_CMD_SECEDE_SUP_SERVICE);
		sendData.setResp_type(IdpConstants.IDP_PARAM_RESP_TYPE_XML);
		sendData.setResp_flow(IdpConstants.IDP_PARAM_RESP_FLOW_RESPONSE);
		sendData.setUser_mdn(mdn);
		sendData.setSvc_code(svcCd);

		if (svcMngNum != null && svcMngNum.length() > 0)
			sendData.setUser_svc_mng_num(svcMngNum);

		return this.repository.sendIDP(sendData, IdpConstants.HTTP_METHOD_GET);
	}

	/**
	 * <pre>
	 * 4.3.2. 부가서비스 가입 여부 조회.
	 * </pre>
	 * 
	 * @param mdn
	 * @param svcCode
	 * @return IdpReceiverM
	 */
	@Override
	public IdpReceiverM serviceSubscriptionCheck(String mdn, String svcCode) {
		IdpSenderM sendData = new IdpSenderM();

		sendData.setUrl(IdpConstants.IDP_REQ_URL_USER_INFO_SEARCH);
		sendData.setCmd(IdpConstants.IDP_REQ_CMD_SERVICE_SUBSCRIPTION_CHECK);
		sendData.setResp_type(IdpConstants.IDP_PARAM_RESP_TYPE_XML);
		sendData.setResp_flow(IdpConstants.IDP_PARAM_RESP_FLOW_RESPONSE);
		sendData.setUser_mdn(mdn);
		sendData.setSvc_code(svcCode);
		return this.repository.sendIDP(sendData, IdpConstants.HTTP_METHOD_GET);
	}

}
