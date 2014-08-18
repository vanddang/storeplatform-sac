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

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.common.Menu;
import com.skplanet.storeplatform.sac.display.common.constant.DisplayConstants;
import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.skplanet.storeplatform.framework.core.exception.StorePlatformException;
import com.skplanet.storeplatform.sac.client.display.vo.other.OtherPartProductReq;
import com.skplanet.storeplatform.sac.client.display.vo.other.OtherPartProductRes;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.common.Identifier;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.common.Price;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.common.Title;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.product.Point;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.product.Product;
import com.skplanet.storeplatform.sac.common.header.vo.SacRequestHeader;
import com.skplanet.storeplatform.sac.display.common.service.MemberBenefitService;
import com.skplanet.storeplatform.sac.display.other.service.OtherPartProductService;
import com.skplanet.storeplatform.sac.display.other.vo.PartProduct;
import com.skplanet.storeplatform.sac.display.response.CommonMetaInfoGenerator;

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

    @Autowired
    private MemberBenefitService benefitService;

    @Autowired
    private CommonMetaInfoGenerator metaInfoGenerator;

    @RequestMapping(value = "/partProduct/list/v1", method = RequestMethod.GET)
    @ResponseBody
    public OtherPartProductRes getPartProductInfoList(@Validated OtherPartProductReq req, SacRequestHeader header) {
    	String aid = req.getAid();
    	String partProdId = req.getPartProdId();
        String tenantId = header.getTenantHeader().getTenantId();
        String langCd = header.getTenantHeader().getLangCd();

        List<PartProduct> partProductInfoList = this.otherPartProductService.getPartProductList(aid, partProdId, tenantId, langCd);

        if (CollectionUtils.isEmpty(partProductInfoList)) {
            throw new StorePlatformException("SAC_DSP_0009");
        }

        OtherPartProductRes ppRes = new OtherPartProductRes();
        ppRes.setProductList(new ArrayList<Product>());

        List<Point> pointList = null;
        for (PartProduct pp : partProductInfoList) {
            Product ppro = new Product();
            ppro.setIdentifierList(Arrays.asList(new Identifier("channel", pp.getProdId()), new Identifier("parentChannel", pp.getParentProdId())));
            ppro.setTitle(new Title(pp.getProdNm()));
            ppro.setPrice(new Price(pp.getProdAmt()));
            ppro.setProdKind(pp.getProdKind());
            ppro.setProdCase(pp.getProdCase());

            if (ppRes.getParentProduct() == null) {
                ppRes.setParentProduct(new Product());
                ppRes.getParentProduct().setMenuList(new ArrayList<Menu>());
                ppRes.getParentProduct().getMenuList().add(new Menu(pp.getParentTopMenuId(), pp.getParentTopMenuNm(), DisplayConstants.DP_MENU_TOPCLASS_TYPE));
                ppRes.getParentProduct().getMenuList().add(new Menu(pp.getParentMenuId(), pp.getParentMenuNm(), null));
            }

            // 부모상품에 대한 마일리지 정책 적용
            if(pointList == null)
            	pointList = metaInfoGenerator.generateMileage(benefitService.getMileageInfo(tenantId, pp.getParentTopMenuId(), pp.getParentProdId(), pp.getProdAmt()));

            ppro.setPointList(pointList);

            ppRes.getProductList().add(ppro);
        }

        return ppRes;
    }

}
