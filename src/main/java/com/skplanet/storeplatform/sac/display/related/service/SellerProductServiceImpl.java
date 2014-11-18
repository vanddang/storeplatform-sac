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
import com.skplanet.storeplatform.sac.client.display.vo.related.SellerProductSacReq;
import com.skplanet.storeplatform.sac.client.display.vo.related.SellerProductSacRes;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.common.CommonResponse;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.product.Product;
import com.skplanet.storeplatform.sac.common.header.vo.SacRequestHeader;
import com.skplanet.storeplatform.sac.display.common.constant.DisplayConstants;
import com.skplanet.storeplatform.sac.display.meta.service.MetaInfoService;
import com.skplanet.storeplatform.sac.display.meta.vo.MetaInfo;
import com.skplanet.storeplatform.sac.display.meta.vo.ProductBasicInfo;
import com.skplanet.storeplatform.sac.display.response.ResponseInfoGenerateFacade;
import org.apache.commons.lang3.StringUtils;
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
 * Seller Product Service 인터페이스(CoreStoreBusiness)
 * 
 * Updated on : 2014. 02. 18. Updated by : 유시혁.
 */
@Service
public class SellerProductServiceImpl implements SellerProductService {

	private final Logger log = LoggerFactory.getLogger(this.getClass());

	@Autowired
	@Qualifier("sac")
	private CommonDAO commonDAO;

	@Autowired
	private MetaInfoService metaInfoService;

	@Autowired
	private ResponseInfoGenerateFacade responseInfoGenerateFacade;

	/**
	 * 
	 * <pre>
	 * 특정 판매자별 상품 조회.
	 * </pre>
	 * 
	 * @param requestVO
	 *            SellerProductSacReq
	 * @param requestHeader
	 *            SacRequestHeader
	 * @param version
     * @return SellerProductSacRes
	 */
	@Override
	public SellerProductSacRes searchSellerProductList(SellerProductSacReq requestVO, SacRequestHeader requestHeader, int version) {

		// 요청 값 세팅
		requestVO.setOffset(requestVO.getOffset() != null ? requestVO.getOffset() : 1);
		requestVO.setCount(requestVO.getCount() != null ? requestVO.getCount() : 20);

        // 헤더 값 세팅
        Map<String, Object> req = new HashMap<String, Object>();
        req.put("sellerNo", requestVO.getSellerNo());
        if (!StringUtils.isEmpty(requestVO.getExceptId())) {
            req.put("arrayExceptId", StringUtils.split(requestVO.getExceptId(), "+"));
        }
        req.put("offset", requestVO.getOffset());
        req.put("count", requestVO.getCount());

        req.put("tenantId", requestHeader.getTenantHeader().getTenantId());
        req.put("langCd", requestHeader.getTenantHeader().getLangCd());
        req.put("deviceModelCd", requestHeader.getDeviceHeader().getModel());
        req.put("mmDeviceModelCd", DisplayConstants.DP_ANY_PHONE_4MM);
        req.put("exceptId", requestVO.getExceptId());

		SellerProductSacRes sellerProductSacRes = new SellerProductSacRes();
		CommonResponse commonResponse = new CommonResponse();
		Map<String, Object> reqMap = new HashMap<String, Object>();

		// 특정 판매자별 상품 조회
		this.log.debug("특정 판매자별 상품 조회");
		List<ProductBasicInfo> sellerProductList;
        if(version == 1)
            sellerProductList = this.commonDAO.queryForList("SellerProduct.selectSellerProductList", req, ProductBasicInfo.class);
        else if(version == 2)
            sellerProductList = this.commonDAO.queryForList("SellerProduct.selectSellerProductListNP", req, ProductBasicInfo.class);
        else
            sellerProductList = new ArrayList<ProductBasicInfo>();

        List<Product> productList = new ArrayList<Product>();
        Integer cnt = null;

		if (!sellerProductList.isEmpty()) {
			reqMap.put("tenantHeader", requestHeader.getTenantHeader());
			reqMap.put("deviceHeader", requestHeader.getDeviceHeader());
			reqMap.put("prodStatusCd", DisplayConstants.DP_SALE_STAT_ING);

			for (ProductBasicInfo productBasicInfo : sellerProductList) {

				reqMap.put("productBasicInfo", productBasicInfo);
				reqMap.put("imageCd", DisplayConstants.DP_APP_REPRESENT_IMAGE_CD);
                MetaInfo retMetaInfo = this.metaInfoService.getAppMetaInfo(reqMap);
				if (retMetaInfo != null) {
                    Product product = this.responseInfoGenerateFacade.generateAppProduct(retMetaInfo);
					productList.add(product);
				}

			}
			cnt = sellerProductList.get(0).getTotalCount();
		}

		this.log.debug("특정 판매자별 상품 조회 결과 : " + commonResponse.getTotalCount() + "건");
        sellerProductSacRes.setProductList(productList);
        commonResponse.setTotalCount(cnt != null ? cnt : 0);
		sellerProductSacRes.setCommonResponse(commonResponse);

		return sellerProductSacRes;
	}

}
