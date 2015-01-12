/*
 * Copyright (c) 2013 SK planet.
 * All right reserved.
 *
 * This software is the confidential and proprietary information of SK planet.
 * You shall not disclose such Confidential Information and
 * shall use it only in accordance with the terms of the license agreement
 * you entered into with SK planet.
 */
package com.skplanet.storeplatform.sac.display.product.service;

import com.skplanet.icms.refactoring.deploy.DPProductTotalVO;
import com.skplanet.icms.refactoring.deploy.NotificationRefactoringSac;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * <p>
 * ProductDeployTest
 * </p>
 * Updated on : 2014. 07. 01 Updated by : 정희원, SK 플래닛.
 */
@ActiveProfiles(value = "local")
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({ "classpath*:/spring-test/context-test.xml" })
public class ProductDeployTest {

    @Autowired
    private ProductDeployCompositeService deployService;

    @Test
    public void deployTest01() {
        NotificationRefactoringSac noti = new NotificationRefactoringSac();
        noti.setTransactionKey(0);
        DPProductTotalVO dpProductTotalVO = new DPProductTotalVO();
        // ...

        deployService.executeProcess(noti);
    }
}
