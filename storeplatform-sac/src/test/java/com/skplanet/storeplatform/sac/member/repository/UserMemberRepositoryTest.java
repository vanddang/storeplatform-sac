package com.skplanet.storeplatform.sac.member.repository;

import com.skplanet.storeplatform.sac.member.domain.shared.UserMember;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

/**
 * UserMemberRepositoryTest
 * User: 1002210
 * Date: 16. 1. 4.
 * Time: 오후 7:36
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({ "classpath*:/spring-test/context-test.xml" })
@ActiveProfiles("local")
@Transactional("transactionManagerForScMember")
public class UserMemberRepositoryTest {

    @Autowired
    UserMemberRepository userMemberRepository;

    @Autowired
    UserMemberTestRepository userMemberTestRepository;

    @Test
    public void test01() {
        UserMember userMember = userMemberRepository.findByEmail("no-member@none.not");
        assert userMember == null;
    }

}