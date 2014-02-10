/*
 * Copyright (c) 2013 SK planet.
 * All right reserved.
 *
 * This software is the confidential and proprietary information of SK planet.
 * You shall not disclose such Confidential Information and
 * shall use it only in accordance with the terms of the license agreement
 * you entered into with SK planet.
 */
package com.skplanet.storeplatform.sac.display.epub.service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.skplanet.storeplatform.framework.core.persistence.dao.CommonDAO;
import com.skplanet.storeplatform.sac.client.display.vo.epub.EpubChannelReq;
import com.skplanet.storeplatform.sac.client.display.vo.epub.EpubChannelRes;
import com.skplanet.storeplatform.sac.client.display.vo.epub.EpubSeriesReq;
import com.skplanet.storeplatform.sac.client.display.vo.epub.EpubSeriesRes;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.common.Date;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.common.Identifier;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.common.Menu;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.common.Price;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.common.Source;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.common.Title;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.product.Accrual;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.product.Book;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.product.Contributor;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.product.Play;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.product.Product;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.product.Rights;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.product.Store;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.product.Support;
import com.skplanet.storeplatform.sac.display.common.DisplayCommonUtil;
import com.skplanet.storeplatform.sac.display.common.constant.DisplayConstants;
import com.skplanet.storeplatform.sac.display.epub.vo.EpubDetail;
import com.skplanet.storeplatform.sac.display.epub.vo.MgzinSubscription;

/**
 * EPUB Service
 *
 * Updated on : 2014-01-09 Updated by : 임근대, SK플래닛.
 */
@Service
@Transactional
public class EpubServceImpl implements EpubService {

	private static final Logger logger = LoggerFactory.getLogger(EpubServceImpl.class);

	@Autowired
	@Qualifier("sac")
	private CommonDAO commonDAO;



	/*
	 * (non-Javadoc)
	 *
	 * @see
	 * com.skplanet.storeplatform.sac.display.vod.service.VodService#searchVod(com.skplanet.storeplatform.sac.client
	 * .display.vo.vod.VodDetailReq)
	 */
	@Override
	public EpubChannelRes searchEpubChannel(EpubChannelReq req) {
		EpubChannelRes res = new EpubChannelRes();
		Product product = new Product();

		// --------------------------------------------------------
		// 1. Channel 정보 조회
		// --------------------------------------------------------
		EpubDetail epubDetail = this.commonDAO.queryForObject("EpubDetail.selectEpubChannel", req, EpubDetail.class);
		logger.debug("epubDetail={}", epubDetail);

		String sMetaClsCd = epubDetail.getMetaClsfCd();

		MgzinSubscription mzinSubscription = null;
		// 잡지인 경우 정기구독 정보 제공
		if (sMetaClsCd.equals("CT24") || sMetaClsCd.equals("CT26")) {
			mzinSubscription = this.commonDAO.queryForObject("EpubDetail.selectEpubSubscription", req, MgzinSubscription.class);
		}

		this.mapProduct(product, epubDetail, mzinSubscription);


		// 단행인 경우 시리즈 정보를 제공
		if (sMetaClsCd.equals("CT19")) {
			List<EpubDetail> epubSeriesList = null;
			epubSeriesList = this.commonDAO.queryForList("EpubDetail.selectEpubSeries", req, EpubDetail.class);

			//TODO:
			this.mapSubProductList_dummy(product);
			//this.mapSubProductList(product, epubSeriesList);
		}


		res.setProduct(product);

		return res;
	}


	/*
	 * (non-Javadoc)
	 *
	 * @see
	 * com.skplanet.storeplatform.sac.display.epub.service.EpubService#searchEpubSeries(com.skplanet.storeplatform.sac
	 * .client.display.vo.epub.EpubSeriesReq)
	 */
	@Override
	public EpubSeriesRes searchEpubSeries(EpubSeriesReq req) {
		EpubSeriesRes res = new EpubSeriesRes();

		Product product = new Product();
		// --------------------------------------------------------
		// 1. Channel 정보 조회
		// --------------------------------------------------------
		EpubDetail epubDetail = this.commonDAO.queryForObject("EpubDetail.selectEpubChannel", req, EpubDetail.class);
		logger.debug("epubDetail={}", epubDetail);

		String sMetaClsCd = epubDetail.getMetaClsfCd();

		MgzinSubscription mzinSubscription = null;
		// 잡지인 경우 정기구독 정보 제공
		if (sMetaClsCd.equals("CT24") || sMetaClsCd.equals("CT26")) {
			mzinSubscription = this.commonDAO.queryForObject("EpubDetail.selectEpubSubscription", req, MgzinSubscription.class);
		}

		this.mapProduct(product, epubDetail, mzinSubscription);


		// 단행인 경우 시리즈 정보를 제공
		if (sMetaClsCd.equals("CT19")) {
			List<EpubDetail> epubSeriesList = null;
			epubSeriesList = this.commonDAO.queryForList("EpubDetail.selectEpubSeries", req, EpubDetail.class);

			//TODO:
			this.mapSubProductList_dummy(product);
			//this.mapSubProductList(product, epubSeriesList);
		}

		res.setProduct(product);

		return res;
	}

	/**
	 *
	 * @param product
	 * @param epubDetail
	 */
	private void mapProduct(Product product, EpubDetail mapperVO, MgzinSubscription mzinSubscription) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd'T'HHmmssZ");
		Menu menu = null;
		Contributor contributor = null;
		Date date = null;
		Accrual accrual = null;
		Rights rights = null;
		Source source = null;
		Price price = null;
		Book book = null;
		Support support = null;

		List<Menu> menuList = null;
		List<Source> sourceList = null;
		List<Support> supportList = null;

		// 상품ID
		product.setIdentifier(new Identifier(DisplayConstants.DP_CHANNEL_IDENTIFIER_CD, mapperVO.getProdId()));

		// 상품 정보 (상품명)
		product.setTitle(new Title(mapperVO.getProdNm()));

		//-------------------------------------------
		// 메뉴 정보
		//-------------------------------------------
		menu = new Menu();
		menuList = new ArrayList<Menu>();
		menu.setType(DisplayConstants.DP_MENU_TOPCLASS_TYPE);
		menu.setId(mapperVO.getTopMenuId());
		menu.setName(mapperVO.getTopMenuNm());
		menuList.add(menu);

		menu = new Menu();
		menu.setId(mapperVO.getMenuId());
		menu.setName(mapperVO.getMenuNm());
		menuList.add(menu);

		menu = new Menu();
		menu.setType(DisplayConstants.DP_META_CLASS_MENU_TYPE);
		menu.setId(mapperVO.getMetaClsfCd());
		menuList.add(menu);
		product.setMenuList(menuList);

		//-------------------------------------------
		// 저작권 정보
		//-------------------------------------------
		if (DisplayConstants.DP_EBOOK_TOP_MENU_ID.equals(mapperVO.getTopMenuId())) { // 이북
			contributor = new Contributor();
			contributor.setName(mapperVO.getArtist1Nm());
			contributor.setPublisher(mapperVO.getChnlCompNm());

			//출판일
			if(mapperVO.getIssueDay() != null) {
				date = new Date();
				date.setType(DisplayConstants.DP_DATE_PUBLISH);
				date.setText(mapperVO.getIssueDay() == null ? "" : sdf.format(mapperVO.getIssueDay()));
				contributor.setDate(date);
			}
			product.setContributor(contributor);
		} else if (DisplayConstants.DP_COMIC_TOP_MENU_ID.equals(mapperVO.getTopMenuId())) { // 코믹
			contributor = new Contributor();
			contributor.setName(mapperVO.getArtist1Nm());
			contributor.setPainter(mapperVO.getArtist2Nm());
			contributor.setPublisher(mapperVO.getChnlCompNm());

			if(mapperVO.getIssueDay() != null) {
				date = new Date();
				date.setType(DisplayConstants.DP_DATE_PUBLISH);
				date.setText(mapperVO.getIssueDay() == null ? "" : sdf.format(mapperVO.getIssueDay()));
				contributor.setDate(date);
			}

			product.setContributor(contributor);
		}

		// 평점 정보
		accrual = new Accrual();
		accrual.setDownloadCount(mapperVO.getPrchsCnt());
		accrual.setScore(mapperVO.getAvgEvluScore());
		accrual.setVoterCount(mapperVO.getPaticpersCnt());
		product.setAccrual(accrual);

		// 이용권한 정보
		rights = new Rights();
		rights.setGrade(mapperVO.getProdGrdCd());
		product.setRights(rights);

		// 이미지 정보
		source = new Source();
		sourceList = new ArrayList<Source>();

		if(StringUtils.isNotEmpty(mapperVO.getImgPath())) {
			source.setMediaType(DisplayCommonUtil.getMimeType(mapperVO.getImgPath()));
			source.setSize(mapperVO.getImgSize());
			source.setType(DisplayConstants.DP_THUMNAIL_SOURCE);
			source.setUrl(mapperVO.getImgPath());
			sourceList.add(source);
		}

		product.setSourceList(sourceList);

		// 상품 정보 (상품설명)
		product.setProductExplain(mapperVO.getProdBaseDesc());

		// 상품 정보 (상품가격)
		price = new Price();
		price.setFixedPrice(mapperVO.getProdNetAmt());
		price.setText(mapperVO.getProdAmt());
		product.setPrice(price);


		//TODO: tableOfContents


		//TODO:
		book = new Book();
		//TODO: BookVersion
		//book.setBookVersion(bookVersion);
		book.setStatus(mapperVO.getBookStatus());
		book.setType(mapperVO.getBookType());
		book.setTotalCount(mapperVO.getBookCount());

		supportList = new ArrayList<Support>();
		support = new Support();
		support.setType(DisplayConstants.DP_EBOOK_STORE_SUPPORT_NM);
		support.setText(mapperVO.getSupportStore());
		supportList.add(support);
		support = new Support();
		support.setType(DisplayConstants.DP_EBOOK_PLAY_SUPPORT_NM);
		support.setText(mapperVO.getSupportPlay());
		supportList.add(support);

		book.setSupportList(supportList);

		product.setBook(book);
	}

	/**
	 *
	 * @param res
	 * @param product
	 */
	private void mapSubProductList(Product product, List<EpubDetail> epubSeriesList) {
		//TODO:
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see
	 * com.skplanet.storeplatform.sac.display.vod.service.VodService#searchVod(com.skplanet.storeplatform.sac.client
	 * .display.vo.vod.VodDetailReq)
	 */
	//@Override
	public EpubChannelRes searchEpub_dummy(EpubChannelReq req) {
		EpubChannelRes res = new EpubChannelRes();

		Product product = new Product();
		product.setIdentifier(new Identifier(DisplayConstants.DP_CHANNEL_IDENTIFIER_CD, "H001539415"));

		Menu menu = new Menu();
		menu.setId("DP000518");
		menu.setType("broadcast");

		Menu menu1 = new Menu();
		menu1.setId("DP18001");
		menu1.setName("broadcast/drama");

		product.setMenuList(new ArrayList<Menu>(Arrays.asList(menu, menu1)));

		product.setTitle(new Title("별에서 온 그대 7회"));

		this.mapSubProductList_dummy(product);

		res.setProduct(product);

		return res;
	}


	private void mapSubProductList_dummy(Product product) {
		// subProjectList
		List<Product> subProjectList = new ArrayList<Product>();
		Product product1 = new Product();

		product1.setIdentifier(new Identifier(DisplayConstants.DP_CHANNEL_IDENTIFIER_CD, "H001625436"));
		product1.setTitle(new Title("빨리 가려면 혼자 가고, 멀리 가려면 함께 가라"));

		Rights rights = new Rights();
		Support support = new Support();
		rights.setPlay(new Play(support, new Price(700), new Date("date/publish", "2013")));
		rights.setStore(new Store(support, new Price(700), new Date("date/publish", "2013")));
		rights.setDate(new Date("date/publish", "2013"));
		product1.setRights(rights);

		Book book = new Book();
		book.setStatus("continue");
		book.setTotalPages("13");
		book.setType("serial");

		List<Support> supportList = new ArrayList<Support>();
		support.setType("store");
		supportList.add(support);
		book.setSupportList(supportList);
		product1.setBook(new Book());
		subProjectList.add(product1);
		product.setSubProductList(subProjectList);
	}

}
