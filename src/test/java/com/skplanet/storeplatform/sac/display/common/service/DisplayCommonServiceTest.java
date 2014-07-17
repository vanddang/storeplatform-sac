/*
 * Copyright (c) 2013 SK planet.
 * All right reserved.
 *
 * This software is the confidential and proprietary information of SK planet.
 * You shall not disclose such Confidential Information and
 * shall use it only in accordance with the terms of the license agreement
 * you entered into with SK planet.
 */
package com.skplanet.storeplatform.sac.display.common.service;

import com.skplanet.storeplatform.framework.core.exception.StorePlatformException;
import com.skplanet.storeplatform.sac.display.common.ProductType;
import com.skplanet.storeplatform.sac.display.common.vo.ProductInfo;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * <p>
 * DESC
 * </p>
 * Updated on : 2014. 07. 08 Updated by : 정희원, SK 플래닛.
 */
@ActiveProfiles(value = "local")
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({ "classpath*:/spring-test/context-test.xml" })
public class DisplayCommonServiceTest {

    @Autowired
    private DisplayCommonService commonService;

    @Test(expected = StorePlatformException.class)
    public void test01() {
        commonService.selectProductInfo("");
    }

    @Test
    public void test02() {
        ProductInfo info = commonService.selectProductInfo("0000651325");
        assert info.getProductType() == ProductType.App;
    }

    @Test
    public void test03() {
        ProductInfo info = commonService.selectProductInfo("H002882740");
        assert info.getProductType() == ProductType.Webtoon;
    }
}
