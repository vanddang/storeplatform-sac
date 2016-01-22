/*
 * Copyright (c) 2013 SK planet.
 * All right reserved.
 *
 * This software is the confidential and proprietary information of SK planet.
 * You shall not disclose such Confidential Information and
 * shall use it only in accordance with the terms of the license agreement
 * you entered into with SK planet.
 */
package com.skplanet.storeplatform.sac.member.user.service;

import com.google.common.base.Strings;
import com.google.common.collect.Lists;
import com.skplanet.storeplatform.sac.member.domain.mbr.UserDelivery;
import com.skplanet.storeplatform.sac.member.repository.UserDeliveryRepository;
import com.skplanet.storeplatform.sac.member.repository.UserMemberTransformRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * <p>
 * 배송 정보 서비스
 * </p>
 * Updated on : 2016. 01. 22 Updated by : 정희원, SK 플래닛.
 */
@Service
@Transactional("transactionManagerForScMember")
public class DeliveryInfoServiceImpl implements DeliveryInfoService {

    @Autowired
    private UserMemberTransformRepository memberTransformRepository;

    @Autowired
    private UserDeliveryRepository deliveryRepository;


    @Override
    @Transactional(readOnly = true)
    public List<UserDelivery> find(final String userKey, final String type) {

        List<UserDelivery> delv = deliveryRepository.findByUserKeyAndType(userKey, type);
        if(delv.size() > 0)
            return delv;

        String tranUserKey = memberTransformRepository.findAfterUserKeyByBeforeUserKey(userKey);
        if(Strings.isNullOrEmpty(tranUserKey))
            return Lists.newArrayList();

        delv = deliveryRepository.findByUserKeyAndType(tranUserKey, type);
        return delv;
    }

    @Override
    public void merge(String userKey, UserDelivery delivery) {

    }

    @Override
    public void delete(String userKey, long delvSeq) {

    }
}
