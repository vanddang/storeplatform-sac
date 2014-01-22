package com.skplanet.storeplatform.sac.member.common.idp.service;

import java.util.Map;

import com.skplanet.storeplatform.external.client.idp.vo.ImIDPReceiverM;

/**
 * OneID 관련 API 기능 항목들
 * 
 * Updated on : 2014. 1. 13. Updated by : 김경복, 부르칸.
 */
public interface ImIDPService {

	/** 회원인증. */
	public ImIDPReceiverM authForId(String key, String pwd) throws Exception;

	/** 모바일 정보 조회. */
	public ImIDPReceiverM getMdnInfoIDP(String mdn) throws Exception;

	/** 이용동의. */
	public ImIDPReceiverM agreeUser(Map<String, Object> param) throws Exception;

	/** 회원정보 조회. */
	public ImIDPReceiverM userInfoIdpSearchServer(String imServiceNo) throws Exception;

	/** ID 가입여부 체크. */
	public ImIDPReceiverM checkIdStatusIdpIm(String id) throws Exception;

	/** 부가프로파일정보수정요청. */
	public ImIDPReceiverM updateAdditionalInfo(Map<String, Object> param) throws Exception;

	/** 2.1.4 개별약관해지요청. */
	public ImIDPReceiverM discardUser(Map<String, Object> param) throws Exception;

	/** 서비스 가입리스트 조회. */
	public ImIDPReceiverM findJoinServiceListIDP(Map<String, Object> param) throws Exception;

}
