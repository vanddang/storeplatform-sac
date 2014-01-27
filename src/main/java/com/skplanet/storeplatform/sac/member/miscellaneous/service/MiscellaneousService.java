package com.skplanet.storeplatform.sac.member.miscellaneous.service;

import com.skplanet.storeplatform.sac.client.member.vo.miscellaneous.ConfirmCaptchaReq;
import com.skplanet.storeplatform.sac.client.member.vo.miscellaneous.ConfirmCaptchaRes;
import com.skplanet.storeplatform.sac.client.member.vo.miscellaneous.ConfirmEmailAuthorizationCodeReq;
import com.skplanet.storeplatform.sac.client.member.vo.miscellaneous.ConfirmEmailAuthorizationCodeRes;
import com.skplanet.storeplatform.sac.client.member.vo.miscellaneous.ConfirmPhoneAuthorizationCodeReq;
import com.skplanet.storeplatform.sac.client.member.vo.miscellaneous.ConfirmPhoneAuthorizationCodeRes;
import com.skplanet.storeplatform.sac.client.member.vo.miscellaneous.CreateIndividualPolicyReq;
import com.skplanet.storeplatform.sac.client.member.vo.miscellaneous.CreateIndividualPolicyRes;
import com.skplanet.storeplatform.sac.client.member.vo.miscellaneous.GetCaptchaRes;
import com.skplanet.storeplatform.sac.client.member.vo.miscellaneous.GetEmailAuthorizationCodeReq;
import com.skplanet.storeplatform.sac.client.member.vo.miscellaneous.GetEmailAuthorizationCodeRes;
import com.skplanet.storeplatform.sac.client.member.vo.miscellaneous.GetIndividualPolicyReq;
import com.skplanet.storeplatform.sac.client.member.vo.miscellaneous.GetIndividualPolicyRes;
import com.skplanet.storeplatform.sac.client.member.vo.miscellaneous.GetOpmdReq;
import com.skplanet.storeplatform.sac.client.member.vo.miscellaneous.GetOpmdRes;
import com.skplanet.storeplatform.sac.client.member.vo.miscellaneous.GetPhoneAuthorizationCodeReq;
import com.skplanet.storeplatform.sac.client.member.vo.miscellaneous.GetPhoneAuthorizationCodeRes;
import com.skplanet.storeplatform.sac.client.member.vo.miscellaneous.GetUaCodeReq;
import com.skplanet.storeplatform.sac.client.member.vo.miscellaneous.GetUaCodeRes;
import com.skplanet.storeplatform.sac.client.member.vo.miscellaneous.RemoveIndividualPolicyReq;
import com.skplanet.storeplatform.sac.client.member.vo.miscellaneous.RemoveIndividualPolicyRes;
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
	 *            GetUaCodeReq
	 * @param requestHeader
	 *            SacRequestHeader
	 * @return GetUaCodeRes
	 * @throws Exception
	 *             Exception
	 * 
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
	 *            GetOpmdReq
	 * @return GetOpmdRes
	 * @throws Exception
	 *             Exception
	 */
	public GetOpmdRes getOpmd(GetOpmdReq request) throws Exception;

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
	 * @throws Exception
	 *             Exception
	 */
	public GetPhoneAuthorizationCodeRes getPhoneAuthorizationCode(SacRequestHeader requestHeader,
			GetPhoneAuthorizationCodeReq request) throws Exception;

	/**
	 * <pre>
	 * 휴대폰 인증 코드 확인.
	 * </pre>
	 * 
	 * @param request
	 *            ConfirmPhoneAuthorizationCodeReq
	 * @return ConfirmPhoneAuthorizationCodeRes
	 * @throws Exception
	 *             Exception
	 */
	public ConfirmPhoneAuthorizationCodeRes confirmPhoneAutorizationCode(ConfirmPhoneAuthorizationCodeReq request)
			throws Exception;

	/**
	 * <pre>
	 * Captcha 문자 발급.
	 * </pre>
	 * 
	 * @return GetCaptchaRes
	 * @throws Exception
	 *             Exception
	 */
	public GetCaptchaRes getCaptcha() throws Exception;

	/**
	 * <pre>
	 * Captcha 문자 확인.
	 * </pre>
	 * 
	 * @param request
	 *            ConfirmCaptchaReq
	 * @return ConfirmCaptchaRes
	 * @throws Exception
	 *             Exception
	 */
	public ConfirmCaptchaRes confirmCaptcha(ConfirmCaptchaReq request) throws Exception;

	/**
	 * <pre>
	 * 이메일 인증 코드 생성.
	 * </pre>
	 * 
	 * @param request
	 *            GetPhoneAuthorizationCodeReq
	 * @return GetEmailAuthorizationCodeRes
	 * @throws Exception
	 *             Exception
	 */
	public GetEmailAuthorizationCodeRes getEmailAuthorizationCode(GetEmailAuthorizationCodeReq request)
			throws Exception;

	/**
	 * <pre>
	 * 이메일 인증 코드 확인.
	 * </pre>
	 * 
	 * @param request
	 *            ConfirmEmailAuthorizationCodeReq
	 * @return ConfirmEmailAuthorizationCodeRes
	 * @throws Exception
	 *             Exception
	 */
	public ConfirmEmailAuthorizationCodeRes confirmEmailAuthorizationCode(ConfirmEmailAuthorizationCodeReq request)
			throws Exception;

	/**
	 * <pre>
	 * 2.3.8. 사용자별 정책 조회.
	 * </pre>
	 * 
	 * @param header
	 * @param req
	 * @return GetIndividualPolicyRes
	 */
	public GetIndividualPolicyRes getIndividualPolicy(SacRequestHeader header, GetIndividualPolicyReq req);

	/**
	 * <pre>
	 * 2.3.9. 사용자별 정책 등록/수정.
	 * </pre>
	 * 
	 * @param header
	 * @param req
	 * @return CreateIndividualPolicyRes
	 */
	public CreateIndividualPolicyRes createIndividualPolicy(SacRequestHeader header, CreateIndividualPolicyReq req);

	/**
	 * <pre>
	 * 2.3.10. 사용자별 정책 삭제.
	 * </pre>
	 * 
	 * @param header
	 * @param req
	 * @return RemoveIndividualPolicyRes
	 */
	public RemoveIndividualPolicyRes removeIndividualPolicy(SacRequestHeader header, RemoveIndividualPolicyReq req);
}
