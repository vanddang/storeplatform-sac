package com.skplanet.storeplatform.sac.display.cache.service;

import com.skplanet.storeplatform.sac.display.cache.vo.PkgToAppInfo;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static junit.framework.Assert.assertNotNull;
import static junit.framework.Assert.assertTrue;

/**
 * Updated on : 2016-01-19. Updated by : 양해엽, SK Planet.
 */
@ActiveProfiles(value = "local")
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({ "classpath*:/spring-test/context-test.xml", "classpath:sac/spring/context-cache.xml" })
public class PkgToAppInfoManagerImplTest {

    @Autowired
    private PkgToAppInfoManager pkgToAppInfoManager;

    @Test
    public void test() {
        org.junit.Assert.assertNotNull(pkgToAppInfoManager);
    }

    @Test
    public void testGet() {

        PkgToAppInfo pkgToAppInfo = pkgToAppInfoManager.get("com.skp.android.paypin");
        if (pkgToAppInfo != null) {
            assertNotNull(pkgToAppInfo.getProdId());
        }
    }

    @Test
    public void testEvict() {
        PkgToAppInfo pkgToAppInfo = pkgToAppInfoManager.get("com.skp.android.paypin");
        if (pkgToAppInfo == null) {
            assertTrue("not exist package name", true);
            return;
        }

        pkgToAppInfoManager.evict(pkgToAppInfo.getProdId());

        pkgToAppInfo = pkgToAppInfoManager.get("com.skp.android.paypin");
        if (pkgToAppInfo != null) {
            assertNotNull(pkgToAppInfo.getProdId());
        }
    }
}