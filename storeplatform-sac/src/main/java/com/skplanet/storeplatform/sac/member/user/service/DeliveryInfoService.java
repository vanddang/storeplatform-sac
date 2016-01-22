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

import com.skplanet.storeplatform.sac.member.domain.mbr.UserDelivery;

import javax.annotation.Nullable;
import java.util.List;

/**
 * <p>
 * 배송지 정보 관리 서비스
 * </p>
 * Updated on : 2016. 01. 21 Updated by : 정희원, SK 플래닛.
 */
public interface DeliveryInfoService {

    /**
     * 배송 정보를 조회합니다.
     * @param userKey 사용자 식별자
     * @param type 배송 유형
     * @return 조회 결과값. 맞는 대상이 없는 경우 비어있는 목록이 응답됨
     */
    List<UserDelivery> find(String userKey, @Nullable String type);

    void merge(String userKey, UserDelivery delivery);

    void delete(String userKey, long delvSeq);
}
