package com.skplanet.storeplatform.sac.display;

import com.skplanet.storeplatform.sac.display.common.service.DisplayCommonService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;

/**
 * 전시 공통 모듈 테스트
 *
 * Updated on : 2014. 01. 07 Updated by : 정희원, SK 플래닛.
 */
@ActiveProfiles(value = "local")
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({"classpath*:/spring-test/context-test.xml"})
@TransactionConfiguration
@Transactional
public class CommonServiceTest {

    protected Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private DisplayCommonService commonService;

    @Test
    public void getBatchStandardDateTest()
    {
        String dt = commonService.getBatchStandardDateString("1", "2");
        logger.info("{}", dt);
    }

    @Test
    public void getBatchStandardDateTest2()
    {
        String dt = commonService.getBatchStandardDateString("1", "3");
        logger.info("{}", dt);
    }

    @Test
    public void menuListTest() {
        commonService.getMenuItemList("", "");
    }

}
