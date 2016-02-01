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
import com.skplanet.storeplatform.framework.core.exception.StorePlatformException;
import com.skplanet.storeplatform.sac.member.common.constant.MemberConstants;
import com.skplanet.storeplatform.sac.member.domain.shared.QUserDevice;
import com.skplanet.storeplatform.sac.member.domain.shared.QUserMember;
import com.skplanet.storeplatform.sac.member.domain.shared.UserDevice;
import com.skplanet.storeplatform.sac.member.domain.shared.UserMember;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 * <p>
 * UserDeviceRepositoryImpl
 * </p>
 * Updated on : 2016. 01. 26 Updated by : 정희원, SK 플래닛.
 */
@Repository
public class UserDeviceRepositoryImpl implements UserDeviceRepository {

    public static final QUserDevice $ = QUserDevice.userDevice;

    @PersistenceContext(unitName = "puMbr")
    private EntityManager emMbr;

    @Override
    public UserDevice findByUserKeyAndDeviceKey(String userKey, String deviceKey) {

        BooleanBuilder cond = new BooleanBuilder()
                            .and($.id.member.insdUsermbrNo.eq(userKey))
                            .and($.id.member.mbrStatusMainCd.ne(MemberConstants.MAIN_STATUS_SECEDE))
                            .and($.authYn.eq("Y"))
                            .and($.id.insdDeviceId.eq(deviceKey));

        UserDevice device = new JPAQuery(emMbr)
                            .from($)
                            .innerJoin($.id.member)
                            .leftJoin($.setting).fetch()
                            .where(cond).uniqueResult($);

        if(device == null)
            throw new StorePlatformException("SAC_MEM_1516");

        return device;
    }

}
