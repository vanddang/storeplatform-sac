/*
 * Copyright (c) 2013 SK planet.
 * All right reserved.
 *
 * This software is the confidential and proprietary information of SK planet.
 * You shall not disclose such Confidential Information and
 * shall use it only in accordance with the terms of the license agreement
 * you entered into with SK planet.
 */
package com.skplanet.storeplatform.sac.display.feature.category.service;

import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import com.skplanet.storeplatform.framework.core.exception.StorePlatformException;
import com.skplanet.storeplatform.framework.core.persistence.dao.CommonDAO;
import com.skplanet.storeplatform.sac.client.display.vo.feature.category.FeatureCategoryEpubSacReq;
import com.skplanet.storeplatform.sac.client.display.vo.feature.category.FeatureCategoryEpubSacRes;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.common.CommonResponse;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.product.Product;
import com.skplanet.storeplatform.sac.common.header.vo.DeviceHeader;
import com.skplanet.storeplatform.sac.common.header.vo.SacRequestHeader;
import com.skplanet.storeplatform.sac.common.header.vo.TenantHeader;
import com.skplanet.storeplatform.sac.display.common.constant.DisplayConstants;
import com.skplanet.storeplatform.sac.display.common.service.DisplayCommonService;
import com.skplanet.storeplatform.sac.display.meta.service.MetaInfoService;
import com.skplanet.storeplatform.sac.display.meta.vo.MetaInfo;
import com.skplanet.storeplatform.sac.display.meta.vo.ProductBasicInfo;
import com.skplanet.storeplatform.sac.display.response.ResponseInfoGenerateFacade;

/**
 * 
 * 
 * Updated on : 2013. 12. 24. Updated by : 서영배, GTSOFT.
 */
@org.springframework.stereotype.Service
public class FeatureCategoryEpubServiceImpl implements FeatureCategoryEpubService {
	private transient Logger logger = LoggerFactory.getLogger(FeatureCategoryEpubServiceImpl.class);

	// private final Logger log = LoggerFactory.getLogger(this.getClass());

	@Autowired
	@Qualifier("sac")
	private CommonDAO commonDAO;

	@Autowired
	private DisplayCommonService displayCommonService;

	@Autowired
	private MetaInfoService metaInfoService;

	@Autowired
	private ResponseInfoGenerateFacade responseInfoGenerateFacade;

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.skplanet.storeplatform.sac.product.service.TotalRecommendService#searchTotalRecommendList(java.lang.String,
	 * java.lang.String, java.lang.String, java.lang.String, java.lang.String, int, int)
	 */
	@Override
	public FeatureCategoryEpubSacRes searchEpubList(FeatureCategoryEpubSacReq requestVO, SacRequestHeader header) {
		// TODO Auto-generated method stub
		// 공통 응답 변수 선언
		// int totalCount = 0;
		FeatureCategoryEpubSacRes responseVO = new FeatureCategoryEpubSacRes();
		CommonResponse commonResponse = new CommonResponse();

		String topMenuId = requestVO.getTopMenuId();
		String listId = requestVO.getListId();

		// topMenuId 필수 파라미터 체크
		if (StringUtils.isEmpty(topMenuId)) {
			throw new StorePlatformException("SAC_DSP_0002", "topMenuId", topMenuId);
		}

		// listId 필수 파라미터 체크
		if (StringUtils.isEmpty(listId)) {
			throw new StorePlatformException("SAC_DSP_0002", "listId", listId);
		}

		// 메뉴ID 유효값 체크 DP13 : 이북, DP14 : 만화
		if (!"DP13".equals(topMenuId) && !"DP14".equals(topMenuId)) {
			throw new StorePlatformException("SAC_DSP_0003", "topMenuId", topMenuId);
		}

		// 리스트ID 유효값 체크
		if (!"ADM000000013".equals(listId) && !"ADM000000002".equals(listId) && !"RNK000000002".equals(listId)
				&& !"RNK000000006".equals(listId)) {
			throw new StorePlatformException("SAC_DSP_0003", "listId", listId);
		}

		// ADM000000002 만화만 조회되어야 함 topMenuId DP13(이북) 넘어온 경우 체크
		if ("ADM000000002".equals(listId) && "DP13".equals(topMenuId)) {
			throw new StorePlatformException("SAC_DSP_0003", "listId", listId, "topMenuId", topMenuId);
		}

		// RNK000000002 이북만 조회되어야 함 topMenuId DP14(코믹) 넘어온 경우 체크
		if ("RNK000000002".equals(listId) && "DP14".equals(topMenuId)) {
			throw new StorePlatformException("SAC_DSP_0003", "listId", listId, "topMenuId", topMenuId);
		}

		if (!StringUtils.isEmpty(requestVO.getFilteredBy())) {
			try {
				requestVO.setFilteredBy(URLEncoder.encode(requestVO.getFilteredBy(), "UTF-8"));
			} catch (Exception ex) {
				throw new StorePlatformException("SAC_DSP_9999", ex);
			}
		}

		// 시작점 ROW Default 세팅
		if (requestVO.getOffset() == null) {
			requestVO.setOffset(1);
		}
		// 페이지당 노출될 ROW 개수 Default 세팅
		if (requestVO.getCount() == null) {
			requestVO.setCount(20);
		}

		// 헤더값 세팅
		requestVO.setDeviceModelCd(header.getDeviceHeader().getModel());
		requestVO.setTenantId(header.getTenantHeader().getTenantId());
		requestVO.setLangCd("ko");
		// requestVO.setImageCd("DP000101");

		// 배치완료 기준일시 조회
		String stdDt = this.displayCommonService.getBatchStandardDateString(requestVO.getTenantId(), listId);

		// 기준일시 체크
		if (StringUtils.isEmpty(stdDt)) {
			throw new StorePlatformException("SAC_DSP_0002", "stdDt", stdDt);
		} else {
			requestVO.setStdDt(stdDt);
		}

		// prodGradeCd encode 처리(테넌트에서 인코딩하여 넘길 시 제거 필요)
		if (!StringUtils.isEmpty(requestVO.getProdGradeCd())) {
			try {
				requestVO.setProdGradeCd(URLEncoder.encode(requestVO.getProdGradeCd(), "UTF-8"));
			} catch (Exception ex) {
				throw new StorePlatformException("SAC_DSP_9999", ex);
			}

			// prodGradeCd 배열로 변경
			String[] prodGradeCdArr = requestVO.getProdGradeCd().split("\\+");
			requestVO.setProdGradeCdArr(prodGradeCdArr);
		}

		List<ProductBasicInfo> productBasicInfoList;

		// ADM000000013 : 운영자 추천, ADM000000002 : 신규 만화, RNK000000002 : 신규 이북, RNK000000006 : 인기코믹/인기도서
		if (listId.equals("ADM000000013")) {
			this.logger.debug("----------------------------------------------------------------");
			this.logger.debug("만화/이북 > 추천 > Tstore 추천 조회");
			this.logger.debug("----------------------------------------------------------------");

			productBasicInfoList = this.commonDAO.queryForList("FeatureCategory.selectCategoryEpubRecomList",
					requestVO, ProductBasicInfo.class);
		} else if (listId.equals("ADM000000002")) {
			this.logger.debug("----------------------------------------------------------------");
			this.logger.debug("만화 > 최신 조회");
			this.logger.debug("----------------------------------------------------------------");

			productBasicInfoList = this.commonDAO.queryForList("FeatureCategory.selectCategoryEpubRecomList",
					requestVO, ProductBasicInfo.class);
		} else if (listId.equals("RNK000000002")) {
			this.logger.debug("----------------------------------------------------------------");
			this.logger.debug("eBook > 최신 > 일반/장르 조회");
			this.logger.debug("----------------------------------------------------------------");

			productBasicInfoList = this.commonDAO.queryForList("FeatureCategory.selectCategoryEpubRecomList",
					requestVO, ProductBasicInfo.class);
		} else {
			this.logger.debug("----------------------------------------------------------------");
			this.logger.debug("만화/이북 > 추천 > 인기만화/인기도서 조회");
			this.logger.debug("----------------------------------------------------------------");

			productBasicInfoList = this.commonDAO.queryForList("FeatureCategory.selectCategoryEpubBestList", requestVO,
					ProductBasicInfo.class);
		}

		List<Product> productList = new ArrayList<Product>();

		// Meta DB 조회 파라미터 생성
		Map<String, Object> reqMap = new HashMap<String, Object>();
		TenantHeader tenantHeader = header.getTenantHeader();
		DeviceHeader deviceHeader = header.getDeviceHeader();
		reqMap.put("req", requestVO);
		reqMap.put("tenantHeader", tenantHeader);
		reqMap.put("deviceHeader", deviceHeader);
		reqMap.put("stdDt", stdDt);
		reqMap.put("lang", tenantHeader.getLangCd());

		reqMap.put("svcGrpCd", DisplayConstants.DP_MULTIMEDIA_PROD_SVC_GRP_CD);
		// reqMap.put("contentTypeCd", DisplayConstants.DP_CHANNEL_CONTENT_TYPE_CD);
		reqMap.put("prodStatusCd", DisplayConstants.DP_SALE_STAT_ING);
		reqMap.put("imageCd", DisplayConstants.DP_EBOOK_COMIC_REPRESENT_IMAGE_CD);

		if (productBasicInfoList != null && productBasicInfoList.size() > 0) {
			for (ProductBasicInfo productBasicInfo : productBasicInfoList) {
				reqMap.put("productBasicInfo", productBasicInfo);

				// Meta 정보 조회
				MetaInfo retMetaInfo = this.metaInfoService.getEbookComicMetaInfo(reqMap);

				if (retMetaInfo != null) {
					if ("DP13".equals(requestVO.getTopMenuId())) {
						// Response Generate
						Product product = this.responseInfoGenerateFacade.generateEbookProduct(retMetaInfo);
						productList.add(product);
					} else {
						// Response Generate
						Product product = this.responseInfoGenerateFacade.generateComicProduct(retMetaInfo);
						productList.add(product);
					}
				}
			}
			commonResponse.setTotalCount(productBasicInfoList.get(0).getTotalCount());
			responseVO.setProductList(productList);
			responseVO.setCommonResponse(commonResponse);
		} else {
			// 조회 결과 없음
			commonResponse.setTotalCount(0);
			responseVO.setProductList(productList);
			responseVO.setCommonResponse(commonResponse);
		}

		return responseVO;
	}

}
