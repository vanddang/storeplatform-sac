package com.skplanet.storeplatform.sac.member.repository;

import com.skplanet.storeplatform.sac.member.domain.LimitTarget;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.List;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({ "classpath*:/spring-test/context-test.xml" })
@ActiveProfiles("local")
@Transactional("transactionManagerForJpaMember")
public class LimitTargetRepositoryTest {

    @Autowired
    LimitTargetRepository limitTargetRepository;

    @Test
    public void findByLimitPolicyKeyAndLimtPolicyCdIn_limtPolicyKey() {
        String limtPolicyKey = "01088870008";
        List<String> limtPolicyCdList = null;
        List<LimitTarget> limitTargets = limitTargetRepository.findByLimitPolicyKeyAndLimtPolicyCdIn(limtPolicyKey, limtPolicyCdList);
    }

    @Test
    public void findByLimitPolicyKeyAndLimtPolicyCdIn_limtPolicyKey_and_limtPolicyCdList() {
            String limtPolicyKey = "01088870008";
        List<String> limtPolicyCdList = Arrays.asList(new String[]{"OR003101", "OR003103"});
        List<LimitTarget> limitTargets = limitTargetRepository.findByLimitPolicyKeyAndLimtPolicyCdIn(limtPolicyKey, limtPolicyCdList);

        System.out.println(limitTargets);
    }

}