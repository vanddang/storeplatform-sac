/*
 * Copyright (c) 2014 SK planet.
 * All right reserved.
 *
 * This software is the confidential and proprietary information of SK planet.
 * You shall not disclose such Confidential Information and
 * shall use it only in accordance with the terms of the license agreement
 * you entered into with SK planet.
 */
package com.skplanet.storeplatform.sac.display.feature.theme.recommend.service;

import com.skplanet.storeplatform.sac.client.display.vo.feature.recommend.ThemeRecommendSacReq;
import com.skplanet.storeplatform.sac.client.display.vo.feature.recommend.ThemeRecommendSacRes;
import com.skplanet.storeplatform.sac.common.header.vo.SacRequestHeader;

/**
 * 테마 추천 상품 리스트 조회 API Interface
 * 
 * Updated on : 2014. 02. 18. Updated by : 윤주영, GTSOFT.
 */
public interface ThemeRecommendProductService {

	ThemeRecommendSacRes searchThemeRecommendProductList(ThemeRecommendSacReq requestVO, SacRequestHeader header);

	ThemeRecommendSacRes searchDummyThemeRecommendProductList(ThemeRecommendSacReq requestVO, SacRequestHeader header);
}
