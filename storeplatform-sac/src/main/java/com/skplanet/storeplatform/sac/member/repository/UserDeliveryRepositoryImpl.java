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
import com.mysema.query.BooleanBuilder;
import com.mysema.query.jpa.impl.JPAQuery;
import com.skplanet.storeplatform.sac.member.domain.mbr.QUserDelivery;
import com.skplanet.storeplatform.sac.member.domain.mbr.UserDelivery;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

/**
 * <p>
 * UserDeliveryRepositoryImpl
 * </p>
 * Updated on : 2016. 01. 22 Updated by : 정희원, SK 플래닛.
 */
@Repository
public class UserDeliveryRepositoryImpl implements UserDeliveryRepository {

    public static final QUserDelivery $ = QUserDelivery.userDelivery;

    @PersistenceContext(unitName = "puMbr")
    private EntityManager emMbr;

    @Override
    public List<UserDelivery> findByUserKeyAndType(String userKey, String type) {
        BooleanBuilder expr = new BooleanBuilder($.member.insdUsermbrNo.eq(userKey));

        if(!Strings.isNullOrEmpty(type))
            expr.and($.deliveryTypeCd.eq(type));

        return new JPAQuery(emMbr).from($)
                .where(expr)
                .list($);
    }
}
