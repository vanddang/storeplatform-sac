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
 * DESC
 * </p>
 * Updated on : 2014. 04. 01 Updated by : 정희원, SK 플래닛.
 */
public class MultimediaMetaParam extends CommonInfo {
    private static final long serialVersionUID = 1L;

    private String langCd;
    private String channelId;
    private String tenantId;


    public String getCacheKey() {
        return channelId + "_" + tenantId + "_" + langCd;
    }
}
