/*
 * Copyright (c) 2013 SK planet.
 * All right reserved.
 *
 * This software is the confidential and proprietary information of SK planet.
 * You shall not disclose such Confidential Information and
 * shall use it only in accordance with the terms of the license agreement
 * you entered into with SK planet.
 */
package com.skplanet.storeplatform.sac.display.related.service;

import com.skplanet.storeplatform.framework.core.persistence.dao.CommonDAO;
import com.skplanet.storeplatform.sac.client.display.vo.related.AuthorProductSacReq;
import com.skplanet.storeplatform.sac.client.display.vo.related.AuthorProductSacRes;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.common.CommonResponse;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.product.Product;
import com.skplanet.storeplatform.sac.common.header.vo.SacRequestHeader;
import com.skplanet.storeplatform.sac.display.common.constant.DisplayConstants;
import com.skplanet.storeplatform.sac.display.meta.service.MetaInfoService;
import com.skplanet.storeplatform.sac.display.meta.vo.MetaInfo;
import com.skplanet.storeplatform.sac.display.meta.vo.ProductBasicInfo;
import com.skplanet.storeplatform.sac.display.response.EbookComicGenerator;
import com.skplanet.storeplatform.sac.display.response.ResponseInfoGenerateFacade;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * AuthorProductService 인터페이스(CoreStoreBusiness)
 * 
 * Updated on : 2014. 02. 24. Updated by : 유시혁.
 */
@Service
public class AuthorProductServiceImpl implements AuthorProductService {

	private final Logger log = LoggerFactory.getLogger(this.getClass());

	@Autowired
	@Qualifier("sac")
	private CommonDAO commonDAO;

	@Autowired
	private MetaInfoService metaInfoService;

	@Autowired
	private ResponseInfoGenerateFacade responseInfoGenerateFacade;

	@Autowired
	private EbookComicGenerator ebookComicGenerator;

	/**
	 * 
	 * <pre>
	 * 특정 작가별 리스트 조회.
	 * </pre>
	 * 
	 * @param requestVO
	 *            AuthorProductSacReq
	 * @param requestHeader
	 *            SacRequestHeader
	 * @param version
     * @return AuthorProductSacRes
	 */
	@Override
	public AuthorProductSacRes searchAuthorProductList(AuthorProductSacReq requestVO, SacRequestHeader requestHeader, int version) {

        requestVO.setOffset(requestVO.getOffset() != null ? requestVO.getOffset() : 1);
        requestVO.setCount(requestVO.getCount() != null ? requestVO.getCount() : 20);

        Map<String, Object> req = new HashMap<String, Object>();
        req.put("authorName", requestVO.getAuthorName());
        req.put("filteredBy", requestVO.getFilteredBy());
        req.put("offset", requestVO.getOffset());
        req.put("count", requestVO.getCount());

        req.put("tenantId", requestHeader.getTenantHeader().getTenantId());
        req.put("langCd", requestHeader.getTenantHeader().getLangCd());
        req.put("deviceModelCd", requestHeader.getDeviceHeader().getModel());
        req.put("mmDeviceModelCd", DisplayConstants.DP_ANY_PHONE_4MM);
        req.put("exceptId", requestVO.getExceptId());

		AuthorProductSacRes authorProductSacRes = new AuthorProductSacRes();
		CommonResponse commonResponse = new CommonResponse();
		Map<String, Object> reqMap = new HashMap<String, Object>();
		MetaInfo retMetaInfo = null;
		Product product = null;

		// 특정 아티스트 정보 조회
		this.log.debug("특정 아티스트 정보 조회");
		MetaInfo authorMetaInfo = this.commonDAO.queryForObject("AuthorProduct.selectAuthorInfo", req,
				MetaInfo.class);
        List<Product> productList = new ArrayList<Product>();
        Integer cnt = null;

		if (authorMetaInfo != null) {
			if (authorMetaInfo.getTopMenuId().equals(DisplayConstants.DP_EBOOK_TOP_MENU_ID)) {
				authorProductSacRes.setContributor(this.ebookComicGenerator.generateEbookContributor(authorMetaInfo));
			} else if (authorMetaInfo.getTopMenuId().equals(DisplayConstants.DP_COMIC_TOP_MENU_ID)) {
				authorProductSacRes.setContributor(this.ebookComicGenerator.generateComicContributor(authorMetaInfo));
			}

            req.put("topMenuId", authorMetaInfo.getTopMenuId());

			// 특정 작가별 상품 조회
			this.log.debug("특정 작가별 상품 조회");
			List<ProductBasicInfo> authorProductList;
            if(version == 1)
                authorProductList = this.commonDAO.queryForList("AuthorProduct.selectAuthorProductList", req, ProductBasicInfo.class);
            else if(version == 2)
                authorProductList = this.commonDAO.queryForList("AuthorProduct.selectAuthorProductListNP", req, ProductBasicInfo.class);
            else
                authorProductList = new ArrayList<ProductBasicInfo>();

			if (!authorProductList.isEmpty()) {
				reqMap.put("tenantHeader", requestHeader.getTenantHeader());
				reqMap.put("deviceHeader", requestHeader.getDeviceHeader());
				reqMap.put("prodStatusCd", DisplayConstants.DP_SALE_STAT_ING);

				for (ProductBasicInfo productBasicInfo : authorProductList) {

					reqMap.put("productBasicInfo", productBasicInfo);

					reqMap.put("imageCd", DisplayConstants.DP_EBOOK_COMIC_REPRESENT_IMAGE_CD);
					retMetaInfo = this.metaInfoService.getEbookComicMetaInfo(reqMap);
                    if(retMetaInfo == null)
                        continue;

                    if (productBasicInfo.getTopMenuId().equals(DisplayConstants.DP_EBOOK_TOP_MENU_ID)) {
                        product = this.responseInfoGenerateFacade.generateEbookProduct(retMetaInfo);
                        productList.add(product);
                        String tempProductExplain = product.getProductExplain();
                        product.setProductExplain(product.getProductDetailExplain());
                        product.setProductDetailExplain(tempProductExplain);
                    } else if (productBasicInfo.getTopMenuId().equals(DisplayConstants.DP_COMIC_TOP_MENU_ID)) {
                        product = this.responseInfoGenerateFacade.generateComicProduct(retMetaInfo);
                        productList.add(product);
                    }
				}
				cnt = authorProductList.get(0).getTotalCount();
				authorProductSacRes.setProductList(productList);
			}
		}

		this.log.debug("특정 작가별 상품 조회 결과 : " + commonResponse.getTotalCount() + "건");
        commonResponse.setTotalCount(cnt != null ? cnt : 0);
        authorProductSacRes.setProductList(productList);
		authorProductSacRes.setCommonResponse(commonResponse);

		return authorProductSacRes;
	}
}
