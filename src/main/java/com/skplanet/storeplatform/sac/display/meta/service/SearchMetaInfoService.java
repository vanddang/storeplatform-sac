package com.skplanet.storeplatform.sac.display.meta.service;

import com.skplanet.storeplatform.sac.client.display.vo.search.SearchProductRes;
import com.skplanet.storeplatform.sac.display.search.vo.SearchProductDTO;

public interface SearchMetaInfoService {
	public SearchProductRes searchMetaInfoList(SearchProductDTO dto);
}
