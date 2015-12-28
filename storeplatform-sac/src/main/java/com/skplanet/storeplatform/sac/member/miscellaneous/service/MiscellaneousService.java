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
	AuthorizeAccountRes authorizeAccount(AuthorizeAccountReq request);

}
