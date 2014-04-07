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
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.support.MessageSourceAccessor;
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
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.product.Point;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.product.Product;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.product.Rights;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.product.Store;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.product.Support;
import com.skplanet.storeplatform.sac.display.common.DisplayCommonUtil;
import com.skplanet.storeplatform.sac.display.common.constant.DisplayConstants;
import com.skplanet.storeplatform.sac.display.common.service.DisplayCommonService;
import com.skplanet.storeplatform.sac.display.common.vo.ProductImage;
import com.skplanet.storeplatform.sac.display.common.vo.TmembershipDcInfo;
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

	/** The message source accessor. */
	@Autowired
	private MessageSourceAccessor messageSourceAccessor;
    
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

		String userKey = StringUtils.defaultString(req.getUserKey());
		String deviceKey = StringUtils.defaultString(req.getDeviceKey());
		
		// --------------------------------------------------------
		// 1. Channel 정보 조회
		// --------------------------------------------------------
        Map<String, Object> param = new HashMap<String, Object>();
        param.put("tenantId", req.getTenantId());
        param.put("channelId", req.getChannelId());
        param.put("langCd", req.getLangCd());
        param.put("deviceModel", req.getDeviceModel());
        param.put("virtualDeviceModelNo", DisplayConstants.DP_ANY_PHONE_4MM);
        param.put("representImgCd", DisplayConstants.DP_EBOOK_COMIC_REPRESENT_IMAGE_CD);
        
        
		EpubDetail epubDetail = this.getEpubChannel(param);
		//logger.debug("epubDetail={}", epubDetail);
		

		if(epubDetail != null) {
			String sMetaClsCd = epubDetail.getMetaClsfCd();

			MgzinSubscription mzinSubscription = null;
			// 잡지인 경우 정기구독 정보 제공
			if (sMetaClsCd.equals(DisplayConstants.DP_MAGAZINE_META_CLASS_CD) || sMetaClsCd.equals(DisplayConstants.DP_INTERACTIVE_MAGAZINE_META_CLASS_CD)) {
				mzinSubscription = getMgzinSubscription(param);
			}
			
            //코믹의 경우 ScreenShot 제공
            List<ProductImage> screenshotList = null;
            if(epubDetail != null && StringUtils.equals(DisplayConstants.DP_COMIC_TOP_MENU_ID, epubDetail.getTopMenuId())) {
            	screenshotList = getScreenshotList(req.getChannelId(), req.getLangCd());
            }
            
			this.mapProduct(param, product, epubDetail, mzinSubscription, screenshotList);

			// 단편인 경우 시리즈 정보를 제공
			if(StringUtils.equals(sMetaClsCd, DisplayConstants.DP_BOOK_META_CLASS_CD)) {
                param.put("orderedBy", DisplayConstants.DP_ORDEREDBY_TYPE_RECENT);
                param.put("offset", 1);
                param.put("count", 1);
                
                //코믹 에피소드 이미지 코드
                if(StringUtils.equals(DisplayConstants.DP_COMIC_TOP_MENU_ID, epubDetail.getTopMenuId()))
                	param.put("representImgCd", DisplayConstants.DP_COMIC_EPISODE_REPRESENT_IMAGE_CD); //코믹 에피소드 대표이미지
                
				List<EpubDetail> subProductList = getEpubSeries(param);

                List<ExistenceScRes> existenceScResList = getExistenceScReses(req.getTenantId(), userKey, deviceKey, subProductList);
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
		String orderedBy = StringUtils.defaultString(req.getOrderedBy(), DisplayConstants.DP_ORDEREDBY_TYPE_RECENT);
		String userKey = StringUtils.defaultString(req.getUserKey());
		String deviceKey = StringUtils.defaultString(req.getDeviceKey());
		
		
        Map<String, Object> param = new HashMap<String, Object>();
        param.put("tenantId", req.getTenantId());
        param.put("channelId", req.getChannelId());
        param.put("langCd", req.getLangCd());
        param.put("deviceModel", StringUtils.defaultString(req.getDeviceModel()));
        param.put("bookTypeCd", StringUtils.defaultString(req.getBookTypeCd()));
        param.put("virtualDeviceModelNo", DisplayConstants.DP_ANY_PHONE_4MM);
        param.put("orderedBy", orderedBy);
        param.put("baseChapter", req.getBaseChapter());
        param.put("representImgCd", DisplayConstants.DP_EBOOK_COMIC_REPRESENT_IMAGE_CD);
        param.put("offset", req.getOffset() == null ? 1 : req.getOffset());
        param.put("count", req.getCount() == null ? 20 : req.getCount());
        final EpubDetail epubDetail = getEpubChannel(param);

        if(epubDetail != null) {
            String sMetaClsCd = epubDetail.getMetaClsfCd();

            MgzinSubscription mzinSubscription = null;
            // 잡지인 경우 정기구독 정보 제공
            if (sMetaClsCd.equals(DisplayConstants.DP_MAGAZINE_META_CLASS_CD) || sMetaClsCd.equals(DisplayConstants.DP_INTERACTIVE_MAGAZINE_META_CLASS_CD)) {
                mzinSubscription = getMgzinSubscription(param);
            }
            
            //코믹의 경우 ScreenShot 제공
            List<ProductImage> screenshotList = null;
            if(epubDetail != null && StringUtils.equals(DisplayConstants.DP_COMIC_TOP_MENU_ID, epubDetail.getTopMenuId())) {
            	screenshotList = getScreenshotList(req.getChannelId(), req.getLangCd());
            }
            
            this.mapProduct(param, product, epubDetail, mzinSubscription, screenshotList);

            //orderedBy=noPayment 기구매 체크.
            List<ExistenceScRes> existenceScResList = null;
			if(StringUtils.equals(orderedBy, DisplayConstants.DP_ORDEREDBY_TYPE_NONPAYMENT) && StringUtils.isNotEmpty(userKey) && StringUtils.isNotEmpty(deviceKey)) {
				List<String> episodeIdList = getEpisodeIdList(param);
				existenceScResList = commonService.checkPurchaseList(req.getTenantId(), userKey, deviceKey, episodeIdList);
				
				List<String> paymentProdIdList = new ArrayList<String>();
				for(ExistenceScRes existenceScRes : existenceScResList) {
					paymentProdIdList.add(existenceScRes.getProdId());
				}
				param.put("paymentProdIdList", paymentProdIdList);
			}

            //코믹 에피소드 이미지 코드
            if(StringUtils.equals(DisplayConstants.DP_COMIC_TOP_MENU_ID, epubDetail.getTopMenuId()))
            	param.put("representImgCd", DisplayConstants.DP_COMIC_EPISODE_REPRESENT_IMAGE_CD); //코믹 에피소드 대표이미지
            List<EpubDetail> subProductList = getEpubSeries(param);
            if(!StringUtils.equals(orderedBy, DisplayConstants.DP_ORDEREDBY_TYPE_NONPAYMENT) && StringUtils.isNotEmpty(userKey) && StringUtils.isNotEmpty(deviceKey)) {
            	//정렬방식이 미구매 순인 경우 필터링 데이터이기 떄문에 아닌 경우에만 구매 체크.
            	existenceScResList = getExistenceScReses(req.getTenantId(), userKey, deviceKey, subProductList);
            }
            this.mapSubProductList(param, product, subProductList, existenceScResList);
            
            res.setProduct(product);
        } else {
            throw new StorePlatformException("SAC_DSP_0009");
        }


		return res;
	}

	/**
	 * Mapping Screenshot
	 * @param param
	 * @return
	 */
	private List<ProductImage> getScreenshotList(String channelId, String langCd) {
		Map<String, String> param = new HashMap<String, String>();
		param.put("channelId", channelId);
		param.put("langCd", langCd);
		List<ProductImage> screenshotList = this.commonDAO.queryForList("EpubDetail.selectSourceList", param, ProductImage.class);
		return screenshotList;
	}

	
	/**
	 * EpisodeId List
	 * @param param
	 * @return
	 */
	private List<String> getEpisodeIdList(Map<String, Object> param) {
		return this.commonDAO.queryForList("EpubDetail.selectProdRshp", param, String.class);
	}

    /**
     * Magazine Subscription
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
    	logger.debug("param={}", param);
        return this.commonDAO.queryForObject("EpubDetail.selectEpubChannel", param, EpubDetail.class);
    }

    /**
     * Epub 시리즈 조회
     * @param param
     * @return
     */
    public List<EpubDetail> getEpubSeries(Map<String, Object> param) {
    	logger.debug("param={}", param);
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
	private void mapProduct(Map<String, Object> param, Product product, EpubDetail mapperVO, MgzinSubscription mzinSubscription, List<ProductImage> screenshotList) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd'T'HHmmssZ");
		
		// 상품ID
		List<Identifier> identifierList = new ArrayList<Identifier>();
		identifierList.add(new Identifier(DisplayConstants.DP_CHANNEL_IDENTIFIER_CD, mapperVO.getProdId()));
		product.setIdentifierList(identifierList);

		// 상품 정보 (상품명)
		product.setTitle(new Title(mapperVO.getProdNm()));

		//productDetailExplain (상품 상세설명)
		product.setProductDetailExplain(mapperVO.getProdDtlDesc());

		//productIntroduction (상품 소개 내용)
		product.setProductIntroduction(mapperVO.getProdIntrDscr());

		// SvcGrpCd
		product.setSvcGrpCd(mapperVO.getSvcGrpCd());
		
		//판매상태
		product.setSalesStatus(mapperVO.getProdStatusCd());
		
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
		product.setSourceList(this.mapSourceList(mapperVO, screenshotList));

        // Book
		product.setBook(this.mapBook(mapperVO));
		
		product.setDateList(mapDateList(mapperVO, sdf));
		
        //tmembership 할인율
        TmembershipDcInfo tmembershipDcInfo = commonService.getTmembershipDcRateForMenu(mapperVO.getTenantId(), mapperVO.getTopMenuId());
        if(tmembershipDcInfo != null) {
        	List<Point> pointList = null; 
        	
        	if(tmembershipDcInfo.getNormalDcRate() != null) {
        		pointList = new ArrayList<Point>();
		        Point point = new Point();
		        point.setName(DisplayConstants.DC_RATE_TMEMBERSHIP);
		        point.setType(DisplayConstants.DC_RATE_TYPE_NORMAL);
		        point.setDiscountRate(tmembershipDcInfo.getNormalDcRate());
		        pointList.add(point);
        	}
        	if(tmembershipDcInfo.getFreepassDcRate() != null) {
        		if(pointList == null) pointList = new ArrayList<Point>();
        		Point point = new Point();
        		point.setName(DisplayConstants.DC_RATE_TMEMBERSHIP);
        		point.setType(DisplayConstants.DC_RATE_TYPE_FREEPASS);
        		point.setDiscountRate(tmembershipDcInfo.getFreepassDcRate());
        		pointList.add(point);
        	}
	        
        	product.setPointList(pointList);
        }
	}

    /**
     * Mapping Source List
     * @param mapperVO
     * @return
     */
    private List<Source> mapSourceList(EpubDetail mapperVO, List<ProductImage> screenshotList) {
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
        
		// screenshot
        if(screenshotList != null) {
			for (ProductImage screenshotImage : screenshotList) {
				String imagePath = screenshotImage.getFilePath() + screenshotImage.getFileNm();
				Source screenshotSource = new Source();
				screenshotSource.setType(DisplayConstants.DP_SOURCE_TYPE_SCREENSHOT);
				screenshotSource.setSize(screenshotImage.getFileSize());
				screenshotSource.setMediaType(DisplayCommonUtil.getMimeType(imagePath));
				screenshotSource.setUrl(imagePath);
				sourceList.add(screenshotSource);
			}
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
        contributor = new Contributor();
        contributor.setName(mapperVO.getArtist1Nm());  		// 글작가
        contributor.setPainter(mapperVO.getArtist2Nm()); 	// 그림작가
        contributor.setPublisher(mapperVO.getChnlCompNm()); // 출판사
        contributor.setTranslator(mapperVO.getArtist3Nm()); // 번역자        

        
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

    	if (StringUtils.isNotEmpty(mapperVO.getChapter())
    			/* 단편인 경우 Chapter 정보 비노출 */
    			&& !StringUtils.equals(mapperVO.getMetaClsfCd(), DisplayConstants.DP_BOOK_META_CLASS_CD)
    			) {
            Chapter chapter = new Chapter();
            if(StringUtils.isNumeric(mapperVO.getChapter()))
                chapter.setText(Integer.parseInt(mapperVO.getChapter()));
            
            //chapter.setUnit(mapperVO.getChapterUnit());
            //챕터 단위 - 권/호/회 (언어처리)
            String chapterUnit = commonService.getEpubChapterUnit(mapperVO.getBookClsfCd());
            chapter.setUnit(chapterUnit);
            
            book.setChapter(chapter);
        }

        book.setScid(mapperVO.getSubContentsId());
        book.setSize(mapperVO.getFileSize());
        book.setTotalPages(mapperVO.getBookPageCnt());
		book.setTotalCount(mapperVO.getTotalCount());
        book.setBookVersion(mapperVO.getProdVer());
        book.setStatus(mapperVO.getBookStatus());
        
        
        // 채널 정보에서만 리턴
        // freeItem 정보를 위한 건수 조회
        if(StringUtils.equals(mapperVO.getBookClsfCd(), DisplayConstants.DP_BOOK_BOOK)) {
        	book.setBookCount(mapperVO.getBookCnt());
        	book.setBookFreeCount(mapperVO.getBookFreeCnt());
        	book.setType(DisplayConstants.DP_BOOK_TYPE_BOOK);
        } else if(StringUtils.equals(mapperVO.getBookClsfCd(), DisplayConstants.DP_BOOK_SERIAL)) {
        	book.setSerialCount(mapperVO.getSerialCnt());
        	book.setSerialFreeCount(mapperVO.getSerialCnt());
        	book.setType(DisplayConstants.DP_BOOK_TYPE_SERIAL);
        } else if(StringUtils.equals(mapperVO.getBookClsfCd(), DisplayConstants.DP_BOOK_MAGAZINE)) {
        	book.setSerialCount(mapperVO.getSerialCnt());
        	book.setSerialFreeCount(mapperVO.getSerialCnt());
        }
        
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
		support = this.mapSupport(DisplayConstants.DP_EBOOK_PLAY_SUPPORT_NM, mapperVO.getSupportPlay());
		supportList.add(support);
		
		support = this.mapSupport(DisplayConstants.DP_EBOOK_STORE_SUPPORT_NM, mapperVO.getSupportStore());
		supportList.add(support);

		return supportList;
	}

    /**
     * Mapping Support
     * @param type
     * @param text
     * @return
     */
    private Support mapSupport(String type, String text) {
        Support support = null;
        
        if(StringUtils.isNotEmpty(type) || StringUtils.isNotEmpty(type)) {
        	support = new Support();
        	support.setType(type);
        	support.setText(text);
        }
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

		play.setPrice(this.mapPrice(mapperVO.getPlayProdAmt(), mapperVO.getPlayProdNetAmt()));

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
		
		// 판매상태
		play.setSalesStatus(mapperVO.getPlayProdStatusCd());
		
        // 사용자 구매 가능 상태
        if(existenceMap != null && existenceMap.containsKey(mapperVO.getStoreProdId()) && param.containsKey("userKey")  && param.containsKey("deviceKey")) {
            String userPurStatus = getSalesStatus(mapperVO, (String) param.get("userKey"), (String) param.get("deviceKey"));
            if(userPurStatus != null)  play.setUserPurStatus(userPurStatus);
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

		//가격
		store.setPrice(this.mapPrice(mapperVO.getStoreProdAmt(), mapperVO.getStoreProdNetAmt()));

		// 이용기간단위
		if (StringUtils.isNotEmpty(mapperVO.getStoreUsePeriodUnitCd())) {
			store.setUsePeriodUnitCd(mapperVO.getStoreUsePeriodUnitCd());
		}
		
		// 판매상태
		store.setSalesStatus(mapperVO.getStoreProdStatusCd());
		
        // 사용자 구매 가능 상태
        if(existenceMap != null && existenceMap.containsKey(mapperVO.getStoreProdId()) && param.containsKey("userKey")  && param.containsKey("deviceKey")) {
            String userPurStatus = getSalesStatus(mapperVO, (String) param.get("userKey"), (String) param.get("deviceKey"));
            if(userPurStatus != null)  store.setUserPurStatus(userPurStatus);
        }
        

		return store;
	}



	/**
	 * Mapping Price
	 * @param mapperVO
	 * @return
	 */
	private Price mapPrice(Integer prodAmt, Integer prodNetAmt) {
		Price price = null;
		
		if(prodAmt != null || prodNetAmt != null) {
			price = new Price();
			price.setText(prodAmt);
			price.setFixedPrice(prodNetAmt);
		}
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
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd'T'HHmmssZ");
        List<Product> subProjectList = new ArrayList<Product>();

        if(epubSeriesList != null && epubSeriesList.size() > 0) {
            EpubDetail temp = epubSeriesList.get(0);
            product.setSubProductTotalCount(temp.getTotalCount());

            //기구매 체크
            Map<String, ExistenceScRes> existenceMap = new HashMap<String, ExistenceScRes>();
            if(existenceScResList != null) {
	            for(ExistenceScRes existenceScRes : existenceScResList) {
	                existenceMap.put(existenceScRes.getProdId(), existenceScRes);
	            }
            }

            for(EpubDetail mapperVO : epubSeriesList) {
                Product subProduct = new Product();

                List<Identifier> identifierList = new ArrayList<Identifier>();

                identifierList.add(new Identifier(DisplayConstants.DP_CONTENT_IDENTIFIER_CD, mapperVO.getCid()));
                subProduct.setIdentifierList(identifierList);

                subProduct.setTitle(new Title(mapperVO.getProdNm()));
                
                // 상품 설명
                subProduct.setProductExplain(mapperVO.getProdBaseDesc());
                subProduct.setProductDetailExplain(mapperVO.getProdDtlDesc());
                subProduct.setProductIntroduction(mapperVO.getProdIntrDscr());
                subProduct.setMenuList(mapMenuList(mapperVO));
                subProduct.setRights(mapRights(mapperVO, param, existenceMap));

                subProduct.setBook(mapBook(mapperVO));
                subProduct.setDateList(mapDateList(mapperVO, sdf));
                subProjectList.add(subProduct);

            }
        } else product.setSubProductTotalCount(0);
        
        product.setSubProductList(subProjectList);

	}


    /**
     * DataList
     * @param mapperVO
     * @param sdf
     * @return
     */
	private List<Date> mapDateList(EpubDetail mapperVO, SimpleDateFormat sdf) {
		List<Date> dateList = new ArrayList<Date>();
		if(mapperVO.getRegDt() != null) {
            Date date = new Date();
			date.setType(DisplayConstants.DP_DATE_REG);
			date.setText(sdf.format(mapperVO.getRegDt()));
			dateList.add(date);
		}

        if(StringUtils.isNotEmpty(mapperVO.getIssueDay())) {
            Date date = new Date();
            date.setType(DisplayConstants.DP_DATE_RELEASE);
            date.setText(mapperVO.getIssueDay());
            dateList.add(date);
        }
		return dateList;
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
