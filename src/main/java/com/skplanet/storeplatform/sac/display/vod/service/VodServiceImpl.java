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
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.skplanet.storeplatform.framework.core.exception.StorePlatformException;
import com.skplanet.storeplatform.framework.core.persistence.dao.CommonDAO;
import com.skplanet.storeplatform.sac.client.display.vo.vod.VodDetailReq;
import com.skplanet.storeplatform.sac.client.display.vo.vod.VodDetailRes;
import com.skplanet.storeplatform.sac.client.internal.member.user.vo.GradeInfoSac;
import com.skplanet.storeplatform.sac.client.internal.purchase.vo.ExistenceListRes;
import com.skplanet.storeplatform.sac.client.internal.purchase.vo.ExistenceRes;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.common.Date;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.common.Identifier;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.common.Menu;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.common.Price;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.common.Source;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.common.Time;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.common.Title;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.product.Accrual;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.product.Badge;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.product.Chapter;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.product.Contributor;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.product.Distributor;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.product.Play;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.product.Point;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.product.Preview;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.product.Product;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.product.Rights;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.product.Store;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.product.Support;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.product.VideoInfo;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.product.Vod;
import com.skplanet.storeplatform.sac.display.common.DisplayCommonUtil;
import com.skplanet.storeplatform.sac.display.common.constant.DisplayConstants;
import com.skplanet.storeplatform.sac.display.common.service.DisplayCommonService;
import com.skplanet.storeplatform.sac.display.common.service.MemberBenefitService;
import com.skplanet.storeplatform.sac.display.common.vo.MileageInfo;
import com.skplanet.storeplatform.sac.display.common.vo.ProductImage;
import com.skplanet.storeplatform.sac.display.common.vo.TmembershipDcInfo;
import com.skplanet.storeplatform.sac.display.response.CommonMetaInfoGenerator;
import com.skplanet.storeplatform.sac.display.vod.vo.VodDetail;

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

	// [2.x fadeout] 상품 상세 요청 시 예외 처리
	@Value("#{propertiesForSac['sc2x.fadeout.dummy.product.vod.channel']}")
	private String sc2xFadeOutDummyProductChannel;

	@Autowired
	private MemberBenefitService benefitService;

	@Autowired
	private CommonMetaInfoGenerator metaInfoGenerator;

	@Autowired
	private DisplayCommonService displayCommonService;

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.skplanet.storeplatform.sac.display.vod.service.VodService#searchVod(com.skplanet.storeplatform.sac.client
	 * .display.vo.vod.VodDetailReq)
	 */
	@Override
	public VodDetailRes searchVod(VodDetailReq req, boolean supportFhdVideo) {
		// VOC 응대를 위한 로깅
		logger.info("channelId={},userKey={},deviceKey={},deviceModel={}", req.getChannelId(), req.getUserKey(),
				req.getDeviceKey(), req.getDeviceModel());

		VodDetailRes res = new VodDetailRes();
		Product product = new Product();

		final String channelId = req.getChannelId();

		String userKey = StringUtils.defaultString(req.getUserKey());
		String deviceKey = StringUtils.defaultString(req.getDeviceKey());

		// OrderBy
		final String orderedBy = StringUtils.defaultString(req.getOrderedBy(),
				DisplayConstants.DP_ORDEREDBY_TYPE_RECENT);

		// 판매 중지(다운로드 허용) 상품 포함 여부
		// N (Default) : 판매중, Y : 판매중, 판매중지, 판매불가-다운허용
		String includeProdStopStatus = StringUtils.defaultString(req.getIncludeProdStopStatus(), "N");

		// [2.x fadeout] 상품 상세 요청 시 예외 처리
		// 요청한 상품의 ID가 예외 처리에 포함된 상품이라면 중지 상태도 조회하도록 한다.
		String temp = StringUtils.defaultString(this.sc2xFadeOutDummyProductChannel);
		if (temp.contains(channelId))
			includeProdStopStatus = "Y";

		// Parameter 셋팅
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("representImgCd", DisplayConstants.DP_VOD_REPRESENT_IMAGE_CD); // 이미지 코드
		param.put("virtualDeviceModelNo", DisplayConstants.DP_ANY_PHONE_4MM); // 가상 프로비저닝 모델명 (멀티미디어).
		param.put("deviceModel", req.getDeviceModel());
		param.put("channelId", channelId);
		param.put("langCd", req.getLangCd());
		param.put("tenantId", req.getTenantId());
		param.put("baseChapter", req.getBaseChapter());
		param.put("orderedBy", orderedBy);
		param.put("userKey", userKey);
		param.put("deviceKey", deviceKey);
		param.put("includeProdStopStatus", includeProdStopStatus);
		param.put("offset", req.getOffset() == null ? 1 : req.getOffset());
		param.put("count", req.getCount() == null ? 20 : req.getCount());

		// ###################################################################################
		// 1. Channel 정보 조회
		// ###################################################################################
		VodDetail vodDetail = this.getVodChanndel(param);
		// ###################################################################################

		if (vodDetail != null) {

			// Screenshots
			List<ProductImage> screenshotList = this.getScreenshotList(req.getChannelId(), req.getLangCd());

			// 채널의 상품 이용정책 조회 (가장 최신 Chapter의 Episode 이용정책 조회)
			VodDetail usePolicyInfo = this.getProdUsePolicyInfo(param);
			if (usePolicyInfo != null) {
				// 조회된 이용정책 set (update by 이석희 2015.07.24)
				vodDetail = this.setUsePolicyInfo(vodDetail, usePolicyInfo);
			}

			/* Channel Product Mapping */
			this.mapProduct(req, product, vodDetail, screenshotList, supportFhdVideo);

			// 좋아요 여부
			product.setLikeYn(vodDetail.getLikeYn());

			ExistenceListRes existenceListRes = null;
			// orderedBy='nonPayment'
			if (StringUtils.equals(orderedBy, DisplayConstants.DP_ORDEREDBY_TYPE_NONPAYMENT)
					&& StringUtils.isNotBlank(userKey) && StringUtils.isNotBlank(deviceKey)) {
				List<String> episodeIdList = this.getEpisodeIdList(param);

				// 상품 목록의 구매 내역 유무를 확인
				existenceListRes = this.commonService.checkPurchaseList(req.getTenantId(), req.getUserKey(),
						req.getDeviceKey(), episodeIdList);
				if (existenceListRes == null) {
					existenceListRes = new ExistenceListRes();
				}

				List<String> paymentProdIdList = new ArrayList<String>();
				for (ExistenceRes existenceRes : existenceListRes.getExistenceListRes()) {
					paymentProdIdList.add(existenceRes.getProdId());
				}

				// #24889 VOD/이북 전권 소장/대여 후 미구매로 정렬 시 대여/소장이 노출되는 문제 수정
				// episode id 로 filter 하면 전권대여/소장 구매 시 대여소장 상품 모두 Filtering 되지 않기 때문에 content id 로 filter.
				List<String> paymentContentIdList = this.getContentIdListByEpisodeIdList(paymentProdIdList);

				param.put("paymentProdIdList", paymentProdIdList);
				param.put("paymentContentIdList", paymentContentIdList);
			}

			// ###################################################################################
			// 2. subProjectList
			// ###################################################################################
			List<VodDetail> subProductList = this.getSubProjectList(param);
			// ###################################################################################

			if (!StringUtils.equals(orderedBy, DisplayConstants.DP_ORDEREDBY_TYPE_NONPAYMENT)
					&& StringUtils.isNotBlank(userKey) && StringUtils.isNotBlank(deviceKey)) {
				// 정렬방식이 미구매 순인 경우 필터링 데이터이기 떄문에 아닌 경우에만 구매 체크.
				existenceListRes = this.getExistenceScReses(req, subProductList);
			}

			// Episode Product Mapping
			this.mapSubProductList(req, product, subProductList, existenceListRes, supportFhdVideo);

			res.setProduct(product);
		} else {
			throw new StorePlatformException("SAC_DSP_0009");
		}
		return res;
	}

	/**
	 * Episode id List 로 Content Id 조회
	 * 
	 * @param paymentProdIdList
	 * @return
	 */
	private List<String> getContentIdListByEpisodeIdList(List<String> paymentProdIdList) {
		List<String> contentIdList = null;
		if (paymentProdIdList.size() == 0) {
			contentIdList = new ArrayList<String>();
		} else {
			Map<String, Object> param = new HashMap<String, Object>();
			param.put("prodIdList", paymentProdIdList);
			contentIdList = this.commonDAO.queryForList("VodDetail.selectContentIdListByEpisodeIdList", param,
					String.class);
		}
		return contentIdList;
	}

	/**
	 * VOD Channel
	 * 
	 * @param param
	 * @return
	 */
	private VodDetail getVodChanndel(Map<String, Object> param) {
		VodDetail vodDetail = this.commonDAO.queryForObject("VodDetail.selectVodChannel", param, VodDetail.class);
		return vodDetail;
	}

	/**
	 * Episode Id List
	 * 
	 * @param param
	 * @return
	 */
	private List<String> getEpisodeIdList(Map<String, Object> param) {
		List<String> episodeIdList = this.commonDAO.queryForList("VodDetail.selectProdRshp", param, String.class);
		return episodeIdList;
	}

	/**
	 * Episode List
	 * 
	 * @param param
	 * @return
	 */
	private List<VodDetail> getSubProjectList(Map<String, Object> param) {
		List<VodDetail> subProductList = this.commonDAO.queryForList("VodDetail.selectVodSeries", param,
				VodDetail.class);
		return subProductList;
	}

	/**
	 * Mapping Screenshot
	 * 
	 * @param channelId
	 * @param langCd
	 * @return
	 */
	private List<ProductImage> getScreenshotList(String channelId, String langCd) {
		Map<String, String> param = new HashMap<String, String>();
		param.put("channelId", channelId);
		param.put("langCd", langCd);
		List<ProductImage> screenshotList = this.commonDAO.queryForList("VodDetail.selectSourceList", param,
				ProductImage.class);
		return screenshotList;
	}

	/**
	 * 기구매 체크 (구매서버 연동)
	 * 
	 * @param req
	 * @param subProductList
	 * @return
	 */
	private ExistenceListRes getExistenceScReses(VodDetailReq req, List<VodDetail> subProductList) {
		if (StringUtils.isNotBlank(req.getUserKey()) || StringUtils.isNotBlank(req.getDeviceKey())) {
			ExistenceListRes res = new ExistenceListRes();
			res.setExistenceListRes(new ArrayList<ExistenceRes>());
			return res;
		}

		ExistenceListRes existenceListRes = null;
		if (subProductList != null && subProductList.size() > 0 && StringUtils.isNotBlank(req.getUserKey())
				&& StringUtils.isNotBlank(req.getDeviceKey())) {
			// 기구매 체크
			List<String> episodeIdList = new ArrayList<String>();
			for (VodDetail subProduct : subProductList) {
				if (StringUtils.isNotEmpty(subProduct.getPlayProdId())) {
					episodeIdList.add(subProduct.getPlayProdId());
				} else if (StringUtils.isNotEmpty(subProduct.getStoreProdId())) {
					episodeIdList.add(subProduct.getStoreProdId());
				}
			}
			try {
				existenceListRes = this.commonService.checkPurchaseList(req.getTenantId(), req.getUserKey(),
						req.getDeviceKey(), episodeIdList);
			} catch (StorePlatformException e) {
				// ignore : 구매 연동 오류 발생해도 상세 조회는 오류 없도록 처리. 구매 연동오류는 VOC 로 처리한다.
				existenceListRes = new ExistenceListRes();
				existenceListRes.setExistenceListRes(new ArrayList<ExistenceRes>());
			}
		}
		return existenceListRes;
	}

	/**
	 * Product Mapping
	 * 
	 * @param req
	 * @param product
	 *            Product
	 * @param mapperVO
	 *            Product 에 Mapping 할 데이터
	 * @param screenshotList
	 *            Screenshot 목록
	 */
	private void mapProduct(VodDetailReq req, Product product, VodDetail mapperVO, List<ProductImage> screenshotList,
			boolean supportFhdVideo) {

		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd'T'HHmmssZ");

		List<Identifier> identifierList = new ArrayList<Identifier>();
		identifierList.add(new Identifier(DisplayConstants.DP_CHANNEL_IDENTIFIER_CD, mapperVO.getProdId()));
		product.setIdentifierList(identifierList);

		product.setTitle(this.mapTitle(mapperVO));

		List<Source> sourceList = this.mapSourceList(mapperVO, screenshotList);
		product.setSourceList(sourceList);

		// 상품 설명
		product.setProductExplain(mapperVO.getProdBaseDesc());
		product.setProductDetailExplain(mapperVO.getProdDtlDesc());
		product.setProductIntroduction(mapperVO.getProdIntrDscr());

		// 판매상태
		product.setSalesStatus(mapperVO.getProdStatusCd());

		// SvcGrpCd
		product.setSvcGrpCd(mapperVO.getSvcGrpCd());

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

		// Accrual
		Accrual accrual = this.mapAccrual(mapperVO);
		product.setAccrual(accrual);

		Vod vod = this.mapVod(mapperVO, supportFhdVideo);
		product.setVod(vod);

		// Badge
		Badge badge = this.getBadgeInfo(mapperVO);
		product.setBadge(badge);
		// tmembership 할인율
		TmembershipDcInfo tmembershipDcInfo = this.commonService.getTmembershipDcRateForMenu(req.getTenantId(),
				mapperVO.getTopMenuId());
		List<Point> pointList = this.metaInfoGenerator.generatePoint(tmembershipDcInfo);
		// Tstore멤버십 적립율 정보
		if (StringUtils.isNotEmpty(req.getUserKey())) {
			// 회원등급 조회
			GradeInfoSac userGradeInfo = this.commonService.getUserGrade(req.getUserKey());
			if (userGradeInfo != null) {
				if (pointList == null)
					pointList = new ArrayList<Point>();
				String userGrade = userGradeInfo.getUserGradeCd();
				Integer prodAmt = 0;
				if (mapperVO.getStoreProdAmt() != null && mapperVO.getStoreProdAmt() > 0)
					prodAmt = mapperVO.getStoreProdAmt();
				else if (mapperVO.getPlayProdAmt() != null && mapperVO.getPlayProdAmt() > 0)
					prodAmt = mapperVO.getPlayProdAmt();

				MileageInfo mileageInfo = this.benefitService.getMileageInfo(req.getTenantId(), mapperVO.getMenuId(), req.getChannelId(), prodAmt);
				mileageInfo = this.benefitService.checkFreeProduct(mileageInfo, prodAmt);
				pointList.addAll(this.metaInfoGenerator.generateMileage(mileageInfo, userGrade));
			}
		}
		if (pointList.size() > 0)
			product.setPointList(pointList);

	}

	/**
	 * Title
	 * 
	 * @param mapperVO
	 * @return
	 */
	private Title mapTitle(VodDetail mapperVO) {
		Title title = new Title();
		// title.setPrefix(mapperVO.getPrefixTitle());
		title.setPrefix(mapperVO.getVodTitlNm());
		title.setText(mapperVO.getProdNm());
		return title;
	}

	/**
	 * Accural
	 * 
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
	 * Rights Mapping
	 * 
	 * @param mapperVO
	 * @param req
	 * @param existenceMap
	 * @return
	 */
	private Rights mapRights(VodDetail mapperVO, VodDetailReq req, Map<String, ExistenceRes> existenceMap) {
		Rights rights = new Rights();
		rights.setGrade(mapperVO.getProdGrdCd());

		/** 기타 상품 등급 코드 */
		// rights.setGradeExtra(mapperVO.getProdGrdExtraCd());
		rights.setPlus19Yn(mapperVO.getPlus19Yn());

		// 영화,TV방송에 대한 allow 설정
		if (StringUtils.equals(DisplayConstants.DP_MOVIE_TOP_MENU_ID, mapperVO.getTopMenuId())
				|| StringUtils.equals(DisplayConstants.DP_TV_TOP_MENU_ID, mapperVO.getTopMenuId())
				|| StringUtils.equals(DisplayConstants.DP_VOD_TOP_MENU_ID, mapperVO.getTopMenuId())) {
			if (StringUtils.equals(mapperVO.getDwldAreaLimtYn(), "Y")) {
				rights.setAllow(DisplayConstants.DP_RIGHTS_ALLOW_DOMESTIC);
			}
		}

		// rights.setAllow(mapperVO.getDwldAreaLimtYn());
		/** dwldAreaLimitYn 다운로드 지역제한 == 'Y' 일 경우 domestic 리턴 */
		/*
		 * if(StringUtils.isNotEmpty(mapperVO.getDwldAreaLimtYn()) && mapperVO.getDwldAreaLimtYn().equals("Y")) {
		 * rights.setAllow(DisplayConstants.DP_RIGHTS_ALLOW_DOMESTIC); }
		 */

		// Preview
		rights.setPreview(this.mapPreview(mapperVO));

		// -------------------------------------------
		// Store, Play
		// -------------------------------------------
		/** play 정보 */
		rights.setPlay(this.mapPlay(mapperVO, req, existenceMap));

		/** Store 정보 */
		rights.setStore(this.mapStore(mapperVO, req, existenceMap));
		return rights;
	}

	/**
	 * <pre>
	 * Store Mapping
	 * </pre>
	 * 
	 * @param mapperVO
	 * @param req
	 * @param existenceMap
	 * @return
	 */
	private Store mapStore(VodDetail mapperVO, VodDetailReq req, Map<String, ExistenceRes> existenceMap) {
		Store store = null;
		if (StringUtils.isNotEmpty(mapperVO.getStoreProdId())) {
			store = new Store();
			// Store.Identifier
			List<Identifier> identifierList = new ArrayList<Identifier>();
			identifierList.add(new Identifier(DisplayConstants.DP_EPISODE_IDENTIFIER_CD, mapperVO.getStoreProdId()));
			store.setIdentifierList(identifierList);

			List<Support> supportList = new ArrayList<Support>();
			supportList.add(this.mapSupport(DisplayConstants.DP_DRM_SUPPORT_NM, mapperVO.getStoreDrmYn()));
			supportList.add(this.mapSupport(DisplayConstants.DP_DL_STRM_NM, mapperVO.getStoreDlStrmCd()));
			store.setSupportList(supportList);

			if (mapperVO.getStoreUsePeriod() != null) {
				store.setDate(DisplayCommonUtil.makeDateUsagePeriodV2(mapperVO.getStoreUsePeriodUnitCd(),
						mapperVO.getStoreUsePeriod(), mapperVO.getStoreUsePeriodUnitCdNm()));
			} 
			
			// 가격
			store.setPrice(this.mapPrice(mapperVO.getStoreProdAmt(), mapperVO.getStoreProdNetAmt()));

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

			// 판매상태
			store.setSalesStatus(mapperVO.getStoreProdStatusCd());

		}
		return store;
	}

	/**
	 * <pre>
	 * Play Mapping
	 * </pre>
	 * 
	 * @param mapperVO
	 * @param req
	 * @param existenceMap
	 * @return
	 */
	private Play mapPlay(VodDetail mapperVO, VodDetailReq req, Map<String, ExistenceRes> existenceMap) {
		Play play = null;
		if (StringUtils.isNotEmpty(mapperVO.getPlayProdId())) {
			play = new Play();
			List<Identifier> identifierList = new ArrayList<Identifier>();
			identifierList.add(new Identifier(DisplayConstants.DP_EPISODE_IDENTIFIER_CD, mapperVO.getPlayProdId()));
			play.setIdentifierList(identifierList);

			List<Support> supportList = new ArrayList<Support>();
			supportList.add(this.mapSupport(DisplayConstants.DP_DRM_SUPPORT_NM, mapperVO.getPlayDrmYn()));
			supportList.add(this.mapSupport(DisplayConstants.DP_DL_STRM_NM, mapperVO.getPlayDlStrmCd()));
			play.setSupportList(supportList);

			if (mapperVO.getPlayUsePeriod() != null) {
				play.setDate(DisplayCommonUtil.makeDateUsagePeriod(mapperVO.getPlayUsePeriodUnitCd(),
						mapperVO.getPlayUsePeriod(), mapperVO.getPlayUsePeriodUnitCdNm()));
			}

			// 가격
			play.setPrice(this.mapPrice(mapperVO.getPlayProdAmt(), mapperVO.getPlayProdNetAmt()));

			Source source = null;
			if (StringUtils.isNotEmpty(mapperVO.getFilePath())) {
				source = new Source();
				source.setMediaType(DisplayCommonUtil.getMimeType(mapperVO.getFilePath()));
				source.setUrl(mapperVO.getFilePath());
			}

			if (mapperVO.getStrmNetworkCd() != null) {
				play.setNetworkRestrict(DisplayConstants.DP_NETWORK_RESTRICT);
			}

			// 판매상태
			play.setSalesStatus(mapperVO.getPlayProdStatusCd());

			// 사용자 구매 가능 상태
			if (existenceMap != null && existenceMap.containsKey(mapperVO.getPlayProdId())
					&& StringUtils.isNotBlank(req.getUserKey()) && StringUtils.isNotBlank(req.getDeviceKey())) {
				String userPurStatus = this.getSalesStatus(mapperVO, req.getUserKey(), req.getDeviceKey());
				if (userPurStatus != null)
					play.setUserPurStatus(userPurStatus);
			}

		}
		return play;
	}

	/**
	 * Mapping Price
	 * 
	 * @param prodAmt
	 * @param prodNetAmt
	 * @return
	 */
	private Price mapPrice(Integer prodAmt, Integer prodNetAmt) {
		Price price = null;

		if (prodAmt != null || prodNetAmt != null) {
			price = new Price();
			price.setText(prodAmt);
			price.setFixedPrice(prodNetAmt);
		}
		return price;
	}

	/**
	 * Mapping Support
	 * 
	 * @param type
	 * @param text
	 * @return
	 */
	private Support mapSupport(String type, String text) {
		Support support = null;

		if (StringUtils.isNotEmpty(type) || StringUtils.isNotEmpty(type)) {
			support = new Support();
			support.setType(type);
			support.setText(text);
		}
		return support;
	}

	/**
	 * <pre>
	 * method 설명.
	 * </pre>
	 * 
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
			source.setUrl(this.commonService.makePreviewUrl(mapperVO.getScSamplUrl()));
			source.setMediaType(DisplayCommonUtil.getMimeType(mapperVO.getScSamplUrl()));
			sourceList.add(source);
		}
		if (StringUtils.isNotEmpty(mapperVO.getSamplUrl())) {
			Source source = new Source();
			source.setType(DisplayConstants.DP_PREVIEW_HQ);
			source.setUrl(this.commonService.makePreviewUrl(mapperVO.getSamplUrl()));
			source.setMediaType(DisplayCommonUtil.getMimeType(mapperVO.getSamplUrl()));
			sourceList.add(source);
		}
		preview.setSourceList(sourceList);
		return preview;
	}

	/**
	 * Distributor
	 * 
	 * @param mapperVO
	 * @return
	 */
	private Distributor mapDistributor(VodDetail mapperVO) {
		Distributor distributor = new Distributor();
		distributor.setSellerKey(mapperVO.getSellerMbrNo());
		// (2014-05-17) 이슈 : 멀티미디어 상품은 판매자 정보를 회원API를 통해 받아야 한다.
		// 전시 API 에서는 sellerKey만 내려주도록 한다.
		/*
		 * distributor.setName(mapperVO.getExpoSellerNm()); distributor.setTel(mapperVO.getExpoSellerTelno());
		 * distributor.setEmail(mapperVO.getExpoSellerEmail());
		 */
		return distributor;
	}

	/**
	 * Contributor
	 * 
	 * @param mapperVO
	 * @return
	 */
	private Contributor mapContributor(VodDetail mapperVO) {
		Contributor contributor = new Contributor();
		contributor.setDirector(mapperVO.getArtist2Nm()); // 감독
		contributor.setArtist(mapperVO.getArtist1Nm()); // 출연
		contributor.setCompany(mapperVO.getChnlCompNm()); // 제공업체
		contributor.setAgency(mapperVO.getAgencyNm()); // 기획사
		/*
		 * contributor.setPublisher(mapperVO.getChnlCompNm()); //배급사
		 */
		if (StringUtils.equals(mapperVO.getTopMenuId(), DisplayConstants.DP_TV_TOP_MENU_ID)) { // TV 방송
			// 2014.06.23 방송사 명은 TB_DP_VOD_PROD.AGENCY_NM 값을 보여줘야 한다. (CMS-MM)
			// contributor.setChannel(mapperVO.getBrdcCompCdNm()); //방송사
			contributor.setChannel(mapperVO.getAgencyNm()); // 방송사
		}
		return contributor;
	}

	/**
	 * Mapping Source List
	 * 
	 * @param mapperVO
	 * @param screenshotList
	 * @return
	 */
	private List<Source> mapSourceList(VodDetail mapperVO, List<ProductImage> screenshotList) {
		Source source;
		List<Source> sourceList = new ArrayList<Source>();
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

		// 에피소드 VOD 시리즈 thumbnail 이미지
		if (StringUtils.isNotEmpty(mapperVO.getEpsdImgPath()) && StringUtils.isNotEmpty(mapperVO.getEpsdImgNm())) {
			source = new Source();
			String imagePath = mapperVO.getEpsdImgPath() + mapperVO.getEpsdImgNm();
			source.setMediaType(DisplayCommonUtil.getMimeType(imagePath));
			source.setSize(mapperVO.getEpsdImgSize());
			source.setType(DisplayConstants.DP_EPISODE_THUMNAIL_SOURCE);
			source.setUrl(imagePath);
			sourceList.add(source);
		}

		// screenshot ( 19plus 상품일 경우 스크린샷 제외 )
		if (screenshotList != null && !"Y".equals(mapperVO.getPlus19Yn())) {
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
	 * DataList
	 * 
	 * @param mapperVO
	 * @param sdf
	 * @return
	 */
	private List<Date> mapDateList(VodDetail mapperVO, SimpleDateFormat sdf) {
		List<Date> dateList = new ArrayList<Date>();
		if (mapperVO.getRegDt() != null) {
			Date date = new Date();
			date.setType(DisplayConstants.DP_DATE_REG);
			date.setText(sdf.format(mapperVO.getRegDt()));
			dateList.add(date);
		}

		if (StringUtils.isNotEmpty(mapperVO.getIssueDay())) {
			Date date = new Date();
			date.setType(DisplayConstants.DP_DATE_RELEASE);
			date.setText(mapperVO.getIssueDay());
			dateList.add(date);
		}
		return dateList;
	}

	/**
	 * Menu List
	 * 
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

		// Meta Class
		menu = new Menu();
		menu.setType(DisplayConstants.DP_META_CLASS_MENU_TYPE);
		menu.setId(mapperVO.getMetaClsfCd());
		menuList.add(menu);

		// Genre
		if (StringUtils.isNotEmpty(mapperVO.getGenreCd())) {
			menu = new Menu();
			menu.setType(DisplayConstants.DP_MENU_TYPE_GENRE);
			menu.setId(mapperVO.getGenreCd());
			menu.setName(mapperVO.getGenreCdNm());
			menuList.add(menu);
		}
		return menuList;
	}

	/**
	 * Support List
	 * 
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

		/** HD_YN */
		support = new Support();
		support.setType(DisplayConstants.DP_VOD_HD_SUPPORT_NM);
		support.setText(mapperVO.getHdvYn());
		supportList.add(support);

		/** DOLBY_SPRT_YN */
		support = new Support();
		support.setType(DisplayConstants.DP_VOD_DOLBY_NM);
		support.setText(mapperVO.getDolbySprtYn());
		supportList.add(support);

		// BTV Support (기존 서비스 유지를 위해 하드코딩)
		support = new Support();
		support.setType(DisplayConstants.DP_VOD_BTV_SUPPORT_NM);
		support.setText("Y");
		supportList.add(support);

		return supportList;
	}

	/**
	 * SubProduct Mapping
	 * 
	 * @param req
	 *            요청 정보
	 * @param product
	 *            Channel 정보
	 * @param vodDetailList
	 *            VOD Episode List
	 * @param existenceListRes
	 *            기구매 체크 결과
	 */
	private void mapSubProductList(VodDetailReq req, Product product, List<VodDetail> vodDetailList,
			ExistenceListRes existenceListRes, boolean supportFhdVideo) {

		List<Product> subProjectList = new ArrayList<Product>();

		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd'T'HHmmssZ");

		if (vodDetailList != null && vodDetailList.size() > 0) {
			// SubProduct TotalCount
			VodDetail temp = vodDetailList.get(0);
			product.setSubProductTotalCount(temp.getTotalCount());

			// 기구매 체크
			Map<String, ExistenceRes> existenceMap = new HashMap<String, ExistenceRes>();
			if (existenceListRes != null) {
				for (ExistenceRes existenceRes : existenceListRes.getExistenceListRes()) {
					existenceMap.put(existenceRes.getProdId(), existenceRes);
				}
			}

			for (VodDetail mapperVO : vodDetailList) {
				Product subProduct = new Product();
				// List<ProductImage> screenshotList = getScreenshotList(mapperVO.getProdId(), req.getLangCd());

				List<Identifier> identifierList = new ArrayList<Identifier>();

				identifierList.add(new Identifier(DisplayConstants.DP_CONTENT_IDENTIFIER_CD, mapperVO.getCid()));

				subProduct.setIdentifierList(identifierList);

				subProduct.setTitle(this.mapTitle(mapperVO));

				// 상품 설명
				subProduct.setProductExplain(mapperVO.getProdBaseDesc()); 
				subProduct.setProductDetailExplain(mapperVO.getProdDtlDesc());
				subProduct.setProductIntroduction(mapperVO.getProdIntrDscr());

				List<Source> sourceList = this.mapSourceList(mapperVO, null);
				subProduct.setSourceList(sourceList);

				// SupportList
				List<Support> supportList = this.mapSupportList(mapperVO);
				subProduct.setSupportList(supportList);

				// MenuList
				List<Menu> menuList = this.mapMenuList(mapperVO);
				subProduct.setMenuList(menuList);

				// DateList
				List<Date> dateList = this.mapDateList(mapperVO, sdf);
				subProduct.setDateList(dateList);

				// Contributor
				Contributor contributor = this.mapContributor(mapperVO);
				subProduct.setContributor(contributor);

				// Distributor
				Distributor distributor = this.mapDistributor(mapperVO);
				subProduct.setDistributor(distributor);

				// rights
				Rights rights = this.mapRights(mapperVO, req, existenceMap);

				// 이용 기간 기준(대여)
				if (StringUtils.isNotEmpty(mapperVO.getPlayProdId())) {
					// default 값 설정 "DP013002" : 구매시
					String playUsagePeriod = this.displayCommonService.getUsePeriodSetCd(mapperVO.getTopMenuId(),
							mapperVO.getPlayProdId(), mapperVO.getPlayDrmYn(), mapperVO.getSvcGrpCd());
					if (StringUtils.isNotEmpty(playUsagePeriod)) {
						if ("DP013001".equals(playUsagePeriod)) {
							rights.getPlay().setUsagePeriod("download");
						} else if ("DP013002".equals(playUsagePeriod)) {
							rights.getPlay().setUsagePeriod("purchase");
						}
					}
				}
				// 이용 기간 기준(소장)
				if (StringUtils.isNotEmpty(mapperVO.getStoreProdId())) {
					// default 값 설정 "DP013002" : 구매시
					String storeUsagePeriod = this.displayCommonService.getUsePeriodSetCd(mapperVO.getTopMenuId(),
							mapperVO.getStoreProdId(), mapperVO.getStoreDrmYn(), mapperVO.getSvcGrpCd());
					if (StringUtils.isNotEmpty(storeUsagePeriod)) {
						if ("DP013001".equals(storeUsagePeriod)) {
							rights.getStore().setUsagePeriod("download");
						} else if ("DP013002".equals(storeUsagePeriod)) {
							rights.getStore().setUsagePeriod("purchase");
						}
					}
				}
				subProduct.setRights(rights);

				// VOD Mapping
				if (supportFhdVideo) { // 4.0
					// NM:일반화질 (A), SD 고화질 (B), HD 화질 (C), HIHD 화질 (D)
					subProduct.setVod(this.mapVodV2(mapperVO, supportFhdVideo));
				} else { // 3.0
					// NM:일반화질 (A), SD 고화질 (B), HD(C)/HIHD(D) 화질(HIHD화질 우선)
					subProduct.setVod(this.mapVod(mapperVO, supportFhdVideo));
				}

				// Accrual
				Accrual accrual = this.mapAccrual(mapperVO);
				subProduct.setAccrual(accrual);

				// Badge
				Badge badge = this.getBadgeInfo(mapperVO);
				subProduct.setBadge(badge);

				subProjectList.add(subProduct);
			}
		} else
			product.setSubProductTotalCount(0);
		product.setSubProductList(subProjectList);

	}

	/**
	 * VOD 3.0
	 * 
	 * @param mapperVO
	 * @param supportFhdVideo
	 * @return
	 */
	private Vod mapVod(VodDetail mapperVO, boolean supportFhdVideo) {
		Vod vod = new Vod();
		List<VideoInfo> videoInfoList = new ArrayList<VideoInfo>();
		Chapter chapter = new Chapter();
		VideoInfo videoInfo;

		if (mapperVO.getEpsdPlayTm() != null) {
			Time runningTime = new Time();
			runningTime.setText(String.valueOf(mapperVO.getEpsdPlayTm()));
			vod.setRunningTime(runningTime);
		}

		if (StringUtils.isNotEmpty(mapperVO.getChapter())) {
			chapter.setUnit(this.commonService.getVodChapterUnit());
			chapter.setText(Integer.parseInt(mapperVO.getChapter()));
			vod.setChapter(chapter);
		}

		// -------------------------------------------------------------
		// 화질 정보
		// NM:일반화질 (A), SD:SD화질 (B), HD화질(C), HIHD화질 (D)
		// -------------------------------------------------------------
		/** 일반화질 정보 */
		if (StringUtils.isNotEmpty(mapperVO.getNmSubContsId())) {
			videoInfo = this.getNmVideoInfo(mapperVO);
			videoInfoList.add(videoInfo);
		}
		/** SD 고화질 정보 */
		if (StringUtils.isNotEmpty(mapperVO.getSdSubContsId())) {
			videoInfo = this.getSdVideoInfo(mapperVO);
			videoInfoList.add(videoInfo);
		}

		// HIHD (D화질) 정보 우선, 없으며 HD 정보를 내려줌
		if (StringUtils.isNotEmpty(mapperVO.getHdSubContsId()) || StringUtils.isNotEmpty(mapperVO.getHihdSubContsId())) {
			/** HD 화질 정보 */
			videoInfo = this.getHdVideoInfo(mapperVO);
			videoInfoList.add(videoInfo);
		}

		// FHD 지원 : T store 4.0 에서는 NM, SD, HD, FHD 화질 지원
		if (supportFhdVideo && StringUtils.isNotEmpty(mapperVO.getFhdSubContsId())) {
			/** FHD 고화질 정보 */
			videoInfo = this.getFhdVideoInfo(mapperVO);
			videoInfoList.add(videoInfo);
		}
		if (videoInfoList.size() > 0)
			vod.setVideoInfoList(videoInfoList);
		return vod;
	}

	/**
	 * VOD V2 4.0
	 * 
	 * @param mapperVO
	 * @param supportFhdVideo
	 * @return
	 */
	private Vod mapVodV2(VodDetail mapperVO, boolean supportFhdVideo) {
		Vod vod = new Vod();
		List<VideoInfo> videoInfoList = new ArrayList<VideoInfo>();
		Chapter chapter = new Chapter();
		VideoInfo videoInfo;

		if (mapperVO.getEpsdPlayTm() != null) {
			Time runningTime = new Time();
			runningTime.setText(String.valueOf(mapperVO.getEpsdPlayTm()));
			vod.setRunningTime(runningTime);
		}

		if (StringUtils.isNotEmpty(mapperVO.getChapter())) {
			chapter.setUnit(this.commonService.getVodChapterUnit());
			chapter.setText(Integer.parseInt(mapperVO.getChapter()));
			vod.setChapter(chapter);
		}

		// -------------------------------------------------------------
		// 화질 정보
		// NM:일반화질 (A), SD 고화질 (B), HD 화질 (C), HIHD 화질 (D)
		// -------------------------------------------------------------
		/** 일반화질 정보 */
		if (StringUtils.isNotEmpty(mapperVO.getNmSubContsId())) {
			videoInfo = this.getNmVideoInfo(mapperVO);
			videoInfoList.add(videoInfo);
		}
		/** SD 고화질 정보 */
		if (StringUtils.isNotEmpty(mapperVO.getSdSubContsId())) {
			videoInfo = this.getSdVideoInfo(mapperVO);
			videoInfoList.add(videoInfo);
		}

		/** HD 화질 정보 */
		if (StringUtils.isNotEmpty(mapperVO.getHdSubContsId())) {
			videoInfo = this.getHdVideoInfoV2(mapperVO);
			videoInfoList.add(videoInfo);
		}

		/** HIHD 화질 정보 */
		if (StringUtils.isNotEmpty(mapperVO.getHihdSubContsId())) {
			videoInfo = this.getHiHdVideoInfo(mapperVO);
			videoInfoList.add(videoInfo);
		}

		if (videoInfoList.size() > 0)
			vod.setVideoInfoList(videoInfoList);
		return vod;
	}

	private VideoInfo getNmVideoInfo(VodDetail mapperVO) {
		VideoInfo videoInfo = new VideoInfo();
		videoInfo.setPictureSize(mapperVO.getNmDpPicRatio());
		videoInfo.setPixel(mapperVO.getNmDpPixel());
		videoInfo.setScid(mapperVO.getNmSubContsId());
		videoInfo.setSize(mapperVO.getNmFileSize().toString());
		videoInfo.setType(DisplayConstants.DP_VOD_QUALITY_NORMAL);
		videoInfo.setVersion(mapperVO.getNmProdVer());
		return videoInfo;
	}

	private VideoInfo getSdVideoInfo(VodDetail mapperVO) {
		VideoInfo videoInfo = new VideoInfo();
		videoInfo.setPictureSize(mapperVO.getSdDpPicRatio());
		videoInfo.setPixel(mapperVO.getSdDpPixel());
		videoInfo.setScid(mapperVO.getSdSubContsId());
		videoInfo.setSize(mapperVO.getSdFileSize().toString());
		videoInfo.setType(DisplayConstants.DP_VOD_QUALITY_SD);
		videoInfo.setVersion(mapperVO.getSdProdVer());
		return videoInfo;
	}

	/**
	 * HD&HIHD
	 * 
	 * @param mapperVO
	 * @return
	 */
	private VideoInfo getHdVideoInfo(VodDetail mapperVO) {
		VideoInfo videoInfo = new VideoInfo();

		// HIHD 화질 (D) 정보 우선, 없으며 HD화질 (C) 정보를 내려줌
		if (StringUtils.isNotEmpty(mapperVO.getHihdSubContsId())) {
			// HIHD 화질 (D)
			videoInfo.setType(DisplayConstants.DP_VOD_QUALITY_HIHD);
			videoInfo.setPictureSize(mapperVO.getHihdDpPicRatio());
			videoInfo.setPixel(mapperVO.getHihdDpPixel());
			videoInfo.setScid(mapperVO.getHihdSubContsId());
			videoInfo.setSize(mapperVO.getHihdFileSize().toString());
			videoInfo.setVersion(mapperVO.getHihdProdVer());
		} else {
			// HD 화질 (C)
			videoInfo.setType(DisplayConstants.DP_VOD_QUALITY_HD);
			videoInfo.setPictureSize(mapperVO.getHdDpPicRatio());
			videoInfo.setPixel(mapperVO.getHdDpPixel());
			videoInfo.setScid(mapperVO.getHdSubContsId());
			videoInfo.setSize(mapperVO.getHdFileSize().toString());
			videoInfo.setVersion(mapperVO.getHdProdVer());
		}

		return videoInfo;
	}

	/**
	 * HD (C화질) V2
	 * 
	 * @param mapperVO
	 * @return
	 */
	private VideoInfo getHdVideoInfoV2(VodDetail mapperVO) {
		VideoInfo videoInfo = new VideoInfo();
		videoInfo.setType(DisplayConstants.DP_VOD_QUALITY_HD);
		videoInfo.setPictureSize(mapperVO.getHdDpPicRatio());
		videoInfo.setPixel(mapperVO.getHdDpPixel());
		videoInfo.setScid(mapperVO.getHdSubContsId());
		videoInfo.setSize(mapperVO.getHdFileSize().toString());
		videoInfo.setVersion(mapperVO.getHdProdVer());
		return videoInfo;
	}

	/**
	 * HIHD (D화질)
	 * 
	 * @param mapperVO
	 * @return
	 */
	private VideoInfo getHiHdVideoInfo(VodDetail mapperVO) {
		VideoInfo videoInfo = new VideoInfo();
		videoInfo.setType(DisplayConstants.DP_VOD_QUALITY_HIHD);
		videoInfo.setPictureSize(mapperVO.getHihdDpPicRatio());
		videoInfo.setPixel(mapperVO.getHihdDpPixel());
		videoInfo.setScid(mapperVO.getHihdSubContsId());
		videoInfo.setSize(mapperVO.getHihdFileSize().toString());
		videoInfo.setVersion(mapperVO.getHihdProdVer());
		return videoInfo;
	}

	/**
	 * Full HD 화질 Video 정보 리턴
	 * 
	 * @param mapperVO
	 * @return
	 */
	private VideoInfo getFhdVideoInfo(VodDetail mapperVO) {
		VideoInfo videoInfo = new VideoInfo();
		videoInfo.setPictureSize(mapperVO.getFhdDpPicRatio());
		videoInfo.setPixel(mapperVO.getFhdDpPixel());
		videoInfo.setScid(mapperVO.getFhdSubContsId());
		videoInfo.setSize(mapperVO.getFhdFileSize().toString());
		videoInfo.setType(DisplayConstants.DP_VOD_QUALITY_FHD);
		videoInfo.setVersion(mapperVO.getFhdProdVer());
		return videoInfo;
	}

	/**
	 * 판매 상태 조회
	 * 
	 * @param mapperVO
	 * @param userKey
	 * @param deviceKey
	 * @return
	 */
	private String getSalesStatus(VodDetail mapperVO, String userKey, String deviceKey) {
		String salesStatus = null;
		// 기구매 체크
		if (!StringUtils.equals(mapperVO.getProdStatusCd(), DisplayConstants.DP_SALE_STAT_ING)) {
			// 04, 09, 10의 경우 구매이력이 없으면 상품 없음을 표시한다.
			if (DisplayConstants.DP_SALE_STAT_PAUSED.equals(mapperVO.getProdStatusCd())
					|| DisplayConstants.DP_SALE_STAT_RESTRIC_DN.equals(mapperVO.getProdStatusCd())
					|| DisplayConstants.DP_SALE_STAT_DROP_REQ_DN.equals(mapperVO.getProdStatusCd())) {
				if (StringUtils.isNotBlank(userKey) && StringUtils.isNotBlank(deviceKey)) {
				} else
					salesStatus = "restricted";
			} else
				salesStatus = "restricted";
		}
		return salesStatus;
	}

	/**
	 * Badge 정보 리턴
	 * 
	 * @param mapperVO
	 * @return
	 */
	private Badge getBadgeInfo(VodDetail mapperVO) {

		if (mapperVO.getBadgeCd() == null && mapperVO.getBadgeOptText() == null)
			return null;
		Badge badge = new Badge();
		badge.setCode(mapperVO.getBadgeCd());
		badge.setText(mapperVO.getBadgeOptText());

		return badge;
	}

	/**
	 * 이용정책 set (최신 Chapter Episode의 이용정책)
	 * 
	 * @param epubDetail
	 * @param usePolicyInfo
	 * @return EpubDetail
	 */
	private VodDetail setUsePolicyInfo(VodDetail vodDetail, VodDetail usePolicyInfo) {

		vodDetail.setStoreProdId(usePolicyInfo.getStoreProdId());
		vodDetail.setStoreDrmYn(usePolicyInfo.getStoreDrmYn());
		vodDetail.setStoreDlStrmCd(usePolicyInfo.getStoreDlStrmCd());
		vodDetail.setStoreUsePeriod(usePolicyInfo.getStoreUsePeriod());
		vodDetail.setStoreUsePeriodUnitCd(usePolicyInfo.getStoreUsePeriodUnitCd());
		
		vodDetail.setPlayProdId(usePolicyInfo.getPlayProdId());
		vodDetail.setPlayDrmYn(usePolicyInfo.getPlayDrmYn());
		vodDetail.setPlayUsePeriod(usePolicyInfo.getPlayUsePeriod());
		vodDetail.setPlayUsePeriodUnitCd(usePolicyInfo.getPlayUsePeriodUnitCd());
		vodDetail.setPlayDlStrmCd(usePolicyInfo.getPlayDlStrmCd());

		return vodDetail;
	}

	/**
	 * 채널의 상품 이용정책 조회 (가장 최신 Chapter의 Episode 이용정책 조회)
	 * 
	 * @param param
	 * @return
	 */
	private VodDetail getProdUsePolicyInfo(Map<String, Object> param) {
		logger.debug("param={}", param);
		return this.commonDAO.queryForObject("VodDetail.getProdUsePolicyInfo", param, VodDetail.class);
	}
}
