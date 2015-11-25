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
 * FeatureKey
 * 스위치값 bit 위치 정의
 * </p>
 * Updated on : 2015. 08. 12 Updated by : 정희원, SK 플래닛.
 */
public class FeatureKey {

    /**
     * FeatureSwitch의 debug로그를 서버에 남길지 여부
     */
    public static final int FEATURE_SWITCH_DEBUG = 0;

    public static final int PROMO_EVENT_FORCE_DB = 8;
    public static final int PROMO_EVENT_USE_SYSTIME = 9;
    public static final int PROMO_EVENT_SKIP_TRANSITION_THRESHOLD = 10;

    //public static final int[] INTEGER_SW = new int[]{}; // numeric value가 필요한 경우 offset, length 지정하여 값을 추출하도록 처리

}
