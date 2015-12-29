package com.skplanet.storeplatform.sac.member.miscellaneous.service;

import com.skplanet.storeplatform.sac.client.member.vo.miscellaneous.CreateAdditionalServiceReq;
import com.skplanet.storeplatform.sac.client.member.vo.miscellaneous.CreateAdditionalServiceRes;
import com.skplanet.storeplatform.sac.client.member.vo.miscellaneous.GetAdditionalServiceReq;
import com.skplanet.storeplatform.sac.client.member.vo.miscellaneous.GetAdditionalServiceRes;

/**
 * 
 * 부가 서비스 기능 관련 인터페이스
 * 
 * Updated on : 2014. 1. 7. Updated by : 김다슬, 인크로스.
 * Updated on : 2015. 12. 28. Updated by : 임근대, SKP. - Micellaneos 클래스에서 부가 서비스 관련 기능 클래스 분리
 */
public interface AdditionalServiceService {

	/**
	 * <pre>
	 * 부가서비스 가입 - IDP 연동.
	 * </pre>
	 * 
	 * @param request
	 *            CreateAdditionalServiceReq
	 * @return CreateAdditionalServiceRes
	 */
	CreateAdditionalServiceRes regAdditionalService(CreateAdditionalServiceReq request);

	/**
	 * <pre>
	 * 부가서비스 가입 조회 - IDP 연동.
	 * </pre>
	 * 
	 * @param request
	 *            GetAdditionalServiceReq
	 * @return GetAdditionalServiceRes
	 */
	GetAdditionalServiceRes getAdditionalService(GetAdditionalServiceReq request);

}
