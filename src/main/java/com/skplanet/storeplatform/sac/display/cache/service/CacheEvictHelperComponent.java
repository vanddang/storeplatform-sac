/*
 * Copyright (c) 2013 SK planet.
 * All right reserved.
 *
 * This software is the confidential and proprietary information of SK planet.
 * You shall not disclose such Confidential Information and
 * shall use it only in accordance with the terms of the license agreement
 * you entered into with SK planet.
 */
package com.skplanet.storeplatform.sac.display.cache.service;

import com.skplanet.storeplatform.sac.display.common.ProductType;

import java.util.List;

/**
 * <p>
 * CacheEvictHelperComponent
 * </p>
 * Updated on : 2014. 04. 28 Updated by : 정희원, SK 플래닛.
 */
public interface CacheEvictHelperComponent {
    /**
     * 상품 메타 Evict
     * @param prodType
     * @param prodIdList
     */
    void evictProductMeta(ProductType prodType, List<String> prodIdList);

    /**
     * 상품 메타 Evict
     * @param prodType
     * @param prodId
     */
    void evictProductMeta(ProductType prodType, String prodId);

    /**
     * 특정 상품의 모든 메타 Evict
     * @param prodType
     */
    void evictProductMetaAll(ProductType prodType);

    /**
     * 브랜드에 해당하는 쇼핑 상품 메타데이터 Evict
     * @param brandId
     */
    void evictProductMetaByBrand(String brandId);
}
