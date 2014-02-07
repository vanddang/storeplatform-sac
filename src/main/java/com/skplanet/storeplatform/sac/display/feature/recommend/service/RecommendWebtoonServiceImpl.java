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

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.skplanet.storeplatform.framework.core.exception.StorePlatformException;
import com.skplanet.storeplatform.framework.core.persistence.dao.CommonDAO;
import com.skplanet.storeplatform.sac.client.display.vo.feature.recommend.RecommendWebtoonSacReq;
import com.skplanet.storeplatform.sac.client.display.vo.feature.recommend.RecommendWebtoonSacRes;
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
import com.skplanet.storeplatform.sac.common.header.vo.SacRequestHeader;
import com.skplanet.storeplatform.sac.display.common.constant.DisplayConstants;
import com.skplanet.storeplatform.sac.display.common.service.DisplayCommonService;
import com.skplanet.storeplatform.sac.display.feature.recommend.vo.RecommendWebtoon;

/**
 * WebtoonList Service 인터페이스(CoreStoreBusiness) 구현체
 * 
 * Updated on : 2013. 12. 23. Updated by : 김형식, SK 플래닛.
 */
@Service
public class RecommendWebtoonServiceImpl implements RecommendWebtoonService {

	// private final Logger log = LoggerFactory.getLogger(this.getClass());

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
	 * @param RecommendWebtoonSacReq
	 * @return RecommendWebtoonRes 리스트
	 */

	@Override
	public RecommendWebtoonSacRes searchWebtoonList(SacRequestHeader header, RecommendWebtoonSacReq req) {

		RecommendWebtoonSacRes responseVO = null;

		// 헤더값 세팅
		req.setTenantId(header.getTenantHeader().getTenantId());
		req.setDeviceModelCd(header.getDeviceHeader().getModel());
		req.setLangCd(header.getTenantHeader().getLangCd());

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

		// tenantId 필수 파라미터 체크
		if (StringUtils.isEmpty(req.getTenantId())) {
			throw new StorePlatformException("SAC_DSP_0002", "tenantId", req.getTenantId());
		}

		// listId 필수 파라미터 체크
		if (StringUtils.isEmpty(req.getListId())) {
			throw new StorePlatformException("SAC_DSP_0002", "listId", req.getListId());
		}

		// 배치완료 기준일시 조회
		String stdDt = this.displayCommonService.getBatchStandardDateString(req.getTenantId(), req.getListId());

		// 기준일시 체크
		if (StringUtils.isEmpty(stdDt)) {
			throw new StorePlatformException("SAC_DSP_0002", "stdDt", stdDt);
		} else {
			req.setStdDt(stdDt);
		}

		Integer totalCount = 0;
		List<RecommendWebtoon> resultList = this.commonDAO.queryForList("Webtoon.getAdminWebtoonList", req,
				RecommendWebtoon.class);

		if (resultList != null) {
			RecommendWebtoon webtoonDto = new RecommendWebtoon();

			// Response VO를 만들기위한 생성자
			Product product = null;
			Identifier identifier = null;
			Menu menu = null;
			Rights rights = null;
			Title title = null;
			Source source = null;
			Price price = null;
			Book book = null;
			Contributor contributor = null;
			Accrual accrual = null;
			Date date = null;
			List<Identifier> identifierList = null;
			List<Menu> menuList = null;
			List<Source> sourceList = null;
			List<Product> productList = new ArrayList<Product>();

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
				menu.setId(webtoonDto.getUpMenuId());
				menu.setName(webtoonDto.getUpMenuName());
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

				// 완료 여부
				book = new Book();
				book.setStatus(webtoonDto.getComptYn());

				// 상품 정보 (상품명)
				title = new Title();
				title.setPrefix(webtoonDto.getPreFix());
				title.setText(webtoonDto.getProdNm());

				// 이미지 정보
				sourceList = new ArrayList<Source>();
				source = new Source();
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

			responseVO = new RecommendWebtoonSacRes();
			responseVO.setProductList(productList);

			CommonResponse commonResponse = new CommonResponse();
			commonResponse.setTotalCount(totalCount);
			responseVO.setCommonResponse(commonResponse);
		}

		return responseVO;
	}
}
