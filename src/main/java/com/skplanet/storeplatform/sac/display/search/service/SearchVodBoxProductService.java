package com.skplanet.storeplatform.sac.display.search.service;

import com.skplanet.storeplatform.sac.client.display.vo.search.SearchProductReq;
import com.skplanet.storeplatform.sac.client.display.vo.search.SearchProductRes;
import com.skplanet.storeplatform.sac.common.header.vo.SacRequestHeader;

public interface SearchVodBoxProductService {
	public SearchProductRes searchVodBoxProduct(SearchProductReq req, SacRequestHeader header);
}
