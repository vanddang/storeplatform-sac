/*
 * Copyright (c) 2013 SK planet.
 * All right reserved.
 *
 * This software is the confidential and proprietary information of SK planet.
 * You shall not disclose such Confidential Information and
 * shall use it only in accordance with the terms of the license agreement
 * you entered into with SK planet.
 */
package com.skplanet.storeplatform.sac.display.feature.category.service;

import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.skplanet.storeplatform.framework.core.persistence.dao.CommonDAO;
import com.skplanet.storeplatform.sac.client.display.vo.feature.category.FeatureCategoryEpubSacReq;
import com.skplanet.storeplatform.sac.display.meta.vo.ProductBasicInfo;

/**
 * 쿼리 튜닝 검증 단위 테스트를 위해 별도의 클래스로 분리
 * 
 * Updated on : 2014. 11. 10. Updated by : 서대영, SK플래닛.
 */
@Service
public class FeatureCategoryEpubDataServiceImpl implements FeatureCategoryEpubDataService {

	@Autowired
	@Qualifier("sac")
	private CommonDAO commonDAO;

	public void setCommonDAO(CommonDAO commonDAO) {
		this.commonDAO = commonDAO;
	}
	
	@Override
	public List<ProductBasicInfo> selectCategoryEpubRecomList(FeatureCategoryEpubSacReq req) {
		List<ProductBasicInfo> list = commonDAO.queryForList("FeatureCategory.selectCategoryEpubRecomList", req, ProductBasicInfo.class);
		if (list == null) {
			list = Collections.emptyList();
		}
		return list;
	}

}