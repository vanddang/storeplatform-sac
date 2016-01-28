package com.skplanet.storeplatform.sac.display.cache.service;

import com.skplanet.storeplatform.sac.display.cache.vo.SupportDevice;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static junit.framework.Assert.assertNotNull;
import static junit.framework.Assert.assertTrue;


/**
 * Updated on : 2016-01-20. Updated by : 양해엽, SK Planet.
 */
@ActiveProfiles(value = "local")
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({ "classpath*:/spring-test/context-test.xml", "classpath:sac/spring/context-cache.xml" })
public class SupportDeviceManagerImplTest {

    @Autowired
    private SupportDeviceManager supportDeviceManager;

    @Test
    public void test() {
        assertNotNull(supportDeviceManager);
    }

    @Test
    public void testGet() {

        supportDeviceManager.get("0000655901", "SM-N920S");
        SupportDevice supportDevice = supportDeviceManager.get("0000655901", "ARC");
        if (supportDevice != null) {
            assertNotNull(supportDevice.getScId());
            assertNotNull(supportDevice.getOsVer());
            assertNotNull(supportDevice.getVer());
            assertNotNull(supportDevice.getVerCd());
        }
    }

    @Test
    public void testEvict() {
        SupportDevice supportDevice = supportDeviceManager.get("0000655901", "ARC");
        if (supportDevice == null) {
            assertTrue("not exist supportDevice", true);
            return;
        }

        supportDeviceManager.evict("0000655901");

        supportDevice = supportDeviceManager.get("0000655901", "ARC");
        if (supportDevice != null) {
            assertNotNull(supportDevice.getScId());
        }
    }
}