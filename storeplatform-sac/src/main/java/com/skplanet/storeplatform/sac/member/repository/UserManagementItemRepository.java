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

import com.skplanet.storeplatform.sac.member.domain.UserManagementItem;

/**
 * <p>
 * UserManagementItemRepository
 * </p>
 * Updated on : 2016. 01. 11 Updated by : 정희원, SK 플래닛.
 */
public interface UserManagementItemRepository {

    UserManagementItem findOne(UserManagementItem.PK id);

    UserManagementItem findByUserKeyAndItemCd(String userKey, String itemCd);

    void save(UserManagementItem item);

}
