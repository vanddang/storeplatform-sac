package com.skplanet.storeplatform.sac.display.search.service;

import com.skplanet.storeplatform.sac.client.display.vo.search.SearchProductReq;
import com.skplanet.storeplatform.sac.client.display.vo.search.SearchProductRes;

public interface SearchVodBoxProductService {
	public SearchProductRes searchVodBoxProdIdList(SearchProductReq req);
}
