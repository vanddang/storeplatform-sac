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
import com.mysema.query.jpa.impl.JPAUpdateClause;
import com.mysema.query.support.Expressions;
import com.mysema.query.types.Expression;
import com.mysema.query.types.Ops;
import com.mysema.query.types.Projections;
import com.mysema.query.types.expr.DateExpression;
import com.mysema.query.types.expr.DateOperation;
import com.mysema.query.types.template.StringTemplate;
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
                .and(now().between($.useStartDt, $.useEndDt));

        return new JPAQuery(em).from($)
                            .where(cond)
                            .list(Projections.fields(UserOcb.class,
                                    $.member,
                                    $.useYn,
                                    StringTemplate.create("function('decrypt', {0})", $.ocbNo).as("ocbNo"),
                                    $.useStartDt,
                                    $.ocbAuthMtdCd,
                                    $.useEndDt,
                                    $.regId,
                                    $.regDt));
    }

    @Override
    public UserOcb findByUserKeyAndOcbNo(String userKey, String ocbNo) {
        BooleanBuilder cond = new BooleanBuilder();
        cond.and($.member.insdUsermbrNo.eq(userKey))
                .and($.useYn.eq("Y"))
                .and(now().between($.useStartDt, $.useEndDt))
                .and($.ocbNo.eq(encrypt(ocbNo)));

        return new JPAQuery(emMbr).from($)
                            .where(cond)
                            .uniqueResult($);
    }

    @Override
    public void updateDisableAll(String userKey) {
        new JPAUpdateClause(emMbr, $)
                .where($.member.insdUsermbrNo.eq(userKey).and($.useYn.eq("Y")))
                .set($.useYn, "N")
                .set($.useEndDt, now())
                .execute();
    }

    @Override
    public void updateDisable(String userKey, String ocbNo) {
        BooleanBuilder cond = new BooleanBuilder();
        cond.and($.member.insdUsermbrNo.eq(userKey))
                .and($.useYn.eq("Y"))
                .and($.ocbNo.eq(encrypt(ocbNo)));

        new JPAUpdateClause(emMbr, $)
                .where(cond)
                .set($.useYn, "N")
                .set($.useEndDt, now())
                .execute();
    }

    @Override
    public void save(UserOcb userOcb) {
        // ocbNo의 암호화 처리 때문에 쿼리 이용
        emMbr.createNamedQuery("UserOcb.insert")
                .setParameter("userKey", userOcb.getMember().getInsdUsermbrNo())
                .setParameter("ocbNo", userOcb.getOcbNo())
                .setParameter("startDt", userOcb.getUseStartDt())
                .setParameter("endDt", userOcb.getUseEndDt())
                .setParameter("useYn", userOcb.getUseYn())
                .setParameter("authCd", userOcb.getOcbAuthMtdCd())
                .setParameter("regId", userOcb.getRegId())
                .executeUpdate();
    }

    private static Expression<String> encrypt(String v) {
        return Expressions.stringTemplate("function('encrypt', {0})", v);
    }

    private static DateExpression<Date> now() {
        return DateOperation.create(Date.class, Ops.DateTimeOps.CURRENT_DATE);  // Same as JPA's CURRENT_DATE
    }

}
