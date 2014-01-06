/*
 * Copyright (c) 2013 SK planet.
 * All right reserved.
 *
 * This software is the confidential and proprietary information of SK planet.
 * You shall not disclose such Confidential Information and
 * shall use it only in accordance with the terms of the license agreement
 * you entered into with SK planet.
 */
package com.skplanet.storeplatform.sac.external.idp.service;

import java.util.HashMap;
import java.util.Map;

import com.skplanet.storeplatform.sac.external.idp.constant.ImIDPManagerConstants;
import com.skplanet.storeplatform.sac.external.idp.vo.ImIDPReceiverM;

/**
 * 
 * IDP 연동 서비스 Interface
 * 
 * Updated on : 2013. 12. 31. Updated by : Jeon.ByungYoul, SK planet.
 */
public interface ImIdpSacService extends ImIDPManagerConstants {

	ImIDPReceiverM createUser(Map<String, Object> param) throws Exception;

	ImIDPReceiverM userInfoSearchServer(String imServiceNo) throws Exception;

	ImIDPReceiverM agreeUser(Map<String, Object> param) throws Exception;

	ImIDPReceiverM DeleteUser(Map<String, Object> param) throws Exception;

	ImIDPReceiverM discardUser(Map<String, Object> param) throws Exception;

	ImIDPReceiverM userInfoSearchBrowser(Map<String, Object> param) throws Exception;

	ImIDPReceiverM updateUserInfo(Map<String, Object> param) throws Exception;

	ImIDPReceiverM updateAdditionalInfo(Map<String, Object> param) throws Exception;

	ImIDPReceiverM modifyPwd(Map<String, Object> param) throws Exception;

	ImIDPReceiverM resetPwd(Map<String, Object> param) throws Exception;

	ImIDPReceiverM authCreateSms(String user_tn, String user_tn_nation_cd) throws Exception;

	ImIDPReceiverM updateUserName(String key, String user_name, String user_birthday, String sn_auth_key,
			String user_auth_key, String rname_auth_mns_code, String ci, String di, HashMap map) throws Exception;

	ImIDPReceiverM updateGuardian(String key, String parent_type, String parent_rname_auth_key, String parent_name,
			String parent_email, String user_auth_key, String parent_birthday) throws Exception;

	ImIDPReceiverM getSerivceInfo(Map<String, Object> param) throws Exception;

	ImIDPReceiverM checkDupId(String id) throws Exception;

	ImIDPReceiverM authForId(String key, String pwd) throws Exception;

	ImIDPReceiverM findJoinServiceList(String user_id) throws Exception;

	ImIDPReceiverM sendMobileAuthCode(String user_mdn, String user_mdn_type) throws Exception;

	ImIDPReceiverM sendMobileAuthCode(String user_mdn, String user_mdn_type, String user_social_number)
			throws Exception;

	ImIDPReceiverM mobileAuth(Map<String, Object> param) throws Exception;

	ImIDPReceiverM checkIdStatusIdpIm(String id) throws Exception;

	ImIDPReceiverM checkIdPwdAuth(String id, String pwd) throws Exception;

	ImIDPReceiverM findUserIdByMdn(Map<String, Object> param) throws Exception;

	ImIDPReceiverM authForSvcNo(String im_int_svc_no) throws Exception;

	ImIDPReceiverM userInfoIdpSearchServer(String imServiceNo) throws Exception;

	String makeSnAuthKey(String mbrNm, String user_birthday) throws Exception;

	ImIDPReceiverM dupUserIdCheck(String id) throws Exception;

	ImIDPReceiverM dupTelNoCheck(String user_tn) throws Exception;

	ImIDPReceiverM resendConfirmEmail(String key, String user_email) throws Exception;

	ImIDPReceiverM sendAuthSms(String key, String user_tn) throws Exception;

	ImIDPReceiverM sendAuthEmail(String key, String user_email) throws Exception;

	ImIDPReceiverM resetVaildPeriod(String key) throws Exception;

	ImIDPReceiverM checkDelUser(String key) throws Exception;

	ImIDPReceiverM setLoginStatus(String key, String login_status_code) throws Exception;

}
