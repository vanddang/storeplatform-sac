package com.skplanet.storeplatform.sac.member.miscellaneous.service;

import com.skplanet.storeplatform.sac.client.member.vo.user.PinAuthorizationCheckReq;
import com.skplanet.storeplatform.sac.client.member.vo.user.PinAuthorizationCheckRes;
import com.skplanet.storeplatform.sac.common.header.vo.SacRequestHeader;

/**
 * 
 * PIN 기능 관련 인터페이스
 * 
 * Updated on : 2014. 1. 7. Updated by : 김다슬, 인크로스.
 * Updated on : 2015. 12. 28. Updated by : 임근대, SKP. - Miscellaneous 클래스에서 PIN 관련 기능 클래스 분리
 */
public interface PinService {

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
	PinAuthorizationCheckRes pinAuthorizationCheck(SacRequestHeader header, PinAuthorizationCheckReq request);

}
