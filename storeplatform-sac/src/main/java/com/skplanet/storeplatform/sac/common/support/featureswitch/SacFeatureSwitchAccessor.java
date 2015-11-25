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
 * SacFeatureSwitchAccessor
 * </p>
 * Updated on : 2015. 08. 12 Updated by : 정희원, SK 플래닛.
 */
public interface SacFeatureSwitchAccessor {

    /**
     * 스위치 값을 조회한다. 조회에 문제 있으면 항상 false값을 응답한다.
     * @param featureKey FeatureKey에 정의된 상수값
     * @return 스위치 값
     */
    boolean get(int featureKey);

}
