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
import com.skplanet.storeplatform.sac.member.common.MemberRepositoryContext;
import com.skplanet.storeplatform.sac.member.domain.shared.QUserManagementItem;
import com.skplanet.storeplatform.sac.member.domain.shared.UserManagementItem;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 * <p>
 * UserManagementItemRepositoryImpl
 * </p>
 * Updated on : 2016. 01. 11 Updated by : 정희원, SK 플래닛.
 */
@Repository
public class UserManagementItemRepositoryImpl implements UserManagementItemRepository {

    public static final QUserManagementItem $ = QUserManagementItem.userManagementItem;

    @PersistenceContext(unitName = "puMbr")
    private EntityManager emMbr;

    @PersistenceContext(unitName = "puIdleMbr")
    private EntityManager emIdleMbr;

    private EntityManager getCurrentEntityManager() {
        return MemberRepositoryContext.isNormal() ? emMbr : emIdleMbr;
    }

    @Override
    public UserManagementItem findOne(UserManagementItem.PK id) {
        return getCurrentEntityManager().find(UserManagementItem.class, id);
    }

    @Override
    public UserManagementItem findByUserKeyAndItemCd(String userKey, String itemCd) {
        return new JPAQuery(getCurrentEntityManager()).from($)
                .where($.member.insdUsermbrNo.eq(userKey).and($.mangItemCd.eq(itemCd)))
                .uniqueResult($);
    }

    @Override
    public void save(UserManagementItem item) {
        emMbr.persist(item);
    }
}
