package com.skplanet.storeplatform.sac.display.meta.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.skplanet.storeplatform.framework.core.persistence.dao.CommonDAO;
import com.skplanet.storeplatform.sac.display.meta.vo.AppMetaInfo;
import com.skplanet.storeplatform.sac.display.meta.vo.EbookComicMetaInfo;
import com.skplanet.storeplatform.sac.display.meta.vo.MusicMetaInfo;
import com.skplanet.storeplatform.sac.display.meta.vo.ProductBasicInfo;
import com.skplanet.storeplatform.sac.display.meta.vo.ShoppingMetaInfo;
import com.skplanet.storeplatform.sac.display.meta.vo.VODMetaInfo;

@Service
@Transactional
public class MetaInfoServiceImpl implements MetaInfoService {
	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	@Qualifier("sac")
	private CommonDAO commonDAO;

	@Override
	public VODMetaInfo getVODMetaInfo(ProductBasicInfo productBasicInfo) {
		return this.commonDAO.queryForObject("MetaInfo.getVODMetaInfo", productBasicInfo, VODMetaInfo.class);
	}

	@Override
	public MusicMetaInfo getMusicMetaInfo(ProductBasicInfo productBasicInfo) {
		return this.commonDAO.queryForObject("MetaInfo.getMusicMetaInfo", productBasicInfo, MusicMetaInfo.class);
	}

	@Override
	public EbookComicMetaInfo getEbookComidMetaInfo(ProductBasicInfo productBasicInfo) {
		return this.commonDAO.queryForObject("MetaInfo.getEbookComidMetaInfo", productBasicInfo,
				EbookComicMetaInfo.class);
	}

	@Override
	public AppMetaInfo getAppMetaInfo(ProductBasicInfo productBasicInfo) {
		return this.commonDAO.queryForObject("MetaInfo.getAppMetaInfo", productBasicInfo, AppMetaInfo.class);
	}

	@Override
	public ShoppingMetaInfo getShoppingMetaInfo(ProductBasicInfo productBasicInfo) {
		return this.commonDAO.queryForObject("MetaInfo.getShoppingMetaInfo", productBasicInfo, ShoppingMetaInfo.class);
	}

}
