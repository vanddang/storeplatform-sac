/*
 * Copyright (c) 2013 SK planet.
 * All right reserved.
 *
 * This software is the confidential and proprietary information of SK planet.
 * You shall not disclose such Confidential Information and
 * shall use it only in accordance with the terms of the license agreement
 * you entered into with SK planet.
 */
package com.skplanet.storeplatform.sac.display.menu.vo;

import com.skplanet.storeplatform.framework.core.common.vo.CommonInfo;
import com.skplanet.storeplatform.sac.client.product.vo.MenuDetail;

import java.util.List;

/**
 * <p>
 * MenuList
 * </p>
 * Updated on : 2014. 11. 18 Updated by : 정희원, SK 플래닛.
 */
public class IntegratedMenuList extends CommonInfo {
    private static final long serialVersionUID = 1L;

    private List<MenuDetail> featuredMenuList;

    private List<MenuDetail> categoryMenuList;

    public IntegratedMenuList(List<MenuDetail> featuredMenuList, List<MenuDetail> categoryMenuList) {
        this.featuredMenuList = featuredMenuList;
        this.categoryMenuList = categoryMenuList;
    }

    public List<MenuDetail> getFeaturedMenuList() {
        return featuredMenuList;
    }

    public void setFeaturedMenuList(List<MenuDetail> featuredMenuList) {
        this.featuredMenuList = featuredMenuList;
    }

    public List<MenuDetail> getCategoryMenuList() {
        return categoryMenuList;
    }

    public void setCategoryMenuList(List<MenuDetail> categoryMenuList) {
        this.categoryMenuList = categoryMenuList;
    }
}
