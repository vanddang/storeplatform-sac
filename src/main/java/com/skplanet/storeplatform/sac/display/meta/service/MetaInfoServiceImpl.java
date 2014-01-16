package com.skplanet.storeplatform.sac.display.meta.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.skplanet.storeplatform.framework.core.persistence.dao.CommonDAO;
import com.skplanet.storeplatform.sac.display.search.vo.SearchProduct;
import com.skplanet.storeplatform.sac.display.search.vo.VODMetaInfo;

@Service
@Transactional
public class MetaInfoServiceImpl implements MetaInfoService {
	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	@Qualifier("sac")
	private CommonDAO commonDAO;

	@Override
	public VODMetaInfo getVODMetaInfo(SearchProduct dto) {
		return this.commonDAO.queryForObject("MetaInfo.getVODMetaInfo", dto, VODMetaInfo.class);
	}

}
