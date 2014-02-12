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
import org.springframework.stereotype.Service;

import com.skplanet.storeplatform.framework.core.exception.StorePlatformException;
import com.skplanet.storeplatform.framework.core.persistence.dao.CommonDAO;
import com.skplanet.storeplatform.sac.client.display.vo.feature.category.FeatureCategoryVodSacReq;
import com.skplanet.storeplatform.sac.client.display.vo.feature.category.FeatureCategoryVodSacRes;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.common.CommonResponse;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.product.Product;
import com.skplanet.storeplatform.sac.common.header.vo.DeviceHeader;
import com.skplanet.storeplatform.sac.common.header.vo.SacRequestHeader;
import com.skplanet.storeplatform.sac.common.header.vo.TenantHeader;
import com.skplanet.storeplatform.sac.display.category.service.CategoryAppServiceImpl;
import com.skplanet.storeplatform.sac.display.common.constant.DisplayConstants;
import com.skplanet.storeplatform.sac.display.common.service.DisplayCommonService;
import com.skplanet.storeplatform.sac.display.meta.service.MetaInfoService;
import com.skplanet.storeplatform.sac.display.meta.vo.MetaInfo;
import com.skplanet.storeplatform.sac.display.meta.vo.ProductBasicInfo;
import com.skplanet.storeplatform.sac.display.response.ResponseInfoGenerateFacade;

/**
 * 
 * 
 * Updated on : 2014. 01. 27. Updated by 조준일, nTels.
 */
@Service
public class FeatureCategoryVodServiceImpl implements FeatureCategoryVodService {
	private transient Logger logger = LoggerFactory.getLogger(CategoryAppServiceImpl.class);

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
	 * com.skplanet.storeplatform.sac.display.feature.category.service.FeatureCategoryVodService#searchVodList(com.skplanet
	 * .storeplatform.sac.client.display.vo.feature.category.FeatureCategoryVodReq)
	 */
	@Override
	public FeatureCategoryVodSacRes searchVodList(FeatureCategoryVodSacReq req, SacRequestHeader header) {

		FeatureCategoryVodSacRes vodRes = new FeatureCategoryVodSacRes();
		CommonResponse commonResponse = new CommonResponse();

		String topMenuId = req.getTopMenuId();
		String listId = req.getListId();
		String filteredBy = req.getFilteredBy();

		// topMenuId 필수 파라미터 체크
		if (StringUtils.isEmpty(topMenuId)) {
			throw new StorePlatformException("SAC_DSP_0002", "topMenuId", topMenuId);
		}

		// listId 필수 파라미터 체크
		if (StringUtils.isEmpty(listId)) {
			throw new StorePlatformException("SAC_DSP_0002", "listId", listId);
		}

		// 메뉴ID 유효값 체크
		if (!"DP17".equals(topMenuId) && !"DP18".equals(topMenuId)) {
			throw new StorePlatformException("SAC_DSP_0003", "topMenuId", topMenuId);
		}

		// 리스트ID 유효값 체크
		if (!"ADM000000003".equals(listId) && !"ADM000000008".equals(listId)) {
			throw new StorePlatformException("SAC_DSP_0003", "listId", listId);
		}

		// 영화>추천, 영화>1000원관, 방송>카테고리별 추천, 방송>방송사별 최신Up API는 filteredBy 필수
		if (StringUtils.isEmpty(req.getMenuId()) && StringUtils.isEmpty(filteredBy)) {
			throw new StorePlatformException("SAC_DSP_0002", "filteredBy", filteredBy);
		}

		// 시작점 ROW Default 세팅
		if (req.getOffset() == null) {
			req.setOffset(1);
		}
		// 페이지당 노출될 ROW 개수 Default 세팅
		if (req.getCount() == null) {
			req.setCount(20);
		}

		// 헤더값 세팅
		req.setDeviceModelCd(header.getDeviceHeader().getModel());
		req.setTenantId(header.getTenantHeader().getTenantId());
		req.setImageCd("DP000101");
		req.setLangCd(header.getTenantHeader().getLangCd());

		// 배치완료 기준일시 조회
		String stdDt = this.displayCommonService.getBatchStandardDateString(req.getTenantId(), listId);

		// 기준일시 체크
		if (StringUtils.isEmpty(stdDt)) {
			throw new StorePlatformException("SAC_DSP_0002", "stdDt", stdDt);
		} else {
			req.setStdDt(stdDt);
		}

		// prodGradeCd encode 처리(테넌트에서 인코딩하여 넘길 시 제거 필요)
		if (!StringUtils.isEmpty(req.getProdGradeCd())) {
			try {
				req.setProdGradeCd(URLEncoder.encode(req.getProdGradeCd(), "UTF-8"));
			} catch (Exception ex) {
				throw new StorePlatformException("SAC_DSP_9999", ex);
			}

			// prodGradeCd 배열로 변경
			String[] prodGradeCdArr = req.getProdGradeCd().split("\\+");
			req.setProdGradeCdArr(prodGradeCdArr);
		}

		List<ProductBasicInfo> productBasicInfoList;

		// ADM000000008 : 운영자 추천, ADM000000003 운영자 신규
		// DP17 : 영화, DP18 : 방송
		if ("ADM000000008".equals(listId)) {
			if ("DP17".equals(topMenuId)) {
				if ("recommend".equals(filteredBy)) {
					this.logger.debug("----------------------------------------------------------------");
					this.logger.debug("영화 > 추천 상품 조회");
					this.logger.debug("----------------------------------------------------------------");
				} else if ("movie1000".equals(filteredBy)) {
					this.logger.debug("----------------------------------------------------------------");
					this.logger.debug("영화 > 1000원관 상품 조회");
					this.logger.debug("----------------------------------------------------------------");
				} else {
					this.logger.debug("----------------------------------------------------------------");
					this.logger.debug("유효하지않은 조회유형");
					this.logger.debug("----------------------------------------------------------------");

					throw new StorePlatformException("SAC_DSP_0003", "listId", listId, "topMenuId", topMenuId,
							"filteredBy", filteredBy);
				}
			} else {
				this.logger.debug("----------------------------------------------------------------");
				this.logger.debug("방송 > 카테고리별 추천 상품 조회");
				this.logger.debug("----------------------------------------------------------------");
			}

			// 추천 리스트 조회
			productBasicInfoList = this.commonDAO.queryForList("FeatureCategory.selectFeatureVodList", req,
					ProductBasicInfo.class);

		} else {
			if (!"".equals(filteredBy) && filteredBy != null) {
				this.logger.debug("----------------------------------------------------------------");
				this.logger.debug("방송 > 방송사별 최신 UP 상품 조회");
				this.logger.debug("----------------------------------------------------------------");

				// 배치완료 기준일시 조회
				// 최신 Up 상품의 경우 방송 카테고리별 추천 상품 제외하기 때문에 운영자추천 배치완료 일자 조회함
				String subStdDt = this.displayCommonService.getBatchStandardDateString(req.getTenantId(),
						"ADM000000008");

				// 기준일시 체크
				if (StringUtils.isEmpty(subStdDt)) {
					throw new StorePlatformException("SAC_DSP_0002", "subStdDt", subStdDt);
				} else {
					req.setSubStdDt(subStdDt);
				}

				// 리스트 조회
				productBasicInfoList = this.commonDAO.queryForList("FeatureCategory.selectFeatureNewUpTvList", req,
						ProductBasicInfo.class);

			} else {
				this.logger.debug("----------------------------------------------------------------");
				this.logger.debug("영화/방송 > 신규 상품 조회");
				this.logger.debug("----------------------------------------------------------------");

				// 신규 리스트 조회
				productBasicInfoList = this.commonDAO.queryForList("FeatureCategory.selectFeatureNewVodist", req,
						ProductBasicInfo.class);
			}
		}

		List<Product> productList = new ArrayList<Product>();

		// Meta DB 조회 파라미터 생성
		Map<String, Object> reqMap = new HashMap<String, Object>();
		TenantHeader tenantHeader = header.getTenantHeader();
		DeviceHeader deviceHeader = header.getDeviceHeader();
		reqMap.put("req", req);
		reqMap.put("tenantHeader", tenantHeader);
		reqMap.put("deviceHeader", deviceHeader);
		reqMap.put("stdDt", stdDt);
		reqMap.put("lang", tenantHeader.getLangCd());

		reqMap.put("svcGrpCd", DisplayConstants.DP_MULTIMEDIA_PROD_SVC_GRP_CD);
		// reqMap.put("contentTypeCd", DisplayConstants.DP_CHANNEL_CONTENT_TYPE_CD);
		reqMap.put("prodStatusCd", DisplayConstants.DP_SALE_STAT_ING);
		reqMap.put("imageCd", DisplayConstants.DP_VOD_REPRESENT_IMAGE_CD);

		if (productBasicInfoList != null && productBasicInfoList.size() > 0) {
			for (ProductBasicInfo productBasicInfo : productBasicInfoList) {
				reqMap.put("productBasicInfo", productBasicInfo);

				// Meta 정보 조회
				MetaInfo retMetaInfo = this.metaInfoService.getEbookComicMetaInfo(reqMap);

				if (retMetaInfo != null) {
					// Response Generate
					Product product = this.responseInfoGenerateFacade.generateEbookProduct(retMetaInfo);
					productList.add(product);
				}
			}
			commonResponse.setTotalCount(productBasicInfoList.get(0).getTotalCount());
			vodRes.setProductList(productList);
			vodRes.setCommonResponse(commonResponse);
		} else {
			// 조회 결과 없음
			commonResponse.setTotalCount(0);
			vodRes.setProductList(productList);
			vodRes.setCommonResponse(commonResponse);
		}

		return vodRes;
	}

}
