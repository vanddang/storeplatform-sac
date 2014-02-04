package com.skplanet.storeplatform.sac.member.common.idp.service;

import java.util.HashMap;
import java.util.Map;

import com.skplanet.storeplatform.external.client.idp.vo.ImIDPReceiverM;

/**
 * OneID 관련 API 기능 항목들
 * 
 * Updated on : 2014. 1. 13. Updated by : 김경복, 부르칸.
 */
public interface ImIDPService {
	// 20p - 개요

	/** 2.1. IDP 정책설계 API(TX). */
	/** 2.1.2. 서비스 이용 동의. */
	public ImIDPReceiverM agreeUser(Map<String, Object> param);

	/** 2.1.4. 개별약관해지요청. */
	public ImIDPReceiverM discardUser(Map<String, Object> param);

	/** 2.1.6 공통프로파일조회요청 (For Server). */
	public ImIDPReceiverM userInfoSearchServer(String imServiceNo);

	/** 2.1.7 공통프로파일정보수정요청. */
	public ImIDPReceiverM updateUserInfo(Map<String, Object> param);

	/** 2.1.8 부가프로파일정보수정요청. */
	public ImIDPReceiverM updateAdditionalInfo(Map<String, Object> param);

	/** 2.1.9 비밀번호변경요청. */
	public ImIDPReceiverM modifyPwd(Map<String, Object> param);

	/** 2.1.15 국가 정보 조회 요청. */
	/** 2.1.20 로그인 상태 정보 변경 요청. */
	public ImIDPReceiverM setLoginStatus(String key, String login_status_code);

	/** 2.1.23 ID 전환불가설정/해제 요청. */
	/** 2.1.24 실명변경 요청. */
	public ImIDPReceiverM updateUserName(String key, String user_name, String user_birthday, String sn_auth_key,
			String user_auth_key, String rname_auth_mns_code, String ci, String di, HashMap map);

	/** 2.1.25 법정대리인 동의정보 변경 요청. */
	public ImIDPReceiverM updateGuardian(String key, String parent_type, String parent_rname_auth_key,
			String parent_name, String parent_email, String user_auth_key, String parent_birthday);

	/** 2.1.26 RX 배포 Retry 요청. */
	/** 2.2 SP 요구사항 API. */

	/** 2.2 SP 요구사항 API. */
	/** 2.2.3 MDN 중복체크. */
	/** 2.2.4 MDN 조회 (SKT, non-SKT 구분). */
	/** 2.2.5 통합 ID 회원로그인. */
	public ImIDPReceiverM authForId(String key, String pwd);

	/** 2.2.6. 통합 ID 서비스 가입리스트 조회. */
	public ImIDPReceiverM findJoinServiceListIDP(Map<String, Object> param);

	/** 2.2.9 ID 가입여부 체크. */
	public ImIDPReceiverM checkIdStatusIdpIm(String id);

	/** 2.2.11 MDN 정보 조회 (SKT 가입자). */
	public ImIDPReceiverM getMdnInfoIDP(String mdn);

	/** 2.2.12 IM 통합회원 ID 찾기. */
	public ImIDPReceiverM findUserIdByMdn(Map<String, Object> param);

	/** 2.2.13 이용동의 가능여부 조회. */

	/** 2.2.15. 기본 프로파일 조회(For Server). */
	public ImIDPReceiverM userInfoIdpSearchServer(String imServiceNo);

}
