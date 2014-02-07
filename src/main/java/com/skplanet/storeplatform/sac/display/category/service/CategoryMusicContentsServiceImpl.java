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

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.map.JsonMappingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.skplanet.storeplatform.framework.core.exception.StorePlatformException;
import com.skplanet.storeplatform.framework.core.persistence.dao.CommonDAO;
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
	public MusicContentsSacRes searchMusicContentsList(MusicContentsSacReq requestVO, SacRequestHeader requestHeader)
			throws JsonGenerationException, JsonMappingException, IOException, Exception {

		// int totalCount = 0;

		String filteredBy; // 차트 구분 코드
		String orderedBy;
		String menuId;
		int offset; // 시작점 ROW
		int count; // 페이지당 노출 ROW 수

		filteredBy = requestVO.getFilteredBy(); // 차트 구분 코드
		orderedBy = requestVO.getOrderedBy();
		menuId = requestVO.getMenuId();

		// 헤더값 세팅
		requestVO.setTenantId(requestHeader.getTenantHeader().getTenantId());
		requestVO.setDeviceModelCd(requestHeader.getDeviceHeader().getModel());
		requestVO.setLangCd(requestHeader.getTenantHeader().getLangCd());

		offset = requestVO.getOffset(); // 시작점 ROW
		count = requestVO.getCount(); // 페이지당 노출 ROW 수

		// rownum 디폴트값 세팅
		if (offset <= 0) {
			offset = 1;
			requestVO.setOffset(offset);
		}
		if (count <= 0) {
			count = 20;
			requestVO.setCount(count);
		}

		MusicContentsSacRes responseVO = new MusicContentsSacRes();
		CommonResponse commonResponse = new CommonResponse();

		// filteredBy 필수 파라미터 체크
		if (StringUtils.isEmpty(filteredBy)) {
			throw new StorePlatformException("SAC_DSP_0002", "filteredBy", filteredBy);
		}

		// tenantId 필수 파라미터 체크
		if (StringUtils.isEmpty(requestVO.getTenantId())) {
			throw new StorePlatformException("SAC_DSP_0002", "tenantId", requestVO.getTenantId());
		}

		if (null == menuId || "".equals(menuId)) {
			requestVO.setMenuId(DisplayConstants.DP_MUSIC_TOP_MENU_ID);
		}

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
			if (orderedBy.equals("") || orderedBy == null) {
				requestVO.setOrderedBy("popular");
			}
		}

		// 배치완료 기준일시 조회
		String stdDt = this.displayCommonService.getBatchStandardDateString(requestVO.getTenantId(),
				requestVO.getBatchId());

		// 기준일시 체크
		if (StringUtils.isEmpty(stdDt)) {
			throw new StorePlatformException("SAC_DSP_0002", "stdDt", stdDt);
		} else {
			// 뮤직 배치일자는 년월일만 필요
			requestVO.setStdDt(stdDt.substring(0, 8));
		}

		List<ProductBasicInfo> productBasicInfoList;

		productBasicInfoList = this.commonDAO.queryForList("MusicMain.getMusicMainList", requestVO,
				ProductBasicInfo.class);

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
