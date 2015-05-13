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

import com.skplanet.storeplatform.sac.display.cache.vo.UpdateProduct;
import com.skplanet.storeplatform.sac.display.cache.vo.UpdateProductParam;

/**
 * <p>
 * 캐시된 이런저런 정보
 * </p>
 * Updated on : 2014. 06. 12 Updated by : 정희원, SK 플래닛.
 */
public interface CachedExtraInfoManager {

    UpdateProduct getUpdateProductInfo(UpdateProductParam param);

    /**
     * 패키지명으로 상품ID 조회.
     * TODO 자동업데이트시에도 이 메소드를 이용하도록 하여야 한다.
     * @param pkgNm 패키지명
     * @return 상품ID
     */
    String getProdIdByPkgNm(String pkgNm);

    /**
     * 상품ID에 할당된 패키지명 정보를 evict합니다.
     * @param prodId 상품ID
     */
    void evictPkgsInProd(String prodId);
}
