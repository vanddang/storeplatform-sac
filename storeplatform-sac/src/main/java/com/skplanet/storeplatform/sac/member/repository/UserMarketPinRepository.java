/*
 * Copyright (c) 2013 SK planet.
 * All right reserved.
 *
 * This software is the confidential and proprietary information of SK planet.
 * You shall not disclose such Confidential Information and
 * shall use it only in accordance with the terms of the license agreement
 * you entered into with SK planet.
 */
package com.skplanet.storeplatform.sac.member.repository;

import com.skplanet.storeplatform.sac.member.domain.mbr.UserMarketPin;

/**
 * 회원 Market PIN Repository
 * Updated on : 2016. 01. 07 Updated by : 임근대, SK 플래닛.
 */
public interface UserMarketPinRepository {

    /**
     * 회원 Market PIN 등록
     * @param userMarketPin 회원 Market PIN
     */
    void save(UserMarketPin userMarketPin);

    /**
     * 회원 Market PIN 조회
     * @param insdUsermbrNo 회원 번호
     * @return Market PIN 정보
     */
    UserMarketPin findOne(String insdUsermbrNo);
}
