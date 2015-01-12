package com.skplanet.storeplatform.sac.display.feature.recommend.service;

import com.skplanet.storeplatform.sac.client.display.vo.feature.recommend.RecommendTodaySacReq;
import com.skplanet.storeplatform.sac.client.display.vo.feature.recommend.RecommendTodaySacRes;
import com.skplanet.storeplatform.sac.common.header.vo.SacRequestHeader;

/**
 * 
 * 
 * Updated on : 2014. 2. 6. Updated by : 조준일, nTels.
 */
public interface RecommendTodayService {
	/**
	 * <pre>
	 * Feature VOD 카테고리 상품 조회.
	 * </pre>
	 * 
	 * @param req
	 *            UserDefine 파라미터
	 * @param header
	 *            공통헤더
	 * @return FeatureCategoryVodRes
	 */
	RecommendTodaySacRes searchTodayList(RecommendTodaySacReq req, SacRequestHeader header);
}
