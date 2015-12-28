package com.skplanet.storeplatform.sac.member.miscellaneous.service;

import com.skplanet.storeplatform.sac.client.member.vo.miscellaneous.*;
import com.skplanet.storeplatform.sac.common.header.vo.SacRequestHeader;

/**
 * 
 * 휴대폰 인증 기능 관련 인터페이스
 * 
 * Updated on : 2014. 1. 7. Updated by : 김다슬, 인크로스.
 * Updated on : 2015. 12. 28. Updated by : 임근대, SKP. - Micellaneos 클래스에서 Captcha 관련 기능 클래스 분리
 */
public interface PhoneAuthorizationService {

	/**
	 * <pre>
	 * 휴대폰 인증 SMS 발송.
	 * 1. 휴대폰 인증 코드 생성
	 * 2. 생성된 인증 코드를 DB에 저장
	 * 3. External Comp.에 SMS 발송 요청
	 * 4. 결과 Response
	 * </pre>
	 * 
	 * @param request
	 *            GetPhoneAuthorizationCodeReq
	 * @param requestHeader
	 *            SacRequestHeader
	 * @return GetPhoneAuthorizationCodeRes
	 */
	GetPhoneAuthorizationCodeRes getPhoneAuthorizationCode(SacRequestHeader requestHeader,
																  GetPhoneAuthorizationCodeReq request);

	/**
	 * <pre>
	 * 휴대폰 인증 코드 확인.
	 * </pre>
	 * 
	 * @param request
	 *            ConfirmPhoneAuthorizationCodeReq
	 * @param requestHeader
	 *            SacRequestHeader
	 * @return ConfirmPhoneAuthorizationCodeRes
	 */
	ConfirmPhoneAuthorizationCodeRes confirmPhoneAutorizationCode(SacRequestHeader requestHeader,
																		 ConfirmPhoneAuthorizationCodeReq request);
	/**
	 * <pre>
	 * 2.3.17.	휴대폰 인증 여부 확인.
	 * </pre>
	 * 
	 * @param header
	 *            SacRequestHeader
	 * @param request
	 *            ConfirmPhoneAuthorizationCheckReq
	 * @return ConfirmPhoneAuthorizationCheckRes
	 */
	ConfirmPhoneAuthorizationCheckRes confirmPhoneAutorizationCheck(SacRequestHeader header,
																		   ConfirmPhoneAuthorizationCheckReq request);

}
