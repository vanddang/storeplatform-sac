package com.skplanet.storeplatform.sac.display.meta.service;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.skplanet.storeplatform.framework.core.persistence.dao.CommonDAO;
import com.skplanet.storeplatform.sac.display.meta.vo.MetaInfo;

/**
 * Meta 정보 조회 Service 구현체
 * 
 * Updated on : 2014. 1. 27. Updated by : 오승민, 인크로스
 */
@Service
public class MetaInfoServiceImpl implements MetaInfoService {
	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	@Qualifier("sac")
	private CommonDAO commonDAO;

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.skplanet.storeplatform.sac.display.meta.service.MetaInfoService#getAppMetaInfo(java.util.Map)
	 */
	@Override
	public MetaInfo getAppMetaInfo(Map<String, Object> paramMap) {
		return this.commonDAO.queryForObject("MetaInfo.getAppMetaInfo", paramMap, MetaInfo.class);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.skplanet.storeplatform.sac.display.meta.service.MetaInfoService#getMusicMetaInfo(java.util.Map)
	 */
	@Override
	public MetaInfo getMusicMetaInfo(Map<String, Object> paramMap) {
		return this.commonDAO.queryForObject("MetaInfo.getMusicMetaInfo", paramMap, MetaInfo.class);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.skplanet.storeplatform.sac.display.meta.service.MetaInfoService#getVODMetaInfo(java.util.Map)
	 */
	@Override
	public MetaInfo getVODMetaInfo(Map<String, Object> paramMap) {
		return this.commonDAO.queryForObject("MetaInfo.getVODMetaInfo", paramMap, MetaInfo.class);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.skplanet.storeplatform.sac.display.meta.service.MetaInfoService#getEbookComicMetaInfo(java.util.Map)
	 */
	@Override
	public MetaInfo getEbookComicMetaInfo(Map<String, Object> paramMap) {
		return this.commonDAO.queryForObject("MetaInfo.getEbookComicMetaInfo", paramMap, MetaInfo.class);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.skplanet.storeplatform.sac.display.meta.service.MetaInfoService#getWebtoonMetaInfo(java.util.Map)
	 */
	@Override
	public MetaInfo getWebtoonMetaInfo(Map<String, Object> paramMap) {
		return this.commonDAO.queryForObject("MetaInfo.getWebtoonMetaInfo", paramMap, MetaInfo.class);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.skplanet.storeplatform.sac.display.meta.service.MetaInfoService#getShoppingMetaInfo(java.util.Map)
	 */
	@Override
	public MetaInfo getShoppingMetaInfo(Map<String, Object> paramMap) {
		return this.commonDAO.queryForObject("MetaInfo.getShoppingMetaInfo", paramMap, MetaInfo.class);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.skplanet.storeplatform.sac.display.meta.service.MetaInfoService#getFreepassMetaInfo(java.util.Map)
	 */
	@Override
	public MetaInfo getFreepassMetaInfo(Map<String, Object> paramMap) {
		return this.commonDAO.queryForObject("MetaInfo.getFreepassMetaInfo", paramMap, MetaInfo.class);
	}

}
