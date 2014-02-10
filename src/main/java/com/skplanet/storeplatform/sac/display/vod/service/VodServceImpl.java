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

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.skplanet.storeplatform.framework.core.persistence.dao.CommonDAO;
import com.skplanet.storeplatform.sac.client.display.vo.vod.VodDetailReq;
import com.skplanet.storeplatform.sac.client.display.vo.vod.VodDetailRes;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.common.Date;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.common.Identifier;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.common.Menu;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.common.Price;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.common.Source;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.common.Time;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.common.Title;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.product.Accrual;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.product.Chapter;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.product.Contributor;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.product.Distributor;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.product.Play;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.product.Preview;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.product.Product;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.product.Rights;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.product.Store;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.product.Support;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.product.VideoInfo;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.product.Vod;
import com.skplanet.storeplatform.sac.display.common.DisplayCommonUtil;
import com.skplanet.storeplatform.sac.display.common.constant.DisplayConstants;
import com.skplanet.storeplatform.sac.display.common.vo.ProductImage;
import com.skplanet.storeplatform.sac.display.vod.vo.VodDetail;

/**
 * VOD Service
 *
 * Updated on : 2014-01-09 Updated by : 임근대, SK플래닛.
 */
@Service
@Transactional
public class VodServceImpl implements VodService {

	private static final Logger logger = LoggerFactory.getLogger(VodServceImpl.class);

	@Autowired
	@Qualifier("sac")
	private CommonDAO commonDAO;

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

		// --------------------------------------------------------
		// 1. Channel 정보 조회
		// --------------------------------------------------------
		req.setImgCd(DisplayConstants.DP_VOD_REPRESENT_IMAGE_CD);
		VodDetail vodDetail = this.commonDAO.queryForObject("VodDetail.selectVodChannel", req, VodDetail.class);
		logger.debug("vodDetail={}", vodDetail);

		if(vodDetail != null) {

			//TODO: 상품 상태 체크
			String prodStatusCd = vodDetail.getProdStatusCd();
            if (prodStatusCd.equals("PD000401")) { // 미승인
            } else if (prodStatusCd.equals("PD000402")) { // 판매대기
            } else if (prodStatusCd.equals("PD000405")) { // 판매금지
            } else if (prodStatusCd.equals("PD000406")) { // 해지요청
            } else if (prodStatusCd.equals("PD000407")) { // 일시정지
            } else if (prodStatusCd.equals("PD000408")) { // 판매삭제
            }

			//Screenshots
			ProductImage productImage = new ProductImage();
			productImage.setProdId(req.getChannelId());
			productImage.setLangCd(req.getLangCd());
			List<ProductImage> screenshotList = this.commonDAO.queryForList("VodDetail.selectSourceList", productImage, ProductImage.class);
			this.mapProduct(product, vodDetail, screenshotList);

			// --------------------------------------------------------
			// 2. subProjectList
			// --------------------------------------------------------
			List<VodDetail> subProductList = this.commonDAO.queryForList("VodDetail.selectVodSeries", req, VodDetail.class);
			this.mapSubProductList(product, subProductList);

			res.setProduct(product);
		} else {
			//TODO : 데이터 없을 경우..
		}
		return res;
	}



	/**
	 *
	 * @param product
	 * @param mapperVO
	 * @param screenshotList
	 * @return
	 */
	private void mapProduct(Product product, VodDetail mapperVO, List<ProductImage> screenshotList) {

		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd'T'HHmmssZ");
		product.setIdentifier(new Identifier(DisplayConstants.DP_CHANNEL_IDENTIFIER_CD, mapperVO.getProdId()));
		product.setTitle(new Title(mapperVO.getProdNm()));

		//-------------------------------------------
		// 대표 이미지 (thumbnail)
		//-------------------------------------------
		List<Source> sourceList = new ArrayList<Source>();
		if(StringUtils.isNotEmpty(mapperVO.getImgPath())) {
			Source source = new Source();
			source.setMediaType(DisplayCommonUtil.getMimeType(mapperVO.getImgPath()));
			source.setSize(mapperVO.getImgSize());
			source.setType(DisplayConstants.DP_SOURCE_TYPE_THUMBNAIL);
			source.setUrl(mapperVO.getImgPath()+mapperVO.getImgNm());
			sourceList.add(source);
		}

		//-------------------------------------------
		// screenshot
		//-------------------------------------------
		for (ProductImage screenshotImage : screenshotList) {
			Source screenshotSource = new Source();
			screenshotSource.setType(DisplayConstants.DP_SOURCE_TYPE_SCREENSHOT);
			screenshotSource.setSize(screenshotImage.getFileSize());
			screenshotSource.setUrl(screenshotImage.getFilePath() + screenshotImage.getFileNm());
			sourceList.add(screenshotSource);
		}
		product.setSourceList(sourceList);

		//-------------------------------------------
		// 상품 설명
		//-------------------------------------------
		product.setProductExplain(mapperVO.getProdBaseDesc());
		product.setProductDetailExplain(mapperVO.getProdDtlDesc());
		product.setProductIntroduction(mapperVO.getProdIntrDscr());


		//-------------------------------------------
		// SupportList
		//-------------------------------------------
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

		/** BTV_YN */
		support = new Support();
		support.setType(DisplayConstants.DP_VOD_BTV_SUPPORT_NM);
		support.setText(mapperVO.getBtvYn());
		supportList.add(support);

		/** DOLBY_SPRT_YN */
		support = new Support();
		support.setType(DisplayConstants.DP_VOD_DOLBY_NM);
		support.setText(mapperVO.getDolbySprtYn());
		supportList.add(support);
		product.setSupportList(supportList);

		//-------------------------------------------
		// MenuList
		//-------------------------------------------
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

		product.setMenuList(menuList);


		//-------------------------------------------
		// DateList
		//-------------------------------------------
		List<Date> dateList = new ArrayList<Date>();
		Date date = null;
		if(mapperVO.getRegDt() != null) {
			date = new Date();
			date.setType(DisplayConstants.DP_DATE_REG);
			date.setText(sdf.format(mapperVO.getRegDt()));
			dateList.add(date);
		}

		if(mapperVO.getSvcStartDt() != null) {
			date = new Date();
			date.setType(DisplayConstants.DP_DATE_RELEASE);
			date.setText(sdf.format(mapperVO.getSvcStartDt()));
			dateList.add(date);
		}

		product.setDateList(dateList);

		//-------------------------------------------
		// Contributor
		//-------------------------------------------
		Contributor contributor = new Contributor();
		contributor.setDirector(mapperVO.getArtist2Nm()); 	//감독
		contributor.setArtist(mapperVO.getArtist1Nm()); 	//출연


		//TODO: 코드값 상수처리?
		if(mapperVO.getTopMenuId().equals("DP000518")) { //공통코드 : DP000518 (TV 방송)
			contributor.setChannel(mapperVO.getBrdcCompCdNm()); //방송사
		}
		if(mapperVO.getIssueDay() != null) {
			date = new Date();
			date.setText(sdf.format(mapperVO.getIssueDay()));
			contributor.setDate(date);
		}
		product.setContributor(contributor);


		//-------------------------------------------
		// Distributor (판매자 정보)
		//-------------------------------------------
		Distributor distributor = new Distributor();
        distributor.setSellerKey(mapperVO.getSellerMbrNo());
        distributor.setName(mapperVO.getExpoSellerNm());
        distributor.setTel(mapperVO.getExpoSellerTelno());
        distributor.setEmail(mapperVO.getExpoSellerEmail());
		product.setDistributor(distributor);


		//-------------------------------------------
		// rights
		//-------------------------------------------
		Rights rights = new Rights();
		rights.setGrade(mapperVO.getProdGrdCd());
		/** dwldAreaLimitYn 다운로드 지역제한 == 'Y' 일 경우 domestic 리턴 */
		if(StringUtils.isNotEmpty(mapperVO.getDwldAreaLimtYn())
				&& mapperVO.getDwldAreaLimtYn().equals("Y")) {
			rights.setAllow(DisplayConstants.DP_RIGHTS_ALLOW_DOMESTIC);
		}

		//-------------------------------------------
		// Preview
		//-------------------------------------------
		Preview preview = new Preview();

		sourceList = new ArrayList<Source>();
		Source source = null;
		if (StringUtils.isNotEmpty(mapperVO.getScSamplUrl())) {
			source = new Source();
			source.setType(DisplayConstants.DP_PREVIEW_LQ);
			source.setUrl(mapperVO.getScSamplUrl());
			sourceList.add(source);
		}
		if (StringUtils.isNotEmpty(mapperVO.getSamplUrl())) {
			source = new Source();
			source.setType(DisplayConstants.DP_PREVIEW_HQ);
			source.setUrl(mapperVO.getSamplUrl());
			sourceList.add(source);
		}
		preview.setSourceList(sourceList);
		rights.setPreview(preview);


		//-------------------------------------------
		// Store, Play
		//-------------------------------------------
		/** play 정보 */
		Play play = new Play();
		if (StringUtils.isNotEmpty(mapperVO.getPlayProdId())) {
			Support playSupport = new Support();
			Price playPrice = new Price();
			playSupport.setType(DisplayConstants.DP_DRM_SUPPORT_NM);
			playSupport.setText(mapperVO.getPlayDrmYn());
			play.setSupport(playSupport);

			date = new Date();
			date.setType(DisplayConstants.DP_DATE_USAGE_PERIOD);
			date.setText(mapperVO.getUsagePeriod());
			playPrice.setText(mapperVO.getPlayProdAmt() == null ? 0 : mapperVO.getPlayProdAmt());

			Source playSource = new Source();
			playSource.setUrl(mapperVO.getPlayProdId());

			play.setDate(date); // 이용기간
			play.setPrice(playPrice); // 바로보기 상품 금액
			play.setSource(playSource); // 바로보기 상품 url
			if (mapperVO.getStrmNetworkCd() != null) {
				play.setNetworkRestrict(DisplayConstants.DP_NETWORK_RESTRICT);
			}
			rights.setPlay(play);
		}

		/** Store 정보 */
		Store store = new Store();
		if (StringUtils.isNotEmpty(mapperVO.getStoreProdId())) {
			Support storeSupport = new Support();
			Price storePrice = new Price();
			Source storeSource = new Source();

			storeSupport.setType(DisplayConstants.DP_DRM_SUPPORT_NM);
			storeSupport.setText(mapperVO.getStoreDrmYn());
			store.setSupport(storeSupport);

			storePrice.setText(mapperVO.getStoreProdAmt() == null ? 0 : mapperVO.getStoreProdAmt());
			storeSource.setUrl(mapperVO.getStoreProdId());

			store.setPrice(storePrice);
			store.setSource(storeSource);

			// 네트워크 제한이 있을경우
			if (mapperVO.getDwldNetworkCd() != null) {
				store.setNetworkRestrict(DisplayConstants.DP_NETWORK_RESTRICT);
			}
			rights.setStore(store);

		}

		//Accrual
		Accrual accrual = new Accrual();
		accrual.setVoterCount(mapperVO.getPaticpersCnt());
		accrual.setDownloadCount(mapperVO.getPrchsCnt());
		accrual.setScore(mapperVO.getAvgEvluScore());
		product.setAccrual(accrual);

	}

	/**
	 *
	 * @param res
	 * @param product
	 */
	private void mapSubProductList(Product product, List<VodDetail> vodDetailList) {

		List<Product> subProjectList = new ArrayList<Product>();


		/*
		Product product1 = new Product();
		product1.setIdentifier(new Identifier(DisplayConstants.DP_CONTENT_IDENTIFIER_CD, "0002119673"));

		Support support2 = new Support();
		support2.setType("drm");
		support2.setText("Y");
		Support support3 = new Support();
		support3.setType("hd");
		support3.setText("Y");
		product1.setSupportList(new ArrayList<Support>(Arrays.asList(support2, support3)));

		product1.setTitle(new Title("무한도전"));

		Date date2 = new Date();
		date2.setType("date/reg");
		date2.setText("20130324T220917+0900");
		Date date3 = new Date();
		date3.setType("date/saleReg");
		date3.setText("20130324T220917+0900");
		product1.setDateList(new ArrayList<Date>(Arrays.asList(date2, date3)));


		Support emplySupport = new Support();
		Rights rights2 = new Rights();
		rights2.setPlay(new Play(emplySupport, new Price(700), new Date("date/publish", "2013")));
		rights2.setStore(new Store(emplySupport, new Price(700), new Date("date/publish", "2013")));
		rights2.setDate(new Date("date/publish", "2013"));
		product1.setRights(rights2);

		VideoInfo videoInfo = new VideoInfo();
		videoInfo.setScid("0002663073");
		videoInfo.setType("normal");
		videoInfo.setPixel("576x324");
		videoInfo.setPictureSize("16:9");
		videoInfo.setVersion("1");
		videoInfo.setBtvcid("2222222222");
		videoInfo.setSize("307990233");

		product1.setVod(new Vod(new Time("16", "63"), videoInfo));
		subProjectList.add(product1);
		*/

		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd'T'HHmmssZ");

		List<Source> sourceList = null;
		Source source = null;


		if(vodDetailList != null && vodDetailList.size() > 0) {
			VodDetail vodDetail = vodDetailList.get(0);
			product.setSubProductTotalCount(vodDetail.getTotalCount());
		}

		for(VodDetail vodDetail : vodDetailList) {
			Product subProduct = new Product();

			List<Identifier> identifierList = new ArrayList<Identifier>();

			identifierList.add(new Identifier(DisplayConstants.DP_EPISODE_IDENTIFIER_CD, vodDetail.getProdId()));
			identifierList.add(new Identifier(DisplayConstants.DP_CONTENT_IDENTIFIER_CD, vodDetail.getCid()));

			subProduct.setIdentifierList(identifierList);

			subProduct.setTitle(new Title(vodDetail.getProdNm()));

			//-------------------------------------------
			// 대표 이미지 (thumbnail)
			//-------------------------------------------
			/*
			List<Source> sourceList = new ArrayList<Source>();
			source = new Source();
			source.setMediaType(DisplayCommonUtil.getMimeType(vodDetail.getImgPath()));
			source.setSize(vodDetail.getImgSize());
			source.setType(DisplayConstants.DP_SOURCE_TYPE_THUMBNAIL);
			source.setUrl(vodDetail.getImgPath()+vodDetail.getImgNm());
			sourceList.add(source);
			*/
			//-------------------------------------------
			// screenshot
			//-------------------------------------------
			/*
			for (ProductImage screenshotImage : screenshotList) {
				Source screenshotSource = new Source();
				screenshotSource.setType(DisplayConstants.DP_SOURCE_TYPE_SCREENSHOT);
				screenshotSource.setSize(screenshotImage.getFileSize());
				screenshotSource.setUrl(screenshotImage.getFilePath() + screenshotImage.getFileNm());
				sourceList.add(screenshotSource);
			}
			subProduct.setSourceList(sourceList);
			*/

			//-------------------------------------------
			// 상품 설명
			//-------------------------------------------
			subProduct.setProductExplain(vodDetail.getProdBaseDesc());
			subProduct.setProductDetailExplain(vodDetail.getProdDtlDesc());
			subProduct.setProductIntroduction(vodDetail.getProdIntrDscr());


			//-------------------------------------------
			// SupportList
			//-------------------------------------------
			List<Support> supportList = new ArrayList<Support>();
			/** HDCP_YN */
			Support support = new Support();
			support.setType(DisplayConstants.DP_VOD_HDCP_SUPPORT_NM);
			support.setText(vodDetail.getHdcpYn());
			supportList.add(support);

			/** DOLBY_SPRT_YN */
			support = new Support();
			support.setType(DisplayConstants.DP_VOD_HD_SUPPORT_NM);
			support.setText(vodDetail.getHdvYn());
			supportList.add(support);

			/** BTV_YN */
			support = new Support();
			support.setType(DisplayConstants.DP_VOD_BTV_SUPPORT_NM);
			support.setText(vodDetail.getBtvYn());
			supportList.add(support);

			/** DOLBY_SPRT_YN */
			support = new Support();
			support.setType(DisplayConstants.DP_VOD_DOLBY_NM);
			support.setText(vodDetail.getDolbySprtYn());
			supportList.add(support);
			subProduct.setSupportList(supportList);

			//-------------------------------------------
			// MenuList
			//-------------------------------------------
			List<Menu> menuList = new ArrayList<Menu>();
			Menu menu = new Menu();
			menu.setId(vodDetail.getTopMenuId());
			menu.setName(vodDetail.getTopMenuNm());
			menu.setType(DisplayConstants.DP_MENU_TOPCLASS_TYPE);
			menuList.add(menu);

			menu = new Menu();
			menu.setId(vodDetail.getMenuId());
			menu.setName(vodDetail.getMenuNm());
			menuList.add(menu);

			//Meta Class
			menu = new Menu();
			menu.setType(DisplayConstants.DP_META_CLASS_MENU_TYPE);
			menu.setId(vodDetail.getMetaClsfCd());
			menuList.add(menu);

			//Genre
			menu = new Menu();
			menu.setType(DisplayConstants.DP_MENU_TYPE_GENRE);
			menu.setId(vodDetail.getGenreCd());
			menuList.add(menu);

			subProduct.setMenuList(menuList);


			//-------------------------------------------
			// DateList
			//-------------------------------------------
			List<Date> dateList = new ArrayList<Date>();
			Date date = null;
			if(vodDetail.getRegDt() != null) {
				date = new Date();
				date.setType(DisplayConstants.DP_DATE_REG);
				date.setText(sdf.format(vodDetail.getRegDt()));
				dateList.add(date);
			}

			if(vodDetail.getSvcStartDt() != null) {
				date = new Date();
				date.setType(DisplayConstants.DP_DATE_RELEASE);
				date.setText(sdf.format(vodDetail.getSvcStartDt()));
				dateList.add(date);
			}

			subProduct.setDateList(dateList);

			//-------------------------------------------
			// Contributor
			//-------------------------------------------
			Contributor contributor = new Contributor();
			contributor.setDirector(vodDetail.getArtist2Nm()); 	//감독
			contributor.setArtist(vodDetail.getArtist1Nm()); 	//출연


			//TODO: 코드값 상수처리?
			if(vodDetail.getTopMenuId().equals("DP000518")) { //공통코드 : DP000518 (TV 방송)
				contributor.setChannel(vodDetail.getBrdcCompCdNm()); //방송사
			}
			if(vodDetail.getIssueDay() != null) {
				date = new Date();
				date.setText(sdf.format(vodDetail.getIssueDay()));
				contributor.setDate(date);
			}
			subProduct.setContributor(contributor);


			//-------------------------------------------
			// Distributor (판매자 정보)
			//-------------------------------------------
			Distributor distributor = new Distributor();
	        distributor.setSellerKey(vodDetail.getSellerMbrNo());
	        distributor.setName(vodDetail.getExpoSellerNm());
	        distributor.setTel(vodDetail.getExpoSellerTelno());
	        distributor.setEmail(vodDetail.getExpoSellerEmail());
	        subProduct.setDistributor(distributor);


			//-------------------------------------------
			// rights
			//-------------------------------------------
			Rights rights = new Rights();
			rights.setGrade(vodDetail.getProdGrdCd());
			/** dwldAreaLimitYn 다운로드 지역제한 == 'Y' 일 경우 domestic 리턴 */
			if(StringUtils.isNotEmpty(vodDetail.getDwldAreaLimtYn())
					&& vodDetail.getDwldAreaLimtYn().equals("Y")) {
				rights.setAllow(DisplayConstants.DP_RIGHTS_ALLOW_DOMESTIC);
			}

			//-------------------------------------------
			// Preview
			//-------------------------------------------
			Preview preview = new Preview();

			sourceList = new ArrayList<Source>();
			if (StringUtils.isNotEmpty(vodDetail.getScSamplUrl())) {
				source = new Source();
				source.setType(DisplayConstants.DP_PREVIEW_LQ);
				source.setUrl(vodDetail.getScSamplUrl());
				sourceList.add(source);
			}
			if (StringUtils.isNotEmpty(vodDetail.getSamplUrl())) {
				source = new Source();
				source.setType(DisplayConstants.DP_PREVIEW_HQ);
				source.setUrl(vodDetail.getSamplUrl());
				sourceList.add(source);
			}
			preview.setSourceList(sourceList);
			rights.setPreview(preview);


			//-------------------------------------------
			// VOD
			// TODO:
			//-------------------------------------------
			List<VideoInfo> videoInfoList = new ArrayList<VideoInfo>();
			Vod vod = new Vod();
			Time runningTime = new Time();
			Chapter chapter = new Chapter();
			VideoInfo videoInfo = null;

			runningTime.setText(String.valueOf(vodDetail.getEpsdPlayTm()));
			vod.setRunningTime(runningTime);

			chapter.setUnit(vodDetail.getChapterUnit());
			if(StringUtils.isNotEmpty(vodDetail.getChapter())) { //null 일 경우 NumberFormatExceptio 발생
				chapter.setText(Integer.parseInt(vodDetail.getChapter()));
			}
			vod.setChapter(chapter);

			/** 일반화질 정보 */
			if (StringUtils.isNotEmpty(vodDetail.getNmSubContsId())) {
				videoInfo = new VideoInfo();
				videoInfo.setPictureSize(vodDetail.getNmDpPicRatio());
				videoInfo.setPixel(vodDetail.getNmDpPixel());
				videoInfo.setScid(vodDetail.getNmSubContsId());
				videoInfo.setSize(vodDetail.getNmFileSize().toString());
				videoInfo.setType(DisplayConstants.DP_VOD_QUALITY_NORMAL);
				videoInfo.setVersion(vodDetail.getNmProdVer());
				videoInfoList.add(videoInfo);
			}
			/** SD 고화질 정보 */
			if (StringUtils.isNotEmpty(vodDetail.getSdSubContsId())) {
				videoInfo = new VideoInfo();
				videoInfo.setPictureSize(vodDetail.getSdDpPicRatio());
				videoInfo.setPixel(vodDetail.getSdDpPixel());
				videoInfo.setScid(vodDetail.getSdSubContsId());
				videoInfo.setSize(vodDetail.getSdFileSize().toString());
				videoInfo.setType(DisplayConstants.DP_VOD_QUALITY_SD);
				videoInfo.setVersion(vodDetail.getSdProdVer());
				videoInfoList.add(videoInfo);
			}
			/** HD 고화질 정보 */
			if (StringUtils.isNotEmpty(vodDetail.getHdSubContsId())) {
				videoInfo = new VideoInfo();
				videoInfo.setPictureSize(vodDetail.getHdDpPicRatio());
				videoInfo.setPixel(vodDetail.getHdDpPixel());
				videoInfo.setScid(vodDetail.getHdSubContsId());
				videoInfo.setSize(vodDetail.getHdFileSize().toString());
				videoInfo.setType(DisplayConstants.DP_VOD_QUALITY_HD);
				videoInfo.setVersion(vodDetail.getHdProdVer());
				videoInfoList.add(videoInfo);
			}
			vod.setVideoInfoList(videoInfoList);
			product.setVod(vod);

			//-------------------------------------------
			// Store, Play
			//-------------------------------------------
			/** play 정보 */
			Play play = new Play();
			if (StringUtils.isNotEmpty(vodDetail.getPlayProdId())) {
				Support playSupport = new Support();
				Price playPrice = new Price();
				playSupport.setType(DisplayConstants.DP_DRM_SUPPORT_NM);
				playSupport.setText(vodDetail.getPlayDrmYn());
				play.setSupport(playSupport);

				date = new Date();
				date.setType(DisplayConstants.DP_DATE_USAGE_PERIOD);
				date.setText(vodDetail.getUsagePeriod());
				playPrice.setText(vodDetail.getPlayProdAmt() == null ? 0 : vodDetail.getPlayProdAmt());

				Source playSource = new Source();
				playSource.setUrl(vodDetail.getPlayProdId());

				play.setDate(date); // 이용기간
				play.setPrice(playPrice); // 바로보기 상품 금액
				play.setSource(playSource); // 바로보기 상품 url
				if (vodDetail.getStrmNetworkCd() != null) {
					play.setNetworkRestrict(DisplayConstants.DP_NETWORK_RESTRICT);
				}
				rights.setPlay(play);
			}

			/** Store 정보 */
			Store store = new Store();
			if (StringUtils.isNotEmpty(vodDetail.getStoreProdId())) {
				Support storeSupport = new Support();
				Price storePrice = new Price();
				Source storeSource = new Source();

				storeSupport.setType(DisplayConstants.DP_DRM_SUPPORT_NM);
				storeSupport.setText(vodDetail.getStoreDrmYn());
				store.setSupport(storeSupport);

				storePrice.setText(vodDetail.getStoreProdAmt() == null ? 0 : vodDetail.getStoreProdAmt());
				storeSource.setUrl(vodDetail.getStoreProdId());

				store.setPrice(storePrice);
				store.setSource(storeSource);

				// 네트워크 제한이 있을경우
				if (vodDetail.getDwldNetworkCd() != null) {
					store.setNetworkRestrict(DisplayConstants.DP_NETWORK_RESTRICT);
				}
				rights.setStore(store);

			}

			//Accrual
			Accrual accrual = new Accrual();
			accrual.setVoterCount(vodDetail.getPaticpersCnt());
			accrual.setDownloadCount(vodDetail.getPrchsCnt());
			accrual.setScore(vodDetail.getAvgEvluScore());
			subProduct.setAccrual(accrual);


			subProjectList.add(subProduct);

		}
		product.setSubProductList(subProjectList);

	}

}
