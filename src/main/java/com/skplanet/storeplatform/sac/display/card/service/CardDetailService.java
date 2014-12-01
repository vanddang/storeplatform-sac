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

import java.util.List;

import com.skplanet.storeplatform.sac.client.product.vo.Card;
import com.skplanet.storeplatform.sac.display.card.vo.CardDetail;
import com.skplanet.storeplatform.sac.display.card.vo.CardDetailParam;
import com.skplanet.storeplatform.sac.display.card.vo.CardDynamicInfo;
import com.skplanet.storeplatform.sac.display.card.vo.PreferredCategoryInfo;

/**
 * Class 설명
 *
 * Updated on : 2014. 10. 8.
 * Updated by : 양해엽, SK 플래닛.
 */
public interface CardDetailService {

	public CardDetail searchCardDetail(CardDetailParam param);

	public String getExpoYnInPanel(final String tenantId, final String cardId);

	public Card makeCard(final CardDetail cardDetail);

	public Card makeCard(final CardDetail cardDetail, final PreferredCategoryInfo preferredCategoryInfo, final String langCd);

    public List<CardDynamicInfo> getCardDynamicInfo(final String tenantId, final String userKey, final List<String> cardList);
}
