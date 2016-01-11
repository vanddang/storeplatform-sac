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

import com.skplanet.storeplatform.sac.member.domain.UserMember;

/**
 * <p>
 * UserMemberTestRepository
 * </p>
 * Updated on : 2016. 01. 11 Updated by : 정희원, SK 플래닛.
 */
public interface UserMemberTestRepository {

    /**
     * 아무 데이터를 조회한다. 테스트용 메소드
     * @return
     */
    UserMember findAny();
}
