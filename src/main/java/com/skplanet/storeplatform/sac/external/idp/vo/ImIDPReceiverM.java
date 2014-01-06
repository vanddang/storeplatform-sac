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
 * 
 * ImIDPReceiverM class
 * 
 * Updated on : 2014. 1. 6. Updated by : Jeon.ByungYoul, SK planet.
 */
public class ImIDPReceiverM {

	private ResponseHeader responseHeader;
	private ResponseBody responseBody;

	public class ResponseHeader {
		/** 처리 완료된 command */
		private String cmd;
		/** 처리 결과 코드 */
		private String result;
		/** 처리 결과 메세지 */
		private String result_text;

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

	public class ResponseBody {
		private String trx_no = "";
		private String im_int_svc_no = "";
		private String user_id = "";
		private String user_key = "";
		private String user_passwd_type = "";
		private String user_passwd_modify_date = "";
		private String auth_type = "";
		private String user_tn = "";
		private String user_tn_nation_cd = "";
		private String user_tn_type = "";
		private String is_user_tn_auth = "";
		private String is_user_tn_own = "";
		private String user_email = "";
		private String is_email_auth = "";
		private String user_type = "";
		private String user_name = "";
		private String is_rname_auth = "";
		private String rname_auth_sst_code = "";
		private String biz_number = "";
		private String biz_name = "";
		private String is_biz_auth = "";
		private String user_mdn = "";
		private String user_sex = "";
		private String user_birthday = "";
		private String user_calendar = "";
		private String user_zipcode = "";
		private String user_address = "";
		private String user_address2 = "";
		private String user_nation_code = "";
		private String user_nation_name = "";
		private String is_foreign = "";
		private String is_im_changed = "";
		private String user_status_code = "";
		private String is_parent_approve = "";
		private String parent_approve_sst_code = "";
		private String join_sst_list = "";
		private String join_path_code = "";
		private String join_sst_code = "";
		private String join_date = "";
		private String join_time = "";
		private String join_ip = "";
		private String is_term = "";
		private String term_reason_cd = "";
		private String sst_term = "";
		private String login_status_code = "";
		private String login_limit_sst_code = "";
		private String modify_sst_code = "";
		private String modify_req_date = "";
		private String modify_req_time = "";
		private String dup_sst = "";
		private String auth_code = "";
		private String use_sst = "";
		private String user_auth_key = "";
		private String sign_data = "";
		private String user_passwd = "";
		private String parent_approve_sst_cd = "";
		private String sp_list = "";
		private String mobile_sign = "";
		private String svc_mng_num = "";
		private String model_id = "";
		private String idp_user_status_cd = "";
		private String im_user_status_cd = "";
		private String phone_auth_code = "";
		private String old_id = "";
		private String user_ci = "";

		// ONE ID 2.0 TX
		private String rname_auth_mns_code = "";// 실명인증 수단 코드 1: 휴대폰, 2: 아이핀
		private String rname_auth_mbr_code = "";// 실명인증 회원 코드 10 : 내국인, 20: 외국인
		private String rname_auth_date = "";// 실명인증 일시 . 14 자리
		private String is_ocb_term_req = "";// OCB 해지 요청 여부 Y= 해지 요청 N= 해지 철회
		private String ocb_term_date = "";// OCB 해지 요청 일시 (YYYYMMDDHH24MISS)
		private String browser_code = "";// Browser 코드 I: IE, S: Safari, C: Chrome, D: Dolphin, O: Opera, F: Firefox T:
										 // Tizen W: Wave, E: 기타
		private String rname_auth_type_cd = "";// 실명 인증 유형 코드
		private String user_di = "";// DI (64 byte)
		private String lang_code = "";// 발송 언어 KOR=한국어, ENG=영어, JPN:일본어 default=KOR
		private String is_mkt_email_recv = "";// 마케팅 메일 수신 여부 Y: 동의, N: 미동의
		private String parent_type = "";// 법정대리인관계코드 0:부, 1:모, 2:기타
		private String parent_rname_auth_type = "";// 법정대리인실명인증수단코드 1:휴대폰 본인인증, , 3:IPIN, 6:이메일 (외국인 법정대리인 인증)

		/*
		 * parent_rname_auth_key 법정대리인실명인증값(해외는 미획득) CI 값(88bytes) is_parent_approve 이고, parent_rname_auth_type 이 6 이면,
		 * 해당 필드는 null 이다.
		 */
		private String parent_rname_auth_key = "";
		private String parent_name = ""; // 법정대리인이름
		private String parent_birthday = ""; // 법정대리인 생년월일
		private String parent_email = ""; // 법정대리인 이메일
		private String parent_approve_date = ""; // 법정대리인동의일자 (YYYYMMDD)

		private String user_area_code = ""; // 사용자지역코드(ISO 3166-2)
		private String user_area_name = ""; // 사용자지역이름
		private String user_city_name = ""; // 사용자도시이름
		private String im_mem_type_cd = ""; // 통합회원 유형 코드 100: 국내회원, 900: 글로벌회원
		private String sus_status_code = ""; // 직권중지 상태 코드 80:직권중지 해제, 90:직권중지 설정
		private String via_sst_code = ""; // 경유 서비스 사이트 코드
		private String ocb_join_code = ""; // 통합포인트 가입 여부 Y=가입, N=미가입
		private String service_profiles = ""; // 사용자가추가로입력한프로파일전체리스트전달(“name=Value”리스트)
		private String emailYn = ""; //
		private String PAS_INFO = ""; //

		private String sst_ter_list = "";
		private String join_term_cd = "";

		private String os_code = ""; // OS 코드(A: Android, I: iOS, T: Tizen, B: Bada)

		public String getSst_ter_list() {
			return this.sst_ter_list;
		}

		public void setSst_ter_list(String sst_ter_list) {
			this.sst_ter_list = sst_ter_list;
		}

		public String getJoin_term_cd() {
			return this.join_term_cd;
		}

		public void setJoin_term_cd(String join_term_cd) {
			this.join_term_cd = join_term_cd;
		}

		public String getTrx_no() {
			return this.trx_no;
		}

		public void setTrx_no(String trx_no) {
			this.trx_no = trx_no;
		}

		public String getIm_int_svc_no() {
			return this.im_int_svc_no;
		}

		public void setIm_int_svc_no(String im_int_svc_no) {
			this.im_int_svc_no = im_int_svc_no;
		}

		public String getUser_id() {
			return this.user_id;
		}

		public void setUser_id(String user_id) {
			this.user_id = user_id;
		}

		public String getUser_key() {
			return this.user_key;
		}

		public void setUser_key(String user_key) {
			this.user_key = user_key;
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

		public String getIs_user_tn_auth() {
			return this.is_user_tn_auth;
		}

		public void setIs_user_tn_auth(String is_user_tn_auth) {
			this.is_user_tn_auth = is_user_tn_auth;
		}

		public String getIs_user_tn_own() {
			return this.is_user_tn_own;
		}

		public void setIs_user_tn_own(String is_user_tn_own) {
			this.is_user_tn_own = is_user_tn_own;
		}

		public String getUser_email() {
			return this.user_email;
		}

		public void setUser_email(String user_email) {
			this.user_email = user_email;
		}

		public String getIs_email_auth() {
			return this.is_email_auth;
		}

		public void setIs_email_auth(String is_email_auth) {
			this.is_email_auth = is_email_auth;
		}

		public String getUser_type() {
			return this.user_type;
		}

		public void setUser_type(String user_type) {
			this.user_type = user_type;
		}

		public String getUser_name() {
			return this.user_name;
		}

		public void setUser_name(String user_name) {
			this.user_name = user_name;
		}

		public String getIs_rname_auth() {
			return this.is_rname_auth;
		}

		public void setIs_rname_auth(String is_rname_auth) {
			this.is_rname_auth = is_rname_auth;
		}

		public String getRname_auth_sst_code() {
			return this.rname_auth_sst_code;
		}

		public void setRname_auth_sst_code(String rname_auth_sst_code) {
			this.rname_auth_sst_code = rname_auth_sst_code;
		}

		public String getBiz_number() {
			return this.biz_number;
		}

		public void setBiz_number(String biz_number) {
			this.biz_number = biz_number;
		}

		public String getBiz_name() {
			return this.biz_name;
		}

		public void setBiz_name(String biz_name) {
			this.biz_name = biz_name;
		}

		public String getIs_biz_auth() {
			return this.is_biz_auth;
		}

		public void setIs_biz_auth(String is_biz_auth) {
			this.is_biz_auth = is_biz_auth;
		}

		public String getUser_mdn() {
			return this.user_mdn;
		}

		public void setUser_mdn(String user_mdn) {
			this.user_mdn = user_mdn;
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

		public String getUser_calendar() {
			return this.user_calendar;
		}

		public void setUser_calendar(String user_calendar) {
			this.user_calendar = user_calendar;
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

		public String getUser_nation_code() {
			return this.user_nation_code;
		}

		public void setUser_nation_code(String user_nation_code) {
			this.user_nation_code = user_nation_code;
		}

		public String getUser_nation_name() {
			return this.user_nation_name;
		}

		public void setUser_nation_name(String user_nation_name) {
			this.user_nation_name = user_nation_name;
		}

		public String getIs_foreign() {
			return this.is_foreign;
		}

		public void setIs_foreign(String is_foreign) {
			this.is_foreign = is_foreign;
		}

		public String getIs_im_changed() {
			return this.is_im_changed;
		}

		public void setIs_im_changed(String is_im_changed) {
			this.is_im_changed = is_im_changed;
		}

		public String getUser_status_code() {
			return this.user_status_code;
		}

		public void setUser_status_code(String user_status_code) {
			this.user_status_code = user_status_code;
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

		public String getJoin_sst_list() {
			return this.join_sst_list;
		}

		public void setJoin_sst_list(String join_sst_list) {
			this.join_sst_list = join_sst_list;
		}

		public String getJoin_path_code() {
			return this.join_path_code;
		}

		public void setJoin_path_code(String join_path_code) {
			this.join_path_code = join_path_code;
		}

		public String getJoin_sst_code() {
			return this.join_sst_code;
		}

		public void setJoin_sst_code(String join_sst_code) {
			this.join_sst_code = join_sst_code;
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

		public String getJoin_ip() {
			return this.join_ip;
		}

		public void setJoin_ip(String join_ip) {
			this.join_ip = join_ip;
		}

		public String getIs_term() {
			return this.is_term;
		}

		public void setIs_term(String is_term) {
			this.is_term = is_term;
		}

		public String getTerm_reason_cd() {
			return this.term_reason_cd;
		}

		public void setTerm_reason_cd(String term_reason_cd) {
			this.term_reason_cd = term_reason_cd;
		}

		public String getSst_term() {
			return this.sst_term;
		}

		public void setSst_term(String sst_term) {
			this.sst_term = sst_term;
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

		public String getModify_sst_code() {
			return this.modify_sst_code;
		}

		public void setModify_sst_code(String modify_sst_code) {
			this.modify_sst_code = modify_sst_code;
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

		public String getDup_sst() {
			return this.dup_sst;
		}

		public void setDup_sst(String dup_sst) {
			this.dup_sst = dup_sst;
		}

		public String getAuth_code() {
			return this.auth_code;
		}

		public void setAuth_code(String auth_code) {
			this.auth_code = auth_code;
		}

		public String getUse_sst() {
			return this.use_sst;
		}

		public void setUse_sst(String use_sst) {
			this.use_sst = use_sst;
		}

		public String getUser_auth_key() {
			return this.user_auth_key;
		}

		public void setUser_auth_key(String user_auth_key) {
			this.user_auth_key = user_auth_key;
		}

		public String getSign_data() {
			return this.sign_data;
		}

		public void setSign_data(String sign_data) {
			this.sign_data = sign_data;
		}

		public String getUser_passwd() {
			return this.user_passwd;
		}

		public void setUser_passwd(String user_passwd) {
			this.user_passwd = user_passwd;
		}

		public String getParent_approve_sst_cd() {
			return this.parent_approve_sst_cd;
		}

		public void setParent_approve_sst_cd(String parent_approve_sst_cd) {
			this.parent_approve_sst_cd = parent_approve_sst_cd;
		}

		public String getSp_list() {
			return this.sp_list;
		}

		public void setSp_list(String sp_list) {
			this.sp_list = sp_list;
		}

		public String getMobile_sign() {
			return this.mobile_sign;
		}

		public void setMobile_sign(String mobile_sign) {
			this.mobile_sign = mobile_sign;
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

		public String getIdp_user_status_cd() {
			return this.idp_user_status_cd;
		}

		public void setIdp_user_status_cd(String idp_user_status_cd) {
			this.idp_user_status_cd = idp_user_status_cd;
		}

		public String getIm_user_status_cd() {
			return this.im_user_status_cd;
		}

		public void setIm_user_status_cd(String im_user_status_cd) {
			this.im_user_status_cd = im_user_status_cd;
		}

		public String getPhone_auth_code() {
			return this.phone_auth_code;
		}

		public void setPhone_auth_code(String phone_auth_code) {
			this.phone_auth_code = phone_auth_code;
		}

		public String getOld_id() {
			return this.old_id;
		}

		public void setOld_id(String old_id) {
			this.old_id = old_id;
		}

		public String getUser_ci() {
			return this.user_ci;
		}

		public void setUser_ci(String user_ci) {
			this.user_ci = user_ci;
		}

		public String getRname_auth_mns_code() {
			return this.rname_auth_mns_code;
		}

		public void setRname_auth_mns_code(String rname_auth_mns_code) {
			this.rname_auth_mns_code = rname_auth_mns_code;
		}

		public String getRname_auth_mbr_code() {
			return this.rname_auth_mbr_code;
		}

		public void setRname_auth_mbr_code(String rname_auth_mbr_code) {
			this.rname_auth_mbr_code = rname_auth_mbr_code;
		}

		public String getRname_auth_date() {
			return this.rname_auth_date;
		}

		public void setRname_auth_date(String rname_auth_date) {
			this.rname_auth_date = rname_auth_date;
		}

		public String getIs_ocb_term_req() {
			return this.is_ocb_term_req;
		}

		public void setIs_ocb_term_req(String is_ocb_term_req) {
			this.is_ocb_term_req = is_ocb_term_req;
		}

		public String getOcb_term_date() {
			return this.ocb_term_date;
		}

		public void setOcb_term_date(String ocb_term_date) {
			this.ocb_term_date = ocb_term_date;
		}

		public String getBrowser_code() {
			return this.browser_code;
		}

		public void setBrowser_code(String browser_code) {
			this.browser_code = browser_code;
		}

		public String getRname_auth_type_cd() {
			return this.rname_auth_type_cd;
		}

		public void setRname_auth_type_cd(String rname_auth_type_cd) {
			this.rname_auth_type_cd = rname_auth_type_cd;
		}

		public String getUser_di() {
			return this.user_di;
		}

		public void setUser_di(String user_di) {
			this.user_di = user_di;
		}

		public String getLang_code() {
			return this.lang_code;
		}

		public void setLang_code(String lang_code) {
			this.lang_code = lang_code;
		}

		public String getIs_mkt_email_recv() {
			return this.is_mkt_email_recv;
		}

		public void setIs_mkt_email_recv(String is_mkt_email_recv) {
			this.is_mkt_email_recv = is_mkt_email_recv;
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

		public String getParent_name() {
			return this.parent_name;
		}

		public void setParent_name(String parent_name) {
			this.parent_name = parent_name;
		}

		public String getParent_birthday() {
			return this.parent_birthday;
		}

		public void setParent_birthday(String parent_birthday) {
			this.parent_birthday = parent_birthday;
		}

		public String getParent_email() {
			return this.parent_email;
		}

		public void setParent_email(String parent_email) {
			this.parent_email = parent_email;
		}

		public String getParent_approve_date() {
			return this.parent_approve_date;
		}

		public void setParent_approve_date(String parent_approve_date) {
			this.parent_approve_date = parent_approve_date;
		}

		public String getUser_area_code() {
			return this.user_area_code;
		}

		public void setUser_area_code(String user_area_code) {
			this.user_area_code = user_area_code;
		}

		public String getUser_area_name() {
			return this.user_area_name;
		}

		public void setUser_area_name(String user_area_name) {
			this.user_area_name = user_area_name;
		}

		public String getUser_city_name() {
			return this.user_city_name;
		}

		public void setUser_city_name(String user_city_name) {
			this.user_city_name = user_city_name;
		}

		public String getIm_mem_type_cd() {
			return this.im_mem_type_cd;
		}

		public void setIm_mem_type_cd(String im_mem_type_cd) {
			this.im_mem_type_cd = im_mem_type_cd;
		}

		public String getSus_status_code() {
			return this.sus_status_code;
		}

		public void setSus_status_code(String sus_status_code) {
			this.sus_status_code = sus_status_code;
		}

		public String getVia_sst_code() {
			return this.via_sst_code;
		}

		public void setVia_sst_code(String via_sst_code) {
			this.via_sst_code = via_sst_code;
		}

		public String getOcb_join_code() {
			return this.ocb_join_code;
		}

		public void setOcb_join_code(String ocb_join_code) {
			this.ocb_join_code = ocb_join_code;
		}

		public String getService_profiles() {
			return this.service_profiles;
		}

		public void setService_profiles(String service_profiles) {
			this.service_profiles = service_profiles;
		}

		public String getEmailYn() {
			return this.emailYn;
		}

		public void setEmailYn(String emailYn) {
			this.emailYn = emailYn;
		}

		public String getPAS_INFO() {
			return this.PAS_INFO;
		}

		public void setPAS_INFO(String pas_info) {
			this.PAS_INFO = pas_info;
		}

		public String getOs_code() {
			return this.os_code;
		}

		public void setOs_code(String os_code) {
			this.os_code = os_code;
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
