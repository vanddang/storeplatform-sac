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

import com.skplanet.storeplatform.framework.core.exception.StorePlatformException;
import com.skplanet.storeplatform.sac.client.internal.display.localsci.sci.IapProductInfoSCI;
import com.skplanet.storeplatform.sac.client.internal.display.localsci.vo.IapProductInfoReq;
import com.skplanet.storeplatform.sac.client.internal.display.localsci.vo.IapProductInfoRes;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

/**
 * <p>
 * DESC
 * </p>
 * Updated on : 2014. 04. 16 Updated by : 정희원, SK 플래닛.
 */
@ActiveProfiles(value = "local")
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({ "classpath*:/spring-test/context-test.xml" })
@Transactional
public class IapProductInfoSCITest {

    private static final Logger logger = LoggerFactory.getLogger(IapProductInfoSCITest.class);

    @Autowired
    private IapProductInfoSCI iapProductInfoSCI;

    @Test
    public void test01_정상() {
        IapProductInfoRes iapProductInfo = iapProductInfoSCI.getIapProductInfo(new IapProductInfoReq("0910008892"));
        logger.info("{}", iapProductInfo);
        assert iapProductInfo != null;
    }

    @Test(expected = StorePlatformException.class)
    public void test02_잘못된요청() {
        IapProductInfoRes iapProductInfo = iapProductInfoSCI.getIapProductInfo(null);
        assert iapProductInfo == null;
    }

    @Test
    public void test03_IAP식별자아님() {
        IapProductInfoRes iapProductInfo = iapProductInfoSCI.getIapProductInfo(new IapProductInfoReq("0000653878"));
        assert iapProductInfo == null;
    }
}
