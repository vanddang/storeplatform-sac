/*
 * Copyright (c) 2013 SK planet.
 * All right reserved.
 *
 * This software is the confidential and proprietary information of SK planet.
 * You shall not disclose such Confidential Information and
 * shall use it only in accordance with the terms of the license agreement
 * you entered into with SK planet.
 */
package com.skplanet.storeplatform.sac.display.related.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.map.JsonMappingException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.skplanet.storeplatform.framework.core.exception.StorePlatformException;
import com.skplanet.storeplatform.framework.core.persistence.dao.CommonDAO;
import com.skplanet.storeplatform.sac.client.display.vo.related.BoughtTogetherProductSacReq;
import com.skplanet.storeplatform.sac.client.display.vo.related.BoughtTogetherProductSacRes;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.common.CommonResponse;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.product.Product;
import com.skplanet.storeplatform.sac.common.header.vo.SacRequestHeader;
import com.skplanet.storeplatform.sac.display.common.constant.DisplayConstants;
import com.skplanet.storeplatform.sac.display.meta.service.MetaInfoService;
import com.skplanet.storeplatform.sac.display.meta.vo.MetaInfo;
import com.skplanet.storeplatform.sac.display.meta.vo.ProductBasicInfo;
import com.skplanet.storeplatform.sac.display.response.ResponseInfoGenerateFacade;

/**
 * BoughtTogether Product Service 인터페이스(CoreStoreBusiness)
 * 
 * Updated on : 2014. 02. 18. Updated by : 유시혁.
 */
@Service
public class BoughtTogetherProductServiceImpl implements BoughtTogetherProductService {

	private final Logger log = LoggerFactory.getLogger(this.getClass());

	@Autowired
	@Qualifier("sac")
	private CommonDAO commonDAO;

	@Autowired
	private MetaInfoService metaInfoService;

	@Autowired
	private ResponseInfoGenerateFacade responseInfoGenerateFacade;

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.skplanet.storeplatform.sac.biz.product.service.BoughtTogetherProductListService#searchBoughtTogetherProductList
	 * ( RelatedProductRequest requestVO)
	 */
	@Override
	public BoughtTogetherProductSacRes searchBoughtTogetherProductList(BoughtTogetherProductSacReq requestVO,
			SacRequestHeader requestHeader) throws JsonGenerationException, JsonMappingException, IOException,
			Exception {

		// 헤더 값 세팅
		this.log.debug("헤더 값 세팅");
		requestVO.setTenantId(requestHeader.getTenantHeader().getTenantId());
		requestVO.setLangCd(requestHeader.getTenantHeader().getLangCd());
		requestVO.setDeviceModelCd(requestHeader.getDeviceHeader().getModel());

		// 요청 값 세팅
		this.log.debug("요청 값 세팅");
		requestVO.setOffset(requestVO.getOffset() != null ? requestVO.getOffset() : 1);
		requestVO.setCount(requestVO.getCount() != null ? requestVO.getCount() : 20);
		if (!StringUtils.isEmpty(requestVO.getExceptId())) {
			requestVO.setArrayExceptId(requestVO.getExceptId().split("\\+"));
		}

		// 필수 파라미터 체크
		this.log.debug("필수 파라미터 체크");
		if (StringUtils.isEmpty(requestVO.getProductId())) {
			throw new StorePlatformException("SAC_DSP_0002", "productId", requestVO.getProductId());
		}

		BoughtTogetherProductSacRes boughtTogetherProductSacRes = new BoughtTogetherProductSacRes();
		CommonResponse commonResponse = new CommonResponse();
		Map<String, Object> reqMap = new HashMap<String, Object>();
		MetaInfo retMetaInfo = null;
		Product product = null;

		// 이 상품과 유사 상품 조회
		this.log.debug("이 상품과 함께 구매한 상품 조회");
		List<ProductBasicInfo> boughtTogetherProductList = this.commonDAO.queryForList(
				"BoughtTogetherProduct.selectBoughtTogetherProductList", requestVO, ProductBasicInfo.class);
		List<Product> productList = new ArrayList<Product>();

		if (!boughtTogetherProductList.isEmpty()) {
			reqMap.put("tenantHeader", requestHeader.getTenantHeader());
			reqMap.put("deviceHeader", requestHeader.getDeviceHeader());
			reqMap.put("prodStatusCd", DisplayConstants.DP_SALE_STAT_ING);

			for (ProductBasicInfo productBasicInfo : boughtTogetherProductList) {

				reqMap.put("productBasicInfo", productBasicInfo);

				if (productBasicInfo.getTopMenuId().equals(DisplayConstants.DP_EBOOK_TOP_MENU_ID)) {
					reqMap.put("imageCd", DisplayConstants.DP_EBOOK_COMIC_REPRESENT_IMAGE_CD);
					retMetaInfo = this.metaInfoService.getEbookComicMetaInfo(reqMap);
					if (retMetaInfo != null) {
						product = this.responseInfoGenerateFacade.generateEbookProduct(retMetaInfo);
						productList.add(product);
					}
				} else if (productBasicInfo.getTopMenuId().equals(DisplayConstants.DP_COMIC_TOP_MENU_ID)) {
					reqMap.put("imageCd", DisplayConstants.DP_EBOOK_COMIC_REPRESENT_IMAGE_CD);
					retMetaInfo = this.metaInfoService.getEbookComicMetaInfo(reqMap);
					if (retMetaInfo != null) {
						product = this.responseInfoGenerateFacade.generateComicProduct(retMetaInfo);
						productList.add(product);
					}
				} else if (productBasicInfo.getTopMenuId().equals(DisplayConstants.DP_MOVIE_TOP_MENU_ID)) {
					reqMap.put("imageCd", DisplayConstants.DP_VOD_REPRESENT_IMAGE_CD);
					retMetaInfo = this.metaInfoService.getVODMetaInfo(reqMap);
					if (retMetaInfo != null) {
						product = this.responseInfoGenerateFacade.generateMovieProduct(retMetaInfo);
						productList.add(product);
					}
				} else if (productBasicInfo.getTopMenuId().equals(DisplayConstants.DP_TV_TOP_MENU_ID)) {
					reqMap.put("imageCd", DisplayConstants.DP_VOD_REPRESENT_IMAGE_CD);
					retMetaInfo = this.metaInfoService.getVODMetaInfo(reqMap);
					if (retMetaInfo != null) {
						product = this.responseInfoGenerateFacade.generateBroadcastProduct(retMetaInfo);
						productList.add(product);
					}
				} else if (productBasicInfo.getTopMenuId().equals(DisplayConstants.DP_MUSIC_TOP_MENU_ID)) {
					reqMap.put("imageCd", DisplayConstants.DP_MUSIC_REPRESENT_IMAGE_CD);
					retMetaInfo = this.metaInfoService.getMusicMetaInfo(reqMap);
					if (retMetaInfo != null) {
						product = this.responseInfoGenerateFacade.generateMusicProduct(retMetaInfo);
						productList.add(product);
					}
				} else {
					reqMap.put("imageCd", DisplayConstants.DP_APP_REPRESENT_IMAGE_CD);
					retMetaInfo = this.metaInfoService.getAppMetaInfo(reqMap);
					if (retMetaInfo != null) {
						product = this.responseInfoGenerateFacade.generateAppProduct(retMetaInfo);
						productList.add(product);
					}
				}
			}
			commonResponse.setTotalCount(boughtTogetherProductList.get(0).getTotalCount());
			boughtTogetherProductSacRes.setProductList(productList);
		} else {
			commonResponse.setTotalCount(0);
		}
		this.log.debug("이 상품과 함께 구매한 상품 조회 결과 : " + commonResponse.getTotalCount() + "건");
		boughtTogetherProductSacRes.setCommonResponse(commonResponse);

		// int totalCount = 1;
		//
		// RelatedProductListRes responseVO = null;
		// CommonResponse commonResponse = null;
		//
		// if (true) {
		//
		// // Response VO를 만들기위한 생성자
		// List<Product> productList = new ArrayList<Product>();
		// List<Menu> menuList = new ArrayList<Menu>();
		// List<Source> sourceList = new ArrayList<Source>();
		//
		// for (int i = 1; i <= totalCount; i++) {
		// Product product = new Product();
		//
		// Identifier identifier = new Identifier();
		// Title title = new Title();
		// Price price = new Price();
		// Menu menu = new Menu();
		// Source source = new Source();
		// Accrual accrual = new Accrual();
		// Rights rights = new Rights();
		// App app = new App();
		//
		// // 상품ID
		// identifier = new Identifier();
		// identifier.setText("0000254239");
		//
		// // App
		// app.setAid("OA00254239");
		// app.setPackageName("net.hwado.paid.PDFGenerator");
		// app.setSize(1280765);
		// app.setVersionCode("1");
		// app.setVersion("1.0");
		//
		// /*
		// * Menu(메뉴정보) Id, Name, Type
		// */
		// menu.setId("MN000504");
		// menu.setName("생활/위치");
		// menu.setType("topClass");
		// menuList.add(menu);
		// menu = new Menu();
		// menu.setId("MN04003");
		// menu.setName("유틸리티");
		// menu.setType("");
		// menuList.add(menu);
		//
		// /*
		// * TITLE
		// */
		// title.setText("PDF 생성기");
		//
		// price.setText(1000);
		//
		// /*
		// * source mediaType, size, type, url
		// */
		// source.setMediaType("image/png");
		// source.setSize(0);
		// source.setType("thumbNail");
		// source.setUrl("http://wap.tstore.co.kr/images/IF1423022570420091207142717/0000254239/thumbnail/0000254239_130_130_0_91.PNG");
		// sourceList.add(source);
		//
		// /*
		// * Accrual changeRank 변동 순위, 하락은 음수로 표현한다.
		// */
		// accrual.setDownloadCount(246);
		// accrual.setVoterCount(2);
		// accrual.setScore(3.0);
		//
		// rights.setGrade("0");
		//
		// product = new Product();
		// product.setProductExplain("PDF 생성기,PDF Generator,HWADO,hwado,www.hwado.net,HWADOTECH");
		// product.setIdentifier(identifier);
		// product.setPrice(price);
		// product.setApp(app);
		// product.setMenuList(menuList);
		// product.setAccrual(accrual);
		// product.setTitle(title);
		// product.setRights(rights);
		// product.setSourceList(sourceList);
		//
		// productList.add(product);
		//
		// }
		//
		// responseVO = new RelatedProductListRes();
		// commonResponse = new CommonResponse();
		// responseVO.setProductList(productList);
		// commonResponse.setTotalCount(totalCount);
		// responseVO.setCommonRes(commonResponse);
		//
		// ObjectMapper objectMapper = new ObjectMapper();
		// objectMapper.setSerializationInclusion(JsonSerialize.Inclusion.NON_DEFAULT);
		// String json = objectMapper.writeValueAsString(responseVO);
		//
		// this.log.debug("test json : {}", json);
		// // System.out.println(json);
		//
		// }
		return boughtTogetherProductSacRes;
	}

}
