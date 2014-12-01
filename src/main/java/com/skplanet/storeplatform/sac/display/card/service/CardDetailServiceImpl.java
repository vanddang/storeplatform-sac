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

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang3.StringUtils;
import org.codehaus.jackson.map.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.skplanet.storeplatform.framework.core.persistence.dao.CommonDAO;
import com.skplanet.storeplatform.sac.client.product.vo.Card;
import com.skplanet.storeplatform.sac.client.product.vo.DatasetProp;
import com.skplanet.storeplatform.sac.client.product.vo.EtcProp;
import com.skplanet.storeplatform.sac.client.product.vo.Stats;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.common.Source;
import com.skplanet.storeplatform.sac.common.util.PartialProcessor;
import com.skplanet.storeplatform.sac.common.util.PartialProcessorHandler;
import com.skplanet.storeplatform.sac.display.cache.service.PanelCardInfoManager;
import com.skplanet.storeplatform.sac.display.cache.vo.CardInfo;
import com.skplanet.storeplatform.sac.display.card.vo.CardDetail;
import com.skplanet.storeplatform.sac.display.card.vo.CardDetailParam;
import com.skplanet.storeplatform.sac.display.card.vo.CardDynamicInfo;
import com.skplanet.storeplatform.sac.display.card.vo.InjtVar;
import com.skplanet.storeplatform.sac.display.card.vo.PreferredCategoryInfo;
import com.skplanet.storeplatform.sac.display.common.constant.DisplayConstants;
import com.skplanet.storeplatform.sac.display.common.service.menu.MenuInfoService;
import com.skplanet.storeplatform.sac.display.response.CommonMetaInfoGenerator;


/**
 * Class 설명
 *
 * Updated on : 2014. 10. 8.
 * Updated by : 양해엽, SK 플래닛
 */
@Service
public class CardDetailServiceImpl implements CardDetailService {

    public static final int CARD_WND_SIZE = 20;

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	@Qualifier("sac")
	private CommonDAO commonDAO;

	@Autowired
	private CommonMetaInfoGenerator commonGenerator;

    @Autowired
    private PanelCardInfoManager panelCardInfoManager;

    @Autowired
    private MenuInfoService menuInfoService;


    private static final String CARDTP_FC = "CD05000030";

    private static final Pattern RX_DT_FC = Pattern.compile("DT200003(\\d\\d)");


	@Override
	public CardDetail searchCardDetail(CardDetailParam cardDetailParam) {

		CardInfo cardInfo = getCardInfo(cardDetailParam);

		if (cardInfo == null) return null;

		CardDetail cardDetail = new CardDetail();
		BeanUtils.copyProperties(cardInfo, cardDetail);

		List<CardDynamicInfo> dynamicInfoList = getCardDynamicInfo(cardDetailParam.getTenantId(), cardDetailParam.getUserKey(), Arrays.asList(cardDetailParam.getCardId()));
        if (dynamicInfoList.size() > 0) {
            CardDynamicInfo dynamicInfo = dynamicInfoList.get(0);
            cardDetail.setLikeYn(dynamicInfo.getLikeYn());
            cardDetail.setCntShar(dynamicInfo.getCntShar());
            cardDetail.setCntLike(dynamicInfo.getCntLike());
        }

        return cardDetail;
	}

	@Override
	public CardInfo getCardInfo(CardDetailParam cardDetailParam) {

		return panelCardInfoManager.getCardInfo(cardDetailParam.getTenantId(), cardDetailParam.getCardId());
	}

	@Override
	public CardInfo getCardInfo(final String tenantId, final String cardId) {

		return panelCardInfoManager.getCardInfo(tenantId, cardId);
	}

    @Override
    public List<CardDynamicInfo> getCardDynamicInfo(final String tenantId, final String userKey, final List<String> cardList) {
        final ArrayList<CardDynamicInfo> rtn = new ArrayList<CardDynamicInfo>(cardList.size());

        PartialProcessor.process(cardList, new PartialProcessorHandler<String>() {
            @Override
            public String processPaddingItem() {
                return StringUtils.EMPTY;
            }

            @Override
            public void processPartial(List<String> partialList) {
                HashMap<String, Object> req = new HashMap<String, Object>();
                req.put("tenantId", tenantId);
                req.put("userKey", userKey);
                req.put("cardList", partialList);
                rtn.addAll(commonDAO.queryForList("CardDetail.getCardDynamicInfo", req, CardDynamicInfo.class));
            }
        }, CARD_WND_SIZE);

        return rtn;
    }

    @Override
	public String getExpoYnInPanel(final String tenantId, final String cardId) {

    	HashMap<String, Object> req = new HashMap<String, Object>();
        req.put("tenantId", tenantId);
        req.put("cardId", tenantId);

		return commonDAO.queryForObject("CardDetail.getExpoYnInPanel", req, String.class);
	}

	@Override
	public Card makeCard(final CardDetail cardDetail) {

		if (cardDetail == null) return null;

		Card card = new Card();

		card.setId(cardDetail.getCardId());
		card.setTypeCd(cardDetail.getCardTypeCd());
		card.setTitle(cardDetail.getCardTitle());
		card.setDesc(cardDetail.getCardDesc());
		card.setLayout(StringUtils.defaultString(cardDetail.getCardLayOut()));
		card.setCardImgType(StringUtils.defaultString(cardDetail.getCardImgType()));

		List<Source> sourceList = new ArrayList<Source>();
		if (StringUtils.isNotBlank(cardDetail.getCardImgPath())) {
			sourceList.add(commonGenerator.generateSource(DisplayConstants.DP_SOURCE_TYPE_CARDIMG,
					cardDetail.getCardImgPath(), cardDetail.getCardImgRatio()));
		}
		if (StringUtils.isNotBlank(cardDetail.getLndImgPath())) {
			sourceList.add(commonGenerator.generateSource(DisplayConstants.DP_SOURCE_TYPE_LANDIMG,
					cardDetail.getLndImgPath(), cardDetail.getLndImgRatio()));
		}
		card.setSourceList(sourceList);

		card.setLndTitle(cardDetail.getLndTitle());
		card.setLndDesc(cardDetail.getLndDesc());
		card.setLndLayout(StringUtils.defaultString(cardDetail.getLndLayOut()));
		card.setMenuId(StringUtils.defaultString(cardDetail.getMenuId()));
		card.setLikeYn(cardDetail.getLikeYn());
		card.setShareUrl(StringUtils.defaultString(cardDetail.getShareUrl()).replaceAll("#\\{cardId\\}",cardDetail.getCardId()));

		card.setEtcProp(makeEtcProp(cardDetail));
		card.setDatasetProp(makeDatasetProp(cardDetail));
		card.setStats(makeStats(cardDetail));

		return card;
	}

	@Override
	public Card makeCard(final CardDetail cardDetail, final PreferredCategoryInfo preferredCategoryInfo, final String langCd) {

		if (cardDetail == null) return null;

		Card card = makeCard(cardDetail);

		makeTitleParam(card, preferredCategoryInfo, langCd);

		return card;
	}

	private void makeTitleParam(Card card, final PreferredCategoryInfo preferredCategoryInfo, final String langCd) {

		card.setTitleParam(new HashMap<String, String>());

		if (preferredCategoryInfo == null ) return;

		// FCx 카드 처리 CD05000030
		if (!StringUtils.equals(card.getTypeCd(), CARDTP_FC)) return;


        String reqMenuId = card.getDatasetProp().getUrlParam().get("topMenuId");

        String prefMenuId;
        Matcher m = RX_DT_FC.matcher(card.getDatasetProp().getId());
        if (m.matches()) {
            int idx = Integer.parseInt(m.group(1));
            if(idx < 1)
                return;

            prefMenuId = preferredCategoryInfo.getPreferMenu(reqMenuId, idx - 1);
        }
        else
            return;

        if(prefMenuId == null)
            return;

        /**
         * FC1, FC2, FC3 card_title에 #{category}만 현재는 존재
         */
        String title = card.getTitle();
        if ( title.contains("#{category}") ) {
            Map<String, String> titleParam = new HashMap<String, String>();
            titleParam.put("category", menuInfoService.getMenuName(prefMenuId, langCd));

            card.setTitleParam(titleParam);

            card.getDatasetProp().getUrlParam().put("menuId", prefMenuId);
            card.getDatasetProp().getUrlParam().remove("topMenuId");
        }

        return;
	}


	private EtcProp makeEtcProp(final CardDetail cardDetail) {
		EtcProp etcProp = new EtcProp();

		etcProp.setExpoYnCardTitle(cardDetail.getExpoYnCardTitle());
		etcProp.setExpoYnCardDesc(cardDetail.getExpoYnCardDesc());
		etcProp.setExpoYnCardLike(cardDetail.getExpoYnCardLike());
		etcProp.setExpoYnCardShar(cardDetail.getExpoYnCardShar());
		etcProp.setExpoYnCardDcPrvPrice(cardDetail.getExpoYnCardDcPrvPrice());
		etcProp.setExpoYnCardDcRate(cardDetail.getExpoYnCardDcRate());
		etcProp.setExpoYnCardImg(cardDetail.getExpoYnCardImg());
		etcProp.setExpoYnCardItemNo(cardDetail.getExpoYnCardItemNo());
		etcProp.setExpoYnCardRecomReason(cardDetail.getExpoYnCardRecomReason());
		etcProp.setExpoYnCardAbstrTm(cardDetail.getExpoYnCardAbstrTm());
		etcProp.setExpoYnLndTitle(cardDetail.getExpoYnLndTitle());
		etcProp.setExpoYnLndDesc(cardDetail.getExpoYnLndDesc());
		etcProp.setExpoYnLndLayout(cardDetail.getExpoYnLndLayout());
		etcProp.setExpoYnLndItemNo(cardDetail.getExpoYnLndItemNo());
		etcProp.setExpoYnLndDcPrvPrice(cardDetail.getExpoYnLndDcPrvPrice());
		etcProp.setExpoYnLndDcRate(cardDetail.getExpoYnLndDcRate());
		etcProp.setExpoYnLndImg(cardDetail.getExpoYnLndImg());
		etcProp.setExpoYnLndImgOverlay(cardDetail.getExpoYnLndImgOverlay());

		return etcProp;
	}

	private DatasetProp makeDatasetProp(final CardDetail cardDetail) {
		DatasetProp datasetProp = new DatasetProp();

		datasetProp.setId(StringUtils.defaultString(cardDetail.getDatasetId()));
		datasetProp.setTitle(StringUtils.defaultString(cardDetail.getTitle()));
		datasetProp.setUrl(StringUtils.defaultString(cardDetail.getTenantUrl()));
		datasetProp.setSmartOfrListId(StringUtils.defaultString(cardDetail.getSmartOfrListId()));

		datasetProp.setUrlParam(makeUrlParam(cardDetail));

		datasetProp.setItemLndUrl(StringUtils.defaultString(cardDetail.getItemLndUrl()));

		return datasetProp;
	}

	private Stats makeStats(final CardDetail cardDetail) {
		Stats stats = new Stats();

		stats.setCntLike(cardDetail.getCntLike());
		stats.setCntShar(cardDetail.getCntShar());

		return stats;
	}

	private Map<String, String> makeUrlParam(final CardDetail cardDetail) {
		Map<String, String> urlParam = new HashMap<String, String>();

		/**
		 * Card의 InjtVar가 DataSet의 InjtVar를 Override하는 방식.
		 */
		makeUrlParamFromInjtVar(urlParam, cardDetail.getDatasetInjtVar());
		makeUrlParamFromInjtVar(urlParam, cardDetail.getCardInjtVar());

		return urlParam;
	}

	private void makeUrlParamFromInjtVar(Map<String, String> urlParam, final String injtVar) {

		ObjectMapper mapper = new ObjectMapper();

		logger.debug("injtVar={}",injtVar);

		if (StringUtils.isBlank(injtVar)) return;

		InjtVar injtVarObj = null;
		try {
			injtVarObj = mapper.readValue(injtVar, InjtVar.class);
		} catch (Exception e) {
			logger.error("주입변수 언마셜링 중 오류가 발생하였습니다.\n", e);
			return;
		}
		logger.debug("injtVarObj={}",injtVarObj.toString());

		Map<String, String> mapValue = injtVarObj.getValue();
		if (mapValue != null) {
			for (String key : mapValue.keySet()) {
				String value = mapValue.get(key);
				urlParam.put(key, value);
			}
		}
	}


}

