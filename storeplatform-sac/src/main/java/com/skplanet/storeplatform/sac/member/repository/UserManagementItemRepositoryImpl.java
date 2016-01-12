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
import com.skplanet.storeplatform.sac.member.domain.QUserManagementItem;
import com.skplanet.storeplatform.sac.member.domain.UserManagementItem;
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
    private EntityManager em;

    @Override
    public UserManagementItem findOne(UserManagementItem.PK id) {
        return em.find(UserManagementItem.class, id);
    }

    @Override
    public UserManagementItem findByUserKeyAndItemCd(String userKey, String itemCd) {
        return new JPAQuery(em).from($)
                .where($.member.insdUsermbrNo.eq(userKey).and($.mangItemCd.eq(itemCd)))
                .uniqueResult($);
    }

    @Override
    public void save(UserManagementItem item) {
        em.persist(item);
    }
}
