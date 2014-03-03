package com.skplanet.storeplatform.sac.display.banner.service;

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
import com.skplanet.storeplatform.sac.client.display.vo.banner.BannerSacReq;
import com.skplanet.storeplatform.sac.client.display.vo.banner.BannerSacRes;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.common.CommonResponse;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.common.Identifier;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.common.Menu;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.common.Url;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.product.Banner;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.product.Preview;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.product.SalesOption;
import com.skplanet.storeplatform.sac.common.header.vo.SacRequestHeader;
import com.skplanet.storeplatform.sac.display.banner.vo.BannerDefault;
import com.skplanet.storeplatform.sac.display.common.constant.DisplayConstants;
import com.skplanet.storeplatform.sac.display.common.service.DisplayCommonService;
import com.skplanet.storeplatform.sac.display.meta.service.MetaInfoService;
import com.skplanet.storeplatform.sac.display.meta.vo.MetaInfo;
import com.skplanet.storeplatform.sac.display.meta.vo.ProductBasicInfo;
import com.skplanet.storeplatform.sac.display.response.CommonMetaInfoGenerator;

/**
 * Banner Service 인터페이스(CoreStoreBusiness) 구현체
 * 
 * Updated on : 2014. 02. 21. Updated by : 이태희.
 */
@Service
public class BannerServceImpl implements BannerService {
	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	private final int BANNER_MAX_COUNT = 20; // 요청 가능한 배너 최대 개수
	private final int HOME_BANNER_COUNT = 12; // Home 배너 12개 (모바일웹 정사각형 배너)
	private final int GAME_BANNER_COUNT = 2; // 게임 배너 2개 (모바일웹 정사각형 배너)
	private final int FUN_BANNER_COUNT = 2; // Fun 배너 2개 (모바일웹 정사각형 배너)
	private final int LIFE_BANNER_COUNT = 2; // 생활/위치 배너 2개 (모바일웹 정사각형 배너)
	private final int EDU_BANNER_COUNT = 2; // 어학/교육 배너 2개 (모바일웹 정사각형 배너)

	private final int APPGUIDE_BANNER_COUNT = 2; // 앱가이드 배너 2개 (앱가이드 배너)
	private final int BESTAPP_BANNER_COUNT = 4; // BEST 앱 배너 2개 (앱가이드 배너)

	@Autowired
	@Qualifier("sac")
	private CommonDAO commonDAO;

	@Autowired
	private DisplayCommonService displayCommonService;

	@Autowired
	CommonMetaInfoGenerator commonMetaInfoGenerator;

	@Autowired
	MetaInfoService metaInfoService;

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.skplanet.storeplatform.sac.display.banner.service.BannerService#searchBannerList(com.skplanet.storeplatform
	 * .sac.common.header.vo.SacRequestHeader, com.skplanet.storeplatform.sac.client.display.vo.banner.BannerSacReq)
	 */
	@Override
	public BannerSacRes searchBannerList(SacRequestHeader header, BannerSacReq bannerReq) {
		BannerSacRes bannerRes = new BannerSacRes();
		String reqBnrMenuId = bannerReq.getBnrMenuId();
		String reqMobileWebExpoYn = bannerReq.getMobileWebExpoYn();
		String reqBnrExpoMenuId = bannerReq.getBnrExpoMenuId();
		String reqImgSizeCd = bannerReq.getImgSizeCd();

		this.logger.debug("----------------------------------------------------------------");
		this.logger.debug("[searchBannerList] reqBnrMenuId : {}", reqBnrMenuId);
		this.logger.debug("[searchBannerList] reqMobileWebExpoYn : {}", reqMobileWebExpoYn);
		this.logger.debug("[searchBannerList] reqBnrExpoMenuId : {}", reqBnrExpoMenuId);
		this.logger.debug("[searchBannerList] reqImgSizeCd : {}", reqImgSizeCd);
		this.logger.debug("----------------------------------------------------------------");

		int imgCount = 0;
		String imgSizeList[] = null;

		try {
			// 이미지 사이즈 코드 유효값 확인
			imgSizeList = reqImgSizeCd.split("\\+");

			if (imgSizeList == null || imgSizeList.length == 0) {
				// 실행에 필요한 파라미터가 유효하지 않습니다.
				throw new StorePlatformException("SAC_DSP_0003", "imgSizeCd", reqImgSizeCd);
			} else {
				for (int v = 0; v < imgSizeList.length; v++) {
					imgCount += Integer.parseInt(imgSizeList[v].split("\\/")[1]);
				}
			}
		} catch (Exception e) {
			// 실행에 필요한 파라미터가 유효하지 않습니다.
			throw new StorePlatformException("SAC_DSP_0003", "imgSizeCd", reqImgSizeCd);
		}

		if (imgCount > this.BANNER_MAX_COUNT) {
			// imgSizeCd 파라미터의 최대 허용 개수는 XX 개 입니다.
			throw new StorePlatformException("SAC_DSP_0004", "imgSizeCd", this.BANNER_MAX_COUNT);
		}

		// 헤더정보 세팅
		bannerReq.setTenantId(header.getTenantHeader().getTenantId());
		bannerReq.setLangCd(header.getTenantHeader().getLangCd());
		bannerReq.setDeviceModelCd(header.getDeviceHeader().getModel());
		bannerReq.setAnyDeviceModelCd(DisplayConstants.DP_ANY_PHONE_4MM);

		// ※모바일웹 직사각형, 모바일웹 상/하단, T플레이, 유료앱이무료 배너는 프로비저닝을 제외한다.
		// ※모바일웹 정사각형 배너는 Home 12개, 게임 2개, Fun 2개, 생활/위치 2개, 어학/교육 2개 총 20를 요청한다.
		// ※앱가이드 배너는 앱가이드 배너2개, 베스트앱 4개 총 6개를 요청한다.
		// ※앱가이드 배너는 배너유형이 상품모바일 배너일 경우 상품ID가 채널단위로 들어간다.

		String reqImgCd = null; // 요청 이미지
		Integer reqImgCnt = null; // 요청 이미지 개수

		String bnrMenuId = null; // 배너메뉴ID
		String bnrType = null; // 배너타입
		String prodId = null; // 상품ID
		String topMenuId = null; // 탑메뉴ID
		String stdDt = null; // 배치완료 기준일시
		String recommendId = null; // 추천ID
		String brandShopNo = null; // 브랜드샵 번호
		String themeId = null; // 테마추천ID

		int provCnt = 0; // 프로비저닝 건수
		int passCnt = 0; // 결과리스트에 담긴 배너 개수

		boolean homeBannerFullFlag = false; // 모바일웹 Home 배너 최대 개수 여부
		boolean gameBannerFullFlag = false; // 모바일웹 게임 배너 최대 개수 여부
		boolean funBannerFullFlag = false; // 모바일웹 Fun 배너 최대 개수 여부
		boolean lifeBannerFullFlag = false; // 모바일웹 생활/위치 배너 최대 개수 여부
		boolean eduBannerFullFlag = false; // 모바일웹 어학/교육 배너 최대 개수 여부

		boolean appGuideBannerFlag = false; // 앱가이드 배너 최대 개수 여부
		boolean bestAppBannerFlag = false; // BEST 앱 배너 최대 개수 여부

		MetaInfo metaInfo = null; // 메타정보 VO
		BannerDefault bannerDefault = null; // 배너VO
		List<BannerDefault> bannerList = null; // 배너리스트
		List<BannerDefault> recommendProdList = null; // 운영자추천 상품 리스트
		List<BannerDefault> brandShopProdList = null; // 브랜드샵 상품 리스트
		List<BannerDefault> themeProdList = null; // 앱가이드 테마추천 상품 리스트
		List<BannerDefault> prodList = null; // 앱가이드 상품 리스트
		List<BannerDefault> resultList = new ArrayList<BannerDefault>(); // 결과 리스트

		for (int i = 0; i < imgSizeList.length; i++) {
			passCnt = 0;
			reqImgCd = imgSizeList[i].split("\\/")[0]; // 요청 이미지 코드
			reqImgCnt = Integer.parseInt(imgSizeList[i].split("\\/")[1]); // 요청 이미지 건수

			// 배너리스트 조회
			bannerReq.setImgSizeCd(reqImgCd);
			bannerList = this.commonDAO.queryForList("Banner.searchBannerList", bannerReq, BannerDefault.class);

			for (int j = 0; j < bannerList.size(); j++) {
				bannerDefault = bannerList.get(j);
				bnrMenuId = bannerDefault.getBnrMenuId();
				bnrType = bannerDefault.getBnrInfoTypeCd();

				// 모바일웹 정사각형 배너
				if ("DP010999".equals(reqBnrMenuId)) {
					if ("DP010915".equals(bnrMenuId)) { // Home 12개
						if (homeBannerFullFlag) {
							continue;
						}
						if (passCnt == this.HOME_BANNER_COUNT) {
							passCnt = 0;
							homeBannerFullFlag = true;
							continue;
						}
					} else if ("DP010916".equals(bnrMenuId)) { // 게임 2개
						if (gameBannerFullFlag) {
							continue;
						}
						if (passCnt == this.GAME_BANNER_COUNT) {
							passCnt = 0;
							gameBannerFullFlag = true;
							continue;
						}
					} else if ("DP010917".equals(bnrMenuId)) { // Fun 2개
						if (funBannerFullFlag) {
							continue;
						}
						if (passCnt == this.FUN_BANNER_COUNT) {
							passCnt = 0;
							funBannerFullFlag = true;
							continue;
						}
					} else if ("DP010918".equals(bnrMenuId)) { // 생활/위치 2개
						if (lifeBannerFullFlag) {
							continue;
						}
						if (passCnt == this.LIFE_BANNER_COUNT) {
							passCnt = 0;
							lifeBannerFullFlag = true;
							continue;
						}
					} else if ("DP010919".equals(bnrMenuId)) { // 어학/교육 2개
						if (eduBannerFullFlag) {
							continue;
						}
						if (passCnt == this.EDU_BANNER_COUNT) {
							passCnt = 0;
							eduBannerFullFlag = true;
							continue;
						}
					}
				}
				// 앱가이드
				else if ("DP010912".equals(reqBnrMenuId)) {
					if ("DP010912".equals(bnrMenuId)) { // 앱가이드 2개
						if (appGuideBannerFlag) {
							continue;
						}
						if (passCnt == this.APPGUIDE_BANNER_COUNT) {
							passCnt = 0;
							appGuideBannerFlag = true;
							continue;
						}
					} else if ("DP010910".equals(bnrMenuId)) { // BEST 앱 4개
						if (bestAppBannerFlag) {
							continue;
						}
						if (passCnt == this.BESTAPP_BANNER_COUNT) {
							passCnt = 0;
							bestAppBannerFlag = true;
							continue;
						}
					}
				} else {
					// 요청건수 확인
					if (reqImgCnt == passCnt) {
						break;
					}
				}

				// 배너타입 : 상품 지정 입력
				if (DisplayConstants.DP_BANNER_PRODUCT_CD.equals(bnrType)) {
					prodId = bannerDefault.getBnrInfo();
					topMenuId = bannerDefault.getTopMenuId();

					// 앱가이드
					if ("DP010912".equals(bnrMenuId)) {
						bannerReq.setProdId(prodId);

						// 상품 리스트 조회 (앱가이드의 경우 배너정보에 채널 상품ID가 입력)
						prodList = this.commonDAO.queryForList("Banner.searchAppGuideProdList", bannerReq,
								BannerDefault.class);

						if (prodList != null && !prodList.isEmpty()) {
							for (int k = 0; k < prodList.size(); k++) {
								bannerReq.setProdId(prodList.get(k).getPartProdId());
								bannerReq.setTopMenuId(prodList.get(k).getTopMenuId());

								// 상품 프로비저닝
								provCnt = (Integer) this.commonDAO.queryForObject("Banner.getBannerProvisioing",
										bannerReq);

								if (provCnt > 0) {
									// 메타정보 조회
									metaInfo = this.getMetaInfo(header, bannerReq);

									if (metaInfo != null) {
										bannerDefault.setTopMenuId(metaInfo.getTopMenuId());
										bannerDefault.setTopMenuNm(metaInfo.getTopMenuNm());
										bannerDefault.setMenuId(metaInfo.getMenuId());
										bannerDefault.setMenuNm(metaInfo.getMenuNm());
										bannerDefault.setMetaClsfCd(metaInfo.getMetaClsfCd());
										bannerDefault.setSampleUrl(metaInfo.getScSamplUrl());
										bannerDefault.setSampleUrlHq(metaInfo.getSamplUrl());
										bannerDefault.setProdCaseCd(metaInfo.getProdCaseCd());

										++passCnt;
										resultList.add(bannerDefault);
										break;
									}
								}
							}
						}
					} else {
						bannerReq.setProdId(prodId);
						bannerReq.setTopMenuId(topMenuId);

						// 상품 프로비저닝
						provCnt = (Integer) this.commonDAO.queryForObject("Banner.getBannerProvisioing", bannerReq);

						if (provCnt > 0) {
							// 메타정보 조회
							metaInfo = this.getMetaInfo(header, bannerReq);

							if (metaInfo != null) {
								bannerDefault.setTopMenuId(metaInfo.getTopMenuId());
								bannerDefault.setTopMenuNm(metaInfo.getTopMenuNm());
								bannerDefault.setMenuId(metaInfo.getMenuId());
								bannerDefault.setMenuNm(metaInfo.getMenuNm());
								bannerDefault.setMetaClsfCd(metaInfo.getMetaClsfCd());
								bannerDefault.setSampleUrl(metaInfo.getScSamplUrl());
								bannerDefault.setSampleUrlHq(metaInfo.getSamplUrl());
								bannerDefault.setProdCaseCd(metaInfo.getProdCaseCd());

								++passCnt;
								resultList.add(bannerDefault);
							}
						}
					}
				}
				// 배너타입 : 운영자 임의 추천
				else if (DisplayConstants.DP_BANNER_ADMIN_RECOMM_CD.equals(bnrType)) {
					recommendId = bannerDefault.getBnrInfo();

					// 배치완료 기준일시 조회
					stdDt = this.displayCommonService.getBatchStandardDateString(bannerReq.getTenantId(), recommendId);

					// 기준일시 체크
					if (StringUtils.isNotEmpty(stdDt)) {
						bannerReq.setStdDt(stdDt);
						bannerReq.setRecommendId(recommendId);

						// 운영자 추천 상품 리스트 조회
						recommendProdList = this.commonDAO.queryForList("Banner.searchRecommendProdList", bannerReq,
								BannerDefault.class);

						if (recommendProdList != null && !recommendProdList.isEmpty()) {
							for (int k = 0; k < recommendProdList.size(); k++) {
								bannerReq.setProdId(recommendProdList.get(k).getPartProdId());
								bannerReq.setTopMenuId(recommendProdList.get(k).getTopMenuId());

								// 상품 프로비저닝
								provCnt = (Integer) this.commonDAO.queryForObject("Banner.getBannerProvisioing",
										bannerReq);

								if (provCnt > 0) {
									++passCnt;
									resultList.add(bannerDefault);
									break;
								}
							}
						}
					}
				}
				// 배너타입 : 특정 브랜드샵
				else if (DisplayConstants.DP_BANNER_SPECIFIC_BRANDSHOP_CD.equals(bnrType)) {
					brandShopNo = bannerDefault.getBnrInfo();
					bannerReq.setBrandShopNo(brandShopNo);

					// 브랜드샵 상품 리스트 조회
					brandShopProdList = this.commonDAO.queryForList("Banner.searchBrandShopProdList", bannerReq,
							BannerDefault.class);

					if (brandShopProdList != null && !brandShopProdList.isEmpty()) {
						for (int k = 0; k < brandShopProdList.size(); k++) {
							bannerReq.setProdId(brandShopProdList.get(k).getPartProdId());
							bannerReq.setTopMenuId(brandShopProdList.get(k).getTopMenuId());

							// 상품 프로비저닝
							provCnt = (Integer) this.commonDAO.queryForObject("Banner.getBannerProvisioing", bannerReq);

							if (provCnt > 0) {
								++passCnt;
								resultList.add(bannerDefault);
								break;
							}
						}
					}
				}
				// 배너타입 : 테마추천 리스트 연결 (앱가이드)
				else if (DisplayConstants.DP_BANNER_THEME_RECOMM_CD.equals(bnrType)) {
					themeId = bannerDefault.getBnrInfo();

					// 배치완료 기준일시 조회
					stdDt = this.displayCommonService.getBatchStandardDateString(bannerReq.getTenantId(), themeId);

					// 기준일시 체크
					if (StringUtils.isNotEmpty(stdDt)) {
						bannerReq.setStdDt(stdDt);
						bannerReq.setThemeId(themeId);

						// 테마추천 상품 리스트 조회
						themeProdList = this.commonDAO.queryForList("Banner.searchThemeRecommProdList", bannerReq,
								BannerDefault.class);

						if (themeProdList != null && !themeProdList.isEmpty()) {
							for (int k = 0; k < themeProdList.size(); k++) {
								bannerReq.setProdId(themeProdList.get(k).getPartProdId());
								bannerReq.setTopMenuId(themeProdList.get(k).getTopMenuId());

								// 상품 프로비저닝
								provCnt = (Integer) this.commonDAO.queryForObject("Banner.getBannerProvisioing",
										bannerReq);

								if (provCnt > 0) {
									++passCnt;
									resultList.add(bannerDefault);
									break;
								}
							}
						}
					}
				} else {
					++passCnt;
					resultList.add(bannerDefault);
				}
			}
		}

		// Response 생성
		bannerRes = this.generateResponse(resultList);
		return bannerRes;
	}

	/**
	 * <pre>
	 * 상품 메타정보 조회.
	 * </pre>
	 * 
	 * @param header
	 *            header
	 * @param bannerReq
	 *            bannerReq
	 * @return MetaInfo
	 */
	private MetaInfo getMetaInfo(SacRequestHeader header, BannerSacReq bannerReq) {
		String prodId = bannerReq.getProdId();
		String topMenuId = bannerReq.getTopMenuId();

		MetaInfo metaInfo = null; // 메타정보 VO
		ProductBasicInfo productInfo = new ProductBasicInfo(); // 메타정보 조회용 상품 파라미터
		Map<String, Object> paramMap = new HashMap<String, Object>(); // 메타정보 조회용 파라미터

		// 메타정보 조회를 위한 파라미터 세팅
		productInfo.setProdId(prodId);
		productInfo.setPartProdId(prodId);
		productInfo.setContentsTypeCd(DisplayConstants.DP_EPISODE_CONTENT_TYPE_CD);
		paramMap.put("prodRshpCd", DisplayConstants.DP_CHANNEL_EPISHODE_RELATIONSHIP_CD);
		paramMap.put("tenantHeader", header.getTenantHeader());
		paramMap.put("deviceHeader", header.getDeviceHeader());
		paramMap.put("prodStatusCd", DisplayConstants.DP_SALE_STAT_ING);
		paramMap.put("productBasicInfo", productInfo);

		// APP
		if (DisplayConstants.DP_GAME_TOP_MENU_ID.equals(topMenuId)
				|| DisplayConstants.DP_FUN_TOP_MENU_ID.equals(topMenuId)
				|| DisplayConstants.DP_LIFE_LIVING_TOP_MENU_ID.equals(topMenuId)
				|| DisplayConstants.DP_LANG_EDU_TOP_MENU_ID.equals(topMenuId)) {
			paramMap.put("imageCd", DisplayConstants.DP_APP_REPRESENT_IMAGE_CD);
			metaInfo = this.metaInfoService.getAppMetaInfo(paramMap);
		}
		// 이북 및 코믹
		else if (DisplayConstants.DP_EBOOK_TOP_MENU_ID.equals(topMenuId)
				|| DisplayConstants.DP_COMIC_TOP_MENU_ID.equals(topMenuId)) {
			paramMap.put("imageCd", DisplayConstants.DP_EBOOK_COMIC_REPRESENT_IMAGE_CD);
			metaInfo = this.metaInfoService.getEbookComicMetaInfo(paramMap);
		}
		// 영화 및 방송
		else if (DisplayConstants.DP_MOVIE_TOP_MENU_ID.equals(topMenuId)
				|| DisplayConstants.DP_TV_TOP_MENU_ID.equals(topMenuId)) {
			paramMap.put("imageCd", DisplayConstants.DP_VOD_REPRESENT_IMAGE_CD);
			metaInfo = this.metaInfoService.getVODMetaInfo(paramMap);
		}
		// Tstore 쇼핑
		else if (DisplayConstants.DP_SHOPPING_TOP_MENU_ID.equals(topMenuId)) {
			paramMap.put("imageCd", DisplayConstants.DP_SHOPPING_REPRESENT_IMAGE_CD);
			metaInfo = this.metaInfoService.getShoppingMetaInfo(paramMap);
		}

		return metaInfo;
	}

	/**
	 * <pre>
	 * 배너 Response 생성.
	 * </pre>
	 * 
	 * @param list
	 *            list
	 * @return BannerSacResㄴ
	 */
	private BannerSacRes generateResponse(List<BannerDefault> list) {
		String bnrType = null;
		String bnrInfo = null;
		String topMenuId = null;

		Banner banner = null;
		Preview preview = null;
		Url url = null;
		SalesOption salesOption = null;
		Menu menu = null;

		List<Identifier> identifierList = null;
		List<Menu> menuList = null;
		List<Banner> bannerList = new ArrayList<Banner>();

		BannerDefault bannerDf = null;
		MetaInfo metaInfo = new MetaInfo();
		BannerSacRes bannerRes = new BannerSacRes();
		CommonResponse commonResponse = new CommonResponse();

		if (list != null && !list.isEmpty()) {
			for (int i = 0; i < list.size(); i++) {
				bannerDf = list.get(i);
				bnrType = bannerDf.getBnrInfoTypeCd();
				bnrInfo = bannerDf.getBnrInfo();
				topMenuId = bannerDf.getTopMenuId();

				banner = new Banner();
				identifierList = new ArrayList<Identifier>();

				// 배너ID 정보
				identifierList.add(this.commonMetaInfoGenerator.generateIdentifier(
						DisplayConstants.DP_BANNER_IDENTIFIER_CD, bannerDf.getBnrSeq()));

				// 배너 이미지사이즈 코드 정보
				banner.setImgSizeCd(bannerDf.getImgSizeCd());

				// 배너제목 정보
				metaInfo.setProdNm(bannerDf.getBnrNm());
				banner.setTitle(this.commonMetaInfoGenerator.generateTitle(metaInfo));

				// 이미지 정보
				metaInfo.setImagePath(bannerDf.getImgPath());
				banner.setSourceList(this.commonMetaInfoGenerator.generateBannerSourceList(metaInfo));

				// 배너타입 : 상품 지정 입력
				if (DisplayConstants.DP_BANNER_PRODUCT_CD.equals(bnrType)) {
					// 배너유형 정보
					banner.setType(DisplayConstants.DP_BANNER_TYPE_PRODUCT);

					// 상품ID 정보
					identifierList.add(this.commonMetaInfoGenerator.generateIdentifier(
							DisplayConstants.DP_BANNER_TYPE_PRODUCT, bnrInfo));

					// 메뉴 정보
					metaInfo.setMenuId(bannerDf.getMenuId());
					metaInfo.setMenuNm(bannerDf.getMenuNm());
					metaInfo.setTopMenuId(bannerDf.getTopMenuId());
					metaInfo.setTopMenuNm(bannerDf.getTopMenuNm());
					metaInfo.setMetaClsfCd(bannerDf.getMetaClsfCd());
					banner.setMenuList(this.commonMetaInfoGenerator.generateMenuList(metaInfo));

					// 영화 및 방송
					if (DisplayConstants.DP_MOVIE_TOP_MENU_ID.equals(topMenuId)
							|| DisplayConstants.DP_TV_TOP_MENU_ID.equals(topMenuId)) {
						preview = new Preview();

						// 미리보기 정보
						metaInfo.setScSamplUrl(bannerDf.getSampleUrl());
						metaInfo.setSamplUrl(bannerDf.getSampleUrlHq());
						preview.setSourceList(this.commonMetaInfoGenerator.generateBannerSourceList(metaInfo));
						banner.setPreview(preview);
					}
					// Tstore 쇼핑
					else if (DisplayConstants.DP_SHOPPING_TOP_MENU_ID.equals(topMenuId)) {
						salesOption = new SalesOption();
						salesOption.setType(bannerDf.getProdCaseCd());
						banner.setSalesOption(salesOption);
					}
				}
				// 배너타입 : URL 직접 입력 (외부 URL)
				else if (DisplayConstants.DP_BANNER_URL_NEW_CD.equals(bnrType)) {
					// 배너유형 정보
					banner.setType(DisplayConstants.DP_BANNER_TYPE_EXTERNAL_URL);

					// URL 정보
					url = new Url();
					url.setText(bnrInfo);
					banner.setUrl(url);
				}
				// 배너타입 : 카테고리 모바일 배너
				else if (DisplayConstants.DP_BANNER_CATEGORY_CD.equals(bnrType)) {
					// 배너유형 정보
					banner.setType(DisplayConstants.DP_BANNER_TYPE_CATEGORY);

					// 메뉴 정보
					menu = new Menu();
					menuList = new ArrayList<Menu>();
					menu.setId(bnrInfo);
					menuList.add(menu);
					banner.setMenuList(menuList);
				}
				// 배너타입 : 운영자 임의 추천
				else if (DisplayConstants.DP_BANNER_ADMIN_RECOMM_CD.equals(bnrType)) {
					// 배너유형 정보
					banner.setType(DisplayConstants.DP_BANNER_TYPE_THEME_ZONE);

					// 추천ID 정보
					identifierList.add(this.commonMetaInfoGenerator.generateIdentifier(
							DisplayConstants.DP_BANNER_TYPE_THEME_ZONE, bnrInfo));
				}
				// 배너타입 : 브랜드샵 리스트
				else if (DisplayConstants.DP_BANNER_BRANDSHOP_LIST_CD.equals(bnrType)) {
					// 배너유형 정보
					banner.setType(DisplayConstants.DP_BANNER_TYPE_BRAND_SHOP_CATEGORY);

					// 메뉴 정보
					menu = new Menu();
					menuList = new ArrayList<Menu>();
					menu.setType(DisplayConstants.DP_MENU_TOPCLASS_TYPE);
					menu.setId(bnrInfo);
					menuList.add(menu);
					banner.setMenuList(menuList);
				}
				// 배너타입 : 특정 브랜드샵
				else if (DisplayConstants.DP_BANNER_SPECIFIC_BRANDSHOP_CD.equals(bnrType)) {
					// 배너유형 정보
					banner.setType(DisplayConstants.DP_BANNER_TYPE_BRAND_SHOP);

					// 브랜드샵ID 정보
					identifierList.add(this.commonMetaInfoGenerator.generateIdentifier(
							DisplayConstants.DP_BANNER_TYPE_BRAND_SHOP, bnrInfo));
				}
				// 배너타입 : 내부 URL
				else if (DisplayConstants.DP_BANNER_INTERNAL_URL_CD.equals(bnrType)) {
					// 배너유형 정보
					banner.setType(DisplayConstants.DP_BANNER_TYPE_URL);

					// URL 정보
					url = new Url();
					url.setText(bnrInfo);
					banner.setUrl(url);
				}
				// 배너타입 : 상황별 추천
				else if (DisplayConstants.DP_BANNER_SITUATIONAL_RECOMM_CD.equals(bnrType)) {
					// 배너유형 정보
					banner.setType(DisplayConstants.DP_BANNER_TYPE_THEME_RECOMM);

					// 추천ID 정보
					identifierList.add(this.commonMetaInfoGenerator.generateIdentifier(
							DisplayConstants.DP_BANNER_TYPE_THEME_RECOMM, bnrInfo));
				}
				// 배너타입 : 테마추천 리스트 연결
				else if (DisplayConstants.DP_BANNER_THEME_RECOMM_CD.equals(bnrType)) {
					// 배너유형 정보
					banner.setType(DisplayConstants.DP_BANNER_TYPE_APP_GUIDE);

					// 테마추천ID 정보
					identifierList.add(this.commonMetaInfoGenerator.generateIdentifier(
							DisplayConstants.DP_BANNER_TYPE_APP_GUIDE, bnrInfo));
				}

				banner.setIdentifierList(identifierList);
				bannerList.add(banner);
			}
			commonResponse.setTotalCount(list.size());
		}

		bannerRes.setBannerList(bannerList);
		bannerRes.setCommonResponse(commonResponse);
		return bannerRes;
	}
}
