/*
 * Copyright (c) 2013 SK planet.
 * All right reserved.
 *
 * This software is the confidential and proprietary information of SK planet.
 * You shall not disclose such Confidential Information and
 * shall use it only in accordance with the terms of the license agreement
 * you entered into with SK planet.
 */
package com.skplanet.storeplatform.sac.display.cache.service;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.skplanet.storeplatform.framework.core.persistence.dao.CommonDAO;
import com.skplanet.storeplatform.sac.display.meta.vo.MetaInfo;

/**
 * 특정 상품 메타 캐시 조회 서비스 클래스
 * (캐시 적용하려 하였으나, 성능 개선 효과가 미비하여 적용 취소)
 * 
 * Updated on : 2014. 8. 25.
 * Updated by : 서대영, SK플래닛
 */
@Service
public class CategorySpecificProductInfoManagerImpl implements CategorySpecificProductInfoManager {

    @Autowired
    @Qualifier("sac")
    private CommonDAO commonDAO;
    
    public void setCommonDAO(CommonDAO commonDAO) {
		this.commonDAO = commonDAO;
	}

	@Override
    // @Cacheable(value = "sac:display:category:product:ebookcomic", unless = "#result == null")
    public MetaInfo getEbookComicMeta(Map<String, Object> paramMap) {
        return commonDAO.queryForObject("CategorySpecificProduct.getEbookComicMetaInfo", paramMap, MetaInfo.class);
    }
	
}
