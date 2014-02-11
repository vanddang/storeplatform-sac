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

import com.skplanet.storeplatform.framework.core.exception.StorePlatformException;
import com.skplanet.storeplatform.framework.core.persistence.dao.CommonDAO;
import com.skplanet.storeplatform.purchase.constant.PurchaseConstants;
import com.skplanet.storeplatform.sac.client.display.vo.shopping.ShoppingReq;
import com.skplanet.storeplatform.sac.client.display.vo.shopping.ShoppingRes;
import com.skplanet.storeplatform.sac.client.display.vo.shopping.ShoppingThemeRes;
import com.skplanet.storeplatform.sac.client.member.vo.seller.DetailInformationReq;
import com.skplanet.storeplatform.sac.client.member.vo.seller.DetailInformationRes;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.common.CommonResponse;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.common.Date;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.common.Identifier;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.common.Menu;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.common.Price;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.common.Source;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.common.Title;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.product.Accrual;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.product.Contributor;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.product.Distributor;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.product.Layout;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.product.Product;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.product.Promotion;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.product.Purchase;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.product.Rights;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.product.SalesOption;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.product.SelectOption;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.product.SubSelectOption;
import com.skplanet.storeplatform.sac.client.purchase.history.vo.HistoryListSacReq;
import com.skplanet.storeplatform.sac.client.purchase.history.vo.HistoryListSacRes;
import com.skplanet.storeplatform.sac.client.purchase.history.vo.ProductListSac;
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
import com.skplanet.storeplatform.sac.member.seller.service.SellerSearchService;
import com.skplanet.storeplatform.sac.purchase.history.service.HistoryListService;

/**
 * ShoppingList Service 인터페이스(CoreStoreBusiness) 구현체
 * 
 * Updated on : 2013. 12. 23. Updated by : 김형식, SK 플래닛.
 */
@Service
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

	@Autowired
	HistoryListService historyListService;

	@Autowired
	private SellerSearchService sellerSearchService;

	/**
	 * <pre>
	 * 쇼핑 추천/인기 상품 리스트 조회.
	 * </pre>
	 */
	@Override
	public ShoppingRes getFeatureProductList(SacRequestHeader header, ShoppingReq req) {

		// 공통 응답 변수 선언
		if (req.getDummy() == null) {
			ShoppingRes res = new ShoppingRes();
			CommonResponse commonResponse = new CommonResponse();
			TenantHeader tenantHeader = header.getTenantHeader();
			DeviceHeader deviceHeader = header.getDeviceHeader();
			String[] temp = deviceHeader.getOsVersion().trim().split("/");
			String osVersion = temp[1];
			req.setTenantId(tenantHeader.getTenantId());
			req.setDeviceModelCd(deviceHeader.getModel());
			req.setLangCd(tenantHeader.getLangCd());
			req.setOsVersion(osVersion);
			req.setImageCd(DisplayConstants.DP_SHOPPING_REPRESENT_IMAGE_CD);

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

			// 상품등급코드 유효값 체크
			if (!this.commonProdGradeCd(req)) {
				res.setCommonResponse(commonResponse);
				return res;
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

			// DB 조회 파라미터 생성
			Map<String, Object> reqMap = new HashMap<String, Object>();
			reqMap.put("req", req);
			reqMap.put("tenantHeader", tenantHeader);
			reqMap.put("deviceHeader", deviceHeader);
			reqMap.put("stdDt", stdDt);
			reqMap.put("lang", tenantHeader.getLangCd());

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
		} else {
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
				identifier.setType("catalog");
				identifier.setText("CT00010008");

				// 메뉴 정보
				menu.setType("menuId");
				menu.setId("DP28009");
				menu.setName("편의점/마트");
				menuList.add(menu);

				// 상품 정보 (상품명)
				title.setText("추천/인기 카탈로그 상품");
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
				date.setType("duration/salePeriod");
				date.setText("2013-03-05 00:00:00/2014-03-25 23:59:59");
				rights.setGrade("PD004401");
				rights.setDate(date);

				// contributor
				identifier1.setType("brand");
				identifier1.setText("BR00010008");
				contributor.setName("세븐일레븐 바이더웨이");
				contributor.setIdentifier(identifier1);

				// saleoption
				saleoption.setType("delivery");

				// 데이터 매핑
				product.setIdentifier(identifier);
				product.setMenuList(menuList);
				product.setTitle(title);
				product.setPrice(price);
				product.setRights(rights);
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
	}

	/**
	 * <pre>
	 * 쇼핑 신규 상품 조회 .
	 * </pre>
	 */
	@Override
	public ShoppingRes getNewProductList(SacRequestHeader header, ShoppingReq req) {
		if (req.getDummy() == null) {
			// 공통 응답 변수 선언
			ShoppingRes res = new ShoppingRes();
			CommonResponse commonResponse = new CommonResponse();
			TenantHeader tenantHeader = header.getTenantHeader();
			DeviceHeader deviceHeader = header.getDeviceHeader();
			String[] temp = deviceHeader.getOsVersion().trim().split("/");
			String osVersion = temp[1];
			req.setTenantId(tenantHeader.getTenantId());
			req.setDeviceModelCd(deviceHeader.getModel());
			req.setLangCd(tenantHeader.getLangCd());
			req.setOsVersion(osVersion);
			req.setImageCd(DisplayConstants.DP_SHOPPING_REPRESENT_IMAGE_CD);

			// 필수 파라미터 체크
			if (StringUtils.isEmpty(header.getTenantHeader().getTenantId())) {
				throw new StorePlatformException("SAC_DSP_0002", "tenantId", req.getTenantId());
			}

			if (StringUtils.isEmpty(req.getProdCharge())) {
				req.setProdCharge(null);
			}
			// 상품등급코드 유효값 체크
			if (!this.commonProdGradeCd(req)) {
				res.setCommonResponse(commonResponse);
				return res;
			}

			// offset, Count default setting
			this.commonOffsetCount(req);

			// DB 조회 파라미터 생성
			Map<String, Object> reqMap = new HashMap<String, Object>();
			reqMap.put("req", req);
			reqMap.put("tenantHeader", tenantHeader);
			reqMap.put("deviceHeader", deviceHeader);
			reqMap.put("lang", tenantHeader.getLangCd());

			reqMap.put("imageCd", req.getImageCd());
			reqMap.put("svcGrpCd", DisplayConstants.DP_TSTORE_SHOPPING_PROD_SVC_GRP_CD);
			reqMap.put("contentTypeCd", DisplayConstants.DP_CHANNEL_CONTENT_TYPE_CD);
			reqMap.put("prodStatusCd", DisplayConstants.DP_SALE_STAT_ING);
			reqMap.put("prodRshpCd", DisplayConstants.DP_CHANNEL_EPISHODE_RELATIONSHIP_CD);

			// ID list 조회
			List<ProductBasicInfo> productBasicInfoList = this.commonDAO.queryForList("Shopping.getNewProductList",
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
		} else {
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
				identifier.setType("catalog");
				identifier.setText("CT00010008");

				// 메뉴 정보
				menu.setType("menuId");
				menu.setId("DP28009");
				menu.setName("편의점/마트");
				menuList.add(menu);

				// 상품 정보 (상품명)
				title.setText("추천/인기 카탈로그 상품");
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
				date.setType("duration/salePeriod");
				date.setText("2013-03-05 00:00:00/2014-03-25 23:59:59");
				rights.setGrade("PD004401");
				rights.setDate(date);

				// contributor
				identifier1.setType("brand");
				identifier1.setText("BR00010008");
				contributor.setName("세븐일레븐 바이더웨이");
				contributor.setIdentifier(identifier1);

				// saleoption
				saleoption.setType("delivery");

				// 데이터 매핑
				product.setIdentifier(identifier);
				product.setMenuList(menuList);
				product.setTitle(title);
				product.setPrice(price);
				product.setRights(rights);
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
	}

	/**
	 * <pre>
	 *  쇼핑 세부카테고리  상품 조회 .
	 * </pre>
	 */
	@Override
	public ShoppingRes getSubProductList(SacRequestHeader header, ShoppingReq req) {
		if (req.getDummy() == null) {
			// 공통 응답 변수 선언
			ShoppingRes res = new ShoppingRes();
			CommonResponse commonResponse = new CommonResponse();
			TenantHeader tenantHeader = header.getTenantHeader();
			DeviceHeader deviceHeader = header.getDeviceHeader();
			String[] temp = deviceHeader.getOsVersion().trim().split("/");
			String osVersion = temp[1];
			req.setTenantId(tenantHeader.getTenantId());
			req.setDeviceModelCd(deviceHeader.getModel());
			req.setLangCd(tenantHeader.getLangCd());
			req.setOsVersion(osVersion);
			req.setImageCd(DisplayConstants.DP_SHOPPING_REPRESENT_IMAGE_CD);

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
			// 상품등급코드 유효값 체크
			if (!this.commonProdGradeCd(req)) {
				res.setCommonResponse(commonResponse);
				return res;
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

			// DB 조회 파라미터 생성
			Map<String, Object> reqMap = new HashMap<String, Object>();
			reqMap.put("req", req);
			reqMap.put("tenantHeader", tenantHeader);
			reqMap.put("deviceHeader", deviceHeader);
			reqMap.put("stdDt", stdDt);
			reqMap.put("lang", tenantHeader.getLangCd());

			reqMap.put("imageCd", req.getImageCd());
			reqMap.put("svcGrpCd", DisplayConstants.DP_TSTORE_SHOPPING_PROD_SVC_GRP_CD);
			reqMap.put("contentTypeCd", DisplayConstants.DP_CHANNEL_CONTENT_TYPE_CD);
			reqMap.put("prodStatusCd", DisplayConstants.DP_SALE_STAT_ING);
			reqMap.put("prodRshpCd", DisplayConstants.DP_CHANNEL_EPISHODE_RELATIONSHIP_CD);

			// ID list 조회
			List<ProductBasicInfo> productBasicInfoList = this.commonDAO.queryForList("Shopping.getSubProductList",
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
		} else {
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
				identifier.setType("catalog");
				identifier.setText("CT00010008");

				// 메뉴 정보
				menu.setType("menuId");
				menu.setId("DP28009");
				menu.setName("편의점/마트");
				menuList.add(menu);

				// 상품 정보 (상품명)
				title.setText("추천/인기 카탈로그 상품");
				title.setPrefix("Y");
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
				date.setType("duration/salePeriod");
				date.setText("2013-03-05 00:00:00/2014-03-25 23:59:59");
				rights.setGrade("PD004401");
				rights.setDate(date);

				// contributor
				identifier1.setType("brand");
				identifier1.setText("BR00010008");
				contributor.setName("세븐일레븐 바이더웨이");
				contributor.setIdentifier(identifier1);

				// saleoption
				saleoption.setType("delivery");

				// 데이터 매핑
				product.setIdentifier(identifier);
				product.setMenuList(menuList);
				product.setTitle(title);
				product.setPrice(price);
				product.setRights(rights);
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
	}

	/**
	 * <pre>
	 * 특가 상품 리스트 조회.
	 * </pre>
	 */
	@Override
	public ShoppingRes getSecialPriceProductList(SacRequestHeader header, ShoppingReq req) {
		ShoppingRes res = new ShoppingRes();
		CommonResponse commonResponse = new CommonResponse();
		TenantHeader tenantHeader = header.getTenantHeader();
		DeviceHeader deviceHeader = header.getDeviceHeader();
		String[] temp = deviceHeader.getOsVersion().trim().split("/");
		String osVersion = temp[1];
		req.setTenantId(tenantHeader.getTenantId());
		req.setDeviceModelCd(deviceHeader.getModel());
		req.setLangCd(tenantHeader.getLangCd());
		req.setOsVersion(osVersion);
		req.setImageCd(DisplayConstants.DP_SHOPPING_REPRESENT_IMAGE_CD);

		List<Shopping> resultList = new ArrayList<Shopping>();

		if (StringUtils.isEmpty(req.getProdCharge())) {
			req.setProdCharge(null);
		}
		// 상품등급코드 유효값 체크
		if (!this.commonProdGradeCd(req)) {
			res.setCommonResponse(commonResponse);
			return res;
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
				product.setId(shopping.getPartProdId()); // 특가 상품 ID
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

			res = new ShoppingRes();
			res.setProductList(productList);

			commonResponse.setTotalCount(totalCount);
			res.setCommonResponse(commonResponse);
		}
		return res;
	}

	/**
	 * <pre>
	 * 기획전  상품  조회.
	 * </pre>
	 */
	@Override
	public ShoppingThemeRes getSpecialSalesList(SacRequestHeader header, ShoppingReq req) {
		// 공통 응답 변수 선언
		ShoppingThemeRes res = null;
		List<Shopping> resultList = new ArrayList<Shopping>();
		TenantHeader tenantHeader = header.getTenantHeader();
		DeviceHeader deviceHeader = header.getDeviceHeader();
		String[] temp = deviceHeader.getOsVersion().trim().split("/");
		String osVersion = temp[1];
		req.setTenantId(tenantHeader.getTenantId());
		req.setDeviceModelCd(deviceHeader.getModel());
		req.setLangCd(tenantHeader.getLangCd());
		req.setOsVersion(osVersion);
		req.setImageCd(DisplayConstants.DP_SHOPPING_SPECIAL_REPRESENT_IMAGE_CD);

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

			res = new ShoppingThemeRes();
			res.setPromotionList(promotionList);
		}
		return res;
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
		TenantHeader tenantHeader = header.getTenantHeader();
		DeviceHeader deviceHeader = header.getDeviceHeader();
		String[] temp = deviceHeader.getOsVersion().trim().split("/");
		String osVersion = temp[1];
		req.setTenantId(tenantHeader.getTenantId());
		req.setDeviceModelCd(deviceHeader.getModel());
		req.setLangCd(tenantHeader.getLangCd());
		req.setOsVersion(osVersion);
		req.setImageCd(DisplayConstants.DP_SHOPPING_REPRESENT_IMAGE_CD);
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
		// 상품등급코드 유효값 체크
		if (!this.commonProdGradeCd(req)) {
			res.setCommonResponse(commonResponse);
			return res;
		}
		// offset, Count default setting
		this.commonOffsetCount(req);

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

		TenantHeader tenantHeader = header.getTenantHeader();
		DeviceHeader deviceHeader = header.getDeviceHeader();
		String[] temp = deviceHeader.getOsVersion().trim().split("/");
		String osVersion = temp[1];
		req.setTenantId(tenantHeader.getTenantId());
		req.setDeviceModelCd(deviceHeader.getModel());
		req.setLangCd(tenantHeader.getLangCd());
		req.setOsVersion(osVersion);
		req.setImageCd(DisplayConstants.DP_SHOPPING_BRAND_REPRESENT_IMAGE_CD);

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
		List<Shopping> resultList = new ArrayList<Shopping>();
		CommonResponse commonResponse = new CommonResponse();
		TenantHeader tenantHeader = header.getTenantHeader();
		DeviceHeader deviceHeader = header.getDeviceHeader();
		String[] temp = deviceHeader.getOsVersion().trim().split("/");
		String osVersion = temp[1];
		req.setTenantId(tenantHeader.getTenantId());
		req.setDeviceModelCd(deviceHeader.getModel());
		req.setLangCd(tenantHeader.getLangCd());
		req.setOsVersion(osVersion);
		req.setImageCd(DisplayConstants.DP_SHOPPING_REPRESENT_IMAGE_CD);
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

		if (StringUtils.isEmpty(req.getProdCharge())) {
			req.setProdCharge(null);
		}
		// 상품등급코드 유효값 체크
		if (!this.commonProdGradeCd(req)) {
			res.setCommonResponse(commonResponse);
			return res;
		}

		// offset, Count default setting
		this.commonOffsetCount(req);

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

		TenantHeader tenantHeader = header.getTenantHeader();
		DeviceHeader deviceHeader = header.getDeviceHeader();
		String[] temp = deviceHeader.getOsVersion().trim().split("/");
		String osVersion = temp[1];
		req.setTenantId(tenantHeader.getTenantId());
		req.setDeviceModelCd(deviceHeader.getModel());
		req.setLangCd(tenantHeader.getLangCd());
		req.setOsVersion(osVersion);
		req.setImageCd(DisplayConstants.DP_SHOPPING_THEME_REPRESENT_IMAGE_CD);

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
		List<Shopping> resultList = new ArrayList<Shopping>();

		CommonResponse commonResponse = new CommonResponse();
		TenantHeader tenantHeader = header.getTenantHeader();
		DeviceHeader deviceHeader = header.getDeviceHeader();
		String[] temp = deviceHeader.getOsVersion().trim().split("/");
		String osVersion = temp[1];
		req.setTenantId(tenantHeader.getTenantId());
		req.setDeviceModelCd(deviceHeader.getModel());
		req.setLangCd(tenantHeader.getLangCd());
		req.setOsVersion(osVersion);
		req.setImageCd(DisplayConstants.DP_SHOPPING_REPRESENT_IMAGE_CD);

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
		// 상품등급코드 유효값 체크
		if (!this.commonProdGradeCd(req)) {
			res.setCommonResponse(commonResponse);
			return res;
		}
		// offset, Count default setting
		this.commonOffsetCount(req);

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
		if (req.getDummy() == null) {

			// 공통 응답 변수 선언
			ShoppingRes res = new ShoppingRes();
			CommonResponse commonResponse = new CommonResponse();
			TenantHeader tenantHeader = header.getTenantHeader();
			DeviceHeader deviceHeader = header.getDeviceHeader();
			String[] temp = deviceHeader.getOsVersion().trim().split("/");
			String osVersion = temp[1];
			req.setTenantId(tenantHeader.getTenantId());
			req.setDeviceModelCd(deviceHeader.getModel());
			req.setLangCd(tenantHeader.getLangCd());
			req.setOsVersion(osVersion);
			req.setImageCd(DisplayConstants.DP_SHOPPING_REPRESENT_IMAGE_CD);

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
			// 상품등급코드 유효값 체크
			if (!this.commonProdGradeCd(req)) {
				res.setCommonResponse(commonResponse);
				return res;
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

			// DB 조회 파라미터 생성
			Map<String, Object> reqMap = new HashMap<String, Object>();
			reqMap.put("req", req);
			reqMap.put("tenantHeader", tenantHeader);
			reqMap.put("deviceHeader", deviceHeader);
			reqMap.put("stdDt", stdDt);
			reqMap.put("lang", tenantHeader.getLangCd());

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
		} else {
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
				identifier.setType("catalog");
				identifier.setText("CT00010008");

				// 메뉴 정보
				menu.setType("menuId");
				menu.setId("DP28009");
				menu.setName("편의점/마트");
				menuList.add(menu);

				// 상품 정보 (상품명)
				title.setText("추천/인기 카탈로그 상품");
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
				date.setType("duration/salePeriod");
				date.setText("2013-03-05 00:00:00/2014-03-25 23:59:59");
				rights.setGrade("PD004401");
				rights.setDate(date);

				// contributor
				identifier1.setType("brand");
				identifier1.setText("BR00010008");
				contributor.setName("세븐일레븐 바이더웨이");
				contributor.setIdentifier(identifier1);

				// saleoption
				saleoption.setType("delivery");

				// 데이터 매핑
				product.setIdentifier(identifier);
				product.setMenuList(menuList);
				product.setTitle(title);
				product.setPrice(price);
				product.setRights(rights);
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
	}

	/**
	 * <pre>
	 * 특정 브랜드에 대한 다른 상품 리스트
	 * </pre>
	 */
	@Override
	public ShoppingRes getBrandAnotherProductList(SacRequestHeader header, ShoppingReq req) {
		if (req.getDummy() == null) {

			// 공통 응답 변수 선언
			ShoppingRes res = new ShoppingRes();
			CommonResponse commonResponse = new CommonResponse();
			TenantHeader tenantHeader = header.getTenantHeader();
			DeviceHeader deviceHeader = header.getDeviceHeader();
			String[] temp = deviceHeader.getOsVersion().trim().split("/");
			String osVersion = temp[1];
			req.setTenantId(tenantHeader.getTenantId());
			req.setDeviceModelCd(deviceHeader.getModel());
			req.setLangCd(tenantHeader.getLangCd());
			req.setOsVersion(osVersion);
			req.setImageCd(DisplayConstants.DP_SHOPPING_REPRESENT_IMAGE_CD);

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
			// 상품등급코드 유효값 체크
			if (!this.commonProdGradeCd(req)) {
				res.setCommonResponse(commonResponse);
				return res;
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

			// DB 조회 파라미터 생성
			Map<String, Object> reqMap = new HashMap<String, Object>();
			reqMap.put("req", req);
			reqMap.put("tenantHeader", tenantHeader);
			reqMap.put("deviceHeader", deviceHeader);
			reqMap.put("stdDt", stdDt);
			reqMap.put("lang", tenantHeader.getLangCd());

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
		} else {
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
				identifier.setType("catalog");
				identifier.setText("CT00010008");

				// 메뉴 정보
				menu.setType("menuId");
				menu.setId("DP28009");
				menu.setName("편의점/마트");
				menuList.add(menu);

				// 상품 정보 (상품명)
				title.setText("추천/인기 카탈로그 상품");
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
				date.setType("duration/salePeriod");
				date.setText("2013-03-05 00:00:00/2014-03-25 23:59:59");
				rights.setGrade("PD004401");
				rights.setDate(date);

				// contributor
				identifier1.setType("brand");
				identifier1.setText("BR00010008");
				contributor.setName("세븐일레븐 바이더웨이");
				contributor.setIdentifier(identifier1);

				// saleoption
				saleoption.setType("delivery");

				// 데이터 매핑
				product.setIdentifier(identifier);
				product.setMenuList(menuList);
				product.setTitle(title);
				product.setPrice(price);
				product.setRights(rights);
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
	}

	/**
	 * <pre>
	 * 쇼핑상세
	 * </pre>
	 */
	@Override
	public ShoppingRes getShoppingDetail(SacRequestHeader header, ShoppingReq req) {
		// 공통 응답 변수 선언
		ShoppingRes res = new ShoppingRes();
		TenantHeader tenantHeader = header.getTenantHeader();
		DeviceHeader deviceHeader = header.getDeviceHeader();
		String[] temp = deviceHeader.getOsVersion().trim().split("/");
		String osVersion = temp[1];
		req.setTenantId(tenantHeader.getTenantId());
		req.setDeviceModelCd(deviceHeader.getModel());
		req.setLangCd(tenantHeader.getLangCd());
		req.setOsVersion(osVersion);
		req.setImageCd(DisplayConstants.DP_SHOPPING_REPRESENT_IMAGE_CD);

		// 필수 파라미터 체크
		if (StringUtils.isEmpty(header.getTenantHeader().getTenantId())) {
			throw new StorePlatformException("SAC_DSP_0002", "tenantId", req.getTenantId());
		}
		if (StringUtils.isEmpty(req.getProdId())) {
			throw new StorePlatformException("SAC_DSP_0002", "prodId", req.getProdId());
		}

		// OPTIONAL 파라미터 체크
		if (StringUtils.isEmpty(req.getSpecialProdId())) {
			req.setSpecialProdId(null);
		}
		if (StringUtils.isEmpty(req.getUserKey())) {
			req.setUserKey(null);
		}
		if (StringUtils.isEmpty(req.getDeviceKey())) {
			req.setDeviceKey(null);
		}

		// DB 조회 파라미터 생성
		Map<String, Object> reqMap = new HashMap<String, Object>();
		reqMap.put("req", req);
		reqMap.put("tenantHeader", tenantHeader);
		reqMap.put("deviceHeader", deviceHeader);
		reqMap.put("lang", tenantHeader.getLangCd());

		reqMap.put("imageCd", req.getImageCd());
		reqMap.put("svcGrpCd", DisplayConstants.DP_TSTORE_SHOPPING_PROD_SVC_GRP_CD);
		reqMap.put("contentTypeCd", DisplayConstants.DP_CHANNEL_CONTENT_TYPE_CD);
		reqMap.put("prodStatusCd", DisplayConstants.DP_SALE_STAT_ING);
		reqMap.put("prodRshpCd", DisplayConstants.DP_CHANNEL_EPISHODE_RELATIONSHIP_CD);
		// ID list 조회
		List<Shopping> resultChannelList = this.commonDAO.queryForList("Shopping.getShoppingChannelDetail", reqMap,
				Shopping.class);

		if (resultChannelList == null) {
			throw new StorePlatformException("SAC_DSP_0001", "쇼핑 상세 확인 ");
		} else {
			if (resultChannelList.size() > 0) {

				// Response VO를 만들기위한 생성자
				Product product = null;
				Identifier identifier = null;
				Identifier identifier1 = null;
				Menu menu = null;
				Title title = null;
				Source source = null;
				Contributor contributor = null;
				Accrual accrual = null;

				List<Identifier> identifierList = null;

				// / 에피소드용
				Product episodeProduct = null;
				Identifier episodeIdentifier = null;
				List<Identifier> episodeIdentifierList = null;
				Menu episodeMenu = null;
				List<Menu> episodeMenuList = new ArrayList<Menu>();
				Price episodePrice = null;
				Rights episodeRights = null;
				Date episodeDate = null;
				Distributor distributor = null;
				Purchase purchase = null;
				Identifier purchaseIdentifier = null;
				List<Identifier> purchaseIdentifierList = new ArrayList<Identifier>();
				Date purchaseDate = null;
				List<Date> episodeDateList = new ArrayList<Date>();
				SalesOption episodeSaleOption = null;

				// / 옵션용
				SelectOption selectOption = null;
				SubSelectOption subSelectOption = null;
				List<SelectOption> selectOptionList = new ArrayList<SelectOption>();
				List<SubSelectOption> subSelectOptionList = new ArrayList<SubSelectOption>();
				Title option1Title = null;
				Price option1Price = null;
				Title option2Title = null;
				Price option2Price = null;

				List<Menu> menuList = null;
				List<Source> sourceList = null;
				List<Product> subProductList = new ArrayList<Product>();

				List<Product> productList = new ArrayList<Product>();
				// 채널 list 조회
				for (int i = 0; i < resultChannelList.size(); i++) {
					Shopping shopping = resultChannelList.get(i);

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
					menu.setId(shopping.getMenuId());
					menu.setName(shopping.getMenuName());
					menuList.add(menu);

					// 상품 정보 (상품명)
					title = new Title();
					title.setText(shopping.getCatalogName());

					// 이미지 정보
					sourceList = new ArrayList<Source>();
					source = new Source();
					source.setType(DisplayConstants.DP_SOURCE_TYPE_THUMBNAIL);
					source.setUrl(shopping.getFilePos());
					sourceList.add(source);

					// 다운로드 수
					accrual = new Accrual();
					accrual.setDownloadCount(shopping.getPrchsQty());

					// contributor
					contributor = new Contributor();
					identifier1 = new Identifier();
					identifier1.setType(DisplayConstants.DP_BRAND_IDENTIFIER_CD);
					identifier1.setText(shopping.getBrandId());
					contributor.setName(shopping.getBrandName());
					contributor.setIdentifier(identifier1);

					String deliveryValue = shopping.getProdCaseCd();
					// 배송상품은 단품이며 기본정보는 동일
					if (deliveryValue.equals("delivery")) {
						reqMap.put("endRow", "1");
					}

					// 에피소드 list 조회
					List<Shopping> resultEpisodeList = this.commonDAO.queryForList("Shopping.getShoppingEpisodeDetail",
							reqMap, Shopping.class);
					if (resultEpisodeList != null) {
						for (int kk = 0; kk < resultEpisodeList.size(); kk++) {
							Shopping episodeShopping = resultEpisodeList.get(kk);

							episodeProduct = new Product();

							// 특가 상품일 경우
							episodeMenu = new Menu();
							episodeMenu.setType(episodeShopping.getSpecialSale());
							episodeMenuList.add(episodeMenu);
							episodeProduct.setMenuList(episodeMenuList);

							// 채널 상품 정보 (상품ID)
							episodeIdentifierList = new ArrayList<Identifier>();
							episodeIdentifier = new Identifier();
							episodeIdentifier.setType(DisplayConstants.DP_CHANNEL_IDENTIFIER_CD);
							episodeIdentifier.setText(episodeShopping.getProdId());
							episodeIdentifierList.add(episodeIdentifier);

							// 에피소드 상품 정보 (상품ID)
							episodeIdentifier = new Identifier();
							episodeIdentifier.setType(DisplayConstants.DP_EPISODE_IDENTIFIER_CD);
							episodeIdentifier.setText(episodeShopping.getPartProdId());
							episodeIdentifierList.add(episodeIdentifier);

							episodeProduct.setIdentifierList(episodeIdentifierList);

							// 에피소드 상품 가격 정보
							episodePrice = new Price();
							episodePrice.setFixedPrice(episodeShopping.getProdNetAmt());
							episodePrice.setDiscountRate(episodeShopping.getDcRate());
							episodePrice.setText(episodeShopping.getProdAmt());
							episodeProduct.setPrice(episodePrice);

							// 에피소드 구매내역 정보
							String prchsId = null;
							String prchsDt = null;
							String prchsState = null;
							int purchseCount = 0;

							try {
								// 구매내역 조회를 위한 생성자
								ProductListSac productListSac = new ProductListSac();
								productListSac.setProdId(episodeShopping.getPartProdId());

								List<ProductListSac> productEpisodeList = new ArrayList<ProductListSac>();
								productEpisodeList.add(productListSac);

								HistoryListSacReq historyListSacReq = new HistoryListSacReq();
								historyListSacReq.setTenantId(req.getTenantId());
								historyListSacReq.setUserKey(req.getUserKey());
								historyListSacReq.setDeviceKey(req.getDeviceKey());
								historyListSacReq.setPrchsProdType(PurchaseConstants.PRCHS_PROD_TYPE_OWN);
								historyListSacReq.setStartDt("19000101000000");
								historyListSacReq.setEndDt(episodeShopping.getSysDate());
								historyListSacReq.setOffset(1);
								historyListSacReq.setCount(1);
								historyListSacReq.setProductList(productEpisodeList);

								// 구매내역 조회 실행
								if (req.getUserKey() != null) {// userKey 가 없을 경우
									HistoryListSacRes historyListSacRes = this.historyListService
											.searchHistoryList(historyListSacReq);

									this.log.debug("----------------------------------------------------------------");
									this.log.debug("[getDownloadComicInfo] purchase count : {}",
											historyListSacRes.getTotalCnt());
									this.log.debug("----------------------------------------------------------------");
									purchseCount = historyListSacRes.getTotalCnt();
									if (historyListSacRes.getTotalCnt() > 0) {
										prchsId = historyListSacRes.getHistoryList().get(0).getPrchsId();
										prchsDt = historyListSacRes.getHistoryList().get(0).getPrchsDt();
										prchsState = historyListSacRes.getHistoryList().get(0).getPrchsCaseCd();

										if (PurchaseConstants.PRCHS_CASE_PURCHASE_CD.equals(prchsState)) {
											prchsState = "payment";
										} else if (PurchaseConstants.PRCHS_CASE_GIFT_CD.equals(prchsState)) {
											prchsState = "gift";
										}
									}
								}
							} catch (Exception ex) {
								throw new StorePlatformException("SAC_DSP_0001", "구매내역 조회 ", ex);
							}

							purchase = new Purchase();
							purchaseIdentifier = new Identifier();
							purchaseIdentifier.setType(DisplayConstants.DP_PURCHASE_IDENTIFIER_CD);
							purchaseIdentifier.setText(prchsId);
							purchaseIdentifierList.add(purchaseIdentifier);
							purchase.setIdentifierList(purchaseIdentifierList);
							purchase.setState(prchsState);
							purchaseDate = new Date();
							purchaseDate.setType(DisplayConstants.DP_SHOPPING_PURCHASE_TYPE_NM);
							purchaseDate.setText(prchsDt);
							purchase.setDate(purchaseDate);
							if (purchseCount > 0) {// 구매 건수가 있을 경우
								episodeProduct.setPurchase(purchase);
							}

							// 에피소드 날짜 권한 정보
							episodeDateList = new ArrayList<Date>();
							episodeRights = new Rights();
							episodeDate = new Date();
							episodeDate.setType(DisplayConstants.DP_SHOPPING_RIGHTS_TYPE_NM);
							episodeDate.setText(episodeShopping.getApplyStartDt() + "/"
									+ episodeShopping.getApplyEndDt());
							episodeDateList.add(episodeDate);

							episodeDate = new Date();
							episodeDate.setType(DisplayConstants.DP_SHOPPING_RIGHTS_TYPE_UNIT_NM);
							episodeDate.setText(episodeShopping.getUsePeriod());

							episodeDateList.add(episodeDate);

							// 상품 구매가 있고 후기가 없으면 feedback값을 내려줘야 함
							if (purchseCount > 0) {
								episodeRights.setAllow(episodeShopping.getAllow());
							}

							episodeRights.setGrade(episodeShopping.getProdGrdCd());
							episodeRights.setDateList(episodeDateList);
							episodeProduct.setRights(episodeRights);

							// saleOption 셋팅
							episodeSaleOption = new SalesOption();
							episodeSaleOption.setBtob(episodeShopping.getB2bProdYn()); // B2B_상품_여부
							episodeSaleOption.setType(shopping.getProdCaseCd());

							if (episodeShopping.getSoldOut().equals("Y")) {
								episodeSaleOption.setSatus(DisplayConstants.DP_SOLDOUT);
							} else {
								episodeSaleOption.setSatus(DisplayConstants.DP_CONTINUE);
							}

							episodeSaleOption.setMaxMonthlySale(episodeShopping.getMthMaxCnt()); // 월_최대_판매_수량
							episodeSaleOption.setMaxDailySale(episodeShopping.getDlyMaxCnt()); // 일_최대_판매_수량
							episodeSaleOption.setMaxMonthlyBuy(episodeShopping.getMthUsrMaxCnt()); // 월_회원_최대_구매_수량
							episodeSaleOption.setMaxDailyBuy(episodeShopping.getDlyUsrMaxCnt()); // 일_회원_최대_구매_수량
							episodeSaleOption.setMaxOnceBuy(episodeShopping.getEachMaxCnt()); // 1차_최대_구매_수량
							episodeSaleOption.setPlaceUsage(episodeShopping.getUsePlac()); // 사용_장소
							episodeSaleOption.setRestrictUsage(episodeShopping.getUseLimtDesc()); // 사용_제한_설명
							episodeSaleOption.setPrincipleUsage(episodeShopping.getNoticeMatt()); // 공지_사항
							episodeSaleOption.setRefundUsage(episodeShopping.getPrchsCancelDrbkReason()); // 구매_취소_환불_사유
							episodeProduct.setSalesOption(episodeSaleOption);
							boolean nextFlag = false;
							// Option 정보조회( 배송상품일 경우만 존재 )
							if (deliveryValue.equals("delivery")) {
								reqMap.put("chnlProdId", episodeShopping.getProdId());
								// 에피소드 list 조회
								List<Shopping> resultOptionList = this.commonDAO.queryForList(
										"Shopping.getShoppingOption", reqMap, Shopping.class);
								if (resultOptionList != null) {
									for (int mm = 0; mm < resultOptionList.size(); mm++) {
										Shopping optionShopping = resultOptionList.get(mm);
										if (optionShopping.getSubYn().equals("Y")) { // 옵션 1 인 경우
											selectOption = new SelectOption();
											// 옵션1 상품 ID
											selectOption.setId(optionShopping.getPartProdId());
											selectOption.setIndex(optionShopping.getExpoOrd());

											// 옵션1 상품 정보 (상품명)
											option1Title = new Title();
											option1Title.setText(optionShopping.getOptPdNm());
											selectOption.setTitle(option1Title);
											// 옵션1 상품 가격정보
											option1Price = new Price();
											option1Price.setFixedPrice(optionShopping.getProdNetAmt());
											option1Price.setDiscountRate(optionShopping.getDcRate());
											option1Price.setText(optionShopping.getProdAmt());
											selectOption.setPrice(option1Price);
										}
										if (optionShopping.getSubYn().equals("N")) { // 옵션 2 인 경우
											subSelectOption = new SubSelectOption();
											// 옵션2 상품 ID
											subSelectOption.setSubId(optionShopping.getOpt1Nm());

											// 옵션2 상품 정보 (상품명)
											option2Title = new Title();
											option2Title.setText(optionShopping.getOptPdNm());
											subSelectOption.setSubTitle(option2Title);
											// 옵션2 상품 가격정보
											option2Price = new Price();
											option2Price.setFixedPrice(optionShopping.getProdNetAmt());
											option2Price.setDiscountRate(optionShopping.getDcRate());
											option2Price.setText(optionShopping.getProdAmt());
											subSelectOption.setSubPrice(option2Price);

											subSelectOptionList.add(subSelectOption);
										}
										if (mm < resultOptionList.size() - 1) {
											if (resultOptionList.get(mm + 1).getSubYn().equals("Y")) { // 다음건이 Y 이면 값을
												nextFlag = true;
											} else {
												nextFlag = false;
											}
										} else { // 마지막에는 값을 셋팅
											nextFlag = true;
										}

										if (nextFlag) {
											selectOption.setSubSelectOptionList(subSelectOptionList);
											subSelectOptionList = new ArrayList<SubSelectOption>();
											selectOptionList.add(selectOption);
										}
									}
								}
							}

							episodeProduct.setSelectOptionList(selectOptionList);

							// 판매자정보 셋팅
							DetailInformationReq memberReq = new DetailInformationReq();
							DetailInformationRes memberRes = new DetailInformationRes();
							try {
								memberReq.setSellerKey(episodeShopping.getSellerMbrNo());
								memberReq.setSellerId("");
								memberRes = this.sellerSearchService.detailInformation(header, memberReq);
								if (memberRes != null) {
									memberRes.getSellerMbr().getSellerCompany();
									distributor = new Distributor();
									distributor.setType(DisplayConstants.DP_CORPORATION_IDENTIFIER_CD);
									distributor.setIdentifier(memberRes.getSellerMbr().getSellerId());
									distributor.setName(memberRes.getSellerMbr().getSellerName());
									distributor.setCompany(memberRes.getSellerMbr().getSellerCompany());
									distributor.setTel(memberRes.getSellerMbr().getRepPhone());
									distributor.setEmail(memberRes.getSellerMbr().getSellerEmail());
									distributor.setAddress(memberRes.getSellerMbr().getSellerAddress()
											+ memberRes.getSellerMbr().getSellerDetailAddress());
									distributor.setRegNo(memberRes.getSellerMbr().getBizRegNumber());
									episodeProduct.setDistributor(distributor);

								}
							} catch (Exception e) {
								throw new StorePlatformException("SAC_DSP_0001", "멤버 정보 조회 ", e);
							}

							// 에피소드 특가상품
							if (episodeShopping.getSpecialSale() != null) {
								episodeProduct.setId(episodeShopping.getPartProdId());
							}
							subProductList.add(episodeProduct);
						}
					}
					// 데이터 매핑
					product.setIdentifierList(identifierList);
					product.setMenuList(menuList);
					product.setTitle(title);
					product.setSourceList(sourceList);
					product.setAccrual(accrual);
					product.setContributor(contributor);
					product.setSubProductList(subProductList);
					productList.add(i, product);
				}
				res.setProductList(productList);
			} else {
				throw new StorePlatformException("SAC_DSP_0001", "쇼핑 상세 확인 ");
			}
		}
		return res;
	}

	private void commonOffsetCount(ShoppingReq req) {

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

		req.setOffset(req.getOffset() <= 0 ? 1 : req.getOffset());
		req.setCount(req.getCount() <= 0 ? 20 : req.getCount());
	}

	private boolean commonProdGradeCd(ShoppingReq req) {
		boolean result = true;
		// 상품등급코드 유효값 체크
		if (StringUtils.isNotEmpty(req.getProdGradeCd())) {
			String[] arrayProdGradeCd = req.getProdGradeCd().split("\\+");
			for (int i = 0; i < arrayProdGradeCd.length; i++) {
				if (StringUtils.isNotEmpty(arrayProdGradeCd[i])) {
					if (!"PD004401".equals(arrayProdGradeCd[i]) && !"PD004402".equals(arrayProdGradeCd[i])
							&& !"PD004403".equals(arrayProdGradeCd[i])) {
						this.log.debug("----------------------------------------------------------------");
						this.log.debug("유효하지않은 상품 등급 코드 : " + arrayProdGradeCd[i]);
						this.log.debug("----------------------------------------------------------------");
						result = false;
					}
				}
			}
		} else {
			req.setProdGradeCd(null);
		}

		// '+'로 연결 된 상품등급코드를 배열로 전달
		if (StringUtils.isNotEmpty(req.getProdGradeCd())) {
			String[] arrayProdGradeCd = req.getProdGradeCd().split("\\+");
			req.setArrayProdGradeCd(arrayProdGradeCd);
		}
		return result;
	}
}
