package com.skplanet.storeplatform.sac.member.miscellaneous.service;

import com.skplanet.storeplatform.sac.client.member.vo.miscellaneous.AuthorizeAccountReq;
import com.skplanet.storeplatform.sac.client.member.vo.miscellaneous.AuthorizeAccountRes;
import com.skplanet.storeplatform.sac.client.member.vo.miscellaneous.ConfirmCaptchaReq;
import com.skplanet.storeplatform.sac.client.member.vo.miscellaneous.ConfirmCaptchaRes;
import com.skplanet.storeplatform.sac.client.member.vo.miscellaneous.ConfirmEmailAuthorizationCodeReq;
import com.skplanet.storeplatform.sac.client.member.vo.miscellaneous.ConfirmEmailAuthorizationCodeRes;
import com.skplanet.storeplatform.sac.client.member.vo.miscellaneous.ConfirmEmailAuthorizationUrlSacReq;
import com.skplanet.storeplatform.sac.client.member.vo.miscellaneous.ConfirmEmailAuthorizationUrlSacRes;
import com.skplanet.storeplatform.sac.client.member.vo.miscellaneous.ConfirmPhoneAuthorizationCheckReq;
import com.skplanet.storeplatform.sac.client.member.vo.miscellaneous.ConfirmPhoneAuthorizationCheckRes;
import com.skplanet.storeplatform.sac.client.member.vo.miscellaneous.ConfirmPhoneAuthorizationCodeReq;
import com.skplanet.storeplatform.sac.client.member.vo.miscellaneous.ConfirmPhoneAuthorizationCodeRes;
import com.skplanet.storeplatform.sac.client.member.vo.miscellaneous.CreateAdditionalServiceReq;
import com.skplanet.storeplatform.sac.client.member.vo.miscellaneous.CreateAdditionalServiceRes;
import com.skplanet.storeplatform.sac.client.member.vo.miscellaneous.CreateIndividualPolicyReq;
import com.skplanet.storeplatform.sac.client.member.vo.miscellaneous.CreateIndividualPolicyRes;
import com.skplanet.storeplatform.sac.client.member.vo.miscellaneous.GetAdditionalServiceReq;
import com.skplanet.storeplatform.sac.client.member.vo.miscellaneous.GetAdditionalServiceRes;
import com.skplanet.storeplatform.sac.client.member.vo.miscellaneous.GetCaptchaRes;
import com.skplanet.storeplatform.sac.client.member.vo.miscellaneous.GetEmailAuthorizationCodeReq;
import com.skplanet.storeplatform.sac.client.member.vo.miscellaneous.GetEmailAuthorizationCodeRes;
import com.skplanet.storeplatform.sac.client.member.vo.miscellaneous.GetEmailAuthorizationUrlSacReq;
import com.skplanet.storeplatform.sac.client.member.vo.miscellaneous.GetEmailAuthorizationUrlSacRes;
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
import com.skplanet.storeplatform.sac.client.member.vo.user.PinAuthorizationCheckReq;
import com.skplanet.storeplatform.sac.client.member.vo.user.PinAuthorizationCheckRes;
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
	 * 
	 */
	public GetUaCodeRes getUaCode(SacRequestHeader requestHeader, GetUaCodeReq request);

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
	 */
	public GetOpmdRes getOpmd(GetOpmdReq request);

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
	public GetPhoneAuthorizationCodeRes getPhoneAuthorizationCode(SacRequestHeader requestHeader,
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
	public ConfirmPhoneAuthorizationCodeRes confirmPhoneAutorizationCode(SacRequestHeader requestHeader,
			ConfirmPhoneAuthorizationCodeReq request);

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
	public GetEmailAuthorizationCodeRes getEmailAuthorizationCode(SacRequestHeader requestHeader,
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
	public ConfirmEmailAuthorizationCodeRes confirmEmailAuthorizationCode(ConfirmEmailAuthorizationCodeReq request);

	/**
	 * <pre>
	 * 부가서비스 가입 - IDP 연동.
	 * </pre>
	 * 
	 * @param request
	 *            CreateAdditionalServiceReq
	 * @return CreateAdditionalServiceRes
	 */
	public CreateAdditionalServiceRes regAdditionalService(CreateAdditionalServiceReq request);

	/**
	 * <pre>
	 * 부가서비스 가입 조회 - IDP 연동.
	 * </pre>
	 * 
	 * @param request
	 *            GetAdditionalServiceReq
	 * @return GetAdditionalServiceRes
	 */
	public GetAdditionalServiceRes getAdditionalService(GetAdditionalServiceReq request);

	/**
	 * <pre>
	 * 단말 모델코드 조회.
	 * </pre>
	 * 
	 * @param request
	 *            GetModelCodeReq
	 * @return GetModelCodeRes
	 */
	public GetModelCodeRes getModelCode(GetModelCodeReq request);

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
	 *            SacRequestHeader
	 * @param req
	 *            GetIndividualPolicyReq
	 * @return GetIndividualPolicyRes
	 */
	public GetIndividualPolicyRes getIndividualPolicy(SacRequestHeader header, GetIndividualPolicyReq req);

	/**
	 * <pre>
	 * 2.3.9. 사용자별 정책 등록/수정.
	 * </pre>
	 * 
	 * @param header
	 *            SacRequestHeader
	 * @param req
	 *            CreateIndividualPolicyReq
	 * @return CreateIndividualPolicyRes
	 */
	public CreateIndividualPolicyRes regIndividualPolicy(SacRequestHeader header, CreateIndividualPolicyReq req);

	/**
	 * <pre>
	 * 2.3.10. 사용자별 정책 삭제.
	 * </pre>
	 * 
	 * @param header
	 *            SacRequestHeader
	 * @param req
	 *            RemoveIndividualPolicyReq
	 * @return RemoveIndividualPolicyRes
	 */
	public RemoveIndividualPolicyRes remIndividualPolicy(SacRequestHeader header, RemoveIndividualPolicyReq req);

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
	public ConfirmPhoneAuthorizationCheckRes confirmPhoneAutorizationCheck(SacRequestHeader header,
			ConfirmPhoneAuthorizationCheckReq req);

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
	public GetEmailAuthorizationUrlSacRes getEmailAuthorizationUrl(SacRequestHeader header,
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
	public ConfirmEmailAuthorizationUrlSacRes confirmEmailAuthorizationUrl(SacRequestHeader header,
			ConfirmEmailAuthorizationUrlSacReq req);

	/**
	 * <pre>
	 * 2.1.60.	휴대기기 PIN 번호 인증 여부 확인.
	 * </pre>
	 * 
	 * @param header
	 *            SacRequestHeader
	 * @param request
	 *            PinAuthorizationCheckReq
	 * @return PinAuthorizationCheckRes
	 */
	public PinAuthorizationCheckRes pinAuthorizationCheck(SacRequestHeader header, PinAuthorizationCheckReq req);

}
