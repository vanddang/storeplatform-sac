/*
 * Copyright (c) 2013 SK planet.
 * All right reserved.
 *
 * This software is the confidential and proprietary information of SK planet.
 * You shall not disclose such Confidential Information and
 * shall use it only in accordance with the terms of the license agreement
 * you entered into with SK planet.
 */
package com.skplanet.storeplatform.sac.client.display.vo.menu;

import com.skplanet.storeplatform.framework.core.common.vo.CommonInfo;
import com.skplanet.storeplatform.sac.client.product.vo.MenuDetail;

import java.util.List;

/**
 * <p>
 * MenuIntegrationListRes
 * </p>
 * Updated on : 2014. 10. 20 Updated by : 정희원, SK 플래닛.
 */
public class MenuIntegrationListRes extends CommonInfo {
    private static final long serialVersionUID = 1L;

    public MenuIntegrationListRes() {}

    public MenuIntegrationListRes(List<MenuDetail> menuDetailList) {
        this.menuDetailList = menuDetailList;
    }

    private List<MenuDetail> menuDetailList;

    public List<MenuDetail> getMenuDetailList() {
        return menuDetailList;
    }

    public void setMenuDetailList(List<MenuDetail> menuDetailList) {
        this.menuDetailList = menuDetailList;
    }
}
