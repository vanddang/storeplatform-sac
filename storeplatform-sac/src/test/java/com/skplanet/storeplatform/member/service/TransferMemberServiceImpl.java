/*
 * Copyright (c) 2013 SK planet.
 * All right reserved.
 *
 * This software is the confidential and proprietary information of SK planet.
 * You shall not disclose such Confidential Information and
 * shall use it only in accordance with the terms of the license agreement
 * you entered into with SK planet.
 */
package com.skplanet.storeplatform.member.service;

import com.google.common.collect.Lists;
import com.skplanet.storeplatform.sac.member.domain.shared.UserClauseAgree;
import com.skplanet.storeplatform.sac.member.domain.shared.UserDevice;
import com.skplanet.storeplatform.sac.member.domain.shared.UserManagementItem;
import com.skplanet.storeplatform.sac.member.domain.shared.UserMember;
import org.springframework.stereotype.Service;

import javax.persistence.Entity;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

/**
 * <p>
 * TransferMemberServiceImpl
 * </p>
 * Updated on : 2016. 01. 18 Updated by : 정희원, SK 플래닛.
 */
@Service
public class TransferMemberServiceImpl implements TransferMemberService {

    @PersistenceContext(unitName = "puMbr")
    private EntityManager emMbr;

    @PersistenceContext(unitName = "puIdleMbr")
    private EntityManager emIdleMbr;

    @Override
    public void executeNormalToIdle(String userKey) {
        move(userKey, emMbr, emIdleMbr);
    }

    @Override
    public void executeIdleToNormal(String userKey) {
        move(userKey, emIdleMbr, emMbr);
    }

    private void move(String userKey, EntityManager fromEm, EntityManager toEm) {
        UserMember norMem = fromEm.find(UserMember.class, userKey);

        if(norMem == null)
            throw new RuntimeException("데이터가 없습니다");

        List<UserDevice> devices = norMem.getDevices();
        List<UserClauseAgree> agrees = norMem.getClauseAgrees();
        List<UserManagementItem> managementItems = norMem.getManagementItems();

        List<Object> objects = Lists.newArrayList();
        objects.add(norMem);
        objects.addAll(devices);
        objects.addAll(agrees);
        objects.addAll(managementItems);

        norMem.setDevices(null);
        norMem.setClauseAgrees(null);
        norMem.setManagementItems(null);

        for (Object o : objects) {
            toEm.persist(o);
            fromEm.remove(o);
        }
    }
}
