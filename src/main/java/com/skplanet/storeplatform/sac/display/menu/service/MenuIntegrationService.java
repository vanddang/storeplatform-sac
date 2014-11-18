/*
 * Copyright (c) 2013 SK planet.
 * All right reserved.
 *
 * This software is the confidential and proprietary information of SK planet.
 * You shall not disclose such Confidential Information and
 * shall use it only in accordance with the terms of the license agreement
 * you entered into with SK planet.
 */
package com.skplanet.storeplatform.sac.display.menu.service;

import com.skplanet.storeplatform.sac.client.product.vo.MenuDetail;

import java.util.List;

/**
 * <p>
 * MenuIntegrationService
 * </p>
 * Updated on : 2014. 10. 20 Updated by : 정희원, SK 플래닛.
 */
public interface MenuIntegrationService {

    /**
     * 주어진 조건에 대한 메뉴 목록을 조회한다.
     * @param tenantId Required
     * @param systemId Required
     * @param langCd Required
     * @param upMenuKey Optional
     * @return 메뉴 목록
     */
    List<MenuDetail> selectMenuIntegrationList(String tenantId, String systemId, String langCd, String upMenuKey);
}
