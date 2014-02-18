/*
 * Copyright (c) 2013 SK planet.
 * All right reserved.
 *
 * This software is the confidential and proprietary information of SK planet.
 * You shall not disclose such Confidential Information and
 * shall use it only in accordance with the terms of the license agreement
 * you entered into with SK planet.
 */
package com.skplanet.storeplatform.sac.runtime.acl.service.authorization;

import com.skplanet.storeplatform.sac.runtime.acl.service.authorizaiton.RequestAuthorizeService;
import com.skplanet.storeplatform.sac.runtime.acl.service.common.AclDataAccessService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

/**
 *
 * 인가 서비스 테스트
 *
 * Updated on : 2014. 2. 14.
 * Updated by : 정희원, SK 플래닛
 */
@ActiveProfiles(value = "local")
@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration({ "classpath*:/spring-test/context-test.xml" })
public class RequestAuthorizationServiceImplTest {

    @Autowired
    private RequestAuthorizeService authorizationService;

    @Autowired
    private AclDataAccessService dbAccessService;


    @Test
    public void authServiceTest() {
        String res = dbAccessService.selectUsableInterface("25f9aabf90acf38aa2e6d0da49e9eee75", "I03000033");
        assert res != null;
    }

    @Test
    public void authServiceTest2() {
        String res = dbAccessService.selectUsableInterface("25f9aabf90acf38aa2e6d0da49e9eee75", "");
        assert res == null;
    }
}
