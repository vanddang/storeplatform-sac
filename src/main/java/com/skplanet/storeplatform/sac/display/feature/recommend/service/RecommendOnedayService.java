package com.skplanet.storeplatform.sac.display.feature.recommend.service;

import com.skplanet.storeplatform.sac.client.display.vo.feature.recommend.RecommendOnedaySacReq;
import com.skplanet.storeplatform.sac.client.display.vo.feature.recommend.RecommendOnedaySacRes;
import com.skplanet.storeplatform.sac.common.header.vo.SacRequestHeader;

/**
 * 
 * 
 * Updated on : 2014. 2. 28. Updated by : 이승훈, nTels.
 */
public interface RecommendOnedayService {
	/**
	 * <pre>
	 * 하루에 하나 상품 조회.
	 * </pre>
	 * 
	 * @param req
	 *            UserDefine 파라미터
	 * @param header
	 *            공통헤더
	 * @return FeatureCategoryVodRes
	 */
	RecommendOnedaySacRes searchOnedayList(RecommendOnedaySacReq req, SacRequestHeader header);
}
