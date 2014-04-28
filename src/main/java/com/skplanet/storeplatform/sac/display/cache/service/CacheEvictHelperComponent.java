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

import java.util.List;

/**
 * <p>
 * DESC
 * </p>
 * Updated on : 2014. 04. 28 Updated by : 정희원, SK 플래닛.
 */
public interface CacheEvictHelperComponent {
    void evictProductMeta(String prodType, List<String> prodIdList);
    void evictProductMeta(String prodType, String prodId);
    void evictProductMetaAll(String prodType);
}
