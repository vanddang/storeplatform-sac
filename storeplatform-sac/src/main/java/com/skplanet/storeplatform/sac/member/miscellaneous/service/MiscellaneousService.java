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
