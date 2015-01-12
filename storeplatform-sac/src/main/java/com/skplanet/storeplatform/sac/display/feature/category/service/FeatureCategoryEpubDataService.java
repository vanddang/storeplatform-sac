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

import java.util.List;

import com.skplanet.storeplatform.sac.client.display.vo.feature.category.FeatureCategoryEpubSacReq;
import com.skplanet.storeplatform.sac.display.meta.vo.ProductBasicInfo;

/**
 * 쿼리 튜닝 검증 단위 테스트를 위해 별도의 클래스로 분리
 * 
 * Updated on : 2014. 11. 10. Updated by : 서대영, SK플래닛.
 */
public interface FeatureCategoryEpubDataService {

	
	List<ProductBasicInfo> selectCategoryEpubRecomList(FeatureCategoryEpubSacReq req);

}
