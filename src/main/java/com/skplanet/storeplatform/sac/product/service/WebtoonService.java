package com.skplanet.storeplatform.sac.product.service;

import java.io.IOException;

import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.map.JsonMappingException;

import com.skplanet.storeplatform.sac.client.product.vo.category.WebtoonRequest;
import com.skplanet.storeplatform.sac.client.product.vo.category.WebtoonResponse;

/**
 * 웹툰 Service 인터페이스(CoreStoreBusiness)
 * 
 * Updated on : 2013. 12. 23. Updated by : 김형식, SK 플래닛.
 */
public interface WebtoonService {

	/**
	 * <pre>
	 * 일반 카테고리 웹툰 리스트 조회.
	 * </pre>
	 * 
	 * @param WebtoonRequest
	 * @return WebtoonResponse 리스트
	 */
	public WebtoonResponse getWebtoonList(WebtoonRequest req) throws JsonGenerationException, JsonMappingException,
			IOException, Exception;

	/**
	 * <pre>
	 * 운영자 추천 웹툰 리스트 조회.
	 * </pre>
	 * 
	 * @param WebtoonRequest
	 * @return WebtoonResponse 리스트
	 */
	public WebtoonResponse getAdminWebtoonList(WebtoonRequest req) throws JsonGenerationException,
			JsonMappingException, IOException, Exception;

}
