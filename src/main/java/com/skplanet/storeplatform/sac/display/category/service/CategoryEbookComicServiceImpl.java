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
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.skplanet.storeplatform.framework.core.persistence.dao.CommonDAO;
import com.skplanet.storeplatform.sac.client.display.vo.category.CategoryEbookComicReq;
import com.skplanet.storeplatform.sac.client.display.vo.category.CategoryEbookComicRes;
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
import com.skplanet.storeplatform.sac.common.header.vo.DeviceHeader;
import com.skplanet.storeplatform.sac.common.header.vo.SacRequestHeader;
import com.skplanet.storeplatform.sac.common.header.vo.TenantHeader;
import com.skplanet.storeplatform.sac.display.common.constant.DisplayConstants;
import com.skplanet.storeplatform.sac.display.meta.service.MetaInfoService;
import com.skplanet.storeplatform.sac.display.meta.vo.MetaInfo;
import com.skplanet.storeplatform.sac.display.meta.vo.ProductBasicInfo;
import com.skplanet.storeplatform.sac.display.response.ResponseInfoGenerateFacade;

/**
 * CategoryEbookComic Service 인터페이스(CoreStoreBusiness) 구현체
 * 
 * Updated on : 2014. 01. 06. Updated by : 이태희, SK 플래닛.
 */
@Service
@Transactional
public class CategoryEbookComicServiceImpl implements CategoryEbookComicService {
	private transient Logger logger = LoggerFactory.getLogger(CategoryEbookComicServiceImpl.class);

	@Autowired
	@Qualifier("sac")
	private CommonDAO commonDAO;

	@Autowired
	private MetaInfoService metaInfoService;

	@Autowired
	private ResponseInfoGenerateFacade responseInfoGenerateFacade;

	@Override
	public CategoryEbookComicRes searchEbookComicList(CategoryEbookComicReq req, SacRequestHeader header) {
		this.logger.debug("----------------------------------------------------------------");
		this.logger.debug("searchEbookComicList Service started!!");
		this.logger.debug("----------------------------------------------------------------");

		TenantHeader tenantHeader = header.getTenantHeader();
		DeviceHeader deviceHeader = header.getDeviceHeader();

		this.logger.debug("########################################################");
		this.logger.debug("tenantHeader.getTenantId()	:	" + tenantHeader.getTenantId());
		this.logger.debug("tenantHeader.getLangCd()		:	" + tenantHeader.getLangCd());
		this.logger.debug("deviceHeader.getModel()		:	" + deviceHeader.getModel());
		this.logger.debug("########################################################");

		req.setTenantId(tenantHeader.getTenantId());
		req.setLangCd(tenantHeader.getLangCd());
		req.setDeviceModelCd(deviceHeader.getModel());

		CategoryEbookComicRes caegoryEbookComicRes = new CategoryEbookComicRes();
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

		// 일반 카테고리 이북/코믹 상품 조회
		List<ProductBasicInfo> ebookComicList = this.commonDAO.queryForList("Category.selectCategoryEbookComicList",
				req, ProductBasicInfo.class);

		if (req.getDummy() == null) { // Dummy 가 아닐 경우

			if (!ebookComicList.isEmpty()) {
				Map<String, Object> reqMap = new HashMap<String, Object>();
				reqMap.put("tenantHeader", tenantHeader);
				reqMap.put("deviceHeader", deviceHeader);
				reqMap.put("prodStatusCd", DisplayConstants.DP_SALE_STAT_ING);
				for (ProductBasicInfo productBasicInfo : ebookComicList) {
					reqMap.put("productBasicInfo", productBasicInfo);
					reqMap.put("imageCd", DisplayConstants.DP_EBOOK_COMIC_REPRESENT_IMAGE_CD);
					MetaInfo retMetaInfo = this.metaInfoService.getEbookComicMetaInfo(reqMap);

					if (retMetaInfo != null) {
						if (DisplayConstants.DP_EBOOK_TOP_MENU_ID.equals(retMetaInfo.getTopMenuId())) {
							Product product = this.responseInfoGenerateFacade.generateEbookProduct(retMetaInfo);
							productList.add(product);
						} else if (DisplayConstants.DP_COMIC_TOP_MENU_ID.equals(retMetaInfo.getTopMenuId())) {
							Product product = this.responseInfoGenerateFacade.generateComicProduct(retMetaInfo);
							productList.add(product);
						}
					}
				}
				commonResponse.setTotalCount(ebookComicList.get(0).getTotalCount());
				caegoryEbookComicRes.setProductList(productList);
				caegoryEbookComicRes.setCommonResponse(commonResponse);
			} else {
				// 조회 결과 없음
				commonResponse.setTotalCount(0);
				caegoryEbookComicRes.setProductList(productList);
				caegoryEbookComicRes.setCommonResponse(commonResponse);
			}

			// if (!ebookComicList.isEmpty()) {
			// CategoryEbookComic mapperVO = null;
			//
			// Identifier identifier = null;
			// Menu menu = null;
			// Contributor contributor = null;
			// Date date = null;
			// Accrual accrual = null;
			// Rights rights = null;
			// Title title = null;
			// Source source = null;
			// Price price = null;
			// Book book = null;
			// Support support = null;
			// Product product = null;
			//
			// List<Identifier> identifierList = null;
			// List<Menu> menuList = null;
			// List<Source> sourceList = null;
			// List<Support> supportList = null;
			//
			// for (int i = 0; i < ebookComicList.size(); i++) {
			// product = new Product();
			// mapperVO = ebookComicList.get(i);
			//
			// // 상품 정보 (상품ID)
			// identifierList = new ArrayList<Identifier>();
			// identifier = new Identifier();
			// identifier.setType(DisplayConstants.DP_CHANNEL_IDENTIFIER_CD);
			// identifier.setText(mapperVO.getProdId());
			// identifierList.add(identifier);
			// product.setIdentifierList(identifierList);
			//
			// // 메뉴 정보
			// menu = new Menu();
			// menuList = new ArrayList<Menu>();
			// menu.setType(DisplayConstants.DP_MENU_TOPCLASS_TYPE);
			// menu.setId(mapperVO.getTopMenuId());
			// menu.setName(mapperVO.getTopMenuNm());
			// menuList.add(menu);
			//
			// menu = new Menu();
			// menu.setId(mapperVO.getMenuId());
			// menu.setName(mapperVO.getMenuNm());
			// menuList.add(menu);
			//
			// menu = new Menu();
			// menu.setType(DisplayConstants.DP_META_CLASS_MENU_TYPE);
			// menu.setId(mapperVO.getMetaClsfCd());
			// menuList.add(menu);
			// product.setMenuList(menuList);
			//
			// // 저작권 정보
			// if (DisplayConstants.DP_EBOOK_TOP_MENU_ID.equals(mapperVO.getTopMenuId())) { // 이북
			// contributor = new Contributor();
			// contributor.setName(mapperVO.getArtist1Nm());
			// contributor.setPublisher(mapperVO.getChnlCompNm());
			//
			// date = new Date();
			// date.setType("date/publish");
			// date.setText(mapperVO.getIssueDay() == null ? "" : mapperVO.getIssueDay());
			// contributor.setDate(date);
			//
			// product.setContributor(contributor);
			// } else if (DisplayConstants.DP_COMIC_TOP_MENU_ID.equals(mapperVO.getTopMenuId())) { // 코믹
			// contributor = new Contributor();
			// contributor.setName(mapperVO.getArtist1Nm());
			// contributor.setPainter(mapperVO.getArtist2Nm());
			// contributor.setPublisher(mapperVO.getChnlCompNm());
			//
			// date = new Date();
			// date.setType("date/publish");
			// date.setText(mapperVO.getIssueDay() == null ? "" : mapperVO.getIssueDay());
			// contributor.setDate(date);
			//
			// product.setContributor(contributor);
			// }
			//
			// // 평점 정보
			// accrual = new Accrual();
			// accrual.setDownloadCount(mapperVO.getDwldCnt());
			// accrual.setScore(mapperVO.getAvgEvluScore());
			// accrual.setVoterCount(mapperVO.getPaticpersCnt());
			// product.setAccrual(accrual);
			//
			// // 이용권한 정보
			// rights = new Rights();
			// rights.setGrade(mapperVO.getProdGrdCd());
			// product.setRights(rights);
			//
			// // 상품 정보 (상품명)
			// title = new Title();
			// title.setText(mapperVO.getProdNm());
			// product.setTitle(title);
			//
			// // 이미지 정보
			// source = new Source();
			// sourceList = new ArrayList<Source>();
			// source.setMediaType(DisplayCommonUtil.getMimeType(mapperVO.getImgPath()));
			// source.setSize(mapperVO.getImgSize());
			// source.setType(DisplayConstants.DP_THUMNAIL_SOURCE);
			// source.setUrl(mapperVO.getImgPath());
			// sourceList.add(source);
			// product.setSourceList(sourceList);
			//
			// // 상품 정보 (상품설명)
			// product.setProductExplain(mapperVO.getProdBaseDesc());
			//
			// // 상품 정보 (상품가격)
			// price = new Price();
			// price.setFixedPrice(mapperVO.getProdNetAmt());
			// price.setText(mapperVO.getProdAmt());
			// product.setPrice(price);
			//
			// book = new Book();
			// book.setStatus(mapperVO.getBookStatus());
			// book.setType(mapperVO.getBookType());
			// book.setTotalCount(mapperVO.getBookCount());
			//
			// supportList = new ArrayList<Support>();
			// support = new Support();
			// support.setType(DisplayConstants.DP_EBOOK_STORE_SUPPORT_NM);
			// support.setText(mapperVO.getSupportStore());
			// supportList.add(support);
			// support = new Support();
			// support.setType(DisplayConstants.DP_EBOOK_PLAY_SUPPORT_NM);
			// support.setText(mapperVO.getSupportPlay());
			// supportList.add(support);
			//
			// book.setSupportList(supportList);
			//
			// product.setBook(book);
			//
			// // 데이터 매핑
			// productList.add(i, product);
			// }
			// commonResponse.setTotalCount(mapperVO.getTotalCount());
			// caegoryEbookComicRes.setProductList(productList);
			// caegoryEbookComicRes.setCommonResponse(commonResponse);
			// } else {
			// commonResponse.setTotalCount(1);
			// caegoryEbookComicRes.setProductList(productList);
			// caegoryEbookComicRes.setCommonResponse(commonResponse);
			// }

		} else {
			// Dummy Data
			Identifier identifier = null;
			Menu menu = null;
			Contributor contributor = null;
			Date date = null;
			Accrual accrual = null;
			Rights rights = null;
			Title title = null;
			Source source = null;
			Price price = null;
			Book book = null;
			Support support = null;
			Product product = null;

			List<Identifier> identifierList = null;
			List<Menu> menuList = null;
			List<Source> sourceList = null;
			List<Support> supportList = new ArrayList<Support>();

			for (int i = 0; i < 1; i++) {
				product = new Product();

				// DP13 : ebook, DP14 : 만화
				if ("DP13".equals(req.getTopMenuId())) {
					// 상품 정보 (상품ID)
					identifierList = new ArrayList<Identifier>();
					identifier = new Identifier();
					identifier.setType("channel");
					identifier.setText("H001579466");
					identifierList.add(identifier);
					product.setIdentifierList(identifierList);

					// 메뉴 정보
					menu = new Menu();
					menuList = new ArrayList<Menu>();
					menu.setType("topClass");
					menu.setId("DP13");
					menu.setName("ebook");
					menuList.add(menu);

					menu = new Menu();
					menu.setId("DP13003001");
					menu.setName("한국소설");
					menuList.add(menu);

					menu = new Menu();
					menu.setType("metaClass");
					menu.setId("CT19");
					menuList.add(menu);
					product.setMenuList(menuList);

					// 저작권 정보
					contributor = new Contributor();
					contributor.setName("이원호");
					contributor.setPublisher("네오픽션");

					date = new Date();
					date.setType("date/publish");
					date.setText("20120730");
					contributor.setDate(date);
					product.setContributor(contributor);

					// 평점 정보
					accrual = new Accrual();
					accrual.setDownloadCount(10);
					accrual.setScore(4.5);
					accrual.setVoterCount(123);
					product.setAccrual(accrual);

					// 이용권한 정보
					rights = new Rights();
					rights.setGrade("PD004401");
					product.setRights(rights);

					// 상품 정보 (상품명)
					title = new Title();
					title.setText("작전명 KT");
					product.setTitle(title);

					// 이미지 정보
					source = new Source();
					sourceList = new ArrayList<Source>();
					source.setMediaType("image/png");
					source.setSize(1234);
					source.setType("thumbnail");
					source.setUrl("/SMILE_DATA7/PEBOOK/201401/06/0002092536/1/0003917513/1/01_0002092536_480_679_1539_130x186.PNG");
					sourceList.add(source);
					product.setSourceList(sourceList);

					// 상품 정보 (상품설명)
					product.setProductExplain("500만 부 작가 이원호의 신작 장편소설 남한과 북한, 미국, 일본 정보기관의 치열한 첩보전 40년간 베일에 싸인 납치 사건의 전모를 파헤친다!");

					// 상품 정보 (상품가격)
					price = new Price();
					price.setFixedPrice(8100);
					price.setText(8100);
					product.setPrice(price);

					book = new Book();
					support = new Support();
					support.setType("store");
					supportList.add(support);
					book.setSupportList(supportList);
					product.setBook(book);
				} else if ("DP14".equals(req.getTopMenuId())) {
					// 상품 정보 (상품ID)
					identifierList = new ArrayList<Identifier>();
					identifier = new Identifier();
					identifier.setType("channel");
					identifier.setText("H001579674");
					identifierList.add(identifier);
					product.setIdentifierList(identifierList);

					// 메뉴 정보
					menu = new Menu();
					menuList = new ArrayList<Menu>();
					menu.setType("topClass");
					menu.setId("DP14");
					menu.setName("만화");
					menuList.add(menu);

					menu = new Menu();
					menu.setId("DP14001");
					menu.setName("무협");
					menuList.add(menu);

					menu = new Menu();
					menu.setType("metaClass");
					menu.setId("CT21");
					menuList.add(menu);
					product.setMenuList(menuList);

					// 저작권 정보
					contributor = new Contributor();
					contributor.setName("최봉학");
					contributor.setPainter("최봉학");
					contributor.setPublisher("코리아컨텐츠네트워크");

					date = new Date();
					date.setType("date/publish");
					date.setText("20140106");
					contributor.setDate(date);
					product.setContributor(contributor);

					// 평점 정보
					accrual = new Accrual();
					accrual.setDownloadCount(3);
					accrual.setScore(4.0);
					accrual.setVoterCount(18);
					product.setAccrual(accrual);

					// 이용권한 정보
					rights = new Rights();
					rights.setGrade("PD004401");
					product.setRights(rights);

					// 상품 정보 (상품명)
					title = new Title();
					title.setText("[최봉학]천리장검 12권");
					product.setTitle(title);

					// 이미지 정보
					source = new Source();
					sourceList = new ArrayList<Source>();
					source.setMediaType("image/png");
					source.setSize(1234);
					source.setType("thumbnail");
					source.setUrl("/SMILE_DATA7/PEBOOK/201401/06/0002092536/1/0003917513/1/01_0002092536_480_679_1539_130x186.PNG");
					sourceList.add(source);
					product.setSourceList(sourceList);

					// 상품 정보 (상품설명)
					product.setProductExplain("달밤 아래 고요해야 할 무덤가에선 때 아닌 난투가 벌어지고 있었다.");

					// 상품 정보 (상품가격)
					price = new Price();
					price.setFixedPrice(8100);
					price.setText(8100);
					product.setPrice(price);

					book = new Book();
					book.setStatus("continue");
					book.setTotalPages("13");
					book.setType("serial");

					support = new Support();
					support.setType("store");
					supportList.add(support);
					book.setSupportList(supportList);
					product.setBook(book);
				}

				// 데이터 매핑
				productList.add(i, product);
			}
			commonResponse.setTotalCount(1);
			caegoryEbookComicRes.setProductList(productList);
			caegoryEbookComicRes.setCommonResponse(commonResponse);
		}

		return caegoryEbookComicRes;
	}
}
