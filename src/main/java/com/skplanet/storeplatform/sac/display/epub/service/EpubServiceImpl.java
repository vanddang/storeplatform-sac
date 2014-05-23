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

import com.skplanet.storeplatform.sac.client.internal.purchase.vo.ExistenceListRes;
import com.skplanet.storeplatform.sac.client.internal.purchase.vo.ExistenceRes;
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
            List<ProductImage> screenshotList = getScreenshotList(epubDetail.getTopMenuId(), req.getChannelId(), req.getLangCd());
            
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

                ExistenceListRes existenceListRes = getExistenceScReses(req.getTenantId(), userKey, deviceKey, subProductList);
				this.mapSubProductList(param, product, subProductList, existenceListRes);
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
            screenshotList = getScreenshotList(epubDetail.getTopMenuId(), req.getChannelId(), req.getLangCd());

            this.mapProduct(param, product, epubDetail, mzinSubscription, screenshotList);

            //orderedBy=noPayment 기구매 체크.
            ExistenceListRes existenceListRes = null;
			if(StringUtils.equals(orderedBy, DisplayConstants.DP_ORDEREDBY_TYPE_NONPAYMENT) && StringUtils.isNotBlank(userKey) && StringUtils.isNotBlank(deviceKey)) {
				List<String> episodeIdList = getEpisodeIdList(param);
				existenceListRes = commonService.checkPurchaseList(req.getTenantId(), userKey, deviceKey, episodeIdList);
				
				List<String> paymentProdIdList = new ArrayList<String>();
				for(ExistenceRes existenceRes : existenceListRes.getExistenceListRes()) {
					paymentProdIdList.add(existenceRes.getProdId());
				}
				param.put("paymentProdIdList", paymentProdIdList);
			}

            //코믹 에피소드 이미지 코드
            if(StringUtils.equals(DisplayConstants.DP_COMIC_TOP_MENU_ID, epubDetail.getTopMenuId()))
            	param.put("representImgCd", DisplayConstants.DP_COMIC_EPISODE_REPRESENT_IMAGE_CD); //코믹 에피소드 대표이미지
            List<EpubDetail> subProductList = getEpubSeries(param);
            if(!StringUtils.equals(orderedBy, DisplayConstants.DP_ORDEREDBY_TYPE_NONPAYMENT) && StringUtils.isNotBlank(userKey) && StringUtils.isNotBlank(deviceKey)) {
            	//정렬방식이 미구매 순인 경우 필터링 데이터이기 떄문에 아닌 경우에만 구매 체크.
            	existenceListRes = getExistenceScReses(req.getTenantId(), userKey, deviceKey, subProductList);
            }
            this.mapSubProductList(param, product, subProductList, existenceListRes);
            
            res.setProduct(product);
        } else {
            throw new StorePlatformException("SAC_DSP_0009");
        }


		return res;
	}

	/**
	 * Mapping Screenshot
	 * @param topMenuId
     * @param channelId
     * @param langCd
	 * @return
	 */
	private List<ProductImage> getScreenshotList(String topMenuId, String channelId, String langCd) {
		Map<String, String> param = new HashMap<String, String>();
		param.put("channelId", channelId);
		param.put("langCd", langCd);
		List<ProductImage> screenshotList = null;
		
		if(StringUtils.equals(DisplayConstants.DP_COMIC_TOP_MENU_ID, topMenuId)) {
			screenshotList = this.commonDAO.queryForList("EpubDetail.selectComicSourceList", param, ProductImage.class);
		} else if(StringUtils.equals(DisplayConstants.DP_EBOOK_TOP_MENU_ID, topMenuId)) {
				screenshotList = this.commonDAO.queryForList("EpubDetail.selectEbookSourceList", param, ProductImage.class);
		}
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
    private ExistenceListRes getExistenceScReses(String tenantId, String userKey, String deviceKey, List<EpubDetail> subProductList) {
    	
    	if(StringUtils.isNotBlank(userKey) || StringUtils.isNotBlank(deviceKey)) {
            ExistenceListRes res = new ExistenceListRes();
            res.setExistenceListRes(new ArrayList<ExistenceRes>());
    		return res;
    	}

        ExistenceListRes res = null;
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
                res = commonService.checkPurchaseList(tenantId, userKey, deviceKey, episodeIdList);
            } catch (StorePlatformException e) {
                //ignore : 구매 연동 오류 발생해도 상세 조회는 오류 없도록 처리. 구매 연동오류는 VOC 로 처리한다.
                res = new ExistenceListRes();
                res.setExistenceListRes(new ArrayList<ExistenceRes>());
            }
        }
        return res;
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
		//04/14. AS-IS 이북에서만 저자설명 노출  
		if(StringUtils.equals(DisplayConstants.DP_EBOOK_TOP_MENU_ID, mapperVO.getTopMenuId())) {
			product.setAboutWriter(mapperVO.getProdBaseDesc());
		}

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
     * @param screenshotList
     * @return
     */
    private List<Source> mapSourceList(EpubDetail mapperVO, List<ProductImage> screenshotList) {
        Source source;
        List<Source> sourceList = new ArrayList<Source>();;
        // 대표 이미지 (thumbnail)
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
				source = new Source();
				source.setType(DisplayConstants.DP_SOURCE_TYPE_SCREENSHOT);
				source.setSize(screenshotImage.getFileSize());
				source.setMediaType(DisplayCommonUtil.getMimeType(imagePath));
				source.setUrl(imagePath);
				sourceList.add(source);
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
        // (2014-05-17) 이슈 : 멀티미디어 상품은 판매자 정보를 회원API를 통해 받아야 한다.
        // 전시 API 에서는 sellerKey만 내려주도록 한다.
        /*
        distributor.setName(mapperVO.getExpoSellerNm());
        distributor.setTel(mapperVO.getExpoSellerTelno());
        distributor.setEmail(mapperVO.getExpoSellerEmail());
        */
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
        
        
        // 만화 > 잡지 (CT22) : 업데이트 주기 정보를 TB_DP_PROD_DESC.PROD_INTR_DSCR 값에서 내려줌 (product.book.updateCycle)
        if(StringUtils.equals(mapperVO.getMetaClsfCd(), DisplayConstants.DP_MAGAZINE_COMIC_META_CLASS_CD)) {
        	book.setUpdateCycle(mapperVO.getProdIntrDscr());
        }
        
        // 채널 정보에서만 리턴
        // freeItem 정보를 위한 건수 조회
        
        
        /*
        //ASIS 로직
	     , ( CASE WHEN ( ( P.BOOK_CNT > 0 AND P.SERIAL_CNT = 0 ) AND P.BOOK_FREE_CNT > 0 ) THEN
	           '전체 '||P.BOOK_CNT||'권 중 무료 '||P.BOOK_FREE_CNT||'권'
	      WHEN ( ( P.SERIAL_CNT > 0 AND P.BOOK_CNT = 0 ) AND P.SERIAL_FREE_CNT > 0 ) THEN
	           '전체 '||P.SERIAL_CNT||'회 중 무료 '||P.SERIAL_FREE_CNT||'회'
	      WHEN ( P.SERIAL_CNT > 0 AND BOOK_CNT > 0 ) THEN
	           ( CASE WHEN P.SERIAL_FREE_CNT > 0 OR P.BOOK_FREE_CNT > 0 THEN
	                  ( CASE WHEN P.SERIAL_FREE_CNT > 0 THEN '연재물 '|| P.SERIAL_FREE_CNT || '회' END )||
	                  ( CASE WHEN P.BOOK_FREE_CNT > 0 THEN
	                              CONCAT( ( CASE WHEN P.SERIAL_FREE_CNT > 0 THEN ', ' END )
	                                    , '단행본 '|| P.BOOK_FREE_CNT || '권' )
	                    END ) || ' 무료'
	              END )
	  	  END )  AS FREE_ITEM
         */
        Integer bookCnt = mapperVO.getBookCnt() == null ? 0 : mapperVO.getBookCnt();
        Integer bookFreeCnt = mapperVO.getBookFreeCnt() == null ? 0 : mapperVO.getBookFreeCnt();
        Integer serialCnt = mapperVO.getSerialCnt() == null ? 0 : mapperVO.getSerialCnt();
        Integer serialFreeCnt = mapperVO.getSerialFreeCnt() == null ? 0 : mapperVO.getSerialFreeCnt();
        Integer magazineCnt = mapperVO.getMagazineCnt() == null ? 0 : mapperVO.getMagazineCnt();
        Integer magazineFreeCnt = mapperVO.getMagazineFreeCnt() == null ? 0 : mapperVO.getMagazineFreeCnt();
        
        //ASIS FreeItem 로직 구현
        //단행본만 있는 경우 : 전체 x 권 중 무료 x 권
        //연재물만 있는 경우 : 전체 x 회 중 무료 x 회
        //연재/단행 있는 경우 : 연재물 x 회, 단행본 x권
        book.setBookCount(bookCnt);
        book.setSerialCount(serialCnt);
        book.setMagazineCount(magazineCnt);
        
        if((bookCnt > 0 && serialCnt == 0) && bookFreeCnt > 0) {
        	book.setBookFreeCount(bookFreeCnt);
        } else if ((serialCnt > 0 && bookCnt == 0) && serialFreeCnt > 0) {
        	book.setSerialFreeCount(serialFreeCnt);
        } else if (serialCnt > 0 && bookCnt > 0) {
        	book.setBookFreeCount(bookFreeCnt);
        	book.setSerialFreeCount(serialFreeCnt);
        }
        
        if(StringUtils.equals(mapperVO.getBookClsfCd(), DisplayConstants.DP_BOOK_BOOK)) {
        	book.setType(DisplayConstants.DP_BOOK_TYPE_BOOK);
        } else if(StringUtils.equals(mapperVO.getBookClsfCd(), DisplayConstants.DP_BOOK_SERIAL)) {
        	book.setType(DisplayConstants.DP_BOOK_TYPE_SERIAL);
        } else if(StringUtils.equals(mapperVO.getBookClsfCd(), DisplayConstants.DP_BOOK_MAGAZINE)) {
        	book.setMagazineFreeCount(magazineFreeCnt);
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
	private Rights mapRights(EpubDetail mapperVO, Map<String, Object> param, Map<String, ExistenceRes> existenceMap) {
		Rights rights = new Rights();
		//rights.setAllow(mapperVO.getDwldAreaLimtYn());
		
		// eBook 상품에 대한 allow 설정
		if (StringUtils.equals(DisplayConstants.DP_EBOOK_TOP_MENU_ID, mapperVO.getTopMenuId())) {
			if (StringUtils.equals(DisplayConstants.DP_SUBSCRIPTION_CD, mapperVO.getChnlClsfCd())) {
				rights.setAllow(DisplayConstants.DP_RIGHTS_ALLOW_SUBSCRIPTION);
			}
		}

		
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
	private Play mapPlay(EpubDetail mapperVO, Map<String, Object> param, Map<String, ExistenceRes> existenceMap) {
		Play play = new Play();

		List<Identifier> identifierList = new ArrayList<Identifier>();
		identifierList.add(new Identifier(DisplayConstants.DP_EPISODE_IDENTIFIER_CD, mapperVO.getPlayProdId()));
		play.setIdentifierList(identifierList);

		ArrayList<Support> supportList = new ArrayList<Support>();
		supportList.add(this.mapSupport(DisplayConstants.DP_DRM_SUPPORT_NM, mapperVO.getPlayDrmYn()));
		play.setSupportList(supportList);

		play.setPrice(this.mapPrice(mapperVO.getPlayProdAmt(), mapperVO.getPlayProdNetAmt()));

		if(mapperVO.getPlayUsePeriod() != null) {
			play.setDate(DisplayCommonUtil.makeDateUsagePeriod(mapperVO.getPlayUsePeriodUnitCd(), mapperVO.getPlayUsePeriod(), mapperVO.getPlayUsePeriodUnitCdNm()));
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
	private Store mapStore(EpubDetail mapperVO, Map<String, Object> param, Map<String, ExistenceRes> existenceMap) {
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
	 * @param prodAmt
     * @param prodNetAmt
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
     * @param existenceListRes
     */
	private void mapSubProductList(Map<String, Object> param, Product product, List<EpubDetail> epubSeriesList, ExistenceListRes existenceListRes) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd'T'HHmmssZ");
        List<Product> subProjectList = new ArrayList<Product>();

        if(epubSeriesList != null && epubSeriesList.size() > 0) {
            EpubDetail temp = epubSeriesList.get(0);
            product.setSubProductTotalCount(temp.getTotalCount());

            //기구매 체크
            Map<String, ExistenceRes> existenceMap = new HashMap<String, ExistenceRes>();
            if(existenceListRes != null) {
	            for(ExistenceRes existenceRes : existenceListRes.getExistenceListRes()) {
	                existenceMap.put(existenceRes.getProdId(), existenceRes);
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
                subProduct.setSourceList(this.mapSourceList(mapperVO, null));
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
