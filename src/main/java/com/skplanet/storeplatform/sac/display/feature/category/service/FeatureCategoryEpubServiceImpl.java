/*
 * Copyright (c) 2013 SK planet.
 * All right reserved.
 *
 * This software is the confidential and proprietary information of SK planet.
 * You shall not disclose such Confidential Information and
 * shall use it only in accordance with the terms of the license agreement
 * you entered into with SK planet.
 */
package com.skplanet.storeplatform.sac.display.feature.category.service;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.transaction.annotation.Transactional;

import com.skplanet.storeplatform.framework.core.persistence.dao.CommonDAO;
import com.skplanet.storeplatform.sac.api.util.StringUtil;
import com.skplanet.storeplatform.sac.client.display.vo.feature.category.FeatureCategoryEpubReq;
import com.skplanet.storeplatform.sac.client.display.vo.feature.category.FeatureCategoryEpubRes;
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
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.product.Support;
import com.skplanet.storeplatform.sac.common.header.vo.SacRequestHeader;
import com.skplanet.storeplatform.sac.display.common.service.DisplayCommonService;
import com.skplanet.storeplatform.sac.display.feature.category.vo.CategoryEpubDTO;

/**
 * 
 * 
 * Updated on : 2013. 12. 24. Updated by : 서영배, GTSOFT.
 */
@org.springframework.stereotype.Service
@Transactional
public class FeatureCategoryEpubServiceImpl implements FeatureCategoryEpubService {
	private transient Logger logger = LoggerFactory.getLogger(FeatureCategoryEpubServiceImpl.class);

	private final Logger log = LoggerFactory.getLogger(this.getClass());

	@Autowired
	@Qualifier("sac")
	private CommonDAO commonDAO;

	@Autowired
	private DisplayCommonService displayCommonService;

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.skplanet.storeplatform.sac.product.service.TotalRecommendService#searchTotalRecommendList(java.lang.String,
	 * java.lang.String, java.lang.String, java.lang.String, java.lang.String, int, int)
	 */
	@Override
	public FeatureCategoryEpubRes searchEpubList(FeatureCategoryEpubReq requestVO, SacRequestHeader header) {
		// TODO Auto-generated method stub
		// 공통 응답 변수 선언
		int totalCount = 0;
		FeatureCategoryEpubRes responseVO = null;
		CommonResponse commonResponse = null;

		String topMenuId = requestVO.getTopMenuId();
		String listId = requestVO.getListId();

		List<CategoryEpubDTO> resultList;

		// 필수 파라미터 체크
		if (StringUtils.isEmpty(topMenuId) || StringUtils.isEmpty(listId)) {
			this.logger.debug("----------------------------------------------------------------");
			this.logger.debug("필수 파라미터 부족");
			this.logger.debug("----------------------------------------------------------------");

			responseVO = new FeatureCategoryEpubRes();
			responseVO.setCommonResponse(new CommonResponse());
			return responseVO;
		}

		// 메뉴ID 유효값 체크 DP13 : 이북, DP14 : 만화
		if (!"DP13".equals(topMenuId) && !"DP14".equals(topMenuId)) {
			this.logger.debug("----------------------------------------------------------------");
			this.logger.debug("유효하지않은 탑메뉴ID");
			this.logger.debug("----------------------------------------------------------------");

			responseVO = new FeatureCategoryEpubRes();
			responseVO.setCommonResponse(new CommonResponse());
			return responseVO;
		}

		// 리스트ID 유효값 체크
		if (!"ADM000000013".equals(listId) && !"ADM000000002".equals(listId) && !"RNK000000002".equals(listId)
				&& !"RNK000000006".equals(listId)) {
			this.logger.debug("----------------------------------------------------------------");
			this.logger.debug("유효하지않은 리스트ID");
			this.logger.debug("----------------------------------------------------------------");

			responseVO = new FeatureCategoryEpubRes();
			responseVO.setCommonResponse(new CommonResponse());
			return responseVO;
		}

		// 시작점 ROW Default 세팅
		if (requestVO.getOffset() == null) {
			requestVO.setOffset(1);
		}
		// 페이지당 노출될 ROW 개수 Default 세팅
		if (requestVO.getCount() == null) {
			requestVO.setCount(20);
		}

		// 헤더값 세팅
		requestVO.setDeviceModelCd(header.getDeviceHeader().getModel());
		requestVO.setTenantId(header.getTenantHeader().getTenantId());
		requestVO.setLangCd("ko");
		// requestVO.setImageCd("DP000101");

		// 배치완료 기준일시 조회
		String stdDt = this.displayCommonService.getBatchStandardDateString(requestVO.getTenantId(), listId);

		// 기준일시 체크
		if (StringUtils.isEmpty(stdDt)) {
			this.logger.debug("----------------------------------------------------------------");
			this.logger.debug("배치완료 기준일시 정보 누락");
			this.logger.debug("----------------------------------------------------------------");

			responseVO = new FeatureCategoryEpubRes();
			responseVO.setCommonResponse(new CommonResponse());
			return responseVO;
		}
		requestVO.setStdDt(stdDt);

		// ADM000000013 : 운영자 추천, ADM000000002 : 신규 만화, RNK000000002 : 신규 이북, RNK000000006 : 인기코믹/인기도서
		if (listId.equals("ADM000000013")) {
			this.logger.debug("----------------------------------------------------------------");
			this.logger.debug("만화/이북 > 추천 > Tstore 추천 조회");
			this.logger.debug("----------------------------------------------------------------");

			resultList = this.commonDAO.queryForList("FeatureCategory.selectCategoryEpubRecomList", requestVO,
					CategoryEpubDTO.class);
		} else if (listId.equals("ADM000000002")) {
			this.logger.debug("----------------------------------------------------------------");
			this.logger.debug("만화 > 최신 조회");
			this.logger.debug("----------------------------------------------------------------");

			resultList = this.commonDAO.queryForList("FeatureCategory.selectCategoryEpubListDummy", requestVO,
					CategoryEpubDTO.class);
		} else if (listId.equals("RNK000000002")) {
			this.logger.debug("----------------------------------------------------------------");
			this.logger.debug("eBook > 최신 > 일반/장르 조회");
			this.logger.debug("----------------------------------------------------------------");

			resultList = this.commonDAO.queryForList("FeatureCategory.selectCategoryEpubListDummy", requestVO,
					CategoryEpubDTO.class);
		} else {
			this.logger.debug("----------------------------------------------------------------");
			this.logger.debug("만화/이북 > 추천 > 인기만화/인기도서 조회");
			this.logger.debug("----------------------------------------------------------------");

			resultList = this.commonDAO.queryForList("FeatureCategory.selectCategoryEpubListDummy", requestVO,
					CategoryEpubDTO.class);
		}

		List<Product> listVO = new ArrayList<Product>();

		CategoryEpubDTO categoryEpubDTO;
		Product product;
		Identifier identifier;
		Title title;
		Book book;
		Accrual accrual;
		Rights rights;
		Source source;
		Price price;
		Support support;
		Menu menu;
		Contributor contributor;

		// Response VO를 만들기위한 생성자
		List<Menu> menuList;
		List<Source> sourceList;
		List<Support> supportList;

		for (int i = 0; resultList != null && i < resultList.size(); i++) {

			categoryEpubDTO = resultList.get(i);
			product = new Product();
			identifier = new Identifier();
			title = new Title();
			book = new Book();
			accrual = new Accrual();
			rights = new Rights();
			source = new Source();
			price = new Price();
			support = new Support();
			contributor = new Contributor();

			// 상품ID
			identifier = new Identifier();

			// Response VO를 만들기위한 생성자
			menuList = new ArrayList<Menu>();
			sourceList = new ArrayList<Source>();
			supportList = new ArrayList<Support>();

			totalCount = categoryEpubDTO.getTotalCount();

			identifier.setType("channel");
			identifier.setText(categoryEpubDTO.getProdId());
			title.setText(categoryEpubDTO.getProdNm());

			menu = new Menu();
			menu.setId(categoryEpubDTO.getTopMenuId());
			menu.setName(categoryEpubDTO.getTopMenuNm());
			menu.setType("topClass");
			menuList.add(menu);
			menu = new Menu();
			menu.setId(categoryEpubDTO.getMenuId());
			menu.setName(categoryEpubDTO.getMenuNm());
			// menu.setType("");
			menuList.add(menu);
			menu = new Menu();
			menu.setId(categoryEpubDTO.getMetaClsfCd());
			menu.setName("ebook/series");
			menu.setType("metaClass");
			menuList.add(menu);

			book.setType(categoryEpubDTO.getBookType());
			book.setTotalPages(categoryEpubDTO.getBookPageCnt());

			book.setStatus(categoryEpubDTO.getBookStatus());

			support.setText(categoryEpubDTO.getSupportPlay());

			supportList.add(support);
			book.setSupportList(supportList);
			product.setBook(book);
			this.log.debug("setBook");
			contributor.setName(categoryEpubDTO.getArtist1Nm());
			contributor.setCompany(categoryEpubDTO.getChnlCompNm());
			if (!"".equals(StringUtil.nvl(categoryEpubDTO.getIssueDay(), ""))) {
				Date date = new Date();
				date.setType("date/reg");
				date.setText(categoryEpubDTO.getIssueDay());
				contributor.setDate(date);
			}
			this.log.debug("setCompany");

			accrual.setVoterCount(categoryEpubDTO.getPrchsCnt());
			accrual.setDownloadCount(categoryEpubDTO.getDwldCnt());
			accrual.setScore(3.3);
			this.log.debug("accrual");
			/*
			 * Rights grade
			 */
			rights.setGrade(categoryEpubDTO.getProdGrdCd());

			source.setMediaType("image/jpeg");
			source.setSize(categoryEpubDTO.getFileSize());
			source.setType("thumbNail");
			source.setUrl(categoryEpubDTO.getFilePath());
			sourceList.add(source);
			this.log.debug("sourceList");
			/*
			 * Price text
			 */
			price.setText(categoryEpubDTO.getProdAmt());
			price.setFixedPrice(categoryEpubDTO.getProdNetAmt());
			this.log.debug("price");
			// product = new Product();
			product.setIdentifier(identifier);
			product.setTitle(title);
			// support.setText(categoryEpubDTO.getDrmYn() + "|" + categoryEpubDTO.getPartParentClsfCd());
			// supportList.add(support);
			// product.setSupportList(supportList);
			product.setMenuList(menuList);

			product.setAccrual(accrual);
			product.setRights(rights);
			product.setProductExplain(categoryEpubDTO.getProdBaseDesc());
			product.setSourceList(sourceList);
			product.setPrice(price);
			product.setContributor(contributor);

			listVO.add(product);

		}
		responseVO = new FeatureCategoryEpubRes();
		commonResponse = new CommonResponse();
		commonResponse.setTotalCount(totalCount);
		this.log.debug(String.valueOf(listVO.size()));
		responseVO.setCommonResponse(commonResponse);
		responseVO.setProductList(listVO);
		return responseVO;
	}

}
