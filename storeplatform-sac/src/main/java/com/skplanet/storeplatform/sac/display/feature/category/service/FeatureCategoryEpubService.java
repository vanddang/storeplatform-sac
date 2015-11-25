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

import com.skplanet.storeplatform.sac.client.display.vo.feature.category.FeatureCategoryEpubSacReq;
import com.skplanet.storeplatform.sac.client.display.vo.feature.category.FeatureCategoryEpubSacRes;
import com.skplanet.storeplatform.sac.common.header.vo.SacRequestHeader;

/**
 * 
 * 
 * Updated on : 2013. 12. 19. Updated by : 서영배, GTSOFT.
 */
public interface FeatureCategoryEpubService {

	/**
	 * <pre>
	 * 운영자 추천 전체 카테고리 상품 리스트 조회.
	 * </pre>
	 * 
	 * @param requestVO
	 *            UserDefine 파라미터
	 * @param header
	 *            공통헤더
	 * 
	 * @return 운영자 추천 전체 카테고리 상품 리스트
	 */
	FeatureCategoryEpubSacRes searchEpubList(FeatureCategoryEpubSacReq requestVO, SacRequestHeader header);
}
