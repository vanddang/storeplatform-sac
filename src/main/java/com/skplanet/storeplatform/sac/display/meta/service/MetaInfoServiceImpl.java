package com.skplanet.storeplatform.sac.display.meta.service;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.skplanet.storeplatform.framework.core.persistence.dao.CommonDAO;
import com.skplanet.storeplatform.sac.display.meta.vo.MetaInfo;

@Service
@Transactional
public class MetaInfoServiceImpl implements MetaInfoService {
	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	@Qualifier("sac")
	private CommonDAO commonDAO;

	@Override
	public MetaInfo getAppMetaInfo(Map<String, Object> paramMap) {
		return this.commonDAO.queryForObject("MetaInfo.getAppMetaInfo", paramMap, MetaInfo.class);
	}

	@Override
	public MetaInfo getMusicMetaInfo(Map<String, Object> paramMap) {
		return this.commonDAO.queryForObject("MetaInfo.getMusicMetaInfo", paramMap, MetaInfo.class);
	}

	@Override
	public MetaInfo getVODMetaInfo(Map<String, Object> paramMap) {
		return this.commonDAO.queryForObject("MetaInfo.getVODMetaInfo", paramMap, MetaInfo.class);
	}

	@Override
	public MetaInfo getEbookComidMetaInfo(Map<String, Object> paramMap) {
		return this.commonDAO.queryForObject("MetaInfo.getEbookComidMetaInfo", paramMap, MetaInfo.class);
	}

	@Override
	public MetaInfo getShoppingMetaInfo(Map<String, Object> paramMap) {
		return this.commonDAO.queryForObject("MetaInfo.getShoppingMetaInfo", paramMap, MetaInfo.class);
	}

}
