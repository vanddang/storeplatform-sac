/*
 * Copyright (c) 2013 SK planet.
 * All right reserved.
 *
 * This software is the confidential and proprietary information of SK planet.
 * You shall not disclose such Confidential Information and
 * shall use it only in accordance with the terms of the license agreement
 * you entered into with SK planet.
 */
package com.skplanet.storeplatform.sac.display.meta.service;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;

import com.skplanet.storeplatform.framework.core.persistence.dao.CommonDAO;
import com.skplanet.storeplatform.sac.display.meta.vo.MetaInfo;

/**
 * 특정 상품 Meta 정보 조회 서비스 클래스
 * 
 * Updated on : 2014. 8. 25. Updated by : 서대영, SK플래닛.
 */
@Service
public class CategorySpecificMetaInfoServiceImpl implements CategorySpecificMetaInfoService {

	@Autowired
	@Qualifier("sac")
	private CommonDAO commonDAO;
	
	public void setCommonDAO(CommonDAO commonDAO) {
		this.commonDAO = commonDAO;
	}

	@Override
	public MetaInfo getSpecificEbookList(Map<String, Object> paramMap) {
		MetaInfo me;
		
		if (isUseCache()) {
			me = commonDAO.queryForObject("CategorySpecificProduct.getEbookComicMetaInfo", paramMap, MetaInfo.class);
		} else {
			me = commonDAO.queryForObject("CategorySpecificProduct.getEbookComicMetaInfo", paramMap, MetaInfo.class);
		}
		
		return me;
	}
	
    private boolean isUseCache() {
        return (Boolean) RequestContextHolder.currentRequestAttributes().getAttribute("useCache", RequestAttributes.SCOPE_REQUEST);
    }

}
