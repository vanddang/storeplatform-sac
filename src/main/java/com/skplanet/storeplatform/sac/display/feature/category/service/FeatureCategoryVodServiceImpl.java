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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.skplanet.storeplatform.framework.core.exception.StorePlatformException;
import com.skplanet.storeplatform.framework.core.persistence.dao.CommonDAO;
import com.skplanet.storeplatform.framework.core.util.StringUtils;
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
		req.setAnyDeviceModelCd(DisplayConstants.DP_ANY_PHONE_4MM);
		req.setTenantId(header.getTenantHeader().getTenantId());
		req.setLangCd(header.getTenantHeader().getLangCd());

		// 배치완료 기준일시 조회
		String stdDt = this.displayCommonService.getBatchStandardDateString(req.getTenantId(), listId);

		if (StringUtils.isEmpty(stdDt)) {
			throw new StorePlatformException("SAC_DSP_0002", "stdDt", stdDt);
		} else {
			req.setStdDt(stdDt);
		}

		// prodGradeCd 배열로 변경
		if (!StringUtils.isEmpty(req.getProdGradeCd())) {
			// prodGradeCd encode 처리(테넌트에서 인코딩하여 넘길 시 제거 필요)
			// try {
			// req.setProdGradeCd(URLEncoder.encode(req.getProdGradeCd(), "UTF-8"));
			// } catch (Exception ex) {
			// throw new StorePlatformException("SAC_DSP_9999", ex);
			// }

			String[] prodGradeCdArr = StringUtils.split(req.getProdGradeCd(), "+");
			req.setProdGradeCdArr(prodGradeCdArr);
		}

		List<ProductBasicInfo> productBasicInfoList;

		// TGR000000002 운영자 신규
		// ADM000000031 : 영화 추천, ADM000000037 : 영화 1,000원관
		// ADM000000033 : 방송 추천 미드/외화, ADM000000034 : 방송 추천 드라마, ADM000000035 : 방송 추천 연예/오락, ADM000000036 : 방송 추천 애니/키즈,
		// ADM000000041 : 방송 추천 케이블, ADM000000042 : 방송 추천 스페셜
		// DP17 : 영화, DP18 : 방송
		if ("TGR000000002".equals(listId)) {
			if ("mbc".equals(filteredBy) || "kbs".equals(filteredBy) || "sbs".equals(filteredBy)) {
				this.logger.debug("----------------------------------------------------------------");
				this.logger.debug("방송 > 방송사별 최신 UP 상품 조회");
				this.logger.debug("----------------------------------------------------------------");

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
		} else {
			this.logger.debug("----------------------------------------------------------------");
			this.logger.debug("영화/방송 > 추천 상품 조회");
			this.logger.debug("----------------------------------------------------------------");

			// 추천 리스트 조회
			productBasicInfoList = this.commonDAO.queryForList("FeatureCategory.selectFeatureVodList", req,
					ProductBasicInfo.class);
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
				MetaInfo retMetaInfo = this.metaInfoService.getVODMetaInfo(reqMap);

				if (retMetaInfo != null) {
					// Response Generate
					if ("DP17".equals(topMenuId)) {
						Product product = this.responseInfoGenerateFacade.generateMovieProduct(retMetaInfo);
						productList.add(product);
					} else {
						Product product = this.responseInfoGenerateFacade.generateBroadcastProduct(retMetaInfo);
						productList.add(product);
					}

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
