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

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.apache.xmlbeans.impl.util.Base64;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.skplanet.storeplatform.framework.core.exception.StorePlatformException;
import com.skplanet.storeplatform.framework.core.persistence.dao.CommonDAO;
import com.skplanet.storeplatform.framework.test.JacksonMarshallingHelper;
import com.skplanet.storeplatform.framework.test.MarshallingHelper;
import com.skplanet.storeplatform.sac.client.display.vo.download.DownloadVodSacReq;
import com.skplanet.storeplatform.sac.client.display.vo.download.DownloadVodSacRes;
import com.skplanet.storeplatform.sac.client.internal.purchase.history.sci.HistoryInternalSCI;
import com.skplanet.storeplatform.sac.client.internal.purchase.history.vo.HistoryListSacInReq;
import com.skplanet.storeplatform.sac.client.internal.purchase.history.vo.HistoryListSacInRes;
import com.skplanet.storeplatform.sac.client.internal.purchase.history.vo.ProductListSacIn;
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
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.product.Encryption;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.product.EncryptionContents;
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
import com.skplanet.storeplatform.sac.display.common.constant.DisplayConstants;
import com.skplanet.storeplatform.sac.display.common.service.DisplayCommonService;
import com.skplanet.storeplatform.sac.display.meta.vo.MetaInfo;
import com.skplanet.storeplatform.sac.display.response.CommonMetaInfoGenerator;
import com.skplanet.storeplatform.sac.display.response.EncryptionGenerator;
import com.skplanet.storeplatform.sac.display.response.VodGenerator;
import com.skplanet.storeplatform.sac.purchase.constant.PurchaseConstants;
import com.thoughtworks.xstream.core.util.Base64Encoder;

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
	HistoryInternalSCI historyInternalSCI;
	@Autowired
	private CommonMetaInfoGenerator commonGenerator;
	@Autowired
	private VodGenerator vodGenerator;
	@Autowired
	private EncryptionGenerator encryptionGenerator;
	@Autowired
	private DownloadAES128Helper downloadAES128Helper;

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

		MetaInfo downloadSystemDate = this.commonDAO.queryForObject("Download.selectDownloadSystemDate", "",
				MetaInfo.class);

		downloadVodSacReq.setTenantId(tanantHeader.getTenantId());
		downloadVodSacReq.setDeviceModelCd(deviceHeader.getModel());
		downloadVodSacReq.setLangCd(tanantHeader.getLangCd());
		downloadVodSacReq.setImageCd(DisplayConstants.DP_VOD_REPRESENT_IMAGE_CD);

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

			// 필수 파라미터 체크
			if (StringUtils.isEmpty(idType)) {
				throw new StorePlatformException("SAC_DSP_0002", "idType", idType);
			}
			if (StringUtils.isEmpty(productId)) {
				throw new StorePlatformException("SAC_DSP_0002", "productId", productId);
			}
			if (StringUtils.isEmpty(deviceKey)) {
				throw new StorePlatformException("SAC_DSP_0002", "deviceKey", deviceKey);
			}
			if (StringUtils.isEmpty(userKey)) {
				throw new StorePlatformException("SAC_DSP_0002", "userKey", userKey);
			}

			// ID유형 유효값 체크
			if (!"channel".equals(idType) && !"episode".equals(idType)) {
				throw new StorePlatformException("SAC_DSP_0003", "idType", idType);
			}

			// 다운로드 Vod 상품 조회
			MetaInfo metaInfo = this.commonDAO.queryForObject("Download.getDownloadVodInfo", downloadVodSacReq,
					MetaInfo.class);

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

			if (metaInfo != null) {

				String prchsId = null;
				String prchsDt = null;
				String dwldExprDt = null;
				String prchsState = null;
				String prchsProdId = null;

				try {
					// 구매내역 조회를 위한 생성자
					ProductListSacIn productListSacIn = new ProductListSacIn();
					List<ProductListSacIn> productList = new ArrayList<ProductListSacIn>();

					productListSacIn.setProdId(metaInfo.getStoreProdId());
					productList.add(productListSacIn);

					productListSacIn = new ProductListSacIn();
					productListSacIn.setProdId(metaInfo.getPlayProdId());
					productList.add(productListSacIn);

					HistoryListSacInReq historyListSacReq = new HistoryListSacInReq();
					historyListSacReq.setTenantId(downloadVodSacReq.getTenantId());
					historyListSacReq.setUserKey(downloadVodSacReq.getUserKey());
					historyListSacReq.setDeviceKey(downloadVodSacReq.getDeviceKey());
					historyListSacReq.setPrchsProdType(PurchaseConstants.PRCHS_PROD_TYPE_OWN);
					historyListSacReq.setStartDt("19000101000000");
					historyListSacReq.setEndDt(metaInfo.getSysDate());
					historyListSacReq.setOffset(1);
					historyListSacReq.setCount(1);
					historyListSacReq.setProductList(productList);

					// 구매내역 조회 실행
					HistoryListSacInRes historyListSacRes = this.historyInternalSCI
							.searchHistoryList(historyListSacReq);

					if (historyListSacRes.getTotalCnt() > 0) {
						prchsId = historyListSacRes.getHistoryList().get(0).getPrchsId();
						prchsDt = historyListSacRes.getHistoryList().get(0).getPrchsDt();
						dwldExprDt = historyListSacRes.getHistoryList().get(0).getDwldExprDt();
						prchsState = historyListSacRes.getHistoryList().get(0).getPrchsCaseCd();
						prchsProdId = historyListSacRes.getHistoryList().get(0).getProdId();

						// 소장
						if (prchsProdId.equals(metaInfo.getStoreProdId())) {
							if (DisplayConstants.PRCHS_CASE_PURCHASE_CD.equals(prchsState)) {
								prchsState = "payment";
							} else if (DisplayConstants.PRCHS_CASE_GIFT_CD.equals(prchsState)) {
								prchsState = "gift";
							}
						} else {
							downloadVodSacReq.setPrchsDt(prchsDt);
							downloadVodSacReq.setDwldExprDt(dwldExprDt);

							// 대여 상품 구매상태 조회
							prchsState = (String) this.commonDAO.queryForObject("Download.getEbookPurchaseState",
									downloadVodSacReq);
						}
					}
				} catch (Exception ex) {
					throw new StorePlatformException("SAC_DSP_0001", "구매내역 조회 ", ex);
				}

				/************************************************************************************************
				 * 상품 정보
				 ************************************************************************************************/
				product = new Product();
				metaInfo.setContentsTypeCd(DisplayConstants.DP_CHANNEL_CONTENT_TYPE_CD);
				product.setIdentifierList(this.commonGenerator.generateIdentifierList(metaInfo));
				supportList = new ArrayList<Support>();
				supportList.add(this.commonGenerator.generateSupport(DisplayConstants.DP_VOD_HDCP_SUPPORT_NM,
						metaInfo.getHdcpYn()));
				supportList.add(this.commonGenerator.generateSupport(DisplayConstants.DP_VOD_HD_SUPPORT_NM,
						metaInfo.getHdvYn()));
				supportList.add(this.commonGenerator.generateSupport(DisplayConstants.DP_VOD_BTV_SUPPORT_NM,
						metaInfo.getBtvYn()));
				supportList.add(this.commonGenerator.generateSupport(DisplayConstants.DP_VOD_DOLBY_NM,
						metaInfo.getDolbySprtYn()));
				product.setSupportList(supportList);
				product.setTitle(this.commonGenerator.generateTitle(metaInfo)); // 상품명
				product.setMenuList(this.commonGenerator.generateMenuList(metaInfo)); // 상품 메뉴정보
				product.setSourceList(this.commonGenerator.generateSourceList(metaInfo)); // 상품 이미지정보
				product.setVod(this.vodGenerator.generateVod(metaInfo)); // VOD 정보
				product.setRights(this.commonGenerator.generateRights(metaInfo)); // 이용등급 및 소장/대여 정보
				product.setDistributor(this.commonGenerator.generateDistributor(metaInfo)); // 판매자 정보

				// 구매 정보
				if (StringUtils.isNotEmpty(prchsId)) {
					metaInfo.setPurchaseId(prchsId);
					metaInfo.setPurchaseDt(prchsDt);
					metaInfo.setPurchaseState(prchsState);
					metaInfo.setPurchaseProdId(prchsProdId);
					product.setPurchase(this.commonGenerator.generatePurchase(metaInfo));

					metaInfo.setExpiredDate(downloadSystemDate.getExpiredDate());
					metaInfo.setDwldExprDt(dwldExprDt);
					metaInfo.setBpJoinFileType(DisplayConstants.DP_FORDOWNLOAD_BP_DEFAULT_TYPE);

					metaInfo.setUserKey(downloadVodSacReq.getUserKey());
					metaInfo.setDeviceKey(downloadVodSacReq.getDeviceKey());
					metaInfo.setDeviceType("");
					metaInfo.setDeviceSubKey("");

					// 소장, 대여 구분(Store : 소장, Play : 대여)
					if (prchsProdId.equals(metaInfo.getStoreProdId())) {
						metaInfo.setDrmYn(metaInfo.getStoreDrmYn());
						metaInfo.setProdChrg(metaInfo.getStoreProdChrg());
					} else {
						metaInfo.setDrmYn(metaInfo.getPlayDrmYn());
						metaInfo.setProdChrg(metaInfo.getPlayProdChrg());
					}

					// 암호화 정보
					EncryptionContents contents = this.encryptionGenerator.generateEncryptionContents(metaInfo);

					MarshallingHelper marshaller = new JacksonMarshallingHelper();
					byte[] jsonData = marshaller.marshal(contents);

					// JSON 암호화
					byte[] encryptByte = this.downloadAES128Helper.encryption(jsonData);

					Encryption encryption = new Encryption();
					encryption.setType(DisplayConstants.DP_FORDOWNLOAD_ENCRYPT_TYPE + "/"
							+ this.downloadAES128Helper.getSAC_RANDOM_NUMBER());

					// JSON 암호화값을 BASE64 Encoding
					Base64Encoder encoder = new Base64Encoder();
					String encryptString = encoder.encode(encryptByte);
					encryption.setText(encryptString);

					product.setEncryption(encryption);

					try {
						Encryption testEn = new Encryption();
						testEn = product.getEncryption();

						byte[] testValue = Base64.decode(testEn.getText().getBytes());
						byte[] dec = this.downloadAES128Helper.decryption(testValue);

						this.log.debug("----------------------------------------------------------------");
						this.log.debug("Encryption Type : {}", testEn.getType());
						this.log.debug("Encryption Text : {}", testEn.getText());
						this.log.debug("Decryption Text : {}", new String(dec, "UTF-8"));
						this.log.debug("----------------------------------------------------------------");
					} catch (UnsupportedEncodingException e) {
						e.printStackTrace();
					}
				}

				commonResponse.setTotalCount(1);
			} else {
				commonResponse.setTotalCount(0);
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
			identifier.setType("channel");
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
			menuList.add(menu);
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
			List<Support> playSupportList = new ArrayList<Support>();
			List<Identifier> playIdentifierList = new ArrayList<Identifier>();
			Price playPrice = new Price();

			identifier = new Identifier();
			identifier.setType(DisplayConstants.DP_EPISODE_IDENTIFIER_CD);
			identifier.setText("H001601609");
			playIdentifierList.add(identifier);

			playSupport.setType(DisplayConstants.DP_DRM_SUPPORT_NM);
			playSupport.setText("Y");
			playSupportList.add(playSupport);

			date = new Date();
			date.setType(DisplayConstants.DP_DATE_USAGE_PERIOD);
			date.setText("30일");
			playPrice.setText(800);

			play.setIdentifierList(playIdentifierList);
			play.setSupportList(playSupportList);
			play.setDate(date); // 이용기간
			play.setPrice(playPrice); // 바로보기 상품 금액

			play.setSupport(playSupport);
			play.setDate(date); // 이용기간
			play.setPrice(playPrice); // 바로보기 상품 금액
			play.setNetworkRestrict("ota");

			rights.setPlay(play);

			Support storeSupport = new Support();
			List<Support> storeSupportList = new ArrayList<Support>();
			List<Identifier> storeIdentifierList = new ArrayList<Identifier>();
			Price storePrice = new Price();

			identifier = new Identifier();
			identifier.setType(DisplayConstants.DP_EPISODE_IDENTIFIER_CD);
			identifier.setText("H001601609");
			storeIdentifierList.add(identifier);

			storeSupport.setType("drm");
			storeSupport.setText("Y");
			storeSupportList.add(storeSupport);

			storePrice.setText(1200);

			store.setIdentifierList(storeIdentifierList);
			store.setSupportList(storeSupportList);
			store.setPrice(storePrice);
			store.setNetworkRestrict("ota");

			rights.setStore(store);

			distributor.setName("서진우");
			distributor.setTel("0215990011");
			distributor.setEmail("skplanet_vod@tstore.co.kr");
			distributor.setRegNo("중구-02923호");

			// 구매 정보
			purchase.setState("payment");
			List<Identifier> purchaseIdentifierList = new ArrayList<Identifier>();

			identifier = new Identifier();
			identifier.setType(DisplayConstants.DP_PURCHASE_IDENTIFIER_CD);
			identifier.setText("MI100000000000044286");
			purchaseIdentifierList.add(identifier);

			identifier = new Identifier();
			identifier.setType(DisplayConstants.DP_EPISODE_IDENTIFIER_CD);
			identifier.setText("0000395599");
			purchaseIdentifierList.add(identifier);

			purchase.setIdentifierList(purchaseIdentifierList);

			date = new Date();
			date.setType("date/purchase");
			date.setText("20130722143732");
			purchase.setDate(date);

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
