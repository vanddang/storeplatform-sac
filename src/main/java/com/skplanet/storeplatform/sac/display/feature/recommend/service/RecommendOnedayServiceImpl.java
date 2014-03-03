package com.skplanet.storeplatform.sac.display.feature.recommend.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.skplanet.storeplatform.framework.core.exception.StorePlatformException;
import com.skplanet.storeplatform.framework.core.persistence.dao.CommonDAO;
import com.skplanet.storeplatform.framework.core.util.StringUtils;
import com.skplanet.storeplatform.sac.client.display.vo.feature.recommend.RecommendOnedaySacReq;
import com.skplanet.storeplatform.sac.client.display.vo.feature.recommend.RecommendOnedaySacRes;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.common.CommonResponse;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.product.Product;
import com.skplanet.storeplatform.sac.common.header.vo.DeviceHeader;
import com.skplanet.storeplatform.sac.common.header.vo.SacRequestHeader;
import com.skplanet.storeplatform.sac.common.header.vo.TenantHeader;
import com.skplanet.storeplatform.sac.display.common.constant.DisplayConstants;
import com.skplanet.storeplatform.sac.display.feature.recommend.vo.RecommendOneday;
import com.skplanet.storeplatform.sac.display.meta.service.MetaInfoService;
import com.skplanet.storeplatform.sac.display.meta.vo.MetaInfo;
import com.skplanet.storeplatform.sac.display.response.ResponseInfoGenerateFacade;

/**
 * 
 * 
 * Updated on : 2014. 2. 28. Updated by : 이승훈, nTels.
 */
@Service
public class RecommendOnedayServiceImpl implements RecommendOnedayService {

	// private transient Logger log = LoggerFactory.getLogger(CategoryAppServiceImpl.class);

	@Autowired
	@Qualifier("sac")
	private CommonDAO commonDAO;

	@Autowired
	private ResponseInfoGenerateFacade responseInfoGenerateFacade;

	@Autowired
	private MetaInfoService metaInfoService;

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.skplanet.storeplatform.sac.product.service.TotalRecommendService#searchTotalRecommendList(java.lang.String,
	 * java.lang.String, java.lang.String, java.lang.String, java.lang.String, int, int)
	 */
	@Override
	public RecommendOnedaySacRes searchOnedayList(RecommendOnedaySacReq requestVO, SacRequestHeader header) {
		// TODO Auto-generated method stub
		// 공통 응답 변수 선언
		RecommendOnedaySacRes responseVO = new RecommendOnedaySacRes();
		CommonResponse commonResponse = new CommonResponse();

		// 헤더값 세팅
		requestVO.setTenantId(header.getTenantHeader().getTenantId());
		requestVO.setDeviceModelCd(header.getDeviceHeader().getModel());
		requestVO.setLangCd(header.getTenantHeader().getLangCd());

		// tenantId 필수 파라미터 체크
		if (StringUtils.isEmpty(requestVO.getTenantId())) {
			throw new StorePlatformException("SAC_DSP_0002", "tenantId", requestVO.getTenantId());
		}

		// 시작점 ROW Default 세팅
		if (requestVO.getOffset() == null) {
			requestVO.setOffset(1);
		}
		// 페이지당 노출될 ROW 개수 Default 세팅
		if (requestVO.getCount() == null) {
			requestVO.setCount(20);
		}
		// 상품 등급 코드 설정
		if (StringUtils.isNotEmpty(requestVO.getProdGradeCd())) {
			String[] arrayProdGradeCd = requestVO.getProdGradeCd().split("\\+");
			for (int i = 0; i < arrayProdGradeCd.length; i++) {
				if (StringUtils.isNotEmpty(arrayProdGradeCd[i])) {
					if (!"PD004401".equals(arrayProdGradeCd[i]) && !"PD004402".equals(arrayProdGradeCd[i])
							&& !"PD004403".equals(arrayProdGradeCd[i])) {
						throw new StorePlatformException("SAC_DSP_0003", (i + 1) + " 번째 prodGradeCd",
								arrayProdGradeCd[i]);
					}
				}
			}
		}

		// '+'로 연결 된 상품등급코드를 배열로 전달
		if (StringUtils.isNotEmpty(requestVO.getProdGradeCd())) {
			String[] arrayProdGradeCd = requestVO.getProdGradeCd().split("\\+");
			requestVO.setArrayProdGradeCd(arrayProdGradeCd);
		}

		// 검색조건 설정 없을때 A로 설정
		if (StringUtils.isEmpty(requestVO.getSearchType())) {
			requestVO.setSearchType("A");
		}
		// 검색시간 설정
		List<String> periodList = Arrays.asList(StringUtils.split(requestVO.getPeriod(), "/"));
		if (periodList.size() > 0) {
			int findStirng = requestVO.getPeriod().indexOf("/");

			String periodStart = "";
			String periodEnd = "";
			if (requestVO.getPeriod().length() < 25) {
				if (findStirng == 0) {
					periodEnd = periodList.get(0);
					periodEnd = periodEnd.replace("T", "");
					periodEnd = periodEnd.substring(0, 14);
					requestVO.setPeriodEnd(periodEnd);

				} else {
					periodStart = periodList.get(0);
					periodStart = periodStart.replace("T", "");
					periodStart = periodStart.substring(0, 14);
					requestVO.setPeriodStart(periodStart);

				}
			} else {
				periodStart = periodList.get(0);
				periodEnd = periodList.get(1);

				periodStart = periodStart.replace("T", "");
				periodStart = periodStart.substring(0, 14);
				requestVO.setPeriodStart(periodStart);

				periodEnd = periodEnd.replace("T", "");
				periodEnd = periodEnd.substring(0, 14);
				requestVO.setPeriodEnd(periodEnd);

			}
		}
		List<RecommendOneday> recommendOnedayList;

		recommendOnedayList = this.commonDAO.queryForList("FeatureRecommend.selectRecommendOnedayList", requestVO,
				RecommendOneday.class);

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

		if (!recommendOnedayList.isEmpty()) {

			Product product = null;

			for (RecommendOneday recommendOneday : recommendOnedayList) {
				String topMenuId = recommendOneday.getTopMenuId();
				String svcGrpCd = recommendOneday.getSvcGrpCd();

				reqMap.put("productBasicInfo", recommendOneday);
				reqMap.put("req", reqMap);
				reqMap.put("tenantHeader", tenantHeader);
				reqMap.put("deviceHeader", deviceHeader);
				reqMap.put("lang", tenantHeader.getLangCd());

				product = new Product(); // 결과물

				MetaInfo retMetaInfo = null;

				// APP 상품의 경우
				if (DisplayConstants.DP_APP_PROD_SVC_GRP_CD.equals(svcGrpCd)) {
					reqMap.put("imageCd", DisplayConstants.DP_APP_REPRESENT_IMAGE_CD);
					retMetaInfo = this.metaInfoService.getAppMetaInfo(reqMap);
					if (retMetaInfo != null) {
						product = this.responseInfoGenerateFacade.generateBroadcastProduct(retMetaInfo);
						productList.add(product);
					}

				} else if (DisplayConstants.DP_MULTIMEDIA_PROD_SVC_GRP_CD.equals(svcGrpCd)) { // 멀티미디어 타입일 경우
					// 영화/방송 상품의 경우
					reqMap.put("imageCd", DisplayConstants.DP_VOD_REPRESENT_IMAGE_CD);
					if (DisplayConstants.DP_MOVIE_TOP_MENU_ID.equals(topMenuId)
							|| DisplayConstants.DP_TV_TOP_MENU_ID.equals(topMenuId)) {
						retMetaInfo = this.metaInfoService.getVODMetaInfo(reqMap);

						if (retMetaInfo != null) {
							product = this.responseInfoGenerateFacade.generateMovieProduct(retMetaInfo);
							productList.add(product);
						}
					} else if (DisplayConstants.DP_EBOOK_TOP_MENU_ID.equals(topMenuId)
							|| DisplayConstants.DP_COMIC_TOP_MENU_ID.equals(topMenuId)) { // Ebook / Comic 상품의
																						  // 경우
						retMetaInfo = this.metaInfoService.getEbookComicMetaInfo(reqMap);
						if (retMetaInfo != null) {
							if (DisplayConstants.DP_EBOOK_TOP_MENU_ID.equals(topMenuId)) {
								product = this.responseInfoGenerateFacade.generateEbookProduct(retMetaInfo);
								productList.add(product);
							} else {
								product = this.responseInfoGenerateFacade.generateComicProduct(retMetaInfo);
								productList.add(product);
							}
						}

					} else if (DisplayConstants.DP_MUSIC_TOP_MENU_ID.equals(topMenuId)) { // 음원 상품의 경우
						retMetaInfo = this.metaInfoService.getMusicMetaInfo(reqMap);
						if (retMetaInfo != null) {
							// product = this.responseInfoGenerateFacade.generateSpecificMusicProduct(metaInfo);
							product = this.responseInfoGenerateFacade.generateMusicProduct(retMetaInfo);
							productList.add(product);
						}

					} else if (DisplayConstants.DP_WEBTOON_TOP_MENU_ID.equals(topMenuId)) { // WEBTOON 상품의 경우
						retMetaInfo = this.metaInfoService.getWebtoonMetaInfo(reqMap);
						if (retMetaInfo != null) {
							product = this.responseInfoGenerateFacade.generateWebtoonProduct(retMetaInfo);
							productList.add(product);
						}

					}
				} else if (DisplayConstants.DP_TSTORE_SHOPPING_PROD_SVC_GRP_CD.equals(svcGrpCd)) { // 쇼핑 상품의 경우
					retMetaInfo = this.metaInfoService.getShoppingMetaInfo(reqMap);
					if (retMetaInfo != null) {
						product = this.responseInfoGenerateFacade.generateShoppingProduct(retMetaInfo);
						productList.add(product);
					}
				}
			}
			commonResponse.setTotalCount(productList.size());
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
