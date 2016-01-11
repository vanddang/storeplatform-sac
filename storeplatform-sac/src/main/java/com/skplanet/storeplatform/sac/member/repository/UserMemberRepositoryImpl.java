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

import com.google.common.base.Strings;
import com.mysema.query.jpa.impl.JPAQuery;
import com.skplanet.storeplatform.sac.member.domain.QUserMember;
import com.skplanet.storeplatform.sac.member.domain.UserMember;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 * <p>
 * UserMemberRepositoryImpl
 * </p>
 * Updated on : 2016. 01. 04 Updated by : 정희원, SK 플래닛.
 */
@Repository
public class UserMemberRepositoryImpl implements UserMemberRepository {

    public static final QUserMember $ = QUserMember.userMember;

    @PersistenceContext(unitName = "puMbr")
    private EntityManager em;

    @Override
    public UserMember findOne(String userKey) {
        return new JPAQuery(em).from($)
                .where($.insdUsermbrNo.eq(userKey))
                .uniqueResult($);
    }

    @Override
    public UserMember findAny() {
        return new JPAQuery(em).from($)
                .innerJoin($.devices)
                .singleResult($);
    }

    @Override
    public UserMember findByEmail(String email) {
        return new JPAQuery(em).from($)
                .where($.emailAddr.eq(email))
                .uniqueResult($);
    }

    @Override
    public boolean isExist(String userKey) {
        String r = new JPAQuery(em).from($)
                .where($.insdUsermbrNo.eq(userKey))
                .uniqueResult($.insdUsermbrNo);

        return !Strings.isNullOrEmpty(r);
    }
}
