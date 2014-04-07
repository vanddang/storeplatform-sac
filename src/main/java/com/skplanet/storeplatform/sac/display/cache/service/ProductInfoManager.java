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

import com.skplanet.storeplatform.sac.display.cache.vo.*;

import java.util.List;

/**
 * <p>
 * 캐쉬용 메타 데이터 조회 서비스
 * </p>
 * Updated on : 2014. 03. 03 Updated by : 정희원, SK 플래닛.
 */
public interface ProductInfoManager {

    /**
     * 앱 메타정보 조회
     *
     * @param param
     * @return
     */
    AppMeta getAppMeta(AppMetaParam param);

    List<AppMeta> getAppMetaList(String langCd, String tenantId, List<String> prodIdList, String deviceModelCd);

    /**
     * 캐싱된 메타정보를 Evict한다.
     * @param param
     */
    void evictAppMeta(AppMetaParam param);

    /**
     * 앱 메타 캐쉬 정보를 모두 Evict한다.
     */
    void evictAllAppMeta();

    /**
     * 멀티미디어 메타정보 조회
     * @param param
     * @return
     */
    MultimediaMeta getMultimediaMeta(MultimediaMetaParam param);

    /**
     * 서브컨텐트 정보를 조회한다. APP상품에서 이용한다.
     * @param prodId
     * @param deviceModelCd
     * @return
     */
    SubContent getSubContent(String prodId, String deviceModelCd);

    /**
     * 메뉴 정보를 조회한다.
     *
     * @param langCd
     * @param menuId
     * @param prodId
     * @return
     */
    MenuInfo getMenuInfo(String langCd, String menuId, String prodId);
}
