package com.skplanet.storeplatform.sac.display.meta.service;

import com.skplanet.storeplatform.sac.display.search.vo.SearchProduct;
import com.skplanet.storeplatform.sac.display.search.vo.VODMetaInfo;

public interface MetaInfoService {

	// TODO osm1021 review cache 문제가 없다면 VO를 파라미터로 추가 (일단 추가)
	public VODMetaInfo getVODMetaInfo(SearchProduct dto);
}
