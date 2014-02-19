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
import java.util.Map.Entry;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.skplanet.storeplatform.framework.core.exception.StorePlatformException;
import com.skplanet.storeplatform.framework.core.persistence.dao.CommonDAO;
import com.skplanet.storeplatform.framework.core.util.StringUtils;
import com.skplanet.storeplatform.sac.client.display.vo.feature.recommend.ThemeRecommendSacReq;
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
import com.skplanet.storeplatform.sac.display.common.DisplayCommonUtil;
import com.skplanet.storeplatform.sac.display.common.constant.DisplayConstants;
import com.skplanet.storeplatform.sac.display.common.service.DisplayCommonService;
import com.skplanet.storeplatform.sac.display.feature.theme.recommend.vo.ThemeRecommend;
import com.skplanet.storeplatform.sac.display.meta.service.MetaInfoService;
import com.skplanet.storeplatform.sac.display.response.ResponseInfoGenerateFacade;

/**
 * 테마 추천 상품 리스트 조회 API Interface 구현체.
 * 
 * Updated on : 2014. 02. 18. Updated by : 윤주영, GTSOFT.
 */
@Service
public class ThemeRecommendProductServiceImpl implements ThemeRecommendProductService {

	private final Logger log = LoggerFactory.getLogger(this.getClass());

	@Autowired
	@Qualifier("sac")
	private CommonDAO commonDAO;

	@Autowired
	private MetaInfoService metaInfoService;

	@Autowired
	private DisplayCommonService displayCommonService;

	@Autowired
	private ResponseInfoGenerateFacade responseInfoGenerateFacade;

	@Override
	public ThemeRecommendSacRes searchThemeRecommendProductList(ThemeRecommendSacReq requestVO,
			SacRequestHeader requestHeader) throws StorePlatformException {
		// TODO Auto-generated method stub

		Map<String, Object> mapReq = new HashMap<String, Object>();

		TenantHeader tenantHeader = requestHeader.getTenantHeader();
		DeviceHeader deviceHeader = requestHeader.getDeviceHeader();

		mapReq.put("tenantHeader", tenantHeader);
		mapReq.put("deviceHeader", deviceHeader);

		// 필수 파라미터 체크
		if (StringUtils.isEmpty(requestVO.getRecommendId())) {
			throw new StorePlatformException("SAC_DSP_0002", "recommendId", requestVO.getRecommendId());
		}

		List<ThemeRecommend> packageInfo = new ArrayList<ThemeRecommend>();
		List<ThemeRecommend> productInfo = new ArrayList<ThemeRecommend>();
		List<String> imageCodeList = new ArrayList<String>();
		imageCodeList.add(DisplayConstants.DP_APP_REPRESENT_IMAGE_CD);
		imageCodeList.add(DisplayConstants.DP_VOD_REPRESENT_IMAGE_CD);
		imageCodeList.add(DisplayConstants.DP_EBOOK_COMIC_REPRESENT_IMAGE_CD);
		imageCodeList.add(DisplayConstants.DP_MUSIC_REPRESENT_IMAGE_CD);
		imageCodeList.add(DisplayConstants.DP_SHOPPING_REPRESENT_IMAGE_CD);
		mapReq.put("imageCdList", imageCodeList);
		mapReq.put("recommendId", requestVO.getRecommendId());

		mapReq.put("START_ROW", requestVO.getOffset());
		mapReq.put("END_ROW", requestVO.getOffset() + requestVO.getCount() - 1);

		if (this.log.isDebugEnabled()) {
			this.mapPrint(mapReq);
		}
		packageInfo = this.commonDAO
				.queryForList("Isf.ThemeRecommend.getRecomendPkgList", mapReq, ThemeRecommend.class);

		if (packageInfo.isEmpty()) {
			throw new StorePlatformException("SAC_DSP_0009");
		}

		Layout layout = this.makeLayout(packageInfo);

		productInfo = this.commonDAO.queryForList("Isf.ThemeRecommend.getRecommendPkgProdList", mapReq,
				ThemeRecommend.class);
		if (productInfo.isEmpty()) {
			throw new StorePlatformException("SAC_DSP_0009");
		}

		List<Product> productList = this.makeProductList(productInfo);

		ThemeRecommendSacRes responseVO = new ThemeRecommendSacRes();

		CommonResponse commonResponse = new CommonResponse();
		commonResponse.setTotalCount(productList.size());
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

	private List<Product> makeProductList(List<ThemeRecommend> resultList) {

		List<Product> listVO = new ArrayList<Product>();

		Iterator<ThemeRecommend> iterator = resultList.iterator();
		while (iterator.hasNext()) {

			ThemeRecommend mapper = iterator.next();

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

				identifierList = this.generateIdentifierList(idReqMap);
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
					date.setText(mapper.getVodSaleDt());
					contributor.setDate(date);
					product.setContributor(contributor);

				} else if (DisplayConstants.DP_EBOOK_TOP_MENU_ID.equals(mapper.getTopMenuId())
						|| DisplayConstants.DP_COMIC_TOP_MENU_ID.equals(mapper.getTopMenuId())) { // Ebook / Comic

					contributor.setName(mapper.getBookArtistNm()); // 제목
					contributor.setPainter(mapper.getBookArtistNm2()); //
					contributor.setPublisher(mapper.getBookChnlCompNm()); // 출판사
					Date date = new Date();
					date.setText(mapper.getBookSaleDt()); // 출판년도
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

				identifierList = this.generateIdentifierList(idReqMap);
				product.setIdentifierList(identifierList);

			}

			product.setProductExplain(mapper.getProdDesc());

			listVO.add(product);

		} // end of while

		return listVO;
	}

	private List<Identifier> generateIdentifierList(Map<String, String> param) {
		Identifier identifier = null;
		List<Identifier> identifierList = new ArrayList<Identifier>();

		String contentsTypeCd = param.get("contentsTypeCd");
		if (DisplayConstants.DP_EPISODE_CONTENT_TYPE_CD.equals(contentsTypeCd)) { // Episode ID 기준검색일 경우 (PD002502)
			identifier = new Identifier();
			identifier.setType(DisplayConstants.DP_EPISODE_IDENTIFIER_CD);
			identifier.setText(param.get("prodId"));
			identifierList.add(identifier);

			if (DisplayConstants.DP_SHOPPING_TOP_MENU_ID.equals(param.get("topMenuId"))) {
				if (param.get("catalogId") != null) {
					identifier = new Identifier();
					identifier.setType(DisplayConstants.DP_EPISODE_IDENTIFIER_CD);
					identifier.setText(param.get("catalogId"));
					identifierList.add(identifier);
				} else {
					if (param.get("prodId") != null) {
						identifier = new Identifier();
						identifier.setType(DisplayConstants.DP_EPISODE_IDENTIFIER_CD);
						identifier.setText(param.get("prodId"));
						identifierList.add(identifier);
					}
				}
			} else if (DisplayConstants.DP_MUSIC_TOP_MENU_ID.equals(param.get("topMenuId"))) {
				identifier = new Identifier();
				identifier.setType(DisplayConstants.DP_SONG_IDENTIFIER_CD);
				identifier.setText(param.get("outsdContentsId"));
				identifierList.add(identifier);
			}
		} else if (DisplayConstants.DP_CHANNEL_CONTENT_TYPE_CD.equals(contentsTypeCd) // Catalog ID 기준 검색일 경우
				&& DisplayConstants.DP_SHOPPING_TOP_MENU_ID.equals(param.get("topMenuId"))) {
			identifier = new Identifier();
			identifier.setType(DisplayConstants.DP_EPISODE_IDENTIFIER_CD);
			identifier.setText(param.get("prodId"));
			identifierList.add(identifier);

			identifier = new Identifier();
			identifier.setType(DisplayConstants.DP_EPISODE_IDENTIFIER_CD);
			identifier.setText(param.get("catalogId"));
			identifierList.add(identifier);
		} else if (DisplayConstants.DP_CHANNEL_CONTENT_TYPE_CD.equals(contentsTypeCd)) { // Channel ID 기준 검색일 경우
			identifier = new Identifier();
			identifier.setType(DisplayConstants.DP_CHANNEL_IDENTIFIER_CD);
			identifier.setText(param.get("prodId"));
			identifierList.add(identifier);
		}
		return identifierList;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.skplanet.storeplatform.sac.product.service.TotalRecommendService#searchTotalRecommendList(java.lang.String,
	 * java.lang.String, java.lang.String, java.lang.String, java.lang.String, int, int)
	 */
	@Override
	public ThemeRecommendSacRes searchDummyThemeRecommendProductList(ThemeRecommendSacReq requestVO,
			SacRequestHeader header) {
		// TODO Auto-generated method stub

		ThemeRecommendSacRes response = new ThemeRecommendSacRes();
		CommonResponse commonResponse = null;
		List<Product> listVO = new ArrayList<Product>();

		Product product;
		Layout layout;
		Identifier identifier;
		Title title;
		Source source;
		Menu menu;
		App app;
		Accrual accrual;
		Rights right;
		Date date;
		Price price;
		Support support;

		// Response VO를 만들기위한 생성자
		List<Menu> menuList;
		List<Source> sourceList;
		List<Identifier> identifierList;
		List<Support> supportList;

		product = new Product();
		layout = new Layout();
		identifier = new Identifier();
		title = new Title();
		source = new Source();

		// Response VO를 만들기위한 생성자
		sourceList = new ArrayList<Source>();

		// 추천 패키지 추천 사유
		title.setText("나도 이젠 프로게이머");

		source.setMediaType("image/jpeg");
		source.setSize(128);
		source.setType(DisplayConstants.DP_THUMNAIL_SOURCE);
		source.setUrl("http://wap.tstore.co.kr/android6/201312/04/IF1423502835320131125163752/0000648339/img/thumbnail/bb.jpg");

		layout.setTitle(title);
		layout.setSource(source);

		product = new Product();
		menuList = new ArrayList<Menu>();
		sourceList = new ArrayList<Source>();
		identifierList = new ArrayList<Identifier>();
		supportList = new ArrayList<Support>();

		title = new Title();
		source = new Source();

		// 상품ID
		identifier = new Identifier();

		identifier.setType(DisplayConstants.DP_CHANNEL_IDENTIFIER_CD);
		identifier.setText("0000648339");
		identifierList.add(identifier);

		menu = new Menu();
		menu.setId("DP01");
		menu.setName("GAME");
		menu.setType("topClass");
		menuList.add(menu);
		menu = new Menu();
		menu.setId("DP01004");
		menu.setName("RPG");
		menuList.add(menu);

		app = new App();
		app.setAid("OA00648339");
		app.setPackageName("com.webzenm.mtg4kakao");
		app.setVersionCode("11");
		app.setVersion("1.1");
		app.setSize(256);

		accrual = new Accrual();
		accrual.setVoterCount(1519);
		accrual.setDownloadCount(31410);
		accrual.setScore(4.5);

		right = new Rights();
		right.setGrade("0");

		date = new Date();
		date.setType("date/issue");
		date.setText("20120913T195630+0900/20121013T195630+0900");

		price = new Price();
		price.setFixedPrice(4300);
		price.setDiscountRate(0.0);
		price.setText(4300);

		title.setText("뮤 더 제네시스 for Kakao ");

		source.setMediaType("image/png");
		source.setSize(0);
		source.setType(DisplayConstants.DP_THUMNAIL_SOURCE);
		source.setUrl("http://wap.tstore.co.kr/android6/201312/04/IF1423502835320131125163752/0000648339/img/thumbnail/0000648339_130_130_0_91_20131204195212.PNG");
		sourceList.add(source);

		support = new Support();
		support.setText("iab");
		support.setText("Y");
		supportList.add(support);

		support = new Support();
		support.setText("drm");
		support.setText("Y");
		supportList.add(support);

		product.setTitle(title);
		product.setMenuList(menuList);
		product.setApp(app);
		product.setAccrual(accrual);
		product.setRights(right);
		product.setDate(date);
		product.setPrice(price);
		product.setSourceList(sourceList);
		product.setSupportList(supportList);
		product.setIdentifierList(identifierList);

		listVO.add(product);

		commonResponse = new CommonResponse();
		commonResponse.setTotalCount(listVO.size());

		response.setCommonRes(commonResponse);
		response.setLayout(layout);
		response.setProductList(listVO);

		return response;
	}

	private void mapPrint(Map<String, Object> mapReq) {
		// Get Map in Set interface to get key and value
		Set<Entry<String, Object>> s = mapReq.entrySet();
		// Move next key and value of Map by iterator
		Iterator<Entry<String, Object>> it = s.iterator();
		while (it.hasNext()) {
			// key=value separator this by Map.Entry to get key and value
			Entry<String, Object> m = it.next();

			// getKey is used to get key of Map
			String key = m.getKey();

			// getValue is used to get value of key in Map
			Object value = m.getValue();

			this.log.debug(key + ":[" + value.toString() + "]");
		}
	}
}
