/*
 * Copyright (c) 2013 SK planet.
 * All right reserved.
 *
 * This software is the confidential and proprietary information of SK planet.
 * You shall not disclose such Confidential Information and
 * shall use it only in accordance with the terms of the license agreement
 * you entered into with SK planet.
 */
package com.skplanet.storeplatform.sac.client.display.vo.card;

import com.skplanet.storeplatform.framework.core.common.vo.CommonInfo;

import java.util.List;

/**
 * <p>
 * DESC
 * </p>
 * Updated on : 2014. 11. 03 Updated by : 정희원, SK 플래닛.
 */
public class PreferredCategoryReq extends CommonInfo {
    private static final long serialVersionUID = 1L;

    private String menuId;
    private String menuNm;
    private List<PreferredCategoryReq> prefer;

    public String getMenuId() {
        return menuId;
    }

    public void setMenuId(String menuId) {
        this.menuId = menuId;
    }

    public String getMenuNm() {
        return menuNm;
    }

    public void setMenuNm(String menuNm) {
        this.menuNm = menuNm;
    }

    public List<PreferredCategoryReq> getPrefer() {
        return prefer;
    }

    public void setPrefer(List<PreferredCategoryReq> prefer) {
        this.prefer = prefer;
    }
}
