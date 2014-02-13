/*
 * Copyright (c) 2013 SK planet.
 * All right reserved.
 *
 * This software is the confidential and proprietary information of SK planet.
 * You shall not disclose such Confidential Information and
 * shall use it only in accordance with the terms of the license agreement
 * you entered into with SK planet.
 */
package com.skplanet.storeplatform.sac.display.feature.recommend.service;

import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import com.skplanet.storeplatform.framework.core.exception.StorePlatformException;
import com.skplanet.storeplatform.framework.core.persistence.dao.CommonDAO;
import com.skplanet.storeplatform.framework.core.util.StringUtils;
import com.skplanet.storeplatform.sac.client.display.vo.feature.recommend.RecommendAdminSacReq;
import com.skplanet.storeplatform.sac.client.display.vo.feature.recommend.RecommendAdminSacRes;
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
 * Updated on : 2013. 12. 19. Updated by : 서영배, GTSOFT.
 */
@org.springframework.stereotype.Service
public class RecommendAdminServiceImpl implements RecommendAdminService {

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
	public RecommendAdminSacRes searchAdminList(RecommendAdminSacReq requestVO, SacRequestHeader header) {
		// TODO Auto-generated method stub

		// 공통 응답 변수 선언
		// int totalCount = 0;
		RecommendAdminSacRes responseVO = new RecommendAdminSacRes();
		CommonResponse commonResponse = new CommonResponse();

		// 헤더값 세팅
		requestVO.setTenantId(header.getTenantHeader().getTenantId());
		requestVO.setDeviceModelCd(header.getDeviceHeader().getModel());
		requestVO.setLangCd(header.getTenantHeader().getLangCd());

		if (StringUtils.isEmpty(requestVO.getTenantId()))
			requestVO.setTenantId("S01");
		if (StringUtils.isEmpty(requestVO.getDeviceModelCd()))
			requestVO.setDeviceModelCd("SHW-M180L");
		if (StringUtils.isEmpty(requestVO.getListId()))
			requestVO.setListId("ADM000000013");
		if (StringUtils.isEmpty(requestVO.getTopMenuId()))
			requestVO.setTopMenuId("DP01");

		// tenantId 필수 파라미터 체크
		if (StringUtils.isEmpty(requestVO.getTenantId())) {
			throw new StorePlatformException("SAC_DSP_0002", "tenantId", requestVO.getTenantId());
		}
		// listId 필수 파라미터 체크
		if (StringUtils.isEmpty(requestVO.getListId())) {
			throw new StorePlatformException("SAC_DSP_0002", "listId", requestVO.getListId());
		}
		// topMenuId 필수 파라미터 체크
		if (StringUtils.isEmpty(requestVO.getTopMenuId())) {
			throw new StorePlatformException("SAC_DSP_0002", "topMenuId", requestVO.getTopMenuId());
		}

		// 리스트ID 유효값 체크
		if (!"ADM000000013".equals(requestVO.getListId())) {
			throw new StorePlatformException("SAC_DSP_0003", "listId", requestVO.getListId());
		}

		// 시작점 ROW Default 세팅
		if (requestVO.getOffset() == 0) {
			requestVO.setOffset(1);
		}
		// 페이지당 노출될 ROW 개수 Default 세팅
		if (requestVO.getCount() == 0) {
			requestVO.setCount(20);
		}

		// 배치완료 기준일시 조회
		String stdDt = this.displayCommonService.getBatchStandardDateString(requestVO.getTenantId(),
				requestVO.getListId());

		// 기준일시 체크
		if (StringUtils.isEmpty(stdDt)) {
			throw new StorePlatformException("SAC_DSP_0002", "stdDt", stdDt);
		} else {
			requestVO.setStdDt(stdDt);
		}

		// topMenuId encode 처리(테넌트에서 인코딩하여 넘길 시 encode 제거 필요)
		if (!StringUtils.isEmpty(requestVO.getTopMenuId())) {
			try {
				requestVO.setTopMenuId(URLEncoder.encode(requestVO.getTopMenuId(), "UTF-8"));
			} catch (Exception ex) {
				throw new StorePlatformException("SAC_DSP_9999", ex);
			}

			// topMenuId 배열로 변경
			String[] topMenuIdArr = requestVO.getTopMenuId().split("\\+");
			requestVO.setTopMenuIdArr(topMenuIdArr);
		}

		// prodGradeCd encode 처리(테넌트에서 인코딩하여 넘길 시 encode 제거 필요)
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

		List<ProductBasicInfo> productBasicInfoList = this.commonDAO.queryForList(
				"FeatureRecommend.selectRecommendAdminList", requestVO, ProductBasicInfo.class);

		List<Product> productList = new ArrayList<Product>();

		// DB 조회 파라미터 생성
		Map<String, Object> reqMap = new HashMap<String, Object>();
		TenantHeader tenantHeader = header.getTenantHeader();
		DeviceHeader deviceHeader = header.getDeviceHeader();
		reqMap.put("req", requestVO);
		reqMap.put("tenantHeader", tenantHeader);
		reqMap.put("deviceHeader", deviceHeader);
		reqMap.put("stdDt", stdDt);
		reqMap.put("lang", tenantHeader.getLangCd());

		reqMap.put("imageCd", DisplayConstants.DP_APP_REPRESENT_IMAGE_CD);
		reqMap.put("svcGrpCd", DisplayConstants.DP_APP_PROD_SVC_GRP_CD);
		reqMap.put("prodStatusCd", DisplayConstants.DP_SALE_STAT_ING);

		if (productBasicInfoList != null && productBasicInfoList.size() > 0) {
			for (ProductBasicInfo productBasicInfo : productBasicInfoList) {
				reqMap.put("productBasicInfo", productBasicInfo);

				// App Meta 정보 조회
				MetaInfo retMetaInfo = this.metaInfoService.getAppMetaInfo(reqMap);

				if (retMetaInfo != null) {
					// App Response Generate
					Product product = this.responseInfoGenerateFacade.generateAppProduct(retMetaInfo);
					productList.add(product);
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
