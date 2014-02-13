package com.skplanet.storeplatform.sac.display.feature.recommend.service;

import java.net.URLEncoder;
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
import com.skplanet.storeplatform.sac.client.display.vo.feature.recommend.RecommendTodaySacReq;
import com.skplanet.storeplatform.sac.client.display.vo.feature.recommend.RecommendTodaySacRes;
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
 * Updated on : 2014. 2. 6. Updated by : 조준일, nTels.
 */
@Service
public class RecommendTodayServiceImpl implements RecommendTodayService {

	// private transient Logger log = LoggerFactory.getLogger(CategoryAppServiceImpl.class);

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
	public RecommendTodaySacRes searchTodayList(RecommendTodaySacReq requestVO, SacRequestHeader header) {
		// TODO Auto-generated method stub
		// 공통 응답 변수 선언
		RecommendTodaySacRes responseVO = new RecommendTodaySacRes();
		CommonResponse commonResponse = new CommonResponse();

		// 헤더값 세팅
		requestVO.setTenantId(header.getTenantHeader().getTenantId());
		requestVO.setDeviceModelCd(header.getDeviceHeader().getModel());
		requestVO.setLangCd(header.getTenantHeader().getLangCd());

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
			if ("DP16".equals(requestVO.getTopMenuId())) {
				// 뮤직 배치일자는 년월일만 필요
				// requestVO.setStdDt(stdDt.substring(0, 8));
				requestVO.setStdDt("20120710"); // 더미 데이터
			} else {
				requestVO.setStdDt(stdDt);
			}
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

		if ("DP16".equals(requestVO.getTopMenuId())) {
			productBasicInfoList = this.commonDAO.queryForList("FeatureRecommend.selectRecommendTodayMusicList",
					requestVO, ProductBasicInfo.class);
		} else {
			productBasicInfoList = this.commonDAO.queryForList("FeatureRecommend.selectRecommendTodayList", requestVO,
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
		reqMap.put("stdDt", requestVO.getStdDt());
		reqMap.put("lang", tenantHeader.getLangCd());

		// DP13 : eBook DP14 : 코믹 DP16 : 뮤직
		if ("DP16".equals(requestVO.getTopMenuId())) {
			reqMap.put("imageCd", DisplayConstants.DP_MUSIC_REPRESENT_IMAGE_CD);
		} else {
			reqMap.put("imageCd", DisplayConstants.DP_EBOOK_COMIC_REPRESENT_IMAGE_CD);
		}

		reqMap.put("svcGrpCd", DisplayConstants.DP_MULTIMEDIA_PROD_SVC_GRP_CD);
		reqMap.put("contentTypeCd", DisplayConstants.DP_CHANNEL_CONTENT_TYPE_CD);
		reqMap.put("prodStatusCd", DisplayConstants.DP_SALE_STAT_ING);

		if (productBasicInfoList != null && productBasicInfoList.size() > 0) {
			for (ProductBasicInfo productBasicInfo : productBasicInfoList) {
				reqMap.put("productBasicInfo", productBasicInfo);

				if ("DP16".equals(requestVO.getTopMenuId())) {
					// Meta 정보 조회
					MetaInfo retMetaInfo = this.metaInfoService.getMusicMetaInfo(reqMap);

					if (retMetaInfo != null) {
						// Response Generate
						Product product = this.responseInfoGenerateFacade.generateMusicProduct(retMetaInfo);
						productList.add(product);
					}
				} else {
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
