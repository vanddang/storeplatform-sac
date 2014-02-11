package com.skplanet.storeplatform.sac.display.feature.theme.recommend.invoker;

import com.skplanet.storeplatform.external.client.isf.vo.ISFRes;
import com.skplanet.storeplatform.framework.core.exception.StorePlatformException;
import com.skplanet.storeplatform.sac.client.display.vo.feature.recommend.ThemeRecommendReq;

/**
 * Theme Recommend EC Service 인터페이스(CoreStoreBusiness)
 * 
 * Updated on : 2014. 02. 10. Updated by : 윤주영, SK 플래닛.
 */
public interface ThemeRecommendInvoker {

	public ISFRes invoke(ThemeRecommendReq requestVO) throws StorePlatformException;

}
