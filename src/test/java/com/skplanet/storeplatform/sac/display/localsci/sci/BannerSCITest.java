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
import com.skplanet.storeplatform.framework.core.exception.StorePlatformMethodArgumentNotValidException;
import com.skplanet.storeplatform.sac.client.internal.display.localsci.sci.BannerInfoSCI;
import com.skplanet.storeplatform.sac.client.internal.display.localsci.vo.Banner;
import com.skplanet.storeplatform.sac.client.internal.display.localsci.vo.BannerInfoSacReq;
import com.skplanet.storeplatform.sac.client.internal.display.localsci.vo.BannerInfoSacRes;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
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
 * Created by 1002184 on 2015-01-14.
 */
@ActiveProfiles(value = "local")
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({ "classpath*:/spring-test/context-test.xml" })
@Transactional
public class BannerSCITest {

    private static final Logger logger = LoggerFactory.getLogger(BannerSCITest.class);

    @Autowired
    private BannerInfoSCI bannerInfoSCI;

    @Test
    public void testGetBannerInfoList_정상() {
        BannerInfoSacReq req = new BannerInfoSacReq();
        req.setTenantId("S01");
        req.setBnrMenuId("DP010929");
        req.setBnrExpoMenuId("DP011100");
        req.setImgSizeCd("DP011033");
        req.setCount(3);

        try {
            BannerInfoSacRes res = this.bannerInfoSCI.getBannerInfoList(req);
            this.logger.debug("##### banner cnt : {}", res.getBannerList().size());
            for (Banner banner : res.getBannerList()) {
                this.logger.debug("##### banner VO : {}",
                        ReflectionToStringBuilder.toString(banner, ToStringStyle.MULTI_LINE_STYLE));
            }
        } catch (StorePlatformException e) {

        }
    }

    @Test
    public void testGetBannerInfoList_카운트파라미터없이요청() {
        BannerInfoSacReq req = new BannerInfoSacReq();
        req.setTenantId("S01");
        req.setBnrMenuId("DP010929");
        req.setBnrExpoMenuId("DP011100");
        req.setImgSizeCd("DP011030");

        try {
            this.bannerInfoSCI.getBannerInfoList(req);
        } catch (StorePlatformException e) {
            this.logger.debug(e.getMessage());
        }
    }

    @Test(expected = StorePlatformException.class)
    public void testGetBannerInfoList_요청자료없음() {
        BannerInfoSacReq req = new BannerInfoSacReq();
        req.setTenantId("S01");
        req.setBnrMenuId("DP010929");
        req.setBnrExpoMenuId("DP011100");
        req.setImgSizeCd("DP0110XX");
        req.setCount(3);

        this.bannerInfoSCI.getBannerInfoList(req);
    }

    @Test(expected = StorePlatformMethodArgumentNotValidException.class)
    public void testGetBannerInfoList_필수파라미터없이요청() {
        BannerInfoSacReq req = new BannerInfoSacReq();
        req.setBnrMenuId("DP010929");
        req.setBnrExpoMenuId("DP011100");
        req.setImgSizeCd("DP011033");
        req.setCount(3);

        this.bannerInfoSCI.getBannerInfoList(req);
    }
}
