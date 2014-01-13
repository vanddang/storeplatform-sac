/*
 * COPYRIGHT(c) SK telecom 2009
 * This software is the proprietary information of SK telecom.
 *
 * Revision History
 * Author       Date            Description
 * --------     ----------      ------------------
 * ?            ?               ?
 * nefer        2009.12.08      move to omp_common
 *
 */
package com.skplanet.storeplatform.sac.member.common.idp.vo;

/**
 * Calss 설명
 * 
 * Updated on : 2014. 1. 13. Updated by : 김경복, 부르칸.
 */
public class IDPSenderM {
	/* 연동 요청 (공통 결과 API) */
	/** 세부 요청 command. */
	private String cmd;
	/** 등록된 SP ID. */
	private String sp_id;
	/** 요청 인증을 위한 signature. */
	private String sp_auth_key;
	/** 처리 결과 Data Format. */
	private String resp_type; // 1 : "name=vlaue", 2 : XML default=1
	/** 처리결과 전달 방식. */
	private String resp_flow; // resp : response 방식, redt : redirect 방식 default=resp
	/** redirect 할 url. */
	private String resp_url;

	/* 연동 요청 (기본 API) */
	/** 서비스 중복 가입 체크 key 종류. */
	private String key_type; // 1 : email, 2 : 주민번호 default=1
	/** 서비스 중복 가입을 체크 할 값. */
	private String key;
	private String pre_key;
	/** 사용자 아이디. */
	private String user_id;
	/** 내국인, 외국인에 따른 실명 인증 type. */
	private String type; // 1 : 내국인, 2 : 국내거주외국인
	/** 사용자 이름 or 국내 거주 외국인 이름. */
	private String user_name;
	/** 사용자 주민번호 or 외국인번호. */
	private String user_social_number;
	/** 사용자 패스워드. */
	private String user_passwd;
	/** 인증 할 휴대폰번호. */
	private String user_mdn;
	/** 이통사 구분. */
	private String user_mdn_type;
	/** 입력한 휴대폰 인증 코드. */
	private String user_code;
	/** IDP로 부터 발행 받은 mobile_sign value. */
	private String mobile_sign;
	/** IDP로 부터 전달 받은 sign_data value. */
	private String sign_data;
	/** 발급된 watermark image url. */
	private String image_url;
	/** 인증코드 확인을 위한 signature. */
	private String image_sign;
	/** 변경 전 mail. */
	private String pre_user_email;
	/** 가입 시 발행된 user key. */
	private String user_key;

	/* 연동 요청 (회원 가입 API) */
	/** 사용자 email */
	private String user_email;
	/** 사용자 직업 */
	private String user_job_code;
	/** 우편번호 */
	private String user_zipcode;
	/** 거주지 주소 */
	private String user_address;
	/** 거주지 상세주소 */
	private String user_address2;
	/** 성별 */
	private String user_sex;
	/** 생년월일 */
	private String user_birthday;
	/** 사용자 휴대폰번호 */
	private String user_phone;
	/** 워터마크 인증 포함 여부 */
	private String watermark_auth;
	/** 회원 유형 */
	private String user_type;
	/** 14세 미만 여부 */
	private String is_parents;
	/** 부모 주민등록번호 */
	private String parent_social_number;
	/** 부모 이름 */
	private String parent_name;
	/** 부모 이메일 */
	private String parent_email;
	/** 부모 휴대폰번호 */
	private String parent_mdn;
	/** 14세 미만 직업 */
	private String user_job;
	/** 사용자 유선 전화번호 */
	private String user_tel;
	/** 인증 키 */
	private String user_auth_key;
	/** 실명인증여부 */
	private String is_rname_auth;
	/** 양음력 여부 */
	private String user_calendar;
	/** */
	// private String service profile;
	/** 이통사 */
	private String mdn_corp;

	/* 연동 요청 (회원 정보 조회 API) */
	/** 조회를 원하는 profile field - '|'로 구분 */
	private String req_profile_list;

	/** 보안 */
	private String phone_auth_key; // 휴대폰인증 인증키
	private String sn_auth_key; // 실명인증 인증키

	/* 연동 추가 정보 */
	/** 요청 URL */
	private String url;

	/* 연동 요청 (부가서비스 가입/해지 API) */
	/** 부가서비스 코드 */
	private String svc_code;
	/** 서비스 관리 번호 */
	private String user_svc_mng_num;

	// private String sendFrom = "tstore";

	private String approve_sp_id; // 가입동의 SPID

	private String comment = ""; // sms내용

	private String is_foreign = "";

	private String target_service = "";

	private String service_token = "";

	private String service_secret = "";

	private String service_id = "";

	private String sp_token = "";

	private String user_ci = "";

	private String scr_id = "";
	private String send_order = "";

	public String getSend_order() {
		return this.send_order;
	}

	public void setSend_order(String send_order) {
		this.send_order = send_order;
	}

	public String getScr_id() {
		return this.scr_id;
	}

	public void setScr_id(String scr_id) {
		this.scr_id = scr_id;
	}

	public String getMdn_corp() {
		return this.mdn_corp;
	}

	public void setMdn_corp(String mdn_corp) {
		this.mdn_corp = mdn_corp;
	}

	public String getUser_calendar() {
		return this.user_calendar;
	}

	public void setUser_calendar(String user_calendar) {
		this.user_calendar = user_calendar;
	}

	public String getIs_rname_auth() {
		return this.is_rname_auth;
	}

	public void setIs_rname_auth(String is_rname_auth) {
		this.is_rname_auth = is_rname_auth;
	}

	public String getPhone_auth_key() {
		return this.phone_auth_key;
	}

	public void setPhone_auth_key(String phone_auth_key) {
		this.phone_auth_key = phone_auth_key;
	}

	public String getSn_auth_key() {
		return this.sn_auth_key;
	}

	public void setSn_auth_key(String sn_auth_key) {
		this.sn_auth_key = sn_auth_key;
	}

	public String getUser_key() {
		return this.user_key;
	}

	public void setUser_key(String user_key) {
		this.user_key = user_key;
	}

	public String getPre_user_email() {
		return this.pre_user_email;
	}

	public void setPre_user_email(String pre_user_email) {
		this.pre_user_email = pre_user_email;
	}

	public String getCmd() {
		return this.cmd;
	}

	public void setCmd(String cmd) {
		this.cmd = cmd;
	}

	public String getSp_id() {
		return this.sp_id;
	}

	public void setSp_id(String sp_id) {
		this.sp_id = sp_id;
	}

	public String getSp_auth_key() {
		return this.sp_auth_key;
	}

	public void setSp_auth_key(String sp_auth_key) {
		this.sp_auth_key = sp_auth_key;
	}

	public String getResp_type() {
		return this.resp_type;
	}

	public void setResp_type(String resp_type) {
		this.resp_type = resp_type;
	}

	public String getResp_flow() {
		return this.resp_flow;
	}

	public void setResp_flow(String resp_flow) {
		this.resp_flow = resp_flow;
	}

	public String getResp_url() {
		return this.resp_url;
	}

	public void setResp_url(String resp_url) {
		this.resp_url = resp_url;
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

	public String getUser_id() {
		return this.user_id;
	}

	public void setUser_id(String user_id) {
		this.user_id = user_id;
	}

	public String getType() {
		return this.type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getUser_name() {
		return this.user_name;
	}

	public void setUser_name(String user_name) {
		this.user_name = user_name;
	}

	public String getUser_social_number() {
		return this.user_social_number;
	}

	public void setUser_social_number(String user_social_number) {
		this.user_social_number = user_social_number;
	}

	public String getUser_passwd() {
		return this.user_passwd;
	}

	public void setUser_passwd(String user_passwd) {
		this.user_passwd = user_passwd;
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

	public String getUser_code() {
		return this.user_code;
	}

	public void setUser_code(String user_code) {
		this.user_code = user_code;
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

	public String getUser_address2() {
		return this.user_address2;
	}

	public void setUser_address2(String user_address2) {
		this.user_address2 = user_address2;
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

	public String getUser_phone() {
		return this.user_phone;
	}

	public void setUser_phone(String user_phone) {
		this.user_phone = user_phone;
	}

	public String getWatermark_auth() {
		return this.watermark_auth;
	}

	public void setWatermark_auth(String watermark_auth) {
		this.watermark_auth = watermark_auth;
	}

	public String getUser_type() {
		return this.user_type;
	}

	public void setUser_type(String user_type) {
		this.user_type = user_type;
	}

	public String getIs_parents() {
		return this.is_parents;
	}

	public void setIs_parents(String is_parents) {
		this.is_parents = is_parents;
	}

	public String getParent_social_number() {
		return this.parent_social_number;
	}

	public void setParent_social_number(String parent_social_number) {
		this.parent_social_number = parent_social_number;
	}

	public String getParent_name() {
		return this.parent_name;
	}

	public void setParent_name(String parent_name) {
		this.parent_name = parent_name;
	}

	public String getParent_email() {
		return this.parent_email;
	}

	public void setParent_email(String parent_email) {
		this.parent_email = parent_email;
	}

	public String getParent_mdn() {
		return this.parent_mdn;
	}

	public void setParent_mdn(String parent_mdn) {
		this.parent_mdn = parent_mdn;
	}

	public String getUser_job() {
		return this.user_job;
	}

	public void setUser_job(String user_job) {
		this.user_job = user_job;
	}

	public String getUser_tel() {
		return this.user_tel;
	}

	public void setUser_tel(String user_tel) {
		this.user_tel = user_tel;
	}

	public String getUser_auth_key() {
		return this.user_auth_key;
	}

	public void setUser_auth_key(String user_auth_key) {
		this.user_auth_key = user_auth_key;
	}

	public String getReq_profile_list() {
		return this.req_profile_list;
	}

	public void setReq_profile_list(String req_profile_list) {
		this.req_profile_list = req_profile_list;
	}

	public String getUrl() {
		return this.url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getSvc_code() {
		return this.svc_code;
	}

	public void setSvc_code(String svc_code) {
		this.svc_code = svc_code;
	}

	public String getUser_svc_mng_num() {
		return this.user_svc_mng_num;
	}

	public void setUser_svc_mng_num(String user_svc_mng_num) {
		this.user_svc_mng_num = user_svc_mng_num;
	}

	// public String getSendFrom() {
	// return sendFrom;
	// }
	//
	// public void setSendFrom(String sendFrom) {
	// this.sendFrom = sendFrom;
	// }

	public String getApprove_sp_id() {
		return this.approve_sp_id;
	}

	public void setApprove_sp_id(String approve_sp_id) {
		this.approve_sp_id = approve_sp_id;
	}

	public String getPre_key() {
		return this.pre_key;
	}

	public void setPre_key(String pre_key) {
		this.pre_key = pre_key;
	}

	public String getComment() {
		return this.comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	public String getIs_foreign() {
		return this.is_foreign;
	}

	public void setIs_foreign(String is_foreign) {
		this.is_foreign = is_foreign;
	}

	public String getTarget_service() {
		return this.target_service;
	}

	public void setTarget_service(String target_service) {
		this.target_service = target_service;
	}

	public String getService_token() {
		return this.service_token;
	}

	public void setService_token(String service_token) {
		this.service_token = service_token;
	}

	public String getService_secret() {
		return this.service_secret;
	}

	public void setService_secret(String service_secret) {
		this.service_secret = service_secret;
	}

	public String getService_id() {
		return this.service_id;
	}

	public void setService_id(String service_id) {
		this.service_id = service_id;
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
