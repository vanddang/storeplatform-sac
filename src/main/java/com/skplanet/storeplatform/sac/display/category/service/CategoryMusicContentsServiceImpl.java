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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.skplanet.storeplatform.framework.core.exception.StorePlatformException;
import com.skplanet.storeplatform.framework.core.persistence.dao.CommonDAO;
import com.skplanet.storeplatform.framework.core.util.StringUtils;
import com.skplanet.storeplatform.sac.client.display.vo.music.MusicContentsSacReq;
import com.skplanet.storeplatform.sac.client.display.vo.music.MusicContentsSacRes;
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
 * Music Contents Service 인터페이스(CoreStoreBusiness)
 * 
 * Updated on : 2013. 12. 24. Updated by : 윤주영, SK 플래닛.
 */
@Service
public class CategoryMusicContentsServiceImpl implements CategoryMusicContentsService {

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
	 * @see com.skplanet.storeplatform.sac.biz.product.service.MusicContentsListService#searchMusicContentsList(
	 * MusicContentsRequest requestVO)
	 */
	@Override
	public MusicContentsSacRes searchMusicContentsList(MusicContentsSacReq requestVO, SacRequestHeader requestHeader) {

		// int totalCount = 0;

		String filteredBy; // 차트 구분 코드
		String orderedBy;

		filteredBy = requestVO.getFilteredBy(); // 차트 구분 코드
		orderedBy = requestVO.getOrderedBy();

		// 헤더값 세팅
		requestVO.setTenantId(requestHeader.getTenantHeader().getTenantId());
		requestVO.setDeviceModelCd(requestHeader.getDeviceHeader().getModel());
		requestVO.setAnyDeviceModelCd(DisplayConstants.DP_ANY_PHONE_4MM);
		requestVO.setLangCd(requestHeader.getTenantHeader().getLangCd());

		// 시작점 ROW Default 세팅
		if (requestVO.getOffset() == null) {
			requestVO.setOffset(1);
		}
		// 페이지당 노출될 ROW 개수 Default 세팅
		if (requestVO.getCount() == null) {
			requestVO.setCount(20);
		}

		MusicContentsSacRes responseVO = new MusicContentsSacRes();
		CommonResponse commonResponse = new CommonResponse();

		// filteredBy 필수 파라미터 체크
		// if (StringUtils.isEmpty(filteredBy)) {
		// throw new StorePlatformException("SAC_DSP_0002", "filteredBy", filteredBy);
		// }

		// tenantId 필수 파라미터 체크
		// if (StringUtils.isEmpty(requestVO.getTenantId())) {
		// throw new StorePlatformException("SAC_DSP_0002", "tenantId", requestVO.getTenantId());
		// }

		// 챠트구분, 배치ID 세팅
		if (filteredBy.equals("top")) { // TOP 뮤직
			requestVO.setChartClsfCd("DP004901");
			requestVO.setBatchId("MELON_DP004901");
		} else if (filteredBy.equals("recent")) { // 최신음악
			requestVO.setChartClsfCd("DP004904");
			requestVO.setBatchId("MELON_DP004904");
		} else if (filteredBy.equals("genre")) { // 장르별
			requestVO.setChartClsfCd("DP004905");
			requestVO.setBatchId("MELON_DP004905");
			if (null == orderedBy || "".equals(orderedBy)) {
				requestVO.setOrderedBy("popular");
			}
		} else if (filteredBy.equals("bell")) { // 벨소리
			requestVO.setChartClsfCd("DP004903");
			requestVO.setBatchId("MELON_DP004903");
		} else if (filteredBy.equals("ring")) { // 컬러링
			requestVO.setChartClsfCd("DP004902");
			requestVO.setBatchId("MELON_DP004902");
		} else if (filteredBy.equals("mainTop")) { // 메인TOP
			requestVO.setBatchId("RNK000000025");
			requestVO.setListId("RNK000000025");
		}

		// 웹 랭킹 음원일 경우 stdDt 넘어옴
		String stdDt = requestVO.getStdDt();
		if (StringUtils.isEmpty(stdDt)) {
			// 배치완료 기준일시 조회
			stdDt = this.displayCommonService.getBatchStandardDateString(requestVO.getTenantId(),
					requestVO.getBatchId());

			// 기준일시 체크
			if (StringUtils.isEmpty(stdDt)) {
				throw new StorePlatformException("SAC_DSP_0002", "stdDt", stdDt);
			} else {
				if (!filteredBy.equals("mainTop")) {
					// 뮤직 챠트 배치일자는 년월일만 필요
					requestVO.setStdDt(stdDt.substring(0, 8));
				} else {
					// 메인TOP 의 경우는 stdDt 전체 필요
					requestVO.setStdDt(stdDt);
				}
			}
		}

		List<ProductBasicInfo> productBasicInfoList;

		if (filteredBy.equals("mainTop")) {
			productBasicInfoList = this.commonDAO.queryForList("MusicMain.getMusicMainTopList", requestVO,
					ProductBasicInfo.class);
		} else {
			productBasicInfoList = this.commonDAO.queryForList("MusicMain.getMusicMainList", requestVO,
					ProductBasicInfo.class);
		}

		List<Product> productList = new ArrayList<Product>();

		// Meta DB 조회 파라미터 생성
		Map<String, Object> reqMap = new HashMap<String, Object>();
		TenantHeader tenantHeader = requestHeader.getTenantHeader();
		DeviceHeader deviceHeader = requestHeader.getDeviceHeader();
		reqMap.put("req", requestVO);
		reqMap.put("tenantHeader", tenantHeader);
		reqMap.put("deviceHeader", deviceHeader);
		reqMap.put("stdDt", requestVO.getStdDt());
		reqMap.put("lang", tenantHeader.getLangCd());
		reqMap.put("chartClsfCd", requestVO.getChartClsfCd());

		reqMap.put("imageCd", DisplayConstants.DP_MUSIC_REPRESENT_IMAGE_CD);
		reqMap.put("svcGrpCd", DisplayConstants.DP_MULTIMEDIA_PROD_SVC_GRP_CD);
		reqMap.put("prodStatusCd", DisplayConstants.DP_SALE_STAT_ING);
		reqMap.put("contentTypeCd", DisplayConstants.DP_EPISODE_CONTENT_TYPE_CD);

		if (productBasicInfoList != null && productBasicInfoList.size() > 0) {
			for (ProductBasicInfo productBasicInfo : productBasicInfoList) {
				reqMap.put("productBasicInfo", productBasicInfo);

				// Meta 정보 조회
				MetaInfo retMetaInfo = this.metaInfoService.getMusicMetaInfo(reqMap);

				if (retMetaInfo != null) {
					// Response Generate
					Product product = this.responseInfoGenerateFacade.generateMusicProduct(retMetaInfo);
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
