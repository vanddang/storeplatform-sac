package com.skplanet.storeplatform.sac.display.meta.service;

import com.skplanet.storeplatform.sac.display.meta.vo.AppMetaInfo;
import com.skplanet.storeplatform.sac.display.meta.vo.EbookComicMetaInfo;
import com.skplanet.storeplatform.sac.display.meta.vo.MusicMetaInfo;
import com.skplanet.storeplatform.sac.display.meta.vo.ProductBasicInfo;
import com.skplanet.storeplatform.sac.display.meta.vo.ShoppingMetaInfo;
import com.skplanet.storeplatform.sac.display.meta.vo.VODMetaInfo;

public interface MetaInfoService {

	// TODO osm1021 review cache 문제가 없다면 VO를 파라미터로 추가 (일단 추가)
	public VODMetaInfo getVODMetaInfo(ProductBasicInfo productBasicInfo);

	public MusicMetaInfo getMusicMetaInfo(ProductBasicInfo productBasicInfo);

	public EbookComicMetaInfo getEbookComidMetaInfo(ProductBasicInfo productBasicInfo);

	public AppMetaInfo getAppMetaInfo(ProductBasicInfo productBasicInfo);

	public ShoppingMetaInfo getShoppingMetaInfo(ProductBasicInfo productBasicInfo);
}
