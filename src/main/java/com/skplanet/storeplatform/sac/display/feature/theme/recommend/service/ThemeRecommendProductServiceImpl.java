/*
 * Copyright (c) 2013 SK planet.
 * All right reserved.
 *
 * This software is the confidential and proprietary information of SK planet.
 * You shall not disclose such Confidential Information and
 * shall use it only in accordance with the terms of the license agreement
 * you entered into with SK planet.
 */
package com.skplanet.storeplatform.sac.display.feature.theme.recommend.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
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
import com.skplanet.storeplatform.sac.client.display.vo.feature.recommend.ThemeRecommendProdSacReq;
import com.skplanet.storeplatform.sac.client.display.vo.feature.recommend.ThemeRecommendSacRes;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.common.CommonResponse;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.common.Date;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.common.Identifier;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.common.Menu;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.common.Price;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.common.Source;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.common.Title;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.product.Accrual;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.product.App;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.product.Contributor;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.product.Layout;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.product.Music;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.product.Product;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.product.Rights;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.product.Support;
import com.skplanet.storeplatform.sac.common.header.vo.DeviceHeader;
import com.skplanet.storeplatform.sac.common.header.vo.SacRequestHeader;
import com.skplanet.storeplatform.sac.common.header.vo.TenantHeader;
import com.skplanet.storeplatform.sac.common.util.DateUtils;
import com.skplanet.storeplatform.sac.display.common.DisplayCommonUtil;
import com.skplanet.storeplatform.sac.display.common.constant.DisplayConstants;
import com.skplanet.storeplatform.sac.display.feature.isf.common.util.IsfUtils;
import com.skplanet.storeplatform.sac.display.feature.theme.recommend.vo.ThemeRecommend;
import com.skplanet.storeplatform.sac.display.meta.service.MetaInfoService;
import com.skplanet.storeplatform.sac.display.meta.vo.MetaInfo;
import com.skplanet.storeplatform.sac.display.meta.vo.ProductBasicInfo;
import com.skplanet.storeplatform.sac.display.response.ResponseInfoGenerateFacade;

/**
 * 테마 추천 상품 리스트 조회 API Interface 구현체.
 * 
 * Updated on : 2014. 02. 18. Updated by : 윤주영, GTSOFT.
 */
@Service
public class ThemeRecommendProductServiceImpl implements ThemeRecommendProductService {

	private final Logger log = LoggerFactory.getLogger(this.getClass());

	private int totalCount = 0;

	@Autowired
	@Qualifier("sac")
	private CommonDAO commonDAO;

	@Autowired
	private MetaInfoService metaInfoService;

	@Autowired
	private ResponseInfoGenerateFacade responseInfoGenerateFacade;

	@Override
	public ThemeRecommendSacRes searchThemeRecommendProductList(ThemeRecommendProdSacReq requestVO,
			SacRequestHeader requestHeader) throws StorePlatformException {

		// TODO Auto-generated method stub
		Map<String, Object> mapReq = new HashMap<String, Object>();

		TenantHeader tenantHeader = requestHeader.getTenantHeader();
		DeviceHeader deviceHeader = requestHeader.getDeviceHeader();

		mapReq.put("tenantHeader", tenantHeader);
		mapReq.put("deviceHeader", deviceHeader);
		mapReq.put("virtualDeviceModel", DisplayConstants.DP_ANY_PHONE_4MM);

		List<ThemeRecommend> packageInfo = new ArrayList<ThemeRecommend>();
		// List<ThemeRecommend> productInfo = new ArrayList<ThemeRecommend>();
		List<String> imageCodeList = new ArrayList<String>();
		imageCodeList.add(DisplayConstants.DP_APP_REPRESENT_IMAGE_CD);
		imageCodeList.add(DisplayConstants.DP_VOD_REPRESENT_IMAGE_CD);
		imageCodeList.add(DisplayConstants.DP_EBOOK_COMIC_REPRESENT_IMAGE_CD);
		imageCodeList.add(DisplayConstants.DP_MUSIC_REPRESENT_IMAGE_CD);
		imageCodeList.add(DisplayConstants.DP_SHOPPING_REPRESENT_IMAGE_CD);
		mapReq.put("imageCdList", imageCodeList);
		mapReq.put("recommendId", requestVO.getRecommendId());

		int start = 1;
		int end = 20;

		if (requestVO.getOffset() > 0) {
			start = requestVO.getOffset();
		}
		if (requestVO.getOffset() > 0 && (requestVO.getOffset() + requestVO.getCount() - 1) >= start) {
			end = requestVO.getOffset() + requestVO.getCount() - 1;
		}
		mapReq.put("START_ROW", start);
		mapReq.put("END_ROW", end);

		/*
		 * if (this.log.isDebugEnabled()) { this.mapPrint(mapReq); }
		 */
		packageInfo = this.commonDAO
				.queryForList("Isf.ThemeRecommend.getRecomendPkgList", mapReq, ThemeRecommend.class);

		if (packageInfo.isEmpty()) {
			throw new StorePlatformException("SAC_DSP_0009");
		}

		Layout layout = this.makeLayout(packageInfo);

		List<Product> productList = new ArrayList<Product>();

		// Meta 처리
		// 상품 기본 정보 List 조회
		List<ProductBasicInfo> productBasicInfoList = this.commonDAO.queryForList(
				"Isf.ThemeRecommend.getBasicRecommendPkgProdList", mapReq, ProductBasicInfo.class);

		Product product = null;
		MetaInfo metaInfo = null;

		if (this.log.isDebugEnabled()) {
			this.log.debug("##### selected product basic info cnt : {}", productBasicInfoList.size());
		}
		if (!productBasicInfoList.isEmpty()) {

			Map<String, Object> paramMap = new HashMap<String, Object>();
			paramMap.put("tenantHeader", tenantHeader);
			paramMap.put("deviceHeader", deviceHeader);
			paramMap.put("prodStatusCd", DisplayConstants.DP_SALE_STAT_ING); // 판매중

			// Meta 정보 조회
			for (ProductBasicInfo productBasicInfo : productBasicInfoList) {

				this.totalCount = productBasicInfo.getTotalCount();

				String topMenuId = productBasicInfo.getTopMenuId(); // 탑메뉴
				String svcGrpCd = productBasicInfo.getSvcGrpCd(); // 서비스 그룹 코드
				paramMap.put("productBasicInfo", productBasicInfo);

				if (this.log.isDebugEnabled()) {
					this.log.debug("##### Top Menu Id : {}", topMenuId);
					this.log.debug("##### Service Group Cd : {}", svcGrpCd);
				}
				// 상품 SVC_GRP_CD 조회
				// DP000203 : 멀티미디어
				// DP000206 : Tstore 쇼핑
				// DP000205 : 소셜쇼핑
				// DP000204 : 폰꾸미기
				// DP000201 : 애플리캐이션
				// APP 상품의 경우
				if (DisplayConstants.DP_APP_PROD_SVC_GRP_CD.equals(svcGrpCd)) {
					paramMap.put("imageCd", DisplayConstants.DP_APP_REPRESENT_IMAGE_CD);
					if (this.log.isDebugEnabled()) {
						this.log.debug("##### Search for app  meta info product");
					}
					metaInfo = this.metaInfoService.getAppMetaInfo(paramMap);
					if (metaInfo != null) {
						product = this.responseInfoGenerateFacade.generateAppProduct(metaInfo);
						productList.add(product);
					}

				} else if (DisplayConstants.DP_MULTIMEDIA_PROD_SVC_GRP_CD.equals(svcGrpCd)) { // 멀티미디어 타입일 경우
					// 영화/방송 상품의 경우
					paramMap.put("imageCd", DisplayConstants.DP_VOD_REPRESENT_IMAGE_CD);
					if (DisplayConstants.DP_MOVIE_TOP_MENU_ID.equals(topMenuId)
							|| DisplayConstants.DP_TV_TOP_MENU_ID.equals(topMenuId)) {
						if (this.log.isDebugEnabled()) {
							this.log.debug("##### Search for Vod  meta info product");
						}
						metaInfo = this.metaInfoService.getVODMetaInfo(paramMap);
						if (metaInfo != null) {
							if (DisplayConstants.DP_MOVIE_TOP_MENU_ID.equals(topMenuId)) {
								product = this.responseInfoGenerateFacade.generateMovieProduct(metaInfo);
							} else {
								product = this.responseInfoGenerateFacade.generateBroadcastProduct(metaInfo);
							}
							productList.add(product);
						}
					} else if (DisplayConstants.DP_EBOOK_TOP_MENU_ID.equals(topMenuId)
							|| DisplayConstants.DP_COMIC_TOP_MENU_ID.equals(topMenuId)) { // Ebook / Comic 상품의 경우

						paramMap.put("imageCd", DisplayConstants.DP_EBOOK_COMIC_REPRESENT_IMAGE_CD);

						if (this.log.isDebugEnabled()) {
							this.log.debug("##### Search for EbookComic product");
						}
						metaInfo = this.metaInfoService.getEbookComicMetaInfo(paramMap);
						if (metaInfo != null) {
							if (DisplayConstants.DP_EBOOK_TOP_MENU_ID.equals(topMenuId)) {
								product = this.responseInfoGenerateFacade.generateEbookProduct(metaInfo);
							} else {
								product = this.responseInfoGenerateFacade.generateComicProduct(metaInfo);
							}
							productList.add(product);
						}

					} else if (DisplayConstants.DP_MUSIC_TOP_MENU_ID.equals(topMenuId)) { // 음원 상품의 경우

						paramMap.put("imageCd", DisplayConstants.DP_MUSIC_REPRESENT_IMAGE_CD);
						paramMap.put("contentTypeCd", DisplayConstants.DP_EPISODE_CONTENT_TYPE_CD);

						if (this.log.isDebugEnabled()) {
							this.log.debug("##### Search for music meta info product");
						}
						metaInfo = this.metaInfoService.getMusicMetaInfo(paramMap);
						if (metaInfo != null) {
							product = this.responseInfoGenerateFacade.generateMusicProduct(metaInfo);
							productList.add(product);
						}
					}
				} else if (DisplayConstants.DP_TSTORE_SHOPPING_PROD_SVC_GRP_CD.equals(svcGrpCd)) { // 쇼핑 상품의 경우
					paramMap.put("prodRshpCd", DisplayConstants.DP_CHANNEL_EPISHODE_RELATIONSHIP_CD);
					paramMap.put("imageCd", DisplayConstants.DP_SHOPPING_REPRESENT_IMAGE_CD);

					if (this.log.isDebugEnabled()) {
						this.log.debug("##### Search for Shopping  meta info product");
					}
					metaInfo = this.metaInfoService.getShoppingMetaInfo(paramMap);
					if (metaInfo != null) {
						product = this.responseInfoGenerateFacade.generateShoppingProduct(metaInfo);
						productList.add(product);
					}
				}
			}
		}

		if (this.log.isDebugEnabled()) {
			this.log.debug("product count : {}", productList.size());
			this.log.debug("total count : {}", this.totalCount);
			// productList.clear();
		}

		// data 무존재시 운영자 추천으로 대체
		/*
		 * if (productList.isEmpty()) { this.totalCount = 0; List<MetaInfo> metaInfoList = new ArrayList<MetaInfo>();
		 * metaInfoList = this.commonDAO.queryForList("Isf.ThemeRecommend.getRecommendPkgProdList", mapReq,
		 * MetaInfo.class); if (metaInfoList.isEmpty()) { throw new StorePlatformException("SAC_DSP_0009"); }
		 * 
		 * for (MetaInfo metaInfo1 : metaInfoList) { String topMenuId = metaInfo1.getTopMenuId(); this.totalCount =
		 * metaInfo1.getTotalCount();
		 * 
		 * if (topMenuId.equals(DisplayConstants.DP_MOVIE_TOP_MENU_ID)) { product =
		 * this.responseInfoGenerateFacade.generateMovieProduct(metaInfo1); } else if
		 * (topMenuId.equals(DisplayConstants.DP_TV_TOP_MENU_ID)) { product =
		 * this.responseInfoGenerateFacade.generateBroadcastProduct(metaInfo1); } else if
		 * (topMenuId.equals(DisplayConstants.DP_EBOOK_TOP_MENU_ID)) { product =
		 * this.responseInfoGenerateFacade.generateEbookProduct(metaInfo1); } else if
		 * (topMenuId.equals(DisplayConstants.DP_COMIC_TOP_MENU_ID)) { // 멀티미디어 카테고리 조회 product =
		 * this.responseInfoGenerateFacade.generateComicProduct(metaInfo1); } else if
		 * (topMenuId.equals(DisplayConstants.DP_MUSIC_TOP_MENU_ID)) { // 뮤직 카테고리 조회 product =
		 * this.responseInfoGenerateFacade.generateMusicProduct(metaInfo1); } else if
		 * (topMenuId.equals(DisplayConstants.DP_SHOPPING_TOP_MENU_ID)) { // 쇼핑 카테고리 조회 product =
		 * this.responseInfoGenerateFacade.generateShoppingProduct(metaInfo1); } else { // 앱 카테고리 조회 product =
		 * this.responseInfoGenerateFacade.generateAppProduct(metaInfo1); } productList.add(product); } }
		 */

		ThemeRecommendSacRes responseVO = new ThemeRecommendSacRes();

		CommonResponse commonResponse = new CommonResponse();
		commonResponse.setTotalCount(this.totalCount);
		responseVO.setCommonRes(commonResponse);

		responseVO.setLayout(layout);
		responseVO.setProductList(productList);

		return responseVO;

	}

	private Layout makeLayout(List<ThemeRecommend> resultList) {

		Layout layout = new Layout();

		if (resultList.get(0) != null) {
			Title title = new Title();
			title.setText(resultList.get(0).getPkgNm());
			layout.setTitle(title);

			Menu menu = new Menu();
			menu.setId(resultList.get(0).getPkgId());
			menu.setName(resultList.get(0).getPkgNm());
			layout.setMenu(menu);

			Source source = new Source();
			source.setUrl(resultList.get(0).getPkgImgPos());
			source.setType(DisplayConstants.DP_THUMNAIL_SOURCE);
			layout.setSource(source);
		}

		return layout;
	}

	@SuppressWarnings("unused")
	private List<Product> makeProductList(List<ThemeRecommend> resultList) {

		List<Product> listVO = new ArrayList<Product>();

		Iterator<ThemeRecommend> iterator = resultList.iterator();
		while (iterator.hasNext()) {

			ThemeRecommend mapper = iterator.next();

			this.totalCount = mapper.getTotalCount();

			Product product;
			Identifier identifier;
			Title title;
			App app;
			Music music;
			Accrual accrual;
			Rights rights;
			Source source;
			Support support;
			Price price;
			Menu menu;
			Contributor contributor;

			// Response VO를 만들기위한 생성자
			List<Menu> menuList;
			List<Source> sourceList;
			List<Support> supportList;
			List<Identifier> identifierList;

			product = new Product();
			identifier = new Identifier();
			title = new Title();
			app = new App();
			music = new Music();
			accrual = new Accrual();
			rights = new Rights();
			support = new Support();
			source = new Source();
			price = new Price();
			contributor = new Contributor();

			// 상품ID
			identifier = new Identifier();

			// Response VO를 만들기위한 생성자
			menuList = new ArrayList<Menu>();
			sourceList = new ArrayList<Source>();
			supportList = new ArrayList<Support>();
			identifierList = new ArrayList<Identifier>();

			Map<String, String> idReqMap = new HashMap<String, String>();
			idReqMap.put("prodId", mapper.getProdId());
			idReqMap.put("topMenuId", mapper.getTopMenuId());
			idReqMap.put("contentsTypeCd", mapper.getContentsTypeCd());
			idReqMap.put("outsdContentsId", mapper.getOutsdContentsId());

			title.setText(mapper.getProdNm());
			product.setTitle(title);

			menu = new Menu();
			menu.setId(mapper.getTopMenuId());
			menu.setName(mapper.getTopMenuNm());
			menu.setType(DisplayConstants.DP_MENU_TOPCLASS_TYPE);
			menuList.add(menu);
			menu = new Menu();
			menu.setId(mapper.getMenuId());
			menu.setName(mapper.getMenuNm());
			menuList.add(menu);
			product.setMenuList(menuList);

			accrual.setVoterCount(Integer.valueOf(mapper.getPartCnt()));
			accrual.setDownloadCount(Integer.valueOf(mapper.getDwldCnt()));
			accrual.setScore(Double.valueOf(mapper.getAvgScore()));
			product.setAccrual(accrual);

			/*
			 * Rights grade
			 */
			rights.setGrade(mapper.getProdGrdCd());
			product.setRights(rights);

			source.setType(DisplayConstants.DP_THUMNAIL_SOURCE);
			source.setMediaType(DisplayCommonUtil.getMimeType(mapper.getFilePos()));
			source.setUrl(mapper.getFilePos());
			sourceList.add(source);
			product.setSourceList(sourceList);

			/*
			 * Price text
			 */
			price.setText(Integer.valueOf(mapper.getProdAmt())); // 가격
			price.setFixedPrice(Integer.valueOf(mapper.getProdNetAmt())); // 고정가
			product.setPrice(price);

			// 상품 SVC_GRP_CD 조회
			// DP000203 : 멀티미디어
			// DP000206 : Tstore 쇼핑
			// DP000205 : 소셜쇼핑
			// DP000204 : 폰꾸미기
			// DP000201 : 애플리캐이션
			if (DisplayConstants.DP_APP_PROD_SVC_GRP_CD.equals(mapper.getSvcGrpCd())) { // 앱 타입일 경우

				identifier.setType(DisplayConstants.DP_EPISODE_IDENTIFIER_CD);
				identifier.setText(mapper.getProdId());
				identifierList.add(identifier);
				product.setIdentifierList(identifierList);

				app.setAid(mapper.getAid());
				app.setPackageName(mapper.getApkPkg());
				app.setVersionCode(mapper.getApkVerCd());
				app.setVersion(mapper.getProdVer()); // 확인 필요
				product.setApp(app);

				// support list
				support.setText(DisplayConstants.DP_DRM_SUPPORT_NM);
				support.setText(mapper.getDrm());
				supportList.add(support);

				support.setText(DisplayConstants.DP_IN_APP_SUPPORT_NM);
				support.setText(mapper.getInAppBilling());
				supportList.add(support);

				product.setSupportList(supportList);

			} else if (DisplayConstants.DP_MULTIMEDIA_PROD_SVC_GRP_CD.equals(mapper.getSvcGrpCd())) { // 멀티미디어 타입일 경우

				identifierList = IsfUtils.generateIdentifierList(idReqMap);
				product.setIdentifierList(identifierList);

				// support list
				if (!StringUtils.isEmpty(mapper.getHdvYn())) {
					support.setText(DisplayConstants.DP_VOD_HD_SUPPORT_NM);
					support.setText(mapper.getHdvYn());
					supportList.add(support);
				}

				if (!StringUtils.isEmpty(mapper.getDolbySprt_yn())) {
					support.setText(DisplayConstants.DP_VOD_DOLBY_SUPPORT_NM);
					support.setText(mapper.getDolbySprt_yn());
					supportList.add(support);
				}

				if (!supportList.isEmpty()) {
					product.setSupportList(supportList);
				}

				if (DisplayConstants.DP_MOVIE_TOP_MENU_ID.equals(mapper.getTopMenuId())
						|| DisplayConstants.DP_TV_TOP_MENU_ID.equals(mapper.getTopMenuId())) { // 영화/방송

					contributor.setDirector(mapper.getVodArtistNm2()); // 제작자
					contributor.setArtist(mapper.getVodArtistNm()); // 출연자
					contributor.setCompany(mapper.getVodChnlCompNm());
					Date date = new Date();
					date.setText(DateUtils.parseDate(mapper.getVodSaleDt()));
					contributor.setDate(date);
					product.setContributor(contributor);

				} else if (DisplayConstants.DP_EBOOK_TOP_MENU_ID.equals(mapper.getTopMenuId())
						|| DisplayConstants.DP_COMIC_TOP_MENU_ID.equals(mapper.getTopMenuId())) { // Ebook / Comic

					contributor.setName(mapper.getBookArtistNm()); // 제목
					contributor.setPainter(mapper.getBookArtistNm2()); //
					contributor.setPublisher(mapper.getBookChnlCompNm()); // 출판사
					Date date = new Date();
					date.setText(DateUtils.parseDate(mapper.getBookSaleDt())); // 출판년도
					contributor.setDate(date);
					product.setContributor(contributor);

				} else if (DisplayConstants.DP_MUSIC_TOP_MENU_ID.equals(mapper.getTopMenuId())) { // 음원 상품의 경우
					// music service list set
					List<com.skplanet.storeplatform.sac.client.product.vo.intfmessage.product.Service> serviceList = new ArrayList<com.skplanet.storeplatform.sac.client.product.vo.intfmessage.product.Service>();

					com.skplanet.storeplatform.sac.client.product.vo.intfmessage.product.Service service = new com.skplanet.storeplatform.sac.client.product.vo.intfmessage.product.Service();
					service.setName(DisplayConstants.DP_MUSIC_SERVICE_MP3);
					service.setType(mapper.getMp3Sprt());
					serviceList.add(service);

					service = new com.skplanet.storeplatform.sac.client.product.vo.intfmessage.product.Service();
					service.setName(DisplayConstants.DP_MUSIC_SERVICE_BELL);
					service.setType(mapper.getBellSprt());
					serviceList.add(service);

					service = new com.skplanet.storeplatform.sac.client.product.vo.intfmessage.product.Service();
					service.setName(DisplayConstants.DP_MUSIC_SERVICE_RING);
					service.setType(mapper.getRingSprt());
					serviceList.add(service);
					music.setServiceList(serviceList);
					product.setMusic(music);

					contributor.setName(mapper.getMusicArtistNm()); // 가수
					contributor.setAlbum(mapper.getMusicArtistNm3()); // 앨범명
					contributor.setPublisher(mapper.getMusicChnlCompNm()); // 발행인
					contributor.setAgency(mapper.getMusicAgencyNm()); // 에이전시
					product.setContributor(contributor);
				}
			} else if (DisplayConstants.DP_TSTORE_SHOPPING_PROD_SVC_GRP_CD.equals(mapper.getSvcGrpCd())) { // 쇼핑 상품의 경우

				identifierList = IsfUtils.generateIdentifierList(idReqMap);
				product.setIdentifierList(identifierList);

			}

			product.setProductExplain(mapper.getProdDesc());

			listVO.add(product);

		} // end of while

		return listVO;
	}
}
