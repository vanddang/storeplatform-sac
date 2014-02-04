package com.skplanet.storeplatform.sac.member.miscellaneous.service;

import com.skplanet.storeplatform.sac.client.member.vo.miscellaneous.AuthorizeAccountReq;
import com.skplanet.storeplatform.sac.client.member.vo.miscellaneous.AuthorizeAccountRes;
import com.skplanet.storeplatform.sac.client.member.vo.miscellaneous.ConfirmCaptchaReq;
import com.skplanet.storeplatform.sac.client.member.vo.miscellaneous.ConfirmCaptchaRes;
import com.skplanet.storeplatform.sac.client.member.vo.miscellaneous.ConfirmEmailAuthorizationCodeReq;
import com.skplanet.storeplatform.sac.client.member.vo.miscellaneous.ConfirmEmailAuthorizationCodeRes;
import com.skplanet.storeplatform.sac.client.member.vo.miscellaneous.ConfirmPhoneAuthorizationCodeReq;
import com.skplanet.storeplatform.sac.client.member.vo.miscellaneous.ConfirmPhoneAuthorizationCodeRes;
import com.skplanet.storeplatform.sac.client.member.vo.miscellaneous.ConfirmRealNameAuthorizationReq;
import com.skplanet.storeplatform.sac.client.member.vo.miscellaneous.ConfirmRealNameAuthorizationRes;
import com.skplanet.storeplatform.sac.client.member.vo.miscellaneous.CreateAdditionalServiceReq;
import com.skplanet.storeplatform.sac.client.member.vo.miscellaneous.CreateAdditionalServiceRes;
import com.skplanet.storeplatform.sac.client.member.vo.miscellaneous.CreateIndividualPolicyReq;
import com.skplanet.storeplatform.sac.client.member.vo.miscellaneous.CreateIndividualPolicyRes;
import com.skplanet.storeplatform.sac.client.member.vo.miscellaneous.GetAdditionalServiceReq;
import com.skplanet.storeplatform.sac.client.member.vo.miscellaneous.GetAdditionalServiceRes;
import com.skplanet.storeplatform.sac.client.member.vo.miscellaneous.GetCaptchaRes;
import com.skplanet.storeplatform.sac.client.member.vo.miscellaneous.GetEmailAuthorizationCodeReq;
import com.skplanet.storeplatform.sac.client.member.vo.miscellaneous.GetEmailAuthorizationCodeRes;
import com.skplanet.storeplatform.sac.client.member.vo.miscellaneous.GetIndividualPolicyReq;
import com.skplanet.storeplatform.sac.client.member.vo.miscellaneous.GetIndividualPolicyRes;
import com.skplanet.storeplatform.sac.client.member.vo.miscellaneous.GetModelCodeReq;
import com.skplanet.storeplatform.sac.client.member.vo.miscellaneous.GetModelCodeRes;
import com.skplanet.storeplatform.sac.client.member.vo.miscellaneous.GetOpmdReq;
import com.skplanet.storeplatform.sac.client.member.vo.miscellaneous.GetOpmdRes;
import com.skplanet.storeplatform.sac.client.member.vo.miscellaneous.GetPhoneAuthorizationCodeReq;
import com.skplanet.storeplatform.sac.client.member.vo.miscellaneous.GetPhoneAuthorizationCodeRes;
import com.skplanet.storeplatform.sac.client.member.vo.miscellaneous.GetUaCodeReq;
import com.skplanet.storeplatform.sac.client.member.vo.miscellaneous.GetUaCodeRes;
import com.skplanet.storeplatform.sac.client.member.vo.miscellaneous.RemoveIndividualPolicyReq;
import com.skplanet.storeplatform.sac.client.member.vo.miscellaneous.RemoveIndividualPolicyRes;
import com.skplanet.storeplatform.sac.client.member.vo.miscellaneous.ResendSmsForRealNameAuthorizationReq;
import com.skplanet.storeplatform.sac.client.member.vo.miscellaneous.ResendSmsForRealNameAuthorizationRes;
import com.skplanet.storeplatform.sac.client.member.vo.miscellaneous.SendSmsForRealNameAuthorizationReq;
import com.skplanet.storeplatform.sac.client.member.vo.miscellaneous.SendSmsForRealNameAuthorizationRes;
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
	 * @param requestHeader
	 *            SacRequestHeader
	 * @param request
	 *            GetPhoneAuthorizationCodeReq
	 * @return GetEmailAuthorizationCodeRes
	 * @throws Exception
	 */
	public GetEmailAuthorizationCodeRes getEmailAuthorizationCode(SacRequestHeader requestHeader,
			GetEmailAuthorizationCodeReq request) throws Exception;

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
	 * 부가서비스 가입 - IDP 연동.
	 * </pre>
	 * 
	 * @param request
	 *            CreateAdditionalServiceReq
	 * @return CreateAdditionalServiceRes
	 * @throws Exception
	 *             Exception
	 */
	public CreateAdditionalServiceRes createAdditionalService(CreateAdditionalServiceReq request) throws Exception;

	/**
	 * <pre>
	 * 부가서비스 가입 조회 - IDP 연동.
	 * </pre>
	 * 
	 * @param request
	 *            GetAdditionalServiceReq
	 * @return GetAdditionalServiceRes
	 * @throws Exception
	 *             Exception
	 */
	public GetAdditionalServiceRes getAdditionalService(GetAdditionalServiceReq request) throws Exception;

	public GetModelCodeRes getModelCode(GetModelCodeReq request);

	/**
	 * <pre>
	 * 실명인증용 휴대폰 인증 SMS 발송.
	 * </pre>
	 * 
	 * @param request
	 *            SendSmsForRealNameAuthorizationReq
	 * @return SendSmsForRealNameAuthorizationRes
	 */
	public SendSmsForRealNameAuthorizationRes sendSmsForRealNameAuthorization(SendSmsForRealNameAuthorizationReq request);

	/**
	 * <pre>
	 * 실명인증용 휴대폰 인증 코드 확인.
	 * </pre>
	 * 
	 * @param request
	 *            ConfirmRealNameAuthorizationReq
	 * @return ConfirmRealNameAuthorizationRes
	 */
	public ConfirmRealNameAuthorizationRes confirmRealNameAuthorization(ConfirmRealNameAuthorizationReq request);

	/**
	 * <pre>
	 * 실명인증용 휴대폰 인증 SMS 재발송.
	 * </pre>
	 * 
	 * @param request
	 *            ResendSmsForRealNameAuthorizationReq
	 * @return ResendSmsForRealNameAuthorizationRes
	 */
	public ResendSmsForRealNameAuthorizationRes resendSmsForRealNameAuthorization(
			ResendSmsForRealNameAuthorizationReq request);

	/**
	 * <pre>
	 * 결제 계좌 정보 인증.
	 * </pre>
	 * 
	 * @param request
	 *            AuthorizeAccountReq
	 * @return AuthorizeAccountRes
	 */
	public AuthorizeAccountRes authorizeAccount(AuthorizeAccountReq request);

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
