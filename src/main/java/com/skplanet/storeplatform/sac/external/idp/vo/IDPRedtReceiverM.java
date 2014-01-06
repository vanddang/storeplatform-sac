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

import com.skplanet.storeplatform.sac.external.idp.util.IdpCommonUtil;

/**
 * IDP redirect 연동 결과를 받는 객체
 * 
 * @author 양영열
 * 
 */
public class IDPRedtReceiverM {
	/** 처리 완료된 command */
	private String cmd;
	/** 처리 결과 코드 */
	private String result;
	/** 처리 결과 메세지 */
	private String result_text;

	/* 연동 결과 (기본 API) */
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

	/* 연동 결과 (회원 가입 API) */
	/** 가입 본인 인증을 수행할 request url */
	private String email_auth_link;
	/** IDP 가 발행하는 회원 identity key */
	private String user_key;
	/** 가입 승인된 사용자 ID */
	private String user_id;
	/** */
	// private String common_profile; IDP를 통해 돌려 받아야 하는 값들 (공통부분)
	/** */
	// private String service_profile; IDP를 통해 돌려 받아야 하는 값들 (서비스부분)

	/* 연동 결과 (비실명 회원 인증 API) */
	/** 사용자 인증시 IDP 에서 발행된 signature */
	private String user_auth_key;
	/** 인증된 무선 전용회원 MDN */
	private String user_mdn;

	/* 연동 결과 (회원 정보 조회 API) */
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
	/** 생일구분 (양/음력) */
	private String user_calendar;
	/** 실명인증여부 */
	private String is_rname_auth;
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
	/** email 승인 요청 여부 **/
	private String is_email_approved;
	/** */
	// private String profile_name; 조회 요청한 Profile 값들

	/** 연동 결과 (회원 정보 변경 API) */
	/** */
	// private String common_profile; Provisioning 대상 Field로 설정된 것들 (공통부분)
	/** */
	// private String service_profile; IDP를 통해 돌려 받아야 하는 값들 (서비스부분)

	private String[] idList;

	private String info;

	/** 회원가입신청 관련 */
	private String emailYn;
	private String birthCls;
	private String realNmAuthYn;
	private String nateIdUseYn;
	private String nateIdStartDt;
	private String homeAddr;
	private String homeAddrDtl;
	private String socialDate;
	private String exposureNm;

	private long mobileCnt;
	private String repHpYn; // 대표 휴대폰 번호 세팅.
	private String[] modelNm;
	private String[] hpNm;
	private String[] hpNo;
	private String[] mnoCd;
	private String[] smsYn;
	private String[] svcMngNum;
	private String[] uaCd;

	/** 사용자 ID i-topping 인지 여부 */
	private String id_itopping_id;

	/** 변경전 사용자 이메일 */
	private String pre_user_email;
	/** 변경 전/후 값 */
	private String key;
	private String pre_key;

	public String getExposureNm() {
		return this.exposureNm;
	}

	public void setExposureNm(String exposureNm) {
		this.exposureNm = exposureNm;
	}

	public String[] getUaCd() {
		return this.uaCd;
	}

	public void setUaCd(String[] uaCd) {
		this.uaCd = uaCd;
	}

	public String getSocialDate() {
		return this.socialDate;
	}

	public void setSocialDate(String socialDate) {
		socialDate = IdpCommonUtil.removeSpecial(socialDate);
		this.socialDate = socialDate;
	}

	public String getUser_address2() {
		return this.user_address2;
	}

	public void setUser_address2(String user_address2) {
		user_address2 = IdpCommonUtil.removeSpecial(user_address2);
		this.user_address2 = user_address2;
	}

	public String getKey() {
		return this.key;
	}

	public void setKey(String key) {
		key = IdpCommonUtil.removeSpecial(key);
		this.key = key;
	}

	public String getPre_key() {
		return this.pre_key;
	}

	public void setPre_key(String pre_key) {
		pre_key = IdpCommonUtil.removeSpecial(pre_key);
		this.pre_key = pre_key;
	}

	public String getPre_user_email() {
		return this.pre_user_email;
	}

	public void setPre_user_email(String pre_user_email) {
		// pre_user_email = CommonUtil.removeSpecial(pre_user_email);
		this.pre_user_email = pre_user_email;
	}

	public String getId_itopping_id() {
		return this.id_itopping_id;
	}

	public void setId_itopping_id(String id_itopping_id) {
		id_itopping_id = IdpCommonUtil.removeSpecial(id_itopping_id);
		this.id_itopping_id = id_itopping_id;
	}

	public String getIs_rname_auth() {
		return this.is_rname_auth;
	}

	public void setIs_rname_auth(String is_rname_auth) {
		is_rname_auth = IdpCommonUtil.removeSpecial(is_rname_auth);
		this.is_rname_auth = is_rname_auth;
	}

	public String getUser_calendar() {
		return this.user_calendar;
	}

	public void setUser_calendar(String user_calendar) {
		user_calendar = IdpCommonUtil.removeSpecial(user_calendar);
		this.user_calendar = user_calendar;
	}

	public String getRepHpYn() {
		return this.repHpYn;
	}

	public void setRepHpYn(String repHpYn) {
		repHpYn = IdpCommonUtil.removeSpecial(repHpYn);
		this.repHpYn = repHpYn;
	}

	public String[] getModelNm() {
		return this.modelNm;
	}

	public void setModelNm(String[] modelNm) {
		if (modelNm != null && modelNm.length > 0) {
			for (int i = 0; i < modelNm.length; i++) {
				if (modelNm[i] != null) {
					modelNm[i] = IdpCommonUtil.removeSpecial(modelNm[i]);
				}
			}
		}
		this.modelNm = modelNm;
	}

	public String[] getHpNm() {
		return this.hpNm;
	}

	public void setHpNm(String[] hpNm) {
		if (hpNm != null && hpNm.length > 0) {
			for (int i = 0; i < hpNm.length; i++) {
				if (hpNm[i] != null) {
					hpNm[i] = IdpCommonUtil.removeSpecial(hpNm[i]);
				}
			}
		}
		this.hpNm = hpNm;
	}

	public String[] getHpNo() {
		return this.hpNo;
	}

	public void setHpNo(String[] hpNo) {
		if (hpNo != null && hpNo.length > 0) {
			for (int i = 0; i < hpNo.length; i++) {
				if (hpNo[i] != null) {
					hpNo[i] = IdpCommonUtil.removeSpecial(hpNo[i]);
				}
			}
		}
		this.hpNo = hpNo;
	}

	public String[] getMnoCd() {
		return this.mnoCd;
	}

	public void setMnoCd(String[] mnoCd) {
		if (mnoCd != null && mnoCd.length > 0) {
			for (int i = 0; i < mnoCd.length; i++) {
				if (mnoCd[i] != null) {
					mnoCd[i] = IdpCommonUtil.removeSpecial(mnoCd[i]);
				}
			}
		}
		this.mnoCd = mnoCd;
	}

	public String[] getSmsYn() {
		return this.smsYn;
	}

	public void setSmsYn(String[] smsYn) {
		if (smsYn != null && smsYn.length > 0) {
			for (int i = 0; i < smsYn.length; i++) {
				if (smsYn[i] != null) {
					smsYn[i] = IdpCommonUtil.removeSpecial(smsYn[i]);
				}
			}
		}
		this.smsYn = smsYn;
	}

	public long getMobileCnt() {
		return this.mobileCnt;
	}

	public void setMobileCnt(long mobileCnt) {
		this.mobileCnt = mobileCnt;
	}

	public String getHomeAddr() {
		return this.homeAddr;
	}

	public void setHomeAddr(String homeAddr) {
		homeAddr = IdpCommonUtil.removeSpecial(homeAddr);
		this.homeAddr = homeAddr;
	}

	public String getHomeAddrDtl() {
		return this.homeAddrDtl;
	}

	public void setHomeAddrDtl(String homeAddrDtl) {
		homeAddrDtl = IdpCommonUtil.removeSpecial(homeAddrDtl);
		this.homeAddrDtl = homeAddrDtl;
	}

	public String getNateIdStartDt() {
		return this.nateIdStartDt;
	}

	public void setNateIdStartDt(String nateIdStartDt) {
		nateIdStartDt = IdpCommonUtil.removeSpecial(nateIdStartDt);
		this.nateIdStartDt = nateIdStartDt;
	}

	public String getEmailYn() {
		return this.emailYn;
	}

	public void setEmailYn(String emailYn) {
		emailYn = IdpCommonUtil.removeSpecial(emailYn);
		this.emailYn = emailYn;
	}

	public String getBirthCls() {
		return this.birthCls;
	}

	public void setBirthCls(String birthCls) {
		birthCls = IdpCommonUtil.removeSpecial(birthCls);
		this.birthCls = birthCls;
	}

	public String getRealNmAuthYn() {
		return this.realNmAuthYn;
	}

	public void setRealNmAuthYn(String realNmAuthYn) {
		realNmAuthYn = IdpCommonUtil.removeSpecial(realNmAuthYn);
		this.realNmAuthYn = realNmAuthYn;
	}

	public String getNateIdUseYn() {
		return this.nateIdUseYn;
	}

	public void setNateIdUseYn(String nateIdUseYn) {
		nateIdUseYn = IdpCommonUtil.removeSpecial(nateIdUseYn);
		this.nateIdUseYn = nateIdUseYn;
	}

	public String getInfo() {
		return this.info;
	}

	public void setInfo(String info) {
		info = IdpCommonUtil.removeSpecial(info);
		this.info = info;
	}

	public String getCmd() {
		return this.cmd;
	}

	public void setCmd(String cmd) {
		cmd = IdpCommonUtil.removeSpecial(cmd);
		this.cmd = cmd;
	}

	public String getResult() {
		return this.result;
	}

	public void setResult(String result) {
		result = IdpCommonUtil.removeSpecial(result);
		this.result = result;
	}

	public String getResult_text() {
		return this.result_text;
	}

	public void setResult_text(String result_text) {
		result_text = IdpCommonUtil.removeSpecial(result_text);
		this.result_text = result_text;
	}

	public String getJoin_type() {
		return this.join_type;
	}

	public void setJoin_type(String join_type) {
		join_type = IdpCommonUtil.removeSpecial(join_type);
		this.join_type = join_type;
	}

	public String getMobile_sign() {
		return this.mobile_sign;
	}

	public void setMobile_sign(String mobile_sign) {
		mobile_sign = IdpCommonUtil.removeSpecial(mobile_sign);
		this.mobile_sign = mobile_sign;
	}

	public String getSign_data() {
		return this.sign_data;
	}

	public void setSign_data(String sign_data) {
		sign_data = IdpCommonUtil.removeSpecial(sign_data);
		this.sign_data = sign_data;
	}

	public String getImage_url() {
		return this.image_url;
	}

	public void setImage_url(String image_url) {
		if (image_url != null) {
			image_url = image_url.replaceAll("<", "");
			image_url = image_url.replaceAll(">", "");
			image_url = image_url.replaceAll("%", "");
			image_url = image_url.replaceAll("--", "");
			image_url = image_url.replaceAll("#", "");
		}
		this.image_url = image_url;
	}

	public String getImage_sign() {
		return this.image_sign;
	}

	public void setImage_sign(String image_sign) {
		image_sign = IdpCommonUtil.removeSpecial(image_sign);
		this.image_sign = image_sign;
	}

	public String getEmail_auth_link() {
		return this.email_auth_link;
	}

	public void setEmail_auth_link(String email_auth_link) {
		email_auth_link = email_auth_link.replaceAll("<", "");
		email_auth_link = email_auth_link.replaceAll(">", "");
		this.email_auth_link = email_auth_link;
	}

	public String getUser_key() {
		return this.user_key;
	}

	public void setUser_key(String user_key) {
		user_key = IdpCommonUtil.removeSpecial(user_key);
		this.user_key = user_key;
	}

	public String getUser_id() {
		return this.user_id;
	}

	public void setUser_id(String user_id) {
		// user_id = CommonUtil.removeSpecial(user_id);
		this.user_id = user_id;
	}

	public String getUser_auth_key() {
		return this.user_auth_key;
	}

	public void setUser_auth_key(String user_auth_key) {
		user_auth_key = IdpCommonUtil.removeSpecial(user_auth_key);
		this.user_auth_key = user_auth_key;
	}

	public String getUser_mdn() {
		return this.user_mdn;
	}

	public void setUser_mdn(String user_mdn) {
		user_mdn = IdpCommonUtil.removeSpecial(user_mdn);
		this.user_mdn = user_mdn;
	}

	public String getUser_email() {
		return this.user_email;
	}

	public void setUser_email(String user_email) {
		// user_email = CommonUtil.removeSpecial(user_email);
		this.user_email = user_email;
	}

	public String getUser_job_code() {
		return this.user_job_code;
	}

	public void setUser_job_code(String user_job_code) {
		user_job_code = IdpCommonUtil.removeSpecial(user_job_code);
		this.user_job_code = user_job_code;
	}

	public String getUser_zipcode() {
		return this.user_zipcode;
	}

	public void setUser_zipcode(String user_zipcode) {
		user_zipcode = IdpCommonUtil.removeSpecial(user_zipcode);
		this.user_zipcode = user_zipcode;
	}

	public String getUser_address() {
		return this.user_address;
	}

	public void setUser_address(String user_address) {
		user_address = IdpCommonUtil.removeSpecial(user_address);
		this.user_address = user_address;
	}

	public String getUser_sex() {
		return this.user_sex;
	}

	public void setUser_sex(String user_sex) {
		user_sex = IdpCommonUtil.removeSpecial(user_sex);
		this.user_sex = user_sex;
	}

	public String getUser_birthday() {
		return this.user_birthday;
	}

	public void setUser_birthday(String user_birthday) {
		user_birthday = IdpCommonUtil.removeSpecial(user_birthday);
		this.user_birthday = user_birthday;
	}

	public String getUser_social_number() {
		return this.user_social_number;
	}

	public void setUser_social_number(String user_social_number) {
		user_social_number = IdpCommonUtil.removeSpecial(user_social_number);
		this.user_social_number = user_social_number;
	}

	public String getUser_name() {
		return this.user_name;
	}

	public void setUser_name(String user_name) {
		user_name = IdpCommonUtil.removeSpecial(user_name);
		this.user_name = user_name;
	}

	public String getUser_phone() {
		return this.user_phone;
	}

	public void setUser_phone(String user_phone) {
		user_phone = IdpCommonUtil.removeSpecial(user_phone);
		this.user_phone = user_phone;
	}

	public String getId_list() {
		return this.id_list;
	}

	public void setId_list(String id_list) {
		id_list = IdpCommonUtil.removeSpecial(id_list);
		this.id_list = id_list;
	}

	public String getTemp_passwd() {
		return this.temp_passwd;
	}

	public void setTemp_passwd(String temp_passwd) {
		temp_passwd = IdpCommonUtil.removeSpecial(temp_passwd);
		this.temp_passwd = temp_passwd;
	}

	public String[] getIdList() {
		return this.idList;
	}

	public void setIdList(String[] idList) {
		if (idList != null && idList.length > 0) {
			for (int i = 0; i < idList.length; i++) {
				if (idList[i] != null) {
					idList[i] = IdpCommonUtil.removeSpecial(idList[i]);
				}
			}
		}
		this.idList = idList;
	}

	public String getPhone_auth_code() {
		return this.phone_auth_code;
	}

	public void setPhone_auth_code(String phone_auth_code) {
		phone_auth_code = IdpCommonUtil.removeSpecial(phone_auth_code);
		this.phone_auth_code = phone_auth_code;
	}

	public String[] getSvcMngNum() {
		return this.svcMngNum;
	}

	public void setSvcMngNum(String[] svcMngNum) {
		this.svcMngNum = svcMngNum;
	}

	public String getIs_email_approved() {
		return this.is_email_approved;
	}

	public void setIs_email_approved(String is_email_approved) {
		this.is_email_approved = is_email_approved;
	}

}
