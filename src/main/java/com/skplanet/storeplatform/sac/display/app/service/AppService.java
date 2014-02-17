package com.skplanet.storeplatform.sac.display.app.service;

import com.skplanet.storeplatform.sac.display.app.vo.AppDetailParam;
import com.skplanet.storeplatform.sac.client.display.vo.app.AppDetailRes;

/**
 * AppService
 *
 * Updated on : 2014. 01. 06. Updated by : 정희원, SK 플래닛.
 */
public interface AppService {

    public AppDetailRes getAppDetail(AppDetailParam request);

}
