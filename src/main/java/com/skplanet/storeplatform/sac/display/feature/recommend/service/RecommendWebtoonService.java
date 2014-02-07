package com.skplanet.storeplatform.sac.display.feature.recommend.service;

import com.skplanet.storeplatform.sac.client.display.vo.feature.recommend.RecommendWebtoonSacReq;
import com.skplanet.storeplatform.sac.client.display.vo.feature.recommend.RecommendWebtoonSacRes;
import com.skplanet.storeplatform.sac.common.header.vo.SacRequestHeader;

public interface RecommendWebtoonService {

	/**
	 * <pre>
	 * 운영자 추천 웹툰 리스트 조회.
	 * </pre>
	 * 
	 * @param RecommendWebtoonSacReq
	 * @return RecommendWebtoonRes 리스트
	 */
	public RecommendWebtoonSacRes searchWebtoonList(SacRequestHeader header, RecommendWebtoonSacReq req);

}
