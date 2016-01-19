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

import com.skplanet.storeplatform.sac.member.domain.shared.UserMember;

/**
 * <p>
 * UserMemberRepository
 * </p>
 * Updated on : 2016. 01. 04 Updated by : 정희원, SK 플래닛.
 */
public interface UserMemberRepository {

    UserMember findOne(String userKey);

    UserMember findByEmail(String email);

    boolean isExist(String userKey);
}
