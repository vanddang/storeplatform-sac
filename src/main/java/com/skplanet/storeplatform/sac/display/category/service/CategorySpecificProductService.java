/*
 * Copyright (c) 2013 SK planet.
 * All right reserved.
 *
 * This software is the confidential and proprietary information of SK planet.
 * You shall not disclose such Confidential Information and
 * shall use it only in accordance with the terms of the license agreement
 * you entered into with SK planet.
 */
package com.skplanet.storeplatform.sac.display.category.service;

import com.skplanet.storeplatform.sac.client.display.vo.category.CategorySpecificSacRes;
import com.skplanet.storeplatform.sac.display.category.vo.SearchProductListParam;

/**
 * <p>
 * CategorySpecificProductService
 * </p>
 * Updated on : 2015. 07. 01 Updated by : 정희원, SK 플래닛.
 */
public interface CategorySpecificProductService {

    /**
     * 상품 종류에 상관없이 특정 상품을 조회한다.
     * @param param
     * @return
     */
    CategorySpecificSacRes searchProductList(SearchProductListParam param);
}
