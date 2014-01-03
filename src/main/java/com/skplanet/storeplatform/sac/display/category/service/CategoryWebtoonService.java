package com.skplanet.storeplatform.sac.display.category.service;

import java.io.IOException;

import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.map.JsonMappingException;

import com.skplanet.storeplatform.sac.client.display.vo.category.CategoryWebtoonReq;
import com.skplanet.storeplatform.sac.client.display.vo.category.CategoryWebtoonRes;

public interface CategoryWebtoonService {

	/**
	 * <pre>
	 * 일반 카테고리 웹툰 리스트 조회.
	 * </pre>
	 * 
	 * @param RecommendWebtoonReq
	 * @return CategoryWebtoonRes 리스트
	 */
	public CategoryWebtoonRes getWebtoonList(CategoryWebtoonReq req) throws JsonGenerationException,
			JsonMappingException, IOException, Exception;

}
