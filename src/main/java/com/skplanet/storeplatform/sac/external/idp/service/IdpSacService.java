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

import java.util.Map;

import com.skplanet.storeplatform.sac.external.idp.constant.IdpConstants;
import com.skplanet.storeplatform.sac.external.idp.vo.IDPReceiverM;
import com.skplanet.storeplatform.sac.external.idp.vo.IDPSenderM;

/**
 * 
 * IDP 연동 서비스 Interface
 * 
 * Updated on : 2013. 12. 31. Updated by : Jeon.ByungYoul, SK planet.
 */
public interface IdpSacService extends IdpConstants {
	IDPReceiverM alredyJoinCheckByEmail(String email) throws Exception;

	IDPReceiverM alredyJoinCheckBySn(String ssn) throws Exception;

	IDPReceiverM alredyJoinCheck(String checkKey, String checkKeyType)
			throws Exception;

	IDPReceiverM checkDupID(String id) throws Exception;

	IDPReceiverM realNameAuthForNative(String name, String ssn)
			throws Exception;

	IDPReceiverM realNameAuthForForeign(String name, String ssn)
			throws Exception;

	IDPReceiverM realNameAuth(String personType, String name, String ssn)
			throws Exception;

	IDPReceiverM sendMobileAuthCode(String mdn) throws Exception;

	IDPReceiverM sendMobileAuthCode(String mdn, String telType)
			throws Exception;

	IDPReceiverM sendMobileAuthCode(String mdn, String telType, String ssn)
			throws Exception;

	IDPReceiverM mobileAuth(String mdn, String authCode, String mobileSign,
			String signData) throws Exception;

	IDPReceiverM warterMarkImageUrl() throws Exception;

	IDPReceiverM warterMarkAuth(String authCode, String imageSign,
			String signData) throws Exception;

	IDPReceiverM authNateId(String nateId, String natePwd) throws Exception;

	IDPReceiverM authPwd(String id, String pwd) throws Exception;

	IDPReceiverM authMdn(String user_mdn, String user_code, String mobile_sign,
			String sign_data) throws Exception;

	IDPReceiverM getEmailAuthLink(String id) throws Exception;

	IDPReceiverM getEmailAuthLink(String user_auth_key, String id)
			throws Exception;

	IDPReceiverM modifyEmail4JoinWait(String preEmail, String email,
			String pwd, String userKey) throws Exception;

	IDPReceiverM modifyAuthInfo(String user_auth_key, String key_type,
			String key) throws Exception;

	IDPReceiverM modifyEmailAuthInfo(String sp_auth_key, String user_auth_key,
			String user_key, String pre_key, String key) throws Exception;

	IDPReceiverM modifyPasswordAuthInfo(String user_auth_key, String user_key,
			String pre_key, String key) throws Exception;

	String makePhoneAuthKey(String phoneMeta) throws Exception;

	String makeSnAuthKey(String mbrNm, String userId) throws Exception;

	IDPReceiverM joinMember(Map<String, Object> param) throws Exception;

	IDPReceiverM userAuthForWap(String userMdn) throws Exception;

	IDPReceiverM userAuthForId(String userId, String userPwd) throws Exception;

	IDPReceiverM userLogoutForSSO() throws Exception;

	IDPReceiverM searchUserCommonInfoByUserKey(String userAuthKey,
			String userKey) throws Exception;

	IDPReceiverM searchUserCommonInfoByID(String userAuthKey, String userID)
			throws Exception;

	IDPReceiverM searchUserCommonInfoByEmail(String userAuthKey, String email)
			throws Exception;

	IDPReceiverM searchUserCommonInfo(String userAuthKey, String queryKeyType,
			String queryKeyValue) throws Exception;

	IDPReceiverM searchUserCommonInfo4SPServer(String keyType, String key)
			throws Exception;

	IDPReceiverM searchUserIDByEmail(String email, String userCode,
			String imageSign, String signData) throws Exception;

	IDPReceiverM searchUserIDBySN(String ssn) throws Exception;

	IDPReceiverM searchUserIDByUserKey(String userKey, String userCode,
			String imageSign, String signData) throws Exception;

	IDPReceiverM searchUserID(String queryKeyType, String queryKeyValue,
			String userCode, String imageSign, String signData)
			throws Exception;

	IDPReceiverM searchUserID(String queryKeyType, String queryKeyValue)
			throws Exception;

	IDPReceiverM findPwdByEmail(String email) throws Exception;

	IDPReceiverM findPwdByID(String userID) throws Exception;

	IDPReceiverM findPwdByID(String userID, String userCode, String imageSign,
			String signData) throws Exception;

	IDPReceiverM findPwdByUserKey(String userKey) throws Exception;

	IDPReceiverM findPwd(String queryKeyType, String queryKeyValue)
			throws Exception;

	IDPReceiverM findPwd(String queryKeyType, String queryKeyValue,
			String userCode, String imageSign, String signData)
			throws Exception;

	IDPReceiverM secedeUserByID(String userAuthKey, String userID)
			throws Exception;

	IDPReceiverM secedeUserByUserKey(String userAuthKey, String userKey)
			throws Exception;

	IDPReceiverM secedeUser(String userAuthKey, String secedeKeyType,
			String secedeKeyValue) throws Exception;

	IDPReceiverM aleadyJoinCheck4Mdn(String mdn) throws Exception;

	IDPReceiverM authForWap(String mdn) throws Exception;

	IDPReceiverM join4Wap(String mdn) throws Exception;

	IDPReceiverM secedeUser4Wap(String mdn) throws Exception;

	IDPReceiverM joinSupService(String mdn, String svcCode) throws Exception;

	IDPReceiverM joinSupService(String mdn, String svcCd, String svcMngNum)
			throws Exception;

	IDPReceiverM secedeSupService(String mdn, String svcCode) throws Exception;

	IDPReceiverM secedeSupService(String mdn, String svcCd, String svcMngNum)
			throws Exception;

	IDPReceiverM tmapServiceAvalibleCheck(String mdn) throws Exception;

	IDPReceiverM tmapServiceCheck(String mdn, String svcCd) throws Exception;

	IDPReceiverM otherChannelIdAuth(String user_id, String user_passwd)
			throws Exception;

	IDPReceiverM otherChannelList(String key_type, String key) throws Exception;

	IDPReceiverM otherChannelRegist(String user_id, String user_passwd)
			throws Exception;

	IDPReceiverM findModelId(String mdn) throws Exception;

	IDPReceiverM mobileSend(String user_mdn, String comment) throws Exception;

	IDPReceiverM idpSmsSend(String receiverMdn, String msg, String senderMdn)
			throws Exception;

	IDPReceiverM joinSupServiceRequest(String mdn, String svcCode)
			throws Exception;

	IDPReceiverM secedeSupServiceRequest(String mdn, String svcCode)
			throws Exception;

	IDPReceiverM findBill(String mdn) throws Exception;

	IDPReceiverM createToken(String user_id) throws Exception;

	IDPReceiverM mappingToken(String user_id) throws Exception;

	IDPReceiverM searchToken(String user_id) throws Exception;

	IDPReceiverM deleteToken(String user_id) throws Exception;

	IDPReceiverM modifyProfile(Map<String, Object> param) throws Exception;

	IDPReceiverM sendIDPHttps(IDPSenderM sendData) throws Exception;

}
