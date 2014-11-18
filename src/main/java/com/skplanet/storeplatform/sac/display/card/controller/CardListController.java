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

import com.skplanet.storeplatform.sac.client.display.vo.card.CardListInPanelReq;
import com.skplanet.storeplatform.sac.client.product.vo.Panel;
import com.skplanet.storeplatform.sac.common.header.vo.SacRequestHeader;
import com.skplanet.storeplatform.sac.display.card.service.CardListService;
import com.skplanet.storeplatform.sac.display.card.vo.PreferredCategoryInfo;
import com.skplanet.storeplatform.sac.display.cache.vo.SegmentInfo;
import org.apache.commons.lang3.BooleanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.validation.Valid;

/**
 * <p>
 * CardController
 * </p>
 * Updated on : 2014. 10. 08 Updated by : 정희원, SK 플래닛.
 */
@Controller
public class CardListController {

    @Autowired
    private CardListService cardListService;

    @RequestMapping(value = "/display/card/listInPanel/v1", method = RequestMethod.POST)
    @ResponseBody
    public Panel listInPanel(@RequestBody @Valid CardListInPanelReq req, SacRequestHeader header) {
        String tenantId = header.getTenantHeader().getTenantId();
        String langCd = header.getTenantHeader().getLangCd();
        SegmentInfo segmentInfo = new SegmentInfo(req.getSegment());

        return cardListService.listInPanel(tenantId, langCd, req.getPanelId(),
                segmentInfo, new PreferredCategoryInfo(req.getPreferredCategoryList()),
                BooleanUtils.toBoolean(req.getDisableCardLimit()));
    }
}
