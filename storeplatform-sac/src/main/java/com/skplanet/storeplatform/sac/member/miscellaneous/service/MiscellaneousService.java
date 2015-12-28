package com.skplanet.storeplatform.sac.member.miscellaneous.service;

import com.skplanet.storeplatform.sac.client.member.vo.miscellaneous.*;
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
	 * 2.1.60.	휴대기기 PIN 번호 인증 여부 확인.
	 * </pre>
	 * 
	 * @param header
	 *            SacRequestHeader
	 * @param request
	 *            PinAuthorizationCheckReq
	 * @return PinAuthorizationCheckRes
	 */
	public PinAuthorizationCheckRes pinAuthorizationCheck(SacRequestHeader header, PinAuthorizationCheckReq request);

}
