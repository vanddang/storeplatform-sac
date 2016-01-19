package com.skplanet.storeplatform.sac.member.user.service;

import com.skplanet.storeplatform.member.service.TransferMemberService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * Created with IntelliJ IDEA.
 * User: 1002210
 * Date: 16. 1. 18.
 * Time: 오후 7:42
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ActiveProfiles("local")
@ContextConfiguration(locations = {"classpath:/spring-test/context-test.xml"})
public class TransferMemberServiceTest {

    @Autowired
    private TransferMemberService transferMemberService;

    @Test
//    @Rollback(false)
    public void test01() {
        // 왜 두번 감싸질까나 ... ?
//        transferMemberService.executeNormalToIdle("IM120000055585520140703112545");
        transferMemberService.executeIdleToNormal("IM120000055585520140703112545");
    }

}