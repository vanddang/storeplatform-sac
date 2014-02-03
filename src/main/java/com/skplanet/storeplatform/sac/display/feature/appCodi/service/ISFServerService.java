package com.skplanet.storeplatform.sac.display.feature.appCodi.service;

import java.io.IOException;

import com.skplanet.storeplatform.sac.display.feature.appCodi.vo.Request;
import com.skplanet.storeplatform.sac.display.feature.appCodi.vo.Response;

/**
 * ISF Server Service 인터페이스(CoreStoreBusiness)
 * 
 * Updated on : 2014. 01. 28. Updated by : 윤주영, SK 플래닛.
 */
public interface ISFServerService {

	/**
	 * <pre>
	 * ISF Server make data service..
	 * </pre>
	 * 
	 * @param Request
	 *            requestVO
	 * @return Response
	 */
	public Response makeAppCodiList(Request requestVO) throws IOException, Exception;

	public String makeAppCodiXML();
}
