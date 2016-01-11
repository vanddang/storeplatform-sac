/*
 * Copyright (c) 2013 SK planet.
 * All right reserved.
 *
 * This software is the confidential and proprietary information of SK planet.
 * You shall not disclose such Confidential Information and
 * shall use it only in accordance with the terms of the license agreement
 * you entered into with SK planet.
 */
package com.skplanet.storeplatform.sac.member.user.service;

import com.skplanet.storeplatform.framework.core.exception.StorePlatformException;
import com.skplanet.storeplatform.sac.member.domain.UserMember;
import com.skplanet.storeplatform.sac.member.repository.UserMemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * <p>
 * DESC
 * </p>
 * Updated on : 2016. 01. 08 Updated by : 정희원, SK 플래닛.
 */
@Service
public class UserMemberServiceImpl implements UserMemberService {

    @Autowired
    private UserMemberRepository memberRepository;

    @Transactional("transactionManagerForScMember")
    @Override
    public UserMember _findOne(String userKey) {
        UserMember user = memberRepository.findOne(userKey);
        if(!user.isAvailable())
            throw new StorePlatformException("SAC_MEM_0003", "userKey", userKey);
        user.setMbrNm(user.getMbrNm() + "_");

        return user;
    }
}
