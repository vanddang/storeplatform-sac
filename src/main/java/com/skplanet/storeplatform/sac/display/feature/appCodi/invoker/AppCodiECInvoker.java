package com.skplanet.storeplatform.sac.display.feature.appCodi.invoker;

import com.skplanet.storeplatform.external.client.isf.vo.ISFRes;
import com.skplanet.storeplatform.framework.core.exception.StorePlatformException;
import com.skplanet.storeplatform.sac.client.display.vo.feature.appCodi.AppCodiSacReq;

/**
 * App Codi EC Invoker Interface
 * 
 * Updated on : 2014. 01. 28. Updated by : 윤주영, SK 플래닛.
 */
public interface AppCodiECInvoker {

	public ISFRes invoke(AppCodiSacReq requestVO) throws StorePlatformException;

}
