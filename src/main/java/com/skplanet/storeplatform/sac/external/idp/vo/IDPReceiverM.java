/*
 * Copyright (c) 2013 SK planet.
 * All right reserved.
 *
 * This software is the confidential and proprietary information of SK planet.
 * You shall not disclose such Confidential Information and
 * shall use it only in accordance with the terms of the license agreement
 * you entered into with SK planet.
 */
package com.skplanet.storeplatform.sac.external.idp.vo;

import java.util.HashMap;

/**
 * IDPReceiverM
 * 
 * Updated on : 2013. 12. 30. Updated by : Jeon.ByungYoul, SK planet.
 */
public class IDPReceiverM {
	private ResponseHeader responseHeader;
	private ResponseBody responseBody;

	public class ResponseHeader {
		/** 처리 완료된 command */
		private String cmd;
		/** 처리 결과 코드 */
		private String result;
		/** 처리 결과 메세지 */
		private String result_text;

		// TODO IDP 수정 후 삭제 예정 (임시로 추가된 사항임)
		private String sp_id;

		public String getSp_id() {
			return this.sp_id;
		}

		public void setSp_id(String sp_id) {
			this.sp_id = sp_id;
		}

		public String getCmd() {
			return this.cmd;
		}

		public void setCmd(String cmd) {
			this.cmd = cmd;
		}

		public String getResult() {
			return this.result;
		}

		public void setResult(String result) {
			this.result = result;
		}

		public String getResult_text() {
			return this.result_text;
		}

		public void setResult_text(String result_text) {
			this.result_text = result_text;
		}

	}

	/**
	 * @author soohee
	 * 
	 */
	public class ResponseBody {
		/** 서비스에 가입된 상태 */
		private String join_type; // 비실명 기반 회원 체크 'svc' : 서비스, IDP 가입 회원, 'idp' : 서비스 미가입, IDP 기 가입
		/** 인증번호 확인을 위한 signature */
		private String mobile_sign;
		/** signature 를 구성하는 source data */
		private String sign_data;
		/** 발급된 image location */
		private String image_url;
		/** 인증코드 확인을 위한 signature */
		private String image_sign;
		/** 휴대폰 인증 코드 */
		private String phone_auth_code;

		/** 가입 본인 인증을 수행할 request url */
		private String email_auth_link;
		/** IDP 가 발행하는 회원 identity key */
		private String user_key;
		/** 가입 승인된 사용자 ID */
		private String user_id;

		/*
		 * 연동 실패 시 요청값 전부를 response 함. 20100317 soohee
		 */
		/** 연동 키 타입 **/
		private String key_type;
		/** 연동 키 값 **/
		private String key;
		/** 실명인증 인증키 */
		private String sn_auth_key;
		/** 휴대폰인증 인증키 */
		private String phone_auth_key;
		/** SP ID **/
		private String sp_id;

		// private String common_profile; IDP를 통해 돌려 받아야 하는 값들 (공통부분)
		/** 생일구분 (양/음력) */
		private String user_calendar;
		/** 전화번호 */
		private String user_tel;
		/** 사용자 식별코드 */
		private String user_type;
		/** 외국인 식별코드 */
		private String is_foreign;
		/** 사용자 국가 코드 */
		private String user_nation;
		/** 실명인증여부 */
		private String is_rname_auth;

		/** */
		// private String service_profile; IDP를 통해 돌려 받아야 하는 값들 (서비스부분)

		/** 사용자 인증시 IDP 에서 발행된 signature */
		private String user_auth_key;
		/** 인증된 무선 전용회원 MDN */
		private String user_mdn;

		private String user_mdn_type;

		/** 사용자 email */
		private String user_email;
		/** 사용자 직업 코드 */
		private String user_job_code;
		/** 우편번호 */
		private String user_zipcode;
		/** 거주지 주소 */
		private String user_address;
		private String user_address2;
		/** 사용자 성별 */
		private String user_sex;
		/** 사용자 생년월일 */
		private String user_birthday;
		/** 사용자 주민번호 or 사업자 번호 */
		private String user_social_number;
		/** 사용자 이름 or 법인명 */
		private String user_name;
		/** 등록 휴대폰 list로 파이프(|)로 구분함 */
		private String user_phone;
		/** 조회된 모든 ID 로 하나 또는 복수 파이프(|)로 구분함 */
		private String id_list;
		/** 임시 비밀번호 */
		private String temp_passwd;
		/** SKT 단말기 모델 */
		private String model_id;
		/** 이통사 정보 */
		private String mdnCorp;
		/** email 승인 요청 여부 **/
		private String is_email_approved;

		/** */
		// private String profile_name; 조회 요청한 Profile 값들

		/** 연동 결과 (회원 정보 변경 API) */
		/** */
		// private String common_profile; Provisioning 대상 Field로 설정된 것들 (공통부분)
		/** */
		// private String service_profile; IDP를 통해 돌려 받아야 하는 값들 (서비스부분)

		/** 등록되어 있는 아이디 리스트 배열 */
		private String[] idList;
		/** SKT 서비스 관리번호 */
		private String svc_mng_num;
		/** 사용자 가입 상태 */
		private String status;
		/** 사용 가능 여부 */
		private String id_type;
		/** 사용자 ID i-topping 인지 여부 */
		private String is_itopping_id;
		/** 변경전 사용자 이메일 */
		private String pre_user_email;

		/* 부가서비스 */
		/** 부가서비스 코드 */
		private String svc_code;
		/** 부가서비스 결과 하나 또는 복수 파이프(|)로 구분함 / 결과는 (=) 로 구분 */
		private String svc_result;

		private String sp_list; // 타채널 가입 리스트

		private String comment;
		private String Charge;// SKT 사용자의 휴대폰 요금제 코드

		private String ServiceCD;// SKT 사용자의 휴대폰 요금제에 따른 부가서비스 코드

		private String sp_token;

		private String user_ci;

		public String getCharge() {
			return this.Charge;
		}

		public void setCharge(String charge) {
			this.Charge = charge;
		}

		public String getServiceCD() {
			return this.ServiceCD;
		}

		public void setServiceCD(String serviceCD) {
			this.ServiceCD = serviceCD;
		}

		public String getSp_list() {
			return this.sp_list;
		}

		public void setSp_list(String sp_list) {
			this.sp_list = sp_list;
		}

		public String getMdnCorp() {
			return this.mdnCorp;
		}

		public void setMdnCorp(String mdnCorp) {
			this.mdnCorp = mdnCorp;
		}

		public String getSp_id() {
			return this.sp_id;
		}

		public void setSp_id(String sp_id) {
			this.sp_id = sp_id;
		}

		public String getKey_type() {
			return this.key_type;
		}

		public void setKey_type(String key_type) {
			this.key_type = key_type;
		}

		public String getKey() {
			return this.key;
		}

		public void setKey(String key) {
			this.key = key;
		}

		public String getSn_auth_key() {
			return this.sn_auth_key;
		}

		public void setSn_auth_key(String sn_auth_key) {
			this.sn_auth_key = sn_auth_key;
		}

		public String getPhone_auth_key() {
			return this.phone_auth_key;
		}

		public void setPhone_auth_key(String phone_auth_key) {
			this.phone_auth_key = phone_auth_key;
		}

		public String getUser_address2() {
			return this.user_address2;
		}

		public void setUser_address2(String user_address2) {
			this.user_address2 = user_address2;
		}

		public String getPre_user_email() {
			return this.pre_user_email;
		}

		public void setPre_user_email(String pre_user_email) {
			this.pre_user_email = pre_user_email;
		}

		public String getIs_itopping_id() {
			return this.is_itopping_id;
		}

		public void setIs_itopping_id(String is_itopping_id) {
			this.is_itopping_id = is_itopping_id;
		}

		public String getId_type() {
			return this.id_type;
		}

		public void setId_type(String id_type) {
			this.id_type = id_type;
		}

		public String getUser_calendar() {
			return this.user_calendar;
		}

		public void setUser_calendar(String user_calendar) {
			this.user_calendar = user_calendar;
		}

		public String getUser_tel() {
			return this.user_tel;
		}

		public void setUser_tel(String user_tel) {
			this.user_tel = user_tel;
		}

		public String getUser_type() {
			return this.user_type;
		}

		public void setUser_type(String user_type) {
			this.user_type = user_type;
		}

		public String getIs_foreign() {
			return this.is_foreign;
		}

		public void setIs_foreign(String is_foreign) {
			this.is_foreign = is_foreign;
		}

		public String getUser_nation() {
			return this.user_nation;
		}

		public void setUser_nation(String user_nation) {
			this.user_nation = user_nation;
		}

		public String getIs_rname_auth() {
			return this.is_rname_auth;
		}

		public void setIs_rname_auth(String is_rname_auth) {
			this.is_rname_auth = is_rname_auth;
		}

		public String getSvc_mng_num() {
			return this.svc_mng_num;
		}

		public void setSvc_mng_num(String svc_mng_num) {
			this.svc_mng_num = svc_mng_num;
		}

		public String getStatus() {
			return this.status;
		}

		public void setStatus(String status) {
			this.status = status;
		}

		public String getJoin_type() {
			return this.join_type;
		}

		public void setJoin_type(String join_type) {
			this.join_type = join_type;
		}

		public String getMobile_sign() {
			return this.mobile_sign;
		}

		public void setMobile_sign(String mobile_sign) {
			this.mobile_sign = mobile_sign;
		}

		public String getSign_data() {
			return this.sign_data;
		}

		public void setSign_data(String sign_data) {
			this.sign_data = sign_data;
		}

		public String getImage_url() {
			return this.image_url;
		}

		public void setImage_url(String image_url) {
			this.image_url = image_url;
		}

		public String getImage_sign() {
			return this.image_sign;
		}

		public void setImage_sign(String image_sign) {
			this.image_sign = image_sign;
		}

		public String getEmail_auth_link() {
			return this.email_auth_link;
		}

		public void setEmail_auth_link(String email_auth_link) {
			this.email_auth_link = email_auth_link;
		}

		public String getUser_key() {
			return this.user_key;
		}

		public void setUser_key(String user_key) {
			this.user_key = user_key;
		}

		public String getUser_id() {
			return this.user_id;
		}

		public void setUser_id(String user_id) {
			this.user_id = user_id;
		}

		public String getUser_auth_key() {
			return this.user_auth_key;
		}

		public void setUser_auth_key(String user_auth_key) {
			this.user_auth_key = user_auth_key;
		}

		public String getUser_mdn() {
			return this.user_mdn;
		}

		public void setUser_mdn(String user_mdn) {
			this.user_mdn = user_mdn;
		}

		public String getUser_mdn_type() {
			return this.user_mdn_type;
		}

		public void setUser_mdn_type(String user_mdn_type) {
			this.user_mdn_type = user_mdn_type;
		}

		public String getUser_email() {
			return this.user_email;
		}

		public void setUser_email(String user_email) {
			this.user_email = user_email;
		}

		public String getUser_job_code() {
			return this.user_job_code;
		}

		public void setUser_job_code(String user_job_code) {
			this.user_job_code = user_job_code;
		}

		public String getUser_zipcode() {
			return this.user_zipcode;
		}

		public void setUser_zipcode(String user_zipcode) {
			this.user_zipcode = user_zipcode;
		}

		public String getUser_address() {
			return this.user_address;
		}

		public void setUser_address(String user_address) {
			this.user_address = user_address;
		}

		public String getUser_sex() {
			return this.user_sex;
		}

		public void setUser_sex(String user_sex) {
			this.user_sex = user_sex;
		}

		public String getUser_birthday() {
			return this.user_birthday;
		}

		public void setUser_birthday(String user_birthday) {
			this.user_birthday = user_birthday;
		}

		public String getUser_social_number() {
			return this.user_social_number;
		}

		public void setUser_social_number(String user_social_number) {
			this.user_social_number = user_social_number;
		}

		public String getUser_name() {
			return this.user_name;
		}

		public void setUser_name(String user_name) {
			this.user_name = user_name;
		}

		public String getUser_phone() {
			return this.user_phone;
		}

		public void setUser_phone(String user_phone) {
			this.user_phone = user_phone;
		}

		public String getId_list() {
			return this.id_list;
		}

		public void setId_list(String id_list) {
			this.id_list = id_list;
		}

		public String getTemp_passwd() {
			return this.temp_passwd;
		}

		public void setTemp_passwd(String temp_passwd) {
			this.temp_passwd = temp_passwd;
		}

		public String getModel_id() {
			return this.model_id;
		}

		public void setModel_id(String model_id) {
			this.model_id = model_id;
		}

		public String[] getIdList() {
			return this.idList;
		}

		public void setIdList(String[] idList) {
			this.idList = idList;
		}

		public String getPhone_auth_code() {
			return this.phone_auth_code;
		}

		public void setPhone_auth_code(String phone_auth_code) {
			this.phone_auth_code = phone_auth_code;
		}

		public String getSvc_code() {
			return this.svc_code;
		}

		public void setSvc_code(String svc_code) {
			this.svc_code = svc_code;
		}

		public String getSvc_result() {
			return this.svc_result;
		}

		public void setSvc_result(String svc_result) {
			this.svc_result = svc_result;
		}

		public String getIs_email_approved() {
			return this.is_email_approved;
		}

		public void setIs_email_approved(String is_email_approved) {
			this.is_email_approved = is_email_approved;
		}

		public String getComment() {
			return this.comment;
		}

		public void setComment(String comment) {
			this.comment = comment;
		}

		public String getSp_token() {
			return this.sp_token;
		}

		public void setSp_token(String sp_token) {
			this.sp_token = sp_token;
		}

		public String getUser_ci() {
			return this.user_ci;
		}

		public void setUser_ci(String user_ci) {
			this.user_ci = user_ci;
		}
	}

	public ResponseHeader getResponseHeader() {
		return this.responseHeader;
	}

	public void setResponseHeader(ResponseHeader responseHeader) {
		this.responseHeader = responseHeader;
	}

	public ResponseBody getResponseBody() {
		return this.responseBody;
	}

	public void setResponseBody(ResponseBody responseBody) {
		this.responseBody = responseBody;
	}

	/**
	 * ReceiverM 객체를 HashMap 타입으로 변환 리플렉션이기 때문에 자주 사용하지 말것 (호환을위해 만들었음)
	 * 
	 * @author nefer 2009-12-12
	 * @return
	 */
	public HashMap<String, Object> convertToMap() {
		System.out.println("======== convertToMap start =========");
		HashMap<String, Object> map = new HashMap<String, Object>();
		// CommonUtil.appendMapForClassProperty(map, this.responseHeader);
		// CommonUtil.appendMapForClassProperty(map, this.responseBody);
		System.out.println("======== convertToMap end =========");
		return map;
	}
}
