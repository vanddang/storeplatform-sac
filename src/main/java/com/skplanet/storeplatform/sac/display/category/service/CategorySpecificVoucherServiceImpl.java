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

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.skplanet.storeplatform.framework.core.persistence.dao.CommonDAO;
import com.skplanet.storeplatform.sac.client.display.vo.category.CategoryVoucherSacReq;
import com.skplanet.storeplatform.sac.client.display.vo.category.CategoryVoucherSacRes;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.common.CommonResponse;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.product.Coupon;
import com.skplanet.storeplatform.sac.common.header.vo.DeviceHeader;
import com.skplanet.storeplatform.sac.common.header.vo.SacRequestHeader;
import com.skplanet.storeplatform.sac.common.header.vo.TenantHeader;
import com.skplanet.storeplatform.sac.display.common.constant.DisplayConstants;
import com.skplanet.storeplatform.sac.display.meta.service.MetaInfoService;
import com.skplanet.storeplatform.sac.display.meta.vo.MetaInfo;
import com.skplanet.storeplatform.sac.display.meta.vo.ProductBasicInfo;
import com.skplanet.storeplatform.sac.display.response.ResponseInfoGenerateFacade;

/**
 * searchSpecificVoucherDetail Service 인터페이스(CoreStoreBusiness) 구현체
 * 
 * Updated on : 2014. 12. 07. Updated by : 김형식, SK 플래닛.
 */
@Service
public class CategorySpecificVoucherServiceImpl implements CategorySpecificVoucherService {

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
	 * 카테고리 이용권 조회.
	 * </pre>
	 * 
	 * @param req
	 *            파라미터
	 * @param header
	 *            공통헤더
	 * @return  카테고리  이용권 상세 조회
	 */
	@Override
	public CategoryVoucherSacRes searchSpecificVoucherDetail(SacRequestHeader header, CategoryVoucherSacReq req) {
		// 공통 응답 변수 선언
		CategoryVoucherSacRes res = new CategoryVoucherSacRes();
		CommonResponse commonResponse = new CommonResponse();
		TenantHeader tenantHeader = header.getTenantHeader();
		DeviceHeader deviceHeader = header.getDeviceHeader();

		// DB 조회 파라미터 생성
		Map<String, Object> reqMap = new HashMap<String, Object>();
		ProductBasicInfo productBasicInfo = new ProductBasicInfo();
		productBasicInfo.setProdId(req.getProductId());
		MetaInfo retMetaInfo = null;
		Coupon coupon = null;
		reqMap.put("tenantHeader", tenantHeader);
		reqMap.put("deviceHeader", deviceHeader);
		reqMap.put("bannerImageCd", DisplayConstants.DP_FREEPASS_BANNER_IMAGE_CD);
		reqMap.put("thumbnailImageCd", DisplayConstants.DP_FREEPASS_THUMBNAIL_IMAGE_CD);
		reqMap.put("ebookThumbnailImageCd", DisplayConstants.DP_FREEPASS_EBOOK_THUMBNAIL_IMAGE_CD);
		reqMap.put("productBasicInfo", productBasicInfo);

		retMetaInfo = this.metaInfoService.getVoucherMetaInfo(reqMap);
		if(retMetaInfo == null){
			coupon = new Coupon();
			res.setCoupon(coupon);
			commonResponse.setTotalCount(0);
		}else{
    		coupon = this.responseInfoGenerateFacade.generateVoucherProduct(retMetaInfo);
    		commonResponse.setTotalCount(1);
		}	
	
		res.setCommonResponse(commonResponse);
		res.setCoupon(coupon);
		return res;
	}
	
}
