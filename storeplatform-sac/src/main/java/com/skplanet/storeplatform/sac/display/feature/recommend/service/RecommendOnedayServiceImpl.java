package com.skplanet.storeplatform.sac.display.feature.recommend.service;

import com.skplanet.storeplatform.framework.core.exception.StorePlatformException;
import com.skplanet.storeplatform.framework.core.persistence.dao.CommonDAO;
import com.skplanet.storeplatform.framework.core.util.StringUtils;
import com.skplanet.storeplatform.sac.client.display.vo.feature.recommend.RecommendOnedaySacReq;
import com.skplanet.storeplatform.sac.client.display.vo.feature.recommend.RecommendOnedaySacRes;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.common.*;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.common.Date;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.product.Product;
import com.skplanet.storeplatform.sac.common.header.vo.DeviceHeader;
import com.skplanet.storeplatform.sac.common.header.vo.SacRequestHeader;
import com.skplanet.storeplatform.sac.common.header.vo.TenantHeader;
import com.skplanet.storeplatform.sac.common.util.DateUtils;
import com.skplanet.storeplatform.sac.display.common.constant.DisplayConstants;
import com.skplanet.storeplatform.sac.display.feature.recommend.vo.RecommendOneday;
import com.skplanet.storeplatform.sac.display.meta.service.MetaInfoService;
import com.skplanet.storeplatform.sac.display.meta.vo.MetaInfo;
import com.skplanet.storeplatform.sac.display.meta.vo.ProductBasicInfo;
import com.skplanet.storeplatform.sac.display.response.ResponseInfoGenerateFacade;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * RecommendOnedayServiceImpl
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
		// 공통 응답 변수 선언
		RecommendOnedaySacRes responseVO = new RecommendOnedaySacRes();
		CommonResponse commonResponse = new CommonResponse();

		// 헤더값 세팅
		requestVO.setTenantId(header.getTenantHeader().getTenantId());
		requestVO.setDeviceModelCd(header.getDeviceHeader().getModel());
		requestVO.setLangCd(header.getTenantHeader().getLangCd());

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
		if (!StringUtils.isEmpty(requestVO.getSeq())) {
			requestVO.setSearchType("S");
		} else if (StringUtils.isEmpty(requestVO.getPeriod())) {
			requestVO.setSearchType("A");
		} else {
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
		}
		List<RecommendOneday> recommendOnedayList;

		recommendOnedayList = this.commonDAO.queryForList("FeatureRecommend.selectRecommendOnedayList", requestVO,
				RecommendOneday.class);

		List<Product> productList = new ArrayList<Product>();
		List<Date> dateList = null;

		if (!recommendOnedayList.isEmpty()) {

			Product product = null;
			Date date = null;

			for (RecommendOneday recommendOneday : recommendOnedayList) {
				String topMenuId = recommendOneday.getTopMenuId();
				String svcGrpCd = recommendOneday.getSvcGrpCd();

				ProductBasicInfo basicInfo = new ProductBasicInfo();
				basicInfo.setProdId(recommendOneday.getProdId());
				basicInfo.setMenuId(recommendOneday.getMenuId());
				basicInfo.setPartProdId(recommendOneday.getPartProdId());
				basicInfo.setTopMenuId(recommendOneday.getTopMenuId());
				basicInfo.setContentsTypeCd(recommendOneday.getContentsTypeCd());
				basicInfo.setTenantId(recommendOneday.getTenantId());

				product = new Product(); // 결과물

				MetaInfo retMetaInfo = null;

				// APP 상품의 경우
				if (DisplayConstants.DP_APP_PROD_SVC_GRP_CD.equals(svcGrpCd)) {
					retMetaInfo = this.metaInfoService.getAppMetaInfo( basicInfo );
					if (retMetaInfo != null) {
						retMetaInfo.setOneSeq(recommendOneday.getOneSeq());
						product = this.responseInfoGenerateFacade.generateAppProduct(retMetaInfo);
						// 하루에 하나 정보
						product.setPartChrgmonyAppYn(recommendOneday.getPartChrgmonyAppYn());
						product.setFreeItemAmt(recommendOneday.getFreeItemAmt());
						product.setProdRealreAmt(recommendOneday.getProdRealreAmt());
						product.setProdOffrAmt(recommendOneday.getProdOffrAmt());
						dateList = new ArrayList<Date>();
						date = new Date();
						date.setType("date/expo");
						date.setText(recommendOneday.getExpoDt());
						dateList.add(date);
						date = new Date();
						date.setType("date/expoStart");
						date.setTextFromDate(DateUtils.parseDate(recommendOneday.getExpoStartDt()));
						dateList.add(date);
						date = new Date();
						date.setType("date/expoEnd");
						date.setTextFromDate(DateUtils.parseDate(recommendOneday.getExpoEndDt()));
						dateList.add(date);
						product.setDateList(dateList);
						// svcgrpcd추가
						Menu menu = new Menu();
						List<Menu> menuList = product.getMenuList();
						menu.setType(DisplayConstants.DP_SVC_GRP_CD_TYPE);
						menu.setName(recommendOneday.getSvcGrpNm());
						menu.setId(recommendOneday.getSvcGrpCd());
						menuList.add(menu);
						product.setMenuList(menuList);
						productList.add(product);
					}

				} else if (DisplayConstants.DP_MULTIMEDIA_PROD_SVC_GRP_CD.equals(svcGrpCd)) { // 멀티미디어 타입일 경우
					// 영화/방송 상품의 경우
					if (DisplayConstants.DP_MOVIE_TOP_MENU_ID.equals(topMenuId)
							|| DisplayConstants.DP_TV_TOP_MENU_ID.equals(topMenuId)) {
						retMetaInfo = this.metaInfoService.getVODMetaInfo( basicInfo );

						if (retMetaInfo != null) {
							retMetaInfo.setOneSeq(recommendOneday.getOneSeq());
							if (DisplayConstants.DP_MOVIE_TOP_MENU_ID.equals(topMenuId)) {
								product = this.responseInfoGenerateFacade.generateMovieProduct(retMetaInfo);
							} else {
								product = this.responseInfoGenerateFacade.generateBroadcastProduct(retMetaInfo);
							}
							// 하루에 하나 정보
							product.setPartChrgmonyAppYn(recommendOneday.getPartChrgmonyAppYn());
							product.setFreeItemAmt(recommendOneday.getFreeItemAmt());
							product.setProdRealreAmt(recommendOneday.getProdRealreAmt());
							product.setProdOffrAmt(recommendOneday.getProdOffrAmt());
							dateList = new ArrayList<Date>();
							date = new Date();
							date.setType("date/expo");
							date.setText(recommendOneday.getExpoDt());
							dateList.add(date);
							date = new Date();
							date.setType("date/expoStart");
							date.setTextFromDate(DateUtils.parseDate(recommendOneday.getExpoStartDt()));
							dateList.add(date);
							date = new Date();
							date.setType("date/expoEnd");
							date.setTextFromDate(DateUtils.parseDate(recommendOneday.getExpoEndDt()));
							dateList.add(date);
							product.setDateList(dateList);
							// svcgrpcd추가
							Menu menu = new Menu();
							List<Menu> menuList = product.getMenuList();
							menu.setType(DisplayConstants.DP_SVC_GRP_CD_TYPE);
							menu.setName(recommendOneday.getSvcGrpNm());
							menu.setId(recommendOneday.getSvcGrpCd());
							menuList.add(menu);
							product.setMenuList(menuList);
							productList.add(product);
						}
					} else if (DisplayConstants.DP_EBOOK_TOP_MENU_ID.equals(topMenuId)
							|| DisplayConstants.DP_COMIC_TOP_MENU_ID.equals(topMenuId)) {
						// Ebook / Comic 상품의 경우
						retMetaInfo = this.metaInfoService.getEbookComicMetaInfo( basicInfo );
						if (retMetaInfo != null) {
							if (DisplayConstants.DP_EBOOK_TOP_MENU_ID.equals(topMenuId)) {
								retMetaInfo.setOneSeq(recommendOneday.getOneSeq());
								product = this.responseInfoGenerateFacade.generateEbookProduct(retMetaInfo);
								// 하루에 하나 정보
								product.setPartChrgmonyAppYn(recommendOneday.getPartChrgmonyAppYn());
								product.setFreeItemAmt(recommendOneday.getFreeItemAmt());
								product.setProdRealreAmt(recommendOneday.getProdRealreAmt());
								product.setProdOffrAmt(recommendOneday.getProdOffrAmt());
								dateList = new ArrayList<Date>();
								date = new Date();
								date.setType("date/expo");
								date.setText(recommendOneday.getExpoDt());
								dateList.add(date);
								date = new Date();
								date.setType("date/expoStart");
								date.setTextFromDate(DateUtils.parseDate(recommendOneday.getExpoStartDt()));
								dateList.add(date);
								date = new Date();
								date.setType("date/expoEnd");
								date.setTextFromDate(DateUtils.parseDate(recommendOneday.getExpoEndDt()));
								dateList.add(date);
								product.setDateList(dateList);
								// svcgrpcd추가
								Menu menu = new Menu();
								List<Menu> menuList = product.getMenuList();
								menu.setType(DisplayConstants.DP_SVC_GRP_CD_TYPE);
								menu.setName(recommendOneday.getSvcGrpNm());
								menu.setId(recommendOneday.getSvcGrpCd());
								menuList.add(menu);
								product.setMenuList(menuList);
								productList.add(product);
							} else {
								retMetaInfo.setOneSeq(recommendOneday.getOneSeq());
								product = this.responseInfoGenerateFacade.generateComicProduct(retMetaInfo);
								// 하루에 하나 정보
								product.setPartChrgmonyAppYn(recommendOneday.getPartChrgmonyAppYn());
								product.setFreeItemAmt(recommendOneday.getFreeItemAmt());
								product.setProdRealreAmt(recommendOneday.getProdRealreAmt());
								product.setProdOffrAmt(recommendOneday.getProdOffrAmt());
								dateList = new ArrayList<Date>();
                                date = new Date();
                                date.setType("date/expo");
                                date.setText(recommendOneday.getExpoDt());
                                dateList.add(date);
								date = new Date();
								date.setType("date/expoStart");
								date.setTextFromDate(DateUtils.parseDate(recommendOneday.getExpoStartDt()));
								dateList.add(date);
								date = new Date();
								date.setType("date/expoEnd");
								date.setTextFromDate(DateUtils.parseDate(recommendOneday.getExpoEndDt()));
								dateList.add(date);
								product.setDateList(dateList);
								// svcgrpcd추가
								Menu menu = new Menu();
								List<Menu> menuList = product.getMenuList();
								menu.setType(DisplayConstants.DP_SVC_GRP_CD_TYPE);
								menu.setName(recommendOneday.getSvcGrpNm());
								menu.setId(recommendOneday.getSvcGrpCd());
								menuList.add(menu);
								product.setMenuList(menuList);
								productList.add(product);
							}
						}

					} else if (DisplayConstants.DP_MUSIC_TOP_MENU_ID.equals(topMenuId)) { // 음원 상품의 경우
						retMetaInfo = this.metaInfoService.getMusicMetaInfo( basicInfo );
						if (retMetaInfo != null) {
							retMetaInfo.setOneSeq(recommendOneday.getOneSeq());
							product = this.responseInfoGenerateFacade.generateMusicProduct(retMetaInfo);
							// 하루에 하나 정보
							product.setPartChrgmonyAppYn(recommendOneday.getPartChrgmonyAppYn());
							product.setFreeItemAmt(recommendOneday.getFreeItemAmt());
							product.setProdRealreAmt(recommendOneday.getProdRealreAmt());
							product.setProdOffrAmt(recommendOneday.getProdOffrAmt());
							dateList = new ArrayList<Date>();
							date = new Date();
							date.setType("date/expo");
							date.setText(recommendOneday.getExpoDt());
							dateList.add(date);
							date = new Date();
							date.setType("date/expoStart");
							date.setTextFromDate(DateUtils.parseDate(recommendOneday.getExpoStartDt()));
							dateList.add(date);
							date = new Date();
							date.setType("date/expoEnd");
							date.setTextFromDate(DateUtils.parseDate(recommendOneday.getExpoEndDt()));
							dateList.add(date);
							product.setDateList(dateList);
							// svcgrpcd추가
							Menu menu = new Menu();
							List<Menu> menuList = product.getMenuList();
							menu.setType(DisplayConstants.DP_SVC_GRP_CD_TYPE);
							menu.setName(recommendOneday.getSvcGrpNm());
							menu.setId(recommendOneday.getSvcGrpCd());
							menuList.add(menu);
							product.setMenuList(menuList);
							productList.add(product);
						}

					} else if (DisplayConstants.DP_WEBTOON_TOP_MENU_ID.equals(topMenuId)) { // WEBTOON 상품의 경우
						retMetaInfo = this.metaInfoService.getWebtoonMetaInfo( basicInfo );
						if (retMetaInfo != null) {
							retMetaInfo.setOneSeq(recommendOneday.getOneSeq());
							product = this.responseInfoGenerateFacade.generateWebtoonProduct(retMetaInfo);
							// 하루에 하나 정보
							product.setPartChrgmonyAppYn(recommendOneday.getPartChrgmonyAppYn());
							product.setFreeItemAmt(recommendOneday.getFreeItemAmt());
							product.setProdRealreAmt(recommendOneday.getProdRealreAmt());
							product.setProdOffrAmt(recommendOneday.getProdOffrAmt());
							dateList = new ArrayList<Date>();
							date = new Date();
							date.setType("date/expo");
							date.setText(recommendOneday.getExpoDt());
							dateList.add(date);
							date = new Date();
							date.setType("date/expoStart");
							date.setTextFromDate(DateUtils.parseDate(recommendOneday.getExpoStartDt()));
							dateList.add(date);
							date = new Date();
							date.setType("date/expoEnd");
							date.setTextFromDate(DateUtils.parseDate(recommendOneday.getExpoEndDt()));
							dateList.add(date);
							product.setDateList(dateList);
							// svcgrpcd추가
							Menu menu = new Menu();
							List<Menu> menuList = product.getMenuList();
							menu.setType(DisplayConstants.DP_SVC_GRP_CD_TYPE);
							menu.setName(recommendOneday.getSvcGrpNm());
							menu.setId(recommendOneday.getSvcGrpCd());
							menuList.add(menu);
							product.setMenuList(menuList);
							productList.add(product);
						}

					}
				} else if (DisplayConstants.DP_TSTORE_SHOPPING_PROD_SVC_GRP_CD.equals(svcGrpCd)) { // 쇼핑 상품의 경우
					retMetaInfo = this.metaInfoService.getShoppingMetaInfo( basicInfo );
					if (retMetaInfo != null) {
						retMetaInfo.setOneSeq(recommendOneday.getOneSeq());
						product = this.responseInfoGenerateFacade.generateShoppingProduct(retMetaInfo);
						// 하루에 하나 정보
						product.setPartChrgmonyAppYn(recommendOneday.getPartChrgmonyAppYn());
						product.setFreeItemAmt(recommendOneday.getFreeItemAmt());
						product.setProdRealreAmt(recommendOneday.getProdRealreAmt());
						product.setProdOffrAmt(recommendOneday.getProdOffrAmt());
						dateList = new ArrayList<Date>();
						date = new Date();
						date.setType("date/expo");
						date.setText(recommendOneday.getExpoDt());
						dateList.add(date);
						date = new Date();
						date.setType("date/expoStart");
						date.setTextFromDate(DateUtils.parseDate(recommendOneday.getExpoStartDt()));
						dateList.add(date);
						date = new Date();
						date.setType("date/expoEnd");
						date.setTextFromDate(DateUtils.parseDate(recommendOneday.getExpoEndDt()));
						dateList.add(date);
						product.setDateList(dateList);
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

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.skplanet.storeplatform.sac.product.service.TotalRecommendService#searchTotalRecommendList(java.lang.String,
	 * java.lang.String, java.lang.String, java.lang.String, java.lang.String, int, int)
	 */
	@Override
	public RecommendOnedaySacRes searchOnedayInform(RecommendOnedaySacReq requestVO, SacRequestHeader header) {
		// 공통 응답 변수 선언
		RecommendOnedaySacRes responseVO = new RecommendOnedaySacRes();
		CommonResponse commonResponse = new CommonResponse();

		// 헤더값 세팅
		requestVO.setTenantId(header.getTenantHeader().getTenantId());
		requestVO.setDeviceModelCd(header.getDeviceHeader().getModel());
		requestVO.setLangCd(header.getTenantHeader().getLangCd());

		RecommendOneday recommendOnedayInform;

		recommendOnedayInform = this.commonDAO.queryForObject("FeatureRecommend.selectRecommendOnedayInform",
				requestVO, RecommendOneday.class);

		List<Product> productList = new ArrayList<Product>();

		if (recommendOnedayInform != null) {

			Product product = new Product();
			Title title = new Title();
			List<Source> sourceList = new ArrayList<Source>();
			Source source = new Source();

			// title 설정
			title.setText(recommendOnedayInform.getNotfctWrtgold());
			product.setTitle(title);

			// source 설정
			source.setUrl(recommendOnedayInform.getImageUrl());
			source.setType(DisplayConstants.DP_SOURCE_TYPE_THUMBNAIL);
			sourceList.add(source);
			product.setSourceList(sourceList);

			productList.add(product);

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
