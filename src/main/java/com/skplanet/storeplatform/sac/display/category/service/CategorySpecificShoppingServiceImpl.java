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

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.skplanet.storeplatform.framework.core.exception.StorePlatformException;
import com.skplanet.storeplatform.framework.core.persistence.dao.CommonDAO;
import com.skplanet.storeplatform.sac.client.display.vo.category.CategoryShoppingSacReq;
import com.skplanet.storeplatform.sac.client.display.vo.category.CategoryShoppingSacRes;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.product.Product;
import com.skplanet.storeplatform.sac.common.header.vo.DeviceHeader;
import com.skplanet.storeplatform.sac.common.header.vo.SacRequestHeader;
import com.skplanet.storeplatform.sac.common.header.vo.TenantHeader;
import com.skplanet.storeplatform.sac.display.common.constant.DisplayConstants;
import com.skplanet.storeplatform.sac.display.common.service.DisplayCommonService;
import com.skplanet.storeplatform.sac.display.common.service.MemberBenefitService;
import com.skplanet.storeplatform.sac.display.common.vo.SupportDevice;
import com.skplanet.storeplatform.sac.display.meta.service.MetaInfoService;
import com.skplanet.storeplatform.sac.display.meta.vo.MetaInfo;
import com.skplanet.storeplatform.sac.display.response.ResponseInfoGenerateFacade;

/**
 * WebtoonList Service 인터페이스(CoreStoreBusiness) 구현체
 * 
 * Updated on : 2014. 12. 07. Updated by : 김형식, SK 플래닛.
 */
@Service
public class CategorySpecificShoppingServiceImpl implements CategorySpecificShoppingService {

	private final Logger log = LoggerFactory.getLogger(this.getClass());

	@Autowired
	@Qualifier("sac")
	private CommonDAO commonDAO;

	@Autowired
	private MetaInfoService metaInfoService;

	@Autowired
	private ResponseInfoGenerateFacade responseInfoGenerateFacade;

	@Autowired
	private DisplayCommonService displayCommonService;
	
    @Autowired
    private MemberBenefitService memberBenefitService;
	/**
	 * 
	 * <pre>
	 * 카테고리 쇼핑 상세 조회.
	 * </pre>
	 * 
	 * @param req
	 *            파라미터
	 * @param header
	 *            공통헤더
	 * @return  카테고리  쇼핑 상세 조회
	 */
	@Override
	public CategoryShoppingSacRes searchSpecificShoppingDetail(SacRequestHeader header, CategoryShoppingSacReq req) {
		// 공통 응답 변수 선언
		CategoryShoppingSacRes res = new CategoryShoppingSacRes();
		TenantHeader tenantHeader = header.getTenantHeader();
		DeviceHeader deviceHeader = header.getDeviceHeader();

		req.setImageCd(DisplayConstants.DP_SHOPPING_REPRESENT_IMAGE_CD);
		req.setVirtualDeviceModelNo(DisplayConstants.DP_ANY_PHONE_4MM);

		if (StringUtils.isEmpty(req.getSpecialProdId())) {
			req.setSpecialProdId(null);
		}

		if (StringUtils.isNotEmpty(req.getSaleDtUseYn())) {
			if (!"Y".equals(req.getSaleDtUseYn()) && !"N".equals(req.getSaleDtUseYn())) {
				throw new StorePlatformException("SAC_DSP_0003", "saleDtUseYn", req.getSaleDtUseYn());
			}
			if ("N".equals(req.getSaleDtUseYn())) {
				req.setSaleDtUseYn(null);
			}
		}
		if (StringUtils.isEmpty(req.getSaleDtUseYn())) {
			req.setSaleDtUseYn(null);
		}
		if (StringUtils.isNotEmpty(req.getIncludeProdStopStatus())) {
			if (!"Y".equals(req.getIncludeProdStopStatus()) && !"N".equals(req.getIncludeProdStopStatus())) {
				throw new StorePlatformException("SAC_DSP_0003", "includeProdStopStatus",
						req.getIncludeProdStopStatus());
			}
			if ("Y".equals(req.getIncludeProdStopStatus())) {
				req.setIncludeProdStopStatus(null);
			}
		}

		if (StringUtils.isEmpty(req.getIncludeProdStopStatus())) {
			req.setIncludeProdStopStatus(null);
		}		

		// DB 조회 파라미터 생성
		Map<String, Object> reqMap = new HashMap<String, Object>();
		reqMap.put("req", req);
		reqMap.put("tenantHeader", tenantHeader);
		reqMap.put("deviceHeader", deviceHeader);
		reqMap.put("lang", tenantHeader.getLangCd());

		reqMap.put("imageCd", req.getImageCd());
		reqMap.put("svcGrpCd", DisplayConstants.DP_TSTORE_SHOPPING_PROD_SVC_GRP_CD);
		reqMap.put("contentTypeCd", DisplayConstants.DP_CHANNEL_CONTENT_TYPE_CD);
		reqMap.put("prodStatusCd", DisplayConstants.DP_SALE_STAT_ING);
		reqMap.put("prodRshpCd", DisplayConstants.DP_CHANNEL_EPISHODE_RELATIONSHIP_CD);
		reqMap.put("channelContentTypeCd", DisplayConstants.DP_EPISODE_CONTENT_TYPE_CD);


		if (!this.commonSupportDeviceShopping(header)) {
			// 조회 결과 없음
			Product product = new Product();
			res.setProduct(product);
			return res;
		}

		// ID list 조회
		MetaInfo retMetaInfo =  this.commonDAO.queryForObject("Shopping.searchSpecificShoppingDetail", reqMap, MetaInfo.class);
		if (retMetaInfo != null) {
			
			retMetaInfo.setMileageInfo(memberBenefitService.getMileageInfo(tenantHeader.getTenantId(), retMetaInfo.getTopMenuId(), retMetaInfo.getProdId(), retMetaInfo.getProdAmt()));

			// 쇼핑 Response Generate
			Product product = this.responseInfoGenerateFacade.generateShoppingProduct(retMetaInfo);
			if (StringUtils.isNotEmpty(req.getSpecialProdId())) {
				product.setSpecialCouponId(retMetaInfo.getSpecialCouponId());
				product.setSpecialProdYn(retMetaInfo.getSpecialSale());
			}
			res.setProduct(product);
		}else{
			// 조회 결과 없음
			Product product = new Product();
			res.setProduct(product);
			return res;			
		}
		return res;
	}
	/**
	 * 쇼핑 지원 여부 .
	 * 
	 * @param header
	 *            header
	 * @return boolean
	 */
	private boolean commonSupportDeviceShopping(SacRequestHeader header) {
		boolean result = true;

		// 단말 지원정보 조회
		SupportDevice supportDevice = this.displayCommonService.getSupportDeviceInfo(header.getDeviceHeader()
				.getModel());
		if (!supportDevice.getSclShpgSprtYn().equals("Y")) {
			this.log.debug("----------------------------------------------------------------");
			this.log.debug("[shopping] supportDevice is empty!");
			this.log.debug("----------------------------------------------------------------");
			result = false;
		}
		return result;
	}	
}
