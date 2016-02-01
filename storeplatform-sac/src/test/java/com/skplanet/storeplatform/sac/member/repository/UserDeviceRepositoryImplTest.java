package com.skplanet.storeplatform.sac.member.repository;

import com.skplanet.storeplatform.framework.core.exception.StorePlatformException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:/spring-test/context-test.xml"})
@ActiveProfiles("local")
public class UserDeviceRepositoryImplTest {

    @Autowired
    private UserDeviceRepository deviceRepository;

    @Test(expected = StorePlatformException.class)
    public void doWrongMember() {
        deviceRepository.findByUserKeyAndDeviceKey("US201409171359100160007334_", "DE2014091713591006615043859");
    }

    @Test(expected = StorePlatformException.class)
    public void doWrongDevice() {
        deviceRepository.findByUserKeyAndDeviceKey("US201409171359100160007334", "DE2014091713591006615043859_");
    }

    @Test
    public void doSuccess() {
        deviceRepository.findByUserKeyAndDeviceKey("US201508121122059760027739", "DE2015081211220600915051690");
    }

}