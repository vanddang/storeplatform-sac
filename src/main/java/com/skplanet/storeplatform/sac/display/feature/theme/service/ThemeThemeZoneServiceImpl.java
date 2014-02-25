/*
 * Copyright (c) 2013 SK planet.
 * All right reserved.
 *
 * This software is the confidential and proprietary information of SK planet.
 * You shall not disclose such Confidential Information and
 * shall use it only in accordance with the terms of the license agreement
 * you entered into with SK planet.
 */
package com.skplanet.storeplatform.sac.display.feature.theme.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.skplanet.storeplatform.framework.core.persistence.dao.CommonDAO;
import com.skplanet.storeplatform.framework.core.util.StringUtils;
import com.skplanet.storeplatform.sac.client.display.vo.theme.ThemeThemeZoneSacReq;
import com.skplanet.storeplatform.sac.client.display.vo.theme.ThemeThemeZoneSacRes;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.common.CommonResponse;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.common.Date;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.common.Identifier;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.common.Menu;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.common.Price;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.common.Source;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.common.Title;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.product.Contributor;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.product.Layout;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.product.Product;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.product.Rights;
import com.skplanet.storeplatform.sac.common.header.vo.DeviceHeader;
import com.skplanet.storeplatform.sac.common.header.vo.SacRequestHeader;
import com.skplanet.storeplatform.sac.common.header.vo.TenantHeader;
import com.skplanet.storeplatform.sac.display.common.constant.DisplayConstants;
import com.skplanet.storeplatform.sac.display.feature.theme.vo.ThemeThemeZoneInfo;
import com.skplanet.storeplatform.sac.display.meta.service.MetaInfoService;
import com.skplanet.storeplatform.sac.display.meta.vo.MetaInfo;
import com.skplanet.storeplatform.sac.display.meta.vo.ProductBasicInfo;
import com.skplanet.storeplatform.sac.display.response.EbookComicGenerator;
import com.skplanet.storeplatform.sac.display.response.MusicInfoGenerator;
import com.skplanet.storeplatform.sac.display.response.VodGenerator;

/**
 * 
 * 
 * Updated on : 2014. 2. 21. Updated by : 이승훈, 엔텔스.
 */

@Service
public class ThemeThemeZoneServiceImpl implements ThemeThemeZoneService {
	@Autowired
	@Qualifier("sac")
	private CommonDAO commonDAO;

	@Autowired
	private MetaInfoService metaInfoService;

	@Autowired
	private MusicInfoGenerator musicGenerator;

	@Autowired
	private VodGenerator vodGenerator;

	@Autowired
	private EbookComicGenerator ebookComicGenerator;

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.skplanet.storeplatform.sac.biz.product.service.EbookComicThemeService#EbookComicThemeService(com.skplanet
	 * .storeplatform.sac.client.product.vo.EbookComicThemeRequestVO)
	 */
	@Override
	public ThemeThemeZoneSacRes searchThemeThemeZoneList(ThemeThemeZoneSacReq req, SacRequestHeader header) {

		TenantHeader tenantHeader = header.getTenantHeader();
		DeviceHeader deviceHeader = header.getDeviceHeader();

		req.setTenantId(tenantHeader.getTenantId());
		req.setLangCd(tenantHeader.getLangCd());
		req.setDeviceModelCd(deviceHeader.getModel());

		ThemeThemeZoneSacRes res = new ThemeThemeZoneSacRes();

		if (req.getDummy() == null) {

			int offset = 1; // default
			int count = 20; // default

			if (req.getOffset() != null) {
				offset = req.getOffset();
			}
			req.setOffset(offset);

			if (req.getCount() != null) {
				count = req.getCount();
			}
			count = offset + count - 1;
			req.setCount(count);

			String themezoneId = req.getThemezoneId();

			CommonResponse commonResponse = new CommonResponse();
			List<Product> productList = new ArrayList<Product>();

			Map<String, Object> reqMap = new HashMap<String, Object>();
			reqMap.put("tenantHeader", tenantHeader);
			reqMap.put("deviceHeader", deviceHeader);
			reqMap.put("prodStatusCd", DisplayConstants.DP_SALE_STAT_ING);

			// 테마 조회
			List<ThemeThemeZoneInfo> ThemeThemeZoneInfoMeta = this.commonDAO.queryForList(
					"ThemeThemeZone.selectThemeThemeZone", req, ThemeThemeZoneInfo.class);

			// 테마상품 조회
			List<ProductBasicInfo> productBasicInfoList = this.commonDAO.queryForList(
					"ThemeThemeZone.selectThemeThemeZoneList", req, ProductBasicInfo.class);

			if (!productBasicInfoList.isEmpty()) {

				Product product = null;
				// Identifier 설정
				Identifier identifier = null;
				List<Identifier> identifierList = null;
				Menu menu = null;
				List<Menu> menuList = null;
				Title title = null;

				List<Source> sourceList = null;
				Source source = null;
				Rights rights = null;
				Layout layout = new Layout();

				// layout 설정
				if (!(StringUtils.isEmpty(themezoneId))) {
					ThemeThemeZoneInfo themeThemeZoneInfo = null;
					themeThemeZoneInfo = ThemeThemeZoneInfoMeta.get(0);
					layout = new Layout();

					title = new Title();
					title.setText(themeThemeZoneInfo.getBnrNm());
					layout.setTitle(title);

					// source 정보
					source = new Source();
					sourceList = new ArrayList<Source>();
					source.setType(DisplayConstants.DP_SOURCE_TYPE_THUMBNAIL);
					source.setUrl(themeThemeZoneInfo.getImgPath());
					sourceList.add(source);
					layout.setSource(source);

					// 메뉴 정보
					menu = new Menu(); // 메뉴
					menu.setId(themeThemeZoneInfo.getBnrMenuId());
					menu.setName(themeThemeZoneInfo.getBnrMenuNm());
					layout.setMenu(menu);

				}

				for (ProductBasicInfo productBasicInfo : productBasicInfoList) {
					String topMenuId = productBasicInfo.getTopMenuId();
					String svcGrpCd = productBasicInfo.getSvcGrpCd();

					reqMap.put("productBasicInfo", productBasicInfo);
					reqMap.put("req", req);
					reqMap.put("tenantHeader", tenantHeader);
					reqMap.put("deviceHeader", deviceHeader);
					reqMap.put("lang", tenantHeader.getLangCd());

					product = new Product(); // 결과물

					// identifier 정보
					identifier = new Identifier();
					identifierList = new ArrayList<Identifier>();

					identifier.setType(DisplayConstants.DP_CHANNEL_IDENTIFIER_CD);
					identifier.setText(productBasicInfo.getPartProdId());
					identifier.setType(DisplayConstants.DP_EPISODE_IDENTIFIER_CD);
					identifier.setText(productBasicInfo.getProdId());
					identifierList.add(identifier);
					product.setIdentifierList(identifierList);
					MetaInfo retMetaInfo = null;
					System.out.println(svcGrpCd);
					System.out.println("22222");
					// APP 상품의 경우
					if (DisplayConstants.DP_APP_PROD_SVC_GRP_CD.equals(svcGrpCd)) {
						reqMap.put("imageCd", DisplayConstants.DP_APP_REPRESENT_IMAGE_CD);
						retMetaInfo = this.metaInfoService.getAppMetaInfo(reqMap);

					} else if (DisplayConstants.DP_MULTIMEDIA_PROD_SVC_GRP_CD.equals(svcGrpCd)) { // 멀티미디어 타입일 경우
						// 영화/방송 상품의 경우
						reqMap.put("imageCd", DisplayConstants.DP_VOD_REPRESENT_IMAGE_CD);
						if (DisplayConstants.DP_MOVIE_TOP_MENU_ID.equals(topMenuId)
								|| DisplayConstants.DP_TV_TOP_MENU_ID.equals(topMenuId)) {
							retMetaInfo = this.metaInfoService.getVODMetaInfo(reqMap);
							// 영화용 Contributor 설정
							if (retMetaInfo != null) {
								Contributor contributor = this.vodGenerator.generateMovieContributor(retMetaInfo);
								product.setContributor(contributor);
							}
						} else if (DisplayConstants.DP_EBOOK_TOP_MENU_ID.equals(topMenuId)
								|| DisplayConstants.DP_COMIC_TOP_MENU_ID.equals(topMenuId)) { // Ebook / Comic 상품의
																							  // 경우
							retMetaInfo = this.metaInfoService.getEbookComicMetaInfo(reqMap);
							System.out.println("333333");
							// Ebook용 Contributor 설정
							if (retMetaInfo != null) {
								Contributor contributor = this.ebookComicGenerator
										.generateEbookContributor(retMetaInfo);
								product.setContributor(contributor);
							}
							System.out.println("444");
						} else if (DisplayConstants.DP_MUSIC_TOP_MENU_ID.equals(topMenuId)) { // 음원 상품의 경우
							retMetaInfo = this.metaInfoService.getMusicMetaInfo(reqMap);
							// Music용 Contributor 설정
							if (retMetaInfo != null) {
								Contributor contributor = this.musicGenerator.generateContributor(retMetaInfo);
								product.setContributor(contributor);
							}
						} else if (DisplayConstants.DP_WEBTOON_TOP_MENU_ID.equals(topMenuId)) { // WEBTOON 상품의 경우
							retMetaInfo = this.metaInfoService.getWebtoonMetaInfo(reqMap);
							// Comic용 Contributor 설정
							if (retMetaInfo != null) {
								Contributor contributor = this.ebookComicGenerator
										.generateComicContributor(retMetaInfo);
								product.setContributor(contributor);
							}
						}
					} else if (DisplayConstants.DP_TSTORE_SHOPPING_PROD_SVC_GRP_CD.equals(svcGrpCd)) { // 쇼핑 상품의 경우
						retMetaInfo = this.metaInfoService.getShoppingMetaInfo(reqMap);
					}
					if (retMetaInfo != null) {
						// title 정보
						title = new Title();
						title.setText(retMetaInfo.getProdNm());
						product.setTitle(title);

						// right 정보
						rights = new Rights();
						rights.setGrade(retMetaInfo.getProdGrdCd());
						product.setRights(rights);

						// 메뉴 정보
						menu = new Menu(); // 메뉴
						menuList = new ArrayList<Menu>(); // 메뉴 리스트
						menu.setId(retMetaInfo.getMenuId());
						menu.setName(retMetaInfo.getMenuNm());
						menuList.add(menu);
						product.setMenuList(menuList);
						productList.add(product);
					}

				}
				commonResponse.setTotalCount(productBasicInfoList.get(0).getTotalCount());
				res.setProductList(productList);
				if (!(StringUtils.isEmpty(themezoneId))) {
					res.setLayOut(layout);
				}
				res.setCommonResponse(commonResponse);
			} else {
				// 조회 결과 없음
				commonResponse.setTotalCount(0);
				res.setProductList(productList);
				res.setCommonResponse(commonResponse);
			}
			return res;
		} else {
			return this.generateDummy();
		}
	}

	/**
	 * <pre>
	 * 더미 데이터 생성.
	 * </pre>
	 * 
	 * @return CategorySpecificSacRes
	 */
	private ThemeThemeZoneSacRes generateDummy() {
		Identifier identifier = null;
		List<Identifier> identifierList;
		Title title = null;
		Menu menu = null;
		Source source = null;
		Price price = null;
		Rights rights = null;
		Contributor contributor = null;
		Date date = null;

		Product product = null;
		Layout layOut = null;

		List<Menu> menuList = null;
		List<Source> sourceList = null;

		sourceList = new ArrayList<Source>();
		List<Product> productList = new ArrayList<Product>();
		CommonResponse commonResponse = new CommonResponse();
		ThemeThemeZoneSacRes res = new ThemeThemeZoneSacRes();

		menuList = new ArrayList<Menu>();

		product = new Product();
		identifier = new Identifier();
		source = new Source();
		price = new Price();
		rights = new Rights();
		contributor = new Contributor();
		date = new Date();

		layOut = new Layout();
		title = new Title();
		// title 설정
		title.setText("브랜드샵 이름");
		// source 설정
		source.setMediaType("image/jpeg");
		source.setType("thumbnail");
		source.setSize(659069);
		source.setUrl("http://wap.tstore.co.kr/android6/201311/22/IF1423067129420100319114239/0000643818/img/thumbnail/0000643818_130_130_0_91_20131122120310.PNG");

		// mene 설정
		menu = new Menu();
		menu.setId("dummyMenuId0");
		menu.setName("game/simulation");
		menuList.add(menu);
		layOut.setTitle(title);
		layOut.setSource(source);
		layOut.setMenu(menu);

		// Identifier 설정
		identifierList = new ArrayList<Identifier>();
		identifier.setType("episodeId");
		identifier.setText("H900063306");
		identifierList.add(identifier);

		// title 설정
		title.setText("추리, 심리, 미스터리 모음 테마 eBook 모음전");

		// price 설정
		price.setText(3000);

		// mene 설정
		menu = new Menu();
		menu.setId("dummyMenuId0");
		menu.setName("game/simulation");
		menuList.add(menu);

		// rights 설정
		rights.setGrade("PD004401");

		// contributor 설정
		contributor.setName("김기백");
		contributor.setPublisher("코믹플러스");
		// date 설정
		date.setType("date/update");
		date.setText("20130820190000");
		contributor.setDate(date);

		// source 설정
		source.setMediaType("image/jpeg");
		source.setType("thumbnail");
		source.setSize(659069);
		source.setUrl("http://wap.tstore.co.kr/android6/201311/22/IF1423067129420100319114239/0000643818/img/thumbnail/0000643818_130_130_0_91_20131122120310.PNG");
		sourceList.add(source);

		product.setIdentifierList(identifierList);
		product.setTitle(title);
		product.setPrice(price);
		product.setMenuList(menuList);
		product.setSourceList(sourceList);
		product.setRights(rights);
		product.setContributor(contributor);
		productList.add(product);

		commonResponse.setTotalCount(productList.size());
		res.setCommonResponse(commonResponse);
		res.setLayOut(layOut);
		res.setProductList(productList);

		return res;
	}
}
