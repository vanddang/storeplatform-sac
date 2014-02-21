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
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.skplanet.storeplatform.framework.core.exception.StorePlatformException;
import com.skplanet.storeplatform.framework.core.persistence.dao.CommonDAO;
import com.skplanet.storeplatform.sac.client.display.vo.category.CategoryVodBoxSacReq;
import com.skplanet.storeplatform.sac.client.display.vo.category.CategoryVodBoxSacRes;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.common.CommonResponse;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.common.Date;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.common.Identifier;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.common.Menu;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.common.Price;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.common.Source;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.common.Time;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.common.Title;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.product.Chapter;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.product.Contributor;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.product.Play;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.product.Preview;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.product.Product;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.product.Rights;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.product.Store;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.product.Support;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.product.VideoInfo;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.product.Vod;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.product.VodExplain;
import com.skplanet.storeplatform.sac.common.header.vo.SacRequestHeader;
import com.skplanet.storeplatform.sac.display.category.vo.CategoryVodBox;
import com.skplanet.storeplatform.sac.display.common.constant.DisplayConstants;

/**
 * Category Vod Box Service 인터페이스(CoreStoreBusiness) 구현체
 * 
 * Updated on : 2014. 02. 12. Updated by : 유시혁.
 */
@Service
public class CategoryVodBoxServiceImpl implements CategoryVodBoxService {

	private final Logger log = LoggerFactory.getLogger(this.getClass());

	@Autowired
	@Qualifier("sac")
	private CommonDAO commonDAO;

	/**
	 * <pre>
	 * Vod Box 리스트 조회.
	 * </pre>
	 * 
	 * @param requestVO
	 *            CategoryVodBoxSacReq
	 * @param requestHeader
	 *            SacRequestHeader
	 * @return CategoryVodBoxSacRes
	 */
	@Override
	public CategoryVodBoxSacRes searchVodBoxList(CategoryVodBoxSacReq requestVO, SacRequestHeader requestHeader) {

		// 헤더 값 세팅
		this.log.debug("헤더 값 세팅");
		requestVO.setTenantId(requestHeader.getTenantHeader().getTenantId());
		requestVO.setLangCd(requestHeader.getTenantHeader().getLangCd());
		requestVO.setDeviceModelCd(requestHeader.getDeviceHeader().getModel());

		// 요청 값 세팅
		this.log.debug("요청 값 세팅");
		requestVO.setOffset(requestVO.getOffset() != null ? requestVO.getOffset() : 1);
		requestVO.setCount(requestVO.getCount() != null ? requestVO.getCount() : 20);
		requestVO.setDuration(requestVO.getDuration() != null ? requestVO.getDuration() : 30);
		requestVO.setChapter(requestVO.getChapter() != null ? requestVO.getChapter() : 0);

		// 필수 파라미터 체크
		this.log.debug("필수 파라미터 체크");
		if (StringUtils.isEmpty(requestVO.getChannelId())) {
			throw new StorePlatformException("SAC_DSP_0002", "channelId", requestVO.getChannelId());
		} else if (StringUtils.isEmpty(requestVO.getFilteredBy())) {
			throw new StorePlatformException("SAC_DSP_0002", "filteredBy", requestVO.getFilteredBy());
		} else if (!requestVO.getFilteredBy().equals("duration") && !requestVO.getFilteredBy().equals("chapter")
				&& !requestVO.getFilteredBy().equals("regDate")) {
			throw new StorePlatformException("SAC_DSP_0003", "filteredBy", requestVO.getFilteredBy());
		} else if (requestVO.getFilteredBy().equals("regDate") && StringUtils.isEmpty(requestVO.getRegDate())) {
			throw new StorePlatformException("SAC_DSP_0003", "regDate", requestVO.getRegDate());
		}

		CategoryVodBoxSacRes categoryVodBoxSacRes = new CategoryVodBoxSacRes();
		CommonResponse commonResponse = new CommonResponse();
		List<Product> productList = new ArrayList<Product>();
		Product product;
		Identifier identifier;
		List<Identifier> identifierList;
		Title title;
		Menu menu;
		List<Menu> menuList;
		Rights rights;
		Preview preview;
		Support support;
		List<Support> supportList;
		Source source;
		List<Source> sourceList;
		Store store;
		Price price;
		Vod vod;
		Time runningTime;
		VideoInfo videoInfo;
		List<VideoInfo> videoInfoList;
		VodExplain vodExplain;
		Contributor contributor;
		Date date;
		Play play;
		Chapter chapter;

		// VOD 보관함 조회
		this.log.debug("VOD 보관함 조회");
		List<CategoryVodBox> categoryVodBoxList = this.commonDAO.queryForList("CategoryVodBox.selectCategoryVodBox",
				requestVO, CategoryVodBox.class);

		if (!categoryVodBoxList.isEmpty()) {

			for (CategoryVodBox categoryVodBox : categoryVodBoxList) {

				product = new Product();
				/*
				 * IdentifierList
				 */
				identifierList = new ArrayList<Identifier>();
				identifier = new Identifier();
				identifier.setType(DisplayConstants.DP_CONTENT_IDENTIFIER_CD);
				identifier.setText(categoryVodBox.getCid());
				identifierList.add(identifier);
				identifier = new Identifier();
				identifier.setType(DisplayConstants.DP_CHANNEL_IDENTIFIER_CD);
				identifier.setText(categoryVodBox.getChnlProdId());
				identifierList.add(identifier);
				product.setIdentifierList(identifierList);

				/*
				 * SupportList
				 */
				supportList = new ArrayList<Support>();
				support = new Support();
				support.setType(DisplayConstants.DP_VOD_BTV_SUPPORT_NM);
				support.setText(categoryVodBox.getBtvYn());
				supportList.add(support);
				product.setSupportList(supportList);

				/*
				 * TITLE
				 */
				title = new Title();
				title.setText(categoryVodBox.getProdNm());
				product.setTitle(title);

				/*
				 * ProductExplain
				 */
				product.setProductExplain(categoryVodBox.getProdBaseDesc());

				/*
				 * MenuList
				 */
				menuList = new ArrayList<Menu>();
				menu = new Menu();
				menu.setId(categoryVodBox.getTopMenuId());
				menu.setName(categoryVodBox.getTopMenuNm());
				menu.setType(DisplayConstants.DP_MENU_TOPCLASS_TYPE);
				menuList.add(menu);
				menu = new Menu();
				menu.setId(categoryVodBox.getMenuId());
				menu.setName(categoryVodBox.getMenuNm());
				menuList.add(menu);
				menu = new Menu();
				menu.setId(categoryVodBox.getMetaClsfCd());
				menu.setType(DisplayConstants.DP_META_CLASS_MENU_TYPE);
				menuList.add(menu);
				product.setMenuList(menuList);

				/*
				 * Contributor
				 */
				contributor = new Contributor();
				contributor.setDirector(categoryVodBox.getArtist2Nm());
				if (categoryVodBox.getTopMenuId().equals(DisplayConstants.DP_TV_TOP_MENU_ID)) { // TV 방송
					contributor.setChannel(categoryVodBox.getBrdcCompNm());
				}
				contributor.setArtist(categoryVodBox.getArtist1Nm());
				date = new Date();
				date.setType(DisplayConstants.DP_DATE_BROADCAST);
				date.setText(categoryVodBox.getIssueDay());
				contributor.setDate(date);
				product.setContributor(contributor);

				/*
				 * Date
				 */
				date = new Date();
				date.setType(DisplayConstants.DP_DATE_REG);
				date.setText(categoryVodBox.getRegDt());
				product.setDate(date);

				/*
				 * Rights
				 */
				rights = new Rights();
				rights.setGrade(categoryVodBox.getProdGrdCd());

				preview = new Preview();
				sourceList = new ArrayList<Source>();
				source = new Source();
				source.setType(DisplayConstants.DP_PREVIEW_LQ); // 고화질
				source.setUrl(categoryVodBox.getSamplUrl());
				sourceList.add(source);
				source = new Source();
				source.setType(DisplayConstants.DP_PREVIEW_HQ); // 저화질
				source.setUrl(categoryVodBox.getScSamplUrl());
				sourceList.add(source);
				preview.setSourceList(sourceList);
				rights.setPreview(preview);

				if (!categoryVodBox.getUsePeriodUnitCd().equals(DisplayConstants.DP_USE_PERIOD_UNIT_CD_NONE)) { // 바로보기
					play = new Play();
					supportList = new ArrayList<Support>();
					support = new Support();
					support.setType(DisplayConstants.DP_DRM_SUPPORT_NM);
					support.setText(categoryVodBox.getDrmYn());
					supportList.add(support);
					play.setSupportList(supportList);
					date = new Date();
					date.setType(DisplayConstants.DP_SHOPPING_RIGHTS_TYPE_UNIT_NM);
					date.setText(categoryVodBox.getUsePeriod() + categoryVodBox.getUsePeriodUnitNm());
					play.setDate(date);
					price = new Price();
					price.setText(categoryVodBox.getProdAmt());
					play.setPrice(price);
					identifierList = new ArrayList<Identifier>();
					identifier = new Identifier();
					identifier.setType(DisplayConstants.DP_EPISODE_IDENTIFIER_CD);
					identifier.setText(categoryVodBox.getProdId());
					identifierList.add(identifier);
					play.setIdentifierList(identifierList);
					// play.setPlayProductStatusCode("restrict"); // "사용중" 상태가 아닐경우에 "restrict" 노출(항상 사용중)
					rights.setPlay(play);
				} else if (categoryVodBox.getUsePeriodUnitCd().equals(DisplayConstants.DP_USE_PERIOD_UNIT_CD_NONE)) { // 다운로드
					store = new Store();
					supportList = new ArrayList<Support>();
					support = new Support();
					support.setType(DisplayConstants.DP_DRM_SUPPORT_NM);
					support.setText(categoryVodBox.getDrmYn());
					supportList.add(support);
					store.setSupportList(supportList);
					price = new Price();
					price.setText(categoryVodBox.getProdAmt());
					store.setPrice(price);
					identifierList = new ArrayList<Identifier>();
					identifier = new Identifier();
					identifier.setType(DisplayConstants.DP_EPISODE_IDENTIFIER_CD);
					identifier.setText(categoryVodBox.getProdId());
					identifierList.add(identifier);
					store.setIdentifierList(identifierList);
					// store.setStoreProductStatusCode("restrict"); // "사용중" 상태가 아닐경우에 "restrict" 노출(항상 사용중)
					rights.setStore(store);
				}
				product.setRights(rights);

				/*
				 * VOD
				 */
				vod = new Vod();
				runningTime = new Time();
				// runningTime.setUnit("");
				runningTime.setText(categoryVodBox.getEpsdPlayTm());
				vod.setRunningTime(runningTime);
				chapter = new Chapter();
				chapter.setUnit(categoryVodBox.getChapterUnit());
				chapter.setText(categoryVodBox.getChapter());
				vod.setChapter(chapter);

				vodExplain = new VodExplain();
				vodExplain.setSaleDateInfo(categoryVodBox.getIssueDay());
				vod.setVodExplain(vodExplain);

				videoInfoList = new ArrayList<VideoInfo>();

				if (StringUtils.isNotEmpty(categoryVodBox.getNmSubContentsId())) {
					videoInfo = new VideoInfo();
					videoInfo.setType("normal");
					videoInfo.setScid(categoryVodBox.getNmSubContentsId());
					videoInfo.setPixel(categoryVodBox.getNmRsltnNm());
					videoInfo.setPictureSize(categoryVodBox.getNmDpPgRatioNm());
					videoInfo.setVersion(categoryVodBox.getNmProdVer());
					videoInfo.setBtvcid(categoryVodBox.getNmBtvCid());
					videoInfo.setSize(categoryVodBox.getNmFileSize());
					videoInfoList.add(videoInfo);
				}

				if (StringUtils.isNotEmpty(categoryVodBox.getSdSubContentsId())) {
					videoInfo = new VideoInfo();
					videoInfo.setType("sd");
					videoInfo.setScid(categoryVodBox.getSdSubContentsId());
					videoInfo.setPixel(categoryVodBox.getSdRsltnNm());
					videoInfo.setPictureSize(categoryVodBox.getSdDpPgRatioNm());
					videoInfo.setVersion(categoryVodBox.getSdProdVer());
					videoInfo.setBtvcid(categoryVodBox.getSdBtvCid());
					videoInfo.setSize(categoryVodBox.getSdFileSize());
					videoInfoList.add(videoInfo);
				}

				if (StringUtils.isNotEmpty(categoryVodBox.getHdSubContentsId())) {
					videoInfo = new VideoInfo();
					videoInfo.setType("hd");
					videoInfo.setScid(categoryVodBox.getHdSubContentsId());
					videoInfo.setPixel(categoryVodBox.getHdRsltnNm());
					videoInfo.setPictureSize(categoryVodBox.getHdDpPgRatioNm());
					videoInfo.setVersion(categoryVodBox.getHdProdVer());
					videoInfo.setBtvcid(categoryVodBox.getHdBtvCid());
					videoInfo.setSize(categoryVodBox.getHdFileSize());
					videoInfoList.add(videoInfo);
				}

				vod.setVideoInfoList(videoInfoList);
				product.setVod(vod);

				productList.add(product);
			}
			categoryVodBoxSacRes.setProductList(productList);
			commonResponse.setTotalCount(categoryVodBoxList.get(0).getTotalCount());
		} else {
			commonResponse.setTotalCount(0);
		}
		categoryVodBoxSacRes.setCommonRes(commonResponse);

		// Dummy data
		// if (true) {
		//
		// // Response VO를 만들기위한 생성자
		// List<Product> productList = new ArrayList<Product>();
		// List<Menu> menuList = new ArrayList<Menu>();
		//
		// Product product = new Product();
		// Identifier identifier;
		// List<Identifier> identifierList;
		// Title title;
		// Menu menu;
		// Rights rights;
		// Preview preview;
		// Support support;
		// List<Support> supportList;
		// Source source;
		// List<Source> sourceList;
		// Store store;
		// Price price;
		// Vod vod;
		// Time runningTime;
		// VideoInfo videoInfo;
		// List<VideoInfo> videoInfoList;
		// VodExplain vodExplain;
		// Contributor contributor;
		// Date date;
		// Play play;
		// Chapter chapter;
		//
		// /*
		// * IdentifierList
		// */
		// identifierList = new ArrayList<Identifier>();
		// identifier = new Identifier();
		// identifier.setType("Content");
		// identifier.setText("0000029452");
		// identifierList.add(identifier);
		// identifier = new Identifier();
		// identifier.setType("channel");
		// identifier.setText("H900000372");
		// identifierList.add(identifier);
		// product.setIdentifierList(identifierList);
		//
		// /*
		// * SupportList
		// */
		// supportList = new ArrayList<Support>();
		// support = new Support();
		// support.setType("btv");
		// support.setText("Y");
		// supportList.add(support);
		// product.setSupportList(supportList);
		//
		// /*
		// * TITLE
		// */
		// title = new Title();
		// title.setText("프리즌브레이크 시즌 1");
		// product.setTitle(title);
		//
		// /*
		// * ProductExplain
		// */
		// product.setProductExplain("사형 집행 직전, 전기의자에 앉은 링컨의 눈에 아버지의 모습이 보이고, 잠시 후 사형을 연기한다는 판사의 전화가 걸려온다. 한편, 마이클은 새 통로를 찾다가 화상을 입어 문신의 일부를 잃는다.");
		//
		// /*
		// * Contributor
		// */
		// contributor = new Contributor();
		// contributor.setDirector("");
		// contributor.setChannel("");
		// contributor.setArtist("웬트워스 밀러, 도미닉 퍼셀");
		// date = new Date();
		// date.setType("date/broadcast");
		// date.setText("");
		// contributor.setDate(date);
		// product.setContributor(contributor);
		//
		// /*
		// * MenuList
		// */
		// menuList = new ArrayList<Menu>();
		// menu = new Menu();
		// menu.setId("DP18");
		// menu.setName("TV 방송");
		// menu.setType("topCategory");
		// menuList.add(menu);
		// menu = new Menu();
		// menu.setId("DP18002");
		// menu.setName("미드/외화");
		// menuList.add(menu);
		// menu = new Menu();
		// menu.setId("CT14");
		// menu.setType("metaClass");
		// menuList.add(menu);
		// product.setMenuList(menuList);
		//
		// /*
		// * Date
		// */
		// date = new Date();
		// date.setType("date/reg");
		// date.setText("20101223212513");
		// product.setDate(date);
		//
		// /*
		// * Rights
		// */
		// rights = new Rights();
		// rights.setGrade("2");
		// preview = new Preview();
		// sourceList = new ArrayList<Source>();
		// source = new Source();
		// source.setType("video/x-freeview-lq");
		// source.setUrl("/SMILE_DATA/201004/27/0000023733/2/06_DanielPowter-BadDay_short(1).mp4");
		// sourceList.add(source);
		// source = new Source();
		// source.setType("video/x-freeview-hq");
		// source.setUrl("/SMILE_DATA/201004/27/0000023733/2/06_DanielPowter-BadDay_short.mp4");
		// sourceList.add(source);
		// preview.setSourceList(sourceList);
		// rights.setPreview(preview);
		// play = new Play();
		// supportList = new ArrayList<Support>();
		// support = new Support();
		// support.setType("drm");
		// support.setText("Y");
		// supportList.add(support);
		// play.setSupportList(supportList);
		// date = new Date();
		// date.setType("uint/usagePeriod");
		// date.setText("2일");
		// play.setDate(date);
		// price = new Price();
		// price.setText(600);
		// play.setPrice(price);
		// identifierList = new ArrayList<Identifier>();
		// identifier = new Identifier();
		// identifier.setType("episode");
		// identifier.setText("H900000394");
		// identifierList.add(identifier);
		// play.setIdentifierList(identifierList);
		// play.setPlayProductStatusCode("");
		// rights.setPlay(play);
		// store = new Store();
		// supportList = new ArrayList<Support>();
		// support = new Support();
		// support.setType("drm");
		// support.setText("N");
		// supportList.add(support);
		// store.setSupportList(supportList);
		// price = new Price();
		// price.setText(0);
		// store.setPrice(price);
		// identifierList = new ArrayList<Identifier>();
		// identifier = new Identifier();
		// identifier.setType("episode");
		// identifier.setText("");
		// identifierList.add(identifier);
		// store.setIdentifierList(identifierList);
		// store.setStoreProductStatusCode("");
		// rights.setStore(store);
		// product.setRights(rights);
		//
		// /*
		// * VOD
		// */
		// vod = new Vod();
		// runningTime = new Time();
		// runningTime.setUnit("");
		// runningTime.setText("45");
		// vod.setRunningTime(runningTime);
		// chapter = new Chapter();
		// chapter.setUnit("회");
		// chapter.setText(15);
		// vod.setChapter(chapter);
		// vodExplain = new VodExplain();
		// vodExplain.setSaleDateInfo("");
		// vodExplain.setText("");
		// vod.setVodExplain(vodExplain);
		// videoInfoList = new ArrayList<VideoInfo>();
		// videoInfo = new VideoInfo();
		// videoInfo.setType("normal");
		// videoInfo.setScid("0000030215");
		// videoInfo.setPixel("640x480");
		// videoInfo.setPictureSize("4:3");
		// videoInfo.setVersion("1");
		// videoInfo.setBtvcid("{3252188A-789A-4C5D-9417-16909CDAB444}");
		// videoInfo.setSize("0");
		// videoInfoList.add(videoInfo);
		// videoInfo = new VideoInfo();
		// videoInfo.setType("sd");
		// videoInfo.setScid("0000030216");
		// videoInfo.setPixel("640x480");
		// videoInfo.setPictureSize("4:3");
		// videoInfo.setVersion("1");
		// videoInfo.setBtvcid("{3252188A-789A-4C5D-9417-16909CDAB444}");
		// videoInfo.setSize("0");
		// videoInfoList.add(videoInfo);
		// videoInfo = new VideoInfo();
		// videoInfo.setType("hd");
		// videoInfo.setScid("0000030214");
		// videoInfo.setPixel("640x480");
		// videoInfo.setPictureSize("4:3");
		// videoInfo.setVersion("1");
		// videoInfo.setBtvcid("{3252188A-789A-4C5D-9417-16909CDAB444}");
		// videoInfo.setSize("0");
		// videoInfoList.add(videoInfo);
		// vod.setVideoInfoList(videoInfoList);
		// product.setVod(vod);
		//
		// productList.add(product);
		//
		// vodBoxListRes = new VodBoxListRes();
		// commonResponse = new CommonResponse();
		// vodBoxListRes.setProductList(productList);
		// commonResponse.setTotalCount(totalCount);
		// vodBoxListRes.setCommonRes(commonResponse);
		// }
		return categoryVodBoxSacRes;

	}

}
