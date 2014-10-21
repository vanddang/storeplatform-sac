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
import com.skplanet.storeplatform.sac.api.util.DateUtil;
import com.skplanet.storeplatform.sac.client.display.vo.shopping.ShoppingBrandAnotherReq;
import com.skplanet.storeplatform.sac.client.display.vo.shopping.ShoppingBrandReq;
import com.skplanet.storeplatform.sac.client.display.vo.shopping.ShoppingBrandRes;
import com.skplanet.storeplatform.sac.client.display.vo.shopping.ShoppingCategoryAnotherReq;
import com.skplanet.storeplatform.sac.client.display.vo.shopping.ShoppingDetailReq;
import com.skplanet.storeplatform.sac.client.display.vo.shopping.ShoppingDetailRes;
import com.skplanet.storeplatform.sac.client.display.vo.shopping.ShoppingFeatureReq;
import com.skplanet.storeplatform.sac.client.display.vo.shopping.ShoppingPlanReq;
import com.skplanet.storeplatform.sac.client.display.vo.shopping.ShoppingReq;
import com.skplanet.storeplatform.sac.client.display.vo.shopping.ShoppingRes;
import com.skplanet.storeplatform.sac.client.display.vo.shopping.ShoppingSpcialSaleRes;
import com.skplanet.storeplatform.sac.client.display.vo.shopping.ShoppingSubReq;
import com.skplanet.storeplatform.sac.client.display.vo.shopping.ShoppingThemeReq;
import com.skplanet.storeplatform.sac.client.display.vo.shopping.ShoppingThemeRes;
import com.skplanet.storeplatform.sac.client.internal.display.localsci.vo.PaymentInfo;
import com.skplanet.storeplatform.sac.client.internal.display.localsci.vo.PaymentInfoSacReq;
import com.skplanet.storeplatform.sac.client.internal.member.user.vo.GradeInfoSac;
import com.skplanet.storeplatform.sac.client.internal.purchase.history.sci.HistoryInternalSCI;
import com.skplanet.storeplatform.sac.client.internal.purchase.history.vo.HistoryListSacInReq;
import com.skplanet.storeplatform.sac.client.internal.purchase.history.vo.HistoryListSacInRes;
import com.skplanet.storeplatform.sac.client.internal.purchase.history.vo.ProductListSacIn;
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
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.product.Point;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.product.Product;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.product.Promotion;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.product.Rights;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.product.SalesOption;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.product.SelectOption;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.product.SubSelectOption;
import com.skplanet.storeplatform.sac.common.header.vo.DeviceHeader;
import com.skplanet.storeplatform.sac.common.header.vo.SacRequestHeader;
import com.skplanet.storeplatform.sac.common.header.vo.TenantHeader;
import com.skplanet.storeplatform.sac.common.util.DateUtils;
import com.skplanet.storeplatform.sac.display.common.constant.DisplayConstants;
import com.skplanet.storeplatform.sac.display.common.service.DisplayCommonService;
import com.skplanet.storeplatform.sac.display.common.service.MemberBenefitService;
import com.skplanet.storeplatform.sac.display.common.vo.MileageInfo;
import com.skplanet.storeplatform.sac.display.common.vo.SupportDevice;
import com.skplanet.storeplatform.sac.display.common.vo.TmembershipDcInfo;
import com.skplanet.storeplatform.sac.display.meta.service.MetaInfoService;
import com.skplanet.storeplatform.sac.display.meta.vo.MetaInfo;
import com.skplanet.storeplatform.sac.display.meta.vo.ProductBasicInfo;
import com.skplanet.storeplatform.sac.display.response.CommonMetaInfoGenerator;
import com.skplanet.storeplatform.sac.display.response.ResponseInfoGenerateFacade;
import com.skplanet.storeplatform.sac.display.response.ShoppingInfoGenerator;

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
	HistoryInternalSCI historyInternalSCI;

	@Autowired
	private CommonMetaInfoGenerator commonGenerator;

	@Autowired
	private ShoppingInfoGenerator shoppingGenerator;

	@Autowired
	private MemberBenefitService benefitService;

	@Autowired
	private DisplayCommonService commonService;

	/**
	 * 쇼핑 추천/인기 상품 리스트 조회.
	 * 
	 * @param header
	 *            header
	 * @param req
	 *            req
	 * @return ShoppingRes
	 */
	@Override
	public ShoppingRes getFeatureProductList(SacRequestHeader header, ShoppingFeatureReq req) {

		// 공통 응답 변수 선언
		ShoppingRes res = new ShoppingRes();
		CommonResponse commonResponse = new CommonResponse();
		TenantHeader tenantHeader = header.getTenantHeader();
		DeviceHeader deviceHeader = header.getDeviceHeader();
		req.setImageCd(DisplayConstants.DP_SHOPPING_REPRESENT_IMAGE_CD);
		req.setVirtualDeviceModelNo(DisplayConstants.DP_ANY_PHONE_4MM);

		if (StringUtils.isEmpty(req.getListId())) {
			throw new StorePlatformException("SAC_DSP_0002", "listId", req.getListId());
		}
		if (StringUtils.isEmpty(req.getProdCharge())) {
			req.setProdCharge(null);
		}

		// 파라미터 유효값 체크
		if (StringUtils.isNotEmpty(req.getProdGradeCd())) {
			String[] arrayProdGradeCd = req.getProdGradeCd().split("\\+");
			for (int i = 0; i < arrayProdGradeCd.length; i++) {
				if (StringUtils.isNotEmpty(arrayProdGradeCd[i])) {
					if (!"PD004401".equals(arrayProdGradeCd[i]) && !"PD004402".equals(arrayProdGradeCd[i])
							&& !"PD004403".equals(arrayProdGradeCd[i])) {
						this.log.debug("----------------------------------------------------------------");
						this.log.debug("유효하지않은 상품 등급 코드 : " + arrayProdGradeCd[i]);
						this.log.debug("----------------------------------------------------------------");

						throw new StorePlatformException("SAC_DSP_0003", (i + 1) + " 번째 prodGradeCd",
								arrayProdGradeCd[i]);
					}
				}
			}
		}

		int offset = 1; // default
		int count = 20; // default

		if (req.getOffset() != null) {
			offset = req.getOffset();
		}
		req.setOffset(offset); // set offset

		if (req.getCount() != null) {
			count = req.getCount();
		}
		count = offset + count - 1;
		req.setCount(count); // set count

		req.setOffset(req.getOffset() <= 0 ? 1 : req.getOffset());
		req.setCount(req.getCount() <= 0 ? 20 : req.getCount());

		// '+'로 연결 된 상품등급코드를 배열로 전달
		if (StringUtils.isNotEmpty(req.getProdGradeCd())) {
			String[] arrayProdGradeCd = req.getProdGradeCd().split("\\+");
			req.setArrayProdGradeCd(arrayProdGradeCd);
		}
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
		reqMap.put("channelContentTypeCd", DisplayConstants.DP_CHANNEL_CONTENT_TYPE_CD);

		List<Product> productList = new ArrayList<Product>();
		if (!this.commonSupportDeviceShopping(header)) {
			// 조회 결과 없음
			commonResponse.setTotalCount(0);
			res.setProductList(productList);
			res.setCommonResponse(commonResponse);
			return res;
		}

		// ID list 조회
		List<ProductBasicInfo> productBasicInfoList = this.commonDAO.queryForList("Shopping.getFeatureProductList",
				reqMap, ProductBasicInfo.class);

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
	 * 쇼핑 신규 상품 조회.
	 * 
	 * @param header
	 *            header
	 * @param req
	 *            req
	 * @return ShoppingRes
	 */
	@Override
	public ShoppingRes getNewProductList(SacRequestHeader header, ShoppingReq req) {
		// 공통 응답 변수 선언
		ShoppingRes res = new ShoppingRes();
		CommonResponse commonResponse = new CommonResponse();
		TenantHeader tenantHeader = header.getTenantHeader();
		DeviceHeader deviceHeader = header.getDeviceHeader();
		req.setTenantId(tenantHeader.getTenantId());
		req.setDeviceModelCd(deviceHeader.getModel());
		req.setLangCd(tenantHeader.getLangCd());
		req.setImageCd(DisplayConstants.DP_SHOPPING_REPRESENT_IMAGE_CD);
		req.setVirtualDeviceModelNo(DisplayConstants.DP_ANY_PHONE_4MM);

		// 필수 파라미터 체크
		if (StringUtils.isEmpty(header.getTenantHeader().getTenantId())) {
			throw new StorePlatformException("SAC_DSP_0002", "tenantId", req.getTenantId());
		}

		if (StringUtils.isEmpty(req.getProdCharge())) {
			req.setProdCharge(null);
		}
		// 상품등급코드 유효값 체크
		if (!this.commonProdGradeCd(req)) {
			throw new StorePlatformException("SAC_DSP_0003", "prodGradeCd", req.getProdGradeCd());
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
		reqMap.put("channelContentTypeCd", DisplayConstants.DP_CHANNEL_CONTENT_TYPE_CD);

		List<Product> productList = new ArrayList<Product>();
		if (!this.commonSupportDeviceShopping(header)) {
			// 조회 결과 없음
			commonResponse.setTotalCount(0);
			res.setProductList(productList);
			res.setCommonResponse(commonResponse);
			return res;
		}

		// ID list 조회
		List<ProductBasicInfo> productBasicInfoList = this.commonDAO.queryForList("Shopping.getNewProductList", reqMap,
				ProductBasicInfo.class);
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
	 * 쇼핑 세부카테고리 상품 조회 .
	 * 
	 * @param header
	 *            header
	 * @param req
	 *            req
	 * @return ShoppingRes
	 */
	@Override
	public ShoppingRes getSubProductList(SacRequestHeader header, ShoppingSubReq req) {
		// 공통 응답 변수 선언
		ShoppingRes res = new ShoppingRes();
		CommonResponse commonResponse = new CommonResponse();
		TenantHeader tenantHeader = header.getTenantHeader();
		DeviceHeader deviceHeader = header.getDeviceHeader();

		req.setImageCd(DisplayConstants.DP_SHOPPING_REPRESENT_IMAGE_CD);
		req.setVirtualDeviceModelNo(DisplayConstants.DP_ANY_PHONE_4MM);

		if (StringUtils.isEmpty(req.getMenuId())) {
			throw new StorePlatformException("SAC_DSP_0002", "menuId", req.getMenuId());
		}
		if (StringUtils.isEmpty(req.getProdCharge())) {
			req.setProdCharge(null);
		}
		// 파라미터 유효값 체크
		if (StringUtils.isNotEmpty(req.getProdGradeCd())) {
			String[] arrayProdGradeCd = req.getProdGradeCd().split("\\+");
			for (int i = 0; i < arrayProdGradeCd.length; i++) {
				if (StringUtils.isNotEmpty(arrayProdGradeCd[i])) {
					if (!"PD004401".equals(arrayProdGradeCd[i]) && !"PD004402".equals(arrayProdGradeCd[i])
							&& !"PD004403".equals(arrayProdGradeCd[i])) {
						this.log.debug("----------------------------------------------------------------");
						this.log.debug("유효하지않은 상품 등급 코드 : " + arrayProdGradeCd[i]);
						this.log.debug("----------------------------------------------------------------");

						throw new StorePlatformException("SAC_DSP_0003", (i + 1) + " 번째 prodGradeCd",
								arrayProdGradeCd[i]);
					}
				}
			}
		}

		int offset = 1; // default
		int count = 20; // default

		if (req.getOffset() != null) {
			offset = req.getOffset();
		}
		req.setOffset(offset); // set offset

		if (req.getCount() != null) {
			count = req.getCount();
		}
		count = offset + count - 1;
		req.setCount(count); // set count

		req.setOffset(req.getOffset() <= 0 ? 1 : req.getOffset());
		req.setCount(req.getCount() <= 0 ? 20 : req.getCount());

		// '+'로 연결 된 상품등급코드를 배열로 전달
		if (StringUtils.isNotEmpty(req.getProdGradeCd())) {
			String[] arrayProdGradeCd = req.getProdGradeCd().split("\\+");
			req.setArrayProdGradeCd(arrayProdGradeCd);
		}

		if (StringUtils.isEmpty(req.getOrderedBy())) {
			req.setOrderedBy(DisplayConstants.DP_SHOPPING_RECENT_DEFAULT_ORDERED_OPTION);
		}

		if (!DisplayConstants.DP_SHOPPING_RECENT_DEFAULT_ORDERED_OPTION.equals(req.getOrderedBy())
				&& !DisplayConstants.DP_SHOPPING_POPULAR_DEFAULT_ORDERED_OPTION.equals(req.getOrderedBy())) {
			throw new StorePlatformException("SAC_DSP_0003", "orderedBy", req.getOrderedBy());
		}

		String stdDt = "";
		if (req.getOrderedBy().equals(DisplayConstants.DP_SHOPPING_POPULAR_DEFAULT_ORDERED_OPTION)) {
			// 필수 파라미터 체크
			req.setListId(DisplayConstants.DP_LIST_CATEGORY_SHOPPING_POPULAR);

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
		reqMap.put("channelContentTypeCd", DisplayConstants.DP_CHANNEL_CONTENT_TYPE_CD);

		List<Product> productList = new ArrayList<Product>();
		if (!this.commonSupportDeviceShopping(header)) {
			// 조회 결과 없음
			commonResponse.setTotalCount(0);
			res.setProductList(productList);
			res.setCommonResponse(commonResponse);
			return res;
		}

		// ID list 조회
		List<ProductBasicInfo> productBasicInfoList = this.commonDAO.queryForList("Shopping.getSubProductList", reqMap,
				ProductBasicInfo.class);
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
	 * 특가 상품 리스트 조회.
	 * 
	 * @param header
	 *            header
	 * @param req
	 *            req
	 * @return ShoppingRes
	 */
	@Override
	public ShoppingRes getSpecialPriceProductList(SacRequestHeader header, ShoppingReq req) {
		ShoppingRes res = new ShoppingRes();
		CommonResponse commonResponse = new CommonResponse();
		TenantHeader tenantHeader = header.getTenantHeader();
		DeviceHeader deviceHeader = header.getDeviceHeader();
		req.setTenantId(tenantHeader.getTenantId());
		req.setDeviceModelCd(deviceHeader.getModel());
		req.setLangCd(tenantHeader.getLangCd());
		req.setImageCd(DisplayConstants.DP_SHOPPING_REPRESENT_IMAGE_CD);
		req.setVirtualDeviceModelNo(DisplayConstants.DP_ANY_PHONE_4MM);

		List<MetaInfo> resultList = null;
		MetaInfo shopping = null;
		if (StringUtils.isEmpty(req.getProdCharge())) {
			req.setProdCharge(null);
		}
		// 상품등급코드 유효값 체크
		if (!this.commonProdGradeCd(req)) {
			throw new StorePlatformException("SAC_DSP_0003", "prodGradeCd", req.getProdGradeCd());
		}
		// offset, Count default setting
		this.commonOffsetCount(req);

		List<Product> productList = new ArrayList<Product>();
		if (!this.commonSupportDeviceShopping(header)) {
			// 조회 결과 없음
			commonResponse.setTotalCount(0);
			res.setProductList(productList);
			res.setCommonResponse(commonResponse);
			return res;
		}

		Integer totalCount = 0;
		resultList = this.commonDAO.queryForList("Shopping.getSpecialPriceProductList", req, MetaInfo.class);

		if (resultList != null) {
			shopping = new MetaInfo();

			// Response VO를 만들기위한 생성자
			Product product = null;

			for (int i = 0; i < resultList.size(); i++) {
				shopping = resultList.get(i);
				product = new Product();
				// 특가 상품 정보 (상품ID)
				product.setIdentifierList(this.commonGenerator.generateIdentifierList(shopping));
				// Title 생성
				Title title = this.commonGenerator.generateTitle(shopping);
				// Price 생성
				Price price = this.shoppingGenerator.generatePrice(shopping);
				// MenuList 생성
				List<Menu> menuList = this.commonGenerator.generateMenuList(shopping);
				// SourceList 생성
				List<Source> sourceList = this.commonGenerator.generateSourceList(shopping);
				// Accrual 생성
				Accrual accrual = this.shoppingGenerator.generateAccrual(shopping);
				// Rights 생성
				Rights rights = this.shoppingGenerator.generateRights(shopping);
				// Shopping용 Contributor 생성
				Contributor contributor = this.shoppingGenerator.generateContributor(shopping);
				// SalesOption 생성
				SalesOption salesOption = this.shoppingGenerator.generateSalesOption(shopping);

				// Tstore멤버십 적립율 정보
				MileageInfo mileageInfo = this.benefitService.getMileageInfo(req.getTenantId(),
						shopping.getTopMenuId(), shopping.getProdId(), shopping.getProdAmt());
				List<Point> pointList = this.commonGenerator.generateMileage(mileageInfo);

				// 데이터 매핑
				product.setMenuList(menuList);
				product.setTitle(title);
				product.setPrice(price);
				product.setSourceList(sourceList);
				product.setAccrual(accrual);
				product.setRights(rights);
				product.setContributor(contributor);
				product.setSalesOption(salesOption);
				product.setSpecialProdYn(shopping.getSpecialSale()); // 특가 상품 일 경우
				product.setPointList(pointList); // Tstore멤버십 적립율
				totalCount = shopping.getTotalCount();
				productList.add(i, product);
			}

			res = new ShoppingRes();
			res.setProductList(productList);

			commonResponse.setTotalCount(totalCount);
			res.setCommonResponse(commonResponse);
		} else {
			// 조회 결과 없음
			commonResponse.setTotalCount(0);
			res.setProductList(productList);
			res.setCommonResponse(commonResponse);
		}
		return res;
	}

	/**
	 * 기획전 상품 조회.
	 * 
	 * @param header
	 *            header
	 * @param req
	 *            req
	 * @return ShoppingThemeRes
	 */
	@Override
	public ShoppingSpcialSaleRes getSpecialSalesList(SacRequestHeader header, ShoppingReq req) {
		// 공통 응답 변수 선언
		ShoppingSpcialSaleRes res = null;
		CommonResponse commonResponse = new CommonResponse();
		Integer totalCount = 0;
		List<MetaInfo> resultList = null;
		MetaInfo shopping = null;

		TenantHeader tenantHeader = header.getTenantHeader();
		DeviceHeader deviceHeader = header.getDeviceHeader();
		req.setTenantId(tenantHeader.getTenantId());
		req.setDeviceModelCd(deviceHeader.getModel());
		req.setVirtualDeviceModelNo(DisplayConstants.DP_ANY_PHONE_4MM);
		req.setLangCd(tenantHeader.getLangCd());
		req.setBannerImgCd(DisplayConstants.DP_SHOPPING_SPECIAL_BANNER_IMAGE_CD);
		req.setPromotionImgCd(DisplayConstants.DP_SHOPPING_SPECIAL_PROMOTION_IMAGE_CD);
		// 필수 파라미터 체크
		if (StringUtils.isEmpty(req.getTenantId())) {
			throw new StorePlatformException("SAC_DSP_0002", "tenantId", req.getTenantId());
		}

		if (StringUtils.isEmpty(req.getOrderedBy())) {
			req.setOrderedBy(DisplayConstants.DP_SHOPPING_RECENT_DEFAULT_ORDERED_OPTION);
		}
		if (!DisplayConstants.DP_SHOPPING_RECENT_DEFAULT_ORDERED_OPTION.equals(req.getOrderedBy())
				&& !DisplayConstants.DP_SHOPPING_CLOSINGTIME_DEFAULT_ORDERED_OPTION.equals(req.getOrderedBy())) {
			throw new StorePlatformException("SAC_DSP_0003", "orderedBy", req.getOrderedBy());
		}
		// offset, Count default setting
		this.commonOffsetCount(req);

		resultList = this.commonDAO.queryForList("Shopping.getSpecialSalesList", req, MetaInfo.class);

		if (resultList != null) {
			shopping = new MetaInfo();

			// Response VO를 만들기위한 생성자
			Promotion promotion = null;
			List<Promotion> promotionList = new ArrayList<Promotion>();

			for (int i = 0; i < resultList.size(); i++) {
				shopping = resultList.get(i);

				// 상품 정보 (상품ID)
				promotion = new Promotion();
				List<Identifier> identifierList = new ArrayList<Identifier>();
				identifierList.add(this.commonGenerator.generateIdentifier(DisplayConstants.DP_PROMOTION_IDENTIFIER_CD,
						shopping.getPlanId()));

				// 상품 정보 (상품명)
				Title title = this.commonGenerator.generateTitle(shopping);
				// 이미지 정보
				List<Source> sourceList = this.shoppingGenerator.generateSpecialSalesSourceList(shopping);

				Date date = new Date();
				date.setText(DateUtils.parseDate(shopping.getPlanStartDt()),
						DateUtils.parseDate(shopping.getPlanEndDt()));
				// 데이터 매핑
				promotion.setIdentifierList(identifierList);
				promotion.setTitle(title);
				promotion.setPromotionExplain(shopping.getSubTitlNm());
				promotion.setUsagePeriod(date.getText());
				promotion.setSourceList(sourceList);

				Date date1 = new Date();
				date1.setTextFromDate(DateUtils.parseDate(shopping.getPrzwnerAnnoDt()));
				promotion.setReleaseDate(date1.getText());
				promotion.setGiveaway(shopping.getPlanGiftNm());
				promotionList.add(i, promotion);
				totalCount = shopping.getTotalCount();
			}

			res = new ShoppingSpcialSaleRes();
			res.setPromotionList(promotionList);

			commonResponse.setTotalCount(totalCount);
			res.setCommonResponse(commonResponse);
		} else {
			// 조회 결과 없음
			res = new ShoppingSpcialSaleRes();
			List<Promotion> promotionList = new ArrayList<Promotion>();
			res.setPromotionList(promotionList);
			commonResponse.setTotalCount(0);
			res.setCommonResponse(commonResponse);
		}
		return res;
	}

	/**
	 * 특정 기획전에 대한 상품 리스트 조회.
	 * 
	 * @param header
	 *            header
	 * @param req
	 *            req
	 * @return ShoppingThemeRes
	 */
	@Override
	public ShoppingThemeRes getSpecialSalesProductList(SacRequestHeader header, ShoppingPlanReq req) {
		// 공통 응답 변수 선언
		ShoppingThemeRes res = new ShoppingThemeRes();
		CommonResponse commonResponse = new CommonResponse();
		TenantHeader tenantHeader = header.getTenantHeader();
		DeviceHeader deviceHeader = header.getDeviceHeader();

		req.setTenantId(tenantHeader.getTenantId());
		req.setImageCd(DisplayConstants.DP_SHOPPING_REPRESENT_IMAGE_CD);
		req.setBannerImgCd(DisplayConstants.DP_SHOPPING_SPECIAL_BANNER_IMAGE_CD);
		req.setPromotionImgCd(DisplayConstants.DP_SHOPPING_SPECIAL_PROMOTION_IMAGE_CD);
		req.setDeviceModelCd(deviceHeader.getModel());
		req.setVirtualDeviceModelNo(DisplayConstants.DP_ANY_PHONE_4MM);
		req.setLangCd(tenantHeader.getLangCd());
		List<MetaInfo> resultList = null;
		MetaInfo shopping = null;

		if (StringUtils.isEmpty(req.getPlanId())) {
			throw new StorePlatformException("SAC_DSP_0002", "planId", req.getPlanId());
		}
		if (StringUtils.isEmpty(req.getProdCharge())) {
			req.setProdCharge(null);
		}
		// 파라미터 유효값 체크
		if (StringUtils.isNotEmpty(req.getProdGradeCd())) {
			String[] arrayProdGradeCd = req.getProdGradeCd().split("\\+");
			for (int i = 0; i < arrayProdGradeCd.length; i++) {
				if (StringUtils.isNotEmpty(arrayProdGradeCd[i])) {
					if (!"PD004401".equals(arrayProdGradeCd[i]) && !"PD004402".equals(arrayProdGradeCd[i])
							&& !"PD004403".equals(arrayProdGradeCd[i])) {
						this.log.debug("----------------------------------------------------------------");
						this.log.debug("유효하지않은 상품 등급 코드 : " + arrayProdGradeCd[i]);
						this.log.debug("----------------------------------------------------------------");

						throw new StorePlatformException("SAC_DSP_0003", (i + 1) + " 번째 prodGradeCd",
								arrayProdGradeCd[i]);
					}
				}
			}
		}

		int offset = 1; // default
		int count = 20; // default

		if (req.getOffset() != null) {
			offset = req.getOffset();
		}
		req.setOffset(offset); // set offset

		if (req.getCount() != null) {
			count = req.getCount();
		}
		count = offset + count - 1;
		req.setCount(count); // set count

		req.setOffset(req.getOffset() <= 0 ? 1 : req.getOffset());
		req.setCount(req.getCount() <= 0 ? 20 : req.getCount());

		// '+'로 연결 된 상품등급코드를 배열로 전달
		if (StringUtils.isNotEmpty(req.getProdGradeCd())) {
			String[] arrayProdGradeCd = req.getProdGradeCd().split("\\+");
			req.setArrayProdGradeCd(arrayProdGradeCd);
		}
		// 프로모션 리스트 가져오기
		resultList = this.commonDAO.queryForList("Shopping.getSpecialSalesList", req, MetaInfo.class);

		if (resultList != null) {
			shopping = new MetaInfo();

			// Response VO를 만들기위한 생성자
			Promotion promotion = null;
			Date date = null;
			List<Promotion> promotionList = new ArrayList<Promotion>();

			for (int i = 0; i < resultList.size(); i++) {
				shopping = resultList.get(i);

				// 상품 정보 (상품ID)
				promotion = new Promotion();
				List<Identifier> identifierList = new ArrayList<Identifier>();
				identifierList.add(this.commonGenerator.generateIdentifier(DisplayConstants.DP_PROMOTION_IDENTIFIER_CD,
						shopping.getPlanId()));

				// 상품 정보 (상품명)
				Title title = this.commonGenerator.generateTitle(shopping);
				// 이미지 정보
				List<Source> sourceList = this.shoppingGenerator.generateSpecialSalesSourceList(shopping);

				date = new Date();
				date.setText(DateUtils.parseDate(shopping.getPlanStartDt()),
						DateUtils.parseDate(shopping.getPlanEndDt()));
				// 데이터 매핑
				promotion.setIdentifierList(identifierList);
				promotion.setTitle(title);
				promotion.setPromotionExplain(shopping.getSubTitlNm());
				promotion.setUsagePeriod(date.getText());
				promotion.setSourceList(sourceList);
				Date date1 = new Date();
				date1.setTextFromDate(DateUtils.parseDate(shopping.getPrzwnerAnnoDt()));
				promotion.setReleaseDate(date1.getText());
				promotion.setGiveaway(shopping.getPlanGiftNm());
				promotionList.add(i, promotion);
			}
			res.setPromotionList(promotionList);
		}

		List<Product> productList = new ArrayList<Product>();
		if (resultList != null) {
			if (resultList.size() > 0) {
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
				reqMap.put("channelContentTypeCd", DisplayConstants.DP_CHANNEL_CONTENT_TYPE_CD);

				if (!this.commonSupportDeviceShopping(header)) {
					// 조회 결과 없음
					commonResponse.setTotalCount(0);
					res.setProductList(productList);
					res.setCommonResponse(commonResponse);
					return res;
				}
				// ID list 조회
				List<ProductBasicInfo> productBasicInfoList = this.commonDAO.queryForList(
						"Shopping.getSpecialSalesProductList", reqMap, ProductBasicInfo.class);
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
	 * 브랜드샵 - 메인 리스트 조회.
	 * 
	 * @param header
	 *            header
	 * @param req
	 *            req
	 * @return ShoppingRes
	 */
	@Override
	public ShoppingRes getBrandshopMainList(SacRequestHeader header, ShoppingReq req) {
		// 공통 응답 변수 선언
		ShoppingRes res = null;
		CommonResponse commonResponse = new CommonResponse();
		boolean menuIdFlag = false;
		List<MetaInfo> resultList = null;
		List<MetaInfo> hotBrandList = null;
		List<MetaInfo> detailList = null;
		List<MetaInfo> menuBrandList = null;

		TenantHeader tenantHeader = header.getTenantHeader();
		DeviceHeader deviceHeader = header.getDeviceHeader();
		req.setTenantId(tenantHeader.getTenantId());
		req.setDeviceModelCd(deviceHeader.getModel());
		req.setLangCd(tenantHeader.getLangCd());
		req.setImageCd(DisplayConstants.DP_SHOPPING_BRAND_REPRESENT_IMAGE_CD);
		req.setVirtualDeviceModelNo(DisplayConstants.DP_ANY_PHONE_4MM);

		// 필수 파라미터 체크
		if (StringUtils.isEmpty(header.getTenantHeader().getTenantId())) {
			throw new StorePlatformException("SAC_DSP_0002", "tenantId", req.getTenantId());
		}
		// offset, Count default setting
		this.commonOffsetCount(req);
		resultList = new ArrayList<MetaInfo>();

		if (StringUtils.isEmpty(req.getBrandType())) {
			req.setBrandType(DisplayConstants.DP_VOD_QUALITY_NORMAL);
		}

		if (!DisplayConstants.DP_VOD_QUALITY_NORMAL.equals(req.getBrandType())
				&& !DisplayConstants.DP_SHOPPING_BRAND_HOT.equals(req.getBrandType())) {
			throw new StorePlatformException("SAC_DSP_0003", "brandType", req.getBrandType());
		}

		if (!StringUtils.isEmpty(req.getMenuId())) {
			menuIdFlag = true;
			resultList = this.commonDAO.queryForList("Shopping.getBrandshopMainList", req, MetaInfo.class);
		} else {
			if (req.getBrandType().equals(DisplayConstants.DP_SHOPPING_BRAND_HOT)) {
				req.setMenuId(null);
				hotBrandList = this.commonDAO.queryForList("Shopping.getBrandshopMainList", req, MetaInfo.class);
				for (MetaInfo hotBrandShopping : hotBrandList) {
					resultList.add(hotBrandShopping);
				}
			} else {
				menuBrandList = this.commonDAO.queryForList("Shopping.getShoppingBrandMenuList", req, MetaInfo.class);
				for (int i = 0; i < menuBrandList.size(); i++) {
					req.setMenuId(menuBrandList.get(i).getMenuId());
					detailList = this.commonDAO.queryForList("Shopping.getBrandshopMainList", req, MetaInfo.class);
					for (MetaInfo detailShopping : detailList) {
						resultList.add(detailShopping);
					}
				}
			}
		}

		if (resultList != null) {
			MetaInfo shopping = null;
			shopping = new MetaInfo();

			// Response VO를 만들기위한 생성자
			Product product = null;
			List<Identifier> identifierList = null;

			List<Product> productList = new ArrayList<Product>();

			for (int i = 0; i < resultList.size(); i++) {
				shopping = resultList.get(i);

				// 상품 정보 (상품ID)
				product = new Product();
				identifierList = new ArrayList<Identifier>();
				identifierList.add(this.commonGenerator.generateIdentifier(DisplayConstants.DP_BRAND_IDENTIFIER_CD,
						shopping.getBrandId()));

				// MenuList 생성
				List<Menu> menuList = this.commonGenerator.generateMenuList(shopping);

				// 상품 정보 (상품명)
				Title title = this.commonGenerator.generateTitle(shopping);

				// SourceList 생성
				List<Source> sourceList = this.commonGenerator.generateSourceList(shopping);

				// Tstore멤버십 적립율 정보
				MileageInfo mileageInfo = this.benefitService.getMileageInfo(req.getTenantId(),
						shopping.getTopMenuId(), shopping.getProdId(), shopping.getProdAmt());
				List<Point> pointList = this.commonGenerator.generateMileage(mileageInfo);

				// 데이터 매핑
				product.setIdentifierList(identifierList);
				product.setMenuList(menuList);
				product.setTitle(title);
				product.setSourceList(sourceList);
				product.setPointList(pointList); // Tstore멤버십 적립율

				productList.add(i, product);
				commonResponse.setTotalCount(shopping.getTotalCount());
			}

			res = new ShoppingRes();
			res.setProductList(productList);
			if (!DisplayConstants.DP_SHOPPING_BRAND_HOT.equals(req.getBrandType())) {
				if (!menuIdFlag) {
					commonResponse.setTotalCount(resultList.size());
				}
			}

			res.setCommonResponse(commonResponse);
		} else {
			// 조회 결과 없음
			res = new ShoppingRes();
			List<Product> productList = new ArrayList<Product>();
			res.setProductList(productList);
			commonResponse.setTotalCount(0);
			res.setCommonResponse(commonResponse);
		}
		return res;
	}

	/**
	 * 특정 브랜드샵 상품 리스트.
	 * 
	 * @param header
	 *            header
	 * @param req
	 *            req
	 * @return ShoppingRes
	 */
	@Override
	public ShoppingBrandRes getBrandshopProductList(SacRequestHeader header, ShoppingBrandReq req) {
		// 공통 응답 변수 선언
		ShoppingBrandRes res = new ShoppingBrandRes();
		List<MetaInfo> resultList = null;
		MetaInfo shopping = null;
		CommonResponse commonResponse = new CommonResponse();
		TenantHeader tenantHeader = header.getTenantHeader();
		DeviceHeader deviceHeader = header.getDeviceHeader();
		req.setTenantId(tenantHeader.getTenantId());
		req.setDeviceModelCd(deviceHeader.getModel());
		req.setLangCd(tenantHeader.getLangCd());
		req.setImageCd(DisplayConstants.DP_SHOPPING_REPRESENT_IMAGE_CD);
		req.setVirtualDeviceModelNo(DisplayConstants.DP_ANY_PHONE_4MM);

		if (StringUtils.isEmpty(req.getBrandId())) {
			throw new StorePlatformException("SAC_DSP_0002", "blandId", req.getBrandId());
		}
		if (StringUtils.isEmpty(req.getProdCharge())) {
			req.setProdCharge(null);
		}
		if (StringUtils.isEmpty(req.getOrderedBy())) {
			req.setOrderedBy(DisplayConstants.DP_SHOPPING_RECENT_DEFAULT_ORDERED_OPTION);
		}
		if (!DisplayConstants.DP_SHOPPING_RECENT_DEFAULT_ORDERED_OPTION.equals(req.getOrderedBy())
				&& !DisplayConstants.DP_SHOPPING_POPULAR_DEFAULT_ORDERED_OPTION.equals(req.getOrderedBy())) {
			throw new StorePlatformException("SAC_DSP_0003", "orderedBy", req.getOrderedBy());
		}
		String stdDt = "";
		if (req.getOrderedBy().equals(DisplayConstants.DP_SHOPPING_POPULAR_DEFAULT_ORDERED_OPTION)) {

			req.setListId(DisplayConstants.DP_LIST_CATEGORY_SHOPPING_POPULAR);

			stdDt = this.displayCommonService.getBatchStandardDateString(header.getTenantHeader().getTenantId(),
					req.getListId());

			// 기준일시 체크
			if (StringUtils.isEmpty(stdDt)) {
				throw new StorePlatformException("SAC_DSP_0003", "stdDt", stdDt);
			}
			req.setStdDt(stdDt);
		}

		// 파라미터 유효값 체크
		if (StringUtils.isNotEmpty(req.getProdGradeCd())) {
			String[] arrayProdGradeCd = req.getProdGradeCd().split("\\+");
			for (int i = 0; i < arrayProdGradeCd.length; i++) {
				if (StringUtils.isNotEmpty(arrayProdGradeCd[i])) {
					if (!"PD004401".equals(arrayProdGradeCd[i]) && !"PD004402".equals(arrayProdGradeCd[i])
							&& !"PD004403".equals(arrayProdGradeCd[i])) {
						this.log.debug("----------------------------------------------------------------");
						this.log.debug("유효하지않은 상품 등급 코드 : " + arrayProdGradeCd[i]);
						this.log.debug("----------------------------------------------------------------");

						throw new StorePlatformException("SAC_DSP_0003", (i + 1) + " 번째 prodGradeCd",
								arrayProdGradeCd[i]);
					}
				}
			}
		}

		int offset = 1; // default
		int count = 20; // default

		if (req.getOffset() != null) {
			offset = req.getOffset();
		}
		req.setOffset(offset); // set offset

		if (req.getCount() != null) {
			count = req.getCount();
		}
		count = offset + count - 1;
		req.setCount(count); // set count

		req.setOffset(req.getOffset() <= 0 ? 1 : req.getOffset());
		req.setCount(req.getCount() <= 0 ? 20 : req.getCount());

		// '+'로 연결 된 상품등급코드를 배열로 전달
		if (StringUtils.isNotEmpty(req.getProdGradeCd())) {
			String[] arrayProdGradeCd = req.getProdGradeCd().split("\\+");
			req.setArrayProdGradeCd(arrayProdGradeCd);
		}

		// 브랜드샵 정보 가져오기
		resultList = this.commonDAO.queryForList("Shopping.getBrandshopMainList", req, MetaInfo.class);

		if (resultList != null) {
			shopping = new MetaInfo();

			// Response VO를 만들기위한 생성자
			Layout layOut = null;

			for (int i = 0; i < resultList.size(); i++) {
				shopping = resultList.get(i);
				layOut = new Layout();
				// 상품 정보 (상품명)
				Title title = this.commonGenerator.generateTitle(shopping);
				// 메뉴정보
				List<Menu> menuList = this.commonGenerator.generateMenuList(shopping);

				layOut.setTitle(title);
				layOut.setMenuList(menuList);
			}

			res.setLayOut(layOut);
		}

		List<Product> productList = new ArrayList<Product>();
		if (resultList != null) {
			if (resultList.size() > 0) {
				// DB 조회 파라미터 생성
				Map<String, Object> reqMap = new HashMap<String, Object>();
				reqMap.put("req", req);
				reqMap.put("stdDt", stdDt);
				reqMap.put("tenantHeader", tenantHeader);
				reqMap.put("deviceHeader", deviceHeader);
				reqMap.put("lang", tenantHeader.getLangCd());

				reqMap.put("imageCd", req.getImageCd());
				reqMap.put("svcGrpCd", DisplayConstants.DP_TSTORE_SHOPPING_PROD_SVC_GRP_CD);
				reqMap.put("contentTypeCd", DisplayConstants.DP_CHANNEL_CONTENT_TYPE_CD);
				reqMap.put("prodStatusCd", DisplayConstants.DP_SALE_STAT_ING);
				reqMap.put("prodRshpCd", DisplayConstants.DP_CHANNEL_EPISHODE_RELATIONSHIP_CD);
				reqMap.put("channelContentTypeCd", DisplayConstants.DP_CHANNEL_CONTENT_TYPE_CD);

				if (!this.commonSupportDeviceShopping(header)) {
					// 조회 결과 없음
					commonResponse.setTotalCount(0);
					res.setProductList(productList);
					res.setCommonResponse(commonResponse);
					return res;
				}
				List<ProductBasicInfo> productBasicInfoList = this.commonDAO.queryForList(
						"Shopping.getBrandshopProductList", reqMap, ProductBasicInfo.class);
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
	 * 쇼핑테마 리스트상품 리스트.
	 * 
	 * @param header
	 *            header
	 * @param req
	 *            req
	 * @return ShoppingRes
	 */
	@Override
	public ShoppingRes getThemeList(SacRequestHeader header, ShoppingReq req) {
		// 공통 응답 변수 선언
		ShoppingRes res = null;
		CommonResponse commonResponse = new CommonResponse();
		Integer totalCount = 0;
		List<MetaInfo> resultList = null;

		TenantHeader tenantHeader = header.getTenantHeader();
		DeviceHeader deviceHeader = header.getDeviceHeader();
		req.setTenantId(tenantHeader.getTenantId());
		req.setDeviceModelCd(deviceHeader.getModel());
		req.setLangCd(tenantHeader.getLangCd());
		req.setImageCd(DisplayConstants.DP_SHOPPING_THEME_REPRESENT_IMAGE_CD);

		// 필수 파라미터 체크
		if (StringUtils.isEmpty(header.getTenantHeader().getTenantId())) {
			throw new StorePlatformException("SAC_DSP_0002", "tenantId", req.getTenantId());
		}

		// offset, Count default setting
		this.commonOffsetCount(req);

		resultList = this.commonDAO.queryForList("Shopping.getThemeList", req, MetaInfo.class);

		if (resultList != null) {
			MetaInfo shopping = null;
			shopping = new MetaInfo();

			// Response VO를 만들기위한 생성자
			Product product = null;
			List<Identifier> identifierList = null;
			List<Product> productList = new ArrayList<Product>();

			for (int i = 0; i < resultList.size(); i++) {
				shopping = resultList.get(i);

				// 상품 정보 (상품ID)
				product = new Product();
				identifierList = new ArrayList<Identifier>();
				identifierList.add(this.commonGenerator.generateIdentifier(DisplayConstants.DP_THEME_IDENTIFIER_CD,
						shopping.getProdId()));

				// 상품 정보 (상품명)
				Title title = this.commonGenerator.generateTitle(shopping);

				// SourceList 생성
				List<Source> sourceList = this.commonGenerator.generateSourceList(shopping);

				// 데이터 매핑
				product.setIdentifierList(identifierList);
				product.setTitle(title);
				product.setSourceList(sourceList);
				productList.add(i, product);
				totalCount = shopping.getTotalCount();
			}

			res = new ShoppingRes();
			res.setProductList(productList);

			commonResponse.setTotalCount(totalCount);
			res.setCommonResponse(commonResponse);
		} else {
			// 조회 결과 없음
			res = new ShoppingRes();
			List<Product> productList = new ArrayList<Product>();
			res.setProductList(productList);
			commonResponse.setTotalCount(0);
			res.setCommonResponse(commonResponse);
		}
		return res;
	}

	/**
	 * 특정 테마 리스트상품 리스트.
	 * 
	 * @param header
	 *            header
	 * @param req
	 *            req
	 * @return ShoppingRes
	 */
	@Override
	public ShoppingBrandRes getThemeProductList(SacRequestHeader header, ShoppingThemeReq req) {
		// 공통 응답 변수 선언
		ShoppingBrandRes res = new ShoppingBrandRes();
		List<MetaInfo> resultList = null;

		CommonResponse commonResponse = new CommonResponse();
		TenantHeader tenantHeader = header.getTenantHeader();
		DeviceHeader deviceHeader = header.getDeviceHeader();
		req.setTenantId(tenantHeader.getTenantId());
		req.setImageCd(DisplayConstants.DP_SHOPPING_REPRESENT_IMAGE_CD);
		req.setVirtualDeviceModelNo(DisplayConstants.DP_ANY_PHONE_4MM);

		if (StringUtils.isEmpty(req.getThemeId())) {
			throw new StorePlatformException("SAC_DSP_0002", "themeId", req.getThemeId());
		}
		if (StringUtils.isEmpty(req.getProdCharge())) {
			req.setProdCharge(null);
		}
		// 파라미터 유효값 체크
		if (StringUtils.isNotEmpty(req.getProdGradeCd())) {
			String[] arrayProdGradeCd = req.getProdGradeCd().split("\\+");
			for (int i = 0; i < arrayProdGradeCd.length; i++) {
				if (StringUtils.isNotEmpty(arrayProdGradeCd[i])) {
					if (!"PD004401".equals(arrayProdGradeCd[i]) && !"PD004402".equals(arrayProdGradeCd[i])
							&& !"PD004403".equals(arrayProdGradeCd[i])) {
						this.log.debug("----------------------------------------------------------------");
						this.log.debug("유효하지않은 상품 등급 코드 : " + arrayProdGradeCd[i]);
						this.log.debug("----------------------------------------------------------------");

						throw new StorePlatformException("SAC_DSP_0003", (i + 1) + " 번째 prodGradeCd",
								arrayProdGradeCd[i]);
					}
				}
			}
		}

		int offset = 1; // default
		int count = 20; // default

		if (req.getOffset() != null) {
			offset = req.getOffset();
		}
		req.setOffset(offset); // set offset

		if (req.getCount() != null) {
			count = req.getCount();
		}
		count = offset + count - 1;
		req.setCount(count); // set count

		req.setOffset(req.getOffset() <= 0 ? 1 : req.getOffset());
		req.setCount(req.getCount() <= 0 ? 20 : req.getCount());

		// '+'로 연결 된 상품등급코드를 배열로 전달
		if (StringUtils.isNotEmpty(req.getProdGradeCd())) {
			String[] arrayProdGradeCd = req.getProdGradeCd().split("\\+");
			req.setArrayProdGradeCd(arrayProdGradeCd);
		}
		// 배치완료 기준일시 조회
		String stdDt = this.displayCommonService.getBatchStandardDateString(header.getTenantHeader().getTenantId(),
				req.getThemeId());

		// 기준일시 체크
		if (StringUtils.isEmpty(stdDt)) {
			throw new StorePlatformException("SAC_DSP_0003", "stdDt", stdDt);
		}
		// 테마 정보 가져오기
		resultList = this.commonDAO.queryForList("Shopping.getThemeList", req, MetaInfo.class);

		if (resultList != null) {
			MetaInfo shopping = null;
			shopping = new MetaInfo();

			// Response VO를 만들기위한 생성자
			Layout layOut = null;
			for (int i = 0; i < resultList.size(); i++) {
				shopping = resultList.get(i);
				layOut = new Layout();
				// 상품 정보 (상품명)
				Title title = this.commonGenerator.generateTitle(shopping);
				// 데이터 매핑
				layOut.setTitle(title);
			}

			res.setLayOut(layOut);
		}
		List<Product> productList = new ArrayList<Product>();
		if (resultList != null) {
			if (resultList.size() > 0) {
				// DB 조회 파라미터 생성
				Map<String, Object> reqMap = new HashMap<String, Object>();
				reqMap.put("req", req);
				reqMap.put("stdDt", stdDt);
				reqMap.put("tenantHeader", tenantHeader);
				reqMap.put("deviceHeader", deviceHeader);
				reqMap.put("lang", tenantHeader.getLangCd());

				reqMap.put("imageCd", req.getImageCd());
				reqMap.put("svcGrpCd", DisplayConstants.DP_TSTORE_SHOPPING_PROD_SVC_GRP_CD);
				reqMap.put("contentTypeCd", DisplayConstants.DP_CHANNEL_CONTENT_TYPE_CD);
				reqMap.put("prodStatusCd", DisplayConstants.DP_SALE_STAT_ING);
				reqMap.put("prodRshpCd", DisplayConstants.DP_CHANNEL_EPISHODE_RELATIONSHIP_CD);
				reqMap.put("channelContentTypeCd", DisplayConstants.DP_CHANNEL_CONTENT_TYPE_CD);

				if (!this.commonSupportDeviceShopping(header)) {
					// 조회 결과 없음
					commonResponse.setTotalCount(0);
					res.setProductList(productList);
					res.setCommonResponse(commonResponse);
					return res;
				}

				List<ProductBasicInfo> productBasicInfoList = this.commonDAO.queryForList(
						"Shopping.getThemeProductList", reqMap, ProductBasicInfo.class);
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
			} else {
				commonResponse.setTotalCount(0);
				res.setProductList(productList);
				res.setCommonResponse(commonResponse);
			}
		}
		return res;
	}

	/**
	 * 특정 카탈로그에 대한 다른 상품 리스트.
	 * 
	 * @param header
	 *            header
	 * @param req
	 *            req
	 * @return ShoppingRes
	 */
	@Override
	public ShoppingRes getCatagoryAnotherProductList(SacRequestHeader header, ShoppingCategoryAnotherReq req) {

		// 공통 응답 변수 선언
		ShoppingRes res = new ShoppingRes();
		CommonResponse commonResponse = new CommonResponse();
		TenantHeader tenantHeader = header.getTenantHeader();
		DeviceHeader deviceHeader = header.getDeviceHeader();

		req.setImageCd(DisplayConstants.DP_SHOPPING_REPRESENT_IMAGE_CD);
		req.setVirtualDeviceModelNo(DisplayConstants.DP_ANY_PHONE_4MM);

		if (StringUtils.isEmpty(req.getMenuId())) {
			throw new StorePlatformException("SAC_DSP_0002", "menuId", req.getMenuId());
		}
		if (StringUtils.isEmpty(req.getProdCharge())) {
			req.setProdCharge(null);
		}
		if (StringUtils.isEmpty(req.getExceptId())) {
			req.setExceptId(null);
		}
		// 파라미터 유효값 체크
		if (StringUtils.isNotEmpty(req.getProdGradeCd())) {
			String[] arrayProdGradeCd = req.getProdGradeCd().split("\\+");
			for (int i = 0; i < arrayProdGradeCd.length; i++) {
				if (StringUtils.isNotEmpty(arrayProdGradeCd[i])) {
					if (!"PD004401".equals(arrayProdGradeCd[i]) && !"PD004402".equals(arrayProdGradeCd[i])
							&& !"PD004403".equals(arrayProdGradeCd[i])) {
						this.log.debug("----------------------------------------------------------------");
						this.log.debug("유효하지않은 상품 등급 코드 : " + arrayProdGradeCd[i]);
						this.log.debug("----------------------------------------------------------------");

						throw new StorePlatformException("SAC_DSP_0003", (i + 1) + " 번째 prodGradeCd",
								arrayProdGradeCd[i]);
					}
				}
			}
		}

		int offset = 1; // default
		int count = 20; // default

		if (req.getOffset() != null) {
			offset = req.getOffset();
		}
		req.setOffset(offset); // set offset

		if (req.getCount() != null) {
			count = req.getCount();
		}
		count = offset + count - 1;
		req.setCount(count); // set count

		req.setOffset(req.getOffset() <= 0 ? 1 : req.getOffset());
		req.setCount(req.getCount() <= 0 ? 20 : req.getCount());

		// '+'로 연결 된 상품등급코드를 배열로 전달
		if (StringUtils.isNotEmpty(req.getProdGradeCd())) {
			String[] arrayProdGradeCd = req.getProdGradeCd().split("\\+");
			req.setArrayProdGradeCd(arrayProdGradeCd);
		}
		if (StringUtils.isEmpty(req.getOrderedBy())) {
			req.setOrderedBy(DisplayConstants.DP_SHOPPING_RECENT_DEFAULT_ORDERED_OPTION);
		}
		if (!DisplayConstants.DP_SHOPPING_RECENT_DEFAULT_ORDERED_OPTION.equals(req.getOrderedBy())
				&& !DisplayConstants.DP_SHOPPING_POPULAR_DEFAULT_ORDERED_OPTION.equals(req.getOrderedBy())) {
			throw new StorePlatformException("SAC_DSP_0003", "orderedBy", req.getOrderedBy());
		}

		String stdDt = "";
		if (req.getOrderedBy().equals(DisplayConstants.DP_SHOPPING_POPULAR_DEFAULT_ORDERED_OPTION)) {
			// 필수 파라미터 체크
			req.setListId(DisplayConstants.DP_LIST_CATEGORY_SHOPPING_POPULAR);

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
		reqMap.put("channelContentTypeCd", DisplayConstants.DP_CHANNEL_CONTENT_TYPE_CD);

		List<Product> productList = new ArrayList<Product>();
		if (!this.commonSupportDeviceShopping(header)) {
			// 조회 결과 없음
			commonResponse.setTotalCount(0);
			res.setProductList(productList);
			res.setCommonResponse(commonResponse);
			return res;
		}

		// ID list 조회
		List<ProductBasicInfo> productBasicInfoList = this.commonDAO.queryForList(
				"Shopping.getCatagoryAnotherProductList", reqMap, ProductBasicInfo.class);
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
	 * 특정 브랜드에 대한 다른 상품 리스트.
	 * 
	 * @param header
	 *            header
	 * @param req
	 *            req
	 * @return ShoppingRes
	 */
	@Override
	public ShoppingRes getBrandAnotherProductList(SacRequestHeader header, ShoppingBrandAnotherReq req) {

		// 공통 응답 변수 선언
		ShoppingRes res = new ShoppingRes();
		CommonResponse commonResponse = new CommonResponse();
		TenantHeader tenantHeader = header.getTenantHeader();
		DeviceHeader deviceHeader = header.getDeviceHeader();

		req.setImageCd(DisplayConstants.DP_SHOPPING_REPRESENT_IMAGE_CD);
		req.setVirtualDeviceModelNo(DisplayConstants.DP_ANY_PHONE_4MM);

		if (StringUtils.isEmpty(req.getBrandId())) {
			throw new StorePlatformException("SAC_DSP_0002", "brandId", req.getBrandId());
		}

		if (StringUtils.isEmpty(req.getExceptId())) {
			req.setExceptId(null);
		}

		if (StringUtils.isEmpty(req.getProdCharge())) {
			req.setProdCharge(null);
		}
		// 파라미터 유효값 체크
		if (StringUtils.isNotEmpty(req.getProdGradeCd())) {
			String[] arrayProdGradeCd = req.getProdGradeCd().split("\\+");
			for (int i = 0; i < arrayProdGradeCd.length; i++) {
				if (StringUtils.isNotEmpty(arrayProdGradeCd[i])) {
					if (!"PD004401".equals(arrayProdGradeCd[i]) && !"PD004402".equals(arrayProdGradeCd[i])
							&& !"PD004403".equals(arrayProdGradeCd[i])) {
						this.log.debug("----------------------------------------------------------------");
						this.log.debug("유효하지않은 상품 등급 코드 : " + arrayProdGradeCd[i]);
						this.log.debug("----------------------------------------------------------------");

						throw new StorePlatformException("SAC_DSP_0003", (i + 1) + " 번째 prodGradeCd",
								arrayProdGradeCd[i]);
					}
				}
			}
		}

		int offset = 1; // default
		int count = 20; // default

		if (req.getOffset() != null) {
			offset = req.getOffset();
		}
		req.setOffset(offset); // set offset

		if (req.getCount() != null) {
			count = req.getCount();
		}
		count = offset + count - 1;
		req.setCount(count); // set count

		req.setOffset(req.getOffset() <= 0 ? 1 : req.getOffset());
		req.setCount(req.getCount() <= 0 ? 20 : req.getCount());

		// '+'로 연결 된 상품등급코드를 배열로 전달
		if (StringUtils.isNotEmpty(req.getProdGradeCd())) {
			String[] arrayProdGradeCd = req.getProdGradeCd().split("\\+");
			req.setArrayProdGradeCd(arrayProdGradeCd);
		}

		if (StringUtils.isEmpty(req.getOrderedBy())) {
			req.setOrderedBy(DisplayConstants.DP_SHOPPING_RECENT_DEFAULT_ORDERED_OPTION);
		}

		if (!DisplayConstants.DP_SHOPPING_RECENT_DEFAULT_ORDERED_OPTION.equals(req.getOrderedBy())
				&& !DisplayConstants.DP_SHOPPING_POPULAR_DEFAULT_ORDERED_OPTION.equals(req.getOrderedBy())) {
			throw new StorePlatformException("SAC_DSP_0003", "orderedBy", req.getOrderedBy());
		}

		String stdDt = "";
		if (req.getOrderedBy().equals(DisplayConstants.DP_SHOPPING_POPULAR_DEFAULT_ORDERED_OPTION)) {
			// 필수 파라미터 체크
			req.setListId(DisplayConstants.DP_LIST_CATEGORY_SHOPPING_POPULAR);

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
		reqMap.put("channelContentTypeCd", DisplayConstants.DP_CHANNEL_CONTENT_TYPE_CD);

		List<Product> productList = new ArrayList<Product>();
		if (!this.commonSupportDeviceShopping(header)) {
			// 조회 결과 없음
			commonResponse.setTotalCount(0);
			res.setProductList(productList);
			res.setCommonResponse(commonResponse);
			return res;
		}

		// ID list 조회
		List<ProductBasicInfo> productBasicInfoList = this.commonDAO.queryForList(
				"Shopping.getBrandAnotherProductList", reqMap, ProductBasicInfo.class);
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
	 * 쇼핑상세.
	 * 
	 * @param header
	 *            header
	 * @param req
	 *            req
	 * @return ShoppingRes
	 */
	@Override
	public ShoppingDetailRes getShoppingDetail(SacRequestHeader header, ShoppingDetailReq req) {
		// 공통 응답 변수 선언
		ShoppingDetailRes res = new ShoppingDetailRes();
		TenantHeader tenantHeader = header.getTenantHeader();
		DeviceHeader deviceHeader = header.getDeviceHeader();

		req.setImageCd(DisplayConstants.DP_SHOPPING_REPRESENT_IMAGE_CD);
		req.setVirtualDeviceModelNo(DisplayConstants.DP_ANY_PHONE_4MM);
		req.setProdRshpCd(DisplayConstants.DP_CHANNEL_EPISHODE_RELATIONSHIP_CD);
		// 필수 파라미터 체크

		if (!DisplayConstants.DP_CATALOG_IDENTIFIER_CD.equals(req.getType())
				&& !DisplayConstants.DP_EPISODE_IDENTIFIER_CD.equals(req.getType())) {
			throw new StorePlatformException("SAC_DSP_0003", "type", req.getType());
		}

		if (StringUtils.isEmpty(req.getProductId())) {
			throw new StorePlatformException("SAC_DSP_0002", "productId", req.getProductId());
		}
		if (StringUtils.isEmpty(req.getSpecialProdId())) { // 특가 상품이 아닌경우
			if (DisplayConstants.DP_EPISODE_IDENTIFIER_CD.equals(req.getType())) {
				MetaInfo channelByEpisode = this.commonDAO.queryForObject("Shopping.getChannelByepisode", req,
						MetaInfo.class);
				if (channelByEpisode != null) {
					req.setSpecialProdId(req.getProductId());
					req.setProductId(channelByEpisode.getCatalogId());
					req.setSpecialType(DisplayConstants.DP_EPISODE_IDENTIFIER_CD);
				} else {
					throw new StorePlatformException("SAC_DSP_0009");
				}
			}
		}

		// OPTIONAL 파라미터 체크
		if (StringUtils.isEmpty(req.getSpecialProdId())) {
			req.setSpecialProdId(null);
		}
		if (StringUtils.isNotEmpty(req.getSpecialType())) {
			if (!DisplayConstants.DP_EPISODE_IDENTIFIER_CD.equals(req.getSpecialType())
					&& !DisplayConstants.DP_CHANNEL_IDENTIFIER_CD.equals(req.getSpecialType())) {
				throw new StorePlatformException("SAC_DSP_0003", "specialType", req.getSpecialType());
			}
		}
		// OPTIONAL 파라미터 체크
		if (StringUtils.isEmpty(req.getSpecialType())) {
			req.setSpecialType(DisplayConstants.DP_EPISODE_IDENTIFIER_CD);
		}

		if (StringUtils.isEmpty(req.getUserKey())) {
			req.setUserKey(null);
		}
		if (StringUtils.isEmpty(req.getDeviceKey())) {
			req.setDeviceKey(null);
		}
		if (StringUtils.isNotEmpty(req.getSaleDtUseYn())) {
			if (!"Y".equals(req.getSaleDtUseYn()) && !"N".equals(req.getSaleDtUseYn())) {
				throw new StorePlatformException("SAC_DSP_0003", "saleDtUseYn", req.getSaleDtUseYn());
			}
			if ("N".equals(req.getSaleDtUseYn())) {
				req.setSaleDtUseYn(null);
			}
		}
		if (StringUtils.isEmpty(req.getSaleDtUseYn())) {
			req.setSaleDtUseYn(null);
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

		if (!this.commonSupportDeviceShopping(header)) {
			return res;
		}
		// ID list 조회
		List<MetaInfo> resultChannelList = this.commonDAO.queryForList("Shopping.getShoppingChannelDetail", reqMap,
				MetaInfo.class);

		if (resultChannelList == null) {
			throw new StorePlatformException("SAC_DSP_0009");
		} else {
			if (resultChannelList.size() > 0) {

				// Response VO를 만들기위한 생성자
				Product product = null;
				Source source = null;

				// / 에피소드용
				Product episodeProduct = null;
				List<Identifier> episodeIdentifierList = null;

				Rights episodeRights = null;
				Distributor distributor = null;
				SalesOption episodeSaleOption = null;

				// / 옵션용
				SelectOption selectOption = null;
				SubSelectOption subSelectOption = null;
				List<SelectOption> selectOptionList = new ArrayList<SelectOption>();
				List<SubSelectOption> subSelectOptionList = new ArrayList<SubSelectOption>();
				Title option2Title = null;
				List<Product> subProductList = new ArrayList<Product>();

				// 채널 list 조회
				for (int i = 0; i < 1; i++) {
					MetaInfo shopping = resultChannelList.get(i);

					product = new Product();

					// 상품 정보 (상품ID)
					product.setIdentifierList(this.commonGenerator.generateIdentifierList(shopping));

					// MenuList 생성
					List<Menu> menuList = this.commonGenerator.generateMenuList(shopping);

					// 티멤버십 DC 정보
					TmembershipDcInfo info = this.displayCommonService.getTmembershipDcRateForMenu(
							tenantHeader.getTenantId(), shopping.getTopMenuId());
					List<Point> pointList = this.commonGenerator.generatePoint(info);

					// Tstore멤버십 적립율 정보
					if (StringUtils.isNotEmpty(req.getUserKey())) {
						// 회원등급 조회
						GradeInfoSac userGradeInfo = this.commonService.getUserGrade(req.getUserKey());
						if (userGradeInfo != null) {
							if (pointList == null)
								pointList = new ArrayList<Point>();
							String userGrade = userGradeInfo.getUserGradeCd();
							MileageInfo mileageInfo = this.benefitService.getMileageInfo(tenantHeader.getTenantId(),
									shopping.getTopMenuId(), shopping.getProdId(), shopping.getProdAmt());
							pointList.addAll(this.commonGenerator.generateMileage(mileageInfo, userGrade));
						}
					}

					// Title 생성
					Title title = this.commonGenerator.generateTitle(shopping);

					// SourceList 생성
					List<Source> sourceList = this.commonGenerator.generateSourceList(shopping);
					// 이미지 정보
					String detailImgCd = "";
					// 이미지 정보 (상세 이미지 가져오기)
					for (int qq = 0; qq < 2; qq++) {
						if (qq == 0) {
							detailImgCd = DisplayConstants.DP_SHOPPING_REPRESENT_DETAIL_IMAGE_CD;
						} else {
							detailImgCd = DisplayConstants.DP_SHOPPING_REPRESENT_CUT_DETAIL_IMAGE_CD;
						}
						reqMap.put("cutDetailImageCd", detailImgCd);
						List<MetaInfo> resultImgDetailList = this.commonDAO.queryForList(
								"Shopping.getShoppingImgDetailList", reqMap, MetaInfo.class);
						for (int pp = 0; pp < resultImgDetailList.size(); pp++) {
							if (qq == 0) {
								source = this.commonGenerator.generateSource(DisplayConstants.DP_SOURCE_TYPE_ORIGINAL,
										resultImgDetailList.get(pp).getFilePath());
							} else {
								source = this.commonGenerator.generateSource(
										DisplayConstants.DP_SOURCE_TYPE_SCREENSHOT, resultImgDetailList.get(pp)
												.getFilePath());
							}
							sourceList.add(source);
						}
					}
					// Accrual 생성
					Accrual accrual = this.shoppingGenerator.generateAccrual(shopping);
					// Shopping용 Contributor 생성
					Contributor contributor = this.shoppingGenerator.generateContributor(shopping);

					String deliveryValue = shopping.getProdCaseCd();
					// 배송상품은 단품이며 기본정보는 동일
					if (deliveryValue.equals("delivery")) {
						reqMap.put("endRow", "1");
					}

					// 에피소드 list 조회
					List<MetaInfo> resultEpisodeList = this.commonDAO.queryForList("Shopping.getShoppingEpisodeDetail",
							reqMap, MetaInfo.class);
					if (resultEpisodeList != null) {
						for (int kk = 0; kk < resultEpisodeList.size(); kk++) {
							MetaInfo episodeShopping = resultEpisodeList.get(kk);

							episodeProduct = new Product();
							// 쿠폰코드 입력
							episodeProduct.setCouponCode(episodeShopping.getCouponCode());

							if (!deliveryValue.equals("delivery")) {
								// 아이템코드 입력
								episodeProduct.setItemCode(episodeShopping.getItemCode());
							}

							// 채널, 에피소드 상품 판매 상태 코드
							if (req.getSaleDtUseYn() != null) {
								// 만약 판매중이면 판매중지로 바꿔야함
								if (episodeShopping.getProdStatusCd() != null) {
									if (episodeShopping.getProdStatusCd().equals(DisplayConstants.DP_SALE_STAT_ING)) {
										episodeProduct.setSalesStatus(DisplayConstants.DP_SALE_STAT_STOP);
									} else {
										episodeProduct.setSalesStatus(episodeShopping.getProdStatusCd());
									}
								} else {
									episodeProduct.setSalesStatus(episodeShopping.getProdStatusCd());
								}
							} else {
								episodeProduct.setSalesStatus(episodeShopping.getProdStatusCd());
							}

							// 특가 상품일 경우
							episodeProduct.setSpecialProdYn(episodeShopping.getSpecialSale());

							// 특가 상품 쿠폰 ID 경우
							episodeProduct.setSpecialCouponId(episodeShopping.getSpecialCouponId());
							// Title 생성
							Title episodeTitle = this.commonGenerator.generateTitle(episodeShopping);
							episodeProduct.setTitle(episodeTitle);

							// 채널 상품 정보 (상품ID)
							episodeIdentifierList = new ArrayList<Identifier>();
							episodeIdentifierList.add(this.commonGenerator.generateIdentifier(
									DisplayConstants.DP_CHANNEL_IDENTIFIER_CD, episodeShopping.getProdId()));

							// 에피소드 상품 정보 (상품ID)
							episodeIdentifierList.add(this.commonGenerator.generateIdentifier(
									DisplayConstants.DP_EPISODE_IDENTIFIER_CD, episodeShopping.getPartProdId()));
							episodeProduct.setIdentifierList(episodeIdentifierList);

							// 에피소드 상품 가격 정보
							Price episodePrice = this.shoppingGenerator.generatePrice(episodeShopping);
							episodeProduct.setPrice(episodePrice);

							// 에피소드 구매내역 정보
							boolean purchaseFlag = true;
							String prchsId = null;
							String prchsDt = null;
							String prchsState = null;
							String permitDeviceYn = null;
							try {
								this.log.info("################ [SAC DP LocalSCI] SAC Purchase Stat : historyInternalSCI.searchHistoryList : "
										+ DateUtil.getToday("yyyy-MM-dd hh:mm:ss.SSS"));
								// 구매내역 조회를 위한 생성자
								ProductListSacIn productListSacIn = new ProductListSacIn();
								List<ProductListSacIn> productEpisodeList = new ArrayList<ProductListSacIn>();

								productListSacIn.setProdId(episodeShopping.getPartProdId());
								productEpisodeList.add(productListSacIn);

								HistoryListSacInReq historyListSacReq = new HistoryListSacInReq();
								historyListSacReq.setTenantId(tenantHeader.getTenantId());
								historyListSacReq.setUserKey(req.getUserKey());
								historyListSacReq.setDeviceKey(req.getDeviceKey());
								historyListSacReq.setPrchsProdHaveYn(DisplayConstants.PRCHS_PROD_HAVE_YES);
								historyListSacReq.setPrchsProdType(DisplayConstants.PRCHS_PROD_TYPE_UNIT);
								historyListSacReq.setStartDt(DisplayConstants.PRCHS_START_DATE);
								historyListSacReq.setPrchsStatusCd(DisplayConstants.PRCHS_STSTUS_COMPLETE_CD);
								historyListSacReq.setEndDt(episodeShopping.getSysDate());
								historyListSacReq.setOffset(1);
								historyListSacReq.setCount(1);
								historyListSacReq.setProductList(productEpisodeList);

								// 구매내역 조회 실행
								if (StringUtils.isNotEmpty(req.getUserKey())) {
									HistoryListSacInRes historyListSacRes = this.historyInternalSCI
											.searchHistoryList(historyListSacReq);
									this.log.debug("----------------------------------------------------------------");
									this.log.debug("[getShoppingInfo] purchase count : {}",
											historyListSacRes.getTotalCnt());
									this.log.debug("----------------------------------------------------------------");
									if (historyListSacRes.getTotalCnt() > 0) {
										prchsId = historyListSacRes.getHistoryList().get(0).getPrchsId();
										prchsDt = historyListSacRes.getHistoryList().get(0).getPrchsDt();
										prchsState = historyListSacRes.getHistoryList().get(0).getPrchsCaseCd();
										permitDeviceYn = historyListSacRes.getHistoryList().get(0).getPermitDeviceYn();
										if (DisplayConstants.PRCHS_CASE_PURCHASE_CD.equals(prchsState)) {
											prchsState = "payment";
										} else if (DisplayConstants.PRCHS_CASE_GIFT_CD.equals(prchsState)) {
											prchsState = "gift";
										}

										this.log.debug("----------------------------------------------------------------");
										this.log.debug("[getShoppingInfo] prchsId : {}", prchsId);
										this.log.debug("[getShoppingInfo] prchsDt : {}", prchsDt);
										this.log.debug("[getShoppingInfo] prchsState : {}", prchsState);
										this.log.debug("----------------------------------------------------------------");
									}
								}

								this.log.info("################ [SAC DP LocalSCI] SAC Purchase End : historyInternalSCI.searchHistoryList : "
										+ DateUtil.getToday("yyyy-MM-dd hh:mm:ss.SSS"));
							} catch (Exception ex) {
								purchaseFlag = false;
								this.log.error("구매내역 조회 연동 중 오류가 발생하였습니다. \n{}", ex);
								// throw new StorePlatformException("SAC_DSP_2001", "구매내역 조회 ", ex);
							}
							if (purchaseFlag) {
								if (StringUtils.isNotEmpty(prchsId) && "Y".equals(permitDeviceYn)) {
									episodeShopping.setPurchaseId(prchsId);
									episodeShopping.setPurchaseProdId(episodeShopping.getPartProdId());
									episodeShopping.setPurchaseDt(prchsDt);
									episodeShopping.setPurchaseState(prchsState);
									// 구매 정보
									episodeProduct.setPurchase(this.commonGenerator.generatePurchase(episodeShopping));
								}
							}

							// 에피소드 날짜 권한 정보
							// episodeProduct.setDateList(this.shoppingGenerator.generateDateList(episodeShopping));
							episodeRights = new Rights();

							// 상품 구매가 있고 후기가 없으면 feedback값을 내려줘야 함
							// 구매 여부 조회
							if (!StringUtils.isEmpty(req.getUserKey())) { // userKey가 있을 경우만
								boolean purchaseYn = this.displayCommonService.checkPurchase(
										tenantHeader.getTenantId(), req.getUserKey(), req.getDeviceKey(),
										episodeShopping.getPartProdId());
								this.log.info("구매 여부::{}", purchaseYn);
								if (purchaseYn) {
									episodeRights.setAllow(episodeShopping.getAllow());
								}
							}
							// // 상품 구매가 있고 후기가 없으면 feedback값을 내려줘야 함
							// if (purchseCount > 0) {
							// episodeRights.setAllow(episodeShopping.getAllow());
							// }

							episodeRights.setGrade(episodeShopping.getProdGrdCd());
							episodeRights.setDateList(this.shoppingGenerator.generateDateList(episodeShopping));
							episodeProduct.setRights(episodeRights);

							// saleOption 셋팅
							episodeSaleOption = new SalesOption();
							episodeSaleOption.setBtob(episodeShopping.getB2bProdYn()); // B2B_상품_여부
							episodeSaleOption.setType(episodeShopping.getProdCaseCd());

							if (episodeShopping.getSoldOut().equals("Y")) {
								episodeSaleOption.setStatus(DisplayConstants.DP_SOLDOUT);
							} else {
								episodeSaleOption.setStatus(DisplayConstants.DP_CONTINUE);
							}
							episodeSaleOption.setMaxCount(Integer.parseInt(episodeShopping.getSaleCnt()));
							episodeSaleOption.setMaxMonthlySale(Integer.parseInt(episodeShopping.getMthMaxCnt())); // 월_최대_판매_수량
							episodeSaleOption.setMaxDailySale(Integer.parseInt(episodeShopping.getDlyMaxCnt())); // 일_최대_판매_수량
							episodeSaleOption.setMaxMonthlyBuy(Integer.parseInt(episodeShopping.getMthUsrMaxCnt())); // 월_회원_최대_구매_수량
							episodeSaleOption.setMaxDailyBuy(Integer.parseInt(episodeShopping.getDlyUsrMaxCnt())); // 일_회원_최대_구매_수량
							episodeSaleOption.setMaxOnceBuy(Integer.parseInt(episodeShopping.getEachMaxCnt())); // 1차_최대_구매_수량
							episodeSaleOption.setPlaceUsage(episodeShopping.getUsePlac()); // 사용_장소
							episodeSaleOption.setRestrictUsage(episodeShopping.getUseLimtDesc()); // 사용_제한_설명
							episodeSaleOption.setPrincipleUsage(episodeShopping.getNoticeMatt()); // 공지_사항
							episodeSaleOption.setRefundUsage(episodeShopping.getPrchsCancelDrbkReason()); // 구매_취소_환불_사유
							episodeProduct.setSalesOption(episodeSaleOption);
							boolean nextFlag = false;
							int indexOption = 1;
							// Option 정보조회( 배송상품일 경우만 존재 )
							if (deliveryValue.equals("delivery")) {
								reqMap.put("chnlProdId", episodeShopping.getProdId());
								// 에피소드 list 조회
								List<MetaInfo> resultOptionList = this.commonDAO.queryForList(
										"Shopping.getShoppingOption", reqMap, MetaInfo.class);
								if (resultOptionList != null) {
									for (int mm = 0; mm < resultOptionList.size(); mm++) {
										MetaInfo optionShopping = resultOptionList.get(mm);
										if (optionShopping.getSubYn().equals("Y")) { // 옵션 1 인 경우
											selectOption = new SelectOption();
											// 옵션1 상품 ID
											if (optionShopping.getExpoOrd().equals("1")) {
												selectOption.setIndex(String.valueOf(indexOption));
												indexOption++;
											}

											// 옵션1 상품 정보 (상품명)
											Title option1Title = this.commonGenerator.generateTitle(optionShopping);
											selectOption.setId(optionShopping.getPartProdId());
											selectOption.setTitle(option1Title);
											// 옵션1 상품 가격정보
											Price option1Price = this.shoppingGenerator.generatePrice(optionShopping);
											selectOption.setPrice(option1Price);
											selectOption.setItemCode(optionShopping.getItemCode());

											if (req.getSaleDtUseYn() != null) {
												// 만약 판매중이면 판매중지로 바꿔야함
												if (optionShopping.getProdStatusCd() != null) {
													if (optionShopping.getProdStatusCd().equals(
															DisplayConstants.DP_SALE_STAT_ING)) {
														selectOption.setSalesStatus(DisplayConstants.DP_SALE_STAT_STOP);
													} else {
														selectOption.setSalesStatus(episodeShopping.getProdStatusCd());
													}
												} else {
													selectOption.setSalesStatus(optionShopping.getProdStatusCd());
												}
											} else {
												selectOption.setSalesStatus(optionShopping.getProdStatusCd());
											}

											selectOption.setSubYn("N");
										}
										if (optionShopping.getSubYn().equals("N")) { // 옵션 2 인 경우
											selectOption.setSubYn("Y");
											subSelectOption = new SubSelectOption();
											subSelectOption.setId(optionShopping.getPartProdId());

											// 옵션2 상품 정보 (상품명)
											option2Title = new Title();
											option2Title.setText(optionShopping.getOptPdNm());
											subSelectOption.setTitle(option2Title);
											// 옵션2 상품 가격정보
											Price option2Price = this.shoppingGenerator.generatePrice(optionShopping);
											subSelectOption.setPrice(option2Price);
											subSelectOption.setItemCode(optionShopping.getItemCode());

											if (req.getSaleDtUseYn() != null) {
												// 만약 판매중이면 판매중지로 바꿔야함
												if (optionShopping.getProdStatusCd() != null) {
													if (optionShopping.getProdStatusCd().equals(
															DisplayConstants.DP_SALE_STAT_ING)) {
														subSelectOption
																.setSalesStatus(DisplayConstants.DP_SALE_STAT_STOP);
													} else {
														subSelectOption
																.setSalesStatus(optionShopping.getProdStatusCd());
													}
												} else {
													subSelectOption.setSalesStatus(optionShopping.getProdStatusCd());
												}
											} else {
												subSelectOption.setSalesStatus(optionShopping.getProdStatusCd());
											}

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
							distributor = new Distributor();
							distributor.setSellerKey(episodeShopping.getSellerMbrNo());

							this.log.debug("#########################################################");
							this.log.debug("sellerMbrNo	:	" + episodeShopping.getSellerMbrNo());
							this.log.debug("#########################################################");

							episodeProduct.setDistributor(distributor);

							subProductList.add(episodeProduct);
						}

					}
					// 데이터 매핑
					// 채널 상품 판매 상태 코드
					product.setProductExplain(shopping.getProdBaseDesc());
					product.setProductDetailExplain(shopping.getProdDtlDesc());
					product.setMenuList(menuList);
					product.setPointList(pointList);
					product.setTitle(title);
					product.setSourceList(sourceList);
					product.setAccrual(accrual);
					product.setContributor(contributor);
					product.setSubProductTotalCount(subProductList.size());
					product.setSubProductList(subProductList);

				}
				res.setProduct(product);
			} else {
				throw new StorePlatformException("SAC_DSP_0009");
			}
		}
		return res;
	}

	/**
	 * 공통 offset,count 셋팅.
	 * 
	 * @param req
	 *            req
	 * @return
	 */
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

	/**
	 * 공통 상품 등급 코드 셋팅.
	 * 
	 * @param req
	 *            req
	 * @return boolean
	 */
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

	/**
	 * 쇼핑 지원 여부 .
	 * 
	 * @param header
	 *            header
	 * @return boolean
	 */
	private boolean commonSupportDeviceShopping(SacRequestHeader header) {
		boolean result = true;

		// 단말 지원정보 조회
		SupportDevice supportDevice = this.displayCommonService.getSupportDeviceInfo(header.getDeviceHeader()
				.getModel());
		if (!supportDevice.getSclShpgSprtYn().equals("Y")) {
			this.log.debug("----------------------------------------------------------------");
			this.log.debug("[shopping] supportDevice is empty!");
			this.log.debug("----------------------------------------------------------------");
			result = false;
		}
		return result;
	}

	/**
	 * 쇼핑 구매 연동.
	 * 
	 * @param req
	 *            req
	 * @return PaymentInfo
	 */
	@Override
	public List<PaymentInfo> getShoppingforPayment(PaymentInfoSacReq req) {
		List<PaymentInfo> paymentInfoList = new ArrayList<PaymentInfo>();
		List<String> prodIdList = req.getProdIdList();
		Map<String, Object> paramMap = new HashMap<String, Object>();
		// / 단말 지원 정보 조회
		SupportDevice supportDevice = this.displayCommonService.getSupportDeviceInfo(req.getDeviceModelCd());
		paramMap.put("lang", req.getLangCd());
		paramMap.put("tenantId", req.getTenantId());
		paramMap.put("prodRshpCd", DisplayConstants.DP_CHANNEL_EPISHODE_RELATIONSHIP_CD);
		paramMap.put("imageCd", DisplayConstants.DP_SHOPPING_REPRESENT_IMAGE_CD);
		paramMap.put("deviceModelCd", req.getDeviceModelCd());
		paramMap.put("supportDevice", supportDevice);
		paramMap.put("dpAnyPhone4mm", DisplayConstants.DP_ANY_PHONE_4MM);
		PaymentInfo paymentInfo = null;
		for (int i = 0; i < prodIdList.size(); i++) {
			paramMap.put("prodId", prodIdList.get(i));
			paymentInfo = this.commonDAO.queryForObject("PaymentInfo.getShoppingMetaInfo", paramMap, PaymentInfo.class);
			if (paymentInfo == null) {
				throw new StorePlatformException("SAC_DSP_0005", "[쇼핑상품 조회]" + prodIdList.get(0));
			}
			paymentInfoList.add(paymentInfo);
		}

		return paymentInfoList;
	}
}
