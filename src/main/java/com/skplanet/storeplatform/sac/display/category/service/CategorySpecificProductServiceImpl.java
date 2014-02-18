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

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.skplanet.storeplatform.framework.core.exception.StorePlatformException;
import com.skplanet.storeplatform.framework.core.persistence.dao.CommonDAO;
import com.skplanet.storeplatform.sac.client.display.vo.category.CategorySpecificSacReq;
import com.skplanet.storeplatform.sac.client.display.vo.category.CategorySpecificSacRes;
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
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.product.Product;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.product.Rights;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.product.SalesOption;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.product.Support;
import com.skplanet.storeplatform.sac.common.header.vo.SacRequestHeader;
import com.skplanet.storeplatform.sac.display.common.constant.DisplayConstants;
import com.skplanet.storeplatform.sac.display.common.service.DisplayCommonService;
import com.skplanet.storeplatform.sac.display.meta.vo.MetaInfo;
import com.skplanet.storeplatform.sac.display.meta.vo.ProductBasicInfo;
import com.skplanet.storeplatform.sac.display.response.ResponseInfoGenerateFacade;

/**
 * 특정 상품 조회 Service 구현체
 * 
 * Updated on : 2014. 1. 27. Updated by : 오승민, 인크로스.
 */
@Service
public class CategorySpecificProductServiceImpl implements CategorySpecificProductService {
	private final Logger log = LoggerFactory.getLogger(this.getClass());

	@Autowired
	@Qualifier("sac")
	private CommonDAO commonDAO;

	@Autowired
	private ResponseInfoGenerateFacade responseInfoGenerateFacade;

	@Autowired
	private DisplayCommonService displayCommonService;

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.skplanet.storeplatform.sac.display.category.service.CategorySpecificProductService#getSpecificProductList
	 * (com.skplanet.storeplatform.sac.client.display.vo.category.CategorySpecificSacReq,
	 * com.skplanet.storeplatform.sac.common.header.vo.SacRequestHeader)
	 */
	@Override
	public CategorySpecificSacRes getSpecificProductList(CategorySpecificSacReq req, SacRequestHeader header) {
		// String tenantId = header.getTenantHeader().getTenantId();

		CategorySpecificSacRes res = new CategorySpecificSacRes();
		CommonResponse commonResponse = new CommonResponse();
		Product product = null;
		MetaInfo metaInfo = null;
		List<Product> productList = new ArrayList<Product>();

		if (req.getDummy() == null) {
			List<String> prodIdList = Arrays.asList(StringUtils.split(req.getList(), "+"));
			if (prodIdList.size() > DisplayConstants.DP_CATEGORY_SPECIFIC_PRODUCT_PARAMETER_LIMIT) {
				// TODO osm1021 에러 처리 추가 필요
				throw new StorePlatformException("SAC_DSP_0004", "list",
						DisplayConstants.DP_CATEGORY_SPECIFIC_PRODUCT_PARAMETER_LIMIT);
			}

			// 상품 기본 정보 List 조회
			List<ProductBasicInfo> productBasicInfoList = this.commonDAO.queryForList(
					"CategorySpecificProduct.selectProductInfoList", prodIdList, ProductBasicInfo.class);

			this.log.debug("##### parameter cnt : {}", prodIdList.size());
			this.log.debug("##### selected product basic info cnt : {}", productBasicInfoList.size());
			if (productBasicInfoList != null) {
				Map<String, Object> paramMap = new HashMap<String, Object>();
				paramMap.put("tenantHeader", header.getTenantHeader());
				paramMap.put("deviceHeader", header.getDeviceHeader());
				paramMap.put("prodStatusCd", DisplayConstants.DP_SALE_STAT_ING);
				paramMap.put("lang", "ko");

				for (ProductBasicInfo productBasicInfo : productBasicInfoList) {
					String topMenuId = productBasicInfo.getTopMenuId();
					String svcGrpCd = productBasicInfo.getSvcGrpCd();
					paramMap.put("productBasicInfo", productBasicInfo);

					this.log.debug("##### Top Menu Id : {}", topMenuId);
					this.log.debug("##### Service Group Cd : {}", svcGrpCd);

					// 상품 SVC_GRP_CD 조회
					// DP000203 : 멀티미디어
					// DP000206 : Tstore 쇼핑
					// DP000205 : 소셜쇼핑
					// DP000204 : 폰꾸미기
					// DP000201 : 애플리캐이션

					// APP 상품의 경우
					if (DisplayConstants.DP_APP_PROD_SVC_GRP_CD.equals(svcGrpCd)) {
						paramMap.put("imageCd", DisplayConstants.DP_APP_REPRESENT_IMAGE_CD);
						this.log.debug("##### Search for app specific product");
						metaInfo = this.commonDAO.queryForObject("CategorySpecificProduct.getAppMetaInfo", paramMap,
								MetaInfo.class);
						if (metaInfo != null) {
							product = this.responseInfoGenerateFacade.generateSpecificAppProduct(metaInfo);
							productList.add(product);
						}

					} else if (DisplayConstants.DP_MULTIMEDIA_PROD_SVC_GRP_CD.equals(svcGrpCd)) { // 멀티미디어 타입일 경우
						// 영화/방송 상품의 경우
						paramMap.put("imageCd", DisplayConstants.DP_VOD_REPRESENT_IMAGE_CD);
						if (DisplayConstants.DP_MOVIE_TOP_MENU_ID.equals(topMenuId)
								|| DisplayConstants.DP_TV_TOP_MENU_ID.equals(topMenuId)) {
							this.log.debug("##### Search for Vod specific product");
							metaInfo = this.commonDAO.queryForObject("CategorySpecificProduct.getVODMetaInfo",
									paramMap, MetaInfo.class);
							if (metaInfo != null) {
								if (DisplayConstants.DP_MOVIE_TOP_MENU_ID.equals(topMenuId)) {
									product = this.responseInfoGenerateFacade.generateSpecificMovieProduct(metaInfo);
								} else {
									product = this.responseInfoGenerateFacade
											.generateSpecificBroadcastProduct(metaInfo);
								}
								productList.add(product);
							}
						} else if (DisplayConstants.DP_EBOOK_TOP_MENU_ID.equals(topMenuId)
								|| DisplayConstants.DP_COMIC_TOP_MENU_ID.equals(topMenuId)) { // Ebook / Comic 상품의 경우

							paramMap.put("imageCd", DisplayConstants.DP_EBOOK_COMIC_REPRESENT_IMAGE_CD);
							this.log.debug("##### Search for EbookComic specific product");
							metaInfo = this.commonDAO.queryForObject("CategorySpecificProduct.getEbookComicMetaInfo",
									paramMap, MetaInfo.class);
							if (metaInfo != null) {
								if (DisplayConstants.DP_EBOOK_TOP_MENU_ID.equals(topMenuId)) {
									product = this.responseInfoGenerateFacade.generateSpecificEbookProduct(metaInfo);
								} else {
									product = this.responseInfoGenerateFacade.generateSpecificComicProduct(metaInfo);
								}
								productList.add(product);
							}

						} else if (DisplayConstants.DP_MUSIC_TOP_MENU_ID.equals(topMenuId)) { // 음원 상품의 경우
							// 배치완료 기준일시 조회
							// TODO 기준 ListID가 없기 때문에 일단 멜론 Top 100으로 고정
							// String stdDt = this.displayCommonService.getBatchStandardDateString(tenantId,
							// "MELON_DP004901");
							// paramMap.put("stdDt", stdDt);
							productBasicInfo.setMenuId("DP004901");
							paramMap.put("imageCd", DisplayConstants.DP_MUSIC_REPRESENT_IMAGE_CD);

							// TODO osm1021 dummy data 꼭 삭제할것
							paramMap.put("stdDt", "20110806");

							this.log.debug("##### Search for Music specific product");
							metaInfo = this.commonDAO.queryForObject("CategorySpecificProduct.getMusicMetaInfo",
									paramMap, MetaInfo.class);
							if (metaInfo != null) {
								product = this.responseInfoGenerateFacade.generateSpecificMusicProduct(metaInfo);
								productList.add(product);
							}
						}
					} else if (DisplayConstants.DP_TSTORE_SHOPPING_PROD_SVC_GRP_CD.equals(svcGrpCd)) { // 쇼핑 상품의 경우
						paramMap.put("prodRshpCd", DisplayConstants.DP_CHANNEL_EPISHODE_RELATIONSHIP_CD);
						paramMap.put("imageCd", DisplayConstants.DP_SHOPPING_REPRESENT_IMAGE_CD);

						this.log.debug("##### Search for Shopping specific product");
						metaInfo = this.commonDAO.queryForObject("CategorySpecificProduct.getShoppingMetaInfo",
								paramMap, MetaInfo.class);
						if (metaInfo != null) {
							product = this.responseInfoGenerateFacade.generateSpecificShoppingProduct(metaInfo);
							productList.add(product);
						}
					}
				}
			}
			commonResponse.setTotalCount(productList.size());
			res.setCommonResponse(commonResponse);
			res.setProductList(productList);
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
	private CategorySpecificSacRes generateDummy() {
		Identifier identifier = null;
		Support support = null;
		Menu menu = null;
		Accrual accrual = null;
		Rights rights = null;
		Title title = null;
		Source source = null;
		Price price = null;

		App app = null;

		List<Menu> menuList = null;
		List<Source> sourceList = null;
		List<Support> supportList = null;
		Product product = null;
		List<Product> productList = new ArrayList<Product>();
		CommonResponse commonResponse = new CommonResponse();
		CategorySpecificSacRes res = new CategorySpecificSacRes();

		menuList = new ArrayList<Menu>();
		sourceList = new ArrayList<Source>();
		supportList = new ArrayList<Support>();

		product = new Product();
		identifier = new Identifier();
		app = new App();
		accrual = new Accrual();
		rights = new Rights();
		source = new Source();
		price = new Price();
		title = new Title();
		support = new Support();

		identifier = new Identifier();
		identifier.setType("episodeId");
		identifier.setText("0000643818");

		support.setType("iab");
		support.setText("PD012301");
		supportList.add(support);

		menu = new Menu();
		menu.setId("DP000501");
		menu.setName("게임");
		menu.setType("topClass");
		menuList.add(menu);
		menu = new Menu();
		menu.setId("DP01004");
		menu.setName("RPG");
		menuList.add(menu);

		app.setAid("OA00643818");
		app.setPackageName("proj.syjt.tstore");
		app.setVersionCode("11000");
		app.setVersion("1.1");

		accrual.setVoterCount(14305);
		accrual.setDownloadCount(513434);
		accrual.setScore(4.8);

		rights.setGrade("0");

		title.setText("워밸리 온라인");

		source.setType("thumbnail");
		source.setUrl("http://wap.tstore.co.kr/android6/201311/22/IF1423067129420100319114239/0000643818/img/thumbnail/0000643818_130_130_0_91_20131122120310.PNG");
		sourceList.add(source);

		price.setText(0);

		product = new Product();
		product.setIdentifier(identifier);
		// product.setSupport("y|iab");
		product.setSupportList(supportList);
		product.setMenuList(menuList);
		product.setApp(app);
		product.setAccrual(accrual);
		product.setRights(rights);
		product.setTitle(title);
		product.setSourceList(sourceList);
		product.setProductExplain("★이벤트★세상에 없던 모바일 MMORPG!");
		product.setPrice(price);

		productList.add(product);

		menuList = new ArrayList<Menu>();
		sourceList = new ArrayList<Source>();
		supportList = new ArrayList<Support>();

		product = new Product();
		identifier = new Identifier();
		accrual = new Accrual();
		rights = new Rights();
		title = new Title();
		source = new Source();
		price = new Price();
		support = new Support();

		// 상품ID
		identifier = new Identifier();
		identifier.setType("channelId");
		identifier.setText("H001540562");

		support.setType("hd");
		support.setText("Y");
		supportList.add(support);

		menu = new Menu();
		menu.setId("DP000517");
		menu.setName("영화");
		menu.setType("topClass");
		menuList.add(menu);
		menu = new Menu();
		menu.setId("DP17002");
		menu.setName("액션");
		menuList.add(menu);

		accrual.setVoterCount(51);
		accrual.setDownloadCount(5932);
		accrual.setScore(3.8);

		rights.setGrade("4");

		title.setText("[20%할인]친구 2");

		source.setUrl("http://wap.tstore.co.kr/SMILE_DATA7/PVOD/201401/02/0002057676/3/0003876930/3/RT1_02000024893_1_0921_182x261_130x186.PNG");
		sourceList.add(source);

		price.setText(3200);

		product = new Product();
		product.setIdentifier(identifier);
		product.setSupportList(supportList);
		product.setMenuList(menuList);
		product.setAccrual(accrual);
		product.setRights(rights);
		product.setTitle(title);
		product.setSourceList(sourceList);
		product.setProductExplain("니 내랑 부산 접수할래? ...");
		product.setPrice(price);
		productList.add(product);

		product = this.generateShoppingProduct();
		productList.add(product);

		commonResponse.setTotalCount(productList.size());
		res.setCommonResponse(commonResponse);
		res.setProductList(productList);
		return res;
	}

	/**
	 * <pre>
	 * Dummy date 생성.
	 * </pre>
	 * 
	 * @return Product
	 */
	private Product generateShoppingProduct() {
		Identifier identifier = new Identifier();
		Menu menu = new Menu();
		Contributor contributor = new Contributor();
		Date date = new Date();
		Accrual accrual = new Accrual();
		Rights rights = new Rights();
		Title title = new Title();
		Source source = new Source();
		Price price = new Price();
		List<Menu> menuList = new ArrayList<Menu>();
		List<Source> sourceList = new ArrayList<Source>();
		Product product = new Product();

		identifier.setType("catalog");
		identifier.setText("1234");

		// 메뉴 정보
		menu.setType("menuId");
		menu.setId("DP28013");
		menu.setName("버거/치킨/피자");
		menuList.add(menu);

		// 상품 정보 (상품명)
		title.setText("커피/음료/아이스크림");

		// 상품 정보 (상품가격)
		price.setFixedPrice(17500);
		price.setDiscountRate(10d);
		price.setText(15750);

		source.setType("thumbnail");
		source.setUrl("inst_thumbnail_20111216154840.jpg");
		sourceList.add(source);

		// 다운로드 수
		accrual.setDownloadCount(3000);

		// 이용권한 정보
		date.setType("duration/salePeriod");
		date.setText("20130820190000/20131231235959");
		rights.setGrade("0");
		rights.setDate(date);

		// contributor
		identifier = new Identifier();
		identifier.setType("brand");
		identifier.setText("세븐일레븐 바이더웨이");
		contributor.setName("세븐일레븐");
		contributor.setIdentifier(identifier);

		// saleoption
		SalesOption saleoption = new SalesOption();
		saleoption.setType("type");

		// 데이터 매핑
		product.setIdentifier(identifier);
		product.setMenuList(menuList);
		product.setTitle(title);
		product.setPrice(price);
		product.setSourceList(sourceList);
		product.setAccrual(accrual);
		product.setRights(rights);
		product.setContributor(contributor);
		product.setSalesOption(saleoption);

		return product;
		// SalesOption saleoption = null;
		// product = new Product();
		// identifier = new Identifier();
		// contributor = new Contributor();
		// accrual = new Accrual();
		// rights = new Rights();
		// title = new Title();
		// source = new Source();
		// price = new Price();
		// support = new Support();
		//
		// // 상품 정보 (상품ID)
		// product = new Product();
		// identifier = new Identifier();
		// identifier.setType("catagoryId");
		// identifier.setText("1234");
		//
		// // 메뉴 정보
		// menuList = new ArrayList<Menu>();
		// menu = new Menu();
		// menu.setType("topClass");
		// menu.setId("DP000528");
		// menu.setName("쇼핑");
		// menuList.add(menu);
		//
		// menu = new Menu();
		// menu.setType("topClass");
		// menu.setId("DP28013");
		// menu.setName("버거/치킨/피자");
		// menuList.add(menu);
		//
		// // 상품 정보 (상품명)
		// title = new Title();
		// title.setText("커피/음료/아이스크림");
		//
		// // 상품 정보 (상품가격)
		// price = new Price();
		// price.setFixedPrice(17500);
		// price.setDiscountRate(10d);
		// price.setText(15750);
		//
		// // 이미지 정보
		// sourceList = new ArrayList<Source>();
		// source = new Source();
		// source.setType("thumbnail");
		// source.setUrl("inst_thumbnail_20111216154840.jpg");
		// sourceList.add(source);
		//
		// // 다운로드 수
		// accrual = new Accrual();
		// accrual.setDownloadCount(6229);
		//
		// // 이용권한 정보
		// rights = new Rights();
		// date = new Date();
		// date.setText("20130820190000/20131231235959");
		// rights.setGrade("PD004401");
		// rights.setDate(date);
		//
		// // contributor
		// contributor = new Contributor();
		// identifier = new Identifier();
		// identifier.setType("brandId");
		// identifier.setText("세븐일레븐 바이더웨이");
		// contributor.setIdentifier(identifier);
		//
		// // saleoption
		// saleoption = new SalesOption();
		//
		// // 데이터 매핑
		// product.setIdentifier(identifier);
		// product.setMenuList(menuList);
		// product.setTitle(title);
		// product.setPrice(price);
		// product.setSourceList(sourceList);
		// product.setAccrual(accrual);
		// product.setRights(rights);
		// product.setContributor(contributor);
		// return product;
	}
}
