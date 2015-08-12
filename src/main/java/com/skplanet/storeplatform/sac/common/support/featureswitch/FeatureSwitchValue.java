/*
 * Copyright (c) 2013 SK planet.
 * All right reserved.
 *
 * This software is the confidential and proprietary information of SK planet.
 * You shall not disclose such Confidential Information and
 * shall use it only in accordance with the terms of the license agreement
 * you entered into with SK planet.
 */
package com.skplanet.storeplatform.sac.common.support.featureswitch;

/**
 * <p>
 * FeatureSwitchValue
 * Context에 저장했는지의 여부를 관리해야 되기 때문에 만든 래퍼 클래스
 * </p>
 * Updated on : 2015. 08. 12 Updated by : 정희원, SK 플래닛.
 */
public class FeatureSwitchValue {

    public FeatureSwitchValue(byte[] value) {
        this.value = value;
    }

    private byte[] value;

    public byte[] getValue() {
        return value;
    }
}
