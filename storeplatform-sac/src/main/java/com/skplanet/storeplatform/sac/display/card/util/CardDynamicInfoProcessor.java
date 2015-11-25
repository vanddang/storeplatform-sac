/*
 * Copyright (c) 2013 SK planet.
 * All right reserved.
 *
 * This software is the confidential and proprietary information of SK planet.
 * You shall not disclose such Confidential Information and
 * shall use it only in accordance with the terms of the license agreement
 * you entered into with SK planet.
 */
package com.skplanet.storeplatform.sac.display.card.util;

import com.google.common.base.Strings;
import com.skplanet.storeplatform.sac.client.product.vo.Card;
import com.skplanet.storeplatform.sac.client.product.vo.Stats;
import com.skplanet.storeplatform.sac.display.card.service.CardDetailService;
import com.skplanet.storeplatform.sac.display.card.vo.CardDynamicInfo;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * CardDynamicInfoProcessor
 * 카드의 동적 자료들을 일괄 처리해준다.
 * 노출할 카드들을 addCard()로 추가해놓고 로직 마지막에 execute()를 호출하면 추가한 카드들에 대해 일괄적으로 동적 자료들을 적용해준다.
 * </p>
 * Updated on : 2014. 12. 01 Updated by : 정희원, SK 플래닛.
 */
public class CardDynamicInfoProcessor {

    private String tenantId;
    private Map<String, Card> cardMap;

    private CardDetailService detailService;

    public CardDynamicInfoProcessor(String tenantId, CardDetailService detailService) {
        if(Strings.isNullOrEmpty(tenantId) || detailService == null)
            throw new IllegalArgumentException("유효한 tenantId, detailService 값을 입력해주어야 합니다.");

        this.tenantId = tenantId;
        this.detailService = detailService;
        cardMap = new LinkedHashMap<String, Card>();
    }

    /**
     * 카드 상세 정보를 조회한다.
     * @param card
     * @return
     */
    public void addCard(Card card) {

        if (card == null)
            return;

        cardMap.put(card.getId(), card);
    }

    /**
     * 해당 사용자의
     * @param userKey
     */
    public void execute(String userKey) {

        List<CardDynamicInfo> cardDynList = detailService.getCardDynamicInfo(tenantId, userKey, new ArrayList<String>(cardMap.keySet()));

        for (CardDynamicInfo dynamicInfo : cardDynList) {
            Card card = cardMap.get(dynamicInfo.getCardId());
            if(card == null)
                continue;

            Stats stats = card.getStats();
            if(stats == null) {
                stats = new Stats();
                card.setStats(stats);
            }

            stats.setCntShar(dynamicInfo.getCntShar());
            stats.setCntLike(dynamicInfo.getCntLike());
            card.setLikeYn(dynamicInfo.getLikeYn());
        }
    }

}
