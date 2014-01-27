package com.skplanet.storeplatform.sac.member.common.idp.service;

import java.util.Map;

import com.skplanet.storeplatform.external.client.idp.vo.IDPReceiverM;

/**
 * IDP - API 항목
 * 
 * Updated on : 2014. 1. 13. Updated by : 김경복, 부르칸.
 */
public interface IDPService {
	/** 2.1. 기본 API. */
	/** 2.1.2. 서비스 중복 가입 체크. (이메일 기준). */
	public IDPReceiverM alredyJoinCheckByEmail(String email) throws Exception;

	/** 2.1.13. 아이디 중복 체크. */
	public IDPReceiverM checkDupID(String id) throws Exception;

	/** 2.1.8. 자동 가입 방지 Image 발급. */
	public IDPReceiverM warterMarkImageUrl() throws Exception;

	/** 2.1.9. 자동 가입 방지 인증. */
	public IDPReceiverM warterMarkAuth(String authCode, String imageSign, String signData) throws Exception;

	/** 2.1.10. 휴대폰 단말 기종 조회 및 업데이트. */
	public IDPReceiverM deviceCompare(String mdn) throws Exception;

	/** 2.2. 회원 가입 API. */
	/** 2.2.7 간편 회원 가입. */
	public IDPReceiverM simpleJoin(Map<String, Object> param) throws Exception;

	/** 2.3. 회원 인증 API. */
	/** 2.3.1. 유선 회원의 로그인. */
	public IDPReceiverM userAuthForId(String userId, String userPwd) throws Exception;

	/** 2.3.2. 유선 회원의 비밀번호 확인. */
	public IDPReceiverM authPwd(String id, String pwd) throws Exception;

	/** 2.4. 회원 정보 조회 API. */
	/** 2.4.2. 기본 Profile 조회 (For SP Server). */
	public IDPReceiverM searchUserCommonInfo(String queryKeyType, String queryKeyValue) throws Exception;

	/** 2.4.3. 특정 Profile 조회 (For SO Server). */
	public IDPReceiverM searchSpecialProfile(String queryKeyType, String queryKeyValue) throws Exception;

	/** 2.5. 회원 정보 변경 API. */
	/** 2.5.1. 인증 정보 변경 API. */
	public IDPReceiverM modifyAuthInfo(String user_auth_key, String key_type, String key) throws Exception;

	/** 2.5.2. 회원 정보 변경. */
	public IDPReceiverM modifyProfile(Map<String, Object> param) throws Exception;

	/** 2.6. 회원 해지 API. */
	/** 2.6.1. 회원 해지. */
	public IDPReceiverM secedeUser(String userAuthKey, String secedeKeyType, String secedeKeyValue) throws Exception;

	/** 3. MDN 기반서비스 API. */
	/** 3.1. 기본 API. */
	/** 3.1.1. MDN/Password 중복 가입 체크. */
	public IDPReceiverM aleadyJoinCheckForMdn(String mdn) throws Exception;

	/** 3.2. 회원 가입 API. */
	/** 3.2.1. 모바일 회원가입. */
	public IDPReceiverM join4Wap(String mdn, String mdnCorp) throws Exception;

	/** 3.3. 회원 인증 API. */
	/** 3.3.1. 무선 회원 인증 (For SP Server). */
	public IDPReceiverM authForWap(String mdn) throws Exception;

	/** 3.3. 회원 정보 조회 API. */
	/** 3.4.1 무선 회원 Profile 조회 (For SP Server). */
	public IDPReceiverM findProfileForWap(String mdn) throws Exception;

	/** 3.6. 사용자 해지 API. */
	/** 3.6.1. 무선 회원 해지 (For SP Server). */
	public IDPReceiverM secedeUser4Wap(String mdn) throws Exception;

	/** 4. 부가서비스 서비스 API. */
	/** 4.1.1. 부가서비스 가입. */
	public IDPReceiverM joinSupService(String mdn, String svcCd, String svcMngNum) throws Exception;

	/** 4.2.1. 부가서비스 해지. */
	public IDPReceiverM secedeSupService(String mdn, String svcCd, String svcMngNum) throws Exception;

	/** 4.3.2. 부가서비스 가입 여부 조회. */
	public IDPReceiverM serviceSubscriptionCheck(String mdn, String svcCode) throws Exception;
}
