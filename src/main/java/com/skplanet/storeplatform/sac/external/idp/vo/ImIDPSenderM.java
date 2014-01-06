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

/**
 * ImIDPSenderM Class
 * 
 * Updated on : 2014. 1. 6. Updated by : Jeon.ByungYoul, SK planet.
 */
public class ImIDPSenderM {
	/* 연동 요청 (공통 결과 API) */
	/** 세부 요청 command */
	private String cmd;
	/** 등록된 SP ID */
	private String sp_id;
	/** 요청 인증을 위한 signature */
	private String sp_auth_key;
	/** 처리 결과 Data Format */
	private String resp_type; // 1 : "name=vlaue", 2 : XML default=1
	/** 처리결과 전달 방식 */
	private String resp_flow; // resp : response 방식, redt : redirect 방식 default=resp
	/** redirect 할 url */
	private String resp_url;

	/* 연동 요청 (기본 API) */
	/** 서비스 중복 가입 체크 key 종류 */
	private String key_type; // 1 : IM 통합서비스 no, 2 : IM 통합ID default=1

	/** 서비스 중복 가입을 체크 할 값 */
	private String key;

	/** 인증 키 */
	private String user_auth_key;

	/** 사용자 아이디 */
	private String user_id;

	/** 사용자 패스워드 */
	private String user_passwd;

	/** 사용자 패스워드 타입 */
	private String user_passwd_type;

	/** 사용자 패스워드 변경일 */
	private String user_passwd_modify_date;

	/** 인증 유형 코드 */
	private String auth_type;

	/** 필수 전화번호 */
	private String user_tn;

	/** 필수 전화번호 소유여부 */
	private String is_user_tn_own;

	private String is_user_tn_auth;

	/** 필수 전화번호 국가코드 */
	private String user_tn_nation_cd;

	/** 필수 전화번호 유형 */
	private String user_tn_type;

	/** 사용자 email */
	private String user_email;

	/** 사용자 email 인증 여부 */
	private String is_email_auth;

	/** 회원 유형 */
	private String user_type;

	/** 사용자 이름 or 국내 거주 외국인 이름 */
	private String user_name;

	/** 실명인증여부 */
	private String is_rname_auth;

	/** 인증 할 휴대폰번호 */
	private String user_mdn;

	/** 인증 할 휴대폰번호 signature */
	private String user_mdn_auth_key;

	/** 성별 */
	private String user_sex;

	/** 생년월일 */
	private String user_birthday;

	/** 양음력 여부 */
	private String user_calendar;

	/** 우편번호 */
	private String user_zipcode;

	/** 거주지 주소 */
	private String user_address;

	/** 거주지 상세주소 */
	private String user_address2;

	/** 국가 코드 */
	private String user_nation_code;
	private String user_nation_name;

	/** 언어 */
	private String lang_code;

	/** 통합 전환 여부 */
	private String is_im_changed;

	/** 일괄전환 대상 서비스 사이트 */
	private String tans_sst_list;

	/** 가입자 상태 코드 */
	private String user_status_code;

	/** 부모 관계 코드 */
	private String parent_type;

	/** 부모 실명인증 수단 코드 */
	private String parent_rname_auth_type;

	/** 부모 실명인증 CI값 */
	private String parent_rname_auth_key;

	/** 부모 이름 */
	private String parent_name;

	/** 부모 전화번호 */
	private String parent_mdn;

	/** 부모 이메일 */
	private String parent_email;

	/** 부모 동의 일자 */
	private String parent_approve_date;

	/** 부모 동의 여부 */
	private String is_parent_approve;

	/** 부모 동의 서비스 사이트 코드 */
	private String parent_approve_sst_code;

	/** 약관동의 정보 */
	private String consent_tac;

	/** 가입경로 코드 */
	private String join_path_code;

	/** 가입일자 */
	private String join_date;

	/** 가입시간 */
	private String join_time;

	/** 변경 요청 일자 */
	private String modify_req_date;

	/** 변경 요청 시간 */
	private String modify_req_time;

	/** 해지 사유 코드 */
	private String term_reason_cd;

	/** 요청 일자 */
	private String req_date;

	/** 요청 시간 */
	private String req_time;

	/** CI값 */
	private String ipin_ci;

	/** 로그인 상태 코드 */
	private String login_status_code;

	/** 제한 서비스사이트 코드 */
	private String login_limit_sst_code;

	/** 서비스 사이트명 */
	private String service_name;

	/** 통합 서비스 번호 */
	private String im_int_svc_no;

	/** 서비스 관리 번호 */
	private String svc_mng_num;

	/** 단말 모델ID */
	private String model_id;

	/** 사용자 주민번호 or 외국인번호 */
	private String user_social_number;

	/** 이통사 구분 */
	private String user_mdn_type;

	/** 입력한 휴대폰 인증 코드 */
	private String user_code;

	/** IDP로 부터 발행 받은 mobile_sign value */
	private String mobile_sign;

	/** IDP로 부터 전달 받은 sign_data value */
	private String sign_data;

	private String url;

	// TX(provisioning)시 사용
	private String rname_auth_sst_code;

	private String rname_auth_type_cd;

	private String join_sst_code;

	private String modify_sst_code;

	private String join_sst_list;

	private String target_sst_cd;

	private String sn_auth_key;

	private String old_id;

	private String is_biz_auth;

	private String user_ci;
	private String user_di;//

	// OneID20적용 후 추가
	/* 트랜잭션 번호 */
	private String trx_no;
	/* 직권중지 상태 코드 */
	private String sus_status_code;

	// ONE ID 2.0 TX
	private String udt_type_cd;// 업데이트 구분 코드 1: TN, 2:EM, 3:TN + EM 4: 부가정보 (lang_code, is_mkt_email_recv 만 업데이트 할 경우)
							   // 5: 법인정보 (biz_number, biz_name, is_biz_auth 필수 값)
	private String rname_auth_mns_code;// 실명인증 수단 코드 1: 휴대폰, 2: 아이핀
	private String rname_auth_date;// 실명인증 일시 – 14 자리
	private String parent_birthday;// 법정대리인 생년월일
	private String emailYn;//
	private String marketingYn;//

	public String getTarget_sst_cd() {
		return this.target_sst_cd;
	}

	public void setTarget_sst_cd(String target_sst_cd) {
		this.target_sst_cd = target_sst_cd;
	}

	public String getJoin_sst_list() {
		return this.join_sst_list;
	}

	public void setJoin_sst_list(String join_sst_list) {
		this.join_sst_list = join_sst_list;
	}

	public String getModify_sst_code() {
		return this.modify_sst_code;
	}

	public void setModify_sst_code(String modify_sst_code) {
		this.modify_sst_code = modify_sst_code;
	}

	public String getJoin_sst_code() {
		return this.join_sst_code;
	}

	public void setJoin_sst_code(String join_sst_code) {
		this.join_sst_code = join_sst_code;
	}

	public String getRname_auth_sst_code() {
		return this.rname_auth_sst_code;
	}

	public void setRname_auth_sst_code(String rname_auth_sst_code) {
		this.rname_auth_sst_code = rname_auth_sst_code;
	}

	public String getIs_user_tn_auth() {
		return this.is_user_tn_auth;
	}

	public void setIs_user_tn_auth(String is_user_tn_auth) {
		this.is_user_tn_auth = is_user_tn_auth;
	}

	public String getUrl() {
		return this.url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getUser_tn() {
		return this.user_tn;
	}

	public void setUser_tn(String user_tn) {
		this.user_tn = user_tn;
	}

	public String getUser_tn_nation_cd() {
		return this.user_tn_nation_cd;
	}

	public void setUser_tn_nation_cd(String user_tn_nation_cd) {
		this.user_tn_nation_cd = user_tn_nation_cd;
	}

	public String getUser_tn_type() {
		return this.user_tn_type;
	}

	public void setUser_tn_type(String user_tn_type) {
		this.user_tn_type = user_tn_type;
	}

	public String getReq_date() {
		return this.req_date;
	}

	public void setReq_date(String req_date) {
		this.req_date = req_date;
	}

	public String getReq_time() {
		return this.req_time;
	}

	public String getSvc_mng_num() {
		return this.svc_mng_num;
	}

	public void setSvc_mng_num(String svc_mng_num) {
		this.svc_mng_num = svc_mng_num;
	}

	public String getModel_id() {
		return this.model_id;
	}

	public void setModel_id(String model_id) {
		this.model_id = model_id;
	}

	public String getIm_int_svc_no() {
		return this.im_int_svc_no;
	}

	public void setIm_int_svc_no(String im_int_svc_no) {
		this.im_int_svc_no = im_int_svc_no;
	}

	public void setReq_time(String req_time) {
		this.req_time = req_time;
	}

	public String getService_name() {
		return this.service_name;
	}

	public void setService_name(String service_name) {
		this.service_name = service_name;
	}

	public String getLogin_status_code() {
		return this.login_status_code;
	}

	public void setLogin_status_code(String login_status_code) {
		this.login_status_code = login_status_code;
	}

	public String getLogin_limit_sst_code() {
		return this.login_limit_sst_code;
	}

	public void setLogin_limit_sst_code(String login_limit_sst_code) {
		this.login_limit_sst_code = login_limit_sst_code;
	}

	public String getIpin_ci() {
		return this.ipin_ci;
	}

	public void setIpin_ci(String ipin_ci) {
		this.ipin_ci = ipin_ci;
	}

	public String getUser_calendar() {
		return this.user_calendar;
	}

	public String getIs_user_tn_own() {
		return this.is_user_tn_own;
	}

	public void setIs_user_tn_own(String is_user_tn_own) {
		this.is_user_tn_own = is_user_tn_own;
	}

	public String getIs_email_auth() {
		return this.is_email_auth;
	}

	public void setIs_email_auth(String is_email_auth) {
		this.is_email_auth = is_email_auth;
	}

	public String getTerm_reason_cd() {
		return this.term_reason_cd;
	}

	public void setTerm_reason_cd(String term_reason_cd) {
		this.term_reason_cd = term_reason_cd;
	}

	public String getUser_mdn_auth_key() {
		return this.user_mdn_auth_key;
	}

	public void setUser_mdn_auth_key(String user_mdn_auth_key) {
		this.user_mdn_auth_key = user_mdn_auth_key;
	}

	public String getUser_nation_code() {
		return this.user_nation_code;
	}

	public void setUser_nation_code(String user_nation_code) {
		this.user_nation_code = user_nation_code;
	}

	public String getLang_code() {
		return this.lang_code;
	}

	public void setLang_code(String lang_code) {
		this.lang_code = lang_code;
	}

	public String getIs_im_changed() {
		return this.is_im_changed;
	}

	public void setIs_im_changed(String is_im_changed) {
		this.is_im_changed = is_im_changed;
	}

	public String getTans_sst_list() {
		return this.tans_sst_list;
	}

	public void setTans_sst_list(String tans_sst_list) {
		this.tans_sst_list = tans_sst_list;
	}

	public String getUser_status_code() {
		return this.user_status_code;
	}

	public void setUser_status_code(String user_status_code) {
		this.user_status_code = user_status_code;
	}

	public String getParent_type() {
		return this.parent_type;
	}

	public void setParent_type(String parent_type) {
		this.parent_type = parent_type;
	}

	public String getParent_rname_auth_type() {
		return this.parent_rname_auth_type;
	}

	public void setParent_rname_auth_type(String parent_rname_auth_type) {
		this.parent_rname_auth_type = parent_rname_auth_type;
	}

	public String getParent_rname_auth_key() {
		return this.parent_rname_auth_key;
	}

	public void setParent_rname_auth_key(String parent_rname_auth_key) {
		this.parent_rname_auth_key = parent_rname_auth_key;
	}

	public String getParent_approve_date() {
		return this.parent_approve_date;
	}

	public void setParent_approve_date(String parent_approve_date) {
		this.parent_approve_date = parent_approve_date;
	}

	public String getIs_parent_approve() {
		return this.is_parent_approve;
	}

	public void setIs_parent_approve(String is_parent_approve) {
		this.is_parent_approve = is_parent_approve;
	}

	public String getParent_approve_sst_code() {
		return this.parent_approve_sst_code;
	}

	public void setParent_approve_sst_code(String parent_approve_sst_code) {
		this.parent_approve_sst_code = parent_approve_sst_code;
	}

	public String getConsent_tac() {
		return this.consent_tac;
	}

	public void setConsent_tac(String consent_tac) {
		this.consent_tac = consent_tac;
	}

	public String getJoin_path_code() {
		return this.join_path_code;
	}

	public void setJoin_path_code(String join_path_code) {
		this.join_path_code = join_path_code;
	}

	public String getJoin_date() {
		return this.join_date;
	}

	public void setJoin_date(String join_date) {
		this.join_date = join_date;
	}

	public String getJoin_time() {
		return this.join_time;
	}

	public void setJoin_time(String join_time) {
		this.join_time = join_time;
	}

	public String getModify_req_date() {
		return this.modify_req_date;
	}

	public void setModify_req_date(String modify_req_date) {
		this.modify_req_date = modify_req_date;
	}

	public String getModify_req_time() {
		return this.modify_req_time;
	}

	public void setModify_req_time(String modify_req_time) {
		this.modify_req_time = modify_req_time;
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

	public String getOld_id() {
		return this.old_id;
	}

	public void setOld_id(String old_id) {
		this.old_id = old_id;
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

	public String getUser_passwd_type() {
		return this.user_passwd_type;
	}

	public void setUser_passwd_type(String user_passwd_type) {
		this.user_passwd_type = user_passwd_type;
	}

	public String getUser_passwd_modify_date() {
		return this.user_passwd_modify_date;
	}

	public void setUser_passwd_modify_date(String user_passwd_modify_date) {
		this.user_passwd_modify_date = user_passwd_modify_date;
	}

	public String getAuth_type() {
		return this.auth_type;
	}

	public void setAuth_type(String auth_type) {
		this.auth_type = auth_type;
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

	public String getUser_email() {
		return this.user_email;
	}

	public void setUser_email(String user_email) {
		this.user_email = user_email;
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

	public String getUser_type() {
		return this.user_type;
	}

	public void setUser_type(String user_type) {
		this.user_type = user_type;
	}

	public String getParent_name() {
		return this.parent_name;
	}

	public void setParent_name(String parent_name) {
		this.parent_name = parent_name;
	}

	public String getParent_mdn() {
		return this.parent_mdn;
	}

	public void setParent_mdn(String parent_mdn) {
		this.parent_mdn = parent_mdn;
	}

	public String getUser_auth_key() {
		return this.user_auth_key;
	}

	public void setUser_auth_key(String user_auth_key) {
		this.user_auth_key = user_auth_key;
	}

	public String getSn_auth_key() {
		return this.sn_auth_key;
	}

	public void setSn_auth_key(String sn_auth_key) {
		this.sn_auth_key = sn_auth_key;
	}

	public String getParent_email() {
		return this.parent_email;
	}

	public void setParent_email(String parent_email) {
		this.parent_email = parent_email;
	}

	public String getIs_biz_auth() {
		return this.is_biz_auth;
	}

	public void setIs_biz_auth(String is_biz_auth) {
		this.is_biz_auth = is_biz_auth;
	}

	public String getUser_nation_name() {
		return this.user_nation_name;
	}

	public void setUser_nation_name(String user_nation_name) {
		this.user_nation_name = user_nation_name;
	}

	public String getUser_ci() {
		return this.user_ci;
	}

	public void setUser_ci(String user_ci) {
		this.user_ci = user_ci;
	}

	public String getSus_status_code() {
		return this.sus_status_code;
	}

	public void setSus_status_code(String sus_status_code) {
		this.sus_status_code = sus_status_code;
	}

	public String getTrx_no() {
		return this.trx_no;
	}

	public void setTrx_no(String trx_no) {
		this.trx_no = trx_no;
	}

	public String getUdt_type_cd() {
		return this.udt_type_cd;
	}

	public void setUdt_type_cd(String udt_type_cd) {
		this.udt_type_cd = udt_type_cd;
	}

	public String getRname_auth_mns_code() {
		return this.rname_auth_mns_code;
	}

	public void setRname_auth_mns_code(String rname_auth_mns_code) {
		this.rname_auth_mns_code = rname_auth_mns_code;
	}

	public String getUser_di() {
		return this.user_di;
	}

	public void setUser_di(String user_di) {
		this.user_di = user_di;
	}

	public String getRname_auth_date() {
		return this.rname_auth_date;
	}

	public void setRname_auth_date(String rname_auth_date) {
		this.rname_auth_date = rname_auth_date;
	}

	public String getParent_birthday() {
		return this.parent_birthday;
	}

	public void setParent_birthday(String parent_birthday) {
		this.parent_birthday = parent_birthday;
	}

	public String getEmailYn() {
		return this.emailYn;
	}

	public void setEmailYn(String emailYn) {
		this.emailYn = emailYn;
	}

	public String getMarketingYn() {
		return this.marketingYn;
	}

	public void setMarketingYn(String marketingYn) {
		this.marketingYn = marketingYn;
	}

	public String getRname_auth_type_cd() {
		return this.rname_auth_type_cd;
	}

	public void setRname_auth_type_cd(String rname_auth_type_cd) {
		this.rname_auth_type_cd = rname_auth_type_cd;
	}

}
