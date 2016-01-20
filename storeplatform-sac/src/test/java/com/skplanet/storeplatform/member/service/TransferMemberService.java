/*
 * Copyright (c) 2013 SK planet.
 * All right reserved.
 *
 * This software is the confidential and proprietary information of SK planet.
 * You shall not disclose such Confidential Information and
 * shall use it only in accordance with the terms of the license agreement
 * you entered into with SK planet.
 */
package com.skplanet.storeplatform.member.service;

/**
 * <p>
 * DESC
 * </p>
 * Updated on : 2016. 01. 18 Updated by : 정희원, SK 플래닛.
 */
public interface TransferMemberService {

    /**
     * 일반 -> 휴면
     * @param userKey
     */
    void executeNormalToIdle(String userKey);

    /**
     * 휴면 -> 일반
     * @param userKey
     */
    void executeIdleToNormal(String userKey);
}
