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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.skplanet.storeplatform.framework.core.exception.StorePlatformException;
import com.skplanet.storeplatform.framework.core.persistence.dao.CommonDAO;
import com.skplanet.storeplatform.framework.core.util.StringUtils;
import com.skplanet.storeplatform.purchase.client.history.vo.ExistenceScRes;
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
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.product.Chapter;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.product.Contributor;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.product.Distributor;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.product.Play;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.product.Product;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.product.Rights;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.product.Store;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.product.Support;
import com.skplanet.storeplatform.sac.display.common.DisplayCommonUtil;
import com.skplanet.storeplatform.sac.display.common.constant.DisplayConstants;
import com.skplanet.storeplatform.sac.display.common.service.DisplayCommonService;
import com.skplanet.storeplatform.sac.display.epub.vo.EpubDetail;
import com.skplanet.storeplatform.sac.display.epub.vo.MgzinSubscription;

/**
 * EPUB Service
 *
 * Updated on : 2014-01-09 Updated by : 임근대, SK플래닛.
 */
@Service
public class EpubServiceImpl implements EpubService {

	private static final Logger logger = LoggerFactory.getLogger(EpubServiceImpl.class);

	@Autowired
	@Qualifier("sac")
	private CommonDAO commonDAO;

    @Autowired
    private DisplayCommonService commonService;

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
        Map<String, Object> param = new HashMap<String, Object>();
        param.put("tenantId", req.getTenantId());
        param.put("channelId", req.getChannelId());
        param.put("langCd", req.getLangCd());
        param.put("deviceModel", req.getDeviceModel());
        param.put("imgCd", DisplayConstants.DP_EBOOK_COMIC_REPRESENT_IMAGE_CD);
        param.put("virtualDeviceModelNo", DisplayConstants.DP_ANY_PHONE_4MM);
		EpubDetail epubDetail = this.getEpubChannel(param);
		//logger.debug("epubDetail={}", epubDetail);

        if(epubDetail != null) {

            String sMetaClsCd = epubDetail.getMetaClsfCd();

            MgzinSubscription mzinSubscription = null;
            // 잡지인 경우 정기구독 정보 제공
            if (sMetaClsCd.equals("CT24") || sMetaClsCd.equals("CT26")) {
                mzinSubscription = getMgzinSubscription(param);
            }

            this.mapProduct(param, product, epubDetail, mzinSubscription);


            res.setProduct(product);
        } else {
            throw new StorePlatformException("SAC_DSP_0009");
        }
		if(epubDetail != null) {

			String sMetaClsCd = epubDetail.getMetaClsfCd();

			MgzinSubscription mzinSubscription = null;
			// 잡지인 경우 정기구독 정보 제공
			if (sMetaClsCd.equals("CT24") || sMetaClsCd.equals("CT26")) {
				mzinSubscription = getMgzinSubscription(param);
			}

			this.mapProduct(param, product, epubDetail, mzinSubscription);

			// 단행인 경우 시리즈 정보를 제공
			if(StringUtils.equals(sMetaClsCd, "CT19")) {

                param.put("orderedBy", org.apache.commons.lang3.StringUtils.defaultString(req.getOrderedBy(), DisplayConstants.DP_ORDEREDBY_TYPE_RECENT));
                param.put("offset", 1);
                param.put("count", 1);

                EpubSeriesReq epubSeriesReq = new EpubSeriesReq();
                epubSeriesReq.setTenantId(req.getTenantId());
                epubSeriesReq.setChannelId(req.getChannelId());
                epubSeriesReq.setLangCd(req.getLangCd());
                epubSeriesReq.setDeviceModel(req.getDeviceModel());

				List<EpubDetail> subProductList = getEpubSeries(param);

                List<ExistenceScRes> existenceScResList = getExistenceScReses(req.getTenantId(), req.getUserKey(), req.getDeviceKey(), subProductList);
				this.mapSubProductList(param, product, subProductList, existenceScResList);
			}
            res.setProduct(product);
        } else {
            throw new StorePlatformException("SAC_DSP_0009");
        }

		return res;
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
        Map<String, Object> param = new HashMap<String, Object>();
        param.put("tenantId", req.getTenantId());
        param.put("channelId", req.getChannelId());
        param.put("langCd", req.getLangCd());
        param.put("deviceModel", req.getDeviceModel());
        param.put("bookTypeCd", req.getBookTypeCd());
        param.put("virtualDeviceModelNo", DisplayConstants.DP_ANY_PHONE_4MM);
        param.put("orderedBy", StringUtils.defaultString(req.getOrderedBy(), DisplayConstants.DP_ORDEREDBY_TYPE_RECENT));
        param.put("imgCd", DisplayConstants.DP_EBOOK_COMIC_REPRESENT_IMAGE_CD);
        param.put("offset", req.getOffset() == null ? 1 : req.getOffset());
        param.put("count", req.getCount() == null ? 20 : req.getCount());
        final EpubDetail epubDetail = getEpubChannel(param);

        if(epubDetail != null) {
            logger.debug("epubDetail={}", epubDetail);

            String sMetaClsCd = epubDetail.getMetaClsfCd();

            MgzinSubscription mzinSubscription = null;
            // 잡지인 경우 정기구독 정보 제공
            if (sMetaClsCd.equals("CT24") || sMetaClsCd.equals("CT26")) {
                mzinSubscription = getMgzinSubscription(param);
            }

            this.mapProduct(param, product, epubDetail, mzinSubscription);

            
            //orderedBy=noPayment 기구매 체크.
            
            
            
            // 단행인 경우 시리즈 정보를 제공
            if (sMetaClsCd.equals("CT19")) {
                List<EpubDetail> subProductList = getEpubSeries(param);

                List<ExistenceScRes> existenceScResList = getExistenceScReses(req.getTenantId(), req.getUserKey(), req.getDeviceKey(), subProductList);

                //this.mapSubProductList_dummy(product);
                this.mapSubProductList(param, product, subProductList, existenceScResList);
            }
            res.setProduct(product);
        } else {
            throw new StorePlatformException("SAC_DSP_0009");
        }


		return res;
	}

    /**
     *
     * @param param
     * @return
     */
    private MgzinSubscription getMgzinSubscription(Map<String, Object> param) {
        return this.commonDAO.queryForObject("EpubDetail.selectEpubSubscription", param, MgzinSubscription.class);
    }

    /**
     * 채널 정보 조회
     * @param param
     * @return
     */
    private EpubDetail getEpubChannel(Map<String, Object> param) {
        return this.commonDAO.queryForObject("EpubDetail.selectEpubChannel", param, EpubDetail.class);
    }

    /**
     * Epub 시리즈 조회
     * @param param
     * @return
     */
    private List<EpubDetail> getEpubSeries(Map<String, Object> param) {
        return this.commonDAO.queryForList("EpubDetail.selectEpubSeries", param, EpubDetail.class);
    }


    /**
     * 기구매 체크 (구매서버 연동)
     * @param tenantId
     * @param userKey
     * @param deviceKey
     * @param subProductList
     * @return
     */
    private List<ExistenceScRes> getExistenceScReses(String tenantId, String userKey, String deviceKey, List<EpubDetail> subProductList) {
    	
    	if(StringUtils.isNotEmpty(userKey) || StringUtils.isNotEmpty(deviceKey)) {
    		return new ArrayList<ExistenceScRes>();
    	}
    	
        List<ExistenceScRes> existenceScResList = null;
        if(subProductList != null && subProductList.size() > 0) {
            //기구매 체크
            List<String> episodeIdList = new ArrayList<String>();
            for(EpubDetail subProduct : subProductList) {
                if(StringUtils.isNotEmpty(subProduct.getPlayProdId())) {
                    episodeIdList.add(subProduct.getPlayProdId());
                } else if(StringUtils.isNotEmpty(subProduct.getStoreProdId())) {
                    episodeIdList.add(subProduct.getStoreProdId());
                }
            }
            try {
        		existenceScResList = commonService.checkPurchaseList(tenantId, userKey, deviceKey, episodeIdList);
            } catch (StorePlatformException e) {
                //ignore : 구매 연동 오류 발생해도 상세 조회는 오류 없도록 처리. 구매 연동오류는 VOC 로 처리한다.
                existenceScResList = new ArrayList<ExistenceScRes>();
            }
        }
        return existenceScResList;
    }

    /**
	 * Mapping Product
     * @param param
     * @param product
*          Project 정보
     * @param mapperVO
*          DB 조회 결과
     * @param mzinSubscription
     */
	private void mapProduct(Map<String, Object> param, Product product, EpubDetail mapperVO, MgzinSubscription mzinSubscription) {

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

        // 이용권한 정보
        product.setRights(this.mapRights(mapperVO, param, null));

		// 메뉴 정보
		product.setMenuList(this.mapMenuList(mapperVO));

		// 저작권 정보
        product.setContributor(this.mapContributor(mapperVO));

		// 판매자 정보
        product.setDistributor(this.mapDistributor(mapperVO));

		// 평점 정보
		product.setAccrual(this.mapAccurual(mapperVO));

		// 이미지 정보
		product.setSourceList(this.mapSourceList(mapperVO));

        // Book
		product.setBook(this.mapBook(mapperVO));
	}

    /**
     * Mapping Source List
     * @param mapperVO
     * @return
     */
    private List<Source> mapSourceList(EpubDetail mapperVO) {
        Source source;
        List<Source> sourceList;
        sourceList = new ArrayList<Source>();
        if (StringUtils.isNotEmpty(mapperVO.getImgPath()) && StringUtils.isNotEmpty(mapperVO.getImgNm())) {
        	source = new Source();
        	String imagePath = mapperVO.getImgPath() + mapperVO.getImgNm();
            source.setMediaType(DisplayCommonUtil.getMimeType(imagePath));
            source.setSize(mapperVO.getImgSize());
            source.setType(DisplayConstants.DP_THUMNAIL_SOURCE);
            source.setUrl(imagePath);
            sourceList.add(source);
        }
        
        return sourceList;
    }

    /**
     * Mapping Accurual
     * @param mapperVO
     * @return
     */
    private Accrual mapAccurual(EpubDetail mapperVO) {
        Accrual accrual;
        accrual = new Accrual();
        accrual.setDownloadCount(mapperVO.getPrchsCnt());
        accrual.setScore(mapperVO.getAvgEvluScore());
        accrual.setVoterCount(mapperVO.getPaticpersCnt());
        return accrual;
    }

    /**
     * Mapping Contributor
     * @param mapperVO
     * @return
     */
    private Contributor mapContributor(EpubDetail mapperVO) {
        Contributor contributor = null;
        Date date;
        if (DisplayConstants.DP_EBOOK_TOP_MENU_ID.equals(mapperVO.getTopMenuId())) { // 이북
            contributor = new Contributor();
            contributor.setName(mapperVO.getArtist1Nm());
            contributor.setPublisher(mapperVO.getChnlCompNm());

        } else if (DisplayConstants.DP_COMIC_TOP_MENU_ID.equals(mapperVO.getTopMenuId())) { // 코믹
            contributor = new Contributor();
            contributor.setName(mapperVO.getArtist1Nm());
            contributor.setPainter(mapperVO.getArtist2Nm());
            contributor.setPublisher(mapperVO.getChnlCompNm());
        }
        
        // 출판일
        if (StringUtils.isNotEmpty(mapperVO.getIssueDay())) {
            date = new Date();
            date.setType(DisplayConstants.DP_DATE_PUBLISH);
            date.setText(mapperVO.getIssueDay());
            contributor.setDate(date);
        }
        return contributor;
    }

    /**
     * Mapping Distributor
     * @param mapperVO
     * @return
     */
    private Distributor mapDistributor(EpubDetail mapperVO) {
        Distributor distributor = new Distributor();
        distributor.setSellerKey(mapperVO.getSellerMbrNo());
        distributor.setName(mapperVO.getExpoSellerNm());
        distributor.setTel(mapperVO.getExpoSellerTelno());
        distributor.setEmail(mapperVO.getExpoSellerEmail());
        return distributor;
    }

    /**
     * Mapping Book
     * @param mapperVO
     * @return
     */
	private Book mapBook(EpubDetail mapperVO) {

		Book book = new Book();

    	if (StringUtils.equals(mapperVO.getMetaClsfCd(), DisplayConstants.DP_SERIAL_META_CLASS_CD) && StringUtils.isNotEmpty(mapperVO.getChapter())) {
            Chapter chapter = new Chapter();
            chapter.setUnit(mapperVO.getChapterUnit());
            if(StringUtils.isNumeric(mapperVO.getChapter()))
                chapter.setText(Integer.parseInt(mapperVO.getChapter()));
            book.setChapter(chapter);
        }

        book.setScid(mapperVO.getSubContentsId());
        book.setSize(mapperVO.getFileSize());
        book.setTotalPages(mapperVO.getBookPageCnt());
		book.setTotalCount(mapperVO.getBookCount());
        book.setBookVersion(mapperVO.getProdVer());
        book.setStatus(mapperVO.getBookStatus());
        book.setType(mapperVO.getBookType());
		book.setSupportList(this.mapSupportList(mapperVO));
		
		return book;
	}


    /**
     * Mapping Menu List
     * @param mapperVO
     * @return
     */
	private List<Menu> mapMenuList(EpubDetail mapperVO) {
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
     * Mapping  rights
     * @param mapperVO
     * @param param
     * @param existenceMap
     * @return
	 */
	private Rights mapRights(EpubDetail mapperVO, Map<String, Object> param, Map<String, ExistenceScRes> existenceMap) {
		Rights rights = new Rights();
		rights.setAllow(mapperVO.getDwldAreaLimtYn());
		rights.setGrade(mapperVO.getProdGrdCd());
		// 소장 정보
		if (StringUtils.isNotEmpty(mapperVO.getStoreProdId())) {
			rights.setStore(this.mapStore(mapperVO, param, existenceMap));
		}
		// 대여 정보
		if (StringUtils.isNotEmpty(mapperVO.getPlayProdId())) {
			rights.setPlay(this.mapPlay(mapperVO, param, existenceMap));
		}

		return rights;
	}

    /**
     * Mapping Support List
     * @param mapperVO
     * @return
     */
	public List<Support> mapSupportList(EpubDetail mapperVO) {
		List<Support> supportList = new ArrayList<Support>();
		Support support = null;
		if (StringUtils.equals(mapperVO.getSupportPlay(), "Y")) {
			support = this.mapSupport(DisplayConstants.DP_EBOOK_PLAY_SUPPORT_NM, "Y");
			supportList.add(support);
		} else {
			support = this.mapSupport(DisplayConstants.DP_EBOOK_PLAY_SUPPORT_NM, "N");
			supportList.add(support);
		}

		if ("Y".equals(mapperVO.getSupportStore())) {
			support = this.mapSupport(DisplayConstants.DP_EBOOK_STORE_SUPPORT_NM, "Y");
			supportList.add(support);
		} else {
			support = this.mapSupport(DisplayConstants.DP_EBOOK_STORE_SUPPORT_NM, "N");
			supportList.add(support);
		}
		return supportList;
	}

    /**
     * Mapping Support
     * @param type
     * @param text
     * @return
     */
    private Support mapSupport(String type, String text) {
        Support support = new Support();
        support.setType(type);
        support.setText(text);
        return support;
    }

    /**
	 * Mapping Play
     * @param mapperVO
     * @param param
     * @param existenceMap
     * @return
	 */
	private Play mapPlay(EpubDetail mapperVO, Map<String, Object> param, Map<String, ExistenceScRes> existenceMap) {
		Play play = new Play();

		List<Identifier> identifierList = new ArrayList<Identifier>();
		identifierList.add(new Identifier(DisplayConstants.DP_EPISODE_IDENTIFIER_CD, mapperVO.getPlayProdId()));
		play.setIdentifierList(identifierList);

		ArrayList<Support> supportList = new ArrayList<Support>();
		supportList.add(this.mapSupport(DisplayConstants.DP_DRM_SUPPORT_NM, mapperVO.getPlayDrmYn()));
		play.setSupportList(supportList);

		mapperVO.setProdAmt(mapperVO.getPlayProdAmt());
		play.setPrice(this.mapPrice(mapperVO));

		if(StringUtils.isNotEmpty(mapperVO.getUsePeriodNm())) {
			Date date = new Date();
			date.setType(DisplayConstants.DP_DATE_USAGE_PERIOD);
			date.setText(mapperVO.getUsePeriodNm());
			play.setDate(date);
		}

		// 이용기간단위
		if (StringUtils.isNotEmpty(mapperVO.getPlayUsePeriodUnitCd())) {
			play.setUsePeriodUnitCd(mapperVO.getPlayUsePeriodUnitCd());
		}
		
        //Sales Status
        if(existenceMap != null && existenceMap.containsKey(mapperVO.getStoreProdId())) {
            String salesStatus = getSalesStatus(mapperVO, (String) param.get("userKey"), (String) param.get("deviceKey"));
            if(salesStatus != null)  play.setSalesStatus(salesStatus);
        }


		return play;
	}


	/**
	 * Mapping Store
     * @param mapperVO
     * @param param
     * @param existenceMap
     * @return
	 */
	private Store mapStore(EpubDetail mapperVO, Map<String, Object> param, Map<String, ExistenceScRes> existenceMap) {
		Store store = new Store();

		List<Identifier> identifierList = new ArrayList<Identifier>();
		identifierList.add(new Identifier(DisplayConstants.DP_EPISODE_IDENTIFIER_CD, mapperVO.getStoreProdId()));
		store.setIdentifierList(identifierList);

		ArrayList<Support> supportList = new ArrayList<Support>();
		supportList.add(this.mapSupport(DisplayConstants.DP_DRM_SUPPORT_NM, mapperVO.getStoreDrmYn()));
		store.setSupportList(supportList);

		mapperVO.setProdAmt(mapperVO.getStoreProdAmt());

		// 이용기간단위
		if (StringUtils.isNotEmpty(mapperVO.getStoreUsePeriodUnitCd())) {
			store.setUsePeriodUnitCd(mapperVO.getStoreUsePeriodUnitCd());
		}

        //Sales Status
        if(existenceMap != null && existenceMap.containsKey(mapperVO.getStoreProdId())) {
            String salesStatus = getSalesStatus(mapperVO, (String) param.get("userKey"), (String) param.get("deviceKey"));
            if(salesStatus != null)  store.setSalesStatus(salesStatus);
        }

		return store;
	}



	/**
	 * Mapping Price
	 * @param mapperVO
	 * @return
	 */
	private Price mapPrice(EpubDetail mapperVO) {
		Price price = new Price();
		price.setText(mapperVO.getProdAmt());
		price.setFixedPrice(mapperVO.getProdNetAmt());
		return price;
	}


	/**
	 * Mapping Sub ProdcutList
     * @param param
     * @param product
     * @param epubSeriesList
     * @param existenceScResList
     */
	private void mapSubProductList(Map<String, Object> param, Product product, List<EpubDetail> epubSeriesList, List<ExistenceScRes> existenceScResList) {

        List<Product> subProjectList = new ArrayList<Product>();

        if(epubSeriesList != null && epubSeriesList.size() > 0) {
            //logger.debug("epubSeriesList={}", epubSeriesList);
            EpubDetail temp = epubSeriesList.get(0);
            product.setSubProductTotalCount(temp.getTotalCount());

            //기구매 체크
            Map<String, ExistenceScRes> existenceMap = new HashMap<String, ExistenceScRes>();
            for(ExistenceScRes existenceScRes : existenceScResList) {
                existenceMap.put(existenceScRes.getProdId(), existenceScRes);
            }

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

                subProduct.setRights(mapRights(mapperVO, param, existenceMap));

                subProduct.setBook(mapBook(mapperVO));

                subProjectList.add(subProduct);

            }
        }
        product.setSubProductList(subProjectList);

	}

    /**
     * 판매 상태 조회
     * @param mapperVO
     * @param userKey
     * @param deviceKey
     * @return
     */
    private String getSalesStatus(EpubDetail mapperVO, String userKey, String deviceKey) {
        String salesStatus = null;
        //기구매 체크
        if (!mapperVO.getProdStatusCd().equals(DisplayConstants.DP_SALE_STAT_ING)) {
            // 04, 09, 10의 경우 구매이력이 없으면 상품 없음을 표시한다.
            if (DisplayConstants.DP_SALE_STAT_PAUSED.equals(mapperVO.getProdStatusCd()) ||
                    DisplayConstants.DP_SALE_STAT_RESTRIC_DN.equals(mapperVO.getProdStatusCd()) ||
                    DisplayConstants.DP_SALE_STAT_DROP_REQ_DN.equals(mapperVO.getProdStatusCd())) {
                if (!com.skplanet.storeplatform.framework.core.util.StringUtils.isEmpty(userKey) && !com.skplanet.storeplatform.framework.core.util.StringUtils.isEmpty(deviceKey)) {
                }
                else
                    salesStatus = "restricted";
            }
            else
                salesStatus = "restricted";
        }
        return salesStatus;
    }
}
