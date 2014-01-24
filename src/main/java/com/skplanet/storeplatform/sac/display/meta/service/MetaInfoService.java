package com.skplanet.storeplatform.sac.display.meta.service;

import java.util.Map;

import com.skplanet.storeplatform.sac.display.meta.vo.MetaInfo;

public interface MetaInfoService {

	public MetaInfo getAppMetaInfo(Map<String, Object> paramMap);

	public MetaInfo getMusicMetaInfo(Map<String, Object> paramMap);

	// TODO osm1021 review cache 문제가 없다면 VO를 파라미터로 추가 (일단 추가)
	public MetaInfo getVODMetaInfo(Map<String, Object> paramMap);

	public MetaInfo getEbookComidMetaInfo(Map<String, Object> paramMap);

	public MetaInfo getShoppingMetaInfo(Map<String, Object> paramMap);
}
