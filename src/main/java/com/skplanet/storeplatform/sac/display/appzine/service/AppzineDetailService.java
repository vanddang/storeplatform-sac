package com.skplanet.storeplatform.sac.display.appzine.service;

import java.io.IOException;

import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.map.JsonMappingException;

import com.skplanet.storeplatform.sac.client.display.vo.appzine.AppzineDetailSacReq;
import com.skplanet.storeplatform.sac.client.display.vo.appzine.AppzineDetailSacRes;
import com.skplanet.storeplatform.sac.common.header.vo.SacRequestHeader;

/**
 * AppzineDetailService 인터페이스(CoreStoreBusiness)
 * 
 * Updated on : 2014. 02. 10. Updated by : 유시혁.
 */
public interface AppzineDetailService {

	/**
	 * <pre>
	 * Appzine 회차별 목록 조회
	 * </pre>
	 * 
	 * @param AppzineDetailSacRes
	 *            reqeustVO
	 * @param SacRequestHeader
	 *            requestHeader
	 * @return AppzineDetailSacRes
	 */
	public AppzineDetailSacRes searchAppzineDetail(AppzineDetailSacReq reqeustVO, SacRequestHeader requestHeader)
			throws JsonGenerationException, JsonMappingException, IOException, Exception;

}
