package com.skplanet.storeplatform.sac.display.meta.service;

import com.skplanet.storeplatform.sac.display.meta.vo.MetaInfo;
import com.skplanet.storeplatform.sac.display.meta.vo.ProductBasicInfo;

public interface MetaInfoService {

	public MetaInfo getAppMetaInfo(ProductBasicInfo productBasicInfo);

	public MetaInfo getMusicMetaInfo(ProductBasicInfo productBasicInfo);

	// TODO osm1021 review cache 문제가 없다면 VO를 파라미터로 추가 (일단 추가)
	public MetaInfo getVODMetaInfo(ProductBasicInfo productBasicInfo);

	public MetaInfo getEbookComidMetaInfo(ProductBasicInfo productBasicInfo);

	public MetaInfo getShoppingMetaInfo(ProductBasicInfo productBasicInfo);
}
