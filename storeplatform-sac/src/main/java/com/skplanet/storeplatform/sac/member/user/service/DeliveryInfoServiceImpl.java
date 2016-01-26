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
import com.skplanet.storeplatform.framework.core.exception.StorePlatformException;
import com.skplanet.storeplatform.member.client.common.constant.Constant;
import com.skplanet.storeplatform.sac.member.domain.mbr.UserDelivery;
import com.skplanet.storeplatform.sac.member.domain.shared.UserMember;
import com.skplanet.storeplatform.sac.member.repository.UserDeliveryRepository;
import com.skplanet.storeplatform.sac.member.repository.UserMemberTransformRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

/**
 * <p>
 * 배송 정보 서비스
 * </p>
 * Updated on : 2016. 01. 22 Updated by : 정희원, SK 플래닛.
 */
@Service
@Transactional(value = "transactionManagerForScMember", readOnly = true)
public class DeliveryInfoServiceImpl implements DeliveryInfoService {

    public static final int MAX_DELV_INFO_CNT = 2;

    @Autowired
    private UserMemberTransformRepository memberTransformRepository;

    @Autowired
    private UserDeliveryRepository deliveryRepository;

    @Autowired
    private UserMemberService memberService;


    @Override
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
    @Transactional
    public void merge(final String userKey, final UserDelivery delivery) {

        UserMember member = memberService.findByUserKeyAndActive(userKey);
        boolean isNew = delivery.getSeq() == null;

        if(!isNew) {
            UserDelivery found = deliveryRepository.findOne(delivery.getSeq());
            if(found == null)
                throw new StorePlatformException("SAC_MEM_0002", "UserDelivery#" + delivery.getSeq());

            delivery.setUseDt(found.getUseDt());
            deliveryRepository.save(delivery);
            return;
        }

        delivery.setMember(member);

        if(delivery.getDeliveryTypeCd().equals(Constant.DELIVERY_BASE_CD)) {
            // 기본 배송지 등록 케이스
            List<UserDelivery> list = deliveryRepository.findByUserKeyAndType(userKey, Constant.DELIVERY_BASE_CD);
            if(list.size() > 0)
                throw new StorePlatformException("SAC_MEM_1416", MAX_DELV_INFO_CNT);
        }
        else {
            // 최근 배송지 등록 케이스
            List<UserDelivery> list = deliveryRepository.findByUserKeyAndType(userKey, Constant.DELIVERY_RECENTLY_CD);

            // 최근 배송지는 2개까지만 저장 가능
            if(list.size() >= MAX_DELV_INFO_CNT) {
                for (UserDelivery ud : list.subList(1, list.size())) {
                    deliveryRepository.delete(ud);
                }
            }

            delivery.setUseDt(new Date());
        }

        deliveryRepository.save(delivery);
    }

    @Override
    @Transactional
    public void delete(String userKey, long delvSeq) {

        memberService.findByUserKeyAndActive(userKey);

        UserDelivery delv = deliveryRepository.findOne(delvSeq);

        if(!delv.getMember().getInsdUsermbrNo().equals(userKey))
            throw new StorePlatformException("SAC_MEM_0008");

        deliveryRepository.delete(delv);
    }
}
