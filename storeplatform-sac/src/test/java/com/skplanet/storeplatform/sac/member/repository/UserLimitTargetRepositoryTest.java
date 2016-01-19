package com.skplanet.storeplatform.sac.member.repository;

import com.google.common.base.Objects;
import com.skplanet.storeplatform.sac.member.domain.shared.UserLimitTarget;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import static org.junit.Assert.*;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({ "classpath*:/spring-test/context-test.xml" })
@ActiveProfiles("local")
@Transactional("transactionManagerForMember")
public class UserLimitTargetRepositoryTest {

    @PersistenceContext(unitName = "puMbr")
    EntityManager em;

    @Autowired
    UserLimitTargetRepository limitTargetRepository;


    @Test
    public void findByLimitPolicyKeyAndLimtPolicyCdIn() {
        //Given
        String limtPolicyKey = "01088870008";
        String limtPolicyCd = "OR003101";

        //When
        List<UserLimitTarget> userLimitTarget = limitTargetRepository.findByLimitPolicyKeyAndLimtPolicyCdIn(limtPolicyKey, Arrays.asList(limtPolicyCd));

        //Then
        System.out.println(userLimitTarget);
    }

    @Test
    public void findByLimitPolicyKeyAndLimtPolicyCdIn_결과값없음() {
        //Given

        //When
        List<UserLimitTarget> userLimitTarget = limitTargetRepository.findByLimitPolicyKeyAndLimtPolicyCdIn("", new ArrayList<String>());

        // Then
        assertNull(userLimitTarget);
    }

    @Test
    public void findByLimitPolicyKeyAndLimtPolicyCdIn_limtPolicyKey() {
        //Given
        String limtPolicyKey = "01088870008";
        List<String> limtPolicyCdList = null;

        //When
        limitTargetRepository.findByLimitPolicyKeyAndLimtPolicyCdIn(limtPolicyKey, limtPolicyCdList);

        // Then
    }

    @Test
    public void findByLimitPolicyKeyAndLimtPolicyCdIn_limtPolicyKey_and_limtPolicyCdList() {
        //Given
        String limtPolicyKey = "01088870008";
        List<String> limtPolicyCdList = Arrays.asList("OR003101", "OR003103");

        //When
        //List<UserLimitTarget> userLimitTargets =
        limitTargetRepository.findByLimitPolicyKeyAndLimtPolicyCdIn(limtPolicyKey, limtPolicyCdList);

        // Then
        //System.out.println(userLimitTargets);
    }

    //void saveLimitPolicy(List<LimitTarget> limitTargets) throws ParseException;
    @Test
    @Transactional
    public void saveLimitPolicy_persist() {
        //Given
        UserLimitTarget userLimitTarget = getDummyLimitTarget();

        //When
        limitTargetRepository.saveLimitPolicy(userLimitTarget);

        // Then
        List<UserLimitTarget> limitTargets = limitTargetRepository.findByLimitPolicyKeyAndLimtPolicyCdIn(userLimitTarget.getLimtPolicyKey(), Arrays.asList(userLimitTarget.getLimtPolicyCd()));
        assertTrue(limitTargets.size() == 1);

    }

    private UserLimitTarget getDummyLimitTarget() {
        SimpleDateFormat sdFormat = new SimpleDateFormat("yyyyMMddHHmmSS");
        UserLimitTarget userLimitTarget = new UserLimitTarget();
        userLimitTarget.setLimtPolicyKey("0000");
        userLimitTarget.setLimtPolicyCd("0000");
        userLimitTarget.setEndDtString(sdFormat, "29991231235959");
        userLimitTarget.setLimtAmt("1000");
        userLimitTarget.setStartDt(new Date());
        userLimitTarget.setRegId("test");
        userLimitTarget.setPolicyApplyValue("apply_value");
        return userLimitTarget;
    }

    @Test(expected = DataIntegrityViolationException.class)
    @Transactional
    public void saveLimitPolicy_DataIntegrityViolationException() {
        //Given
        //DateFormat sdFormat = new SimpleDateFormat("yyyyMMddHHmmSS");
        //Date endDt = null;
        //try { endDt = sdFormat.parse("29991231235959"); } catch (ParseException ignore) {}

        UserLimitTarget userLimitTarget = new UserLimitTarget();
        userLimitTarget.setLimtPolicyKey("0000");
        userLimitTarget.setLimtPolicyCd("0000");
        userLimitTarget.setEndDt(null); //필수값 테스트
        userLimitTarget.setLimtAmt("1000");

        //When
        limitTargetRepository.saveLimitPolicy(userLimitTarget);
        limitTargetRepository.findByLimitPolicyKeyAndLimtPolicyCdIn(userLimitTarget.getLimtPolicyKey(), Arrays.asList(userLimitTarget.getLimtPolicyCd()));

        assertTrue("필수값이 없기 때문에 Exception 이 발생해야 함", false);
    }

    @Transactional
    public Integer persist(UserLimitTarget userLimitTarget) {
        limitTargetRepository.saveLimitPolicy(userLimitTarget);
        return userLimitTarget.getSeq();
    }

    @Test
    public void findOne() {
        //Given
        UserLimitTarget userLimitTarget = getDummyLimitTarget();
        Integer seq = persist(userLimitTarget);
        
        //When
        UserLimitTarget findUserLimitTarget = limitTargetRepository.findOne(seq);

        // Then
        assertNotNull(findUserLimitTarget.getSeq());
    }
    
    @Test
    public void updateLimitPolicyHistory() {
        //Given
        UserLimitTarget userLimitTarget = getDummyLimitTarget();
        Integer seq = persist(userLimitTarget);
        String updId = "test";
        
        //When
        limitTargetRepository.findOne(seq);
        Integer count = limitTargetRepository.updateLimitPolicyHistory(seq, updId);
        UserLimitTarget findUserLimitTarget = limitTargetRepository.findOne(seq);

        //Then
        assertTrue(count > 0);
        System.out.println(Objects.toStringHelper(findUserLimitTarget));
        //TODO : updId 비교
        //assertEquals(updId, findUserLimitTarget.getUpdId());
    }

    @Test
    public void updateLimitPolicyKey() {
        //Given
        UserLimitTarget userLimitTarget = getDummyLimitTarget();
        Integer seq = persist(userLimitTarget);
        String newLimtPolicyKey = "test";

        //When
        Integer count = limitTargetRepository.updateLimitPolicyKey(userLimitTarget.getLimtPolicyKey(), newLimtPolicyKey);
        em.flush();
        UserLimitTarget findUserLimitTarget = limitTargetRepository.findOne(seq);

        //Then
        assertTrue(count > 0);
        assertEquals(newLimtPolicyKey, findUserLimitTarget.getLimtPolicyKey());
    }

    @Test
    public void updatePolicyApplyValue() {
        //Given
        UserLimitTarget userLimitTarget = getDummyLimitTarget();
        Integer seq = persist(userLimitTarget);
        String newPolicyApplyValue = "test";

        //When
        Integer count = limitTargetRepository.updatePolicyApplyValue(userLimitTarget.getPolicyApplyValue(), newPolicyApplyValue);
        em.flush();
        UserLimitTarget findUserLimitTarget = limitTargetRepository.findOne(seq);

        //Then
        assertTrue(count > 0);
        assertEquals(newPolicyApplyValue, findUserLimitTarget.getPolicyApplyValue());
    }

}