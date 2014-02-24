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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.skplanet.storeplatform.framework.core.exception.StorePlatformException;
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
	 * @return SellerProductSacRes
	 */
	@Override
	public SellerProductSacRes searchSellerProductList(SellerProductSacReq requestVO, SacRequestHeader requestHeader) {

		// 헤더 값 세팅
		this.log.debug("헤더 값 세팅");
		requestVO.setTenantId(requestHeader.getTenantHeader().getTenantId());
		requestVO.setLangCd(requestHeader.getTenantHeader().getLangCd());
		requestVO.setDeviceModelCd(requestHeader.getDeviceHeader().getModel());

		// 요청 값 세팅
		this.log.debug("요청 값 세팅");
		requestVO.setOffset(requestVO.getOffset() != null ? requestVO.getOffset() : 1);
		requestVO.setCount(requestVO.getCount() != null ? requestVO.getCount() : 20);
		if (!StringUtils.isEmpty(requestVO.getExceptId())) {
			requestVO.setArrayExceptId(requestVO.getExceptId().split("\\+"));
		}

		// 필수 파라미터 체크
		this.log.debug("필수 파라미터 체크");
		if (StringUtils.isEmpty(requestVO.getSellerNo())) {
			throw new StorePlatformException("SAC_DSP_0002", "sellerNo", requestVO.getSellerNo());
		}

		SellerProductSacRes sellerProductSacRes = new SellerProductSacRes();
		CommonResponse commonResponse = new CommonResponse();
		Map<String, Object> reqMap = new HashMap<String, Object>();
		MetaInfo retMetaInfo = null;
		Product product = null;

		// 특정 판매자별 상품 조회
		this.log.debug("특정 판매자별 상품 조회");
		List<ProductBasicInfo> sellerProductList = this.commonDAO.queryForList("SellerProduct.selectSellerProductList",
				requestVO, ProductBasicInfo.class);
		List<Product> productList = new ArrayList<Product>();

		if (!sellerProductList.isEmpty()) {
			reqMap.put("tenantHeader", requestHeader.getTenantHeader());
			reqMap.put("deviceHeader", requestHeader.getDeviceHeader());
			reqMap.put("prodStatusCd", DisplayConstants.DP_SALE_STAT_ING);

			for (ProductBasicInfo productBasicInfo : sellerProductList) {

				reqMap.put("productBasicInfo", productBasicInfo);

				reqMap.put("imageCd", DisplayConstants.DP_APP_REPRESENT_IMAGE_CD);
				retMetaInfo = this.metaInfoService.getAppMetaInfo(reqMap);
				if (retMetaInfo != null) {
					product = this.responseInfoGenerateFacade.generateAppProduct(retMetaInfo);
					productList.add(product);
				}

			}
			commonResponse.setTotalCount(sellerProductList.get(0).getTotalCount());
			sellerProductSacRes.setProductList(productList);
		} else {
			commonResponse.setTotalCount(0);
		}
		this.log.debug("특정 판매자별 상품 조회 결과 : " + commonResponse.getTotalCount() + "건");
		sellerProductSacRes.setCommonResponse(commonResponse);

		return sellerProductSacRes;
	}

}
