package com.skplanet.storeplatform.sac.member.miscellaneous.service;

import com.skplanet.storeplatform.sac.client.member.vo.miscellaneous.*;
import com.skplanet.storeplatform.sac.common.header.vo.SacRequestHeader;

/**
 * 
 * Device info 기능 관련 인터페이스
 * 
 * Updated on : 2014. 1. 7. Updated by : 김다슬, 인크로스.
 * Updated on : 2015. 12. 28. Updated by : 임근대, SKP. - Micellaneos 클래스에서 Device info 관련 기능 클래스 분리
 */
public interface DeviceInfoService {

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
	GetUaCodeRes getUaCode(SacRequestHeader requestHeader, GetUaCodeReq request);

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
	GetOpmdRes getOpmd(GetOpmdReq request);

	/**
	 * <pre>
	 * 단말 모델코드 조회.
	 * </pre>
	 * 
	 * @param request
	 *            GetModelCodeReq
	 * @return GetModelCodeRes
	 */
	GetModelCodeRes getModelCode(GetModelCodeReq request);

}
