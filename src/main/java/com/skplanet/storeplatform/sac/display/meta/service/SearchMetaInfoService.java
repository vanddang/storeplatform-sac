package com.skplanet.storeplatform.sac.display.meta.service;

import java.util.List;

import com.skplanet.storeplatform.sac.client.display.vo.search.SearchProductReq;
import com.skplanet.storeplatform.sac.display.search.vo.SearchProductDTO;
import com.skplanet.storeplatform.sac.display.search.vo.SearchProductMetaInfoDTO;

public interface SearchMetaInfoService {
	public List<SearchProductMetaInfoDTO> searchMetaInfoList(SearchProductReq req, SearchProductDTO dto);
}
