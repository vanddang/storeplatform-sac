/*
 * Copyright (c) 2013 SK planet.
 * All right reserved.
 *
 * This software is the confidential and proprietary information of SK planet.
 * You shall not disclose such Confidential Information and
 * shall use it only in accordance with the terms of the license agreement
 * you entered into with SK planet.
 */
package com.skplanet.storeplatform.sac.display.promotion;

import com.google.common.base.Strings;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.skplanet.storeplatform.framework.core.persistence.dao.CommonDAO;
import com.skplanet.storeplatform.sac.common.util.PartialProcessor;
import com.skplanet.storeplatform.sac.common.util.PartialProcessorHandler;
import com.skplanet.storeplatform.sac.display.cache.vo.RawPromotionEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * PromotionEventDataServiceImpl
 * </p>
 * Updated on : 2015. 10. 22 Updated by : 정희원, SK 플래닛.
 */
@Service
public class PromotionEventDataServiceImpl implements PromotionEventDataService {

    public static final String TARGET_USER_ALL = "DP01400001";
    @Autowired
    @Qualifier("sac")
    private CommonDAO commonDAO;

    @Override
    public RawPromotionEvent getLivePromotionEventForUser(String tenantId, String chnlId, String menuId, String userKey) {

        Map<String, Object> req = Maps.newHashMap();
        req.put("tenantId", tenantId);
        req.put("targetUserAll", TARGET_USER_ALL);
        req.put("userKey", userKey);
        req.put("keyList", PromotionEventUtils.makeKeys(chnlId, menuId));

        /*
         쿼리 정렬 조건 설명:
            TARGET_USER_KIND(사용자 타게팅 방법)이 '모두' 인 경우 JOIN_CD_1(참여중)으로
            그 외의 경우 TB_DP_PROM_USER에서 userKey로 찾아서 있는 경우에만 JOIN_CD_1(참여중)으로 처리한다.
            참여 여부, 탐색 순서에 따라 정렬을 하여 가장 상위에 있는 데이터가 유효한 데이터이다
         */
        return commonDAO.queryForObject("PromotionEventMapper.getLivePromotionEventForUser", req, RawPromotionEvent.class);
    }

    @Override
    public List<String> getPromotionUserList(int promId) {
        Map<String, Object> req = Maps.newHashMap();
        req.put("promId", promId);

        return commonDAO.queryForList("PromotionEventMapper.getPromotionUserList", req, String.class);
    }

    @Override
    public List<RawPromotionEvent> getRawEventList(String tenantId, List<String> keyList, int filterCd) {

        Map<String, Object> req = Maps.newHashMap();

        if(!Strings.isNullOrEmpty(tenantId))
            req.put("tenantId", tenantId);

        if(keyList != null)
            req.put("keyList", keyList);

        req.put("liveOnly", filterCd == GET_RAW_EVENT_BY_LIVE);
        req.put("liveAndReserved", filterCd == GET_RAW_EVENT_BY_READY);
        req.put("all", filterCd == GET_RAW_EVENT_BY_ALL);

        return commonDAO.queryForList("PromotionEventMapper.getPromotionEventList", req, RawPromotionEvent.class);
    }

    @Override
    public List<RawPromotionEvent> getRawEventList(String tenantId, List<Integer> promIdList) {

        final Map<String, Object> req = Maps.newHashMap();
        req.put("tenantId", tenantId);

        final List<RawPromotionEvent> res = Lists.newArrayList();
        PartialProcessor.process(promIdList, new PartialProcessorHandler<Integer>() {
            @Override
            public Integer processPaddingItem() {
                return -1;
            }

            @Override
            public void processPartial(List<Integer> partialList) {
                req.put("promIdList", partialList);
                res.addAll(commonDAO.queryForList("PromotionEventMapper.getPromotionEventList", req, RawPromotionEvent.class));
            }
        }, 20);

        return res;
    }
}
