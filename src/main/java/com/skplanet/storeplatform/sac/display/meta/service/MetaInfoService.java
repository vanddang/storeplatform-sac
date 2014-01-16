package com.skplanet.storeplatform.sac.display.meta.service;

import java.util.List;

import com.skplanet.storeplatform.sac.display.search.vo.SearchProductDTO;
import com.skplanet.storeplatform.sac.display.search.vo.SearchProductMetaInfoDTO;

public interface MetaInfoService {
	public List<SearchProductMetaInfoDTO> getMetaInfoList(SearchProductDTO dto);

	// TODO osm1021 review cache 문제가 없다면 VO를 파라미터로 추가 (일단 추가)
	public SearchProductMetaInfoDTO getMetaInfo(SearchProductDTO dto);
}
