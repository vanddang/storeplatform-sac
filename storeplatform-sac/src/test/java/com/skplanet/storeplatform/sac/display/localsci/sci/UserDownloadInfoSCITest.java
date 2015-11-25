/*
 * Copyright (c) 2013 SK planet.
 * All right reserved.
 *
 * This software is the confidential and proprietary information of SK planet.
 * You shall not disclose such Confidential Information and
 * shall use it only in accordance with the terms of the license agreement
 * you entered into with SK planet.
 */
package com.skplanet.storeplatform.sac.display.localsci.sci;

import com.skplanet.storeplatform.sac.client.internal.display.localsci.sci.UserDownloadInfoSCI;
import com.skplanet.storeplatform.sac.client.internal.display.localsci.vo.UserDownloadInfoReq;
import com.skplanet.storeplatform.sac.client.internal.display.localsci.vo.UserDownloadInfoRes;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * <p>
 * UserDownloadInfoSCITest
 * </p>
 * Updated on : 2015. 04. 13 Updated by : 정희원, SK 플래닛.
 */
@ActiveProfiles(value = "local")
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({ "classpath*:/spring-test/context-test.xml" })
public class UserDownloadInfoSCITest {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private UserDownloadInfoSCI userDownloadInfoSCI;

    @Test
    public void test01() {
        UserDownloadInfoRes v = userDownloadInfoSCI.getUserDownloadInfo(new UserDownloadInfoReq("O+YPHIY4o4MeL8n9GwlWi1Dlb40=", "OA00655077", "S03"));
        logger.info("{}", v);
    }

    @Test
    public void test02() {
        UserDownloadInfoRes v = userDownloadInfoSCI.getUserDownloadInfo(new UserDownloadInfoReq("SA28hL6tgOSj3PzHsO8T2tKZnHA=", "OA00655077", "S02"));
        logger.info("{}", v);
    }
}
