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
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.codehaus.jackson.map.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.skplanet.storeplatform.framework.core.persistence.dao.CommonDAO;
import com.skplanet.storeplatform.sac.client.product.vo.Card;
import com.skplanet.storeplatform.sac.client.product.vo.DatasetProp;
import com.skplanet.storeplatform.sac.client.product.vo.EtcProp;
import com.skplanet.storeplatform.sac.client.product.vo.Stats;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.common.Source;
import com.skplanet.storeplatform.sac.display.card.vo.CardDetail;
import com.skplanet.storeplatform.sac.display.card.vo.CardDetailParam;
import com.skplanet.storeplatform.sac.display.card.vo.InjtVar;
import com.skplanet.storeplatform.sac.display.common.constant.DisplayConstants;
import com.skplanet.storeplatform.sac.display.response.CommonMetaInfoGenerator;


/**
 * Class 설명
 *
 * Updated on : 2014. 10. 8.
 * Updated by : 양해엽, SK 플래닛
 */
@Service
public class CardDetailServiceImpl implements CardDetailService {

	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	@Qualifier("sac")
	private CommonDAO commonDAO;

	@Autowired
	private CommonMetaInfoGenerator commonGenerator;

	@Override
	public CardDetail searchCardDetail(CardDetailParam cardDetailParam) {

		return commonDAO.queryForObject("CardDetail.getCard", cardDetailParam, CardDetail.class);
	}

	@Override
	public String getExpoYnInPanel(CardDetailParam cardDetailParam) {

		return commonDAO.queryForObject("CardDetail.getExpoYnInPanel", cardDetailParam, String.class);
	}

	@Override
	public Card makeCard(CardDetail cardDetail) {

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
		card.setShareUrl(cardDetail.getShareUrl());

		card.setEtcProp(makeEtcProp(cardDetail));
		card.setDatasetProp(makeDatasetProp(cardDetail));
		card.setStats(makeStats(cardDetail));

		return card;
	}

	private EtcProp makeEtcProp(CardDetail cardDetail) {
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

	private DatasetProp makeDatasetProp(CardDetail cardDetail) {
		DatasetProp datasetProp = new DatasetProp();

		datasetProp.setId(StringUtils.defaultString(cardDetail.getDatasetId()));
		datasetProp.setTitle(StringUtils.defaultString(cardDetail.getTitle()));
		datasetProp.setUrl(StringUtils.defaultString(cardDetail.getTenantUrl()));
		datasetProp.setSmartOfrListId(StringUtils.defaultString(cardDetail.getSmartOfrListId()));

		datasetProp.setUrlParam(makeUrlParam(cardDetail));

		datasetProp.setItemLndUrl(StringUtils.defaultString(cardDetail.getItemLndUrl()));

		return datasetProp;
	}

	private Stats makeStats(CardDetail cardDetail) {
		Stats stats = new Stats();

		stats.setCntLike(cardDetail.getCntLike());
		stats.setCntShar(cardDetail.getCntShar());

		return stats;
	}

	private Map<String, String> makeUrlParam(CardDetail cardDetail) {
		Map<String, String> urlParam = new HashMap<String, String>();

		/**
		 * Card의 InjtVar가 DataSet의 InjtVar를 Override하는 방식.
		 */
		makeUrlParamFromInjtVar(urlParam, cardDetail.getDatasetInjtVar());
		makeUrlParamFromInjtVar(urlParam, cardDetail.getCardInjtVar());

		return urlParam;
	}

	private void makeUrlParamFromInjtVar(Map<String, String> urlParam, String injtVar) {

		ObjectMapper mapper = new ObjectMapper();

		logger.debug("injtVar={}",injtVar);

		if (StringUtils.isBlank(injtVar)) return;

		InjtVar injtVarObj = null;
		try {
			injtVarObj = mapper.readValue(injtVar, InjtVar.class);
		} catch (Exception e) {
//			throw new StorePlatformException("SAC_DSP_0001", injtVar);
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

