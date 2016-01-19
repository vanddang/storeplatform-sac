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

import com.skplanet.storeplatform.sac.member.domain.shared.UserMember;

/**
 * <p>
 * UserMemberService
 * </p>
 * Updated on : 2016. 01. 18 Updated by : 정희원, SK 플래닛.
 */
public interface UserMemberService {

    /**
     * 유효한 회원 정보를 조회하고 조회된 DB에 따라 저장소를 설정한다.
     * @see com.skplanet.storeplatform.sac.member.common.MemberRepositoryContext
     * @param userKey
     * @return
     */
    UserMember findByUserKeyAndTransitRepo(String userKey);
}
