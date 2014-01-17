package com.skplanet.storeplatform.sac.member.miscellaneous.service;

import com.skplanet.storeplatform.sac.client.member.vo.miscellaneous.ConfirmPhoneAuthorizationCodeReq;
import com.skplanet.storeplatform.sac.client.member.vo.miscellaneous.ConfirmPhoneAuthorizationCodeRes;
import com.skplanet.storeplatform.sac.client.member.vo.miscellaneous.GetCaptchaRes;
import com.skplanet.storeplatform.sac.client.member.vo.miscellaneous.GetOpmdReq;
import com.skplanet.storeplatform.sac.client.member.vo.miscellaneous.GetOpmdRes;
import com.skplanet.storeplatform.sac.client.member.vo.miscellaneous.GetPhoneAuthorizationCodeReq;
import com.skplanet.storeplatform.sac.client.member.vo.miscellaneous.GetPhoneAuthorizationCodeRes;
import com.skplanet.storeplatform.sac.client.member.vo.miscellaneous.GetUaCodeReq;
import com.skplanet.storeplatform.sac.client.member.vo.miscellaneous.GetUaCodeRes;
import com.skplanet.storeplatform.sac.common.header.vo.SacRequestHeader;

/**
 * 
 * 기타 기능 관련 인터페이스
 * 
 * Updated on : 2014. 1. 7. Updated by : 김다슬, 인크로스.
 */
public interface MiscellaneousService {

	/**
	 * <pre>
	 * UA 코드 정보 조회.
	 * </pre>
	 * 
	 * @param request
	 * @return GetUaCodeRes
	 * @throws Exception
	 */
	public GetUaCodeRes getUaCode(SacRequestHeader requestHeader, GetUaCodeReq request) throws Exception;

	/**
	 * <pre>
	 * OPMD 모회선 번호 조회.
	 * 989로 시작하는 MSISDN이 들어오면 모번호를 조회해서 반환하고, 
	 * 989로 시작하지 않는경우 파라미터로 받은 MSISDN을 그대로 반환.
	 * </pre>
	 * 
	 * @param request
	 * @return GetOpmdRes
	 * @throws Exception
	 */
	public GetOpmdRes getOpmd(GetOpmdReq req) throws Exception;

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
	 * @return GetPhoneAuthorizationCodeRes
	 * @throws Exception
	 */
	public GetPhoneAuthorizationCodeRes getPhoneAuthorizationCode(GetPhoneAuthorizationCodeReq request)
			throws Exception;

	/**
	 * <pre>
	 * 휴대폰 인증 코드 확인.
	 * </pre>
	 * 
	 * @param request
	 * @return ConfirmPhoneAuthorizationCodeRes
	 * @throws Exception
	 */
	public ConfirmPhoneAuthorizationCodeRes confirmPhoneAutorizationCode(ConfirmPhoneAuthorizationCodeReq request)
			throws Exception;

	/**
	 * <pre>
	 * Captcha 문자 발급.
	 * </pre>
	 * 
	 * @param req
	 * @return GetCaptchaRes
	 * @throws Exception
	 */
	public GetCaptchaRes getCaptcha() throws Exception;
}
