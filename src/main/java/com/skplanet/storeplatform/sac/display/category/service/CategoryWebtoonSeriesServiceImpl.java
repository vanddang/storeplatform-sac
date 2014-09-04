/*
 * Copyright (c) 2013 SK planet.
 * All right reserved.
 *
 * This software is the confidential and proprietary information of SK planet.
 * You shall not disclose such Confidential Information and
 * shall use it only in accordance with the terms of the license agreement
 * you entered into with SK planet.
 */
package com.skplanet.storeplatform.sac.display.category.service;

import com.skplanet.storeplatform.framework.core.persistence.dao.CommonDAO;
import com.skplanet.storeplatform.sac.client.display.vo.category.CategoryWebtoonSeriesSacReq;
import com.skplanet.storeplatform.sac.client.display.vo.category.CategoryWebtoonSeriesSacRes;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.common.CommonResponse;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.common.Date;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.common.Identifier;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.product.Layout;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.product.Product;
import com.skplanet.storeplatform.sac.common.header.vo.DeviceHeader;
import com.skplanet.storeplatform.sac.common.header.vo.SacRequestHeader;
import com.skplanet.storeplatform.sac.common.header.vo.TenantHeader;
import com.skplanet.storeplatform.sac.display.common.constant.DisplayConstants;
import com.skplanet.storeplatform.sac.display.common.service.DisplayCommonService;
import com.skplanet.storeplatform.sac.display.meta.service.MetaInfoService;
import com.skplanet.storeplatform.sac.display.meta.vo.MetaInfo;
import com.skplanet.storeplatform.sac.display.meta.vo.ProductBasicInfo;
import com.skplanet.storeplatform.sac.display.response.CommonMetaInfoGenerator;
import com.skplanet.storeplatform.sac.display.response.EbookComicGenerator;
import com.skplanet.storeplatform.sac.display.response.ResponseInfoGenerateFacade;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 웹툰 회차별 목록 조회.
 * 
 * Updated on : 2014. 2. 6. Updated by : 이승훈, 엔텔스.
 */
@Service
public class CategoryWebtoonSeriesServiceImpl implements CategoryWebtoonSeriesService {

	@Autowired
	@Qualifier("sac")
	private CommonDAO commonDAO;

	@Autowired
	private CommonMetaInfoGenerator commonGenerator;

	@Autowired
	private ResponseInfoGenerateFacade responseInfoGenerateFacade;

	@Autowired
	private MetaInfoService metaInfoService;

	@Autowired
	private DisplayCommonService displayCommonService;

	@Autowired
	private EbookComicGenerator ebookComicGenerator;

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.skplanet.storeplatform.sac.display.category.service.CategorySpecificVodService#CategoryWebtoonSeriesSacRes
	 * (com.skplanet.storeplatform.sac.client.display.vo.category.CategoryWebtoonSeriesSacReq,
	 * com.skplanet.storeplatform.sac.common.header.vo.SacRequestHeader)
	 */
	@Override
	public CategoryWebtoonSeriesSacRes getCategoryWebtoonSeriesList(CategoryWebtoonSeriesSacReq req,
			SacRequestHeader header) {

		TenantHeader tenantHeader = header.getTenantHeader();
		DeviceHeader deviceHeader = header.getDeviceHeader();

		req.setTenantId(tenantHeader.getTenantId());
		req.setLangCd(tenantHeader.getLangCd());
		req.setDeviceModelCd(deviceHeader.getModel());

		CategoryWebtoonSeriesSacRes res = new CategoryWebtoonSeriesSacRes();
		CommonResponse commonResponse = new CommonResponse();
		Product product = null;

		List<Product> productList = new ArrayList<Product>();
		MetaInfo metaInfo = null;

        int offset = 1; // default
        int count = 20; // default

        if (req.getOffset() != null) {
            offset = req.getOffset();
        }
        req.setOffset(offset);

        if (req.getCount() != null) {
            count = req.getCount();
        }
        count = offset + count - 1;
        req.setCount(count);

        // 웹툰 Top Menu ID.
        req.setTopMenuId(DisplayConstants.DP_WEBTOON_TOP_MENU_ID);

        // 웹툰 회차별 채널 조회
        List<ProductBasicInfo> productBasicPartInfo = this.commonDAO.queryForList("Webtoon.selectWebtoonSeries",
                req, ProductBasicInfo.class);

        // 웹툰 회차별 List 조회
        List<ProductBasicInfo> productBasicInfoList = this.commonDAO.queryForList(
                "Webtoon.selectWebtoonSeriesList", req, ProductBasicInfo.class);

        // layout 설정
        Layout layout = null;

        if (!productBasicPartInfo.isEmpty()) {
            Map<String, Object> reqMap = new HashMap<String, Object>();
            reqMap.put("tenantHeader", tenantHeader);
            reqMap.put("deviceHeader", deviceHeader);
            reqMap.put("prodStatusCd", DisplayConstants.DP_SALE_STAT_ING);

            ProductBasicInfo productBasicInfo = null;
            productBasicInfo = productBasicPartInfo.get(0);
            reqMap.put("productBasicInfo", productBasicInfo);
            reqMap.put("imageCd", DisplayConstants.DP_WEBTOON_REPRESENT_IMAGE_CD);
            MetaInfo retMetaInfo = this.metaInfoService.getWebtoonMetaInfo(reqMap);
            layout = new Layout();
            Date date = new Date();
            if (retMetaInfo != null) {
                Identifier identifier = null;
                List<Identifier> identifierList = new ArrayList<Identifier>();

                identifier = this.ebookComicGenerator.generateIdentifier(retMetaInfo);
                identifierList.add(identifier);
                identifier = this.commonGenerator.generateIdentifier(DisplayConstants.DP_EPISODE_IDENTIFIER_CD,
                        productBasicInfo.getFirstProdId());
                identifierList.add(identifier);
                date.setType("week/serially");
                date.setText(productBasicInfo.getSeriallyWkdy());

                layout.setIdentifierList(identifierList);
                layout.setTitle(this.commonGenerator.generateTitle(retMetaInfo));
                layout.setProductExplain(retMetaInfo.getProdBaseDesc());
                layout.setAccrual(this.commonGenerator.generateAccrual(retMetaInfo));
                layout.setSourceList(this.commonGenerator.generateSourceList(retMetaInfo));
                layout.setDate(date);
                layout.setContributor(this.ebookComicGenerator.generateComicContributor(retMetaInfo));
            }
        }
        if (!productBasicInfoList.isEmpty()) {
            Map<String, Object> reqMap = new HashMap<String, Object>();
            reqMap.put("tenantHeader", tenantHeader);
            reqMap.put("deviceHeader", deviceHeader);
            reqMap.put("prodStatusCd", DisplayConstants.DP_SALE_STAT_ING);
            for (ProductBasicInfo productBasicInfo : productBasicInfoList) {
                reqMap.put("productBasicInfo", productBasicInfo);
                reqMap.put("imageCd", DisplayConstants.DP_WEBTOON_REPRESENT_IMAGE_CD);
                // MetaInfo retMetaInfo = this.metaInfoService.getWebtoonMetaInfo(reqMap);
                metaInfo = this.commonDAO.queryForObject("Webtoon.getWebtoonEpisodeMetaInfo", reqMap,
                        MetaInfo.class);

                if (metaInfo != null) {
                    product = this.responseInfoGenerateFacade.generateWebtoonProduct(metaInfo);
                    productList.add(product);
                }
            }
            commonResponse.setTotalCount(productBasicInfoList.get(0).getTotalCount());
            res.setLayout(layout);
            res.setProductList(productList);
            res.setCommonResponse(commonResponse);
        } else {
            // 조회 결과 없음
            commonResponse.setTotalCount(0);
            res.setLayout(layout);
            res.setProductList(productList);
            res.setCommonResponse(commonResponse);
        }
        return res;
	}


}
