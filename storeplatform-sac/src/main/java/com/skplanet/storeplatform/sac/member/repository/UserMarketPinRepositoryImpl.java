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

import com.skplanet.storeplatform.sac.member.domain.QUserMarketPin;
import com.skplanet.storeplatform.sac.member.domain.UserMarketPin;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 * 회원 Market PIN Repository
 * Updated on : 2016. 01. 07 Updated by : 임근대, SK 플래닛.
 */
@Repository
public class UserMarketPinRepositoryImpl implements UserMarketPinRepository {

    public static final QUserMarketPin $ = QUserMarketPin.userMarketPin;

    @PersistenceContext(unitName = "puMbr")
    private EntityManager em;

    public UserMarketPin findOne(String insdUsermbrNo) {
        return em.find(UserMarketPin.class, insdUsermbrNo);
    }


}
