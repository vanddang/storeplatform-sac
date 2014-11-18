/*
 * Copyright (c) 2013 SK planet.
 * All right reserved.
 *
 * This software is the confidential and proprietary information of SK planet.
 * You shall not disclose such Confidential Information and
 * shall use it only in accordance with the terms of the license agreement
 * you entered into with SK planet.
 */
package com.skplanet.storeplatform.sac.display.card.service;

import com.skplanet.storeplatform.framework.core.exception.StorePlatformException;
import com.skplanet.storeplatform.framework.core.util.StringUtils;
import com.skplanet.storeplatform.sac.client.product.vo.Card;
import com.skplanet.storeplatform.sac.client.product.vo.Panel;
import com.skplanet.storeplatform.sac.display.cache.service.PanelCardInfoManager;
import com.skplanet.storeplatform.sac.display.cache.vo.PanelCardMapping;
import com.skplanet.storeplatform.sac.display.cache.vo.PanelItem;
import com.skplanet.storeplatform.sac.display.cache.vo.SegmentInfo;
import com.skplanet.storeplatform.sac.display.card.vo.*;
import com.skplanet.storeplatform.sac.display.common.service.menu.MenuInfoService;
import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static com.skplanet.storeplatform.sac.display.common.DisplayJsonUtils.parseToSet;
import static com.skplanet.storeplatform.sac.display.common.constant.DisplayConstants.SGMT_TP_SEGMENT;

/**
 * <p>
 * CardListServiceImpl
 * </p>
 * Updated on : 2014. 10. 10 Updated by : 정희원, SK 플래닛.
 */
@Service
public class CardListServiceImpl implements CardListService {

    private static final Logger logger = LoggerFactory.getLogger(CardListServiceImpl.class);

    @Autowired
    private CardDetailService cardDetailService;

    @Autowired
    private MenuInfoService menuInfoService;

    @Autowired
    private PanelCardInfoManager panelCardInfoManager;

    private static final int PN_LEVEL_LV2 = 2;
    private static final int PN_LEVEL_LV3 = 3;

    private static final String CARDTP_FC = "CD05000030";

    private static final Pattern RX_DT_FC = Pattern.compile("DT200003(\\d\\d)");

    private static final int CARD_LIMIT_MAX = 50;

    @Override
    public Panel listInPanel(final String tenantId, final String langCd, final String panelId, final SegmentInfo sgmtKey,
                             PreferredCategoryInfo preferredCategoryInfo, boolean disableCardLimit) {

        CardListGeneratorContext ctx = new CardListGeneratorContext(tenantId, langCd, panelId, sgmtKey, preferredCategoryInfo, disableCardLimit);

        List<PanelItem> panelItems = panelCardInfoManager.getPanelList(ctx.getTenantId(), panelId);

        if(CollectionUtils.isEmpty(panelItems))
            throw new StorePlatformException("SAC_DSP_0009");

        Panel rootPn = new Panel();
        rootPn.setSubGroup(new ArrayList<Panel>());
        rootPn.setCardList(new ArrayList<Card>());

        for (PanelItem panelItem : panelItems) {
            if(panelItem.getPanelLevel() == PN_LEVEL_LV2) {
                mapPanel(rootPn, panelItem);
                attachCardList(ctx, rootPn, panelItem.getPanelId());
            }
            else if (panelItem.getPanelLevel() == PN_LEVEL_LV3) {
                Panel subPn = new Panel();
                subPn.setCardList(new ArrayList<Card>());

                mapPanel(subPn, panelItem);
                attachCardList(ctx, subPn, panelItem.getPanelId());

                rootPn.getSubGroup().add(subPn);
            }
        }

        return rootPn;
    }

    /**
     * 패널에 카드목록을 붙인다.
     * @param ctx
     * @param pn
     * @param panelId
     */
    private void attachCardList(CardListGeneratorContext ctx, Panel pn, String panelId) {
        List<PanelCardMapping> cardList = getPanelCardMaping(ctx, panelId);
        int maxCardCnt = ctx.isDisableCardLimit() ? CARD_LIMIT_MAX :
                            (pn.getMaxDpCardCnt() != null ? pn.getMaxDpCardCnt() : CARD_LIMIT_MAX);
        int cardCnt = 0;
        Date stdDt = new Date();

        for (PanelCardMapping panCard : cardList) {
            if(cardCnt >= maxCardCnt)
                break;

            if(!panCard.isVisibleForDate(stdDt))
                continue;

            CardDetail cardDetail = cardDetailService.searchCardDetail(new CardDetailParam(ctx.getTenantId(), panCard.getCardId()));
            if (cardDetail != null) {
                // NOTICE 플러그인 형태로 구현 가능하지 않을까?
                // Segment 프로비저닝 적용
                if (cardDetail.getSegmTypeCd().equals(SGMT_TP_SEGMENT)) {
                    if(!this.isPassSegmentProvision(ctx.getTenantId(), cardDetail.getCardId(), ctx.getSegmentInfo()))
                        continue;
                }

                // ===== Action Before =====
                Card card = cardDetailService.makeCard(cardDetail);
                // ===== Action After  =====

                // FCx 카드 처리 CD05000030
                if (cardDetail.getCardTypeCd().equals(CARDTP_FC)) {
                    String reqMenuId = card.getDatasetProp().getUrlParam().get("topMenuId");

                    String prefMenuId;
                    Matcher m = RX_DT_FC.matcher(cardDetail.getDatasetId());
                    if (m.matches()) {
                        int idx = Integer.parseInt(m.group(1));
                        if(idx < 1)
                            continue;

                        prefMenuId = ctx.getPreferredCategoryInfo().getPreferMenu(reqMenuId, idx - 1);
                    }
                    else
                        continue;

                    if(prefMenuId == null)
                        continue;

                    // String processor #{title} 형태의 문자열을 원하는 것으로 치환
                    String title = card.getTitle();
                    title = title.replaceAll("#\\{category\\}", menuInfoService.getMenuName(prefMenuId, ctx.getLangCd()));
                    card.setTitle(title);
                    card.setLndTitle(title);

                    card.getDatasetProp().getUrlParam().put("menuId", prefMenuId);
                    card.getDatasetProp().getUrlParam().remove("topMenuId");
                }

                pn.getCardList().add(card);
                ++cardCnt;
            }
        }
    }

    /**
     * 캐쉬 처리를 위한 준비
     * - KEY: tenantId+panelId
     * - Evict시기: tenantId+panelId의 구성 데이터가 변경되는 경우
     * @param ctx
     * @param panelId
     * @return
     */
    private List<PanelCardMapping> getPanelCardMaping(CardListGeneratorContext ctx, String panelId) {
        return panelCardInfoManager.getCardListInPanel(ctx.getTenantId(), panelId);
    }

    private void mapPanel(Panel pn, PanelItem panelItem) {
        pn.setId(panelItem.getPanelId());
        pn.setLevel(panelItem.getPanelLevel());
        pn.setMaxDpCardCnt(panelItem.getMaxDpCntCard());
        pn.setName(panelItem.getPanelDesc());
    }

    private boolean isPassSegmentProvision(String tenantId, String cardId, SegmentInfo segmentInfo) {
        CardSegment cardSegment = panelCardInfoManager.getCardSegmentInfo(tenantId, cardId);
        if(cardSegment == null) {
            logger.error("tenant#{}, #card{}에 해당하는 세그먼트 정보가 존재하지 않습니다.", tenantId, cardId);
            return false;
        }

        Set outsdMbrLevel = parseToSet(cardSegment.getOutsdMbrLevelCd());
        Set insdMbrLevel = parseToSet(cardSegment.getInsdMbrLevelCd());
        Set ageClsf = parseToSet(cardSegment.getAgeClsfCd());
        Set categoryBest = parseToSet(cardSegment.getCategoryBest());
        Set mnoCd = parseToSet(cardSegment.getMnoCd());
        Set sex = parseToSet(cardSegment.getSex());

        return outsdMbrLevel.contains(segmentInfo.getOutsdMbrGrdCd()) &&
                insdMbrLevel.contains(segmentInfo.getInsdMbrGrdCd()) &&
                ageClsf.contains(segmentInfo.getAgeClsfCd()) &&
                CollectionUtils.containsAny(categoryBest, segmentInfo.getCategoryBest()) &&
                mnoCd.contains(segmentInfo.getMnoClsfCd()) &&
                sex.contains(segmentInfo.getSex()) &&
                StringUtils.equals(segmentInfo.getDeviceChgYn(), cardSegment.getDeviceChgYn()) &&
                StringUtils.equals(segmentInfo.getNewEntryYn(), cardSegment.getNewEntryYn());
    }
}
