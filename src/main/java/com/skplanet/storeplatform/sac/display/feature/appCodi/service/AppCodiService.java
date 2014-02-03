package com.skplanet.storeplatform.sac.display.feature.appCodi.service;

import java.io.IOException;

import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.map.JsonMappingException;

import com.skplanet.storeplatform.sac.client.display.vo.feature.appCodi.AppCodiListRes;
import com.skplanet.storeplatform.sac.client.display.vo.feature.appCodi.AppCodiReq;
import com.skplanet.storeplatform.sac.common.header.vo.SacRequestHeader;

/**
 * App Codi Service 인터페이스(CoreStoreBusiness)
 * 
 * Updated on : 2014. 01. 28. Updated by : 윤주영, SK 플래닛.
 */
public interface AppCodiService {

	/**
	 * <pre>
	 * 대분류 전시 Menu 조회.
	 * </pre>
	 * 
	 * @param AppCodiReq
	 *            requestVO
	 * @param SacRequestHeader
	 *            requestHeader
	 * @return AppCodiListRes
	 */
	public AppCodiListRes searchAppCodiList(AppCodiReq requestVO, SacRequestHeader requestHeader)
			throws JsonGenerationException, JsonMappingException, IOException, Exception;
}
