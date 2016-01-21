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
import com.skplanet.storeplatform.sac.member.common.MemberRepositoryContext;
import com.skplanet.storeplatform.sac.member.common.constant.MemberConstants;
import com.skplanet.storeplatform.sac.member.domain.shared.QUserMember;
import com.skplanet.storeplatform.sac.member.domain.shared.UserMember;
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
    private EntityManager emMbr;

    @PersistenceContext(unitName = "puIdleMbr")
    private EntityManager emIdleMbr;

    private EntityManager getCurrentEntityManager() {
        return MemberRepositoryContext.isNormal() ? emMbr : emIdleMbr;
    }

    @Override
    public UserMember findOne(String userKey) {
        return new JPAQuery(getCurrentEntityManager()).from($)
                .where($.insdUsermbrNo.eq(userKey))
                .uniqueResult($);
    }

    @Override
    public UserMember findByEmail(String email) {
        return new JPAQuery(getCurrentEntityManager()).from($)
                .where($.emailAddr.eq(email))
                .uniqueResult($);
    }

    @Override
    public UserMember findByUserKeyAndActive(String userKey) {
        EntityManager em = getCurrentEntityManager();
        return new JPAQuery(em).from($)
                .where($.insdUsermbrNo.eq(userKey).and($.mbrStatusMainCd.ne(MemberConstants.MAIN_STATUS_SECEDE)))
                .uniqueResult($);
    }

    @Override
    public boolean isExist(String userKey) {
        String r = new JPAQuery(getCurrentEntityManager()).from($)
                .where($.insdUsermbrNo.eq(userKey))
                .uniqueResult($.insdUsermbrNo);

        return !Strings.isNullOrEmpty(r);
    }
}
