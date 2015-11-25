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
 * 캐쉬 처리에 필요한 부가적인 기능들을 모아놓은 서비스
 * </p>
 * Updated on : 2014. 04. 25 Updated by : 정희원, SK 플래닛.
 */
public interface CacheSupportService {

    /**
     * (앱 상품) 지원 단말 목록을 조회한다
     * @param prodId
     * @return
     */
    List<String> getSupportDeviceList(String prodId);

    /**
     * (앱 상품) 상품에 관련된 메뉴 목록을 조회한다.
     * @param prodId
     * @return
     */
    List<String> getMenuList(String prodId);

    /**
     * 브랜드에 해당하는 카탈로그 목록을 조회한다.
     * @param brandId
     * @return
     */
    List<String> getCatalogListByBrand(String brandId);
}
