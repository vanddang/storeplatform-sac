/*
 * Copyright (c) 2013 SK planet.
 * All right reserved.
 *
 * This software is the confidential and proprietary information of SK planet.
 * You shall not disclose such Confidential Information and
 * shall use it only in accordance with the terms of the license agreement
 * you entered into with SK planet.
 */
package com.skplanet.storeplatform.sac.display.cache.service;

import com.skplanet.storeplatform.sac.display.card.vo.CardSegment;
import com.skplanet.storeplatform.sac.display.cache.vo.PanelCardMapping;
import com.skplanet.storeplatform.sac.display.cache.vo.PanelItem;
import com.skplanet.storeplatform.sac.display.cache.vo.MenuListCat;

import java.util.List;

/**
 * <p>
 * TStore 4.0용 Panel, Card 정보 캐시 관리자
 * </p>
 * Updated on : 2014. 11. 06 Updated by : 정희원, SK 플래닛.
 */
public interface PanelCardInfoManager {

    Object getCardDetail(String tenantId, String cardId);

    /**
     * 패널에 속한 카드 목록을 조회한다.
     * @param tenantId
     * @param panelId
     * @return
     */
    List<PanelItem> getPanelList(String tenantId, String panelId);

    /**
     * 패널에 대한 카드 매핑을 조회한다.
     * @param tenantId
     * @param panelId
     * @return
     */
    List<PanelCardMapping> getCardListInPanel(String tenantId, String panelId);

    /**
     * 카드 세그먼트 정보를 조회한다.
     * @param tenantId
     * @param cardId
     * @return
     */
    CardSegment getCardSegmentInfo(String tenantId, String cardId);

    /**
     * 통합 메뉴 리스트 조회
     * @param tenantId
     * @param systemId
     * @param langCd
     * @param upMenuKey
     * @return
     */
    List<MenuListCat> getMenuList(String tenantId, String systemId, String langCd, String upMenuKey);
}
