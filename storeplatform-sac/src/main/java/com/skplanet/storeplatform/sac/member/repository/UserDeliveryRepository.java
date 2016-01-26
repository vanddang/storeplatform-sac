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

import com.skplanet.storeplatform.sac.member.domain.mbr.UserDelivery;

import java.util.List;

/**
 * <p>
 * UserDeliveryRepository
 * </p>
 * Updated on : 2016. 01. 22 Updated by : 정희원, SK 플래닛.
 */
public interface UserDeliveryRepository {

    UserDelivery findOne(Long id);

    List<UserDelivery> findByUserKeyAndType(String userKey, String type);

    void save(UserDelivery delv);

    void delete(UserDelivery delv);
}
