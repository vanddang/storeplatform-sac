package com.skplanet.storeplatform.sac.member.domain;

import com.skplanet.storeplatform.sac.member.repository.UserClauseAgreeRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import static org.junit.Assert.*;

/**
 * UserClauseAgreeTest
 * User: 1002210
 * Date: 16. 1. 5.
 * Time: 오후 4:48
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({ "classpath*:/spring-test/context-test.xml" })
@ActiveProfiles("local")
@Transactional("transactionManagerForScMember")
public class UserClauseAgreeTest {

    @PersistenceContext(unitName = "puMbr")
    EntityManager em;

    @Autowired
    UserClauseAgreeRepository userClauseAgreeRepository;

    @Test
    public void test01() {
        UserClauseAgree a = new UserClauseAgree();
        a.setInsdUsermbrNo("IM120000055210220140602152221");
        a.setAgreeYn('Y');
        a.setClauseId("US010605");
        a.setClauseVer("1.1");
        a.setMandAgreeYn('Y');

        userClauseAgreeRepository.save(a);
        assert a.getRegDt() != null;
    }
}