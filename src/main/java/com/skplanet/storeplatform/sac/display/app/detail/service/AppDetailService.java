package com.skplanet.storeplatform.sac.display.app.detail.service;

import com.skplanet.storeplatform.sac.client.display.vo.app.AppDetailReq;
import com.skplanet.storeplatform.sac.client.display.vo.app.AppDetailRes;

/**
 * AppDetailService
 *
 * Updated on : 2014. 01. 06. Updated by : 정희원, SK 플래닛.
 */
public interface AppDetailService {

    public AppDetailRes getAppDetail(AppDetailReq request);

}
