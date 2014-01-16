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
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.skplanet.storeplatform.framework.core.persistence.dao.CommonDAO;
import com.skplanet.storeplatform.sac.client.display.vo.category.CategoryWebtoonReq;
import com.skplanet.storeplatform.sac.client.display.vo.category.CategoryWebtoonRes;
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
import com.skplanet.storeplatform.sac.common.header.vo.SacRequestHeader;
import com.skplanet.storeplatform.sac.display.category.vo.CategoryWebtoonDTO;

/**
 * WebtoonList Service 인터페이스(CoreStoreBusiness) 구현체
 * 
 * Updated on : 2013. 12. 23. Updated by : 김형식, SK 플래닛.
 */
@Service
public class CategoryWebtoonServiceImpl implements CategoryWebtoonService {

	private final Logger log = LoggerFactory.getLogger(this.getClass());

	@Autowired
	@Qualifier("sac")
	private CommonDAO commonDAO;

	/**
	 * <pre>
	 * 일반 카테고리 웹툰 리스트 조회.
	 * </pre>
	 * 
	 * @param CategoryWebtoonReq
	 * @return CategoryWebtoonRes 리스트
	 * @throws Exception
	 */
	@Override
	public CategoryWebtoonRes searchWebtoonList(SacRequestHeader header, CategoryWebtoonReq req) {

		CategoryWebtoonRes responseVO = null;

		Integer totalCount = 0;

		/** TODO 2. 테스트용 if 헤더 셋팅 */
		if (header.getTenantHeader() == null) {
			req.setTenantId("S01");
			req.setImageCd("DP000196");
			req.setLangCd("ko");
			req.setDeviceModelCd("SHV-E330SSO");
		} else {
			req.setTenantId("S01");
			req.setSystemId(header.getTenantHeader().getSystemId());
			req.setImageCd("DP000196");
			req.setLangCd("ko");
			req.setDeviceModelCd("SHV-E330SSO");
		}

		List<CategoryWebtoonDTO> resultList = this.commonDAO.queryForList("Webtoon.getWebtoonList", req,
				CategoryWebtoonDTO.class);

		if (resultList != null) {
			CategoryWebtoonDTO webtoonDto = new CategoryWebtoonDTO();

			// Response VO를 만들기위한 생성자
			Product product = null;
			Identifier identifier = null;
			Menu menu = null;
			Rights rights = null;
			Title title = null;
			Source source = null;
			Price price = null;
			Contributor contributor = null;
			Accrual accrual = null;
			Date date = null;

			List<Menu> menuList = null;
			List<Source> sourceList = null;
			List<Product> productList = new ArrayList<Product>();

			for (int i = 0; i < resultList.size(); i++) {
				webtoonDto = resultList.get(i);
				product = new Product();

				// 상품 정보 (상품ID)
				identifier = new Identifier();
				identifier.setType("episode");
				identifier.setText(webtoonDto.getProdId());

				// 메뉴 정보
				menuList = new ArrayList<Menu>();
				menu = new Menu();
				menu.setType("topClass");
				menu.setId(webtoonDto.getUpMenuId());
				menu.setName(webtoonDto.getUpMenuName());
				menuList.add(menu);

				menu = new Menu();
				menu.setType("menuId");
				menu.setId(webtoonDto.getMenuId());
				menu.setName(webtoonDto.getMenuNm());
				menuList.add(menu);

				// contributor
				contributor = new Contributor();
				contributor.setName(webtoonDto.getArtist1Nm());

				accrual = new Accrual();
				accrual.setScore(Double.parseDouble(webtoonDto.getAvgScore()));

				// 상품 정보 (상품명)
				title = new Title();
				title.setPrefix(webtoonDto.getIconYn());
				title.setText(webtoonDto.getProdNm());

				// 이미지 정보
				sourceList = new ArrayList<Source>();
				source = new Source();
				source.setType("thumbnail");
				source.setUrl(webtoonDto.getFilePos());
				sourceList.add(source);

				// 업데이트 날짜
				date = new Date();
				date.setType("date/reg");
				date.setText(webtoonDto.getUpdDt());

				// 데이터 매핑
				product.setIdentifier(identifier);
				product.setMenuList(menuList);
				product.setContributor(contributor);
				product.setAccrual(accrual);
				product.setTitle(title);
				product.setRights(rights);
				product.setSourceList(sourceList);
				product.setPrice(price);
				product.setDate(date);
				totalCount = webtoonDto.getTotalCount();
				productList.add(i, product);
			}

			responseVO = new CategoryWebtoonRes();
			responseVO.setProductList(productList);

			CommonResponse commonResponse = new CommonResponse();
			commonResponse.setTotalCount(totalCount);
			responseVO.setCommonResponse(commonResponse);
		}
		return responseVO;
	}

}
