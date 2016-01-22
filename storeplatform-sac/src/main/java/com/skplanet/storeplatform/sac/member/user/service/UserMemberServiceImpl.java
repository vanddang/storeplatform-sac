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

import com.google.common.base.Strings;
import com.skplanet.storeplatform.framework.core.exception.StorePlatformException;
import com.skplanet.storeplatform.sac.member.common.MemberRepositoryContext;
import com.skplanet.storeplatform.sac.member.domain.shared.UserMember;
import com.skplanet.storeplatform.sac.member.repository.UserMemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * <p>
 * UserMemberServiceImpl
 * </p>
 * Updated on : 2016. 01. 18 Updated by : 정희원, SK 플래닛.
 */
@Service
public class UserMemberServiceImpl implements UserMemberService {

    @Autowired
    private UserMemberRepository memberRepository;

    @Override
    public UserMember findByUserKeyAndTransitRepo(String userKey) {

        if(Strings.isNullOrEmpty(userKey))
            throw new StorePlatformException("SAC_MEM_0001", "userKey");

        UserMember member;

        MemberRepositoryContext.setTarget(MemberRepositoryContext.RepositoryTarget.Normal);
        member = memberRepository.findByUserKeyAndActive(userKey); // from Normal DB

        if(member == null) {
            MemberRepositoryContext.setTarget(MemberRepositoryContext.RepositoryTarget.Idle);
            member = memberRepository.findByUserKeyAndActive(userKey); // from Idle DB
        }

        if(member == null) {
            throw new StorePlatformException("SAC_MEM_0003", "userKey", userKey);
        }

        member.setFromNormal(MemberRepositoryContext.isNormal());

        return member;
    }
}
