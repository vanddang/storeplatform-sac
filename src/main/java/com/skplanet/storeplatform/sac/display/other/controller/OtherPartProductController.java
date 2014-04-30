/*
 * Copyright (c) 2013 SK planet.
 * All right reserved.
 *
 * This software is the confidential and proprietary information of SK planet.
 * You shall not disclose such Confidential Information and
 * shall use it only in accordance with the terms of the license agreement
 * you entered into with SK planet.
 */
package com.skplanet.storeplatform.sac.display.other.controller;

import com.skplanet.storeplatform.framework.core.exception.StorePlatformException;
import com.skplanet.storeplatform.sac.client.display.vo.other.OtherPartProductReq;
import com.skplanet.storeplatform.sac.client.display.vo.other.OtherPartProductRes;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.common.Identifier;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.common.Price;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.common.Title;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.product.Product;
import com.skplanet.storeplatform.sac.common.header.vo.SacRequestHeader;
import com.skplanet.storeplatform.sac.display.other.service.OtherPartProductService;
import com.skplanet.storeplatform.sac.display.other.vo.PartProduct;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * <p>
 * DESC
 * </p>
 * Updated on : 2014. 03. 25 Updated by : 정희원, SK 플래닛.
 */
@Controller
@RequestMapping("/display/other")
public class OtherPartProductController {

    private static final Logger logger = LoggerFactory.getLogger(OtherPartProductController.class);

    @Autowired
    private OtherPartProductService otherPartProductService;

    @RequestMapping(value = "/partProduct/list/v1", method = RequestMethod.GET)
    @ResponseBody
    public OtherPartProductRes getPartProductInfoList(@Validated OtherPartProductReq req, SacRequestHeader header) {
        List<PartProduct> partProductInfoList = otherPartProductService.getPartProductList(req.getAid(), header.getTenantHeader().getTenantId(), header.getTenantHeader().getLangCd());

        if(partProductInfoList == null || partProductInfoList.size() == 0) {
            throw new StorePlatformException("SAC_DSP_0009");
        }

        OtherPartProductRes ppRes = new OtherPartProductRes();
        ppRes.setProductList(new ArrayList<Product>());

        for (PartProduct pp : partProductInfoList) {
            Product ppro = new Product();
            ppro.setIdentifierList(Arrays.asList(new Identifier("channel", pp.getProdId()), new Identifier("parentChannel", pp.getParentProdId())));
            ppro.setTitle(new Title(pp.getProdNm()));
            ppro.setPrice(new Price(pp.getProdAmt()));
            ppro.setProdKind(pp.getProdKind());
            ppro.setProdCase(pp.getProdCase());
            ppRes.getProductList().add(ppro);
        }

        return ppRes;
    }

}
