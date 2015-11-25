package com.skplanet.storeplatform.sac.display.common;

import com.skplanet.storeplatform.sac.display.cache.service.CacheEvictManager;
import com.skplanet.storeplatform.sac.display.common.constant.DisplayConstants;
import com.skplanet.storeplatform.sac.display.common.service.DisplayCommonService;
import com.skplanet.storeplatform.sac.display.common.vo.TenantSalePolicy;
import com.skplanet.storeplatform.sac.display.common.vo.TmembershipDcInfo;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;

/**
 * 전시 공통 모듈 테스트
 *
 * Updated on : 2014. 01. 07 Updated by : 정희원, SK 플래닛.
 */
@ActiveProfiles(value = "local")
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({"classpath*:/spring-test/context-test.xml"})
@WebAppConfiguration
//@TransactionConfiguration
//@Transactional
public class CommonServiceTest {

    protected Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private DisplayCommonService commonService;

    @Autowired
    private CacheEvictManager cacheEvictManager;

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

    @Test
    public void testGetTmembershipDcRateForMenu() {
        TmembershipDcInfo tmembershipDcRateForMenu = commonService.getTmembershipDcRateForMenu("S01", "DP01");
        assert tmembershipDcRateForMenu.getNormalDcRate() == 50;
    }

    @Test
    public void testGetTmembershipDcRateForMenu02() {
        TmembershipDcInfo tmembershipDcRateForMenu = commonService.getTmembershipDcRateForMenu("S01", "DP18");
        assert tmembershipDcRateForMenu.getNormalDcRate() == 50;
        assert tmembershipDcRateForMenu.getFreepassDcRate() == 50;
    }

    @Test
    public void testGetTmembershipDcRateMax() {
        cacheEvictManager.evictAllTmembershipDcRate();
        TmembershipDcInfo tmembershipDcRateForMenu = commonService.getTmembershipDcRateForMenu("S01", DisplayConstants.REQUEST_TMEMBERSHIP_ALL_MENU);
        logger.info(ReflectionToStringBuilder.toString(tmembershipDcRateForMenu));
    }

    @Test
    public void checkPurchaseTest() {

        // 구매된 것
        boolean v1 = commonService.checkPurchase("S01", "IM142100005724280201303121051", "01046353524", "S930000381");
//        assert v1;

        // 구매 취소된 것
        boolean v2 = commonService.checkPurchase("S01", "IM142100005724280201303121051", "01046353524", "S930000383");
//        assert !v2;
    }

}
