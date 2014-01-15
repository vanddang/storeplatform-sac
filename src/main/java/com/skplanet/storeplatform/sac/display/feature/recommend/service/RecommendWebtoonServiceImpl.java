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

import java.util.ArrayList;
import java.util.List;

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
import com.skplanet.storeplatform.sac.common.header.vo.SacRequestHeader;
import com.skplanet.storeplatform.sac.display.common.service.DisplayCommonService;
import com.skplanet.storeplatform.sac.display.feature.recommend.vo.RecommendWebtoonDTO;

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
	@Autowired
	private DisplayCommonService displayCommonService;

	/**
	 * <pre>
	 * 운영자 추천 웹툰 리스트 조회.
	 * </pre>
	 * 
	 * @param RecommendWebtoonReq
	 * @return RecommendWebtoonRes 리스트
	 */

	@Override
	public RecommendWebtoonRes searchWebtoonList(SacRequestHeader header, RecommendWebtoonReq req) {

		RecommendWebtoonRes responseVO = null;

		/** TODO 2. 테스트용 if 헤더 셋팅 */
		if (header.getTenantHeader() == null) {
			req.setTenantId("S01");
			req.setImageCd("DP000196");
			req.setLangCd("ko");
			req.setDeviceModelCd("SHV-E330SSO");
		} else {
			req.setTenantId(header.getTenantHeader().getTenantId());
			req.setSystemId(header.getTenantHeader().getSystemId());
			req.setImageCd("DP000196");
			req.setLangCd("ko");
			req.setDeviceModelCd("SHV-E330SSO");
		}

		String stdDt = "";

		stdDt = this.displayCommonService.getBatchStandardDateString(req.getTenantId(), req.getListId());
		if (stdDt == null) {
			stdDt = "20130101000000";
		}
		req.setStdDt(stdDt);

		Integer totalCount = 0;
		List<RecommendWebtoonDTO> resultList = this.commonDAO.queryForList("Webtoon.getAdminWebtoonList", req,
				RecommendWebtoonDTO.class);

		if (resultList != null) {
			RecommendWebtoonDTO webtoonDto = new RecommendWebtoonDTO();

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

			responseVO = new RecommendWebtoonRes();
			responseVO.setProductList(productList);

			CommonResponse commonResponse = new CommonResponse();
			commonResponse.setTotalCount(totalCount);
			responseVO.setCommonResponse(commonResponse);
		}

		return responseVO;
	}
}
