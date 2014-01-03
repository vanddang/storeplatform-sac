/*
 * Copyright (c) 2013 SK planet.
 * All right reserved.
 *
 * This software is the confidential and proprietary information of SK planet.
 * You shall not disclose such Confidential Information and
 * shall use it only in accordance with the terms of the license agreement
 * you entered into with SK planet.
 */
package com.skplanet.storeplatform.sac.product.service;

import java.io.IOException;

import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.map.JsonMappingException;

import com.skplanet.storeplatform.sac.client.product.vo.category.SpecificProductRequest;
import com.skplanet.storeplatform.sac.client.product.vo.category.SpecificProductResponse;

/**
 * 특정 상품 조회 인터페이스(CoreStoreBusiness)
 * 
 * Updated on : 2013-12-18 Updated by : 오승민, Incross.
 */
public interface SpecificProductListService {
	/**
	 * <pre>
	 * 특정 상품 조회 API
	 * </pre>
	 * 
	 * @param list
	 * @param imageSizeCd
	 * @return 특정 상품 List
	 * @throws IOException
	 * @throws JsonMappingException
	 * @throws JsonGenerationException
	 */
	public SpecificProductResponse getSpecificProductList(SpecificProductRequest request);
}
