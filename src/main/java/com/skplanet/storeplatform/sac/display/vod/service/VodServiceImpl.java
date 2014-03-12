/*
 * Copyright (c) 2013 SK planet.
 * All right reserved.
 *
 * This software is the confidential and proprietary information of SK planet.
 * You shall not disclose such Confidential Information and
 * shall use it only in accordance with the terms of the license agreement
 * you entered into with SK planet.
 */
package com.skplanet.storeplatform.sac.display.vod.service;

import com.skplanet.storeplatform.framework.core.exception.StorePlatformException;
import com.skplanet.storeplatform.framework.core.persistence.dao.CommonDAO;
import com.skplanet.storeplatform.purchase.client.history.vo.ExistenceScRes;
import com.skplanet.storeplatform.sac.client.display.vo.vod.VodDetailReq;
import com.skplanet.storeplatform.sac.client.display.vo.vod.VodDetailRes;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.common.*;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.product.*;
import com.skplanet.storeplatform.sac.display.common.DisplayCommonUtil;
import com.skplanet.storeplatform.sac.display.common.constant.DisplayConstants;
import com.skplanet.storeplatform.sac.display.common.service.DisplayCommonService;
import com.skplanet.storeplatform.sac.display.common.vo.ProductImage;
import com.skplanet.storeplatform.sac.display.vod.vo.VodDetail;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * VOD Service
 *
 * Updated on : 2014-01-09 Updated by : 임근대, SK플래닛.
 */
@Service
@Transactional
public class VodServiceImpl implements VodService {

	private static final Logger logger = LoggerFactory.getLogger(VodServiceImpl.class);

	@Autowired
	@Qualifier("sac")
	private CommonDAO commonDAO;

    @Autowired
    private DisplayCommonService commonService;

	/*
	 * (non-Javadoc)
	 * @see com.skplanet.storeplatform.sac.display.vod.service.VodService#searchVod(com.skplanet.storeplatform.sac.client.display.vo.vod.VodDetailReq)
	 */
	@Override
	public VodDetailRes searchVod(VodDetailReq req) {
		logger.debug("req={}", req);

		// Dummy
		VodDetailRes res = new VodDetailRes();
		Product product = new Product();

		//SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd'T'HHmmssZ");

		// 1. Channel 정보 조회
        Map<String, Object> param = new HashMap<String, Object>();
        param.put("imgCd", DisplayConstants.DP_VOD_REPRESENT_IMAGE_CD);
        param.put("virtualDeviceModelNo", DisplayConstants.DP_ANY_PHONE_4MM);
        param.put("deviceModel", req.getDeviceModel());
        param.put("channelId", req.getChannelId());
        param.put("langCd", req.getLangCd());
        param.put("tenantId", req.getTenantId());
        param.put("orderedBy", StringUtils.defaultString(req.getOrderedBy(), DisplayConstants.DP_ORDEREDBY_TYPE_RECENT));
        param.put("offset", req.getOffset());
        param.put("count", req.getCount());

		VodDetail vodDetail = this.commonDAO.queryForObject("VodDetail.selectVodChannel", param, VodDetail.class);
		logger.debug("vodDetail={}", vodDetail);

		if(vodDetail != null) {
			//Screenshots
			List<ProductImage> screenshotList = getScreenshotList(req.getChannelId(), req.getLangCd());
			this.mapProduct(req, product, vodDetail, screenshotList);

			// 2. subProjectList
            List<VodDetail> subProductList = this.commonDAO.queryForList("VodDetail.selectVodSeries", param, VodDetail.class);

            List<ExistenceScRes> existenceScResList = getExistenceScReses(req, subProductList);
            this.mapSubProductList(req, product, subProductList, existenceScResList);

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
		List<ProductImage> screenshotList = this.commonDAO.queryForList("VodDetail.selectSourceList", param, ProductImage.class);
		return screenshotList;
	}

    /**
     * 기구매 체크 (구매서버 연동)
     * @param req
     * @param subProductList
     * @return
     */
    private List<ExistenceScRes> getExistenceScReses(VodDetailReq req, List<VodDetail> subProductList) {
    	if(StringUtils.isNotEmpty(req.getUserKey()) && StringUtils.isNotEmpty(req.getDeviceKey())) {
    		return new ArrayList<ExistenceScRes>();
    	}
        List<ExistenceScRes> existenceScResList = null;
        if(subProductList != null && subProductList.size() > 0) {
            //기구매 체크
            List<String> episodeIdList = new ArrayList<String>();
            for(VodDetail subProduct : subProductList) {
                if(StringUtils.isNotEmpty(subProduct.getPlayProdId())) {
                    episodeIdList.add(subProduct.getPlayProdId());
                } else if(StringUtils.isNotEmpty(subProduct.getStoreProdId())) {
                    episodeIdList.add(subProduct.getStoreProdId());
                }
            }
            try {
        		existenceScResList = commonService.checkPurchaseList(req.getTenantId(), req.getUserKey(), req.getDeviceKey(), episodeIdList);
            } catch (StorePlatformException e) {
                //ignore : 구매 연동 오류 발생해도 상세 조회는 오류 없도록 처리. 구매 연동오류는 VOC 로 처리한다.
            	existenceScResList = new ArrayList<ExistenceScRes>();
            }
        }
        return existenceScResList;
    }


    /**
	 * Product Mapping
     * @param req
     * @param product Product
     * @param mapperVO  Product 에 Mapping 할 데이터
     * @param screenshotList Screenshot 목록
     */
	private void mapProduct(VodDetailReq req, Product product, VodDetail mapperVO, List<ProductImage> screenshotList) {

		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd'T'HHmmssZ");
		product.setIdentifier(new Identifier(DisplayConstants.DP_CHANNEL_IDENTIFIER_CD, mapperVO.getProdId()));
		product.setTitle(mapTitle(mapperVO));

		List<Source> sourceList = this.mapSourceList(mapperVO, screenshotList);
		product.setSourceList(sourceList);

		// 상품 설명
		product.setProductExplain(mapperVO.getProdBaseDesc());
		product.setProductDetailExplain(mapperVO.getProdDtlDesc());
		product.setProductIntroduction(mapperVO.getProdIntrDscr());


		// SupportList
		List<Support> supportList = this.mapSupportList(mapperVO);
		product.setSupportList(supportList);

		// MenuList
		List<Menu> menuList = this.mapMenuList(mapperVO);
		product.setMenuList(menuList);


		// DateList
		List<Date> dateList = this.mapDateList(mapperVO, sdf);
		product.setDateList(dateList);

		// Contributor
		Contributor contributor = this.mapContributor(mapperVO);
		product.setContributor(contributor);


		// Distributor (판매자 정보)
		Distributor distributor = this.mapDistributor(mapperVO);
		product.setDistributor(distributor);


		// rights
		Rights rights = this.mapRights(mapperVO, req, null);
		product.setRights(rights);

		//Accrual
		Accrual accrual = this.mapAccrual(mapperVO);
		product.setAccrual(accrual);

	}

    /**
     * Title
     * @param mapperVO
     * @return
     */
    private Title mapTitle(VodDetail mapperVO) {
        Title title = new Title();
        title.setPrefix(mapperVO.getVodTitlNm());
        title.setText(mapperVO.getProdNm());
        return title;
    }

    /**
     * Accural
     * @param mapperVO
     * @return
     */
	private Accrual mapAccrual(VodDetail mapperVO) {
		Accrual accrual = new Accrual();
		accrual.setVoterCount(mapperVO.getPaticpersCnt());
		accrual.setDownloadCount(mapperVO.getPrchsCnt());
		accrual.setScore(mapperVO.getAvgEvluScore());
		return accrual;
	}

    /**
     * Rights
     * @param mapperVO
     * @param req
     * @param existenceMap
     * @return
     */
	private Rights mapRights(VodDetail mapperVO, VodDetailReq req, Map<String, ExistenceScRes> existenceMap) {
		Rights rights = new Rights();
		rights.setGrade(mapperVO.getProdGrdCd());
		rights.setAllow(mapperVO.getDwldAreaLimtYn());

		/** dwldAreaLimitYn 다운로드 지역제한 == 'Y' 일 경우 domestic 리턴 */
		/*
		if(StringUtils.isNotEmpty(mapperVO.getDwldAreaLimtYn())
				&& mapperVO.getDwldAreaLimtYn().equals("Y")) {
			rights.setAllow(DisplayConstants.DP_RIGHTS_ALLOW_DOMESTIC);
		}
		*/

		// Preview
		rights.setPreview(mapPreview(mapperVO));


		//-------------------------------------------
		// Store, Play
		//-------------------------------------------
		/** play 정보 */
		rights.setPlay(mapPlay(mapperVO, req, existenceMap));

		/** Store 정보 */
		rights.setStore(mapStore(mapperVO, req, existenceMap));
		return rights;
	}

	/**
	 * <pre>
	 * method 설명.
	 * </pre>
	 * @param mapperVO
	 * @param req
	 * @param existenceMap
	 * @param rights
	 * @return 
	 */
	private Store mapStore(VodDetail mapperVO, VodDetailReq req, Map<String, ExistenceScRes> existenceMap) {
		Store store = null;
		if (StringUtils.isNotEmpty(mapperVO.getStoreProdId())) {
			store = new Store();
			// Store.Identifier
			store.setIdentifier(new Identifier(DisplayConstants.DP_EPISODE_IDENTIFIER_CD, mapperVO.getStoreProdId()));

			Support storeSupport = new Support();
			Price storePrice = new Price();

			storeSupport.setType(DisplayConstants.DP_DRM_SUPPORT_NM);
			storeSupport.setText(mapperVO.getStoreDrmYn());
			store.setSupport(storeSupport);

			storePrice.setText(mapperVO.getStoreProdAmt() == null ? 0 : mapperVO.getStoreProdAmt());

			store.setPrice(storePrice);

			Source source = null;
			if (StringUtils.isNotEmpty(mapperVO.getFilePath())) {
				source = new Source();
				source.setMediaType(DisplayCommonUtil.getMimeType(mapperVO.getFilePath()));
				source.setUrl(mapperVO.getFilePath());
			}
			
			// 네트워크 제한이 있을경우
			if (mapperVO.getDwldNetworkCd() != null) {
				store.setNetworkRestrict(DisplayConstants.DP_NETWORK_RESTRICT);
			}

            if(existenceMap != null && existenceMap.containsKey(mapperVO.getStoreProdId())) {
                String salesStatus = getSalesStatus(mapperVO, req.getUserKey(), req.getDeviceKey());
                if(salesStatus != null)  store.setSalesStatus(salesStatus);
            }

		}
		return store;
	}

	/**
	 * <pre>
	 * method 설명.
	 * </pre>
	 * @param mapperVO
	 * @param req
	 * @param existenceMap
	 * @param rights
	 * @return 
	 */
	private Play mapPlay(VodDetail mapperVO, VodDetailReq req, Map<String, ExistenceScRes> existenceMap) {
		Date date;
		Play play = null;
		if (StringUtils.isNotEmpty(mapperVO.getPlayProdId())) {
			play = new Play();
			//Play.Identifier
			play.setIdentifier(new Identifier(DisplayConstants.DP_EPISODE_IDENTIFIER_CD, mapperVO.getPlayProdId()));

			Support playSupport = new Support();
			Price playPrice = new Price();
			playSupport.setType(DisplayConstants.DP_DRM_SUPPORT_NM);
			playSupport.setText(mapperVO.getPlayDrmYn());
			play.setSupport(playSupport);

			date = new Date();
			date.setType(DisplayConstants.DP_DATE_USAGE_PERIOD);
			date.setText(mapperVO.getUsagePeriod());
			playPrice.setText(mapperVO.getPlayProdAmt() == null ? 0 : mapperVO.getPlayProdAmt());

			Source source = null;
			if (StringUtils.isNotEmpty(mapperVO.getFilePath())) {
				source = new Source();
				source.setMediaType(DisplayCommonUtil.getMimeType(mapperVO.getFilePath()));
				source.setUrl(mapperVO.getFilePath());
			}

			play.setDate(date); // 이용기간
			play.setPrice(playPrice); // 바로보기 상품 금액
			if (mapperVO.getStrmNetworkCd() != null) {
				play.setNetworkRestrict(DisplayConstants.DP_NETWORK_RESTRICT);
			}

            if(existenceMap != null && existenceMap.containsKey(mapperVO.getPlayProdId())) {
                String salesStatus = getSalesStatus(mapperVO, req.getUserKey(), req.getDeviceKey());
                if(salesStatus != null)  play.setSalesStatus(salesStatus);
            }

		}
		return play;
	}

	/**
	 * <pre>
	 * method 설명.
	 * </pre>
	 * @param mapperVO
	 * @return
	 */
	private Preview mapPreview(VodDetail mapperVO) {
		List<Source> sourceList;
		Preview preview = new Preview();

		sourceList = new ArrayList<Source>();
		if (StringUtils.isNotEmpty(mapperVO.getScSamplUrl())) {
            Source source = new Source();
			source.setType(DisplayConstants.DP_PREVIEW_LQ);
			source.setUrl(mapperVO.getScSamplUrl());
			sourceList.add(source);
		}
		if (StringUtils.isNotEmpty(mapperVO.getSamplUrl())) {
            Source source = new Source();
			source.setType(DisplayConstants.DP_PREVIEW_HQ);
			source.setUrl(mapperVO.getSamplUrl());
			sourceList.add(source);
        }
		preview.setSourceList(sourceList);
		return preview;
	}

    /**
     * Distributor
     * @param mapperVO
     * @return
     */
    private Distributor mapDistributor(VodDetail mapperVO) {
		Distributor distributor = new Distributor();
        distributor.setSellerKey(mapperVO.getSellerMbrNo());
        distributor.setName(mapperVO.getExpoSellerNm());
        distributor.setTel(mapperVO.getExpoSellerTelno());
        distributor.setEmail(mapperVO.getExpoSellerEmail());
		return distributor;
	}

    /**
     * Contributor
     * @param mapperVO
     * @return
     */
	private Contributor mapContributor(VodDetail mapperVO) {
		Contributor contributor = new Contributor();
		contributor.setDirector(mapperVO.getArtist2Nm()); 	//감독
		contributor.setArtist(mapperVO.getArtist1Nm()); 	//출연
		contributor.setCompany(mapperVO.getChnlCompNm());	//제공업체
		contributor.setAgency(mapperVO.getAgencyNm());		//기획사
		contributor.setPublisher(mapperVO.getChnlCompNm()); //배급사

		//기획사
		if(mapperVO.getTopMenuId().equals("DP000518")) { //공통코드 : DP000518 (TV 방송)
			contributor.setChannel(mapperVO.getBrdcCompCdNm()); //방송사
		}
		return contributor;
	}

    /**
     * SourceList
     * @param mapperVO
     * @param screenshotList
     * @return
     */
	private List<Source> mapSourceList(VodDetail mapperVO, List<ProductImage> screenshotList) {
		// 대표 이미지 (thumbnail)
		List<Source> sourceList = new ArrayList<Source>();
		if(StringUtils.isNotEmpty(mapperVO.getImgPath())) {
			Source source = new Source();
			source.setMediaType(DisplayCommonUtil.getMimeType(mapperVO.getImgPath()));
			source.setSize(mapperVO.getImgSize());
			source.setType(DisplayConstants.DP_SOURCE_TYPE_THUMBNAIL);
			source.setUrl(mapperVO.getImgPath()+mapperVO.getImgNm());
			sourceList.add(source);
		}

		// screenshot
		for (ProductImage screenshotImage : screenshotList) {
			Source screenshotSource = new Source();
			screenshotSource.setType(DisplayConstants.DP_SOURCE_TYPE_SCREENSHOT);
			screenshotSource.setSize(screenshotImage.getFileSize());
			screenshotSource.setUrl(screenshotImage.getFilePath() + screenshotImage.getFileNm());
			sourceList.add(screenshotSource);
		}
		return sourceList;
	}

    /**
     * DataList
     * @param mapperVO
     * @param sdf
     * @return
     */
	private List<Date> mapDateList(VodDetail mapperVO, SimpleDateFormat sdf) {
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
     * Menu List
     * @param mapperVO
     * @return
     */
	private List<Menu> mapMenuList(VodDetail mapperVO) {
		List<Menu> menuList = new ArrayList<Menu>();
		Menu menu = new Menu();
		menu.setId(mapperVO.getTopMenuId());
		menu.setName(mapperVO.getTopMenuNm());
		menu.setType(DisplayConstants.DP_MENU_TOPCLASS_TYPE);
		menuList.add(menu);

		menu = new Menu();
		menu.setId(mapperVO.getMenuId());
		menu.setName(mapperVO.getMenuNm());
		menuList.add(menu);

		//Meta Class
		menu = new Menu();
		menu.setType(DisplayConstants.DP_META_CLASS_MENU_TYPE);
		menu.setId(mapperVO.getMetaClsfCd());
		menuList.add(menu);

		//Genre
		menu = new Menu();
		menu.setType(DisplayConstants.DP_MENU_TYPE_GENRE);
		menu.setId(mapperVO.getGenreCd());
		menuList.add(menu);
		return menuList;
	}

    /**
     * Support List
     * @param mapperVO
     * @return
     */
	private List<Support> mapSupportList(VodDetail mapperVO) {
		List<Support> supportList = new ArrayList<Support>();
		/** HDCP_YN */
		Support support = new Support();
		support.setType(DisplayConstants.DP_VOD_HDCP_SUPPORT_NM);
		support.setText(mapperVO.getHdcpYn());
		supportList.add(support);

		/** DOLBY_SPRT_YN */
		support = new Support();
		support.setType(DisplayConstants.DP_VOD_HD_SUPPORT_NM);
		support.setText(mapperVO.getHdvYn());
		supportList.add(support);

		/** DOLBY_SPRT_YN */
		support = new Support();
		support.setType(DisplayConstants.DP_VOD_DOLBY_NM);
		support.setText(mapperVO.getDolbySprtYn());
		supportList.add(support);
		return supportList;
	}

	/**
	 * SubProduct Mapping
     * TODO : existenceScResList
     * @param req  요청 정보
     * @param product   Channel 정보
     * @param vodDetailList VOD Episode List
     * @param existenceScResList 기구매 체크 결과
     */
	private void mapSubProductList(VodDetailReq req, Product product, List<VodDetail> vodDetailList, List<ExistenceScRes> existenceScResList) {

		List<Product> subProjectList = new ArrayList<Product>();

		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd'T'HHmmssZ");


		if(vodDetailList != null && vodDetailList.size() > 0) {
            //SubProduct TotalCount
			VodDetail temp = vodDetailList.get(0);
			product.setSubProductTotalCount(temp.getTotalCount());

            //기구매 체크
            Map<String, ExistenceScRes> existenceMap = new HashMap<String, ExistenceScRes>();
            for(ExistenceScRes existenceScRes : existenceScResList) {
                existenceMap.put(existenceScRes.getProdId(), existenceScRes);
            }

			for(VodDetail mapperVO : vodDetailList) {
				Product subProduct = new Product();
				
				//List<ProductImage> screenshotList = getScreenshotList(mapperVO.getProdId(), req.getLangCd());

				List<Identifier> identifierList = new ArrayList<Identifier>();

				identifierList.add(new Identifier(DisplayConstants.DP_EPISODE_IDENTIFIER_CD, mapperVO.getProdId()));
				identifierList.add(new Identifier(DisplayConstants.DP_CONTENT_IDENTIFIER_CD, mapperVO.getCid()));

				subProduct.setIdentifierList(identifierList);

				subProduct.setTitle(new Title(mapperVO.getProdNm()));

				// 상품 설명
				subProduct.setProductExplain(mapperVO.getProdBaseDesc());
				subProduct.setProductDetailExplain(mapperVO.getProdDtlDesc());
				subProduct.setProductIntroduction(mapperVO.getProdIntrDscr());

                //SupportList
				List<Support> supportList = this.mapSupportList(mapperVO);
				subProduct.setSupportList(supportList);

                //MenuList
				List<Menu> menuList = this.mapMenuList(mapperVO);
				subProduct.setMenuList(menuList);

                //DateList
				List<Date> dateList = this.mapDateList(mapperVO, sdf);
				subProduct.setDateList(dateList);

                //Contributor
				Contributor contributor = this.mapContributor(mapperVO);
				subProduct.setContributor(contributor);

                //Distributor
				Distributor distributor = this.mapDistributor(mapperVO);
		        subProduct.setDistributor(distributor);

                // rights
                Rights rights = this.mapRights(mapperVO, req, existenceMap);
                subProduct.setRights(rights);

				// VOD
                subProduct.setVod(mapVod(mapperVO));

                //Accrual
				Accrual accrual = this.mapAccrual(mapperVO);
				subProduct.setAccrual(accrual);

				subProjectList.add(subProduct);
			}
		}
		product.setSubProductList(subProjectList);

	}

    /**
     * VOD
     * @param mapperVO
     * @return
     */
    private Vod mapVod(VodDetail mapperVO) {
        Vod vod = new Vod();
        List<VideoInfo> videoInfoList = new ArrayList<VideoInfo>();
        Time runningTime = new Time();
        Chapter chapter = new Chapter();
        VideoInfo videoInfo;

        runningTime.setText(String.valueOf(mapperVO.getEpsdPlayTm()));
        vod.setRunningTime(runningTime);

        chapter.setUnit(mapperVO.getChapterUnit());
        if(StringUtils.isNotEmpty(mapperVO.getChapter())) {
            chapter.setText(Integer.parseInt(mapperVO.getChapter()));
        }
        vod.setChapter(chapter);

        /** 일반화질 정보 */
        if (StringUtils.isNotEmpty(mapperVO.getNmSubContsId())) {
            videoInfo = new VideoInfo();
            videoInfo.setPictureSize(mapperVO.getNmDpPicRatio());
            videoInfo.setPixel(mapperVO.getNmDpPixel());
            videoInfo.setScid(mapperVO.getNmSubContsId());
            videoInfo.setSize(mapperVO.getNmFileSize().toString());
            videoInfo.setType(DisplayConstants.DP_VOD_QUALITY_NORMAL);
            videoInfo.setVersion(mapperVO.getNmProdVer());
            videoInfoList.add(videoInfo);
        }
        /** SD 고화질 정보 */
        if (StringUtils.isNotEmpty(mapperVO.getSdSubContsId())) {
            videoInfo = new VideoInfo();
            videoInfo.setPictureSize(mapperVO.getSdDpPicRatio());
            videoInfo.setPixel(mapperVO.getSdDpPixel());
            videoInfo.setScid(mapperVO.getSdSubContsId());
            videoInfo.setSize(mapperVO.getSdFileSize().toString());
            videoInfo.setType(DisplayConstants.DP_VOD_QUALITY_SD);
            videoInfo.setVersion(mapperVO.getSdProdVer());
            videoInfoList.add(videoInfo);
        }
        /** HD 고화질 정보 */
        if (StringUtils.isNotEmpty(mapperVO.getHdSubContsId())) {
            videoInfo = new VideoInfo();
            videoInfo.setPictureSize(mapperVO.getHdDpPicRatio());
            videoInfo.setPixel(mapperVO.getHdDpPixel());
            videoInfo.setScid(mapperVO.getHdSubContsId());
            videoInfo.setSize(mapperVO.getHdFileSize().toString());
            videoInfo.setType(DisplayConstants.DP_VOD_QUALITY_HD);
            videoInfo.setVersion(mapperVO.getHdProdVer());
            videoInfoList.add(videoInfo);
        }
        vod.setVideoInfoList(videoInfoList);
        return vod;
    }

    /**
     * 판매 상태 조회
     * @param mapperVO
     * @param userKey
     * @param deviceKey
     * @return
     */
    private String getSalesStatus(VodDetail mapperVO, String userKey, String deviceKey) {
        String salesStatus = null;
        //기구매 체크
        if (!mapperVO.getProdStatusCd().equals(DisplayConstants.DP_SALE_STAT_ING)) {
            // 04, 09, 10의 경우 구매이력이 없으면 상품 없음을 표시한다.
            if (DisplayConstants.DP_SALE_STAT_PAUSED.equals(mapperVO.getProdStatusCd()) ||
                    DisplayConstants.DP_SALE_STAT_RESTRIC_DN.equals(mapperVO.getProdStatusCd()) ||
                    DisplayConstants.DP_SALE_STAT_DROP_REQ_DN.equals(mapperVO.getProdStatusCd())) {
                if (StringUtils.isNotEmpty(userKey) && StringUtils.isNotEmpty(deviceKey)) {
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
