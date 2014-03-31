/*
 * Copyright (c) 2013 SK planet.
 * All right reserved.
 *
 * This software is the confidential and proprietary information of SK planet.
 * You shall not disclose such Confidential Information and
 * shall use it only in accordance with the terms of the license agreement
 * you entered into with SK planet.
 */
package com.skplanet.storeplatform.sac.display.common.vo;

import com.skplanet.storeplatform.framework.core.common.vo.CommonInfo;

/**
 * <p>
 * DESC
 * </p>
 * Updated on : 2014. 03. 31 Updated by : 정희원, SK 플래닛.
 */
public class TmembershipDcInfo extends CommonInfo {

    private static final long serialVersionUID = 1L;

    private Integer normalDcRate;
    private Integer freepassDcRate;

    public Integer getNormalDcRate() {
        return normalDcRate;
    }

    public void setNormalDcRate(Integer normalDcRate) {
        this.normalDcRate = normalDcRate;
    }

    public Integer getFreepassDcRate() {
        return freepassDcRate;
    }

    public void setFreepassDcRate(Integer freepassDcRate) {
        this.freepassDcRate = freepassDcRate;
    }
}
