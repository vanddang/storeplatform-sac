/*
 * Copyright (c) 2013 SK planet.
 * All right reserved.
 *
 * This software is the confidential and proprietary information of SK planet.
 * You shall not disclose such Confidential Information and
 * shall use it only in accordance with the terms of the license agreement
 * you entered into with SK planet.
 */
package com.skplanet.storeplatform.sac.display.shopping.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.skplanet.storeplatform.framework.core.exception.StorePlatformException;
import com.skplanet.storeplatform.framework.core.persistence.dao.CommonDAO;
import com.skplanet.storeplatform.sac.client.display.vo.shopping.ShoppingReq;
import com.skplanet.storeplatform.sac.client.display.vo.shopping.ShoppingRes;
import com.skplanet.storeplatform.sac.client.display.vo.shopping.ShoppingThemeRes;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.common.CommonResponse;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.common.Date;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.common.Identifier;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.common.Menu;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.common.Price;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.common.Source;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.common.Title;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.product.Accrual;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.product.Contributor;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.product.Layout;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.product.Product;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.product.Promotion;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.product.Rights;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.product.SalesOption;
import com.skplanet.storeplatform.sac.common.header.vo.DeviceHeader;
import com.skplanet.storeplatform.sac.common.header.vo.SacRequestHeader;
import com.skplanet.storeplatform.sac.common.header.vo.TenantHeader;
import com.skplanet.storeplatform.sac.display.common.constant.DisplayConstants;
import com.skplanet.storeplatform.sac.display.common.service.DisplayCommonService;
import com.skplanet.storeplatform.sac.display.meta.service.MetaInfoService;
import com.skplanet.storeplatform.sac.display.meta.vo.MetaInfo;
import com.skplanet.storeplatform.sac.display.meta.vo.ProductBasicInfo;
import com.skplanet.storeplatform.sac.display.response.ResponseInfoGenerateFacade;
import com.skplanet.storeplatform.sac.display.shopping.vo.Shopping;

/**
 * ShoppingList Service 인터페이스(CoreStoreBusiness) 구현체
 * 
 * Updated on : 2013. 12. 23. Updated by : 김형식, SK 플래닛.
 */
@Service
@Transactional
public class ShoppingServiceImpl implements ShoppingService {

	private final Logger log = LoggerFactory.getLogger(this.getClass());

	@Autowired
	@Qualifier("sac")
	private CommonDAO commonDAO;

	@Autowired
	private DisplayCommonService displayCommonService;

	@Autowired
	private MetaInfoService metaInfoService;

	@Autowired
	private ResponseInfoGenerateFacade responseInfoGenerateFacade;

	/**
	 * <pre>
	 * 쇼핑 추천/인기 상품 리스트 조회.
	 * </pre>
	 */
	@Override
	public ShoppingRes getFeatureProductList(SacRequestHeader header, ShoppingReq req) {

		// 공통 응답 변수 선언
		ShoppingRes res = new ShoppingRes();
		CommonResponse commonResponse = new CommonResponse();
		req.setLangCd(header.getTenantHeader().getLangCd());
		req.setImageCd("DP000164");
		req.setDeviceModelCd("SHV-E330SSO");

		// 필수 파라미터 체크
		if (StringUtils.isEmpty(header.getTenantHeader().getTenantId())) {
			throw new StorePlatformException("SAC_DSP_0002", "tenantId", req.getTenantId());
		}
		if (StringUtils.isEmpty(req.getListId())) {
			throw new StorePlatformException("SAC_DSP_0002", "listId", req.getListId());
		}

		if (StringUtils.isEmpty(req.getProdCharge())) {
			req.setProdCharge(null);
		}
		if (StringUtils.isEmpty(req.getProdGradeCd())) {
			req.setProdGradeCd(null);
		}
		// offset, Count default setting
		this.commonOffsetCount(req);
		// 배치완료 기준일시 조회
		String stdDt = this.displayCommonService.getBatchStandardDateString(header.getTenantHeader().getTenantId(),
				req.getListId());

		// 기준일시 체크
		if (StringUtils.isEmpty(stdDt)) {
			throw new StorePlatformException("SAC_DSP_0003", "stdDt", stdDt);
		}

		TenantHeader tenantHeader = header.getTenantHeader();
		DeviceHeader deviceHeader = header.getDeviceHeader();

		// DB 조회 파라미터 생성
		Map<String, Object> reqMap = new HashMap<String, Object>();
		reqMap.put("req", req);
		reqMap.put("tenantHeader", tenantHeader);
		reqMap.put("deviceHeader", deviceHeader);
		reqMap.put("stdDt", stdDt);
		reqMap.put("lang", tenantHeader.getLangCd());

		// TODO osm1021 Dummy data 꼭 삭제할것!!!!!!!!!!
		reqMap.put("imageCd", req.getImageCd());
		reqMap.put("svcGrpCd", DisplayConstants.DP_TSTORE_SHOPPING_PROD_SVC_GRP_CD);
		reqMap.put("contentTypeCd", DisplayConstants.DP_CHANNEL_CONTENT_TYPE_CD);
		reqMap.put("prodStatusCd", DisplayConstants.DP_SALE_STAT_ING);
		reqMap.put("prodRshpCd", DisplayConstants.DP_CHANNEL_EPISHODE_RELATIONSHIP_CD);

		// ID list 조회
		List<ProductBasicInfo> productBasicInfoList = this.commonDAO.queryForList("Shopping.getFeatureProductList",
				reqMap, ProductBasicInfo.class);
		List<Product> productList = new ArrayList<Product>();
		if (productBasicInfoList != null) {
			if (productBasicInfoList.size() > 0) {
				for (ProductBasicInfo productBasicInfo : productBasicInfoList) {
					reqMap.put("productBasicInfo", productBasicInfo);
					// 쇼핑 Meta 정보 조회
					MetaInfo retMetaInfo = this.metaInfoService.getShoppingMetaInfo(reqMap);
					if (retMetaInfo != null) {
						// 쇼핑 Response Generate
						Product product = this.responseInfoGenerateFacade.generateShoppingProduct(retMetaInfo);
						productList.add(product);
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
		}
		return res;
	}

	/**
	 * <pre>
	 * 쇼핑 신규 상품 조회 .
	 * </pre>
	 */
	@Override
	public ShoppingRes getNewProductList(SacRequestHeader header, ShoppingReq req) {
		// 공통 응답 변수 선언
		ShoppingRes res = new ShoppingRes();
		CommonResponse commonResponse = new CommonResponse();
		req.setLangCd(header.getTenantHeader().getLangCd());
		req.setImageCd("DP000164");
		req.setDeviceModelCd("SHV-E330SSO");

		// 필수 파라미터 체크
		if (StringUtils.isEmpty(header.getTenantHeader().getTenantId())) {
			throw new StorePlatformException("SAC_DSP_0002", "tenantId", req.getTenantId());
		}

		if (StringUtils.isEmpty(req.getProdCharge())) {
			req.setProdCharge(null);
		}
		if (StringUtils.isEmpty(req.getProdGradeCd())) {
			req.setProdGradeCd(null);
		}
		// offset, Count default setting
		this.commonOffsetCount(req);

		TenantHeader tenantHeader = header.getTenantHeader();
		DeviceHeader deviceHeader = header.getDeviceHeader();

		// DB 조회 파라미터 생성
		Map<String, Object> reqMap = new HashMap<String, Object>();
		reqMap.put("req", req);
		reqMap.put("tenantHeader", tenantHeader);
		reqMap.put("deviceHeader", deviceHeader);
		reqMap.put("lang", tenantHeader.getLangCd());

		// TODO osm1021 Dummy data 꼭 삭제할것!!!!!!!!!!
		reqMap.put("imageCd", req.getImageCd());
		reqMap.put("svcGrpCd", DisplayConstants.DP_TSTORE_SHOPPING_PROD_SVC_GRP_CD);
		reqMap.put("contentTypeCd", DisplayConstants.DP_CHANNEL_CONTENT_TYPE_CD);
		reqMap.put("prodStatusCd", DisplayConstants.DP_SALE_STAT_ING);
		reqMap.put("prodRshpCd", DisplayConstants.DP_CHANNEL_EPISHODE_RELATIONSHIP_CD);

		// ID list 조회
		List<ProductBasicInfo> productBasicInfoList = this.commonDAO.queryForList("Shopping.getNewProductList", reqMap,
				ProductBasicInfo.class);
		List<Product> productList = new ArrayList<Product>();
		if (productBasicInfoList != null) {
			if (productBasicInfoList.size() > 0) {
				for (ProductBasicInfo productBasicInfo : productBasicInfoList) {
					reqMap.put("productBasicInfo", productBasicInfo);
					// 쇼핑 Meta 정보 조회
					MetaInfo retMetaInfo = this.metaInfoService.getShoppingMetaInfo(reqMap);
					if (retMetaInfo != null) {
						// 쇼핑 Response Generate
						Product product = this.responseInfoGenerateFacade.generateShoppingProduct(retMetaInfo);
						productList.add(product);
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
		}
		return res;

	}

	/**
	 * <pre>
	 *  쇼핑 세부카테고리  상품 조회 .
	 * </pre>
	 */
	@Override
	public ShoppingRes getSubProductList(SacRequestHeader header, ShoppingReq req) {
		// 공통 응답 변수 선언
		ShoppingRes res = new ShoppingRes();
		CommonResponse commonResponse = new CommonResponse();
		req.setLangCd(header.getTenantHeader().getLangCd());
		req.setImageCd("DP000164");
		req.setDeviceModelCd("SHV-E330SSO");

		// 필수 파라미터 체크
		if (StringUtils.isEmpty(header.getTenantHeader().getTenantId())) {
			throw new StorePlatformException("SAC_DSP_0002", "tenantId", req.getTenantId());
		}
		if (StringUtils.isEmpty(req.getMenuId())) {
			throw new StorePlatformException("SAC_DSP_0002", "menuId", req.getMenuId());
		}

		if (StringUtils.isEmpty(req.getProdCharge())) {
			req.setProdCharge(null);
		}
		if (StringUtils.isEmpty(req.getProdGradeCd())) {
			req.setProdGradeCd(null);
		}
		if (StringUtils.isEmpty(req.getOrderedBy())) {
			req.setOrderedBy(DisplayConstants.DP_SHOPPING_RECENT_DEFAULT_ORDERED_OPTION);
		}
		// offset, Count default setting
		this.commonOffsetCount(req);

		String stdDt = "";
		if (req.getOrderedBy().equals("download")) {
			// 필수 파라미터 체크
			if (StringUtils.isEmpty(req.getListId())) {
				throw new StorePlatformException("SAC_DSP_0002", "listId", req.getListId());
			}

			stdDt = this.displayCommonService.getBatchStandardDateString(header.getTenantHeader().getTenantId(),
					req.getListId());

			// 기준일시 체크
			if (StringUtils.isEmpty(stdDt)) {
				throw new StorePlatformException("SAC_DSP_0003", "stdDt", stdDt);
			}
			req.setStdDt(stdDt);
		}
		TenantHeader tenantHeader = header.getTenantHeader();
		DeviceHeader deviceHeader = header.getDeviceHeader();

		// DB 조회 파라미터 생성
		Map<String, Object> reqMap = new HashMap<String, Object>();
		reqMap.put("req", req);
		reqMap.put("tenantHeader", tenantHeader);
		reqMap.put("deviceHeader", deviceHeader);
		reqMap.put("stdDt", stdDt);
		reqMap.put("lang", tenantHeader.getLangCd());

		// TODO osm1021 Dummy data 꼭 삭제할것!!!!!!!!!!
		reqMap.put("imageCd", req.getImageCd());
		reqMap.put("svcGrpCd", DisplayConstants.DP_TSTORE_SHOPPING_PROD_SVC_GRP_CD);
		reqMap.put("contentTypeCd", DisplayConstants.DP_CHANNEL_CONTENT_TYPE_CD);
		reqMap.put("prodStatusCd", DisplayConstants.DP_SALE_STAT_ING);
		reqMap.put("prodRshpCd", DisplayConstants.DP_CHANNEL_EPISHODE_RELATIONSHIP_CD);

		// ID list 조회
		List<ProductBasicInfo> productBasicInfoList = this.commonDAO.queryForList("Shopping.getSubProductList", reqMap,
				ProductBasicInfo.class);
		List<Product> productList = new ArrayList<Product>();
		if (productBasicInfoList != null) {
			if (productBasicInfoList.size() > 0) {
				for (ProductBasicInfo productBasicInfo : productBasicInfoList) {
					reqMap.put("productBasicInfo", productBasicInfo);
					// 쇼핑 Meta 정보 조회
					MetaInfo retMetaInfo = this.metaInfoService.getShoppingMetaInfo(reqMap);
					if (retMetaInfo != null) {
						// 쇼핑 Response Generate
						Product product = this.responseInfoGenerateFacade.generateShoppingProduct(retMetaInfo);
						productList.add(product);
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
		}
		return res;

	}

	/**
	 * <pre>
	 * 특가 상품 리스트 조회.
	 * </pre>
	 */
	@Override
	public ShoppingRes getSecialPriceProductList(SacRequestHeader header, ShoppingReq req) {
		ShoppingRes responseVO = null;
		/** TODO 2. 테스트용 if 헤더 셋팅 */
		List<Shopping> resultList = new ArrayList<Shopping>();

		req.setTenantId(header.getTenantHeader().getTenantId());
		req.setSystemId(header.getTenantHeader().getSystemId());
		req.setLangCd(header.getTenantHeader().getLangCd());
		req.setImageCd("DP0001A8");
		req.setDeviceModelCd("SHV-E330SSO");

		if (StringUtils.isEmpty(req.getProdCharge())) {
			req.setProdCharge(null);
		}
		if (StringUtils.isEmpty(req.getProdGradeCd())) {
			req.setProdGradeCd(null);
		}
		// offset, Count default setting
		this.commonOffsetCount(req);

		Integer totalCount = 0;
		resultList = this.commonDAO.queryForList("Shopping.getSecialPriceProductList", req, Shopping.class);

		if (resultList != null) {
			Shopping shopping = new Shopping();

			// Response VO를 만들기위한 생성자
			Product product = null;
			Identifier identifier = null;
			Identifier identifier1 = null;
			Menu menu = null;
			Rights rights = null;
			Title title = null;
			Source source = null;
			Price price = null;
			Contributor contributor = null;
			Accrual accrual = null;
			Date date = null;
			SalesOption saleoption = null;
			List<Identifier> identifierList = null;

			List<Menu> menuList = null;
			List<Source> sourceList = null;
			List<Product> productList = new ArrayList<Product>();

			for (int i = 0; i < resultList.size(); i++) {
				shopping = resultList.get(i);

				// 상품 정보 (상품ID)
				product = new Product();
				identifierList = new ArrayList<Identifier>();
				identifier = new Identifier();
				identifier.setType(DisplayConstants.DP_CATALOG_IDENTIFIER_CD);
				identifier.setText(shopping.getCatalogId());
				identifierList.add(identifier);

				// 메뉴 정보
				menuList = new ArrayList<Menu>();
				menu = new Menu();
				menu.setType(DisplayConstants.DP_MENU_TOPCLASS_TYPE);
				menu.setId(shopping.getUpMenuId());
				menu.setName(shopping.getUpMenuName());
				menuList.add(menu);

				menu = new Menu();
				menu.setType(shopping.getSpecialSale());
				menu.setId(shopping.getMenuId());
				menu.setName(shopping.getMenuName());
				menuList.add(menu);

				// 상품 정보 (상품명)
				title = new Title();
				title.setText(shopping.getCatalogName());
				// title.setPrefix(shopping.getNewYn());

				// 상품 정보 (상품가격)
				price = new Price();

				price.setFixedPrice(shopping.getProdNetAmt());
				price.setDiscountRate(shopping.getDcRate());
				price.setText(shopping.getProdAmt());

				// 이미지 정보
				sourceList = new ArrayList<Source>();
				source = new Source();
				source.setType(DisplayConstants.DP_SOURCE_TYPE_THUMBNAIL);
				source.setUrl(shopping.getFilePos());
				sourceList.add(source);

				// 다운로드 수
				accrual = new Accrual();
				accrual.setDownloadCount(shopping.getPrchsQty());

				// 이용권한 정보
				rights = new Rights();
				date = new Date();
				date.setType(DisplayConstants.DP_SHOPPING_RIGHTS_TYPE_NM);
				date.setText(shopping.getApplyStartDt() + "/" + shopping.getApplyEndDt());
				rights.setGrade(shopping.getProdGrdCd());
				rights.setDate(date);

				// contributor
				contributor = new Contributor();
				identifier1 = new Identifier();
				identifier1.setType(DisplayConstants.DP_BRAND_IDENTIFIER_CD);
				identifier1.setText(shopping.getBrandId());
				contributor.setName(shopping.getBrandName());
				contributor.setIdentifier(identifier1);

				// saleoption
				saleoption = new SalesOption();
				saleoption.setType(shopping.getProdCaseCd());
				if (shopping.getSoldOut().equals("Y")) {
					saleoption.setSatus(DisplayConstants.DP_SOLDOUT);
				} else {
					saleoption.setSatus(DisplayConstants.DP_CONTINUE);
				}

				// 데이터 매핑
				product.setIdentifierList(identifierList);
				product.setMenuList(menuList);
				product.setTitle(title);
				product.setPrice(price);
				product.setSourceList(sourceList);
				product.setAccrual(accrual);
				product.setRights(rights);
				product.setContributor(contributor);
				product.setSalesOption(saleoption);
				totalCount = shopping.getTotalCount();
				productList.add(i, product);
			}

			responseVO = new ShoppingRes();
			responseVO.setProductList(productList);

			CommonResponse commonResponse = new CommonResponse();
			commonResponse.setTotalCount(totalCount);
			responseVO.setCommonResponse(commonResponse);
		}
		return responseVO;
	}

	/**
	 * <pre>
	 * 기획전  상품  조회.
	 * </pre>
	 */
	@Override
	public ShoppingThemeRes getSpecialSalesList(SacRequestHeader header, ShoppingReq req) {
		// 공통 응답 변수 선언
		ShoppingThemeRes responseVO = null;
		List<Shopping> resultList = new ArrayList<Shopping>();

		req.setTenantId(header.getTenantHeader().getTenantId());
		req.setSystemId(header.getTenantHeader().getSystemId());
		req.setLangCd(header.getTenantHeader().getLangCd());
		req.setImageCd("DP0001A8");
		req.setDeviceModelCd("SHV-E330SSO");

		// 필수 파라미터 체크
		if (StringUtils.isEmpty(req.getTenantId())) {
			throw new StorePlatformException("SAC_DSP_0002", "tenantId", req.getTenantId());
		}
		if (StringUtils.isEmpty(req.getOrderedBy())) {
			req.setOrderedBy(DisplayConstants.DP_SHOPPING_RECENT_DEFAULT_ORDERED_OPTION);
		}
		// offset, Count default setting
		this.commonOffsetCount(req);

		resultList = this.commonDAO.queryForList("Shopping.getSpecialSalesList", req, Shopping.class);

		if (resultList != null) {
			Shopping shopping = new Shopping();

			// Response VO를 만들기위한 생성자
			Promotion promotion = null;
			Identifier identifier = null;
			Title title = null;
			Source source = null;
			List<Promotion> promotionList = new ArrayList<Promotion>();

			for (int i = 0; i < resultList.size(); i++) {
				shopping = resultList.get(i);

				// 상품 정보 (상품ID)
				promotion = new Promotion();
				identifier = new Identifier();
				identifier.setText(shopping.getPlanId());

				// 상품 정보 (상품명)
				title = new Title();
				title.setText(shopping.getPlanName());

				// 이미지 정보
				source = new Source();
				source.setType(DisplayConstants.DP_SOURCE_TYPE_THUMBNAIL);
				source.setUrl(shopping.getFilePos());

				// 데이터 매핑
				promotion.setIdentifier(identifier);
				promotion.setTitle(title);
				promotion.setPromotionExplain(shopping.getSubTitleName());
				promotion.setUsagePeriod(shopping.getPlanStartDt() + "/" + shopping.getPlanEndDt());
				promotion.setSource(source);
				promotionList.add(i, promotion);
			}

			responseVO = new ShoppingThemeRes();
			responseVO.setPromotionList(promotionList);
		}
		return responseVO;
	}

	/**
	 * <pre>
	 * 특정 기획전에 대한 상품 리스트 조회.
	 * </pre>
	 */
	@Override
	public ShoppingThemeRes getSpecialSalesProductList(SacRequestHeader header, ShoppingReq req) {
		// 공통 응답 변수 선언
		ShoppingThemeRes res = new ShoppingThemeRes();
		CommonResponse commonResponse = new CommonResponse();
		req.setLangCd(header.getTenantHeader().getLangCd());
		req.setImageCd("DP000164");
		req.setDeviceModelCd("SHV-E330SSO");
		List<Shopping> resultList = new ArrayList<Shopping>();

		// 필수 파라미터 체크
		if (StringUtils.isEmpty(header.getTenantHeader().getTenantId())) {
			throw new StorePlatformException("SAC_DSP_0002", "tenantId", req.getTenantId());
		}
		if (StringUtils.isEmpty(req.getPlanId())) {
			throw new StorePlatformException("SAC_DSP_0002", "planId", req.getPlanId());
		}

		if (StringUtils.isEmpty(req.getProdCharge())) {
			req.setProdCharge(null);
		}
		if (StringUtils.isEmpty(req.getProdGradeCd())) {
			req.setProdGradeCd(null);
		}
		// offset, Count default setting
		this.commonOffsetCount(req);

		TenantHeader tenantHeader = header.getTenantHeader();
		DeviceHeader deviceHeader = header.getDeviceHeader();

		// 프로모션 리스트 가져오기
		resultList = this.commonDAO.queryForList("Shopping.getSpecialSalesList", req, Shopping.class);

		if (resultList != null) {
			Shopping shopping = new Shopping();

			// Response VO를 만들기위한 생성자
			Promotion promotion = null;
			Identifier identifier = null;
			Title title = null;
			Source source = null;
			List<Promotion> promotionList = new ArrayList<Promotion>();

			for (int i = 0; i < resultList.size(); i++) {
				shopping = resultList.get(i);

				// 상품 정보 (상품ID)
				promotion = new Promotion();
				identifier = new Identifier();
				identifier.setText(shopping.getPlanId());

				// 상품 정보 (상품명)
				title = new Title();
				title.setText(shopping.getPlanName());

				// 이미지 정보
				source = new Source();
				source.setType(DisplayConstants.DP_SOURCE_TYPE_THUMBNAIL);
				source.setUrl(shopping.getFilePos());

				// 데이터 매핑
				promotion.setIdentifier(identifier);
				promotion.setTitle(title);
				promotion.setPromotionExplain(shopping.getSubTitleName());
				promotion.setUsagePeriod(shopping.getPlanStartDt() + "/" + shopping.getPlanEndDt());
				promotion.setSource(source);
				promotionList.add(i, promotion);
			}
			res.setPromotionList(promotionList);
		}

		// DB 조회 파라미터 생성
		Map<String, Object> reqMap = new HashMap<String, Object>();
		reqMap.put("req", req);
		reqMap.put("tenantHeader", tenantHeader);
		reqMap.put("deviceHeader", deviceHeader);
		reqMap.put("lang", tenantHeader.getLangCd());

		// TODO osm1021 Dummy data 꼭 삭제할것!!!!!!!!!!
		reqMap.put("imageCd", req.getImageCd());
		reqMap.put("svcGrpCd", DisplayConstants.DP_TSTORE_SHOPPING_PROD_SVC_GRP_CD);
		reqMap.put("contentTypeCd", DisplayConstants.DP_CHANNEL_CONTENT_TYPE_CD);
		reqMap.put("prodStatusCd", DisplayConstants.DP_SALE_STAT_ING);
		reqMap.put("prodRshpCd", DisplayConstants.DP_CHANNEL_EPISHODE_RELATIONSHIP_CD);

		// ID list 조회
		List<ProductBasicInfo> productBasicInfoList = this.commonDAO.queryForList(
				"Shopping.getSpecialSalesProductList", reqMap, ProductBasicInfo.class);
		List<Product> productList = new ArrayList<Product>();
		if (productBasicInfoList != null) {
			if (productBasicInfoList.size() > 0) {
				for (ProductBasicInfo productBasicInfo : productBasicInfoList) {
					reqMap.put("productBasicInfo", productBasicInfo);
					// 쇼핑 Meta 정보 조회
					MetaInfo retMetaInfo = this.metaInfoService.getShoppingMetaInfo(reqMap);
					if (retMetaInfo != null) {
						// 쇼핑 Response Generate
						Product product = this.responseInfoGenerateFacade.generateShoppingProduct(retMetaInfo);
						productList.add(product);
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
		}
		return res;
	}

	/**
	 * <pre>
	 * 브랜드샵 - 메인 리스트 조회.
	 * </pre>
	 */
	@Override
	public ShoppingRes getBrandshopMainList(SacRequestHeader header, ShoppingReq req) {
		// 공통 응답 변수 선언
		ShoppingRes responseVO = null;
		List<Shopping> resultList = new ArrayList<Shopping>();
		List<Shopping> hotBrandList = new ArrayList<Shopping>();
		List<Shopping> detailList = new ArrayList<Shopping>();
		List<Shopping> menuBrandList = new ArrayList<Shopping>();

		req.setTenantId(header.getTenantHeader().getTenantId());
		req.setSystemId(header.getTenantHeader().getSystemId());
		req.setLangCd(header.getTenantHeader().getLangCd());
		req.setImageCd("DP0001A4");
		req.setDeviceModelCd("SHV-E330SSO");

		// 필수 파라미터 체크
		if (StringUtils.isEmpty(header.getTenantHeader().getTenantId())) {
			throw new StorePlatformException("SAC_DSP_0002", "tenantId", req.getTenantId());
		}
		// offset, Count default setting
		this.commonOffsetCount(req);

		if (!StringUtils.isEmpty(req.getMenuId())) {
			resultList = this.commonDAO.queryForList("Shopping.getBrandshopMainList", req, Shopping.class);
		} else {
			req.setMenuId(null);
			hotBrandList = this.commonDAO.queryForList("Shopping.getBrandshopMainList", req, Shopping.class);
			for (Shopping hotBrandShopping : hotBrandList) {
				resultList.add(hotBrandShopping);
			}
			menuBrandList = this.commonDAO.queryForList("Shopping.getShoppingBrandMenuList", req, Shopping.class);
			for (int i = 0; i < menuBrandList.size(); i++) {
				req.setMenuId(menuBrandList.get(i).getMenuId());
				detailList = this.commonDAO.queryForList("Shopping.getBrandshopMainList", req, Shopping.class);
				for (Shopping detailShopping : detailList) {
					resultList.add(detailShopping);
				}
			}
		}

		if (resultList != null) {
			Shopping shopping = new Shopping();

			// Response VO를 만들기위한 생성자
			Product product = null;
			Identifier identifier = null;
			Menu menu = null;
			Title title = null;
			Source source = null;
			List<Identifier> identifierList = null;

			List<Menu> menuList = null;
			List<Source> sourceList = null;
			List<Product> productList = new ArrayList<Product>();

			for (int i = 0; i < resultList.size(); i++) {
				shopping = resultList.get(i);

				// 상품 정보 (상품ID)
				product = new Product();
				identifierList = new ArrayList<Identifier>();
				identifier = new Identifier();
				identifier.setType(DisplayConstants.DP_BRAND_IDENTIFIER_CD);
				identifier.setText(shopping.getBrandId());
				identifierList.add(identifier);

				// 메뉴 정보
				menuList = new ArrayList<Menu>();
				menu = new Menu();
				menu.setType(DisplayConstants.DP_MENU_TOPCLASS_TYPE);
				menu.setId(shopping.getUpMenuId());
				menu.setName(shopping.getUpMenuName());
				menuList.add(menu);

				menu = new Menu();
				menu.setId(shopping.getMenuId());
				menu.setName(shopping.getMenuName());
				menuList.add(menu);

				// 상품 정보 (상품명)
				title = new Title();
				title.setText(shopping.getBrandName());

				// 이미지 정보
				sourceList = new ArrayList<Source>();
				source = new Source();
				source.setType(DisplayConstants.DP_SOURCE_TYPE_THUMBNAIL);
				source.setUrl(shopping.getFilePos());
				sourceList.add(source);

				// 데이터 매핑
				product.setIdentifierList(identifierList);
				product.setMenuList(menuList);
				product.setTitle(title);
				product.setSourceList(sourceList);
				productList.add(i, product);
			}

			responseVO = new ShoppingRes();
			responseVO.setProductList(productList);
		}
		return responseVO;
	}

	/**
	 * <pre>
	 * 특정 브랜드샵 상품 리스트
	 * </pre>
	 */
	@Override
	public ShoppingRes getBrandshopProductList(SacRequestHeader header, ShoppingReq req) {
		// 공통 응답 변수 선언
		ShoppingRes res = new ShoppingRes();
		CommonResponse commonResponse = new CommonResponse();
		req.setLangCd(header.getTenantHeader().getLangCd());
		req.setImageCd("DP000164");
		req.setDeviceModelCd("SHV-E330SSO");
		List<Shopping> resultList = new ArrayList<Shopping>();

		// 필수 파라미터 체크
		if (StringUtils.isEmpty(header.getTenantHeader().getTenantId())) {
			throw new StorePlatformException("SAC_DSP_0002", "tenantId", req.getTenantId());
		}
		if (StringUtils.isEmpty(req.getBrandId())) {
			throw new StorePlatformException("SAC_DSP_0002", "blandId", req.getBrandId());
		}
		if (StringUtils.isEmpty(req.getOrderedBy())) {
			req.setOrderedBy(DisplayConstants.DP_SHOPPING_RECENT_DEFAULT_ORDERED_OPTION);
		}

		// offset, Count default setting
		this.commonOffsetCount(req);

		TenantHeader tenantHeader = header.getTenantHeader();
		DeviceHeader deviceHeader = header.getDeviceHeader();

		// 브랜드샵 정보 가져오기
		resultList = this.commonDAO.queryForList("Shopping.getBrandshopMainList", req, Shopping.class);

		if (resultList != null) {
			Shopping shopping = new Shopping();

			// Response VO를 만들기위한 생성자
			Layout layOut = null;
			Title title = null;
			Menu menu = null;

			for (int i = 0; i < resultList.size(); i++) {
				shopping = resultList.get(i);
				// 상품 정보 (상품ID)
				layOut = new Layout();
				// 상품 정보 (상품명)
				title = new Title();
				title.setText(shopping.getBrandName());
				// 메뉴정보
				menu = new Menu();
				menu.setId(shopping.getMenuId());
				menu.setName(shopping.getMenuName());
				// 데이터 매핑
				layOut.setTitle(title);
				layOut.setMenu(menu);
			}

			res.setLayOut(layOut);
		}

		// DB 조회 파라미터 생성
		Map<String, Object> reqMap = new HashMap<String, Object>();
		reqMap.put("req", req);
		reqMap.put("tenantHeader", tenantHeader);
		reqMap.put("deviceHeader", deviceHeader);
		reqMap.put("lang", tenantHeader.getLangCd());

		// TODO osm1021 Dummy data 꼭 삭제할것!!!!!!!!!!
		reqMap.put("imageCd", req.getImageCd());
		reqMap.put("svcGrpCd", DisplayConstants.DP_TSTORE_SHOPPING_PROD_SVC_GRP_CD);
		reqMap.put("contentTypeCd", DisplayConstants.DP_CHANNEL_CONTENT_TYPE_CD);
		reqMap.put("prodStatusCd", DisplayConstants.DP_SALE_STAT_ING);
		reqMap.put("prodRshpCd", DisplayConstants.DP_CHANNEL_EPISHODE_RELATIONSHIP_CD);

		List<ProductBasicInfo> productBasicInfoList = this.commonDAO.queryForList("Shopping.getBrandshopProductList",
				reqMap, ProductBasicInfo.class);
		List<Product> productList = new ArrayList<Product>();
		if (productBasicInfoList != null) {
			if (productBasicInfoList.size() > 0) {
				for (ProductBasicInfo productBasicInfo : productBasicInfoList) {
					reqMap.put("productBasicInfo", productBasicInfo);
					// 쇼핑 Meta 정보 조회
					MetaInfo retMetaInfo = this.metaInfoService.getShoppingMetaInfo(reqMap);
					if (retMetaInfo != null) {
						// 쇼핑 Response Generate
						Product product = this.responseInfoGenerateFacade.generateShoppingProduct(retMetaInfo);
						productList.add(product);
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
		}
		return res;
	}

	/**
	 * <pre>
	 * 쇼핑테마 리스트상품 리스트
	 * </pre>
	 */
	@Override
	public ShoppingRes getThemeList(SacRequestHeader header, ShoppingReq req) {
		// 공통 응답 변수 선언
		ShoppingRes responseVO = null;
		List<Shopping> resultList = new ArrayList<Shopping>();

		req.setTenantId(header.getTenantHeader().getTenantId());
		req.setSystemId(header.getTenantHeader().getSystemId());
		req.setLangCd(header.getTenantHeader().getLangCd());
		req.setImageCd("DP0001A7");
		req.setDeviceModelCd("SHV-E330SSO");

		// 필수 파라미터 체크
		if (StringUtils.isEmpty(header.getTenantHeader().getTenantId())) {
			throw new StorePlatformException("SAC_DSP_0002", "tenantId", req.getTenantId());
		}

		// offset, Count default setting
		this.commonOffsetCount(req);

		resultList = this.commonDAO.queryForList("Shopping.getThemeList", req, Shopping.class);

		if (resultList != null) {
			Shopping shopping = new Shopping();

			// Response VO를 만들기위한 생성자
			Product product = null;
			Identifier identifier = null;
			Title title = null;
			Source source = null;
			List<Identifier> identifierList = null;
			List<Source> sourceList = null;
			List<Product> productList = new ArrayList<Product>();

			for (int i = 0; i < resultList.size(); i++) {
				shopping = resultList.get(i);

				// 상품 정보 (상품ID)
				product = new Product();
				identifierList = new ArrayList<Identifier>();
				identifier = new Identifier();
				identifier.setType(DisplayConstants.DP_THEME_IDENTIFIER_CD);
				identifier.setText(shopping.getThemeId());
				identifierList.add(identifier);

				// 상품 정보 (상품명)
				title = new Title();
				title.setText(shopping.getThemeName());

				// 이미지 정보
				sourceList = new ArrayList<Source>();
				source = new Source();
				source.setType(DisplayConstants.DP_SOURCE_TYPE_THUMBNAIL);
				source.setUrl(shopping.getFilePos());
				sourceList.add(source);

				// 데이터 매핑
				product.setIdentifierList(identifierList);
				product.setTitle(title);
				product.setSourceList(sourceList);
				productList.add(i, product);
			}

			responseVO = new ShoppingRes();
			responseVO.setProductList(productList);
		}
		return responseVO;
	}

	/**
	 * <pre>
	 * 특정 테마 리스트상품 리스트
	 * </pre>
	 */
	@Override
	public ShoppingRes getThemeProductList(SacRequestHeader header, ShoppingReq req) {
		// 공통 응답 변수 선언
		ShoppingRes res = new ShoppingRes();
		CommonResponse commonResponse = new CommonResponse();
		req.setTenantId(header.getTenantHeader().getTenantId());
		req.setSystemId(header.getTenantHeader().getSystemId());
		req.setLangCd(header.getTenantHeader().getLangCd());
		req.setImageCd("DP000164");
		req.setDeviceModelCd("SHV-E330SSO");
		List<Shopping> resultList = new ArrayList<Shopping>();

		// 필수 파라미터 체크
		if (StringUtils.isEmpty(header.getTenantHeader().getTenantId())) {
			throw new StorePlatformException("SAC_DSP_0002", "tenantId", req.getTenantId());
		}
		if (StringUtils.isEmpty(req.getThemeId())) {
			throw new StorePlatformException("SAC_DSP_0002", "themeId", req.getThemeId());
		}

		if (StringUtils.isEmpty(req.getProdCharge())) {
			req.setProdCharge(null);
		}
		if (StringUtils.isEmpty(req.getProdGradeCd())) {
			req.setProdGradeCd(null);
		}
		// offset, Count default setting
		this.commonOffsetCount(req);

		TenantHeader tenantHeader = header.getTenantHeader();
		DeviceHeader deviceHeader = header.getDeviceHeader();

		// 테마 정보 가져오기
		resultList = this.commonDAO.queryForList("Shopping.getThemeList", req, Shopping.class);

		if (resultList != null) {
			Shopping shopping = new Shopping();

			// Response VO를 만들기위한 생성자
			Layout layOut = null;
			Title title = null;

			for (int i = 0; i < resultList.size(); i++) {
				shopping = resultList.get(i);
				// 상품 정보 (상품ID)
				layOut = new Layout();
				// 상품 정보 (상품명)
				title = new Title();
				title.setText(shopping.getThemeName());
				// 데이터 매핑
				layOut.setTitle(title);
			}

			res.setLayOut(layOut);
		}

		// DB 조회 파라미터 생성
		Map<String, Object> reqMap = new HashMap<String, Object>();
		reqMap.put("req", req);
		reqMap.put("tenantHeader", tenantHeader);
		reqMap.put("deviceHeader", deviceHeader);
		reqMap.put("lang", tenantHeader.getLangCd());

		// TODO osm1021 Dummy data 꼭 삭제할것!!!!!!!!!!
		reqMap.put("imageCd", req.getImageCd());
		reqMap.put("svcGrpCd", DisplayConstants.DP_TSTORE_SHOPPING_PROD_SVC_GRP_CD);
		reqMap.put("contentTypeCd", DisplayConstants.DP_CHANNEL_CONTENT_TYPE_CD);
		reqMap.put("prodStatusCd", DisplayConstants.DP_SALE_STAT_ING);
		reqMap.put("prodRshpCd", DisplayConstants.DP_CHANNEL_EPISHODE_RELATIONSHIP_CD);

		List<ProductBasicInfo> productBasicInfoList = this.commonDAO.queryForList("Shopping.getThemeProductList",
				reqMap, ProductBasicInfo.class);
		List<Product> productList = new ArrayList<Product>();
		if (productBasicInfoList != null) {
			if (productBasicInfoList.size() > 0) {
				for (ProductBasicInfo productBasicInfo : productBasicInfoList) {
					reqMap.put("productBasicInfo", productBasicInfo);
					// 쇼핑 Meta 정보 조회
					MetaInfo retMetaInfo = this.metaInfoService.getShoppingMetaInfo(reqMap);
					if (retMetaInfo != null) {
						// 쇼핑 Response Generate
						Product product = this.responseInfoGenerateFacade.generateShoppingProduct(retMetaInfo);
						productList.add(product);
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
		}
		return res;
	}

	/**
	 * <pre>
	 *   특정 카탈로그에 대한 다른 상품 리스트
	 * </pre>
	 */
	@Override
	public ShoppingRes getCatagoryAnotherProductList(SacRequestHeader header, ShoppingReq req) {

		// 공통 응답 변수 선언
		ShoppingRes res = new ShoppingRes();
		CommonResponse commonResponse = new CommonResponse();
		req.setLangCd(header.getTenantHeader().getLangCd());
		req.setImageCd("DP000164");
		req.setDeviceModelCd("SHV-E330SSO");

		// 필수 파라미터 체크
		if (StringUtils.isEmpty(header.getTenantHeader().getTenantId())) {
			throw new StorePlatformException("SAC_DSP_0002", "tenantId", req.getTenantId());
		}
		if (StringUtils.isEmpty(req.getExceptProdId())) {
			throw new StorePlatformException("SAC_DSP_0002", "exceptProdId", req.getExceptProdId());
		}
		if (StringUtils.isEmpty(req.getMenuId())) {
			throw new StorePlatformException("SAC_DSP_0002", "menuId", req.getMenuId());
		}

		if (StringUtils.isEmpty(req.getProdCharge())) {
			req.setProdCharge(null);
		}
		if (StringUtils.isEmpty(req.getProdGradeCd())) {
			req.setProdGradeCd(null);
		}
		if (StringUtils.isEmpty(req.getOrderedBy())) {
			req.setOrderedBy(DisplayConstants.DP_SHOPPING_DOWNLOAD_DEFAULT_ORDERED_OPTION);
		}
		// offset, Count default setting
		this.commonOffsetCount(req);

		String stdDt = "";
		if (req.getOrderedBy().equals("download")) {
			// 필수 파라미터 체크
			if (StringUtils.isEmpty(req.getListId())) {
				throw new StorePlatformException("SAC_DSP_0002", "listId", req.getListId());
			}

			stdDt = this.displayCommonService.getBatchStandardDateString(header.getTenantHeader().getTenantId(),
					req.getListId());

			// 기준일시 체크
			if (StringUtils.isEmpty(stdDt)) {
				throw new StorePlatformException("SAC_DSP_0003", "stdDt", stdDt);
			}
			req.setStdDt(stdDt);
		}
		TenantHeader tenantHeader = header.getTenantHeader();
		DeviceHeader deviceHeader = header.getDeviceHeader();

		// DB 조회 파라미터 생성
		Map<String, Object> reqMap = new HashMap<String, Object>();
		reqMap.put("req", req);
		reqMap.put("tenantHeader", tenantHeader);
		reqMap.put("deviceHeader", deviceHeader);
		reqMap.put("stdDt", stdDt);
		reqMap.put("lang", tenantHeader.getLangCd());

		// TODO osm1021 Dummy data 꼭 삭제할것!!!!!!!!!!
		reqMap.put("imageCd", req.getImageCd());
		reqMap.put("svcGrpCd", DisplayConstants.DP_TSTORE_SHOPPING_PROD_SVC_GRP_CD);
		reqMap.put("contentTypeCd", DisplayConstants.DP_CHANNEL_CONTENT_TYPE_CD);
		reqMap.put("prodStatusCd", DisplayConstants.DP_SALE_STAT_ING);
		reqMap.put("prodRshpCd", DisplayConstants.DP_CHANNEL_EPISHODE_RELATIONSHIP_CD);

		// ID list 조회
		List<ProductBasicInfo> productBasicInfoList = this.commonDAO.queryForList(
				"Shopping.getCatagoryAnotherProductList", reqMap, ProductBasicInfo.class);
		List<Product> productList = new ArrayList<Product>();
		if (productBasicInfoList != null) {
			if (productBasicInfoList.size() > 0) {
				for (ProductBasicInfo productBasicInfo : productBasicInfoList) {
					reqMap.put("productBasicInfo", productBasicInfo);
					// 쇼핑 Meta 정보 조회
					MetaInfo retMetaInfo = this.metaInfoService.getShoppingMetaInfo(reqMap);
					if (retMetaInfo != null) {
						// 쇼핑 Response Generate
						Product product = this.responseInfoGenerateFacade.generateShoppingProduct(retMetaInfo);
						productList.add(product);
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
		}
		return res;

	}

	/**
	 * <pre>
	 * 특정 브랜드에 대한 다른 상품 리스트
	 * </pre>
	 */
	@Override
	public ShoppingRes getBrandAnotherProductList(SacRequestHeader header, ShoppingReq req) {
		// 공통 응답 변수 선언
		ShoppingRes res = new ShoppingRes();
		CommonResponse commonResponse = new CommonResponse();
		req.setLangCd(header.getTenantHeader().getLangCd());
		req.setImageCd("DP000164");
		req.setDeviceModelCd("SHV-E330SSO");

		// 필수 파라미터 체크
		if (StringUtils.isEmpty(header.getTenantHeader().getTenantId())) {
			throw new StorePlatformException("SAC_DSP_0002", "tenantId", req.getTenantId());
		}
		if (StringUtils.isEmpty(req.getExceptProdId())) {
			throw new StorePlatformException("SAC_DSP_0002", "exceptProdId", req.getExceptProdId());
		}
		if (StringUtils.isEmpty(req.getBrandId())) {
			throw new StorePlatformException("SAC_DSP_0002", "brandId", req.getBrandId());
		}

		if (StringUtils.isEmpty(req.getProdCharge())) {
			req.setProdCharge(null);
		}
		if (StringUtils.isEmpty(req.getProdGradeCd())) {
			req.setProdGradeCd(null);
		}
		if (StringUtils.isEmpty(req.getOrderedBy())) {
			req.setOrderedBy(DisplayConstants.DP_SHOPPING_DOWNLOAD_DEFAULT_ORDERED_OPTION);
		}
		// offset, Count default setting
		this.commonOffsetCount(req);

		String stdDt = "";
		if (req.getOrderedBy().equals("download")) {
			// 필수 파라미터 체크
			if (StringUtils.isEmpty(req.getListId())) {
				throw new StorePlatformException("SAC_DSP_0002", "listId", req.getListId());
			}

			stdDt = this.displayCommonService.getBatchStandardDateString(header.getTenantHeader().getTenantId(),
					req.getListId());

			// 기준일시 체크
			if (StringUtils.isEmpty(stdDt)) {
				throw new StorePlatformException("SAC_DSP_0003", "stdDt", stdDt);
			}
			req.setStdDt(stdDt);
		}
		TenantHeader tenantHeader = header.getTenantHeader();
		DeviceHeader deviceHeader = header.getDeviceHeader();

		// DB 조회 파라미터 생성
		Map<String, Object> reqMap = new HashMap<String, Object>();
		reqMap.put("req", req);
		reqMap.put("tenantHeader", tenantHeader);
		reqMap.put("deviceHeader", deviceHeader);
		reqMap.put("stdDt", stdDt);
		reqMap.put("lang", tenantHeader.getLangCd());

		// TODO osm1021 Dummy data 꼭 삭제할것!!!!!!!!!!
		reqMap.put("imageCd", req.getImageCd());
		reqMap.put("svcGrpCd", DisplayConstants.DP_TSTORE_SHOPPING_PROD_SVC_GRP_CD);
		reqMap.put("contentTypeCd", DisplayConstants.DP_CHANNEL_CONTENT_TYPE_CD);
		reqMap.put("prodStatusCd", DisplayConstants.DP_SALE_STAT_ING);
		reqMap.put("prodRshpCd", DisplayConstants.DP_CHANNEL_EPISHODE_RELATIONSHIP_CD);

		// ID list 조회
		List<ProductBasicInfo> productBasicInfoList = this.commonDAO.queryForList(
				"Shopping.getBrandAnotherProductList", reqMap, ProductBasicInfo.class);
		List<Product> productList = new ArrayList<Product>();
		if (productBasicInfoList != null) {
			if (productBasicInfoList.size() > 0) {
				for (ProductBasicInfo productBasicInfo : productBasicInfoList) {
					reqMap.put("productBasicInfo", productBasicInfo);
					// 쇼핑 Meta 정보 조회
					MetaInfo retMetaInfo = this.metaInfoService.getShoppingMetaInfo(reqMap);
					if (retMetaInfo != null) {
						// 쇼핑 Response Generate
						Product product = this.responseInfoGenerateFacade.generateShoppingProduct(retMetaInfo);
						productList.add(product);
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
		}
		return res;
	}

	/**
	 * <pre>
	 * 쇼핑상세
	 * </pre>
	 */
	@Override
	public ShoppingRes getShoppingDetail(SacRequestHeader header, ShoppingReq req) {
		int totalCount = 0;
		ShoppingRes responseVO = null;
		ShoppingReq requestVO = new ShoppingReq();
		requestVO.setOffset(1);
		requestVO.setCount(20);
		if (null == req.getTenantId() || "".equals(req.getTenantId())) {
			// throw new Exception("tenantId 는 필수 파라메터 입니다.");
		}
		if (null == req.getSystemId() || "".equals(req.getSystemId())) {
			// throw new Exception("systemId 는 필수 파라메터 입니다.");
		}

		// List<shopping> resultList = this.commonDAO.queryForList("Shopping.selectShoppingList", requestVO,
		// shopping.class);
		// List<shopping> resultList = null;

		// if (resultList != null) {
		// shopping shopping = new shopping();

		// Response VO를 만들기위한 생성자
		Identifier identifier = new Identifier();
		Identifier identifier1 = new Identifier();
		Menu menu = new Menu();
		Rights rights = new Rights();
		Title title = new Title();
		Source source = new Source();
		Price price = new Price();
		Product product = new Product();
		Contributor contributor = new Contributor();
		Accrual acc = new Accrual();
		Date date = new Date();
		SalesOption saleoption = new SalesOption();

		List<Menu> menuList = new ArrayList<Menu>();
		List<Source> sourceList = new ArrayList<Source>();
		List<Product> productList = new ArrayList<Product>();

		for (int i = 0; i < 1; i++) {
			// shopping = resultList.get(i);
			// 상품 정보 (상품ID)
			identifier.setType("product");
			identifier.setText("S900000960");

			// 메뉴 정보
			menu.setType("menuId");
			menu.setId("MN28009");
			menu.setName("편의점/마트");
			menuList.add(menu);

			// 상품 정보 (상품명)
			title.setText("핫식스 캔 250ml");
			// 상품 정보 (상품가격)

			price.setFixedPrice(1000);
			price.setDiscountRate(0d);
			price.setDiscountPrice(0);
			price.setText(1000);

			// 이미지 정보
			source.setType("thumbnail");
			source.setUrl("inst_thumbnail_20111216154840.jpg");
			sourceList.add(source);
			// contributor

			acc.setDownloadCount(6229);

			// 이용권한 정보
			date.setText("20130820190000/20131231235959");
			rights.setGrade("PD004401");
			rights.setDate(date);

			// contributor
			identifier1.setType("brand");
			identifier1.setText("세븐일레븐 바이더웨이");
			contributor.setIdentifier(identifier1);

			// saleoption
			saleoption.setType("delivery");

			// 데이터 매핑
			product.setIdentifier(identifier);
			product.setMenuList(menuList);
			product.setTitle(title);
			product.setPrice(price);
			// product.setRights(rights);
			product.setSourceList(sourceList);
			product.setAccrual(acc);
			product.setRights(rights);
			product.setContributor(contributor);
			product.setSalesOption(saleoption);

			productList.add(i, product);
			identifier = new Identifier();
			menu = new Menu();
			menuList = new ArrayList<Menu>();
			rights = new Rights();
			title = new Title();
			source = new Source();
			sourceList = new ArrayList<Source>();
			price = new Price();
			product = new Product();
		}

		responseVO = new ShoppingRes();
		responseVO.setProductList(productList);

		CommonResponse commonResponse = new CommonResponse();
		commonResponse.setTotalCount(20);
		responseVO.setCommonResponse(commonResponse);
		// }
		return responseVO;
	}

	private void commonOffsetCount(ShoppingReq req) {

		if (req.getOffset() == null) {
			req.setOffset(1);
		}
		if (req.getCount() == null) {
			req.setCount(20);
		}
		req.setOffset(req.getOffset() <= 0 ? 1 : req.getOffset());
		req.setCount(req.getCount() <= 0 ? 20 : req.getCount());
	}
}
