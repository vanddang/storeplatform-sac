package com.skplanet.storeplatform.sac.member.user.service;

import com.skplanet.storeplatform.sac.client.member.vo.common.HeaderVo;
import com.skplanet.storeplatform.sac.client.member.vo.user.AuthorizeByIdReq;
import com.skplanet.storeplatform.sac.client.member.vo.user.AuthorizeByIdRes;
import com.skplanet.storeplatform.sac.client.member.vo.user.AuthorizeByMdnReq;
import com.skplanet.storeplatform.sac.client.member.vo.user.AuthorizeByMdnRes;

/**
 * 회원 로그인 관련 인터페이스
 * 
 * Updated on : 2014. 1. 6. Updated by : 반범진, 지티소프트.
 */
public interface LoginService {

	/**
	 * 모바일 전용 회원 인증 (MDN 인증)
	 * 
	 * @param headerVo
	 * @param req
	 * @return
	 * @throws Exception
	 */
	public AuthorizeByMdnRes authorizeByMdn(HeaderVo headerVo,
			AuthorizeByMdnReq req) throws Exception;

	/**
	 * ID 기반 회원 인증 (One ID, IDP 회원)
	 * 
	 * @param headerVo
	 * @param req
	 * @return
	 * @throws Exception
	 */
	public AuthorizeByIdRes authorizeById(HeaderVo headerVo,
			AuthorizeByIdReq req) throws Exception;
}
