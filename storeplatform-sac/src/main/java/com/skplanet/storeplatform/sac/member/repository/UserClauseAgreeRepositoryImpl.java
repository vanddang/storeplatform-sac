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

import com.mysema.query.jpa.impl.JPAQuery;
import com.skplanet.storeplatform.sac.member.domain.shared.QUserClauseAgree;
import com.skplanet.storeplatform.sac.member.domain.shared.UserClauseAgree;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

/**
 * <p>
 * UserClauseAgreeRepositoryImpl
 * </p>
 * Updated on : 2016. 01. 05 Updated by : 정희원, SK 플래닛.
 */
@Repository
public class UserClauseAgreeRepositoryImpl implements UserClauseAgreeRepository {

    public static final QUserClauseAgree $ = QUserClauseAgree.userClauseAgree;

    @PersistenceContext(unitName = "puMbr")
    private EntityManager em;

    @Override
    public void save(UserClauseAgree v) {
        em.persist(v);
    }

    @Override
    public List<UserClauseAgree> findByInsdUsermbrNo(String userKey) {
        JPAQuery q = new JPAQuery(em)
                .from($)
                .where($.member.insdUsermbrNo.eq(userKey));

        return q.list($);
    }
}
