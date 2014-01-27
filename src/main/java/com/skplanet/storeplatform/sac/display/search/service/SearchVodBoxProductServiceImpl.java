package com.skplanet.storeplatform.sac.display.search.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.skplanet.storeplatform.framework.core.persistence.dao.CommonDAO;
import com.skplanet.storeplatform.sac.api.conts.DisplayConstants;
import com.skplanet.storeplatform.sac.client.display.vo.search.SearchProductReq;
import com.skplanet.storeplatform.sac.client.display.vo.search.SearchProductRes;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.common.CommonResponse;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.product.Product;
import com.skplanet.storeplatform.sac.common.header.vo.DeviceHeader;
import com.skplanet.storeplatform.sac.common.header.vo.SacRequestHeader;
import com.skplanet.storeplatform.sac.common.header.vo.TenantHeader;
import com.skplanet.storeplatform.sac.display.common.service.DisplayCommonService;
import com.skplanet.storeplatform.sac.display.meta.service.MetaInfoService;
import com.skplanet.storeplatform.sac.display.meta.vo.MetaInfo;
import com.skplanet.storeplatform.sac.display.meta.vo.ProductBasicInfo;
import com.skplanet.storeplatform.sac.display.response.ResponseInfoGenerateFacade;

/**
 * Vod 상품 조회 Serivce 구현체.
 * 
 * Updated on : 2014. 1. 27. Updated by : 오승민, 인크로스.
 */
@Service
@Transactional
public class SearchVodBoxProductServiceImpl implements SearchVodBoxProductService {

	@Autowired
	@Qualifier("sac")
	private CommonDAO commonDAO;

	@Autowired
	private DisplayCommonService displayCommonService;

	@Autowired
	private MetaInfoService metaInfoService;

	@Autowired
	private ResponseInfoGenerateFacade responseInfoGenerateFacade;

	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.skplanet.storeplatform.sac.display.search.service.SearchVodBoxProductService#searchVodBoxProduct(com.skplanet
	 * .storeplatform.sac.client.display.vo.search.SearchProductReq,
	 * com.skplanet.storeplatform.sac.common.header.vo.SacRequestHeader)
	 */
	@Override
	public SearchProductRes searchVodBoxProduct(SearchProductReq req, SacRequestHeader header) {
		CommonResponse commonResponse = new CommonResponse();
		SearchProductRes res = new SearchProductRes();
		TenantHeader tenantHeader = header.getTenantHeader();
		DeviceHeader deviceHeader = header.getDeviceHeader();

		String tenantId = tenantHeader.getTenantId();
		String listId = req.getListId();

		// 배치완료 기준일시 조회
		String stdDt = this.displayCommonService.getBatchStandardDateString(tenantId, listId);

		// DB 조회 파라미터 생성
		Map<String, Object> reqMap = new HashMap<String, Object>();
		reqMap.put("req", req);
		reqMap.put("tenantHeader", tenantHeader);
		reqMap.put("deviceHeader", deviceHeader);
		reqMap.put("stdDt", stdDt);
		reqMap.put("lang", tenantHeader.getLangCd());

		// TODO osm1021 Dummy data 꼭 삭제할것!!!!!!!!!!
		reqMap.put("imageCd", "DP006206");

		reqMap.put("svcGrpCd", DisplayConstants.DP_MULTIMEDIA_PROD_SVC_GRP_CD);
		reqMap.put("etcCd", DisplayConstants.DP_MOVIE_ETC_CD);
		reqMap.put("contentTypeCd", DisplayConstants.DP_CHANNEL_CONTENT_TYPE_CD);
		reqMap.put("prodStatusCd", DisplayConstants.DP_SALE_STAT_ING);
		// ID list 조회
		List<ProductBasicInfo> productBasicInfoList = this.commonDAO.queryForList(
				"SearchVodBoxProduct.searchVodBoxProdId", reqMap, ProductBasicInfo.class);
		List<Product> productList = new ArrayList<Product>();

		if (productBasicInfoList != null) {
			for (ProductBasicInfo productBasicInfo : productBasicInfoList) {
				reqMap.put("productBasicInfo", productBasicInfo);
				// VOD Meta 정보 조회
				MetaInfo retMetaInfo = this.metaInfoService.getVODMetaInfo(reqMap);
				if (retMetaInfo != null) {
					// Movie Response Generate
					Product product = this.responseInfoGenerateFacade.generateMovieProduct(retMetaInfo);
					productList.add(product);
				}
			}
			commonResponse.setTotalCount(productList.size());
			res.setProductList(productList);
			res.setCommonResponse(commonResponse);
		} else {
			// 조회 결과 없음
			commonResponse.setTotalCount(0);
			res.setProductList(productList);
			res.setCommonResponse(commonResponse);
		}
		return res;
	}
}
