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

import com.skplanet.storeplatform.framework.core.persistence.dao.CommonDAO;
import com.skplanet.storeplatform.framework.core.util.StringUtils;
import com.skplanet.storeplatform.sac.client.display.vo.epub.EpubChannelReq;
import com.skplanet.storeplatform.sac.client.display.vo.epub.EpubChannelRes;
import com.skplanet.storeplatform.sac.client.display.vo.epub.EpubSeriesReq;
import com.skplanet.storeplatform.sac.client.display.vo.epub.EpubSeriesRes;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.common.*;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.product.*;
import com.skplanet.storeplatform.sac.display.common.DisplayCommonUtil;
import com.skplanet.storeplatform.sac.display.common.constant.DisplayConstants;
import com.skplanet.storeplatform.sac.display.epub.vo.EpubDetail;
import com.skplanet.storeplatform.sac.display.epub.vo.MgzinSubscription;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * EPUB Service
 *
 * Updated on : 2014-01-09 Updated by : 임근대, SK플래닛.
 */
@Service
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
		EpubDetail epubDetail = this.getEpubChannel(req);
		logger.debug("epubDetail={}", epubDetail);
		if(epubDetail != null) {

			String sMetaClsCd = epubDetail.getMetaClsfCd();

			MgzinSubscription mzinSubscription = null;
			// 잡지인 경우 정기구독 정보 제공
			if (sMetaClsCd.equals("CT24") || sMetaClsCd.equals("CT26")) {
				mzinSubscription = getMgzinSubscription(req);
			}

			this.mapProduct(product, epubDetail, mzinSubscription);

			// 단행인 경우 시리즈 정보를 제공
			if (sMetaClsCd.equals("CT19")) {
                EpubSeriesReq epubSeriesReq = new EpubSeriesReq();
                epubSeriesReq.setTenantId(req.getTenantId());
                epubSeriesReq.setChannelId(req.getChannelId());
                epubSeriesReq.setLangCd(req.getLangCd());
                epubSeriesReq.setDeviceModel(req.getDeviceModel());

				List<EpubDetail> epubSeriesList = getEpubSeries(epubSeriesReq);

				this.mapSubProductList(product, epubSeriesList);
			}
		}

		res.setProduct(product);

		return res;
	}

    private List<EpubDetail> getEpubSeries(EpubSeriesReq epubSeriesReq) {
        return this.commonDAO.queryForList("EpubDetail.selectEpubSeries", epubSeriesReq, EpubDetail.class);
    }

    /*
     * (non-Javadoc)
     *
     * @see
     * com.skplanet.storeplatform.sac.display.epub.service.EpubService#searchEpubSeries(com.skplanet.storeplatform.sac
     * .client.display.vo.epub.EpubDetailReq)
     */
	@Override
	public EpubSeriesRes searchEpubSeries(EpubSeriesReq req) {
        EpubSeriesRes res = new EpubSeriesRes();

		Product product = new Product();
		// 1. Channel 정보 조회
        EpubChannelReq channelReq = new EpubChannelReq();
        channelReq.setChannelId(req.getChannelId());
        channelReq.setDeviceModel(req.getDeviceModel());
        channelReq.setTenantId(req.getTenantId());
        channelReq.setLangCd(req.getLangCd());
        final EpubDetail epubDetail = getEpubChannel(channelReq);

        if(epubDetail != null) {
            logger.debug("epubDetail={}", epubDetail);

            String sMetaClsCd = epubDetail.getMetaClsfCd();

            MgzinSubscription mzinSubscription = null;
            // 잡지인 경우 정기구독 정보 제공
            if (sMetaClsCd.equals("CT24") || sMetaClsCd.equals("CT26")) {
                mzinSubscription = getMgzinSubscription(channelReq);
            }

            this.mapProduct(product, epubDetail, mzinSubscription);

            // 단행인 경우 시리즈 정보를 제공
            if (sMetaClsCd.equals("CT19")) {
                List<EpubDetail> epubSeriesList = getEpubSeries(req);

                // TODO:
                //this.mapSubProductList_dummy(product);
                this.mapSubProductList(product, epubSeriesList);
            }
        }

		res.setProduct(product);

		return res;
	}

    private MgzinSubscription getMgzinSubscription(EpubChannelReq channelReq) {
        return this.commonDAO.queryForObject("EpubDetail.selectEpubSubscription", channelReq, MgzinSubscription.class);
    }


    private EpubDetail getEpubChannel(EpubChannelReq req) {
        return this.commonDAO.queryForObject("EpubDetail.selectEpubChannel", req, EpubDetail.class);
    }


    /**
	 * Product
	 * @param product
     *          Project 정보
	 * @param mapperVO
     *          DB 조회 결과
     * @param mzinSubscription
	 */
	private void mapProduct(Product product, EpubDetail mapperVO, MgzinSubscription mzinSubscription) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd'T'HHmmssZ");
		Menu menu = null;
		Contributor contributor = null;
		Date date = null;
		Accrual accrual = null;
		Rights rights = null;
		Source source = null;
		Book book = null;
		Support support = null;

		List<Menu> menuList = null;
		List<Source> sourceList = null;
		List<Support> supportList = null;

		// 상품ID
		product.setIdentifier(new Identifier(DisplayConstants.DP_CHANNEL_IDENTIFIER_CD, mapperVO.getProdId()));

		// 상품 정보 (상품명)
		product.setTitle(new Title(mapperVO.getProdNm()));


		//productDetailExplain (상품 상세설명)
		product.setProductDetailExplain(mapperVO.getProdDtlDesc());

		//productIntroduction (상품 소개 내용)
		product.setProductIntroduction(mapperVO.getProdIntrDscr());

		//tableOfContents (목차 정보)
		product.setTableOfContents(mapperVO.getBookTbctns());

		//aboutWriter (작가 소개) - prodBaseDesc 컬럼
		product.setAboutWriter(mapperVO.getProdBaseDesc());


		//supportList (지원정보 리스트)

		//rights
		rights = this.getRights(mapperVO);

		// 메뉴 정보
		menuList = this.getMenuList(mapperVO);
		product.setMenuList(menuList);

		// 저작권 정보
		if (DisplayConstants.DP_EBOOK_TOP_MENU_ID.equals(mapperVO.getTopMenuId())) { // 이북
			contributor = new Contributor();
			contributor.setName(mapperVO.getArtist1Nm());
			contributor.setPublisher(mapperVO.getChnlCompNm());

			// 출판일
			if (mapperVO.getIssueDay() != null) {
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

			if (mapperVO.getIssueDay() != null) {
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

		if (StringUtils.isNotEmpty(mapperVO.getImgPath())) {
			source.setMediaType(DisplayCommonUtil.getMimeType(mapperVO.getImgPath()));
			source.setSize(mapperVO.getImgSize());
			source.setType(DisplayConstants.DP_THUMNAIL_SOURCE);
			source.setUrl(mapperVO.getImgPath());
			sourceList.add(source);
		}

		product.setSourceList(sourceList);


		// 상품 정보 (상품가격)
		/*
		price = new Price();
		price.setFixedPrice(mapperVO.getProdNetAmt());
		price.setText(mapperVO.getProdAmt());
		product.setPrice(price);
		 */
		book =  this.getBook(mapperVO);


		// TODO: BookVersion
		// book.setBookVersion(bookVersion);
		book.setStatus(mapperVO.getBookStatus());
		book.setType(mapperVO.getBookType());
		book.setTotalCount(mapperVO.getBookCount());

		//book > supportList (지원정보 리스트)
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


	private Book getBook(EpubDetail mapperVO) {
		Book book = new Book();
		Chapter chapter = new Chapter();
		chapter.setUnit(mapperVO.getChapter());
		book.setChapter(chapter);
		book.setTotalCount(mapperVO.getBookCount());

		// eBook 연재물인 경우에만 Type과 status를 적용한다.
		if (DisplayConstants.DP_SERIAL_META_CLASS_CD.equals(mapperVO.getMetaClsfCd())) {
			book.setType(DisplayConstants.DP_EBOOK_SERIAL_NM);
			/*
			if ("Y".equals(mapperVO.getComptYn())) {
				book.setStatus(DisplayConstants.DP_EBOOK_COMPLETED_NM);
			} else {
				book.setStatus(DisplayConstants.DP_EBOOK_CONTINUE_NM);
			}
			*/
		}
		//book.setSupportList(this.getSupportList(mapperVO));
		return book;
	}


	private List<Menu> getMenuList(EpubDetail mapperVO) {
		Menu menu;
		List<Menu> menuList;
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
		return menuList;

	}

	/**
	 * <pre>
	 * method 설명.
	 * </pre>
	 * @param mapperVO
	 * @return
	 */
	private Rights getRights(EpubDetail mapperVO) {
		Rights rights = new Rights();
		rights.setAllow(mapperVO.getDwldAreaLimtYn());
		rights.setGrade(mapperVO.getProdGrdCd());

		// 소장 정보
		if (StringUtils.isNotEmpty(mapperVO.getStoreProdId())) {
			rights.setStore(this.getStore(mapperVO));
		}
		// 대여 정보
		if (StringUtils.isNotEmpty(mapperVO.getPlayProdId())) {
			rights.setPlay(this.getPlay(mapperVO));
		}

		return rights;
	}

	public List<Support> getSupportList(EpubDetail mapperVO) {
		List<Support> supportList = new ArrayList<Support>();
		Support support = null;
		if ("Y".equals(mapperVO.getSupportPlay())) {
			support = this.getSupport(DisplayConstants.DP_EBOOK_PLAY_SUPPORT_NM, "Y");
			supportList.add(support);
		} else {
			support = this.getSupport(DisplayConstants.DP_EBOOK_PLAY_SUPPORT_NM, "N");
			supportList.add(support);
		}

		if ("Y".equals(mapperVO.getSupportStore())) {
			support = this.getSupport(DisplayConstants.DP_EBOOK_STORE_SUPPORT_NM, "Y");
			supportList.add(support);
		} else {
			support = this.getSupport(DisplayConstants.DP_EBOOK_STORE_SUPPORT_NM, "N");
			supportList.add(support);
		}
		return supportList;
	}

	/**
	 * <pre>
	 * method 설명.
	 * </pre>
	 * @param mapperVO
	 * @return
	 */
	private Play getPlay(EpubDetail mapperVO) {
		Play play = new Play();

		List<Identifier> identifierList = new ArrayList<Identifier>();
		identifierList.add(new Identifier(DisplayConstants.DP_EPISODE_IDENTIFIER_CD, mapperVO.getPlayProdId()));
		play.setIdentifierList(identifierList);

		ArrayList<Support> supportList = new ArrayList<Support>();
		supportList.add(this.getSupport(DisplayConstants.DP_DRM_SUPPORT_NM, mapperVO.getPlayDrmYn()));
		play.setSupportList(supportList);

		mapperVO.setProdAmt(mapperVO.getPlayProdAmt());
		//TODO :
		//mapperVO.setProdNetAmt(mapperVO.getPlayProdNetAmt());
		play.setPrice(this.getPrice(mapperVO));

		Date date = new Date();
		date.setType(DisplayConstants.DP_DATE_USAGE_PERIOD);
		//TODO :
		//date.setText(mapperVO.getUsePeriodNm());
		play.setDate(date);

		return play;
	}


	/**
	 * <pre>
	 * method 설명.
	 * </pre>
	 * @param mapperVO
	 * @return
	 */
	private Price getPrice(EpubDetail mapperVO) {
		Price price = new Price();
		price.setText(mapperVO.getProdAmt());
		price.setFixedPrice(mapperVO.getProdNetAmt());
		return price;
	}


	/**
	 * <pre>
	 * method 설명.
	 * </pre>
	 * @param mapperVO
	 * @return
	 */
	private Store getStore(EpubDetail mapperVO) {
		Store store = new Store();

		List<Identifier> identifierList = new ArrayList<Identifier>();
		identifierList.add(new Identifier(DisplayConstants.DP_EPISODE_IDENTIFIER_CD, mapperVO.getStoreProdId()));
		store.setIdentifierList(identifierList);

		ArrayList<Support> supportList = new ArrayList<Support>();
		supportList.add(this.getSupport(DisplayConstants.DP_DRM_SUPPORT_NM, mapperVO.getStoreDrmYn()));
		store.setSupportList(supportList);

		mapperVO.setProdAmt(mapperVO.getStoreProdAmt());
		//TODO:
		//mapperVO.setProdNetAmt(mapperVO.getStoreProdNetAmt());
		//store.setPrice(this.getPrice(mapperVO));

		return store;
	}


	/**
	 * <pre>
	 * method 설명.
	 * </pre>
	 * @param dpDrmSupportNm
	 * @param storeDrmYn
	 * @return
	 */
	private Support getSupport(String dpDrmSupportNm, String storeDrmYn) {
		// TODO Auto-generated method stub
		return null;
	}


	/**
	 *
	 * @param product
	 * @param epubSeriesList
	 */
	private void mapSubProductList(Product product, List<EpubDetail> epubSeriesList) {

        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd'T'HHmmssZ");

        List<Source> sourceList = null;
        Source source = null;
        Date date;

        List<Product> subProjectList = new ArrayList<Product>();

        if(epubSeriesList != null && epubSeriesList.size() > 0) {
            logger.debug("epubSeriesList={}", epubSeriesList);
            EpubDetail temp = epubSeriesList.get(0);
            product.setSubProductTotalCount(temp.getTotalCount());

            for(EpubDetail mapperVO : epubSeriesList) {
                Product subProduct = new Product();

                List<Identifier> identifierList = new ArrayList<Identifier>();

                identifierList.add(new Identifier(DisplayConstants.DP_EPISODE_IDENTIFIER_CD, mapperVO.getProdId()));
                identifierList.add(new Identifier(DisplayConstants.DP_CONTENT_IDENTIFIER_CD, mapperVO.getCid()));
                subProduct.setIdentifierList(identifierList);

                subProduct.setTitle(new Title(mapperVO.getProdNm()));

                // 상품 설명
                subProduct.setProductExplain(mapperVO.getProdBaseDesc());
                subProduct.setProductDetailExplain(mapperVO.getProdDtlDesc());
                subProduct.setProductIntroduction(mapperVO.getProdIntrDscr());

                //TODO: rights
                Rights rights = getRights(mapperVO);
                subProduct.setRights(rights);

                Book book = getBook(mapperVO);
                subProduct.setBook(book);

                subProjectList.add(subProduct);

            }
        }
        product.setSubProductList(subProjectList);

	}

	/*
	 * (non-Javadoc)
	 *
	 * @see
	 * com.skplanet.storeplatform.sac.display.vod.service.VodService#searchVod(com.skplanet.storeplatform.sac.client
	 * .display.vo.vod.VodDetailReq)
	 */
	// @Override
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

    /**
     * @deprecated dummy
     * @param product
     */
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
