/*
 * Copyright (c) 2013 SK planet.
 * All right reserved.
 *
 * This software is the confidential and proprietary information of SK planet.
 * You shall not disclose such Confidential Information and
 * shall use it only in accordance with the terms of the license agreement
 * you entered into with SK planet.
 */
package com.skplanet.storeplatform.sac.display.cache.vo;

import com.skplanet.storeplatform.framework.core.common.vo.CommonInfo;

/**
 * <p>
 * MenuInfoParam
 * </p>
 * Updated on : 2014. 04. 25 Updated by : 정희원, SK 플래닛.
 */
public class MenuInfoParam extends CommonInfo {
    private static final long serialVersionUID = 1L;

    private String channelId;
    private String langCd;
    private String menuId;

    public MenuInfoParam() {}

    public MenuInfoParam(String channelId, String menuId, String langCd) {
        this.channelId = channelId;
        this.langCd = langCd;
        this.menuId = menuId;
    }

    public String getChannelId() {
        return channelId;
    }

    public void setChannelId(String channelId) {
        this.channelId = channelId;
    }

    public String getLangCd() {
        return langCd;
    }

    public void setLangCd(String langCd) {
        this.langCd = langCd;
    }

    public String getMenuId() {
        return menuId;
    }

    public void setMenuId(String menuId) {
        this.menuId = menuId;
    }

    public String getCacheKey() {
        return channelId + "_" + menuId + "_" + langCd;
    }
}
