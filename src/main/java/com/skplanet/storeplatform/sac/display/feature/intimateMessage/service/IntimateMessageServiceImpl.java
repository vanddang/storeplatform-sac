/*
 * Copyright (c) 2013 SK planet.
 * All right reserved.
 *
 * This software is the confidential and proprietary information of SK planet.
 * You shall not disclose such Confidential Information and
 * shall use it only in accordance with the terms of the license agreement
 * you entered into with SK planet.
 */
package com.skplanet.storeplatform.sac.display.feature.intimateMessage.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.skplanet.storeplatform.framework.core.persistence.dao.CommonDAO;
import com.skplanet.storeplatform.sac.client.display.vo.feature.intimateMessage.IntimateMessageAppCodiSacReq;
import com.skplanet.storeplatform.sac.client.display.vo.feature.intimateMessage.IntimateMessageAppCodiSacRes;
import com.skplanet.storeplatform.sac.client.display.vo.feature.intimateMessage.IntimateMessageSacReq;
import com.skplanet.storeplatform.sac.client.display.vo.feature.intimateMessage.IntimateMessageSacRes;
import com.skplanet.storeplatform.sac.client.internal.member.user.sci.DeviceSCI;
import com.skplanet.storeplatform.sac.client.internal.member.user.sci.SearchUserSCI;
import com.skplanet.storeplatform.sac.client.internal.member.user.vo.ChangedDeviceHistorySacReq;
import com.skplanet.storeplatform.sac.client.internal.member.user.vo.ChangedDeviceHistorySacRes;
import com.skplanet.storeplatform.sac.client.internal.member.user.vo.SearchUserGradeSacReq;
import com.skplanet.storeplatform.sac.client.internal.member.user.vo.SearchUserGradeSacRes;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.common.CommonResponse;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.common.Date;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.common.Identifier;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.common.Source;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.common.Title;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.common.Url;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.product.IntimateMessage;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.product.Product;
import com.skplanet.storeplatform.sac.common.header.vo.SacRequestHeader;
import com.skplanet.storeplatform.sac.common.util.DateUtils;
import com.skplanet.storeplatform.sac.display.common.DisplayCommonUtil;
import com.skplanet.storeplatform.sac.display.common.constant.DisplayConstants;
import com.skplanet.storeplatform.sac.display.common.service.DisplayCommonService;
import com.skplanet.storeplatform.sac.display.common.vo.SupportDevice;
import com.skplanet.storeplatform.sac.display.feature.intimateMessage.vo.IntimateMessageDefault;
import com.skplanet.storeplatform.sac.display.meta.service.MetaInfoService;
import com.skplanet.storeplatform.sac.display.meta.vo.MetaInfo;
import com.skplanet.storeplatform.sac.display.meta.vo.ProductBasicInfo;
import com.skplanet.storeplatform.sac.display.response.ResponseInfoGenerateFacade;

/**
 * DownloadComic Service 인터페이스(CoreStoreBusiness) 구현체
 * 
 * Updated on : 2014. 1. 28. Updated by : 이태희.
 */
@Service
public class IntimateMessageServiceImpl implements IntimateMessageService {
	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	@Qualifier("sac")
	private CommonDAO commonDAO;

	@Autowired
	private DeviceSCI deviceSCI;

	@Autowired
	private SearchUserSCI searchUserSCI;

	@Autowired
	private DisplayCommonService displayCommonService;

	@Autowired
	MetaInfoService metaInfoService;

	@Autowired
	private ResponseInfoGenerateFacade responseInfoGenerateFacade;

	private final String appCodiBatchId = "MSG0000001";

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.skplanet.storeplatform.sac.display.feature.intimateMessage.service.IntimateMessageService#
	 * searchIntimateMessageList(com.skplanet.storeplatform.sac.common.header.vo.SacRequestHeader,
	 * com.skplanet.storeplatform.sac.client.display.vo.feature.intimateMessage.IntimateMessageSacReq)
	 */
	@Override
	public IntimateMessageSacRes searchIntimateMessageList(SacRequestHeader header, IntimateMessageSacReq messageReq) {
		IntimateMessageSacRes intimateMessageRes = new IntimateMessageSacRes();
		CommonResponse commonResponse = new CommonResponse();
		List<IntimateMessage> intimateMessageList = new ArrayList<IntimateMessage>();

		String userKey = messageReq.getUserKey();
		String deviceKey = messageReq.getDeviceKey();

		this.logger.debug("----------------------------------------------------------------");
		this.logger.debug("[searchIntimateMessageList] userKey : {}", userKey);
		this.logger.debug("[searchIntimateMessageList] deviceId : {}", deviceKey);
		this.logger.debug("----------------------------------------------------------------");

		// offset Default 값 세팅
		if (messageReq.getOffset() == null) {
			messageReq.setOffset(1);
		}
		// count Default 값 세팅
		if (messageReq.getCount() == null) {
			messageReq.setCount(20);
		}

		// 페이징 계산
		messageReq.setCount(messageReq.getOffset() + messageReq.getCount() - 1);
		// 헤더정보 세팅
		messageReq.setTenantId(header.getTenantHeader().getTenantId());

		// 기기변경 이력 조회를 위한 생성자
		ChangedDeviceHistorySacReq deviceReq = null;
		ChangedDeviceHistorySacRes deviceRes = null;

		// 회원등급 조회를 위한 생성자
		SearchUserGradeSacReq gradeReq = null;
		SearchUserGradeSacRes gradeRes = null;

		if ("all".equals(messageReq.getMsgType())) {
			try {
				gradeReq = new SearchUserGradeSacReq();
				gradeReq.setUserKey(userKey);

				// 회원등급 조회
				this.logger.info("##### [SAC DSP LocalSCI] SAC Member Start : searchUserSCI.searchUserGrade");
				long start = System.currentTimeMillis();
				gradeRes = this.searchUserSCI.searchUserGrade(gradeReq);
				this.logger.info("##### [SAC DSP LocalSCI] SAC Member End : searchUserSCI.searchUserGrade");
				long end = System.currentTimeMillis();
				this.logger.info("##### [SAC DSP LocalSCI] SAC Member searchUserSCI.searchUserGrade takes {} ms",
						(end - start));
			} catch (Exception ex) {
				this.logger.error("회원등급 조회 연동 중 오류가 발생하였습니다.\n", ex);
			}

			// 회원등급 세팅
			if (gradeRes != null) {
				messageReq.setMemberGrade(gradeRes.getGradeInfoSac().getUserGradeCd());
				this.logger.info("MemberGrade", messageReq.getMemberGrade());
			}

			try {
				deviceReq = new ChangedDeviceHistorySacReq();
				deviceReq.setUserKey(userKey);
				deviceReq.setDeviceKey(deviceKey);

				// 기기변경 이력 조회
				this.logger.info("##### [SAC DSP LocalSCI] SAC Member Start : deviceSCI.searchChangedDeviceHistory");
				long start = System.currentTimeMillis();
				deviceRes = this.deviceSCI.searchChangedDeviceHistory(deviceReq);
				this.logger.info("##### [SAC DSP LocalSCI] SAC Member End : deviceSCI.searchChangedDeviceHistory");
				long end = System.currentTimeMillis();
				this.logger.info(
						"##### [SAC DSP LocalSCI] SAC Member deviceSCI.searchChangedDeviceHistory takes {} ms",
						(end - start));
			} catch (Exception ex) {
				this.logger.error("기기변경 이력 조회 연동 중 오류가 발생하였습니다.\n", ex);
			}

			// 기기변경이력 세팅
			if (deviceRes != null) {
				messageReq.setDeviceChangeYn(deviceRes.getIsChanged());
				this.logger.info("DeviceChangeYn", messageReq.getDeviceChangeYn());
			}
		}

		// Intimate Message 조회
		List<IntimateMessageDefault> resultList = this.commonDAO.queryForList(
				"IntimateMessage.searchIntimateMessageList", messageReq, IntimateMessageDefault.class);

		if (resultList != null && !resultList.isEmpty()) {
			IntimateMessageDefault messageDefault = new IntimateMessageDefault();

			IntimateMessage intimateMessage = null;
			Identifier identifier = null;
			Title title = null;
			Title subTitle = null;
			Url url = null;
			Date date = null;
			Source source = null;

			List<Identifier> identifierList = null;
			List<Source> sourceList = null;

			for (int i = 0; i < resultList.size(); i++) {
				messageDefault = resultList.get(i);
				intimateMessage = new IntimateMessage();

				// ID 정보
				identifier = new Identifier();
				identifierList = new ArrayList<Identifier>();
				identifier.setType(messageDefault.getMsgTypeCd());
				identifier.setText(messageDefault.getMsgId());
				identifierList.add(identifier);
				intimateMessage.setIdentifierList(identifierList);

				// 메인 제목
				title = new Title();
				title.setText(messageDefault.getMainMsg());
				title.setColor(messageDefault.getMainColor());
				intimateMessage.setTitle(title);

				// 하위 제목
				subTitle = new Title();
				subTitle.setText(messageDefault.getInfrMsg());
				subTitle.setColor(messageDefault.getInfrColor());
				intimateMessage.setSubTitle(subTitle);

				// 오퍼링 타입
				if ("url".equals(messageDefault.getOfrTypeCd())) {
					url = new Url();
					date = new Date();

					date.setType(DisplayConstants.DP_DATE_REG);
					date.setTextFromDate(DateUtils.parseDate(messageDefault.getRegDt()));
					url.setDate(date);
					url.setText(messageDefault.getOfrDesc());
					intimateMessage.setUrl(url);
				} else if ("themeRecomm".equals(messageDefault.getOfrTypeCd())) {
					intimateMessage.setThemeRecommId(messageDefault.getOfrDesc());
				} else if ("appCodi".equals(messageDefault.getOfrTypeCd())) {
					if (StringUtils.isEmpty(messageDefault.getOfrDesc())) {
						intimateMessage.setAppCodi("none");
					} else {
						intimateMessage.setAppCodi(messageDefault.getOfrDesc());
					}
				} else if ("purchaseHistory".equals(messageDefault.getOfrTypeCd())) {
					intimateMessage.setPurchaseHistory("purchaseHistory");
				}

				// 배경 이미지
				if (StringUtils.isNotEmpty(messageDefault.getGnbImgPath())) {
					source = new Source();
					sourceList = new ArrayList<Source>();
					source.setMediaType(DisplayCommonUtil.getMimeType(messageDefault.getGnbImgPath()));
					source.setType(DisplayConstants.DP_SOURCE_TYPE_GNB_BG);
					source.setUrl(messageDefault.getGnbImgPath());
					sourceList.add(source);
				}

				// 아이콘 이미지
				if (StringUtils.isNotEmpty(messageDefault.getBiImgPath())) {
					if (sourceList == null) {
						sourceList = new ArrayList<Source>();
					}
					source = new Source();
					source.setMediaType(DisplayCommonUtil.getMimeType(messageDefault.getBiImgPath()));
					source.setType(DisplayConstants.DP_SOURCE_TYPE_GNB_ICON);
					source.setUrl(messageDefault.getBiImgPath());
					sourceList.add(source);
				}

				if (sourceList != null) {
					intimateMessage.setSourceList(sourceList);
					sourceList = null;
				}

				intimateMessageList.add(intimateMessage);
			}

			commonResponse.setTotalCount(messageDefault.getTotalCount());
		} else {
			commonResponse.setTotalCount(0);
		}

		intimateMessageRes.setCommonResponse(commonResponse);
		intimateMessageRes.setIntimateMessageList(intimateMessageList);

		return intimateMessageRes;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.skplanet.storeplatform.sac.display.feature.intimateMessage.service.IntimateMessageService#
	 * searchIntimateMessageAppCodiList(com.skplanet.storeplatform.sac.common.header.vo.SacRequestHeader,
	 * com.skplanet.storeplatform.sac.client.display.vo.feature.intimateMessage.IntimateMessageAppCodiSacReq)
	 */
	@Override
	public IntimateMessageAppCodiSacRes searchIntimateMessageAppCodiList(SacRequestHeader header,
			IntimateMessageAppCodiSacReq messageReq) {

		IntimateMessageAppCodiSacRes appCodiRes = new IntimateMessageAppCodiSacRes();
		CommonResponse commonResponse = new CommonResponse();
		List<Product> productList = new ArrayList<Product>();

		String daCode = messageReq.getDaCode();

		this.logger.debug("----------------------------------------------------------------");
		this.logger.debug("[searchIntimateMessageAppCodiList] daCode : {}", daCode);
		this.logger.debug("----------------------------------------------------------------");

		// offset Default 값 세팅
		if (messageReq.getOffset() == null) {
			messageReq.setOffset(1);
		}
		// count Default 값 세팅
		if (messageReq.getCount() == null) {
			messageReq.setCount(20);
		}

		// 페이징 계산
		messageReq.setCount(messageReq.getOffset() + messageReq.getCount() - 1);

		// 헤더정보 세팅
		messageReq.setTenantId(header.getTenantHeader().getTenantId());
		messageReq.setLangCd(header.getTenantHeader().getLangCd());
		messageReq.setDeviceModelCd(header.getDeviceHeader().getModel());
		messageReq.setAnyDeviceModelCd(DisplayConstants.DP_ANY_PHONE_4MM);

		// 배치완료 기준일시 조회
		String stdDt = this.displayCommonService.getBatchStandardDateString(messageReq.getTenantId(),
				this.appCodiBatchId);

		if (StringUtils.isNotEmpty(stdDt)) {
			messageReq.setStdDt(stdDt);

			// 단말 지원정보 조회
			SupportDevice supportDevice = this.displayCommonService.getSupportDeviceInfo(header.getDeviceHeader()
					.getModel());

			if (supportDevice != null) {
				messageReq.setEbookSprtYn(supportDevice.getEbookSprtYn());
				messageReq.setComicSprtYn(supportDevice.getComicSprtYn());
				messageReq.setMusicSprtYn(supportDevice.getMusicSprtYn());
				messageReq.setVideoDrmSprtYn(supportDevice.getVideoDrmSprtYn());
				messageReq.setSdVideoSprtYn(supportDevice.getSdVideoSprtYn());

				// 앱코디 상품 리스트 조회
				List<MetaInfo> prodList = this.commonDAO.queryForList(
						"IntimateMessage.searchIntimateMessageAppCodiList", messageReq, MetaInfo.class);

				if (prodList != null && !prodList.isEmpty()) {
					MetaInfo metaInfo = null;

					for (int i = 0; i < prodList.size(); i++) {
						metaInfo = prodList.get(i);

						// 상품 메타 정보 조회
						metaInfo = this.getMetaInfo(header, metaInfo.getProdId(), metaInfo.getTopMenuId());

						if (metaInfo != null) {
							productList.add(this.generateProductVO(metaInfo));
						}
					}

					commonResponse.setTotalCount(prodList.get(0).getTotalCount());
				} else {
					commonResponse.setTotalCount(0);
				}
			} else {
				this.logger.debug("----------------------------------------------------------------");
				this.logger.debug("[searchIntimateMessageAppCodiList] supportDevice is empty!");
				this.logger.debug("----------------------------------------------------------------");
			}
		} else {
			this.logger.debug("----------------------------------------------------------------");
			this.logger.debug("[searchIntimateMessageAppCodiList] stdDt is empty!");
			this.logger.debug("----------------------------------------------------------------");
		}

		appCodiRes.setProductList(productList);
		appCodiRes.setCommonResponse(commonResponse);
		return appCodiRes;
	}

	/**
	 * <pre>
	 * Intimate Message 앱코디 상품 메타정보 조회.
	 * </pre>
	 * 
	 * @param header
	 *            header
	 * @param prodId
	 *            prodId
	 * @param topMenuId
	 *            topMenuId
	 * @return MetaInfo
	 */
	private MetaInfo getMetaInfo(SacRequestHeader header, String prodId, String topMenuId) {
		MetaInfo metaInfo = null; // 메타정보 VO
		ProductBasicInfo productInfo = new ProductBasicInfo(); // 메타정보 조회용 상품 파라미터
		Map<String, Object> paramMap = new HashMap<String, Object>(); // 메타정보 조회용 파라미터

		// 메타정보 조회를 위한 파라미터 세팅
		productInfo.setProdId(prodId);
		productInfo.setTopMenuId(topMenuId);
		productInfo.setContentsTypeCd(DisplayConstants.DP_CHANNEL_CONTENT_TYPE_CD);
		paramMap.put("prodRshpCd", DisplayConstants.DP_CHANNEL_EPISHODE_RELATIONSHIP_CD);
		paramMap.put("tenantHeader", header.getTenantHeader());
		paramMap.put("deviceHeader", header.getDeviceHeader());
		paramMap.put("prodStatusCd", DisplayConstants.DP_SALE_STAT_ING);
		paramMap.put("productBasicInfo", productInfo);

		// APP
		if (DisplayConstants.DP_GAME_TOP_MENU_ID.equals(topMenuId)
				|| DisplayConstants.DP_FUN_TOP_MENU_ID.equals(topMenuId)
				|| DisplayConstants.DP_LIFE_LIVING_TOP_MENU_ID.equals(topMenuId)
				|| DisplayConstants.DP_LANG_EDU_TOP_MENU_ID.equals(topMenuId)) {
			this.logger.debug("----------------------------------------------------------------");
			this.logger.debug("[searchIntimateMessageAppCodiList] 메타정보조회 : 앱상품");
			this.logger.debug("----------------------------------------------------------------");

			paramMap.put("imageCd", DisplayConstants.DP_APP_REPRESENT_IMAGE_CD);
			metaInfo = this.metaInfoService.getAppMetaInfo(paramMap);
		}
		// 이북 및 코믹
		else if (DisplayConstants.DP_EBOOK_TOP_MENU_ID.equals(topMenuId)
				|| DisplayConstants.DP_COMIC_TOP_MENU_ID.equals(topMenuId)) {
			this.logger.debug("----------------------------------------------------------------");
			this.logger.debug("[searchIntimateMessageAppCodiList] 메타정보조회 : 이북, 코믹");
			this.logger.debug("----------------------------------------------------------------");

			paramMap.put("imageCd", DisplayConstants.DP_EBOOK_COMIC_REPRESENT_IMAGE_CD);
			metaInfo = this.metaInfoService.getEbookComicMetaInfo(paramMap);
		}
		// 영화 및 방송
		else if (DisplayConstants.DP_MOVIE_TOP_MENU_ID.equals(topMenuId)
				|| DisplayConstants.DP_TV_TOP_MENU_ID.equals(topMenuId)) {
			this.logger.debug("----------------------------------------------------------------");
			this.logger.debug("[searchIntimateMessageAppCodiList] 메타정보조회 : 영화, 방송");
			this.logger.debug("----------------------------------------------------------------");

			paramMap.put("imageCd", DisplayConstants.DP_VOD_REPRESENT_IMAGE_CD);
			metaInfo = this.metaInfoService.getVODMetaInfo(paramMap);
		}
		// 통합뮤직
		else if (DisplayConstants.DP_MUSIC_TOP_MENU_ID.equals(topMenuId)) {
			this.logger.debug("----------------------------------------------------------------");
			this.logger.debug("[searchIntimateMessageAppCodiList] 메타정보조회 : 통합뮤직");
			this.logger.debug("----------------------------------------------------------------");

			paramMap.put("imageCd", DisplayConstants.DP_MUSIC_REPRESENT_IMAGE_CD);
			metaInfo = this.metaInfoService.getMusicMetaInfo(paramMap);
		}

		return metaInfo;
	}

	/**
	 * <pre>
	 * Intimate Message 앱코디 상품 VO 생성.
	 * </pre>
	 * 
	 * @param metaInfo
	 *            metaInfo
	 * @return Products
	 */
	private Product generateProductVO(MetaInfo metaInfo) {
		Product product = null;
		String topMenuId = metaInfo.getTopMenuId();

		// APP
		if (DisplayConstants.DP_GAME_TOP_MENU_ID.equals(topMenuId)
				|| DisplayConstants.DP_FUN_TOP_MENU_ID.equals(topMenuId)
				|| DisplayConstants.DP_LIFE_LIVING_TOP_MENU_ID.equals(topMenuId)
				|| DisplayConstants.DP_LANG_EDU_TOP_MENU_ID.equals(topMenuId)) {
			product = this.responseInfoGenerateFacade.generateAppProductShort(metaInfo);
		}
		// 이북
		else if (DisplayConstants.DP_EBOOK_TOP_MENU_ID.equals(topMenuId)) {
			product = this.responseInfoGenerateFacade.generateEbookProductShort(metaInfo);
		}
		// 코믹
		else if (DisplayConstants.DP_COMIC_TOP_MENU_ID.equals(topMenuId)) {
			product = this.responseInfoGenerateFacade.generateComicProductShort(metaInfo);
		}
		// 영화
		else if (DisplayConstants.DP_MOVIE_TOP_MENU_ID.equals(topMenuId)) {
			product = this.responseInfoGenerateFacade.generateMovieProductShort(metaInfo);
		}
		// 방송
		else if (DisplayConstants.DP_TV_TOP_MENU_ID.equals(topMenuId)) {
			product = this.responseInfoGenerateFacade.generateBroadcastProductShort(metaInfo);
		}
		// 통합뮤직
		else if (DisplayConstants.DP_MUSIC_TOP_MENU_ID.equals(topMenuId)) {
			product = this.responseInfoGenerateFacade.generateMusicProductShort(metaInfo);
		}

		return product;
	}
}
