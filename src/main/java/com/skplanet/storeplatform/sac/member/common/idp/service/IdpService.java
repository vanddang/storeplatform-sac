package com.skplanet.storeplatform.sac.member.common.idp.service;

import java.util.Map;

import com.skplanet.storeplatform.external.client.idp.vo.IdpReceiverM;

/**
 * IDP - API 항목
 * 
 * Updated on : 2014. 1. 13. Updated by : 김경복, 부르칸.
 */
public interface IdpService {
	/** 2.1. 기본 API. */
	/** 2.1.2. 서비스 중복 가입 체크. (이메일 기준). */
	public IdpReceiverM alredyJoinCheckByEmail(String email);

	/** 2.1.13. 아이디 중복 체크. */
	public IdpReceiverM checkDupID(String id);

	/** 2.1.8. 자동 가입 방지 Image 발급. */
	public IdpReceiverM warterMarkImageUrl();

	/** 2.1.9. 자동 가입 방지 인증. */
	public IdpReceiverM warterMarkAuth(String authCode, String imageSign, String signData);

	/** 2.1.10. 휴대폰 단말 기종 조회 및 업데이트. */
	public IdpReceiverM deviceCompare(String mdn);

	/** 2.2. 회원 가입 API. */
	/** 2.2.7 간편 회원 가입. */
	public IdpReceiverM simpleJoin(Map<String, Object> param);

	/** 2.3. 회원 인증 API. */
	/** 2.3.1. 유선 회원의 로그인. */
	public IdpReceiverM userAuthForId(String userId, String userPwd);

	/** 2.3.2. 유선 회원의 비밀번호 확인. */
	public IdpReceiverM authPwd(String id, String pwd);

	/** 2.4. 회원 정보 조회 API. */
	/** 2.4.2. 기본 Profile 조회 (For SP Server). */
	public IdpReceiverM searchUserCommonInfo(String queryKeyType, String queryKeyValue);

	/** 2.4.3. 특정 Profile 조회 (For SO Server). */
	public IdpReceiverM searchSpecialProfile(String queryKeyType, String queryKeyValue);

	/** 2.5. 회원 정보 변경 API. */
	/** 2.5.1. 인증 정보 변경 API. */
	public IdpReceiverM modifyAuthInfo(String user_auth_key, String key_type, String key);

	/** 2.5.2. 회원 정보 변경. */
	public IdpReceiverM modifyProfile(Map<String, Object> param);

	/** 2.6. 회원 해지 API. */
	/** 2.6.1. 회원 해지. */
	public IdpReceiverM secedeUser(String userAuthKey, String secedeKeyType, String secedeKeyValue);

	/** 3. MDN 기반서비스 API. */
	/** 3.1. 기본 API. */
	/** 3.1.1. MDN/Password 중복 가입 체크. */
	public IdpReceiverM aleadyJoinCheckForMdn(String mdn);

	/** 3.2. 회원 가입 API. */
	/** 3.2.1. 모바일 회원가입. */
	public IdpReceiverM join4Wap(String mdn, String mdnCorp);

	/** 3.3. 회원 인증 API. */
	/** 3.3.1. 무선 회원 인증 (For SP Server). */
	public IdpReceiverM authForWap(String mdn);

	/** 3.3. 회원 정보 조회 API. */
	/** 3.4.1 무선 회원 Profile 조회 (For SP Server). */
	public IdpReceiverM findProfileForWap(String mdn);

	/** 3.6. 사용자 해지 API. */
	/** 3.6.1. 무선 회원 해지 (For SP Server). */
	public IdpReceiverM secedeUser4Wap(String mdn);

	/** 4. 부가서비스 서비스 API. */
	/** 4.1.1. 부가서비스 가입. */
	public IdpReceiverM joinSupService(String mdn, String svcCd, String svcMngNum);

	/** 4.2.1. 부가서비스 해지. */
	public IdpReceiverM secedeSupService(String mdn, String svcCd, String svcMngNum);

	/** 4.3.2. 부가서비스 가입 여부 조회. */
	public IdpReceiverM serviceSubscriptionCheck(String mdn, String svcCode);
}
