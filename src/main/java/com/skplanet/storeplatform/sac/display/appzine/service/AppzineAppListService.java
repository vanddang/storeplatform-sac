package com.skplanet.storeplatform.sac.display.appzine.service;

import java.io.IOException;

import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.map.JsonMappingException;

import com.skplanet.storeplatform.sac.client.display.vo.appzine.AppzineAppListSacReq;
import com.skplanet.storeplatform.sac.client.display.vo.appzine.AppzineAppListSacRes;
import com.skplanet.storeplatform.sac.common.header.vo.SacRequestHeader;

/**
 * AppzineAppListService 인터페이스(CoreStoreBusiness)
 * 
 * Updated on : 2014. 02. 10. Updated by : 유시혁.
 */
public interface AppzineAppListService {

	/**
	 * <pre>
	 * Appzine 앱 목록 조회
	 * </pre>
	 * 
	 * @param AppzineAppListSacRes
	 *            reqeustVO
	 * @param SacRequestHeader
	 *            requestHeader
	 * @return AppzineAppListSacRes
	 */
	public AppzineAppListSacRes searchAppzineAppList(AppzineAppListSacReq reqeustVO, SacRequestHeader requestHeader)
			throws JsonGenerationException, JsonMappingException, IOException, Exception;

}
