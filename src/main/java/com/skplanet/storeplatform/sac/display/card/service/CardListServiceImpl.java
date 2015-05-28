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

import static com.skplanet.storeplatform.sac.display.common.DisplayJsonUtils.parseToSet;
import static com.skplanet.storeplatform.sac.display.common.constant.DisplayConstants.CARD_SGMT_NA;
import static com.skplanet.storeplatform.sac.display.common.constant.DisplayConstants.CARD_SGMT_TP_ALL;
import static com.skplanet.storeplatform.sac.display.common.constant.DisplayConstants.CARD_SGMT_TP_SEGMENT;
import static com.skplanet.storeplatform.sac.display.common.constant.DisplayConstants.CARD_TYPE_TING;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.skplanet.storeplatform.external.client.shopping.util.StringUtil;
import com.skplanet.storeplatform.framework.core.exception.StorePlatformException;
import com.skplanet.storeplatform.sac.client.product.vo.Card;
import com.skplanet.storeplatform.sac.client.product.vo.Panel;
import com.skplanet.storeplatform.sac.display.cache.service.PanelCardInfoManager;
import com.skplanet.storeplatform.sac.display.cache.vo.CardInfo;
import com.skplanet.storeplatform.sac.display.cache.vo.PanelCardMapping;
import com.skplanet.storeplatform.sac.display.cache.vo.PanelItem;
import com.skplanet.storeplatform.sac.display.cache.vo.SegmentInfo;
import com.skplanet.storeplatform.sac.display.card.util.CardDynamicInfoProcessor;
import com.skplanet.storeplatform.sac.display.card.vo.CardDetail;
import com.skplanet.storeplatform.sac.display.card.vo.CardSegment;
import com.skplanet.storeplatform.sac.display.card.vo.PanelContextParam;
import com.skplanet.storeplatform.sac.display.card.vo.PreferredCategoryInfo;

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
    private PanelCardInfoManager cacheManager;

    private static final int PN_LEVEL_LV2 = 2;
    private static final int PN_LEVEL_LV3 = 3;

    private static final int CARD_LIMIT_MAX = 50;

    @Override
    public Panel listInPanel( String tenantId, String langCd, String panelId, String useGrdCd, String userKey, SegmentInfo sgmtKey,
                             PreferredCategoryInfo preferredCategoryInfo, boolean disableCardLimit) {

        PanelContextParam panelContextParam = new PanelContextParam( tenantId, langCd, panelId, useGrdCd, userKey, sgmtKey, preferredCategoryInfo, disableCardLimit );

        List<PanelItem> panelItems = cacheManager.getPanelList( tenantId, panelId );

        if( CollectionUtils.isEmpty(panelItems) ) throw new StorePlatformException("SAC_DSP_0009");

        Panel rootPn = new Panel();
        rootPn.setSubGroup(new ArrayList<Panel>());
        rootPn.setCardList(new ArrayList<Card>());

        CardDynamicInfoProcessor processor = new CardDynamicInfoProcessor( tenantId, cardDetailService );

        for (PanelItem panelItem : panelItems) {
        	if(panelItem.getPanelLevel() == PN_LEVEL_LV2) {
                panelContextParam.setCurrentPanelLevel(PN_LEVEL_LV2);
                mapPanel(rootPn, panelItem);
                attachCardList(panelContextParam, rootPn, panelItem.getPanelId(), processor);

        	} else if (panelItem.getPanelLevel() == PN_LEVEL_LV3) {
                panelContextParam.setCurrentPanelLevel(PN_LEVEL_LV3);
                Panel subPn = new Panel();
                subPn.setCardList(new ArrayList<Card>());

                mapPanel(subPn, panelItem);
                attachCardList(panelContextParam, subPn, panelItem.getPanelId(), processor);

                rootPn.getSubGroup().add(subPn);
            }
        }

        processor.execute(userKey);

        return rootPn;
    }

    /**
     * 패널에 카드목록을 붙인다.
     * @param contextParam
     * @param parentPanel
     * @param panelId
     * @param processor
     */
    private void attachCardList( PanelContextParam contextParam, Panel parentPanel, String panelId, CardDynamicInfoProcessor processor ) {

    	List<PanelCardMapping> cardList = getPanelCardMaping(contextParam, panelId);

    	if( CollectionUtils.isEmpty(cardList) ) return;

        int maxCardCnt = contextParam.isDisableCardLimit() ? CARD_LIMIT_MAX :
                            (parentPanel.getMaxDpCardCnt() != null ? parentPanel.getMaxDpCardCnt() : CARD_LIMIT_MAX ); // 최대 표시 카드 수량
        int cardCnt    = 0;    // 노출중인 카드 수량

        Date stdDt = new Date();

        /**
         * # moreYn 처리용 로직
         * maxCardCnt = maxCardCnt + 1
         * [Process loop]
         * if(cardCnt == maxCardCnt)
         *   moreYn := "Y"
         *   cardList.removeLast()
         * else
         *   moreYn := "N"
         */
        if( ! contextParam.isDisableCardLimit() ) {
            ++maxCardCnt;
        }

        for( PanelCardMapping panelCard : cardList ) {

        	if( cardCnt >= maxCardCnt ) break;

            if( ! panelCard.isVisibleForDate(stdDt) ) continue;

            CardInfo cardInfo = cacheManager.getCardInfo(contextParam.getTenantId(), panelCard.getCardId());

            if( isProvisioned(cardInfo, contextParam) ) continue;

            CardDetail cardDetail = new CardDetail();
            BeanUtils.copyProperties(cardInfo, cardDetail);

            Card card = cardDetailService.makeCard( cardDetail, contextParam.getPreferredCategory(), contextParam.getLangCd() );

            if (card == null) continue;

            processor.addCard(card);

            parentPanel.getCardList().add(card);

            ++cardCnt;

        }

        if( ! contextParam.isDisableCardLimit() ) {
            if(cardCnt == maxCardCnt) {
                parentPanel.setMoreYn("Y");
                parentPanel.getCardList().remove(cardCnt - 1);
            } else {
            	parentPanel.setMoreYn("N");
            }
        }
    }

    private boolean isProvisioned( CardInfo cardInfo, PanelContextParam param ) {

        if( cardInfo == null ) return true;

        // 이용자 정보가 없을 경우, 세그먼트 타입이 '전체'가 아닐 경우 filtering
        if( StringUtil.isEmpty(param.getUserKey()) &&  ! CARD_SGMT_TP_ALL.equals(cardInfo.getSegmTypeCd()) ) return true;

        // 카드 이용등급 제어 (입력등급보다 낮을 경우 filtering)
        if( cardInfo.getCardUseGrdCd().compareToIgnoreCase(param.getUseGrdCd()) > 0 ) return true;

        // 팅카드 제어
        if( CARD_TYPE_TING.equals(cardInfo.getCardTypeCd()) && ! "Y".equalsIgnoreCase(param.getSegmentInfo().getTingYn()) ) return true;

        // TEST MDN 제어
        if( "Y".equalsIgnoreCase(cardInfo.getTestMdnYn()) && ! "Y".equalsIgnoreCase(param.getSegmentInfo().getTestMdnYn()) ) return true;

        // Segment 제어
        if( CARD_SGMT_TP_SEGMENT.equals(cardInfo.getSegmTypeCd()) && isSegmentProvisioned( param.getTenantId(), cardInfo.getCardId(), param.getSegmentInfo()) ) return true;

        return false;

    }

    /**
     * 캐쉬 처리를 위한 준비
     * - KEY: tenantId+panelId
     * - Evict시기: tenantId+panelId의 구성 데이터가 변경되는 경우
     * @param ctx
     * @param panelId
     * @return
     */
    private List<PanelCardMapping> getPanelCardMaping(PanelContextParam ctx, String panelId) {
        return cacheManager.getCardListInPanel(ctx.getTenantId(), panelId);
    }

    private void mapPanel(Panel pn, PanelItem panelItem) {
        pn.setId(panelItem.getPanelId());
        pn.setLevel(panelItem.getPanelLevel());
        pn.setMaxDpCardCnt(panelItem.getMaxDpCntCard());
        pn.setName(panelItem.getPanelDesc());
    }

    @SuppressWarnings({ "unchecked", "rawtypes" })
    private boolean isSegmentProvisioned( String tenantId, String cardId, SegmentInfo segmentInfo ) {

    	CardSegment cardSegment = cacheManager.getCardSegmentInfo( tenantId, cardId );

    	if( cardSegment == null ) {
            logger.error("tenant#{}, #card{}에 해당하는 세그먼트 정보가 존재하지 않습니다.", tenantId, cardId);
            return true;
        }

        Set<String> sgmtMbrLvl = new HashSet<String>(2);
        sgmtMbrLvl.add(segmentInfo.getInsdMbrGrdCd());
        sgmtMbrLvl.add(segmentInfo.getOutsdMbrGrdCd());

        Set outsdMbrLevel = parseToSet(cardSegment.getOutsdMbrLevelCd());
        Set insdMbrLevel  = parseToSet(cardSegment.getInsdMbrLevelCd());
        Set<String> cardMbrLvl = new HashSet<String>(outsdMbrLevel.size() + insdMbrLevel.size());
        cardMbrLvl.addAll(outsdMbrLevel);
        cardMbrLvl.addAll(insdMbrLevel);

        Set cardAgeClsf      = parseToSet(cardSegment.getAgeClsfCd());
        Set cardCategoryBest = parseToSet(cardSegment.getCategoryBest());
        Set cardMnoCd        = parseToSet(cardSegment.getMnoCd());
        Set cardSex          = parseToSet(cardSegment.getSex());


        if( ! cardMbrLvl.contains(CARD_SGMT_NA)       && ! CollectionUtils.containsAny(cardMbrLvl, sgmtMbrLvl)                          ) return true;
        if( ! cardAgeClsf.contains(CARD_SGMT_NA)      && ! cardAgeClsf.contains(segmentInfo.getAgeClsfCd())                             ) return true;
        if( ! cardCategoryBest.contains(CARD_SGMT_NA) && ! CollectionUtils.containsAny(cardCategoryBest, segmentInfo.getCategoryBest()) ) return true;
        if( ! cardMnoCd.contains(CARD_SGMT_NA)        && ! cardMnoCd.contains(segmentInfo.getMnoClsfCd())                               ) return true;
        if( ! cardSex.contains(CARD_SGMT_NA)          && ! cardSex.contains(segmentInfo.getSex())                                       ) return true;

        if( ! cardSegment.getDeviceChgYn().equals(CARD_SGMT_NA) && ! cardSegment.getDeviceChgYn().equals(segmentInfo.getDeviceChgYn())  ) return true;
        if( ! cardSegment.getNewEntryYn().equals(CARD_SGMT_NA)  && ! cardSegment.getNewEntryYn().equals(segmentInfo.getNewEntryYn())    ) return true;

        return false;

    }
}
