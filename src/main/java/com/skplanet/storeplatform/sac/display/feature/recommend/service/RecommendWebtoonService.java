package com.skplanet.storeplatform.sac.display.feature.recommend.service;

import java.io.IOException;

import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.map.JsonMappingException;

import com.skplanet.storeplatform.sac.client.display.vo.feature.recommend.RecommendWebtoonReq;
import com.skplanet.storeplatform.sac.client.display.vo.feature.recommend.RecommendWebtoonRes;

public interface RecommendWebtoonService {

	/**
	 * <pre>
	 * 운영자 추천 웹툰 리스트 조회.
	 * </pre>
	 * 
	 * @param RecommendWebtoonReq
	 * @return RecommendWebtoonRes 리스트
	 */
	public RecommendWebtoonRes getAdminWebtoonList(RecommendWebtoonReq req) throws JsonGenerationException,
			JsonMappingException, IOException, Exception;

}
