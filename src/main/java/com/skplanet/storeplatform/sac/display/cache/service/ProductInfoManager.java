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

import com.skplanet.storeplatform.sac.display.cache.vo.AppMetaInfoParam;
import com.skplanet.storeplatform.sac.display.cache.vo.AppMetaInfo;
import com.skplanet.storeplatform.sac.display.cache.vo.SubContent;
import com.skplanet.storeplatform.sac.display.cache.vo.MenuInfo;

/**
 * <p>
 * DESC
 * </p>
 * Updated on : 2014. 03. 03 Updated by : 정희원, SK 플래닛.
 */
public interface ProductInfoManager {

    /**
     *
     * @param param
     * @return
     */
    AppMetaInfo getAppMetaInfo(AppMetaInfoParam param);

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
