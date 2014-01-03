/*
 * Copyright (c) 2013 SK planet.
 * All right reserved.
 *
 * This software is the confidential and proprietary information of SK planet.
 * You shall not disclose such Confidential Information and
 * shall use it only in accordance with the terms of the license agreement
 * you entered into with SK planet.
 */
package com.skplanet.storeplatform.sac.display.feature.recommend.service;

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
import com.skplanet.storeplatform.sac.client.display.vo.feature.recommend.RecommendWebtoonReq;
import com.skplanet.storeplatform.sac.client.display.vo.feature.recommend.RecommendWebtoonRes;
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

/**
 * WebtoonList Service 인터페이스(CoreStoreBusiness) 구현체
 * 
 * Updated on : 2013. 12. 23. Updated by : 김형식, SK 플래닛.
 */
@Service
public class RecommendWebtoonServiceImpl implements RecommendWebtoonService {

	private final Logger log = LoggerFactory.getLogger(this.getClass());

	@Autowired
	@Qualifier("sac")
	private CommonDAO commonDAO;

	/**
	 * <pre>
	 * 운영자 추천 웹툰 리스트 조회.
	 * </pre>
	 * 
	 * @param RecommendWebtoonReq
	 * @return RecommendWebtoonRes 리스트
	 */
	@Override
	public RecommendWebtoonRes getAdminWebtoonList(RecommendWebtoonReq req) throws JsonGenerationException,
			JsonMappingException, IOException, Exception {

		RecommendWebtoonRes responseVO = null;
		req.setOffset(1);
		req.setCount(20);
		req.setTenantId("S01");
		req.setSystemId("S009");
		req.setWeekDayCd("DP010101");
		req.setImageCd("DP000196");
		req.setListId("DP000196");

		if (null == req.getTenantId() || "".equals(req.getTenantId())) {
			// throw new Exception("tenantId 는 필수 파라메터 입니다.");
		}
		if (null == req.getSystemId() || "".equals(req.getSystemId())) {
			// throw new Exception("systemId 는 필수 파라메터 입니다.");
		}

		// List<RecommendWebtoonDTO> resultList = this.commonDAO.queryForList("Webtoon.getAdminWebtoonList", req,
		// RecommendWebtoonDTO.class);

		// if (resultList != null) {
		// RecommendWebtoonDTO RecommendWebtoonDTO = new RecommendWebtoonDTO();

		// Response VO를 만들기위한 생성자
		Identifier identifier = new Identifier();
		Menu menu = new Menu();
		Rights rights = new Rights();
		Title title = new Title();
		Source source = new Source();
		Price price = new Price();
		Product product = new Product();
		Contributor contributor = new Contributor();
		Accrual accrual = new Accrual();
		Date date = new Date();

		List<Menu> menuList = new ArrayList<Menu>();
		List<Source> sourceList = new ArrayList<Source>();
		List<Product> productList = new ArrayList<Product>();

		for (int i = 0; i < 1; i++) {
			// RecommendWebtoonDTO = resultList.get(i);
			// 상품 정보 (상품ID)
			identifier.setType("episode");
			identifier.setText("H090124341");

			// 메뉴 정보
			menu.setType("topClass");
			menu.setId("MN26");
			menu.setName("웹툰");
			menuList.add(menu);

			menu = new Menu();
			menu.setType("menuId");
			menu.setId("MN26004");
			menu.setName("웹툰/스릴러");
			menuList.add(menu);

			// contributor
			contributor.setName("장한나");

			accrual.setScore(4.5);

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
			product.setAccrual(accrual);
			product.setTitle(title);
			product.setRights(rights);
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

		responseVO = new RecommendWebtoonRes();
		responseVO.setProductList(productList);

		CommonResponse commonResponse = new CommonResponse();
		commonResponse.setTotalCount(20);
		responseVO.setCommonResponse(commonResponse);
		// }
		return responseVO;
	}

}
