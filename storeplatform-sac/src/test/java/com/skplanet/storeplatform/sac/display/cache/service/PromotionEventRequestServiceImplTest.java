package com.skplanet.storeplatform.sac.display.cache.service;

import com.google.common.collect.Sets;
import com.skplanet.plandasj.Plandasj;
import com.skplanet.spring.data.plandasj.PlandasjConnectionFactory;
import com.skplanet.storeplatform.sac.display.cache.SacRedisKeys;
import com.skplanet.storeplatform.sac.display.cache.vo.GetPromotionEventParam;
import com.skplanet.storeplatform.sac.display.cache.vo.PromotionEvent;
import com.skplanet.storeplatform.sac.display.cache.vo.RawPromotionEvent;
import com.skplanet.storeplatform.sac.display.cache.vo.RawPromotionUser;
import com.skplanet.storeplatform.sac.display.promotion.PromotionEventDataService;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

/*
  테스트 수행시 주어진 tenantId, promTypeValue만을 사용해야 한다.
  해당 값이 redis에 저장되는 키를 이루는 구성 요소이고 그 값으로 테스트 이후 redis에서 해당 데이터를 제거한다.
  값을 추가하고 싶으면 반드시 tearDownTest()에 추가적인 데이터 삭제 조치를 취한다.
 */

@ActiveProfiles(value = "local")
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({"classpath:/spring-test/context-test.xml", "classpath:sac/spring/context-cache.xml"})
public class PromotionEventRequestServiceImplTest {
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

    @Autowired
    private PromotionEventRequestService promotionEventRequestService;

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
        if (plandasj != null) {
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
    }

    @Test
    @Transactional
    public void basicRequestPromotionTest() {
        // DB에 데이터 넣은 후 동기화
        RawPromotionEvent before = newRawPromotionEvent(currentDateAddedBy(-10), currentDateAddedBy(-5), promTypeValue[0], targetUserKindAll);
        RawPromotionEvent now = newRawPromotionEvent(currentDateAddedBy(-10), currentDateAddedBy(10), promTypeValue[1], targetUserKindAll);
        RawPromotionEvent after = newRawPromotionEvent(currentDateAddedBy(10), currentDateAddedBy(20), promTypeValue[2], targetUserKindAll);
        eventDataService.insertRawPromotionEvent(before);
        eventDataService.insertRawPromotionEvent(now);
        eventDataService.insertRawPromotionEvent(after);
        promotionEventSyncService.syncPromotion(tenantId, promTypeValue[0]);
        promotionEventSyncService.syncPromotion(tenantId, promTypeValue[1]);
        promotionEventSyncService.syncPromotion(tenantId, promTypeValue[2]);

        // 조회 테스트
        GetPromotionEventParam param = new GetPromotionEventParam();
        param.setTenantId(tenantId);
        param.setChnlId(promTypeValue[0]);
        param.setMenuId("NOMENUID");
        param.setUserKey("NOUSERKEY");
        PromotionEvent beforeP = promotionEventRequestService.getPromotionEvent(param);
        assertNull(beforeP);

        param.setChnlId(promTypeValue[1]);
        PromotionEvent nowP = promotionEventRequestService.getPromotionEvent(param);
        assertTrue(PromotionEventConverter.convert(now).equals(nowP));

        param.setChnlId(promTypeValue[2]);
        PromotionEvent afterP = promotionEventRequestService.getPromotionEvent(param);
        assertNull(afterP);
    }

    @Test
    @Transactional
    public void requestPromotionWithTargetUserTest() {
        // 데이터 생성
        RawPromotionEvent now = newRawPromotionEvent(currentDateAddedBy(-10), currentDateAddedBy(10), promTypeValue[0], targetUserKindTarget);
        RawPromotionUser[] nowU = {newRawPromotionUser(now.getPromId(), "nowU1"), newRawPromotionUser(now.getPromId(), "nowU2")};

        // DB 입력 및 동기화
        eventDataService.insertRawPromotionEvent(now);
        insertPromotionUsers(nowU);
        promotionEventSyncService.syncPromotion(tenantId, now.getPromTypeValue());


        // 조회 테스트
        GetPromotionEventParam param = new GetPromotionEventParam();
        param.setTenantId(tenantId);
        param.setChnlId("NOCHNLID");
        param.setMenuId(promTypeValue[0]);
        param.setUserKey("nowU1");
        PromotionEvent nowP = promotionEventRequestService.getPromotionEvent(param);
        assertTrue(PromotionEventConverter.convert(now).equals(nowP));

        param.setUserKey("NOUSERKEY");
        PromotionEvent noUserKeyP = promotionEventRequestService.getPromotionEvent(param);
        assertNull(noUserKeyP);
    }

    /*
        다수의 쓰레드가 DB에 접근하는 테스트라서 Transactional을 사용할 수가 없다.
        Transactional을 사용하는 경우 다른 쓰레드에서 DB의 데이터를 읽어도 값이 없는 것 처럼 나온다. (READ_COMMITTED)
        Oracle에서는 READ_UNCOMITTED를 지원하지 않는다.
     */
    @Test
    public void nextPromotionEventTest() throws InterruptedException {
            // 데이터 생성
            RawPromotionEvent before = newRawPromotionEvent(currentDateAddedBy(-10), currentDateAddedBy(-5), promTypeValue[0], targetUserKindTarget);
            RawPromotionUser[] beforeU = {newRawPromotionUser(before.getPromId(), "beforeU1"), newRawPromotionUser(before.getPromId(), "beforeU2")};
            RawPromotionEvent now = newRawPromotionEvent(currentDateAddedBy(-10), currentDateAddedBy(10), promTypeValue[0], targetUserKindTarget);
            RawPromotionUser[] nowU = {newRawPromotionUser(now.getPromId(), "nowU1"), newRawPromotionUser(now.getPromId(), "nowU2")};
        try {
            // DB 입력
            eventDataService.insertRawPromotionEvent(before);
            insertPromotionUsers(beforeU);
            eventDataService.insertRawPromotionEvent(now);
            insertPromotionUsers(nowU);

            // Redis에 강제로 옛날 데이터를 저장
            plandasj.hset(SacRedisKeys.promotionEventKey().getBytes()
                    , SacRedisKeys.promotionEventField(tenantId, promTypeValue[0]).getBytes()
                    , PromotionEventConverter.convert(PromotionEventConverter.convert(before)));
            plandasj.sadd(SacRedisKeys.promotionEventTargetUserKey(tenantId, promTypeValue[0]), "beforeU1", "beforeU2");

            // Lock 초기화
            plandasj.del(SacRedisKeys.promotionEventLockKey(tenantId, promTypeValue[0]));

            // 조회시 자동으로 다음 이벤트를 가져 오는지 확인
            GetPromotionEventParam param = new GetPromotionEventParam();
            param.setTenantId(tenantId);
            param.setChnlId("NOCHNLID");
            param.setMenuId(promTypeValue[0]);
            param.setUserKey("nowU1");
            PromotionEvent nowP = promotionEventRequestService.getPromotionEvent(param);
            assertNull(nowP);
            for (int i = 0; i < 10; ++i) {
                nowP = promotionEventRequestService.getPromotionEvent(param);
                Thread.sleep(1);
                if (nowP != null) break;
            }
            assertTrue(PromotionEventConverter.convert(now).equals(nowP));

            param.setUserKey("beforeU1");
            PromotionEvent beforeP = promotionEventRequestService.getPromotionEvent(param);
            assertNull(beforeP);
        }
        finally {
            eventDataService.deleteEvents(tenantId, before.getPromId());
            eventDataService.deleteEvents(tenantId, now.getPromId());
            eventDataService.deleteTargetUsers(before.getPromId());
            eventDataService.deleteTargetUsers(now.getPromId());
        }
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
