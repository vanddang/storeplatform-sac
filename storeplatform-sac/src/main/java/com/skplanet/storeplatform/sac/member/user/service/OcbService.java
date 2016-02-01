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

import com.skplanet.storeplatform.sac.member.domain.shared.UserOcb;
import com.skplanet.storeplatform.sac.member.repository.UserOcbRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * <p>
 * OcbService
 * - OCB서비스 JPA버전
 * </p>
 * Updated on : 2016. 02. 01 Updated by : 정희원, SK 플래닛.
 */
@Service
@Transactional(value = "transactionManagerForMember", readOnly = true)
public class OcbService {

    @Autowired
    private UserMemberService memberService;

    @Autowired
    private UserOcbRepository ocbRepository;

    /**
     * 유저의 OCB정보를 조회한다.
     * @param userKey 유저 식별자
     * @return 응답
     */
    public List<UserOcb> find(String userKey) {
        memberService.findByUserKeyAndTransitRepo(userKey);
        return ocbRepository.findByUserKey(userKey);
    }

    @Transactional("transactionManagerForScMember")
    public void delete() {

    }

    @Transactional("transactionManagerForScMember")
    public void merge() {

    }

}
