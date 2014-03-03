/*
 * Copyright (c) 2013 SK planet.
 * All right reserved.
 *
 * This software is the confidential and proprietary information of SK planet.
 * You shall not disclose such Confidential Information and
 * shall use it only in accordance with the terms of the license agreement
 * you entered into with SK planet.
 */
package com.skplanet.storeplatform.sac.display.feature.brandshop.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.skplanet.storeplatform.framework.core.persistence.dao.CommonDAO;
import com.skplanet.storeplatform.sac.client.display.vo.brandshop.BrandshopSacReq;
import com.skplanet.storeplatform.sac.client.display.vo.brandshop.BrandshopSacRes;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.common.CommonResponse;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.common.Identifier;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.common.Menu;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.common.Price;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.common.Source;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.common.Title;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.product.Accrual;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.product.Layout;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.product.Product;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.product.Rights;
import com.skplanet.storeplatform.sac.common.header.vo.DeviceHeader;
import com.skplanet.storeplatform.sac.common.header.vo.SacRequestHeader;
import com.skplanet.storeplatform.sac.common.header.vo.TenantHeader;
import com.skplanet.storeplatform.sac.display.common.constant.DisplayConstants;
import com.skplanet.storeplatform.sac.display.feature.brandshop.vo.BrandshopInfo;
import com.skplanet.storeplatform.sac.display.meta.service.MetaInfoService;
import com.skplanet.storeplatform.sac.display.meta.vo.MetaInfo;
import com.skplanet.storeplatform.sac.display.meta.vo.ProductBasicInfo;
import com.skplanet.storeplatform.sac.display.response.ResponseInfoGenerateFacade;

/**
 * 
 * 
 * Updated on : 2014. 2. 25. Updated by : 이승훈, 엔텔스.
 */

@Service
public class BrandshopServiceImpl implements BrandshopService {
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
	 * com.skplanet.storeplatform.sac.biz.product.service.EbookComicThemeService#EbookComicThemeService(com.skplanet
	 * .storeplatform.sac.client.product.vo.EbookComicThemeRequestVO)
	 */
	@Override
	public BrandshopSacRes searchBrandshop(BrandshopSacReq req, SacRequestHeader header) {

		TenantHeader tenantHeader = header.getTenantHeader();
		DeviceHeader deviceHeader = header.getDeviceHeader();

		req.setTenantId(tenantHeader.getTenantId());
		req.setLangCd(tenantHeader.getLangCd());
		req.setDeviceModelCd(deviceHeader.getModel());

		BrandshopSacRes res = new BrandshopSacRes();

		if (req.getDummy() == null) {

			// 필수 파라미터 체크 channelId
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

			CommonResponse commonResponse = new CommonResponse();
			List<Product> productList = new ArrayList<Product>();
			// 브렌드샵 테마 조회
			List<BrandshopInfo> brandshopList = this.commonDAO.queryForList("Brandshop.selectBrandshop", req,
					BrandshopInfo.class);

			if (!brandshopList.isEmpty()) {

				Product product = null;

				// Identifier 설정
				Identifier identifier = null;
				List<Identifier> identifierList = null;
				Menu menu = null;
				List<Menu> menuList = null;
				Title title = null;

				List<Source> sourceList = null;
				Source source = null;

				BrandshopInfo brandshopInfo = null;
				Map<String, Object> reqMap = new HashMap<String, Object>();
				reqMap.put("tenantHeader", tenantHeader);
				reqMap.put("deviceHeader", deviceHeader);
				reqMap.put("prodStatusCd", DisplayConstants.DP_SALE_STAT_ING);

				for (int i = 0; i < brandshopList.size(); i++) {
					brandshopInfo = brandshopList.get(i);

					product = new Product(); // 결과물

					// identifier 정보
					identifier = new Identifier();
					identifierList = new ArrayList<Identifier>();

					identifier.setType("brand");
					identifier.setText(brandshopInfo.getBrandId());
					identifierList.add(identifier);
					product.setIdentifierList(identifierList);

					// 메뉴 정보
					menu = new Menu(); // 메뉴
					menuList = new ArrayList<Menu>(); // 메뉴 리스트
					menu.setId(brandshopInfo.getCategoryNo());
					menu.setName(brandshopInfo.getMenuNm());
					menu.setType("topClass");
					menuList.add(menu);
					product.setMenuList(menuList);

					// title 정보
					title = new Title();
					title.setText(brandshopInfo.getBrandShopNm());
					product.setTitle(title);

					// source 정보
					source = new Source();
					sourceList = new ArrayList<Source>();
					source.setType(DisplayConstants.DP_SOURCE_TYPE_THUMBNAIL);
					source.setUrl(brandshopInfo.getLogImgPos());
					sourceList.add(source);
					product.setSourceList(sourceList);

					// 데이터 매핑
					productList.add(i, product);

				}
				commonResponse.setTotalCount(brandshopList.get(0).getTotalCount());
				res.setProductList(productList);
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

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.skplanet.storeplatform.sac.biz.product.service.EbookComicThemeService#EbookComicThemeService(com.skplanet
	 * .storeplatform.sac.client.product.vo.EbookComicThemeRequestVO)
	 */
	@Override
	public BrandshopSacRes searchBrandshopList(BrandshopSacReq req, SacRequestHeader header) {

		TenantHeader tenantHeader = header.getTenantHeader();
		DeviceHeader deviceHeader = header.getDeviceHeader();

		req.setTenantId(tenantHeader.getTenantId());
		req.setLangCd(tenantHeader.getLangCd());
		req.setDeviceModelCd(deviceHeader.getModel());

		BrandshopSacRes res = new BrandshopSacRes();

		if (req.getDummy() == null) {

			// 필수 파라미터 체크 channelId
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

			if (req.getOrderedBy() != null) {
				req.setOrderedBy("DP000701");
			}

			CommonResponse commonResponse = new CommonResponse();
			List<Product> productList = new ArrayList<Product>();
			// 브렌드샵 테마 조회
			List<ProductBasicInfo> productBasicInfoList = this.commonDAO.queryForList("Brandshop.selectBrandshopList",
					req, ProductBasicInfo.class);

			// Meta DB 조회 파라미터 생성
			Map<String, Object> reqMap = new HashMap<String, Object>();

			reqMap.put("req", req);
			reqMap.put("tenantHeader", tenantHeader);
			reqMap.put("deviceHeader", deviceHeader);
			reqMap.put("lang", tenantHeader.getLangCd());

			if (!productBasicInfoList.isEmpty()) {

				Product product = null;

				for (ProductBasicInfo productBasicInfo : productBasicInfoList) {

					String topMenuId = productBasicInfo.getTopMenuId();
					String svcGrpCd = productBasicInfo.getSvcGrpCd();

					reqMap.put("productBasicInfo", productBasicInfo);
					reqMap.put("req", req);
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
				commonResponse.setTotalCount(productBasicInfoList.get(0).getTotalCount());
				res.setProductList(productList);
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
	private BrandshopSacRes generateDummy() {
		Identifier identifier = null;
		List<Identifier> identifierList;
		Title title = null;
		Menu menu = null;
		Source source = null;
		Price price = null;
		Rights rights = null;
		Accrual accrual = null;

		Product product = null;
		Layout layOut = null;

		List<Menu> menuList = null;
		List<Source> sourceList = null;

		sourceList = new ArrayList<Source>();
		List<Product> productList = new ArrayList<Product>();
		CommonResponse commonResponse = new CommonResponse();
		BrandshopSacRes res = new BrandshopSacRes();

		productList = new ArrayList<Product>();
		menuList = new ArrayList<Menu>();

		product = new Product();
		identifier = new Identifier();
		source = new Source();
		title = new Title();
		price = new Price();
		rights = new Rights();
		accrual = new Accrual();

		layOut = new Layout();
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

		// source 설정
		source.setMediaType("image/jpeg");
		source.setType("thumbnail");
		source.setSize(659069);
		source.setUrl("http://wap.tstore.co.kr/android6/201311/22/IF1423067129420100319114239/0000643818/img/thumbnail/0000643818_130_130_0_91_20131122120310.PNG");
		sourceList.add(source);

		// accrual 설정
		accrual.setVoterCount(3);
		accrual.setDownloadCount(2);
		accrual.setScore(10.0);

		// rights 설정
		rights.setGrade("PD004401");

		product.setIdentifierList(identifierList);
		product.setTitle(title);
		product.setPrice(price);
		product.setMenuList(menuList);
		product.setSourceList(sourceList);
		product.setAccrual(accrual);
		product.setRights(rights);
		productList.add(product);

		commonResponse.setTotalCount(productList.size());
		res.setCommonResponse(commonResponse);
		res.setLayout(layOut);
		res.setProductList(productList);

		return res;
	}
}
