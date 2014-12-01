/*
 * Copyright (c) 2013 SK planet.
 * All right reserved.
 *
 * This software is the confidential and proprietary information of SK planet.
 * You shall not disclose such Confidential Information and
 * shall use it only in accordance with the terms of the license agreement
 * you entered into with SK planet.
 */

package com.skplanet.storeplatform.sac.display.card.controller;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.skplanet.storeplatform.framework.core.exception.StorePlatformException;
import com.skplanet.storeplatform.sac.client.display.vo.card.CardDetailSacReq;
import com.skplanet.storeplatform.sac.client.display.vo.card.CardDetailSacRes;
import com.skplanet.storeplatform.sac.client.product.vo.Card;
import com.skplanet.storeplatform.sac.common.header.vo.SacRequestHeader;
import com.skplanet.storeplatform.sac.display.card.service.CardDetailService;
import com.skplanet.storeplatform.sac.display.cache.vo.CardDetail;
import com.skplanet.storeplatform.sac.display.card.vo.CardDetailParam;

/**
 * Class 설명
 *
 * Updated on : 2014. 10. 8.
 * Updated by : 양해엽, SK 플래닛.
 */
@Controller
public class CardController {

	@Autowired
	private CardDetailService cardDetailService;

	@RequestMapping(value = "/display/card/detail/v1", method = RequestMethod.POST)
	@ResponseBody
	public CardDetailSacRes getCardDetail(@RequestBody @Validated CardDetailSacReq req,	SacRequestHeader header) {

		CardDetailParam param = new CardDetailParam();
		param.setTenantId(header.getTenantHeader().getTenantId());
		param.setCardId(req.getId());
		param.setUserKey(req.getUserKey());

		CardDetail cardDetail = this.cardDetailService.searchCardDetail(param);
		if (cardDetail == null) {
			throw new StorePlatformException("SAC_DSP_0009");
		}

		Card card = this.cardDetailService.makeCard(cardDetail);

		/* expoYnInPanel : Controller를 통한 직접 카드 조회시만 사용 */
		card.setExpoYnInPanel(this.cardDetailService.getExpoYnInPanel(param));

		CardDetailSacRes res = new CardDetailSacRes();
		BeanUtils.copyProperties(card, res);

		return res;

	}

}
