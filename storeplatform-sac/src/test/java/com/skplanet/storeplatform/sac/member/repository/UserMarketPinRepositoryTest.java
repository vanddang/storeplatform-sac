package com.skplanet.storeplatform.sac.member.repository;

import com.skplanet.storeplatform.sac.member.domain.UserMarketPin;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.Assert.assertEquals;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({ "classpath*:/spring-test/context-test.xml" })
@ActiveProfiles("local")
@Transactional("transactionManagerForMember")
public class UserMarketPinRepositoryTest {

    @Autowired
    UserMarketPinRepository userMarketPinRepository;


    @Test
    public void 마켓핀등록() {
        //Given
        String insdUsermbrNo = "0";
        String pinNo = "1234";
        UserMarketPin userMarketPin = new UserMarketPin();
        userMarketPin.setInsdUsermbrNo(insdUsermbrNo);
        userMarketPin.setPinNo(pinNo);

        //When
        userMarketPinRepository.save(userMarketPin);
        UserMarketPin findUserMarketPin = userMarketPinRepository.findOne(insdUsermbrNo);

        //Then
        assertEquals(userMarketPin.getPinNo(), findUserMarketPin.getPinNo());
    }

}