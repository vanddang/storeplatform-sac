/*
 * Copyright (c) 2013 SK planet.
 * All right reserved.
 *
 * This software is the confidential and proprietary information of SK planet.
 * You shall not disclose such Confidential Information and
 * shall use it only in accordance with the terms of the license agreement
 * you entered into with SK planet.
 */
package com.skplanet.storeplatform.sac.display.download.service;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.skplanet.storeplatform.framework.core.exception.StorePlatformException;
import com.skplanet.storeplatform.framework.core.persistence.dao.CommonDAO;
import com.skplanet.storeplatform.purchase.client.history.vo.ExistenceList;
import com.skplanet.storeplatform.purchase.client.history.vo.ExistenceRequest;
import com.skplanet.storeplatform.purchase.client.history.vo.ExistenceResponse;
import com.skplanet.storeplatform.sac.client.display.vo.download.DownloadVodSacReq;
import com.skplanet.storeplatform.sac.client.display.vo.download.DownloadVodSacRes;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.common.CommonResponse;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.common.Date;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.common.Identifier;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.common.Menu;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.common.Price;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.common.Source;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.common.Time;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.common.Title;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.product.Chapter;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.product.Distributor;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.product.Play;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.product.Product;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.product.Purchase;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.product.Rights;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.product.Store;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.product.Support;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.product.VideoInfo;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.product.Vod;
import com.skplanet.storeplatform.sac.common.header.vo.DeviceHeader;
import com.skplanet.storeplatform.sac.common.header.vo.SacRequestHeader;
import com.skplanet.storeplatform.sac.common.header.vo.TenantHeader;
import com.skplanet.storeplatform.sac.display.common.DisplayCommonUtil;
import com.skplanet.storeplatform.sac.display.common.constant.DisplayConstants;
import com.skplanet.storeplatform.sac.display.common.service.DisplayCommonService;
import com.skplanet.storeplatform.sac.display.download.vo.DownloadVod;
import com.skplanet.storeplatform.sac.purchase.history.service.ExistenceService;

/**
 * ProductCategory Service 인터페이스(CoreStoreBusiness) 구현체
 * 
 * Updated on : 2014. 1. 22. Updated by : 이석희, 인크로스.
 */
@Service
@Transactional
public class DownloadVodServiceImpl implements DownloadVodService {

	private final Logger log = LoggerFactory.getLogger(this.getClass());

	@Autowired
	@Qualifier("sac")
	private CommonDAO commonDAO;
	@Autowired
	private DisplayCommonService commonService;

	@Autowired
	ExistenceService existenceService;

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.skplanet.storeplatform.sac.biz.product.service.DownloadAppService#DownloadAppService(com.skplanet
	 * .storeplatform.sac.client.product.vo.DownloadAppReqVO)
	 */
	@Override
	public DownloadVodSacRes searchDownloadVod(SacRequestHeader requestheader, DownloadVodSacReq downloadVodSacReq) {
		TenantHeader tanantHeader = requestheader.getTenantHeader();
		DeviceHeader deviceHeader = requestheader.getDeviceHeader();

		downloadVodSacReq.setTenantId(tanantHeader.getTenantId());
		downloadVodSacReq.setDeviceModelCd(deviceHeader.getModel());
		downloadVodSacReq.setLangCd(tanantHeader.getLangCd());
		downloadVodSacReq.setImgCd(DisplayConstants.DP_VOD_REPRESENT_IMAGE_CD);

		DownloadVodSacRes response = new DownloadVodSacRes();
		CommonResponse commonResponse = new CommonResponse();

		int totalCount = 0;
		String idType = downloadVodSacReq.getIdType();
		String productId = downloadVodSacReq.getProductId();
		String deviceKey = downloadVodSacReq.getDeviceKey();
		String userKey = downloadVodSacReq.getUserKey();

		List<Identifier> identifierList = null;
		List<Menu> menuList = null;
		List<Support> supportList = null;
		List<Source> sourceList = null;
		List<VideoInfo> videoInfoList = null;

		Product product = null;
		Identifier identifier = null;
		Support support = null;
		Rights rights = null;
		Source source = null;
		Title title = null;
		Menu menu = null;
		Purchase purchase = null;
		Vod vod = null;
		Time runningTime = null;
		VideoInfo videoInfo = null;
		Store store = null;
		Play play = null;
		Price price = null;
		Chapter chapter = null;
		Date date = null;
		Distributor distributor = new Distributor();

		if (downloadVodSacReq.getDummy() == null) {

			DownloadVod downloadVodInfo = null;

			// 필수 파라미터 체크
			if (StringUtils.isEmpty(idType) || StringUtils.isEmpty(productId) || StringUtils.isEmpty(deviceKey)
					|| StringUtils.isEmpty(userKey)) {

				// throw new StorePlatformException("필수 파라미터가 부족합니다.");
				throw new StorePlatformException("필수 파라미터가 부족합니다.", "1", "2", "3");
			}
			// ID유형 유효값 체크
			if (!DisplayConstants.DP_CHANNEL_IDENTIFIER_CD.equals(idType)
					&& !DisplayConstants.DP_EPISODE_IDENTIFIER_CD.equals(idType)) {
				throw new StorePlatformException("유효하지않은 ID유형 입니다.", "1", "2", "3");
			}

			try {
				// 다운로드 Vod 상품 조회
				downloadVodInfo = this.commonDAO.queryForObject("Download.getDownloadVodInfo", downloadVodSacReq,
						DownloadVod.class);

				menuList = new ArrayList<Menu>();
				sourceList = new ArrayList<Source>();
				identifierList = new ArrayList<Identifier>();
				videoInfoList = new ArrayList<VideoInfo>();

				product = new Product();
				identifier = new Identifier();
				support = new Support();
				rights = new Rights();
				source = new Source();
				title = new Title();
				purchase = new Purchase();
				vod = new Vod();
				runningTime = new Time();
				store = new Store();
				price = new Price();
				chapter = new Chapter();
				play = new Play();

				if (downloadVodInfo != null) {

					String prchsId = null;

					if (DisplayConstants.DP_EPISODE_IDENTIFIER_CD.equals(idType)) {
						try {
							// 기구매 체크를 위한 생성자
							ExistenceRequest existenceRequest = new ExistenceRequest();
							existenceRequest.setTenantId(downloadVodSacReq.getTenantId());
							existenceRequest.setInsdUsermbrNo(downloadVodSacReq.getUserKey());
							existenceRequest.setInsdDeviceId(downloadVodSacReq.getDeviceKey());
							existenceRequest.setPrchsId("M1040449015718184793");

							ExistenceList existenceList = new ExistenceList();
							// existenceList.setProdId(downloadVodSacReq.getProductId());
							existenceList.setProdId("H900000037"); // 기구매 테스트용 상품 ID

							List<ExistenceList> list = new ArrayList<ExistenceList>();
							list.add(existenceList);
							existenceRequest.setExistenceList(list);

							// 기구매 체크 실행
							List<ExistenceResponse> existenceResponseList = this.existenceService
									.listExist(existenceRequest);

							if (!existenceResponseList.isEmpty()) {
								this.log.debug("----------------------------------------------------------------");
								this.log.debug("구매 상품");
								this.log.debug("----------------------------------------------------------------");

								prchsId = existenceResponseList.get(0).getPrchsId();
							} else {
								this.log.debug("----------------------------------------------------------------");
								this.log.debug("미구매 상품");
								this.log.debug("----------------------------------------------------------------");
							}
						} catch (Exception e) {
							throw new StorePlatformException("ERROR_0001", "1", "2", "3");
						}
					}

					// 상품ID
					identifier = new Identifier();
					identifier.setType(DisplayConstants.DP_CHANNEL_IDENTIFIER_CD);
					identifier.setText(downloadVodInfo.getProdId());
					identifierList.add(identifier);

					supportList = new ArrayList<Support>();
					support = new Support();
					support.setType(DisplayConstants.DP_VOD_HDCP_SUPPORT_NM);
					support.setText(downloadVodInfo.getHdcpYn());
					supportList.add(support);
					support = new Support();
					support.setType(DisplayConstants.DP_VOD_HD_SUPPORT_NM);
					support.setText(downloadVodInfo.getHdvYn());
					supportList.add(support);
					support = new Support();
					support.setType(DisplayConstants.DP_VOD_BTV_SUPPORT_NM);
					support.setText(downloadVodInfo.getBtvYn());
					supportList.add(support);
					support = new Support();
					support.setType(DisplayConstants.DP_VOD_DOLBY_NM);
					support.setText(downloadVodInfo.getDolbySprtYn());
					supportList.add(support);

					title.setText(downloadVodInfo.getProdNm());

					/*
					 * Menu(메뉴정보) Id, Name, Type
					 */
					menu = new Menu();
					menu.setId(downloadVodInfo.getTopMenuId());
					menu.setName(downloadVodInfo.getTopMenuNm());
					menu.setType(DisplayConstants.DP_MENU_TOPCLASS_TYPE);
					menuList.add(menu);
					menu = new Menu();
					menu.setId(downloadVodInfo.getMenuId());
					menu.setName(downloadVodInfo.getMenuNm());
					menuList.add(menu);
					menu = new Menu();
					menu.setId(downloadVodInfo.getMetaClsfCd());
					menu.setType(DisplayConstants.DP_META_CLASS_MENU_TYPE);
					menuList.add(menu);

					/*
					 * source mediaType, size, type, url
					 */
					source.setMediaType(DisplayCommonUtil.getMimeType(downloadVodInfo.getImgPath()
							+ downloadVodInfo.getImgNm()));
					source.setType(DisplayConstants.DP_SOURCE_TYPE_THUMBNAIL);
					source.setUrl(downloadVodInfo.getImgPath() + downloadVodInfo.getImgNm());
					source.setSize(downloadVodInfo.getImgSize());
					sourceList.add(source);

					runningTime.setText(downloadVodInfo.getEpsdPlayTm());
					vod.setRunningTime(runningTime);
					chapter.setUnit(downloadVodInfo.getChapterUnit());
					chapter.setText(Integer.parseInt(downloadVodInfo.getChapter()));
					videoInfo = new VideoInfo();

					/*
					 * 일반화질 정보
					 */
					if (StringUtils.isNotEmpty(downloadVodInfo.getNmBtvCid())) {
						videoInfo.setBtvcid(downloadVodInfo.getNmBtvCid());
						videoInfo.setPictureSize(downloadVodInfo.getNmDpPicRatio());
						videoInfo.setPixel(downloadVodInfo.getNmDpPixel());
						videoInfo.setScid(downloadVodInfo.getNmSubContsId());
						videoInfo.setSize(downloadVodInfo.getNmFileSize().toString());
						videoInfo.setType(DisplayConstants.DP_VOD_QUALITY_NORMAL);
						videoInfo.setVersion(downloadVodInfo.getNmProdVer());
						videoInfoList.add(videoInfo);
					}
					/*
					 * SD 고화질 정보
					 */
					if (StringUtils.isNotEmpty(downloadVodInfo.getSdBtvCid())) {
						videoInfo = new VideoInfo();
						videoInfo.setBtvcid(downloadVodInfo.getSdBtvCid());
						videoInfo.setPictureSize(downloadVodInfo.getSdDpPicRatio());
						videoInfo.setPixel(downloadVodInfo.getSdDpPixel());
						videoInfo.setScid(downloadVodInfo.getSdSubContsid());
						videoInfo.setSize(downloadVodInfo.getSdFileSize().toString());
						videoInfo.setType(DisplayConstants.DP_VOD_QUALITY_SD);
						videoInfo.setVersion(downloadVodInfo.getSdProdVer());
						videoInfoList.add(videoInfo);
					}
					/*
					 * HD 고화질 정보
					 */
					if (StringUtils.isNotEmpty(downloadVodInfo.getHdBtvCid())) {
						videoInfo = new VideoInfo();
						videoInfo.setBtvcid(downloadVodInfo.getHdBtvCid());
						videoInfo.setPictureSize(downloadVodInfo.getHdDpPicRatio());
						videoInfo.setPixel(downloadVodInfo.getHdDpPixel());
						videoInfo.setScid(downloadVodInfo.getHdSubContsid());
						videoInfo.setSize(downloadVodInfo.getHdFileSize().toString());
						videoInfo.setType(DisplayConstants.DP_VOD_QUALITY_HD);
						videoInfo.setVersion(downloadVodInfo.getHdProdVer());
						videoInfoList.add(videoInfo);
					}
					vod.setChapter(chapter);
					vod.setVideoInfoList(videoInfoList);

					/*
					 * Rights grade
					 */
					rights.setAllow(downloadVodInfo.getDwldAreaLimtYn());
					rights.setGrade(downloadVodInfo.getProdGrdCd());

					/*
					 * play 정보
					 */
					if (StringUtils.isNotEmpty(downloadVodInfo.getPlayProdId())) {
						Support playSupport = new Support();
						Price playPrice = new Price();
						playSupport.setType(DisplayConstants.DP_DRM_SUPPORT_NM);
						playSupport.setText(downloadVodInfo.getPlayDrmYn());
						play.setSupport(playSupport);

						date = new Date();
						date.setType(DisplayConstants.DP_DATE_USAGE_PERIOD);
						date.setText(downloadVodInfo.getUsagePeriod());
						playPrice.setText(downloadVodInfo.getPlayProdAmt() == null ? 0 : downloadVodInfo
								.getPlayProdAmt());

						Source playSource = new Source();
						playSource.setUrl(downloadVodInfo.getPlayProdId());

						play.setDate(date); // 이용기간
						play.setPrice(playPrice); // 바로보기 상품 금액
						play.setSource(playSource); // 바로보기 상품 url
						if (downloadVodInfo.getStrmNetworkCd() != null) {
							play.setNetworkRestrict(DisplayConstants.DP_NETWORK_RESTRICT);
						}
						rights.setPlay(play);
					}

					/*
					 * Store 정보
					 */
					if (StringUtils.isNotEmpty(downloadVodInfo.getStoreProdId())) {
						Support storeSupport = new Support();
						Price storePrice = new Price();
						Source storeSource = new Source();

						storeSupport.setType(DisplayConstants.DP_DRM_SUPPORT_NM);
						storeSupport.setText(downloadVodInfo.getStoreDrmYn());
						store.setSupport(storeSupport);

						storePrice.setText(downloadVodInfo.getStoreProdAmt() == null ? 0 : downloadVodInfo
								.getStoreProdAmt());
						storeSource.setUrl(downloadVodInfo.getStoreProdId());

						store.setPrice(storePrice);
						store.setSource(storeSource);

						// 네트워크 제한이 있을경우
						if (downloadVodInfo.getDwldNetworkCd() != null) {
							store.setNetworkRestrict(DisplayConstants.DP_NETWORK_RESTRICT);
						}
						rights.setStore(store);

					}
					/*
					 * 판매자 정보.
					 */
					distributor.setName(StringUtils.isEmpty(downloadVodInfo.getExpoSellerNm()) ? "" : downloadVodInfo
							.getExpoSellerNm());
					distributor.setTel(StringUtils.isEmpty(downloadVodInfo.getExpoSellerTelno()) ? "" : downloadVodInfo
							.getExpoSellerTelno());
					distributor
							.setEmail(StringUtils.isEmpty(downloadVodInfo.getExpoSellerEmail()) ? "" : downloadVodInfo
									.getExpoSellerEmail());
					distributor.setRegNo(StringUtils.isEmpty(downloadVodInfo.getSellerMbrNo()) ? "" : downloadVodInfo
							.getSellerMbrNo());

					// 구매 정보
					identifier = new Identifier();
					identifier.setType(DisplayConstants.DP_PURCHASE_IDENTIFIER_CD);
					identifier.setText(prchsId);
					purchase.setIdentifier(identifier);
					purchase.setPurchaseFlag(StringUtils.isNotEmpty(prchsId) ? "payment" : "nonPayment");
					product.setPurchase(purchase);

					commonResponse.setTotalCount(1);
				}
				product = new Product();
				product.setIdentifierList(identifierList);
				product.setSupportList(supportList);
				product.setTitle(title);
				product.setMenuList(menuList);
				product.setSourceList(sourceList);
				product.setVod(vod);
				product.setRights(rights);
				product.setDistributor(distributor);
				product.setPurchase(purchase);
			} catch (Exception ex) {
				throw new StorePlatformException("ERROR_0001", ex);
			}
		} else {
			// dummy data를 호출할때
			menuList = new ArrayList<Menu>();
			sourceList = new ArrayList<Source>();
			identifierList = new ArrayList<Identifier>();
			videoInfoList = new ArrayList<VideoInfo>();

			product = new Product();
			identifier = new Identifier();
			support = new Support();
			rights = new Rights();
			source = new Source();
			title = new Title();
			purchase = new Purchase();
			vod = new Vod();
			runningTime = new Time();
			store = new Store();
			price = new Price();
			chapter = new Chapter();
			play = new Play();

			// 상품ID
			identifier = new Identifier();
			identifier.setType("episode");
			identifier.setText("H001601609");
			identifierList.add(identifier);

			supportList = new ArrayList<Support>();
			support = new Support();
			support = new Support();
			support.setType("hdcp");
			support.setText("Y");
			supportList.add(support);
			support = new Support();
			support.setType("hd");
			support.setText("N");
			supportList.add(support);
			support = new Support();
			support.setType("btv");
			support.setText("Y");
			supportList.add(support);
			support = new Support();
			support.setType("dolby");
			support.setText("N");
			supportList.add(support);

			title.setText("대지진");

			/*
			 * Menu(메뉴정보) Id, Name, Type
			 */
			menu = new Menu();
			menu.setId("DP000517");
			menu.setName("영화");
			menu.setType("topClass");
			menuList.add(menu);
			menu = new Menu();
			menu.setId("DP17004");
			menu.setName("드라마");
			menu = new Menu();
			menu.setId("CT13");
			menu.setType("metaClass");
			menuList.add(menu);

			/*
			 * source mediaType, size, type, url
			 */
			source.setMediaType("image/png");
			source.setType("thumbnail");
			source.setUrl("http://wap.tstore.co.kr/SMILE_DATA7/PVOD/201310/31/0001747842/5/0003526438/5/RT1_00000191803_1_0346_182x261_130x186.PNG");
			sourceList.add(source);

			runningTime.setText("135");
			vod.setRunningTime(runningTime);
			chapter.setUnit("회");
			chapter.setText(1);

			videoInfo = new VideoInfo();
			videoInfo.setBtvcid("2222222222");
			videoInfo.setPictureSize("16:9");
			videoInfo.setPixel("576x324");
			videoInfo.setScid("0003526439");
			videoInfo.setSize("575311754");
			videoInfo.setType("normal");
			videoInfo.setVersion("1");
			videoInfoList.add(videoInfo);
			videoInfo = new VideoInfo();
			videoInfo.setBtvcid("2222222222");
			videoInfo.setPictureSize("16:9");
			videoInfo.setPixel("720x400");
			videoInfo.setScid("0003526440");
			videoInfo.setSize("1146264845");
			videoInfo.setType("sd");
			videoInfo.setVersion("1");
			videoInfoList.add(videoInfo);
			videoInfo = new VideoInfo();
			videoInfo.setBtvcid("2222222222");
			videoInfo.setPictureSize("16:9");
			videoInfo.setPixel("1280x720");
			videoInfo.setScid("0003526441");
			videoInfo.setSize("2028754302");
			videoInfo.setType("hd");
			videoInfo.setVersion("1");
			videoInfoList.add(videoInfo);
			vod.setChapter(chapter);
			vod.setVideoInfoList(videoInfoList);

			/*
			 * Rights grade
			 */
			rights.setAllow("Y");
			rights.setGrade("PD004401");

			Support playSupport = new Support();
			Price playPrice = new Price();
			playSupport.setType("drm");
			playSupport.setText("Y");

			date = new Date();
			date.setType("duration/usagePeriod");
			date.setText("30일");
			playPrice.setText(800);

			Source playSource = new Source();
			playSource.setUrl("/movie/drama/H001373322");

			play.setSupport(playSupport);
			play.setDate(date); // 이용기간
			play.setPrice(playPrice); // 바로보기 상품 금액
			play.setSource(playSource); // 바로보기 상품 url
			play.setNetworkRestrict("ota");

			rights.setPlay(play);

			Price storePrice = new Price();
			storePrice.setText(1200);
			Support storeSupport = new Support();
			storeSupport.setType("drm");
			storeSupport.setText("Y");
			store.setSupport(storeSupport);
			source = new Source();
			source.setUrl("/movie/drama/H001373322");
			store.setPrice(price);
			store.setSource(source);
			store.setNetworkRestrict("ota");

			rights.setStore(store);

			distributor.setName("서진우");
			distributor.setTel("0215990011");
			distributor.setEmail("skplanet_vod@tstore.co.kr");
			distributor.setRegNo("중구-02923호");

			identifier = new Identifier();
			identifier.setType("purchase");
			identifier.setText("GI100000000265812187");
			purchase.setIdentifier(identifier);
			purchase.setPurchaseFlag("payment");

			product = new Product();
			product.setIdentifierList(identifierList);
			product.setSupportList(supportList);
			product.setTitle(title);
			product.setMenuList(menuList);
			product.setSourceList(sourceList);
			product.setVod(vod);
			product.setRights(rights);
			product.setDistributor(distributor);
			product.setPurchase(purchase);

			commonResponse.setTotalCount(1);
		}

		response.setCommonResponse(commonResponse);
		response.setProduct(product);
		return response;
	}
}
