/*
 * Copyright (c) 2013 SK planet.
 * All right reserved.
 *
 * This software is the confidential and proprietary information of SK planet.
 * You shall not disclose such Confidential Information and
 * shall use it only in accordance with the terms of the license agreement
 * you entered into with SK planet.
 */
package com.skplanet.storeplatform.sac.product.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.map.JsonMappingException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.skplanet.storeplatform.framework.core.persistence.dao.CommonDAO;
import com.skplanet.storeplatform.sac.client.product.vo.category.WebtoonRequest;
import com.skplanet.storeplatform.sac.client.product.vo.category.WebtoonResponse;
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
import com.skplanet.storeplatform.sac.product.vo.WebtoonDTO;

/**
 * WebtoonList Service 인터페이스(CoreStoreBusiness) 구현체
 * 
 * Updated on : 2013. 12. 23. Updated by : 김형식, SK 플래닛.
 */
@Service
public class WebtoonServiceImpl implements WebtoonService {

	private final Logger log = LoggerFactory.getLogger(this.getClass());

	@Autowired
	@Qualifier("sac")
	private CommonDAO commonDAO;

	/**
	 * <pre>
	 * 일반 카테고리 웹툰 리스트 조회.
	 * </pre>
	 * 
	 * @param WebtoonRequest
	 * @return WebtoonResponse 리스트
	 */
	@Override
	public WebtoonResponse getWebtoonList(WebtoonRequest req) throws JsonGenerationException, JsonMappingException,
			IOException, Exception {

		int totalCount = 0;
		WebtoonResponse responseVO = null;
		WebtoonRequest requestVO = new WebtoonRequest();
		requestVO.setOffset(1);
		requestVO.setCount(20);
		if (null == req.getTenantId() || "".equals(req.getTenantId())) {
			// throw new Exception("tenantId 는 필수 파라메터 입니다.");
		}
		if (null == req.getSystemId() || "".equals(req.getSystemId())) {
			// throw new Exception("systemId 는 필수 파라메터 입니다.");
		}

		// List<WebtoonDTO> resultList = this.commonDAO.queryForList("Webtoon.selectWebtoonList", requestVO,
		// WebtoonDTO.class);
		List<WebtoonDTO> resultList = null;

		// if (resultList != null) {
		WebtoonDTO webtoonDto = new WebtoonDTO();

		// Response VO를 만들기위한 생성자
		Identifier identifier = new Identifier();
		Menu menu = new Menu();
		Rights rights = new Rights();
		Title title = new Title();
		Source source = new Source();
		Price price = new Price();
		Product product = new Product();
		Contributor contributor = new Contributor();
		Accrual acc = new Accrual();
		Date date = new Date();

		List<Menu> menuList = new ArrayList<Menu>();
		List<Source> sourceList = new ArrayList<Source>();
		List<Product> productList = new ArrayList<Product>();

		for (int i = 0; i < 1; i++) {
			// webtoonDto = resultList.get(i);
			// 상품 정보 (상품ID)
			identifier.setType("episode");
			identifier.setText("H090124341");

			// 메뉴 정보
			menu.setType("menuId");
			menu.setId("MN26004");
			menu.setName("웹툰/스릴러");
			menuList.add(menu);

			// contributor
			contributor.setName("장한나");

			acc.setScore(4.5);

			// 상품 정보 (상품명)
			title.setText("강철강");

			// 이미지 정보
			source.setType("thumbnail");
			source.setUrl("inst_thumbnail_20111216154840.jpg");
			sourceList.add(source);

			// 이용권한 정보
			rights.setGrade("PD004401");

			// 상품 정보 (상품가격)
			price.setText(Integer.parseInt("0"));
			date.setType("date");
			date.setText("20130820190000");
			// 데이터 매핑
			product.setIdentifier(identifier);
			product.setContributor(contributor);
			product.setAccrual(acc);
			product.setTitle(title);
			// product.setRights(rights);
			product.setSourceList(sourceList);
			product.setPrice(price);
			product.setDate(date);

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

		responseVO = new WebtoonResponse();
		responseVO.setProductList(productList);

		CommonResponse commonResponse = new CommonResponse();
		commonResponse.setTotalCount(20);
		responseVO.setCommonResponse(commonResponse);
		// }
		return responseVO;
	}

	/**
	 * <pre>
	 * 운영자 추천 웹툰 리스트 조회.
	 * </pre>
	 * 
	 * @param WebtoonRequest
	 * @return WebtoonResponse 리스트
	 */
	@Override
	public WebtoonResponse getAdminWebtoonList(WebtoonRequest req) throws JsonGenerationException,
			JsonMappingException, IOException, Exception {

		int totalCount = 0;
		WebtoonResponse responseVO = null;
		WebtoonRequest requestVO = new WebtoonRequest();
		requestVO.setOffset(1);
		requestVO.setCount(20);
		if (null == req.getTenantId() || "".equals(req.getTenantId())) {
			// throw new Exception("tenantId 는 필수 파라메터 입니다.");
		}
		if (null == req.getSystemId() || "".equals(req.getSystemId())) {
			// throw new Exception("systemId 는 필수 파라메터 입니다.");
		}

		// List<WebtoonDTO> resultList = this.commonDAO.queryForList("Webtoon.selectWebtoonList", requestVO,
		// WebtoonDTO.class);
		List<WebtoonDTO> resultList = null;

		// if (resultList != null) {
		WebtoonDTO webtoonDto = new WebtoonDTO();

		// Response VO를 만들기위한 생성자
		Identifier identifier = new Identifier();
		Menu menu = new Menu();
		Rights rights = new Rights();
		Title title = new Title();
		Source source = new Source();
		Price price = new Price();
		Product product = new Product();
		Contributor contributor = new Contributor();
		Accrual acc = new Accrual();
		Date date = new Date();

		List<Menu> menuList = new ArrayList<Menu>();
		List<Source> sourceList = new ArrayList<Source>();
		List<Product> productList = new ArrayList<Product>();

		for (int i = 0; i < 1; i++) {
			// webtoonDto = resultList.get(i);
			// 상품 정보 (상품ID)
			identifier.setType("episode");
			identifier.setText("H090124341");

			// 메뉴 정보
			menu.setType("menuId");
			menu.setId("MN26004");
			menu.setName("웹툰/스릴러");
			menuList.add(menu);

			// contributor
			contributor.setName("장한나");

			acc.setScore(4.5);

			// 상품 정보 (상품명)
			title.setText("강철강");

			// 이미지 정보
			source.setType("thumbnail");
			source.setUrl("inst_thumbnail_20111216154840.jpg");
			sourceList.add(source);

			// 이용권한 정보
			rights.setGrade("PD004401");

			// 상품 정보 (상품가격)
			price.setText(Integer.parseInt("0"));
			date.setText("20130820190000");
			// 데이터 매핑
			product.setIdentifier(identifier);
			product.setContributor(contributor);
			product.setAccrual(acc);
			product.setTitle(title);
			// product.setRights(rights);
			product.setSourceList(sourceList);
			product.setPrice(price);
			product.setDate(date);

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

		responseVO = new WebtoonResponse();
		responseVO.setProductList(productList);

		CommonResponse commonResponse = new CommonResponse();
		commonResponse.setTotalCount(20);
		responseVO.setCommonResponse(commonResponse);
		// }
		return responseVO;
	}

}
