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
import com.skplanet.storeplatform.sac.member.domain.shared.QUserMemberTransform;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 * <p>
 * UserMemberTransformRepositoryImpl
 * </p>
 * Updated on : 2016. 01. 22 Updated by : 정희원, SK 플래닛.
 */
@Repository
public class UserMemberTransformRepositoryImpl implements UserMemberTransformRepository {

    public static final QUserMemberTransform $ = QUserMemberTransform.userMemberTransform;
    @PersistenceContext(unitName = "puMbr")
    private EntityManager emMbr;

    @Override
    public String findAfterUserKeyByBeforeUserKey(String beforeUserKey) {
        return new JPAQuery(emMbr).from($)
                .where($.beforeUserKey.eq(beforeUserKey))
                .uniqueResult($.afterUserKey);
    }
}
