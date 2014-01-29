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
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.product.Book;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.product.Contributor;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.product.Product;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.product.Rights;
import com.skplanet.storeplatform.sac.common.header.vo.DeviceHeader;
import com.skplanet.storeplatform.sac.common.header.vo.SacRequestHeader;
import com.skplanet.storeplatform.sac.common.header.vo.TenantHeader;
import com.skplanet.storeplatform.sac.display.category.vo.CategoryWebtoon;
import com.skplanet.storeplatform.sac.display.common.DisplayCommonUtil;
import com.skplanet.storeplatform.sac.display.common.constant.DisplayConstants;

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

		Integer totalCount = 0;

		TenantHeader tenantHeader = header.getTenantHeader();
		DeviceHeader deviceHeader = header.getDeviceHeader();

		this.log.debug("########################################################");
		this.log.debug("tenantHeader.getTenantId()	:	" + tenantHeader.getTenantId());
		this.log.debug("tenantHeader.getLangCd()	:	" + tenantHeader.getLangCd());
		this.log.debug("deviceHeader.getModel()		:	" + deviceHeader.getModel());
		this.log.debug("########################################################");

		req.setTenantId(tenantHeader.getTenantId());
		req.setLangCd(tenantHeader.getLangCd());
		req.setDeviceModelCd(deviceHeader.getModel());

		CategoryWebtoonRes responseVO = new CategoryWebtoonRes(); // Response 객체
		CommonResponse commonResponse = new CommonResponse();
		List<Product> productList = new ArrayList<Product>();

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

		/**
		 * TODO 삭제 필요.
		 */
		req.setImageCd("DP000196");

		List<CategoryWebtoon> resultList = this.commonDAO.queryForList("Webtoon.getWebtoonList", req,
				CategoryWebtoon.class);

		if (resultList != null) {
			CategoryWebtoon webtoonDto = new CategoryWebtoon();

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
			Book book = null;
			List<Identifier> identifierList = null;
			List<Menu> menuList = null;
			List<Source> sourceList = null;

			for (int i = 0; i < resultList.size(); i++) {
				webtoonDto = resultList.get(i);
				product = new Product();

				// 상품 정보 (상품ID)
				identifierList = new ArrayList<Identifier>();
				identifier = new Identifier();
				identifier.setType(DisplayConstants.DP_EPISODE_IDENTIFIER_CD);
				identifier.setText(webtoonDto.getProdId());
				identifierList.add(identifier);

				// 메뉴 정보
				menuList = new ArrayList<Menu>();
				menu = new Menu();
				menu.setType(DisplayConstants.DP_MENU_TOPCLASS_TYPE);
				menu.setId(webtoonDto.getTopMenuId());
				menu.setName(webtoonDto.getTopMenuName());
				menuList.add(menu);

				menu = new Menu();
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
				title.setPrefix(webtoonDto.getPreFix());
				title.setText(webtoonDto.getProdNm());

				// 완료 여부
				book = new Book();
				book.setStatus(webtoonDto.getComptYn());

				// 이미지 정보
				sourceList = new ArrayList<Source>();
				source = new Source();
				source.setMediaType(DisplayCommonUtil.getMimeType(webtoonDto.getFilePos()));
				// source.setSize(webtoonDto.getImgSize());
				source.setType(DisplayConstants.DP_THUMNAIL_SOURCE);
				source.setUrl(webtoonDto.getFilePos());
				sourceList.add(source);

				// 업데이트 날짜
				date = new Date();
				date.setType(DisplayConstants.DP_DATE_UPT_NM);
				date.setText(webtoonDto.getUpdDt());

				// 데이터 매핑
				product.setIdentifierList(identifierList);
				product.setMenuList(menuList);
				product.setContributor(contributor);
				product.setAccrual(accrual);
				product.setTitle(title);
				product.setBook(book);
				product.setRights(rights);
				product.setSourceList(sourceList);
				product.setPrice(price);
				product.setDate(date);
				totalCount = webtoonDto.getTotalCount();
				productList.add(i, product);
			}

			commonResponse.setTotalCount(totalCount);
			responseVO.setCommonResponse(commonResponse);
			responseVO.setProductList(productList);
		}
		return responseVO;
	}

}
