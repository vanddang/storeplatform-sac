package com.skplanet.storeplatform.sac.member.common.idp.service;

import java.util.Map;

import com.skplanet.storeplatform.external.client.idp.vo.IDPReceiverM;

public interface IDPService {

	/** 아이디 중복 체크 */
	public IDPReceiverM checkDupID(String id) throws Exception;

	/** 회원 로그인 */
	public IDPReceiverM userAuthForId(String userId, String userPwd) throws Exception;

	/** 프로파일 수정 */
	public IDPReceiverM modifyProfile(Map<String, Object> param) throws Exception;

	/** 모바일 API */
	/** 모바일 회원 인증 */
	public IDPReceiverM authForWap(String mdn) throws Exception;

	/** 모바일 회원가입 */
	public IDPReceiverM join4Wap(String mdn) throws Exception;

	/** 사용자 해지 */
	public IDPReceiverM secedeUser4Wap(String mdn) throws Exception;

	/** 3.4.1 무선 회원 Profile 조회 (For SP Server) */
	public IDPReceiverM findProfileForWap(String mdn) throws Exception;
}
