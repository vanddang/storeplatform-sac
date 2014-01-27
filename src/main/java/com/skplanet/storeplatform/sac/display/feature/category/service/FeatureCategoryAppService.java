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

import com.skplanet.storeplatform.sac.client.display.vo.feature.category.FeatureCategoryAppReq;
import com.skplanet.storeplatform.sac.client.display.vo.feature.category.FeatureCategoryAppRes;
import com.skplanet.storeplatform.sac.common.header.vo.SacRequestHeader;

/**
 * 
 * 
 * Updated on : 2013. 12. 19. Updated by : 서영배, GTSOFT.
 */
public interface FeatureCategoryAppService {
	/**
	 * <pre>
	 * 운영자 추천 전체 카테고리 상품 리스트 조회.
	 * </pre>
	 * 
	 * @param requestVO
	 *            UserDefine 파라미터
	 * 
	 * @return 운영자 추천 전체 카테고리 상품 리스트
	 */
	FeatureCategoryAppRes searchAppList(FeatureCategoryAppReq requestVO);

	/**
	 * <pre>
	 * Feature 탑카테고리 App 상품 리스트 조 회 - 최신.
	 * </pre>
	 * 
	 * @param requestVO
	 *            UserDefine 파라미터
	 * @param header
	 *            공통헤더
	 * 
	 * @return 운영자 추천 전체 카테고리 상품 리스트
	 */
	FeatureCategoryAppRes searchMenuAppList(FeatureCategoryAppReq requestVO, SacRequestHeader header);

}
