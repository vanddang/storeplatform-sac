/*
 * Copyright (c) 2013 SK planet.
 * All right reserved.
 *
 * This software is the confidential and proprietary information of SK planet.
 * You shall not disclose such Confidential Information and
 * shall use it only in accordance with the terms of the license agreement
 * you entered into with SK planet.
 */
package com.skplanet.storeplatform.sac.member.domain;

import com.skplanet.storeplatform.sac.member.repository.UserMemberRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * <p>
 * DESC
 * </p>
 * Updated on : 2016. 01. 11 Updated by : 정희원, SK 플래닛.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({ "classpath*:/spring-test/context-test.xml" })
@ActiveProfiles("local")
@Transactional("transactionManagerForScMember")
public class UserMemberTest {

    @Autowired
    private UserMemberRepository memberRepository;

    @Test
    public void test01() {
        UserMember member = memberRepository.findOne("US201411191654511910008497");
        List<UserDevice> devices = member.getDevices();
        assert devices.size() > 0;
    }
}
