package com.skplanet.storeplatform.sac.display.feature.isf.invoker;

import com.skplanet.storeplatform.external.client.isf.vo.ISFRes;
import com.skplanet.storeplatform.framework.core.exception.StorePlatformException;
import com.skplanet.storeplatform.sac.display.feature.isf.invoker.vo.IsfEcReq;

/**
 * ISF EC Invoker Interface
 * 
 * Updated on : 2014. 02. 21. Updated by : 윤주영, SK 플래닛.
 */
public interface IsfEcInvoker {

	public ISFRes invoke(IsfEcReq requestVO) throws StorePlatformException;

}
