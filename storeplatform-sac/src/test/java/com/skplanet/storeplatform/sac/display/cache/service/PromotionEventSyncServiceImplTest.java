package com.skplanet.storeplatform.sac.display.cache.service;

import com.google.common.collect.Sets;
import com.skplanet.plandasj.Plandasj;
import com.skplanet.spring.data.plandasj.PlandasjConnectionFactory;
import com.skplanet.storeplatform.sac.display.cache.SacRedisKeys;
import com.skplanet.storeplatform.sac.display.cache.vo.RawPromotionEvent;
import com.skplanet.storeplatform.sac.display.cache.vo.RawPromotionUser;
import com.skplanet.storeplatform.sac.display.promotion.PromotionEventDataService;
import org.apache.commons.lang3.time.StopWatch;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;


/*
  테스트 수행시 주어진 tenantId, promTypeValue만을 사용해야 한다.
  해당 값이 redis에 저장되는 키를 이루는 구성 요소이고 그 값으로 테스트 이후 redis에서 해당 데이터를 제거한다.
  값을 추가하고 싶으면 반드시 tearDownTest()에 추가적인 데이터 삭제 조치를 취한다.
 */

@ActiveProfiles(value = "local")
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({"classpath:/spring-test/context-test.xml", "classpath:sac/spring/context-cache.xml"})
@Transactional
public class PromotionEventSyncServiceImplTest {
    private String tenantId = "Z00";
    private String targetUserKindAll = "DP01400001";
    private String targetUserKindTarget = "DP01400002";
    private Plandasj plandasj;
    private String[] promTypeValue = {"VALUE_1", "VALUE_2", "VALUE_3"};

    @Autowired
    private PlandasjConnectionFactory connectionFactory;

    @Autowired
    private PromotionEventDataService eventDataService;

    @Autowired
    private PromotionEventSyncService promotionEventSyncService;

    @Before
    public void setUpTest() {
        plandasj = connectionFactory.getConnectionPool().getClient();
        plandasj.hdel(SacRedisKeys.promotionEventKey()
                , SacRedisKeys.promotionEventField(tenantId, promTypeValue[0])
                , SacRedisKeys.promotionEventField(tenantId, promTypeValue[1])
                , SacRedisKeys.promotionEventField(tenantId, promTypeValue[2])
        );
        plandasj.del(
                SacRedisKeys.promotionEventTargetUserKey(tenantId, promTypeValue[0])
                , SacRedisKeys.promotionEventTargetUserKey(tenantId, promTypeValue[1])
                , SacRedisKeys.promotionEventTargetUserKey(tenantId, promTypeValue[2])
        );
    }

    @After
    public void tearDownTest() {
        plandasj.hdel(SacRedisKeys.promotionEventKey()
                , SacRedisKeys.promotionEventField(tenantId, promTypeValue[0])
                , SacRedisKeys.promotionEventField(tenantId, promTypeValue[1])
                , SacRedisKeys.promotionEventField(tenantId, promTypeValue[2])

        );
        plandasj.del(
                SacRedisKeys.promotionEventTargetUserKey(tenantId, promTypeValue[0])
                , SacRedisKeys.promotionEventTargetUserKey(tenantId, promTypeValue[1])
                , SacRedisKeys.promotionEventTargetUserKey(tenantId, promTypeValue[2])
        );
    }

    @Test
    public void basicSyncPromotionTest() {
        // DB에 데이터 넣기
        RawPromotionEvent before = newRawPromotionEvent(currentDateAddedBy(-10), currentDateAddedBy(-5), promTypeValue[0], targetUserKindAll);
        RawPromotionEvent now = newRawPromotionEvent(currentDateAddedBy(-10), currentDateAddedBy(10), promTypeValue[1], targetUserKindAll);
        RawPromotionEvent after = newRawPromotionEvent(currentDateAddedBy(10), currentDateAddedBy(20), promTypeValue[2], targetUserKindAll);
        eventDataService.insertRawPromotionEvent(before);
        eventDataService.insertRawPromotionEvent(now);
        eventDataService.insertRawPromotionEvent(after);

        // 동기화 요청하기
        promotionEventSyncService.syncPromotion(tenantId);

        // Plandas에서 관련 데이터 조회하기
        assertEquals(2, plandasj.hkeys(SacRedisKeys.promotionEventKey()).size());
        byte[] nowP = plandasj.hget(SacRedisKeys.promotionEventKey().getBytes(), SacRedisKeys.promotionEventField(tenantId, promTypeValue[1]).getBytes());
        assertTrue(PromotionEventConverter.convert(now).equals(PromotionEventConverter.convert(nowP)));
        System.out.println(PromotionEventConverter.convert(nowP));

        byte[] afterP = plandasj.hget(SacRedisKeys.promotionEventKey().getBytes(), SacRedisKeys.promotionEventField(tenantId, promTypeValue[2]).getBytes());
        assertTrue(PromotionEventConverter.convert(after).equals(PromotionEventConverter.convert(afterP)));
    }

    @Test
    public void multiPromotionTest() {
        // DB에 데이터 넣기
        RawPromotionEvent before = newRawPromotionEvent(currentDateAddedBy(-10), currentDateAddedBy(-5), promTypeValue[0], targetUserKindAll);
        RawPromotionEvent now_one = newRawPromotionEvent(currentDateAddedBy(-10), currentDateAddedBy(10), promTypeValue[0], targetUserKindAll);
        RawPromotionEvent now_two = newRawPromotionEvent(currentDateAddedBy(-5), currentDateAddedBy(10), promTypeValue[0], targetUserKindAll);
        RawPromotionEvent after = newRawPromotionEvent(currentDateAddedBy(10), currentDateAddedBy(20), promTypeValue[0], targetUserKindAll);
        eventDataService.insertRawPromotionEvent(before);
        eventDataService.insertRawPromotionEvent(now_one);
        eventDataService.insertRawPromotionEvent(now_two);
        eventDataService.insertRawPromotionEvent(after);

        // 동기화 요청하기
        promotionEventSyncService.syncPromotion(tenantId);

        // Plandas에서 관련 데이터 조회하기
        assertEquals(1, plandasj.hkeys(SacRedisKeys.promotionEventKey()).size());
        byte[] nowP = plandasj.hget(SacRedisKeys.promotionEventKey().getBytes(), SacRedisKeys.promotionEventField(tenantId, promTypeValue[0]).getBytes());
        assertTrue(PromotionEventConverter.convert(now_one).equals(PromotionEventConverter.convert(nowP)));
    }

    @Test
    public void targetUserTest() {
        // DB에 데이터 넣기
        RawPromotionEvent before = newRawPromotionEvent(currentDateAddedBy(-10), currentDateAddedBy(-5), promTypeValue[0], targetUserKindTarget);
        RawPromotionUser[] beforeU = {newRawPromotionUser(before.getPromId(), "beforeU1")};
        RawPromotionEvent nowOne = newRawPromotionEvent(currentDateAddedBy(-10), currentDateAddedBy(10), promTypeValue[1], targetUserKindTarget);
        RawPromotionUser[] nowOneU = {newRawPromotionUser(nowOne.getPromId(), "nowOneU1"), newRawPromotionUser(nowOne.getPromId(), "nowOneU2")};
        RawPromotionEvent nowTwo = newRawPromotionEvent(currentDateAddedBy(-5), currentDateAddedBy(10), promTypeValue[1], targetUserKindTarget);
        RawPromotionUser[] nowTwoU = {newRawPromotionUser(nowTwo.getPromId(), "nowTwoU1"), newRawPromotionUser(nowTwo.getPromId(), "nowTwoU2")};
        RawPromotionEvent after = newRawPromotionEvent(currentDateAddedBy(10), currentDateAddedBy(20), promTypeValue[2], targetUserKindTarget);
        RawPromotionUser[] afterU = {newRawPromotionUser(after.getPromId(), "afterU1"), newRawPromotionUser(after.getPromId(), "afterU2")};
        eventDataService.insertRawPromotionEvent(before);
        insertPromotionUsers(beforeU);
        eventDataService.insertRawPromotionEvent(nowOne);
        insertPromotionUsers(nowOneU);
        eventDataService.insertRawPromotionEvent(nowTwo);
        insertPromotionUsers(nowTwoU);
        eventDataService.insertRawPromotionEvent(after);
        insertPromotionUsers(afterU);

        // 동기화 요청하기
        promotionEventSyncService.syncPromotion(tenantId);

        // Plandas에서 관련 데이터 조회하기
        assertEquals(0L, (long)plandasj.scard(SacRedisKeys.promotionEventTargetUserKey(tenantId, promTypeValue[0])));
        assertEquals(2L, (long)plandasj.scard(SacRedisKeys.promotionEventTargetUserKey(tenantId, promTypeValue[1])));
        assertEquals(2L, (long) plandasj.scard(SacRedisKeys.promotionEventTargetUserKey(tenantId, promTypeValue[2])));
        Set<String> userKeys = plandasj.smembers(SacRedisKeys.promotionEventTargetUserKey(tenantId, promTypeValue[1]));
        Set<String> expectedUserKeys = new HashSet<String>(Arrays.asList("nowOneU1", "nowOneU2"));
        assertTrue(Sets.symmetricDifference(expectedUserKeys, userKeys).isEmpty());
    }

    @Test
    public void skipSyncTest() throws InterruptedException {
        // DB에 데이터 넣기
        RawPromotionEvent now = newRawPromotionEvent(currentDateAddedBy(-10), currentDateAddedBy(10), promTypeValue[1], targetUserKindAll);
        RawPromotionEvent after = newRawPromotionEvent(currentDateAddedBy(10), currentDateAddedBy(20), promTypeValue[1], targetUserKindAll);
        eventDataService.insertRawPromotionEvent(now);
        eventDataService.insertRawPromotionEvent(after);

        StopWatch syncTime = new StopWatch();
        syncTime.start();
        // 동기화 수행
        promotionEventSyncService.syncPromotion(tenantId, promTypeValue[1]);
        syncTime.stop();

        StopWatch skipTime = new StopWatch();
        skipTime.start();
        // 동일 데이터는 동기화 스킵
        promotionEventSyncService.syncPromotion(tenantId, promTypeValue[1]);
        skipTime.stop();

        // 시간 비교
        assertTrue(syncTime.getTime() > skipTime.getTime());
    }

    private RawPromotionEvent newRawPromotionEvent(Date startDt, Date endDt, String promTypeValue, String targetUserKind) {
        RawPromotionEvent rpe = new RawPromotionEvent();
        rpe.setTenantId(tenantId);
        rpe.setStartDt(startDt);
        rpe.setEndDt(endDt);
        rpe.setPromTypeCd("ANY_CD");
        rpe.setPromTypeValue(promTypeValue);
        rpe.setPromId(eventDataService.maxPromId() + (int)(Math.random() * 1000));
        rpe.setAcmlMethodCd("ANY_CD");
        rpe.setAcmlDate(endDt);
        rpe.setTargetUserKind(targetUserKind);
        rpe.setUpdDt(currentDateAddedBy(0));

        rpe.setRateGrd1(0);
        rpe.setRateGrd2(0);
        rpe.setRateGrd3(0);
        return rpe;
    }

    private RawPromotionUser newRawPromotionUser(int promId, String userKey) {
        RawPromotionUser rpu = new RawPromotionUser();
        rpu.setPromId(promId);
        rpu.setUserKey(userKey);
        rpu.setDeviceKeyList("[]");
        return rpu;
    }

    private Date currentDateAddedBy(int min) {
        Calendar now = Calendar.getInstance();
        now.add(Calendar.MINUTE, min);
        now.set(Calendar.MILLISECOND, 0);
        return now.getTime();
    }

    private void insertPromotionUsers(RawPromotionUser[] users) {
        for (RawPromotionUser user : users) {
            eventDataService.insertRawPromotionUser(user);
        }
    }
}
