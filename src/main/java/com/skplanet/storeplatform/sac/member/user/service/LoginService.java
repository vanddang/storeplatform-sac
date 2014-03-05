package com.skplanet.storeplatform.sac.member.user.service;

import com.skplanet.storeplatform.sac.client.member.vo.user.AuthorizeByIdReq;
import com.skplanet.storeplatform.sac.client.member.vo.user.AuthorizeByIdRes;
import com.skplanet.storeplatform.sac.client.member.vo.user.AuthorizeByMdnReq;
import com.skplanet.storeplatform.sac.client.member.vo.user.AuthorizeByMdnRes;
import com.skplanet.storeplatform.sac.client.member.vo.user.AuthorizeForAutoUpdateReq;
import com.skplanet.storeplatform.sac.client.member.vo.user.AuthorizeForAutoUpdateRes;
import com.skplanet.storeplatform.sac.common.header.vo.SacRequestHeader;

/**
 * 회원 로그인 관련 인터페이스.
 * 
 * Updated on : 2014. 1. 6. Updated by : 반범진, 지티소프트.
 */
public interface LoginService {

	/**
	 * 모바일 전용 회원 인증 (MDN 인증).
	 * 
	 * @param requestHeader
	 *            SacRequestHeader
	 * @param req
	 *            AuthorizeByMdnReq
	 * @return AuthorizeByMdnRes
	 */
	public AuthorizeByMdnRes executeAuthorizeByMdn(SacRequestHeader requestHeader, AuthorizeByMdnReq req);

	/**
	 * ID 기반 회원 인증 (One ID, IDP 회원).
	 * 
	 * @param requestHeader
	 *            SacRequestHeader
	 * @param req
	 *            AuthorizeByIdReq
	 * @return AuthorizeByIdRes
	 */
	public AuthorizeByIdRes executeAuthorizeById(SacRequestHeader requestHeader, AuthorizeByIdReq req);

	/**
	 * 심플 인증(간편인증).
	 * 
	 * @param requestHeader
	 *            SacRequestHeader
	 * @param req
	 *            AuthorizeForAutoUpdateReq
	 * @return AuthorizeByIdRes
	 */
	public AuthorizeForAutoUpdateRes executeAuthorizeForAutoUpdate(SacRequestHeader requestHeader, AuthorizeForAutoUpdateReq req);

}
