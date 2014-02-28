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

import com.skplanet.storeplatform.framework.core.persistence.dao.CommonDAO;
import com.skplanet.storeplatform.sac.client.display.vo.category.CategoryVodBoxSacReq;
import com.skplanet.storeplatform.sac.client.display.vo.category.CategoryVodBoxSacRes;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.common.CommonResponse;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.common.Date;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.common.Identifier;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.common.Menu;
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
import com.skplanet.storeplatform.sac.display.response.CommonMetaInfoGeneratorImpl;

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

	@Autowired
	CommonMetaInfoGeneratorImpl commonMetaInfo;

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
		requestVO.setMmDeviceModelCd(DisplayConstants.DP_ANY_PHONE_4MM);

		// 요청 값 세팅
		this.log.debug("요청 값 세팅");
		requestVO.setOffset(requestVO.getOffset() != null ? requestVO.getOffset() : 1);
		requestVO.setCount(requestVO.getCount() != null ? requestVO.getCount() : 20);
		requestVO.setDuration(requestVO.getDuration() != null ? requestVO.getDuration() : 30);
		requestVO.setChapter(requestVO.getChapter() != null ? requestVO.getChapter() : 0);
		requestVO.setRegDate(requestVO.getRegDate() != null ? requestVO.getRegDate() : "20000101000000");

		CategoryVodBoxSacRes categoryVodBoxSacRes = new CategoryVodBoxSacRes();
		CommonResponse commonResponse = new CommonResponse();
		List<Product> productList = new ArrayList<Product>();
		Product product;
		List<Identifier> identifierList;
		List<Menu> menuList;
		Rights rights;
		Preview preview;
		List<Support> supportList;
		List<Source> sourceList;
		Store store;
		Vod vod;
		VideoInfo videoInfo;
		List<VideoInfo> videoInfoList;
		VodExplain vodExplain;
		Contributor contributor;
		Play play;

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
				identifierList.add(this.commonMetaInfo.generateIdentifier(DisplayConstants.DP_CONTENT_IDENTIFIER_CD,
						categoryVodBox.getCid()));
				identifierList.add(this.commonMetaInfo.generateIdentifier(DisplayConstants.DP_CHANNEL_IDENTIFIER_CD,
						categoryVodBox.getChnlProdId()));
				product.setIdentifierList(identifierList);

				// /*
				// * SupportList // 삭제 예정
				// */
				// supportList = new ArrayList<Support>();
				// support = new Support();
				// supportList.add(this.commonMetaInfo.generateSupport(DisplayConstants.DP_VOD_BTV_SUPPORT_NM,
				// categoryVodBox.getBtvYn()));
				// product.setSupportList(supportList);

				/*
				 * TITLE
				 */
				product.setTitle(new Title(categoryVodBox.getProdNm()));

				/*
				 * ProductExplain
				 */
				product.setProductExplain(categoryVodBox.getProdBaseDesc());

				/*
				 * MenuList
				 */
				menuList = new ArrayList<Menu>();
				menuList.add(new Menu(categoryVodBox.getTopMenuId(), categoryVodBox.getTopMenuNm(),
						DisplayConstants.DP_MENU_TOPCLASS_TYPE));
				menuList.add(new Menu(categoryVodBox.getMenuId(), categoryVodBox.getMenuNm(), null));
				menuList.add(new Menu(categoryVodBox.getMetaClsfCd(), null, DisplayConstants.DP_META_CLASS_MENU_TYPE));
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
				contributor.setDate(this.commonMetaInfo.generateDate(DisplayConstants.DP_DATE_BROADCAST,
						categoryVodBox.getIssueDay()));
				product.setContributor(contributor);

				/*
				 * Date
				 */
				product.setDate(this.commonMetaInfo.generateDate(DisplayConstants.DP_DATE_REG,
						categoryVodBox.getRegDt()));

				/*
				 * Rights
				 */
				rights = new Rights();
				rights.setGrade(categoryVodBox.getProdGrdCd());

				preview = new Preview();
				sourceList = new ArrayList<Source>();
				sourceList.add(this.commonMetaInfo.generateSource(DisplayConstants.DP_PREVIEW_LQ,
						categoryVodBox.getSamplUrl())); // 고화질
				sourceList.add(this.commonMetaInfo.generateSource(DisplayConstants.DP_PREVIEW_HQ,
						categoryVodBox.getScSamplUrl())); // 저화질
				preview.setSourceList(sourceList);
				rights.setPreview(preview);

				if (!categoryVodBox.getUsePeriodUnitCd().equals(DisplayConstants.DP_USE_PERIOD_UNIT_CD_NONE)) { // 바로보기
					play = new Play();
					supportList = new ArrayList<Support>();
					supportList.add(this.commonMetaInfo.generateSupport(DisplayConstants.DP_DRM_SUPPORT_NM,
							categoryVodBox.getDrmYn()));
					play.setSupportList(supportList);
					play.setDate(new Date(DisplayConstants.DP_SHOPPING_RIGHTS_TYPE_UNIT_NM, categoryVodBox
							.getUsePeriod() + categoryVodBox.getUsePeriodUnitNm()));
					play.setPrice(this.commonMetaInfo.generatePrice(categoryVodBox.getProdAmt(),
							categoryVodBox.getProdNetAmt()));
					identifierList = new ArrayList<Identifier>();
					identifierList.add(this.commonMetaInfo.generateIdentifier(
							DisplayConstants.DP_EPISODE_IDENTIFIER_CD, categoryVodBox.getProdId()));
					play.setIdentifierList(identifierList);
					// play.setPlayProductStatusCode("restrict"); // "사용중" 상태가 아닐경우에 "restrict" 노출(항상 사용중)
					rights.setPlay(play);
				} else if (categoryVodBox.getUsePeriodUnitCd().equals(DisplayConstants.DP_USE_PERIOD_UNIT_CD_NONE)) { // 다운로드
					store = new Store();
					supportList = new ArrayList<Support>();
					supportList.add(this.commonMetaInfo.generateSupport(DisplayConstants.DP_DRM_SUPPORT_NM,
							categoryVodBox.getDrmYn()));
					store.setSupportList(supportList);
					store.setPrice(this.commonMetaInfo.generatePrice(categoryVodBox.getProdAmt(),
							categoryVodBox.getProdNetAmt()));
					identifierList = new ArrayList<Identifier>();
					identifierList.add(this.commonMetaInfo.generateIdentifier(
							DisplayConstants.DP_EPISODE_IDENTIFIER_CD, categoryVodBox.getProdId()));
					store.setIdentifierList(identifierList);
					// store.setStoreProductStatusCode("restrict"); // "사용중" 상태가 아닐경우에 "restrict" 노출(항상 사용중)
					rights.setStore(store);
				}
				product.setRights(rights);

				/*
				 * VOD
				 */
				vod = new Vod();
				vod.setRunningTime(new Time(null, categoryVodBox.getEpsdPlayTm()));
				vod.setChapter(new Chapter(categoryVodBox.getChapterUnit(), categoryVodBox.getChapter()));

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
					// videoInfo.setBtvcid(categoryVodBox.getNmBtvCid()); // 삭제 예정
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
					// videoInfo.setBtvcid(categoryVodBox.getSdBtvCid()); // 삭제 예정
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
					// videoInfo.setBtvcid(categoryVodBox.getHdBtvCid()); // 삭제 예정
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
		return categoryVodBoxSacRes;

	}

}
