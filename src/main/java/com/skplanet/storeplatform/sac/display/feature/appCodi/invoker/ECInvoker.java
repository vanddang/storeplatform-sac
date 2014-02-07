package com.skplanet.storeplatform.sac.display.feature.appCodi.invoker;

import com.skplanet.storeplatform.external.client.isf.vo.ISFRes;
import com.skplanet.storeplatform.framework.core.exception.StorePlatformException;
import com.skplanet.storeplatform.sac.client.display.vo.feature.appCodi.AppCodiReq;

public interface ECInvoker {

	public ISFRes invoke(AppCodiReq requestVO) throws StorePlatformException;

}
