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

import com.mysema.query.BooleanBuilder;
import com.mysema.query.jpa.impl.JPAQuery;
import com.mysema.query.types.Ops;
import com.mysema.query.types.expr.DateOperation;
import com.skplanet.storeplatform.sac.member.common.MemberRepositoryContext;
import com.skplanet.storeplatform.sac.member.domain.shared.QUserOcb;
import com.skplanet.storeplatform.sac.member.domain.shared.UserOcb;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.Date;
import java.util.List;

/**
 * <p>
 * UserOcbRepositoryImpl
 * </p>
 * Updated on : 2016. 02. 01 Updated by : 정희원, SK 플래닛.
 */
@Repository
public class UserOcbRepositoryImpl implements UserOcbRepository {

    public static final QUserOcb $ = QUserOcb.userOcb;
    @PersistenceContext(unitName = "puMbr")
    private EntityManager emMbr;

    @PersistenceContext(unitName = "puIdleMbr")
    private EntityManager emIdleMbr;

    private EntityManager getCurrentEntityManager() {
        return MemberRepositoryContext.isNormal() ? emMbr : emIdleMbr;
    }

    @Override
    public List<UserOcb> findByUserKey(String userKey) {
        EntityManager em = getCurrentEntityManager();
        BooleanBuilder cond = new BooleanBuilder();
        cond.and($.member.insdUsermbrNo.eq(userKey))
                .and($.useYn.eq("Y"))
                .and(DateOperation.create(Date.class, Ops.DateTimeOps.CURRENT_DATE).between($.useStartDt, $.useEndDt)); // Same as JPA's CURRENT_DATE

        return new JPAQuery(em).from($)
                .where(cond)
                .list($);
    }
}
