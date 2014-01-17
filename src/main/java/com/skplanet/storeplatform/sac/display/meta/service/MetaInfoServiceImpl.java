package com.skplanet.storeplatform.sac.display.meta.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.skplanet.storeplatform.framework.core.persistence.dao.CommonDAO;
import com.skplanet.storeplatform.sac.display.meta.vo.MetaInfo;
import com.skplanet.storeplatform.sac.display.meta.vo.ProductBasicInfo;

@Service
@Transactional
public class MetaInfoServiceImpl implements MetaInfoService {
	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	@Qualifier("sac")
	private CommonDAO commonDAO;

	@Override
	public MetaInfo getAppMetaInfo(ProductBasicInfo productBasicInfo) {
		return this.commonDAO.queryForObject("MetaInfo.getAppMetaInfo", productBasicInfo, MetaInfo.class);
	}

	@Override
	public MetaInfo getMusicMetaInfo(ProductBasicInfo productBasicInfo) {
		return this.commonDAO.queryForObject("MetaInfo.getMusicMetaInfo", productBasicInfo, MetaInfo.class);
	}

	@Override
	public MetaInfo getVODMetaInfo(ProductBasicInfo productBasicInfo) {
		return this.commonDAO.queryForObject("MetaInfo.getVODMetaInfo", productBasicInfo, MetaInfo.class);
	}

	@Override
	public MetaInfo getEbookComidMetaInfo(ProductBasicInfo productBasicInfo) {
		return this.commonDAO.queryForObject("MetaInfo.getEbookComidMetaInfo", productBasicInfo, MetaInfo.class);
	}

	@Override
	public MetaInfo getShoppingMetaInfo(ProductBasicInfo productBasicInfo) {
		return this.commonDAO.queryForObject("MetaInfo.getShoppingMetaInfo", productBasicInfo, MetaInfo.class);
	}

}
