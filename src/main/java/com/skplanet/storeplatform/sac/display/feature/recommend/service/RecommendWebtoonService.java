package com.skplanet.storeplatform.sac.display.feature.recommend.service;

import com.skplanet.storeplatform.sac.client.display.vo.feature.recommend.RecommendWebtoonReq;
import com.skplanet.storeplatform.sac.client.display.vo.feature.recommend.RecommendWebtoonRes;
import com.skplanet.storeplatform.sac.common.header.vo.SacRequestHeader;

public interface RecommendWebtoonService {

	/**
	 * <pre>
	 * 운영자 추천 웹툰 리스트 조회.
	 * </pre>
	 * 
	 * @param RecommendWebtoonReq
	 * @return RecommendWebtoonRes 리스트
	 */
	public RecommendWebtoonRes searchWebtoonList(SacRequestHeader header, RecommendWebtoonReq req);

}
