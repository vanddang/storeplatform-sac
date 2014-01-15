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
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.skplanet.storeplatform.framework.core.persistence.dao.CommonDAO;
import com.skplanet.storeplatform.sac.client.display.vo.shopping.ShoppingReq;
import com.skplanet.storeplatform.sac.client.display.vo.shopping.ShoppingRes;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.common.CommonResponse;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.common.Date;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.common.Identifier;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.common.Menu;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.common.Price;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.common.Source;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.common.Title;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.product.Accrual;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.product.Contributor;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.product.Product;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.product.Rights;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.product.SalesOption;
import com.skplanet.storeplatform.sac.display.common.service.DisplayCommonService;
import com.skplanet.storeplatform.sac.display.shopping.vo.ShoppingDTO;

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

	/**
	 * <pre>
	 * 쇼핑 추천/인기 상품 리스트 조회.
	 * </pre>
	 * 
	 * @param ShoppingReq
	 * @return ShoppingRes 리스트
	 */
	@Override
	public ShoppingRes getFeatureProductList(ShoppingReq req) {

		if (req.getDummy() == null) {
			ShoppingRes responseVO = null;

			// Header 정보 //
			if (req.getTenantId() == null) {
				req.setTenantId("S01");
			}
			if (req.getImageCd() == null) {
				req.setImageCd("DP000196");
			}
			if (req.getLangCd() == null) {
				req.setLangCd("ko");
			}
			if (req.getDeviceModelCd() == null) {
				req.setDeviceModelCd("SHV-E330SSO");
			}

			String stdDt = "";
			stdDt = this.displayCommonService.getBatchStandardDateString(req.getTenantId(), req.getListId());
			if (stdDt == null) {
				stdDt = "20130101000000";
			}
			req.setStdDt(stdDt);

			Integer totalCount = 0;
			List<ShoppingDTO> resultList = this.commonDAO.queryForList("Shopping.getFeatureProductList", req,
					ShoppingDTO.class);

			if (resultList != null) {
				ShoppingDTO ShoppingDto = new ShoppingDTO();

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

				List<Menu> menuList = null;
				List<Source> sourceList = null;
				List<Product> productList = new ArrayList<Product>();

				for (int i = 0; i < resultList.size(); i++) {
					ShoppingDto = resultList.get(i);

					// 상품 정보 (상품ID)
					product = new Product();
					identifier = new Identifier();
					identifier.setType("catalog");
					identifier.setText(ShoppingDto.getCatagoryId());

					// 메뉴 정보
					menuList = new ArrayList<Menu>();
					menu = new Menu();
					menu.setType("menuId");
					menu.setId(ShoppingDto.getMenuId());
					menu.setName(ShoppingDto.getMenuName());
					menuList.add(menu);

					// 상품 정보 (상품명)
					title = new Title();
					title.setText(ShoppingDto.getCatagoryName());
					title.setPrefix(ShoppingDto.getNewYn());

					// 상품 정보 (상품가격)
					price = new Price();
					price.setFixedPrice(ShoppingDto.getProdNetAmt());
					price.setDiscountRate(ShoppingDto.getDcRate());
					price.setText(Integer.parseInt(ShoppingDto.getProdAmt()));

					// 이미지 정보
					sourceList = new ArrayList<Source>();
					source = new Source();
					source.setType("thumbnail");
					source.setUrl("inst_thumbnail_20111216154840.jpg");
					sourceList.add(source);

					// 다운로드 수
					accrual = new Accrual();
					accrual.setDownloadCount(ShoppingDto.getPrchsQty());

					// 이용권한 정보
					rights = new Rights();
					date = new Date();
					date.setType("duration/salePeriod");
					date.setText(ShoppingDto.getApplyStartDt() + "/" + ShoppingDto.getApplyEndDt());
					rights.setGrade(ShoppingDto.getProdGrdCd());
					rights.setDate(date);

					// contributor
					contributor = new Contributor();
					identifier1 = new Identifier();
					identifier1.setType("brand");
					identifier1.setText(ShoppingDto.getBrandId());
					contributor.setName(ShoppingDto.getBrandName());
					contributor.setIdentifier(identifier1);

					// saleoption
					saleoption = new SalesOption();
					saleoption.setType(ShoppingDto.getProdCaseCd());

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
					totalCount = ShoppingDto.getTotalCount();
					productList.add(i, product);
				}

				responseVO = new ShoppingRes();
				responseVO.setProductList(productList);

				CommonResponse commonResponse = new CommonResponse();
				commonResponse.setTotalCount(totalCount);
				responseVO.setCommonResponse(commonResponse);
			}
			return responseVO;
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

			// List<ShoppingDTO> resultList = this.commonDAO.queryForList("Shopping.selectShoppingList", requestVO,
			// ShoppingDTO.class);
			// List<ShoppingDTO> resultList = null;

			// if (resultList != null) {
			// ShoppingDTO ShoppingDto = new ShoppingDTO();

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
				// ShoppingDto = resultList.get(i);
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

				price.setFixedPrice("1000");
				price.setDiscountRate("0");
				price.setDiscountPrice("0");
				price.setText(Integer.parseInt("1000"));

				// 이미지 정보
				source.setType("thumbnail");
				source.setUrl("inst_thumbnail_20111216154840.jpg");
				sourceList.add(source);
				// contributor

				acc.setDownloadCount("6229");

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
	 * 
	 * @param ShoppingReq
	 * @return ShoppingRes 리스트
	 */
	@Override
	public ShoppingRes getNewProductList(ShoppingReq req) {
		if (req.getDummy() == null) {
			ShoppingRes responseVO = null;
			// Header 정보 //
			if (req.getTenantId() == null) {
				req.setTenantId("S01");
			}
			if (req.getImageCd() == null) {
				req.setImageCd("DP000196");
			}
			if (req.getLangCd() == null) {
				req.setLangCd("ko");
			}
			if (req.getDeviceModelCd() == null) {
				req.setDeviceModelCd("SHV-E330SSO");
			}

			Integer totalCount = 0;
			List<ShoppingDTO> resultList = this.commonDAO.queryForList("Shopping.getNewProductList", req,
					ShoppingDTO.class);

			if (resultList != null) {
				ShoppingDTO ShoppingDto = new ShoppingDTO();

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

				List<Menu> menuList = null;
				List<Source> sourceList = null;
				List<Product> productList = new ArrayList<Product>();

				for (int i = 0; i < resultList.size(); i++) {
					ShoppingDto = resultList.get(i);

					// 상품 정보 (상품ID)
					product = new Product();
					identifier = new Identifier();
					identifier.setType("catalog");
					identifier.setText(ShoppingDto.getCatagoryId());

					// 메뉴 정보
					menuList = new ArrayList<Menu>();
					menu = new Menu();
					menu.setType("menuId");
					menu.setId(ShoppingDto.getMenuId());
					menu.setName(ShoppingDto.getMenuName());
					menuList.add(menu);

					// 상품 정보 (상품명)
					title = new Title();
					title.setText(ShoppingDto.getCatagoryName());
					title.setPrefix(ShoppingDto.getNewYn());

					// 상품 정보 (상품가격)
					price = new Price();
					price.setFixedPrice(ShoppingDto.getProdNetAmt());
					price.setDiscountRate(ShoppingDto.getDcRate());
					price.setText(Integer.parseInt(ShoppingDto.getProdAmt()));

					// 이미지 정보
					sourceList = new ArrayList<Source>();
					source = new Source();
					source.setType("thumbnail");
					source.setUrl("inst_thumbnail_20111216154840.jpg");
					sourceList.add(source);

					// 다운로드 수
					accrual = new Accrual();
					accrual.setDownloadCount(ShoppingDto.getPrchsQty());

					// 이용권한 정보
					rights = new Rights();
					date = new Date();
					date.setType("duration/salePeriod");
					date.setText(ShoppingDto.getApplyStartDt() + "/" + ShoppingDto.getApplyEndDt());
					rights.setGrade(ShoppingDto.getProdGrdCd());
					rights.setDate(date);

					// contributor
					contributor = new Contributor();
					identifier1 = new Identifier();
					identifier1.setType("brand");
					identifier1.setText(ShoppingDto.getBrandId());
					contributor.setName(ShoppingDto.getBrandName());
					contributor.setIdentifier(identifier1);

					// saleoption
					saleoption = new SalesOption();
					saleoption.setType(ShoppingDto.getProdCaseCd());

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
					totalCount = ShoppingDto.getTotalCount();
					productList.add(i, product);
				}

				responseVO = new ShoppingRes();
				responseVO.setProductList(productList);

				CommonResponse commonResponse = new CommonResponse();
				commonResponse.setTotalCount(totalCount);
				responseVO.setCommonResponse(commonResponse);
			}
			return responseVO;
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

			// List<ShoppingDTO> resultList = this.commonDAO.queryForList("Shopping.selectShoppingList", requestVO,
			// ShoppingDTO.class);
			// List<ShoppingDTO> resultList = null;

			// if (resultList != null) {
			// ShoppingDTO ShoppingDto = new ShoppingDTO();

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
				// ShoppingDto = resultList.get(i);
				// 상품 정보 (상품ID)
				identifier.setType("catalog");
				identifier.setText("CT00010008");

				// 메뉴 정보
				menu.setType("menuId");
				menu.setId("DP28009");
				menu.setName("편의점/마트");
				menuList.add(menu);

				// 상품 정보 (상품명)
				title.setText("신규 카탈로그 상품");
				title.setPrefix("Y");
				// 상품 정보 (상품가격)

				price.setFixedPrice("1000");
				price.setDiscountRate("0");
				price.setDiscountPrice("0");
				price.setText(Integer.parseInt("1000"));

				// 이미지 정보
				source.setType("thumbnail");
				source.setUrl("inst_thumbnail_20111216154840.jpg");
				sourceList.add(source);
				// contributor

				acc.setDownloadCount("6229");

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
	 * 
	 * @param ShoppingReq
	 * @return ShoppingRes 리스트
	 */
	@Override
	public ShoppingRes getSubProductList(ShoppingReq req) {
		if (req.getDummy() == null) {
			ShoppingRes responseVO = null;

			// Header 정보 //
			if (req.getTenantId() == null) {
				req.setTenantId("S01");
			}
			if (req.getImageCd() == null) {
				req.setImageCd("DP000196");
			}
			if (req.getLangCd() == null) {
				req.setLangCd("ko");
			}
			if (req.getDeviceModelCd() == null) {
				req.setDeviceModelCd("SHV-E330SSO");
			}
			// default 정보
			if (req.getOrderedBy() == null) {
				req.setOrderedBy("recent");
			}

			if (req.getOrderedBy().equals("download")) {
				String stdDt = "";
				stdDt = this.displayCommonService.getBatchStandardDateString(req.getTenantId(), req.getListId());
				if (stdDt == null) {
					stdDt = "20130101000000";
				}
				req.setStdDt(stdDt);
			}

			Integer totalCount = 0;
			List<ShoppingDTO> resultList = this.commonDAO.queryForList("Shopping.getSubProductList", req,
					ShoppingDTO.class);

			if (resultList != null) {
				ShoppingDTO ShoppingDto = new ShoppingDTO();

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

				List<Menu> menuList = null;
				List<Source> sourceList = null;
				List<Product> productList = new ArrayList<Product>();

				for (int i = 0; i < resultList.size(); i++) {
					ShoppingDto = resultList.get(i);

					// 상품 정보 (상품ID)
					product = new Product();
					identifier = new Identifier();
					identifier.setType("catalog");
					identifier.setText(ShoppingDto.getCatagoryId());

					// 메뉴 정보
					menuList = new ArrayList<Menu>();
					menu = new Menu();
					menu.setType("menuId");
					menu.setId(ShoppingDto.getMenuId());
					menu.setName(ShoppingDto.getMenuName());
					menuList.add(menu);

					// 상품 정보 (상품명)
					title = new Title();
					title.setText(ShoppingDto.getCatagoryName());
					title.setPrefix(ShoppingDto.getNewYn());

					// 상품 정보 (상품가격)
					price = new Price();
					price.setFixedPrice(ShoppingDto.getProdNetAmt());
					price.setDiscountRate(ShoppingDto.getDcRate());
					price.setText(Integer.parseInt(ShoppingDto.getProdAmt()));

					// 이미지 정보
					sourceList = new ArrayList<Source>();
					source = new Source();
					source.setType("thumbnail");
					source.setUrl("inst_thumbnail_20111216154840.jpg");
					sourceList.add(source);

					// 다운로드 수
					accrual = new Accrual();
					accrual.setDownloadCount(ShoppingDto.getPrchsQty());

					// 이용권한 정보
					rights = new Rights();
					date = new Date();
					date.setType("duration/salePeriod");
					date.setText(ShoppingDto.getApplyStartDt() + "/" + ShoppingDto.getApplyEndDt());
					rights.setGrade(ShoppingDto.getProdGrdCd());
					rights.setDate(date);

					// contributor
					contributor = new Contributor();
					identifier1 = new Identifier();
					identifier1.setType("brand");
					identifier1.setText(ShoppingDto.getBrandId());
					contributor.setName(ShoppingDto.getBrandName());
					contributor.setIdentifier(identifier1);

					// saleoption
					saleoption = new SalesOption();
					saleoption.setType(ShoppingDto.getProdCaseCd());

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
					totalCount = ShoppingDto.getTotalCount();
					productList.add(i, product);
				}

				responseVO = new ShoppingRes();
				responseVO.setProductList(productList);

				CommonResponse commonResponse = new CommonResponse();
				commonResponse.setTotalCount(totalCount);
				responseVO.setCommonResponse(commonResponse);
			}
			return responseVO;
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

			// List<ShoppingDTO> resultList = this.commonDAO.queryForList("Shopping.selectShoppingList", requestVO,
			// ShoppingDTO.class);
			// List<ShoppingDTO> resultList = null;

			// if (resultList != null) {
			// ShoppingDTO ShoppingDto = new ShoppingDTO();

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
				// ShoppingDto = resultList.get(i);
				// 상품 정보 (상품ID)
				identifier.setType("catalog");
				identifier.setText("CT00010008");

				// 메뉴 정보
				menu.setType("menuId");
				menu.setId("DP28009");
				menu.setName("편의점/마트");
				menuList.add(menu);

				// 상품 정보 (상품명)
				title.setText("서브카테고리 카탈로그 상품");
				title.setPrefix("Y");
				// 상품 정보 (상품가격)

				price.setFixedPrice("1000");
				price.setDiscountRate("0");
				price.setDiscountPrice("0");
				price.setText(Integer.parseInt("1000"));

				// 이미지 정보
				source.setType("thumbnail");
				source.setUrl("inst_thumbnail_20111216154840.jpg");
				sourceList.add(source);
				// contributor

				acc.setDownloadCount("6229");

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
	 * 
	 * @param ShoppingReq
	 * @return ShoppingRes 리스트
	 */
	@Override
	public ShoppingRes getSecialPriceProductList(ShoppingReq req) {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * <pre>
	 * 기획전  상품  조회.
	 * </pre>
	 * 
	 * @param ShoppingReq
	 * @return ShoppingRes 리스트
	 */
	@Override
	public ShoppingRes getSpecialSalesList(ShoppingReq req) {
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

		// List<ShoppingDTO> resultList = this.commonDAO.queryForList("Shopping.selectShoppingList", requestVO,
		// ShoppingDTO.class);
		// List<ShoppingDTO> resultList = null;

		// if (resultList != null) {
		// ShoppingDTO ShoppingDto = new ShoppingDTO();

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
			// ShoppingDto = resultList.get(i);
			// 상품 정보 (상품ID)
			identifier.setType("product");
			identifier.setText("S900000960");

			// 메뉴 정보
			menu.setType("menuId");
			menu.setId("MN28009");
			menu.setName("편의점/마트");
			menuList.add(menu);

			// 상품 정보 (상품명)
			title.setText("기획전  상품  조회");
			// 상품 정보 (상품가격)

			price.setFixedPrice("1000");
			price.setDiscountRate("0");
			price.setDiscountPrice("0");
			price.setText(Integer.parseInt("1000"));

			// 이미지 정보
			source.setType("thumbnail");
			source.setUrl("inst_thumbnail_20111216154840.jpg");
			sourceList.add(source);
			// contributor

			acc.setDownloadCount("6229");

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
			// product.setMenuList(menuList);
			product.setTitle(title);
			// product.setPrice(price);
			// product.setRights(rights);
			product.setSourceList(sourceList);
			// product.setAccrual(acc);
			// product.setRights(rights);
			// product.setContributor(contributor);
			// product.setSalesOption(saleoption);

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

	/**
	 * <pre>
	 * 특정 기획전에 대한 상품 리스트 조회.
	 * </pre>
	 * 
	 * @param ShoppingReq
	 * @return ShoppingRes 리스트
	 */
	@Override
	public ShoppingRes getSpecialSalesProductList(ShoppingReq req) {
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

		// List<ShoppingDTO> resultList = this.commonDAO.queryForList("Shopping.selectShoppingList", requestVO,
		// ShoppingDTO.class);
		// List<ShoppingDTO> resultList = null;

		// if (resultList != null) {
		// ShoppingDTO ShoppingDto = new ShoppingDTO();

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
			// ShoppingDto = resultList.get(i);
			// 상품 정보 (상품ID)
			identifier.setType("product");
			identifier.setText("S900000960");

			// 메뉴 정보
			menu.setType("menuId");
			menu.setId("MN28009");
			menu.setName("편의점/마트");
			menuList.add(menu);

			// 상품 정보 (상품명)
			title.setText("특정 기획전에 대한 상품 리스트");
			// 상품 정보 (상품가격)

			price.setFixedPrice("1000");
			price.setDiscountRate("0");
			price.setDiscountPrice("0");
			price.setText(Integer.parseInt("1000"));

			// 이미지 정보
			source.setType("thumbnail");
			source.setUrl("inst_thumbnail_20111216154840.jpg");
			sourceList.add(source);
			// contributor

			acc.setDownloadCount("6229");

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

	/**
	 * <pre>
	 * 브랜드샵 - 메인 리스트 조회.
	 * </pre>
	 * 
	 * @param ShoppingReq
	 * @return ShoppingRes 리스트
	 */
	@Override
	public ShoppingRes getBrandshopMainList(ShoppingReq req) {
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

		// List<ShoppingDTO> resultList = this.commonDAO.queryForList("Shopping.selectShoppingList", requestVO,
		// ShoppingDTO.class);
		// List<ShoppingDTO> resultList = null;

		// if (resultList != null) {
		// ShoppingDTO ShoppingDto = new ShoppingDTO();

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
			// ShoppingDto = resultList.get(i);
			// 상품 정보 (상품ID)
			identifier.setType("product");
			identifier.setText("S900000960");

			// 메뉴 정보
			menu.setType("menuId");
			menu.setId("MN28009");
			menu.setName("편의점/마트");
			menuList.add(menu);

			// 상품 정보 (상품명)
			title.setText("브랜드샵 - 메인");
			// 상품 정보 (상품가격)

			price.setFixedPrice("1000");
			price.setDiscountRate("0");
			price.setDiscountPrice("0");
			price.setText(Integer.parseInt("1000"));

			// 이미지 정보
			source.setType("thumbnail");
			source.setUrl("inst_thumbnail_20111216154840.jpg");
			sourceList.add(source);
			// contributor

			acc.setDownloadCount("6229");

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
			// product.setPrice(price);
			// product.setRights(rights);
			product.setSourceList(sourceList);
			// product.setAccrual(acc);
			// product.setRights(rights);
			// product.setContributor(contributor);
			// product.setSalesOption(saleoption);

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

	/**
	 * <pre>
	 * 특정 브랜드샵 상품 리스트
	 * </pre>
	 * 
	 * @param ShoppingReq
	 * @return ShoppingRes 리스트
	 */
	@Override
	public ShoppingRes getBrandshopProductList(ShoppingReq req) {
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

		// List<ShoppingDTO> resultList = this.commonDAO.queryForList("Shopping.selectShoppingList", requestVO,
		// ShoppingDTO.class);
		// List<ShoppingDTO> resultList = null;

		// if (resultList != null) {
		// ShoppingDTO ShoppingDto = new ShoppingDTO();

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
			// ShoppingDto = resultList.get(i);
			// 상품 정보 (상품ID)
			identifier.setType("product");
			identifier.setText("S900000960");

			// 메뉴 정보
			menu.setType("menuId");
			menu.setId("MN28009");
			menu.setName("편의점/마트");
			menuList.add(menu);

			// 상품 정보 (상품명)
			title.setText("특정 브랜드샵");
			// 상품 정보 (상품가격)

			price.setFixedPrice("1000");
			price.setDiscountRate("0");
			price.setDiscountPrice("0");
			price.setText(Integer.parseInt("1000"));

			// 이미지 정보
			source.setType("thumbnail");
			source.setUrl("inst_thumbnail_20111216154840.jpg");
			sourceList.add(source);
			// contributor

			acc.setDownloadCount("6229");

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

	/**
	 * <pre>
	 * 쇼핑테마 리스트상품 리스트
	 * </pre>
	 * 
	 * @param ShoppingReq
	 * @return ShoppingRes 리스트
	 */
	@Override
	public ShoppingRes getThemeList(ShoppingReq req) {
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

		// List<ShoppingDTO> resultList = this.commonDAO.queryForList("Shopping.selectShoppingList", requestVO,
		// ShoppingDTO.class);
		// List<ShoppingDTO> resultList = null;

		// if (resultList != null) {
		// ShoppingDTO ShoppingDto = new ShoppingDTO();

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
			// ShoppingDto = resultList.get(i);
			// 상품 정보 (상품ID)
			identifier.setType("product");
			identifier.setText("S900000960");

			// 메뉴 정보
			menu.setType("menuId");
			menu.setId("MN28009");
			menu.setName("편의점/마트");
			menuList.add(menu);

			// 상품 정보 (상품명)
			title.setText("쇼핑테마");
			// 상품 정보 (상품가격)

			price.setFixedPrice("1000");
			price.setDiscountRate("0");
			price.setDiscountPrice("0");
			price.setText(Integer.parseInt("1000"));

			// 이미지 정보
			source.setType("thumbnail");
			source.setUrl("inst_thumbnail_20111216154840.jpg");
			sourceList.add(source);
			// contributor

			acc.setDownloadCount("6229");

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
			// product.setMenuList(menuList);
			product.setTitle(title);
			// product.setPrice(price);
			// product.setRights(rights);
			product.setSourceList(sourceList);
			// product.setAccrual(acc);
			// product.setRights(rights);
			// product.setContributor(contributor);
			// product.setSalesOption(saleoption);

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

	/**
	 * <pre>
	 * 특정 테마 리스트상품 리스트
	 * </pre>
	 * 
	 * @param ShoppingReq
	 * @return ShoppingRes 리스트
	 */
	@Override
	public ShoppingRes getThemeProductList(ShoppingReq req) {
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

		// List<ShoppingDTO> resultList = this.commonDAO.queryForList("Shopping.selectShoppingList", requestVO,
		// ShoppingDTO.class);
		// List<ShoppingDTO> resultList = null;

		// if (resultList != null) {
		// ShoppingDTO ShoppingDto = new ShoppingDTO();

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
			// ShoppingDto = resultList.get(i);
			// 상품 정보 (상품ID)
			identifier.setType("product");
			identifier.setText("S900000960");

			// 메뉴 정보
			menu.setType("menuId");
			menu.setId("MN28009");
			menu.setName("편의점/마트");
			menuList.add(menu);

			// 상품 정보 (상품명)
			title.setText("특정 테마 리스트");
			// 상품 정보 (상품가격)

			price.setFixedPrice("1000");
			price.setDiscountRate("0");
			price.setDiscountPrice("0");
			price.setText(Integer.parseInt("1000"));

			// 이미지 정보
			source.setType("thumbnail");
			source.setUrl("inst_thumbnail_20111216154840.jpg");
			sourceList.add(source);
			// contributor

			acc.setDownloadCount("6229");

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

	/**
	 * <pre>
	 *   특정 카탈로그에 대한 다른 상품 리스트
	 * </pre>
	 * 
	 * @param ShoppingReq
	 * @return ShoppingRes 리스트
	 */
	@Override
	public ShoppingRes getCatagoryAnotherProductList(ShoppingReq req) {
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

		// List<ShoppingDTO> resultList = this.commonDAO.queryForList("Shopping.selectShoppingList", requestVO,
		// ShoppingDTO.class);
		// List<ShoppingDTO> resultList = null;

		// if (resultList != null) {
		// ShoppingDTO ShoppingDto = new ShoppingDTO();

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
			// ShoppingDto = resultList.get(i);
			// 상품 정보 (상품ID)
			identifier.setType("product");
			identifier.setText("S900000960");

			// 메뉴 정보
			menu.setType("menuId");
			menu.setId("MN28009");
			menu.setName("편의점/마트");
			menuList.add(menu);

			// 상품 정보 (상품명)
			title.setText("특정 카탈로그에 대한 다른 상품 리스트");
			// 상품 정보 (상품가격)

			price.setFixedPrice("1000");
			price.setDiscountRate("0");
			price.setDiscountPrice("0");
			price.setText(Integer.parseInt("1000"));

			// 이미지 정보
			source.setType("thumbnail");
			source.setUrl("inst_thumbnail_20111216154840.jpg");
			sourceList.add(source);
			// contributor

			acc.setDownloadCount("6229");

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

	/**
	 * <pre>
	 * 특정 브랜드에 대한 다른 상품 리스트
	 * </pre>
	 * 
	 * @param ShoppingReq
	 * @return ShoppingRes 리스트
	 */
	@Override
	public ShoppingRes getBrandAnotherProductList(ShoppingReq req) {
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

		// List<ShoppingDTO> resultList = this.commonDAO.queryForList("Shopping.selectShoppingList", requestVO,
		// ShoppingDTO.class);
		// List<ShoppingDTO> resultList = null;

		// if (resultList != null) {
		// ShoppingDTO ShoppingDto = new ShoppingDTO();

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
			// ShoppingDto = resultList.get(i);
			// 상품 정보 (상품ID)
			identifier.setType("product");
			identifier.setText("S900000960");

			// 메뉴 정보
			menu.setType("menuId");
			menu.setId("MN28009");
			menu.setName("편의점/마트");
			menuList.add(menu);

			// 상품 정보 (상품명)
			title.setText("특정 브랜드");
			// 상품 정보 (상품가격)

			price.setFixedPrice("1000");
			price.setDiscountRate("0");
			price.setDiscountPrice("0");
			price.setText(Integer.parseInt("1000"));

			// 이미지 정보
			source.setType("thumbnail");
			source.setUrl("inst_thumbnail_20111216154840.jpg");
			sourceList.add(source);
			// contributor

			acc.setDownloadCount("6229");

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

	/**
	 * <pre>
	 * 쇼핑상세
	 * </pre>
	 * 
	 * @param ShoppingReq
	 * @return ShoppingRes 리스트
	 */
	@Override
	public ShoppingRes getShoppingDetail(ShoppingReq req) {
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

		// List<ShoppingDTO> resultList = this.commonDAO.queryForList("Shopping.selectShoppingList", requestVO,
		// ShoppingDTO.class);
		// List<ShoppingDTO> resultList = null;

		// if (resultList != null) {
		// ShoppingDTO ShoppingDto = new ShoppingDTO();

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
			// ShoppingDto = resultList.get(i);
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

			price.setFixedPrice("1000");
			price.setDiscountRate("0");
			price.setDiscountPrice("0");
			price.setText(Integer.parseInt("1000"));

			// 이미지 정보
			source.setType("thumbnail");
			source.setUrl("inst_thumbnail_20111216154840.jpg");
			sourceList.add(source);
			// contributor

			acc.setDownloadCount("6229");

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

}
