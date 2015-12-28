package com.skplanet.storeplatform.sac.member.miscellaneous.service;

import com.skplanet.storeplatform.sac.client.member.vo.miscellaneous.*;
import com.skplanet.storeplatform.sac.common.header.vo.SacRequestHeader;

/**
 * 
 * 이메일 인증 기능 관련 인터페이스
 * 
 * Updated on : 2014. 1. 7. Updated by : 김다슬, 인크로스.
 * Updated on : 2015. 12. 28. Updated by : 임근대, SKP. - Micellaneos 클래스에서 Email인증 관련 기능 클래스 분리
 */
public interface EmailAuthorizationService {

	/**
	 * <pre>
	 * 이메일 인증 코드 생성.
	 * </pre>
	 * 
	 * @param requestHeader
	 *            SacRequestHeader
	 * @param request
	 *            GetPhoneAuthorizationCodeReq
	 * @return GetEmailAuthorizationCodeRes
	 */
	GetEmailAuthorizationCodeRes getEmailAuthorizationCode(SacRequestHeader requestHeader,
																  GetEmailAuthorizationCodeReq request);

	/**
	 * <pre>
	 * 이메일 인증 코드 확인.
	 * </pre>
	 * 
	 * @param request
	 *            ConfirmEmailAuthorizationCodeReq
	 * @return ConfirmEmailAuthorizationCodeRes
	 */
	ConfirmEmailAuthorizationCodeRes confirmEmailAuthorizationCode(ConfirmEmailAuthorizationCodeReq request);

	/**
	 * <pre>
	 * 2.3.19. 이메일 인증 URL 생성.
	 * </pre>
	 * 
	 * @param header
	 *            SacRequestHeader
	 * @param req
	 *            GetEmailAuthorizationUrlSacReq
	 * @return GetEmailAuthorizationUrlSacRes
	 */
	GetEmailAuthorizationUrlSacRes getEmailAuthorizationUrl(SacRequestHeader header,
																   GetEmailAuthorizationUrlSacReq req);

	/**
	 * <pre>
	 * 2.3.19. 이메일 인증 URL 확인.
	 * </pre>
	 * 
	 * @param header
	 *            SacRequestHeader
	 * @param req
	 *            ConfirmEmailAuthorizationUrlSacReq
	 * @return ConfirmEmailAuthorizationUrlSacRes
	 */
	ConfirmEmailAuthorizationUrlSacRes confirmEmailAuthorizationUrl(SacRequestHeader header,
																		   ConfirmEmailAuthorizationUrlSacReq req);

}
